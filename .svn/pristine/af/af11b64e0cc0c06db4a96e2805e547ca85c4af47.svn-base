package com.wanmi.ares.report.goods.service;


import com.google.common.collect.Lists;
import com.wanmi.ares.exception.AresRuntimeException;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.flow.dao.ReplaySkuFlowUserInfoMapper;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.goods.dao.SkuMapper;
import com.wanmi.ares.report.goods.model.criteria.GoodsQueryCriteria;
import com.wanmi.ares.report.goods.model.root.SkuReport;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.GoodsInfoQueryRequest;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;

import jodd.util.RandomString;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Sku报表服务
 * Created by dyt on 2017/9/21.
 */
@Service
@Slf4j
public class GoodsReportExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private GoodsReportService goodsReportService;

    @Autowired
    private ReplaySkuFlowUserInfoMapper skuFlowUserInfoMapper;

    @Autowired
    private GoodsInfoSpecDetailRelMapper specDetailRelMapper;

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn(Map<String, String> specDetailNameMap){
        DecimalFormat format = new DecimalFormat("0.00");
        return new Column[]{
                new Column("商品名称", (cell, object) -> {
                    SkuReport trade = (SkuReport) object;
                    cell.setCellValue(trade.getName() == null ? "无" : trade.getName());}),
                new Column("商品类型", (cell, object) -> {
                    SkuReport vo = (SkuReport) object;
                    Integer goodsType = vo.getGoodsType();
                    String cellValue = "";
                    if (Objects.nonNull(goodsType)) {
                        switch (goodsType) {
                            case 0:
                                cellValue = "实物商品";
                                break;
                            case 1:
                                cellValue = "虚拟商品";
                                break;
                            case 2:
                                cellValue = "电子卡券";
                                break;
                            default:
                        }
                    }
                    cell.setCellValue(cellValue);
                }),
                new Column("SKU编码", new SpelColumnRender<SkuReport>("skuNo")),
                new Column("规格", (cell, object) -> {
                    SkuReport trade = (SkuReport) object;
                    String specDetailName = (specDetailNameMap==null ? null : specDetailNameMap.get(trade.getId()));
                    cell.setCellValue(specDetailName == null ? StringUtils.EMPTY : specDetailName);
                }),
                new Column("浏览量", new SpelColumnRender<SkuReport>("totalPv") {
                    @Override
                    public void render(HSSFCell cell, SkuReport skuReport) throws AresRuntimeException {
                        cell.setCellValue(skuReport.getTotalPv());
                    }
                }),
                new Column("访客数", new SpelColumnRender<SkuReport>("totalUv") {
                    @Override
                    public void render(HSSFCell cell, SkuReport skuReport) throws AresRuntimeException {
                        cell.setCellValue(skuReport.getTotalUv());
                    }
                }),
                new Column("下单笔数", new SpelColumnRender<SkuReport>("orderCount")),
                new Column("下单金额", new SpelColumnRender<SkuReport>("orderAmt")),
                new Column("下单件数", new SpelColumnRender<SkuReport>("orderNum")),
                new Column("下单人数", new SpelColumnRender<SkuReport>("customerCount")),
                new Column("付款订单数", new SpelColumnRender<SkuReport>("payCount")),
                new Column("付款人数", new SpelColumnRender<SkuReport>("customerPayCount")),
                new Column("付款金额", new SpelColumnRender<SkuReport>("payAmt")),
                new Column("付款商品数", new SpelColumnRender<SkuReport>("payNum")),
                new Column("退单件数", new SpelColumnRender<SkuReport>("returnOrderNum")),
                new Column("退单笔数", new SpelColumnRender<SkuReport>("returnOrderCount")),
                new Column("退单金额", new SpelColumnRender<SkuReport>("returnOrderAmt")),
                new Column("单品转化率", (cell, object) -> {
                    SkuReport trade = (SkuReport) object;
                    cell.setCellValue(format.format(trade.getOrderConversion()).concat("%"));
                }),
        };
    }

    /**
     * Sku设置Excel信息
     * @param excelHelper
     * @param specDetailNameMap
     * @param skuReports
     */
    private void setSkuExcel(ExcelHelper excelHelper, Map<String, String> specDetailNameMap, List<SkuReport> skuReports){
        excelHelper.addSheet("商品销售报表_商品报表", getColumn(specDetailNameMap), skuReports);
    }

    @Override
    public BaseResponse exportReport(ExportQuery query) throws IOException {
        List<String> fileUrl = new ArrayList<>();
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1);
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("goods/sku/%s/%s/%s商品报表_%s-%s-%s", query.getCompanyId(),
                DateUtil.format(endDate, DateUtil.FMT_MONTH_2),  storeService.getStoreName(query) ,
                query.getDateFrom(), query.getDateTo(), randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        GoodsQueryCriteria criteria = new GoodsQueryCriteria();
        criteria.setBegDate(query.getDateFrom());
        criteria.setEndDate(query.getDateTo());
        criteria.setStoreSelectType(query.getStoreSelectType());
        if(!Constants.BOSS_ID.equals(query.getCompanyId())) {
            criteria.setCompanyId(query.getCompanyId());
        }
        Long totalCount = skuMapper.countSkuReportByGroup(criteria);

        if(totalCount < 1){
            ExcelHelper excelHelper = new ExcelHelper();
            this.setSkuExcel(excelHelper, null, new ArrayList<>());
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                excelHelper.write(baos);
                String fileName = String.format("%s.xls",commonFileName);
                osdService.uploadExcel(baos , fileName);
                fileUrl.add(fileName);
            }
            return BaseResponse.success(fileUrl);
        }

        long pageSize = 5000;//一个excel文档有5000条信息
        long pageCount = 0L;
        if (totalCount % pageSize > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }

        GoodsInfoQueryRequest infoRequest = new GoodsInfoQueryRequest();
        infoRequest.setPageSize(pageSize);

        BigDecimal hun = new BigDecimal("100");

        for (long i = 0L; i < pageCount; i++) {
            long pageNum = i * pageSize;
            criteria.setNumber(pageNum);
            criteria.setSize(pageSize);
            criteria.setEndDate(query.getDateTo());
            List<SkuReport> skuReports = skuMapper.querySkuReportByGroup(criteria);
            List<String> ids = skuReports.stream().map(SkuReport::getId).collect(Collectors.toList());
            Map<String,Long> skuUvMap = new HashMap<>(ids.size()>>1);
            Map<String,Long> skuCustomerMap = new HashMap<>(ids.size()>>1);
            Map<String,String> specDetailNameMap = new HashMap<>(ids.size()>>1);
            GoodsQueryCriteria queryCriteria = KsBeanUtil.convert(criteria,GoodsQueryCriteria.class);

            criteria.setEndDate(DateUtil.format(endDate.minusDays(-1),DateUtil.FMT_DATE_1));
            List<List<String>> idsPartition = Lists.partition(ids,1000);
            for(List<String> skuList: idsPartition){
                queryCriteria.setIds(skuList);
                List<SkuReport> skuUvList =  this.skuFlowUserInfoMapper.queryUvBySku(queryCriteria);
                if(skuUvList != null && !skuUvList.isEmpty()) {
                    skuUvMap.putAll(
                            skuUvList
                                    .stream()
                                    .collect(
                                            Collectors.toMap(SkuReport::getId, SkuReport::getTotalUv)
                                    )
                    );
                }

                skuCustomerMap.putAll(skuMapper.querySkuCustomer(criteria)
                        .stream()
                        .collect(
                                Collectors.toMap(SkuReport::getId,SkuReport::getCustomerCount)
                        )
                );

                List<GoodsInfoSpecDetailRel> specDetailRels = this.specDetailRelMapper.queryByGoodsId(skuList,0);

                if(specDetailRels!=null&&!specDetailRels.isEmpty()) {
                    specDetailRels
                            .stream()
                            .forEach(goodsInfoSpecDetailRel -> {
                                        String detailName = null;
                                        if (MapUtils.isNotEmpty(specDetailNameMap)) {
                                            detailName = specDetailNameMap.get(goodsInfoSpecDetailRel.getGoodsInfoId());
                                        }
                                        if (StringUtils.isNotBlank(detailName)) {
                                            detailName = detailName.concat(" ")
                                                    .concat(goodsInfoSpecDetailRel.getDetailName());
                                        } else {
                                            detailName = goodsInfoSpecDetailRel.getDetailName();
                                        }
                                        specDetailNameMap.put(goodsInfoSpecDetailRel.getGoodsInfoId(), detailName);
                                    }
                            );
                }
            }

            if(CollectionUtils.isNotEmpty(skuReports)){
                skuReports.forEach(skuReport -> {

                    BigDecimal totalUv = new BigDecimal(0);
                    //一般生近日报表，月报表时 不为null
                    if(MapUtils.isNotEmpty(skuUvMap)){
                        totalUv = BigDecimal.valueOf(skuUvMap.get(skuReport.getId())==null?0:skuUvMap.get(skuReport.getId()));
                    }

                    //计算下单转换率
                    BigDecimal viewNum = totalUv.longValue()== 0L?BigDecimal.ONE:totalUv;
                    BigDecimal orderConversion = new BigDecimal(String.valueOf(skuReport.getCustomerPayCount()==null?0:skuReport.getCustomerPayCount())).divide(viewNum, 4, RoundingMode.HALF_UP).multiply(hun).setScale(2, RoundingMode.HALF_UP);
                    skuReport.setOrderConversion(orderConversion.compareTo(hun)>0?hun:orderConversion); //最大为100，大于100设为100
                    if(skuReport.getOrderConversion().compareTo(hun) > 0){
                        skuReport.setOrderConversion(hun);
                    }

                    //访问人数
                    skuReport.setTotalUv(totalUv.longValue());
                });


                infoRequest.setGoodsInfoIds(ids.stream().collect(Collectors.toList()));
                ExcelHelper excelHelper = new ExcelHelper();
                this.setSkuExcel(excelHelper, specDetailNameMap, skuReports);
                try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                    excelHelper.write(baos);
                    //如果超过一页，文件名后缀增加(索引值)
                    String suffix = StringUtils.EMPTY;
                    if(pageCount > 1){
                        suffix ="(".concat(String.valueOf(i+1)).concat(")");
                    }
                    String fileName = String.format("%s%s.xls",commonFileName, suffix);
                    osdService.uploadExcel(baos , fileName);
                    fileUrl.add(fileName);
                }
            }
        }

        return BaseResponse.success(fileUrl);
    }
}
