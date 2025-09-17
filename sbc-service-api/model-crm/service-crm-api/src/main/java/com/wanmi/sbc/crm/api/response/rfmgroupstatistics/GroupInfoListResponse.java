package com.wanmi.sbc.crm.api.response.rfmgroupstatistics;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.GroupInfoVo;
import com.wanmi.sbc.crm.bean.vo.RfmgroupstatisticsDataVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName GroupInfoListResponse
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/1/17 13:48
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 人群信息查询结果
     */
    @Schema(description = "人群信息查询结果")
    private List<GroupInfoVo> groupInfoVoList;
}
