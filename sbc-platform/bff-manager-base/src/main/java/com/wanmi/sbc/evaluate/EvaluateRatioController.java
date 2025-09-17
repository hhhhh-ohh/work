package com.wanmi.sbc.evaluate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.evaluateratio.EvaluateRatioQueryProvider;
import com.wanmi.sbc.setting.api.provider.evaluateratio.EvaluateRatioSaveProvider;
import com.wanmi.sbc.setting.api.request.evaluateratio.EvaluateRatioModifyRequest;
import com.wanmi.sbc.setting.api.response.evaluateratio.EvaluateRatioByIdResponse;
import com.wanmi.sbc.setting.api.response.evaluateratio.EvaluateRatioModifyResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * 评价系数
 *
 * @author liutao
 * @date 2019/3/1 10:17 AM
 */
@RestController
@Validated
@RequestMapping("/evaluate/ratio")
@Tag(name =  "评价系数API", description =  "EvaluateRatioController")
public class EvaluateRatioController {

    @Autowired
    private EvaluateRatioQueryProvider evaluateRatioQueryProvider;

    @Autowired
    private EvaluateRatioSaveProvider evaluateRatioSaveProvider;

    /**
     * 查询评价系数
     *
     * @return
     */
    @Operation(summary = "查询评价系数")
    @RequestMapping(value = "/getEvaluateInfo", method = RequestMethod.GET)
    public BaseResponse<EvaluateRatioByIdResponse> getEvaluateInfo() {
        return evaluateRatioQueryProvider.findOne();
    }

    /**
     * 更新评价系数
     *
     * @param evaluateRatioModifyRequest
     * @return
     */
    @Operation(summary = "更新评价系数")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public BaseResponse<EvaluateRatioModifyResponse> updateEvaluateRatio(@RequestBody EvaluateRatioModifyRequest evaluateRatioModifyRequest) {
        if (evaluateRatioModifyRequest == null){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030188);
        }
        BigDecimal goodsRatio = evaluateRatioModifyRequest.getGoodsRatio();
        BigDecimal logisticsRatio = evaluateRatioModifyRequest.getLogisticsRatio();
        BigDecimal serverRatio = evaluateRatioModifyRequest.getServerRatio();
        BigDecimal add = goodsRatio.add(logisticsRatio).add(serverRatio);

        if (add.compareTo(BigDecimal.ONE) != 0){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030189);
        }

        if (getNumberOfDecimalPlace(goodsRatio) > 2 || getNumberOfDecimalPlace(logisticsRatio) > 2 || getNumberOfDecimalPlace(serverRatio) > 2) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        return evaluateRatioSaveProvider.modify(evaluateRatioModifyRequest);
    }

    /**
     * 获取保留的小数位数
     */
    private  int getNumberOfDecimalPlace(BigDecimal bigDecimal) {
        final String s = bigDecimal.toPlainString();
        final int index = s.indexOf('.');
        if (index < 0) {
            return 0;
        }
        return s.length() - 1 - index;
    }

}
