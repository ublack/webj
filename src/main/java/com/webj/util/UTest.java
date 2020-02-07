package com.webj.util;


import java.util.Optional;

public class UTest {

    public static void main(String[] args) {

        System.out.println(Optional.ofNullable(null).orElse("abc"));

    }


}
