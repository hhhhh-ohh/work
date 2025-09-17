package com.wanmi.ares.source.service;

import com.wanmi.ares.report.customer.dao.CustomerLevelMapper;
import com.wanmi.ares.source.model.root.CustomerLevel;
import com.wanmi.ares.source.model.root.ReplayStoreLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 客户等级
 */
@Slf4j
@Service
public class CustomerLevelService {

    @Resource
    private CustomerLevelMapper customerLevelMapper;

    /**
     * 主键批量查询品台客户等级
     * @param levelIds
     * @return
     */
    public List<CustomerLevel> queryCustomerLevelByIds(List<String> levelIds){
        return customerLevelMapper.queryCustomerLevelByIds(levelIds);
    }

    /**
     * @Author lvzhenwei
     * @Description 查询店铺会员等级
     * @Date 11:23 2019/9/20
     * @Param [levelIds]
     * @return java.util.List<com.wanmi.ares.source.model.root.ReplayStoreLevel>
     **/
    public List<ReplayStoreLevel> queryStoreCustomerLevelByIds(List<String> levelIds){
        return customerLevelMapper.queryStoreCustomerLevelByIds(levelIds);
    }


}
