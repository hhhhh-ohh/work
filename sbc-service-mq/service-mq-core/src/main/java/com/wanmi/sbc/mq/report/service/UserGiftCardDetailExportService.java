package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.ares.view.wechatvideo.VideoView;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailExportRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardDetailQueryRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardQueryRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailPageResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardInfoPageResponse;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @description 会员礼品卡信息导出
 * @author  lvzhenwei
 * @date 2022/12/20 10:14 上午
 **/
@Service
@Slf4j
public class UserGiftCardDetailExportService implements ExportBaseService {

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    @Autowired private ExportUtilService exportUtilService;

    @Autowired private OsdService osdService;

    public static final int MAX_SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        UserGiftCardQueryRequest userGiftCardQueryRequest = JSON.parseObject(data.getParam(), UserGiftCardQueryRequest.class);
        String exportName = String.format("会员礼品卡详情记录报表");
        String fileName = String.format("会员礼品卡详情记录_%s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + exportUtilService.getRandomNum());

        log.info("{} export begin, param:{}", exportName, userGiftCardQueryRequest);

        List<String> resourceKeyList = new ArrayList<>();
        String resourceKey = String.format("userGiftCardDetail/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);

        // 构造分页请求
        userGiftCardQueryRequest.setPageSize(MAX_SIZE);

        // 获取总数
        long totalCount = userGiftCardProvider.countForExport(userGiftCardQueryRequest).getContext();
        // 总页数
        long fileSize = (long) Math.ceil(1.0 * totalCount / MAX_SIZE);
        // 写入表头
        ExcelHelper<UserGiftCardInfoVO> excelHelper = new ExcelHelper<>();
        Column[] columns = this.getColumns();
        // 没有数据则生成空表
        if (totalCount == 0) {
            String newFileName = String.format("%s.xls", resourceKey);
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.addSheet(exportName, columns, Collections.emptyList());
            excelHelper.write(emptyStream);
            osdService.uploadExcel(emptyStream, newFileName);
            resourceKeyList.add(newFileName);
            return BaseResponse.success(resourceKeyList);
        }
        // 分页查询、导出
        for (int i = 0; i < fileSize; i++) {
            userGiftCardQueryRequest.setPageNum(0);
            UserGiftCardInfoPageResponse response = userGiftCardProvider.getUserGiftCardInfoPage(userGiftCardQueryRequest).getContext();
            if (Objects.nonNull(response)) {
                //如果超过一页，文件名后缀增加(索引值)
                String suffix = StringUtils.EMPTY;
                if (fileSize > 1) {
                    suffix = "(".concat(String.valueOf(i + 1)).concat(")");
                }
                List<UserGiftCardInfoVO> dataList = response.getUserGiftCardInfoVOS().getContent();
                excelHelper.addSheet(exportName+"_"+suffix, columns, dataList);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                excelHelper.write(byteArrayOutputStream);
                String newFileName = String.format("%s%s.xls", resourceKey, suffix);
                // 报表上传
                osdService.uploadExcel(byteArrayOutputStream, newFileName);
                resourceKeyList.add(newFileName);
            }
        }
        return BaseResponse.success(String.join(",", resourceKeyList));
    }

    /**
     * 获取表头
     */
    public Column[] getColumns() {
        List<Column> columnList = new ArrayList<>();
        columnList.add(new Column("卡号", new SpelColumnRender<VideoView>("giftCardNo")));
        columnList.add(new Column("卡类型", (cell, object) -> {
            UserGiftCardInfoVO userGiftCardInfoVO = (UserGiftCardInfoVO) object;
            cell.setCellValue(this.getGiftCardType(userGiftCardInfoVO));
        }));
        columnList.add(new Column("制卡批次ID", new SpelColumnRender<VideoView>("batchNo")));
        columnList.add(new Column("面值", (cell, object) -> {
            UserGiftCardInfoVO userGiftCardInfoVO = (UserGiftCardInfoVO) object;
            cell.setCellValue(this.getParValue(userGiftCardInfoVO));
        }));
        columnList.add(new Column("礼品卡名称", new SpelColumnRender<VideoView>("giftCardName")));
        columnList.add(new Column("激活时间", new SpelColumnRender<VideoView>("activationTime")));
        columnList.add(new Column("过期时间", new SpelColumnRender<VideoView>("expirationTime")));
        columnList.add(new Column("余额", new SpelColumnRender<VideoView>("balance")));
        columnList.add(new Column("状态", (cell, object) -> {
            UserGiftCardInfoVO userGiftCardInfoVO = (UserGiftCardInfoVO) object;
            cell.setCellValue(this.getStatusName(userGiftCardInfoVO));
        }));
        return columnList.toArray(new Column[0]);
    }

    public String getStatusName(UserGiftCardInfoVO userGiftCardInfoVO) {
        GiftCardStatus cardStatus = userGiftCardInfoVO.getCardStatus();
        GiftCardInvalidStatus invalidStatus = userGiftCardInfoVO.getInvalidStatus();
        if(Objects.isNull(invalidStatus)){
            if(cardStatus==GiftCardStatus.NOT_ACTIVE){
                return "未激活";
            }
            if(cardStatus==GiftCardStatus.ACTIVATED){
                return "已激活";
            }
        } else {
            if(invalidStatus == GiftCardInvalidStatus.USE_OVER){
                return "已用完";
            } else if(invalidStatus == GiftCardInvalidStatus.TIME_OVER){
                return "已过期";
            } else if(invalidStatus == GiftCardInvalidStatus.CANCELED){
                return "已销卡";
            }
        }
        return "";
    }

    public String getGiftCardType(UserGiftCardInfoVO userGiftCardInfoVO) {
        GiftCardType giftCardType = userGiftCardInfoVO.getGiftCardType();
        if(Objects.nonNull(giftCardType)){
            if(giftCardType == GiftCardType.CASH_CARD){
                return "现金卡";
            }
            if(giftCardType == GiftCardType.PICKUP_CARD){
                return "提货卡";
            }
        }
        return "";
    }

    public String getParValue(UserGiftCardInfoVO userGiftCardInfoVO) {
        GiftCardType giftCardType = userGiftCardInfoVO.getGiftCardType();
        if(Objects.nonNull(giftCardType)){
            if(giftCardType == GiftCardType.CASH_CARD){
                return userGiftCardInfoVO.getParValue().toString();
            }
        }
        return "-";
    }

}
