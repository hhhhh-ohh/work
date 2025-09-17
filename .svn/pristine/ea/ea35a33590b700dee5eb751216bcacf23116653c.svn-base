package com.wanmi.sbc.electroniccoupon.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsCheckBindRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchPlusStockRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoPlusStockDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardBatchAddRequest;
import com.wanmi.sbc.marketing.bean.dto.ElectronicCardDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @description 卡券Excel保存服务
 * @author malianfeng
 * @date 2022/6/17 15:40
 */
@Slf4j
@Service
public class ElectronicExcelSaveService {

    @Autowired
    private ElectronicCardProvider electronicCardProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    /**
     * 批量保存数据
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public void saveData(List<ElectronicCardDTO> dtoList, String recordId) {
        dtoList.forEach(electronicCardDTO -> {
            electronicCardDTO.setRecordId(recordId);
        });
        ElectronicCardBatchAddRequest request = ElectronicCardBatchAddRequest.builder().dtoList(dtoList).build();
        electronicCardProvider.batchAdd(request);
        // 导入卡密时加绑定商品的库存，加库数量和新增数量保持一致
        if (CollectionUtils.isNotEmpty(dtoList)) {
            this.addStock(dtoList.get(0).getCouponId(), (long) dtoList.size());
        }
    }

    /**
     * 导入卡密时加绑定商品的库存
     */
    public void addStock(Long electronicCouponsId,Long stock) {
        //查询绑定sku
        GoodsInfoVO goodsInfoVO = goodsQueryProvider.findByElectronicCouponsId(GoodsCheckBindRequest.builder()
                .electronicCouponsId(electronicCouponsId)
                .build()).getContext().getGoodsInfoVO();
        //非空 就加库存
        if (Objects.nonNull(goodsInfoVO)) {
            String goodsInfoId = goodsInfoVO.getGoodsInfoId();
            goodsInfoProvider.batchPlusStock(
                    GoodsInfoBatchPlusStockRequest.builder()
                            .stockList(Lists.newArrayList(GoodsInfoPlusStockDTO.builder()
                                    .stock(stock)
                                    .goodsInfoId(goodsInfoId)
                                    .build()))
                            .build());
        }
    }
}
