package com.wanmi.sbc.setting.api.provider.country;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.country.PlatformCountryQueryRequest;
import com.wanmi.sbc.setting.api.response.companyinfo.CompanyInfoPageResponse;
import com.wanmi.sbc.setting.api.response.country.PlatformCountryListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/4/26 15:43
 * @description
 *     <p>国家地区查询
 */
@FeignClient(value = "${application.setting.name}", contextId = "PlatformCountryProvider")
public interface PlatformCountryProvider {

    /**
     * 国家地区查询
     *
     * @return 国家地区查询 {@link PlatformCountryListResponse}
     */
    @PostMapping("/setting/${application.setting.version}/country/list")
    BaseResponse<PlatformCountryListResponse> findAll();

    /**
     * 查询国家地区列表
     *
     * @param platformCountryQueryRequest 请求参数和筛选对象 {@link PlatformCountryQueryRequest}
     * @return 国家地区列表信息 {@link PlatformCountryListResponse}
     */
    @PostMapping("/setting/${application.setting.version}/country/list-by-ids")
    BaseResponse<PlatformCountryListResponse> findCountryList(
            @RequestBody @Valid PlatformCountryQueryRequest platformCountryQueryRequest);
}
