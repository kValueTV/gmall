package com.kvalue.gmall.service;

import com.kvalue.gmall.bean.PmsBaseAttrInfo;
import com.kvalue.gmall.bean.PmsBaseAttrValue;
import com.kvalue.gmall.bean.PmsBaseSaleAttr;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id);

    boolean saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}
