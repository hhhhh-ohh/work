package com.wanmi.sbc.mq.report.service.base;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderPageRequest;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @return
 * @description 退单导出
 * @author xuyunpeng
 * @date 2021/6/2 9:53 上午
 */
@Service
@Slf4j
public class ReturnOrderBaseService {

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    /**
     * @return
     * @description 导出
     * @author xuyunpeng
     * @date 2021/6/2 9:53 上午
     */
    @ReturnSensitiveWords(functionName = "f_return_order_export_sign_word")
    public List<ReturnOrderVO> export(Operator operator, ReturnOrderPageRequest exportRequest) {
        return returnOrderQueryProvider.page(exportRequest).getContext().getReturnOrderPage().getContent();
    }
}
