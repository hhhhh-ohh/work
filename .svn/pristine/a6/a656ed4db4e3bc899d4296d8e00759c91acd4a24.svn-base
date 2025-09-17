package com.wanmi.sbc.goods.brand.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.brand.entity.ContractBrandBase;
import com.wanmi.sbc.goods.brand.model.root.CheckBrand;
import com.wanmi.sbc.goods.brand.model.root.ContractBrand;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.repository.CheckBrandRepository;
import com.wanmi.sbc.goods.brand.repository.ContractBrandRepository;
import com.wanmi.sbc.goods.brand.request.ContractBrandQueryRequest;
import com.wanmi.sbc.goods.brand.request.ContractBrandSaveRequest;
import com.wanmi.sbc.goods.brand.request.GoodsBrandQueryRequest;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodsaudit.service.GoodsAuditWhereCriteriaBuilder;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 签约品牌服务类
 * Created by sunkun on 2017/10/31.
 */
@Service
@Transactional
public class ContractBrandService {

    @Resource
    private ContractBrandRepository contractBrandRepository;

    @Resource
    private CheckBrandRepository checkBrandRepository;

    @Resource
    private GoodsBrandService goodsBrandService;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsInfoRepository goodsInfoRepository;

    @Resource
    private GoodsAuditRepository goodsAuditRepository;

    /**
     * 新增签约品牌
     *
     * @param request 签约品牌信息
     */
    @Transactional
    public ContractBrand add(ContractBrandSaveRequest request) {
        CheckBrand checkBrand = new CheckBrand();
        ContractBrand contractBrand = new ContractBrand();
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
        return contractBrandRepository.save(contractBrand);
    }

