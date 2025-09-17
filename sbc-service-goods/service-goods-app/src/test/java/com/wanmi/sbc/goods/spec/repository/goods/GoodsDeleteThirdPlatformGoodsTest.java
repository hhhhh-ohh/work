package com.wanmi.sbc.goods.spec.repository.goods;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.api.request.goods.ThirdPlatformGoodsDelRequest;
import com.wanmi.sbc.goods.api.response.linkedmall.ThirdPlatformGoodsDelResponse;
import com.wanmi.sbc.goods.bean.dto.ThirdPlatformGoodsDelDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.provider.impl.goods.GoodsController;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hanwei
 * @className GoodsDeleteThirdPlatformGoodsTest
 * @description TODO
 * @date 2021/6/1 11:22
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GoodsDeleteThirdPlatformGoodsTest {

    @Autowired private GoodsController goodsController;

    @Test
    public void test(){
        List<ThirdPlatformGoodsDelDTO> thirdPlatformGoodsDelDTOList = Stream.of("631524504783").map(
                spuId -> ThirdPlatformGoodsDelDTO.builder()
                        .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                        .goodsSource(GoodsSource.LINKED_MALL)
                        .itemId(Long.valueOf(spuId))
                        .build())
                .collect(Collectors.toList());
        ThirdPlatformGoodsDelResponse response = goodsController.deleteThirdPlatformGoods(
                new ThirdPlatformGoodsDelRequest(thirdPlatformGoodsDelDTOList, true)).getContext();
        System.out.println(JSON.toJSONString(response));
    }
}