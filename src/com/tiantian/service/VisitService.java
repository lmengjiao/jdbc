package com.tiantian.service;

import com.tiantian.dao.VisitDao;
import com.tiantian.entity.Visit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitService {
    //新增
    public Map addVisit(Visit visit){
        VisitDao visitDao = new VisitDao();
        int i = visitDao.addVisit(visit);
        Map codeMap=new HashMap();
        if(i==1){
            codeMap.put("code",0);
            codeMap.put("msg","ok");
            return codeMap;
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","no");
            return codeMap;
        }
    }

    //查询 拜访时间 顾客姓名
    public Map selectByNT(Map map){
        VisitDao dao = new VisitDao();
        List<Map> maps = dao.selectByNT(map);
        HashMap codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","ok");
        codeMap.put("data",maps);
        Map countMap =selectAllByParamVisitCount(map);
        int count= (int) countMap.get("data");
        codeMap.put("count",count);
        return codeMap;
    }

    //全查总条数
    public Map selectAllByParamVisitCount(Map map){
        Map codeMap=new HashMap();
        VisitDao dao=new VisitDao();
        int i=dao.selectAllByParamVisitCount(codeMap);
        codeMap.put("code",0);
        codeMap.put("data",i);
        codeMap.put("msg","总条数");
        return codeMap;
    }
    //查询 搜索框
//    public Map selectByUCV(Map map1){
//        VisitDao dao = new VisitDao();
//        List<Map> v = dao.selectByUCV(map1);
//        Map map=new HashMap();
//        map.put("code111",200);//返回的数据不符合规范
//        map.put("msg111","查询成功");
//        map.put("data111",v);
//        return map1;
//        //return map;
//        //错误示例
////        Map map2=new HashMap();
////        map2.put("number",2001);
////        map2.put("message","数据查询成功");
////        map2.put("object",map);
////        return map2;
//    }
}
