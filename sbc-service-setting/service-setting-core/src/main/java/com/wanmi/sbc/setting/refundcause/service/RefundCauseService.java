package com.wanmi.sbc.setting.refundcause.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseModifyRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.RefundCauseVO;
import com.wanmi.sbc.setting.refundcause.model.root.RefundCause;
import com.wanmi.sbc.setting.refundcause.repository.RefundCauseRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/11/16 10:54
 * @description <p> </p>
 */
@Service
public class RefundCauseService {

    @Autowired
    private RefundCauseRepository refundCauseRepository;

    private static final int REFUND_REASON_SIZE = 50;

    /**
     * 编辑退款原因
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyRefundCause(RefundCauseModifyRequest request){
        List<RefundCauseVO> refundCauseVOList = request.getRefundCauseVOList();
        if(CollectionUtils.isEmpty(refundCauseVOList)){
            return;
        }
        if (refundCauseVOList.size() > REFUND_REASON_SIZE) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070025);
        }
        refundCauseRepository.deleteAll();
        List<RefundCause> refundCauseList = refundCauseVOList.stream().map(refundCauseVO -> {
            RefundCause refundCause = new RefundCause();
            BeanUtils.copyProperties(refundCauseVO, refundCause);
            refundCause.setDelFlag(DeleteFlag.NO);
            return refundCause;
        }).collect(Collectors.toList());
        refundCauseRepository.saveAll(refundCauseList);
    }


    /**
     * 查询全部退款原因
     * @return
     */
    public List<RefundCause> listAll(){
        return refundCauseRepository.findByDelFlag(DeleteFlag.NO);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public RefundCause findById(String id) {
        return refundCauseRepository.findById(id).orElse(null);
    }
}
