package com.wanmi.sbc.goods.distributor.goods.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoAddRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoModifySequenceRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoValidateRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoVerifyRequest;
import com.wanmi.sbc.goods.bean.dto.DistributorGoodsInfoModifySequenceDTO;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.distributor.goods.model.root.DistributorGoodsInfo;
import com.wanmi.sbc.goods.distributor.goods.repository.DistributiorGoodsInfoRepository;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 分销员商品-服务层
 *
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:13
 * @version: 1.0
 */
@Service
public class DistributorGoodsInfoService {

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private DistributiorGoodsInfoRepository distributiorGoodsInfoRepository;

    @Autowired private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    /**
     * 根据分销员-会员ID查询分销员商品列表
     *
     * @param customerId
     * @return
     */
    public List<DistributorGoodsInfo> findByCustomerId(String customerId) {
        return distributiorGoodsInfoRepository.findByCustomerIdOrderBySequence(customerId);
    }

    public List<DistributorGoodsInfo> findByCustomerIdAndStoreId(String customerId, Long storeId) {
        return distributiorGoodsInfoRepository.findByCustomerIdAndStoreIdOrderBySequence(customerId, storeId);
    }

    /***
     * 根据分销员-会员ID，店铺ID查询分销员商品列表
     * @param customerId
     * @param storeId
     * @param stock
     * @return
     */
    public List<DistributorGoodsInfo> findByCustomerIdAndStoreId(String customerId, Long storeId, Long stock) {
        if (Objects.nonNull(storeId)) {
            return distributiorGoodsInfoRepository.findByCustomerIdAndStoreIdOrderAndStock(customerId, storeId, stock);
        } else {
            return distributiorGoodsInfoRepository.findByCustomerIdAndStock(customerId, stock);
        }
    }

    /**
     * 根据分销员-会员ID和SPU编号查询分销员商品列表
     *
     * @param customerId 会员ID
     * @param goodsId    SPU编号
     * @return
     */
    public List<DistributorGoodsInfo> findByCustomerIdAndGoodsId(String customerId, String goodsId) {
        return distributiorGoodsInfoRepository.findByCustomerIdAndGoodsId(customerId, goodsId);
    }

    /**
     * 根据分销员，过滤非精选的单品
     */
    public List<String> verifyDistributorGoodsInfo(DistributorGoodsInfoVerifyRequest request) {
        // 小店下所有精选单品
        List<String> validIds =
                distributiorGoodsInfoRepository.findByCustomerIdOrderBySequence(request.getDistributorId())
                        .stream().map(DistributorGoodsInfo::getGoodsInfoId).collect(Collectors.toList());
        // 过滤非精选的单品
        return request.getGoodsInfoIds().stream().filter(
                goodsInfoId -> !validIds.contains(goodsInfoId)).collect(Collectors.toList());
    }


    /**
     * 新增分销员商品
     *
     * @param distributorGoodsInfoAddRequest
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public DistributorGoodsInfo add(DistributorGoodsInfoAddRequest distributorGoodsInfoAddRequest) {
        GoodsInfo goodsInfo = goodsInfoRepository.findByGoodsInfoIdAndDelFlag(distributorGoodsInfoAddRequest.getGoodsInfoId(), DeleteFlag.NO);
        if (goodsInfo == null || !StringUtils.equals(goodsInfo.getGoodsId(), distributorGoodsInfoAddRequest.getGoodsId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, "商品不存在");
        }
        StoreByIdRequest storeRequest = new StoreByIdRequest();
        storeRequest.setStoreId(distributorGoodsInfoAddRequest.getStoreId());
        StoreByIdResponse storeResponse = storeQueryProvider.getById(storeRequest).getContext();
        if (storeResponse.getStoreVO() == null || storeResponse.getStoreVO().getStoreId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, "店铺不存在");
        }
        DistributorGoodsInfo distributorGoodsInfo =
                findByCustomerIdAndGoodsInfoId(distributorGoodsInfoAddRequest.getCustomerId(),
                        distributorGoodsInfoAddRequest.getGoodsInfoId());
        if (Objects.nonNull(distributorGoodsInfo)) {
            return distributorGoodsInfo;
        }
        Integer sequence = findMaxSequenceByCustomerId(distributorGoodsInfoAddRequest.getCustomerId());
        distributorGoodsInfo = new DistributorGoodsInfo();
        KsBeanUtil.copyPropertiesThird(distributorGoodsInfoAddRequest, distributorGoodsInfo);
        distributorGoodsInfo.setSequence(sequence == null ? 0 : sequence + 1);
        distributorGoodsInfo.setCreateTime(LocalDateTime.now());
        distributorGoodsInfo.setUpdateTime(LocalDateTime.now());
        return distributiorGoodsInfoRepository.save(distributorGoodsInfo);
    }

    /**
     * 根据分销员-会员ID获取管理的分销商品排序最大值
     *
     * @param customerId
     * @return
     */
    public Integer findMaxSequenceByCustomerId(String customerId) {
        return distributiorGoodsInfoRepository.findMaxSequenceByCustomerId(customerId);
    }

