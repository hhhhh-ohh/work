package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.market.MarketingProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.preferential.PreferentialQueryProvider;
import com.wanmi.sbc.marketing.api.request.market.*;
import com.wanmi.sbc.marketing.api.response.market.MarketingGetByIdResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingPageResponse;
import com.wanmi.sbc.marketing.bean.dto.MarketingPageDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.MarketingPageVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.marketing.request.MarketingMutexValidateRequest;
import com.wanmi.sbc.marketing.request.MarketingPageListRequest;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.setting.api.provider.pagemanage.PageInfoExtendQueryProvider;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.response.pagemanage.PageInfoExtendByIdResponse;
import com.wanmi.sbc.setting.bean.vo.PageInfoExtendVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Tag(name = "MarketingBaseController", description = "营销基础服务API")
@RestController
@Validated
@RequestMapping("/marketing")
public class MarketingBaseController {

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PageInfoExtendQueryProvider pageInfoExtendQueryProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private MarketingBaseService baseService;

    @Autowired
    private MarketingProvider marketingProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private PreferentialQueryProvider preferentialQueryProvider;

   /**
     * 获取营销活动列表
     * @param marketingPageListRequest {@link MarketingPageRequest}
     * @return
     */
    @Operation(summary = "获取营销活动列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<MarketingPageVO>> getMarketingList(@RequestBody MarketingPageListRequest marketingPageListRequest) {
        Long storeId = commonUtil.getStoreId();
        MarketingPageRequest marketingPageRequest = new MarketingPageRequest();
        if(Objects.nonNull(storeId)) {
            marketingPageRequest.setStoreId(storeId);
        } else if(Objects.nonNull(marketingPageListRequest.getStoreId())){
            marketingPageRequest.setStoreId(marketingPageListRequest.getStoreId());
        } else {
            //模糊查询店铺名称
            BaseResponse<MicroServicePage<MarketingPageVO>> microServicePageBaseResponse = baseService
                    .getMicroServicePageBaseResponse(marketingPageListRequest);
            if (Objects.nonNull(microServicePageBaseResponse)){
                return microServicePageBaseResponse;
            }
        }
        marketingPageRequest.setMarketingPageDTO(KsBeanUtil.convert(marketingPageListRequest, MarketingPageDTO.class));
        marketingPageRequest.setRules(marketingPageListRequest.getRules());
        marketingPageRequest.setIsRule(marketingPageListRequest.getIsRule());
        BaseResponse<MarketingPageResponse> pageResponse = marketingQueryProvider.page(marketingPageRequest);
        return BaseResponse.success(pageResponse.getContext().getMarketingVOS());
    }



    /**
     * 获取营销活动推广信息
     * @param request
     * @return
     */
    @Operation(summary = "获取营销活动推广信息")
    @PostMapping("/getExtendInfo")
    public BaseResponse<PageInfoExtendByIdResponse> getExtendInfo(@RequestBody PageInfoExtendByIdRequest request){
        this.check(request);
        request.setStoreId(commonUtil.getStoreIdWithDefault());
        String goodsInfoId = request.getGoodsInfoId();
        GoodsInfoVO goodsInfo = this.getGoodsInfo(goodsInfoId);
        if(Objects.nonNull(goodsInfo)){
            request.setPluginType(goodsInfo.getPluginType());
            BaseResponse<PageInfoExtendByIdResponse> extendResponse = pageInfoExtendQueryProvider.findExtendById(request);
            PageInfoExtendByIdResponse context = extendResponse.getContext();
            PageInfoExtendVO pageInfoExtend = context.getPageInfoExtend();
            pageInfoExtend.setGoodsInfoImg(goodsInfo.getGoodsInfoImg());
            return extendResponse;
        }
        BaseResponse<PageInfoExtendByIdResponse> response = pageInfoExtendQueryProvider.findExtendById(request);
        Long pageStoreId = response.getContext().getPageInfoExtend().getStoreId();

        //如果推广信息关联的店铺id是平台的 则不做校验
        if(pageStoreId != null && !pageStoreId.equals(Constants.BOSS_DEFAULT_STORE_ID)){
            commonUtil.checkStoreId(pageStoreId);
        }
        return response;
    }

