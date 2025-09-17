package com.wanmi.sbc.statisticssetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.statisticssetting.StatisticsSettingProvider;
import com.wanmi.sbc.setting.api.request.statisticssetting.QmStatisticsSettingRequest;
import com.wanmi.sbc.setting.api.response.statisticssetting.QmStatisticsSettingResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * @className StatisticsSettingController
 * @description 数谋基础设置
 * @author 张文昌
 * @date 2022/1/6 17:09
 */
@Tag(name = "StatisticsSettingController", description = "数谋基础设置")
@RestController
@Validated
@RequestMapping("/statistics-setting")
public class StatisticsSettingController {

  @Autowired private StatisticsSettingProvider statisticsSettingProvider;

  /**
   * 修改千米数谋基础配置
   *
   * @param request
   * @return
   */
  @Operation(summary = "修改千米数谋基础配置")
  @RequestMapping(value = "/modify-qm", method = RequestMethod.PUT)
  public BaseResponse modifyQmSetting(@RequestBody @Valid QmStatisticsSettingRequest request) {
    return statisticsSettingProvider.modifyQmSetting(request);
  }

  /**
   * 查询千米数谋基础配置
   *
   * @return
   */
  @Operation(summary = "查询千米数谋基础配置")
  @RequestMapping(value = "/get-qm", method = RequestMethod.GET)
  public BaseResponse<QmStatisticsSettingResponse> getQmSetting() {
    return statisticsSettingProvider.getQmSetting();
  }
}
