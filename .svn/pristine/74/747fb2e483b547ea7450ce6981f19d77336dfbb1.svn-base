package com.wanmi.sbc.giftcard.service;

import cn.hutool.core.util.RandomUtil;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailPageRequest;
import com.wanmi.sbc.elastic.bean.vo.customer.EsCustomerDetailVO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBatchProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBatchSendCreateRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardGainRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardInfoRequest;
import com.wanmi.sbc.marketing.bean.dto.GiftCardSendCustomerDTO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardVO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.yunservice.YunGetResourceResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @description 批量发卡导入
 * @author malianfeng
 * @date 2022/12/19 16:30
 */
@Slf4j
@Service
public class GiftCardBatchSendExcelService {
    /**
     * 操作日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GiftCardBatchSendExcelService.class);

    @Value("classpath:/download/gift_card_batch_send_template.xlsx")
    private Resource templateFile;

    @Autowired
    private GiftCardQueryProvider giftCardQueryProvider;

    @Autowired
    private GiftCardBatchProvider giftCardBatchProvider;

    @Autowired
    private EsCustomerDetailQueryProvider esCustomerDetailQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 导入数据总条数限制 1000条
     */
    private final static int IMPORT_COUNT_LIMIT = 1000;

    /**
     * 总发卡数量限制 10000张
     */
    private final static int TOTAL_SEND_NUM_LIMIT = 10000;

