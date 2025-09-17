package com.wanmi.sbc.marketing.provider.impl.appointmentsalegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.AppointmentStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsInfoSpecDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsInfoSpecDetailRelBySkuIdsRequest;
import com.wanmi.sbc.goods.api.response.spec.GoodsInfoSpecDetailRelBySkuIdsResponse;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsalegoods.AppointmentSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentGoodsInfoSimplePageRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleQueryRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.*;
import com.wanmi.sbc.marketing.api.response.appointmentsalegoods.*;
import com.wanmi.sbc.marketing.appointmentsale.model.root.AppointmentSale;
import com.wanmi.sbc.marketing.appointmentsale.service.AppointmentSaleService;
import com.wanmi.sbc.marketing.appointmentsalegoods.model.root.AppointmentSaleGoods;
import com.wanmi.sbc.marketing.appointmentsalegoods.service.AppointmentGoodsInfoSimpleCriterIaBuilder;
import com.wanmi.sbc.marketing.appointmentsalegoods.service.AppointmentSaleGoodsService;
import com.wanmi.sbc.marketing.bean.dto.AppointmentGoodsInfoSimplePageDTO;
import com.wanmi.sbc.marketing.bean.vo.AppointmentVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;

/**
 * 预约抢购查询服务接口实现
 *
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@RestController
@Validated
public class AppointmentSaleGoodsQueryController implements AppointmentSaleGoodsQueryProvider {
    @Autowired private AppointmentSaleGoodsService appointmentSaleGoodsService;

    @Autowired private AppointmentSaleService appointmentSaleService;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private GoodsQueryProvider goodsQueryProvider;

    @Autowired private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

    @Override
    public BaseResponse<AppointmentSaleGoodsPageResponse> page(
            @RequestBody @Valid AppointmentSaleGoodsPageRequest appointmentSaleGoodsPageReq) {
        AppointmentSaleGoodsQueryRequest queryReq =
                KsBeanUtil.convert(
                        appointmentSaleGoodsPageReq, AppointmentSaleGoodsQueryRequest.class);
        Page<AppointmentSaleGoods> appointmentSaleGoodsPage =
                appointmentSaleGoodsService.page(queryReq);
        Page<AppointmentSaleGoodsVO> newPage =
                appointmentSaleGoodsPage.map(
                        entity -> appointmentSaleGoodsService.wrapperVo(entity));
        MicroServicePage<AppointmentSaleGoodsVO> microPage =
                new MicroServicePage<>(newPage, appointmentSaleGoodsPageReq.getPageable());
        AppointmentSaleGoodsPageResponse finalRes = new AppointmentSaleGoodsPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<AppointmentSaleGoodsListResponse> list(
            @RequestBody @Valid AppointmentSaleGoodsListRequest appointmentSaleGoodsListReq) {
        AppointmentSaleGoodsQueryRequest queryReq =
                KsBeanUtil.convert(
                        appointmentSaleGoodsListReq, AppointmentSaleGoodsQueryRequest.class);
        List<AppointmentSaleGoods> appointmentSaleGoodsList =
                appointmentSaleGoodsService.list(queryReq);
        List<AppointmentSaleGoodsVO> newList =
                appointmentSaleGoodsList.stream()
                        .map(entity -> appointmentSaleGoodsService.wrapperVo(entity))
                        .collect(Collectors.toList());
        return BaseResponse.success(new AppointmentSaleGoodsListResponse(newList));
    }

    @Override
    public BaseResponse<AppointmentSaleGoodsByIdResponse> getById(
            @RequestBody @Valid AppointmentSaleGoodsByIdRequest appointmentSaleGoodsByIdRequest) {
        AppointmentSaleGoods appointmentSaleGoods =
                appointmentSaleGoodsService.getOne(
                        appointmentSaleGoodsByIdRequest.getId(),
                        appointmentSaleGoodsByIdRequest.getStoreId());
        return BaseResponse.success(
                new AppointmentSaleGoodsByIdResponse(
                        appointmentSaleGoodsService.wrapperVo(appointmentSaleGoods)));
    }

    @Override
    public BaseResponse<AppointmentResponse> pageBoss(
            @RequestBody @Valid AppointmentGoodsInfoSimplePageRequest request) {
        AppointmentGoodsInfoSimplePageDTO query =
                KsBeanUtil.convert(request, AppointmentGoodsInfoSimplePageDTO.class);
        query.setDelFlag(DeleteFlag.NO);
        Page<AppointmentSaleGoods> page = appointmentSaleGoodsService.build(query);
        if (CollectionUtils.isEmpty(page.getContent())) {
            return BaseResponse.success(AppointmentResponse.builder().build());
        }
        if (AppointmentStatus.RUNNING_SUSPENDED.equals(request.getQueryTab())){
            Page<AppointmentVO> newPage = page.map(g -> {
                AppointmentSaleGoodsVO appointmentSaleGoodsVO = KsBeanUtil.convert(g, AppointmentSaleGoodsVO.class);
                return AppointmentVO.builder().appointmentSaleGoods(appointmentSaleGoodsVO).build();
            });
            return BaseResponse.success(AppointmentResponse.builder().appointmentVOPage(new MicroServicePage<>(newPage,
                    page.getPageable())).build());
        }

        List<AppointmentSale> appointmentSales =
                appointmentSaleService.list(
                        AppointmentSaleQueryRequest.builder()
                                .idList(
                                        page.getContent().stream()
                                                .map(AppointmentSaleGoods::getAppointmentSaleId)
                                                .collect(Collectors.toList()))
                                .build());
        Map<Long, AppointmentSale> appointmentSaleMap =
                appointmentSales.stream()
                        .collect(Collectors.toMap(AppointmentSale::getId, Function.identity()));

        List<GoodsInfoVO> goodsInfoList =
                goodsInfoQueryProvider
                        .listByIds(
                                GoodsInfoListByIdsRequest.builder()
                                        .goodsInfoIds(
                                                page.getContent().stream()
                                                        .map(AppointmentSaleGoods::getGoodsInfoId)
                                                        .collect(Collectors.toList()))
                                        .build())
                        .getContext()
                        .getGoodsInfos();
        Map<String, GoodsInfoVO> goodsInfoMap =
                goodsInfoList.stream()
                        .collect(
                                Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        Page<AppointmentVO> newPage =
                page.map(
                        entity ->
                                appointmentSaleGoodsService.wrapperAppointmentVO(
                                        entity, appointmentSaleMap, goodsInfoMap));
        MicroServicePage<AppointmentVO> microPage =
                new MicroServicePage<>(newPage, page.getPageable());

        // 填充spu主图片
        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            Map<String, String> spuMap =
                    goodsQueryProvider
                            .listByCondition(
                                    GoodsByConditionRequest.builder()
                                            .goodsIds(
                                                    goodsInfoList.stream()
                                                            .map(GoodsInfoVO::getGoodsId)
                                                            .collect(Collectors.toList()))
                                            .build())
                            .getContext().getGoodsVOList().stream()
                            .filter(g -> StringUtils.isNotBlank(g.getGoodsImg()))
                            .collect(Collectors.toMap(GoodsVO::getGoodsId, GoodsVO::getGoodsImg));
            if (MapUtils.isNotEmpty(spuMap)) {
                microPage
                        .getContent()
                        .forEach(
                                s ->
                                        s.getAppointmentSaleGoods()
                                                .setGoodsImg(
                                                        spuMap.get(
                                                                s.getAppointmentSaleGoods()
                                                                        .getGoodsId())));
            }
        }

        GoodsInfoSpecDetailRelBySkuIdsResponse goodsInfoSpecDetailRelBySkuIdsResponse =
                goodsInfoSpecDetailRelQueryProvider
                        .listBySkuIds(
                                new GoodsInfoSpecDetailRelBySkuIdsRequest(
                                        microPage.getContent().stream()
                                                .map(
                                                        a ->
                                                                a.getAppointmentSaleGoods()
                                                                        .getGoodsInfoId())
                                                .collect(Collectors.toList())))
                        .getContext();
        if (goodsInfoSpecDetailRelBySkuIdsResponse != null
                && CollectionUtils.isNotEmpty(
                        goodsInfoSpecDetailRelBySkuIdsResponse.getGoodsInfoSpecDetailRelVOList())) {
            Map<String, String> specMap =
                    goodsInfoSpecDetailRelBySkuIdsResponse.getGoodsInfoSpecDetailRelVOList()
                            .stream()
                            .collect(
                                    Collectors.groupingBy(
                                            GoodsInfoSpecDetailRelVO::getGoodsInfoId,
                                            mapping(
                                                    GoodsInfoSpecDetailRelVO::getDetailName,
                                                    joining(" "))));
            if (MapUtils.isNotEmpty(specMap)) {
                microPage
                        .getContent()
                        .forEach(
                                s ->
                                        s.getAppointmentSaleGoods()
                                                .setSpecText(
                                                        specMap.containsKey(
                                                                        s.getAppointmentSaleGoods()
                                                                                .getGoodsInfoId())
                                                                ? specMap.get(
                                                                        s.getAppointmentSaleGoods()
                                                                                .getGoodsInfoId())
                                                                : ""));
            }
        }
        return BaseResponse.success(
                AppointmentResponse.builder().appointmentVOPage(microPage).build());
    }

    @Override
    public BaseResponse<AppointmentGoodsResponse> pageAppointmentGoodsInfo(
            @RequestBody @Valid AppointmentGoodsInfoSimplePageRequest request) {
        AppointmentGoodsInfoSimpleCriterIaBuilder query =
                KsBeanUtil.convert(request, AppointmentGoodsInfoSimpleCriterIaBuilder.class);
        Page<AppointmentSaleGoodsVO> appointmentSaleGoodsVOS =
                appointmentSaleGoodsService.pageAppointmentGoodsInfo(query);
        MicroServicePage<AppointmentSaleGoodsVO> microServicePage =
                KsBeanUtil.convertPage(appointmentSaleGoodsVOS, AppointmentSaleGoodsVO.class);

        // 填充sku图片
        if (CollectionUtils.isNotEmpty(microServicePage.getContent())) {
            Map<String, GoodsInfoVO> skuMap =
                    goodsInfoQueryProvider
                            .listByConditionAddGoods(
                                    GoodsInfoListByConditionRequest.builder()
                                            .goodsInfoIds(
                                                    microServicePage.getContent().stream()
                                                            .map(
                                                                    AppointmentSaleGoodsVO
                                                                            ::getGoodsInfoId)
                                                            .collect(Collectors.toList()))
                                            .build())
                            .getContext().getGoodsInfos().stream()
                            .collect(
                                    Collectors.toMap(
                                            GoodsInfoVO::getGoodsInfoId, Function.identity()));
            if (MapUtils.isNotEmpty(skuMap)) {
                microServicePage.getContent().stream()
                        .filter(s -> skuMap.containsKey(s.getGoodsInfoId()))
                        .forEach(
                                s -> {
                                    GoodsInfoVO sku = skuMap.get(s.getGoodsInfoId());
                                    s.setGoodsInfoImg(sku.getGoodsInfoImg());
                                    s.setGoodsName(sku.getGoodsInfoName());
                                    s.setGoodsImg(sku.getGoods().getGoodsImg());
                                    s.setMarketPrice(sku.getMarketPrice());
                                    s.setLinePrice(sku.getLinePrice());
                                });
            }
        }

        // 填充规格
        if (Boolean.TRUE.equals(request.getHavSpecTextFlag())
                && CollectionUtils.isNotEmpty(microServicePage.getContent())) {
            GoodsInfoSpecDetailRelBySkuIdsResponse goodsInfoSpecDetailRelBySkuIdsResponse =
                    goodsInfoSpecDetailRelQueryProvider
                            .listBySkuIds(
                                    new GoodsInfoSpecDetailRelBySkuIdsRequest(
                                            microServicePage.getContent().stream()
                                                    .map(AppointmentSaleGoodsVO::getGoodsInfoId)
                                                    .collect(Collectors.toList())))
                            .getContext();

            if (goodsInfoSpecDetailRelBySkuIdsResponse != null
                    && CollectionUtils.isNotEmpty(
                            goodsInfoSpecDetailRelBySkuIdsResponse
                                    .getGoodsInfoSpecDetailRelVOList())) {
                Map<String, String> specMap =
                        goodsInfoSpecDetailRelBySkuIdsResponse.getGoodsInfoSpecDetailRelVOList()
                                .stream()
                                .collect(
                                        Collectors.groupingBy(
                                                GoodsInfoSpecDetailRelVO::getGoodsInfoId,
                                                mapping(
                                                        GoodsInfoSpecDetailRelVO::getDetailName,
                                                        joining(" "))));
                if (MapUtils.isNotEmpty(specMap)) {
                    microServicePage
                            .getContent()
                            .forEach(s -> s.setSpecText(specMap.get(s.getGoodsInfoId())));
                }
            }
        }
        return BaseResponse.success(
                AppointmentGoodsResponse.builder().page(microServicePage).build());
    }

    @Override
    public BaseResponse validate(@RequestBody @Valid AppointmentSaleGoodsValidateRequest request) {
        appointmentSaleGoodsService.validate(request);
        return BaseResponse.SUCCESSFUL();
    }
}
