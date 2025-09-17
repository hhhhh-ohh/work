package com.wanmi.sbc.customer.quicklogin.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * @program: sbc-micro-service
 * @description: 微信小程序用户信息
 * @create: 2020-05-22 15:44
 **/
@Data
@Entity
@Table(name = "wechat_quick_login")
public class WeChatQuickLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String Id;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "union_id")
    private String unionId;

    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;
}