package com.wanmi.sbc.vas.service;

import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.wanmi.sbc.vas.channel.base.ChannelServiceFactory;
import com.wanmi.sbc.vas.channel.goods.service.ChannelSyncGoodsService;
import jakarta.annotation.Resource;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author EDZ
 * @className VopSyncGoodsTempServiceTest
 * @description TODO
 * @date 2021/5/19 17:04
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class VopSyncGoodsTempServiceTest {

    @Resource
    private ChannelServiceFactory factory;

    @Test
    public void initSyncSpuTest(){
        ChannelSyncGoodsService service = factory.getChannelService(ChannelSyncGoodsService.class, ThirdPlatformType.VOP);
        service.initSyncSpu();
    }

    @Test
    public void syncSkuListTest(){
        ChannelSyncGoodsService service = factory.getChannelService(ChannelSyncGoodsService.class, ThirdPlatformType.VOP);
        service.syncSkuList(ChannelGoodsSyncBySkuVasRequest.builder()
                .skuIds(Lists.newArrayList(100018725108L))
                .companyInfoId(1168L)
                .companyName("京东vop")
                .storeId(123458024L)
                .thirdPlatformType(ThirdPlatformType.VOP).build());
    }

    @Test
    public void initSyncSpuNewTest(){
        ChannelSyncGoodsService service = factory.getChannelService(ChannelSyncGoodsService.class, ThirdPlatformType.VOP);
        service.initSyncSpuNew();
    }
}
