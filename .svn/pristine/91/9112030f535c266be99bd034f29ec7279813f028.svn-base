package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoPageRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoPageResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.service.GoodsStockService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName GoodsTakedownTimeJobHandler
 * @Description  定时下架任务Handler
 * @Author qiyuanzhao
 * @Date 2022/6/10 10:51
 **/

@Component
@Slf4j
public class GoodsTakedownTimeJobHandler {

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsStockService goodsStockService;

    @XxlJob(value = "goodsTakedownTimeJobHandler")
    public void execute() throws Exception {
        log.info("-------------------更新spu定时下架--------------------");
        // 查询所有未删除并且上架架的商品
        GoodsInfoPageRequest request = GoodsInfoPageRequest.builder()
                .takedownTimeFlag(Boolean.TRUE)
                .delFlag(DeleteFlag.NO.toValue())
                .addedFlag(AddedFlag.YES.toValue())
                .goodsSource(GoodsSource.SELLER.toValue())
                .build();
        // 查询第一页、每页500条
        request.setPageSize(500);
        request.setPageNum(0);

        // 死循环查询符合条件的商品并上架
        MicroServicePage goodsPagesList;
        do {
            // 因为商品的状态在不断改变，因此永远查第一页
            goodsPagesList = BaseResUtils
                    .getResultFromRes(goodsInfoQueryProvider.page(request), GoodsInfoPageResponse::getGoodsInfoPage);
            if (Objects.isNull(goodsPagesList) || WmCollectionUtils.isEmpty(goodsPagesList.getContent())) {
                break;
            }
            // 商品上架
            goodsStockService.addedOrtakedown(goodsPagesList.getContent(), AddedFlag.NO);
        } while (goodsPagesList.getTotalPages() > 1);
        log.info("-------------------更新spu定时下架完成--------------------");
    }
}