    @Operation(summary = "营销活动验证互斥")
    @PostMapping("/mutexValidate")
    public BaseResponse mutexValidate(@RequestBody MarketingMutexValidateRequest request){
        baseService.mutexValidate(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取商品信息
     *
     * @param goodsInfoId
     * @return
     */
    private GoodsInfoVO getGoodsInfo(String goodsInfoId) {
        if(StringUtils.isBlank(goodsInfoId)){
            return null;
        }
        GoodsInfoListByIdRequest listByIdRequest = new GoodsInfoListByIdRequest();
        listByIdRequest.setGoodsInfoId(goodsInfoId);
        EsSkuPageRequest pageRequest = new EsSkuPageRequest();
        pageRequest.setGoodsInfoIds(Collections.singletonList(goodsInfoId));
        BaseResponse<EsSkuPageResponse> baseResponse = esSkuQueryProvider.page(pageRequest);
        MicroServicePage<GoodsInfoVO> goodsInfoPage = baseResponse.getContext().getGoodsInfoPage();
        List<GoodsInfoVO> goodsInfoVOList = goodsInfoPage.getContent();
        if(CollectionUtils.isEmpty(goodsInfoVOList)){
            return null;
        }
        return goodsInfoVOList.get(NumberUtils.INTEGER_ZERO);
    }

    /**
     * 参数校验
     * @param request
     */
    private void check(PageInfoExtendByIdRequest request){
        if(StringUtils.isBlank(request.getPageId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(StringUtils.isBlank(request.getPlatform())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(Objects.isNull(request.getMarketingType())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(StringUtils.isBlank(request.getUrl())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(Objects.isNull(request.getUseType())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(StringUtils.isBlank(request.getActivityId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 暂停营销活动
     * @param marketingId
     * @return
     */
    @Operation(summary = "暂停营销活动")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/pause/{marketingId}", method = RequestMethod.PUT)
    @Transactional
    public BaseResponse pauseMarketingId(@PathVariable("marketingId")Long marketingId){
        //进行中的营销活动，才能暂停
        MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
        marketingGetByIdRequest.setMarketingId(marketingId);
        MarketingVO marketing = marketingQueryProvider.getById(marketingGetByIdRequest).getContext().getMarketingVO();
        if(marketing != null){
            //越权校验
            commonUtil.checkStoreId(marketing.getStoreId());
            //如果现在时间在活动开始之前或者活动已经结束
            if(LocalDateTime.now().isBefore(marketing.getBeginTime()) || LocalDateTime.now().isAfter(marketing.getEndTime())){
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080003);
            }else{
                baseService.checkPlatForm(marketing);
                MarketingPauseByIdRequest marketingPauseByIdRequest = MarketingPauseByIdRequest.builder().build();
                marketingPauseByIdRequest.setMarketingId(marketingId);
                marketingProvider.pauseById(marketingPauseByIdRequest);

                operateLogMQUtil.convertAndSend("营销","暂停促销活动",
                        "暂停促销活动："+ getMarketingName(marketingId));
                return BaseResponse.SUCCESSFUL();
            }
        }else{
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        }
    }

    /**
     * 开始营销活动
     * @param marketingId
     * @return
     */
    @Operation(summary = "开始营销活动")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/start/{marketingId}", method = RequestMethod.PUT)
    @Transactional
    public BaseResponse startMarketingId(@PathVariable("marketingId")Long marketingId){
        //进行中的营销活动，才能暂停
        MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
        marketingGetByIdRequest.setMarketingId(marketingId);
        MarketingVO marketing = marketingQueryProvider.getById(marketingGetByIdRequest).getContext().getMarketingVO();
        if(marketing != null){
            //如果现在时间在活动开始之前或者活动已经结束，或者当前状态是开始状态
            if(LocalDateTime.now().isBefore(marketing.getBeginTime()) || LocalDateTime.now().isAfter(marketing.getEndTime())
                    || marketing.getIsPause() == BoolFlag.NO){
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080014);
            }else{
                baseService.checkPlatForm(marketing);
                MarketingStartByIdRequest marketingStartByIdRequest = new MarketingStartByIdRequest();
                marketingStartByIdRequest.setMarketingId(marketingId);
                marketingProvider.startById(marketingStartByIdRequest);
                operateLogMQUtil.convertAndSend("营销","开启促销活动",
                        "开启促销活动："+ getMarketingName(marketingId));
                return BaseResponse.SUCCESSFUL();
            }
        }else{
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        }
    }

    /**
     * 关闭营销活动
     * @param marketingId
     * @return
     */
    @Operation(summary = "关闭营销活动")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/close/{marketingId}", method = RequestMethod.PUT)
    @Transactional
    public BaseResponse closeActivity(@PathVariable("marketingId")Long marketingId){
        marketingProvider.close(MarketingCloseRequest.builder()
                .marketingId(marketingId)
                .storeId(commonUtil.getStoreId())
                .platform(commonUtil.getOperator().getPlatform())
                .build());
        operateLogMQUtil.convertAndSend("营销","关闭促销活动",
                "关闭促销活动："+ getMarketingName(marketingId));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除营销活动
     * @param marketingId
     * @return
     */
    @Operation(summary = "删除营销活动")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/delete/{marketingId}", method = RequestMethod.DELETE)
    @Transactional
    public BaseResponse deleteMarketingId(@PathVariable("marketingId")Long marketingId){
        //未开始的营销活动，才能删除
        MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
        marketingGetByIdRequest.setMarketingId(marketingId);
        MarketingVO marketing = marketingQueryProvider.getById(marketingGetByIdRequest).getContext().getMarketingVO();
        if(marketing != null){
            //越权校验
            commonUtil.checkStoreId(marketing.getStoreId());
            if(LocalDateTime.now().isBefore(marketing.getBeginTime())){
                baseService.checkPlatForm(marketing);
                MarketingDeleteByIdRequest marketingDeleteByIdRequest = new MarketingDeleteByIdRequest();
                marketingDeleteByIdRequest.setMarketingId(marketingId);
                marketingProvider.deleteById(marketingDeleteByIdRequest);

                String name = getMarketingName(marketingId);
                operateLogMQUtil.convertAndSend("营销","删除促销活动","删除促销活动："+ name);
                return BaseResponse.SUCCESSFUL();

            }else{
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080002);
            }
        }else{
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        }
    }

    /**
     *  公共方法获取促销活动名称
     * @param marketId
     * @return
     */
    private String getMarketingName(long marketId){
        MarketingGetByIdRequest request = new MarketingGetByIdRequest();
        request.setMarketingId(marketId);
        MarketingGetByIdResponse marketing = marketingQueryProvider.getById(request).getContext();
        return Objects.nonNull(marketing) ? marketing.getMarketingVO().getMarketingName() : " ";
    }
}
