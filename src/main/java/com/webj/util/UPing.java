package com.webj.util;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UPing {

    public static void main(String[] args) throws IOException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(200);

        CountDownLatch cd = new CountDownLatch(254);
        for (int j = 1; j <= 254; j++) {
            int finalJ = j;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        InetAddress one = InetAddress.getByName("10.10.0."  + finalJ);
                        boolean reach = false;
                        reach = one.isReachable(30);
                        if (reach) {
                            System.out.println(one);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        cd.countDown();
//                        System.out.println("now " + cd.getCount());
                    }
                }
            });
        }
        cd.await();
        new CountDownLatch(1).await();
        System.exit(0);
    }


}
