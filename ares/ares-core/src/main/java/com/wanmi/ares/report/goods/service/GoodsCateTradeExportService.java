package com.wanmi.ares.report.goods.service;


import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.CompanyInfoMapper;
import com.wanmi.ares.report.goods.dao.GoodsCateMapper;
import com.wanmi.ares.report.goods.model.criteria.GoodsQueryCriteria;
import com.wanmi.ares.report.goods.model.root.GoodsCateReport;
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
public class GoodsCateTradeExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private GoodsCateMapper goodsCateMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 导出列字段
     * @return
     */
    public Column[] getCateColumn(){
        return new Column[]{
                new Column("分类名称", (cell, object) -> {
                    GoodsCateReport report = (GoodsCateReport) object;
                    cell.setCellValue(report.getCateName() == null ? "无" : report.getCateName());
                }),
                new Column("上级分类", (cell, object) -> {
                    GoodsCateReport report = (GoodsCateReport) object;
                    cell.setCellValue(report.getCateParentName() == null ? "无" : report.getCateParentName());
                }),
                new Column("下单笔数", new SpelColumnRender<GoodsReport>("orderCount")),
                new Column("下单金额", new SpelColumnRender<GoodsReport>("orderAmt")),
                new Column("下单件数", new SpelColumnRender<GoodsReport>("orderNum")),
                new Column("下单人数", new SpelColumnRender<GoodsReport>("customerCount")),
                new Column("付款订单数", new SpelColumnRender<SkuReport>("payCount")),
                new Column("付款人数", new SpelColumnRender<GoodsReport>("customerPayCount")),
                new Column("付款金额", new SpelColumnRender<SkuReport>("payAmt")),
                new Column("付款商品数", new SpelColumnRender<GoodsReport>("payNum")),
                new Column("退单件数", new SpelColumnRender<GoodsReport>("returnOrderNum")),
                new Column("退单笔数", new SpelColumnRender<GoodsReport>("returnOrderCount")),
                new Column("退单金额", new SpelColumnRender<GoodsReport>("returnOrderAmt"))
        };
    }

    /**
     * 分类设置Excel信息
     * @param excelHelper
     * @param goodsReports
     */
    public void setCateExcel(ExcelHelper excelHelper, List<GoodsCateReport> goodsReports){
        excelHelper.addSheet("商品销售报表_分类报表", getCateColumn(), goodsReports);
    }

    @Override
    public BaseResponse exportReport(ExportQuery query) throws IOException {
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1);
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("goods/cate/%s/%s/%s分类报表_%s-%s-%s.xls", query.getCompanyId(),
                DateUtil.format(endDate, DateUtil.FMT_MONTH_2), storeService.getStoreName(query) ,query.getDateFrom()
                , query.getDateTo(), randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);

        GoodsQueryCriteria criteria = new GoodsQueryCriteria();
        criteria.setBegDate(query.getDateFrom());
        criteria.setEndDate(query.getDateTo());
        StoreSelectType storeSelectType = query.getStoreSelectType();
        if(storeSelectType != null){
            criteria.setStoreSelectType(storeSelectType);
            CompanyInfo companyInfo = companyInfoMapper.queryByCompanyCode("O0000001");
            criteria.setO2oCompanyId(String.valueOf(companyInfo.getCompanyId()));
        }
        if(!Constants.BOSS_ID.equals(query.getCompanyId())) {
            criteria.setCompanyId(query.getCompanyId());
        }else{
            criteria.setCompanyId("0");
        }
        List<GoodsCateReport> cateReports = this.goodsCateMapper.queryGoodsCateReportByExport(criteria);
        if(CollectionUtils.isEmpty(cateReports)){
            ExcelHelper excelHelper = new ExcelHelper();
            this.setCateExcel(excelHelper, new ArrayList<>());
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                excelHelper.write(baos);
                osdService.uploadExcel(baos , commonFileName);
            }
            return BaseResponse.success(commonFileName);
        }

        //获取类目详情

        ExcelHelper excelHelper = new ExcelHelper();
        this.setCateExcel(excelHelper,cateReports);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            excelHelper.write(baos);
            osdService.uploadExcel(baos , commonFileName);
            return BaseResponse.success(commonFileName);
        }
    }
}
