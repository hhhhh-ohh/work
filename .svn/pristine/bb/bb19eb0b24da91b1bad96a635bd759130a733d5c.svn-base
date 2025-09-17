package com.wanmi.sbc.follow;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.store.ListNoDeleteStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoFillGoodsStatusRequest;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.RestrictedVO;
import com.wanmi.sbc.goods.response.GoodsInfoListResponse;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.order.api.provider.follow.FollowProvider;
import com.wanmi.sbc.order.api.provider.follow.FollowQueryProvider;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseProvider;
import com.wanmi.sbc.order.api.request.follow.FollowCountRequest;
import com.wanmi.sbc.order.api.request.follow.FollowDeleteRequest;
import com.wanmi.sbc.order.api.request.follow.FollowListRequest;
import com.wanmi.sbc.order.api.request.follow.FollowSaveRequest;
import com.wanmi.sbc.order.api.request.follow.HaveInvalidGoodsRequest;
import com.wanmi.sbc.order.api.request.follow.InvalidGoodsDeleteRequest;
import com.wanmi.sbc.order.api.request.follow.IsFollowRequest;
import com.wanmi.sbc.order.api.response.follow.FollowListResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 商品收藏Controller
 * Created by daiyitian on 17/4/12.
 */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name = "GoodsFollowBaseController", description = "S2B web公用-商品收藏信息API")
public class GoodsFollowBaseController {

    @Autowired
    private FollowProvider followProvider;

    @Autowired
    private FollowQueryProvider followQueryProvider;

    @Autowired
    private PurchaseProvider purchaseProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private MarketingPluginProvider marketingPluginProvider;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Resource(name = "customerFollowListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest, EsGoodsInfoSimpleResponse> goodsListInterface;

    /**
     * 获取商品收藏列表
     * @param queryRequest 查询条件
     * @return 商品收藏分页
     */
    @Operation(summary = "获取商品收藏列表")
    @RequestMapping(value = "/goodsFollows", method = RequestMethod.POST)
    public BaseResponse<FollowListResponse> info(@RequestBody FollowListRequest queryRequest) {
        // 获取会员
        String customerId = commonUtil.getOperatorId();
        queryRequest.setCustomerId(customerId);
        // 按创建时间倒序
        queryRequest.putSort("followTime", SortType.DESC.toValue());
        queryRequest.putSort("followId", SortType.DESC.toValue());

        FollowListResponse  response = followQueryProvider.list(queryRequest).getContext();

        if(CollectionUtils.isNotEmpty(response.getGoodsInfos().getContent())) {
            List<GoodsInfoDTO> dtoList = KsBeanUtil.convertList(response.getGoodsInfos().getContent(), GoodsInfoDTO.class);

            // 设定SKU状态
            List<GoodsInfoVO> goodsInfoVOList = goodsInfoProvider.fillGoodsStatus(
                    GoodsInfoFillGoodsStatusRequest.builder().goodsInfos(dtoList).build()).getContext().getGoodsInfos();
            // 根据开关重新设置分销商品标识
            distributionService.checkDistributionSwitch(goodsInfoVOList);
            // 计算区间价
            GoodsIntervalPriceByCustomerIdResponse priceResponse =
                    goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoVOList, customerId);
            response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            goodsInfoVOList = priceResponse.getGoodsInfoVOList();
            // 计算营销价格
            MarketingPluginGoodsListFilterRequest filterRequest = new MarketingPluginGoodsListFilterRequest();
            filterRequest.setGoodsInfos(KsBeanUtil.convert(goodsInfoVOList, GoodsInfoDTO.class));
            filterRequest.setCustomerId(customerId);
            goodsInfoVOList = marketingPluginProvider.goodsListFilter(filterRequest).getContext().getGoodsInfoVOList();

            // 用户体验优化迭代六，sku列表不再回显当前商品购物车已加购数量
            // 填充购买数
//            PurchaseFillBuyCountRequest purchaseFillBuyCountRequest = new PurchaseFillBuyCountRequest();
//            purchaseFillBuyCountRequest.setCustomerId(customer.getCustomerId());
//            purchaseFillBuyCountRequest.setGoodsInfoList(goodsInfoVOList);
//            purchaseFillBuyCountRequest.setInviteeId(commonUtil.getPurchaseInviteeId());
//            PurchaseFillBuyCountResponse purchaseFillBuyCountResponse = purchaseProvider.fillBuyCount(purchaseFillBuyCountRequest).getContext();
//            goodsInfoVOList = purchaseFillBuyCountResponse.getGoodsInfoList();

            response.setGoodsInfos(new MicroServicePage<GoodsInfoVO>(goodsInfoVOList,  queryRequest.getPageRequest(),
                    response.getGoodsInfos().getTotalElements()));
            // 预约抢购中商品/预售中的商品
            List<String> goodInfoIdList = response.getGoodsInfos().getContent().stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodInfoIdList)) {
                response.setAppointmentSaleVOList(appointmentSaleQueryProvider.inProgressAppointmentSaleInfoByGoodsInfoIdList
                        (AppointmentSaleInProgressRequest.builder().goodsInfoIdList(goodInfoIdList).build()).getContext().getAppointmentSaleVOList());
                response.setBookingSaleVOList(bookingSaleQueryProvider.inProgressBookingSaleInfoByGoodsInfoIdList(
                        BookingSaleInProgressRequest.builder().goodsInfoIdList(goodInfoIdList).build()).getContext().getBookingSaleVOList());
                // 填充sku起售数量
                Map<String, RestrictedVO> skuStartSaleNumMap = goodsBaseService.getSkuStartSaleNumMap(goodInfoIdList);
                for (GoodsInfoVO goodsInfo : response.getGoodsInfos().getContent()) {
                    goodsInfo.setStartSaleNum(skuStartSaleNumMap.get(goodsInfo.getGoodsInfoId()).getRestrictedNum());
                }
            }

