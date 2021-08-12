package com.tiantian.service;

import com.tiantian.dao.UserDao;
import com.tiantian.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    //登录
    public Map login(String username, String password, HttpServletRequest request){
        Map map=new HashMap();
        //service层要调用dao层
        UserDao dao=new UserDao();
        User login = dao.login(username, password);
        if(null==login){
            //账户密码错误
            map.put("code",4001);
            map.put("msg","账户名或密码不正确");
            return map;
        }else{
            //登陆成功 将用户信息放入session
                HttpSession session = request.getSession();
                session.setAttribute("user", login);
                map.put("code", 0);
                map.put("msg", "登陆成功");
                return map;
            }
    }

    //带参数的分页查询
//    public Map selectByParam(int page,int limit){
    public Map selectByParam(Map map1){
        UserDao userDao = new UserDao();
        List<User> users = userDao.selectByParam(map1);
        int i = userDao.selectcount(map1);
        Map map=new HashMap();
        map.put("code111",200);//返回的数据不符合规范
        map.put("msg111","查询成功");
        map.put("count111",i);//把死的写成活的
        map.put("data111",users);
        //return map;
        //错误示例
        Map map2=new HashMap();
        map2.put("number",2001);
        map2.put("message","数据查询成功");
        map2.put("object",map);
        return map2;
    }
}
