package com.wanmi.sbc.crm.lifecyclegroup;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.crm.api.provider.lifecyclegroup.LifeCycleGroupProvider;
import com.wanmi.sbc.crm.api.request.lifecyclegroup.LifeCycleGroupStatisticsPageRequest;
import com.wanmi.sbc.crm.api.request.lifecyclegroup.LifeCycleGroupStatisticsRequest;
import com.wanmi.sbc.crm.api.response.lifecyclegroup.LifeCycleGroupStatisticsHistoryResponse;
import com.wanmi.sbc.crm.bean.vo.LifeCycleGroupDayStatisticsVO;
import com.wanmi.sbc.crm.bean.vo.SimpleCustomGroupVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-11-15
 * \* Time: 15:33
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Tag(name =  "生命周期群管理", description =  "LifeCycleGroupController")
@Slf4j
@RestController
@Validated
@RequestMapping(value = "/crm/lifecyclegroup")
public class LifeCycleGroupController {

    private static AtomicInteger EXPORT_COUNT = new AtomicInteger(0);

    @Autowired
    private LifeCycleGroupProvider lifeCycleGroupProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "生命周期人群分析-概况")
    @GetMapping("/overview")
    BaseResponse overview(){

        return this.lifeCycleGroupProvider.overview();
    }


    @Operation(summary = "生命周期人群分析-趋势")
    @PostMapping("/statistics/historyList")
    BaseResponse historyList(@RequestBody @Valid LifeCycleGroupStatisticsRequest request){

        return this.lifeCycleGroupProvider.getHistoryList(request);
    }

    @Operation(summary = "生命周期人群分析-列表")
    @PostMapping("/statistics/page")
    BaseResponse page(@RequestBody @Valid LifeCycleGroupStatisticsPageRequest request){

        return this.lifeCycleGroupProvider.page(request);
    }


    @Operation(summary = "生命周期人群分析-导出")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @GetMapping("/statistics/export/{encrypted}")
    void export(@PathVariable String encrypted, HttpServletResponse response){
        try {
            if (EXPORT_COUNT.incrementAndGet() > 1) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000016);
            }
            String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
            LifeCycleGroupStatisticsRequest request = JSONObject.parseObject(decrypted, LifeCycleGroupStatisticsRequest.class);
            LifeCycleGroupStatisticsHistoryResponse result = this.lifeCycleGroupProvider.getHistoryList(request).getContext();
            if (result != null) {
                String headerKey = "Content-Disposition";
                String fileName = String.format("批量导出会员生命周期分群_%s~%s.xls",
                        request.getStartDate().format(DateTimeFormatter.BASIC_ISO_DATE),
                        request.getEndDate().format(DateTimeFormatter.BASIC_ISO_DATE)
                );
                try {
                    fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    log.error("/crm/lifecyclegroup/statistics/export, fileName={},", fileName, e);
                }
                String headerValue = String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName);
                response.setHeader(headerKey, headerValue);


                try {
                    exportProcess(result, response.getOutputStream());
                    response.flushBuffer();
                } catch (IOException e) {
                    log.error("导出会员生命周期报表出错：", e);
                    throw new SbcRuntimeException(e);
                }
            }
        }catch (Exception e){
            throw e;
        }finally {

            EXPORT_COUNT.set(0);
        }
    }

    private void exportProcess(LifeCycleGroupStatisticsHistoryResponse result, OutputStream outputStream){
        ExcelHelper excelHelper = new ExcelHelper();
        List<LifeCycleGroupDayStatisticsVO> list = result.getDataList();
        List<Map<String,String>> exportList = new ArrayList<>(list.size());
        for(LifeCycleGroupDayStatisticsVO vo : list){
            Map<String,String> map = new HashMap<>();
            map.put("day",vo.getDay());
            vo.getDatas().forEach(o -> map.put(o.getGroupId().toString(),o.getCustomerCount()+"("+o.getRatio()+"%)"));
            exportList.add(map);
        }




        excelHelper.addSheet(
                "会员生命周期分群导出", getColumn(result), exportList
        );
        excelHelper.write(outputStream);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn(LifeCycleGroupStatisticsHistoryResponse result){
        List<Column> columns = new ArrayList<>(result.getTitleList().size() + 1);
        columns.add(new Column("日期", (cell, object) -> {
            Map<String,String> map = (Map<String, String>) object;

            cell.setCellValue(map.get("day"));

        }));
        for(SimpleCustomGroupVO customGroupVO : result.getTitleList()){
            columns.add(new Column(customGroupVO.getGroupName(), (cell, object) -> {
                Map<String, String> map = (Map<String, String>) object;

                if(map.get(customGroupVO.getGroupId().toString())!=null){
                    cell.setCellValue(map.get(customGroupVO.getGroupId().toString()));
                }else {
                    cell.setCellValue("-");
                }

            }));
        };

        return columns.toArray(new Column[columns.size()]);
    }
}
