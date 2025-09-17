package com.wanmi.sbc.setting.api.provider.statisticssetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.statisticssetting.QmStatisticsSettingRequest;
import com.wanmi.sbc.setting.api.response.statisticssetting.QmStatisticsSettingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @className StatisticsSettingProvider
 * @description
 * @author 张文昌
 * @date 2022/1/6 18:25
 */
@FeignClient(value = "${application.setting.name}", contextId = "StatisticsSettingProvider")
public interface StatisticsSettingProvider {

  /**
   * 修改qm数谋基本配置
   *
   * @author 张文昌
   * @param qmStatisticsSettingRequest 修改qm数谋基本配置 {@link QmStatisticsSettingRequest}
   * @return {@link BaseResponse}
   */
  @PostMapping("/setting/${application.setting.version}/statisticssetting/modifyqmwetting")
  BaseResponse modifyQmSetting(
      @RequestBody @Valid QmStatisticsSettingRequest qmStatisticsSettingRequest);

  /**
   * 查询qm数谋基本配置
   *
   * @author 张文昌
   * @return qm数谋基本配置 {@link QmStatisticsSettingResponse}
   */
  @PostMapping("/setting/${application.setting.version}/statisticssetting/getqmsetting")
  BaseResponse<QmStatisticsSettingResponse> getQmSetting();

  /**
   * 查询qm数谋基本配置 走缓存
   *
   * @author wur
   * @return qm数谋基本配置 {@link QmStatisticsSettingResponse}
   */
  @PostMapping("/setting/${application.setting.version}/statisticssetting/getqmsetting/cache")
  BaseResponse<QmStatisticsSettingResponse> getQmSettingCache();
}
