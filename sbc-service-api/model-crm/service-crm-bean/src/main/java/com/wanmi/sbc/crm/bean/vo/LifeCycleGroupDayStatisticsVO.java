package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName LifeCycleGroupDayStatistics
 * @description
 * @Author zhanggaolei
 * @Date 2021/2/26 13:59
 * @Version 1.0
 **/
@Data
@Schema
public class LifeCycleGroupDayStatisticsVO extends BasicResponse {

    @Schema(description = "日期")
    private String day;

    @Schema(description = "当前日期的数据列表")
    private List<SimpleLifeCycleGroupStatisticsVO> datas;

}
