package com.tiantian.filter;

import com.tiantian.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//过滤器 /*表示过滤所有的请求和响应信息
@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {
    public void destroy() {
        System.out.println("过滤器死亡了");
    }
    /*
    req 请求
    resp 响应
    chain 过滤链 相当于保安 有权利让进或不让进
     */

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //根据session来判断，有则进入，没有 退出到登录页
        //服务器有session了 就不是null ,没登录 就是 null
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("user  session = " + user);
        String requestURI = request.getRequestURI();
        // http://localhost:8080/login.html        login
        System.out.println("requestURI = " + requestURI);
        if (requestURI.equals("/login.html") || requestURI.equals("/reg.html")
                || requestURI.equals("/CodeServlet")
                || requestURI.equals("/LoginServlet")
                || requestURI.equals("/js/layui-v2.5.6/layui/css/layui.css")
                || requestURI.equals("/js/layui-v2.5.6/layui/layui.js")
                || requestURI.equals("/favicon.ico")
                || requestURI.equals("/js/layui-v2.5.6/layui/lay/modules/form.js")
                || requestURI.equals("/js/layui-v2.5.6/layui/lay/modules/layer.js")
                || requestURI.equals("/js/layui-v2.5.6/layui/css/modules/layer/default/layer.css")
                || requestURI.equals("/js/layui-v2.5.6/layui/lay/modules/jquery.js")

        ) {
            chain.doFilter(req, resp);
        } else {
            if (user != null) {
                chain.doFilter(req, resp); // 放行的意思, 否则 不让进    问题: 登录页, 验证码, 注册页面, 是不需要判断session的,以及
                // .js .css .png 等等等, 都不需要 过滤
            } else {
                // 跳转到 登录页面
                response.sendRedirect("/login.html");
            }
        }
    }


    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器出生了，项目一运行，过滤器就出生");
    }

}
