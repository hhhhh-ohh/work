package com.wanmi.sbc.goods.brand.model.root;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * 二次签约品牌实体类
 * @author wangchao
 */
@Data
@Entity
@Table(name = "contract_brand_audit")
public class ContractBrandAudit implements Serializable {


    private static final long serialVersionUID = -3592550199199139473L;

    /**
     * 签约品牌分类
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_brand_id")
    private Long contractBrandId;


    /**
     * 店铺主键
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 品牌编号
     */
    @Column(name = "brand_id",insertable=false,updatable=false)
    private Long brandId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    @JsonManagedReference
    private GoodsBrand goodsBrand;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "check_brand_id")
    @JsonManagedReference
    private CheckBrand checkBrand;


    /**
     * 授权图片路径
     */
    @Column(name = "authorize_pic")
    private String authorizePic;

    /**
     * 是否需要删除标识
     */
    @Column(name = "del_flag")
    private DeleteFlag deleteFlag;
}
