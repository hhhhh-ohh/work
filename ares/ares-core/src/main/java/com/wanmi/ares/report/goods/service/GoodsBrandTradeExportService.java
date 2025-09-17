package com.wanmi.ares.report.goods.service;


import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.CompanyInfoMapper;
import com.wanmi.ares.report.goods.dao.GoodsBrandMapper;
import com.wanmi.ares.report.goods.model.criteria.GoodsQueryCriteria;
import com.wanmi.ares.report.goods.model.root.GoodsBrandReport;
import com.wanmi.ares.report.goods.model.root.GoodsReport;
import com.wanmi.ares.report.goods.model.root.SkuReport;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.source.model.root.CompanyInfo;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
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
public class GoodsBrandTradeExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private GoodsBrandMapper goodsBrandMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 导出列字段
     * @return
     */
    public Column[] getBrandColumn(){
        return new Column[]{
                new Column("品牌名称", (cell, object) -> {
                    GoodsBrandReport report = (GoodsBrandReport) object;
                    cell.setCellValue(report.getBrandName() == null ? "无" : report.getBrandName());
                }),
                new Column("下单笔数", new SpelColumnRender<GoodsReport>("orderCount")),
                new Column("下单金额", new SpelColumnRender<GoodsReport>("orderAmt")),
                new Column("下单件数", new SpelColumnRender<GoodsReport>("orderNum")),
                new Column("下单人数", new SpelColumnRender<SkuReport>("customerCount")),
                new Column("付款订单数", new SpelColumnRender<SkuReport>("payCount")),
                new Column("付款人数", new SpelColumnRender<SkuReport>("customerPayCount")),
                new Column("付款金额", new SpelColumnRender<SkuReport>("payAmt")),
                new Column("付款商品数", new SpelColumnRender<GoodsReport>("payNum")),
                new Column("退单件数", new SpelColumnRender<GoodsReport>("returnOrderNum")),
                new Column("退单笔数", new SpelColumnRender<GoodsReport>("returnOrderCount")),
                new Column("退单金额", new SpelColumnRender<GoodsReport>("returnOrderAmt"))
        };
    }

    /**
     * 品牌设置Excel信息
     * @param excelHelper
     * @param goodsReports
     */
    private void setBrandExcel(ExcelHelper excelHelper, List<GoodsBrandReport> goodsReports){
        excelHelper.addSheet("商品销售报表_品牌报表", getBrandColumn(), goodsReports);
    }

    @Override
    public BaseResponse exportReport(ExportQuery query) throws IOException {
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1);
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("goods/brand/%s/%s/%s品牌报表_%s-%s-%s.xls", query.getCompanyId(),
                DateUtil.format(endDate, DateUtil.FMT_MONTH_2), storeService.getStoreName(query), query.getDateFrom()
                , query.getDateTo(), randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        GoodsQueryCriteria criteria = new GoodsQueryCriteria();
        criteria.setBegDate(query.getDateFrom());
        criteria.setEndDate(query.getDateTo());
        StoreSelectType storeSelectType = query.getStoreSelectType();
        criteria.setStoreSelectType(storeSelectType);
        if(!Constants.BOSS_ID.equals(query.getCompanyId())) {
            criteria.setCompanyId(query.getCompanyId());
        }else{
            criteria.setCompanyId("0");
        }
        if(storeSelectType == StoreSelectType.O2O && criteria.getCompanyId().equals("0")){
            CompanyInfo companyInfo = companyInfoMapper.queryByCompanyCode("O0000001");
            criteria.setO2oCompanyId(String.valueOf(companyInfo.getCompanyId()));
            criteria.setCompanyId(criteria.getO2oCompanyId());
        }else if(storeSelectType == StoreSelectType.SUPPLIER && criteria.getCompanyId().equals("0")){
            criteria.setCompanyId("-2");
        }
        List<GoodsBrandReport> list = this.goodsBrandMapper.queryGoodsBrandReportByExport(criteria);
        if(CollectionUtils.isEmpty(list)){
            ExcelHelper excelHelper = new ExcelHelper();
            this.setBrandExcel(excelHelper, new ArrayList<>());
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                excelHelper.write(baos);
                osdService.uploadExcel(baos , commonFileName);
            }
            return BaseResponse.success(commonFileName);
        }

        ExcelHelper excelHelper = new ExcelHelper();
        this.setBrandExcel(excelHelper, list);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            excelHelper.write(baos);
            osdService.uploadExcel(baos , commonFileName);
            return BaseResponse.success(commonFileName);
        }

    }
}
