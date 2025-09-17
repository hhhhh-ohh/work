package com.wanmi.sbc.empower.api.response.vop.category;

import lombok.Data;

/**
 * @author EDZ
 * @className VopGetCategoryResponse1
 * @description 京东vop分类详情返回报文
 * @date 2021/5/10 16:53
 **/
@Data
public class VopGetCategoryResponse {
    /**
     * 分类ID
     */
    private Long catId;

    /**
     * 父分类id
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类级别
     */
    private Integer catClass;

    /**
     * 是否有效 1是0否
     */
    private Integer state;
}
