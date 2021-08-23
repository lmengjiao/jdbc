package com.tiantian.dao;

import com.tiantian.entity.Visit;
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

public class VisitDao {
    //新增
    public int addVisit(Visit visit){
        //1 创建连接对象
        Connection conn= DBHelper.getConnection();
        //2 sql语句 因为添加的数据是变量 所以用？代替
        String sql="insert into t_visit values (null,?,?,?,?,?)";
        //3 获取预编译sql
        PreparedStatement ps=null;
        //4 执行预编译对象
        int i=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,visit.getUser_id());
            ps.setInt(2,visit.getCust_id());
            ps.setString(3,visit.getVisit_desc());
            ps.setString(4,visit.getVisit_time());
            ps.setString(5,visit.getCreate_time());
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

    //查询 拜访时间 顾客姓名
    public List<Map> selectByNT(Map map) {
        List lists = new ArrayList();
        String page = (String) map.get("page");
        String limit = (String) map.get("limit");
        String user_id = (String) map.get("user_id");
        String cust_id = (String) map.get("cust_id");
        String visit_desc = (String) map.get("visit_desc");
        //1.创建出 连接对象
        Connection conn = DBHelper.getConnection();
        //2.sql
        System.out.println("------------");
        String sql = "select v.*,c.cust_name as cust_name,u.username as username from t_visit v join t_customer c on c.id=v.cust_id join t_user u on u.id=v.user_id where 1=1 ";

        if(null!=user_id && user_id.length()>0){
            sql = sql + " and v.user_id   =  "+user_id+"   ";

        }
        if(null!=cust_id && cust_id.length()>0){
            sql =sql +" and v.cust_id = "+cust_id+"   ";
        }
        if(null!=visit_desc && visit_desc.length()>0){
            sql =sql +" and v.visit_desc like '%"+visit_desc+"%' ";
        }
        sql = sql + "order by v.visit_time,c.cust_name";
        sql = sql + " limit  ? ,  ?";
        System.out.println(" dao de limit sql = " + sql);
        //3.预编译
        PreparedStatement ps = null;
        //4.执行编译
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page), Integer.parseInt(limit));
        try {
            //3
            ps=conn.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());//这是索引
            ps.setInt(2,Integer.parseInt(limit));
            //4
            rs=ps.executeQuery();
            while(rs.next()){
                Map dataMap=new HashMap();
                dataMap.put("id",rs.getInt("id"));
                dataMap.put("user_id",rs.getInt("user_id"));
                dataMap.put("cust_id",rs.getInt("cust_id"));
                dataMap.put("visit_desc",rs.getString("visit_desc"));
                dataMap.put("visit_time",rs.getString("visit_time"));
                dataMap.put("create_time",rs.getString("create_time"));
                dataMap.put("cust_name",rs.getString("cust_name"));
                dataMap.put("username",rs.getString("username"));

                lists.add(dataMap);
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
        return lists;
    }

    //2.带参数的查总条数
    public int selectAllByParamVisitCount(Map map){
        int total=0;
        //1.加载连接
        Connection connection = DBHelper.getConnection();
        //2.书写sql语句
        String  sql = "select count(*) total from t_visit v join t_customer c on c.id=v.cust_id join t_user u on u.id=v.user_id where 1=1";
        //3.预编译
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            //4.执行
            rs=ps.executeQuery();
            if(rs.next()){
                total=rs.getInt("total");
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

    //查询 搜索框
//    public List<Map> selectByUCV(Map map){
//        String page= (String) map.get("page");
//        String limit= (String) map.get("limit");
//        String user_id = (String) map.get("user_id");
//        String cust_id = (String) map.get("cust_id");
//        String visit_desc = (String) map.get("visit_desc");
//        List<Map> lists=new ArrayList<>();
//        //1 创建连接对象
//        Connection conn = DBHelper.getConnection();
//        //2 sql 语句 1=1保持恒等
//        String sql="select * from t_visit where 1=1";
//        if(null!=user_id && user_id.length()>0){
//            sql =sql +" and user_id = "+user_id+" ";
//        }
//        if(null!=cust_id && cust_id.length()>0){
//            sql =sql +" and cust_id = "+cust_id+"   ";
//        }
//        if(null!=visit_desc && visit_desc.length()>0){
//            sql =sql +" and visit_desc like '%"+visit_desc+"%' ";
//        }
//        sql=sql+" limit  ? , ?";
//        System.out.println("da de  sql= " + sql);
//        //3 获取预编译对象
//        PreparedStatement ps=null;
//        //4 获取结果集
//        ResultSet rs=null;
//        //获取索引
//        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));
//        try {
//            //3 获取预编译对象
//            ps=conn.prepareStatement(sql);
//            ps.setInt(1,pageBeanUtil.getStart());
//            ps.setInt(2, Integer.parseInt(limit));
//            //4 获取结果集
//            rs=ps.executeQuery();
//            //5 遍历结果集 一一获取对象
//            while(rs.next()){
//                Map dataMap=new HashMap();
//                dataMap.put("id",rs.getInt("id"));
//                dataMap.put("user_id",rs.getInt("user_id"));
//                dataMap.put("cust_id",rs.getInt("cust_id"));
//                dataMap.put("visit_desc",rs.getString("visit_desc"));
//                dataMap.put("visit_time",rs.getString("visit_time"));
//                dataMap.put("create_time",rs.getString("create_time"));
//                lists.add(dataMap);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return lists;
//    }
    
    //测试
    public static void main(String[] args) {
        //查询 拜访时间 顾客姓名
        VisitDao dao = new VisitDao();
       Map map=new HashMap();
//        map.put("page","1");
//        map.put("limit","5");
//        List<Map> maps = dao.selectByUCV(map);
////        List<Map> maps = dao.selectByNT(map);
//       System.out.println("maps = " + maps);
        int i=dao.selectAllByParamVisitCount(map);
        System.out.println("i = " + i);
    }
}
