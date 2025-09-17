package com.wanmi.sbc.customer.address.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * 客户配送地址
 * Created by CHENLI on 2017/4/13.
 */
@Data
@Entity
@Table(name = "customer_delivery_address")
public class CustomerDeliveryAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 收货地址ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "delivery_address_id")
    private String deliveryAddressId;
    /**
     * 客户ID
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 收货人
     */
    @Column(name = "consignee_name")
    private String consigneeName;

    /**
     * 收货人手机号码
     */
    @Column(name = "consignee_number")
    private String consigneeNumber;

    /**
     * 省
     */
    @Column(name = "province_id")
    private Long provinceId;

    /**
     * 市
     */
    @Column(name = "city_id")
    private Long cityId;

    /**
     * 区
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 街道
     */
    @Column(name = "street_id")
    private Long streetId;

    /**
     * 详细地址
     */
    @Column(name = "delivery_address")
    private String deliveryAddress;

    /**
     * 是否是默认地址 0：否 1：是
     */
    @Column(name = "is_defalt_address")
    @Enumerated
    private DefaultFlag isDefaltAddress;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "create_person")
    private String createPerson;

    /**
     * 修改时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Column(name = "update_person")
    private String updatePerson;

    /**
     * 删除时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "delete_time")
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Column(name = "delete_person")
    private String deletePerson;

    /**
     * 经度
     */
    @Column(name = "latitude")
    private BigDecimal latitude;

    /**
     * 纬度
     */
    @Column(name = "longitude")
    private BigDecimal longitude;

    /**
     * 门牌号
     */
    @Column(name = "house_num")
    private String houseNum;


}
