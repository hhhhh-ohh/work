package com.wanmi.sbc.setting.country.model.root;

import lombok.Data;

import jakarta.persistence.*;

/**
 * @author houshuai
 * @date 2021/4/26 15:30
 * @description <p> 国家地区查询 </p>
 */
@Data
@Entity
@Table(name = "platform_country")
public class PlatformCountry {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 国家地区名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 国家地区简称
     */
    @Column(name = "short_name")
    private String shortName;

}
