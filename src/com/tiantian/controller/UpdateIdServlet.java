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

@WebServlet(name = "UpdateIdServlet",urlPatterns ="/UpdateIdServlet" )
public class UpdateIdServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //字符编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        //接收参数
        String is_del=req.getParameter("is_del");
        String userId = req.getParameter("userId");
        //打印时找bug的基础
        System.out.println("this = " + this);
        System.out.println("userId = " + userId);
        //调用service层
        UserService userService = new UserService();
        Map map = userService.updateid(Integer.parseInt(is_del), Integer.parseInt(userId));
        String s = JSONObject.toJSONString(map);
        //输出到前端
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();
    }
}
