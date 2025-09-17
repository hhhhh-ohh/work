package com.wanmi.sbc.marketing.provider.impl.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerListByConditionRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerListByConditionResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeListResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBillQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillExportRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillForUserPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillQueryRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBillPageResponse;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBusinessType;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBillVO;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCard;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBill;
import com.wanmi.sbc.marketing.giftcard.model.root.UserGiftCard;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardBillService;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardService;
import com.wanmi.sbc.marketing.giftcard.service.UserGiftCardService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lvzhenwei
 * @className GiftCardBillController
 * @description 礼品卡使用记录
 * @date 2022/12/12 11:23 上午
 **/
@RestController
@Validated
public class GiftCardBillQueryController implements GiftCardBillQueryProvider {

    @Autowired private GiftCardBillService giftCardBillService;

    @Autowired private GiftCardService giftCardService;

    @Autowired private EmployeeQueryProvider employeeQueryProvider;

    @Autowired private CustomerQueryProvider customerQueryProvider;

    /**
     * @description 会员礼品卡使用记录分页查询
     * @author  lvzhenwei
     * @date 2022/12/12 11:42 上午
     * @param queryReq
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBillPageResponse>
     **/
    @Override
    public BaseResponse<GiftCardBillPageResponse> getGiftCardBillPageForUser(@RequestBody @Valid GiftCardBillForUserPageRequest queryReq) {
        Page<GiftCardBill> giftCardDetailPage = giftCardBillService.getGiftCardBillPageForUser(queryReq);
        Page<GiftCardBillVO> newPage = giftCardDetailPage.map(entity -> giftCardBillService.wrapperVo(entity));
        MicroServicePage<GiftCardBillVO> microPage = new MicroServicePage<>(newPage, queryReq.getPageable());
        GiftCardBillPageResponse finalRes = new GiftCardBillPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<GiftCardBillPageResponse> page(@RequestBody @Valid GiftCardBillPageRequest giftCardBillPageReq) {
        GiftCardBillQueryRequest queryReq = KsBeanUtil.convert(giftCardBillPageReq, GiftCardBillQueryRequest.class);
        // 限制b端查看交易类型范围，订单抵扣、订单退款、销卡、订单取消
        queryReq.setBusinessTypeList(Arrays.asList(
                GiftCardBusinessType.ORDER_DEDUCTION,
                GiftCardBusinessType.ORDER_REFUND,
                GiftCardBusinessType.CANCEL_CARD,
                GiftCardBusinessType.ORDER_CANCEL));
        Page<GiftCardBill> giftCardBillPage = giftCardBillService.page(queryReq);
        Page<GiftCardBillVO> newPage = giftCardBillPage.map(entity -> giftCardBillService.wrapperVo(entity));
        MicroServicePage<GiftCardBillVO> microPage = new MicroServicePage<>(newPage, giftCardBillPageReq.getPageable());
        this.fillGiftCardInfo(microPage.getContent());
        this.fillTradePersonInfo(microPage.getContent());
        return BaseResponse.success(new GiftCardBillPageResponse(microPage));
    }

    @Override
    public BaseResponse<Long> countForExport(@RequestBody @Valid GiftCardBillExportRequest request) {
        GiftCardBillQueryRequest queryReq = KsBeanUtil.convert(request, GiftCardBillQueryRequest.class);
        Long total = giftCardBillService.count(queryReq);
        return BaseResponse.success(total);
    }

    /**
     * @description 填充礼品卡信息
     * @author malianfeng
     * @date 2022/12/16 15:02
     * @param giftCardBillList 交易记录列表
     * @return void
     */
    public void fillGiftCardInfo(List<GiftCardBillVO> giftCardBillList) {
        if (CollectionUtils.isNotEmpty(giftCardBillList)) {
            // 1. 收集giftCardId列表
            List<Long> giftCardIdList = giftCardBillList.stream()
                    .map(GiftCardBillVO::getGiftCardId).distinct().collect(Collectors.toList());
            // 2. 查询GiftCard列表
            List<GiftCard> byGiftCardIdList = giftCardService.findByGiftCardIdList(giftCardIdList);
            // 3. 构造GiftCardMap，[GiftCardId] => [GiftCard]
            Map<Long, GiftCard> giftCardMap =
                    byGiftCardIdList.stream().collect(Collectors.toMap(GiftCard::getGiftCardId, Function.identity()));
            // 4. 循环赋值
            giftCardBillList.forEach(item -> {
                GiftCard giftCard = giftCardMap.get(item.getGiftCardId());
                if (Objects.nonNull(giftCard)) {
                    item.setGiftCardName(giftCard.getName());
                    item.setGiftCardParValue(giftCard.getParValue());
                }
            });
        }
    }

    /**
     * @description 填充交易人信息
     * @author malianfeng
     * @date 2022/12/16 15:02
     * @param giftCardBillList 交易记录列表
     * @return void
     */
    public void fillTradePersonInfo(List<GiftCardBillVO> giftCardBillList) {
        if (CollectionUtils.isNotEmpty(giftCardBillList)) {
            // 1.1 收集C端用户
            List<String> customerIds = giftCardBillList.stream()
                    .filter(item -> DefaultFlag.NO == item.getTradePersonType())
                    .map(GiftCardBillVO::getTradePerson)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            // 1.2 构造customerMap，[customerId] => [CustomerVO]
            Map<String, CustomerVO> customerMap = null;
            if (CollectionUtils.isNotEmpty(customerIds)) {
                CustomerListByConditionRequest customerListByConditionRequest = new CustomerListByConditionRequest();
                customerListByConditionRequest.setCustomerIds(customerIds);
                customerMap = Optional.ofNullable(customerQueryProvider.listCustomerByCondition(customerListByConditionRequest).getContext())
                        .map(CustomerListByConditionResponse::getCustomerVOList)
                        .orElse(Collections.emptyList())
                        .stream()
                        .collect(Collectors.toMap(CustomerVO::getCustomerId, Function.identity()));
            }

            // 2.1 收集B端用户
            List<String> employeeIds = giftCardBillList.stream()
                    .filter(item -> DefaultFlag.YES == item.getTradePersonType())
                    .map(GiftCardBillVO::getTradePerson)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            // 2.2 构造employeeMap，[employeeId] => [EmployeeListVO]
            Map<String, EmployeeListVO> employeeMap = null;
            if (CollectionUtils.isNotEmpty(employeeIds)) {
                EmployeeListRequest employeeListRequest = new EmployeeListRequest();
                employeeListRequest.setEmployeeIds(employeeIds);
                employeeMap = Optional.ofNullable(employeeQueryProvider.list(employeeListRequest).getContext())
                        .map(EmployeeListResponse::getEmployeeList)
                        .orElse(Collections.emptyList())
                        .stream()
                        .collect(Collectors.toMap(EmployeeListVO::getEmployeeId, Function.identity()));
            }

            // 3. 循环赋值
            for (GiftCardBillVO item : giftCardBillList) {
                String tradePerson = item.getTradePerson();
                String tradePersonName = null;
                String tradePersonAccount = null;
                if (DefaultFlag.NO == item.getTradePersonType() &&
                        Objects.nonNull(customerMap) && customerMap.containsKey(tradePerson)) {
                    // C端用户
                    CustomerVO customerVO = customerMap.get(tradePerson);
                    tradePersonAccount = customerVO.getCustomerAccount();
                    if (Objects.nonNull(customerVO.getCustomerDetail())) {
                        tradePersonName = customerVO.getCustomerDetail().getCustomerName();
                    }
                } else if (DefaultFlag.YES == item.getTradePersonType()
                        && Objects.nonNull(employeeMap) && employeeMap.containsKey(tradePerson)) {
                    // B端用户
                    EmployeeListVO employeeVO = employeeMap.get(tradePerson);
                    tradePersonAccount = employeeVO.getAccountName();
                    tradePersonName = employeeVO.getEmployeeName();
                }
                item.setTradePersonAccount(tradePersonAccount);
                item.setTradePersonName(tradePersonName);
            }
        }
    }
}
