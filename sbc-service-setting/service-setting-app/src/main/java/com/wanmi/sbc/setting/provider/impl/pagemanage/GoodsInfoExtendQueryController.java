package com.wanmi.sbc.setting.provider.impl.pagemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.setting.api.provider.pagemanage.GoodsInfoExtendQueryProvider;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.response.pagemanage.GoodsInfoExtendByIdResponse;
import com.wanmi.sbc.setting.bean.vo.GoodsInfoExtendVO;
import com.wanmi.sbc.setting.pagemanage.model.root.GoodsInfoExtend;
import com.wanmi.sbc.setting.pagemanage.service.GoodsInfoExtendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houshuai
 * @date 2021/5/26 16:29
 * @description <p> 商品推广controller </p>
 */
@RestController
public class GoodsInfoExtendQueryController implements GoodsInfoExtendQueryProvider {

    @Autowired
    private GoodsInfoExtendService goodsInfoExtendService;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;


    @Override
    public BaseResponse<GoodsInfoExtendByIdResponse> findById(GoodsInfoExtendByIdRequest request) {
        GoodsInfoExtend goodsInfoExtend = goodsInfoExtendService.findByGoodsInfoId(request);
        GoodsInfoExtendVO infoExtendVO = new GoodsInfoExtendVO();
        BeanUtils.copyProperties(goodsInfoExtend,infoExtendVO);
        Boolean isOpen = wechatAuthProvider.getMiniProgramStatus().getContext();
        if(isOpen){
            infoExtendVO.setImageUrl(StringUtils.EMPTY);
        }
        GoodsInfoExtendByIdResponse response = new GoodsInfoExtendByIdResponse(infoExtendVO);
        return BaseResponse.success(response);
    }

}
