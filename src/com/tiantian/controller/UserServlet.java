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
// http://localhost:8080/UserServlet?page=1&limit=5 查询方式
@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        //接收参数 page limit
        String page=req.getParameter("page");
        String limit=req.getParameter("limit");
        //调用service
        UserService userService = new UserService();
        Map map=userService.selectByParam(Integer.parseInt(page),Integer.parseInt(limit));
        //把map转为json
        String s= JSONObject.toJSONString(map);
        //使用流输出
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();
    }
}
