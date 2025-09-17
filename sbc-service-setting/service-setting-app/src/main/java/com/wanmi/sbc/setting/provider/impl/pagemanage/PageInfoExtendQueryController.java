package com.wanmi.sbc.setting.provider.impl.pagemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.setting.api.provider.pagemanage.PageInfoExtendQueryProvider;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendByCouponInfoRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.response.pagemanage.PageInfoExtendByIdResponse;
import com.wanmi.sbc.setting.baseconfig.service.BaseConfigService;
import com.wanmi.sbc.setting.bean.vo.PageInfoExtendVO;
import com.wanmi.sbc.setting.pagemanage.model.root.PageInfoExtend;
import com.wanmi.sbc.setting.pagemanage.service.PageInfoExtendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>页面投放查询接口</p>
 * @author dyt
 * @date 2020-04-16
 */
@RestController
public class PageInfoExtendQueryController implements PageInfoExtendQueryProvider {


    @Autowired
    private PageInfoExtendService pageInfoExtendService;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

//    @Override
//    public BaseResponse<PageInfoExtendByIdResponse> findById(@RequestBody PageInfoExtendByIdRequest request) {
//        PageInfoExtendVO vo = KsBeanUtil.convert(pageInfoExtendService.findById(request), PageInfoExtendVO.class);
//        BaseConfig baseConfig = baseConfigService.list(BaseConfigQueryRequest.builder().build()).get(0);
//        String web;
//        if("pc".equals(request.getPlatform())){
//            web = baseConfig.getPcWebsite();
//        }else {
//            web = baseConfig.getMobileWebsite();
//        }
//        if((!web.endsWith("\\")) && (!web.endsWith("/"))){
//            web = web.concat("/");
//        }
//        vo.setUrl(web.concat(Objects.toString(vo.getUrl(), "")));
//        return BaseResponse.success(new PageInfoExtendByIdResponse(vo));
//    }

    @Override
    public BaseResponse<PageInfoExtendByIdResponse> findById(@RequestBody PageInfoExtendByIdRequest request) {
        PageInfoExtend pageInfoExtend = pageInfoExtendService.findById(request.getPageId());
        PageInfoExtendVO vo = null;
        if (pageInfoExtend != null){
            vo = KsBeanUtil.convert(pageInfoExtend, PageInfoExtendVO.class);
        }
        return BaseResponse.success(new PageInfoExtendByIdResponse(vo));
    }

    @Override
    public BaseResponse<PageInfoExtendByIdResponse> findByCouponInfo(@RequestBody PageInfoExtendByCouponInfoRequest request) {
        PageInfoExtend pageInfoExtend = pageInfoExtendService.findByCouponAndActivity(request.getCouponId(), request.getActivityId());
        PageInfoExtendVO vo = null;
        if (pageInfoExtend != null){
            vo = KsBeanUtil.convert(pageInfoExtend, PageInfoExtendVO.class);
        }
        return BaseResponse.success(new PageInfoExtendByIdResponse(vo));
    }

    @Override
    public BaseResponse<PageInfoExtendByIdResponse> findExtendById(@RequestBody PageInfoExtendByIdRequest request) {
        PageInfoExtend pageInfoExtend = pageInfoExtendService.findExtendById(request);
        PageInfoExtendVO pageInfoExtendVO = new PageInfoExtendVO();
        BeanUtils.copyProperties(pageInfoExtend,pageInfoExtendVO);
        Boolean isOpen = wechatAuthProvider.getMiniProgramStatus().getContext();
        if(isOpen){
            pageInfoExtendVO.setImageUrl(StringUtils.EMPTY);
        }
        return BaseResponse.success(new PageInfoExtendByIdResponse(pageInfoExtendVO));
    }
}
