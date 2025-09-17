package com.wanmi.sbc.pointstrade;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.order.api.provider.pointstrade.PointsTradeProvider;
import com.wanmi.sbc.order.api.provider.pointstrade.PointsTradeQueryProvider;
import com.wanmi.sbc.order.api.request.pointstrade.*;
import com.wanmi.sbc.order.bean.dto.PointsTradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeDeliverDTO;
import com.wanmi.sbc.order.bean.dto.TradeDeliverRequestDTO;
import com.wanmi.sbc.order.bean.dto.TradeRemedyDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.PointsTradeVO;
import com.wanmi.sbc.order.bean.vo.TradeDeliverVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyByIdRequest;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName PointsTradeController
 * @Description 积分订单服务 Api
 * @Author lvzhenwei
 * @Date 2019/5/10 14:16
 **/
@Tag(name = "PointsTradeController", description = "积分订单服务 Api")
@RestController
@Validated
@RequestMapping("/points/trade")
@Slf4j
public class PointsTradeController {

    @Autowired
    private PointsTradeQueryProvider pointsTradeQueryProvider;

    @Autowired
    private PointsTradeProvider pointsTradeProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExpressCompanyQueryProvider expressCompanyQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * @Author lvzhenwei
     * @Description 根据查询条件分页查询积分订单列表信息
     * @Date 11:26 2019/5/27
     * @Param [pointsTradeQueryRequest]
     **/
    @Operation(summary = "根据查询条件分页查询积分订单列表信息")
    @EmployeeCheck
    @RequestMapping(method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_points_trade_page_sign_word")
    public BaseResponse<MicroServicePage<PointsTradeVO>> page(@RequestBody PointsTradeQueryDTO pointsTradeQueryRequest) {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        if (companyInfoId != null) {
            pointsTradeQueryRequest.setSupplierId(companyInfoId);
        }
        if (!pointsTradeQueryRequest.getIsBoss()) {
            if (Objects.nonNull(pointsTradeQueryRequest.getTradeState()) && Objects.nonNull(pointsTradeQueryRequest.getTradeState
                    ().getPayState())) {
                pointsTradeQueryRequest.setNotFlowStates(Arrays.asList(FlowState.VOID, FlowState.INIT));
            }
        }
        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        pointsTradeQueryRequest.makeAllAuditFlow();
        MicroServicePage<PointsTradeVO> pointsTradePage = pointsTradeQueryProvider.pageCriteria(PointsTradePageCriteriaRequest.builder().
                pointsTradePageDTO(pointsTradeQueryRequest).build()).getContext().getPointsTradePage();

        List<String> customerIds = pointsTradePage.getContent().stream().map(v -> v.getBuyer().getId()).collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        pointsTradePage.getContent().forEach(pointsTradeVO -> {
            //判断订单会员是否注销
            pointsTradeVO.setLogOutStatus(map.get(pointsTradeVO.getBuyer().getId()));
        });
        return BaseResponse.success(pointsTradePage);
    }

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.bean.vo.PointsTradeVO>
     * @Author lvzhenwei
     * @Description 查看积分订单详情
     * @Date 15:34 2019/5/10
     * @Param [tid]
     **/
    @Operation(summary = "查看积分订单详情")
    @Parameter(name = "tid", description = "积分订单id", required = true)
    @RequestMapping(value = "/{tid}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_points_trade_detail_sign_word")
    public BaseResponse<PointsTradeVO> detail(@PathVariable String tid) {
        PointsTradeVO pointsTradeVO =
                pointsTradeQueryProvider.getById(PointsTradeGetByIdRequest.builder().tid(tid).needLmOrder(Boolean.TRUE).build()).getContext().getPointsTradeVo();

        //越权校验
        commonUtil.checkStoreId(pointsTradeVO.getSupplier().getStoreId());

        //判断订单会员是否注销
        pointsTradeVO.setLogOutStatus(
                customerCacheService.getCustomerLogOutStatus(pointsTradeVO.getBuyer().getId())
        );

        if (CollectionUtils.isNotEmpty(pointsTradeVO.getTradeEventLogs())){

            List<String> customerIds = pointsTradeVO.getTradeEventLogs()
                    .stream()
                    .map(vo->vo.getOperator().getUserId())
                    .collect(Collectors.toList());
            Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);

            pointsTradeVO.getTradeEventLogs().forEach(tradeEventLogVO -> {
                if (Objects.equals(Platform.CUSTOMER,tradeEventLogVO.getOperator().getPlatform())){
                    //判断订单会员是否注销
                    tradeEventLogVO.setLogOutStatus(map.get(tradeEventLogVO.getOperator().getUserId()));
                }
            });
        }

        return BaseResponse.success(pointsTradeVO);
    }

