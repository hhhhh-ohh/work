package com.wanmi.sbc.groupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.grouponcate.GrouponCateQueryProvider;
import com.wanmi.sbc.marketing.api.response.grouponcate.GrouponCateListResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * S2B的拼团分类服务
 */
@RestController
@Validated
@RequestMapping("/groupon/cate")
@Tag(name =  "S2B-web公用-拼团分类服务", description =  "GrouponCateBaseController")
public class GrouponCateBaseController {

    @Autowired
    private GrouponCateQueryProvider grouponCateQueryProvider;

    /**
     * 列表查询拼团分类信息
     *
     * @return 拼团分类分页
     */
    @Operation(summary = "列表查询拼团分类信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseResponse<GrouponCateListResponse> findGrouponCateList() {
        return grouponCateQueryProvider.list();
    }

}
