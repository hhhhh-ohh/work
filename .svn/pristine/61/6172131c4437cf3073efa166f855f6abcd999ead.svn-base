package com.wanmi.sbc.marketing.api.response.drawrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>抽奖记录表分页结果</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 抽奖记录表分页结果
     */
    @Schema(description = "抽奖记录表分页结果")
    private MicroServicePage<DrawRecordVO> drawRecordVOPage;

    /**
     * 活动名称
     */
    @Schema(description = "活动名称")
    private String activityName;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;
}
