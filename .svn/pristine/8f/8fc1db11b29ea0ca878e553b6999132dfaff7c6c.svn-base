package com.wanmi.sbc.goods.xsitegoodscate.model.root;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 * <p>魔方商品分类表实体类</p>
 *
 * @author xufeng
 * @date 2022-02-21 14:54:31
 */
@Data
@Entity
@Table(name = "xsite_goods_cate")
public class XsiteGoodsCate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 页面唯一值
     */
    @Column(name = "page_code")
    private String pageCode;

    /**
     * 分类uuid
     */
    @Column(name = "cate_uuid")
    private String cateUuid;

    /**
     * skuIDs
     */
    @Column(name = "goods_info_ids")
    private String goodsInfoIds;

    /**
     * spuIDs
     */
    @Column(name = "goods_ids")
    private String goodsIds;

}