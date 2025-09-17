package com.wanmi.ares.report.goods.service;


import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsStoreCateMapper;
import com.wanmi.ares.report.goods.model.criteria.GoodsQueryCriteria;
import com.wanmi.ares.report.goods.model.root.GoodsCateReport;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;
import jodd.util.RandomString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Sku报表服务
 * Created by dyt on 2017/9/21.
 */
@Service
@Slf4j
public class GoodsStoreCateTradeExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private GoodsCateTradeExportService goodsCateTradeExportService;

    @Autowired
    private GoodsStoreCateMapper goodsStoreCateMapper;

    @Override
    public BaseResponse exportReport(ExportQuery query) throws IOException {
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1);
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("goods/storecate/%s/%s/%s店铺分类报表_%s-%s-%s.xls", query.getCompanyId(),
                DateUtil.format(endDate, DateUtil.FMT_MONTH_2) , storeService.getStoreName(query) ,
                query.getDateFrom(), query.getDateTo(), randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        GoodsQueryCriteria criteria = new GoodsQueryCriteria();
        criteria.setBegDate(query.getDateFrom());
        criteria.setEndDate(query.getDateTo());
        if(!Constants.BOSS_ID.equals(query.getCompanyId())) {
            criteria.setCompanyId(query.getCompanyId());
        }else{
            criteria.setCompanyId("0");
        }
        List<GoodsCateReport> cateReports = this.goodsStoreCateMapper.queryGoodsStoreCateReportByExport(criteria);
        if(CollectionUtils.isEmpty(cateReports)){
            ExcelHelper excelHelper = new ExcelHelper();
            goodsCateTradeExportService.setCateExcel(excelHelper, new ArrayList<>());
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                excelHelper.write(baos);
                osdService.uploadExcel(baos , commonFileName);
            }
            return BaseResponse.success(commonFileName);
        }

        //获取类目详情

        ExcelHelper excelHelper = new ExcelHelper();
        goodsCateTradeExportService.setCateExcel(excelHelper,cateReports);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            excelHelper.write(baos);
            osdService.uploadExcel(baos , commonFileName);
            return BaseResponse.success(commonFileName);
        }
    }
}
