package com.wanmi.ares.request.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author zhaiqiankun
 * @className VideoDayQueryPageRequest
 * @description 查询报表数据
 * @date 2022/4/7 18:58
 **/
@Schema
@Data
public class VideoQueryPageRequest implements Serializable {

    private static final long serialVersionUID = -5910244294263710373L;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 排序: 默认倒序
     */
    private String sortOrder = "";

    @Schema(description = "页码")
    private int pageNum = 0;

    @Schema(description = "查询数据的数量")
    private int pageSize = 20;

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
     * 查询自然月时，标识年月，格式:2022-04
     */
    @Schema(description = "查询自然月时，标识年月，格式:2022-04")
    private String dateStr;

    /**
     * 查询自然月时，标识年
     */
    @Schema(description = "查询自然月时，标识年")
    private Integer year;

    /**
     * 查询自然月时，标识年
     */
    @Schema(description = "查询自然月时，标识月")
    private Integer month;

    /**
     * 排序字段 (默认日期排序)
     */
    @Schema(description = "排序字段 (默认日期排序)")
    private String sortName = "";

    public Pageable getPageable() {
        return PageRequest.of(pageNum, pageSize);
    }


}
