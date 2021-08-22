package com.tiantian.service;

import com.tiantian.dao.CustomerDao;
import com.tiantian.entity.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    //全查
    public Map selectAllByParam(Map map){
        CustomerDao dao = new CustomerDao();
        List<Map> maps = dao.selectAllByParam(map);
        HashMap codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","ok");
        codeMap.put("data",maps);
        Map countMap = selectAllByParamCount(map);
        int count = (int) countMap.get("data");
        codeMap.put("count",count);
        return codeMap;
    }

    //全查总条数  多表
    public Map selectAllByParamCount(Map map){
        Map codeMap = new HashMap();
        CustomerDao dao = new CustomerDao();
        int i = dao.selectAllParamCount(map);
        codeMap.put("code",0);
        codeMap.put("msg","ok");
        codeMap.put("data",i);
        return codeMap;
    }

    //搜索框
    public Map selectByParam(Map map1){
       CustomerDao dao = new CustomerDao();
        List<Map> customers = dao.selectByParam(map1);
        int i = dao.selectAllParamCount(map1);
        Map map=new HashMap();
        map.put("code111",200);//返回的数据不符合规范
        map.put("msg111","查询成功");
        map.put("count111",i);//把死的写成活的
        map.put("data111",customers);
        //return map;
        //错误示例
        Map map2=new HashMap();
        map2.put("number",2001);
        map2.put("message","数据查询成功");
        map2.put("object",map);
        return map2;
    }

    //增加
    public Map insertCustomer(Customer customer){
        CustomerDao dao = new CustomerDao();
        int i=dao.insertCustomer(customer);
        Map codeMap=new HashMap();
        if(i==1){
            codeMap.put("code",0);
            codeMap.put("msg","yes");
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","no");
        }
        return codeMap;
    }

    //删除
    public int deleteById(Integer id){
        CustomerDao customerDao = new CustomerDao();
        int i=customerDao.deleteById(id);
        return i;
    }

}
