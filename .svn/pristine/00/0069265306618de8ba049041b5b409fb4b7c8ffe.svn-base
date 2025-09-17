package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdResponse;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.GoodsDisplayConfigGetResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.Objects;

/**
 * 获取后台配置项
 * Created by xmn on 2018/10/10.
 */
@RestController
@Validated
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private AuditQueryProvider auditQueryProvider;
    @Resource
    private WechatAuthProvider wechatAuthProvider;
    @Resource
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;


    /**
     * 前台商品列表默认展示维度（大小图、spu |sku）
     *
     * @return
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'goodsDisplayDefault'")
    @RequestMapping(value = "/goodsDisplayDefault", method = RequestMethod.GET)
    public BaseResponse<GoodsDisplayConfigGetResponse> listConfigs() {
        return auditQueryProvider.getGoodsDisplayConfigForMobile();
    }

    /**
     * 获取某个商品的小程序码
     * @return
     */
    @RequestMapping(value = "/getSkuQrCode/{skuId}", method = RequestMethod.GET)
    public BaseResponse<String> getSkuQrCode(@PathVariable String skuId) {
        // 2021.09.13 add by zhengyang
        // 前端要求URL拼接商品模式，分为O2O/SBC，根据商品PluginType判断
        // 默认SBC
        String model = "sbc";
        // 判断商品类型
        GoodsInfoListByIdResponse info = BaseResUtils.getContextFromRes(goodsInfoQueryProvider
                .getGoodsInfoById(GoodsInfoListByIdRequest.builder().goodsInfoId(skuId).build()));
        if (Objects.isNull(info) || Objects.isNull(info.getGoodsInfoVO())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(PluginType.O2O.equals(info.getGoodsInfoVO().getPluginType())){
            model = "o2o";
        }
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        String url = String.format("/goods-details/%s/%s/%s/%s", skuId, commonUtil.getOperatorId(),
                commonUtil.getShareId(""), model);
        String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
        //用于生成redisKey的url，因shareId每次分享不同，故做区分
        String codeUrl =  String.format("/goods-details/%s/%s/%s", skuId, commonUtil.getOperatorId(), model);
        String code = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(codeUrl, "utf-8")).toUpperCase().substring(16));

        if (StringUtils.isNotBlank(redisKey)) {
            redisService.setString(redisKey, url, 15000000L);
        }
        request.setPage("pages/sharepage/sharepage");
        request.setScene("NM" + redisKey);
        request.setCode(code);
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }

    /**
     * 获取某个商品的小程序码
     * @return
     */
    @RequestMapping(value = "/getSkuQrCode/unLogin/{skuId}", method = RequestMethod.GET)
    public BaseResponse<String> getSkuQrCodeForUnLogin(@PathVariable String skuId) {
        // 2021.09.13 add by zhengyang
        // 前端要求URL拼接商品模式，分为O2O/SBC，根据商品PluginType判断
        // 默认SBC
        String model = "sbc";
        // 判断商品类型
        GoodsInfoListByIdResponse info = BaseResUtils.getContextFromRes(goodsInfoQueryProvider
                .getGoodsInfoById(GoodsInfoListByIdRequest.builder().goodsInfoId(skuId).build()));
        if (Objects.isNull(info) || Objects.isNull(info.getGoodsInfoVO())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(PluginType.O2O.equals(info.getGoodsInfoVO().getPluginType())){
            model = "o2o";
        }
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        String url = String.format("/goods-details/%s/%s/%s/%s", skuId, "",
                "", model);
        String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
        //用于生成redisKey的url，因shareId每次分享不同，故做区分
        String codeUrl =  String.format("/goods-details/%s/%s/%s", skuId, "", model);
        String code = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(codeUrl, "utf-8")).toUpperCase().substring(16));

        if (StringUtils.isNotBlank(redisKey)) {
            redisService.setString(redisKey, url, 15000000L);
        }
        request.setPage("pages/sharepage/sharepage");
        request.setScene("NM" + redisKey);
        request.setCode(code);
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }

    @RequestMapping(value = "/getBargainQrCode/{bargainId}/{pluginName}", method = RequestMethod.GET)
    public BaseResponse<String> getBargainQrCode(@PathVariable String bargainId,@PathVariable String pluginName) {
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        String url = "pages/package-D/bargaining/bargain-share/index?id=" + bargainId;
        String code = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
        redisService.setString(code, url, 15000000L);
        if (StringUtils.isNotEmpty(pluginName) && pluginName.startsWith("weda")) {
            request.setPage(pluginName + "/pages/sharepage/sharepage");
        } else {
            request.setPage("pages/sharepage/sharepage");
        }
        request.setScene("BG" + bargainId);
        request.setCode(code);
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }

    @PostMapping(value = "/findByConfigType")
    public BaseResponse<SystemConfigTypeResponse> findByConfigTypeAndDelFlag(@RequestBody @Valid ConfigQueryRequest request) {
        request.setDelFlag(0);
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(request);
    }

    /**
     * @description   未登录时查询 系统配置
     * @author  wur
     * @date: 2022/8/31 17:34
     * @param request
     * @return
     **/
    @PostMapping(value = "/unlogin/findByConfigType")
    public BaseResponse<SystemConfigTypeResponse> unloginFindByConfigTypeAndDelFlag(@RequestBody @Valid ConfigQueryRequest request) {

        //目前只支持 砍价的两个配置可以不登陆就可查询
        if (!ConfigType.BARGIN_GOODS_RULE.toValue().equals(request.getConfigType())
                && !ConfigType.BARGIN_GOODS_SALE_POSTER.toValue().equals(request.getConfigType())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setDelFlag(0);
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(request);
    }
}
