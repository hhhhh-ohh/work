package com.wanmi.sbc.order.optimization.trade1.commit.common;

import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.RandomUtils;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByIdAndDelFlagRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressByIdResponse;
import com.wanmi.sbc.customer.api.response.invoice.CustomerInvoiceByIdAndDelFlagResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.goods.api.response.cate.ContractCateListResponse;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.ContractCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoBaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.order.api.request.pickupcoderecord.PickupCodeRecordQueryRequest;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.bean.dto.TradeBuyCycleDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.payingmemberrecord.model.root.PayingMemberRecord;
import com.wanmi.sbc.order.pickupcoderecord.model.root.PickupCodeRecord;
import com.wanmi.sbc.order.pickupcoderecord.service.PickupCodeRecordService;
import com.wanmi.sbc.order.trade.model.entity.*;
import com.wanmi.sbc.order.trade.model.entity.value.*;
import com.wanmi.sbc.order.trade.model.root.PayingMemberInfo;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeGroupon;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.WriteOffStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className Trade1CommitBuild
 * @description TODO
 * @date 2022/3/10 2:53 下午
 */
@Service
public class Trade1CommitBuilder {

    @Autowired TradeCacheService tradeCacheService;

    @Autowired GeneratorService generatorService;

    @Autowired PickupCodeRecordService pickupCodeRecordService;

    public Buyer buildBuyer(StoreVO storeVO, CommonLevelVO commonLevelVO, CustomerVO customerVO) {
        // 判断是不是店铺会员
        boolean customerFlag = true;
        if (storeVO.getCompanyType().equals(BoolFlag.YES)
                && commonLevelVO.getLevelType().equals(BoolFlag.YES)) {
            customerFlag = false;
        }

        // 1、设置订单买家信息
        return Buyer.fromCustomer(customerVO, commonLevelVO, customerFlag);
    }

    public Consignee buildConsignee(TradeCommitRequest request, Trade1CommitParam param) {
        if (StringUtils.isBlank(request.getConsigneeId())) {
            return null;
        }
        return Consignee.builder()
                .id(request.getConsigneeId())
                .detailAddress(param.getCustomerDeliveryAddressVO().getDeliveryAddress().concat(param.getCustomerDeliveryAddressVO().getHouseNum()))
                .updateTime(request.getConsigneeUpdateTime())
                .phone(param.getCustomerDeliveryAddressVO().getConsigneeNumber())
                .provinceId(param.getCustomerDeliveryAddressVO().getProvinceId())
                .cityId(param.getCustomerDeliveryAddressVO().getCityId())
                .areaId(param.getCustomerDeliveryAddressVO().getAreaId())
                .streetId(param.getCustomerDeliveryAddressVO().getStreetId())
                .address(param.getCustomerDeliveryAddressVO().getDeliveryAddress().concat(param.getCustomerDeliveryAddressVO().getHouseNum()))
                .name(param.getCustomerDeliveryAddressVO().getConsigneeName())
                .latitude(Objects.isNull(request.getLatitude())?param.getCustomerDeliveryAddressVO().getLatitude():request.getLatitude())
                .longitude(Objects.isNull(request.getLongitude())?param.getCustomerDeliveryAddressVO().getLongitude():request.getLongitude())
                .houseNum(StringUtils.isBlank(request.getHouseNum())?param.getCustomerDeliveryAddressVO().getHouseNum():request.getHouseNum())
                .shippingAddress(param.getCustomerDeliveryAddressVO().getDeliveryAddress())
                .build();
    }

