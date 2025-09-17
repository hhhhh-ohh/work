package com.wanmi.sbc.marketing.preferential.model.root;

import com.wanmi.sbc.marketing.bean.enums.GiftType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author edz
 * @className MarketingPreferentialLevel
 * @description 活动区间阶梯信息
 * @date 2022/11/17 17:43
 **/
@Entity
@Table(name = "marketing_preferential_level")
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPreferentialLevel implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferentialLevelId;

    /**
     * 加价购活动ID
     */
    private Long marketingId;

    /**
     * 满金额
     */
    private BigDecimal fullAmount;

    /**
     * 满数量
     */
    private Long fullCount;

    /**
     * 0:可全选  1：选一个
     */
    private GiftType preferentialType;

}
