package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.response.GoodsDisplayConfigGetResponse;
import com.wanmi.sbc.setting.api.response.TicketAuditResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 获取后台配置项
 * Created by xmn on 2018/10/10.
 */
@Tag(name = "ConfigController", description = "获取后台配置项Api")
@RestController
@Validated
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Resource
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * 前台商品列表默认展示维度（大小图、spu|sku）
     *
     * @return
     */
    @Operation(summary = "前台商品列表默认展示维度(大小图、spu|sku)")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'goodsDisplayDefault'")
    @RequestMapping(value = "/goodsDisplayDefault", method = RequestMethod.GET)
    public BaseResponse<GoodsDisplayConfigGetResponse> listConfigs() {
        return auditQueryProvider.getGoodsDisplayConfigForPc();
    }


    /**
     * 获取商城小程序码，与平台显示的小程序码是一样的
     * @return
     */
    @Operation(summary = "获取商城小程序码")
    @RequestMapping(value = "/getPublicQrcode", method = RequestMethod.GET)
    public BaseResponse<String> getPublicQrcode() {
        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
        request.setPage("pages/index/index");
        request.setScene("123");
        return wechatAuthProvider.getWxaCodeUnlimit(request);
    }

    /**
     * 是否开启商家/供应商二次审核
     *
     * @return
     */
    @Operation(summary = "增专资质审核开关")
    @RequestMapping(value = "/ticket/audit", method = RequestMethod.GET)
    public BaseResponse<TicketAuditResponse> isTicketAudit() {

        return auditQueryProvider.isTicketAudit();
    }
}
