package com.wanmi.sbc.goods.brand.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.brand.model.root.CheckBrand;
import com.wanmi.sbc.goods.brand.model.root.ContractBrandAudit;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.repository.CheckBrandRepository;
import com.wanmi.sbc.goods.brand.repository.ContractBrandAuditRepository;
import com.wanmi.sbc.goods.brand.request.ContractBrandAuditQueryRequest;
import com.wanmi.sbc.goods.brand.request.ContractBrandAuditSaveRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 二次签约品牌服务类
 * @author wangchao
 */
@Service
@Transactional
public class ContractBrandAuditService {

    @Resource
    private ContractBrandAuditRepository contractBrandAuditRepository;

    @Resource
    private CheckBrandRepository checkBrandRepository;

    @Resource
    private GoodsBrandService goodsBrandService;

    /**
     * 新增签约品牌
     *
     * @param request 签约品牌信息
     */
    @Transactional
    public ContractBrandAudit add(ContractBrandAuditSaveRequest request) {
        CheckBrand checkBrand = new CheckBrand();
        ContractBrandAudit contractBrand = new ContractBrandAudit();
        BeanUtils.copyProperties(request, contractBrand);
        if (request.getBrandId() != null) {
            //签约平台已有品牌
            GoodsBrand goodsBrand = goodsBrandService.findById(request.getBrandId());
            //平台品牌不存在
            if (goodsBrand == null) {
                throw new SbcRuntimeException(goodsBrandService.getDeleteIndex(request.getBrandId()), GoodsErrorCodeEnum.K030058);
            }
            if(goodsBrand.getDelFlag() == DeleteFlag.YES){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030008,new Object[]{goodsBrand.getBrandName()},
                        goodsBrandService.getDeleteIndex(request.getBrandId()));
            }
            goodsBrand.setBrandId(request.getBrandId());
            contractBrand.setGoodsBrand(goodsBrand);
        } else {
            //商家自定义品牌
            Long checkBrandID = addCheckBrand(request);
            checkBrand.setCheckBrandId(checkBrandID);
            contractBrand.setCheckBrand(checkBrand);
        }
        return contractBrandAuditRepository.save(contractBrand);
    }

    /**
     * 新增待审核品牌
     *
     * @param request 签约品牌信息
     * @return 待审核品牌id
     */
    @Transactional
    public Long addCheckBrand(ContractBrandAuditSaveRequest request) {
        //校验待审核品牌是否重复
        CheckBrand checkBrand = checkBrandRepository.queryByCheckNameAndStoreId(request.getName(), request.getStoreId());
        if (Objects.nonNull(checkBrand)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        checkBrand = new CheckBrand();
        BeanUtils.copyProperties(request, checkBrand);
        checkBrand.setCreateTime(LocalDateTime.now());
        return checkBrandRepository.save(checkBrand).getCheckBrandId();
    }

    /**
     * 根据店铺id删除签约品牌
     *
     * @param storeId 店铺id
     */
    @Transactional
    public void deleteByStoreId(Long storeId) {
        contractBrandAuditRepository.deleteByStoreId(storeId);
    }

    /**
     * 查询签约品牌列表
     *
     * @param request 条件
     * @return 签约品牌列表
     */
    public List<ContractBrandAudit> queryList(ContractBrandAuditQueryRequest request) {
        return contractBrandAuditRepository.findAll(request.getWhereCriteria());
    }
}
