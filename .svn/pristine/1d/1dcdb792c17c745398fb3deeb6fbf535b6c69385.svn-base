package com.wanmi.sbc.customer.follow.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * 店铺客户收藏实体类
 * Created by dyt on 2017/5/17.
 */
@Data
@Entity
@Table(name = "store_customer_follow")
public class StoreCustomerFollow implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 收藏编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    /**
     * 客户编号
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 店铺编号
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 公司信息ID
     */
    @Column(name = "company_info_id")
    private Long companyInfoId;

    /**
     * 关注时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "follow_time")
    private LocalDateTime followTime;


    /**
     * 终端
     */
    @Column(name = "terminal_source")
    private TerminalSource terminalSource;
}
