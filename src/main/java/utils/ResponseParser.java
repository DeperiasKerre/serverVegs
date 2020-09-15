package utils;

import com.google.gson.Gson;
import com.mysql.cj.util.StringUtils;
import models.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseParser {

    public void parse(HttpServletResponse resp, String message, int status) throws IOException {
        Response r = new Response(message);
        PrintWriter out = resp.getWriter();
        String json = new Gson().toJson(r);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(status);
        out.print(json);
        out.flush();
    }

    public int retrieveId(HttpServletRequest req) {
        String pathInfo = req.getRequestURI();
        String[] parts = pathInfo.split("/");
        if (parts.length > 0) {
            pathInfo = parts[parts.length - 1];
        }
        if (StringUtils.isStrictlyNumeric(pathInfo))
            return Integer.parseInt(pathInfo);
        return 0;
    }
}
