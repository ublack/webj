package com.webj.util;

import org.jsoup.Jsoup;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Uxp {

    public static void main(String[] args) throws Exception {
        String mp = System.getProperty("user.home");

        Path urlFile = Paths.get(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\url.txt");

        String url = new String(Files.readAllBytes(urlFile));
        String urlScript = Jsoup.connect(url).get().select("iframe").first().absUrl("src");
        System.out.println(urlScript);

        String startPart = "id=";
        int start = urlScript.indexOf(startPart) + startPart.length();
        String id = urlScript.substring(start);

        String m3u8Addr = "https://m3u8.cdnpan.com/" + id + ".m3u8";
        String m3u8Content = Jsoup.connect(m3u8Addr).ignoreContentType(true).get().text();
        System.out.println(m3u8Content);
        // 下载用文件
        String[] m3lines = m3u8Content.split(" ");
        StringBuilder m3DownloadBuilder = new StringBuilder();
        for (String m3line : m3lines) {
            if (m3line.endsWith(".ts")) {
                m3DownloadBuilder.append(m3line).append("\n");
            }
        }
        FileCopyUtils.copy(m3DownloadBuilder.toString().getBytes(),
                new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\m3u8.txt"));
        // 播放用文件
        StringBuilder playBuilder = new StringBuilder();
        for (String m3line : m3lines) {
            if (m3line.endsWith(".ts")) {
                playBuilder.append("http://localhost:7680/webj/ts/").append(StringUtils.getFilename(m3line)).append("\n");
            }else {
                playBuilder.append(m3line).append("\n");
            }
        }
        FileCopyUtils.copy(playBuilder.toString().getBytes(),
                new File(mp + "\\vv\\m3u8\\common\\index.m3u8"));

        UDownload.main(null);
    }


}
