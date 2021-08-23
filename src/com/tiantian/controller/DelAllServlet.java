package com.tiantian.controller;

import com.alibaba.fastjson.JSONObject;
import com.tiantian.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "DelAllServlet",urlPatterns = "/DelAllServlet")
public class DelAllServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.解决乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        //2.接收参数 重点接收数组
        String[] parameterValues = req.getParameterValues("ids[]");
        CustomerService customerService = new CustomerService();
        System.out.println("parameterValues = " + parameterValues);
        for (String parameterValue : parameterValues) {
            int i = customerService.deleteById(Integer.parseInt(parameterValue));
            System.out.println("i = " + i);
        }
        Map codeMap=new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","ok");
        PrintWriter writer = resp.getWriter();
        String s = JSONObject.toJSONString(codeMap);
        writer.println(s);
        writer.close();
    }
}
