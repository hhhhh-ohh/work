package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 待审核品牌
 * Created by sunkun on 2017/11/1.
 */
@Schema
@Data
public class CheckBrandVO extends BasicResponse {

    private static final long serialVersionUID = 8884626958558309374L;

    /**
     * 待审核品牌分类
     */
    @Schema(description = "待审核品牌分类")
    private Long checkBrandId;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String name;

    /**
     * 品牌昵称
     */
    @Schema(description = "品牌名称")
    private String nickName;

    /**
     * 品牌logo
     */
    @Schema(description = "品牌logo")
    private String logo;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 审核状态(0:未审核,1:通过,2:驳回)
     */
    @Schema(description = "审核状态", contentSchema = com.wanmi.sbc.goods.bean.enums.GoodsBrandCheckStatus.class)
    private Integer status = 0;

}
