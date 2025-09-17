package com.wanmi.sbc.marketing.api.response.drawrecord;

import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）抽奖记录表信息response</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 抽奖记录表信息
     */
    @Schema(description = "抽奖记录表信息")
    private DrawRecordVO drawRecordVO;
}
