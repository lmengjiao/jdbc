package com.tiantian.service;

import com.tiantian.dao.CustomerDao;

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
}
