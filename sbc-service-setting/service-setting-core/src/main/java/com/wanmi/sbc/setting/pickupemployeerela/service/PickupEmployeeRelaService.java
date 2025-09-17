package com.wanmi.sbc.setting.pickupemployeerela.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingIdsRequest;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingIdsResponse;
import com.wanmi.sbc.setting.bean.vo.PickupEmployeeRelaVO;
import com.wanmi.sbc.setting.pickupemployeerela.model.root.PickupEmployeeRela;
import com.wanmi.sbc.setting.pickupemployeerela.repository.PickupEmployeeRelaRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>自提员工关系业务逻辑</p>
 *
 * @author xufeng
 * @date 2021-09-06 14:23:11
 */
@Service("PickupEmployeeRelaService")
public class PickupEmployeeRelaService {
    @Autowired
    private PickupEmployeeRelaRepository pickupEmployeeRelaRepository;

    /**
     * 保存自提员工关系
     *
     * @author xufeng
     */
    @Transactional
    public void save(List<PickupEmployeeRela> entity) {
        pickupEmployeeRelaRepository.saveAll(entity);
    }

    /**
     * 批量删除自提员工关系
     *
     * @param pickupId
     */
    @Transactional
    public void deleteByPickupId(Long pickupId) {
        pickupEmployeeRelaRepository.deleteByPickupId(pickupId);
    }

    /**
     * 将实体包装成VO
     *
     * @author xufeng
     */
    public PickupEmployeeRelaVO wrapperVo(PickupEmployeeRela pickupEmployeeRela) {
        if (pickupEmployeeRela != null) {
            PickupEmployeeRelaVO pickupEmployeeRelaVO = KsBeanUtil.convert(pickupEmployeeRela, PickupEmployeeRelaVO.class);
            return pickupEmployeeRelaVO;
        }
        return null;
    }

    /**
     * 根据自提点Id获取自提点运功信息
     *
     * @param pickupId
     * @return
     */
    public List<PickupEmployeeRelaVO> getEmployeeInfo(Long pickupId) {
        List<PickupEmployeeRela> list = pickupEmployeeRelaRepository.findByPickupId(pickupId);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.stream().map(this::wrapperVo).collect(Collectors.toList());
    }

    /**
     * 批量删除自提员工关系
     * @author xufeng
     */
    @Transactional
    public void deleteByEmployeeIds(List<String> employeeIds) {
        pickupEmployeeRelaRepository.deleteByEmployeeIds(employeeIds);
    }

}

