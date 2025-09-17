package com.wanmi.sbc.halfPricesecondpiece;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.provider.halfpricesecondpiece.HalfPriceSecondPieceProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.request.halfpricesecondpiece.HalfPriceSecondPieceAddRequest;
import com.wanmi.sbc.marketing.api.request.market.ExistsSkuByMarketingTypeRequest;
import com.wanmi.sbc.marketing.api.request.market.PauseModifyRequest;
import com.wanmi.sbc.marketing.bean.dto.SkuExistsDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 第二件半价服务API
 */
@Tag(name = "HalfPriceSecondPieceController", description = "第二件半价服务API")
@RestController
@Validated
@RequestMapping("/half_price_second_piece")
public class HalfPriceSecondPieceController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private HalfPriceSecondPieceProvider halfPriceSecondPieceProvider;

    @Autowired
    private MarketingProvider marketingProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired private MarketingBaseService marketingBaseService;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    /**
     * 新增一口价营销信息
     *
     * @param request
     * @return
     */
    @Operation(summary = "新增第二件半价信息")
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse add(@Valid @RequestBody HalfPriceSecondPieceAddRequest request) {
        request.valid();

        request.setIsBoss(BoolFlag.NO);
        request.setStoreId(commonUtil.getStoreId());
        request.setCreatePerson(commonUtil.getOperatorId());
        // 验证是否又存在同时间段的SKU
        if (Objects.equals(MarketingScopeType.SCOPE_TYPE_CUSTOM, request.getScopeType())) {
            SkuExistsDTO skuExistsDTO = new SkuExistsDTO();
            skuExistsDTO.setSkuIds(request.getScopeIds());
            skuExistsDTO.setEndTime(request.getEndTime());
            skuExistsDTO.setStartTime(request.getBeginTime());
            skuExistsDTO.setMarketingType(MarketingType.HALF_PRICE_SECOND_PIECE);
            List<String> skuIdList = marketingQueryProvider.queryExistsSkuByMarketingType(ExistsSkuByMarketingTypeRequest.builder()
                    .storeId(commonUtil.getStoreId())
                    .skuExistsDTO(skuExistsDTO)
                    .build()).getContext();
            if (CollectionUtils.isNotEmpty(skuIdList)) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080004,
                        new Object[]{skuIdList.size()});
            }
        }

        //全局互斥
        marketingBaseService.mutexValidate(MarketingType.HALF_PRICE_SECOND_PIECE, request.getBeginTime(),
                request.getEndTime(), request.getScopeType(), request.getScopeIds(), request.getStoreId(), null);
        halfPriceSecondPieceProvider.addHalfPriceSecondPiece(request);

        operateLogMQUtil.convertAndSend("营销", "创建第二件半价活动", "创建第二件半价活动：" + request.getMarketingName());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改第二件半价信息
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改第二件半价信息")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse modify(@Valid @RequestBody HalfPriceSecondPieceAddRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setStoreId(commonUtil.getStoreId());

        if (DefaultFlag.YES.equals(request.getIsPause()) && LocalDateTime.now().isAfter(request.getBeginTime())){
            marketingProvider.pauseModify(PauseModifyRequest.builder()
                    .marketingId(request.getMarketingId())
                    .endTime(request.getEndTime())
                    .joinLevel(request.getJoinLevel())
                    .updatePerson(request.getUpdatePerson())
                    .build());
        }else {
            request.valid();
            //全局互斥
            marketingBaseService.mutexValidate(MarketingType.HALF_PRICE_SECOND_PIECE, request.getBeginTime(),
                    request.getEndTime(), request.getScopeType(), request.getScopeIds(), request.getStoreId(), request.getMarketingId());
            halfPriceSecondPieceProvider.modifyHalfPriceSecondPiece(request);
        }

        operateLogMQUtil.convertAndSend("第二件半价营销信息", "编辑第二件半价促销活动", "编辑第二件半价促销活动：" + request.getMarketingName());
        return BaseResponse.SUCCESSFUL();
    }


}
