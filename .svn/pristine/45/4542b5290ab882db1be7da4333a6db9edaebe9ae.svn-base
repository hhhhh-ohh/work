package com.wanmi.sbc.job.electronic;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByTypeRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchMinusStockRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardInvalidRequest;
import com.xxl.job.core.handler.annotation.XxlJob;

import io.seata.spring.annotation.GlobalTransactional;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className ElectronicCardInvalidSyncJobHandler
 * @description 批量修改失效卡券
 * @date 2022/2/9 2:41 下午
 **/
@Slf4j
@Component
public class ElectronicCardInvalidSyncJobHandler {

    @Autowired
    private ElectronicCardQueryProvider electronicCardQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private ElectronicCardProvider electronicCardProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    private static final int SIZE = 1000;

    @GlobalTransactional
    @XxlJob(value="electronicCardInvalidSyncJobHandler")
    public void execute() throws Exception {
        LocalDateTime time = LocalDate.now().atTime(LocalDateTime.now().getHour(), 0, 0);
        GoodsByTypeRequest request = GoodsByTypeRequest.builder()
                .goodsType(Constants.TWO)
                .build();
        request.setPageSize(SIZE);
        batchMinusStock(request,NumberUtils.INTEGER_ZERO,time);
        electronicCardProvider.updateCardInvalid(ElectronicCardInvalidRequest.builder().time(time).build());
    }

    /**
     * 分页查询卡券商品 减库存
     * @param request
     * @param pageNum
     * @param time
     */
    public void batchMinusStock(GoodsByTypeRequest request,int pageNum,LocalDateTime time) {
        request.setPageNum(pageNum);
        List<GoodsInfoVO> goodsInfoVOList = goodsQueryProvider.findByGoodsType(request).getContext().getGoodsInfoVOList();
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            List<GoodsInfoMinusStockDTO> stockList = Lists.newArrayList();
            goodsInfoVOList.forEach(goodsInfoVO -> {
                Long count = electronicCardQueryProvider.countCardInvalidByCouponId(ElectronicCardInvalidRequest.builder()
                        .time(time)
                        .couponId(goodsInfoVO.getElectronicCouponsId())
                        .build()).getContext().getCount();
                if(count > goodsInfoVO.getStock()){
                    count = goodsInfoVO.getStock();
                }
                stockList.add(GoodsInfoMinusStockDTO.builder()
                        .stock(count)
                        .goodsInfoId(goodsInfoVO.getGoodsInfoId())
                        .build());
            });
            goodsInfoProvider.batchMinusStock(
                    GoodsInfoBatchMinusStockRequest.builder()
                            .stockList(stockList)
                            .build());

            //同步es
            List<String> goodsIds = goodsInfoVOList.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());
            esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().
                    deleteIds(goodsIds).build());
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsIds(goodsIds).build());

            pageNum = pageNum + 1;
            batchMinusStock(request,pageNum,time);
        }
    }
}
