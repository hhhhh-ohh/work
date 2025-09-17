package com.wanmi.ares.report.flow.model.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName ReplayFlowDayUserInfoRequest
 * @Description
 * @Author lvzhenwei
 * @Date 2019/8/20 17:36
 **/
@Data
@Builder
public class ReplayFlowDayUserInfoRequest extends BaseRequest {

    private static final long serialVersionUID = -7693673901101972241L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * flow_day表关联id
     */
    private String flowDayId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 对应日期
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate flowDate;

    /**
     * 对应日期--开始日期
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate startFlowDate;

    /**
     * 对应日期--结束日期
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate endFlowDate;

    /**
     * 用户类型：0:总访客用户，1：sku访客用户
     */
    private Integer userType;

    /**
     * 公司id标识
     */
    private String companyIdFlag;
}
