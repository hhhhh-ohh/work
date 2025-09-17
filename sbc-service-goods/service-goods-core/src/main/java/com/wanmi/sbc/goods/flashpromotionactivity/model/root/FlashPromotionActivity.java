package com.wanmi.sbc.goods.flashpromotionactivity.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>限时购活动表实体类</p>
 *
 * @author xufeng
 * @date 2022-02-14 14:54:31
 */
@Data
@Entity
@Table(name = "flash_promotion_activity")
public class FlashPromotionActivity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 限时购活动id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "activity_id")
    private String activityId;

    /**
     * 限时购活动名称
     */
    @Column(name = "activity_name")
    private String activityName;

    /**
     * 活动开始时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * 店铺ID
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 状态 0:开始 1:暂停
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 预热时间
     */
    @Column(name = "pre_time")
    private Integer preTime;

    /**
     * 删除标志，0:未删除 1:已删除
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "create_person")
    private String createPerson;

    /**
     * 更新时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Column(name = "update_person")
    private String updatePerson;

}