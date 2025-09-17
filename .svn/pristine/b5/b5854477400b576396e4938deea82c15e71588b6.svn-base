package com.wanmi.sbc.goods.spec.repository.goods;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author EDZ
 * @className GoodsInfoSpecDetailRelRepositoryTest
 * @description TODO
 * @date 2021/5/20 14:57
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GoodsInfoSpecDetailRelRepositoryTest {

    @Resource
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Test
    public void testSave(){
        String json = "{\"createTime\":\"2021-05-20T14:49:27.652\",\"delFlag\":\"NO\",\"detailName\":\"NF-2030(30m)\",\"goodsId\":\"2c939a82798887260179888943650000\",\"goodsInfoId\":\"2c939a82798887260179888943ae0001\",\"specDetailId\":6806,\"specId\":3052,\"specName\":\"颜色\",\"updateTime\":\"2021-05-20T14:49:27.652\"}";
        GoodsInfoSpecDetailRel detailRel = JSON.parseObject(json,GoodsInfoSpecDetailRel.class);
        goodsInfoSpecDetailRelRepository.save(detailRel);
    }

    @Test
    public void testSaveAll(){
        String json =
                "{\"createTime\":\"2021-05-20T16:16:15.844\",\"delFlag\":\"NO\",\"detailName\":\"NF-2030(30m)\",\"goodsId\":\"2c939a827988d667017988d8bbf40000\",\"goodsInfoId\":\"2c939a827988d667017988d8bc860001\",\"specDetailId\":6878,\"specId\":3061,\"specName\":\"颜色\",\"updateTime\":\"2021-05-20T16:16:15.844\"}";

        GoodsInfoSpecDetailRel detailRel = JSON.parseObject(json,GoodsInfoSpecDetailRel.class);
        goodsInfoSpecDetailRelRepository.saveAll(Lists.newArrayList(detailRel));
    }
}
