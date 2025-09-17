package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.response.BossGoodsEvaluateResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * @Author lvzhenwei
 * @Description 商品评价开关设置
 * @Date 14:19 2019/4/3
 * @Param 
 * @return 
 **/
@Tag(name = "SystemPointsConfigController", description = "WEB端-商品评价开关设置API")
@RestController
@Validated
@RequestMapping("/systemGoodsEvaluateConfig")
public class SystemGoodsEvaluateConfigController {

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    /**
     * 商品评价开关设置是否开启
     * @return
     */
    @RequestMapping(value = "/isGoodsEvaluate", method = RequestMethod.GET)
    public BaseResponse<BossGoodsEvaluateResponse> isGoodsEvaluate() {
        return auditQueryProvider.isGoodsEvaluate();
    }
}