    /**
     * 根据分销员-会员ID和SkuId删除分销员商品信息
     *
     * @param customerId
     * @param goodsInfoId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCustomerIdAndGoodsInfoId(String customerId, String goodsInfoId) {
        return distributiorGoodsInfoRepository.deleteByCustomerIdAndGoodsInfoId(customerId, goodsInfoId);
    }

    /**
     * 修改分销员-分销商品排序
     *
     * @param request
     * @return true : 成功，false:失败
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifySequence(DistributorGoodsInfoModifySequenceRequest request) {
        List<DistributorGoodsInfoModifySequenceDTO> distributorGoodsInfoDTOList =
                request.getDistributorGoodsInfoDTOList();
        List<DistributorGoodsInfo> distributorGoodsInfoList = KsBeanUtil.convert(distributorGoodsInfoDTOList,
                DistributorGoodsInfo.class);
        distributorGoodsInfoList = distributiorGoodsInfoRepository.saveAll(distributorGoodsInfoList);
        return distributorGoodsInfoDTOList.size() == distributorGoodsInfoList.size();
    }

    /**
     * 根据分销员-会员ID查询分销员商品列表(分页接口)
     *
     * @param customerId
     * @return
     */
    public Page<DistributorGoodsInfo> findByCustomerIdAndStatusOrderBySequence(String customerId, Integer status, Long stock,
                                                                               Pageable pageable) {
        if (Objects.nonNull(stock)) {
            return distributiorGoodsInfoRepository.findByCustomerIdAndStatusOrderAndStock(customerId, status, stock, pageable);
        } else {
            return distributiorGoodsInfoRepository.findByCustomerIdAndStatusOrderBySequence(customerId, status, pageable);
        }
    }

    /**
     * 根据分销员-会员ID和skuID查询分销员商品信息
     *
     * @param customerId
     * @param goodsInfoId
     * @return
     */
    public DistributorGoodsInfo findByCustomerIdAndGoodsInfoId(String customerId, String goodsInfoId) {
        return distributiorGoodsInfoRepository.findByCustomerIdAndGoodsInfoId(customerId, goodsInfoId);
    }


    /**
     * 商家-社交分销开关，更新对应的分销员商品状态
     *
     * @param storeId 店铺ID
     * @param status
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int modifyByStoreIdAndStatus(Long storeId, Integer status) {
        return distributiorGoodsInfoRepository.modifyByStoreIdAndStatus(storeId, status, LocalDateTime.now());
    }


    /**
     *
     * 拉卡拉分销员与商家的分账关系绑定后，自动审核该商家已经存在的分销商品
     * @param storeId 店铺ID
     * @param status
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int modifyByStoreIdAndStatusForLaKaLA(Long storeId, Integer status,String customerId) {
        return distributiorGoodsInfoRepository.modifyByStoreIdAndStatusForLaKaLA(storeId, status, LocalDateTime.now(),customerId);
    }

    /**
     * 根据SkuId删除分销员商品信息
     *
     * @param goodsInfoId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteByGoodsInfoId(String goodsInfoId) {
        return distributiorGoodsInfoRepository.deleteByGoodsInfoId(goodsInfoId);
    }

    /**
     * 根据SpuId删除分销员商品信息
     *
     * @param goodsId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteByGoodsId(String goodsId) {
        return distributiorGoodsInfoRepository.deleteByGoodsId(goodsId);
    }

    /**
     * 根据店铺ID集合删除分销员商品表数据
     *
     * @param storeId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteByStoreId(Long storeId) {
        return distributiorGoodsInfoRepository.deleteByStoreId(storeId);
    }

    /**
     * 根据店铺ID集合批量删除分销员商品表数据
     *
     * @param storeIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteByStoreIdsIn(List<Long> storeIds) {
        return distributiorGoodsInfoRepository.deleteByStoreIdsIn(storeIds);
    }

    /**
     * 查询分销员商品表-店铺ID集合数据
     *
     * @return
     */
    public List<Long> findAllStoreId() {
        return distributiorGoodsInfoRepository.findAllStoreId();
    }


