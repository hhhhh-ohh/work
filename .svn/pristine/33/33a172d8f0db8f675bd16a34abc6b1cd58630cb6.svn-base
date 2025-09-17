package com.wanmi.sbc.goods.spec.repository.goods;

import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author EDZ
 * @className GoodsInfoRepositoryTest
 * @description TODO
 * @date 2021/5/21 10:33
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GoodsInfoRepositoryTest {

    @Resource
    private GoodsInfoRepository goodsInfoRepository;

    @Test
    public void countAddedFlagCountByGoodsIdTest(){
        Object obj = goodsInfoRepository.countAddedFlagCountByGoodsId("2c9384ab79893bbe0179893fc2640238");
        Object[] results = (Object[]) obj;

        System.out.println(String.valueOf(results[0]).compareTo(String.valueOf(results[1])));
        System.out.println(String.valueOf(results[0]).compareTo(String.valueOf(results[2])));
    }
}
