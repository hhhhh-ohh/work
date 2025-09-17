package com.wanmi.sbc.crm.customerplan;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.crm.api.provider.planstatisticsmessage.PlanStatisticsMessageQueryProvider;
import com.wanmi.sbc.crm.api.request.planstatisticsmessage.PlanStatisticsMessageByIdRequest;
import com.wanmi.sbc.crm.bean.vo.PlanStatisticsMessageVO;
import com.wanmi.sbc.crm.customerplan.response.PlanStatisticsMessagePushResponse;
import com.wanmi.sbc.crm.customerplan.vo.PlanStatisticsMessagePushVo;
import com.wanmi.sbc.message.api.provider.pushdetail.PushDetailQueryProvider;
import com.wanmi.sbc.message.api.request.pushdetail.PushDetailListRequest;
import com.wanmi.sbc.message.api.response.pushdetail.PushDetailListResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;


@Tag(name =  "运营计划效果统计通知人次统计数据管理API", description =  "PlanStatisticsMessageController")
@RestController
@Validated
@RequestMapping(value = "/planstatisticsmessagepush")
public class PlanStatisticsMessagePushController {

    @Autowired
    private PlanStatisticsMessageQueryProvider planStatisticsMessageQueryProvider;

    @Autowired
    private PushDetailQueryProvider pushDetailQueryProvider;

    @Operation(summary = "根据运营计划id查询对应运营计划效果统计通知人次统计数据信息")
    @GetMapping("/{planId}")
    public BaseResponse<PlanStatisticsMessagePushResponse> getById(@PathVariable Long planId) {
        if (planId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PlanStatisticsMessageVO planStatisticsMessageVO = planStatisticsMessageQueryProvider.getById(
                PlanStatisticsMessageByIdRequest.builder().planId(planId).build()).getContext().getPlanStatisticsMessageVO();
        PlanStatisticsMessagePushVo planStatisticsMessagePushVo = KsBeanUtil.convert(planStatisticsMessageVO, PlanStatisticsMessagePushVo.class);
        PushDetailListResponse pushDetailListResponse = pushDetailQueryProvider.list(PushDetailListRequest.builder().planId(planId).build()).getContext();
        Integer pushNum = pushDetailListResponse.getPushDetailVOList().stream().map(pushDetailVO ->
                pushDetailVO.getSendSum() == null ? NumberUtils.INTEGER_ZERO : pushDetailVO.getSendSum())
                .reduce(NumberUtils.INTEGER_ZERO,(i, j)->i+j);
        if(Objects.nonNull(planStatisticsMessagePushVo)){
            planStatisticsMessagePushVo.setPushNum(pushNum);
        }
        return BaseResponse.success(PlanStatisticsMessagePushResponse.builder().planStatisticsMessagePushVo(planStatisticsMessagePushVo).build());
    }

}
