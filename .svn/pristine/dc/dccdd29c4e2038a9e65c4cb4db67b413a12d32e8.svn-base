package com.wanmi.sbc.goods.freight.service;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.handler.aop.MasterRouteOnly;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.freight.CollectPageInfoRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateStorePageRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateStoreSaveRequest;
import com.wanmi.sbc.goods.api.request.freight.GetFreightInGoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.freight.CollectPageInfoResponse;
import com.wanmi.sbc.goods.api.response.freight.GetFreightInGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.CollectPageFreightFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateStore;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateStoreRepository;
import com.wanmi.sbc.goods.freight.vo.FreightPackageGoodsPriceVO;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 店铺运费模板服务
 * Created by sunkun on 2018/5/3.
 */
@Service
public class FreightTemplateStoreService extends FreightTemplateService{

    @Resource
    private FreightTemplateStoreRepository freightTemplateStoreRepository;

    /**
     * 更新店铺运费模板
     *
     * @param request 店铺运费模板信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void renewalFreightTemplateStore(FreightTemplateStoreSaveRequest request) {
        FreightTemplateStore freightTemplateStore = null;
        if (request.getFreightTempId() == null) {
            this.freightTemplateNameIsRepetition(request.getStoreId(), request.getFreightTempName());
            //新增店铺运费模板
            freightTemplateStore = new FreightTemplateStore();
            BeanUtils.copyProperties(request, freightTemplateStore);
            freightTemplateStore.setCreateTime(LocalDateTime.now());
            freightTemplateStore.setDefaultFlag(DefaultFlag.NO);
            freightTemplateStore.setDelFlag(DeleteFlag.NO);
        } else {
            //修改店铺运费模板
            freightTemplateStore = this.queryById(request.getFreightTempId());
            if (!Objects.equals(freightTemplateStore.getStoreId(), request.getStoreId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (!StringUtils.equals(freightTemplateStore.getFreightTempName(), request.getFreightTempName())) {
                this.freightTemplateNameIsRepetition(freightTemplateStore.getStoreId(), request.getFreightTempName());
            }
            freightTemplateStore.setFixedFreight(request.getFixedFreight());
            freightTemplateStore.setFreightType(request.getFreightType());
            freightTemplateStore.setSatisfyFreight(request.getSatisfyFreight());
            freightTemplateStore.setSatisfyPrice(request.getSatisfyPrice());
            freightTemplateStore.setFreightTempName(request.getFreightTempName());
            freightTemplateStore.setDeliverWay(request.getDeliverWay());
        }
        freightTemplateStore.setDestinationArea(StringUtils.join(request.getDestinationArea(), ","));
        freightTemplateStore.setDestinationAreaName(StringUtils.join(request.getDestinationAreaName(), ","));
        List<Long> araeList = this.querySelectedArea(freightTemplateStore.getStoreId(), freightTemplateStore.getFreightTempId() != null ? freightTemplateStore.getFreightTempId() : 0L);
        //非默认模板校验区域是否重复
        if(Objects.equals(freightTemplateStore.getDefaultFlag(),DefaultFlag.NO)){
            araeList.addAll(Arrays.asList(request.getDestinationArea()).stream().map(Long::valueOf).collect(Collectors.toList()));
            if (this.verifyAreaRepetition(araeList)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030030);
            }
        }
        freightTemplateStoreRepository.save(freightTemplateStore);
    }

    /**
     * 校验名称重复
     *
     * @param storeId 店铺id
     * @param freightTempName 模板名称
     */
    public void freightTemplateNameIsRepetition(Long storeId, String freightTempName) {
        FreightTemplateStore freightTemplateStore = freightTemplateStoreRepository.findByFreightTemplateName(storeId, freightTempName);
        if (freightTemplateStore != null) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030031);
        }
    }

    /**
     * 根据id查询店铺运费模板
     *
     * @param freightTempId 店铺运费模板id
     * @return 店铺运费模板
     */
    public FreightTemplateStore queryById(Long freightTempId) {
        FreightTemplateStore freightTemplateStore = freightTemplateStoreRepository.findByIdAndDefaultFlag(freightTempId);
        if (freightTemplateStore == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return freightTemplateStore;
    }

    /**
     * 查询店铺下所有店铺运费模板
     *
     * @param request 分页参数
     * @return 店铺运费模板分页列表
     */
    public Page<FreightTemplateStore> queryByAll(FreightTemplateStorePageRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("defaultFlag", "desc");
        map.put("createTime", "desc");
        request.setSortMap(map);
        Page<FreightTemplateStore> page = freightTemplateStoreRepository.findAll(getWhereCriteria(request.getStoreId()), request.getPageRequest());
        return page;
    }

    /**
     * 根据主键id删除店铺运费模板
     *
     * @param id 店铺运费模板id
     * @param storeId 店铺id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delById(Long id, Long storeId) {
        FreightTemplateStore freightTemplateStore = this.queryById(id);
        if (!Objects.equals(freightTemplateStore.getStoreId(), storeId) ||
                Objects.equals(freightTemplateStore.getDefaultFlag(), DefaultFlag.YES)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        freightTemplateStore.setDelFlag(DeleteFlag.YES);
        freightTemplateStoreRepository.save(freightTemplateStore);
    }

    /**
     * 根据店铺id和删除状态查询店铺运费模板列表
     * @param storeId 店铺id
     * @param deleteFlag 删除状态
     * @return 店铺运费模板列表
     */
    @MasterRouteOnly
    public List<FreightTemplateStore> findByStoreIdAndDeleteFlag(Long storeId, DeleteFlag deleteFlag){
        return freightTemplateStoreRepository.findByAll(storeId, deleteFlag);
    }

    /**
     * 查询店铺运费模板已选的区域
     *
     * @param storeId 店铺id
     * @param id 店铺运费模板id
     * @return 批量区域id数据
     */
    public List<Long> querySelectedArea(Long storeId, Long id) {
        List<FreightTemplateStore> freightTemplateStores = freightTemplateStoreRepository.findByAll(storeId, DeleteFlag.NO);
        return freightTemplateStores.stream().filter(info -> !Objects.equals(info.getFreightTempId(), id)).map(info -> {
            if (StringUtils.isBlank(info.getDestinationArea())) {
                return null;
            }
            return Arrays.asList(info.getDestinationArea().split(","));
        }).filter(Objects::nonNull).flatMap(Collection::stream).map(Long::valueOf).collect(Collectors.toList());
    }

    /**
     * 封装公共条件
     *
     * @param storeId 店铺id
     * @return 公共条件jpa类
     */
    public static Specification<FreightTemplateStore> getWhereCriteria(Long storeId) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));
            predicates.add(cbuild.equal(root.get("storeId"), storeId));
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }

    /**
     * 验证区域id是否出现重复
     * @param areaList 区域id批量数据
     * @return true:有重复 false:没有重复
     */
    private boolean verifyAreaRepetition(List<Long> areaList) {
        Set<Long> set = new HashSet<>();
        areaList.forEach(id -> {
            set.add(id);
        });
        return areaList.size() != set.size();
    }

    /**
     * @description 查询商品详情运费信息
     * @author wur
     * @date: 2022/7/7 11:37
     * @param request
     * @param goodsInfo
     * @param storeVO
     * @return
     */
    public GetFreightInGoodsInfoResponse inGoodsInfoPage(
            GetFreightInGoodsInfoRequest request, GoodsInfo goodsInfo, StoreVO storeVO) {
        BigDecimal goodsPrice = request.getPrice().multiply(new BigDecimal(request.getNum()));
        // 1. 店铺运费模板计算
        FreightTemplateStore templateStore =
                getByAddress(storeVO.getStoreId(), request.getProvinceId(), request.getCityId());
        if (Objects.isNull(templateStore)) {
            return null;
        }

        GetFreightInGoodsInfoResponse response =
                GetFreightInGoodsInfoResponse.builder()
                        .freightTemplateId(templateStore.getFreightTempId())
                        .freightTemplateType(DefaultFlag.NO)
                        .freightDescribe("免运费")
                        .collectFlag(Boolean.FALSE)
                        .build();

        if (DefaultFlag.NO.equals(templateStore.getFreightType())) {
            // 1.2. 满金额包邮情况
            if (goodsPrice.compareTo(templateStore.getSatisfyPrice()) < 0
                    && BigDecimal.ZERO.compareTo(templateStore.getSatisfyFreight()) < 0) {
                response.setCollectFlag(Boolean.TRUE);
                response.setFreightDescribe(
                        String.format(
                                "运费%s元，满%s元免运费",
                                templateStore.getSatisfyFreight(),
                                templateStore.getSatisfyPrice()));
            }
        } else {
            // 1.3. 固定运费情况
            if (templateStore.getFixedFreight().compareTo(BigDecimal.ZERO) > 0) {
                response.setCollectFlag(Boolean.FALSE);
                response.setFreightDescribe(
                        String.format(String.format("运费%s元", templateStore.getFixedFreight())));
            }
        }
        return response;
    }

    /**
     * @description 查询凑单页 凑单信息
     * @author wur
     * @date: 2022/7/7 16:56
     * @param request
     * @return
     */
    public CollectPageInfoResponse collectPageInfo(CollectPageInfoRequest request) {
        FreightTemplateStore storeFreight = this.queryById(request.getFreightTemplateId());
        if (DefaultFlag.YES.equals(storeFreight.getFreightType())) {
            return CollectPageInfoResponse.builder().build();
        }
        FreightPackageGoodsPriceVO priceVO =
                super.packageGoodsPrice(
                        request.getFreightGoodsInfoVOList(), request.getCustomer());
        BigDecimal totalAmount = priceVO.getTotalAmount();
        CollectPageInfoResponse response = new CollectPageInfoResponse();
        Long totalCount =
                request.getFreightGoodsInfoVOList().stream()
                        .map(FreightGoodsInfoVO::getNum)
                        .reduce(0L, Long::sum);
        response.setTotalNum(totalCount);
        response.setTotalAmount(totalAmount);
        response.setConditionAmount(storeFreight.getSatisfyPrice());
        response.setFreightGoodsInfoVOList(request.getFreightGoodsInfoVOList());
        // 不包邮
        if (totalAmount.compareTo(storeFreight.getSatisfyPrice()) < 0) {
            response.setFreightFlag(CollectPageFreightFlag.COLLECT);
            response.setConditionLack(storeFreight.getSatisfyPrice().subtract(totalAmount));
            response.setConditionUnit("元");
        }
        return response;
    }

    /**
     * @description 购物车 - 处理店铺运费
     * @author wur
     * @date: 2022/7/13 14:39
     * @param goodsInfoList 商品
     * @param storeVO 商家信息
     * @param address 收货地址
     * @return
     */
    public FreightTemplateCartVO cartFreightList(
            List<GoodsInfoCartVO> goodsInfoList, StoreVO storeVO, PlatformAddress address) {
        FreightTemplateCartVO cartVO = new FreightTemplateCartVO();

        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return null;
        }

        // 根据地址匹配运费模板
        FreightTemplateStore templateStore =
                getByAddress(storeVO.getStoreId(), address.getProvinceId(), address.getCityId());
        if (Objects.isNull(templateStore)) {
            return null;
        }
        cartVO.setGoodsInfoId(
                goodsInfoList.stream()
                        .map(GoodsInfoCartVO::getGoodsInfoId)
                        .collect(Collectors.toList()));
        // 处理满金
        FreightTemplateStoreCartVO freightTemplateStoreCartVO = new FreightTemplateStoreCartVO();
        freightTemplateStoreCartVO.setFreightTemplateId(templateStore.getFreightTempId());
        freightTemplateStoreCartVO.setFreightType(templateStore.getFreightType());
        if (DefaultFlag.NO.equals(templateStore.getFreightType())) {
            if (BigDecimal.ZERO.compareTo(templateStore.getSatisfyFreight()) >= 0) {
                cartVO.setFreeFlag(DeleteFlag.YES);
                cartVO.setDescribe("免运费");
            } else {
                cartVO.setDescribe(
                        String.format(
                                "运费%s元，满%s元免运费",
                                templateStore.getSatisfyFreight(),
                                templateStore.getSatisfyPrice()));
                freightTemplateStoreCartVO.setSatisfyFreight(templateStore.getSatisfyFreight());
                freightTemplateStoreCartVO.setSatisfyPrice(templateStore.getSatisfyPrice());
                cartVO.setFreightTemplateStoreCartVO(freightTemplateStoreCartVO);
            }
        } else {
            if (BigDecimal.ZERO.compareTo(templateStore.getFixedFreight()) >= 0) {
                cartVO.setFreeFlag(DeleteFlag.YES);
                cartVO.setDescribe("免运费");
            } else {
                cartVO.setDescribe(
                        String.format(String.format("运费%s元", templateStore.getFixedFreight())));
                freightTemplateStoreCartVO.setFixedFreight(templateStore.getFixedFreight());
                cartVO.setFreightTemplateStoreCartVO(freightTemplateStoreCartVO);
            }
        }
        return cartVO;
    }

    /**
     * @description 根据收货地址匹配运费模板
     * @author wur
     * @date: 2022/7/13 14:19
     * @param storeId
     * @param provId
     * @param cityId
     * @return
     */
    private FreightTemplateStore getByAddress(Long storeId, String provId, String cityId) {
        FreightTemplateStore templateStore = null;
        List<FreightTemplateStore> storeTemplateList =
                this.findByStoreIdAndDeleteFlag(storeId, DeleteFlag.NO);

        // 1.1. 配送地匹配运费模板(若匹配不上则使用默认运费模板)
        Optional<FreightTemplateStore> tempOptional =
                storeTemplateList.stream()
                        .filter(temp -> matchArea(temp.getDestinationArea(), provId, cityId))
                        .findFirst();
        if (tempOptional.isPresent()) {
            templateStore = tempOptional.get();
        } else {
            templateStore =
                    storeTemplateList.stream()
                            .filter(temp -> DefaultFlag.YES.equals(temp.getDefaultFlag()))
                            .findFirst()
                            .orElse(null);
        }
        return templateStore;
    }
}
