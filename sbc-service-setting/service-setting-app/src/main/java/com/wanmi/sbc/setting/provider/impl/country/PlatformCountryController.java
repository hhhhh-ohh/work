package com.wanmi.sbc.setting.provider.impl.country;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.country.PlatformCountryProvider;
import com.wanmi.sbc.setting.api.request.country.PlatformCountryQueryRequest;
import com.wanmi.sbc.setting.api.response.country.PlatformCountryListResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformCountryVO;
import com.wanmi.sbc.setting.country.model.root.PlatformCountry;
import com.wanmi.sbc.setting.country.service.PlatformCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/4/26 15:56
 * @description
 *     <p>国家地区
 */
@RestController
public class PlatformCountryController implements PlatformCountryProvider {

    @Autowired private PlatformCountryService platformCountryService;

    @Override
    public BaseResponse<PlatformCountryListResponse> findAll() {
        List<PlatformCountryVO> platformCountryList =
                platformCountryService.findPlatformCountryList();
        PlatformCountryListResponse response = new PlatformCountryListResponse(platformCountryList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<PlatformCountryListResponse> findCountryList(
            @RequestBody @Valid PlatformCountryQueryRequest request) {
        List<PlatformCountry> goodsPropertyList = platformCountryService.list(request);
        List<PlatformCountryVO> newList =
                goodsPropertyList.stream()
                        .map(entity -> platformCountryService.wrapperVo(entity))
                        .collect(Collectors.toList());
        return BaseResponse.success(new PlatformCountryListResponse(newList));
    }
}
