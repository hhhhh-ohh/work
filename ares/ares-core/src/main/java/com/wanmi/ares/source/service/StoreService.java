package com.wanmi.ares.source.service;

import com.wanmi.ares.enums.StoreState;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.ReplayStoreMapper;
import com.wanmi.ares.report.customer.dao.StoreMapper;
import com.wanmi.ares.source.model.root.Store;
import com.wanmi.ares.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺基础信息service
 * Created by bail on 2018/1/16.
 */
@Slf4j
@Service
public class StoreService {

    @Resource
    private ReplayStoreMapper replayStoreMapper;

    /**
     * 获取店铺名称
     * @param query
     * @return
     */
    public String getStoreName(ExportQuery query){
        String storeName = "";
        if(!Constants.BOSS_ID.equals(query.getCompanyId())) {
            storeName = replayStoreMapper.findCompanyName(query.getCompanyId());
            if(org.apache.commons.lang3.StringUtils.isNotBlank(storeName)) {
                storeName = storeName.concat("_");
            }
        }
        return storeName;
    }
}
