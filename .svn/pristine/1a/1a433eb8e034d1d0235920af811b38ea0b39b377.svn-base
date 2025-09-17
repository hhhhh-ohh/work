package com.wanmi.sbc.empower.customerservice.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingItemVO;
import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSettingItem;
import com.wanmi.sbc.empower.customerservice.repository.CustomerServiceSettingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 在线客服配置业务逻辑
 *
 * @author 韩伟
 * @date 2021-04-08 16:43:56
 */
@Service("CustomerServiceSettingItemService")
public class CustomerServiceSettingItemService {
    @Autowired private CustomerServiceSettingItemRepository customerServiceSettingItemRepository;

    @Transactional
    public void save(List<CustomerServiceSettingItem> entity) {
        customerServiceSettingItemRepository.saveAll(entity);
    }

    @Transactional
    public void deleteByCustomerServiceId(Long customerServiceId) {
        customerServiceSettingItemRepository.deleteByCustomerServiceId(customerServiceId);
    }

    /**
     * 列表查询在线客服配置
     *
     * @author 韩伟
     */
    public List<CustomerServiceSettingItem> list(Long customerServiceId) {
        return customerServiceSettingItemRepository.findByCustomerServiceIdAndDelFlag(
                customerServiceId, DeleteFlag.NO);
    }

    /**
     * 将实体包装成VO
     *
     * @author 韩伟
     */
    public CustomerServiceSettingItemVO wrapperVo(
            CustomerServiceSettingItem customerServiceSettingItem) {
        if (customerServiceSettingItem != null) {
            CustomerServiceSettingItemVO customerServiceSettingItemVO =
                    KsBeanUtil.convert(
                            customerServiceSettingItem, CustomerServiceSettingItemVO.class);
            customerServiceSettingItemVO.setOnlineServiceId(
                    customerServiceSettingItemVO.getCustomerServiceId().intValue());
            return customerServiceSettingItemVO;
        }
        return null;
    }

    /**
     * @description 查询客服的QQ号是否重复
     * @author hanwei
     * @date 2021/4/9 16:31
     * @param [serverAccount]
     * @return
     *     java.util.List<com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSettingItem>
     */
    public List<CustomerServiceSettingItem> checkDuplicateAccount(List<String> serverAccount) {
        return customerServiceSettingItemRepository.checkDuplicateAccount(serverAccount);
    }
}
