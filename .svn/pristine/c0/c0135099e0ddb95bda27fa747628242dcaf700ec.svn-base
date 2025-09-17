package com.wanmi.sbc.crm.api.response.recommendcatemanage;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.RecommendCateManageInfoVO;
import com.wanmi.sbc.crm.bean.vo.RecommendCateManageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName RecommendCateManageInfoListResponse
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/19 14:38
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateManageInfoListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 分类推荐管理信息
     */
    @Schema(description = "分类推荐管理信息")
    private List<RecommendCateManageInfoVO> recommendCateManageInfoVOList;
}
