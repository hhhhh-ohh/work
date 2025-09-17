package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>预置搜索词VO</p>
 * @author weiwenhao
 * @date 2020-04-16 11:40:28
 */
@Schema
@Data
public class PresetSearchTermsVO extends BasicResponse {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 预置搜索词字
     */
    @Schema(description = "预置搜索词字")
    private String presetSearchKeyword;
}
