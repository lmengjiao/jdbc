package com.tiantian.controller;

import com.alibaba.fastjson.JSONObject;
import com.tiantian.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "selectByNTServlet",urlPatterns = "/selectByNTServlet")
public class selectByNTServlet extends HttpServlet {
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
        //3
        String user_id = req.getParameter("user_id");
        String cust_id = req.getParameter("cust_id");
        String visit_desc = req.getParameter("visit_desc");
//        String visit_time = req.getParameter("visit_time");
//        String create_time = req.getParameter("create_time");
//        String cust_name = req.getParameter("cust_name");
        Map paramMap = new HashMap();
        paramMap.put("page",page);
        paramMap.put("limit",limit);
        paramMap.put("user_id",user_id);
        paramMap.put("cust_id",cust_id);
        paramMap.put("visit_desc",visit_desc);
//        paramMap.put("visit_time",visit_time);
//        paramMap.put("create_time",create_time);
//        paramMap.put("cust_name",cust_name);

        VisitService service = new VisitService();
        Map map = service.selectByNT(paramMap);

        PrintWriter writer = resp.getWriter();
        String s = JSONObject.toJSONString(map);
        writer.println(s);
        writer.close();
    }
}
