package com.wanmi.sbc.recommend.analysis;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.dw.api.request.RecommendEffectAnalysisRequest;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectChartDataResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectReportResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectStatisticsDataResponse;
import com.wanmi.sbc.dw.bean.enums.ReportType;
import com.wanmi.sbc.dw.bean.vo.RecommendEffectAnalysisResultVO;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateShenceBurialSiteRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsCateShenceBurialSiteVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.recommend.analysis.RecommendPositionAnalysisProvider;
import com.wanmi.sbc.vas.api.response.recommend.analysis.RecommendEffectReportDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 推荐坑位分析
 *
 * @author lvzhenwei
 * @date 2021/4/17 2:44 下午
 */
@Tag(name =  "推荐坑位分析", description =  "RecommendAnalysisController")
@RestController
@Validated
@Slf4j
@RequestMapping(value = "/recommend-position-analysis")
public class RecommendPositionAnalysisController {

    @Autowired private RecommendPositionAnalysisProvider recommendPositionAnalysisProvider;

    @Autowired private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired private CommonUtil commonUtil;

    private static final int PAGE_SIZE = 5000;

    private static final int ONE_CATE_GRADE = 1;

    private static final int TWO_CATE_GRADE = 2;

    private static final int THREE_CATE_GRADE = 3;

    private AtomicInteger exportCount = new AtomicInteger(0);

    /**
     * 查询推荐效果分析概况
     *
     * @param request
     */
    @Operation(summary = "查询推荐效果分析概况")
    @RequestMapping(value = "/get-effect-statistics", method = RequestMethod.POST)
    public BaseResponse<RecommendEffectStatisticsDataResponse> getRecommendEffectStatistics(
            @RequestBody @Valid RecommendEffectAnalysisRequest request) {
        return recommendPositionAnalysisProvider.getRecommendEffectStatistics(request);
    }

    /**
     * 查询推荐效果分析趋势
     *
     * @param request
     */
    @Operation(summary = "查询推荐效果分析趋势")
    @RequestMapping(value = "/get-effect-chart-data", method = RequestMethod.POST)
    public BaseResponse<RecommendEffectChartDataResponse> getRecommendEffectChartData(
            @RequestBody @Valid RecommendEffectAnalysisRequest request) {
        return recommendPositionAnalysisProvider.getRecommendEffectChartData(request);
    }

