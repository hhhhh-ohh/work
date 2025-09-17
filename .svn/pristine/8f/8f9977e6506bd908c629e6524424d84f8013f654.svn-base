package com.wanmi.sbc.appointmentsale.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
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
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.bean.dto.SupplierDTO;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleProvider;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.appointmentsalegoods.AppointmentSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleListRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsale.RushToAppointmentSaleGoodsRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsListRequest;
import com.wanmi.sbc.marketing.bean.dto.AppointmentSaleGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.AppointmentSaleInfoDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.mq.appointment.RushToAppointmentSaleGoodsMqService;
import com.wanmi.sbc.order.api.provider.appointmentrecord.AppointmentRecordProvider;
import com.wanmi.sbc.order.api.provider.appointmentrecord.AppointmentRecordQueryProvider;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordQueryRequest;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordRequest;
import com.wanmi.sbc.order.api.response.appointmentrecord.AppointmentRecordResponse;
import com.wanmi.sbc.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName AppointmentSaleService
 * @Description 预约活动service
 * @Author zxd
 **/
@Service
public class AppointmentSaleService {

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private AppointmentRecordQueryProvider appointmentRecordQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private AppointmentSaleGoodsQueryProvider appointmentSaleGoodsQueryProvider;

    @Autowired
    private AppointmentSaleProvider appointmentSaleProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private AppointmentSaleService appointmentSaleService;

    @Autowired
    private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired
    private AppointmentRecordProvider appointmentRecordProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private RushToAppointmentSaleGoodsMqService rushToAppointmentSaleGoodsMqService;


    /**
     * @param request
     */
    public void judgeAppointmentGoodsCondition(RushToAppointmentSaleGoodsRequest request) {
        judgeAppointmentGoodsCondition(request, true);
    }

    /**
     * 校验预约资格
     *
     * @param request
     */
    public void judgeAppointmentGoodsCondition(RushToAppointmentSaleGoodsRequest request, boolean flag) {

        // 判断是否已经预约，如果预约了，不可重复预约
        // 判断活动是否存在
        // 判断活动是否正在进行中
        AppointmentRecordResponse response = appointmentRecordQueryProvider.getAppointmentInfo(AppointmentRecordQueryRequest.builder().buyerId(request.getCustomerId())
                .goodsInfoId(request.getSkuId()).appointmentSaleId(request.getAppointmentSaleId()).build()).getContext();

        if (flag && Objects.nonNull(response) && Objects.nonNull(response.getAppointmentRecordVO())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080125);
        }

        if (!flag && (Objects.isNull(response) || Objects.isNull(response.getAppointmentRecordVO()))) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080126);
        }

        List<AppointmentSaleVO> appointmentSaleVOList = appointmentSaleQueryProvider.list(AppointmentSaleListRequest.builder().id(request.getAppointmentSaleId()).build()).getContext().getAppointmentSaleVOList();
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

        //判断店铺是否关店或者禁用
        StoreByIdResponse storeByIdResponse = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(appointmentSaleVO.getStoreId()).build()).getContext();
        EmployeeByCompanyIdResponse employeeByCompanyIdResponse = employeeQueryProvider.getByCompanyId(EmployeeByCompanyIdRequest.builder()
                .companyInfoId(storeByIdResponse.getStoreVO().getCompanyInfo().getCompanyInfoId()).build()).getContext();
        if (storeByIdResponse.getStoreVO().getStoreState() == StoreState.CLOSED || employeeByCompanyIdResponse.getAccountState() == AccountState.DISABLE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }

        List<AppointmentSaleGoodsVO> saleGoodsVOList = appointmentSaleGoodsQueryProvider.list(AppointmentSaleGoodsListRequest.builder().appointmentSaleId
                (appointmentSaleVO.getId()).goodsInfoId(request.getSkuId()).build()).getContext().getAppointmentSaleGoodsVOList();
        if (CollectionUtils.isEmpty(saleGoodsVOList)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080131);
        }
        StoreVO store = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder().storeId(appointmentSaleVO.getStoreId())
                .build())
                .getContext().getStoreVO();


        // 判断活动是否是全平台客户还是店铺内客户
        if (!appointmentSaleVO.getJoinLevel().equals(Constants.STR_MINUS_1)) {
            if (BoolFlag.NO == store.getCompanyType()){
                CustomerSimplifyByIdRequest simplifyByIdRequest = new CustomerSimplifyByIdRequest();
                simplifyByIdRequest.setCustomerId(request.getCustomerId());
                CustomerSimplifyByIdResponse simplifyByIdResponse = customerQueryProvider.simplifyById(simplifyByIdRequest).getContext();
                Long customerLevelId = simplifyByIdResponse.getCustomerLevelId();
                if (!appointmentSaleVO.getJoinLevel().equals(Constants.STR_0) && !Arrays.asList(appointmentSaleVO.getJoinLevel().split(",")).contains(customerLevelId.toString())) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080133);
                }
            }else{
                StoreCustomerRelaListByConditionRequest listByConditionRequest = new StoreCustomerRelaListByConditionRequest();
                listByConditionRequest.setCustomerId(request.getCustomerId());
                listByConditionRequest.setStoreId(appointmentSaleVO.getStoreId());
                List<StoreCustomerRelaVO> relaVOList = storeCustomerQueryProvider.listByCondition(listByConditionRequest).getContext().getRelaVOList();
                if (Objects.nonNull(relaVOList) && relaVOList.size() > 0) {
                    if (!appointmentSaleVO.getJoinLevel().equals(Constants.STR_0) && !Arrays.asList(appointmentSaleVO.getJoinLevel().split(",")).contains(relaVOList.get(0).getStoreLevelId().toString())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080133);
                    }
                } else {
                    //只限于店铺内会员
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080132);
                }
            }
        }

        if (flag) {
            //获取商品所属商家，店铺信息
            SupplierDTO supplier = SupplierDTO.builder()
                    .storeId(store.getStoreId())
                    .storeName(store.getStoreName())
                    .isSelf(store.getCompanyType() == BoolFlag.NO)
                    .supplierCode(store.getCompanyInfo().getCompanyCode())
                    .supplierId(store.getCompanyInfo().getCompanyInfoId())
                    .supplierName(store.getCompanyInfo().getSupplierName())
                    .freightTemplateType(store.getFreightTemplateType())
                    .build();

            GoodsInfoByIdResponse goodsInfoResponse = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(request.getSkuId()).build()).getContext();
            AppointmentSaleGoodsVO appointmentSaleGoodsVO = saleGoodsVOList.get(0);
            appointmentSaleGoodsVO.setSkuPic(goodsInfoResponse.getGoodsInfoImg());
            appointmentSaleGoodsVO.setSkuName(goodsInfoResponse.getGoodsInfoName());
            AppointmentSaleGoodsInfoDTO goodsInfoDTO = KsBeanUtil.convert(appointmentSaleGoodsVO, AppointmentSaleGoodsInfoDTO.class);
            goodsInfoDTO.setGoodsType(goodsInfoResponse.getGoodsType());
            appointmentRecordProvider.add(AppointmentRecordRequest.builder().buyerId(commonUtil.getOperatorId()).supplier(supplier).goodsInfoId(request.getSkuId())
                    .appointmentSaleId(appointmentSaleVO.getId()).appointmentSaleGoodsInfo(goodsInfoDTO)
                    .appointmentSaleInfo(KsBeanUtil.convert(appointmentSaleVO, AppointmentSaleInfoDTO.class)).build());
        }

    }


}