    /**
     * 新增待审核品牌
     *
     * @param request 签约品牌信息
     * @return 待审核品牌id
     */
    @Transactional
    public Long addCheckBrand(ContractBrandSaveRequest request) {
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
     * 批量删除品牌
     *
     * @param ids 批量签约品牌ids
     * @param storeId 店铺id
     */
    @Transactional
    public List<Long> deleteByIds(List<Long> ids, Long storeId) {
        //查询待删除签约品牌
        List<ContractBrand> contractBrands = contractBrandRepository.findBrandByStoreIdAndIds(storeId, ids);

        //删除签约品牌
        contractBrandRepository.deleteByIdsAndStoreId(ids, storeId);
        List<Long> checkIds = contractBrands.stream().map(ContractBrand::getCheckBrand).filter(Objects::nonNull).map(CheckBrand::getCheckBrandId).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(checkIds)){
            checkBrandRepository.deleteByCheckBrandIds(checkIds);
        }
        //根据待删除签约品牌筛选出平台品牌id列表
        List<Long> brandIds = contractBrands.stream().map(contractBrand -> {
            if (Objects.nonNull(contractBrand.getGoodsBrand())) {
                return contractBrand.getGoodsBrand().getBrandId();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        //根据店铺id及平台品牌id 批量修改spu和sku品牌为null
        if(CollectionUtils.isNotEmpty(brandIds)){
            goodsRepository.updateBrandByStoreIdAndBrandIds(storeId, brandIds);
            goodsInfoRepository.updateBrandByStoreIdAndBrandIds(storeId, brandIds);
        }
        return brandIds;
    }

    /**
     * 根据平台品牌id删除
     *
     * @param brandId 品牌id
     */
    @Transactional
    public void deleteByGoodsBrandId(Long brandId) {
        contractBrandRepository.deleteByGoodsBrandId(brandId);
        // 删除spu中关联品牌
        goodsRepository.updateBrandByBrandId(brandId);
        // 删除sku中关联品牌
        goodsInfoRepository.updateSKUBrandByBrandId(brandId);
    }

    /**
     * 校验自定义品牌是否与平台重复
     *
     * @param storeId 店铺id
     * @return 签约品牌列表
     */
    public List<ContractBrand> brandListVerify(Long storeId) {
        ContractBrandQueryRequest request = new ContractBrandQueryRequest();
        request.setStoreId(storeId);
        List<ContractBrand> list = queryList(request);
        //过滤商家自定义品牌
        list.stream().filter(contractBrand -> Objects.nonNull(contractBrand.getCheckBrand()) && Objects.isNull(contractBrand.getGoodsBrand())).map(info -> {
            GoodsBrandQueryRequest goodsBrandQueryRequest = new GoodsBrandQueryRequest();
            goodsBrandQueryRequest.setBrandName(info.getCheckBrand().getName());
            goodsBrandQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            //根据自定义品牌名称查询平台是否有重复
            List<GoodsBrand> goodsBrandList = goodsBrandService.query(goodsBrandQueryRequest);
            if (Objects.nonNull(goodsBrandList) && !goodsBrandList.isEmpty()) {
                info.setGoodsBrand(goodsBrandList.get(0));
                return info;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return list;
    }

    /**
     * 迁移商家品牌
     *
     * @param storeId 店铺id
     */
    @GlobalTransactional
    @Transactional
    public List<GoodsBrandVO> transfer(Long storeId) {
        List<ContractBrand> list = contractBrandRepository.findBrandByCheckId(storeId);
        GoodsBrandQueryRequest brandQueryRequest = new GoodsBrandQueryRequest();

        List<GoodsBrandVO> goodsBrandVOS = new ArrayList<>();
        list.forEach(info -> {
            brandQueryRequest.setBrandName(info.getCheckBrand().getName());
            List<GoodsBrand> goodsBrandList = goodsBrandService.query(brandQueryRequest);
            if (CollectionUtils.isNotEmpty(goodsBrandList)) {
                //平台已存在该品牌直接覆盖
                info.setGoodsBrand(goodsBrandList.get(0));
                info.setCheckBrand(null);
            } else {
                //平台不存在品牌，新增并修改
                GoodsBrand goodsBrand = new GoodsBrand();
                CheckBrand checkBrand = info.getCheckBrand();
                goodsBrand.setBrandName(checkBrand.getName());
                goodsBrand.setCreateTime(LocalDateTime.now());
                goodsBrand.setDelFlag(DeleteFlag.NO);
                goodsBrand.setLogo(checkBrand.getLogo());
                goodsBrand.setNickName(checkBrand.getNickName());
                goodsBrand.setUpdateTime(LocalDateTime.now());
                goodsBrand.setStoreId(checkBrand.getStoreId());
                GoodsBrand newGoodsBrand = goodsBrandService.add(goodsBrand);
                goodsBrandVOS.add(KsBeanUtil.convert(newGoodsBrand,GoodsBrandVO.class));
                info.setGoodsBrand(newGoodsBrand);
                info.setCheckBrand(null);
            }
            contractBrandRepository.save(info);
        });
        return goodsBrandVOS;
    }

    /**
     * 更新
     *
     * @param request 签约品牌信息
     */
    @Transactional
    public void update(ContractBrandSaveRequest request) {
        if(Objects.nonNull(request.getBrandId())){
            GoodsBrand goodsBrand = goodsBrandService.findById(request.getBrandId());
            if(Objects.isNull(goodsBrand)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(goodsBrand.getDelFlag() == DeleteFlag.YES){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030008,new Object[]{goodsBrand.getBrandName()});
            }
        }
        //根据主键id查询签约品牌
        ContractBrand brand = contractBrandRepository.findById(request.getContractBrandId()).orElse(null);
        //查询签约品牌为空或店铺id跟当前商家店铺id不一致 = 非法请求
        if (Objects.isNull(brand) || !Objects.equals(brand.getStoreId(), request.getStoreId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ContractBrand contractBrand = new ContractBrand();
        BeanUtils.copyProperties(request, contractBrand);
        if (request.getCheckBrandId() != null) {
            CheckBrand checkBrand = new CheckBrand();
            BeanUtils.copyProperties(request, checkBrand);
            checkBrand.setCheckBrandId(request.getCheckBrandId());
            checkBrandRepository.saveAndFlush(checkBrand);
            contractBrand.setCheckBrand(checkBrand);
            contractBrandRepository.saveAndFlush(contractBrand);
        } else {
            GoodsBrand goodsBrand = new GoodsBrand();
            goodsBrand.setBrandId(request.getBrandId());
            contractBrand.setGoodsBrand(goodsBrand);
            contractBrandRepository.saveAndFlush(contractBrand);
        }
    }

    /**
     * @description   根据
     * @author  wur
     * @date: 2022/8/1 14:10
     * @param storeIds
     * @param brandId
     * @return
     **/
    public List<ContractBrandBase> findStoreIdAndBrandId(List<Long> storeIds, Long brandId) {
        return contractBrandRepository.findStoreIdAndBrandId(storeIds, brandId);
    }

    /**
     * 查询签约品牌列表
     *
     * @param request 条件
     * @return 签约品牌列表
     */
    public List<ContractBrand> queryList(ContractBrandQueryRequest request) {
        return contractBrandRepository.findAll(request.getWhereCriteria());
    }

    public List<ContractBrandBase> findAllByStoreIdAndBrandIdIn(@Param("storeId") Long storeId, @Param("brandIds") List<Long> brandIds){
        return contractBrandRepository.findAllByStoreIdAndBrandIdIn(storeId,brandIds);
    }

    /***
     * 查询签约品牌是否存在，不存在则新增
     * @param request
     * @return
     */
    public ContractBrand queryAndSaveContractBrand(ContractBrandSaveRequest request){
        List<ContractBrandBase> contractBrandBases =
                contractBrandRepository.findAllByStoreIdAndBrandIdIn(request.getStoreId(), Lists.newArrayList(request.getBrandId()));
        if(CollectionUtils.isEmpty(contractBrandBases)){
            return add(request);
        }
        return KsBeanUtil.copyPropertiesThird(
                contractBrandBases.stream().findFirst().orElse(null), ContractBrand.class);
    }

    /**
     * 根据店铺id删除签约品牌
     *
     * @param storeId 店铺id
     */
    @Transactional
    public void deleteByStoreId(Long storeId) {
        contractBrandRepository.deleteByStoreId(storeId);
    }

    /**
     * 校验签约品牌是否关联商品
     * @param delBrandIds
     * @param storeId
     */
    @Transactional(rollbackFor = Exception.class)
    public void brandDelVerify(List<Long> delBrandIds, Long storeId) {
        List<ContractBrand> contractBrandList = contractBrandRepository
                .findBrandByStoreIdAndIds(storeId, delBrandIds);
        List<GoodsBrand> goodsBrands = goodsBrandService.findByIds(contractBrandList
                .stream()
                .map(ContractBrand::getBrandId)
                .collect(Collectors.toList()));
        if (NumberUtils.compare(delBrandIds.size(), contractBrandList.size()) != 0 &&
        NumberUtils.compare(contractBrandList.size(),goodsBrands.size()) !=0) {
            //分类不存在
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030008);
        }


        //纪录商品名称
        StringBuilder brandName = new StringBuilder();
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        for (GoodsBrand brand : goodsBrands) {
            goodsQueryRequest.setBrandId(brand.getBrandId());
            goodsQueryRequest.setStoreId(storeId);
            goodsQueryRequest.setDelFlag(0);
            List<Goods> goodsList = goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
            if (goodsList.size() > 0) {
                brandName.append(brand.getBrandName());
                brandName.append(',');
            }

            GoodsAuditQueryRequest queryRequest = new GoodsAuditQueryRequest();
            KsBeanUtil.copyProperties(goodsQueryRequest,queryRequest);
            List<GoodsAudit> goodsAuditList = goodsAuditRepository.findAll(GoodsAuditWhereCriteriaBuilder.build(queryRequest));
            if (goodsAuditList.size() > 0) {
                brandName.append(brand.getBrandName());
                brandName.append(',');
            }
        }
        if (brandName.length() > 0) {
            throw new SbcRuntimeException(brandName.substring(0, brandName.length() - 1),
                    GoodsErrorCodeEnum.K030016);
        }
    }
}
