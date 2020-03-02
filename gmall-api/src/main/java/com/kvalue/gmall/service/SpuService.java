package com.kvalue.gmall.service;

import com.kvalue.gmall.bean.PmsProductImage;
import com.kvalue.gmall.bean.PmsProductInfo;
import com.kvalue.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId , String skuId);
}
