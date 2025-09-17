package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/10 10:26
 * @description <p> </p>
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsGoodsBrandPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 批量品牌编号
     */
    @Schema(description = "批量品牌编号")
    private List<Long> brandIds;

    /**
     * and 精准查询，品牌名称
     */
    @Schema(description = "and 精准查询，品牌名称")
    private String brandName;

    /**
     * and 模糊查询，品牌名称
     */
    @Schema(description = "and 模糊查询，品牌名称")
    private String likeBrandName;

    /**
     * and 精准查询，品牌昵称
     */
    @Schema(description = "and 精准查询，品牌昵称")
    private String nickName;

    /**
     * and 模糊查询，品牌昵称
     */
    @Schema(description = "and 模糊查询，品牌昵称")
    private String likeNickName;

    /**
     * 模糊查询，品牌拼音
     */
    @Schema(description = "模糊查询，品牌拼音")
    private String likePinYin;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

    /**
     * 非品牌编号
     */
    @Schema(description = "非品牌编号")
    private Long notBrandId;

    /**
     * 关键字查询，可能含空格
     */
    @Schema(description = "关键字查询，可能含空格")
    private String keywords;

    @Schema(description = "批量品牌id")
    private List<Long> idList;

    /**
     * 是否推荐品牌
     */
    @Schema(description = "是否推荐品牌")
    private DefaultFlag recommendFlag;
}
