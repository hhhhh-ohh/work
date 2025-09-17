package com.wanmi.sbc.goods.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperatorInteger;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.IterableUtils;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateExcelProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateExcelImportRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListByConditionRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: songhanlin
 * @Date: Created In 11:15 2018-12-18
 * @Description: 商品分类excel导入导出
 */
@Slf4j
@Service
public class GoodsCateExcelService {
    /**
     * 操作日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCateExcelService.class);

    @Value("classpath:/download/goods_cate_template.xls")
    private Resource templateFile;

    @Autowired
    private GoodsCateExcelProvider goodsCateExcelProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    private final static int IMPORT_COUNT_LIIT = 5000;

    public void importGoodsCate(String userId){
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.GOODS_CATE_EXCEL_DIR.concat(userId))
                .build()).getContext().getContent();

        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        } else {
            try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
                Sheet sheet = workbook.getSheetAt(0);
                if (sheet == null) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
                } else {
                    this.checkExcel(workbook);
                    int firstRowNum = sheet.getFirstRowNum();
                    int lastRowNum = sheet.getLastRowNum();
                    if (lastRowNum < 1) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
                    } else {
                        GoodsCateListByConditionRequest goodsCateListByConditionRequest = new GoodsCateListByConditionRequest();
                        goodsCateListByConditionRequest.setDelFlag(DeleteFlag.NO.toValue());
                        List<GoodsCateVO> goodsCateList = goodsCateQueryProvider.listByCondition(goodsCateListByConditionRequest).getContext().getGoodsCateVOList();
                        if (CollectionUtils.isNotEmpty(goodsCateList)) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                        }
                        int maxCell = 6;
                        long cateId = 1;
                        for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; ++rowNum) {
                            Row row = sheet.getRow(rowNum);
                            if (row != null) {
                                Cell[] cells = new Cell[maxCell];
                                for (int i = 0; i < maxCell; i++) {
                                    Cell cell = row.getCell(i);
                                    if (cell == null) {
                                        cell = row.createCell(i);
                                    }
                                    cells[i] = cell;
                                }
                                // 一级类目
                                GoodsCateVO goodsCate = new GoodsCateVO();
                                this.setCells( cells, 0, goodsCate);
                                this.setCells(cells, 1, goodsCate);
                                goodsCate.setCateId(cateId);
                                goodsCate.setCateParentId(0L);
                                goodsCateList.stream().filter(cate -> cate.getCateParentId().intValue() == 0 && StringUtils.equals(goodsCate.getCateName(), cate.getCateName())).
                                        findFirst().ifPresent(cate -> {
                                    goodsCate.setCateId(null);
                                });
                                if (goodsCate.getCateId() != null) {
                                    goodsCateList.add(goodsCate);
                                    cateId++;
                                }

                                //二级类目
                                GoodsCateVO goodsCate1 = new GoodsCateVO();
                                this.setCells(cells, 2, goodsCate1);
                                this.setCells(cells, 3, goodsCate1);
                                GoodsCateVO parentGoodsCate = goodsCate.getCateId() == null
                                        ? goodsCateList.stream().filter(cate -> cate.getCateParentId().intValue() == 0
                                        && StringUtils.equals(cate.getCateName(), goodsCate.getCateName())).findFirst().orElse(null)
                                        : goodsCate;
                                goodsCate1.setCateId(cateId);
                                goodsCate1.setCateParentId(parentGoodsCate.getCateId());
                                goodsCateList.stream().filter(cate -> cate.getCateParentId().equals(parentGoodsCate.getCateId()) && StringUtils.equals(goodsCate1.getCateName(), cate.getCateName())).
                                        findFirst().ifPresent(cate -> {
                                    goodsCate1.setCateId(null);
                                });
                                if (goodsCate1.getCateId() != null) {
                                    goodsCateList.add(goodsCate1);
                                    cateId++;
                                }
                                // 三级类目
                                GoodsCateVO goodsCate2 = new GoodsCateVO();
                                this.setCells(cells, 4, goodsCate2);
                                this.setCells(cells, 5, goodsCate2);
                                GoodsCateVO parentGoodsCate1 = goodsCate1.getCateId() == null
                                        ? goodsCateList.stream().filter(cate -> cate.getCateParentId().equals(parentGoodsCate.getCateId())
                                        && StringUtils.equals(cate.getCateName(), goodsCate1.getCateName())).findFirst().orElse(null)
                                        : goodsCate1;
                                goodsCate2.setCateId(cateId);
                                goodsCate2.setCateParentId(parentGoodsCate1.getCateId());
                                goodsCateList.stream().filter(cate -> cate.getCateParentId().equals(parentGoodsCate1.getCateId()) && StringUtils.equals(goodsCate2.getCateName(), cate.getCateName()))
                                        .findFirst().ifPresent(cate -> goodsCate2.setCateId(null));
                                if (goodsCate2.getCateId() != null) {
                                    goodsCateList.add(goodsCate2);
                                    cateId++;
                                }

                            }
                        }
                        //平均拆分list，分批导入
                        OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
                        int maxSize;
                        if (goodsCateList.size()>3){
                            maxSize = operatorInteger.apply(goodsCateList.size());
                        }else {
                            maxSize = Constants.ONE;
                        }
                        List<List<GoodsCateVO>> splitList = IterableUtils.splitList(goodsCateList, maxSize);
                        //导入
                        this.importGoodsCateAsync(splitList);
                    }
                }
            } catch (SbcRuntimeException var52) {
                log.error("商品类目导入异常", var52);
                throw var52;
            } catch (InterruptedException e){
                log.error("商品类目线程池导入异常", e.getMessage());
                Thread.currentThread().interrupt();
            } catch (Exception var53) {
                log.error("商品类目导入异常", var53);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, var53);
            }
        }
    }

    /**
     * 异步导入
     * @param splitList
     * @throws InterruptedException
     */
    private void importGoodsCateAsync(List<List<GoodsCateVO>> splitList) throws InterruptedException {
        int size = splitList.size();
        final CountDownLatch count = new CountDownLatch(size);
        ExecutorService executorService = this.newThreadPoolExecutor(size);
        for (List<GoodsCateVO> goodsCateList : splitList) {
            executorService.execute(()->{
                try {
                    GoodsCateExcelImportRequest goodsCateExcelImportRequest = new GoodsCateExcelImportRequest();
                    goodsCateExcelImportRequest.setGoodsCateList(goodsCateList);
                    goodsCateExcelProvider.importGoodsCate(goodsCateExcelImportRequest);
                } catch (Exception e) {
                    log.error("类目导入异常,异常数据：{},{}",goodsCateList,e.getMessage());
                } finally {
                    count.countDown();
                }
            });
        }
        count.await();
        executorService.shutdown();
    }

