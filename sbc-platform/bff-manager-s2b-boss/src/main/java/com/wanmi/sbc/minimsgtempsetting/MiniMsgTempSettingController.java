package com.wanmi.sbc.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.sellplatform.miniprogramsubscribe.PlatformMiniMsgProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformInitMiniMsgTempData;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformInitMiniMsgTempRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.miniprogramsubscibe.PlatformMiniMsgTempResponse;
import com.wanmi.sbc.message.api.provider.minimsgtempsetting.MiniMsgTempSettingProvider;
import com.wanmi.sbc.message.api.provider.minimsgtempsetting.MiniMsgTempSettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingBatchModifyData;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingBatchModifyRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingListRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingModifyRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingPageRequest;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingListResponse;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingModifyResponse;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingPageResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;


@Tag(name =  "小程序订阅消息模版配置表管理API", description =  "MiniMsgTempSettingController")
@RestController
@Validated
@RequestMapping(value = "/minimsgtempsetting")
public class MiniMsgTempSettingController {

    @Autowired
    private MiniMsgTempSettingQueryProvider miniMsgTempSettingQueryProvider;

    @Autowired
    private MiniMsgTempSettingProvider miniMsgTempSettingProvider;

    @Autowired
    private PlatformMiniMsgProvider platformMiniMsgProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtil redisUtil;

    @Operation(summary = "分页查询小程序订阅消息模版配置表")
    @PostMapping("/page")
    public BaseResponse<MiniMsgTempSettingPageResponse> getPage(@RequestBody @Valid MiniMsgTempSettingPageRequest pageReq) {
        return miniMsgTempSettingQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询小程序订阅消息模版配置表")
    @GetMapping("/list")
    public BaseResponse<MiniMsgTempSettingListResponse> getList() {
        return miniMsgTempSettingQueryProvider.list(MiniMsgTempSettingListRequest.builder().initFlag(Boolean.TRUE).build());
    }

    @Operation(summary = "初始化小程序订阅消息模板")
    @GetMapping("/initMiniMsgTemp")
    public BaseResponse initMiniProgramSubscribeTemplate() {
        String lockKey = CacheKeyConstant.INIT_MINI_PROGRAM_SUBSCRIBE_TEMPLATE_LOCK;
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try {
            // 查询数据库中初始化模板
            MiniMsgTempSettingListResponse miniMsgTempSettingListResponse =
                    miniMsgTempSettingQueryProvider.list(MiniMsgTempSettingListRequest.builder().initFlag(Boolean.TRUE).build()).getContext();
            // 构造初始化入参数据
            List<PlatformInitMiniMsgTempData> data =
                    KsBeanUtil.convert(miniMsgTempSettingListResponse.getMiniMsgTemplateSettingVOList(), PlatformInitMiniMsgTempData.class);
            PlatformInitMiniMsgTempRequest request =
                    PlatformInitMiniMsgTempRequest.builder().data(data).build();
            // 初始化微信小程序订阅消息模板
            BaseResponse<List<PlatformMiniMsgTempResponse>> response =
                    platformMiniMsgProvider.initMiniProgramSubscribeTemplate(request);
            // 将微信小程序订阅消息模板同步到数据库中
            List<PlatformMiniMsgTempResponse> platformMiniMsgTempRespons =
                    response.getContext();
            List<MiniMsgTempSettingBatchModifyData> dataList =
                    KsBeanUtil.convertList(platformMiniMsgTempRespons, MiniMsgTempSettingBatchModifyData.class);
            miniMsgTempSettingProvider.batchModify(MiniMsgTempSettingBatchModifyRequest.builder().dataList(dataList).build());
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "初始化小程序订阅消息模板")
    @GetMapping("/checkInitMiniMsgTemp")
    public BaseResponse<Boolean> checkInitMiniProgramSubscribeTemplate() {
        String lockKey = CacheKeyConstant.INIT_MINI_PROGRAM_SUBSCRIBE_TEMPLATE_LOCK;
        if (redisUtil.hasKey(lockKey)){
            return BaseResponse.success(Boolean.TRUE);
        }
        return BaseResponse.success(Boolean.FALSE);
    }

    @Operation(summary = "根据id查询小程序订阅消息模版配置表")
    @GetMapping("/{id}")
    public BaseResponse<MiniMsgTempSettingByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        MiniMsgTempSettingByIdRequest idReq = new MiniMsgTempSettingByIdRequest();
        idReq.setId(id);
        return miniMsgTempSettingQueryProvider.getById(idReq);
    }

    @Operation(summary = "修改小程序订阅消息模版配置表")
    @PutMapping("/modify")
    public BaseResponse<MiniMsgTempSettingModifyResponse> modify(@RequestBody @Valid MiniMsgTempSettingModifyRequest modifyReq) {
        return miniMsgTempSettingProvider.modify(modifyReq);
    }

}
