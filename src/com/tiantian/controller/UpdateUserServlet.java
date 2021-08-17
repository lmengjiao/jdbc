package com.tiantian.controller;

import com.alibaba.fastjson.JSONObject;
import com.tiantian.entity.User;
import com.tiantian.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UpdateUserServlet",urlPatterns = "/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //字符编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        //接受前端的参数
        String is_del = req.getParameter("is_del");
        String modify_time = req.getParameter("modify_time");
        String password = req.getParameter("password");
        String real_name = req.getParameter("real_name");
        String type = req.getParameter("type");
        String username = req.getParameter("username");
        //缺少一个最重要的参数 就是主键id
        String id = req.getParameter("id");
//        System.out.println("id = " + id);
//        System.out.println("username = " + username);
//        System.out.println("type = " + type);
//        System.out.println("real_name = " + real_name);
//        System.out.println("password = " + password);
//        System.out.println("modify_time = " + modify_time);
//        System.out.println("is_del = " + is_del);
        //调用service层
        UserService userService = new UserService();
        Map map=userService.selectid(Integer.parseInt(id));
        User data= (User) map.get("data");
        //把参数赋值成对象
        User user = new User();
        user.setCreate_time(data.getCreate_time());
        user.setImg(data.getImg());
        user.setIs_del(Integer.parseInt(is_del));
        user.setModify_time(modify_time);
        user.setPassword(password);
        user.setReal_name(real_name);
        user.setType(Integer.parseInt(type));
        user.setUsername(username);
        user.setId(Integer.parseInt(id));
        //输出到前端
        Map map1 = userService.updateUser(user);
        String s = JSONObject.toJSONString(map1);
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();
    }
}
