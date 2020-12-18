package com.webj.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RangeDownLoadFor {

    public static void main(String[] args) throws IOException, InterruptedException {

        URL dest = new URL("http://pic1.win4000.com/wallpaper/2018-09-08/5b937be2bb3e1.jpg");
//        dest.openConnection().getHeaderFields().forEach((k,v)-> System.out.printf("%s = %s%n",k,v));
        long len = dest.openConnection().getHeaderFieldLong("Content-Length", 0);
//        dest.openConnection().getHeaderFields().forEach((k, v) -> System.out.printf("%s,%s%n", k, Arrays.toString(v.toArray())));
        System.out.printf("文件长度%s %n", len);
        String mp = System.getProperty("user.home");
        new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\obj.jpg").delete();
        new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\obj.jpg").createNewFile();

        RandomAccessFile destFile = new RandomAccessFile(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\obj.jpg", "rw");
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

        StopWatch timer = new StopWatch("download");
        timer.start();

        for (Long[] one : ll) {
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

        }
    }

}
