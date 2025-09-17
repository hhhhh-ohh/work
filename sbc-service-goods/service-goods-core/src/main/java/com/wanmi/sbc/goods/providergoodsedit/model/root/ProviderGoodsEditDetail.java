package com.wanmi.sbc.goods.providergoodsedit.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsEditType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

/**
 * @description 商品变更记录
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Data
@Entity
@Table(name = "provider_goods_edit_detail")
public class ProviderGoodsEditDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 商品Id
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 操作类型：操作类型：0.商品信息变更 1.价格变更 2.状态变更 3.其他变更
     */
    @Column(name = "endit_type")
    private GoodsEditType enditType;

    /**
     * 修改内容
     */
    @Column(name = "endit_content")
    private String enditContent;

    @Column(name = "del_flag")
    private DeleteFlag delFlag;
}
