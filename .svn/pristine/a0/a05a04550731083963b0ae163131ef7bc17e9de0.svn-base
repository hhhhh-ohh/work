package com.wanmi.sbc.bookingsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsInfoSpecDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsInfoSpecDetailRelBySkuIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdsResponse;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.bookingsale.*;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.BookingSaleGoodsListRequest;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleAddResponse;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleByIdResponse;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleModifyResponse;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSalePageResponse;
import com.wanmi.sbc.marketing.api.response.bookingsalegoods.BookingSaleGoodsListResponse;
import com.wanmi.sbc.marketing.bean.dto.BookingSaleGoodsDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.request.MarketingMutexValidateRequest;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name =  "预售信息管理API", description =  "BookingSaleController")
@RestController
@Validated
@RequestMapping(value = "/booking/sale")
public class BookingSaleController {

    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired
    private BookingSaleGoodsQueryProvider bookingSaleGoodsQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private BookingSaleProvider bookingSaleProvider;

    @Autowired
    private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired private MarketingBaseService marketingBaseService;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;


    @Operation(summary = "分页查询预售信息")
    @PostMapping("/page")
    public BaseResponse<BookingSalePageResponse> getPage(@RequestBody @Valid BookingSalePageRequest request) {
        request.setDelFlag(DeleteFlag.NO);
        request.putSort("createTime", "desc");
        request.setStoreId(commonUtil.getStoreId());
        request.setPlatform(Platform.SUPPLIER);
        return bookingSaleQueryProvider.page(request);
    }


    @Operation(summary = "根据id查询预售详情信息(编辑)")
    @Parameter(name = "id", description = "预售id", required = true)
    @GetMapping("/{id}")
    public BaseResponse<BookingSaleByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BookingSaleVO bookingSaleVO = bookingSaleQueryProvider.getById(BookingSaleByIdRequest.builder().id(id).
                storeId(commonUtil.getStoreId()).build()).getContext().getBookingSaleVO();

        List<BookingSaleGoodsVO> bookingSaleGoodsList = getBookingSaleGoodsInfo(id);

