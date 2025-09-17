package com.wanmi.sbc.empower.api.provider.wechatauth;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.wechatauth.CommunityMiniProgramRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.DistributionMiniProgramRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.ShareMiniProgramRequest;
import com.wanmi.sbc.empower.api.response.wechatauth.MiniProgramTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by feitingting on 2018/12/28.
 */
@FeignClient(value = "${application.empower.name}", contextId = "WechatAuthProvider")
public interface WechatAuthProvider {
    /**
     * 获取小程序码
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wechat-auth/get-wxa-code-unlimit")
    BaseResponse<String> getWxaCodeUnlimit(@RequestBody MiniProgramQrCodeRequest request);

    /**
     * 分销二维码
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wechat-auth/distribution/miniProgram")
    BaseResponse<String> distributionMiniProgram(@RequestBody DistributionMiniProgramRequest request);

    /**
     * 社区团购活动二维码
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wechat-auth/community/miniProgram")
    BaseResponse<String> communityMiniProgram(@RequestBody CommunityMiniProgramRequest request);

    @PostMapping("/wechat-auth/shareuserid/miniProgram")
    BaseResponse<String> getMiniProgramQrCodeWithShareUserId(@RequestBody ShareMiniProgramRequest request);

    /**
     * 提供获取accessToken接口 替换之前直播 跟 h5 分享 重复获取 accessToken 导致的失效问题
     * @return
     */
    @PostMapping("/wechat-auth/access-token/miniProgram")
    BaseResponse<MiniProgramTokenResponse> getMiniProgramAccessToken();

    /**
     * @description   微信返回accessToken失效，清除缓存重新获取
     * @author  wur
     * @date: 2022/5/9 16:37
     * @return
     **/
    @PostMapping("/wechat-auth/access-token/miniProgram/cleanRedis")
    BaseResponse<MiniProgramTokenResponse> getMiniProgramAccessTokenCleanRedis();


    @PostMapping("/empower/${application.empower.version}/wechat-auth/get-wxa-code-bytes-unlimit")
    BaseResponse<byte[]> getWxaCodeBytesUnlimit(@RequestBody MiniProgramQrCodeRequest request);


    /**
     * 获取微信开关
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/wechat-auth/get-open")
    BaseResponse<Boolean> getMiniProgramStatus();
}