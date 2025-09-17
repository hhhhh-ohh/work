package com.wanmi.sbc.groupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.grouponsetting.GrouponSettingQueryProvider;
import com.wanmi.sbc.marketing.api.response.grouponsetting.GrouponSettingAuditFlagResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by feitingting on 2019/5/16.
 */

/**
 * 拼团设置控制器
 */
@RestController
@Validated
@RequestMapping("/groupon/setting")
@Tag(name =  "S2B拼团设置", description =  "GrouponSettingController")
public class StoreGrouponSettingController {

    @Autowired
    private GrouponSettingQueryProvider grouponSettingQueryProvider;

    /**
     * 获取拼团商品审核状态
     */
    @Operation(summary = "获取拼团商品审核状态")
    @RequestMapping(value="/get-goods-audit-flag",method = RequestMethod.GET)
    public BaseResponse<GrouponSettingAuditFlagResponse> getGoodsAuditFlag(){
        return grouponSettingQueryProvider.getGoodsAuditFlag();
    }

}
