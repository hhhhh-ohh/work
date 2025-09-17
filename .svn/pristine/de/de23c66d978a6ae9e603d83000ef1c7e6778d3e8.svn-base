package com.wanmi.sbc.ares;

import com.wanmi.ares.provider.CustomerBaseQueryProvider;
import com.wanmi.ares.provider.CustomerDistrQueryServiceProvider;
import com.wanmi.ares.request.CustomerQueryRequest;
import com.wanmi.ares.request.customer.CustomerDistrQueryRequest;
import com.wanmi.ares.view.customer.CustomerAreaDistrResponse;
import com.wanmi.ares.view.customer.CustomerLevelDistrResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.configure.ThriftClientConfig;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;

/**
 * <p>客户分布统计报表Controller</p>
 * Created by of628-wenzhi on 2017-10-18-下午2:16.
 */
@Tag(name = "CustomerBaseQueryController", description = "客户信息统计")
@RestController
@Validated
@RequestMapping("/ares/customerQuery")
@Slf4j
@EnableConfigurationProperties(ThriftClientConfig.class)
public class CustomerBaseQueryController {

    @Autowired
    private CustomerBaseQueryProvider customerBaseQueryProvider;

    @PostMapping("/customer-total")
    @Operation(summary = "查询会员总数")
    public BaseResponse<Long> queryCustomerTotal(@RequestBody CustomerQueryRequest request){
        return BaseResponse.success(this.customerBaseQueryProvider.queryCustomerPhoneCount(request));
    }


}