    public String generateAndSetUploadResourceKey(String userId, String fileExt) {
        String operatorRedisKey = RedisKeyConstant.GIFT_CARD_BATCH_SEND_UPLOAD_FILE_NAME.concat(userId);
        String fileName = String.format("批量发卡导入表格_%s_%s%s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")),
                RandomUtil.randomString(4),
                StringUtils.isNotBlank(fileExt) ? "." + fileExt : "");
        String resourceKey = String.format("%s/%s", Constants.GIFT_CARD_BATCH_SEND_EXCEL_DIR, fileName);;
        redisUtil.setString(operatorRedisKey, resourceKey, 60L * 60 * 2);
        return resourceKey;
    }

    public String getUploadResourceKey(String userId) {
        String operatorRedisKey = RedisKeyConstant.GIFT_CARD_BATCH_SEND_UPLOAD_FILE_NAME.concat(userId);
        return redisUtil.getString(operatorRedisKey);
    }

    public void importBatchSend(String userId, Long giftCardId) {
        String resourceKey = this.getUploadResourceKey(userId);
        if (StringUtils.isBlank(resourceKey)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030074);
        }
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
                .build()).getContext().getContent();

        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }

        // 校验并获取礼品卡信息
        GiftCardVO giftCardVO =
                giftCardQueryProvider.checkAndGetForBatchSend(GiftCardInfoRequest.builder().giftCardId(giftCardId).build())
                        .getContext().getGiftCardVO();

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

            // 客户账号和id映射map
            Map<String, String> customerAccountAndIdMap = new HashMap<>();
            // 校验客户账号，返回错误信息Map
            Map<String, String> customerAccountErrMap = this.checkCustomerAccount(workbook, customerAccountAndIdMap);
            if (!customerAccountErrMap.isEmpty()) {
                // 错误map非空，原始数据发生变更，请重新上传文件查看错误模版
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030074);
            }
            // 校验发卡数量是否超过库存
            boolean overflowFlag = this.checkSendNumOverflow(workbook, giftCardVO);
            if (overflowFlag) {
                // 超出库存，原始数据发生变更，请重新上传文件查看错误模版
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030074);
            }

            // 构造礼品卡发卡DTO列表
            Map<String, GiftCardSendCustomerDTO> giftCardSendCustomerMap = new HashMap<>();
            long totalSendNum = 0L;
            int maxCell = 2;
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

                // 获取客户账号
                String customerAccount = ExcelHelper.getValue(getCell(cells, 0));
                // 获取发卡数量
                String sendNumStr = ExcelHelper.getValue(getCell(cells, 1));
                Long sendNumVal = StringUtils.isNotBlank(sendNumStr) ? Long.parseLong(sendNumStr) : 0L;

                // 累加发卡总数
                totalSendNum += sendNumVal;

                // 构造发卡DTO
                GiftCardSendCustomerDTO sendCustomerDTO = giftCardSendCustomerMap.get(customerAccount);
                if (Objects.nonNull(sendCustomerDTO)) {
                    // 已存在，合并发送数量
                    Long oldSendNum = sendCustomerDTO.getSendNum();
                    sendCustomerDTO.setSendNum(oldSendNum + sendNumVal);
                } else {
                    // 不存在，构造新DTO存入Map
                    GiftCardSendCustomerDTO newSendCustomerDTO = new GiftCardSendCustomerDTO();
                    newSendCustomerDTO.setCustomerId(customerAccountAndIdMap.get(customerAccount));
                    newSendCustomerDTO.setSendNum(sendNumVal);
                    giftCardSendCustomerMap.put(customerAccount, newSendCustomerDTO);
                }
            }

            // 批量发卡
            GiftCardBatchSendCreateRequest createRequest = new GiftCardBatchSendCreateRequest();
            createRequest.setGiftCardId(giftCardId);
            createRequest.setSendCustomerList(new ArrayList<>(giftCardSendCustomerMap.values()));
            createRequest.setCreateNum(totalSendNum);
            createRequest.setCreatePerson(userId);
            createRequest.setExcelFilePath(resourceKey);
            giftCardBatchProvider.batchSendCreate(createRequest);

        } catch (SbcRuntimeException e) {
            log.error("批量发卡导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("批量发卡导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
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
                    .resourceKey(Constants.GIFT_CARD_BATCH_SEND_ERR_EXCEL_DIR.concat(userId))
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
            List<String> columnNames = Arrays.asList("客户账号", "发放礼品卡数");
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
     * 批量发卡模板下载
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
            String fileName = URLEncoder.encode("批量发卡导入模板.xlsx", StandardCharsets.UTF_8.name());
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
                    LOGGER.error("读取批量发卡导入模板异常", e);
                }
            }
            try{
                if(wk != null){
                    wk.close();
                }
            }catch (IOException e){
                LOGGER.error("读取批量发卡导入模板Workbook关闭异常", e);
            }
        }
    }


    /**
     * 上传批量发卡模板
     *
     * @param file
     * @param userId
     * @return
     */
    public String upload(MultipartFile file, String userId, Long giftCardId) {
        if (file == null || file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String originalFilename = file.getOriginalFilename();
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS) || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }

        // 校验并获取礼品卡信息
        GiftCardVO giftCardVO =
                giftCardQueryProvider.checkAndGetForBatchSend(GiftCardInfoRequest.builder().giftCardId(giftCardId).build())
                        .getContext().getGiftCardVO();

        String resourceKey = this.generateAndSetUploadResourceKey(userId, fileExt);

        if (StringUtils.isBlank(resourceKey)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030074);
        }

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

            // 校验客户账号，返回错误信息Map
            Map<String, String> customerAccountErrMap = this.checkCustomerAccount(workbook, null);
            // 校验发卡数量是否超过库存
            boolean overflowFlag = this.checkSendNumOverflow(workbook, giftCardVO);

            int maxCell = 2;
            boolean isError = false;
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

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

                // 校验客户账号
                String customerAccount = ExcelHelper.getValue(getCell(cells, 0));
                if (StringUtils.isBlank(customerAccount)) {
                    ExcelHelper.setError(workbook, getCell(cells, 0), "此项必填", style);
                    isError = true;
                } else if (customerAccountErrMap.containsKey(customerAccount)) {
                    // 为单元格设置错误提示
                    ExcelHelper.setError(workbook, getCell(cells, 0), customerAccountErrMap.get(customerAccount), style);
                    isError = true;
                }

                // 校验发卡数量
                String sendNum = ExcelHelper.getValue(getCell(cells, 1));
                if (StringUtils.isBlank(sendNum)) {
                    ExcelHelper.setError(workbook, getCell(cells, 1), "此项必填", style);
                    isError = true;
                } else {
                    if(!ValidateUtil.isNum(sendNum)) {
                        ExcelHelper.setError(style,workbook, getCell(cells, 1), "仅允许数字");
                        isError = true;
                    } else {
                        long sendNumVal = Long.parseLong(sendNum);
                        if (sendNumVal <= 0 || sendNumVal > TOTAL_SEND_NUM_LIMIT) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 1), String.format("必须在1-%d范围内", TOTAL_SEND_NUM_LIMIT));
                            isError = true;
                        } else if (overflowFlag) {
                            ExcelHelper.setError(style,workbook, getCell(cells, 1), "发卡数量不可大于剩余库存数，且不可大于10000张");
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
            log.error("批量发卡上传异常", var52);
            throw var52;
        } catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }catch (Exception var53) {
            log.error("批量发卡上传异常", var53);
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
                .resourceKey(Constants.GIFT_CARD_BATCH_SEND_ERR_EXCEL_DIR.concat(userId))
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
     * @description 校验客户信息
     * @author malianfeng
     * @date 2022/12/19 16:16
     * @param workbook workbook
     * @param customerAccountAndIdMap 客户账号和id映射map
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private Map<String, String> checkCustomerAccount(Workbook workbook, Map<String, String> customerAccountAndIdMap) {
        // 收集账号，排除空串，去重
        List<String> customerAccountList = ExcelUtils.getCellValues(workbook, 0, true);
        if (CollectionUtils.isEmpty(customerAccountList)) {
            return Collections.emptyMap();
        }
        // map收集错误信息
        Map<String, String> errorMap = new HashMap<>();
        // 已存在的客户账号集合
        Set<String> existCustomerAccountSet = new HashSet<>();
        // 构造客户详情列表请求
        EsCustomerDetailPageRequest customerPageRequest = new EsCustomerDetailPageRequest();
        customerPageRequest.setPageNum(0);
        customerPageRequest.setPageSize(customerAccountList.size());
        customerPageRequest.setCustomerAccountList(customerAccountList);
        customerPageRequest.setFilterAllLogOutStatusFlag(Boolean.TRUE);
        // 查询客户详情列表，默认查找的是未删除的客户
        List<EsCustomerDetailVO> esCustomerList = esCustomerDetailQueryProvider.page(customerPageRequest).getContext().getDetailResponseList();
        if (CollectionUtils.isNotEmpty(esCustomerList)) {
            for (EsCustomerDetailVO detail : esCustomerList) {
                String customerAccount = detail.getCustomerAccount();
                // 1. 客户已禁用
                if (CustomerStatus.DISABLE == detail.getCustomerStatus()) {
                    errorMap.put(customerAccount, "客户已禁用");
                }
                // 2. 已注销
                if (!Long.valueOf(LogOutStatus.NORMAL.toValue())
                        .equals(detail.getLogOutStatus())) {
                    errorMap.put(customerAccount, "客户已注销");
                }
                // 记录已存在的客户账号集合，用于判断客户是否删除
                existCustomerAccountSet.add(customerAccount);
                // 客户账号和id映射map，用于构造发卡请求
                if (Objects.nonNull(customerAccountAndIdMap)) {
                    customerAccountAndIdMap.put(customerAccount, detail.getCustomerId());
                }
            }
        }
        // 3. 客户已删除
        customerAccountList.stream()
                .filter(item -> !existCustomerAccountSet.contains(item))
                .forEach(item -> errorMap.put(item, "客户已删除"));
        return errorMap;
    }
    private Map<String, String> checkCustomerAccountOne(String customerAccountRequest, Map<String, String> customerAccountAndIdMap) {
        // 收集账号，排除空串，去重
        List<String> customerAccountList =new ArrayList<>();
        customerAccountList.add(customerAccountRequest);
        // map收集错误信息
        Map<String, String> errorMap = new HashMap<>();
        // 已存在的客户账号集合
        Set<String> existCustomerAccountSet = new HashSet<>();
        // 构造客户详情列表请求
        EsCustomerDetailPageRequest customerPageRequest = new EsCustomerDetailPageRequest();
        customerPageRequest.setPageNum(0);
        customerPageRequest.setPageSize(customerAccountList.size());
        customerPageRequest.setCustomerAccountList(customerAccountList);
        customerPageRequest.setFilterAllLogOutStatusFlag(Boolean.TRUE);
        // 查询客户详情列表，默认查找的是未删除的客户
        List<EsCustomerDetailVO> esCustomerList = esCustomerDetailQueryProvider.page(customerPageRequest).getContext().getDetailResponseList();
        if (CollectionUtils.isNotEmpty(esCustomerList)) {
            for (EsCustomerDetailVO detail : esCustomerList) {
                String customerAccount = detail.getCustomerAccount();
                // 1. 客户已禁用
                if (CustomerStatus.DISABLE == detail.getCustomerStatus()) {
                    errorMap.put(customerAccount, "客户已禁用");
                }
                // 2. 已注销
                if (!Long.valueOf(LogOutStatus.NORMAL.toValue())
                        .equals(detail.getLogOutStatus())) {
                    errorMap.put(customerAccount, "客户已注销");
                }
                // 记录已存在的客户账号集合，用于判断客户是否删除
                existCustomerAccountSet.add(customerAccount);
                // 客户账号和id映射map，用于构造发卡请求
                if (Objects.nonNull(customerAccountAndIdMap)) {
                    customerAccountAndIdMap.put(customerAccount, detail.getCustomerId());
                }
            }
        }
        // 3. 客户已删除
        customerAccountList.stream()
                .filter(item -> !existCustomerAccountSet.contains(item))
                .forEach(item -> errorMap.put(item, "客户已删除"));
        return errorMap;
    }
    /**
     * @description 校验发卡数量是否超库存
     * @author malianfeng 
     * @date 2022/12/19 16:28
     * @param workbook workbook
     * @param giftCardVO 礼品卡信息
     * @return boolean
     */
    private boolean checkSendNumOverflow(Workbook workbook, GiftCardVO giftCardVO) {
        // 收集发卡数量
        List<String> sendNumList = ExcelUtils.getCellValues(workbook, 1, false);
        // 统计总的发卡数量
        long totalSendNum = 0L;
        if (CollectionUtils.isNotEmpty(sendNumList)) {
            for (String numStr : sendNumList) {
                // 发卡数量不能非数字
                if (!ValidateUtil.isNum(numStr)) {
                    return false;
                }
                totalSendNum += Long.parseLong(numStr);
            }
        }
        // 发卡总数量不能超过10000
        if (totalSendNum > TOTAL_SEND_NUM_LIMIT) {
            return true;
        }
        // 库存有限制时，发卡数量不能超过库存
        if (DefaultFlag.NO == giftCardVO.getStockType() && totalSendNum > giftCardVO.getStock()) {
            return true;
        }
        return false;
    }

}