    public Invoice buildInvoice(
            StoreCommitInfoDTO storeCommitInfoDTO, CustomerDeliveryAddressVO address) {
        Invoice invoice =
                Invoice.builder()
                        .generalInvoice(
                                KsBeanUtil.convert(
                                        storeCommitInfoDTO.getGeneralInvoice(),
                                        GeneralInvoice.class))
                        .specialInvoice(
                                KsBeanUtil.convert(
                                        storeCommitInfoDTO.getSpecialInvoice(),
                                        SpecialInvoice.class))
                        .address(storeCommitInfoDTO.getInvoiceAddressDetail())
                        .addressId(storeCommitInfoDTO.getInvoiceAddressId())
                        .projectId(storeCommitInfoDTO.getInvoiceProjectId())
                        .projectName(storeCommitInfoDTO.getInvoiceProjectName())
                        .projectUpdateTime(storeCommitInfoDTO.getInvoiceProjectUpdateTime())
                        .type(storeCommitInfoDTO.getInvoiceType())
                        .sperator(storeCommitInfoDTO.isSpecialInvoiceAddress())
                        .updateTime(storeCommitInfoDTO.getInvoiceAddressUpdateTime())
                        .build();
        if (storeCommitInfoDTO.getInvoiceType() != -1) {
            String taxNo = "";
            // 增票
            if (storeCommitInfoDTO.getInvoiceType().equals(InvoiceType.SPECIAL.toValue())) {
                taxNo =
                        Objects.nonNull(storeCommitInfoDTO.getGeneralInvoice())
                                ? storeCommitInfoDTO.getGeneralInvoice().getIdentification()
                                : "";
            } else {
                taxNo =
                        Objects.nonNull(storeCommitInfoDTO.getSpecialInvoice())
                                ? storeCommitInfoDTO.getSpecialInvoice().getIdentification()
                                : "";
            }
            invoice.setTaxNo(taxNo);

            // 1.若用户选择了某个发票收货地址,查询该地址的联系人与联系方式
            if (storeCommitInfoDTO.isSpecialInvoiceAddress()) {
                BaseResponse<CustomerDeliveryAddressByIdResponse>
                        customerDeliveryAddressByIdResponseBaseResponse =
                        tradeCacheService.getCustomerDeliveryAddressById(
                                invoice.getAddressId());
                CustomerDeliveryAddressByIdResponse customerDeliveryAddressByIdResponse =
                        customerDeliveryAddressByIdResponseBaseResponse.getContext();
                invoice.setPhone(customerDeliveryAddressByIdResponse.getConsigneeNumber());
                invoice.setContacts(customerDeliveryAddressByIdResponse.getConsigneeName());
                invoice.setProvinceId(customerDeliveryAddressByIdResponse.getProvinceId());
                invoice.setCityId(customerDeliveryAddressByIdResponse.getCityId());
                invoice.setAreaId(customerDeliveryAddressByIdResponse.getAreaId());
            }
            // 2.若用户没有选择发货地址，使用临时地址(代客下单特殊-可以传发票临时收货地址)
            else {
                // 虚拟商品有可能出现此问题
                if (address == null) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129);
                }
                invoice.setPhone(address.getConsigneeNumber());
                invoice.setContacts(address.getConsigneeName());
                invoice.setProvinceId(address.getProvinceId());
                invoice.setCityId(address.getCityId());
                invoice.setAreaId(address.getAreaId());
                StringBuffer delAddress = new StringBuffer();
                delAddress.append(address.getDeliveryAddress());
                if (StringUtils.isNotEmpty(address.getHouseNum())) {
                    delAddress.append(address.getHouseNum());
                }
                invoice.setAddress(delAddress.toString());
            }

