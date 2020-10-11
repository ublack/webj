package com.webj.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TvUpdate {

    public static void main(String[] args) throws IOException {

        HashMap<String, String> ees = new LinkedHashMap<>();

        ees.put("一念永恒", "https://v.qq.com/detail/w/ww18u675tfmhas6.html");
        ees.put("仙风剑雨录", "https://v.qq.com/detail/m/mzc00200hc38s5x.html");
        ees.put("无上神帝", "https://v.qq.com/detail/m/mzc00200ilydv1a.html");
        ees.put("独步逍遥", "https://v.qq.com/detail/m/mzc00200qqsk3cv.html");
        ees.put("大话之少年游", "https://v.qq.com/detail/m/mzc00200bcfea29.html");
        ees.put("民调局异闻录", "https://v.qq.com/detail/m/mzc002009s5ivn8.html");
        ees.put("灵剑尊", "https://v.qq.com/detail/2/2w2legt0g8z26al.html");
        ees.put("斗罗大陆", "https://v.qq.com/detail/m/m441e3rjq9kwpsc.html");
        ees.put("武神主宰", "https://v.qq.com/detail/7/7q544xyrava3vxf.html");

        for (Map.Entry<String, String> entry : ees.entrySet()) {
            Document doc = Jsoup.connect(entry.getValue()).get();
            System.out.printf("%s  %s%n", entry.getKey(), doc.select("[itemprop=episodeNumber]").last().text());
            System.out.println(doc.select(".video_type_even").first().select(".type_txt").text());
            System.out.println();
        }

    }
}
