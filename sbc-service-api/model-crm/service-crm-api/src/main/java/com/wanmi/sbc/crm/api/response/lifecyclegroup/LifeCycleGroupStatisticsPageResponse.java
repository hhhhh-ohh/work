package com.wanmi.sbc.crm.api.response.lifecyclegroup;

import com.wanmi.sbc.common.base.BasicResponse;
import com.github.pagehelper.PageInfo;
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
public class LifeCycleGroupStatisticsPageResponse extends BasicResponse {

    @Schema(description = "标题头")
    private List<SimpleCustomGroupVO> titleList;

    @Schema(description = "分页的数据")
    private PageInfo<LifeCycleGroupDayStatisticsVO> pageInfo;

}

