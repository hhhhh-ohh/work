package com.wanmi.sbc.setting.stockWarning.model.root;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stock_warning")
public class StockWarning extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 商品skuId
     */
    @Column(name = "sku_id")
    private String skuId;

    /**
     * 商家id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 是否预警
     */
    @Column(name = "is_warning")
    private BoolFlag isWarning;

    /**
     * 删除标志 0:未删除 1:删除
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Column(name = "creat_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

}
