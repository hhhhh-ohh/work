package com.wanmi.sbc.goods.storegoodstab.model.root;

import com.wanmi.sbc.common.annotation.CanEmpty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * <p>商品详情模板关联实体</p>
 * @author: sunkun
 * @Date: 2018-10-16
 */
@Data
@Entity
@Table(name = "goods_tab_rela")
public class GoodsTabRela implements Serializable {

    private static final long serialVersionUID = -4468105154141495740L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * spu标识
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 详情模板id
     */
    @Column(name = "tab_id")
    private Long tabId;

    /**
     * 内容详情
     */
    @Column(name = "tab_detail")
    @CanEmpty
    private String tabDetail;
}
