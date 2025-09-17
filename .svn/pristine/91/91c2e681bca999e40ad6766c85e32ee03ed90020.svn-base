package com.wanmi.sbc.marketing.provider.impl.fullreturn;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.fullreturn.FullReturnProvider;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnAddRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnIdRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnModifyRequest;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnAddResponse;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnDetailResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.fullreturn.request.MarketingFullReturnSaveRequest;
import com.wanmi.sbc.marketing.fullreturn.service.MarketingFullReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @Author: xufeng
 * @Description:
 * @Date: 2022-04-06 14:29
 */
@Validated
@RestController
public class FullReturnController implements FullReturnProvider {

    @Autowired
    private MarketingFullReturnService marketingFullReturnService;


    /**
     * @param addRequest 新增参数 {@link FullReturnAddRequest}
     * @return
     */
    @Override
    public BaseResponse<FullReturnAddResponse> add(@RequestBody @Valid FullReturnAddRequest addRequest) {
        Marketing marketing = marketingFullReturnService.addMarketingFullReturn(KsBeanUtil.convert(addRequest, MarketingFullReturnSaveRequest.class));
        return BaseResponse.success(FullReturnAddResponse.builder().marketingVO(KsBeanUtil.convert(marketing, MarketingVO.class)).build());
    }

    /**
     * @param modifyRequest 修改参数 {@link FullReturnModifyRequest}
     * @return
     */
    @Override
    public BaseResponse modify(@RequestBody @Valid FullReturnModifyRequest modifyRequest) {
        marketingFullReturnService.modifyMarketingFullReturn(Objects.requireNonNull(KsBeanUtil.convert(modifyRequest, MarketingFullReturnSaveRequest.class)));
        return BaseResponse.SUCCESSFUL();
    }
}
