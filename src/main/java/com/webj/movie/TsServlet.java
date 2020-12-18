package com.webj.movie;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebServlet("/ts/*")
public class TsServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String mp = System.getProperty("user.home");

        String path = req.getPathInfo();
        String fileName = StringUtils.getFilename(path);
        while (true) {
            if (new File(mp + "\\vv\\ts\\" + fileName).exists()) {
                break;
            } else {
                try {
                    System.out.printf("未到位: %s%n", fileName);
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ignored) {

                }
            }
        }
        FileCopyUtils.copy(new FileInputStream(mp + "\\vv\\ts\\" + fileName), resp.getOutputStream());
    }
}
