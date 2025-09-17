package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.QrCodeUtils;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailExportRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailPageRequest;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBatchVO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.GiftCardRecordBaseService;
import com.wanmi.sbc.setting.api.provider.baseconfig.BaseConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.baseconfig.BaseConfigRopResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description 礼品卡详情记录报表
 * @author malianfeng
 * @date 2022/12/14 15:09
 */
@Service
@Slf4j
public class GiftCardDetailExportService implements ExportBaseService {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_1);

    @Autowired private GiftCardRecordBaseService giftCardRecordBaseService;

    @Autowired private GiftCardDetailQueryProvider giftCardDetailQueryProvider;

    @Autowired private ExportUtilService exportUtilService;

    @Autowired private OsdService osdService;

    @Autowired private BaseConfigQueryProvider baseConfigQueryProvider;

    @Autowired private WechatAuthProvider wechatAuthProvider;


    /**
     * 单 Excel 文件支持的最大记录数
     */
    public static final int MAX_SIZE = 5000;

    /**
     * 批次信息列长度（由于制/发卡列表长度不同，批次信息列又要排在最后，故记录长度动态确定其起止下标）
     */
    public static final int BATCH_INFO_COLUMN_NUM = 8;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {

        GiftCardDetailExportRequest exportRequest = JSON.parseObject(data.getParam(), GiftCardDetailExportRequest.class);
        GiftCardSourceType sourceType = exportRequest.getSourceType();
        String batchNo = exportRequest.getBatchNo();

        String subTypeName = this.getSubTypeName(sourceType);
        String exportName = String.format("礼品卡%s详情记录报表", subTypeName);
        String fileName = String.format("礼品卡%s详情记录_%s", subTypeName,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + exportUtilService.getRandomNum());

        log.info("{} export begin, param:{}", exportName, exportRequest);

        List<String> resourceKeyList = new ArrayList<>();
        String resourceKey = String.format("giftCardDetail/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);

        // 构造分页请求
        GiftCardDetailPageRequest pageRequest = KsBeanUtil.convert(exportRequest, GiftCardDetailPageRequest.class);
        pageRequest.setPageSize(MAX_SIZE);

        // 获取总数
        long totalCount = giftCardDetailQueryProvider.countForExport(exportRequest).getContext();
        // 总页数
        long fileSize = (long) Math.ceil(1.0 * totalCount / MAX_SIZE);
        // 写入表头
        ExcelHelper<GiftCardDetailVO> excelHelper;
        Column[] columns = this.getColumns(sourceType);
        // 没有数据则生成空表
        if (totalCount == 0) {
            excelHelper = new ExcelHelper<>();
            String newFileName = String.format("%s.xls", resourceKey);
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.addSheet(exportName, columns, Collections.emptyList());
            excelHelper.write(emptyStream);
            osdService.uploadExcel(emptyStream, newFileName);
            resourceKeyList.add(newFileName);
            return BaseResponse.success(resourceKeyList);
        }
        BaseConfigRopResponse baseConfig = baseConfigQueryProvider.getBaseConfig().getContext();
        // 分页查询、导出
        for (int i = 0; i < fileSize; i++) {
            excelHelper = new ExcelHelper<>();
            SXSSFSheet sheet = excelHelper.addSXSSFSheetHead(exportName, columns);
            pageRequest.setPageNum(i);
            // 分页获取详情记录
            List<GiftCardDetailVO> dataList = giftCardRecordBaseService.getGiftCardDetailList(data.getOperator(), pageRequest);
            // 封装批次信息
            this.wrapperBatchInfo(dataList, data.getOperator());
            // 生成表格
            this.addSheetRow(excelHelper,sheet, columns, dataList, baseConfig);
            // 合并批次信息列的单元格
            this.mergedBatchInfoRegion(sheet, columns.length - BATCH_INFO_COLUMN_NUM, columns.length - 1, dataList.size());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            excelHelper.writeForSXSSF(byteArrayOutputStream);
            //如果超过一页，文件名后缀增加(索引值)
            String suffix = StringUtils.EMPTY;
            if (fileSize > 1) {
                suffix = "(".concat(String.valueOf(i + 1)).concat(")");
            }
            String newFileName = String.format("%s%s.xls", resourceKey, suffix);
            // 报表上传
            osdService.uploadExcel(byteArrayOutputStream, newFileName);
            resourceKeyList.add(newFileName);
        }
        return BaseResponse.success(String.join(",", resourceKeyList));
    }

    private void addSheetRow(ExcelHelper excelHelper,SXSSFSheet sheet, Column[] columns, List<GiftCardDetailVO> dataList, BaseConfigRopResponse baseConfig) {
        int rowIndex = 1;
        for (GiftCardDetailVO data : dataList) {
            data.setMobileWebsite(baseConfig.getMobileWebsite());
            int cellIndex = 0;
            SXSSFRow row = sheet.createRow(rowIndex);
            GiftCardBatchVO giftCardBatchVO = data.getGiftCardBatchVO();
            if((DefaultFlag.YES == giftCardBatchVO.getExportMiniCodeType()) || (DefaultFlag.YES == giftCardBatchVO.getExportWebCodeType())){
                row.setHeight((short)4600);
            }

            SXSSFWorkbook sxssfWorkbook = sheet.getWorkbook();
            for (Column column : columns) {
                SXSSFCell cell = row.createCell(cellIndex++);
                if(column.getHeader().equals("H5一卡一码兑换码") &&
                        Objects.nonNull(giftCardBatchVO.getExportMiniCodeType()) && DefaultFlag.YES == giftCardBatchVO.getExportWebCodeType()){
                    String mobileWebsite = baseConfig.getMobileWebsite();//移动端访问地址
                    String pagePath = "/pages/package-D/gift-card/exchange-card/index?share=1&giftCardNo="+data.getGiftCardNo();
                    String webUrl = mobileWebsite + pagePath;
                    sheet.setColumnWidth(3,11000);
                    Drawing<?> drawing = sheet.createDrawingPatriarch();
                    BufferedImage qrCodeImage = QrCodeUtils.createQrCodeImage(webUrl,280,280);
                    byte[] qrCodeImageByte = QrCodeUtils.imageToBytes(qrCodeImage);
                    try {
                        excelHelper.insertExcelPic(sxssfWorkbook,drawing, rowIndex, 3, qrCodeImageByte);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if(column.getHeader().equals("小程序一卡一码兑换码") &&
                        Objects.nonNull(giftCardBatchVO.getExportMiniCodeType()) && DefaultFlag.YES == giftCardBatchVO.getExportMiniCodeType()){
                    sheet.setColumnWidth(2,11000);
                    String giftCardNo = data.getGiftCardNo();
                    Drawing<?> drawing = sheet.createDrawingPatriarch();
                    MiniProgramQrCodeRequest codeRequest = new MiniProgramQrCodeRequest();
                    codeRequest.setPage("pages/sharepage/sharepage");
                    codeRequest.setWidth(280);
                    codeRequest.setScene(String.format("exchange-card%s", giftCardNo));
                    byte[] miniQrCode = wechatAuthProvider.getWxaCodeBytesUnlimit(codeRequest).getContext();
                    try {
                        excelHelper.insertExcelPic(sxssfWorkbook,drawing, rowIndex, 2, miniQrCode);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    sheet.setColumnWidth(cellIndex-1,5000);
                    column.getRender().render(cell, data);
                }
            }
            rowIndex++;
        }
    }

    /**
     * @description 合并批次信息列的单元格
     * @author malianfeng
     * @date 2023/1/4 13:58
     * @param sheet 表格
     * @param firstCol 列开始下标
     * @param lastCol 列结束下标
     * @param rowSpan 跨行数
     * @return void
     */
    private void mergedBatchInfoRegion(SXSSFSheet sheet, int firstCol, int lastCol, int rowSpan) {
        // 仅跨行大于等2时，才进行合并，否则可能导致:"Merged region H2 must contain 2 or more cells"
        if (rowSpan >= 2) {
            int currentCol = firstCol;
            while (currentCol <= lastCol) {
                CellRangeAddress region = new CellRangeAddress(1, rowSpan, currentCol, currentCol);
                sheet.addMergedRegion(region);
                currentCol++;
            }
        }
    }

    /**
     * 封装批次信息
     */
    private void wrapperBatchInfo(List<GiftCardDetailVO> dataList, Operator operator) {
        // 收集批次id列表
        List<String> batchNoList = dataList.stream().map(GiftCardDetailVO::getBatchNo).distinct().collect(Collectors.toList());
        // 查询批次信息并装换成Map，[batchNo] => [GiftCardBatchVO]
        Map<String, GiftCardBatchVO> giftCardBatchMap = giftCardRecordBaseService.getGiftCardBatchList(operator, batchNoList).stream()
                .collect(Collectors.toMap(GiftCardBatchVO::getBatchNo, Function.identity()));
        for (Map.Entry<String, GiftCardBatchVO> entry : giftCardBatchMap.entrySet()) {
            String batchNo = entry.getKey();
            for (GiftCardDetailVO item : dataList) {
                if (batchNo.equals(item.getBatchNo())) {
                    // 仅为第1条对应批次的详情记录，赋值批次信息
                    item.setGiftCardBatchVO(entry.getValue());
                }
            }
        }
    }

    /**
     * 获取表头
     */
    public Column[] getColumns(GiftCardSourceType sourceType) {
        String subTypeName = this.getSubTypeName(sourceType);
        List<Column> columnList = new ArrayList<>();

        if (GiftCardSourceType.MAKE == sourceType) {
            // ============================================ 制卡记录列封装 ============================================
            columnList.add(new Column("卡号", new SpelColumnRender<GiftCardDetailVO>("giftCardNo")));
            columnList.add(new Column("兑换码", new SpelColumnRender<GiftCardDetailVO>("exchangeCode")));
            columnList.add(new Column("小程序一卡一码兑换码", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                GiftCardBatchVO giftCardBatchVO = detailVO.getGiftCardBatchVO();
                if(Objects.nonNull(giftCardBatchVO.getExportMiniCodeType()) && DefaultFlag.YES == giftCardBatchVO.getExportMiniCodeType()){
                    String pagePath = "pages/sharepage/sharepage";
                    String scene = "exchange-card"+detailVO.getGiftCardNo();
                    cell.setCellValue(String.format("小程序码生成页面路径：%s,对应参数：%s",pagePath,scene));
                } else {
                    cell.setCellValue("");
                }
            }));
            columnList.add(new Column("H5一卡一码兑换码", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                GiftCardBatchVO giftCardBatchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
                if(Objects.nonNull(giftCardBatchVO.getExportWebCodeType()) && DefaultFlag.YES == giftCardBatchVO.getExportWebCodeType()){
                    String mobileWebsite = detailVO.getMobileWebsite();//移动端访问地址
                    String pagePath = "/pages/package-D/gift-card/exchange-card/index?share=1&giftCardNo="+detailVO.getGiftCardNo();
                    String webUrl = mobileWebsite + pagePath;
                    cell.setCellValue(webUrl);
                } else {
                    cell.setCellValue("");
                }
            }));
            columnList.add(new Column("小程序一卡一码兑换小程序一卡一码兑换链接", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                GiftCardBatchVO giftCardBatchVO = detailVO.getGiftCardBatchVO();
                if(Objects.nonNull(giftCardBatchVO.getExportMiniCodeType()) && DefaultFlag.YES == giftCardBatchVO.getExportMiniCodeType()){
                    String pagePath = "pages/sharepage/sharepage";
                    String scene = "exchange-card"+detailVO.getGiftCardNo();
                    cell.setCellValue(String.format("小程序码生成页面路径：%s,对应参数：%s",pagePath,scene));
                } else {
                    cell.setCellValue("");
                }
            }));
            columnList.add(new Column("H5一卡一码兑换链接", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                GiftCardBatchVO giftCardBatchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
                if(Objects.nonNull(giftCardBatchVO.getExportWebCodeType()) && DefaultFlag.YES == giftCardBatchVO.getExportWebCodeType()){
                    String mobileWebsite = detailVO.getMobileWebsite();//移动端访问地址
                    String pagePath = "/pages/package-D/gift-card/exchange-card/index?share=1&giftCardNo="+detailVO.getGiftCardNo();
                    String webUrl = mobileWebsite + pagePath;
                    cell.setCellValue(webUrl);
                } else {
                    cell.setCellValue("");
                }
            }));
            columnList.add(new Column("兑换时间", new SpelColumnRender<GiftCardDetailVO>("acquireTime")));
            columnList.add(new Column("兑换会员", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                String belongPersonAccount = Optional.ofNullable(detailVO.getBelongPersonAccount()).orElse("");
                String belongPersonName = Optional.ofNullable(detailVO.getBelongPersonName()).orElse("");
                cell.setCellValue(String.format("%s %s", belongPersonAccount, belongPersonName));
            }));
            columnList.add(new Column("激活时间", new SpelColumnRender<GiftCardDetailVO>("activationTime")));
            columnList.add(new Column("激活会员", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                String activationPersonAccount = Optional.ofNullable(detailVO.getActivationPersonAccount()).orElse("");
                String activationPersonName = Optional.ofNullable(detailVO.getActivationPersonName()).orElse("");
                cell.setCellValue(String.format("%s %s", activationPersonAccount, activationPersonName));
            }));
            columnList.add(new Column("状态", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                cell.setCellValue(this.getStatusName(detailVO.getCardDetailStatus()));
            }));
        } else if (GiftCardSourceType.SEND == sourceType) {
            // ============================================ 发卡记录列封装 ============================================
            columnList.add(new Column("卡号", new SpelColumnRender<GiftCardDetailVO>("giftCardNo")));
            columnList.add(new Column("发卡会员", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                String belongPersonAccount = Optional.ofNullable(detailVO.getBelongPersonAccount()).orElse("");
                String belongPersonName = Optional.ofNullable(detailVO.getBelongPersonName()).orElse("");
                cell.setCellValue(String.format("%s %s", belongPersonAccount, belongPersonName));
            }));
            columnList.add(new Column("发卡状态", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                cell.setCellValue(this.getSendStatusName(detailVO.getSendStatus()));
            }));
            columnList.add(new Column("激活会员", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                String activationPersonAccount = Optional.ofNullable(detailVO.getActivationPersonAccount()).orElse("");
                String activationPersonName = Optional.ofNullable(detailVO.getActivationPersonName()).orElse("");
                cell.setCellValue(String.format("%s %s", activationPersonAccount, activationPersonName));
            }));
            columnList.add(new Column("激活时间", new SpelColumnRender<GiftCardDetailVO>("activationTime")));
            columnList.add(new Column("状态", (cell, object) -> {
                GiftCardDetailVO detailVO = (GiftCardDetailVO) object;
                cell.setCellValue(this.getStatusName(detailVO.getCardDetailStatus()));
            }));
        };

        // ============================================ 发/制卡记录批次信息列封装 ============================================
        // 制卡/发卡批次ID
        columnList.add(new Column(String.format("%s批次ID", subTypeName), new SpelColumnRender<GiftCardDetailVO>("batchNo")));
        columnList.add(new Column("面值", (cell, object) -> {
            GiftCardBatchVO batchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
            GiftCardType giftCardType = ((GiftCardDetailVO) object).getGiftCardType();
            if(giftCardType == GiftCardType.CASH_CARD){
                if (Objects.nonNull(batchVO) && Objects.nonNull(batchVO.getGiftCard())) {
                    cell.setCellValue(String.format("¥%d.00", batchVO.getGiftCard().getParValue()));
                }
            } else {
                cell.setCellValue("-");
            }
        }));
        columnList.add(new Column("礼品卡名称", (cell, object) -> {
            GiftCardBatchVO batchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
            if (Objects.nonNull(batchVO) && Objects.nonNull(batchVO.getGiftCard())) {
                cell.setCellValue(batchVO.getGiftCard().getName());
            }
        }));
        // 制卡/发卡人
        columnList.add(new Column(String.format("%s人", subTypeName), (cell, object) -> {
            GiftCardBatchVO batchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
            if (Objects.nonNull(batchVO)) {
                String generatePersonAccount = Optional.ofNullable(batchVO.getGeneratePersonAccount()).orElse("");
                String generatePersonName = Optional.ofNullable(batchVO.getGeneratePersonName()).orElse("");
                cell.setCellValue(String.format("%s %s", generatePersonAccount, generatePersonName));
            }
        }));
        // 制卡/发卡时间
        columnList.add(new Column(String.format("%s时间", subTypeName), (cell, object) -> {
            GiftCardBatchVO batchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
            if (Objects.nonNull(batchVO) && Objects.nonNull(batchVO.getGenerateTime())) {
                cell.setCellValue(batchVO.getGenerateTime().format(DTF));
            }
        }));
        // 制卡/发卡数量
        columnList.add(new Column(String.format("%s数量", subTypeName), (cell, object) -> {
            GiftCardBatchVO batchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
            if (Objects.nonNull(batchVO)) {
                cell.setCellValue(batchVO.getBatchNum());
            }
        }));
        columnList.add(new Column("过期时间", (cell, object) -> {
            GiftCardBatchVO batchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
            if (Objects.nonNull(batchVO) && Objects.nonNull(batchVO.getGiftCard())) {
                String expirationVal = "";
                ExpirationType expirationType = batchVO.getGiftCard().getExpirationType();
                if (ExpirationType.FOREVER == expirationType) {
                    expirationVal = "长期有效";
                } else if (ExpirationType.MONTH == expirationType) {
                    expirationVal = String.format("自激活后%d月内有效", batchVO.getGiftCard().getRangeMonth());
                } else {
                    expirationVal = batchVO.getGiftCard().getExpirationTime().format(DTF);
                }
                cell.setCellValue(expirationVal);
            }
        }));
        columnList.add(new Column("审核状态", (cell, object) -> {
            GiftCardBatchVO batchVO = ((GiftCardDetailVO) object).getGiftCardBatchVO();
            if (Objects.nonNull(batchVO)) {
                cell.setCellValue(this.getBatchAuditStatusName(batchVO.getAuditStatus()));
            }
        }));

        return columnList.toArray(new Column[0]);
    }

    /**
     * 获取子类型名称
     */
    public String getSubTypeName(GiftCardSourceType sourceType) {
        if (GiftCardSourceType.MAKE == sourceType) {
            return "制卡";
        } else if (GiftCardSourceType.SEND == sourceType) {
            return "发卡";
        } else if (GiftCardSourceType.BUY == sourceType) {
            return "购卡";
        }
        return "";
    }

    /**
     * 获取发卡状态名称
     */
    public String getSendStatusName(GiftCardSendStatus status) {
        if (GiftCardSendStatus.WAITING_SEND == status) {
            return "待发";
        } else if (GiftCardSendStatus.SUCCEEDED == status) {
            return "成功";
        } else if (GiftCardSendStatus.FAILED == status) {
            return "失败";
        }
        return "";
    }

    /**
     * 获取状态名称
     */
    public String getStatusName(GiftCardDetailStatus status) {
        if (GiftCardDetailStatus.NOT_EXCHANGE == status) {
            return "未兑换";
        } else if (GiftCardDetailStatus.NOT_ACTIVE == status) {
            return "未激活";
        } else if (GiftCardDetailStatus.ACTIVATED == status) {
            return "已激活";
        } else if (GiftCardDetailStatus.CANCELED == status) {
            return "已销卡";
        } else if (GiftCardDetailStatus.EXPIRED == status) {
            return "已过期";
        }
        return "";
    }

    /**
     * 获取批次审核状态名称
     */
    private String getBatchAuditStatusName(AuditStatus auditStatus) {
        if (AuditStatus.WAIT_CHECK == auditStatus) {
            return "待审核";
        } else if (AuditStatus.CHECKED == auditStatus) {
            return "已审核";
        } else if (AuditStatus.NOT_PASS == auditStatus) {
            return "审核失败";
        }
        return "";
    }
}
