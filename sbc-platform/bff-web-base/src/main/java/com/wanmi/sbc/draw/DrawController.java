package com.wanmi.sbc.draw;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.provider.drawactivity.DrawActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.drawactivity.DrawActivitySaveProvider;
import com.wanmi.sbc.marketing.api.request.drawactivity.DrawActivityListRequest;
import com.wanmi.sbc.marketing.api.request.drawactivity.DrawDetailByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawactivity.LotteryByIdRequest;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawActivityListResponse;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawDetailByIdResponse;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawResultResponse;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author huangzhao
 */
@Tag(description= "抽奖活动表管理API", name = "DrawActivityController")
@RestController
@Validated
@RequestMapping(value = "/drawactivity")
public class DrawController {

    @Autowired
    private DrawActivityQueryProvider drawActivityQueryProvider;


    @Autowired
    private DrawActivitySaveProvider drawActivitySaveProvider;

    @Autowired
    private CommonUtil commonUtil;




    @Operation(summary = "列表查询抽奖活动表")
    @PostMapping("/list")
    public BaseResponse<DrawActivityListResponse> getList(@RequestBody @Valid DrawActivityListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return drawActivityQueryProvider.list(listReq);
    }

    @Operation(summary = "已登录根据id查询抽奖活动表")
    @PostMapping("/detailForWeb")
    public BaseResponse<DrawDetailByIdResponse> detailForWeb(@RequestBody DrawDetailByIdRequest request) {
        //活动编号必传
        if (Objects.isNull(request.getActivityId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        Operator operator = commonUtil.getOperator();
        if (Objects.nonNull(operator)){
            request.setCustomerId(operator.getUserId());
        }

        return drawActivityQueryProvider.detailForWeb(request);
    }

    @Operation(summary = "未登陆登录根据id查询抽奖活动表")
    @PostMapping("/unLogin/detailForWeb")
    public BaseResponse<DrawDetailByIdResponse> unLoginDetail(@RequestBody DrawDetailByIdRequest request) {
        //活动编号必传
        if (Objects.isNull(request.getActivityId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return drawActivityQueryProvider.detailForWeb(request);
    }


    @Operation(summary = "抽奖按钮点击开始接口")
    @PostMapping("/lotteryDraw")
    public BaseResponse<DrawResultResponse> lotteryDraw(@RequestBody LotteryByIdRequest request) {
        //活动编号必传
        if (Objects.isNull(request.getActivityId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCustomerId(commonUtil.getOperatorId());
        request.setTerminalToken(commonUtil.getTerminalToken());
        return drawActivitySaveProvider.lotteryDraw(request);
    }




}
