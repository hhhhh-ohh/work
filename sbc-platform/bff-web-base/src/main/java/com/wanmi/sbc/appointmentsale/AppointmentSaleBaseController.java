package com.wanmi.sbc.appointmentsale;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.appointmentsale.service.AppointmentSaleService;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.store.ListNoDeleteStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleIsInProgressRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsale.RushToAppointmentSaleGoodsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSaleIsInProcessResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.marketing.bean.vo.AppointmentRecordVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.mq.appointment.RushToAppointmentSaleGoodsMqService;
import com.wanmi.sbc.order.api.provider.appointmentrecord.AppointmentRecordQueryProvider;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordPageCriteriaRequest;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordPageRequest;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordQueryRequest;
import com.wanmi.sbc.order.api.response.appointmentrecord.AppointmentRecordPageCriteriaResponse;
import com.wanmi.sbc.order.api.response.appointmentrecord.AppointmentRecordResponse;
import com.wanmi.sbc.order.bean.dto.AppointmentQueryDTO;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Tag(name =  "预约抢购webAPI", description =  "AppointmentSaleBaseController")
@RestController
@Validated
@RequestMapping(value = "/appointmentsale")
public class AppointmentSaleBaseController {

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private AppointmentSaleService appointmentSaleService;

    @Autowired
    private AppointmentRecordQueryProvider appointmentRecordQueryProvider;

    @Autowired
    private RushToAppointmentSaleGoodsMqService rushToAppointmentSaleGoodsMqService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    /**
     * @param goodsInfoId
     * @Description: 商品是否正在预购活动中
     */
    @Operation(summary = "商品是否正在预购活动中")
    @GetMapping("/{goodsInfoId}/isInProgress")
    public BaseResponse<AppointmentSaleIsInProcessResponse> isInProgress(@PathVariable String goodsInfoId) {
        String customerId = commonUtil.getOperatorId();
        AppointmentSaleIsInProcessResponse response = new AppointmentSaleIsInProcessResponse();
        if (StringUtils.isNotBlank(customerId)) {
            AppointmentSaleIsInProgressRequest request = AppointmentSaleIsInProgressRequest.builder().goodsInfoId(goodsInfoId).build();
            request.setUserId(customerId);
            response = appointmentSaleQueryProvider.isInProgress(request).getContext();
        }
        return BaseResponse.success(response);
    }

    /**
     * @param goodsInfoId
     * @Description: 判断用户是否预约
     */
    @Operation(summary = "判断用户是否预约")
    @GetMapping("/{goodsInfoId}/isSubscriptionFlag")
    public BaseResponse<Boolean> isSubscriptionFlag(@PathVariable String goodsInfoId) {
        AppointmentSaleIsInProcessResponse response = appointmentSaleQueryProvider.isInProgress(AppointmentSaleIsInProgressRequest.builder().goodsInfoId(goodsInfoId).build()).getContext();

        if (Objects.isNull(response) || Objects.isNull(response.getAppointmentSaleVO())) {
            return BaseResponse.success(Boolean.FALSE);
        }
        AppointmentSaleVO appointmentSaleVO = response.getAppointmentSaleVO();
        AppointmentRecordResponse recordResponse = appointmentRecordQueryProvider.getAppointmentInfo(AppointmentRecordQueryRequest.builder().buyerId(commonUtil.getOperatorId())
                .goodsInfoId(appointmentSaleVO.getAppointmentSaleGood().getGoodsInfoId()).appointmentSaleId(appointmentSaleVO.getId()).build()).getContext();
        if (Objects.nonNull(recordResponse) && Objects.nonNull(recordResponse.getAppointmentRecordVO())) {
            return BaseResponse.success(Boolean.TRUE);
        } else {
            return BaseResponse.success(Boolean.FALSE);
        }
    }


    @Operation(summary = "立即预约")
    @PostMapping("/rushToAppointmentGoods")
    @MultiSubmit
    public BaseResponse rushToAppointmentGoods(@RequestBody @Valid RushToAppointmentSaleGoodsRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        //预约商品对应限制条件判断
        appointmentSaleService.judgeAppointmentGoodsCondition(request);
        //发送mq消息异步处理同步资源
        rushToAppointmentSaleGoodsMqService.rushToAppointmentSaleGoodsMq(JSONObject.toJSONString(request));
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "我的预约")
    @PostMapping("/appointmentSalePage")
    public BaseResponse<AppointmentRecordPageCriteriaResponse> appointmentSalePage(@RequestBody @Valid AppointmentRecordPageRequest request) {
        request.setBuyerId(commonUtil.getOperatorId());
        AppointmentQueryDTO appointmentQueryDTO = AppointmentQueryDTO.builder().buyerId(commonUtil.getOperatorId()).build();
        appointmentQueryDTO.setPageSize(request.getPageSize());
        appointmentQueryDTO.setPageNum(request.getPageNum());

        if(TerminalSource.PC.equals(commonUtil.getTerminal())){
            appointmentQueryDTO.setQueryRealGoods(Boolean.TRUE);
        }
        AppointmentRecordPageCriteriaResponse response = appointmentRecordQueryProvider.pageCriteria(AppointmentRecordPageCriteriaRequest.builder().appointmentQueryDTO(appointmentQueryDTO).build()).getContext();
        if (Objects.isNull(response) || Objects.isNull(response.getAppointmentRecordPage()) || CollectionUtils.isEmpty(response.getAppointmentRecordPage().getContent())) {
            return BaseResponse.success(response);
        }
        List<AppointmentRecordVO> appointmentRecordVOS = response.getAppointmentRecordPage().getContent();

        List<String> skuIds = new ArrayList<>(appointmentRecordVOS.stream().collect(Collectors.groupingBy(AppointmentRecordVO::getGoodsInfoId)).keySet());

        GoodsInfoViewByIdsResponse goodsInfoViewByIdsResponse = goodsInfoQueryProvider.listViewByIds(GoodsInfoViewByIdsRequest.builder().goodsInfoIds(skuIds).isHavSpecText(1).build()).getContext();
        Map<String, GoodsInfoVO> goodsInfoVOMap = goodsInfoViewByIdsResponse.getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity(), (v1, v2) -> v1));
        Map<String, GoodsVO> goodsVOMap = goodsInfoViewByIdsResponse.getGoodses().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity(), (v1, v2) -> v1));

        BaseResponse<ListNoDeleteStoreByIdsResponse> storeByIds = storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest
                .builder().storeIds(appointmentRecordVOS.stream().map(v->v.getSupplier().getStoreId()).collect(Collectors.toList())).build());

        appointmentRecordVOS.forEach(a -> {
            if (goodsInfoVOMap.containsKey(a.getGoodsInfoId())) {
                a.setGoodsInfo(goodsInfoVOMap.get(a.getGoodsInfoId()));
            }
            if (goodsVOMap.containsKey(a.getAppointmentSaleGoodsInfo().getGoodsId())) {
                a.setGoods(goodsVOMap.get(a.getAppointmentSaleGoodsInfo().getGoodsId()));
            }
            //获取店铺名称
            List<StoreVO> storeVOList = storeByIds.getContext().getStoreVOList();
            if (CollectionUtils.isNotEmpty(storeVOList)) {
                Optional<StoreVO> optional = storeVOList.stream().filter(storeVO -> a.getGoodsInfo().getStoreId().equals(storeVO.getStoreId())).findFirst();
                optional.ifPresent(storeVO -> a.getGoodsInfo().setStoreName(storeVO.getStoreName()));
            }
        });

        return BaseResponse.success(response);
    }


}
