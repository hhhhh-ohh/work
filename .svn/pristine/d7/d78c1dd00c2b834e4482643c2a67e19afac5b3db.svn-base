package com.wanmi.sbc.setting.provider.impl.thirdexpresscompany;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.thirdexpresscompany.ThirdExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyQueryRequest;
import com.wanmi.sbc.setting.api.response.expresscompany.ExpressCompanyListResponse;
import com.wanmi.sbc.setting.api.response.thirdexpresscompany.ThirdExpressCompanyListResponse;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.setting.bean.vo.ThirdExpressCompanyVO;
import com.wanmi.sbc.setting.thirdexpresscompany.root.ThirdExpressCompany;
import com.wanmi.sbc.setting.thirdexpresscompany.service.ThirdExpressCompanyService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 第三方平台物流公司查询服务
 * @author malianfeng
 * @date 2022/4/26 17:44
 */
@RestController
@Validated
public class ThirdExpressCompanyQueryController implements ThirdExpressCompanyQueryProvider {

    @Autowired private ThirdExpressCompanyService thirdExpressCompanyService;

    @Override
    public BaseResponse<ThirdExpressCompanyListResponse> list(@RequestBody ThirdExpressCompanyQueryRequest request) {
        List<ThirdExpressCompany> thirdExpressCompanies = thirdExpressCompanyService.list(request);
        if (CollectionUtils.isNotEmpty(thirdExpressCompanies)) {
            List<ThirdExpressCompanyVO> newList = thirdExpressCompanies.stream()
                    .map(entity -> thirdExpressCompanyService.wrapperVo(entity)).collect(Collectors.toList());
            return BaseResponse.success(new ThirdExpressCompanyListResponse(newList));
        }
        return BaseResponse.success(new ThirdExpressCompanyListResponse(Collections.emptyList()));
    }
}
