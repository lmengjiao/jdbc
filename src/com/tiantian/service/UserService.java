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
                //给账户一个前端渲染
                map.put("username",username);
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

    //修改 ajax发送后台
    public Map updateid(Integer is_del,Integer userId){
        UserDao dao = new UserDao();
        int i = dao.updateid(is_del, userId);
        Map map=new HashMap();
        if(i==1){
            map.put("code",0);
            map.put("msg","修改成功");
        }else {
            map.put("code",400);
            map.put("msg","修改失败");
        }
        return map;
    }

    //修改全部发送后台
    public Map updateUser(User user){
        Map codeMap=new HashMap();
        UserDao dao = new UserDao();
        int i=dao.updateUser(user);
        if(i==1){
            codeMap.put("code",0);
            codeMap.put("msg","请求成功");
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","请求失败");
        }
        return codeMap;
    }

    //按id查询
    public Map selectid(Integer id){
        UserDao dao = new UserDao();
        User user = dao.selectid(id);
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","ok");
        map.put("data",user);
        return map;
    }

    //新增 发送后台
    public Map addUser(User user){
        UserDao dao = new UserDao();
        int i = dao.addUser(user);
        Map map=new HashMap();
        if(i==1){
            map.put("code",0);
            map.put("msg","修改成功");
        }else {
            map.put("code",400);
            map.put("msg","修改失败");
        }
        return map;
    }

    //查询业务员
    public Map selectByType(){
        UserDao dao = new UserDao();
        List<User> users = dao.selectByType();
        Map codeMap=new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","ok");
        codeMap.put("data",users);
        return codeMap;
    }
}
