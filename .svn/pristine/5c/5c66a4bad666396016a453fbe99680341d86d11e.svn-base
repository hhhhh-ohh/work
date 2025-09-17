package com.wanmi.sbc.flashsalesetting;

import com.wanmi.ares.enums.DefaultFlag;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsSaveProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsDelByTimeListRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.IsFlashSaleRequest;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsStoreCountResponse;
import com.wanmi.sbc.setting.api.provider.flashsalesetting.FlashSaleSettingQueryProvider;
import com.wanmi.sbc.setting.api.provider.flashsalesetting.FlashSaleSettingSaveProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.ConfigModifyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.flashsalesetting.FlashSaleSettingListModifyRequest;
import com.wanmi.sbc.setting.api.request.flashsalesetting.FlashSaleSettingListRequest;
import com.wanmi.sbc.setting.api.response.flashsalesetting.FlashSaleSettingCancelListResponse;
import com.wanmi.sbc.setting.api.response.flashsalesetting.FlashSaleSettingListResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.FlashSaleSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Tag(name =  "秒杀设置管理API", description =  "FlashSaleSettingController")
@RestController
@Validated
@RequestMapping(value = "/flashsalesetting")
public class FlashSaleSettingController {

    @Autowired
    private FlashSaleSettingQueryProvider flashSaleSettingQueryProvider;

    @Autowired
    private FlashSaleSettingSaveProvider flashSaleSettingSaveProvider;

    @Autowired
    private FlashSaleGoodsSaveProvider flashSaleGoodsSaveProvider;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    @Operation(summary = "列表查询秒杀设置")
    @PostMapping("/list")
    public BaseResponse<FlashSaleSettingListResponse> getList(@RequestBody @Valid FlashSaleSettingListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("time", "asc");
        List<FlashSaleSettingVO> flashSaleSettingVOList = flashSaleSettingQueryProvider.list(listReq).getContext().getFlashSaleSettingVOList();
        flashSaleSettingVOList.forEach(flashSaleSettingVO -> {
                    Boolean isFlashSale = flashSaleGoodsQueryProvider.isFlashSale(IsFlashSaleRequest.builder()
                            .activityTime(flashSaleSettingVO.getTime())
                            .build()).getContext().getIsFlashSale();
                    flashSaleSettingVO.setIsFlashSale(isFlashSale);
                }
        );
        return BaseResponse.success(FlashSaleSettingListResponse.builder()
                .flashSaleSettingVOList(flashSaleSettingVOList)
                .build());
    }

    @Operation(summary = "批量修改秒杀设置")
    @PutMapping("/modifyList")
    public BaseResponse modifyList(@RequestBody @Valid FlashSaleSettingListModifyRequest modifyReq) {
        if (CollectionUtils.isNotEmpty(modifyReq.getFlashSaleSettingVOS())) {
            modifyReq.getFlashSaleSettingVOS().forEach(flashSaleSettingVO -> {
                flashSaleSettingVO.setUpdatePerson(commonUtil.getOperatorId());
                flashSaleSettingVO.setUpdateTime(LocalDateTime.now());
            });
            FlashSaleSettingCancelListResponse modifyResponse = flashSaleSettingSaveProvider.modifyList(modifyReq).getContext();
            //取消掉的场次下的未开始的秒杀商品也要删除
            if (CollectionUtils.isNotEmpty(modifyResponse.getCanceledTimeList())) {
                flashSaleGoodsSaveProvider.deleteByTimeList(new FlashSaleGoodsDelByTimeListRequest(modifyResponse.getCanceledTimeList()));
            }
            operateLogMQUtil.convertAndSend("营销", "秒杀活动", "保存每日场次设置");
        }

        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "获取参与商家数量")
    @PostMapping("/storeCount")
    public BaseResponse<FlashSaleGoodsStoreCountResponse> storeCount() {
        return flashSaleGoodsQueryProvider.storeCount();
    }

    /**
     * 查询秒杀活动超时未支付取消订单设置
     * @param
     * @return
     */
    @Operation(summary = "查询秒杀活动超时未支付取消订单设置")
    @GetMapping("/getFlashSaleOrderAutoCancel")
    public BaseResponse<SystemConfigTypeResponse> getFlashPromotionOrderAutoCancel(){
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.FLASH_SALE_ORDER_AUTO_CANCEL.toValue());
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest);
    }

    /**
     * 更新限时抢购超时未支付取消订单设置
     * @param
     * @return
     */
    @Operation(summary = "更新限时抢购超时未支付取消订单设置")
    @PutMapping("/updateFlashSaleOrderAutoCancel")
    public BaseResponse updateFlashSaleOrderAutoCancel(@RequestBody ConfigModifyRequest configModifyRequest){
        List<ConfigModifyRequest> configModifyRequests = new ArrayList<>();
        configModifyRequest.setStatus(DefaultFlag.YES.toValue());
        configModifyRequests.add(configModifyRequest);
        if(StringUtils.isNotBlank(configModifyRequest.getContext())){
            if(Integer.valueOf(configModifyRequest.getContext())<1 || Integer.valueOf(configModifyRequest.getContext())>999){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        return systemConfigSaveProvider.update(configModifyRequests);
    }

}
