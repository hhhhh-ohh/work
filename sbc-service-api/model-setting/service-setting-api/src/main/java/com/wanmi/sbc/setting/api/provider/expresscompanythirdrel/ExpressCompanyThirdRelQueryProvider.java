package com.wanmi.sbc.setting.api.provider.expresscompanythirdrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.*;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyListBySellTypeResponse;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyThirdRelDetailListResponse;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyThirdRelListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 平台和第三方代销平台物流公司映射查询服务
 * @author malianfeng
 * @date 2022/4/26 17:44
 */
@FeignClient(value = "${application.setting.name}", contextId = "ExpressCompanyThirdRelQueryProvider")
public interface ExpressCompanyThirdRelQueryProvider {

    /**
     * 查询第三方平台物流公司列表
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/expresscompanythirdrel/list")
    BaseResponse<ExpressCompanyThirdRelListResponse> list(@RequestBody @Valid ExpressCompanyThirdRelQueryRequest request);

    /**
     * 查询第三方平台物流公司列表
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/expresscompanythirdrel/get-with-detail")
    BaseResponse<ExpressCompanyThirdRelDetailListResponse> getWithDetail(@RequestBody @Valid ExpressCompanyThirdRelDetailQueryRequest request);

    /**
     * 查询第三方平台物流公司列表
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/express-company-third-rel/list-with-detail")
    BaseResponse<ExpressCompanyThirdRelDetailListResponse> listWithDetail(@RequestBody @Valid ThirdExpressCompanyListRequest request);

    /**
     * @description  根据平台物流公司Code查询绑定的第三方公司物流公司信息
     * @author  wur
     * @date: 2022/4/28 10:53
     * @param request
     * @return
     **/
    @PostMapping("/setting/${application.setting.version}/express-company-third-rel/get-detail-code")
    BaseResponse<ExpressCompanyThirdRelDetailListResponse> getWithDetailByCode(@RequestBody @Valid ThirdExpressCompanyListByCodeRequest request);

    /**
     * @description
     * @author  wur
     * @date: 2022/4/28 11:07
     * @param request 
     * @return 
     **/
    @PostMapping("/setting/${application.setting.version}/express-company-third-rel/get-system-bySellType")
    BaseResponse<ExpressCompanyListBySellTypeResponse> getExpressCompanyBySellType(@RequestBody @Valid ExpressCompanyListBySellTypeRequest request);

}
