package com.wanmi.sbc.goods.marketing.service;


//import com.codingapi.txlcn.tc.annotation.TccTransaction;

import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.goods.api.request.marketing.GoodsMarketingSyncRequest;
import com.wanmi.sbc.goods.api.request.marketing.ListByCustomerIdAndGoodsInfoIdReq;
import com.wanmi.sbc.goods.api.request.marketing.ListByCustomerIdAndMarketingIdReq;
import com.wanmi.sbc.goods.marketing.model.data.GoodsMarketing;
import com.wanmi.sbc.goods.marketing.repository.GoodsMarketingRepository;
import com.wanmi.sbc.goods.util.mapper.GoodsMarketingMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品营销
 */
@Service
@Transactional(readOnly = true, timeout = 10, rollbackFor = Exception.class)
public class GoodsMarketingService {
    @Autowired
    private GoodsMarketingRepository marketingRepository;

    @Autowired
    private GoodsMarketingService goodsMarketingService;

    @Autowired
    private GoodsMarketingMapper goodsMarketingMapper;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 新增文档
     * 专门用于数据新增服务,不允许数据修改的时候调用
     *
     * @param goodsMarketing
     */
    public GoodsMarketing addGoodsMarketing(GoodsMarketing goodsMarketing) {
        String lockKey = CacheKeyConstant.GOODS_MARKETING_ADD_LOCK.concat(goodsMarketing.getCustomerId());
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try {
            GoodsMarketing  goodsMarke = marketingRepository.queryByCustomerIdAndGoodsInfoId(goodsMarketing.getCustomerId(), goodsMarketing.getGoodsInfoId());
            if (Objects.isNull(goodsMarke)) {
                return marketingRepository.save(goodsMarketing);
            } else {
                return goodsMarke;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 修改文档
     * 专门用于数据修改服务,不允许数据新增的时候调用
     *
     * @param goodsMarketing
     */
    public GoodsMarketing updateGoodsMarketing(GoodsMarketing goodsMarketing) {
        return marketingRepository.save(goodsMarketing);
    }

    /**
     * 获取采购单中, 各商品用户选择/默认选择参加的营销活动(满减/满折/满赠)
     *
     * @param customerId
     * @return
     */
    public List<GoodsMarketing> queryGoodsMarketingList(String customerId) {
        List<GoodsMarketing> goodsMarketings = marketingRepository.queryGoodsMarketingListByCustomerId(customerId);
        // 这边是为了处理用户高并发设置sku对应的营销活动,mongo中数据出现重复,采购单加载列表失败的问题(若迁移mysql加约束即可)
        //去重处理
        return goodsMarketings.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(o -> o.getCustomerId() + ":" + o.getGoodsInfoId() + ":" +o.getMarketingId()))
        ), ArrayList::new));
    }

    /**
     * 根据用户编号删除商品使用的营销
     *
     * @param customerId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int delByCustomerId(String customerId) {
        return marketingRepository.deleteAllByCustomerId(customerId);
    }

    /**
     * 根据用户编号和商品编号列表删除商品使用的营销
     *
     * @param customerId
     * @param goodsInfoIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int delByCustomerIdAndGoodsInfoIds(String customerId, List<String> goodsInfoIds) {
        return marketingRepository.deleteByCustomerIdAndGoodsInfoIdIn(customerId, goodsInfoIds);
    }

    /**
     * 批量添加商品使用的营销
     *
     * @param goodsMarketings
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<GoodsMarketing> batchAdd(List<GoodsMarketing> goodsMarketings) {
        String customerId = goodsMarketings.get(0).getCustomerId();
        String lockKey = CacheKeyConstant.GOODS_MARKETING_ADD_LOCK.concat(customerId);
        List<GoodsMarketing> returnList = new ArrayList<>();
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try {
            List<String> goodsInfoIdList = goodsMarketings.stream().map(GoodsMarketing :: getGoodsInfoId).collect(Collectors.toList());
            List<GoodsMarketing>  goodsMarketingList = marketingRepository.queryByCustomerIdAndGoodsInfoIdIn(customerId, goodsInfoIdList);
            if (CollectionUtils.isEmpty(goodsMarketingList)) {
                returnList = marketingRepository.saveAll(goodsMarketings);
            } else {
                List<String> finalGoodsInfoIdList = goodsMarketingList.stream().map(GoodsMarketing :: getGoodsInfoId).collect(Collectors.toList());
                returnList = goodsMarketings.stream().filter(goodsMarketing -> finalGoodsInfoIdList.contains(goodsMarketing.getGoodsInfoId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(returnList)) {
                    returnList = marketingRepository.saveAll(returnList);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return returnList;
    }


    /**
     * 修改商品使用的营销
     *
     * @param goodsMarketing
     * @return
     */
    //@TccTransaction
    @Transactional
    public GoodsMarketing modify(GoodsMarketing goodsMarketing) {
        if (StringUtils.isEmpty(goodsMarketing.getId())) {
            goodsMarketing.setId(UUIDUtil.getUUID());
        }
        GoodsMarketing oldGoodsMarketing = marketingRepository.queryByCustomerIdAndGoodsInfoId(goodsMarketing.getCustomerId(), goodsMarketing.getGoodsInfoId());

        if (oldGoodsMarketing == null) {
            return goodsMarketingService.addGoodsMarketing(goodsMarketing);
        }

        oldGoodsMarketing.setMarketingId(goodsMarketing.getMarketingId());

        return goodsMarketingService.updateGoodsMarketing(oldGoodsMarketing);
    }

    public List<GoodsMarketing> queryByCustomerIdAndMarketingId(ListByCustomerIdAndMarketingIdReq request) {
        return marketingRepository.queryByCustomerIdAndMarketingIdIn(request.getCustomerId(),
                Collections.singletonList(request.getMarketingId()));
    }

    public List<GoodsMarketing> queryByCustomerIdAndGoodsInfoId(ListByCustomerIdAndGoodsInfoIdReq request) {
        List<GoodsMarketing> goodsMarketings =
                marketingRepository.queryByCustomerIdAndGoodsInfoIdIn(request.getCustomerId(),
                        request.getGoodsInfoIds());
        List<GoodsMarketing> newGoodsMarketings = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(goodsMarketings)){
            newGoodsMarketings = marketingRepository.queryByCustomerIdAndMarketingIdIn(request.getCustomerId(),
                    goodsMarketings.stream().map(GoodsMarketing::getMarketingId).collect(Collectors.toList()));
        }
        return newGoodsMarketings;
    }

    /**
     * 同步购物车营销
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void sync(GoodsMarketingSyncRequest request){
        if(CollectionUtils.isNotEmpty(request.getDelSkuIds()) && StringUtils.isNotEmpty(request.getCustomerId())){
            this.delByCustomerIdAndGoodsInfoIds(request.getCustomerId(), request.getDelSkuIds());
        }
        if(CollectionUtils.isNotEmpty(request.getGoodsMarketings())){

            this.batchAdd(goodsMarketingMapper.goodsMarketingVOsToGoodsMarketings(request.getGoodsMarketings()));
        }
    }
}
