package com.kvalue.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.kvalue.gmall.bean.PmsSkuAttrValue;
import com.kvalue.gmall.bean.PmsSkuImage;
import com.kvalue.gmall.bean.PmsSkuInfo;
import com.kvalue.gmall.bean.PmsSkuSaleAttrValue;
import com.kvalue.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.kvalue.gmall.manage.mapper.PmsSkuImageMapper;
import com.kvalue.gmall.manage.mapper.PmsSkuInfoMapper;
import com.kvalue.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.kvalue.gmall.service.SkuService;
import com.kvalue.gmall.manage.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        // 插入skuInfo
        int i = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }
        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }


    }

    public PmsSkuInfo getSkuByIdFromDb(String skuId){
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> list = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(list);
        return skuInfo;
    }
    @Override
    public PmsSkuInfo getSkuById(String skuId,String ip) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        //连接缓存
        Jedis jedis = redisUtil.getJedis();
        //查询缓存
        String skuKey = "sku:"+skuId+":info";
        String skuJson = jedis.get(skuKey);
        if (StringUtils.isNotBlank(skuJson)){
            pmsSkuInfo = JSON.parseObject(skuJson,PmsSkuInfo.class);
        }else {
            String token = UUID.randomUUID().toString();
            //设置分布式锁
            String OK = jedis.set("sku:"+skuId+":lock",token,"nx","px",10);
            if (StringUtils.isNotBlank(OK) && "OK".equals(OK)){

                //若缓存没有，查询数据库
                pmsSkuInfo = getSkuByIdFromDb(skuId);
                if (pmsSkuInfo != null) {
                    jedis.set("sku:"+skuId+":info",JSON.toJSONString(pmsSkuInfo));
                }else {
                    //数据路中该sku不存在
                    //为了防止缓存穿透，将null或空字符串赋予redis
                    jedis.setex("sku:"+skuId+":info",60*3,JSON.toJSONString(""));
                }
                String lockToken = jedis.get("sku:"+skuId+":lock");
                if (StringUtils.isNotBlank(lockToken) && lockToken.equals(token)){
//                    jedis.eval("lua");可以用lua脚本，在查询到key的同时删除该key,防止高并发下的意外发生
                    //访问完MySQL之后，释放分布式锁
                    jedis.del("sku:"+skuId+":lock");//用token确认是自己的锁
                }
            }else {
                //设置失败、自旋（该线程睡眠几秒后，重新访问该方法）
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuById(skuId,ip);
            }
        }
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {

        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);

        return pmsSkuInfos;
    }
}