    /**
     * 查询推荐效果报表
     *
     * @author lvzhenwei
     * @date 2021/4/17 10:40 上午
     * @param request
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.dw.api.response.analysis.RecommendEffectReportResponse>
     */
    @Operation(summary = "查询推荐效果报表")
    @RequestMapping(value = "/get-recommend-effect-report-data", method = RequestMethod.POST)
    public BaseResponse<RecommendEffectReportDataResponse> getRecommendEffectReportData(
            @RequestBody @Valid RecommendEffectAnalysisRequest request) {
        request.setPageNum(request.getPageNum() * request.getPageSize());
        // 判断是否是查询类目报表，如果是类目报表，则查询当前查询类目下的所有子类目
        if (request.getReportsType() == ReportType.CATE_REPORT) {
            if (StringUtils.isNotBlank(request.getSearcher())) {
                GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
                idRequest.setCateId(Long.valueOf(request.getSearcher()));
                if(request.getCateGrade() == THREE_CATE_GRADE){
                    List<Long> cateIds = new ArrayList();
                    cateIds.add(Long.valueOf(request.getSearcher()));
                    request.setCateIds(cateIds);
                } else {
                    request.setCateIds(
                            goodsCateQueryProvider
                                    .getChildCateIdById(idRequest)
                                    .getContext()
                                    .getChildCateIdList());
                }
            }
        }
        // 查询
        RecommendEffectReportResponse recommendEffectReportResponse =
                recommendPositionAnalysisProvider
                        .getRecommendEffectReportData(request)
                        .getContext();
        long totalNum = recommendEffectReportResponse.getTotalNum();
        List<RecommendEffectAnalysisResultVO> recommendEffectAnalysisReportData =
                recommendPositionAnalysisProvider
                        .getRecommendEffectReportData(request)
                        .getContext()
                        .getRecommendEffectAnalysisReportData();
        // 如果是类目报表，查询类目报表对应的类目名称进行报表类目名称的拼接
        if (request.getReportsType() == ReportType.CATE_REPORT) {
            List<Long> cateIds = new ArrayList<>();
            recommendEffectAnalysisReportData.forEach(
                    recommendEffectAnalysisResultVO -> {
                        cateIds.add(Long.valueOf(recommendEffectAnalysisResultVO.getItemName()));
                    });
            GoodsCateShenceBurialSiteRequest cateRequest = new GoodsCateShenceBurialSiteRequest();
            cateRequest.setCateIds(cateIds);
            List<GoodsCateShenceBurialSiteVO> goodsCateList =
                    goodsCateQueryProvider
                            .listGoodsCateShenceBurialSite(cateRequest)
                            .getContext()
                            .getGoodsCateList();
            if (CollectionUtils.isNotEmpty(goodsCateList)) {
                Map<String, String> cateNameMap = new HashMap<>();
                goodsCateList.forEach(
                        goodsCateInfo -> {
                            StringBuilder cateName = new StringBuilder();
                            if (StringUtils.isNotBlank(request.getSearcher())
                                    && Objects.nonNull(request.getCateGrade())) {
                                Long cateId = goodsCateInfo.getThreeLevelGoodsCate().getCateId();
                                if (request.getCateGrade() == ONE_CATE_GRADE) {
                                    cateId = goodsCateInfo.getThreeLevelGoodsCate().getCateId();
                                    cateName.append(
                                            goodsCateInfo.getThreeLevelGoodsCate().getCateName());
                                } else if (request.getCateGrade() == TWO_CATE_GRADE) {
                                    cateId = goodsCateInfo.getThreeLevelGoodsCate().getCateId();
                                    cateName.append(
                                            goodsCateInfo.getSecondLevelGoodsCate().getCateName());
                                    cateName.append('/');
                                    cateName.append(
                                            goodsCateInfo.getThreeLevelGoodsCate().getCateName());
                                } else if (request.getCateGrade() == THREE_CATE_GRADE) {
                                    cateId = goodsCateInfo.getThreeLevelGoodsCate().getCateId();
                                    cateName.append(
                                            goodsCateInfo.getOneLevelGoodsCate().getCateName());
                                    cateName.append('/');
                                    cateName.append(
                                            goodsCateInfo.getSecondLevelGoodsCate().getCateName());
                                    cateName.append('/');
                                    cateName.append(
                                            goodsCateInfo.getThreeLevelGoodsCate().getCateName());
                                }
                                cateNameMap.put(cateId.toString(), cateName.toString());
                            } else {
                                Long cateId = goodsCateInfo.getThreeLevelGoodsCate().getCateId();
                                cateName.append(goodsCateInfo.getOneLevelGoodsCate().getCateName());
                                cateName.append('/');
                                cateName.append(
                                        goodsCateInfo.getSecondLevelGoodsCate().getCateName());
                                cateName.append('/');
                                cateName.append(
                                        goodsCateInfo.getThreeLevelGoodsCate().getCateName());
                                cateNameMap.put(cateId.toString(), cateName.toString());
                            }
                        });
                recommendEffectAnalysisReportData.forEach(
                        recommendEffectAnalysisResultVO -> {
                            String cateReportName =
                                    cateNameMap.get(recommendEffectAnalysisResultVO.getItemName());
                            if (StringUtils.isBlank(cateReportName)) {
                                cateReportName = "-";
                            }
                            recommendEffectAnalysisResultVO.setItemName(cateReportName);
                        });
            }
        }
        if (request.getPageNum() > 0) {
            request.setPageNum(request.getPageNum() / request.getPageSize());
        }
        Page<RecommendEffectAnalysisResultVO> page =
                new PageImpl<>(
                        recommendEffectAnalysisReportData,
                        PageRequest.of(request.getPageNum(), request.getPageSize()),
                        totalNum);
        MicroServicePage<RecommendEffectAnalysisResultVO> pageData =
                KsBeanUtil.convertPage(page, RecommendEffectAnalysisResultVO.class);
        return BaseResponse.success(
                RecommendEffectReportDataResponse.builder()
                        .recommendEffectAnalysisReportData(pageData)
                        .build());
    }