    /**
     * 根据会员id查询这个店铺下的分销商品数
     *
     * @param customerId
     * @return
     */
    public Long getCountsByCustomerId(String customerId) {
        return distributiorGoodsInfoRepository.getCountsByCustomerId(customerId);
    }

    /**
     * 销量排序。前10分销商品
     *
     * @return
     */
    public List<String> salesNumDescLimit10() {
        return distributiorGoodsInfoRepository.salesNumDescLimit10();
    }

    /**
     * 分组查询分销员id跟店铺ID
     * @param limitOne
     * @param limitTwo
     * @return
     */
    public List<String> findGroupByCustomerAndStoreId(Integer limitOne,Integer limitTwo) {
        return distributiorGoodsInfoRepository.findGroupByCustomerAndStoreId(limitOne,limitTwo);
    }

    /**
     * 拉卡拉刷新分销员商品状态
     * @param storeId
     * @param customerId
     */
    @Transactional
    public void updateStatusForLaKaLa(Long storeId,String customerId){
        if (Objects.isNull(storeId) || Objects.isNull(customerId)) {
            distributiorGoodsInfoRepository.updateStatusForLaKaLa();
        } else {
            distributiorGoodsInfoRepository.updateStatusForLaKaLa(storeId,customerId);
        }
    }

    public void validate(DistributorGoodsInfoValidateRequest request) {
        //所有商品
        if(Boolean.TRUE.equals(request.getAllFlag())){
            if(this.check(request.getStoreId(), null, null, null)) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{"分销"});
            }
        } else if(this.check(request.getStoreId(), request.getBrandIds(), request.getStoreCateIds(), request.getSkuIds())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{"分销"});
        }
    }

    /**
     * 验证商品、品牌的重合
     * @param storeId 店铺id
     * @param brandIds 品牌Id
     * @param cateIds 店铺分类Id
     * @param skuIds 商品skuId
     * @return 重合结果
     */
    public Boolean check(Long storeId, List<Long> brandIds, List<Long> cateIds, List<String> skuIds) {
        List<String> spuIds = null;
        if (CollectionUtils.isNotEmpty(cateIds)) {
            spuIds = storeCateGoodsRelaRepository.selectGoodsIdByStoreCateIds(cateIds);
            if (CollectionUtils.isEmpty(spuIds)) {
                return Boolean.FALSE;
            }
        }
        GoodsInfoQueryRequest count = new GoodsInfoQueryRequest();
        count.setStoreId(storeId);
        count.setBrandIds(brandIds);
        count.setGoodsIds(spuIds);
        count.setGoodsInfoIds(skuIds);
        count.setDelFlag(DeleteFlag.NO.toValue());
        count.setDistributionGoodsAuditList(Arrays.asList(
                DistributionGoodsAudit.CHECKED, DistributionGoodsAudit.WAIT_CHECK,
                DistributionGoodsAudit.NOT_PASS, DistributionGoodsAudit.FORBID));
        return this.skuCount(count) > 0;
    }

    public long skuCount(GoodsInfoQueryRequest queryRequest) {
        return goodsInfoRepository.count(queryRequest.getWhereCriteria());
    }
}
