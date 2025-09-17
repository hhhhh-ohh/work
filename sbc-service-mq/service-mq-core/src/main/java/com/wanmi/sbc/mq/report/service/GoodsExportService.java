package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.elastic.api.provider.spu.EsSpuQueryProvider;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightTemplateGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsSpecQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListCateByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsSpecListByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfRequest;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.vo.FreightTemplateGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsExportVo;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecExportVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPageSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className GoodsExportService
 * @description 商品导出
 * @date 2021/5/28 6:15 下午
 **/
@Service
@Slf4j
public class GoodsExportService implements ExportBaseService {

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private EsSpuQueryProvider esSpuQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private ContractCateQueryProvider contractCateQueryProvider;

    @Autowired
    private FreightTemplateGoodsQueryProvider freightTemplateGoodsQueryProvider;

    @Autowired
    private GoodsSpecQueryProvider goodsSpecQueryProvider;

    @Autowired
    private ExportUtilService exportUtilService;

    @Resource(name="reportExecutor")
    private Executor executor;

    @Autowired
    private OsdService osdService;

    private final static Integer SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出商品_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("goods/excel/%s/%s/%s", data.getStoreId(),
                data.getUserId(), fileName);

        boolean isBoss = data.getPlatform() == Platform.PLATFORM;

