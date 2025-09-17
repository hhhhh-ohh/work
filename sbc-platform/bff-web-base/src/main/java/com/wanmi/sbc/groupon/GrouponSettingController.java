package com.wanmi.sbc.groupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.grouponsetting.GrouponSettingQueryProvider;
import com.wanmi.sbc.marketing.api.response.grouponsetting.GrouponSettingPageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * Created by chenli on 2019/5/21.
 */

/**
 * 拼团设置控制器
 */
@RestController
@Validated
@RequestMapping("/groupon/setting")
@Tag(description= "S2B web公用-拼团设置", name = "GrouponSettingController")
public class GrouponSettingController {

    @Autowired
    private GrouponSettingQueryProvider grouponSettingQueryProvider;

    /**
     * 查询拼团设置
     * @return
     */
    @Schema(description = "查询拼团设置")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public BaseResponse<GrouponSettingPageResponse> findOne() {
       return grouponSettingQueryProvider.getSetting();
    }
}
