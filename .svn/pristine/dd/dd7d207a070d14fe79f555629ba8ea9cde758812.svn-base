package com.wanmi.sbc.marketing.bargain.model.root;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wanmi.sbc.marketing.bargaingoods.model.root.BargainGoods;

import jakarta.persistence.*;

import lombok.Data;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * <p>砍价实体类</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Data
@Entity
@Table(name = "bargain")
@DynamicInsert
@NamedEntityGraph(name = "bargainJoinGoodsInfo", attributeNodes = { @NamedAttributeNode("bargainGoods")})
public class BargainJoinGoodsInfo extends BargainBase {

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "bargain_goods_id", insertable = false, updatable = false, nullable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    @JSONField(serialize = false)
    private BargainGoods bargainGoods;

}