package com.tiantian.dao;


import com.tiantian.util.DBHelper;
import com.tiantian.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDao {
    //1.带参数的全查询（两表联查）
    //t_user 和 t_customer
    //select * from t_customer c join t_user u on c.user_id = u.id where 后面要带参数！！！
    //多表联查 返回值是什么？  Map
    //多表联查 后面要带的参数 肯定是——多个表中的属性都有？？？，那么用什么接参数   Map
    public List<Map> selectAllByParam(Map map){
        List lists = new ArrayList();
        String page = (String) map.get("page");
        String limit = (String) map.get("limit");
        //1.创建出 连接对象
        Connection connection = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "select c.* ,t.username as username , t.password as password ,  t.real_name as real_name , t.type as type   from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1 ";
        //select * from c.*,t.id as user_id ,t.useranme as username, t.password as password, t.real_name as real_name, t.type as type from  t_customer c  join t_user  t  on c.user_id  = t.id where 1=1 and c.cust_birth='1977-11-15' and t.is_del=1;
        sql = sql + " and t.is_del=1   ";
        sql = sql + " limit  ? ,  ?";
        System.out.println(" dao de limit sql = " + sql);

        //3.使用连接对象 获取 预编译对象
        PreparedStatement ps = null;
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page), Integer.parseInt(limit));//因为第一个需要?求出来
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());//这是索引
            ps.setInt(2,Integer.parseInt(limit));

            //4.执行sql
            rs = ps.executeQuery();
            while(rs.next()){
                Map dataMap=new HashMap();
                dataMap.put("id",rs.getInt("id"));
                dataMap.put("cust_name",rs.getString("cust_name"));
                dataMap.put("cust_company",rs.getString("cust_company"));
                dataMap.put("cust_position",rs.getString("cust_position"));
                dataMap.put("cust_phone",rs.getString("cust_phone"));
                dataMap.put("cust_birth",rs.getString("cust_birth"));
                dataMap.put("cust_sex",rs.getInt("cust_sex"));
                dataMap.put("user_id",rs.getInt("user_id"));
                dataMap.put("create_time",rs.getString("create_time"));
                dataMap.put("modify_time",rs.getString("modify_time"));
                dataMap.put("username",rs.getString("username"));
                dataMap.put("password",rs.getString("password"));
                dataMap.put("real_name",rs.getString("real_name"));
                dataMap.put("type",rs.getInt("type"));

                lists.add(dataMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    //2.带参数的查总条数（两表联查）
    //select count(*) from t_customer c join t_user u on c.user_id u.id where 后面要带参数！！！

    public int selectAllParamCount(Map map){
        int total = 0;
        //1.创建出 连接对象
        Connection connection = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "select count(*) total  from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1 ";

        PreparedStatement ps = null;
        ResultSet rs = null;
        //3.预编译
        try {
            ps = connection.prepareStatement(sql);
            //4.执行sql
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }
    //测试
    public static void main(String[] args) {
        CustomerDao customerDao = new CustomerDao();
       Map paraMap = new HashMap();
        /* paraMap.put("page","1");
        paraMap.put("limit","5");

        List<Map> maps = customerDao.selectAllByParam(paraMap);
        System.out.println("maps = " + maps);
        System.out.println("maps.size() = " + maps.size());*/
        int i = customerDao.selectAllParamCount(paraMap);
        System.out.println("i = " + i);


    }
}
