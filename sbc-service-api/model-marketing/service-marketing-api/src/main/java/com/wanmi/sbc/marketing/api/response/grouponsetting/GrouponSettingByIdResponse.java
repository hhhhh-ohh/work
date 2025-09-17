package com.wanmi.sbc.marketing.api.response.grouponsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.GrouponSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）拼团活动信息表信息response</p>
 * @author groupon
 * @date 2019-05-15 14:19:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponSettingByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 拼团活动信息表信息
     */
    private GrouponSettingVO grouponSettingVO;
}
