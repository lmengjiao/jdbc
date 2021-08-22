package com.tiantian.dao;


import com.tiantian.entity.Customer;
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

    //搜索框 查询
    public List<Map> selectByParam(Map map) {
        String page = (String) map.get("page");
        String limit = (String) map.get("limit");
        String cust_name = (String) map.get("cust_name");
        String cust_phone = (String) map.get("cust_phone");
        String cust_sex = (String) map.get("cust_sex");
        String username = (String) map.get("username");
        String modify_time = (String) map.get("modify_time");

        List lists = new ArrayList();
        //1 创建连接对象
        Connection conn = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "select c.* ,t.username as username , t.password as password ,  t.real_name as real_name , t.type as type   from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1 ";
        sql=sql + " and t.is_del=1 ";
        if (null != cust_name && cust_name.length() > 0) {
            sql = sql + " and c.cust_name like '%" + cust_name + "%' ";
        }
        if (null != cust_phone && cust_phone.length() > 0) {
            sql = sql + " and c.cust_phone = " + cust_phone + "   ";
        }
        if (null != cust_sex && cust_sex.length() > 0) {
            sql = sql + " and c.cust_sex = " + cust_sex + "   ";
        }
        if (null != username && username.length() > 0) {
            sql = sql + " and t.username like '%" + username + "%' ";
        }
        if (null != modify_time && modify_time.length() > 0) {
            sql = sql + " and c.modify_time = " + modify_time + "   ";
        }
        sql = sql + " limit  ? , ? ";
        System.out.println("da de  sql= " + sql);
        //3 获取预编译对象
        PreparedStatement ps = null;
        //4 获取结果集
        ResultSet rs = null;
        //获取索引
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));
        //3 获取预编译对象
        try {
            ps=conn.prepareStatement(sql);
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
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    //头部工具栏 增加
    public int insertCustomer(Customer customer){
        //1 开链接
        Connection conn = DBHelper.getConnection();
        //2 写sql
        String sql="insert into t_customer values(null,?,?,?,?,?,?,?,?,?,?)";
        //3 预编译
        PreparedStatement ps=null;
        //4 执行编译
        int i=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,customer.getCust_name());
            ps.setString(2,customer.getCust_company());
            ps.setString(3,customer.getCust_position());
            ps.setString(4,customer.getCust_phone());
            ps.setString(5,customer.getCust_birth());
            ps.setInt(6,customer.getCust_sex());
            ps.setString(7,customer.getCust_desc());
            ps.setInt(8,customer.getUser_id());
            ps.setString(9,customer.getCreate_time());
            ps.setString(10,customer.getModify_time());
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

    //头部工具栏 删除
    public int deleteById(Integer id){
        // 开链接
        Connection conn = DBHelper.getConnection();
        // sql
        String sql="delete from t_customer where id=?";
        // 预编译
        PreparedStatement ps=null;
        // 执行编译
        int i=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
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



    //测试
    public static void main(String[] args) {
        CustomerDao customerDao = new CustomerDao();
//       Map paraMap = new HashMap();
//        /* paraMap.put("page","1");
//        paraMap.put("limit","5");*/
//        //全查
       // List<Map> maps = customerDao.selectAllByParam(paramMap);
//        System.out.println("maps = " + maps);
//        System.out.println("maps.size() = " + maps.size());
//        //总条数
//        //int i = customerDao.selectAllParamCount(paraMap);
//        //System.out.println("i = " + i);
//        //搜索框 要放开全查
//        paraMap.put("page","1");
//        paraMap.put("limit","5");
//        paraMap.put("cust_name","郑小龙");

//        //头部工具 增加
//        Customer customer = new Customer();
//        customer.setCust_name("易烊千玺");
//        customer.setCust_company("yg");
//        customer.setCust_position("cq");
//        customer.setCust_phone("123");
//        customer.setCust_birth("2020-02-02");
//        customer.setCust_sex(1);
//        customer.setCust_desc("帅气");
//        customer.setUser_id(38);
//        customer.setCreate_time("2020-03-03");
//        customer.setModify_time("2020-03-03");
//        int i=customerDao.insertCustomer(customer);
//        System.out.println("i = " + i);
        
        //删除
//        int i=customerDao.deleteById(13);
//        System.out.println("i = " + i);

   }
}
