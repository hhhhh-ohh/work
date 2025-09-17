package com.wanmi.sbc.goods.api.response.grouponsharerecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GrouponShareRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）拼团分享访问记录信息response</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponShareRecordByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 拼团分享访问记录信息
     */
    @Schema(description = "拼团分享访问记录信息")
    private GrouponShareRecordVO grouponShareRecordVO;
}
