package com.wanmi.sbc.mq.report.service;


import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardQueryProvider;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardPageRequest;
import com.wanmi.sbc.elastic.bean.vo.goods.EsStandardGoodsPageVO;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardSkuQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListCateByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsByConditionRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsImportState;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LmGoodsExportService implements ExportBaseService {

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private EsStandardQueryProvider esStandardQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ContractCateQueryProvider contractCateQueryProvider;

    @Autowired
    private StandardSkuQueryProvider standardSkuQueryProvider;

    @Resource(name="reportExecutor")
    private Executor executor;

    private final static Integer SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出LinkedMall商品_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("goods/excel/%s/%s/%s", data.getStoreId(),
                data.getUserId(), fileName);

        boolean isBoss = data.getPlatform() == Platform.PLATFORM;

        //查询商品数据量
        EsStandardPageRequest esStandardPageRequest = JSON.parseObject(data.getParam(), EsStandardPageRequest.class);
        esStandardPageRequest.setPageNum(0);
        if (!isBoss){
            esStandardPageRequest.setStoreId(data.getStoreId());
        }
        esStandardPageRequest.setDelFlag(DeleteFlag.NO.toValue());


        Long total = esStandardQueryProvider.count(esStandardPageRequest).getContext();
        //写入表头
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] colunms = this.getColunms(data.getPlatform() == Platform.SUPPLIER,
                isBoss);
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("LinkedMall商品导出", colunms);
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

        esStandardPageRequest.setPageSize(SIZE);
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页写获取数据并写入excel
        int rowIndex = 0;
        for (int i = 0; i < fileSize; i++) {
            esStandardPageRequest.setPageNum(i);
            List<EsStandardGoodsPageVO> esStandardGoodsPageVO = esStandardQueryProvider.page(esStandardPageRequest).getContext().getStandardGoodsPage().getContent();
            if (CollectionUtils.isNotEmpty(esStandardGoodsPageVO)) {
                List<FutureTask<List<GoodsExportVo>>> futureTasks = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(esStandardGoodsPageVO)) {
                    //分十组异步查询
                    List<Collection> splitList = ReportUtil.splitList(esStandardGoodsPageVO, 10, SIZE / 10);
                    for (int n = 0; n < splitList.size(); n++) {
                        List<EsStandardGoodsPageVO> goodsList = (List<EsStandardGoodsPageVO>) splitList.get(n);
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

    private Column[] getColunms(boolean isSupplier, boolean isBoss) {
        Column[] columns = {};
        if (isBoss || isSupplier) {
            Column[] columns1 = {
                    new Column("商品图片", new SpelColumnRender<GoodsExportVo>("goodsImg")),
                    new Column("商品名称", new SpelColumnRender<GoodsExportVo>("goodsInfoName")),
                    new Column("SPU编码", new SpelColumnRender<GoodsExportVo>("goodsNo")),
                    new Column("一级类目", new SpelColumnRender<GoodsExportVo>("cateNameLevel1")),
                    new Column("二级类目", new SpelColumnRender<GoodsExportVo>("cateNameLevel2")),
                    new Column("三级类目", new SpelColumnRender<GoodsExportVo>("cateNameLevel3")),
                    new Column("供应商名称", new SpelColumnRender<GoodsExportVo>("providerName")),
                    new Column("供货价", new SpelColumnRender<GoodsExportVo>("supplyPrice")),
                    new Column("库存", new SpelColumnRender<GoodsExportVo>("stock")),
                    new Column("上下架", (cell, object) -> {
                        GoodsExportVo vo = (GoodsExportVo) object;
                        if(vo.getAddedFlag() != null){
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
                        }
                    }),
            };
            columns = ArrayUtils.addAll(columns, columns1);
        }
        if(isSupplier){
            Column[] columns2 = {
                    new Column("导入状态", (cell, object) -> {
                        GoodsExportVo vo = (GoodsExportVo) object;
                        if (vo.getImportState() != null) {
                            String cellValue = "";
                            switch (vo.getImportState()) {
                                case 0:
                                    cellValue = "未导入";
                                    break;
                                case 1:
                                    cellValue = "已导入";
                                    break;
                                default:
                            }
                            cell.setCellValue(cellValue);
                        }
                    }),
            };
            columns = ArrayUtils.addAll(columns,columns2);
        }
        return columns;
    }



    public List<GoodsExportVo>  getExportData(List<EsStandardGoodsPageVO> goodsList, List<GoodsCateVO> goodsCateVOS) {
        List<GoodsExportVo> goodsExportVoList = new ArrayList<>();
        List<StandardGoodsVO> goodses = KsBeanUtil.copyListProperties(goodsList, StandardGoodsVO.class);
        List<String> goodsIds = goodses.stream().map(StandardGoodsVO::getGoodsId).collect(Collectors.toList());
        StandardGoodsByConditionRequest request = StandardGoodsByConditionRequest.builder()
                .goodsIds(goodsIds)
                .showPointFlag(Boolean.TRUE)
                .delFlag(DeleteFlag.NO.ordinal()).build();

        List<StandardSkuVO> goodsInfos = standardSkuQueryProvider.listByCondition(request).getContext().getGoodsInfos();

        //SKU按照goodsId分组
        Map<String, List<StandardSkuVO>> goodsMap = goodsInfos.stream().collect(Collectors.groupingBy(StandardSkuVO::getGoodsId));

        if (io.seata.common.util.CollectionUtils.isNotEmpty(goodses)) {
            //遍历SPU
            goodses.forEach(standardGoodsVO -> {
                //获取该SPU的SKU列表
                List<StandardSkuVO> goodsInfoVOS = goodsMap.get(standardGoodsVO.getGoodsId());

                if (io.seata.common.util.CollectionUtils.isNotEmpty(goodsInfoVOS)) {
                    goodsInfoVOS.forEach(goodsInfoVO -> {

                        //组装导出数据
                        GoodsExportVo goodsExportVo = KsBeanUtil.convert(standardGoodsVO, GoodsExportVo.class);
                        KsBeanUtil.copyProperties(goodsInfoVO, goodsExportVo);
                        Map<Integer, String> cateNameMap = this.getContractCate(goodsCateVOS, standardGoodsVO);
                        goodsExportVo.setCateNameLevel1(cateNameMap.get(1));
                        goodsExportVo.setCateNameLevel2(cateNameMap.get(2));
                        goodsExportVo.setCateNameLevel3(cateNameMap.get(3));
                        goodsExportVo.setImportState(standardGoodsVO.getImportState());

                        goodsExportVo.setProviderName("LinkedMall");

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
    public Map<Integer, String> getContractCate(List<GoodsCateVO> goodsCateVOS, StandardGoodsVO goodsVO) {
        if (io.seata.common.util.CollectionUtils.isEmpty(goodsCateVOS)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        Map<Integer, String> cateNameMap = new HashMap<>();
        //获取当前商品的分类
        Optional<GoodsCateVO> optional = goodsCateVOS.stream().filter(goodsCateVO -> goodsCateVO.getCateId().equals(goodsVO.getCateId())).findFirst();
        if (optional.isPresent()) {
            GoodsCateVO goodsCateVO = optional.get();
            //上级分类数组
            List<Long> cateIds = Arrays.stream(goodsCateVO.getCatePath().split("\\|")).map(Long::parseLong).collect(Collectors.toList());
            cateIds.add(goodsVO.getCateId());
            //筛选出上级分类和当前分类，按分类层级排序，拼接分类名称
            cateNameMap =
                    goodsCateVOS.stream().filter(vo -> cateIds.contains(vo.getCateId())).collect(Collectors.toMap(GoodsCateVO::getCateGrade, GoodsCateVO::getCateName, (g1,g2)->g1));
        }
        return cateNameMap;
    }
}

