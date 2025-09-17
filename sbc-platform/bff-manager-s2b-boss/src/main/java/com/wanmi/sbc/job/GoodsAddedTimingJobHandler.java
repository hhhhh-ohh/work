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
 * 定时任务Handler
 * 商品定时上架
 *
 * @author dyt
 */
@Component
@Slf4j
public class GoodsAddedTimingJobHandler {

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsStockService goodsStockService;

    @XxlJob(value = "goodsAddedTimingJobHandler")
    public void execute() throws Exception {
        log.info("更新spu定时上架");
        // 每小时执行 0 1 0/1 * * ?

        // 查询所有未删除并且下架的商品
        GoodsInfoPageRequest pageRequest = GoodsInfoPageRequest.builder()
                .addedTimingFlag(Boolean.TRUE)
                .delFlag(DeleteFlag.NO.toValue())
                .addedFlag(AddedFlag.NO.toValue())
                .goodsSource(GoodsSource.SELLER.toValue())
                .build();
        // 查询第一页、每页500条
        pageRequest.setPageSize(500);
        pageRequest.setPageNum(0);

        // 死循环查询符合条件的商品并上架
        MicroServicePage goodsPages;
        do {
            // 因为商品的状态在不断改变，因此永远查第一页
            goodsPages = BaseResUtils
                    .getResultFromRes(goodsInfoQueryProvider.page(pageRequest), GoodsInfoPageResponse::getGoodsInfoPage);
            if (Objects.isNull(goodsPages) || WmCollectionUtils.isEmpty(goodsPages.getContent())) {
                break;
            }
            // 商品上架
            goodsStockService.addedOrtakedown(goodsPages.getContent(), AddedFlag.YES);
        } while (goodsPages.getTotalPages() > 1);
    }
}
