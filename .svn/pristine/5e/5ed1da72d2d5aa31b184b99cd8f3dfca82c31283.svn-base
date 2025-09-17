package com.wanmi.sbc.common.base;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * @author EDZ
 * @className CustomGenerator
 * @description 如果set了主键id，就使用这个id插入，如果不set主键id，则利用数据库本身的自增策略指定id
 * @date 2021/8/16 19:30
 **/
public class CustomGenerator extends IdentityGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor s, Object obj) throws HibernateException {
        Object id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);

        if (id != null && Integer.valueOf(id.toString()) > 0) {
            return id;
        } else {
            return super.generate(s, obj);
        }
    }
}
