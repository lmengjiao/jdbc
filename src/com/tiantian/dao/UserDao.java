package com.tiantian.dao;

import com.tiantian.entity.User;
import com.tiantian.util.DBHelper;
import com.tiantian.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Dao层应该是一个接口 接口可以使用aop 目前不用aop 所以可以写成类
public class UserDao {
    //查询所有业务员
    public List<User> selectByType() {
        //dao层和数据库做连接 用到的知识点叫做jdbc
        //要连接数据库 就需要用到刚刚DbHelper.getConnection()来创建一个和mysql连接的对象
        //1 创建连接对象
        List<User> users=new ArrayList<>();
        Connection conn = DBHelper.getConnection();
        //2 创建sql语句
        String sql="select * from t_user where type=2";
        //3 使用链接 获取预编译对象
        PreparedStatement ps=null;
        ResultSet rs=null; //4执行预编译对象 得出结果集
        try {
            ps=conn.prepareStatement(sql); //3 使用链接 获取预编译对象
            System.out.println("ps = " + ps);
            rs=ps.executeQuery(); //4 执行预编译对象 得出结果集
            //5 遍历结果集 一一获取对象
            while(rs.next()){
                User user=new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close(); //关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    //新增
    public int addUser(User user){
        //1 创建连接对象
        Connection conn=DBHelper.getConnection();
        //2 sql语句 因为添加的数据是变量 所以用？代替
        String sql="insert into t_user values (null,?,?,?,?,?,?,?,?)";
        //3 获取预编译sql
        PreparedStatement ps=null;
        //4 执行预编译对象
        int i=0;
        try {
             ps=conn.prepareStatement(sql);
             ps.setString(1,user.getUsername());
             ps.setString(2,user.getPassword());
             ps.setString(3,user.getReal_name());
             ps.setString(4,user.getImg());
             ps.setInt(5,user.getType());
             ps.setInt(6,user.getIs_del());
             ps.setString(7,user.getCreate_time());
             ps.setString(8,user.getCreate_time());
             //4 执行预编译对象
            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close(); //关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //删除
    public int delUser(int id){
        //1 创建连接对象
        Connection conn=DBHelper.getConnection();
        //2 sql语句
        String sql="delete from t_user where id=?";
        //3 获取预编译对象
        PreparedStatement ps=null;
        //4 执行预编译对象
        int i=0;
        try {
            //3 获取预编译对象
            ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            //4 执行预编译
            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //修改
    public int upUser(User user){
        //1 获取连接
        Connection conn=DBHelper.getConnection();
        //2 sql 语句
        String sql="update t_user set username=?,password=?,real_name=?,img=?,type=?,is_del=?,create_time=?,modify_time=? where id=?";
        //3 获取预编译对象
        PreparedStatement ps=null;
        //4 执行预编译对象
        int i=0;
        try {
            //3 获取预编译对象
            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getCreate_time());
            ps.setInt(9,user.getId());
            //4 执行预编译对象
            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
    
    //登录
    public User login(String username,String password){
        User user =null;
        //创建链接
        Connection conn=DBHelper.getConnection();
        //创建sql 语句
        String sql="select * from t_user where username=? and password=?";
        //获取预编译对象
        PreparedStatement ps=null;
        //执行预编译对象
        ResultSet rs=null;
        try {
            //获取预编译对象
            ps=conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            //执行预编译对象
            rs=ps.executeQuery();
            if(rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //带参数的分页查询 动态sql  搜索框
    // page 页数  limit 条数
//    public List<User> selectByParam(int page,int limit){
    public List<User> selectByParam(Map map){
        String page= (String) map.get("page");
        String limit= (String) map.get("limit");
        String real_name= (String) map.get("real_name");
        String type= (String) map.get("type");
        String username= (String) map.get("username");

        List<User> lists=new ArrayList<>();
        //1 创建连接对象
        Connection conn = DBHelper.getConnection();
        //2 sql 语句 1=1保持恒等
        String sql="select * from t_user where 1=1";

        if(null!=real_name && real_name.length()>0){
            sql =sql +" and real_name like '%"+real_name+"%' ";
        }
        if(null!=type && type.length()>0){
            sql =sql +" and type = "+type+"   ";
        }
        if(null!=username && username.length()>0){
            sql =sql +" and username like '%"+username+"%' ";
        }
        sql=sql+" limit  ? , ?";
        System.out.println("da de  sql= " + sql);
        //3 获取预编译对象
        PreparedStatement ps=null;
        //4 获取结果集
        ResultSet rs=null;
        //获取索引
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));
        try {
            //3 获取预编译对象
            ps=conn.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());
            ps.setInt(2, Integer.parseInt(limit));
            //4 获取结果集
            rs=ps.executeQuery();
            //5 遍历结果集 一一获取对象
            while(rs.next()){
                User user=new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                lists.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close(); //关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    //查询总条数
    public int selectcount(Map map1){
        String real_name= (String) map1.get("real_name");
        String type= (String) map1.get("type");
        String username= (String) map1.get("username");
        int total=0;
        //1 连接
        Connection conn = DBHelper.getConnection();
        //2 sql 1=1保持恒等
        String sql="select count(*) total from t_user where 1=1";
        if(null!=real_name && real_name.length()>0){
            sql =sql +" and real_name like '%"+real_name+"%' ";
        }
        if(null!=type && type.length()>0){
            sql =sql +" and type = "+type+"   ";
        }
        if(null!=username && username.length()>0){
            sql =sql +" and username like '%"+username+"%' ";
        }
        System.out.println("count  sql= " + sql);

        //3 获取预编译对象
        PreparedStatement ps=null;
        //4 获取结果集
        ResultSet rs=null;
        try {
            //3 获取预编译对象
            ps=conn.prepareStatement(sql);
            //4 执行
            rs=ps.executeQuery();
            if(rs.next()){
                 total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    //修改 ajax传输后台的东西
    public int updateid(Integer is_del,Integer userId){
        //连接
        Connection conn = DBHelper.getConnection();
        //sql
        String sql="update t_user set is_del=? where id=?";
        //编译
        PreparedStatement ps=null;
        //执行
        int i=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,is_del);
            ps.setInt(2,userId);
            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //修改 ajax传输后台的东西
    public int updateUser(User user){
        // 连接
        Connection conn = DBHelper.getConnection();
        //sql
        String sql="update t_user set create_time?,img=?,is_del=?,modify_time=?,password=?,real_name=?,type=?,username=? where id=?";
        //预编译
        PreparedStatement ps=null;
        //执行
        int i=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getCreate_time());
            ps.setString(2,user.getImg());
            ps.setInt(3,user.getIs_del());
            ps.setString(4,user.getModify_time());
            ps.setString(5,user.getPassword());
            ps.setString(6,user.getUsername());
            ps.setInt(7,user.getType());
            ps.setString(8,user.getUsername());
            ps.setInt(9,user.getId());
            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //根据id查询
    public User selectid(Integer id){
        User user =null;
        //创建链接
        Connection conn=DBHelper.getConnection();
        //创建sql 语句
        String sql="select * from t_user where id=?";
        //获取预编译对象
        PreparedStatement ps=null;
        //执行预编译对象
        ResultSet rs=null;
        try {
            //获取预编译对象
            ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            //执行预编译对象
            rs=ps.executeQuery();
            if(rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
   //测试是否拿到
    public static void main(String[] args) {
        UserDao dao=new UserDao();
//        //全查
//        List<User> users=dao.selectAll();
//        for (User user : users) {
//            System.out.println("user = " + user);
//        }
        
//        //新增
////        User user=new User();
////        user.setUsername("老余");
////        user.setType(1);
////        user.setReal_name("余庆伟");
////        user.setPassword("123");
////        user.setModify_time("2020-03-02");
////        user.setIs_del(1);
////        user.setImg("xxx");
////        user.setCreate_time("2020-03-02");
////        int i=dao.addUser(user);
////        System.out.println("i = " + i);
        
//        //删除
//        User user=new User();
//        user.setId(33);
//        int i=dao.delUser(33);
//        System.out.println("i = " + i);
        
//        //修改
//        User user=new User();
//        user.setUsername("王甜甜");
//        user.setType(1);
//        user.setReal_name("王一博");
//        user.setPassword("521");
//        user.setModify_time("2121-02-02");
//        user.setIs_del(1);
//        user.setImg("aaa");
//        user.setCreate_time("2020-2-03-02");
//        user.setId(42);
//        int i=dao.upUser(user);
//        System.out.println("i = " + i);
        
        //登录
//        User abc = dao.login("abc", "123456");
//        System.out.println("abc = " + abc);
        
        //分页查询的测试
//        List<User> users = dao.selectByParam(2,5);
//        System.out.println("users = " + users);
//        System.out.println("users.size() = " + users.size());

        //查总条数的测试
//        int i=dao.selectcount();
//        System.out.println("i = " + i);
        
        //测试修改
        int i=dao.updateid(2,7);
        System.out.println("i = " + i);
    }
}
