package com.wanmi.sbc.marketing.api.response.grouponcate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.GrouponCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>拼团活动信息表列表结果</p>
 *
 * @author groupon
 * @date 2019-05-15 14:13:58
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponCateListResponse extends BasicResponse {
    private static final long serialVersionUID = 993622332252325350L;

    /**
     * 拼团活动信息表列表结果
     */
    private List<GrouponCateVO> grouponCateVOList;
}
