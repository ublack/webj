package com.webj.util;

import org.jsoup.Jsoup;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UWoDeDyM3Get {

    public static void main(String[] args) throws IOException {

        Path urlFile = Paths.get("C:\\Users\\T460P\\IdeaProjects\\webj\\src\\main\\resources\\url.txt");

        String url = new String(Files.readAllBytes(urlFile));
        String curPart = url.substring(url.lastIndexOf("/"), url.lastIndexOf(".html")).split("-")[2];
        String urlScript  = Jsoup.connect(url).get().select("div.player").first().selectFirst("script").data();
        System.out.println(urlScript);

        String startPart = String.format("%02d", Integer.parseInt(curPart) + 1) + "%u96C6%24";
        String endPart = "%24zuidam3u8";
        int start = urlScript.indexOf(startPart) + startPart.length();
        int end = urlScript.indexOf(endPart, start);
        String m3u8AddrKkUrl = URLDecoder.decode(urlScript.substring(start, end));
        System.out.println(m3u8AddrKkUrl);

        String kkText = Jsoup.connect(m3u8AddrKkUrl).ignoreContentType(true).get().text();
        System.out.println(kkText);
        String kkContentToReplace = kkText.split(" ")[2];
        String m3u8Addr = m3u8AddrKkUrl.replace("index.m3u8", kkContentToReplace);
        String m3u8Content = Jsoup.connect(m3u8Addr).ignoreContentType(true).get().text();
        System.out.println(m3u8Content);
        // 下载用文件
        String[] m3lines = m3u8Content.split(" ");
        StringBuilder m3DownloadBuilder = new StringBuilder();
        for (String m3line : m3lines) {
            if (m3line.endsWith(".ts")) {
                m3DownloadBuilder.append(StringUtils.applyRelativePath(m3u8Addr, m3line)).append("\n");
            }
        }
        FileCopyUtils.copy(m3DownloadBuilder.toString().getBytes(),
                new File("C:\\Users\\T460P\\IdeaProjects\\webj\\src\\main\\resources\\m3u8.txt"));
        // 播放用文件
        StringBuilder playBuilder = new StringBuilder();
        for (String m3line : m3lines) {
            if (m3line.endsWith(".ts")) {
                playBuilder.append("http://localhost:7680/webj/ts/").append(m3line).append("\n");
            }else {
                playBuilder.append(m3line).append("\n");
            }
        }
        FileCopyUtils.copy(playBuilder.toString().getBytes(),
                new File("G:\\vv\\m3u8\\common\\index.m3u8"));

    }


}
