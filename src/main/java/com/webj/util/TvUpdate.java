package com.webj.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

public class TvUpdate {

    public static void main(String[] args) throws IOException {

        String mp = System.getProperty("user.home");

        HashMap<String, List<String>> ees = new LinkedHashMap<>();

        ees.put("一念永恒", Arrays.asList("https://v.qq.com/detail/w/ww18u675tfmhas6.html", "http://www.tadedy.com/play/50502-0-@@@.html"));
        ees.put("灵剑尊", Arrays.asList("https://v.qq.com/detail/2/2w2legt0g8z26al.html", "http://www.tadedy.com/play/28274-0-@@@.html"));
        ees.put("武神主宰", Arrays.asList("https://v.qq.com/detail/7/7q544xyrava3vxf.html", "http://www.tadedy.com/play/42462-0-@@@.html"));
        ees.put("万界法神", Arrays.asList("https://v.qq.com/detail/m/mzc002007995z4v.html", "http://www.tadedy.com/play/56155-0-@@@.html"));

//        StringBuilder mem = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : ees.entrySet()) {
            Document doc = Jsoup.connect(entry.getValue().get(0)).get();
            String episodeNumber = doc.select("[itemprop=episodeNumber]").last().text();
            if (episodeNumber.contains("集")) {
                episodeNumber = episodeNumber.substring(1, 3);
            }
            String t1 = String.format("%s  %s  %s%n", entry.getKey(), episodeNumber, entry.getValue().get(1).replaceFirst("@@@", String.valueOf(Integer.parseInt(episodeNumber) - 1)));

            String t2 = String.format("%s%n", doc.select(".video_type_even").first().select(".type_txt").text());
//            mem.append(t1).append(t2).append(System.lineSeparator());
            System.out.println(t1);
            System.out.println(t2);
            System.out.println();
        }
//        FileCopyUtils.copy(mem.toString().getBytes(),
//                new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\mem.txt"));
    }
}
