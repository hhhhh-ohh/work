package com.wanmi.sbc.marketing.provider.impl.bookingsalegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.AppointmentStatus;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsInfoSpecDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsInfoSpecDetailRelBySkuIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByConditionResponse;
import com.wanmi.sbc.goods.api.response.spec.GoodsInfoSpecDetailRelBySkuIdsResponse;
import com.wanmi.sbc.goods.bean.vo.BookingSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingGoodsInfoSimplePageRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleQueryRequest;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.*;
import com.wanmi.sbc.marketing.api.response.bookingsalegoods.*;
import com.wanmi.sbc.marketing.bean.dto.BookingGoodsInfoSimplePageDTO;
import com.wanmi.sbc.marketing.bean.vo.BookingVO;
import com.wanmi.sbc.marketing.bookingsale.model.root.BookingSale;
import com.wanmi.sbc.marketing.bookingsale.service.BookingSaleService;
import com.wanmi.sbc.marketing.bookingsalegoods.model.root.BookingSaleGoods;
import com.wanmi.sbc.marketing.bookingsalegoods.service.BookingGoodsInfoSimpleCriterIaBuilder;
import com.wanmi.sbc.marketing.bookingsalegoods.service.BookingSaleGoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;

/**
 * 预售商品信息查询服务接口实现
 *
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@RestController
@Validated
public class BookingSaleGoodsQueryController implements BookingSaleGoodsQueryProvider {
    @Autowired private BookingSaleGoodsService bookingSaleGoodsService;

    @Autowired private BookingSaleService bookingSaleService;

    @Autowired private GoodsQueryProvider goodsQueryProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

    @Override
    public BaseResponse<BookingSaleGoodsPageResponse> page(
            @RequestBody @Valid BookingSaleGoodsPageRequest bookingSaleGoodsPageReq) {
        BookingSaleGoodsQueryRequest queryReq =
                KsBeanUtil.convert(bookingSaleGoodsPageReq, BookingSaleGoodsQueryRequest.class);
        Page<BookingSaleGoods> bookingSaleGoodsPage = bookingSaleGoodsService.page(queryReq);
        Page<BookingSaleGoodsVO> newPage =
                bookingSaleGoodsPage.map(entity -> bookingSaleGoodsService.wrapperVo(entity));
        MicroServicePage<BookingSaleGoodsVO> microPage =
                new MicroServicePage<>(newPage, bookingSaleGoodsPageReq.getPageable());
        BookingSaleGoodsPageResponse finalRes = new BookingSaleGoodsPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<BookingSaleGoodsListResponse> list(
            @RequestBody @Valid BookingSaleGoodsListRequest bookingSaleGoodsListReq) {
        BookingSaleGoodsQueryRequest queryReq =
                KsBeanUtil.convert(bookingSaleGoodsListReq, BookingSaleGoodsQueryRequest.class);
        List<BookingSaleGoods> bookingSaleGoodsList = bookingSaleGoodsService.list(queryReq);
        List<BookingSaleGoodsVO> newList =
                bookingSaleGoodsList.stream()
                        .map(entity -> bookingSaleGoodsService.wrapperVo(entity))
                        .collect(Collectors.toList());
        return BaseResponse.success(new BookingSaleGoodsListResponse(newList));
    }

    @Override
    public BaseResponse<BookingSaleGoodsByIdResponse> getById(
            @RequestBody @Valid BookingSaleGoodsByIdRequest bookingSaleGoodsByIdRequest) {
        BookingSaleGoods bookingSaleGoods =
                bookingSaleGoodsService.getOne(
                        bookingSaleGoodsByIdRequest.getId(),
                        bookingSaleGoodsByIdRequest.getStoreId());
        return BaseResponse.success(
                new BookingSaleGoodsByIdResponse(
                        bookingSaleGoodsService.wrapperVo(bookingSaleGoods)));
    }

    @Override
    public BaseResponse<BookingResponse> pageBoss(
            @RequestBody @Valid BookingGoodsInfoSimplePageRequest request) {
        BookingGoodsInfoSimplePageDTO query =
                KsBeanUtil.convert(request, BookingGoodsInfoSimplePageDTO.class);
        Page<BookingSaleGoods> page = bookingSaleGoodsService.build(query);
        if (CollectionUtils.isEmpty(page.getContent())) {
            return BaseResponse.success(BookingResponse.builder().build());
        }
        if (AppointmentStatus.RUNNING_SUSPENDED.equals(request.getQueryTab())){
            Page<BookingVO> newPage = page.map(g -> {
                BookingSaleGoodsVO bookingSaleGoodsVO = KsBeanUtil.convert(g, BookingSaleGoodsVO.class);
                return BookingVO.builder().bookingSaleGoods(bookingSaleGoodsVO).build();
            });

            return BaseResponse.success(BookingResponse.builder().bookingVOMicroServicePage(new MicroServicePage<>(newPage,
                    page.getPageable())).build());
        }
        List<BookingSale> bookingSales =
                bookingSaleService.list(
                        BookingSaleQueryRequest.builder()
                                .idList(
                                        page.getContent().stream()
                                                .map(BookingSaleGoods::getBookingSaleId)
                                                .collect(Collectors.toList()))
                                .build());
        Map<Long, BookingSale> bookingSaleMap =
                bookingSales.stream()
                        .collect(Collectors.toMap(BookingSale::getId, Function.identity()));

        List<GoodsInfoVO> goodsInfoList =
                goodsInfoQueryProvider
                        .listByIds(
                                GoodsInfoListByIdsRequest.builder()
                                        .goodsInfoIds(
                                                page.getContent().stream()
                                                        .map(BookingSaleGoods::getGoodsInfoId)
                                                        .collect(Collectors.toList()))
                                        .build())
                        .getContext()
                        .getGoodsInfos();
        Map<String, GoodsInfoVO> goodsInfoMap =
                goodsInfoList.stream()
                        .collect(
                                Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        Page<BookingVO> newPage =
                page.map(
                        entity ->
                                bookingSaleGoodsService.wrapperBookingVO(
                                        entity, bookingSaleMap, goodsInfoMap));
        MicroServicePage<BookingVO> microPage = new MicroServicePage<>(newPage, page.getPageable());

        // 填充spu主图片以及划线价
        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            Map<String, GoodsVO> spuMap =
                    goodsQueryProvider
                            .listByCondition(
                                    GoodsByConditionRequest.builder()
                                            .goodsIds(
                                                    goodsInfoList.stream()
                                                            .map(GoodsInfoVO::getGoodsId)
                                                            .collect(Collectors.toList()))
                                            .build())
                            .getContext().getGoodsVOList().stream()
                            .collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
            if (MapUtils.isNotEmpty(spuMap)) {
                microPage.getContent().stream()
                        .filter(s -> spuMap.containsKey(s.getBookingSaleGoods().getGoodsId()))
                        .forEach(
                                s -> {
                                    GoodsVO goods =
                                            spuMap.get(s.getBookingSaleGoods().getGoodsId());
                                    s.getBookingSaleGoods().setGoodsImg(goods.getGoodsImg());
                                    s.getBookingSaleGoods().setLinePrice(goods.getLinePrice());
                                });
            }
        }

        GoodsInfoSpecDetailRelBySkuIdsResponse goodsInfoSpecDetailRelBySkuIdsResponse =
                goodsInfoSpecDetailRelQueryProvider
                        .listBySkuIds(
                                new GoodsInfoSpecDetailRelBySkuIdsRequest(
                                        microPage.getContent().stream()
                                                .map(a -> a.getBookingSaleGoods().getGoodsInfoId())
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
                                        s.getBookingSaleGoods()
                                                .setSpecText(
                                                        specMap.containsKey(
                                                                        s.getBookingSaleGoods()
                                                                                .getGoodsInfoId())
                                                                ? specMap.get(
                                                                        s.getBookingSaleGoods()
                                                                                .getGoodsInfoId())
                                                                : ""));
            }
        }
        return BaseResponse.success(
                BookingResponse.builder().bookingVOMicroServicePage(microPage).build());
    }

    @Override
    public BaseResponse<BookingGoodsResponse> pageBookingGoodsInfo(
            @RequestBody @Valid BookingGoodsInfoSimplePageRequest request) {
        BookingGoodsInfoSimpleCriterIaBuilder query =
                KsBeanUtil.convert(request, BookingGoodsInfoSimpleCriterIaBuilder.class);
        Page<BookingSaleGoodsVO> bookingSaleGoodsVOS =
                bookingSaleGoodsService.pageBookingGoodsInfo(query);
        MicroServicePage<BookingSaleGoodsVO> microServicePage =
                KsBeanUtil.convertPage(bookingSaleGoodsVOS, BookingSaleGoodsVO.class);

        // 填充spu主图片以及划线价
        if (CollectionUtils.isNotEmpty(microServicePage.getContent())) {

            GoodsInfoListByConditionResponse goodsInfoListByConditionResponse =
                    goodsInfoQueryProvider
                            .listByConditionAddGoods(
                                    GoodsInfoListByConditionRequest.builder()
                                            .goodsInfoIds(
                                                    microServicePage.getContent().stream()
                                                            .map(BookingSaleGoodsVO::getGoodsInfoId)
                                                            .collect(Collectors.toList()))
                                            .build())
                            .getContext();
            if (goodsInfoListByConditionResponse != null
                    && CollectionUtils.isNotEmpty(
                            goodsInfoListByConditionResponse.getGoodsInfos())) {

                Map<String, GoodsInfoVO> skuMap =
                        goodsInfoListByConditionResponse.getGoodsInfos().stream()
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
        }

        // 填充规格
        if (Boolean.TRUE.equals(request.getHavSpecTextFlag())
                && CollectionUtils.isNotEmpty(microServicePage.getContent())) {

            GoodsInfoSpecDetailRelBySkuIdsResponse goodsInfoSpecDetailRelBySkuIdsResponse =
                    goodsInfoSpecDetailRelQueryProvider
                            .listBySkuIds(
                                    new GoodsInfoSpecDetailRelBySkuIdsRequest(
                                            microServicePage.getContent().stream()
                                                    .map(BookingSaleGoodsVO::getGoodsInfoId)
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
        // 填充服务器时间
        if (CollectionUtils.isNotEmpty(microServicePage.getContent())) {
            microServicePage.getContent().forEach(s -> s.setServerTime(LocalDateTime.now()));
        }
        return BaseResponse.success(BookingGoodsResponse.builder().page(microServicePage).build());
    }

    @Override
    public BaseResponse validate(@RequestBody @Valid BookingSaleGoodsValidateRequest request) {
        this.bookingSaleGoodsService.validate(request);
        return BaseResponse.SUCCESSFUL();
    }
}
