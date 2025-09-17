package com.wanmi.sbc.marketing.provider.impl.drawactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.drawactivity.DrawActivitySaveProvider;
import com.wanmi.sbc.marketing.api.request.drawactivity.*;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawActivityAddResponse;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawActivityModifyResponse;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawResultResponse;
import com.wanmi.sbc.marketing.bean.vo.DrawResultVO;
import com.wanmi.sbc.marketing.drawactivity.model.root.DrawActivity;
import com.wanmi.sbc.marketing.drawactivity.service.DrawActivityService;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>抽奖活动表保存服务接口实现</p>
 *
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@RestController
@Validated
@Slf4j
public class DrawActivitySaveController implements DrawActivitySaveProvider {
    @Autowired
    private DrawActivityService drawActivityService;

    @Override
    public BaseResponse<DrawActivityAddResponse> add(@RequestBody @Valid DrawActivityAddRequest drawActivityAddRequest) {
        return BaseResponse.success(new DrawActivityAddResponse(
                drawActivityService.wrapperVo(drawActivityService.add(drawActivityAddRequest))));
    }


    @Override
    public BaseResponse<DrawActivityModifyResponse> modify(@RequestBody @Valid DrawActivityModifyRequest drawActivityModifyRequest) {
        DrawActivity drawActivity = new DrawActivity();
        KsBeanUtil.copyPropertiesThird(drawActivityModifyRequest, drawActivity);
        drawActivity.setUpdatePerson(drawActivityModifyRequest.getUserId());
        return BaseResponse.success(new DrawActivityModifyResponse(
                drawActivityService.wrapperVo(drawActivityService.modify(drawActivity,
                        drawActivityModifyRequest.getPrizeDTOList()))));
    }


    @Override
    public BaseResponse deleteById(@RequestBody @Valid DrawActivityDelByIdRequest drawActivityDelByIdRequest) {
        drawActivityService.deleteById(drawActivityDelByIdRequest.getId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteByIdList(@RequestBody @Valid DrawActivityDelByIdListRequest drawActivityDelByIdListRequest) {
        drawActivityService.deleteByIdList(drawActivityDelByIdListRequest.getIdList());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<DrawResultResponse> lotteryDraw(@Valid LotteryByIdRequest lotteryByIdRequest) {
        DrawResultResponse resultResponse = new DrawResultResponse();
        DrawResultVO drawResultVO = drawActivityService.lotteryDraw(lotteryByIdRequest.getActivityId(),
                lotteryByIdRequest.getCustomerId(), lotteryByIdRequest.getTerminalToken());
        resultResponse.setDrawResult(drawResultVO);
        return BaseResponse.success(resultResponse);
    }

    @Override
    public BaseResponse<DrawResultResponse> addDrawCount(@RequestBody @Valid DrawActivityPauseByIdRequest request) {
        drawActivityService.addDrawCount(request.getId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<DrawResultResponse> addAwardCount(@RequestBody @Valid DrawActivityPauseByIdRequest request) {
        drawActivityService.addAwardCount(request.getId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse closeById(@RequestBody @Valid DrawActivityByIdRequest drawActivityByIdRequest) {
        drawActivityService.closeById(drawActivityByIdRequest.getId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse pauseById(@Valid DrawActivityPauseByIdRequest pauseByIdRequest) {
        drawActivityService.pauseDrawActivityById(pauseByIdRequest.getId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse startById(@Valid DrawActivityStartByIdRequest startByIdRequest) {
        drawActivityService.startDrawActivityById(startByIdRequest.getId());
        return BaseResponse.SUCCESSFUL();
    }

}

