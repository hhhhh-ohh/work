package com.wanmi.sbc.pagemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.MagicCouponInfoRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivitySceneResponse;
import com.wanmi.sbc.setting.api.provider.baseconfig.BaseConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.pagemanage.PageInfoExtendQueryProvider;
import com.wanmi.sbc.setting.api.provider.pagemanage.PageInfoExtendSaveProvider;
import com.wanmi.sbc.setting.api.request.baseconfig.BaseConfigListRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendAddRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendByCouponInfoRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendModifyRequest;
import com.wanmi.sbc.setting.api.response.pagemanage.PageInfoExtendByIdResponse;
import com.wanmi.sbc.setting.bean.vo.BaseConfigVO;
import com.wanmi.sbc.setting.bean.vo.PageInfoExtendVO;
import com.wanmi.sbc.third.WechatSetService;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Objects;

/**
 * 页面投放服务
 * Created by daiyitian on 17/4/12.
 */
@Slf4j
@Tag(name = "PageInfoExtendController", description = "页面投放服务")
@RestController
@Validated
@RequestMapping("/pageInfoExtend")
public class PageInfoExtendController {

    @Autowired
    private PageInfoExtendQueryProvider pageInfoExtendQueryProvider;

    @Autowired
    private PageInfoExtendSaveProvider pageInfoExtendSaveProvider;

    @Autowired
    private CouponActivityQueryProvider couponActivityQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private BaseConfigQueryProvider baseConfigQueryProvider;

    @Autowired
    private WechatSetService wechatSetService;

    private final static String MAIN_PAGE_TYPE = "index";
    private final static String o2oPageType = "o2o";
    private final static String COUPON_RECOMMEND = "yes";

