package com.webj.util;

import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class UDownload {

    public static void main(String[] args) throws InterruptedException, IOException {

        ULine.main(null);

        String mp = System.getProperty("user.home");
        List<String> lines = Files.readAllLines(Paths.get(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\m3u8.txt"));
        ExecutorService executors = Executors.newFixedThreadPool(50);

        Collections.sort(lines);
        Stack<String> stack = new Stack<>();
        stack.addAll(lines);
        AtomicInteger cd = new AtomicInteger(lines.size());
        StopWatch timer = new StopWatch("download");
        timer.start();

        while (true) {
            String line = null;
            try {
                line = stack.pop();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("空等待");
                Thread.sleep(3000);
                continue;
            }

            String finalLine = line;
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!Uutil.isNetAvailable()) {
                            Thread.sleep(10000);
                        }
                        URLConnection conn = new URL(finalLine).openConnection();
                        conn.setConnectTimeout(10000);
                        conn.setReadTimeout(10000);
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        InputStream inStream = conn.getInputStream();
                        String fileName = StringUtils.getFilename(finalLine);
                        Files.copy(inStream, Paths.get(mp + "\\vv\\ts", fileName), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println(finalLine);
                        int now = cd.addAndGet(-1);
                        System.out.printf("剩余量: %s%n", now);
                        if (now == 0) {
                            timer.stop();
                            System.out.println(timer.toString());
                            Uutil.displayTrayAndEXit();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        stack.push(finalLine);
                    }
                }
            });
        }

    }


}
