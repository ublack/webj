package com.webj.util;

import org.jsoup.Jsoup;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class UWoDeDyM3Get {

    public static void main(String[] args) throws IOException, InterruptedException {
        Logger logger = Logger.getGlobal();
        String mp = System.getProperty("user.home");

        Path urlFile = Paths.get(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\url.txt");

        String url = new String(Files.readAllBytes(urlFile));
        String urlScript  = Jsoup.connect(url).get().select("div.player").first().selectFirst("script").data();
        logger.info(urlScript);

        String startPart = "now=\"";
        String endPart = "\";var pn";
        int start = urlScript.indexOf(startPart) + startPart.length();
        int end = urlScript.indexOf(endPart, start);
        String m3u8AddrKkUrl = urlScript.substring(start, end);
        logger.info(m3u8AddrKkUrl);

        String kkText = Jsoup.connect(m3u8AddrKkUrl).ignoreContentType(true).get().text();
        logger.info(kkText);
        String kkContentToReplace = kkText.split(" ")[2];
        String m3u8Addr = m3u8AddrKkUrl.replace("index.m3u8", kkContentToReplace);
        String m3u8Content = Jsoup.connect(m3u8Addr).ignoreContentType(true).timeout(5000).get().text();
        logger.info(m3u8Content);
        // 下载用文件
        String[] m3lines = m3u8Content.split(" ");
        StringBuilder m3DownloadBuilder = new StringBuilder();
        for (String m3line : m3lines) {
            if (m3line.endsWith(".ts")) {
                m3DownloadBuilder.append(StringUtils.applyRelativePath(m3u8Addr, m3line)).append("\n");
            }
        }
        FileCopyUtils.copy(m3DownloadBuilder.toString().getBytes(),
                new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\m3u8.txt"));
        // 播放用文件
        StringBuilder playBuilder = new StringBuilder();
        for (String m3line : m3lines) {
            if (m3line.endsWith(".ts")) {
                playBuilder.append("http://192.168.124.3:7660/webj/ts/").append(m3line).append("\n");
            }else {
                playBuilder.append(m3line).append("\n");
            }
        }

        String dir = StringUtils.deleteAny(url,"http://www.tadedy.com/play/");
        boolean trd = new File(mp + "\\vv\\m3u8\\" + dir).mkdir();
        logger.info("目录创建:" + trd);
        boolean trf = new File(mp + "\\vv\\m3u8\\" + dir + "\\index.m3u8").createNewFile();
        logger.info("文件创建:" + trf);

        FileCopyUtils.copy(playBuilder.toString().getBytes(),
                new File(mp + "\\vv\\m3u8\\" + dir + "\\index.m3u8"));
        // 下载
        UDownload.main(null);

    }

}
