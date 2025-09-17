package com.wanmi.sbc.setting.provider.impl.refundcause;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.refundcause.RefundCauseQueryProvider;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseQueryRequest;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryOneResponse;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryResponse;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.RefundCauseVO;
import com.wanmi.sbc.setting.refundcause.model.root.RefundCause;
import com.wanmi.sbc.setting.refundcause.service.RefundCauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/11/16 11:20
 * @description <p> 退款原因查询 </p>
 */
@RestController
public class RefundCauseQueryController implements RefundCauseQueryProvider {

    @Autowired
    private RefundCauseService refundCauseService;

    /**
     * 查询全部退款原因
     * @return
     */
    @Override
    public BaseResponse<RefundCauseQueryResponse> findAll() {
        List<RefundCause> refundCauseList = refundCauseService.listAll();
        List<RefundCauseVO> refundCauseVOList = KsBeanUtil.convertList(refundCauseList, RefundCauseVO.class);
        refundCauseVOList = refundCauseVOList.stream().sorted(Comparator.comparing(RefundCauseVO::getSort)).collect(Collectors.toList());
        RefundCauseQueryResponse response = RefundCauseQueryResponse.builder()
                .refundCauseVOList(refundCauseVOList)
                .build();
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<RefundCauseQueryOneResponse> findById(@RequestBody RefundCauseQueryRequest request) {
        RefundCause refundCause = refundCauseService.findById(request.getId());
        if (Objects.isNull(refundCause) || DeleteFlag.YES.equals(refundCause.getDelFlag())) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070017);
        }
        return BaseResponse.success(KsBeanUtil.convert(refundCause,RefundCauseQueryOneResponse.class));
    }
}
