package com.wanmi.sbc.goods.cate.model.root;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 二次签约分类
 * @author wangchao
 */
@Data
@Entity
@Table(name = "contract_cate_audit")
public class ContractCateAudit implements Serializable {

    private static final long serialVersionUID = 5437356417345377324L;

    /**
     * 签约分类主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_cate_id")
    private Long contractCateId;

    /**
     * 店铺主键
     */
    @Column(name = "store_id")
    private Long storeId;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cate_id")
    @JsonManagedReference
    private GoodsCate goodsCate;

    /**
     * 分类扣率
     */
    @Column(name = "cate_rate")
    private BigDecimal cateRate;

    /**
     * 资质图片路径
     */
    @Column(name = "qualification_pics")
    private String qualificationPics;

    /**
     * 是否需要删除标识
     */
    @Column(name = "del_flag")
    private DeleteFlag deleteFlag;


}
