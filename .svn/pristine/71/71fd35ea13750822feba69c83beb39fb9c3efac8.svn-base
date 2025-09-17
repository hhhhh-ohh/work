package com.wanmi.sbc.flashsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.bean.vo.StoreSimpleInfo;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.flashsale.request.OriginalPriceModifyRequest;
import com.wanmi.sbc.flashsale.request.PosterModifyRequest;
import com.wanmi.sbc.goods.api.provider.flashpromotionactivity.FlashPromotionActivityQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashPromotionActivityPageRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsByActivityIdRequest;
import com.wanmi.sbc.goods.api.response.flashsaleactivity.FlashPromotionActivityPageResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsByActivityIdResponse;
import com.wanmi.sbc.goods.bean.vo.FlashPromotionActivityVO;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.setting.api.provider.AuditProvider;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.ConfigListModifyRequest;
import com.wanmi.sbc.setting.api.request.ConfigModifyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 黄昭
 * @className FlashSaleController
 * @description 限时抢购商品
 * @date 2022/2/10 14:51
 **/
@RestController
@Validated
@RequestMapping("/flashSale")
public class FlashSaleController {

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private AuditProvider auditProvider;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private FlashPromotionActivityQueryProvider flashPromotionActivityQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    /**
     * 海报设置列表
     * @return
     */
    @Operation(summary = "海报设置列表")
    @GetMapping("/poster/list")
    public BaseResponse<ConfigVO> posterList(){
        return auditQueryProvider.posterList();
    }

    /**
     * 查询限时抢购库存抢完后是否允许原价购买
     */
    @Operation(summary = "查询限时抢购库存抢完后是否允许原价购买")
    @GetMapping("/promotion/original/price/list")
    public BaseResponse<ConfigVO> flashPromotionOriginalPrice () {

        return auditQueryProvider.isAllowFlashPromotionOriginalPrice();
    }

    /**
     * 查询秒杀库存抢完后是否允许原价购买
     */
    @Operation(summary = "查询限时抢购库存抢完后是否允许原价购买")
    @GetMapping("/original/price/list")
    public BaseResponse<ConfigVO> flashSaleOriginalPrice () {

        return auditQueryProvider.isAllowFlashSaleOriginalPrice();
    }

