package com.wanmi.sbc.goods.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.OperatorInteger;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandExcelProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandExcelImportRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandNamesExistRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandNickNamesExistRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
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
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

/**
 * @description 商品品牌excel导入导出
 * @author malianfeng
 * @date 2022/8/30 14:56
 */
@Slf4j
@Service
public class GoodsBrandExcelService {
    /**
     * 操作日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsBrandExcelService.class);

    @Value("classpath:/download/goods_brand_template.xlsx")
    private Resource templateFile;

    @Autowired
    private GoodsBrandExcelProvider goodsBrandExcelProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    private final static int IMPORT_COUNT_LIMIT = 1000;

    /**
     * 支持的图片格式列表
     */
    private final static List<String> SUPPORT_IMAGE_SUFFIXES = Arrays.asList("jpg", "jpeg", "png", "gif");

    /**
     * 支持的图片最大字节数
     */
    private final static Long SUPPORT_IMAGE_MAX_BYTE = 50 * 1024L;


    public void importGoodsBrand(String userId){
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.GOODS_BRAND_EXCEL_DIR.concat(userId))
                .build()).getContext().getContent();

        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }

        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            // 验证文档是否正确
            this.checkExcel(workbook);
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            } else if (lastRowNum > IMPORT_COUNT_LIMIT) {
                // 不算第一行1000
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > IMPORT_COUNT_LIMIT) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030071, new Object[] {IMPORT_COUNT_LIMIT});
                }
            }

            List<GoodsBrandVO> goodsBrandList = new ArrayList<>();
            int maxCell = 5;
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; ++rowNum) {

                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                boolean isNotEmpty = false;
                Cell[] cells = new Cell[maxCell];
                for (int i = 0; i < maxCell; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cells[i] = cell;
                    if (StringUtils.isNotBlank(ExcelHelper.getValue(cell))) {
                        isNotEmpty = true;
                    }
                }
                // 数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }

                // 品牌名称
                String brandName = ExcelHelper.getValue(getCell(cells, 0));
                // 品牌别名
                String nickName = ExcelHelper.getValue(getCell(cells, 1));
                // logo图片url
                String logoUrl = ExcelHelper.getValue(getCell(cells, 2));
                // 是否推荐
                String recommendFlag = ExcelHelper.getValue(getCell(cells, 3));
                DefaultFlag recommendFlagVal = "是".equals(recommendFlag) ? DefaultFlag.YES : DefaultFlag.NO;
                // 排序号
                String brandSort = ExcelHelper.getValue(getCell(cells, 4));
                Long brandSortVal = StringUtils.isNotBlank(brandSort) ? Long.parseLong(brandSort) : 0L;

                // 构造品牌列表
                GoodsBrandVO goodsBrandVO = new GoodsBrandVO();
                goodsBrandVO.setBrandName(brandName);
                goodsBrandVO.setNickName(nickName);
                goodsBrandVO.setLogo(logoUrl);
                goodsBrandVO.setRecommendFlag(recommendFlagVal);
                goodsBrandVO.setBrandSort(brandSortVal);
                goodsBrandList.add(goodsBrandVO);
            }
            // 平均拆分list，分批导入
            OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
            int maxSize = operatorInteger.apply(goodsBrandList.size());
            List<List<GoodsBrandVO>> splitList = IterableUtils.splitList(goodsBrandList, maxSize);
            // 导入
            this.importGoodsCateAsync(splitList);
        } catch (SbcRuntimeException var52) {
            log.error("商品品牌导入异常", var52);
            throw var52;
        } catch (InterruptedException e){
            log.error("商品品牌线程池导入异常", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception var53) {
            log.error("商品品牌导入异常", var53);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, var53);
        }
    }

    /**
     * 异步导入
     * @param splitList
     * @throws InterruptedException
     */
    private void importGoodsCateAsync(List<List<GoodsBrandVO>> splitList) throws InterruptedException {
        int size = splitList.size();
        final CountDownLatch count = new CountDownLatch(size);
        ExecutorService executorService = this.newThreadPoolExecutor(size);
        for (List<GoodsBrandVO> goodsBrandList : splitList) {
            executorService.execute(() -> {
                try {
                    GoodsBrandExcelImportRequest goodsBrandExcelImportRequest = new GoodsBrandExcelImportRequest();
                    goodsBrandExcelImportRequest.setGoodsBrandList(goodsBrandList);
                    goodsBrandExcelProvider.importGoodsBrand(goodsBrandExcelImportRequest);
                } catch (Exception e) {
                    log.error("品牌导入异常,异常数据：{},{}",goodsBrandList,e.getMessage());
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
                .setNameFormat("商品品牌导入-%d").build();
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
                    .resourceKey(Constants.GOODS_BRAND_ERR_EXCEL_DIR.concat(userId))
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest);
            return newFileName;
        } catch (IOException e) {
            log.error("生成的错误文件上传至云空间失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
    }


    /**
     * 验证文档是否正确
     * @param workbook
     */
    public void checkExcel(Workbook workbook) {
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            List<String> columnNames = Arrays.asList("品牌名称", "品牌别名", "品牌logo", "是否推荐", "排序号");
            for (int i = 0; i < columnNames.size(); i++) {
                if (!row.getCell(i).getStringCellValue().contains(columnNames.get(i))) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030067);
                }
            }
        } catch (Exception var5) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030067);
        }
    }

    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }

    /**
     * 商品品牌模板下载
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
            String fileName = URLEncoder.encode("商品品牌导入模板.xlsx", StandardCharsets.UTF_8.name());
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
                    LOGGER.error("读取品牌导入模板异常", e);
                }
            }
            try{
                if(wk != null){
                    wk.close();
                }
            }catch (IOException e){
                LOGGER.error("读取品牌导入模板Workbook关闭异常", e);
            }
        }
    }


    /**
     * 上传商品品牌模板
     *
     * @param file
     * @param userId
     * @return
     */
    public String upload(MultipartFile file, String userId) {
        if (file == null || file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String originalFilename = file.getOriginalFilename();
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS) || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }
        String resourceKey = Constants.GOODS_BRAND_EXCEL_DIR.concat(userId);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            // 验证文档是否正确
            this.checkExcel(workbook);
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            } else if (lastRowNum > IMPORT_COUNT_LIMIT) {
                // 不算第一行1000
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > IMPORT_COUNT_LIMIT) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030071, new Object[] {IMPORT_COUNT_LIMIT});
                }
            }

            // 校验品牌logo图片url的格式和大小，返回错误信息Map
            Map<String, String> logoUrlErrMap = this.checkBrandLogoUrl(workbook);
            // 校验品牌名称是否存在，返回存在的Set
            Set<String> brandNameExistSet = this.checkExistBrandName(workbook);
            // 校验品牌别名是否存在，返回存在的Set
            Set<String> brandNickNameExistSet = this.checkExistBrandNickName(workbook);

            int maxCell = 5;
            boolean isError = false;
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 临时存储品牌名称、别名、logo地址、以及单元格对象，验证重复
            Set<String> brandNameSet = new HashSet<>();
            Set<String> nickNameSet = new HashSet<>();

            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; ++rowNum) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                boolean isNotEmpty = false;
                Cell[] cells = new Cell[maxCell];
                for (int i = 0; i < maxCell; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cells[i] = cell;
                    if (StringUtils.isNotBlank(ExcelHelper.getValue(cell))) {
                        isNotEmpty = true;
                    }
                }
                // 数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }

                // 校验品牌名称
                String brandName = ExcelHelper.getValue(getCell(cells, 0));
                if (StringUtils.isBlank(brandName)) {
                    ExcelHelper.setError(workbook, getCell(cells, 0), "此项必填", style);
                    isError = true;
                } else {
                    if (brandNameSet.contains(brandName)) {
                        // 为单元格设置重复错误提示
                        ExcelHelper.setError(workbook, getCell(cells, 0), "该品牌名称在表中重复");
                        isError = true;
                    } else {
                        brandNameSet.add(brandName);
                        if (brandName.length() > 30) {
                            ExcelHelper.setError(workbook, getCell(cells, 0), "品牌名称长度不能超过30", style);
                            isError = true;
                        } else if (brandNameExistSet.contains(brandName)) {
                            ExcelHelper.setError(workbook, getCell(cells, 0), "该品牌名称已存在", style);
                            isError = true;
                        }
                    }
                }

                // 校验品牌别名
                String nickName = ExcelHelper.getValue(getCell(cells, 1));
                if (StringUtils.isNotBlank(nickName)) {
                    if (nickNameSet.contains(nickName)) {
                        // 为单元格设置重复错误提示
                        ExcelHelper.setError(workbook, getCell(cells, 1), "该品牌别名在表中重复");
                        isError = true;
                    } else {
                        nickNameSet.add(nickName);
                        if (nickName.length() > 30) {
                            ExcelHelper.setError(workbook, getCell(cells, 1), "品牌别名长度不能超过30", style);
                            isError = true;
                        } else if (brandNickNameExistSet.contains(nickName)) {
                            ExcelHelper.setError(workbook, getCell(cells, 1), "该品牌别名已存在", style);
                            isError = true;
                        }
                    }
                }

                // 校验logo图片url
                String logoUrl = ExcelHelper.getValue(getCell(cells, 2));
                if (logoUrlErrMap.containsKey(logoUrl)) {
                    ExcelHelper.setError(workbook, getCell(cells, 2), logoUrlErrMap.get(logoUrl), style);
                    isError = true;
                }

                // 校验是否推荐
                String recommendFlag = ExcelHelper.getValue(getCell(cells, 3));
                if (StringUtils.isNotBlank(recommendFlag)) {
                    if (!recommendFlag.equals("是") && !recommendFlag.equals("否")) {
                        ExcelHelper.setError(workbook, getCell(cells, 3), "只允许输入'是'或'否'", style);
                        isError = true;
                    }
                }

                // 校验排序号
                String brandSort = ExcelHelper.getValue(getCell(cells, 4));
                if (StringUtils.isNotBlank(brandSort)) {
                    if(!ValidateUtil.isNum(brandSort)) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 4), "仅允许数字");
                        isError = true;
                    } else {
                        long brandSortVal = Long.parseLong(brandSort);
                        if (brandSortVal < 0 || brandSortVal > 99999999) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 4), "必须在0-99999999范围内");
                            isError = true;
                        }
                    }
                }

            }

            // 上传文件有错误内容
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
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest);

        } catch (SbcRuntimeException var52) {
            log.error("商品品牌上传异常", var52);
            throw var52;
        } catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }catch (Exception var53) {
            log.error("商品品牌上传异常", var53);
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
                .resourceKey(Constants.GOODS_BRAND_ERR_EXCEL_DIR.concat(userId))
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
            // 读取文件，存入字节数组b，返回读取到的字符数，存入read,默认每次将b数组装满
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

    /**
     * 校验品牌名称是否存在
     */
    public Set<String> checkExistBrandName(Workbook workbook) {
        // 收集品牌名称，排除空串，去重
        List<String> brandNameList = ExcelUtils.getCellValues(workbook, 0, true);
        if (CollectionUtils.isEmpty(brandNameList)) {
            return Collections.emptySet();
        }
        List<String> existNames = goodsBrandQueryProvider.queryExistBrandNames(GoodsBrandNamesExistRequest.builder()
                .brandNames(brandNameList).build()).getContext().getExistNames();
        return new HashSet<>(existNames);
    }

    /**
     * 校验品牌别名是否存在
     */
    public Set<String> checkExistBrandNickName(Workbook workbook) {
        // 收集品牌别名，排除空串，去重
        List<String> brandNickNameList = ExcelUtils.getCellValues(workbook, 1, true);
        if (CollectionUtils.isEmpty(brandNickNameList)) {
            return Collections.emptySet();
        }
        List<String> existNickNames = goodsBrandQueryProvider.queryExistNickNames(GoodsBrandNickNamesExistRequest.builder()
                .nickNames(brandNickNameList).build()).getContext().getExistNames();
        return new HashSet<>(existNickNames);
    }

    /**
     * 校验品牌logo图片url的格式和大小
     * @param workbook
     * @return
     */
    public Map<String, String> checkBrandLogoUrl(Workbook workbook) {
        // 收集品牌 logo列 所有的图片url，排除空串，去重
        List<String> imageUrlList = ExcelUtils.getCellValues(workbook, 2, true);
        if (CollectionUtils.isEmpty(imageUrlList)) {
            return Collections.emptyMap();
        }
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        // map收集错误信息
        ConcurrentHashMap<String, String> errorMap = new ConcurrentHashMap<>();
        try {
            // 构造子线程任务列表，并阻塞主线程，任务全部完成，再校验表格其他字段
            CompletableFuture.allOf(imageUrlList.stream()
                    .map(imageUrl -> CompletableFuture.supplyAsync(() -> {
                                // 返回子线程校验的结果
                                return checkImageUrl(imageUrl, SUPPORT_IMAGE_SUFFIXES, SUPPORT_IMAGE_MAX_BYTE);
                            }, threadPool)
                                    .exceptionally(throwable -> "图片地址异常")
                                    .whenComplete((result, throwable) -> {
                                        // 子线程完成时，记录返回信息，处理异常
                                        if (Objects.nonNull(throwable)) {
                                            errorMap.put(imageUrl, "图片地址异常");
                                        } else if (Objects.nonNull(result)) {
                                            errorMap.put(imageUrl, result);
                                        }
                                    })
                    ).toArray(CompletableFuture[]::new)
            ).join();
        } catch (Exception e) {
            log.error("商品品牌上传异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        } finally {
            // 关闭线程池
            threadPool.shutdown();
        }
        return errorMap;
    }

    public static String checkImageUrl(String imageUrl, List<String> supportSuffix, long maxSize) {
        if (StringUtils.isNotBlank(imageUrl)) {

            // 1. 校验图片后缀
            String[] splitList = imageUrl.split("\\.");
            int lastIndex = splitList.length - 1;
            if (lastIndex < 0) {
                return "不是图片地址";
            }
            if (!supportSuffix.contains(splitList[lastIndex].toLowerCase())) {
                return "仅支持jpg、jpge、png、gif格式的图片";
            }

            // 2. 校验图片大小
            try {
                URL url = new URL(imageUrl);
                int len, total = 0;
                byte[] buffer = new byte[10240];
                URLConnection connection = url.openConnection();
                connection.setConnectTimeout(1500);
                connection.setReadTimeout(3000);
                InputStream inputStream = connection.getInputStream();
                while ((len = inputStream.read(buffer)) != -1) {
                    total += len;
                    if (total > maxSize) {
                        inputStream.close();
                        return String.format("图片大小不能超过%skb", maxSize / 1024);
                    }
                }
            } catch (IOException e) {
                log.error("无法访问[{}]，{}", imageUrl, e.getMessage());
                return "图片地址无法访问";
            }
        }
        return null;
    }

}
