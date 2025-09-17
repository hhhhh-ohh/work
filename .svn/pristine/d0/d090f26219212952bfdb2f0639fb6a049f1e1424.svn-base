package com.wanmi.sbc.order.optimization.trade1.snapshot.immediatebuy.service;

import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedValidateVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyCheckInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl.TradeBuyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author edz
 * @className ImmediateBuyService
 * @description TODO
 * @date 2022/3/31 14:24
 */
@Service("immediateBuy")
public class ImmediateBuyService extends TradeBuyService {

    @Autowired
    @Qualifier("tradeBuyCheckService")
    private TradeBuyCheckInterface tradeBuyCheckInterface;

    @Override
    public void check(ParamsDataVO paramsDataVO) {
        TradeBuyRequest request = paramsDataVO.getRequest();
        CustomerVO customerVO = paramsDataVO.getCustomerVO();
        List<GoodsInfoVO> goodsInfoVOS = paramsDataVO.getGoodsInfoResponse().getGoodsInfos();
        // 取第1个商品的类型
        Integer goodsType = goodsInfoVOS.stream().findFirst().map(GoodsInfoVO::getGoodsType).orElse(null);
        // 商品限购检验
        tradeBuyCheckInterface.validateOrderRestricted(
                GoodsRestrictedBatchValidateRequest.builder()
                        .goodsRestrictedValidateVOS(
                                KsBeanUtil.convert(
                                        request.getTradeItemRequests(),
                                        GoodsRestrictedValidateVO.class))
                        .customerVO(customerVO)
                        .openGroupon(Boolean.FALSE)
//                        .storeId(paramsDataVO.getStoreVO().getStoreId())
                        .addressId(request.getAddressId())
                        .goodsType(goodsType)
                        .build());

        // 预约活动校验是否有资格
        IteratorUtils.zip(
                goodsInfoVOS,
                request.getTradeItemRequests(),
                (a, b) -> a.getGoodsInfoId().equals(b.getSkuId()),
                (c, d) -> {
                    d.setBuyPoint(c.getBuyPoint());
                });
        tradeBuyCheckInterface.validateAppointmentQualification(paramsDataVO);

        // 预售活动校验
        tradeBuyCheckInterface.validateBookingSale(paramsDataVO);

        // 商品校验
        tradeBuyCheckInterface.validateGoodsStock(paramsDataVO);

        // 商品渠道检验
        tradeBuyCheckInterface.verifyChannelGoods(paramsDataVO);
    }
}
