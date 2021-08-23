package com.tiantian.controller;

import com.alibaba.fastjson.JSONObject;
import com.tiantian.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "SelectByTypeServlet",urlPatterns = "/SelectByTypeServlet")
public class SelectByTypeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.解决乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        //2.调用service
        UserService userService = new UserService();
        Map map = userService.selectByType();
        //输出map
        PrintWriter writer = resp.getWriter();
        String s = JSONObject.toJSONString(map);
        writer.println(s);
        writer.close();


    }
}
