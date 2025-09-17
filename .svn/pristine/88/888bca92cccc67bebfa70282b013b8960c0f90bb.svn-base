package com.wanmi.sbc.setting.pagemanage.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.setting.api.request.baseconfig.BaseConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendModifyRequest;
import com.wanmi.sbc.setting.baseconfig.model.root.BaseConfig;
import com.wanmi.sbc.setting.baseconfig.service.BaseConfigService;
import com.wanmi.sbc.setting.pagemanage.model.root.PageInfoExtend;
import com.wanmi.sbc.setting.pagemanage.repository.PageInfoExtendRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 物流信息服务
 * Created by dyt on 2020/4/17.
 */
@Service
@Transactional(readOnly = true, timeout = 10)
public class PageInfoExtendService {

    @Autowired
    private PageInfoExtendRepository pageInfoExtendRepository;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private BaseConfigService baseConfigService;

    @Autowired
    private RedisUtil redisUtil;

    private final static String MAIN_PAGE_TYPE = "index";


    @Transactional
    public void modifyExtendById(PageInfoExtendModifyRequest request) {
        PageInfoExtend oldExtend = pageInfoExtendRepository.findById(request.getPageId()).orElse(null);
        if (oldExtend != null) {
            oldExtend.setBackgroundPic(request.getBackGroundPic());
            oldExtend.setUseType(request.getUseType());
            oldExtend.setSources(request.getSources());
            pageInfoExtendRepository.save(oldExtend);
        }
    }

    public PageInfoExtend findById(String pageId){
        Optional<PageInfoExtend> optional = pageInfoExtendRepository.findById(pageId);
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 根据券ID和活动ID查询推广详情
     * @param couponId 券ID
     * @param activityId 活动ID
     * @return
     */
    public PageInfoExtend findByCouponAndActivity(String couponId, String activityId){
        Optional<List<PageInfoExtend>> optional = pageInfoExtendRepository.findByCouponIdAndActivityId(couponId, activityId);
        return (optional.isPresent() && CollectionUtils.isNotEmpty(optional.get())) ? optional.get().get(0) : null;
    }

    public void add(PageInfoExtend pageInfoExtend) {
        pageInfoExtendRepository.save(pageInfoExtend);
    }
    /**
     * 推广详情页查询
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public PageInfoExtend findExtendById(PageInfoExtendByIdRequest request) {
        Optional<PageInfoExtend> pageInfoExtend =
                pageInfoExtendRepository.findByActivityIdAndMarketingType(request.getPageId(),request.getMarketingType());
        BaseConfig baseConfig =
                baseConfigService.list(BaseConfigQueryRequest.builder().build()).get(0);
        String urlPrefix = baseConfig.getMobileWebsite();
        if(!urlPrefix.endsWith("/")) {
            urlPrefix = urlPrefix.concat("/");
        }
        String urlSuffix = request.getUrl();
        String url = "/".concat(urlSuffix);
        // 判断PlugInType类型，O2O类型需要生成后缀判断
        if(PluginType.O2O.equals(request.getPluginType())){
            urlSuffix = urlSuffix.concat("&businessModel=o2o");
            url = url.concat("&businessModel=o2o");
        }
        if (pageInfoExtend.isPresent()) {
            PageInfoExtend extend = pageInfoExtend.get();
            extend.setUrl(urlPrefix.concat(urlSuffix));
            return pageInfoExtend.get();
        }

        String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
        String miniQrCode = this.getWxaCodeUnlimit(url, redisKey,request.getPluginType());
        PageInfoExtend extend = new PageInfoExtend();
        extend.setStoreId(request.getStoreId());
        extend.setPlatform(request.getPlatform());
        extend.setUrl(urlSuffix);
        extend.setPageId(UUIDUtil.getUUID_16());
        extend.setMiniProgramQrCode(redisKey);
        extend.setBackgroundPic(request.getBackgroundPic());
        extend.setImageUrl(miniQrCode);
        extend.setActivityId(request.getPageId());
        extend.setUseType(request.getUseType());
        extend.setMarketingType(request.getMarketingType());
        pageInfoExtendRepository.save(extend);
        //返回全路径
        extend.setUrl(urlPrefix.concat(urlSuffix));
        return extend;
    }

    /**
     * 获取小程序码
     * @param url
     * @param redisKey
     * @return
     */
    private String getWxaCodeUnlimit(String url,String redisKey,PluginType pluginType){
        MiniProgramQrCodeRequest codeRequest = new MiniProgramQrCodeRequest();
        // 判断PlugInType类型，O2O类型需要生成后缀判断
        if(PluginType.O2O.equals(pluginType)){
            url = url.concat("&businessModel=o2o");
        }
        if (StringUtils.isNotBlank(redisKey)) {
            redisUtil.setString(redisKey, url, 15000000L);
        }
        codeRequest.setPage("pages/sharepage/sharepage");
        codeRequest.setScene("EX" + redisKey);
        codeRequest.setWidth(500);
        return wechatAuthProvider.getWxaCodeUnlimit(codeRequest).getContext();
    }
}
