package com.kvalue.gmall.manage.mapper;

import com.kvalue.gmall.bean.PmsBaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

public interface PmsBaseAttrInfoMapper extends Mapper<PmsBaseAttrInfo> {
    public int insertPmsBaseAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
}
