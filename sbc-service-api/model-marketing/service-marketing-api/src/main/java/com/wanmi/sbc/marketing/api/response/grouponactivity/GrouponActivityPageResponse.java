package com.wanmi.sbc.marketing.api.response.grouponactivity;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>拼团活动信息表分页结果</p>
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponActivityPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 拼团活动信息表分页结果
     */
    private MicroServicePage<GrouponActivityVO> grouponActivityVOPage;

}
