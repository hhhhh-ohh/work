package com.wanmi.sbc.customer.employee.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.constant.EmployeeErrorCode;
import com.wanmi.sbc.customer.api.request.employee.RoleInfoAddRequest;
import com.wanmi.sbc.customer.api.request.employee.RoleInfoModifyRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.employee.model.root.RoleInfo;
import com.wanmi.sbc.customer.employee.repository.RoleInfoRepository;
import com.wanmi.sbc.customer.redis.CacheKeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色service
 * Created by CHENLI on 2017/5/8.
 */
@Service
public class RoleInfoService {
    @Autowired
    private RoleInfoRepository roleInfoRepository;

    @Autowired
    private RedisUtil redisService;

    /**
     * 保存角色
     *
     * @param saveRequest
     * @return
     */
    @Transactional
    public Optional<RoleInfo> saveRoleInfo(RoleInfoAddRequest saveRequest) {
        if (roleInfoRepository.findByRoleNameAndCompanyInfoIdAndDelFlag(saveRequest.getRoleName(), saveRequest
                .getCompanyInfoId(), DeleteFlag.NO).isPresent()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010074);
        }
        if ("系统管理员".equals(saveRequest.getRoleName())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010084);
        }

        RoleInfo roleInfo = new RoleInfo();
        BeanUtils.copyProperties(saveRequest, roleInfo);
        roleInfo.setDelFlag(DeleteFlag.NO);
        roleInfo.setCreateTime(LocalDateTime.now());
        roleInfo.setCreatePerson("");

        return Optional.ofNullable(roleInfoRepository.save(roleInfo));
    }

    /**
     * 编辑角色
     *
     * @param editRequest
     * @return
     */
    @Transactional
    public Optional<RoleInfo> editRoleInfo(RoleInfoModifyRequest editRequest) {
        if ("系统管理员".equals(editRequest.getRoleName())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010084);
        }
        Optional<RoleInfo> roleInfoOptional = roleInfoRepository.findById(editRequest.getRoleInfoId());
        if (!roleInfoOptional.isPresent()) {
            return Optional.of(null);
        }

        RoleInfo roleInfo = roleInfoOptional.get();
        if (!roleInfo.getRoleName().equals(editRequest.getRoleName()) && roleInfoRepository
                .findByRoleNameAndCompanyInfoIdAndDelFlag(editRequest.getRoleName(), editRequest.getCompanyInfoId(),
                        DeleteFlag.NO).isPresent()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010074);
        }
        KsBeanUtil.copyProperties(editRequest, roleInfo);
        roleInfo.setUpdateTime(LocalDateTime.now());
        roleInfo.setUpdatePerson("");

        redisService.delete(CacheKeyUtil.getRoleFunctionKey(roleInfo.getRoleInfoId().toString()));

        return Optional.ofNullable(roleInfoRepository.save(roleInfo));
    }

    /**
     * 查询角色列表
     *
     * @param companyInfoId companyInfoId
     * @return
     */
    @Transactional(readOnly = true)
    public List<RoleInfo> listByCompanyId(Long companyInfoId) {
        return roleInfoRepository.findByCompanyInfoIdAndDelFlag(companyInfoId, DeleteFlag.NO).collect(Collectors
                .toList());
    }

    /**
     * 根据roleInfoId 查询roleInfo
     *
     * @param roleInfoId
     * @return
     */
    public Optional<RoleInfo> getRoleInfoById(Long roleInfoId) {
        return roleInfoRepository.findById(roleInfoId);
    }

    /**
     * 删除角色
     *
     * @param roleInfoId
     * @return
     */
    @Transactional
    public int delete(Long roleInfoId) {
        return roleInfoRepository.deleteRoleById(roleInfoId);
    }
}
