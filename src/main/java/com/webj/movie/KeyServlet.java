package com.webj.movie;

import org.springframework.util.FileCopyUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

@WebServlet("*.key")
public class KeyServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String key = FileCopyUtils.copyToString(new FileReader("C:\\Users\\Administrator\\IdeaProjects\\webj\\src\\main\\resources\\key.txt"));
        FileCopyUtils.copy(key, new OutputStreamWriter(resp.getOutputStream()));
    }
}
