package com.kvalue.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kvalue.gmall.bean.PmsBaseAttrInfo;
import com.kvalue.gmall.bean.PmsProductImage;
import com.kvalue.gmall.bean.PmsProductInfo;
import com.kvalue.gmall.bean.PmsProductSaleAttr;
import com.kvalue.gmall.manage.util.PmsUploadUtil;
import com.kvalue.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class SpuController {
    @Reference
    SpuService spuService;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id) {
        return spuService.spuList(catalog3Id);
    }
    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile multipartFile) {
        //将图片或视频上传到分布式的文件存储系统
        String imgUrl = PmsUploadUtil.uploadImg(multipartFile);
        System.out.println(imgUrl);
        //将图片的存储路径返回给页面
        return imgUrl;
    }
    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        return spuService.spuSaleAttrList(spuId);
    }
    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId) {
        return spuService.spuImageList(spuId);
    }

}
