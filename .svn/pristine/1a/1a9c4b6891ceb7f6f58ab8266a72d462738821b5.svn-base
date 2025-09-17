package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoPageRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsStockService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务Handler
 * 供应商商品定时下架
 *
 * @author xufeng
 */

@Component
@Slf4j
public class ProviderGoodsTakedownTimingJobHandler {

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsStockService goodsStockService;

    @XxlJob(value = "providerGoodsTakedownTimingJobHandler")
    public void execute() throws Exception {
        log.info("更新spu定时下架");
        // 每小时执行 0 1 0/1 * * ?
        GoodsInfoPageRequest pageRequest = GoodsInfoPageRequest.builder()
                .takedownTimeFlag(Boolean.TRUE)
                .delFlag(DeleteFlag.NO.toValue())
                .addedFlag(AddedFlag.YES.toValue())
                .goodsSource(GoodsSource.PROVIDER.toValue()).build();
        pageRequest.setPageSize(500);
        //第1页商品
        pageRequest.setPageNum(0);
        MicroServicePage<GoodsInfoVO> goodsPages = goodsInfoQueryProvider.page(pageRequest).getContext().getGoodsInfoPage();
        if (CollectionUtils.isEmpty(goodsPages.getContent())) {
            return;
        }
        goodsStockService.providerGoodsAdded(goodsPages.getContent(), AddedFlag.NO);
        Integer pageCount = goodsPages.getTotalPages();
        if (pageCount <= 1) {
            return;
        }
        //第2页开始
        for (int i = 1; i < pageCount; i++) {
            MicroServicePage<GoodsInfoVO> tmpGoodsPages = goodsInfoQueryProvider.page(pageRequest).getContext().getGoodsInfoPage();
            if (CollectionUtils.isNotEmpty(tmpGoodsPages.getContent())) {
                goodsStockService.providerGoodsAdded(tmpGoodsPages.getContent(), AddedFlag.NO);
            }
        }
    }

}
