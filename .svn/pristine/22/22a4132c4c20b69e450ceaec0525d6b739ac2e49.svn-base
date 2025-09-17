package com.wanmi.sbc.electroniccoupon.service;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperatorInteger;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.IterableUtils;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicImportCheckRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicImportRecordAddRequest;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicImportCheckResponse;
import com.wanmi.sbc.marketing.bean.dto.ElectronicCardDTO;
import com.wanmi.sbc.marketing.bean.enums.CardState;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunFileDeleteRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.util.CommonUtil;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuyunpeng
 * @className ElectronicExcelService
 * @description 卡券Excel服务
 * @date 2022/2/3 11:20 下午
 **/
@Slf4j
@Service
public class ElectronicExcelService {

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private ElectronicCardQueryProvider electronicCardQueryProvider;

    @Autowired
    private ElectronicCardProvider electronicCardProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private ElectronicExcelSaveService electronicExcelSaveService;

    /**
     * 上传文件
     * @param file
     * @return
     */
    public String upload(MultipartFile file, Long couponId) {
        if (file == null || file.isEmpty()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS) || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }

        String resourceKey = Constants.ELECTRONIC_EXCEL_DIR.concat("_").concat(commonUtil.getOperatorId());
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080199);
            }
            CellStyle style = workbook.createCellStyle();
            //检测模板正确性
            this.checkExcel(workbook);
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < Constants.TWO) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080199);
            }
            //当前sheet是否全部为空
            boolean isAllEmpty = true;

            try {
                int maxCell = 4;
                boolean isError = false;
                //存储编码以及单元格对象，验证重复
                Map<String, Cell> numbers = new HashMap<>();
                Map<String, Cell> passwords = new HashMap<>();
                Map<String, Cell> codes = new HashMap<>();
                log.info("卡券导入循环开始"+System.currentTimeMillis());
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum + 2; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        if (rowNum == firstRowNum + 2) {
                            //首行没有row时，创建一个空行
                            row = sheet.createRow(rowNum);
                            for (int i = 0; i < maxCell; i++) {
                                row.createCell(i);
                            }
                        } else {
                            continue;
                        }
                    }
                    Cell[] cells = new Cell[maxCell];
                    boolean isNotEmpty = false;
                    for (int i = 0; i < maxCell; i++) {
                        Cell cell = row.getCell(i);
                        if (cell == null) {
                            cell = row.createCell(i);
                        }
                        //清除批注，防止文件有批注
                        ExcelHelper.clearComment(workbook, cell);
                        cells[i] = cell;
                        if (StringUtils.isNotBlank(ExcelHelper.getValue(cell))) {
                            isNotEmpty = true;
                            isAllEmpty = false;
                        }
                    }
                    //非首行，数据都为空，则跳过去
                    if (rowNum != firstRowNum + 2 && !isNotEmpty) {
                        continue;
                    }

                    //卡号
                    String cardNumber = ExcelHelper.getValue(getCell(cells, 0));
                    if (StringUtils.isNotBlank(cardNumber)) {
                        if (numbers.containsKey(cardNumber)) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 0), "文档中出现重复的卡号");
                            isError = true;
                        }
                        if (cardNumber.length() > 128) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 1), "卡号长度不可超过128");
                            isError = true;
                        }
                        if (!isError) {
                            numbers.put(cardNumber, getCell(cells, 0));
                        }
                    }

                    //卡密
                    String cardPassword = ExcelHelper.getValue(getCell(cells, 1));
                    if (StringUtils.isNotBlank(cardPassword)) {
                        if (passwords.containsKey(cardPassword)) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 1), "文档中出现重复的卡密");
                            isError = true;
                        }
                        if (cardPassword.length() > 128) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 1), "卡密长度不可超过128");
                            isError = true;
                        }
                        if (!isError) {
                            passwords.put(cardPassword, getCell(cells, 1));
                        }
                    }

                    //优惠码
                    String cardPromoCode = ExcelHelper.getValue(getCell(cells, 2));
                    if (StringUtils.isNotBlank(cardPromoCode)) {
                        if (codes.containsKey(cardPromoCode)) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 2), "文档中出现重复的优惠码");
                            isError = true;
                        }
                        if (cardPromoCode.length() > 128) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 1), "优惠码长度不可超过128");
                            isError = true;
                        }
                        if (!isError) {
                            codes.put(cardPromoCode, getCell(cells, 2));
                        }
                    }

                    if (StringUtils.isBlank(cardNumber) && StringUtils.isBlank(cardPassword) && StringUtils.isBlank(cardPromoCode)) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 0), "卡号、卡密、优惠码至少填一项");
                        isError = true;
                    }

                    //销售结束时间取第一行数据
                    if (rowNum == firstRowNum + 2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        Cell cell = getCell(cells, 3);
                        String saleEndTime = ExcelHelper.getValue(cell);
                        if (StringUtils.isBlank(saleEndTime)) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 3), "此项必填");
                            isError = true;
                        } else {
                            //指定日期格式
                            CellStyle dateStyle = workbook.createCellStyle();
                            short format = workbook.createDataFormat().getFormat("yyyy/m/d hh:mm");
                            dateStyle.setDataFormat(format);
                            try {
                                if (cell.getCellType().equals(CellType.NUMERIC) && DateUtil.isCellDateFormatted(cell)) {
                                    Date javaDate = DateUtil.getJavaDate(Double.parseDouble(saleEndTime));
                                    saleEndTime = sdf.format(javaDate);
                                }
                                LocalDateTime endTime = LocalDateTime.parse(saleEndTime, timeFormatter);
                                LocalDateTime now = LocalDateTime.now();
                                if (endTime.getMinute() != 0 || endTime.getSecond() != 0) {
                                    ExcelHelper.setError(dateStyle,workbook, getCell(cells, 3), "销售结束时间只允许整点");
                                    isError = true;
                                } else if (endTime.isBefore(now) || endTime.isEqual(now)) {
                                    ExcelHelper.setError(dateStyle,workbook, getCell(cells, 3), "销售结束时间必须在当前时间之后");
                                    isError = true;
                                }
                            } catch (Exception e) {
                                ExcelHelper.setError(dateStyle,workbook, getCell(cells, 3), "日期格式错误，请按'2021/1/2 10:00'格式输入");
                                isError = true;
                            }
                        }
                    }
                }

                ElectronicImportCheckRequest request = ElectronicImportCheckRequest.builder()
                        .numbers(Lists.newArrayList(numbers.keySet()))
                        .passwords(Lists.newArrayList(passwords.keySet()))
                        .codes(Lists.newArrayList(codes.keySet()))
                        .couponId(couponId)
                        .build();
                ElectronicImportCheckResponse response = electronicCardQueryProvider.getExistsData(request).getContext();

                if (CollectionUtils.isNotEmpty(response.getNumbers())) {
                    for (Map.Entry<String, Cell> entry : numbers.entrySet()) {
                        Cell cell = entry.getValue();
                        if (response.getNumbers().contains(entry.getKey())) {
                            ExcelHelper.setError(style,workbook, cell, "卡号已存在");
                            isError = true;
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(response.getPasswords())) {
                    for (Map.Entry<String, Cell> entry : passwords.entrySet()) {
                        Cell cell = entry.getValue();
                        if (response.getPasswords().contains(entry.getKey())) {
                            ExcelHelper.setError(style,workbook, cell, "卡密已存在");
                            isError = true;
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(response.getCodes())) {
                    for (Map.Entry<String, Cell> entry : codes.entrySet()) {
                        Cell cell = entry.getValue();
                        if (response.getCodes().contains(entry.getKey())) {
                            ExcelHelper.setError(style,workbook, cell, "优惠码已存在");
                            isError = true;
                        }
                    }
                }

                if(isAllEmpty) {
                    //表格空数据
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080199);
                } else if (isError) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030069);
                }
            } catch (SbcRuntimeException e) {
                if (GoodsErrorCodeEnum.K030069.getCode().equals(e.getErrorCode())
                        || CommonErrorCodeEnum.K999999.getCode().equals(e.getErrorCode())) {
                    //上传错误提示excel文件
                    uploadErrorFile(workbook);
                    e.setData(fileExt);
                    e.setErrorCode(GoodsErrorCodeEnum.K030069.getCode());
                }
                throw e;
            }

            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(file.getBytes())
                    .resourceName(file.getOriginalFilename())
                    .resourceKey(resourceKey)
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest);
        } catch (SbcRuntimeException e) {
            log.error("卡券上传异常，resourceKey={}", resourceKey, e);
            if (CommonErrorCodeEnum.K000001.getCode().equals(e.getErrorCode())){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030070);
            }
            throw e;
        } catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为{}", resourceKey, e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        } catch (Exception e) {
            log.error("卡券上传异常，resourceKey={},异常信息：{}", resourceKey, e.getMessage());
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030070);
        }

        return fileExt;
    }


    /**
     * 确认导入
     */
    public void importExcel(Long couponId) {
        String resourceKey = Constants.ELECTRONIC_EXCEL_DIR.concat("_").concat(commonUtil.getOperatorId());
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
                .build()).getContext().getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        // 创建Workbook工作薄对象，表示整个excel
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime saleEndTime = null;
            List<ElectronicCardDTO> electronicCardDTOS = new ArrayList<>(1024);
            try {
                Sheet sheet = workbook.getSheetAt(0);
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                int maxCell = 4;
                this.checkExcel(workbook);
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum + 2; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        if (rowNum == firstRowNum + 2) {
                            //首行没有row时，创建一个空行
                            row = sheet.createRow(rowNum);
                            for (int i = 0; i < maxCell; i++) {
                                row.createCell(i);
                            }
                        } else {
                            continue;
                        }
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
                    //非首行，数据都为空，则跳过去
                    if (rowNum != firstRowNum + 2 && !isNotEmpty) {
                        continue;
                    }

                    ElectronicCardDTO dto = new ElectronicCardDTO();
                    String cardNumber = ExcelHelper.getValue(getCell(cells, 0));
                    String cardPassword = ExcelHelper.getValue(getCell(cells, 1));
                    String cardPromoCode = ExcelHelper.getValue(getCell(cells, 2));
                    if (rowNum == firstRowNum + 2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        Cell cell = getCell(cells, 3);
                        String endTime = ExcelHelper.getValue(cell);
                        if (cell.getCellType().equals(CellType.NUMERIC) && DateUtil.isCellDateFormatted(cell)) {
                            Date javaDate = DateUtil.getJavaDate(Double.parseDouble(endTime));
                            endTime = sdf.format(javaDate);
                        }
                        saleEndTime = LocalDateTime.parse(endTime, timeFormatter);
                    }
                    dto.setCardNumber(cardNumber);
                    dto.setCardPassword(cardPassword);
                    dto.setCardPromoCode(cardPromoCode);
                    dto.setSaleStartTime(now);
                    dto.setSaleEndTime(saleEndTime);
                    dto.setCouponId(couponId);
                    dto.setCardState(CardState.NOT_SEND.toValue());
                    dto.setDelFlag(DeleteFlag.NO);
                    electronicCardDTOS.add(dto);
                }
            } catch (SbcRuntimeException se) {
                if (GoodsErrorCodeEnum.K030069.getCode().equals(se.getErrorCode())
                        || CommonErrorCodeEnum.K999999.getCode().equals(se.getErrorCode())) {
                    // 云服务删除原文件
                    yunServiceProvider.deleteFile(new YunFileDeleteRequest(Collections.singletonList(resourceKey)));
                    //上传错误提示excel文件
                    String errorFileUrl = uploadErrorFile(workbook);
                    se.setData(errorFileUrl);
                    se.setErrorCode(GoodsErrorCodeEnum.K030069.getCode());
                }
                throw se;
            }

            //保存导入记录
            String recordId = electronicCardProvider.addImportRecord(ElectronicImportRecordAddRequest.builder()
                    .couponId(couponId)
                    .createTime(now)
                    .saleStartTime(now)
                    .saleEndTime(saleEndTime).build())
                    .getContext()
                    .getElectronicImportRecordVO().getId();

            //异步分批保存数据
            OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
            int maxSize = operatorInteger.apply(electronicCardDTOS.size());
            List<List<ElectronicCardDTO>> splitList = IterableUtils.splitList(electronicCardDTOS, maxSize);
            this.importRecordAndDetailsAsync(splitList, recordId);
            // 云服务删除原文件
            yunServiceProvider.deleteFile(new YunFileDeleteRequest(Collections.singletonList(resourceKey)));
        }  catch (SbcRuntimeException e) {
            log.error("卡券导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("卡券导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 异步导入数据
     */
    public void importRecordAndDetailsAsync(List<List<ElectronicCardDTO>> splitList, String recordId) {
        try {
            final CountDownLatch count = new CountDownLatch(splitList.size());
            ExecutorService executor = this.newThreadPoolExecutor(splitList.size());
            for (List<ElectronicCardDTO> dtos : splitList) {
                executor.execute(()->{
                    try {
                        electronicExcelSaveService.saveData(dtos, recordId);
                    } catch (Exception e) {
                        log.error("卡密导入异常：{}",e.getMessage());
                    } finally {
                        // 无论是否报错始终执行countDown()，否则报错时主进程一直会等待线程结束
                        count.countDown();
                    }
                });
            }
            count.await();
            executor.shutdown();
        } catch (Exception e) {
            log.error("异步编排导入异常：{}",e.getMessage());
        }
    }


    /**
     * 创建线程池
     * @return
     */
    private ExecutorService newThreadPoolExecutor(int corePoolSize){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("卡券导入-%d").build();
        int maximumPoolSize = corePoolSize * 2;
        int capacity = corePoolSize * 5;
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(capacity), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 获取单元格
     * @param cells
     * @param index
     * @return
     */
    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }

    /**
     * 验证EXCEL
     * @param workbook
     */
    public void checkExcel(Workbook workbook){
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            if(!(row.getCell(0).getStringCellValue().contains("说明：\n" +
                    "1、有效期只填写首个即可\n" +
                    "2、卡号、卡密、优惠码数值为空则无需填写"))){
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080198);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080198);
        }
    }

    /**
     * 上传改价错误提示文件
     *
     * @param workbook
     * @return
     */
    private String uploadErrorFile(Workbook workbook) {
        String errorResourceKey = Constants.ELECTRONIC_ERROR_EXCEL_DIR.concat("_").concat(commonUtil.getOperatorId());
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
                    .resourceKey(errorResourceKey)
                    .build();
            //文件校验错误，返回错误提示文件URL
            return yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
        } catch (IOException e) {
            log.error("卡券错误文件上传至云空间失败，errorResourceKey:{}", errorResourceKey, e);
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030070);
        }
    }

    /**
     * 下载错误表格
     *
     * @return
     */
    public void downErrorFile(String ext) {

        if (!(ext.equalsIgnoreCase(Constants.XLS) || ext.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080197);
        }

        String resourceKey = Constants.ELECTRONIC_ERROR_EXCEL_DIR.concat("_").concat(commonUtil.getOperatorId());
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
                .build()).getContext().getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000023);
        }
        try (
                InputStream is = new ByteArrayInputStream(content);
                ServletOutputStream os = HttpUtil.getResponse().getOutputStream()
        ) {
            //下载错误文档时强制清除页面文档缓存\
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
