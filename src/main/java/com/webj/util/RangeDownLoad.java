package com.webj.util;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class RangeDownLoad {

    public static void main(String[] args) throws IOException, InterruptedException {

        ExecutorService executors = Executors.newFixedThreadPool(5);

        URL dest = new URL("https://www.python.org/ftp/python/3.9.1/python-3.9.1-amd64.exe");
//        dest.openConnection().getHeaderFields().forEach((k,v)-> System.out.printf("%s = %s%n",k,v));
        long len = dest.openConnection().getHeaderFieldLong("Content-Length", 0);
//        dest.openConnection().getHeaderFields().forEach((k, v) -> System.out.printf("%s,%s%n", k, Arrays.toString(v.toArray())));
        System.out.printf("文件长度:%s %n", FileUtils.byteCountToDisplaySize(len));

        String mp = System.getProperty("user.home");
        new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\obj.exe").delete();
        new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\obj.exe").createNewFile();

        RandomAccessFile destFile = new RandomAccessFile(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\obj.exe", "rw");
        FileChannel destChannel = destFile.getChannel();

        ArrayList<Long[]> ll = new ArrayList<>();
        long ut = 30000;
        long cn = 0;
        while (cn + ut < len) {
            Long[] one = new Long[]{cn, cn + ut};
            ll.add(one);
            cn = cn + ut + 1;
        }
        if (cn < len) {
            ll.add(new Long[]{cn + 1, len});
        }
        ll.stream().map(Arrays::toString).forEach(System.out::println);

        LinkedBlockingDeque<Long[]> stack = new LinkedBlockingDeque<>(ll);
        AtomicInteger cd = new AtomicInteger(ll.size());
        StopWatch timer = new StopWatch("download");
        timer.start();

        while (true) {

            Long[] one = stack.poll();
            if (one == null) {
                System.out.println("空等待");
                Thread.sleep(3000);
                continue;
            }

            executors.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!Uutil.isNetAvailable()) {
                            Thread.sleep(10000);
                        }
                        URLConnection conn = dest.openConnection();
                        conn.setConnectTimeout(10000);
                        conn.setReadTimeout(10000);
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty(HttpHeaders.RANGE, HttpRange.toString(Collections.singleton(HttpRange.createByteRange(one[0], one[1]))));
                        InputStream inStream = conn.getInputStream();

                        byte[] block = FileCopyUtils.copyToByteArray(inStream);
                        FileLock lock = destChannel.lock();
                        destChannel.position(one[0]);
                        destChannel.write(ByteBuffer.wrap(block));
                        lock.release();
                        System.out.println(Arrays.toString(one));

                        synchronized (UDownload.class) {
                            int now = cd.addAndGet(-1);
                            System.out.printf("剩余量: %s%n", now);
                            if (now == 0) {
                                timer.stop();
                                System.out.println(timer.toString());
                                Uutil.displayTrayAndEXit();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        stack.add(one);
                    }
                }
            });
        }
    }

}
