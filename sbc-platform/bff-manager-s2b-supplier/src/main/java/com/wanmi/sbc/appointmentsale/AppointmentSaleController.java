package com.wanmi.sbc.appointmentsale;

import com.wanmi.sbc.common.annotation.MultiSubmit;
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
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleProvider;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.appointmentsalegoods.AppointmentSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.*;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsListRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSaleAddResponse;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSaleModifyResponse;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSalePageResponse;
import com.wanmi.sbc.marketing.api.response.appointmentsalegoods.AppointmentSaleGoodsDetailResponse;
import com.wanmi.sbc.marketing.bean.dto.AppointmentSaleGoodsDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.request.MarketingMutexValidateRequest;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name =  "预约抢购管理API", description =  "AppointmentSaleController")
@RestController
@Validated
@RequestMapping(value = "/appointmentsale")
public class AppointmentSaleController {

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private AppointmentSaleProvider appointmentSaleProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;


    @Autowired
    private AppointmentSaleGoodsQueryProvider appointmentSaleGoodsQueryProvider;

    @Autowired
    private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired private MarketingBaseService marketingBaseService;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Operation(summary = "分页查询预约抢购")
    @PostMapping("/page")
    public BaseResponse<AppointmentSalePageResponse> getPage(@RequestBody @Valid AppointmentSalePageRequest request) {
        request.setDelFlag(DeleteFlag.NO);
        request.putSort("createTime", "desc");
        request.setStoreId(commonUtil.getStoreId());
        request.setStatus(request.getQueryTab());
        request.setPlatform(Platform.SUPPLIER);
        return appointmentSaleQueryProvider.page(request);
    }


    @Operation(summary = "根据id查询预约抢购商品详情")
    @Parameter(name = "id", description = "预约id", required = true)
    @GetMapping("/goods/{id}")
    public BaseResponse<AppointmentSaleGoodsDetailResponse> getAppointmentSaleGoodsDetail(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<AppointmentSaleGoodsVO> saleGoodsVOList = getAppointmentSaleGoodsInfo(id);

        return BaseResponse.success(AppointmentSaleGoodsDetailResponse.builder().appointmentSaleGoodsVOList(saleGoodsVOList).build());
    }


