package com.wanmi.sbc.order;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradePageCriteriaRequest;
import com.wanmi.sbc.order.api.request.trade.TradePageQueryRequest;
import com.wanmi.sbc.order.api.request.trade.WriteOffCodeRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.OrderTagVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.order.service.CommunityTradeService;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author edz
 * @className CommunityTradeController
 * @description 社区团购订单
 * @date 2023/8/1 16:53
 **/
@Tag(name = "社区团购订单")
@RestController
@RequestMapping("/community/trade")
@Validated
public class CommunityTradeController {

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CommunityTradeService communityTradeService;

    @Autowired
    CommonUtil commonUtil;

    @Operation(summary = "核销订单")
    @PostMapping(value = "/write/off/order")
    @MultiSubmit
    public BaseResponse writeOffOrder(@RequestBody @Valid WriteOffCodeRequest request) {

        if(StringUtils.isNotBlank(request.getWriteOffCode()) && request.getWriteOffCode().length() == 8){
            request.setWriteOffCode(request.getWriteOffCode().toUpperCase(Locale.ROOT));
        }

        request.setOperator(commonUtil.getOperator());

        return tradeProvider.writeOffOrder(request);
    }

    /**
     * 订单核销商品信息
     */
    @Operation(summary = "订单核销商品信息")
    @GetMapping(value = "/pickup/details/{code}")
    public BaseResponse<TradeVO> orderDetailsByWriteOffCode(@PathVariable String code) {
        if(StringUtils.isNotBlank(code) && code.length() == 8){
            code = code.toUpperCase(Locale.ROOT);
        }else{
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050105);
        }

        //根据核销码查询订单
        TradeVO trade = tradeQueryProvider.orderDetailsByWriteOffCode(
                WriteOffCodeRequest.builder()
                        .writeOffCode(code)
                        .operator(commonUtil.getOperator())
                        .build())
                .getContext().getTradeVO();

        if(Objects.nonNull(trade)){
            try{
                checkOperatorByTrade(trade.getId());
            }catch (Exception e){
                e.printStackTrace();
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050105);
            }
        }else{
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050105);
        }

        return BaseResponse.success(trade);
    }

    /**
     * 分页查询团长订单
     * @param paramRequest
     * @return
     */
    @Operation(summary = "分页查询团长订单")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<Page<TradeVO>> communityOrderPage(
            @RequestBody TradePageQueryRequest paramRequest) {
        TradeQueryDTO tradeQueryRequest =
                TradeQueryDTO.builder()
                        .tradeState(
                                TradeStateDTO.builder()
                                        .flowState(paramRequest.getFlowState())
                                        .payState(paramRequest.getPayState())
                                        .deliverStatus(paramRequest.getDeliverStatus())
                                        .build())
                        .leaderCustomerId(commonUtil.getOperatorId())//团长会员ID
                        .communityOrder(Constants.ONE)//社区团购标识
                        .salesType(paramRequest.getSalesType())//团购类型 0自主销售 1帮卖
                        .channelType(paramRequest.getChannelType())
                        .beginTime(paramRequest.getCreatedFrom())
                        .endTime(paramRequest.getCreatedTo())
                        .keyworks(paramRequest.getKeywords())
                        .customerOrderListAllType(paramRequest.isCustomerOrderListAllType())
                        .isBoss(Boolean.FALSE)
                        .isLeaderCenter(Boolean.TRUE)
                        .build();
        tradeQueryRequest.setPageSize(paramRequest.getPageSize());
        tradeQueryRequest.setPageNum(paramRequest.getPageNum());

        // 设定状态条件逻辑,需筛选出已支付下已审核与部分发货订单
//        tradeQueryRequest.setPayedAndAudit();

        Page<TradeVO> tradePage =
                tradeQueryProvider
                        .pageCriteria(
                                TradePageCriteriaRequest.builder()
                                        .tradePageDTO(tradeQueryRequest)
                                        .build())
                        .getContext()
                        .getTradePage();
        List<TradeVO> tradeReponses = new ArrayList<>();
        tradePage
                .getContent()
                .forEach(
                        info -> {
                            TradeVO tradeReponse = new TradeVO();
                            BeanUtils.copyProperties(info, tradeReponse);
                            tradeReponses.add(tradeReponse);
                        });
        return BaseResponse.success(
                new PageImpl<>(
                        tradeReponses,
                        tradeQueryRequest.getPageable(),
                        tradePage.getTotalElements()));
    }


    /**
     * 发货
     *
     * @param tid
     * @return
     */
    @Operation(summary = "发货")
    @Parameter(name = "tid", description = "订单id", required = true)
    @RequestMapping(value = "/deliver/{tid}", method = RequestMethod.PUT)
    @MultiSubmit
    @GlobalTransactional
    public ResponseEntity<BaseResponse> deliver(@PathVariable String tid) {
        TradeVO tradeVO = checkOperatorByTrade(tid);

        OrderTagVO orderTag = tradeVO.getOrderTag();
        //是否是虚拟订单或者卡券订单
        if(Objects.isNull(orderTag) || !orderTag.getCommunityFlag()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000018);
        }

        String deliverId = communityTradeService.deliver(tradeVO);
        return ResponseEntity.ok(BaseResponse.success(deliverId));
    }

    /**
     * 校验团长权限
     * @param tid
     * @return
     */
    private TradeVO checkOperatorByTrade(String tid) {
        CommunityLeaderVO communityLeaderVO = commonUtil.getCommunityLeader();

        TradeVO trade = tradeQueryProvider.getById(
                TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
        if (Objects.isNull(communityLeaderVO) ||
                Objects.isNull(trade) ||
                Objects.isNull(trade.getCommunityTradeCommission()) ||
                Objects.isNull(trade.getCommunityTradeCommission().getLeaderId()) ||
                !Objects.equals(communityLeaderVO.getLeaderId(), trade.getCommunityTradeCommission().getLeaderId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }

        return trade;
    }

}