    /**
     * 推荐效果报表下载
     *
     * @author lvzhenwei
     * @date 2021/4/19 3:53 下午
     * @param encrypted
     * @param response
     * @return void
     */
    @Operation(summary = "推荐效果报表下载")
    @Parameter(
            name = "encrypted",
            description = "解密",
            required = true)
    @RequestMapping(
            value = "/recommend-effect-report-data/export/params/{encrypted}",
            method = RequestMethod.GET)
    public void export(@PathVariable String encrypted, HttpServletResponse response) {
        try {
            if (exportCount.incrementAndGet() > 1) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
            }
            String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
            RecommendEffectAnalysisRequest recommendEffectAnalysisRequest =
                    JSON.parseObject(decrypted, RecommendEffectAnalysisRequest.class);
            String headerKey = "Content-Disposition";
            String fileType = "推荐效果天报表_%s.xls";
            if (recommendEffectAnalysisRequest.getReportsType() == ReportType.GOODS_REPORT) {
                fileType = "推荐效果商品报表_%s.xls";
            } else if (recommendEffectAnalysisRequest.getReportsType() == ReportType.CATE_REPORT) {
                fileType = "推荐效果类目报表_%s.xls";
            } else if (recommendEffectAnalysisRequest.getReportsType() == ReportType.BRAND_REPORT) {
                fileType = "推荐效果品牌报表_%s.xls";
            }
            String reportTime =
                    recommendEffectAnalysisRequest.getStartDate()
                            + "_"
                            + recommendEffectAnalysisRequest.getEndDate();
            String fileName = String.format(fileType, reportTime);
            try {
                fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                log.error("/recommend-effect-report-data/export/params, fileName={},", fileName, e);
            }
            String headerValue =
                    String.format(
                            "attachment;filename=\"%s\";filename*=\"utf-8''%s\"",
                            fileName, fileName);
            response.setHeader(headerKey, headerValue);
            // 根据返回结果判断分页条数；
            String sheetName = "推荐效果报表";
            // 获取导出ColumnList
            List<Column> columnList =
                    getColumnList(recommendEffectAnalysisRequest.getReportsType());
            ExcelHelper excelHelper = new ExcelHelper();
            Column[] columns;
            columns = columnList.toArray(new Column[0]);
            HSSFSheet sheet = excelHelper.addSheetHead(sheetName, columns);
            // 查询要导出的数据
            recommendEffectAnalysisRequest.setPageNum(NumberUtils.INTEGER_ZERO);
            recommendEffectAnalysisRequest.setPageSize(PAGE_SIZE);
            Page<RecommendEffectAnalysisResultVO> page =
                    getReportData(recommendEffectAnalysisRequest);
            exportData(
                    recommendEffectAnalysisRequest, excelHelper, sheet, columns, page.getContent());
            while (!page.isLast()) {
                Integer newPageNum = recommendEffectAnalysisRequest.getPageNum() + 1;
                recommendEffectAnalysisRequest.setPageNum(newPageNum);
                page = getReportData(recommendEffectAnalysisRequest);
                excelHelper =
                        exportData(
                                recommendEffectAnalysisRequest,
                                excelHelper,
                                sheet,
                                columns,
                                page.getContent());
            }
            excelHelper.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("/recommend-effect-report-data/export/params error: ", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        } finally {
            exportCount.set(0);
        }
    }

