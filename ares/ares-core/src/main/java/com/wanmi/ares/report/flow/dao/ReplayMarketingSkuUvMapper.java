package com.wanmi.ares.report.flow.dao;


import com.wanmi.ares.report.flow.model.root.ReplaySkuFlowUserInfo;
import com.wanmi.ares.request.mq.MarketingSkuSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplayMarketingSkuUvMapper {

    int insertByList(MarketingSkuSource marketingSkuSource);

}