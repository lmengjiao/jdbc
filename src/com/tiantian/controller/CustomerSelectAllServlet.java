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

@WebServlet(name = "CustomerSelectAllServlet",urlPatterns = "/CustomerSelectAllServlet")
public class CustomerSelectAllServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.解决乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        //2.接收参数
        String page = req.getParameter("page");
        System.out.println("page = " + page);
        String limit = req.getParameter("limit");
        System.out.println("limit = " + limit);
        //搜索框
        String cust_name = req.getParameter("cust_name");
        System.out.println("cust_name = " + cust_name);
        String cust_phone = req.getParameter("cust_phone");
        System.out.println("cust_phone = " + cust_phone);
        String cust_sex = req.getParameter("cust_sex");
        System.out.println("cust_sex = " + cust_sex);
        String username = req.getParameter("username");
        System.out.println("username = " + username);
        String modify_time = req.getParameter("modify_time");
        System.out.println("modify_name = " + modify_time);
        Map paramMap = new HashMap();
        paramMap.put("page",page);
        paramMap.put("limit",limit);
        //搜索框
        paramMap.put("cust_name",cust_name);
        paramMap.put("cust_phone",cust_phone);
        paramMap.put("cust_sex",cust_sex);
        paramMap.put("username",username);
        paramMap.put("modify_time",modify_time);

        CustomerService service = new CustomerService();
        Map map = service.selectAllByParam(paramMap);

        PrintWriter writer = resp.getWriter();
        String s = JSONObject.toJSONString(map);
        writer.println(s);
        writer.close();
    }
}