    @Operation(summary = "新增预约抢购活动")
    @PostMapping("/add")
    @MultiSubmit
    public BaseResponse<AppointmentSaleAddResponse> add(@RequestBody @Valid AppointmentSaleAddRequest request) {
        request.setDelFlag(DeleteFlag.NO);
        request.setCreatePerson(commonUtil.getOperatorId());
        request.setStoreId(commonUtil.getStoreId());
        List<String> skuIds = request.getAppointmentSaleGoods().stream().map(AppointmentSaleGoodsDTO::getGoodsInfoId).collect(Collectors.toList());
        validateWholeSaleGood(skuIds, request.getStoreId());
        //校验
        AppointmentSaleModifyRequest modifyRequest = new AppointmentSaleModifyRequest();
        KsBeanUtil.copyPropertiesThird(request, modifyRequest);
        validateAppointmentPermission(modifyRequest);

        List<GoodsVO> goodsVOList = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder().goodsIds(request.getAppointmentSaleGoods().stream().map(v -> v.getGoodsId()).collect(Collectors.toList())).build()).getContext().getGoodsVOList();
        Optional<GoodsVO> optional = goodsVOList.stream().filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType())).findFirst();
        if (optional.isPresent()) {
            throw  new SbcRuntimeException(MarketingErrorCodeEnum.K080023);
        }
        //全局互斥验证
        marketingBaseService.mutexValidateByAdd(request.getStoreId(), request.getAppointmentStartTime(), request.getSnapUpEndTime(), skuIds);
        return appointmentSaleProvider.add(request);
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


    @Operation(summary = "根据id查询预约抢购详情(编辑)")
    @Parameter(name = "id", description = "预约id", required = true)
    @GetMapping("/{id}")
    public BaseResponse<AppointmentSaleModifyResponse> getAppointmentSaleDetail(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AppointmentSaleVO appointmentSaleVO = appointmentSaleQueryProvider.getById(AppointmentSaleByIdRequest.builder().id(id).storeId(commonUtil.getStoreId()).build()).getContext().getAppointmentSaleVO();

        List<AppointmentSaleGoodsVO> saleGoodsVOList = getAppointmentSaleGoodsInfo(id);

        //填充marketingGoodsStatus属性
        if (CollectionUtils.isNotEmpty(saleGoodsVOList)){
            List<GoodsInfoVO> goodsInfoVOS = saleGoodsVOList.stream().map(AppointmentSaleGoodsVO::getGoodsInfoVO).collect(Collectors.toList());
            goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);
        }


        appointmentSaleVO.setAppointmentSaleGoods(saleGoodsVOList);
        return BaseResponse.success(AppointmentSaleModifyResponse.builder().appointmentSaleVO(appointmentSaleVO).build());
    }


    /**
     * 获取活动商品详情
     *
     * @param id
     * @return
     */
    private List<AppointmentSaleGoodsVO> getAppointmentSaleGoodsInfo(Long id) {
        Map<String, String> goodsInfoSpecDetailMap = new HashMap<>();

        List<AppointmentSaleGoodsVO> saleGoodsVOList = appointmentSaleGoodsQueryProvider.list(AppointmentSaleGoodsListRequest.builder().
                appointmentSaleId(id).storeId(commonUtil.getStoreId()).build()).getContext().getAppointmentSaleGoodsVOList();
        if (CollectionUtils.isEmpty(saleGoodsVOList)) {
            return saleGoodsVOList;
        }
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().
                goodsInfoIds(saleGoodsVOList.stream().map(AppointmentSaleGoodsVO::getGoodsInfoId).collect(Collectors.toList())).build()).getContext().getGoodsInfos();

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

        saleGoodsVOList.forEach(saleGood -> {
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
        return saleGoodsVOList;
    }


    @Operation(summary = "修改预约抢购活动信息")
    @PutMapping("/modify")
    public BaseResponse<AppointmentSaleModifyResponse> modify(@RequestBody @Valid AppointmentSaleModifyRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setStoreId(commonUtil.getStoreId());
        //校验时间
        AppointmentSaleVO appointmentSale = appointmentSaleQueryProvider.getById(AppointmentSaleByIdRequest
                .builder().storeId(commonUtil.getStoreId()).id(request.getId()).build()).getContext().getAppointmentSaleVO();
        if (Objects.isNull(appointmentSale)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (appointmentSale.getAppointmentStartTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080142);
        }

        List<String> skuIds = request.getAppointmentSaleGoods().stream().map(AppointmentSaleGoodsDTO::getGoodsInfoId).collect(Collectors.toList());
        validateWholeSaleGood(skuIds, request.getStoreId());
        //校验
        validateAppointmentPermission(request);

        MarketingMutexValidateRequest validateRequest = new MarketingMutexValidateRequest();
        validateRequest.setStoreId(request.getStoreId());
        validateRequest.setCrossBeginTime(request.getAppointmentStartTime());
        validateRequest.setCrossEndTime(request.getSnapUpEndTime());
        validateRequest.setSkuIds(skuIds);
        validateRequest.setNotSelfId(Objects.toString(request.getId()));
        validateRequest.setAppointmentIdFlag(Boolean.TRUE);
        marketingBaseService.mutexValidate(validateRequest);
        return appointmentSaleProvider.modify(request);
    }

    @Operation(summary = "根据id删除预约抢购")
    @Parameter(name = "id", description = "预约id", required = true)
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return appointmentSaleProvider.deleteById(AppointmentSaleDelByIdRequest.builder().id(id).storeId(commonUtil.getStoreId()).build());
    }

    @Operation(summary = "根据id暂停活动/开始活动")
    @Parameter(name = "id", description = "预约id", required = true)
    @PutMapping("/status/{id}")
    public BaseResponse deleteByIdList(@RequestBody @Valid AppointmentSaleStatusRequest request) {
        AppointmentSaleVO appointmentSaleVO = appointmentSaleQueryProvider.getById(AppointmentSaleByIdRequest.builder().storeId(commonUtil.getStoreId()).id(request.getId()).build()).getContext().getAppointmentSaleVO();
        if (Objects.isNull(appointmentSaleVO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (appointmentSaleVO.getPauseFlag().equals(request.getPauseFlag())) {
            return BaseResponse.SUCCESSFUL();
        }
        request.setStoreId(commonUtil.getStoreId());
        return appointmentSaleProvider.modifyStatus(request);
    }

    @Operation(summary = "关闭活动")
    @Parameter(name = "id", description = "预约id", required = true)
    @PutMapping("/close/{id}")
    public BaseResponse close(@PathVariable Long id) {
        return appointmentSaleProvider.close(AppointmentSaleCloseRequest.builder().id(id).storeId(commonUtil.getStoreId()).build());
    }

    /**
     * 活动满足条件校验
     *
     * @param request
     */
    private void validateAppointmentPermission(AppointmentSaleModifyRequest request) {
        if (CollectionUtils.isEmpty(request.getAppointmentSaleGoods())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        GoodsInfoListByIdsResponse goodsInfoListByIdsResponse =
                goodsInfoQueryProvider
                        .listByIds(
                                GoodsInfoListByIdsRequest.builder()
                                        .goodsInfoIds(
                                                request.getAppointmentSaleGoods().stream()
                                                        .map(AppointmentSaleGoodsDTO::getGoodsInfoId)
                                                        .collect(Collectors.toList()))
                                        .build())
                        .getContext();
        if (goodsInfoListByIdsResponse == null
                || CollectionUtils.isEmpty(goodsInfoListByIdsResponse.getGoodsInfos())
                || goodsInfoListByIdsResponse.getGoodsInfos().size()
                != request.getAppointmentSaleGoods().size()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<GoodsInfoVO> exceptionGoodsInfo =
                goodsInfoListByIdsResponse.getGoodsInfos().stream()
                        .filter(
                                g ->
                                        Objects.isNull(g.getStoreId())
                                                || !g.getStoreId().equals(request.getStoreId()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(exceptionGoodsInfo)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 预约类型：必选，单选，默认选中不预约不可购买
        if (Objects.isNull(request.getJoinLevelType())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        // 预约时间（开始时间/结束时间）：必选，精确到时，分/秒数位自动补齐为00，不点击选择时间时，起止时间自动取00:00:00；
        //     开始时间（的日期）不可早于当前时间（的日期），点击日期弹窗的确定时校验，如有错误，需高亮日期选择框并在选择框下方提示：开始时间不可早于当前时间
        //     结束时间不可早于开始时间
        //     抢购时间的开始时间不可早于预约时间的结束时间(可以等于)
        if (Objects.isNull(request.getAppointmentStartTime())
                || Objects.isNull(request.getAppointmentEndTime())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }

        if (request.getAppointmentStartTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080136);
        }
        if (request.getAppointmentStartTime().isAfter(request.getAppointmentEndTime())
                || request.getAppointmentStartTime().isEqual(request.getAppointmentEndTime())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080137);
        }

        // 抢购时间（开始时间/结束时间）：必选，精确到时，分/秒数位自动补齐为00，不点击选择时间时，起止时间自动取00:00:00；
        //     开始时间（的日期）不可早于当前时间（的日期），点击日期弹窗的确定时校验，如有错误，需高亮日期选择框并在选择框下方提示：开始时间不可早于当前时间
        //     结束时间不可早于开始时间
        //     抢购时间的开始时间不可早于预约时间的结束时间(可以等于)

        if (Objects.isNull(request.getSnapUpStartTime())
                || Objects.isNull(request.getSnapUpEndTime())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }

        if (request.getSnapUpStartTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080136);
        }
        if (request.getSnapUpStartTime().isAfter(request.getSnapUpEndTime())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080137);
        }
        if (request.getAppointmentEndTime().compareTo(request.getSnapUpStartTime()) > 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080138);
        }

        // 发货时间：必选，精确到天，发货时间不可早于抢购开始时间(可以等于)
        if (Objects.isNull(request.getDeliverTime())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        if (LocalDate.parse(
                        request.getDeliverTime(),
                        DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1))
                .compareTo(request.getSnapUpStartTime().toLocalDate())
                < 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080139);
        }

        // 预约价：非必填，仅限0-9999999.99间的数字，不填写时以市场价销售
        if (CollectionUtils.isNotEmpty(
                request.getAppointmentSaleGoods().stream()
                        .filter(
                                g ->
                                        Objects.nonNull(g.getPrice())
                                                && g.getPrice()
                                                .compareTo(
                                                        new BigDecimal(
                                                                "9999999.99"))
                                                > 0)
                        .collect(Collectors.toList()))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }
}
