package com.webj.movie;

import org.springframework.util.FileSystemUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@WebServlet("/delete/*")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String mp = System.getProperty("user.home");
        String dirName = req.getParameter("movie");
        CompletableFuture.runAsync(() -> {

            List<String> names = null;
            try {
                names = Files.readAllLines(Paths.get(mp + "/vv/m3u8/" + dirName + "/index.m3u8"))
                        .stream()
                        .filter(v -> v.endsWith(".ts"))
                        .map(v -> new LinkedList<>(Arrays.asList(v.split("/"))).getLast())
                        .collect(Collectors.toList());
                // 删除 ts 片段
                names.forEach(v -> FileSystemUtils.deleteRecursively(new File(mp + "/vv/ts/" + v)));
                // 删除 文件夹
                FileSystemUtils.deleteRecursively(new File(mp + "/vv/m3u8/" + dirName));
                // 日志
                System.out.printf("删除: %s", dirName);
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
         // 返回
        resp.sendRedirect("/webj/");
    }
}
