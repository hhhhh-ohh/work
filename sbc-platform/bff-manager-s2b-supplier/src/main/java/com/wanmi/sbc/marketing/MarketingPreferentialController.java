package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.api.provider.market.MarketingProvider;
import com.wanmi.sbc.marketing.api.provider.preferential.PreferentialProvider;
import com.wanmi.sbc.marketing.api.request.market.PauseModifyRequest;
import com.wanmi.sbc.marketing.api.request.preferential.PreferentialAddRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author edz
 * @className MarketingPreferentialController
 * @description 加价购
 * @date 2022/11/17 14:04
 **/
@RestController
@RequestMapping("/marketing/preferential")
@Validated
@Tag(name = "加价购活动")
public class MarketingPreferentialController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PreferentialProvider preferentialProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private MarketingBaseService marketingBaseService;

    @Autowired
    private MarketingProvider marketingProvider;

    /**
     * @description 创建加价购活动
     * @author  edz
     * @date: 2022/12/9 15:14
     * @param preferentialRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "创建加价购活动")
    @PostMapping("/add")
    @MultiSubmit
    public BaseResponse add(@RequestBody @Valid PreferentialAddRequest preferentialRequest){
        // 基本校验
        preferentialRequest.valid();

        preferentialRequest.setIsBoss(BoolFlag.NO);
        preferentialRequest.setStoreId(commonUtil.getStoreId());
        preferentialRequest.setCreatePerson(commonUtil.getOperatorId());

        //全局互斥
        marketingBaseService.mutexValidate(
                MarketingType.PREFERENTIAL,
                preferentialRequest.getBeginTime(),
                preferentialRequest.getEndTime(),
                preferentialRequest.getScopeType(),
                preferentialRequest.getScopeIds(),
                preferentialRequest.getStoreId(),
                null
        );

        preferentialProvider.add(preferentialRequest);
        operateLogMQUtil.convertAndSend("营销","创建加价购活动","创建加价购活动：" +
                preferentialRequest.getMarketingName());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 修改加价购活动
     * @author  edz
     * @date: 2022/12/9 15:14
     * @param preferentialRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @Operation(summary = "修改加价购活动")
    @PutMapping("modify")
    public BaseResponse modify(@RequestBody @Valid PreferentialAddRequest preferentialRequest){
        // 基本校验

        if (DefaultFlag.YES.equals(preferentialRequest.getIsPause())
                && LocalDateTime.now().isAfter(preferentialRequest.getBeginTime())) {
            //已开始且暂停中,加价购活动可以编辑
            marketingProvider.pauseModify(PauseModifyRequest.builder()
                    .marketingId(preferentialRequest.getMarketingId())
                    .endTime(preferentialRequest.getEndTime())
                    .joinLevel(preferentialRequest.getJoinLevel())
                    .updatePerson(preferentialRequest.getUpdatePerson())
                    .build());
        } else {
            preferentialRequest.valid();
            //全局互斥
            marketingBaseService.mutexValidate(
                    MarketingType.PREFERENTIAL,
                    preferentialRequest.getBeginTime(),
                    preferentialRequest.getEndTime(),
                    preferentialRequest.getScopeType(),
                    preferentialRequest.getScopeIds(),
                    preferentialRequest.getStoreId(),
                    preferentialRequest.getMarketingId()
            );

            preferentialRequest.setStoreId(commonUtil.getStoreId());
            preferentialRequest.setUpdatePerson(commonUtil.getOperatorId());
            preferentialProvider.modify(preferentialRequest);
        }


        operateLogMQUtil.convertAndSend("营销","更新加价购活动","更新加价购活动：" +
                preferentialRequest.getMarketingName());
        return BaseResponse.SUCCESSFUL();
    }
}
