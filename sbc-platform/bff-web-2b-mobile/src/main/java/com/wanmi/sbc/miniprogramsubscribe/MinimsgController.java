package com.wanmi.sbc.miniprogramsubscribe;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ProgramNodeType;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordProvider;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordAddRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordListAddRequest;
import com.wanmi.sbc.message.api.provider.minimsgrecord.MiniMsgRecordQueryProvider;
import com.wanmi.sbc.message.api.provider.minimsgtempsetting.MiniMsgTempSettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgrecord.MiniMsgRecordByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingListRequest;
import com.wanmi.sbc.message.api.response.minimsgrecord.MiniMsgByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingListResponse;
import com.wanmi.sbc.message.bean.vo.MiniMsgTemplateSettingVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;


@Tag(name =  "小程序消息接口", description =  "MinimsgController")
@RestController
@Validated
@RequestMapping(value = "/minimsg")
public class MinimsgController {

    @Autowired
    private MiniMsgRecordQueryProvider miniMsgRecordQueryProvider;

    @Autowired
    private MiniMsgCustomerRecordProvider miniMsgCustomerRecordProvider;

    @Autowired
    private MiniMsgTempSettingQueryProvider miniMsgTempSettingQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 根据授权节点id查询是否弹窗授权
     * @return
     */
    @Operation(summary = "根据授权节点id查询是否弹窗授权")
    @GetMapping("/{nodeId}")
    public BaseResponse<MiniMsgByIdResponse> getByNodeId(@PathVariable String nodeId) {
        if (StringUtils.isEmpty(nodeId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ProgramNodeType requestNodeId = ProgramNodeType.fromValue(Integer.parseInt(nodeId));
        String customerId = commonUtil.getOperatorId();
        MiniMsgRecordByIdRequest miniMsgRecordByIdRequest =
                MiniMsgRecordByIdRequest.builder().nodeId(requestNodeId).customerId(customerId).build();
        return miniMsgRecordQueryProvider.pullWxPage(miniMsgRecordByIdRequest);
    }

    /**
     * 新增客户订阅消息信息
     * @return
     */
    @Operation(summary = "新增客户订阅消息信息")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid MiniMsgCustomerRecordListAddRequest addReq) {
        String customerId = commonUtil.getOperatorId();
        MiniMsgTempSettingListResponse miniMsgTempSettingListResponse =
                miniMsgTempSettingQueryProvider.list(MiniMsgTempSettingListRequest.builder()
                        .templateIds(addReq.getTemplateIds()).initFlag(Boolean.TRUE).build()).getContext();
        List<MiniMsgTemplateSettingVO> miniMsgTemplateSettingVOList =
                miniMsgTempSettingListResponse.getMiniMsgTemplateSettingVOList();
        if (CollectionUtils.isNotEmpty(miniMsgTemplateSettingVOList)){
            miniMsgTemplateSettingVOList.forEach(miniProgramSubscribeTemplateSettingVO -> {
                MiniMsgCustomerRecordAddRequest miniMsgCustomerRecordAddRequest =
                        MiniMsgCustomerRecordAddRequest.builder()
                                .customerId(customerId).openId(addReq.getOpenId()).sendFlag(Constants.ZERO)
                                .triggerNodeId(miniProgramSubscribeTemplateSettingVO.getTriggerNodeId())
                                .templateSettingId(miniProgramSubscribeTemplateSettingVO.getId()).build();
                if (TriggerNodeType.NEW_ACTIVITY == miniProgramSubscribeTemplateSettingVO.getTriggerNodeId()){
                    miniMsgCustomerRecordAddRequest.setMessageActivityId(Constants.NUM_MINUS_1L);
                }
                miniMsgCustomerRecordProvider.add(miniMsgCustomerRecordAddRequest);
            });
        }
        return BaseResponse.SUCCESSFUL();
    }

}