            BaseResponse<ListNoDeleteStoreByIdsResponse> storeByIds = storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest
                    .builder().storeIds(response.getGoodsInfos().getContent().stream().map(GoodsInfoVO::getStoreId).collect(Collectors.toList())).build());

            response.getGoodsInfos().getContent().forEach(goodsInfoVO -> {
                List<StoreVO> storeVOList = storeByIds.getContext().getStoreVOList();
                if (CollectionUtils.isNotEmpty(storeVOList)) {
                    Optional<StoreVO> optional = storeVOList.stream().filter(storeVO -> goodsInfoVO.getStoreId().equals(storeVO.getStoreId())).findFirst();
                    optional.ifPresent(storeVO -> goodsInfoVO.setStoreName(storeVO.getStoreName()));
                }
                response.getGoodses().stream()
                        .filter(goodsEs -> goodsEs.getGoodsId().equals(goodsInfoVO.getGoodsId())).findFirst()
                        .ifPresent(v->goodsInfoVO.setGoodsSubtitle(v.getGoodsSubtitle()));
            });
        }
        return BaseResponse.success(response);
    }

    /**
     * 新增商品收藏
     * @param request 数据
     * @return 结果
     */
    @Operation(summary = "新增商品收藏")
    @RequestMapping(value = "/goodsFollow", method = RequestMethod.POST)
    public BaseResponse add(@RequestBody FollowSaveRequest request) {
        if (StringUtils.isBlank(request.getGoodsInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCustomerId(commonUtil.getOperatorId());
        request.setTerminalSource(commonUtil.getTerminal());
        followProvider.save(request);
       //goodsCustomerFollowService.save(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 取消商品收藏
     * @param request 数据
     * @return 结果
     */
    @Operation(summary = "取消商品收藏")
    @RequestMapping(value = "/goodsFollow", method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody FollowDeleteRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCustomerId(commonUtil.getOperatorId());
        followProvider.delete(request);
       // goodsCustomerFollowService.delete(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除失效商品
     * @return 结果
     */
    @Operation(summary = "删除失效商品")
    @RequestMapping(value = "/goodsFollows", method = RequestMethod.DELETE)
    public BaseResponse delete() {
        followProvider.deleteInvalidGoods(InvalidGoodsDeleteRequest.builder().customerId(commonUtil.getOperatorId()).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 是否含有失效商品
     * @return 结果
     */
    @Operation(summary = "是否含有失效商品")
    @RequestMapping(value = "/hasInvalidGoods", method = RequestMethod.GET)
    public BaseResponse<Boolean> hasInvalid() {
        return BaseResponse.success(followQueryProvider.haveInvalidGoods(HaveInvalidGoodsRequest.builder().customerId(commonUtil.getOperatorId()).build()).getContext().getBoolValue());
    }

    /**
     * 批量验证是否是收藏商品
     * @return 结果，相应的SkuId就是已收藏的商品ID
     */
    @Operation(summary = "批量验证是否是收藏商品<List<String>,相应的SkuId就是已收藏的商品ID>")
    @RequestMapping(value = "/isGoodsFollow", method = RequestMethod.POST)
    public BaseResponse<List<String>> isGoodsFollow(@RequestBody IsFollowRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCustomerId(commonUtil.getOperatorId());
        return BaseResponse.success(followQueryProvider.isFollow(request).getContext().getValue());
    }

    /**
     * 获取商品收藏个数
     * @return 商品收藏个数
     */
    @Operation(summary = "获取商品收藏个数")
    @RequestMapping(value = "/goodsFollowNum", method = RequestMethod.GET)
    public BaseResponse<Long> count() {
        FollowCountRequest request = FollowCountRequest.builder()
                .customerId(commonUtil.getOperatorId())
                .build();
        return BaseResponse.success( followQueryProvider.count(request).getContext().getValue());
    }

    /**
     * 获取商品收藏列表
     *
     * @param queryRequest 查询条件
     * @return 商品收藏分页
     */
    @Operation(summary = "获取商品收藏列表")
    @RequestMapping(value = "/goodsFollows/new", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListResponse> list(@RequestBody FollowListRequest queryRequest) {

        EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        esGoodsInfoQueryRequest.setPageNum(queryRequest.getPageNum());
        esGoodsInfoQueryRequest.setPageSize(queryRequest.getPageSize());
        esGoodsInfoQueryRequest.setCustomerId(commonUtil.getOperatorId());
        return BaseResponse.success(
                goodsBaseService.skuListConvert(
                        goodsListInterface.getList(esGoodsInfoQueryRequest), null));
    }
}
