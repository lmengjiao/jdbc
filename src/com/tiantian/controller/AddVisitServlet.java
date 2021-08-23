package com.tiantian.controller;

import com.alibaba.fastjson.JSONObject;
import com.tiantian.entity.Visit;
import com.tiantian.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "AddVisitServlet",urlPatterns = "/AddVisitServlet")
public class AddVisitServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.解决乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        //2.接收参数
        String user_id = req.getParameter("user_id");
        String id = req.getParameter("id");
        String visit_desc = req.getParameter("visit_desc");
        String visit_time = req.getParameter("visit_time");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String create_time = simpleDateFormat.format(new Date());
        //3.赋值到实体类
        Visit visit = new Visit();
        visit.setUser_id(Integer.parseInt(user_id));
        visit.setId(Integer.parseInt(id));
        visit.setVisit_desc(visit_desc);
        visit.setVisit_time(visit_time);
        visit.setCreate_time(create_time);
        //4.调用service
        VisitService visitService = new VisitService();
        Map map=visitService.addVisit(visit);

        PrintWriter writer = resp.getWriter();
        String s = JSONObject.toJSONString(map);
        writer.println(s);
        writer.close();
    }
}
