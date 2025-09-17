package com.wanmi.sbc.vas;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.VASEntity;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.response.VASSettingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** @Author: songhanlin @Date: Created In 14:45 2020/2/28 @Description: 增值服务 API */
@Tag(name = "VASSettingController", description = "增值服务 API")
@RestController("VASSettingController")
@Validated
@RequestMapping("/vas/setting")
public class VASSettingController {

    @Autowired private CommonUtil commonUtil;

    @Autowired private RedisUtil redisService;

    @Operation(summary = "查询所有增值服务")
    @GetMapping("/list")
    public BaseResponse<VASSettingResponse> queryAllVAS() {
        return BaseResponse.success(
                VASSettingResponse.builder().services(commonUtil.getAllServices()).build());
    }

    @Operation(summary = "查询缓存中所有增值服务")
    @GetMapping("/refresh/list")
    public BaseResponse<VASSettingResponse> queryVASList() {
        Map<String, String> vasList =
                redisService.hgetall(ConfigKey.VALUE_ADDED_SERVICES.toString());
        List<VASEntity> list =
                vasList.entrySet().stream()
                        .map(
                                m -> {
                                    VASEntity vasEntity = new VASEntity();
                                    vasEntity.setServiceName(VASConstants.fromValue(m.getKey()));
                                    vasEntity.setServiceStatus(
                                            StringUtils.equals(
                                                    VASStatus.ENABLE.toValue(), m.getValue()));
                                    return vasEntity;
                                })
                        .collect(Collectors.toList());
        return BaseResponse.success(VASSettingResponse.builder().services(list).build());
    }
}
