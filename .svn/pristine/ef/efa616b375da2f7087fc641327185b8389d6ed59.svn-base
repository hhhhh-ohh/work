package com.wanmi.sbc.order.sellplatform;

import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.storereturnaddress.StoreReturnAddressQueryProvider;
import com.wanmi.sbc.customer.api.request.storereturnaddress.StoreReturnAddressListRequest;
import com.wanmi.sbc.customer.api.response.storereturnaddress.StoreReturnAddressListResponse;
import com.wanmi.sbc.customer.bean.vo.StoreReturnAddressVO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.ReturnType;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnAddress;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.model.value.ReturnLogistics;
import com.wanmi.sbc.order.returnorder.repository.ReturnOrderRepository;
import com.wanmi.sbc.order.trade.model.mapper.TradeMapper;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformReturnOrderProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.SellPlatformAcceptReturnOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.SellPlatformAddReturnOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.SellPlatformReturnOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.SellPlatformUpReturnOrderRequest;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformReturnAddressVO;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformReturnLogisticsVO;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformReturnOrderVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * @author wur
 * @className SellPlatformReturnTradeService
 * @description 第三方平台退单处理
 * @date 2022/4/19 18:24
 **/
@Service
public class SellPlatformReturnTradeService {

    @Autowired private SellPlatformReturnOrderProvider sellPlatformReturnOrderProvider;

    @Autowired private TradeMapper tradeMapper;

    @Autowired private StoreReturnAddressQueryProvider returnAddressQueryProvider;

    @Autowired private ReturnOrderRepository returnOrderRepository;

