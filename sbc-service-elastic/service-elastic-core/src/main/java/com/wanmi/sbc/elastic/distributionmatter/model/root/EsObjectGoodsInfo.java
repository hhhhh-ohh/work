package com.wanmi.sbc.elastic.distributionmatter.model.root;

import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import lombok.Data;

import jakarta.persistence.Enumerated;

/**
 * @author houshuai
 * @date 2021/1/8 16:51
 * @description <p> 分销素材所需goods信息 </p>
 */
@Data
public class EsObjectGoodsInfo {

    /**
     * 商品SKU编号
     */
    private String goodsInfoId;

    /**
     * 商品编号
     */
    private String goodsId;

    /**
     * 商品SKU名称
     */
    private String goodsInfoName;

    /**
     * 商品SKU编码
     */
    private String goodsInfoNo;

    /**
     * 商品图片
     */
    private String goodsInfoImg;

    /**
     * 商品条形码
     */
    private String goodsInfoBarcode;

    /**
     * 店铺id
     */
    private Long storeId;

    private Long cateId;

    private Long brandId;

    @Enumerated
    private DistributionGoodsAudit distributionGoodsAudit;

    private Long companyInfoId;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;
}