    /**
     * 创建线程池
     * @return
     */
    private ExecutorService newThreadPoolExecutor(int corePoolSize){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("商品类目导入-%d").build();
        int maximumPoolSize = corePoolSize * 2;
        int capacity = corePoolSize * 5;
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(capacity), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    public String errorExcel(String newFileName, Workbook wk) throws SbcRuntimeException {
        String userId = commonUtil.getOperatorId();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            wk.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
                    .resourceName(newFileName)
                    .resourceKey(Constants.GOODS_CATE_ERR_EXCEL_DIR.concat(userId))
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
            return newFileName;
        } catch (IOException e) {
            log.error("生成的错误文件上传至云空间失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
    }


    public void checkExcel(Workbook workbook) {
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            if (!row.getCell(0).getStringCellValue().contains("一级类目名称")) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030067);
            }
        } catch (Exception var5) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030067);
        }
    }

    private boolean checkCells(CellStyle style ,Workbook workbook, Cell[] cells, int num,boolean isError) {
        if (num == Constants.ZERO || num == Constants.TWO || num == Constants.FOUR) {
            if (StringUtils.isBlank(ExcelHelper.getValue(cells[num]))) {
                isError = true;
                ExcelHelper.setError(style,workbook, cells[num], "此项必填");
            } else if (ExcelHelper.getValue(cells[num]).trim().length() > Constants.NUM_20) {
                isError = true;
                ExcelHelper.setError(style,workbook, cells[num], "长度必须1-20个字");
            } else if (ValidateUtil.containsEmoji(ExcelHelper.getValue(cells[num]))) {
                isError = true;
                ExcelHelper.setError(style,workbook, cells[num], "含有非法字符");
            }
        } else {
            if (StringUtils.isNotBlank(ExcelHelper.getValue(cells[num]))) {
                BigDecimal cateRate = null;
                try {
                    cateRate = new BigDecimal(ExcelHelper.getValue(cells[num]).trim());
                    if (cateRate.compareTo(new BigDecimal("0")) < 0 || cateRate.compareTo(new BigDecimal("100")) > 0) {
                        isError = true;
                        ExcelHelper.setError(style,workbook, cells[num], "请填写0-100的整数");
                    }
                } catch (Exception e) {
                    isError = true;
                    ExcelHelper.setError(style,workbook, cells[num], "请填写0-100的整数");
                }
            } else {
                if (num == Constants.FIVE) {
                    isError = true;
                    ExcelHelper.setError(style,workbook, cells[num], "此项必填");
                }
            }
        }
        return isError;
    }

    private void setCells(Cell[] cells, int num, GoodsCateVO goodsCate) {
        if (goodsCate.getCateName() == null) {
            Double grade = Math.ceil((double) (num + 1) / 2);
            goodsCate.setCateGrade(grade.intValue());
        }
        if (num == Constants.ZERO || num == Constants.TWO || num == Constants.FOUR) {
            goodsCate.setCateName(ExcelHelper.getValue(cells[num]).trim());
        } else {
            if (StringUtils.isNotBlank(ExcelHelper.getValue(cells[num]))) {
                BigDecimal cateRate = new BigDecimal(ExcelHelper.getValue(cells[num]).trim());
                goodsCate.setCateRate(cateRate);
            }
        }
    }
    /**
     * 商品类目模板下载
     *
     * @return base64位文件字符串
     */
    public void exportTemplate() {
        if (templateFile == null || !templateFile.exists()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030147);
        }
        InputStream is = null;
        Workbook wk = null;
        try {
            String fileName = URLEncoder.encode("商品类目导入模板.xls", StandardCharsets.UTF_8.name());
            is = templateFile.getInputStream();
            HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";" +
                    "filename*=\"utf-8''%s\"", fileName, fileName));
            wk = WorkbookFactory.create(is);
            wk.write(HttpUtil.getResponse().getOutputStream());
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("读取导入模板异常", e);
                }
            }
            try{
                if(wk != null){
                    wk.close();
                }
            }catch (IOException e){
                LOGGER.error("读取导入模板Workbook关闭异常", e);
            }
        }
    }


    /**
     * 上传商品类目模板
     *
     * @param file
     * @param userId
     * @return
     */
    public String upload(MultipartFile file, String userId) {
        if (file == null || file.isEmpty()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS) || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }
        String resourceKey = Constants.GOODS_CATE_EXCEL_DIR.concat(userId);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            } else {
                this.checkExcel(workbook);
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                if (lastRowNum < 1) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
                } else if(lastRowNum > IMPORT_COUNT_LIIT ){
                    // 不算第一行5000
                    int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                    if ((lastRowNum - emptyRowNum) > IMPORT_COUNT_LIIT) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030071, new Object[]{IMPORT_COUNT_LIIT});
                    }
                } else {
                    GoodsCateListByConditionRequest goodsCateListByConditionRequest = new GoodsCateListByConditionRequest();
                    goodsCateListByConditionRequest.setDelFlag(DeleteFlag.NO.toValue());
                    List<GoodsCateVO> goodsCateList = goodsCateQueryProvider.listByCondition(goodsCateListByConditionRequest).getContext().getGoodsCateVOList();
                    if (CollectionUtils.isNotEmpty(goodsCateList)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    int maxCell = 6;
                    boolean isError = false;
                    CellStyle style = workbook.createCellStyle();
                    for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; ++rowNum) {
                        Row row = sheet.getRow(rowNum);
                        if (row != null) {
                            Cell[] cells = new Cell[maxCell];
                            for (int i = 0; i < maxCell; i++) {
                                Cell cell = row.getCell(i);
                                if (cell == null) {
                                    cell = row.createCell(i);
                                }
                                cells[i] = cell;
                            }
                            // 一级类目

                            isError = this.checkCells(style, workbook, cells, 0,  isError);
                            isError = this.checkCells(style, workbook, cells, 1, isError);
                            //二级类目
                            isError = this.checkCells(style, workbook, cells, 2, isError);
                            isError = this.checkCells(style, workbook, cells, 3, isError);
                            // 三级类目
                            isError = this.checkCells(style, workbook, cells, 4, isError);
                            isError = this.checkCells(style, workbook, cells, 5, isError);
                        }
                    }
                    //上传文件有错误内容
                    if (isError) {
                        this.errorExcel(userId.concat(".").concat(fileExt), workbook);
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[]{fileExt});
                    }
                    YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                            .builder()
                            .resourceType(ResourceType.EXCEL)
                            .content(file.getBytes())
                            .resourceName(file.getOriginalFilename())
                            .resourceKey(resourceKey)
                            .build();
                    yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
                }
            }
        } catch (SbcRuntimeException var52) {
            log.error("商品类目上传异常", var52);
            throw var52;
        } catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }catch (Exception var53) {
            log.error("商品类目上传异常", var53);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, var53);
        }
        return fileExt;
    }

    /**
     * 下载Excel错误文档
     *
     * @param userId 用户Id
     * @param ext    文件扩展名
     */
    public void downErrExcel(String userId, String ext) {
        YunGetResourceResponse yunGetResourceResponse = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.GOODS_CATE_ERR_EXCEL_DIR.concat(userId))
                .build()).getContext();
        if (yunGetResourceResponse == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        byte[] content = yunGetResourceResponse.getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        try (
                InputStream is = new ByteArrayInputStream(content);
                ServletOutputStream os = HttpUtil.getResponse().getOutputStream()
        ) {
            //下载错误文档时强制清除页面文档缓存
            HttpServletResponse response = HttpUtil.getResponse();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("expries", -1);
            String fileName = URLEncoder.encode("错误表格.".concat(ext), StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition",
                    String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));

            byte b[] = new byte[1024];
            //读取文件，存入字节数组b，返回读取到的字符数，存入read,默认每次将b数组装满
            int read = is.read(b);
            while (read != -1) {
                os.write(b, 0, read);
                read = is.read(b);
            }
            HttpUtil.getResponse().flushBuffer();
        } catch (Exception e) {
            log.error("下载EXCEL文件异常->", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }


    }


}
