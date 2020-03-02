package com.kvalue.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kvalue.gmall.bean.PmsBaseAttrInfo;
import com.kvalue.gmall.bean.PmsBaseAttrValue;
import com.kvalue.gmall.bean.PmsBaseSaleAttr;
import com.kvalue.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class AttrController {
    @Reference
    AttrService attrService;
    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id){
        return attrService.getAttrInfoList(catalog3Id);
    }
    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        boolean flag = attrService.saveAttrInfo(pmsBaseAttrInfo);
        if (flag) {
            return "insert 成功";
        } else {
            return "insert 失败";
        }
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){
        return attrService.getAttrValueList(attrId);
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        return attrService.baseSaleAttrList();
    }
}
