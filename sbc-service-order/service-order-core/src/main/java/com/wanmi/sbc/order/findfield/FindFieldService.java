package com.wanmi.sbc.order.findfield;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

/**
 * @author zhaiqiankun
 * @description 查询指定字段
 * @date 2022-04-05 21:42:46
 **/
@Service
public class FindFieldService<T> {
    @Autowired
    private EntityManager entityManager;

}
