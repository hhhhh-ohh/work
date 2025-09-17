package com.wanmi.sbc.marketing.pluginconfig.model.root;

import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import lombok.Data;

import jakarta.persistence.*;

/**
 * @author zhanggaolei
 * @className MarketingPluginBean
 * @description
 * @date 2021/6/2 11:34
 */
@Data
@Entity
@Table(name = "marketing_plugin_config")
public class MarketingPluginConfig {

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 营销类型 */
    @Column(name = "marketing_type")
    @Enumerated(EnumType.STRING)
    private MarketingPluginType marketingType;

    /** 排序 */
    @Column(name = "sort")
    private Integer sort;

    /** 共存 */
    @Column(name = "coexist")
    private String coexist;

    /** 描述 */
    @Column(name = "description")
    private String description;
}
