package com.wanmi.sbc.goods.info.model.root;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.PluginTypeConverterListString;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhanggaolei
 * @className PluginTest
 * @description
 * @date 2022/10/18 15:54
 **/
@Data
@Entity
@Table(name = "plugin_test")
public class PluginTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Convert(converter = PluginTypeConverterListString.class)
    @Column(name = "plugin_types")
    private List<PluginType> pluginTypes;

}
