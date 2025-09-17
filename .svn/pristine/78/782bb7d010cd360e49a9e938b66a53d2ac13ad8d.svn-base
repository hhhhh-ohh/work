package com.wanmi.sbc.goods.brand.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 签约品牌实体类
 * Created by sunkun on 2017/10/31.
 */
@Data
public class ContractBrandBase implements Serializable {

    private static final long serialVersionUID = 8253629811866938769L;

    /**
     * 签约品牌分类
     */
    private Long contractBrandId;

    /**
     * 品牌编号
     */
    private Long brandId;

    /**
     * 商家Id
     */
    private Long storeId;

    public ContractBrandBase(Long contractBrandId, Long brandId) {
        this.contractBrandId = contractBrandId;
        this.brandId = brandId;
        this.storeId = null;
    }

    public ContractBrandBase(Long contractBrandId, Long brandId, Long storeId) {
        this.contractBrandId = contractBrandId;
        this.brandId = brandId;
        this.storeId = storeId;
    }
}
