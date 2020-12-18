package com.webj.util;

import java.io.IOException;
import java.net.URL;

public class USize {

    public static void main(String[] args) throws IOException {

        URL dest = new URL("https://download.jetbrains.8686c.com/python/pycharm-community-2020.3.exe");
//        dest.openConnection().getHeaderFields().forEach((k,v)-> System.out.printf("%s = %s%n",k,v));
        System.out.println(dest.openConnection().getHeaderFields().get("Content-Length").toString());
    }

}