    /**
     * 发货
     * @param tid
     * @return
     */
    @Operation(summary = "发货")
    @Parameter(name = "tid", description = "订单id", required = true)
    @RequestMapping(value = "/deliver/{tid}", method = RequestMethod.PUT)
    @MultiSubmit
    @GlobalTransactional
    public ResponseEntity<BaseResponse> deliver(@PathVariable String tid, @Valid @RequestBody TradeDeliverRequestDTO
            tradeDeliverRequest) {
        if (tradeDeliverRequest.getShippingItemList().isEmpty() && tradeDeliverRequest.getGiftItemList().isEmpty()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050086);
        }
        PointsTradeDeliveryCheckRequest pointsTradeDeliveryCheckRequest = PointsTradeDeliveryCheckRequest.builder()
                .tid(tid)
                .tradeDeliver(tradeDeliverRequest)
                .build();
        pointsTradeDeliveryCheckRequest.setBaseStoreId(commonUtil.getStoreId());
        pointsTradeProvider.deliveryCheck(pointsTradeDeliveryCheckRequest);
        //发货校验
//        ExpsComQueryRopRequest queryRopRequest = new ExpsComQueryRopRequest();
//        queryRopRequest.setExpressCompanyId(Long.valueOf(tradeDeliverRequest.getDeliverId()));
//        CompositeResponse<ExpressCompany> response
//                = sdkClient.buildClientRequest().post(queryRopRequest, ExpressCompany.class, "expressCompany.detail",
//                "1.0.0");
//        if (!response.isSuccessful()) {
//            throw new SbcRuntimeException(ResultCode.FAILED);
//        }
//        TradeDeliverVO tradeDeliver = tradeDeliverRequest.toTradeDevlier(response.getSuccessResponse());
        ExpressCompanyByIdRequest request = new ExpressCompanyByIdRequest();
        request.setExpressCompanyId(Long.valueOf(tradeDeliverRequest.getDeliverId()));
        ExpressCompanyVO expressCompanyVO = expressCompanyQueryProvider.getById(request).getContext().getExpressCompanyVO();
        TradeDeliverVO tradeDeliver = tradeDeliverRequest.toTradeDevlier(expressCompanyVO);
        PointsTradeDeliverRequest pointsTradeDeliverRequest = PointsTradeDeliverRequest.builder()
                .tradeDeliver(KsBeanUtil.convert(tradeDeliver, TradeDeliverDTO.class))
                .tid(tid)
                .operator(commonUtil.getOperator())
                .build();
        String deliverId = pointsTradeProvider.deliver(pointsTradeDeliverRequest).getContext().getDeliverId();
        return ResponseEntity.ok(BaseResponse.success(deliverId));
    }

    /**
     * 确认收货
     * @param tid
     * @return
     */
    @Operation(summary = "确认收货")
    @Parameter(name = "tid", description = "订单Id", required = true)
    @RequestMapping(value = "/confirm/{tid}", method = RequestMethod.GET)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> confirm(@PathVariable String tid) {
        pointsTradeProvider.confirmReceive(PointsTradeConfirmReceiveRequest.builder().tid(tid).operator(commonUtil.getOperator()).build());
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 积分订单导出
     * @Date 15:34 2019/5/10
     * @Param [encrypted, response]
     **/
    @Operation(summary = "积分订单导出")
    @EmployeeCheck
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}/{encryptedable}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted,
                       @PathVariable String encryptedable, PointsTradeQueryDTO request) {

        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
        String decryptedable= new String(Base64.getUrlDecoder().decode(encryptedable.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        if (CollectionUtils.isNotEmpty(request.getEmployeeIds())) {
            JSONObject jsonObject = JSONObject.parseObject(decrypted);
            jsonObject.put("employeeIds", request.getEmployeeIds());
            decrypted = jsonObject.toString();
        }
        Operator operator = commonUtil.getOperator();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setDisabled(decryptedable);
        exportDataRequest.setTypeCd(ReportType.BUSINESS_POINTS_TRADE);
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @return org.springframework.http.ResponseEntity<com.wanmi.sbc.common.base.BaseResponse>
     * @Author lvzhenwei
     * @Description 修改卖家备注
     * @Date 14:27 2019/5/22
     * @Param [tid, tradeRemedyRequest]
     **/
    @Operation(summary = "修改卖家备注")
    @Parameter(name = "tid", description = "积分订单id", required = true)
    @RequestMapping(value = "/remark/{tid}", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> sellerRemark(@PathVariable String tid, @RequestBody TradeRemedyDTO
            tradeRemedyRequest) {

        if (StringUtils.isNotBlank(tradeRemedyRequest.getSellerRemark()) &&
                tradeRemedyRequest.getSellerRemark().length() > Constants.NUM_60) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PointsTradeRemedySellerRemarkRequest pointsTradeRemedySellerRemarkRequest = PointsTradeRemedySellerRemarkRequest.builder()
                .sellerRemark(tradeRemedyRequest.getSellerRemark())
                .tid(tid)
                .operator(commonUtil.getOperator())
                .build();
        pointsTradeRemedySellerRemarkRequest.setBaseStoreId(commonUtil.getStoreId());
        pointsTradeProvider.remedySellerRemark(pointsTradeRemedySellerRemarkRequest);

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }
}
