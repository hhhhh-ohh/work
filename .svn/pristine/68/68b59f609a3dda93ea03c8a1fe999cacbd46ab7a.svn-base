package com.wanmi.sbc.marketing.provider.impl.appointmentsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateSimpleRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdRequest;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.*;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsQueryRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsale.*;
import com.wanmi.sbc.marketing.appointmentsale.model.root.AppointmentSale;
import com.wanmi.sbc.marketing.appointmentsale.model.root.AppointmentSaleDO;
import com.wanmi.sbc.marketing.appointmentsale.service.AppointmentSaleService;
import com.wanmi.sbc.marketing.appointmentsalegoods.model.root.AppointmentSaleGoods;
import com.wanmi.sbc.marketing.appointmentsalegoods.service.AppointmentSaleGoodsService;
import com.wanmi.sbc.marketing.bookingsale.model.root.BookingSale;
import com.wanmi.sbc.marketing.bookingsale.service.BookingSaleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预约抢购查询服务接口实现
 *
 * @author zxd
 * @date 2020-05-21 10:32:23
 */
@RestController
@Validated
public class AppointmentSaleQueryController implements AppointmentSaleQueryProvider {
    @Autowired private AppointmentSaleService appointmentSaleService;

    @Autowired private BookingSaleService bookingSaleService;

    @Autowired private AppointmentSaleGoodsService appointmentSaleGoodsService;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Autowired private GoodsQueryProvider goodsQueryProvider;

