package com.kvalue.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kvalue.gmall.bean.PmsBaseAttrInfo;
import com.kvalue.gmall.bean.PmsBaseAttrValue;
import com.kvalue.gmall.bean.PmsBaseSaleAttr;
import com.kvalue.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.kvalue.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.kvalue.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.kvalue.gmall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Override
    public List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> attrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
        for (PmsBaseAttrInfo baseAttrInfo :attrInfos){
            List<PmsBaseAttrValue> pmsBaseAttrValues = new ArrayList<>();
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
            pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
            baseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return attrInfos;
    }

    @Override
    public boolean saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        int insertPmsBaseAttrInfo = 0;
        int updataPmsBaseAttrInfo = 0;
        //判断id是否存在，若存在则为更新
        if (StringUtil.isEmpty(id)) {
//            insertPmsBaseAttrInfo = pmsBaseAttrInfoMapper.insertPmsBaseAttrInfo(pmsBaseAttrInfo);
            insertPmsBaseAttrInfo = pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
        }else {
            updataPmsBaseAttrInfo = pmsBaseAttrInfoMapper.updateByPrimaryKey(pmsBaseAttrInfo);
        }
        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
        if (attrValueList.size() > 0) {
            String attrId = pmsBaseAttrInfo.getId();
            for (int i = 0; i < attrValueList.size(); i++) {

                if (attrValueList.get(i).getAttrId() == null) {
                    attrValueList.get(i).setAttrId(attrId);
                    pmsBaseAttrValueMapper.insert(attrValueList.get(i));
                } else {
                    //更新
                    pmsBaseAttrValueMapper.updateByPrimaryKey(attrValueList.get(i));
                }

            }

        }

        if (insertPmsBaseAttrInfo > 0 || updataPmsBaseAttrInfo > 0) {
            return true;
        }
        return false;

    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        return pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }
}
