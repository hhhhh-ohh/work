package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderListRequest;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityDeliveryOrderVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.CommunityDeliveryBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author daiyitian
 * @className CommunityStockWordExportService
 * @description 社区拼团-发货单导出
 * @date 2022/8/8 9:46 上午
 **/
@Slf4j
@Service
public class CommunityDeliveryWordExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private CommunityDeliveryBaseService communityDeliveryBaseService;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${community.delivery.path}")
    private String filePath;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        File parentDir = new File(filePath);
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        log.info("CommunityDelivery export begin, param:{}", data);
        CommunityDeliveryOrderListRequest idRequest = JSON.parseObject(data.getParam(), CommunityDeliveryOrderListRequest.class);
        CommunityActivityByIdRequest activityByIdRequest = CommunityActivityByIdRequest.builder().activityId(idRequest.getActivityId()).build();
        CommunityActivityVO activity = communityActivityQueryProvider.getById(activityByIdRequest).getContext().getCommunityActivityVO();
        String title = "团长发货单";
        if (DeliveryOrderSummaryType.AREA.equals(idRequest.getType())) {
            title = "区域发货单";
        }
        String activityName = StringUtil.convertSpecialChar(activity.getActivityName());
        List<CommunityDeliveryOrderVO> orderVOList = communityDeliveryBaseService.queryExport(data.getOperator(), idRequest);
        String fileName = String.format("%s%s_%s.zip", activityName, title,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + exportUtilService.getRandomNum());
        String resourceKey = String.format("communityDeliveryOrder/zip/%s", fileName);
        File zipFile = new File(filePath.concat(File.separator).concat(fileName));
        if (zipFile.exists()) {
            zipFile.delete();
        }
        try {
            zipFile.createNewFile();
        } catch (IOException e) {
            log.error("文件路径:{}, 压缩创建文件异常:", zipFile.getName(), e);
        }
        log.info("生成文件开始".concat(zipFile.getAbsolutePath()));

        boolean isHaveFile = false;

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("delivery_trade.flt");
        try (FileOutputStream bos = new FileOutputStream(zipFile);
             ZipArchiveOutputStream zos = new ZipArchiveOutputStream(bos)) {
            for (CommunityDeliveryOrderVO orderVO : orderVOList) {
                //填充freemarker数据
                Map<String, Object> map = new HashMap<>();
                map.put("orderType", orderVO.getType().toValue());
                map.put("title", title);
                map.put("activityName", activityName);
                map.put("startTime", DateUtil.format(activity.getStartTime(), DateUtil.FMT_TIME_1));
                map.put("endTime", DateUtil.format(activity.getEndTime(), DateUtil.FMT_TIME_1));
                String name = orderVO.getLeaderName();
                if (DeliveryOrderSummaryType.AREA.equals(orderVO.getType())) {
                    name = orderVO.getAreaName();
                } else {
                    map.put("leaderAccount", StringUtil.convertSpecialChar(orderVO.getLeaderAccount()));
                    map.put("pickupPointName", StringUtil.convertSpecialChar(orderVO.getPickupPointName()));
                    map.put("contactNumber", StringUtil.convertSpecialChar(orderVO.getContactNumber()));
                    map.put("fullAddress", StringUtil.convertSpecialChar(orderVO.getFullAddress()));
                }
                name = StringUtil.convertSpecialChar(name);
                map.put("name", name);
                map.put("goodsList", orderVO.getGoodsList() == null ? Collections.emptyList() :
                        orderVO.getGoodsList().stream().peek(g -> {
                            g.setGoodsName(StringUtil.convertSpecialChar(g.getGoodsName()));
                            g.setSpecDesc(StringUtil.convertSpecialChar(g.getSpecDesc()));
                        }).collect(Collectors.toList()));
                String wordName = String.format("%s.doc", name);
                //装载zip内容一项
                ZipArchiveEntry zipEntry = new ZipArchiveEntry(wordName);
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
                    template.process(map, writer);
                    zos.putArchiveEntry(zipEntry);
                    zos.write(baos.toByteArray());
                    writer.flush();
                } catch (IOException e) {
                    log.error("装载文件{}，压缩异常:", wordName, e);
                } finally {
                    zos.closeArchiveEntry();
                }
                isHaveFile = true;
            }
        } catch (IOException e) {
            log.info("发货单生成zip失败", e);
        }

        if(isHaveFile) {
            try (FileInputStream fileInputStream = new FileInputStream(zipFile);
                 ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                IOUtils.copy(fileInputStream, output);
                osdService.uploadExcel(output, resourceKey);
                log.info("上传压缩文件:" + zipFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                log.error("压缩文件找不到异常:", e);
            } catch (IOException e) {
                log.error("文件上传异常:", e);
            }
        }

        //清理数据
        if (zipFile.exists()) {
            zipFile.delete();
        }

        if (!isHaveFile) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "该活动没有发货单数据");
        }
        return BaseResponse.success(resourceKey);
    }
}
