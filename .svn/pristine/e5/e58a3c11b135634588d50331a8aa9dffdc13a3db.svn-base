package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goodscate.GoodsCateListRequest
 * 根据条件查询商品分类列表信息请求对象
 * @author lipeng
 * @dateTime 2018/11/1 下午3:25
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCateListByConditionRequest extends BaseQueryRequest  {

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 批量分类编号
     */
    @Schema(description = "批量分类编号")
    private List<Long> cateIds;

    /**
     * 批量分类父编号
     */
    @Schema(description = "批量分类父编号")
    private List<Long> cateParentIds;

    /**
     * 分类父编号
     */
    @Schema(description = "分类父编号")
    private Long cateParentId;

    /**
     * 模糊查询，分类路径
     */
    @Schema(description = "模糊查询，分类路径")
    private String likeCatePath;

    /**
     * 分类层级
     */
    @Schema(description = "分类层级")
    private Integer cateGrade;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

    /**
     * 是否默认
     */
    @Schema(description = "是否默认", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isDefault;

    /**
     * 父类名称
     */
    @Schema(description = "父类名称")
    private String cateName;

    /**
     * 非分类编号
     */
    @Schema(description = "非分类编号")
    private Long notCateId;

    /**
     * 关键字查询，可能含空格
     */
    @Schema(description = "关键字查询，可能含空格")
    private String keywords;

    private Boolean fillChildren;

}
