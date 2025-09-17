package com.wanmi.sbc.crm.api.response.rfmgroupstatistics;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.RfmGroupDataVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName RfmgroupstatisticsListResponse
 * @description
 * @Author lvzhenwei
 * @Date 2019/10/15 16:59
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RfmGroupListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 系统人群分页查询结果
     */
    @Schema(description = "系统人群分页查询结果")
    private List<RfmGroupDataVo> groupDataList;

}
