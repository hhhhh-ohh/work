package com.wanmi.sbc.customer.provider.impl.child;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.child.CustomerChildProvider;
import com.wanmi.sbc.customer.api.request.child.CustomerChildSaveRequest;
import com.wanmi.sbc.customer.api.request.child.CustomerChildUpdateRequest;
import com.wanmi.sbc.customer.api.response.child.CustomerChildResponse;
import com.wanmi.sbc.customer.api.response.child.SchoolResponse;
import com.wanmi.sbc.customer.child.model.root.CustomerChild;
import com.wanmi.sbc.customer.child.model.root.School;
import com.wanmi.sbc.customer.child.service.CustomerChildService;
import com.wanmi.sbc.customer.child.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerChildController implements CustomerChildProvider {
    @Autowired
    private CustomerChildService customerChildService;
    @Autowired
    private SchoolService schoolService;

    @Override
    public BaseResponse addChild(@RequestBody @Valid CustomerChildSaveRequest request) {
        customerChildService.saveChild(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<List<CustomerChildResponse>> listChild(@RequestParam String customerId) {
        List<CustomerChild> list = customerChildService.findChildsByCustomerId(customerId);
        // 将 CustomerChild 列表转换为 CustomerChildResponse 列表
        List<CustomerChildResponse> customerChildResponses = KsBeanUtil.convertList(list, CustomerChildResponse.class);
        return BaseResponse.success(customerChildResponses);
    }

    @Override
    public BaseResponse updateChildSchool(@RequestBody CustomerChildUpdateRequest request){
        customerChildService.updateChildSchool(request);
        return BaseResponse.SUCCESSFUL();
    }

    public BaseResponse<List<SchoolResponse>> getSchoolByAreaId(@RequestParam  String areaId){
        List<School> schoolList = schoolService.findByAreaId(Long.parseLong(areaId));
        List<SchoolResponse> schoolResponses = KsBeanUtil.convertList(schoolList, SchoolResponse.class);
        return BaseResponse.success(schoolResponses);
    }
}