        //填充marketingGoodsStatus属性
        if (CollectionUtils.isNotEmpty(bookingSaleGoodsList)){
            List<GoodsInfoVO> goodsInfoVOS = bookingSaleGoodsList.stream().map(BookingSaleGoodsVO::getGoodsInfoVO).collect(Collectors.toList());
            goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);
        }

        bookingSaleVO.setBookingSaleGoodsList(bookingSaleGoodsList);
        return BaseResponse.success(BookingSaleByIdResponse.builder().bookingSaleVO(bookingSaleVO).build());
    }

    @Operation(summary = "根据id查询预售详情信息")
    @Parameter(name = "id", description = "预售id", required = true)
    @GetMapping("/detail/{id}")
    public BaseResponse<BookingSaleGoodsListResponse> getBookingSaleDetail(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return BaseResponse.success(BookingSaleGoodsListResponse.builder().bookingSaleGoodsVOList(getBookingSaleGoodsInfo(id)).build());
    }


    /**
     * 获取活动商品详情
     *
     * @param id
     * @return
     */
    private List<BookingSaleGoodsVO> getBookingSaleGoodsInfo(Long id) {
        Map<String, String> goodsInfoSpecDetailMap = new HashMap<>();

        List<BookingSaleGoodsVO> bookingSaleGoodsVOList = bookingSaleGoodsQueryProvider.list(BookingSaleGoodsListRequest.builder().
                bookingSaleId(id).storeId(commonUtil.getStoreId()).build()).getContext().getBookingSaleGoodsVOList();
        if (CollectionUtils.isEmpty(bookingSaleGoodsVOList)) {
            return bookingSaleGoodsVOList;
        }
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().
                goodsInfoIds(bookingSaleGoodsVOList.stream().map(BookingSaleGoodsVO::getGoodsInfoId).collect(Collectors.toList())).build()).getContext().getGoodsInfos();

        Map<String, GoodsInfoVO> goodsInfoVOMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, m -> m));

        List<String> skuIds = goodsInfos.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());

        goodsInfoSpecDetailMap.putAll(goodsInfoSpecDetailRelQueryProvider.listBySkuIds(new GoodsInfoSpecDetailRelBySkuIdsRequest(skuIds))
                .getContext().getGoodsInfoSpecDetailRelVOList().stream()
                .filter(v -> StringUtils.isNotBlank(v.getDetailName()))
                .collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (a, b) -> a.concat(" ").concat(b))));


        Map<Long, GoodsBrandVO> goodsBrandVOMap = goodsBrandQueryProvider.listByIds(GoodsBrandByIdsRequest.builder().brandIds(goodsInfos.stream().
                map(GoodsInfoVO::getBrandId).collect(Collectors.toList())).build()).getContext().getGoodsBrandVOList()
                .stream().collect(Collectors.toMap(GoodsBrandVO::getBrandId, m -> m));


        Map<Long, GoodsCateVO> goodsCateVOMap = goodsCateQueryProvider.getByIds(new GoodsCateByIdsRequest(goodsInfos.stream().map(GoodsInfoVO::getCateId).
                collect(Collectors.toList()))).getContext().getGoodsCateVOList().stream().collect(Collectors.toMap(GoodsCateVO::getCateId, m -> m));

        bookingSaleGoodsVOList.forEach(saleGood -> {
            if (goodsInfoVOMap.containsKey(saleGood.getGoodsInfoId())) {
                GoodsInfoVO goodsInfoVO = goodsInfoVOMap.get(saleGood.getGoodsInfoId());
                if (Objects.nonNull(goodsInfoVO.getBrandId()) && goodsBrandVOMap.containsKey(goodsInfoVO.getBrandId())) {
                    goodsInfoVO.setBrandName(goodsBrandVOMap.get(goodsInfoVO.getBrandId()).getBrandName());
                }
                if (goodsCateVOMap.containsKey(goodsInfoVO.getCateId())) {
                    goodsInfoVO.setCateName(goodsCateVOMap.get(goodsInfoVO.getCateId()).getCateName());
                }
                //填充规格值
                goodsInfoVO.setSpecText(goodsInfoSpecDetailMap.get(goodsInfoVO.getGoodsInfoId()));
                saleGood.setGoodsInfoVO(goodsInfoVO);
            }
        });
        return bookingSaleGoodsVOList;
    }


    @Operation(summary = "新增预售信息")
    @PostMapping("/add")
    public BaseResponse<BookingSaleAddResponse> add(@RequestBody @Valid BookingSaleAddRequest request) {
        request.setDelFlag(DeleteFlag.NO);
        request.setCreatePerson(commonUtil.getOperatorId());
        request.setStoreId(commonUtil.getStoreId());
        List<String> skuIds = request.getBookingSaleGoodsList().stream().map(BookingSaleGoodsDTO::getGoodsInfoId).collect(Collectors.toList());
        validateWholeSaleGood(skuIds, request.getStoreId());
        List<GoodsVO> goodsVOList = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder().goodsIds(request.getBookingSaleGoodsList().stream().map(BookingSaleGoodsDTO::getGoodsId).collect(Collectors.toList())).build()).getContext().getGoodsVOList();
        Optional<GoodsVO> optional = goodsVOList.stream().filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType())).findFirst();
        if (optional.isPresent()) {
            throw  new SbcRuntimeException(MarketingErrorCodeEnum.K080024);
        }
        //条件校验
        BookingSaleModifyRequest params = new BookingSaleModifyRequest();
        KsBeanUtil.copyPropertiesThird(request, params);
        validateBookingPermission(params);
        request.setStartTime(params.getStartTime());
        request.setEndTime(params.getEndTime());
        //全局互斥验证
        marketingBaseService.mutexValidateByAdd(request.getStoreId(), request.getStartTime(), request.getEndTime(), skuIds);
        return bookingSaleProvider.add(request);
    }

    @Operation(summary = "修改预售信息")
    @PutMapping("/modify")
    public BaseResponse<BookingSaleModifyResponse> modify(@RequestBody @Valid BookingSaleModifyRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setStoreId(commonUtil.getStoreId());
        BookingSaleVO bookingSale = bookingSaleQueryProvider.getById(BookingSaleByIdRequest.builder().
                storeId(request.getStoreId()).id(request.getId()).build()).getContext().getBookingSaleVO();
        if (Objects.isNull(bookingSale)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (bookingSale.getBookingType().equals(NumberUtils.INTEGER_ZERO)
                && bookingSale.getBookingStartTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080142);
        }
        if (bookingSale.getBookingType().equals(NumberUtils.INTEGER_ONE)
                && bookingSale.getHandSelStartTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080142);
        }

        List<String> skuIds = request.getBookingSaleGoodsList().stream().map(BookingSaleGoodsDTO::getGoodsInfoId).collect(Collectors.toList());
        validateWholeSaleGood(skuIds, request.getStoreId());
        validateBookingPermission(request);
        //全局互斥验证
        MarketingMutexValidateRequest validateRequest = new MarketingMutexValidateRequest();
        validateRequest.setStoreId(request.getStoreId());
        validateRequest.setCrossBeginTime(request.getStartTime());
        validateRequest.setCrossEndTime(request.getEndTime());
        validateRequest.setSkuIds(skuIds);
        validateRequest.setNotSelfId(Objects.toString(request.getId()));
        validateRequest.setBookingIdFlag(Boolean.TRUE);
        marketingBaseService.mutexValidate(validateRequest);
        return bookingSaleProvider.modify(request);
    }

    @Operation(summary = "根据id删除预售信息")
    @Parameter(name = "id", description = "预售id", required = true)
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return bookingSaleProvider.deleteById(BookingSaleDelByIdRequest.builder().id(id).
                storeId(commonUtil.getStoreId()).build());
    }


    /**
     * 批发商品校验
     *
     * @param goodInfoIdList
     */
    private void validateWholeSaleGood(List<String> goodInfoIdList, Long storeId) {
        if (goodInfoIdList.size() > Constants.MARKETING_GOODS_SIZE_MAX) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080027,
                    new Object[]{Constants.MARKETING_GOODS_SIZE_MAX});
        }
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().goodsInfoIds(goodInfoIdList).build()).getContext().getGoodsInfos();
        if (CollectionUtils.isEmpty(goodsInfos)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (CollectionUtils.isNotEmpty(goodsInfos.stream().filter(m -> m.getSaleType() == SaleType.WHOLESALE.toValue()).collect(Collectors.toList()))) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080141);
        }
        if (CollectionUtils.isNotEmpty(goodsInfos.stream().filter(m -> Objects.isNull(m.getStoreId()) || !m.getStoreId().equals(storeId)).collect(Collectors.toList()))) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010111);
        }

    }


    @Operation(summary = "根据id暂停活动/开始活动")
    @Parameter(name = "id", description = "预售id", required = true)
    @DeleteMapping("/status/{id}")
    public BaseResponse deleteByIdList(@RequestBody @Valid BookingSaleStatusRequest request) {
        BookingSaleVO bookingSaleVO = bookingSaleQueryProvider.getById(BookingSaleByIdRequest.builder().
                storeId(commonUtil.getStoreId()).id(request.getId()).build()).getContext().getBookingSaleVO();
        if (Objects.isNull(bookingSaleVO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (bookingSaleVO.getPauseFlag().equals(request.getPauseFlag())) {
            return BaseResponse.SUCCESSFUL();
        }
        request.setStoreId(commonUtil.getStoreId());
        return bookingSaleProvider.modifyStatus(request);
    }

    @Operation(summary = "关闭活动")
    @Parameter(name = "id", description = "预售id", required = true)
    @PutMapping("/close/{id}")
    public BaseResponse close(@PathVariable Long id) {
        return bookingSaleProvider.close(BookingSaleCloseRequest.builder().id(id).storeId(commonUtil.getStoreId()).build());
    }

    /**
     * 活动满足条件校验
     *
     * @param sale
     */
    private void validateBookingPermission(BookingSaleModifyRequest sale) {
        if (CollectionUtils.isEmpty(sale.getBookingSaleGoodsList())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        GoodsInfoListByIdsResponse goodsInfoListByIdsResponse =
                goodsInfoQueryProvider
                        .listByIds(
                                GoodsInfoListByIdsRequest.builder()
                                        .goodsInfoIds(
                                                sale.getBookingSaleGoodsList().stream()
                                                        .map(BookingSaleGoodsDTO::getGoodsInfoId)
                                                        .collect(Collectors.toList()))
                                        .build())
                        .getContext();
        if (goodsInfoListByIdsResponse == null
                || CollectionUtils.isEmpty(goodsInfoListByIdsResponse.getGoodsInfos())
                || goodsInfoListByIdsResponse.getGoodsInfos().size()
                != sale.getBookingSaleGoodsList().size()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<GoodsInfoVO> exceptionGoodsInfo =
                goodsInfoListByIdsResponse.getGoodsInfos().stream()
                        .filter(
                                g ->
                                        Objects.isNull(g.getStoreId())
                                                || !g.getStoreId().equals(sale.getStoreId()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(exceptionGoodsInfo)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 预约类型：必选，单选，默认选中不预约不可购买
        if (Objects.isNull(sale.getJoinLevelType())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        // 预定金支付时间（开始时间/结束时间）：必选，精确到时，分/秒数位自动补齐为00，不点击选择时间时，起止时间自动取00:00:00；
        // 结束时间不可早于开始时间
        //     尾款支付的开始时间不可早于定金支付的结束时间(可以等于)
        // 尾款支付时间（开始时间/结束时间）：必选，精确到时，分/秒数位自动补齐为00，不点击选择时间时，起止时间自动取00:00:00；
        //     开始时间（的日期）不可早于当前时间（的日期），点击日期弹窗的确定时校验，如有错误，需高亮日期选择框并在选择框下方提示：开始时间不可早于当前时间
        //     结束时间不可早于开始时间
        //     尾款支付的开始时间不可早于定金支付的结束时间(可以等于)
        // 发货时间：必选，精确到天，发货时间不可早于尾款支付开始时间(可以等于)
        if (sale.getBookingType().equals(NumberUtils.INTEGER_ONE)) {
            if ((Objects.isNull(sale.getHandSelStartTime())
                    || Objects.isNull(sale.getHandSelEndTime()))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            if (sale.getHandSelStartTime().isBefore(LocalDateTime.now())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080136);
            }
            if (sale.getHandSelStartTime().isAfter(sale.getHandSelEndTime())
                    || sale.getHandSelStartTime().isEqual(sale.getHandSelEndTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080137);
            }

            if (Objects.isNull(sale.getTailStartTime()) || Objects.isNull(sale.getTailEndTime())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            if (sale.getTailStartTime().isBefore(LocalDateTime.now())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080136);
            }
            if (sale.getTailStartTime().isAfter(sale.getTailEndTime())
                    || sale.getTailStartTime().isEqual(sale.getTailEndTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080137);
            }
            if (sale.getHandSelEndTime().compareTo(sale.getTailStartTime()) > 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080144);
            }
            // 发货时间：必选，精确到天，发货时间不可早于抢购开始时间(可以等于)
            if (Objects.isNull(sale.getDeliverTime())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            // 发货时间：必选，精确到天，发货时间不可早于尾款支付开始时间(可以等于)
            if (LocalDateTime.parse(
                            sale.getDeliverTime() + " 23:59:59",
                            DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_1))
                    .compareTo(sale.getTailStartTime())
                    < 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080145);
            }
            sale.setStartTime(sale.getHandSelStartTime());
            sale.setEndTime(sale.getTailEndTime());
        }
        if (sale.getBookingType().equals(NumberUtils.INTEGER_ZERO)) {
            if (Objects.isNull(sale.getBookingStartTime())
                    || Objects.isNull(sale.getBookingEndTime())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            if (sale.getBookingStartTime().isBefore(LocalDateTime.now())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080136);
            }
            if (sale.getBookingStartTime().isAfter(sale.getBookingEndTime())
                    || sale.getBookingStartTime().isEqual(sale.getBookingEndTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080137);
            }
            if (Objects.isNull(sale.getDeliverTime())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            // 发货时间精确到天，可以等于全款预售开始时间
            if (LocalDateTime.parse(
                            sale.getDeliverTime() + " 23:59:59",
                            DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_1))
                    .compareTo(sale.getBookingStartTime())
                    < 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080149);
            }
            sale.setStartTime(sale.getBookingStartTime());
            sale.setEndTime(sale.getBookingEndTime());
        }

        // 定金：必填，默认填充市场价，支持修改，仅限0-9999999.99间的数字，定金膨胀金额必须大于定金
        // 定金膨胀：非必填，默认填充市场价，支持修改，仅限0-9999999.99间的数字，定金膨胀金额必须大于定金
        // 预售数量：非必填，仅限1-9999999间的数字
        // 膨胀金须小于商品单价
        sale.getBookingSaleGoodsList()
                .forEach(
                        s -> {
                            if (sale.getBookingType().equals(NumberUtils.INTEGER_ONE)
                                    && Objects.isNull(s.getHandSelPrice())) {
                                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080147);
                            }
                            if (sale.getBookingType().equals(NumberUtils.INTEGER_ONE)
                                    && s.getHandSelPrice().compareTo(new BigDecimal("9999999.99"))
                                    > 0) {
                                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                            }
                            if (sale.getBookingType().equals(NumberUtils.INTEGER_ONE)
                                    && Objects.nonNull(s.getInflationPrice())
                                    && s.getInflationPrice().compareTo(s.getHandSelPrice()) <= 0) {
                                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080146);
                            }
                            if (sale.getBookingType().equals(NumberUtils.INTEGER_ONE)
                                    && Objects.nonNull(s.getInflationPrice())
                                    && s.getInflationPrice().compareTo(new BigDecimal("9999999.99"))
                                    > 0) {
                                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                            }
                        });
    }
}
