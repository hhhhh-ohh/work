package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerSimplifyByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByCompanyIdRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerRelaListByConditionRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerSimplifyByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByCompanyIdResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.appointmentsalegoods.AppointmentSaleGoodsProvider;
import com.wanmi.sbc.marketing.api.provider.appointmentsalegoods.AppointmentSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleListRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsale.RushToAppointmentSaleGoodsRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsListRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.order.api.provider.appointmentrecord.AppointmentRecordQueryProvider;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordQueryRequest;
import com.wanmi.sbc.order.api.response.appointmentrecord.AppointmentRecordResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className AppointmentSaleGoodsService
 * @description 立刻预约商品mq异步处理同步预约数量
 * @date 2021/8/18 10:00 上午
 */
@Slf4j
@Service
public class AppointmentSaleGoodsService {

    @Autowired private RedissonClient redissonClient;

    @Autowired private AppointmentSaleGoodsProvider appointmentSaleGoodsProvider;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Autowired private EmployeeQueryProvider employeeQueryProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired private AppointmentRecordQueryProvider appointmentRecordQueryProvider;

    @Autowired private CustomerQueryProvider customerQueryProvider;

    @Autowired private AppointmentSaleGoodsQueryProvider appointmentSaleGoodsQueryProvider;

    @Autowired private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Bean
    public Consumer<Message<String>> mqAppointmentSaleGoodsService() {
        return this::extracted;
    }

    private void extracted(Message<String> message) {
        String json = message.getPayload();
        RushToAppointmentSaleGoodsRequest request =
                JSONObject.parseObject(
                        JSON.parse(json).toString(), RushToAppointmentSaleGoodsRequest.class);
        log.info("-----消费立即预约MQ-----，参数：{}", json);
        // 判断预约商品条件
        try {
            this.judgeAppointmentGoodsCondition(request, false);
            // （加锁，保证高并发下预约数量是正确的）
            RLock rLock =
                    redissonClient.getFairLock(
                            RedisKeyConstant.APPOINTMENT_SALE_GOODS_INFO_COUNT
                                    + request.getAppointmentSaleId()
                                    + ":"
                                    + request.getSkuId());
            // 修复sonar检测出的bug
            if (rLock.tryLock(0, 30, TimeUnit.SECONDS)) {
                try {
                    appointmentSaleGoodsProvider.updateAppointmentCount(request);
                } finally {
                    rLock.unlock();
                }
            }
        } catch (Exception e) {
            log.error("更新预约报错", e);
        }
    }

    /**
     * 校验预约资格
     *
     * @param request
     */
    public void judgeAppointmentGoodsCondition(
            RushToAppointmentSaleGoodsRequest request, boolean flag) {

        // 判断是否已经预约，如果预约了，不可重复预约
        // 判断活动是否存在
        // 判断活动是否正在进行中
        AppointmentRecordResponse response =
                appointmentRecordQueryProvider
                        .getAppointmentInfo(
                                AppointmentRecordQueryRequest.builder()
                                        .buyerId(request.getCustomerId())
                                        .goodsInfoId(request.getSkuId())
                                        .appointmentSaleId(request.getAppointmentSaleId())
                                        .build())
                        .getContext();

        if (flag
                && Objects.nonNull(response)
                && Objects.nonNull(response.getAppointmentRecordVO())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080125);
        }

        if (!flag
                && (Objects.isNull(response)
                        || Objects.isNull(response.getAppointmentRecordVO()))) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080126);
        }

        List<AppointmentSaleVO> appointmentSaleVOList =
                appointmentSaleQueryProvider
                        .list(
                                AppointmentSaleListRequest.builder()
                                        .id(request.getAppointmentSaleId())
                                        .build())
                        .getContext()
                        .getAppointmentSaleVOList();
        if (CollectionUtils.isEmpty(appointmentSaleVOList)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
        }
        AppointmentSaleVO appointmentSaleVO = appointmentSaleVOList.get(0);
        if (appointmentSaleVO.getPauseFlag() == 1) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080128);
        }

        if (appointmentSaleVO.getAppointmentStartTime().isAfter(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080129);
        }

        if (appointmentSaleVO.getAppointmentEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080130);
        }

        // 判断店铺是否关店或者禁用
        StoreByIdResponse storeByIdResponse =
                storeQueryProvider
                        .getById(
                                StoreByIdRequest.builder()
                                        .storeId(appointmentSaleVO.getStoreId())
                                        .build())
                        .getContext();
        EmployeeByCompanyIdResponse employeeByCompanyIdResponse =
                employeeQueryProvider
                        .getByCompanyId(
                                EmployeeByCompanyIdRequest.builder()
                                        .companyInfoId(
                                                storeByIdResponse
                                                        .getStoreVO()
                                                        .getCompanyInfo()
                                                        .getCompanyInfoId())
                                        .build())
                        .getContext();
        if (storeByIdResponse.getStoreVO().getStoreState() == StoreState.CLOSED
                || employeeByCompanyIdResponse.getAccountState() == AccountState.DISABLE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }

        List<AppointmentSaleGoodsVO> saleGoodsVOList =
                appointmentSaleGoodsQueryProvider
                        .list(
                                AppointmentSaleGoodsListRequest.builder()
                                        .appointmentSaleId(appointmentSaleVO.getId())
                                        .goodsInfoId(request.getSkuId())
                                        .build())
                        .getContext()
                        .getAppointmentSaleGoodsVOList();
        if (CollectionUtils.isEmpty(saleGoodsVOList)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080131);
        }
        StoreVO store =
                storeQueryProvider
                        .getNoDeleteStoreById(
                                NoDeleteStoreByIdRequest.builder()
                                        .storeId(appointmentSaleVO.getStoreId())
                                        .build())
                        .getContext()
                        .getStoreVO();

        // 判断活动是否是全平台客户还是店铺内客户
        if (!appointmentSaleVO.getJoinLevel().equals(Constants.STR_MINUS_1)) {
            if (BoolFlag.NO == store.getCompanyType()) {
                CustomerSimplifyByIdRequest simplifyByIdRequest = new CustomerSimplifyByIdRequest();
                simplifyByIdRequest.setCustomerId(request.getCustomerId());
                CustomerSimplifyByIdResponse simplifyByIdResponse =
                        customerQueryProvider.simplifyById(simplifyByIdRequest).getContext();
                Long customerLevelId = simplifyByIdResponse.getCustomerLevelId();
                if (!appointmentSaleVO.getJoinLevel().equals(Constants.STR_0)
                        && !Arrays.asList(appointmentSaleVO.getJoinLevel().split(","))
                                .contains(customerLevelId.toString())) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080133);
                }
            } else {
                StoreCustomerRelaListByConditionRequest listByConditionRequest =
                        new StoreCustomerRelaListByConditionRequest();
                listByConditionRequest.setCustomerId(request.getCustomerId());
                listByConditionRequest.setStoreId(appointmentSaleVO.getStoreId());
                List<StoreCustomerRelaVO> relaVOList =
                        storeCustomerQueryProvider
                                .listByCondition(listByConditionRequest)
                                .getContext()
                                .getRelaVOList();
                if (Objects.nonNull(relaVOList) && relaVOList.size() > 0) {
                    if (!appointmentSaleVO.getJoinLevel().equals(Constants.STR_0)
                            && !Arrays.asList(appointmentSaleVO.getJoinLevel().split(","))
                                    .contains(relaVOList.get(0).getStoreLevelId().toString())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080133);
                    }
                } else {
                    // 只限于店铺内会员
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080132);
                }
            }
        }
    }
}
