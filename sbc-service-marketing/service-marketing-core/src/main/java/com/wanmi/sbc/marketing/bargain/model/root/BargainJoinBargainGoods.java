package com.wanmi.sbc.marketing.bargain.model.root;

import com.wanmi.sbc.marketing.bargaingoods.model.root.BargainGoods;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

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
@NamedEntityGraph(name = "bargainGoods", attributeNodes = {@NamedAttributeNode("bargainGoods")})
public class BargainJoinBargainGoods extends BargainBase {

    @ManyToOne
    @JoinColumn(name = "bargain_goods_id", insertable = false, updatable = false)
    private BargainGoods bargainGoods;

}