    /**
     * 页面投放详情
     *
     * @param request 查询参数
     * @return 页面投放详情
     */
    @Operation(summary = "页面投放详情")
    @PostMapping("/query")
    public BaseResponse<PageInfoExtendByIdResponse> query(@Valid @RequestBody PageInfoExtendByIdRequest request) {
        byte[] miniQrCode = null;
        String base64Head = "data:image/png;base64,";
        PageInfoExtendVO pageInfoExtend = null;
        Long storeId = commonUtil.getStoreId();
        boolean isMiniOpen = Constants.yes.equals(wechatSetService.getBySet(TerminalType.MINI).getStatus());
        //新增优惠卷推广
        if(Objects.nonNull(request.getCouponRecommend()) && COUPON_RECOMMEND.equals(request.getCouponRecommend())) {
            pageInfoExtend = pageInfoExtendQueryProvider.findByCouponInfo(PageInfoExtendByCouponInfoRequest.builder()
                    .couponId(request.getPageId())
                    .activityId(request.getActivityId()).build())
                    .getContext().getPageInfoExtend();
            //新增
            if (Objects.isNull(pageInfoExtend)) {
                //填充基本属性
                pageInfoExtend = new PageInfoExtendVO();
                pageInfoExtend.setPageId(UUIDUtil.getUUID());
                pageInfoExtend.setPageCode(request.getPageCode());
                pageInfoExtend.setPageType(request.getPageType());
                pageInfoExtend.setPlatform(request.getPlatform());
                pageInfoExtend.setStoreId(commonUtil.getStoreId());
                // 填充券ID
                pageInfoExtend.setCouponId(request.getPageId());
                // 填充活动ID
                pageInfoExtend.setActivityId(request.getActivityId());
                //填充默认渠道链接
                pageInfoExtend.setUrl(String.format("pages/package-A/customer/my-coupon-detail/index?id=%s&aid=%s", request.getPageId(), request.getActivityId()));
                //微信小程序码生成
                MiniProgramQrCodeRequest codeRequest = new MiniProgramQrCodeRequest();
                codeRequest.setPage("pages/package-A/customer/my-coupon-detail/index");
                codeRequest.setWidth(375);
                // 根据券id和活动id获取小程序二维码scene
                CouponActivitySceneResponse sceneResponse = couponActivityQueryProvider.getSceneByCouponInfo(MagicCouponInfoRequest.builder()
                        .activityId(request.getActivityId())
                        .couponId(request.getPageId()).build())
                        .getContext();
                if (StringUtils.isNotBlank(sceneResponse.getScene())) {
                    codeRequest.setScene(sceneResponse.getScene());
                    if(isMiniOpen) {
                        miniQrCode = wechatAuthProvider.getWxaCodeBytesUnlimit(codeRequest).getContext();
                    }
                    if(Objects.nonNull(miniQrCode)){
                        pageInfoExtend.setMiniProgramQrCode(base64Head.concat(Base64.getEncoder().encodeToString(miniQrCode)));
                        //保存数据
                        pageInfoExtendSaveProvider.add(KsBeanUtil.convert(pageInfoExtend, PageInfoExtendAddRequest.class));
                    }
                }
            }
        }
        else {
            //未改动的业务代码
            request.setStoreId(commonUtil.getStoreId());
            pageInfoExtend = pageInfoExtendQueryProvider.findById(request).getContext().getPageInfoExtend();

            if(Objects.nonNull(pageInfoExtend) && !(commonUtil.getOperator().getPlatform() == Platform.PLATFORM)){
                if(Objects.isNull(storeId) ||
                        Objects.isNull(pageInfoExtend.getStoreId()) ||
                        !pageInfoExtend.getStoreId().equals(storeId)){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }

            MiniProgramQrCodeRequest codeRequest = new MiniProgramQrCodeRequest();
            codeRequest.setPage("pages/sharepage/sharepage");
            codeRequest.setWidth(375);

            //初始化时，首页获取店铺首页程序二维码
            if (isMiniOpen && Objects.isNull(pageInfoExtend) && MAIN_PAGE_TYPE.equalsIgnoreCase(request.getPageType())) {
                if (Objects.nonNull(request.getStoreId())) {//店铺首页
                    codeRequest.setScene(String.format("/store-main/%s", request.getStoreId()));
                    miniQrCode = wechatAuthProvider.getWxaCodeBytesUnlimit(codeRequest).getContext();
                } else {
                    //平台首页
                    codeRequest.setScene("/");
                    miniQrCode = wechatAuthProvider.getWxaCodeBytesUnlimit(codeRequest).getContext();
                }
            }

            //非首页的页面，根据redis是否过期存储
            String key = "TF:".concat(request.getPageId());
            if ((!MAIN_PAGE_TYPE.equalsIgnoreCase(request.getPageType())) && (!redisService.hasKey(key))) {
                String storePram = "";
                if (Objects.nonNull(request.getStoreId())) {
                    storePram = String.format("&storeId=%s", request.getStoreId());
                }
                String param = String.format("?pageType=%s&pageCode=%s%s", request.getPageType(), request.getPageCode(), storePram);
                redisService.setString(key, param, 15000000L);
                codeRequest.setScene(key);
                if(isMiniOpen) {
                    miniQrCode = wechatAuthProvider.getWxaCodeBytesUnlimit(codeRequest).getContext();
                }
                //如果只更新小程序二维码
                if (Objects.nonNull(pageInfoExtend) && miniQrCode != null) {
                    pageInfoExtend.setMiniProgramQrCode(base64Head.concat(Base64.getEncoder().encodeToString(miniQrCode)));
                    pageInfoExtendSaveProvider.add(KsBeanUtil.convert(pageInfoExtend, PageInfoExtendAddRequest.class));
                }
            }

            //新增
            if (Objects.isNull(pageInfoExtend)) {
                pageInfoExtend = new PageInfoExtendVO();
                pageInfoExtend.setPageId(request.getPageId());
                pageInfoExtend.setPageCode(request.getPageCode());
                pageInfoExtend.setPageType(request.getPageType());
                pageInfoExtend.setPlatform(request.getPlatform());
                pageInfoExtend.setStoreId(request.getStoreId());
                pageInfoExtend.setStoreId(commonUtil.getStoreId());
                if (miniQrCode != null) {
                    pageInfoExtend.setMiniProgramQrCode(base64Head.concat(Base64.getEncoder().encodeToString(miniQrCode)));
                }
                pageInfoExtend.setUseType(0);
                //店铺
                if (Objects.nonNull(pageInfoExtend.getStoreId())) {
                    //首页
                    if (MAIN_PAGE_TYPE.equalsIgnoreCase(pageInfoExtend.getPageType())) {
                        if ("pc".equals(request.getPlatform())){
                            pageInfoExtend.setUrl(String.format("/store-main/%s", request.getStoreId()));
                        } else {
                            pageInfoExtend.setUrl(String.format("pages/package-A/store/store-main/index?storeId=%s", request.getStoreId()));
                        }
                    } else {
                        pageInfoExtend.setUrl(String.format("pages/package-B/x-site/page-link/index?pageType=%s&pageCode=%s&storeId=%s", pageInfoExtend.getPageType(), pageInfoExtend.getPageCode(), request.getStoreId()));
                    }
                } else {//商城
                    //o2o首页
                    if (o2oPageType.equalsIgnoreCase(pageInfoExtend.getPageType())) {
                        pageInfoExtend.setUrl(String.format("pages/index/index?model=o2o"));
                    } else if (!MAIN_PAGE_TYPE.equalsIgnoreCase(pageInfoExtend.getPageType())) {
                        //非首页
                        pageInfoExtend.setUrl(String.format("pages/package-B/x-site/page-link/index?pageType=%s&pageCode=%s", pageInfoExtend.getPageType(), pageInfoExtend.getPageCode()));
                    }
                }
                pageInfoExtendSaveProvider.add(KsBeanUtil.convert(pageInfoExtend, PageInfoExtendAddRequest.class));
            }
        }
        //格式加前缀
        String web = urlComplete(request);
        pageInfoExtend.setUrl(web.concat(Objects.toString(pageInfoExtend.getUrl(), "")));
        return BaseResponse.success(PageInfoExtendByIdResponse.builder().pageInfoExtend(pageInfoExtend).build());
    }

    /**
     * url 格式加前缀
     * @param request
     * @return
     */
    public String urlComplete( PageInfoExtendByIdRequest request){
        String web;
        BaseConfigVO baseConfig = baseConfigQueryProvider.list(new BaseConfigListRequest()).getContext().getBaseConfigVOList().get(0);
        if("pc".equals(request.getPlatform())){
            web = baseConfig.getPcWebsite();
        }else {
            web = baseConfig.getMobileWebsite();
        }
        if((!web.endsWith("\\")) && (!web.endsWith("/"))){
            web = web.concat("/");
        }
        return  web;
    }

    /**
     * 编辑页面投放
     */
    @Operation(summary = "编辑页面投放")
    @PutMapping(value = "/modify")
    public BaseResponse edit(@Valid @RequestBody PageInfoExtendModifyRequest request) {
        BaseResponse<PageInfoExtendByIdResponse> response = pageInfoExtendQueryProvider
                .findById(PageInfoExtendByIdRequest.builder().pageId(request.getPageId()).build());
        if (Objects.isNull(response.getContext().getPageInfoExtend())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        //越权校验
        Long storeId = response.getContext().getPageInfoExtend().getStoreId();

        //如果推广信息关联的店铺id是平台的 则不做校验
        if(storeId != null && !storeId.equals(Constants.BOSS_DEFAULT_STORE_ID)){
            commonUtil.checkStoreId(storeId);
        }
        return pageInfoExtendSaveProvider.modify(request);
    }
}
