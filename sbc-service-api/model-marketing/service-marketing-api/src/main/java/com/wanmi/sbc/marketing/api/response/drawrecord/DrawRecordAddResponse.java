package com.wanmi.sbc.marketing.api.response.drawrecord;

import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>抽奖记录表新增结果</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordAddResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的抽奖记录表信息
     */
    @Schema(description = "已新增的抽奖记录表信息")
    private DrawRecordVO drawRecordVO;
}
