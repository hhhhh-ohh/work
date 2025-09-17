package com.wanmi.sbc.empower.provider.impl.wechatauth;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.CommunityMiniProgramRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.DistributionMiniProgramRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.ShareMiniProgramRequest;
import com.wanmi.sbc.empower.api.response.wechatauth.MiniProgramTokenResponse;
import com.wanmi.sbc.empower.miniprogramset.model.root.MiniProgramSet;
import com.wanmi.sbc.empower.miniprogramset.service.MiniProgramSetService;
import com.wanmi.sbc.empower.wechat.WechatApiUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Created by feitingting on 2018/12/28.
 */
@Slf4j
@RestController
public class WechatAuthController implements WechatAuthProvider {

    @Autowired
    private WechatApiUtil wechatApiUtil;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private MiniProgramSetService miniProgramSetService;

    @Value("${wx.miniprogram.code.env.version:develop}")
    private String envVersion;


    /**
     * 生成小程序二维码
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<String> getWxaCodeUnlimit(@RequestBody MiniProgramQrCodeRequest request) {
        String imageURL = "";
        request.setEnv_version(envVersion);
        MiniProgramSet miniProgramSet = miniProgramSetService.getOneByType(Constants.ZERO);
        // 开关启用的时候，采取生成小程序码
        if(miniProgramSet.getStatus().equals(Constants.yes)){
            String code = StringUtils.isNotBlank(request.getCode()) ? request.getCode() : request.getScene();
            request.setCode(null);
            imageURL = wechatApiUtil.getWxaCodeUnlimit(request, "PUBLIC",code);
        }
        return BaseResponse.success(imageURL);
    }



    /**
     * 社交分销里面生成小程序码
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<String> distributionMiniProgram(@RequestBody DistributionMiniProgramRequest request) {
        //实际分享url
        String url = null;
        //用于生成redisKey的url，因shareId每次分享不同，故做区分
        String codeUrl = null;
        String businessModel = PluginType.O2O == request.getPluginType() ? "o2o" : "sbc";
        // 渠道不为空，分享赚的场景，为店内分享或店外分享
        if (StringUtils.isNotBlank(request.getChannel())) {
            // 店外分享
            if (request.getChannel().equals("mall")) {
                //无shareId的url
                codeUrl = String.format("/pages/package-B/goods/goods-details/index?skuId=%s&channel=%s&inviteeId=%s&businessModel=%s",
                        request.getSkuId(), request.getChannel(), request.getInviteeId(), businessModel);
                url = String.format(codeUrl + "&shareId=%s", request.getShareId());
            } else {
                // 店内分享,要有spuId
                url = String.format("/shop-index/goods-detail/%s/%s/%s/%s?channel=%s&businessModel=%s",
                        request.getInviteeId(), request.getSpuId(), request.getSkuId(),
                        request.getShareId(), request.getChannel(), businessModel);
                //无shareId的url
                codeUrl = String.format("/shop-index/goods-detail/%s/%s/%s?channel=%s&businessModel=%s",
                        request.getInviteeId(), request.getSpuId(), request.getSkuId(),
                        request.getChannel(), businessModel);
            }
        }

        // tag标识不为空，说明是邀新或是分享店铺
        if (StringUtils.isNotBlank(request.getTag())) {
            // 分享店铺
            if (request.getTag().equals("shop")) {
                url = String.format("/shop-index-c/%s/%s", request.getInviteeId(), request.getShareId());
                //无shareId的url
                codeUrl = String.format("/shop-index-c/%s", request.getInviteeId());
            } else {
                // 邀新
                //无shareId的url
                codeUrl = String.format("/pages/package-A/login/register/index?inviteeId=%s",
                        request.getInviteeId());
                url = String.format(codeUrl + "&shareId=%s", request.getShareId());
            }
        }

        // 积分商品,链接到积分商品详情，不带分销信息
        if(StringUtils.isNotBlank(request.getPointsGoodsId())){
            //无shareId的url
            codeUrl = String.format("/pages/package-B/goods/goods-details/index?skuId=%s&pointsGoodsId=%s&shareUserId=%s&businessModel=%s",
                    request.getSkuId(), request.getPointsGoodsId(), request.getShareUserId(), businessModel);
            url = String.format(codeUrl + "&shareId=%s", request.getShareId());
        }

        // 把链接加密，生成redisKey，作为参数传递，用来生成小程序码
        String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
        String codeKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(codeUrl, "utf-8")).toUpperCase().substring(16));
        if (StringUtils.isNotBlank(redisKey)) {
            redisService.setString(redisKey, url, 15000000L);
        }

        MiniProgramQrCodeRequest miniProgramQrCodeRequest = new MiniProgramQrCodeRequest();
        miniProgramQrCodeRequest.setIs_hyaline(Boolean.TRUE);
        miniProgramQrCodeRequest.setPage("pages/sharepage/sharepage");
        // 添加分销标识，方便小程序解析
        miniProgramQrCodeRequest.setScene("FX" + redisKey);
        miniProgramQrCodeRequest.setCode(codeKey);
        return getWxaCodeUnlimit(miniProgramQrCodeRequest);
    }


    /**
     * 社区团购里面生成小程序码
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<String> communityMiniProgram(@RequestBody CommunityMiniProgramRequest request) {
        //实际分享url
        String url = null;
        //用于生成redisKey的url，因shareId每次分享不同，故做区分
        String codeUrl = null;
        String businessModel = "sbc";
        //无shareId的url
        codeUrl = String.format("/pages/package-D/community/group-activity-detail/index?activityId=%s&shareUserId=%s&businessModel=%s",
                request.getActivityId(), request.getLeaderId(), businessModel);
        url = String.format(codeUrl + "&shareId=%s", request.getShareId());

        // 把链接加密，生成redisKey，作为参数传递，用来生成小程序码
        String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
        String codeKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(codeUrl, "utf-8")).toUpperCase().substring(16));
        if (StringUtils.isNotBlank(redisKey)) {
            redisService.setString(redisKey, url, 15000000L);
        }

        MiniProgramQrCodeRequest miniProgramQrCodeRequest = new MiniProgramQrCodeRequest();
        miniProgramQrCodeRequest.setIs_hyaline(Boolean.TRUE);
        miniProgramQrCodeRequest.setPage("pages/sharepage/sharepage");
        // 添加社区团购标识，方便小程序解析
        miniProgramQrCodeRequest.setScene("CG" + redisKey);
        miniProgramQrCodeRequest.setCode(codeKey);
        return getWxaCodeUnlimit(miniProgramQrCodeRequest);
    }

    /**
     * 商品详情页分享生成小程序码
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<String> getMiniProgramQrCodeWithShareUserId(@RequestBody ShareMiniProgramRequest request) {
        //用于生成redisKey的url，因shareId每次分享不同，故做区分
        String codeUrl = String.format("/pages/package-B/goods/goods-details/index?skuId=%s&shareUserId=%s",
                request.getSkuId(), request.getShareUserId());
        //实际url
        String url = String.format(codeUrl + "&shareId=%s", request.getShareId());

        //把链接加密，生成redisKey，作为参数传递，用来生成小程序码
        String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
        String codeKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(codeUrl, "utf-8")).toUpperCase().substring(16));

        if (StringUtils.isNotBlank(redisKey)) {
            redisService.setString(redisKey, url, 15000000L);
        }

        MiniProgramQrCodeRequest miniProgramQrCodeRequest = new MiniProgramQrCodeRequest();
        miniProgramQrCodeRequest.setIs_hyaline(Boolean.TRUE);
        miniProgramQrCodeRequest.setPage("pages/sharepage/sharepage");
        // 添加分享标识，方便小程序解析
        miniProgramQrCodeRequest.setScene("SHARE:" + redisKey);
        miniProgramQrCodeRequest.setCode(codeKey);
        return getWxaCodeUnlimit(miniProgramQrCodeRequest);
    }

    @Override
    public BaseResponse<MiniProgramTokenResponse> getMiniProgramAccessToken() {
        return BaseResponse.success(MiniProgramTokenResponse.builder().
                accessToken(wechatApiUtil.getAccessToken("PUBLIC")).build());
    }

    @Override
    public BaseResponse<MiniProgramTokenResponse> getMiniProgramAccessTokenCleanRedis() {
        return BaseResponse.success(MiniProgramTokenResponse.builder().
                accessToken(wechatApiUtil.getAccessTokenCleanRedis("PUBLIC")).build());
    }

    @Override
    public BaseResponse<byte[]> getWxaCodeBytesUnlimit(@RequestBody MiniProgramQrCodeRequest request) {
        return BaseResponse.success(wechatApiUtil.getWxaCodeBytesUnlimit(request,"PUBLIC"));
    }

    @Override
    public BaseResponse<Boolean> getMiniProgramStatus() {
        MiniProgramSet miniProgramSet = miniProgramSetService.getOneByType(Constants.ZERO);
        Integer status = miniProgramSet.getStatus();
        return BaseResponse.success(Objects.equals(status, NumberUtils.INTEGER_ZERO));
    }
}