            // 3.校验与填充增票信息
            if (invoice.getType() == 1) {
                SpecialInvoice spInvoice = invoice.getSpecialInvoice();
                CustomerInvoiceByIdAndDelFlagRequest customerInvoiceByCustomerIdRequest =
                        new CustomerInvoiceByIdAndDelFlagRequest();
                customerInvoiceByCustomerIdRequest.setCustomerInvoiceId(spInvoice.getId());
                BaseResponse<CustomerInvoiceByIdAndDelFlagResponse>
                        customerInvoiceByIdAndDelFlagResponseBaseResponse =
                        tradeCacheService.getCustomerInvoiceByIdAndDelFlag(
                                spInvoice.getId());
                CustomerInvoiceByIdAndDelFlagResponse customerInvoiceByIdAndDelFlagResponse =
                        customerInvoiceByIdAndDelFlagResponseBaseResponse.getContext();
                if (Objects.nonNull(customerInvoiceByIdAndDelFlagResponse)) {
                    if (customerInvoiceByIdAndDelFlagResponse.getCheckState()
                            != CheckState.CHECKED) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010015);
                    }
                    spInvoice.setAccount(customerInvoiceByIdAndDelFlagResponse.getBankNo());
                    spInvoice.setIdentification(
                            customerInvoiceByIdAndDelFlagResponse.getTaxpayerNumber());
                    spInvoice.setAddress(customerInvoiceByIdAndDelFlagResponse.getCompanyAddress());
                    spInvoice.setBank(customerInvoiceByIdAndDelFlagResponse.getBankName());
                    spInvoice.setCompanyName(
                            customerInvoiceByIdAndDelFlagResponse.getCompanyName());
                    spInvoice.setPhoneNo(customerInvoiceByIdAndDelFlagResponse.getCompanyPhone());
                }
            }
            return invoice;
        }
        return invoice;
    }

    public PayInfo buildPayInfo(PayType payType) {
        if (payType != null) {
            // 支付信息

            return PayInfo.builder()
                    .payTypeId(String.format("%d", payType.toValue()))
                    .payTypeName(payType.name())
                    .desc(payType.getDesc())
                    .build();
        }
        return null;
    }

    public PaymentOrder buildPayment() {
        Integer paymentOrder =
                tradeCacheService
                        .getTradeConfigByType(ConfigType.ORDER_SETTING_PAYMENT_ORDER)
                        .getStatus();
        return PaymentOrder.values()[paymentOrder];
    }

    public List<TradeItem> buildItem(
            List<TradeItem> tradeItems, List<GoodsInfoTradeVO> goodsInfoTradeVOS, DefaultFlag isStoreBag, BargainVO bargainVO, TradeBuyCycleDTO tradeBuyCycleDTO) {
        Map<String, GoodsInfoTradeVO> goodsInfoTradeVOMap =
                goodsInfoTradeVOS.stream()
                        .collect(
                                Collectors.toMap(
                                        GoodsInfoBaseVO::getGoodsInfoId, Function.identity()));
        tradeItems.forEach(
                tradeItem -> {
                    GoodsInfoTradeVO goodsInfo = goodsInfoTradeVOMap.get(tradeItem.getSkuId());
                    tradeItem.setOid(generatorService.generateOid());
                    tradeItem.setSkuName(goodsInfo.getGoodsInfoName());
                    tradeItem.setSpuName(goodsInfo.getGoodsInfoName());
                    tradeItem.setPic(goodsInfo.getGoodsInfoImg());
                    tradeItem.setBrand(goodsInfo.getBrandId());
                    tradeItem.setDeliverStatus(DeliverStatus.NOT_YET_SHIPPED);
                    tradeItem.setCateId(goodsInfo.getCateId());
                    tradeItem.setSkuNo(goodsInfo.getGoodsInfoNo());
                    tradeItem.setSpuId(goodsInfo.getGoodsId());
                    tradeItem.setUnit(goodsInfo.getGoodsUnit());
                    tradeItem.setGoodsWeight(goodsInfo.getGoodsWeight());
                    tradeItem.setGoodsCubage(goodsInfo.getGoodsCubage());
                    tradeItem.setFreightTempId(goodsInfo.getFreightTempId());
                    tradeItem.setStoreId(goodsInfo.getStoreId());
                    tradeItem.setDistributionGoodsAudit(goodsInfo.getDistributionGoodsAudit());
                    tradeItem.setCommissionRate(goodsInfo.getCommissionRate());
                    tradeItem.setDistributionCommission(goodsInfo.getDistributionCommission());
                    Long num = tradeItem.getNum();
                    if (Objects.nonNull(tradeBuyCycleDTO)) {
                        goodsInfo.setMarketPrice(tradeBuyCycleDTO.getCyclePrice());
                        tradeItem.setBuyCycleNum(tradeBuyCycleDTO.getDeliveryCycleNum());
                        num = num * tradeBuyCycleDTO.getDeliveryCycleNum();
                    }
                    if (tradeItem.getSkuName().contains("校服")&&(tradeItem.getOriginalPrice().compareTo(goodsInfo.getMarketPrice())>-1)){

                    }else {

                        tradeItem.setOriginalPrice(goodsInfo.getMarketPrice());
                    }
                    // 获取价格，包含区间价
                    BigDecimal price =
                            Trade1CommitPriceUtil.getPrice(goodsInfo, num,isStoreBag);

                    //处理砍价商品价格
                    if (Objects.nonNull(bargainVO)) {
                        tradeItem.setOriginalPrice(bargainVO.getMarketPrice());
                        price = bargainVO.getMarketPrice().subtract(bargainVO.getTargetBargainPrice());
                    }
                    if (tradeItem.getSkuName().contains("校服")&&(tradeItem.getOriginalPrice().compareTo(goodsInfo.getMarketPrice())>-1)){

                    }else {
                        tradeItem.setLevelPrice(price);
                        tradeItem.setPrice(price);
                        tradeItem.setSplitPrice(price.multiply(new BigDecimal(num)));
                    }
                    tradeItem.setCateTopId(goodsInfo.getCateTopId());
                    tradeItem.setEnterPriseAuditState(goodsInfo.getEnterPriseAuditState());
                    tradeItem.setEnterPrisePrice(goodsInfo.getEnterPrisePrice());
                    tradeItem.setBuyPoint(goodsInfo.getBuyPoint());
                    tradeItem.setThirdPlatformSpuId(goodsInfo.getThirdPlatformSpuId());
                    tradeItem.setThirdPlatformSkuId(goodsInfo.getThirdPlatformSkuId());
                    tradeItem.setGoodsSource(goodsInfo.getGoodsSource());
                    tradeItem.setProviderId(goodsInfo.getProviderId());
                    tradeItem.setThirdPlatformType(goodsInfo.getThirdPlatformType());
                    tradeItem.setSupplyPrice(goodsInfo.getSupplyPrice());
                    tradeItem.setProviderSkuId(goodsInfo.getProviderGoodsInfoId());
                    BigDecimal supplyPrice =
                            Objects.nonNull(goodsInfo.getSupplyPrice())
                                    ? goodsInfo.getSupplyPrice()
                                    : BigDecimal.ZERO;
                    // 供货价总额
                    tradeItem.setTotalSupplyPrice(
                            supplyPrice.multiply(new BigDecimal(num)));
                    tradeItem.setPluginType(goodsInfo.getPluginType());

                    if (StringUtils.isBlank(tradeItem.getSpecDetails())) {
                        tradeItem.setSpecDetails(goodsInfo.getSpecText());
                    }

                    // 设置分类费率
                    BaseResponse<ContractCateListResponse> baseResponse =
                            tradeCacheService.queryContractCateList(
                                    goodsInfo.getStoreId(), goodsInfo.getCateId());
                    ContractCateListResponse contractCateListResponse = baseResponse.getContext();
                    if (Objects.nonNull(contractCateListResponse)) {
                        List<ContractCateVO> cates = contractCateListResponse.getContractCateList();
                        if (CollectionUtils.isNotEmpty(cates)) {
                            ContractCateVO cateResponse = cates.get(0);
                            tradeItem.setCateName(cateResponse.getCateName());
                            tradeItem.setCateRate(
                                    cateResponse.getCateRate() != null
                                            ? cateResponse.getCateRate()
                                            : cateResponse.getPlatformCateRate());
                        }
                    }

                    tradeItem.setElectronicCouponsId(goodsInfo.getElectronicCouponsId());
                    tradeItem.setGoodsType(goodsInfo.getGoodsType());
                });
        return tradeItems;
    }

    public List<TradeItem> buildItem(
            List<TradeItem> tradeItems, List<GoodsInfoTradeVO> goodsInfoTradeVOS, Map<String, BigDecimal> skuIdToActivityPrice) {
        Map<String, GoodsInfoTradeVO> goodsInfoTradeVOMap =
                goodsInfoTradeVOS.stream()
                        .collect(
                                Collectors.toMap(
                                        GoodsInfoBaseVO::getGoodsInfoId, Function.identity()));

        tradeItems.forEach(
                tradeItem -> {
                    GoodsInfoTradeVO goodsInfo = goodsInfoTradeVOMap.get(tradeItem.getSkuId());
                    tradeItem.setOid(generatorService.generateOid());
                    tradeItem.setSkuName(goodsInfo.getGoodsInfoName());
                    tradeItem.setSpuName(goodsInfo.getGoodsInfoName());
                    tradeItem.setPic(goodsInfo.getGoodsInfoImg());
                    tradeItem.setBrand(goodsInfo.getBrandId());
                    tradeItem.setDeliverStatus(DeliverStatus.NOT_YET_SHIPPED);
                    tradeItem.setCateId(goodsInfo.getCateId());
                    tradeItem.setSkuNo(goodsInfo.getGoodsInfoNo());
                    tradeItem.setSpuId(goodsInfo.getGoodsId());
                    tradeItem.setUnit(goodsInfo.getGoodsUnit());
                    tradeItem.setGoodsWeight(goodsInfo.getGoodsWeight());
                    tradeItem.setGoodsCubage(goodsInfo.getGoodsCubage());
                    tradeItem.setFreightTempId(goodsInfo.getFreightTempId());
                    tradeItem.setStoreId(goodsInfo.getStoreId());
                    tradeItem.setDistributionGoodsAudit(goodsInfo.getDistributionGoodsAudit());
                    tradeItem.setCommissionRate(goodsInfo.getCommissionRate());
                    tradeItem.setDistributionCommission(goodsInfo.getDistributionCommission());
                    Long num = tradeItem.getNum();
                    tradeItem.setOriginalPrice(goodsInfo.getMarketPrice());
                    BigDecimal activityPrice = skuIdToActivityPrice.get(tradeItem.getSkuId());
                    BigDecimal price;
                    if (Objects.nonNull(activityPrice)){
                        price = activityPrice;
                    } else {
                        // 获取价格，包含区间价
                        price =
                                Trade1CommitPriceUtil.getPrice(goodsInfo, num,DefaultFlag.NO);
                    }


                    tradeItem.setLevelPrice(price);
                    tradeItem.setPrice(price);
                    tradeItem.setSplitPrice(price.multiply(new BigDecimal(num)));
                    tradeItem.setCateTopId(goodsInfo.getCateTopId());
                    tradeItem.setEnterPriseAuditState(goodsInfo.getEnterPriseAuditState());
                    tradeItem.setEnterPrisePrice(goodsInfo.getEnterPrisePrice());
                    tradeItem.setBuyPoint(goodsInfo.getBuyPoint());
                    tradeItem.setThirdPlatformSpuId(goodsInfo.getThirdPlatformSpuId());
                    tradeItem.setThirdPlatformSkuId(goodsInfo.getThirdPlatformSkuId());
                    tradeItem.setGoodsSource(goodsInfo.getGoodsSource());
                    tradeItem.setProviderId(goodsInfo.getProviderId());
                    tradeItem.setThirdPlatformType(goodsInfo.getThirdPlatformType());
                    tradeItem.setSupplyPrice(goodsInfo.getSupplyPrice());
                    tradeItem.setProviderSkuId(goodsInfo.getProviderGoodsInfoId());
                    BigDecimal supplyPrice =
                            Objects.nonNull(goodsInfo.getSupplyPrice())
                                    ? goodsInfo.getSupplyPrice()
                                    : BigDecimal.ZERO;
                    // 供货价总额
                    tradeItem.setTotalSupplyPrice(
                            supplyPrice.multiply(new BigDecimal(num)));
                    tradeItem.setPluginType(goodsInfo.getPluginType());

                    if (StringUtils.isBlank(tradeItem.getSpecDetails())) {
                        tradeItem.setSpecDetails(goodsInfo.getSpecText());
                    }

                    // 设置分类费率
                    BaseResponse<ContractCateListResponse> baseResponse =
                            tradeCacheService.queryContractCateList(
                                    goodsInfo.getStoreId(), goodsInfo.getCateId());
                    ContractCateListResponse contractCateListResponse = baseResponse.getContext();
                    if (Objects.nonNull(contractCateListResponse)) {
                        List<ContractCateVO> cates = contractCateListResponse.getContractCateList();
                        if (CollectionUtils.isNotEmpty(cates)) {
                            ContractCateVO cateResponse = cates.get(0);
                            tradeItem.setCateName(cateResponse.getCateName());
                            tradeItem.setCateRate(
                                    cateResponse.getCateRate() != null
                                            ? cateResponse.getCateRate()
                                            : cateResponse.getPlatformCateRate());
                        }
                    }

                    tradeItem.setElectronicCouponsId(goodsInfo.getElectronicCouponsId());
                    tradeItem.setGoodsType(goodsInfo.getGoodsType());
                });
        return tradeItems;
    }

    public void buildPick(
            Trade trade, Long storeId, Map<Long, PickSettingInfo> pickSettingInfoMap) {
        PickSettingInfo pickSettingInfo = pickSettingInfoMap.get(storeId);
        if (pickSettingInfo != null) {
            trade.setPickupFlag(Boolean.TRUE);
            trade.setPickSettingInfo(pickSettingInfo);
            WriteOffInfo writeOffInfo = new WriteOffInfo();
            // 核销码，ZT加订单号加四位随机数
//            writeOffInfo.setWriteOffCode(
//                    "ZT" + trade.getId() + RandomString.get().randomAlpha(4));
            // 核销码：4位日期+4位数字英文随机码（随机码排除字母I、O以及数字0）
            String writeOffCode= getWriteOffCode(trade);
            writeOffInfo.setWriteOffCode(writeOffCode);
            writeOffInfo.setWriteOffStatus(WriteOffStatus.NOT_WRITTEN_OFF);
            trade.setWriteOffInfo(writeOffInfo);
            trade.setDeliverWay(DeliverWay.PICKUP);
        }
    }

    public void buildBooking(
            Trade trade, TradeCommitRequest tradeCommitRequest, TradeItemGroup group) {
        Boolean isBookingFlag = group.getTradeItems().get(0).getIsBookingSaleGoods();
        if (Objects.nonNull(isBookingFlag)
                && isBookingFlag) {
            trade.setIsBookingSaleGoods(isBookingFlag);

            if (Objects.nonNull(group.getTradeItems().get(0).getBookingType())
                    && Objects.isNull(trade.getBookingType())) {
                trade.setBookingType(group.getTradeItems().get(0).getBookingType());
                if (trade.getBookingType() == BookingType.EARNEST_MONEY
                        && StringUtils.isEmpty(trade.getTailOrderNo())) {
                    // 尾款通知手机号
                    trade.setTailNoticeMobile(tradeCommitRequest.getTailNoticeMobile());
                }
            }
        }
    }

    public TradeGroupon buildTradeGroupon(
            TradeGrouponCommitForm grouponForm, List<TradeItem> tradeItems) {
        if (grouponForm != null) {
            // 2.设置订单拼团信息
            return TradeGroupon.builder()
                    .grouponNo(grouponForm.getGrouponNo())
                    .grouponActivityId(grouponForm.getGrouponActivityId())
                    .goodInfoId(tradeItems.get(0).getSkuId())
                    .goodId(tradeItems.get(0).getSpuId())
                    .returnNum(NumberUtils.INTEGER_ZERO)
                    .returnPrice(BigDecimal.ZERO)
                    .grouponOrderStatus(GrouponOrderStatus.WAIT)
                    .leader(grouponForm.getOpenGroupon())
                    .payState(PayState.NOT_PAID)
                    .grouponInviteeId(grouponForm.getShareUserId())
                    .freeDelivery(grouponForm.isFreeDelivery())
                    .build();
        } else {
            return null;
        }
    }

    public TradeState buildTradeState() {
        return TradeState.builder()
                .deliverStatus(DeliverStatus.NOT_YET_SHIPPED)
                .flowState(FlowState.INIT)
                .payState(PayState.NOT_PAID)
                .createTime(LocalDateTime.now())
                .build();
    }

    public PayingMemberInfo buildPayingMemberInfo(PayingMemberLevelVO payingMemberLevelVO, PayingMemberRecord record){
        if (payingMemberLevelVO != null && record != null) {
            return PayingMemberInfo.builder()
                    .levelId(payingMemberLevelVO.getLevelId())
                    .recordId(record.getRecordId())
                    .couponDiscount(BigDecimal.ZERO)
                    .goodsDiscount(BigDecimal.ZERO)
                    .totalDiscount(BigDecimal.ZERO).build();
        }
        return null;
    }

    private String getWriteOffCode(Trade trade){
        String code = RandomUtils.generateRandomString(4);
        String month = String.format("%02d",LocalDate.now().getMonthValue());
        String day = String.format("%02d",LocalDate.now().getDayOfMonth());
        String writeOffCode = month + day + code;
        //查询当前提货码是否存在
        List<PickupCodeRecord> pickupCodeRecordList = pickupCodeRecordService.list(PickupCodeRecordQueryRequest.builder().pickupCode(writeOffCode).build());
        if(CollectionUtils.isNotEmpty(pickupCodeRecordList)){
            getWriteOffCode(trade);
        } else {
            //将提货码保存
            PickupCodeRecord pickupCodeRecord = new PickupCodeRecord();
            pickupCodeRecord.setPickupCode(writeOffCode);
            pickupCodeRecord.setTradeId(trade.getId());
            pickupCodeRecord.setDelFlag(DeleteFlag.NO);
            pickupCodeRecord.setCreateTime(LocalDateTime.now());
            pickupCodeRecord.setUpdateTime(LocalDateTime.now());
            pickupCodeRecordService.add(pickupCodeRecord);
        }
        return writeOffCode;
    }

}
