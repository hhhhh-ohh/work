package com.wanmi.sbc.customer.child.model.root;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
/**
 * 学校信息实体类
 *
 * @author
 * @date
 */
@Data
@Entity
@Table(name = "school")
public class School implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学校主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Integer schoolId;

    /**
     * 学校名称
     */
    @Column(name = "school_name", length = 150)
    private String schoolName;

    /**
     * 学校编码
     */
    @Column(name = "school_code", length = 50)
    private String schoolCode;

    /**
     * 省ID
     */
    @Column(name = "province_id")
    private Long provinceId;

    /**
     * 市ID
     */
    @Column(name = "city_id")
    private Long cityId;

    /**
     * 区ID
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 省名称
     */
    @Column(name = "province_name", length = 20)
    private String provinceName;

    /**
     * 市名称
     */
    @Column(name = "city_name", length = 20)
    private String cityName;

    /**
     * 区名称
     */
    @Column(name = "area_name", length = 20)
    private String areaName;

    /**
     * 校徽
     */
    @Column(name = "badge_code", length = 50)
    private String badgeCode;

    /**
     * 校徽库存
     */
    @Column(name = "badge_code_stock")
    private Integer badgeCodeStock;

}
