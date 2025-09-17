package com.wanmi.sbc.trade;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradePageCriteriaRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sunkun on 2017/11/23.
 */
@Tag(name = "StoreTradeController", description = "店铺订单服务API")
@RestController
@Validated
    @RequestMapping("/trade")
public class StoreTradeController {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 分页查询可退订单 from ES
     *
     * @param returnQueryRequest
     * @return
     */
    @Operation(summary = "分页查询可退订单 from ES")
    @EmployeeCheck
    @RequestMapping(value = "/list/return", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_supplier_page_can_return_sign_word")
    public ResponseEntity<Page<TradeVO>> pageCanReturn(@RequestBody TradeQueryDTO returnQueryRequest) {
        returnQueryRequest.setSortType("Date");
        returnQueryRequest.setSupplierId(commonUtil.getCompanyInfoId());
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        TradeConfigGetByTypeResponse config = auditQueryProvider.getTradeConfigByType(request).getContext();
        JSONObject content = JSON.parseObject(config.getContext());
        Integer day = content.getObject("day", Integer.class);
        returnQueryRequest.setStatus(config.getStatus());
        if (Objects.nonNull(config.getStatus()) && config.getStatus() == 1 && Objects.nonNull(day) && day > 0) {
            returnQueryRequest.setDay(day);
        }
        //这里放开。。在申请时校验
        returnQueryRequest.setCanOnTheWayReturn(Boolean.TRUE);
        MicroServicePage<TradeVO> tradePage = tradeQueryProvider.pageCriteria(TradePageCriteriaRequest.builder()
                .isReturn(true).tradePageDTO(returnQueryRequest).build()).getContext()
                .getTradePage();

        List<String> customerIds = tradePage.getContent().stream().map(v -> v.getBuyer().getId()).collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        tradePage.getContent().forEach(v->v.setLogOutStatus(map.get(v.getBuyer().getId())));

        return ResponseEntity.ok(new PageImpl<>(tradePage.getContent(),
                 PageRequest.of(returnQueryRequest.getPageNum(), returnQueryRequest.getPageSize()), tradePage.getTotalElements()));
    }
}
