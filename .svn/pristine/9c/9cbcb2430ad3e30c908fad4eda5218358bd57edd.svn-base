package com.wanmi.sbc.customer.api.provider.child;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.child.CustomerChildSaveRequest;
import com.wanmi.sbc.customer.api.request.child.CustomerChildUpdateRequest;
import com.wanmi.sbc.customer.api.response.child.CustomerChildResponse;
import com.wanmi.sbc.customer.api.response.child.SchoolResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "${application.customer.name}", contextId = "CustomerChildProvider")
public interface CustomerChildProvider {
    /**
     * 新增小孩信息
     * @param request
     * @return
     */
    @PostMapping("/customer-child/${application.customer.version}/child/add-child")
    BaseResponse addChild(@RequestBody @Valid CustomerChildSaveRequest request);

    /**
     * 查看孩子信息列表
     * @param customerId
     * @return
     */
    @GetMapping("/customer-child/${application.customer.version}/child/list-child")
    BaseResponse<List<CustomerChildResponse>> listChild(@RequestParam String customerId);

    /**
     * 修改子客户信息的学校信息
     *@param request
     * @return
     */
    @PostMapping("/customer-child/${application.customer.version}/child/update-child-school")
    BaseResponse updateChildSchool(@RequestBody CustomerChildUpdateRequest request);


    /**
     * 获取区域下的学校信息
     *@param areaId
     * @return
     */
    @GetMapping("/customer-child/${application.customer.version}/child/get-school-by-area-id")
    BaseResponse<List<SchoolResponse>> getSchoolByAreaId(@RequestParam  String areaId);
}
