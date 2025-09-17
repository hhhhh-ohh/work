package com.wanmi.sbc.communityactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.CommunityMiniProgramRequest;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@Tag(name = "CommunityMiniProgramController", description = "生成各类小程序码")
@RestController
@RequestMapping("/community")
@Validated
public class CommunityMiniProgramController {

    @Autowired
    private WechatAuthProvider wechatAuthProvider;
    @Autowired
    private CustomerQueryProvider customerQueryProvider;
    @Autowired
    private RedisUtil redisService;
    @Autowired
    private CommonUtil commonUtil;
    @Resource
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CommunityActivityService communityActivityService;

    @Operation(summary = "社区团购分享二维码")
    @RequestMapping(value = "/miniProgramQrCode", method = RequestMethod.POST)
    public BaseResponse<String> miniProgramQrCode(@RequestBody @Valid CommunityMiniProgramRequest request) {
        communityActivityService.checkOpen();
        CommunityLeaderVO communityLeaderVO = commonUtil.getCommunityLeader();

        //团长信息
        if (communityLeaderVO == null ||
                !communityLeaderVO.getCheckStatus().equals(LeaderCheckStatus.CHECKED)
        ) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "团长不存在");
        }

        if(!communityLeaderVO.getAssistFlag().equals(Constants.yes)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "团长无转发权限");
        }

        request.setLeaderId(communityLeaderVO.getLeaderId());
        request.setShareId(commonUtil.getShareId(communityLeaderVO.getLeaderId()));
        return wechatAuthProvider.communityMiniProgram(request);
    }
}
