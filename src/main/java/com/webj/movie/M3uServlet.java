package com.webj.movie;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/m3u8/*")
public class M3uServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        String path = req.getPathInfo();
        String[] paths = StringUtils.split(path, "/");
        String fileName = paths[paths.length - 2] + "/" + paths[paths.length - 1];
        FileCopyUtils.copy(new FileInputStream("G:\\vv\\m3u8\\" + fileName), resp.getOutputStream());

    }

}
