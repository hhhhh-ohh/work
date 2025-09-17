package com.wanmi.sbc.setting.cancellation.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.cancellationreason.CancellationReasonModifyRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.CancellationReasonVO;
import com.wanmi.sbc.setting.cancellation.model.root.CancellationReason;
import com.wanmi.sbc.setting.cancellation.repository.CancellationReasonRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2022/3/29 14:43
 * @description <p> 注销原因SERVICE </p>
 */
@Service
public class CancellationReasonService {

    @Autowired
    private CancellationReasonRepository cancellationReasonRepository;

    private static final int CANCELLATION_REASON_SIZE = 10;

    @Transactional(rollbackFor = Exception.class)
    public void modifyCancellationReason(CancellationReasonModifyRequest request) {
        List<CancellationReasonVO> cancellationReasonVOList = request.getCancellationReasonVOList();
        if(CollectionUtils.isEmpty(cancellationReasonVOList)){
            return;
        }
        if(CollectionUtils.size(cancellationReasonVOList) > CANCELLATION_REASON_SIZE){
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070036);
        }
        cancellationReasonRepository.deleteAll();
        List<CancellationReason> cancellationReasonList = cancellationReasonVOList.stream().map(source -> {
            CancellationReason target = new CancellationReason();
            BeanUtils.copyProperties(source, target);
            target.setDelFlag(DeleteFlag.NO);
            return target;
        }).collect(Collectors.toList());
        cancellationReasonRepository.saveAll(cancellationReasonList);
    }

    /**
     * 查询全部注销原因
     * @return
     */
    public List<CancellationReasonVO> listAll(){
        List<CancellationReason> cancellationReasonList = cancellationReasonRepository.findByDelFlag(DeleteFlag.NO);
        if(CollectionUtils.isEmpty(cancellationReasonList)){
            return Collections.emptyList();
        }
        return KsBeanUtil.convert(cancellationReasonList, CancellationReasonVO.class);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public CancellationReason findById(String id) {
        return cancellationReasonRepository.findById(id).orElse(null);
    }
}
