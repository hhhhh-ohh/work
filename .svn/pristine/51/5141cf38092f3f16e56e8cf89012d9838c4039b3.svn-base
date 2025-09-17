package com.wanmi.sbc.setting.provider.impl.cancellationreason;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.cancellationreason.CancellationReasonQueryProvider;
import com.wanmi.sbc.setting.api.request.cancellationreason.CancellationReasonQueryRequest;
import com.wanmi.sbc.setting.api.response.cancellationreason.CancellationReasonDetailResponse;
import com.wanmi.sbc.setting.api.response.cancellationreason.CancellationReasonQueryResponse;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.CancellationReasonVO;
import com.wanmi.sbc.setting.cancellation.model.root.CancellationReason;
import com.wanmi.sbc.setting.cancellation.service.CancellationReasonService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2022/3/29 15:59
 * @description <p> </p>
 */
@RestController
public class CancellationReasonQueryController implements CancellationReasonQueryProvider {

    @Autowired
    private CancellationReasonService cancellationReasonService;

    @Override
    public BaseResponse<CancellationReasonQueryResponse> findAll() {
        CancellationReasonQueryResponse response = CancellationReasonQueryResponse.builder().build();
        List<CancellationReasonVO> cancellationReasonVOList = cancellationReasonService.listAll();
        if(CollectionUtils.isEmpty(cancellationReasonVOList)){
            return BaseResponse.success(response);
        }
        List<CancellationReasonVO> sortList = cancellationReasonVOList.stream()
                .sorted(Comparator.comparing(CancellationReasonVO::getSort))
                .collect(Collectors.toList());
        response.setCancellationReasonVOList(sortList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CancellationReasonDetailResponse> findById(@RequestBody CancellationReasonQueryRequest request) {
        CancellationReason cancellationReason = cancellationReasonService.findById(request.getId());
        if (Objects.isNull(cancellationReason) || Objects.equals(cancellationReason.getDelFlag(),DeleteFlag.YES)) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070027);
        }
        return BaseResponse.success(KsBeanUtil.convert(cancellationReason, CancellationReasonDetailResponse.class));
    }
}
