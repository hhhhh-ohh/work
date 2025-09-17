package com.wanmi.sbc.vas.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySpuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncVasRequest;
import com.wanmi.sbc.vas.provider.impl.channel.goods.ChannelSyncGoodsController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author EDZ
 * @className VopSyncGoodsTempServiceTest
 * @description TODO
 * @date 2021/5/19 17:04
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ChannelSyncGoodsControllerTest {

    @Autowired
    private ChannelSyncGoodsController channelSyncGoodsController;

    @Test
    public void test(){
        channelSyncGoodsController.syncGoodsNotice(ChannelGoodsSyncVasRequest.builder()
                .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                .build());
    }

    @Test
    public void syncSpuList(){
        ChannelGoodsSyncBySpuVasRequest channelGoodsSyncBySpuVasRequest = ChannelGoodsSyncBySpuVasRequest.builder()
                .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                        .spuIds(Arrays.asList(635081210986L))
                .storeId(1L)
                .storeName("StoreName")
                .companyInfoId(1L)
                .companyName("companyName")
                .storeCateIds(Collections.emptyList())
                .build();
        channelGoodsSyncBySpuVasRequest.setPageNum(0);
        channelGoodsSyncBySpuVasRequest.setPageSize(20);
        BaseResponse<List<String>> response = channelSyncGoodsController.syncSpuList(
                channelGoodsSyncBySpuVasRequest);
        System.out.println(JSON.toJSONString(response));
    }
}
