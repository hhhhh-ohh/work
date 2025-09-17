package com.wanmi.sbc.distribute;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * @Author : baijz
 * @Date : 2019/3/4 14 49
 * @Description : 分销员等级
 */
@Tag(name =  "分销员等级API" ,description = "DistributorLevelController")
@RestController
@Validated
@RequestMapping("/distributor/level")
public class DistributorLevelController {

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 查询分销员等级信息
     * @return
     */
    @Schema(description = "查询分销员等级信息")
    @RequestMapping(value = "/getByCustomerId",method = RequestMethod.GET)
    public BaseResponse<DistributorLevelByCustomerIdResponse> getByCustomerId(){
        return distributionService.getByCustomerId(commonUtil.getOperatorId());
    }
}
