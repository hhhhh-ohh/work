package com.wanmi.ares.request.wechatvideo;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * @description 汇总销售概况
 * @author malianfeng
 * @date 2022/4/29 10:13
 */
@Schema
@Data
public class VideoOverviewQueryRequest implements Serializable {

    private static final long serialVersionUID = -5910244294263710373L;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    @NotNull
    private Long companyInfoId;
}