    /**
     * 分页获取报表导出数据
     * @author  lvzhenwei
     * @date 2021/4/28 11:08 上午
     * @param recommendEffectAnalysisRequest
     * @return org.springframework.data.domain.Page<com.wanmi.sbc.dw.bean.vo.RecommendEffectAnalysisResultVO>
     **/
    private Page<RecommendEffectAnalysisResultVO> getReportData(
            RecommendEffectAnalysisRequest recommendEffectAnalysisRequest) {
        recommendEffectAnalysisRequest.setPageNum(
                recommendEffectAnalysisRequest.getPageNum()
                        * recommendEffectAnalysisRequest.getPageSize());
        RecommendEffectReportResponse recommendEffectReportResponse =
                recommendPositionAnalysisProvider
                        .getRecommendEffectReportData(recommendEffectAnalysisRequest)
                        .getContext();
        List<RecommendEffectAnalysisResultVO> recommendEffectAnalysisReportData =
                recommendEffectReportResponse.getRecommendEffectAnalysisReportData();
        long totalNum = recommendEffectReportResponse.getTotalNum();
        Page<RecommendEffectAnalysisResultVO> page =
                new PageImpl<>(
                        recommendEffectAnalysisReportData,
                        PageRequest.of(
                                recommendEffectAnalysisRequest.getPageNum()
                                        / recommendEffectAnalysisRequest.getPageSize(),
                                recommendEffectAnalysisRequest.getPageSize()),
                        totalNum);
        return page;
    }

    /**
     * 导出报表Excel数据
     * @author  lvzhenwei
     * @date 2021/4/28 11:09 上午
     * @param recommendEffectAnalysisRequest
     * @param excelHelper
     * @param sheet
     * @param columns
     * @param reportData
     * @return com.wanmi.sbc.common.util.excel.ExcelHelper
     **/
    private ExcelHelper exportData(
            RecommendEffectAnalysisRequest recommendEffectAnalysisRequest,
            ExcelHelper excelHelper,
            HSSFSheet sheet,
            Column[] columns,
            List<RecommendEffectAnalysisResultVO> reportData) {
        excelHelper.addSheetRow(
                sheet, columns, reportData,recommendEffectAnalysisRequest.getPageNum()+1);
        return excelHelper;
    }

    /**
     * 报表导出Excel列信息
     * @author  lvzhenwei
     * @date 2021/4/28 11:09 上午
     * @param reportType
     * @return java.util.List<com.wanmi.sbc.common.util.excel.Column>
     **/
    public List<Column> getColumnList(ReportType reportType) {
        List<Column> columnList = new ArrayList<>(20);
        String columnName = " 日期";
        if (reportType == ReportType.GOODS_REPORT) {
            columnName = "商品";
        } else if (reportType == ReportType.CATE_REPORT) {
            columnName = "类目";
        } else if (reportType == ReportType.BRAND_REPORT) {
            columnName = "品牌";
        }
        columnList.add(
                new Column(
                        columnName,
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>("itemName")));
        columnList.add(
                new Column(
                        "曝光人数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "exposureNumberOfPeople")));
        columnList.add(
                new Column(
                        "曝光商品数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "exposureNumberOfGoodsUV")));
        columnList.add(
                new Column(
                        "曝光量",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "exposureNumberOfGoodsPV")));
        columnList.add(
                new Column(
                        "点击人数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "clickNumberOfPeopleUV")));
        columnList.add(
                new Column(
                        "点击量",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "clickNumberOfGoodsPV")));
        columnList.add(
                new Column(
                        "点击率",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>("clickPercent")));
        columnList.add(
                new Column(
                        "平均点击次数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>("avgCntOfClick")));
        columnList.add(
                new Column(
                        "加购次数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>("orderGoodsPrice")));
        columnList.add(
                new Column(
                        "加购人数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "purchasedNumberOfGoodsPV")));
        columnList.add(
                new Column(
                        "下单笔数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>("orderNumber")));
        columnList.add(
                new Column(
                        "下单人数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "orderNumberOfPeople")));
        columnList.add(
                new Column(
                        "付款笔数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>("paymentNumber")));
        columnList.add(
                new Column(
                        "付款人数",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "paymentNumberOfPeople")));
        columnList.add(
                new Column(
                        "付款金额",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "paymentAmountOfMoney")));
        columnList.add(
                new Column(
                        "下单-付款转化率",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "percentOfOrderToPay")));
        columnList.add(
                new Column(
                        "曝光-付款转化率",
                        new SpelColumnRender<RecommendEffectAnalysisResultVO>(
                                "percentOfDisplayToPay")));
        return columnList;
    }
}