    /**
     * 修改限时抢购活动设置
     */
    @Operation(summary = "修改限时抢购活动设置")
    @RequestMapping(value = "/promotion/original/price/modify", method = RequestMethod.POST)
    public BaseResponse modifyFlashrPomotionOriginalPrice (@RequestBody @Valid OriginalPriceModifyRequest request) {
        // 1. 组装限时抢购库存抢完之后是否允许原价购买设置
        ConfigVO userSetting = new ConfigVO();
        userSetting.setConfigKey(ConfigKey.FLASH_GOODS_SALE.toValue());
        userSetting.setConfigType(ConfigType.FLASH_PROMOTION_ORIGINAL_PRICE.toValue());
        userSetting.setStatus(request.getOriginalPriceStatus().toValue());
        // 2. 组装配置列表
        List<ConfigVO> configRequestList = new ArrayList<>();
        configRequestList.add(userSetting);
        auditProvider.modifyConfigList(new ConfigListModifyRequest(configRequestList));

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改限时抢购活动设置
     */
    @Operation(summary = "修改限时抢购活动设置")
    @RequestMapping(value = "/original/price/modify", method = RequestMethod.POST)
    public BaseResponse modifyFlashSaleOriginalPrice (@RequestBody @Valid OriginalPriceModifyRequest request) {
        // 1. 组装限时抢购库存抢完之后是否允许原价购买设置
        ConfigVO userSetting = new ConfigVO();
        userSetting.setConfigKey(ConfigKey.FLASH_GOODS_SALE.toValue());
        userSetting.setConfigType(ConfigType.FLASH_SALE_ORIGINAL_PRICE.toValue());
        userSetting.setStatus(request.getOriginalPriceStatus().toValue());
        // 2. 组装配置列表
        List<ConfigVO> configRequestList = new ArrayList<>();
        configRequestList.add(userSetting);
        auditProvider.modifyConfigList(new ConfigListModifyRequest(configRequestList));

        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 修改海报设置
     * @param request
     * @return
     */
    @Operation(summary = "修改海报设置")
    @PostMapping("/poster/edit")
    public BaseResponse posterEdit(@Valid @RequestBody PosterModifyRequest request){
        ConfigVO vo = new ConfigVO();
        vo.setConfigKey(ConfigKey.FLASH_GOODS_SALE.toString());
        vo.setConfigType(ConfigType.FLASH_GOODS_SALE_POSTER.toValue());
        vo.setStatus(Constants.ONE);
        vo.setContext(request.getContext());
        ConfigListModifyRequest configListModifyRequest = new ConfigListModifyRequest();
        configListModifyRequest.setConfigRequestList(Collections.singletonList(vo));
        auditProvider.modifyConfigList(configListModifyRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据id查询抢购商品表
     * @param activityId
     * @return
     */
    @Operation(summary = "根据id查询抢购商品表")
    @Parameter(name = "activityId", description = "抢购活动id", required = true)
    @GetMapping("/flashPromotion/{activityId}")
    public BaseResponse<FlashSaleGoodsByActivityIdResponse> getByActivityId(@PathVariable String activityId) {
        if (activityId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        FlashSaleGoodsByActivityIdRequest idReq = new FlashSaleGoodsByActivityIdRequest();
        idReq.setActivityId(activityId);
        BaseResponse<FlashSaleGoodsByActivityIdResponse> activityResponse = flashSaleGoodsQueryProvider.getByActivityId(idReq);

        //越权校验
        List<FlashSaleGoodsVO> flashSaleGoodsVOList = activityResponse.getContext().getFlashSaleGoodsVOs();

        if(CollectionUtils.isNotEmpty(flashSaleGoodsVOList)){
            commonUtil.checkStoreId(flashSaleGoodsVOList.get(0).getStoreId());

            //填充供应商名称
            List<GoodsInfoVO> goodsInfoVOS = flashSaleGoodsVOList.stream()
                    .map(FlashSaleGoodsVO::getGoodsInfo)
                    .collect(Collectors.toList());
            storeBaseService.populateProviderName(goodsInfoVOS);

            goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);

            goodsBaseService.populateSkuSupplyPrice(goodsInfoVOS);
        }

        return activityResponse;
    }

    /**
     * 分页查询抢购商品表
     * @param pageReq
     * @return
     */
    @Operation(summary = "分页查询抢购商品表")
    @PostMapping("/page")
    public BaseResponse<FlashPromotionActivityPageResponse> getPage(@RequestBody @Valid FlashPromotionActivityPageRequest pageReq) {
        if (Objects.nonNull(commonUtil.getStoreId())) {
            pageReq.setStoreId(commonUtil.getStoreId());
        }else if(StringUtils.isNotBlank(pageReq.getStoreName())) {
            //模糊查询店铺名称
            List<StoreSimpleInfo> storeList = storeQueryProvider.listByStoreName(ListStoreByNameRequest.builder()
                    .storeName(pageReq.getStoreName()).build()).getContext().getStoreSimpleInfos();
            if(CollectionUtils.isEmpty(storeList)){
                return BaseResponse.success(new FlashPromotionActivityPageResponse(new MicroServicePage<>(Collections.emptyList(), pageReq.getPageable(), 0)));
            }
            pageReq.setStoreIds(storeList.stream().map(StoreSimpleInfo::getStoreId).collect(Collectors.toList()));
        }
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("createTime", "desc");
        FlashPromotionActivityPageResponse response =
                flashPromotionActivityQueryProvider.page(pageReq).getContext();
        //填充平台端店铺
        if(CollectionUtils.isNotEmpty(response.getFlashPromotionActivityVOS().getContent())){
            List<Long> storeIds = response.getFlashPromotionActivityVOS().getContent().stream()
                    .map(FlashPromotionActivityVO::getStoreId).distinct().collect(Collectors.toList());
            Map<Long, String> storeVOMap = new HashMap<>(getStoreMap(storeIds));
            response.getFlashPromotionActivityVOS().getContent().forEach(flashSaleGoodsVO -> {
                flashSaleGoodsVO.setStoreName(storeVOMap.get(flashSaleGoodsVO.getStoreId()));
            });
        }
        return BaseResponse.success(response);
    }

    /**
     * 查询限时抢购超时未支付取消订单设置
     * @param
     * @return
     */
    @Operation(summary = "查询限时抢购超时未支付取消订单设置")
    @GetMapping("/getFlashPromotionOrderAutoCancel")
    public BaseResponse<SystemConfigTypeResponse> getFlashPromotionOrderAutoCancel(){
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.FLASH_PROMOTION_ORDER_AUTO_CANCEL.toValue());
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest);
    }

    /**
     * 更新限时抢购超时未支付取消订单设置
     * @param
     * @return
     */
    @Operation(summary = "更新限时抢购超时未支付取消订单设置")
    @PutMapping("/updateFlashPromotionOrderAutoCancel")
    public BaseResponse updateFlashPromotionOrderAutoCancel(@RequestBody ConfigModifyRequest configModifyRequest){
        List<ConfigModifyRequest> configModifyRequests = new ArrayList<>();
        configModifyRequest.setStatus(DeleteFlag.YES.toValue());
        configModifyRequests.add(configModifyRequest);
        if(StringUtils.isNotBlank(configModifyRequest.getContext())){
            if(Integer.valueOf(configModifyRequest.getContext())<1 || Integer.valueOf(configModifyRequest.getContext())>999){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        return systemConfigSaveProvider.update(configModifyRequests);
    }

    private Map<Long, String> getStoreMap(List<Long> storeIds) {
        List<StoreVO> storeVOS = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build())
                .getContext().getStoreVOList();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(storeVOS)) {
            return storeVOS.stream().collect(Collectors.toMap(StoreVO::getStoreId, StoreVO::getStoreName));
        }
        return new HashMap<>();
    }
}