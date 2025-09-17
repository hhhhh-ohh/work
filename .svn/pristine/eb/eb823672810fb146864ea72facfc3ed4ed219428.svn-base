package com.wanmi.sbc.marketing.api.response.grouponcenter;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.GrouponCenterVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>H5-拼团活动首页列表查询列表结果</p>
 * @author chenli
 * @date 2019-05-21 14:02:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponCenterListResponse extends BasicResponse {
    private static final long serialVersionUID = -4606614341156713129L;

    /**
     * 拼团活动首页列表查询列表结果
     */
    @Schema(description = "拼团活动首页列表查询列表结果")
    private MicroServicePage<GrouponCenterVO> grouponCenterVOList = new MicroServicePage<>(new ArrayList<>());
}
