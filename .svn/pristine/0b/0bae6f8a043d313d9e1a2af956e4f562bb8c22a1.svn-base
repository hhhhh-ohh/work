package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.dto.AutoTagSelectDTO;
import com.wanmi.sbc.crm.bean.enums.RelationType;
import com.wanmi.sbc.crm.bean.enums.TagType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AutoTagSelectVO
 * @description
 * @Author lvzhenwei
 * @Date 2020/8/26 13:43
 **/
@Schema
@Data
public class AutoTagSelectVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 自动标签名称
     */
    @Schema(description = "自动标签名称")
    private String tagName;

    /**
     * 标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签
     */
    @Schema(description = "标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签")
    private TagType type;

    /**
     * 规则天数
     */
    @Schema(description = "规则天数")
    private Integer day;

    /**
     * 一级维度且或关系，0：且，1：或
     */
    @Schema(description = "一级维度且或关系，0：且，1：或")
    private RelationType relationType;

    /**
     *
     */
    private Integer count;

    private Integer maxLen;

    /**
     * 标签选择条件集合
     */
    @Schema(description = "标签选择条件集合")
    private Map<Integer, AutoTagSelectDTO> autoTagSelectMap;

    @Schema(description = "偏好类标签范围属性")
    private List<RangeParamVo> dataRange;

}
