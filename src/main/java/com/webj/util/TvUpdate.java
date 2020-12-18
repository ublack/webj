package com.webj.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TvUpdate {

    public static void main(String[] args) throws IOException {

        String mp = System.getProperty("user.home");

        HashMap<String, String> ees = new LinkedHashMap<>();

        ees.put("一念永恒", "https://v.qq.com/detail/w/ww18u675tfmhas6.html");
        ees.put("仙风剑雨录", "https://v.qq.com/detail/m/mzc00200hc38s5x.html");
        ees.put("无上神帝", "https://v.qq.com/detail/m/mzc00200ilydv1a.html");
        ees.put("灵剑尊", "https://v.qq.com/detail/2/2w2legt0g8z26al.html");
        ees.put("斗罗大陆", "https://v.qq.com/detail/m/m441e3rjq9kwpsc.html");
        ees.put("武神主宰", "https://v.qq.com/detail/7/7q544xyrava3vxf.html");

        StringBuilder mem = new StringBuilder();

        for (Map.Entry<String, String> entry : ees.entrySet()) {
            Document doc = Jsoup.connect(entry.getValue()).get();
            String t1 = String.format("%s  %s%n", entry.getKey(), doc.select("[itemprop=episodeNumber]").last().text());
            String t2 = String.format("%s%n", doc.select(".video_type_even").first().select(".type_txt").text());
            mem.append(t1).append(t2).append(System.lineSeparator());
            System.out.println(t1);
            System.out.println(t2);
        }
        FileCopyUtils.copy(mem.toString().getBytes(),
                new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\mem.txt"));


    }
}
