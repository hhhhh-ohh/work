package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitystockorder.CommunityStockOrderQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderListRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityStockOrderVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.StringWriter;
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
 * @description 社区拼团-备货单导出
 * @date 2022/8/8 9:46 上午
 **/
@Slf4j
@Service
public class CommunityStockWordExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private CommunityStockOrderQueryProvider communityStockOrderQueryProvider;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("customerPoints export begin, param:{}", data);
        LocalDateTime dateTime = LocalDateTime.now();
        CommunityActivityByIdRequest idRequest = JSON.parseObject(data.getParam(), CommunityActivityByIdRequest.class);
        CommunityActivityVO activity = communityActivityQueryProvider.getById(idRequest).getContext().getCommunityActivityVO();
        String activityName = StringUtil.convertSpecialChar(activity.getActivityName());
        String fileName = String.format("%s备货单_%s.doc"
                , activityName,
                dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + exportUtilService.getRandomNum());
        String resourceKey = String.format("communityStockOrder/word/%s", fileName);
        CommunityStockOrderListRequest listRequest = CommunityStockOrderListRequest.builder().activityId(activity.getActivityId()).build();
        List<CommunityStockOrderVO> orderVOList = communityStockOrderQueryProvider.list(listRequest).getContext().getCommunityStockOrderVOList();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("stock_trade.flt");
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("startTime", DateUtil.format(activity.getStartTime(), DateUtil.FMT_TIME_1));
        map.put("endTime", DateUtil.format(activity.getEndTime(), DateUtil.FMT_TIME_1));
        map.put("orders", orderVOList == null ? Collections.emptyList() : orderVOList.stream()
                .peek(s -> {
                    s.setGoodsName(StringUtil.convertSpecialChar(s.getGoodsName()));
                    s.setSpecName(StringUtil.convertSpecialChar(s.getSpecName()));
                }).collect(Collectors.toList()));
        StringWriter writer = new StringWriter();
        template.process(map, writer);
        //上传
        osdService.uploadExcel(writer.toString(), resourceKey);
        return BaseResponse.success(resourceKey);
    }
}
