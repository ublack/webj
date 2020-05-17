package com.webj.movie;

import org.springframework.util.FileCopyUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/ts/key.key")
public class KeyServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String mp = System.getProperty("user.home");

        FileCopyUtils.copy(new FileInputStream(mp + "\\vv\\m3u8\\common\\key.key"), resp.getOutputStream());

    }

}
