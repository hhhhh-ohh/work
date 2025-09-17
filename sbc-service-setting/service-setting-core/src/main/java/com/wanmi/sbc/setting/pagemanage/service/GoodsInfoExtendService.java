package com.wanmi.sbc.setting.pagemanage.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.setting.api.request.baseconfig.BaseConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendDeleteByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendModifyRequest;
import com.wanmi.sbc.setting.baseconfig.model.root.BaseConfig;
import com.wanmi.sbc.setting.baseconfig.service.BaseConfigService;
import com.wanmi.sbc.setting.pagemanage.model.root.GoodsInfoExtend;
import com.wanmi.sbc.setting.pagemanage.repository.GoodsInfoExtendRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author houshuai
 * @date 2021/5/26 15:35
 * @description 商品推广 service
 */
@Service
public class GoodsInfoExtendService {

    @Autowired
    private GoodsInfoExtendRepository goodsInfoExtendRepository;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private BaseConfigService baseConfigService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 推广详情页查询
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public GoodsInfoExtend findByGoodsInfoId(GoodsInfoExtendByIdRequest request) {
        String goodsInfoId = request.getGoodsInfoId();
        Optional<GoodsInfoExtend> extendOptional = goodsInfoExtendRepository.findById(goodsInfoId);
        if (extendOptional.isPresent()) {
            return extendOptional.get();
        }
        BaseConfig baseConfig =
                baseConfigService.list(BaseConfigQueryRequest.builder().build()).get(0);
        String urlPrefix = baseConfig.getMobileWebsite();
        if(!urlPrefix.endsWith("/")) {
            urlPrefix = urlPrefix.concat("/");
        }

        String urlSuffix = "pages/package-B/goods/goods-details/index?skuId=".concat(goodsInfoId);
        String url = "/pages/package-B/goods/goods-details/index?skuId=".concat(goodsInfoId);
        // 判断PlugInType类型，O2O类型需要生成后缀判断
        if(PluginType.O2O.equals(request.getPluginType())){
            urlSuffix = urlSuffix.concat("&businessModel=o2o");
            url = url.concat("&businessModel=o2o");
        }

        String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
        String miniQrCode = this.getWxaCodeUnlimit(goodsInfoId, redisKey, request.getPluginType());
        GoodsInfoExtend goodsInfoExtend = new GoodsInfoExtend();
        goodsInfoExtend.setStoreId(request.getStoreId());
        goodsInfoExtend.setGoodsInfoId(goodsInfoId);
        goodsInfoExtend.setPlatform(request.getPlatform());
        goodsInfoExtend.setUrl(urlPrefix.concat(urlSuffix));
        goodsInfoExtend.setGoodsId(request.getGoodsId());
        goodsInfoExtend.setMiniProgramQrCode(redisKey);
        goodsInfoExtend.setImageUrl(miniQrCode);
        return goodsInfoExtendRepository.save(goodsInfoExtend);
    }

    /**
     * 获取小程序码
     * @param goodsInfoId
     * @param redisKey
     * @return
     */
    private String getWxaCodeUnlimit(String goodsInfoId,String redisKey, PluginType pluginType){
        MiniProgramQrCodeRequest codeRequest = new MiniProgramQrCodeRequest();
        String url = "/pages/package-B/goods/goods-details/index?skuId=".concat(goodsInfoId);
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

    /**
     * 修改渠道和背景图
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyGoodsInfoExtend(GoodsInfoExtendModifyRequest request) {
        Optional<GoodsInfoExtend> goodsInfoExtendOpt =
                goodsInfoExtendRepository.findById(request.getGoodsInfoId());
        goodsInfoExtendOpt.ifPresent(
                goodsInfoExtend -> {
                    goodsInfoExtend.setSources(request.getSources());
                    goodsInfoExtend.setBackgroundPic(request.getBackGroundPic());
                    goodsInfoExtend.setUseType(request.getUseType());
                    goodsInfoExtendRepository.save(goodsInfoExtend);
                });
    }

    /**
     * 删除数据
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoodsInfoExtend(GoodsInfoExtendDeleteByIdRequest request) {
        Optional<GoodsInfoExtend> goodsInfoExtendOpt = goodsInfoExtendRepository.findByGoodsId(request.getGoodsId());
        goodsInfoExtendOpt.ifPresent(goodsInfoExtend -> {
            String goodsInfoId = goodsInfoExtend.getGoodsInfoId();
            if(!StringUtils.equals(request.getGoodsInfoId(),goodsInfoId)){
                goodsInfoExtendRepository.deleteById(goodsInfoId);
            }
        });
    }

}