    /**
     * @description   添加售后单
     * @author  wur
     * @date: 2022/4/24 19:22
     * @param returnOrder
     * @param trade
     * @return
     **/
    public ReturnOrder addReturnOrder(ReturnOrder returnOrder, Trade trade) {
        // 验证关联的订单是否是视频号订单
        if (Objects.isNull(trade.getSellPlatformType())
                || SellPlatformType.NOT_SELL.toValue() == trade.getSellPlatformType().toValue()) {
            return returnOrder;
        }

        if (CollectionUtils.isEmpty(returnOrder.getReturnItems())) {
            return returnOrder;
        }
        ReturnOrderVO returnOrderVO = KsBeanUtil.convert(returnOrder, ReturnOrderVO.class);
        returnOrderVO.setTradeVO(tradeMapper.tradeToTradeVo(trade));

        SellPlatformReturnOrderVO sellPlatformReturnOrderVO = KsBeanUtil.convert(returnOrderVO, SellPlatformReturnOrderVO.class);
        sellPlatformReturnOrderVO.setType(ReturnType.REFUND.toValue() == returnOrderVO.getReturnType().toValue() ? 1 : 2);
        SellPlatformAddReturnOrderRequest request = SellPlatformAddReturnOrderRequest.builder()
                .returnOrderVO(sellPlatformReturnOrderVO)
                .build();
        request.setSellPlatformType(trade.getSellPlatformType());
        BaseResponse<String> baseResponse = sellPlatformReturnOrderProvider.addReturnOrder(request);
        if (baseResponse.isSuccess()) {
            returnOrder.setSellPlatformType(trade.getSellPlatformType());
            returnOrder.setSellPlatformReturnId(baseResponse.getContext());
            returnOrder.setVideoUser(trade.getVideoUser());
            returnOrder.setSceneGroup(trade.getSceneGroup());
        } else {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020032);
        }
        return returnOrder;
    }

    /**
     * @description     取消售后单
     * @author  wur
     * @date: 2022/4/24 19:24
     * @param returnOrder
     * @return
     **/
    public void cancelReturnOrder(ReturnOrder returnOrder) {
        //验证关联的订单是否是视频号订单
        if (Objects.isNull(returnOrder.getSellPlatformType())
                || SellPlatformType.NOT_SELL.toValue() == returnOrder.getSellPlatformType().toValue()
                || Strings.isBlank(returnOrder.getSellPlatformReturnId())) {
            return;
        }

        SellPlatformReturnOrderRequest request = SellPlatformReturnOrderRequest.builder()
                .thirdOpenId(returnOrder.getBuyer().getThirdLoginOpenId())
                .returnOrderId(returnOrder.getId())
                .aftersaleId(returnOrder.getSellPlatformReturnId())
                .build();
        request.setSellPlatformType(returnOrder.getSellPlatformType());
        sellPlatformReturnOrderProvider.cancelReturnOrder(request);
    }

    /**
     * @description    同意退货
     *                 需要封装商家的收货地址信息
     * @author  wur
     * @date: 2022/4/24 19:45
     * @param returnOrder
     * @return
     **/
    public void acceptReturnOrder(ReturnOrder returnOrder, ReturnAddress returnAddress) {
        //验证关联的订单是否是视频号订单
        if (Objects.isNull(returnOrder.getSellPlatformType())
                || SellPlatformType.NOT_SELL.toValue() == returnOrder.getSellPlatformType().toValue()
                || Strings.isBlank(returnOrder.getSellPlatformReturnId())) {
            return;
        }
        //验证订单是否是退货退款
        if (!ReturnType.RETURN.equals(returnOrder.getReturnType())) {
            return;
        }
        if (Objects.isNull(returnAddress)) {
            returnAddress = returnOrder.getReturnAddress();
        }
        //取商家默认退货地址
        if (Objects.isNull(returnAddress)) {
            StoreReturnAddressListResponse addressListResponse = returnAddressQueryProvider.list(StoreReturnAddressListRequest.builder()
                    .storeId(StringUtils.isNotBlank(returnOrder.getProviderId()) ? Long.valueOf(returnOrder.getProviderId()) : returnOrder.getCompany().getStoreId())
                    .delFlag(DeleteFlag.NO)
                    .showAreaNameFlag(Boolean.TRUE)
                    .isDefaultAddress(Boolean.TRUE)
                    .build()).getContext();
            if(Objects.isNull(addressListResponse) || CollectionUtils.isEmpty(addressListResponse.getStoreReturnAddressVOList())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050092);
            }
            returnAddress = wapperReturnAddress(addressListResponse.getStoreReturnAddressVOList().get(0));
        }

        SellPlatformAcceptReturnOrderRequest request = SellPlatformAcceptReturnOrderRequest.builder()
                .thirdOpenId(returnOrder.getBuyer().getThirdLoginOpenId())
                .returnOrderId(returnOrder.getId())
                .aftersaleId(returnOrder.getSellPlatformReturnId())
                .returnAddressVO(KsBeanUtil.convert(returnAddress, SellPlatformReturnAddressVO.class))
                .build();
        request.setSellPlatformType(returnOrder.getSellPlatformType());
        sellPlatformReturnOrderProvider.acceptReturnOrder(request);
    }

    /**
     * @description    处理商家收货地址信息
     * @author  wur
     * @date: 2022/4/26 11:45
     * @param addressVO
     * @return
     **/
    private ReturnAddress wapperReturnAddress(StoreReturnAddressVO addressVO) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.defaultString(addressVO.getProvinceName()));
        sb.append(StringUtils.defaultString(addressVO.getCityName()));
        sb.append(StringUtils.defaultString(addressVO.getAreaName()));
        sb.append(StringUtils.defaultString(addressVO.getStreetName()));
        sb.append(StringUtils.defaultString(addressVO.getReturnAddress()));
        return ReturnAddress.builder()
                .id(addressVO.getAddressId())
                .name(addressVO.getConsigneeName())
                .phone(addressVO.getConsigneeNumber())
                .provinceId(addressVO.getProvinceId())
                .cityId(addressVO.getCityId())
                .areaId(addressVO.getAreaId())
                .streetId(addressVO.getStreetId())
                .address(addressVO.getReturnAddress())
                .provinceName(addressVO.getProvinceName())
                .cityName(addressVO.getCityName())
                .areaName(addressVO.getAreaName())
                .streetName(addressVO.getStreetName())
                .detailAddress(sb.toString())
                .build();
    }

    /**
     * @description     上传物流单信息
     * @author  wur
     * @date: 2022/4/26 11:49
     * @param rid
     * @param logistics
     * @return
     **/
    public void upReturnInfo(String rid, ReturnLogistics logistics) {
        //查询退单信息
        ReturnOrder returnOrder = returnOrderRepository.findById(rid).orElse(null);
        if (returnOrder == null) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
        //验证是否是视频号订单
        if (Objects.isNull(returnOrder.getSellPlatformType())
                || SellPlatformType.NOT_SELL.toValue() == returnOrder.getSellPlatformType().toValue()
                || Strings.isBlank(returnOrder.getSellPlatformReturnId())) {
            return;
        }
        //封装请求
        SellPlatformUpReturnOrderRequest request = new SellPlatformUpReturnOrderRequest();
        request.setReturnOrderId(returnOrder.getId());
        request.setThirdOpenId(returnOrder.getBuyer().getThirdLoginOpenId());
        request.setAftersaleId(returnOrder.getSellPlatformReturnId());
        request.setReturnLogistics(KsBeanUtil.convert(logistics, SellPlatformReturnLogisticsVO.class));
        request.setSellPlatformType(returnOrder.getSellPlatformType());
        sellPlatformReturnOrderProvider.upReturnInfo(request);
    }

    /**
     * @description   修改退单
     * @author  wur
     * @date: 2022/4/27 10:02
     * @param returnOrder
     * @return
     **/
    public void updateReturn (ReturnOrder returnOrder) {
        //验证是否是视频号订单
//        if (Objects.isNull(returnOrder.getSellPlatformType())
//                || Strings.isBlank(returnOrder.getSellPlatformReturnId())) {
//            return;
//        }
//        //验证是否有价格变更
//        if(Objects.isNull(returnOrder.getReturnPrice().getFee())
//                && (Objects.isNull(returnOrder.getReturnPrice().getApplyPrice()) || returnOrder.getReturnPrice().getApplyPrice().equals(returnOrder.getReturnPrice().getTotalPrice()))) {
//            return;
//        }
//        SellPlatformAddReturnOrderRequest request = SellPlatformAddReturnOrderRequest.builder()
//                .returnOrderVO(KsBeanUtil.convert(returnOrder, ReturnOrderVO.class))
//                .build();
//        request.setSellPlatformType(returnOrder.getSellPlatformType());
//        sellPlatformReturnOrderProvider.updateReturn(request);
    }
}