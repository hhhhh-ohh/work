package com.wanmi.ares.report.goods.service;

import com.wanmi.ares.enums.QueryDateCycle;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.customer.dao.CompanyInfoMapper;
import com.wanmi.ares.report.goods.dao.GoodsTotalMapper;
import com.wanmi.ares.report.goods.dao.GoodsTotalRatioMapper;
import com.wanmi.ares.report.goods.model.criteria.GoodsQueryCriteria;
import com.wanmi.ares.report.goods.model.reponse.GoodsTotalResponse;
import com.wanmi.ares.report.goods.model.request.GoodsQueryRequest;
import com.wanmi.ares.report.goods.model.root.GoodsTotalRatioReport;
import com.wanmi.ares.source.model.root.CompanyInfo;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Sku报表服务
 * Created by dyt on 2017/9/21.
 */
@Service
public class GoodsTotalService {


    @Autowired
    private GoodsTotalMapper goodsReportMapper;
    @Autowired
    private GoodsTotalRatioMapper goodsTotalRatioMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 查询商品概览
     * @param request 参数
     * @return
     */
    public GoodsTotalResponse query(GoodsQueryRequest request) {
        GoodsTotalResponse total = new GoodsTotalResponse();
        LocalDate date = LocalDate.now();
        List<GoodsTotalResponse> goodsTotals;
        Integer month = request.getMonth();
        int selectType = request.getSelectType() == null ? QueryDateCycle.TODAY.getValue() : request.getSelectType().getValue();
        GoodsQueryCriteria criteria = new GoodsQueryCriteria();
        StoreSelectType storeSelectType = request.getStoreSelectType();
        criteria.setStoreSelectType(storeSelectType);
        if(StringUtils.isNotBlank(request.getCompanyId()) && (!Constants.BOSS_ID.equals(request.getCompanyId()))) {
            criteria.setCompanyId(request.getCompanyId());
        }else{
            if(storeSelectType != null){
                CompanyInfo companyInfo = companyInfoMapper.queryByCompanyCode("O0000001");
                criteria.setO2oCompanyId(String.valueOf(companyInfo.getCompanyId()));
                String companyId = storeSelectType == StoreSelectType.ALL ? "0" :
                        storeSelectType == StoreSelectType.SUPPLIER ? "-2" : String.valueOf(companyInfo.getCompanyId());
                criteria.setCompanyId(companyId);
            }
        }
        if(month != null){//月份
            criteria.setTable("goods_total_ratio_month");
            criteria.setMonth(month);
        }else if(QueryDateCycle.YESTERDAY.getValue() == selectType){//昨日
            criteria.setTable("goods_total_ratio_day");
            criteria.setDate( DateUtil.format(date.minusDays(1),DateUtil.FMT_DATE_1));
            goodsTotals = goodsReportMapper.findGoodsTotal(criteria);
            if (CollectionUtils.isNotEmpty(goodsTotals)) {
                total = goodsTotals.get(0);
            }
        }else if(QueryDateCycle.LATEST_7_DAYS.getValue() == selectType){//近7日
            criteria.setTable("goods_total_ratio_recent_seven");
        }else if(QueryDateCycle.LATEST_30_DAYS.getValue() == selectType){//近30日
            criteria.setTable("goods_total_ratio_recent_thirty");
        }else{//今日
            criteria.setTable("goods_total_ratio_day");
            criteria.setDate( DateUtil.format(date,DateUtil.FMT_DATE_1));
            goodsTotals = goodsReportMapper.findGoodsTotal(criteria);
            if (CollectionUtils.isNotEmpty(goodsTotals)) {
                total = goodsTotals.get(0);
            }
        }
        List<GoodsTotalRatioReport> ratioReports =  this.goodsTotalRatioMapper.queryGoodsTotalRatioReport(criteria);
        if(CollectionUtils.isNotEmpty(ratioReports)){
            total.setOrderConversion(ratioReports.get(0).getRatio());
        }
        return total;
    }



}