        //查询商品数据量
        EsSpuPageRequest esSpuPageRequest = JSON.parseObject(data.getParam(), EsSpuPageRequest.class);
        esSpuPageRequest.setPageNum(0);
        if (!isBoss){
            esSpuPageRequest.setStoreId(data.getStoreId());
        }
        esSpuPageRequest.setDelFlag(DeleteFlag.NO.toValue());
        //补充店铺分类
        if (esSpuPageRequest.getStoreCateId() != null) {
            BaseResponse<StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfResponse> baseResponse = storeCateQueryProvider
                    .listGoodsRelByStoreCateIdAndIsHaveSelf(
                            new StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfRequest(esSpuPageRequest.getStoreCateId(), true));
            StoreCateListGoodsRelByStoreCateIdAndIsHaveSelfResponse cateIdAndIsHaveSelfResponse = baseResponse.getContext();
            if (Objects.nonNull(cateIdAndIsHaveSelfResponse)) {
                List<StoreCateGoodsRelaVO> relas = cateIdAndIsHaveSelfResponse.getStoreCateGoodsRelaVOList();
                if (CollectionUtils.isEmpty(relas)) {
                    EsSpuPageResponse response = new EsSpuPageResponse();
                    response.setGoodsPage(new MicroServicePage<>(Collections.emptyList(), esSpuPageRequest.getPageable(), 0));
                    return BaseResponse.success(response);
                }
                esSpuPageRequest.setStoreCateGoodsIds(relas.stream().map(StoreCateGoodsRelaVO::getGoodsId).collect(Collectors.toList()));
            } else {
                EsSpuPageResponse response = new EsSpuPageResponse();
                response.setGoodsPage(new MicroServicePage<>(Collections.emptyList(), esSpuPageRequest.getPageable(), 0));
                return BaseResponse.success(response);
            }
        }
        esSpuPageRequest.setShowVendibilityFlag(Boolean.TRUE);//显示可售性
        Long total = esSpuQueryProvider.count(esSpuPageRequest).getContext();
        //写入表头
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] colunms = this.getColunms(data.getPlatform() == Platform.SUPPLIER,
                isBoss);
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("商品导出", colunms);

        //如果没有数据，直接生成空Excel
        if (total.equals(NumberUtils.LONG_ZERO)) {
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.writeForSXSSF(emptyStream);
            osdService.uploadExcel(emptyStream, resourceKey);
            return BaseResponse.success(resourceKey);
        }

        FutureTask<List<GoodsCateVO>> contractCateTask = new FutureTask<>(
                () -> {
                    ContractCateListCateByStoreIdRequest contractCateRequest = new ContractCateListCateByStoreIdRequest();
                    if (isBoss){
                        contractCateRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
                    }else {
                        contractCateRequest.setStoreId(data.getStoreId());
                    }
                    return contractCateQueryProvider.listCateByStoreId(contractCateRequest).getContext().getGoodsCateList();
                }
        );
        executor.execute(contractCateTask);

        esSpuPageRequest.setPageSize(SIZE);
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页写获取数据并写入excel
        int rowIndex = 0;
        for (int i = 0; i < fileSize; i++) {
            esSpuPageRequest.setPageNum(i);
            List<GoodsPageSimpleVO> goodsPageSimpleVOList = esSpuQueryProvider.page(esSpuPageRequest).getContext().getGoodsPage().getContent();
            if (CollectionUtils.isNotEmpty(goodsPageSimpleVOList)) {
                List<FutureTask<List<GoodsExportVo>>> futureTasks = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(goodsPageSimpleVOList)) {
                    //分十组异步查询
                    List<Collection> splitList = ReportUtil.splitList(goodsPageSimpleVOList, 10, SIZE / 10);
                    for (int n = 0; n < splitList.size(); n++) {
                        List<GoodsPageSimpleVO> goodsList = (List<GoodsPageSimpleVO>) splitList.get(n);
                        FutureTask<List<GoodsExportVo>> futureTask = new FutureTask<>(
                                () -> this.getExportData(goodsList, contractCateTask.get())
                        );
                        futureTasks.add(futureTask);
                        executor.execute(futureTask);
                    }

                    for (FutureTask<List<GoodsExportVo>> task : futureTasks) {
                        try {
                            List<GoodsExportVo> goods = task.get();
                            excelHelper.addSXSSFSheetRow(sheet, colunms, goods, rowIndex + 1);
                            rowIndex = rowIndex + goods.size();
                        } catch (ExecutionException e) {
                            log.error("goodsExcel任务执行出错:", e);
                            task.cancel(true);
                        } catch (InterruptedException e) {
                            log.error("goodsExcel任务执行出错:", e);
                            task.cancel(true);
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }
        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * @param isSupplier, isBoss
     * @return
     * @description 组装表头
     * @author xuyunepng
     * @date 2021/5/31 7:06 下午
     */
    public Column[] getColunms(boolean isSupplier, boolean isBoss) {
        Column[] columns = {};
        if (isBoss){
            Column[] columns0 = {
                    new Column("所属商家", new SpelColumnRender<GoodsExportVo>("supplierName")),
            };
            columns = ArrayUtils.addAll(columns, columns0);
        }
        Column[] columns1 = {
                new Column("商品名称", new SpelColumnRender<GoodsExportVo>("goodsInfoName")),
                new Column("商品类型", (cell, object) -> {
                    GoodsExportVo vo = (GoodsExportVo) object;
                    Integer goodsType = vo.getGoodsType();
                    String cellValue = "";
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
                    cell.setCellValue(cellValue);
                }),
                new Column("SPU编码", new SpelColumnRender<GoodsExportVo>("goodsNo")),
                new Column("一级类目", new SpelColumnRender<GoodsExportVo>("cateNameLevel1")),
                new Column("二级类目", new SpelColumnRender<GoodsExportVo>("cateNameLevel2")),
                new Column("三级类目", new SpelColumnRender<GoodsExportVo>("cateNameLevel3")),
                new Column("店铺分类", new SpelColumnRender<GoodsExportVo>("storeCateName")),
                new Column("计量单位", new SpelColumnRender<GoodsExportVo>("goodsUnit")),
                new Column("商品品牌", new SpelColumnRender<GoodsExportVo>("brandName")),
                new Column("商品图片", new SpelColumnRender<GoodsExportVo>("goodsImg")),
                new Column("商品视频", new SpelColumnRender<GoodsExportVo>("goodsVideo")),
                new Column("商品详情", new SpelColumnRender<GoodsExportVo>("goodsDetail")),
                new Column("SKU编码", new SpelColumnRender<GoodsExportVo>("goodsInfoNo")),
                new Column("供应商SKU编码", new SpelColumnRender<GoodsExportVo>("providerGoodsInfoNo")),
                new Column("SKU图片", new SpelColumnRender<GoodsExportVo>("goodsInfoImg")),
                new Column("库存", new SpelColumnRender<GoodsExportVo>("stock")),
                new Column("商品标签", new SpelColumnRender<GoodsExportVo>("goodsLabel")),
        };
        columns = ArrayUtils.addAll(columns, columns1);

        if (isBoss){
            Column[] columns2 = {
                    new Column("实际销量", new SpelColumnRender<GoodsExportVo>("goodsSalesNum")),
                    new Column("注水销量", new SpelColumnRender<GoodsExportVo>("shamSalesNum")),
                    new Column("排序号", new SpelColumnRender<GoodsExportVo>("sortNo")),
                    new Column("商家类型", (cell, object) -> {
                        GoodsExportVo vo = (GoodsExportVo) object;
                        Boolean companyType = vo.getCompanyType() == BoolFlag.NO;
                        String cellValue = companyType ? "平台自营" : "第三方";
                        cell.setCellValue(cellValue);
                    }),
            };
            columns = ArrayUtils.addAll(columns, columns2);
        }

        Column[] columns3 = {
                new Column("条形码", new SpelColumnRender<GoodsExportVo>("goodsInfoBarcode")),
                new Column("上下架", (cell, object) -> {
                    GoodsExportVo vo = (GoodsExportVo) object;
                    AddedFlag addedFlag = AddedFlag.fromValue(vo.getAddedFlag());
                    String cellValue = "";
                    switch (addedFlag) {
                        case NO:
                            cellValue = "未上架";
                            break;
                        case YES:
                            cellValue = "已上架";
                            break;
                        case PART:
                            cellValue = "部分上架";
                            break;
                        default:
                    }
                    cell.setCellValue(cellValue);
                }),
                new Column("重量（kg）", new SpelColumnRender<GoodsExportVo>("goodsWeight")),
                new Column("体积（m³）", new SpelColumnRender<GoodsExportVo>("goodsCubage")),
        };
        columns = ArrayUtils.addAll(columns, columns3);

        if (isSupplier||isBoss) {
            Column[] columns4 = {
                    new Column("销售类型", new SpelColumnRender<GoodsExportVo>("saleType == 0 ? '批发' : '零售'")),
                    new Column("运费模版", new SpelColumnRender<GoodsExportVo>("freightTempName")),
                    new Column("市场价(元)", new SpelColumnRender<GoodsExportVo>("marketPrice")),
                    new Column("划线价(元)", new SpelColumnRender<GoodsExportVo>("linePrice")),
                    new Column("积分价格", new SpelColumnRender<GoodsExportVo>("buyPoint")),
                    new Column("下单方法", (cell, object) -> {
                        String cellValue = "";
                        GoodsExportVo vo = (GoodsExportVo) object;
                        String goodsBuyTypes = vo.getGoodsBuyTypes();
                        if (StringUtils.isNotBlank(goodsBuyTypes)) {
                            cellValue = Arrays.stream(goodsBuyTypes.split(","))
                                    .map(type -> "1".equals(type) ? "立即购买" : "加入购物车").collect(Collectors.joining("｜"));
                        }
                        cell.setCellValue(cellValue);
                    }),

                    new Column("设价方式", (cell, object) -> {
                        String cellValue = "";
                        GoodsExportVo vo = (GoodsExportVo) object;
                        GoodsPriceType priceType = GoodsPriceType.fromValue(vo.getPriceType());
                        switch (priceType) {
                            case MARKET:
                                cellValue = "以市场价销售";
                                break;
                            case CUSTOMER:
                                cellValue = "以等级价销售";
                                break;
                            case STOCK:
                                cellValue = "以阶梯价销售";
                                break;
                            default:
                        }
                        cell.setCellValue(cellValue);
                    }),
                    new Column("供应商名称", new SpelColumnRender<GoodsExportVo>("providerName")),
            };
            columns = ArrayUtils.addAll(columns, columns4);
        } else {
            Column[] columns5 = {
                    new Column("供货价(元)", new SpelColumnRender<GoodsExportVo>("supplyPrice"))
            };
            columns = ArrayUtils.addAll(columns, columns5);
        }

        //规格
        for (int i = 0; i < Constants.FIVE; i++) {
            int specIndex = i + 1;
            Column[] specColunms = {
                    new Column("规格" + specIndex, (cell, object) -> {
                        GoodsExportVo vo = (GoodsExportVo) object;
                        String cellValue = "";
                        if (io.seata.common.util.CollectionUtils.isNotEmpty(vo.getGoodsSpecVOList()) && vo.getGoodsSpecVOList().size() >= specIndex) {
                            GoodsInfoSpecExportVO exportVO = vo.getGoodsSpecVOList().get(specIndex - 1);
                            cellValue = exportVO.getSpecName();
                        }
                        cell.setCellValue(cellValue);
                    }),
                    new Column("规格" + specIndex + "规格值", (cell, object) -> {
                        GoodsExportVo vo = (GoodsExportVo) object;
                        String cellValue = "";
                        if (io.seata.common.util.CollectionUtils.isNotEmpty(vo.getGoodsSpecVOList()) && vo.getGoodsSpecVOList().size() >= specIndex) {
                            GoodsInfoSpecExportVO exportVO = vo.getGoodsSpecVOList().get(specIndex - 1);
                            cellValue = exportVO.getSpecDetailName();
                        }
                        cell.setCellValue(cellValue);
                    })

            };
            columns = ArrayUtils.addAll(columns, specColunms);
        }
        return columns;
    }

    public List<GoodsExportVo> getExportData(List<GoodsPageSimpleVO> goodsPageSimpleVOList, List<GoodsCateVO> contractCateListCate) {
        List<GoodsExportVo> goodsExportVoList = new ArrayList<>();

        List<GoodsVO> goodses = KsBeanUtil.copyListProperties(goodsPageSimpleVOList, GoodsVO.class);
        List<String> goodsIds = goodses.stream().map(GoodsVO::getGoodsId).collect(Collectors.toList());
        GoodsInfoListByConditionRequest request = GoodsInfoListByConditionRequest.builder()
                .goodsIds(goodsIds)
                .showPointFlag(Boolean.TRUE)
                .delFlag(DeleteFlag.NO.ordinal()).build();
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(request).getContext().getGoodsInfos();

        //SKU按照goodsId分组
        Map<String, List<GoodsInfoVO>> goodsMap = goodsInfos.stream().collect(Collectors.groupingBy(GoodsInfoVO::getGoodsId));

        //规格
        List<String> goodsId = goodses.stream().map(GoodsVO::getGoodsId).collect(Collectors.toList());
        GoodsSpecListByGoodsIdsRequest specRequest = GoodsSpecListByGoodsIdsRequest.builder().goodsIds(goodsId).build();
        Map<String, List<GoodsInfoSpecExportVO>> goodsInfoSpecMap = goodsSpecQueryProvider.listByGoodsInfoIdsForExport(specRequest).getContext().getGoodsInfoSpecMap();

        //运费模版
        List<Long> freightTempIds = goodses.stream().map(GoodsVO::getFreightTempId).distinct().collect(Collectors.toList());
        FreightTemplateGoodsListByIdsRequest freightTemplateRequest = FreightTemplateGoodsListByIdsRequest.builder().freightTempIds(freightTempIds).build();
        Map<Long, FreightTemplateGoodsVO> templateGoodsVOMap = freightTemplateGoodsQueryProvider
                .listByIds(freightTemplateRequest).getContext().getFreightTemplateGoodsVOList()
                .stream().collect(Collectors.toMap(FreightTemplateGoodsVO::getFreightTempId, Function.identity()));

        if (io.seata.common.util.CollectionUtils.isNotEmpty(goodses)) {
            //遍历SPU
            goodses.forEach(goodsVO -> {
                //获取该SPU的SKU列表
                List<GoodsInfoVO> goodsInfoVOS = goodsMap.get(goodsVO.getGoodsId());

                //运费模版
                FreightTemplateGoodsVO freightTemplateGoodsVO = templateGoodsVOMap.get(goodsVO.getFreightTempId());

                if (io.seata.common.util.CollectionUtils.isNotEmpty(goodsInfoVOS)) {
                    goodsInfoVOS.forEach(goodsInfoVO -> {

                        //组装导出数据
                        GoodsExportVo goodsExportVo = KsBeanUtil.convert(goodsVO, GoodsExportVo.class);
                        KsBeanUtil.copyProperties(goodsInfoVO, goodsExportVo);
                        Map<Integer, String> cateNameMap = this.getContractCate(contractCateListCate, goodsVO);
                        goodsExportVo.setCateNameLevel1(cateNameMap.get(1));
                        goodsExportVo.setCateNameLevel2(cateNameMap.get(2));
                        goodsExportVo.setCateNameLevel3(cateNameMap.get(3));
                        goodsExportVo.setStoreCateName(goodsVO.getStoreCateName());
                        goodsExportVo.setGoodsSpecVOList(goodsInfoSpecMap.get(goodsInfoVO.getGoodsInfoId()));
                        // 批量导出表格中的虚拟商品、电子卡券的重量、体积、运费模板字段为空
                        if (Objects.nonNull(goodsExportVo.getGoodsType()) && goodsExportVo.getGoodsType() > NumberUtils.INTEGER_ZERO){
                            goodsExportVo.setGoodsCubage(null);
                            goodsExportVo.setGoodsWeight(null);
                            goodsExportVo.setFreightTempName("");
                        } else {
                            if (Objects.nonNull(freightTemplateGoodsVO)) {
                                goodsExportVo.setFreightTempName(freightTemplateGoodsVO.getFreightTempName());
                            }
                        }
                        goodsExportVo.setBrandName(goodsVO.getBrandName());
                        if (CollectionUtils.isNotEmpty(goodsVO.getGoodsLabelList())) {
                            StringBuilder goodsLabel = new StringBuilder();
                            goodsVO.getGoodsLabelList().forEach(labe->{
                                goodsLabel.append(labe.getLabelName()).append(",");
                            });
                            goodsExportVo.setGoodsLabel(goodsLabel.toString());
                        }
                        goodsExportVoList.add(goodsExportVo);
                    });
                }
            });
        }
        return goodsExportVoList;
    }

    /**
     * 签约分类
     *
     * @param goodsVO
     * @return
     */
    public Map<Integer, String> getContractCate(List<GoodsCateVO> goodsCateVOList, GoodsVO goodsVO) {
        if (io.seata.common.util.CollectionUtils.isEmpty(goodsCateVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        Map<Integer, String> cateNameMap = new HashMap<>();
        //获取当前商品的分类
        Optional<GoodsCateVO> optional = goodsCateVOList.stream().filter(goodsCateVO -> goodsCateVO.getCateId().equals(goodsVO.getCateId())).findFirst();
        if (optional.isPresent()) {
            GoodsCateVO goodsCateVO = optional.get();
            //上级分类数组
            List<Long> cateIds = Arrays.stream(goodsCateVO.getCatePath().split("\\|")).map(Long::parseLong).collect(Collectors.toList());
            cateIds.add(goodsVO.getCateId());
            //筛选出上级分类和当前分类，按分类层级排序，拼接分类名称
            cateNameMap =
                    goodsCateVOList.stream().filter(vo -> cateIds.contains(vo.getCateId())).collect(Collectors.toMap(GoodsCateVO::getCateGrade, GoodsCateVO::getCateName, (g1,g2)->g1));
        }
        return cateNameMap;
    }

}
