package com.webj.util;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class ULine {

    public static void main(String[] args) throws IOException {
        String mp = System.getProperty("user.home");

        Path m3u8 = Paths.get(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\m3u8.txt");
        List<String> lines = Files.readAllLines(m3u8);
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            if (StringUtils.endsWithIgnoreCase(line, ".ts")) {
                result.append(line).append("\n");
            }
        }
        FileCopyUtils.copy(result.toString().getBytes(), new File(mp + "\\IdeaProjects\\webj\\src\\main\\resources\\m3u8.txt"));
    }


}
