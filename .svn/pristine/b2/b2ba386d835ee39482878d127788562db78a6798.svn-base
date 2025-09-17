package com.wanmi.sbc.goods.storecate.model.root;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * 商品-店铺分类关联实体类
 * Created by bail on 2017/11/13.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store_cate_goods_rela")
public class StoreCateGoodsRela implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 商品标识
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 店铺分类标识
     */
    @Column(name = "store_cate_id")
    private Long storeCateId;

}

