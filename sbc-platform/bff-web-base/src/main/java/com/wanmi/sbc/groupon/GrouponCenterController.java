package com.wanmi.sbc.groupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.grouponcenter.GrouponCenterQueryProvider;
import com.wanmi.sbc.marketing.api.request.grouponcenter.GrouponCenterListRequest;
import com.wanmi.sbc.marketing.api.response.grouponcenter.GrouponCenterListResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * 拼团活动首页Controller
 */
@RestController
@Validated
@RequestMapping("/groupon/center")
@Tag(name = "GrouponCenterController", description = "S2B web公用-拼团营销")
public class GrouponCenterController {

    @Autowired
    private GrouponCenterQueryProvider grouponCenterQueryProvider;

    /**
     * 查询拼团活动spu列表信息
     * @return
     */
    @Schema(description = "查询拼团活动spu列表信息")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public BaseResponse<GrouponCenterListResponse> list(@RequestBody GrouponCenterListRequest request){

        return grouponCenterQueryProvider.list(request);
    }

}