    @Autowired private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Override
    public BaseResponse<AppointmentSalePageResponse> page(
            @RequestBody @Valid AppointmentSalePageRequest appointmentSalePageReq) {
        AppointmentSaleQueryRequest queryReq =
                KsBeanUtil.convert(appointmentSalePageReq, AppointmentSaleQueryRequest.class);
        Page<AppointmentSale> appointmentSalePage = appointmentSaleService.page(queryReq);
        Page<AppointmentSaleVO> newPage =
                appointmentSalePage.map(entity -> appointmentSaleService.wrapperVo(entity));

        List<AppointmentSaleVO> appointmentSaleVOS = newPage.getContent();
        if (CollectionUtils.isEmpty(appointmentSaleVOS)) {
            MicroServicePage<AppointmentSaleVO> microPage =
                    new MicroServicePage<>(newPage, appointmentSalePageReq.getPageable());
            AppointmentSalePageResponse finalRes = new AppointmentSalePageResponse(microPage);
            return BaseResponse.success(finalRes);
        }
        Map<Long, List<AppointmentSaleGoods>> saleGoodsMap =
                appointmentSaleGoodsService
                        .list(
                                AppointmentSaleGoodsQueryRequest.builder()
                                        .appointmentSaleIdList(
                                                appointmentSaleVOS.stream()
                                                        .map(AppointmentSaleVO::getId)
                                                        .collect(Collectors.toList()))
                                        .build())
                        .stream()
                        .collect(Collectors.groupingBy(AppointmentSaleGoods::getAppointmentSaleId));
        appointmentSaleVOS.forEach(
                s -> {
                    if (saleGoodsMap.containsKey(s.getId())) {
                        s.setAppointmentCount(
                                saleGoodsMap.get(s.getId()).stream()
                                        .mapToInt(AppointmentSaleGoods::getAppointmentCount)
                                        .sum());
                        s.setBuyerCount(
                                saleGoodsMap.get(s.getId()).stream()
                                        .mapToInt(AppointmentSaleGoods::getBuyerCount)
                                        .sum());
                    }
                    s.buildStatus();
                });
        MicroServicePage<AppointmentSaleVO> microPage =
                new MicroServicePage<>(newPage, appointmentSalePageReq.getPageable());
        AppointmentSalePageResponse finalRes = new AppointmentSalePageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<AppointmentSaleListResponse> list(
            @RequestBody @Valid AppointmentSaleListRequest appointmentSaleListReq) {
        AppointmentSaleQueryRequest queryReq =
                KsBeanUtil.convert(appointmentSaleListReq, AppointmentSaleQueryRequest.class);
        List<AppointmentSale> appointmentSaleList = appointmentSaleService.list(queryReq);
        List<AppointmentSaleVO> newList =
                appointmentSaleList.stream()
                        .map(entity -> appointmentSaleService.wrapperVo(entity))
                        .collect(Collectors.toList());
        return BaseResponse.success(new AppointmentSaleListResponse(newList));
    }

    @Override
    public BaseResponse<AppointmentSaleByIdResponse> getById(
            @RequestBody @Valid AppointmentSaleByIdRequest appointmentSaleByIdRequest) {
        AppointmentSale appointmentSale =
                appointmentSaleService.getOne(
                        appointmentSaleByIdRequest.getId(),
                        appointmentSaleByIdRequest.getStoreId());
        return BaseResponse.success(
                new AppointmentSaleByIdResponse(appointmentSaleService.wrapperVo(appointmentSale)));
    }

    @Override
    public BaseResponse<AppointmentSaleIsInProcessResponse> isInProgress(
            @RequestBody @Valid AppointmentSaleIsInProgressRequest request) {
        AppointmentSale appointmentSale =
                appointmentSaleService.isInProcess(request.getGoodsInfoId(), request.getUserId());
        return BaseResponse.success(
                AppointmentSaleIsInProcessResponse.builder()
                        .appointmentSaleVO(appointmentSaleService.wrapperVo(appointmentSale))
                        .build());
    }

    @Override
    public BaseResponse<AppointmentSaleInProcessResponse>
            inProgressAppointmentSaleInfoByGoodsInfoIdList(
                    @RequestBody @Valid AppointmentSaleInProgressRequest request) {
        List<AppointmentSaleDO> appointmentSaleDOS =
                appointmentSaleService.inProgressAppointmentSaleInfoByGoodsInfoIdList(
                        request.getGoodsInfoIdList());

        return BaseResponse.success(
                AppointmentSaleInProcessResponse.builder()
                        .appointmentSaleVOList(
                                KsBeanUtil.convert(appointmentSaleDOS, AppointmentSaleVO.class))
                        .build());
    }

    @Override
    public BaseResponse<AppointmentSaleByIdResponse> getAppointmentSaleRelaInfo(
            @RequestBody @Valid RushToAppointmentSaleGoodsRequest request) {
        AppointmentSaleGoods appointmentSaleGoods =
                appointmentSaleGoodsService
                        .list(
                                AppointmentSaleGoodsQueryRequest.builder()
                                        .goodsInfoId(request.getSkuId())
                                        .appointmentSaleId(request.getAppointmentSaleId())
                                        .build())
                        .get(0);
        AppointmentSale appointmentSale =
                appointmentSaleService.getOne(
                        request.getAppointmentSaleId(), appointmentSaleGoods.getStoreId());
        AppointmentSaleVO appointmentSaleVO =
                KsBeanUtil.convert(appointmentSale, AppointmentSaleVO.class);

        GoodsInfoVO goodsInfo =
                goodsInfoQueryProvider
                        .getGoodsInfoById(
                                GoodsInfoListByIdRequest.builder()
                                        .goodsInfoId(request.getSkuId())
                                        .build())
                        .getContext()
                        .getGoodsInfoVO();
        GoodsVO goods =
                goodsQueryProvider
                        .getById(GoodsByIdRequest.builder().goodsId(goodsInfo.getGoodsId()).build())
                        .getContext();
        appointmentSaleVO.setAppointmentSaleGood(
                KsBeanUtil.convert(appointmentSaleGoods, AppointmentSaleGoodsVO.class));
        appointmentSaleVO.getAppointmentSaleGood().setGoodsInfoVO(goodsInfo);
        appointmentSaleVO.getAppointmentSaleGood().setGoodsVO(goods);
        appointmentSaleVO.setStock(goodsInfo.getStock());
        return BaseResponse.success(
                AppointmentSaleByIdResponse.builder().appointmentSaleVO(appointmentSaleVO).build());
    }

    @Override
    public BaseResponse<AppointmentSaleListResponse> getAppointmentSaleRelaInfoForImmediateBuy(
            @RequestBody @Valid AppointmentForImmediateBuyQueryRequest request) {
        Map<Long, List<AppointmentSaleGoods>> appointmentSaleGoodsMap = new HashMap<>();
        request.getAppointmentSaleIdMap()
                .forEach(
                        (k, v) -> {
                            List<AppointmentSaleGoods> appointmentSaleGoods =
                                    appointmentSaleGoodsService.list(
                                            AppointmentSaleGoodsQueryRequest.builder()
                                                    .goodsInfoIdList(v)
                                                    .appointmentSaleId(k)
                                                    .build());
                            appointmentSaleGoodsMap.put(k, appointmentSaleGoods);
                        });
        Set<Long> appointmentSaleIds = request.getAppointmentSaleIdMap().keySet();
        List<AppointmentSale> appointmentSales =
                appointmentSaleService.list(
                        AppointmentSaleQueryRequest.builder()
                                .idList(new ArrayList<>(appointmentSaleIds))
                                .build());
        if (appointmentSaleIds.size() != appointmentSales.size()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"部分活动不存在");
        }

        List<AppointmentSaleVO> appointmentSaleVOList = new ArrayList<>();
        appointmentSales.forEach(
                appointmentSale -> {
                    List<AppointmentSaleGoods> appointmentSaleGoodsList =
                            appointmentSaleGoodsMap.get(appointmentSale.getId());
                    if (CollectionUtils.isNotEmpty(appointmentSaleGoodsList)) {
                        if (!appointmentSaleGoodsList
                                .get(0)
                                .getStoreId()
                                .equals(appointmentSale.getStoreId())) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"");
                        }
                        AppointmentSaleVO appointmentSaleVO =
                                KsBeanUtil.convert(appointmentSale, AppointmentSaleVO.class);
                        appointmentSaleVO.setAppointmentSaleGoods(
                                KsBeanUtil.convert(
                                        appointmentSaleGoodsList, AppointmentSaleGoodsVO.class));
                        appointmentSaleVOList.add(appointmentSaleVO);
                    }
                });
        return BaseResponse.success(
                AppointmentSaleListResponse.builder()
                        .appointmentSaleVOList(appointmentSaleVOList)
                        .build());
    }

    @Override
    public BaseResponse<AppointmentSaleNotEndResponse> getNotEndActivity(
            @RequestBody @Valid AppointmentSaleByGoodsIdRequest request) {
        List<AppointmentSaleDO> appointmentSaleDOS =
                appointmentSaleService.getNotEndActivity(request.getGoodsId());

        return BaseResponse.success(
                AppointmentSaleNotEndResponse.builder()
                        .appointmentSaleVOList(
                                KsBeanUtil.convert(appointmentSaleDOS, AppointmentSaleVO.class))
                        .build());
    }

    @Override
    public BaseResponse containAppointmentSaleAndBookingSale(
            @Valid @RequestBody AppointmentSaleInProgressRequest request) {
        appointmentSaleService.validParticipateInAppointmentSale(request.getGoodsInfoIdList());
        bookingSaleService.validParticipateInBookingSale(request.getGoodsInfoIdList());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<AppointmentSaleMergeInProcessResponse>
            mergeVaildAppointmentSaleAndBookingSale(
                    @Valid AppointmentSaleMergeInProgressRequest request) {
        goodsRestrictedSaleQueryProvider.validateOrderRestricted(
                GoodsRestrictedBatchValidateSimpleRequest.builder()
                        .goodsRestrictedValidateVOS(request.getGoodsRestrictedValidateVOS())
                        .customerVO(request.getCustomerVO()).storeId(request.getStoreId())
                        .build());
        List<AppointmentSaleDO> appointmentSaleDOS =
                appointmentSaleService.inProgressAppointmentSaleInfoByGoodsInfoIdList(
                        request.getAppointSaleGoodsInfoIds());
        bookingSaleService.validateBookingQualification(
                request.getBookingSaleGoodsInfoIds(), request.getSkuIdAndBookSaleIdMap());
        appointmentSaleService.validParticipateInAppointmentSale(request.getNeedValidSkuIds());
        bookingSaleService.validParticipateInBookingSale(request.getNeedValidSkuIds());
        return BaseResponse.success(
                AppointmentSaleMergeInProcessResponse.builder()
                        .appointmentSaleVOList(
                                KsBeanUtil.convert(appointmentSaleDOS, AppointmentSaleVO.class))
                        .build());
    }

    @Override
    public BaseResponse<AppointmentSaleAndBookingSaleResponse> mergeAppointmentSaleAndBookingSale(
            @Valid AppointmentSaleAndBookingSaleRequest request) {
        AppointmentSaleAndBookingSaleResponse response =
                new AppointmentSaleAndBookingSaleResponse();
        List<AppointmentSaleDO> appointmentSaleDOS =
                appointmentSaleService.inProgressAppointmentSaleInfoByGoodsInfoIdList(
                        request.getGoodsInfoIdList());
        List<BookingSale> bookingSaleList =
                bookingSaleService.inProgressBookingSaleInfoByGoodsInfoIdList(
                        request.getGoodsInfoIdList());
        response.setAppointmentSaleVOList(
                KsBeanUtil.convert(appointmentSaleDOS, AppointmentSaleVO.class));
        response.setBookingSaleVOList(KsBeanUtil.convert(bookingSaleList, BookingSaleVO.class));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<AppointmentSalePageResponse> pageNew(
            @Valid AppointmentSalePageRequest appointmentSalePageReq) {
        AppointmentSaleQueryRequest queryReq =
                KsBeanUtil.convert(appointmentSalePageReq, AppointmentSaleQueryRequest.class);
        MicroServicePage<AppointmentSaleVO> newPage = appointmentSaleService.pageNew(queryReq);

        AppointmentSalePageResponse finalRes = new AppointmentSalePageResponse(newPage);
        return BaseResponse.success(finalRes);
    }
}
