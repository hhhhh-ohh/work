package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-10-15
 * \* Time: 19:23
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@Schema
public class RfmStatisticVo extends BasicResponse {

    @Schema(description = "RFM分布数据标题")
    private String title;

    @Schema(description = "RFM分布数据类型")
    private int type;

    @Schema(description = "RFM分布数据")
    private List<DataVo> data;

    @Schema(description = "创建时间")
    private Date updateTime;
}
