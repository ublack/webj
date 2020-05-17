package com.webj.movie;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/ts/*")
public class TsServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String mp = System.getProperty("user.home");

        String path = req.getPathInfo();
        String fileName = StringUtils.getFilename(path);
        FileCopyUtils.copy(new FileInputStream(mp + "\\vv\\ts\\" + fileName), resp.getOutputStream());
    }
}
