package com.wanmi.sbc.marketing.common;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;

/**
 * 实体类基类
 * @author Administrator
 */
@Data
@MappedSuperclass
public class BaseBean implements Serializable {

    private static final long serialVersionUID = -1876963132849205369L;
    /**
     * 扩展属性
     */
    @Transient
    protected Map<String,Object> others;

}
