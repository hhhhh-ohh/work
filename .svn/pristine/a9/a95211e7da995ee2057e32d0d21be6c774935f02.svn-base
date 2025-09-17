package com.wanmi.sbc.vas;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.VASEntity;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.setting.api.provider.statisticssetting.StatisticsSettingProvider;
import com.wanmi.sbc.setting.api.response.statisticssetting.QmStatisticsSettingResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.response.VASSettingResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

/**
 * @Description: 前端增值服务查询 API
 */
@Tag(name = "VASSettingController", description = "前端增值服务查询 API")
@RestController
@Validated
@RequestMapping("/vas/setting")
public class VASSettingController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private StatisticsSettingProvider statisticsSettingProvider;

    @Operation(summary = "查询所有增值服务")
    @GetMapping("/list")
    public BaseResponse<VASSettingResponse> queryAllVAS() {
        List<VASEntity> vasEntityList = commonUtil.getAllServices();
        //验证智能推荐是否开启，如果未开启则查询 OP智能推荐是否开启
        vasEntityList.forEach(vasEntity -> {
            if (Objects.equals(VASConstants.VAS_RECOMMEND_SETTING, vasEntity.getServiceName())
                    && !vasEntity.isServiceStatus()) {
                //查询是否开启op数模
                QmStatisticsSettingResponse settingResponse =
                        statisticsSettingProvider.getQmSetting().getContext();
                if (Objects.nonNull(settingResponse) && Objects.nonNull(settingResponse.getStatus()) && settingResponse.getStatus() == 1) {
                    vasEntity.setServiceStatus(Boolean.TRUE);
                }
            };
        });
        return BaseResponse.success(VASSettingResponse.builder().services(vasEntityList).build());
    }
}
