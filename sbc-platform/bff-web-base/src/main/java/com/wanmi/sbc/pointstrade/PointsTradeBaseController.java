package com.wanmi.sbc.pointstrade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.api.provider.pointstrade.PointsTradeProvider;
import com.wanmi.sbc.order.api.provider.pointstrade.PointsTradeQueryProvider;
import com.wanmi.sbc.order.api.request.pointstrade.PointsTradeConfirmReceiveRequest;
import com.wanmi.sbc.order.api.request.pointstrade.PointsTradeGetByIdRequest;
import com.wanmi.sbc.order.api.request.pointstrade.PointsTradePageCriteriaRequest;
import com.wanmi.sbc.order.api.request.pointstrade.PointsTradePageQueryRequest;
import com.wanmi.sbc.order.api.response.pointstrade.PointsTradeGetByIdResponse;
import com.wanmi.sbc.order.api.response.trade.TradeDeliverRecordResponse;
import com.wanmi.sbc.order.bean.dto.PointsTradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.PointsTradeVO;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PointsTradeBaseController
 * @Description 积分订单公共controller
 * @Author lvzhenwei
 * @Date 2019/5/21 14:15
 **/
@Tag(name = "PointsTradeBaseController", description = "订单公共服务API")
@RestController
@Validated
@RequestMapping("/points/trade")
@Slf4j
public class PointsTradeBaseController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PointsTradeQueryProvider pointsTradeQueryProvider;

    @Autowired
    private PointsTradeProvider pointsTradeProvider;

    /**
     * 分页查询订单
     */
    @Operation(summary = "分页查询订单")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<Page<PointsTradeVO>> page(@RequestBody PointsTradePageQueryRequest pointsTradePageQueryRequest) {
        PointsTradeQueryDTO pointsTradeQueryDTO = PointsTradeQueryDTO.builder()
                .tradeState(TradeStateDTO.builder()
                        .flowState(pointsTradePageQueryRequest.getFlowState())
                        .payState(pointsTradePageQueryRequest.getPayState())
                        .deliverStatus(pointsTradePageQueryRequest.getDeliverStatus())
                        .build())
                .buyerId(commonUtil.getOperatorId())
                .beginTime(pointsTradePageQueryRequest.getCreatedFrom())
                .endTime(pointsTradePageQueryRequest.getCreatedTo())
                .keyworks(pointsTradePageQueryRequest.getKeywords())
                .orderType(pointsTradePageQueryRequest.getOrderType())
                .isBoss(Boolean.FALSE)
                .build();
        pointsTradeQueryDTO.setPageNum(pointsTradePageQueryRequest.getPageNum());
        pointsTradeQueryDTO.setPageSize(pointsTradePageQueryRequest.getPageSize());
        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        pointsTradeQueryDTO.makeAllAuditFlow();
        Page<PointsTradeVO> tradePage = pointsTradeQueryProvider.pageCriteria(PointsTradePageCriteriaRequest.builder()
                .pointsTradePageDTO(pointsTradeQueryDTO).build()).getContext().getPointsTradePage();
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        List<PointsTradeVO> pointsTradeReponses = new ArrayList<>();
        tradePage.getContent().forEach(info -> {
            PointsTradeVO pointsTradeReponse = new PointsTradeVO();
            BeanUtils.copyProperties(info, pointsTradeReponse);
            pointsTradeReponses.add(pointsTradeReponse);
        });
        return BaseResponse.success(new PageImpl<>(pointsTradeReponses, pointsTradePageQueryRequest.getPageable(),
                tradePage.getTotalElements()));
    }

    /**
     * 查询订单详情
     */
    @Operation(summary = "查询积分订单详情")
    @Parameter(name = "tid", description = "积分订单ID", required = true)
    @RequestMapping(value = "/{tid}", method = RequestMethod.GET)
    public BaseResponse<PointsTradeVO> details(@PathVariable String tid) {
        PointsTradeGetByIdResponse pointsTradeGetByIdResponse =
                pointsTradeQueryProvider.getById(PointsTradeGetByIdRequest.builder().tid(tid).needLmOrder(Boolean.TRUE).build()).getContext();
        PointsTradeVO detail = pointsTradeGetByIdResponse.getPointsTradeVo();
        checkUnauthorized(tid, pointsTradeGetByIdResponse.getPointsTradeVo());
        return BaseResponse.success(detail);
    }

    /**
     * 积分订单确认收货
     *
     * @param tid 订单号
     */
    @Operation(summary = "积分订单确认收货")
    @Parameter(name = "tid", description = "积分订单ID", required = true)
    @RequestMapping(value = "/receive/{tid}", method = RequestMethod.GET)
    @GlobalTransactional
    public BaseResponse confirm(@PathVariable String tid) {
        Operator operator = commonUtil.getOperator();
        PointsTradeVO pointsTradeVO =
                pointsTradeQueryProvider.getById(PointsTradeGetByIdRequest.builder().tid(tid).build()).getContext().getPointsTradeVo();
        checkUnauthorized(tid, pointsTradeVO);
        PointsTradeConfirmReceiveRequest pointsTradeConfirmReceiveRequest = PointsTradeConfirmReceiveRequest.builder()
                .operator(operator).tid(tid).build();
        pointsTradeProvider.confirmReceive(pointsTradeConfirmReceiveRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询积分订单发货清单
     */
    @Operation(summary = "查询积分订单发货清单")
    @Parameter( name = "tid", description = "积分订单ID", required = true)
    @RequestMapping(value = "/deliverRecord/{tid}/{type}", method = RequestMethod.GET)
    public BaseResponse<TradeDeliverRecordResponse> tradeDeliverRecord(@PathVariable String tid, @PathVariable String
            type) {
        PointsTradeVO pointsTradeVO =
                pointsTradeQueryProvider.getById(PointsTradeGetByIdRequest.builder().tid(tid).needLmOrder(Boolean.TRUE).build()).getContext().getPointsTradeVo();
        //订单列表做验证,客户订单列表无需验证
        checkUnauthorized(tid, pointsTradeVO);
        TradeDeliverRecordResponse tradeDeliverRecord = TradeDeliverRecordResponse.builder()
                .status(pointsTradeVO.getTradeState().getFlowState().getStateId())
                .tradeDeliver(pointsTradeVO.getTradeDelivers())
                .build();
        return BaseResponse.success(tradeDeliverRecord);
    }

    /**
     * 校验当前登录人是否是订单购买人
     *
     * @param tid
     * @param detail
     */
    private void checkUnauthorized(@PathVariable String tid, PointsTradeVO detail) {
        if (!detail.getBuyer().getId().equals(commonUtil.getOperatorId())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[]{tid});
        }
    }

}
