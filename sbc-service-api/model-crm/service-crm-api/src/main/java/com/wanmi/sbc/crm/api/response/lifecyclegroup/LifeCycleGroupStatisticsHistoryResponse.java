package com.wanmi.sbc.crm.api.response.lifecyclegroup;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.LifeCycleGroupDayStatisticsVO;
import com.wanmi.sbc.crm.bean.vo.SimpleCustomGroupVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName LifeCycleGroupHistoryResponse
 * @description
 * @Author zhanggaolei
 * @Date 2021/2/26 13:45
 * @Version 1.0
 **/
@Data
@Schema
public class LifeCycleGroupStatisticsHistoryResponse extends BasicResponse {

    @Schema(description = "标题头")
    private List<SimpleCustomGroupVO> titleList;

    @Schema(description = "数据列表")
    private List<LifeCycleGroupDayStatisticsVO> dataList;


}

