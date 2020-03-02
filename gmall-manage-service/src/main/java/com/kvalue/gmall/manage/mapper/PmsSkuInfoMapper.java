package com.kvalue.gmall.manage.mapper;

import com.kvalue.gmall.bean.PmsSkuInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(String productId);
}
