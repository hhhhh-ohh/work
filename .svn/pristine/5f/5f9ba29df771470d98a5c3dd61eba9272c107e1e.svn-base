package com.wanmi.sbc.order;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.VerifyQueryProvider;
import com.wanmi.sbc.order.api.request.trade.PointsTradeCommitRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetGoodsRequest;
import com.wanmi.sbc.order.api.request.trade.VerifyPointsGoodsRequest;
import com.wanmi.sbc.order.api.response.trade.TradeGetGoodsResponse;
import com.wanmi.sbc.order.bean.dto.TradeGoodsInfoPageDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.request.ImmediateExchangeRequest;
import com.wanmi.sbc.order.request.PointsTradeItemRequest;
import com.wanmi.sbc.order.response.PointsTradeConfirmResponse;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>积分订单Controller</p>
 * Created by yinxianzhi on 2019-05-20-下午4:12.
 */
@Tag(name = "PointsTradeController", description = "积分订单服务API")
@RestController
@Validated
@RequestMapping("/pointsTrade")
@Slf4j
public class PointsTradeController {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Resource
    private VerifyQueryProvider verifyQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    @Resource
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private PointsGoodsQueryProvider pointsGoodsQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    /**
     * 立即兑换
     */
    @Operation(summary = "立即兑换")
    @RequestMapping(value = "/exchange", method = RequestMethod.POST)
    public BaseResponse exchange(@RequestBody @Valid ImmediateExchangeRequest exchangeRequest) {
        // 1.获取积分商品信息
        PointsGoodsByIdRequest idReq = new PointsGoodsByIdRequest();
        idReq.setPointsGoodsId(exchangeRequest.getPointsGoodsId());
        PointsGoodsVO pointsGoodsVO = pointsGoodsQueryProvider.getById(idReq).getContext().getPointsGoodsVO();

        //商品原始信息
        if(Objects.nonNull(pointsGoodsVO)) {
            GoodsInfoByIdResponse goodsInfo = goodsInfoQueryProvider
                    .getById(GoodsInfoByIdRequest.builder().goodsInfoId(pointsGoodsVO.getGoodsInfoId()).build())
                    .getContext();
            //校验原始商品的删除、上架、可售状态
            if(Objects.isNull(goodsInfo)
                    || DeleteFlag.YES.equals(goodsInfo.getDelFlag())
                    || AddedFlag.NO.toValue()==goodsInfo.getAddedFlag()
                    || Constants.no.equals(goodsInfo.getVendibility())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030162);
            }
            // 2.验证积分商品(校验积分商品库存，删除，启用停用状态，兑换时间)
            if (DeleteFlag.YES.equals(pointsGoodsVO.getDelFlag()) || EnableStatus.DISABLE.equals(pointsGoodsVO.getStatus())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030162);
            }
            if (pointsGoodsVO.getStock() < exchangeRequest.getNum()) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030163);
            }
            if (pointsGoodsVO.getEndTime().isBefore(LocalDateTime.now())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030164);
            }
            // 3.验证用户积分
            //查询会员可用积分
            CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                    (commonUtil.getOperatorId())).getContext();
            //会员积分余额
            Long pointsAvailable = customer.getPointsAvailable();
            if (pointsAvailable.compareTo(pointsGoodsVO.getPoints() * exchangeRequest.getNum()) < 0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030165);
            }
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 用于立即兑换后，创建订单前的获取订单积分商品信息
     *
     * @param pointsTradeItemRequest
     * @return
     */
    @Operation(summary = "用于确认订单后，创建订单前的获取积分商品信息")
    @RequestMapping(value = "/getExchangeItem", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse<PointsTradeConfirmResponse> getExchangeItem(@RequestBody @Valid PointsTradeItemRequest pointsTradeItemRequest) {
        PointsTradeConfirmResponse confirmResponse = new PointsTradeConfirmResponse();
        // 获取积分商品信息
        PointsGoodsVO pointsGoodsVO = pointsGoodsQueryProvider.getById(new PointsGoodsByIdRequest
                (pointsTradeItemRequest.getPointsGoodsId())).getContext().getPointsGoodsVO();
        PointsTradeItemGroupVO pointsTradeItemGroupVO = wrapperGroupVO(pointsGoodsVO,
                pointsTradeItemRequest.getNum());

        confirmResponse.setPointsTradeConfirmItem(pointsTradeItemGroupVO);
        confirmResponse.setTotalPoints(pointsTradeItemGroupVO.getTradeItem().getPoints() * pointsTradeItemGroupVO
                .getTradeItem().getNum());
        Integer goodsType = pointsTradeItemGroupVO.getTradeItem().getGoodsType();
        OrderTagVO orderTag = new OrderTagVO();
        switch (goodsType) {
            case 1:
                orderTag.setVirtualFlag(Boolean.TRUE);
                break;
            case 2:
                orderTag.setElectronicCouponFlag(Boolean.TRUE);
                break;
            default:
                break;
        }
        confirmResponse.setOrderTagVO(orderTag);
        return BaseResponse.success(confirmResponse);
    }

    /**
     * 提交积分订单，用于生成积分订单操作
     */
    @Operation(summary = "提交订单，用于生成订单操作")
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse<PointsTradeCommitResultVO> commit(@RequestBody @Valid PointsTradeCommitRequest
                                                                  pointsTradeCommitRequest) {
        Operator operator = commonUtil.getOperator();
        pointsTradeCommitRequest.setOperator(operator);
        boolean isOpen = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        pointsTradeCommitRequest.setIsOpen(isOpen);
        // 获取积分商品信息
        PointsGoodsVO pointsGoodsVO = pointsGoodsQueryProvider.getById(new PointsGoodsByIdRequest
                (pointsTradeCommitRequest.getPointsGoodsId())).getContext().getPointsGoodsVO();
        // 获取商品类型
        Integer goodsType = pointsGoodsVO.getGoods().getGoodsType();
        // 如果是实物商品，则校验收货地址参数
        if (NumberUtils.INTEGER_ZERO.equals(goodsType)) {
            //收货地址是否为空
            boolean addressIsEmpty = StringUtils.isEmpty(pointsTradeCommitRequest.getConsigneeId()) ||
                    StringUtils.isEmpty(pointsTradeCommitRequest.getConsigneeAddress());
            // 如果为空，则校验不通过
            if (addressIsEmpty) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else {
            //虚拟订单或者卡券订单取地址
            CustomerDeliveryAddressRequest addressRequest = new CustomerDeliveryAddressRequest();
            addressRequest.setCustomerId(commonUtil.getOperatorId());
            CustomerDeliveryAddressResponse addressResponse = customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(addressRequest).getContext();
            //如果有，就设置，没有则空
            if (Objects.nonNull(addressResponse)) {
                pointsTradeCommitRequest.setConsigneeId(addressResponse.getDeliveryAddressId());
                PlatformAddressListRequest request = PlatformAddressListRequest.builder()
                        .addrIdList(Lists.newArrayList(String.valueOf(addressResponse.getProvinceId()),
                                String.valueOf(addressResponse.getCityId()),
                                String.valueOf(addressResponse.getAreaId()),
                                String.valueOf(addressResponse.getStreetId())))
                        .delFlag(DeleteFlag.NO)
                        .build();
                List<PlatformAddressVO> platformAddressVOList = platformAddressQueryProvider.list(request)
                        .getContext().getPlatformAddressVOList();
                String consigneeAddress = platformAddressVOList.parallelStream().map(PlatformAddressVO::getAddrName).collect(Collectors.joining());
                consigneeAddress = consigneeAddress.concat(addressResponse.getDeliveryAddress());
                pointsTradeCommitRequest.setConsigneeAddress(consigneeAddress);
            }
        }
        PointsTradeItemGroupVO pointsTradeItemGroup = wrapperGroupVO(pointsGoodsVO,
                pointsTradeCommitRequest.getNum());
        pointsTradeCommitRequest.setPointsTradeItemGroup(pointsTradeItemGroup);
        //设置商品类型
        pointsTradeCommitRequest.setGoodsType(goodsType);
        PointsTradeCommitResultVO successResult =
                tradeProvider.pointsCommit(pointsTradeCommitRequest).getContext().getPointsTradeCommitResult();
        return BaseResponse.success(successResult);
    }

    /**
     * 包装积分订单项数据
     * 普通订单从快照中读取 积分订单根据前台数据组装
     *
     * @param pointsGoodsVO
     * @param num
     * @return
     */
    private PointsTradeItemGroupVO wrapperGroupVO(PointsGoodsVO pointsGoodsVO, Long num) {
        // 1.验证用户积分
        //查询会员可用积分
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (commonUtil.getOperatorId())).getContext();
        //会员积分余额
        Long pointsAvailable = customer.getPointsAvailable();
        if (pointsAvailable.compareTo(pointsGoodsVO.getPoints() * num) < 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030165);
        }
        // 2.获取商品信息
        GoodsInfoResponse skuResp = getGoodsResponse(pointsGoodsVO.getGoodsInfoId());
        // 3.获取商品所属商家，店铺信息
        Long storeId = skuResp.getGoodses().get(0).getStoreId();

        StoreVO store = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder().storeId(storeId)
                .build())
                .getContext().getStoreVO();
        SupplierVO supplierVO = SupplierVO.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .isSelf(store.getCompanyType() == BoolFlag.NO)
                .supplierCode(store.getCompanyInfo().getCompanyCode())
                .supplierId(store.getCompanyInfo().getCompanyInfoId())
                .supplierName(store.getCompanyInfo().getSupplierName())
                .freightTemplateType(store.getFreightTemplateType())
                .build();
        // 4.验证包装积分订单商品信息
        TradeItemVO tradeItemVO = verifyQueryProvider.verifyPointsGoods(new VerifyPointsGoodsRequest(
                TradeItemDTO.builder().num(num).skuId(pointsGoodsVO.getGoodsInfoId()).points(pointsGoodsVO.getPoints())
                        .pointsGoodsId(pointsGoodsVO.getPointsGoodsId()).settlementPrice(pointsGoodsVO.getSettlementPrice()).build(),
                pointsGoodsVO,
                KsBeanUtil.convert(skuResp, TradeGoodsInfoPageDTO.class),
                storeId)).getContext().getTradeItem();
        // 5.包装返回值
        PointsTradeItemGroupVO pointsTradeItemGroupVO = new PointsTradeItemGroupVO();
        pointsTradeItemGroupVO.setSupplier(supplierVO);
        pointsTradeItemGroupVO.setTradeItem(tradeItemVO);

        return pointsTradeItemGroupVO;
    }

    /**
     * 获取订单商品详情
     */
    private GoodsInfoResponse getGoodsResponse(String skuId) {
        TradeGetGoodsResponse response =
                tradeQueryProvider.getGoods(TradeGetGoodsRequest.builder().skuIds(Collections.singletonList(skuId)).build
                        ()).getContext();

        return GoodsInfoResponse.builder().goodsInfos(response.getGoodsInfos())
                .goodses(response.getGoodses())
                .goodsIntervalPrices(response.getGoodsIntervalPrices())
                .build();
    }


}
