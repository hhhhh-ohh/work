package com.wanmi.ares.request.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.ares.base.BaseRequest;
import com.wanmi.ares.enums.StatisticsType;
import com.wanmi.ares.enums.VideoTimeType;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * @author zhaiqiankun
 * @className VideoCompanyDayRequest
 * @description 视频号订单公司维度每天统计查询条件
 * @date 2022/4/8 9:31
 **/
@Data
@ToString(callSuper = true)
public class VideoPageRequest extends BaseRequest {
    private static final long serialVersionUID = 499225925985768919L;

    /**
     * 公司ID
     */
    @Schema(description = "公司ID", hidden = true)
    private Long companyInfoId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate beginDate;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate endDate;

    /**
     * 排序字段 (默认日期排序)
     */
    @Schema(description = "排序字段 (默认日期排序)")
    private String sortName = "";

    /**
     * 排序: 默认倒序
     */
    @Schema(description = "排序: 默认倒序")
    private String sortOrder = "";
    /**
     * 页码
     */
    @Schema(description = "页码")
    private int pageNum = 0;
    /**
     * 页面大小
     */
    @Schema(description = "页面大小")
    private int pageSize = 20;

    /**
     * 查询报表时间类型,0:当天,1:最近7天,2:最近30天,3:自然月
     */
    @NotNull
    @Schema(description = "查询报表时间类型,0:当天,1:最近7天,2:最近30天,3:自然月")
    private VideoTimeType videoTimeType;

    /**
     * 查询自然月时，标识年月，格式:2022-04
     */
    @Schema(description = "查询自然月时，标识年月，格式:2022-04")
    private String dateStr;

    /**
     * 统计类型,0:视频号报表,1:店铺报表
     */
    @NotNull
    @Schema(description = "统计类型,0:视频号报表,1:店铺报表,2:天报表,3:概况")
    private StatisticsType statisticsType;

    public Pageable getPageable() {
        return PageRequest.of(pageNum, pageSize);
    }

}
