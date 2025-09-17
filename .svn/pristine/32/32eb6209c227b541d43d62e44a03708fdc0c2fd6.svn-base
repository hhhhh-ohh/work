package com.wanmi.sbc.drawrecord;

import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.marketing.api.provider.drawactivity.DrawActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.drawrecord.DrawRecordQueryProvider;
import com.wanmi.sbc.marketing.api.provider.drawrecord.DrawRecordSaveProvider;
import com.wanmi.sbc.marketing.api.request.drawactivity.DrawActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.*;
import com.wanmi.sbc.marketing.api.response.drawrecord.*;
import com.wanmi.sbc.marketing.bean.vo.DrawActivityVO;
import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyByIdRequest;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Tag(name =  "抽奖活动记录表管理API", description =  "DrawRecordController")
@RestController
@Validated
@RequestMapping(value = "/drawrecord")
@Slf4j
public class DrawRecordController {

    @Autowired
    private DrawRecordQueryProvider drawRecordQueryProvider;

    @Autowired
    private DrawRecordSaveProvider drawRecordSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private DrawActivityQueryProvider drawActivityQueryProvider;

    @Autowired
    private ExpressCompanyQueryProvider expressCompanyQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Operation(summary = "分页查询抽奖活动记录表")
    @PostMapping("/page")
    @ReturnSensitiveWords(functionName = "f_lottery_record_return_sign_word")
    public BaseResponse<DrawRecordPageResponse> getPage(@RequestBody @Valid DrawRecordPageRequest pageReq) {
        pageReq.putSort("id", SortType.DESC.toValue());
        BaseResponse<DrawRecordPageResponse> baseResponse = drawRecordQueryProvider.page(pageReq);
        MicroServicePage<DrawRecordVO> drawRecordVOPage = baseResponse.getContext().getDrawRecordVOPage();
        List<DrawRecordVO> drawRecordVOList = drawRecordVOPage.getContent();
        DrawActivityVO drawActivityVO =
                drawActivityQueryProvider.getById(DrawActivityByIdRequest.builder().id(pageReq.getActivityId())
                        .build()).getContext().getDrawActivityVO();
        if (Objects.nonNull(drawActivityVO)) {
            baseResponse.getContext().setActivityName(drawActivityVO.getActivityName());
            baseResponse.getContext().setStartTime(drawActivityVO.getStartTime());
            baseResponse.getContext().setEndTime(drawActivityVO.getEndTime());
        }
        List<String> customerIds = drawRecordVOList.stream()
                .map(DrawRecordVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        drawRecordVOList.forEach(drawRecordVO1 -> {
            drawRecordVO1.setActivityName(drawActivityVO.getActivityName());
            drawRecordVO1.setLogOutStatus(map.get(drawRecordVO1.getCustomerId()));
        });
        drawRecordVOPage.setContent(drawRecordVOList);
        return baseResponse;
    }

    @Operation(summary = "列表查询抽奖活动记录表")
    @PostMapping("/list")
    public BaseResponse<DrawRecordListResponse> getList(@RequestBody @Valid DrawRecordListRequest listReq) {
        listReq.putSort("id", "desc");
        return drawRecordQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询抽奖活动记录表")
    @GetMapping("/{id}")
    public BaseResponse<DrawRecordByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DrawRecordByIdRequest idReq = new DrawRecordByIdRequest();
        idReq.setId(id);
        return drawRecordQueryProvider.getById(idReq);
    }

    /**
     * 添加中奖纪录发货信息
     */
    @Operation(summary = "添加中奖纪录发货信息")
    @RequestMapping(value = "/add/logistic", method = RequestMethod.POST)
    public BaseResponse<DrawRecordModifyResponse> addLogisticByDrawRecordId(@RequestBody @Valid DrawRecordAddLogisticRequest drawRecordAddLogisticrequest){
        ExpressCompanyVO expressCompanyVO = expressCompanyQueryProvider.getById(ExpressCompanyByIdRequest.builder()
                .expressCompanyId(drawRecordAddLogisticrequest.getLogisticsId()).build()).getContext().getExpressCompanyVO();
        if (Objects.isNull(expressCompanyVO)){
            return BaseResponse.error("请选择支持的物流公司");
        }
        drawRecordAddLogisticrequest.setLogisticsCompany(expressCompanyVO.getExpressName());
        drawRecordAddLogisticrequest.setLogisticsCode(expressCompanyVO.getExpressCode());
        drawRecordAddLogisticrequest.setUpdatePerson(commonUtil.getOperatorId());
        drawRecordAddLogisticrequest.setUpdateTime(LocalDateTime.now());
        return drawRecordSaveProvider.addLogisticByDrawRecordId(drawRecordAddLogisticrequest);
    }

    @Operation(summary = "导出抽奖活动记录表列表")
    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted, HttpServletResponse response) {

        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        Operator operator = commonUtil.getOperator();

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setOperator(operator);
        exportDataRequest.setTypeCd(ReportType.DRAW_RECORD_INFO);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
