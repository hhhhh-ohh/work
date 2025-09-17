package com.wanmi.sbc.third.share;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.wechatshareset.WechatShareSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.wechatshareset.WechatShareSetSaveProvider;
import com.wanmi.sbc.empower.api.request.wechatshareset.WechatShareSetAddRequest;
import com.wanmi.sbc.empower.api.request.wechatshareset.WechatShareSetInfoByStoreIdRequest;
import com.wanmi.sbc.empower.api.response.wechatshareset.WechatShareSetInfoResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: songhanlin
 * @Date: Created In 10:57 AM 2018/8/13
 * @Description: 微信分享Controller
 */
@Tag(name="WechatShareController", description = "微信分享")
@RestController
@Validated
@RequestMapping("/third/share/wechat")
public class WechatShareController {
    @Autowired
    private WechatShareSetQueryProvider wechatShareSetQueryProvider;

    @Autowired
    private WechatShareSetSaveProvider wechatShareSetSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    /**
     * 查询详情
     * @return
     */
    @Operation(summary = "查询详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public BaseResponse<WechatShareSetInfoResponse> findOne() {
        return wechatShareSetQueryProvider.getInfoByStoreId(WechatShareSetInfoByStoreIdRequest
                .builder()
                .storeId(commonUtil.getStoreId())
                .operatePerson(commonUtil.getOperatorId())
                .build());
    }

    /**
     * 修改
     * @param request
     * @return
     */
    @Operation(summary = "修改")
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public BaseResponse save(@RequestBody WechatShareSetAddRequest request) {
        request.setStoreId(commonUtil.getStoreId());
        if (StringUtils.isBlank(request.getShareAppId()) || request.getShareAppId().length() > Constants.NUM_50) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (StringUtils.isBlank(request.getShareAppSecret()) || request.getShareAppSecret().length() > Constants.NUM_50) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setOperatePerson(commonUtil.getOperatorId());
        operateLogMQUtil.convertAndSend("设置", "编辑分享接口", "编辑分享接口");

        return wechatShareSetSaveProvider.add(request);
    }
}
