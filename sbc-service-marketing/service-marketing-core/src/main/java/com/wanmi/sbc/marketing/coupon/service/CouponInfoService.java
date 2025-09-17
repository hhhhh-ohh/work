package com.wanmi.sbc.marketing.coupon.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeListResponse;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.coupon.EsActivityCouponQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponPageRequest;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsActivityCouponVO;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListByConditionRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListCouponDetailRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewPageRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewPageResponse;
import com.wanmi.sbc.goods.bean.dto.CouponInfoForScopeNamesDTO;
import com.wanmi.sbc.goods.bean.dto.CouponMarketingScopeDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.CouponInfoPageResponse;
import com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoPageResponse;
import com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoResponse;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.MagicCouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.PointsCouponVO;
import com.wanmi.sbc.marketing.coupon.model.root.*;
import com.wanmi.sbc.marketing.coupon.repository.*;
import com.wanmi.sbc.marketing.coupon.response.CouponCateResponse;
import com.wanmi.sbc.marketing.coupon.response.CouponInfoResponse;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.model.root.NewcomerPurchaseCoupon;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.repository.NewcomerPurchaseCouponRepository;
import com.wanmi.sbc.marketing.pointscoupon.model.root.PointsCoupon;
import com.wanmi.sbc.marketing.pointscoupon.service.PointsCouponService;
import com.wanmi.sbc.marketing.util.XssUtils;
import com.wanmi.sbc.marketing.util.mapper.CouponInfoMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: songhanlin
 * @Date: Created In 11:42 AM 2018/9/12
 * @Description: 优惠券信息Service
 */
@Slf4j
@Service
public class CouponInfoService {

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private CouponCateRelaRepository couponCateRelaRepository;

    @Autowired
    private CouponMarketingScopeRepository couponMarketingScopeRepository;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CouponActivityRepository couponActivityRepository;

    @Autowired
    private CouponActivityConfigRepository couponActivityConfigRepository;

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    @Lazy
    private CouponCateService couponCateService;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private PointsCouponService pointsCouponService;

    @Autowired
    private CouponInfoMapper couponInfoMapper;

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private NewcomerPurchaseCouponRepository newcomerPurchaseCouponRepository;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<CouponInfo> getWhereCriteria(CouponInfoQueryRequest request) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>(16);
            //店铺id
            if (Objects.nonNull(request.getStoreId())) {
                predicates.add(cbuild.equal(root.get("storeId"), request.getStoreId()));
            }

            if (Objects.equals(DefaultFlag.YES, request.getIsMarketingChose())) {
                Predicate p1 = cbuild.not(cbuild.lessThan(root.get("endTime"), LocalDateTime.now()));
                Predicate p2 = cbuild.equal(root.get("rangeDayType"), RangeDayType.DAYS);
                Predicate result = cbuild.or(p1, p2);
                predicates.add(result);
            }

            if (Objects.nonNull(request.getStartTime())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                //大于或等于传入时间
                Predicate p3 = cbuild.greaterThanOrEqualTo(root.get("startTime"), request.getStartTime());
                Predicate p4 = cbuild.equal(root.get("rangeDayType"), RangeDayType.RANGE_DAY);
                Predicate resultStartTime = cbuild.and(p3, p4);
                predicates.add(resultStartTime);
            }
            if (Objects.nonNull(request.getEndTime())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Predicate p5 = cbuild.lessThanOrEqualTo(root.get("endTime"), request.getEndTime());
                Predicate p6 = cbuild.equal(root.get("rangeDayType"), RangeDayType.RANGE_DAY);
                Predicate resultEndTime = cbuild.and(p5, p6);
                predicates.add(resultEndTime);
            }

            //批量优惠券id
            if (CollectionUtils.isNotEmpty(request.getCouponIds())) {
                predicates.add(root.get("couponId").in(request.getCouponIds().stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())));
            }

            //是否平台优惠券 1平台 0店铺
            if (Objects.nonNull(request.getPlatformFlag())) {
                predicates.add(cbuild.equal(root.get("platformFlag"), request.getPlatformFlag()));
            }


            //使用范围
            if (Objects.nonNull(request.getScopeType())) {
                predicates.add(cbuild.equal(root.get("scopeType"), request.getScopeType()));
            }

            //券类型
            if(Objects.nonNull(request.getCouponType())) {
                predicates.add(cbuild.equal(root.get("couponType"), request.getCouponType()));
            }

            if (Objects.nonNull(request.getCouponMarketingType())) {
                predicates.add(cbuild.equal(root.get("couponMarketingType"), request.getCouponMarketingType()));
            }

            if (CollectionUtils.isNotEmpty(request.getCouponMarketingTypeList())) {
                predicates.add(root.get("couponMarketingType").in(request.getCouponMarketingTypeList()));
            }

            //批量优惠券id
            if (CollectionUtils.isNotEmpty(request.getStoreIds())) {
                predicates.add(root.get("storeId").in(request.getStoreIds()));
            }

            //模糊查询名称
            if (StringUtils.isNotEmpty(request.getLikeCouponName())) {
                predicates.add(cbuild.like(root.get("couponName"), StringUtil.SQL_LIKE_CHAR.concat(XssUtils.replaceLikeWildcard(request.getLikeCouponName().trim())).concat(StringUtil.SQL_LIKE_CHAR)));
            }

            //删除标记
            if (Objects.nonNull(request.getDelFlag())) {
                predicates.add(cbuild.equal(root.get("delFlag"), request.getDelFlag()));
            }

            if (Objects.nonNull(request.getCouponStatus())) {

                switch (request.getCouponStatus()) {
//                " AND now() >= t.begin_time AND now() <= t.end_time";
                    case STARTED://进行中
//                  predicates.add(cbuild.between(root.get("endTime"),LocalDateTime.parse(endTime,formatter),LocalDateTime.parse(endTime,formatter));
                        predicates.add(cbuild.lessThan(root.get("startTime"), LocalDateTime.now()));
                        predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                        predicates.add(cbuild.equal(root.get("rangeDayType"), RangeDayType.RANGE_DAY));
                        break;
                    case NOT_START://未生效
                        predicates.add(cbuild.greaterThanOrEqualTo(root.get("startTime"), LocalDateTime.now()));
                        predicates.add(cbuild.equal(root.get("rangeDayType"), RangeDayType.RANGE_DAY));
                        break;
                    case DAYS://领取生效
                        predicates.add(cbuild.equal(root.get("rangeDayType"), RangeDayType.DAYS));
                        break;
                    case ENDED://已结束
                        predicates.add(cbuild.lessThan(root.get("endTime"), LocalDateTime.now()));
                        predicates.add(cbuild.equal(root.get("rangeDayType"), RangeDayType.RANGE_DAY));
                        break;
                    default:
                        break;
                }
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }


    /**
     * 条件查询优惠券列表
     *
     * @param request
     * @return
     */
    public List<CouponInfo> queryCouponInfos(CouponInfoQueryRequest request) {
        Sort sort = request.getSort();
        if (Objects.nonNull(sort)) {
            return couponInfoRepository.findAll(this.getWhereCriteria(request), sort);
        } else {
            return couponInfoRepository.findAll(this.getWhereCriteria(request));
        }
    }

    /**
     * 分页查询优惠券列表
     *
     * @param request
     * @return
     */
    public CouponInfoPageResponse getCouponInfoPage(CouponInfoQueryRequest request) {
        //分页查询SKU信息列表
        Page<CouponInfo> couponInfos = couponInfoRepository.findAll(this.getWhereCriteria(request), request.getPageRequest());

        if (CollectionUtils.isEmpty(couponInfos.getContent())) {
            return CouponInfoPageResponse.builder().couponInfos(new MicroServicePage<>(Collections.emptyList())).build();
        }

        // 获得所有的优惠券id
        List<String> couponIds = couponInfos.getContent().stream().map(CouponInfo::getCouponId).collect(Collectors.toList());
        // 查询所有优惠券的优惠券分类关系
        List<CouponCateRela> couponCateRelaList = couponCateRelaRepository.findByCouponIdIn(couponIds);

        // 查询查询所有优惠券的优惠券分类
        List<CouponCate> cateList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(couponCateRelaList)) {


            cateList = couponCateService.queryByIds(
                    couponCateRelaList.stream().map(CouponCateRela::getCateId).distinct().collect(Collectors.toList()));

        }
        // 查询查询所有优惠券的商品信息


        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponIdIn(couponIds);

        List<CouponInfoVO> couponInfoVos = this.copyToCouponInfoVoNew(couponInfos.getContent(), couponCateRelaList, cateList, scopeList);

        List<CouponActivityConfig> configs = this.checkOpt(couponIds);
        //优惠券是否关联活动，0否 1是
        couponInfoVos.forEach(coupon -> {
            if (configs.stream().anyMatch(config -> config.getCouponId().equals(coupon.getCouponId()))) {
                coupon.setIsFree(DefaultFlag.NO);
            } else {
                coupon.setIsFree(DefaultFlag.YES);
            }
        });
        MicroServicePage<CouponInfoVO> page = new MicroServicePage<>(couponInfoVos, request.getPageable(), couponInfos.getTotalElements());
        return CouponInfoPageResponse.builder().couponInfos(page).build();
    }

    /**
     * 查询积分商城中积分兑换优惠券列表
     *
     * @param pointsCouponPage
     * @return
     */
    public Page<PointsCouponVO> getPointsCouponInfoPage(Page<PointsCoupon> pointsCouponPage) {
        Page<PointsCouponVO> pointsCouponVOPage = pointsCouponPage.map(entity -> {
            if (entity != null) {
                PointsCouponVO pointsCouponVO = new PointsCouponVO();
                KsBeanUtil.copyPropertiesThird(entity, pointsCouponVO);
                // 活动状态
                PointsCouponStatus pointsCouponStatus = pointsCouponService.getPointsCouponStatus(entity);
                pointsCouponVO.setPointsCouponStatus(pointsCouponStatus);
                return pointsCouponVO;
            }
            return null;
        });
        // 分页查询SKU信息列表
        List<CouponInfo> couponInfos = pointsCouponPage.getContent().stream()
                .map(PointsCoupon::getCouponInfo)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(couponInfos)) {
            return pointsCouponVOPage;
        }
        // 获得所有的优惠券id
        List<String> couponIds = couponInfos.stream().map(CouponInfo::getCouponId).collect(Collectors.toList());
        // 查询所有优惠券的优惠券分类关系
        List<CouponCateRela> couponCateRelaList = couponCateRelaRepository.findByCouponIdIn(couponIds);
        // 查询所有优惠券的优惠券分类
        List<CouponCate> cateList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(couponCateRelaList)) {
            cateList = couponCateService.queryByIds(
                    couponCateRelaList.stream().map(CouponCateRela::getCateId).distinct().collect(Collectors.toList()));
        }
        // 查询查询所有优惠券的商品信息
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponIdIn(couponIds);
        List<CouponInfoVO> couponInfoVos = this.copyToCouponInfoVo(couponInfos, couponCateRelaList, cateList, scopeList);

        List<CouponActivityConfig> configs = this.checkOpt(couponIds);
        //优惠券是否关联活动，0否 1是
        couponInfoVos.forEach(coupon -> {
            if (configs.stream().anyMatch(config -> config.getCouponId().equals(coupon.getCouponId()))) {
                coupon.setIsFree(DefaultFlag.NO);
            } else {
                coupon.setIsFree(DefaultFlag.YES);
            }
        });
        pointsCouponVOPage.forEach(pointsCouponVO -> {
            couponInfoVos.forEach(couponInfoVO -> {
                if (couponInfoVO.getCouponId().equals(pointsCouponVO.getCouponId())) {
                    pointsCouponVO.setCouponInfoVO(couponInfoVO);
                }
            });
        });
        return pointsCouponVOPage;
    }

    /**
     * 组装优惠券信息
     *
     * @param couponInfo
     * @return
     */
    public CouponInfoVO wrapperCouponDetailInfo(CouponInfo couponInfo) {
        // 获得优惠券id
        String couponId = couponInfo.getCouponId();
        // 查询优惠券的优惠券分类关系
        List<CouponCateRela> couponCateRelaList = couponCateRelaRepository.findByCouponId(couponId);
        // 查询优惠券的优惠券分类
        List<CouponCate> cateList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(couponCateRelaList)) {
            cateList = couponCateService.queryByIds(
                    couponCateRelaList.stream().map(CouponCateRela::getCateId).distinct().collect(Collectors.toList()));
        }
        // 查询所有优惠券的商品信息
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponId(couponId);
        List<CouponInfoVO> couponInfoVos = this.copyToCouponInfoVo(Collections.singletonList(couponInfo), couponCateRelaList, cateList, scopeList);

        List<CouponActivityConfig> configs = this.checkOpt(Collections.singletonList(couponId));
        //优惠券是否关联活动，0否 1是
        couponInfoVos.forEach(coupon -> {
            if (configs.stream().anyMatch(config -> config.getCouponId().equals(coupon.getCouponId()))) {
                coupon.setIsFree(DefaultFlag.NO);
            } else {
                coupon.setIsFree(DefaultFlag.YES);
            }
        });
        return couponInfoVos.get(0);
    }

    /**
     * CouponInfo  包装成vo
     *
     * @param couponInfoList
     * @param couponCateRelaList
     * @param cateList
     * @param scopeList
     * @return
     */
    private List<CouponInfoVO> copyToCouponInfoVo(List<CouponInfo> couponInfoList, List<CouponCateRela> couponCateRelaList, List<CouponCate> cateList, List<CouponMarketingScope> scopeList) {
        List<CouponInfoVO> couponInfoVoList = couponInfoList.stream().map(
                couponInfo -> {
                    CouponInfoResponse couponInfoResponse = new CouponInfoResponse();
                    CouponInfoVO couponInfoVo = new CouponInfoVO();
                    KsBeanUtil.copyPropertiesThird(couponInfo, couponInfoVo);
                    couponInfoResponse.setCouponInfo(couponInfoVo);
                    //设置优惠券分类id
                    couponInfoResponse.getCouponInfo().setCateIds(
                            couponCateRelaList.stream().filter(item -> item.getCouponId().equals(couponInfo.getCouponId()))
                                    .map(item -> item.getCateId()).collect(Collectors.toList())
                    );
                    //设置优惠券分类名称
                    List<String> nameList = couponInfoResponse.getCouponInfo().getCateIds().stream().map(
                            id -> {
                                String name = "";
                                for (CouponCate cate : cateList) {
                                    if (cate.getCouponCateId().equals(id)) {
                                        name = cate.getCouponCateName();
                                        return name;
                                    }
                                }
                                return name;
                            }
                    ).collect(Collectors.toList());
                    couponInfoResponse.getCouponInfo().setCateNames(nameList);
                    if (ScopeType.SKU != couponInfo.getScopeType()) {
                        this.couponDetail(couponInfoResponse, couponInfo,
                                scopeList.stream().filter(item -> item.getCouponId().equals(couponInfo.getCouponId())).collect(Collectors.toList()));
                        couponInfoVo.setScopeNames(couponInfoResponse.getCouponInfo().getScopeNames());//关联商品
                    }
                    couponInfoVo.setCateNames(couponInfoResponse.getCouponInfo().getCateNames());//关联分类
                    couponInfoVo.setCouponStatus(getCouponStatus(couponInfo));//获取优惠券状态
                    return couponInfoVo;
                }).collect(Collectors.toList());
        return couponInfoVoList;
    }

    /**
     * 复制一个优惠券信息
     *
     * @param couponId
     * @param operatorId
     * @throws SbcRuntimeException
     */
    @Transactional
    public CouponInfoVO copyCouponInfo(String couponId, String operatorId) throws SbcRuntimeException {
        //业务校验
        CouponInfo couponInfo = getCouponInfoById(couponId);
        List<String> cateIds = new ArrayList<>();
        List<String> scopeIds = null;
        //优惠券分类信息
        List<CouponCateRela> couponCateRelaList = couponCateRelaRepository.findByCouponId(couponInfo.getCouponId());
        if (CollectionUtils.isNotEmpty(couponCateRelaList)) {
            cateIds = couponCateRelaList.stream().map(CouponCateRela::getCateId).collect(Collectors.toList());
        }
        //商品信息：
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponId(couponInfo.getCouponId());
        if (CollectionUtils.isNotEmpty(scopeList)) {
            scopeIds = scopeList.stream().map(CouponMarketingScope::getScopeId).collect(Collectors.toList());
        }

        //优惠券分类
        if (CollectionUtils.isNotEmpty(cateIds) && cateIds.size() > Constants.THREE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //优惠券商品
        if (!Objects.equals(ScopeType.ALL, couponInfo.getScopeType())) {
            if (CollectionUtils.isEmpty(scopeIds)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        CouponInfo couponInfoNew = new CouponInfo();
        KsBeanUtil.copyPropertiesThird(couponInfo, couponInfoNew);
        couponInfoNew.setCouponId(null);
        couponInfoNew.setCreateTime(LocalDateTime.now());
        couponInfoNew.setCreatePerson(operatorId);
        //保存优惠券
        CouponInfo couponInfoCopy = couponInfoRepository.save(couponInfoNew);
        //保存优惠券关联分类
        if (CollectionUtils.isNotEmpty(cateIds)) {
            couponCateRelaRepository.saveAll(generateCouponCateList(couponInfoCopy, cateIds));
        }
        //保存优惠券关联商品
        if (!Objects.equals(ScopeType.ALL, couponInfoCopy.getScopeType())) {
            couponMarketingScopeRepository.saveAll(generateCouponScopeList(couponInfoCopy, scopeIds));
        }


        CouponInfoVO couponInfoVO = couponInfoMapper.couponInfoToCouponInfoVO(couponInfoCopy);
        couponInfoVO.setCateIds(cateIds);
        couponInfoVO.setScopeIds(scopeIds);
        return couponInfoVO;
    }

    /**
     * 新增优惠券信息
     *
     * @param request
     * @throws SbcRuntimeException
     */
    @Transactional
    public CouponInfoVO addCouponInfo(CouponInfoAddRequest request) throws SbcRuntimeException {
        // 校验优惠券分类
        this.checkCouponCate(request.getCateIds(), request.getPlatformFlag());
        //保存优惠券
        CouponInfo couponInfo = couponInfoRepository.save(generateCoupon(request));
        //保存优惠券关联分类
        List<String> cateIds = request.getCateIds();
        if (CollectionUtils.isNotEmpty(cateIds)) {
            couponCateRelaRepository.saveAll(generateCouponCateList(couponInfo, cateIds));
        }
        //保存优惠券关联商品
        List<String> scopeIds = request.getScopeIds().stream().distinct().collect(Collectors.toList());
        if(request.getScopeType().equals(ScopeType.STORE)){
            try{
            List<StoreVO> storeVOS = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(scopeIds.stream().map(t->Long.valueOf(t)).collect(Collectors.toList())).build()).getContext().getStoreVOList();
            if(CollectionUtils.isEmpty(storeVOS)||storeVOS.size()!=scopeIds.size()){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            }catch (NumberFormatException e){
                log.error("{}",e);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        if (CouponMarketingType.FREIGHT_COUPON != request.getCouponMarketingType()) {
            if (!Objects.equals(ScopeType.ALL, request.getScopeType())) {
                couponMarketingScopeRepository.saveAll(generateCouponScopeList(couponInfo, request.getScopeIds()));
            }
        }
        CouponInfoVO couponInfoVO = couponInfoMapper.couponInfoToCouponInfoVO(couponInfo);
        couponInfoVO.setCateIds(cateIds);
        couponInfoVO.setScopeIds(scopeIds);
        couponInfoVO.setStoreIds(request.getStoreIds());
        return couponInfoVO;
    }

    private void checkCouponCate(List<String> cateList, DefaultFlag platformFlag) {
        if (CollectionUtils.isEmpty(cateList)) {
            return;
        }
        List<CouponCate> couponCateList = couponCateService.queryByIds(cateList);
        if (cateList.size() == couponCateList.size()) {
            //如果是商家端的，要排除掉平台专用分类
            if (DefaultFlag.NO.equals(platformFlag)) {
                couponCateList = couponCateList.stream().filter(i ->
                        DefaultFlag.NO.equals(i.getOnlyPlatformFlag())
                ).collect(Collectors.toList());
            }
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080045);
        }
        List<CouponCate> newCouponCateList = couponCateList.stream().filter(i -> DeleteFlag.NO == i.getDelFlag()).collect(Collectors.toList());
        //筛选出已经被删除的id
        List<String> errorCateIds = cateList.stream().filter(id ->
                newCouponCateList.stream().noneMatch(cate -> cate.getCouponCateId().equals(id))
        ).collect(Collectors.toList());
        if (!errorCateIds.isEmpty()) {
            throw new SbcRuntimeException(errorCateIds, MarketingErrorCodeEnum.K080045);
        }
    }

    /**
     * 编辑优惠券信息
     *
     * @param request
     * @throws SbcRuntimeException
     */
    @Transactional
    public CouponInfoVO modifyCouponInfo(CouponInfoModifyRequest request) throws SbcRuntimeException {

        //业务校验 1.未被关联至活动的优惠券支持编辑
        CouponInfo couponInfo = getCouponInfoById(request.getCouponId());

        List<String> ids = new ArrayList<>();
        ids.add(couponInfo.getCouponId());
        List<CouponActivityConfig> configs = checkOpt(ids);
        if (!configs.isEmpty()) {
            //优惠券已经绑定活动
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080047);
        }
        // 校验优惠券分类
        this.checkCouponCate(request.getCateIds(), couponInfo.getPlatformFlag());
        //全部删除关联优惠券商品
        couponMarketingScopeRepository.deleteByCouponId(couponInfo.getCouponId());

        couponInfo.setParticipateType(request.getParticipateType());
        //保存优惠券
        couponInfo = couponInfoRepository.save(generateCouponByModify(request, couponInfo));

        //删除全部关联优惠券分类
        couponCateRelaRepository.deleteByCouponId(couponInfo.getCouponId());

        //保存优惠券关联分类
        List<String> cateIds = request.getCateIds();
        if (CollectionUtils.isNotEmpty(cateIds)) {
            //保存优惠券关联分类
            couponCateRelaRepository.saveAll(generateCouponCateList(couponInfo, cateIds));
        }
        //保存优惠券关联商品
        List<String> scopeIds = request.getScopeIds();
        if (CouponMarketingType.FREIGHT_COUPON != request.getCouponMarketingType()) {
            if (!Objects.equals(ScopeType.ALL, request.getScopeType())) {
                couponMarketingScopeRepository.saveAll(generateCouponScopeList(couponInfo, scopeIds));
            }
        }
        //如果是平台券,新人购优惠券同步优惠券名称
        if (DefaultFlag.YES.equals(couponInfo.getPlatformFlag())) {
            NewcomerPurchaseCoupon newcomerPurchaseCoupon
                    = newcomerPurchaseCouponRepository.findByCouponIdAndDelFlag(couponInfo.getCouponId(), DeleteFlag.NO);
            if (Objects.nonNull(newcomerPurchaseCoupon)) {
                newcomerPurchaseCouponRepository.modifyCouponName(couponInfo.getCouponName(),newcomerPurchaseCoupon.getId());
            }
        }

        CouponInfoVO couponInfoVO = couponInfoMapper.couponInfoToCouponInfoVO(couponInfo);
        couponInfoVO.setCateIds(cateIds);
        couponInfoVO.setScopeIds(scopeIds);
        return couponInfoVO;
    }

    /**
     * 优惠券编辑删除验证
     *
     * @param couponIds
     * @return
     */
    public List<CouponActivityConfig> checkOpt(List<String> couponIds) {
        //只有未关联活动的优惠券支持删除
        return couponActivityConfigRepository.findByCouponIds(couponIds);
    }


    /**
     * 删除优惠券
     *
     * @param couponId
     * @param operatorId
     */
    @Transactional
    public void deleteCoupon(String couponId, String operatorId) {
        List<String> ids = new ArrayList<>();
        ids.add(couponId);
        List<CouponActivityConfig> configList = checkOpt(ids);
        if (!configList.isEmpty()) {
            //优惠券已经绑定活动
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080047);
        }
        //保存优惠券
        couponInfoRepository.deleteCoupon(couponId, operatorId);
    }

    /**
     * 查询优惠券详情（包含优惠券+优惠券分类+优惠券商品信息）
     *
     * @param couponId
     * @return
     */
    public CouponInfoResponse queryCouponInfoDetail(String couponId, Long storeId) {
        CouponInfoResponse couponInfoResponse = new CouponInfoResponse();
        CouponInfo couponInfo = getCouponInfoById(couponId);
        CouponInfoVO couponInfoVo = new CouponInfoVO();
        KsBeanUtil.copyPropertiesThird(couponInfo, couponInfoVo);
        couponInfoResponse.setCouponInfo(couponInfoVo);
        List<CouponCateRela> couponCateRelaList = couponCateRelaRepository.findByCouponId(couponInfo.getCouponId());
        //优惠券分类信息
        couponCate(couponInfoResponse, couponCateRelaList);
        //商品信息：
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponId(couponInfo.getCouponId());
        //组装商品信息
        couponDetail(couponInfoResponse, couponInfo, scopeList);
        //填充o2o信息
        couponInfoService.populateO2oCouponDetail(couponInfoResponse, couponInfo, scopeList, storeId);
        return couponInfoResponse;
    }

    /**
     * 填充o2o信息
     *
     * @param couponInfoResponse
     * @param couponInfo
     * @param scopeList
     * @param storeId            门店端能查看平台端创建的优惠券，通过storeId去查询门店价
     */
    public void populateO2oCouponDetail(CouponInfoResponse couponInfoResponse, CouponInfo couponInfo, List<CouponMarketingScope> scopeList, Long storeId) {

    }


    /**
     * 查询优惠券信息（优惠券单表信息）
     *
     * @param couponId
     * @return
     */
    public CouponInfoVO queryCouponInfo(String couponId) {
        CouponInfo couponInfo = getCouponInfoById(couponId);
        if (Objects.isNull(couponInfo)) {
            return null;
        }
        CouponInfoVO couponInfoVo = new CouponInfoVO();
        KsBeanUtil.copyPropertiesThird(couponInfo, couponInfoVo);
        return couponInfoVo;
    }


    /**
     * 优惠券详情
     * 优惠券分类信息
     * 商品信息：全部、指定品牌、指定分类、指定商品
     *
     * @param couponInfoResponse
     * @param couponInfo
     * @param scopeList
     */
    public void couponDetail(CouponInfoResponse couponInfoResponse, CouponInfo couponInfo, List<CouponMarketingScope> scopeList) {
        //品牌分类
        if (ScopeType.BRAND == couponInfo.getScopeType()) {
            //营销活动包含的所有品牌Id
            List<Long> brandsIds = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors.toList());
            couponBrandDetail(couponInfoResponse, couponInfo, brandsIds);
        }
        //店铺分类
        if (ScopeType.STORE_CATE == couponInfo.getScopeType()) {
            //营销活动包含的所有商品Id
            List<Long> cateIds = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors.toList());
            couponStoreCateDetail(couponInfoResponse, couponInfo, cateIds);
        }
        //平台分类
        if (ScopeType.BOSS_CATE == couponInfo.getScopeType()) {
            //营销活动包含的所有商品Id
            List<Long> cateIds = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors.toList());
            couponBossCateDetail(couponInfoResponse, couponInfo, cateIds);
        }
        //店铺可用
        if (ScopeType.SKU == couponInfo.getScopeType()) {
            //营销活动包含的所有商品Id
            List<String> goodsInfoIds = scopeList.stream().map(CouponMarketingScope::getScopeId).collect(Collectors.toList());
            couponGoodsDetail(couponInfoResponse, couponInfo, goodsInfoIds);
        }
        if(ScopeType.STORE == couponInfo.getScopeType()){
            //营销活动包含的所有店铺Id
            List<Long> storeIds = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors.toList());
            couponStoreDetail(couponInfoResponse, couponInfo, storeIds);
        }
    }


    /**
     * 优惠券分类
     *
     * @param couponInfoResponse
     * @param couponCateRelaList
     */
    private void couponCate(CouponInfoResponse couponInfoResponse, List<CouponCateRela> couponCateRelaList) {
        //组装商品信息
        if (CollectionUtils.isNotEmpty(couponCateRelaList)) {
            //营销活动包含的所有商品Id
            List<String> couponCateRelas = couponCateRelaList.stream().map(CouponCateRela::getCateId).collect(Collectors.toList());
            List<CouponCateResponse> couponCateList = couponCateService.listCouponCateLimitThreeByCouponCateIds(couponCateRelas);
            couponInfoResponse.getCouponInfo().setCateNames(couponCateList.stream().map(CouponCateResponse::getCouponCateName).collect(Collectors.toList()));
            couponInfoResponse.getCouponInfo().setCateIds(couponCateList.stream().map(CouponCateResponse::getCouponCateId).collect(Collectors.toList()));

        }
    }

    /**
     * 优惠券商品信息
     *
     * @param couponInfoResponse
     * @param couponInfo
     * @param goodsInfoIds
     */
    private void couponGoodsDetail(CouponInfoResponse couponInfoResponse, CouponInfo couponInfo, List<String> goodsInfoIds) {
        //组装商品信息
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            GoodsInfoViewPageRequest queryRequest = new GoodsInfoViewPageRequest();
            //FIXME 营销是平铺展示，但是数量达到一定层级，还是需要分页，先暂时这么控制
            queryRequest.setPageSize(10000);
            queryRequest.setAddedFlag(AddedFlag.YES.toValue());//上架
            queryRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
            queryRequest.setAuditStatus(CheckStatus.CHECKED);//已审核
            queryRequest.setIsMarketing(Boolean.TRUE); //是营销查询商品
            queryRequest.setGoodsInfoIds(goodsInfoIds);
            if (CouponType.STOREFRONT_VOUCHER != couponInfo.getCouponType() && CouponType.BOSS_STOREFRONT_VOUCHER != couponInfo.getCouponType()) {
                queryRequest.setStoreId(couponInfo.getStoreId());
            }
            if (Constants.BOSS_DEFAULT_STORE_ID.equals(queryRequest.getStoreId())) {
                queryRequest.setStoreId(null);
            }
            GoodsInfoViewPageResponse goodsInfoResponse = goodsInfoQueryProvider.pageView(queryRequest).getContext();
            List<GoodsVO> goodses = goodsInfoResponse.getGoodses();
            if (CollectionUtils.isEmpty(goodses)) {
                goodsInfoResponse.setGoodses(Lists.newArrayList());
            }
            Map<String, GoodsVO> goodsMap = goodsInfoResponse.getGoodses().parallelStream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
            Map<Long, GoodsCateVO> goodsCateVOMap = goodsInfoResponse.getCates().parallelStream().collect(Collectors.toMap(GoodsCateVO::getCateId, Function.identity()));
            Map<Long, GoodsBrandVO> goodsBrandVOMap = goodsInfoResponse.getBrands().parallelStream().collect(Collectors.toMap(GoodsBrandVO::getBrandId, Function.identity()));
            goodsInfoResponse.getGoodsInfoPage().getContent().forEach(goodsInfoVO -> {
                GoodsVO goods = goodsMap.get(goodsInfoVO.getGoodsId());
                goodsInfoVO.setProviderName(goods.getProviderName());
                Long storeId = goodsInfoVO.getStoreId();
                String storeName = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder()
                        .storeId(storeId)
                        .build()).getContext().getStoreVO().getStoreName();
                goodsInfoVO.setStoreName(storeName);
                Long cateId = goodsInfoVO.getCateId();
                if (Objects.nonNull(cateId)) {
                    GoodsCateVO goodsCateVO = goodsCateVOMap.get(cateId);
                    goodsInfoVO.setCateName(goodsCateVO.getCateName());
                }
                Long brandId = goodsInfoVO.getBrandId();
                if (Objects.nonNull(brandId)) {
                    GoodsBrandVO goodsBrandVO = goodsBrandVOMap.get(brandId);
                    goodsInfoVO.setBrandName(goodsBrandVO.getBrandName());
                }
            });

            couponInfoResponse.setGoodsList(GoodsInfoResponse.builder()
                    .goodsInfoPage(goodsInfoResponse.getGoodsInfoPage())
                    .brands(goodsInfoResponse.getBrands())
                    .cates(goodsInfoResponse.getCates())
                    .goodses(goodsInfoResponse.getGoodses())
                    .build());
            couponInfoResponse.getCouponInfo().setScopeNames(goodsInfoResponse.getGoodsInfoPage().getContent().stream().map(GoodsInfoVO::getGoodsInfoName).collect(Collectors.toList()));
            couponInfoResponse.getCouponInfo().setScopeIds(goodsInfoIds);
        }
    }

    /**
     * 优惠券商品分类信息
     *
     * @param couponInfoResponse
     * @param couponInfo
     * @param cateIds
     */
    private void couponStoreCateDetail(CouponInfoResponse couponInfoResponse, CouponInfo couponInfo, List<Long> cateIds) {

        //组装商品信息
        if (CollectionUtils.isNotEmpty(cateIds)) {
            //店铺分类
            if (Objects.equals(couponInfo.getPlatformFlag(), DefaultFlag.NO)) {
                List<StoreCateVO> storeCateList = storeCateQueryProvider.listByIds(new StoreCateListByIdsRequest(cateIds)).getContext().getStoreCateVOList();
                storeCateList = storeCateList.stream().filter(cate -> cate.getDelFlag() == DeleteFlag.NO).collect(Collectors.toList());
                List<StoreCateVO> newCateList = storeCateList;
                //只显示父级的节点的名称
                List<StoreCateVO> nameGoodsCateList = storeCateList.stream().filter(item -> newCateList.stream().noneMatch(i -> i.getStoreCateId().equals(item.getCateParentId()))).collect(Collectors.toList());

                couponInfoResponse.getCouponInfo().setScopeNames(nameGoodsCateList.stream().map(StoreCateVO::getCateName).collect(Collectors.toList()));
                couponInfoResponse.getCouponInfo().setScopeIds(cateIds.stream().map(id -> String.valueOf(id)).collect(Collectors.toList()));
            }
        }
    }

    /**
     * 优惠券商品分类信息
     *
     * @param couponInfoResponse
     * @param couponInfo
     * @param cateIds
     */
    private void couponBossCateDetail(CouponInfoResponse couponInfoResponse, CouponInfo couponInfo, List<Long> cateIds) {

        //组装商品信息
        if (CollectionUtils.isNotEmpty(cateIds)) {
            //平台分类
            if (Objects.equals(couponInfo.getPlatformFlag(), DefaultFlag.YES)) {
                GoodsCateListByConditionRequest request = new GoodsCateListByConditionRequest();
                request.setCateIds(cateIds);
                request.setDelFlag(DeleteFlag.NO.toValue());
                final List<GoodsCateVO> cateList = goodsCateQueryProvider.listByCondition(request).getContext().getGoodsCateVOList();
                //只显示父级的节点的名称
                List<GoodsCateVO> nameGoodsCateList = cateList.stream().filter(item -> cateList.stream().noneMatch(i -> i.getCateId().equals(item.getCateParentId()))).collect(Collectors.toList());
                couponInfoResponse.getCouponInfo().setScopeNames(nameGoodsCateList.stream().map(GoodsCateVO::getCateName).collect(Collectors.toList()));
                couponInfoResponse.getCouponInfo().setScopeIds(cateIds.stream().map(id -> String.valueOf(id)).collect(Collectors.toList()));
            }
        }
    }

    /**
     * 优惠券商品品牌信息
     *
     * @param couponInfoResponse
     * @param couponInfo
     * @param brandsIds
     */
    private void couponBrandDetail(CouponInfoResponse couponInfoResponse, CouponInfo couponInfo, List<Long> brandsIds) {
        if (CollectionUtils.isEmpty(brandsIds)) {
            return;
        }
        //优惠券品牌信息
        List<String> brandsIdList = new ArrayList<>();
        List<String> brandsNameList = new ArrayList<>();
        if (DefaultFlag.NO.equals(couponInfo.getPlatformFlag())) {
            //获取店铺签约的品牌
            ContractBrandListRequest brandRequest = new ContractBrandListRequest();
            brandRequest.setGoodsBrandIds(brandsIds);
            brandRequest.setStoreId(couponInfo.getStoreId());
            //获取店铺签约的品牌
            List<ContractBrandVO> brandList = contractBrandQueryProvider.list(brandRequest).getContext().getContractBrandVOList();
            //筛选出店铺签约的品牌信息
            brandList = brandList.stream().filter(item ->
                    brandsIds.stream().anyMatch(i ->
                            i.equals(item.getGoodsBrand().getBrandId())
                    )
            ).collect(Collectors.toList());
            brandList.forEach(brand->{
                brandsIdList.add(brand.getGoodsBrand().getBrandId().toString());
                brandsNameList.add(brand.getGoodsBrand().getBrandName());
            });
        } else {
            //获取平台的品牌
            GoodsBrandListRequest brandRequest = new GoodsBrandListRequest();
            brandRequest.setDelFlag(DeleteFlag.NO.toValue());
            brandRequest.setBrandIds(brandsIds);
            List<GoodsBrandVO> brandList = goodsBrandQueryProvider.list(brandRequest).getContext().getGoodsBrandVOList();
            couponInfoResponse.getCouponInfo().setScopeNames(brandList.stream().map(GoodsBrandVO::getBrandName).collect(Collectors.toList()));
            brandList.forEach(brand->{
                brandsIdList.add(brand.getBrandId().toString());
                brandsNameList.add(brand.getBrandName());
            });
        }
        couponInfoResponse.getCouponInfo().setScopeNames(brandsNameList);
        couponInfoResponse.getCouponInfo().setScopeIds(brandsIdList);

    }

    /**
     * 优惠券商品品牌信息
     *
     * @param couponInfoResponse
     * @param couponInfo
     * @param storeIds
     */
    private void couponStoreDetail(CouponInfoResponse couponInfoResponse, CouponInfo couponInfo, List<Long> storeIds) {
        if (CollectionUtils.isEmpty(storeIds)) {
            return;
        }
        List<StoreVO> storeVOS = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext().getStoreVOList();
        List<Long> companyInfoIds = storeVOS.stream().map(StoreVO::getCompanyInfoId).collect(Collectors.toList());
        EmployeeListRequest employeeListRequest = new EmployeeListRequest();
        employeeListRequest.setCompanyInfoIds(companyInfoIds);
        employeeListRequest.setIsMasterAccount(NumberUtils.INTEGER_ONE);
        Map<Long, EmployeeListVO> employeeMap =
                Optional.ofNullable(employeeQueryProvider.list(employeeListRequest).getContext())
                        .map(EmployeeListResponse::getEmployeeList)
                        .orElse(Collections.emptyList())
                        .stream()
                        .collect(Collectors.toMap(EmployeeListVO::getCompanyInfoId, Function.identity()));
        storeVOS.forEach(item -> {
            EmployeeListVO employeeVO = employeeMap.get(item.getCompanyInfoId());
            if (Objects.nonNull(employeeVO)) {
                item.setAccountName(employeeVO.getAccountName());
            }
        });

        List<String> storeNames = new ArrayList<>();
        List<String> storeIdList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(storeVOS)){
            for(StoreVO storeVO :storeVOS){
                storeIdList.add(storeVO.getStoreId().toString());
                storeNames.add(storeVO.getStoreName());
            }
        }
        couponInfoResponse.getCouponInfo().setScopeNames(storeNames);
        couponInfoResponse.getCouponInfo().setScopeIds(storeIdList);
        ListStoreByIdsResponse listStoreByIdsResponse = new ListStoreByIdsResponse();
        listStoreByIdsResponse.setStoreVOList(storeVOS);
        couponInfoResponse.setStoresList(listStoreByIdsResponse);

    }

    /**
     * 促销范围Ids转List<CouponMarketingScope>
     *
     * @param couponInfo
     * @param scopeIds
     * @return
     */
    public List<CouponMarketingScope> generateCouponScopeList(CouponInfo couponInfo, List<String> scopeIds) {
        if (Objects.equals(ScopeType.BOSS_CATE, couponInfo.getScopeType()) || Objects.equals(ScopeType.STORE_CATE, couponInfo.getScopeType())) {
            Map<String, Integer> cateGrades = getCouponCateGrade(couponInfo, scopeIds);
            return generateCouponScopeListForCate(couponInfo, scopeIds, cateGrades);
        }
        return scopeIds.stream().map((scopeId) -> {
            CouponMarketingScope scope = new CouponMarketingScope();
            scope.setCouponId(couponInfo.getCouponId());
            scope.setScopeType(couponInfo.getScopeType());
            scope.setScopeId(scopeId);
            return scope;
        }).collect(Collectors.toList());
    }

    /**
     * 促销范围Ids转List如果为分类填充分类层级
     *
     * @param couponInfo
     * @param scopeIds
     * @param cateGrades
     * @return
     */
    public List<CouponMarketingScope> generateCouponScopeListForCate(CouponInfo couponInfo, List<String> scopeIds, Map<String, Integer> cateGrades) {
        return scopeIds.stream().map((scopeId) -> {
            CouponMarketingScope scope = new CouponMarketingScope();
            scope.setCouponId(couponInfo.getCouponId());
            scope.setScopeId(scopeId);
            scope.setScopeType(couponInfo.getScopeType());
            scope.setCateGrade(cateGrades.get(scopeId));
            return scope;
        }).collect(Collectors.toList());
    }

    /**
     * 优惠券分类Ids转List<CouponMarketingScope>
     *
     * @param couponInfo
     * @param cateIds
     * @return
     */
    public List<CouponCateRela> generateCouponCateList(CouponInfo couponInfo, List<String> cateIds) {
        return cateIds.stream().map((cateId) -> {
            CouponCateRela cateRela = new CouponCateRela();
            cateRela.setPlatformFlag(couponInfo.getPlatformFlag());
            cateRela.setCouponId(couponInfo.getCouponId());
            cateRela.setCateId(cateId);
            return cateRela;
        }).collect(Collectors.toList());
    }


    /**
     * 获取优惠券状态
     *
     * @param couponInfo
     * @return
     */
    public CouponStatus getCouponStatus(CouponInfo couponInfo) {
        if (Objects.equals(RangeDayType.DAYS, couponInfo.getRangeDayType())) {
            return CouponStatus.DAYS;
        } else {
            if (couponInfo.getStartTime() != null && couponInfo.getEndTime() != null) {
                if (LocalDateTime.now().isBefore(couponInfo.getStartTime())) {
                    return CouponStatus.NOT_START;
                } else if (LocalDateTime.now().isAfter(couponInfo.getEndTime())) {
                    return CouponStatus.ENDED;
                } else {
                    return CouponStatus.STARTED;
                }
            }
        }
        return null;
    }

    /**
     * 根据分类id获取分类层级
     *
     * @param couponInfo
     * @param scopeIds
     * @return
     */
    public Map<String, Integer> getCouponCateGrade(CouponInfo couponInfo, List<String> scopeIds) {
        Map<String, Integer> cateGrades = new HashMap<>();
        //处理分类层级
        if (Objects.equals(ScopeType.BOSS_CATE, couponInfo.getScopeType())) {
            List<Long> bossCateIds = scopeIds.stream().map(scope -> Long.valueOf(scope)).collect(Collectors.toList());
            List<GoodsCateVO> cateList = goodsCateQueryProvider.getByIds(new GoodsCateByIdsRequest(bossCateIds)).getContext().getGoodsCateVOList();
            cateList.forEach(goodsCate -> cateGrades.put(goodsCate.getCateId().toString(), goodsCate.getCateGrade()));

        }
        if (Objects.equals(ScopeType.STORE_CATE, couponInfo.getScopeType())) {
            //营销活动包含的所有商品Id
            List<Long> storeCateIds = scopeIds.stream().map(scope -> Long.valueOf(scope)).collect(Collectors.toList());
            //店铺分类
            if (Objects.equals(couponInfo.getPlatformFlag(), DefaultFlag.NO)) {
                List<StoreCateVO> storeCateList = storeCateQueryProvider.listByIds(new StoreCateListByIdsRequest(storeCateIds)).getContext().getStoreCateVOList();
                storeCateList.forEach(goodsCate -> cateGrades.put(goodsCate.getStoreCateId().toString(), goodsCate.getCateGrade()));
            }
        }
        return cateGrades;
    }

    /**
     * 根据优惠券id获取优惠券实体
     *
     * @param couponInfoId
     * @return
     */
    public CouponInfo getCouponInfoById(String couponInfoId) {
        CouponInfo couponInfo = couponInfoRepository.findById(couponInfoId).orElseThrow(() ->
                new SbcRuntimeException(getDeleteIndex(couponInfoId), MarketingErrorCodeEnum.K080046)
        );
        if (DeleteFlag.YES.equals(couponInfo.getDelFlag())) {
            throw new SbcRuntimeException(getDeleteIndex(couponInfoId), MarketingErrorCodeEnum.K080046);
        }
        return couponInfo;
    }

    public CouponInfo findByCouponIdAndStoreIdAndDelFlag(String couponInfoId, Long storeId) {
        return couponInfoRepository.findByCouponIdAndStoreIdAndDelFlag(couponInfoId, storeId, DeleteFlag.NO).orElse(null);
    }


    /**
     * 优惠券对象
     *
     * @return
     */
    private CouponInfo generateCoupon(CouponInfoAddRequest request) {
        CouponInfo couponInfo = new CouponInfo();
        couponInfo.setCouponName(request.getCouponName());
        couponInfo.setRangeDayType(request.getRangeDayType());
        if (request.getRangeDayType() == RangeDayType.RANGE_DAY) {
            couponInfo.setStartTime(request.getStartTime());
            couponInfo.setEndTime(request.getEndTime());
        } else {
            couponInfo.setEffectiveDays(request.getEffectiveDays());
        }
        couponInfo.setFullBuyType(request.getFullBuyType());
        couponInfo.setFullBuyPrice(request.getFullBuyPrice());
        couponInfo.setDenomination(Optional.ofNullable(request.getDenomination()).orElse(BigDecimal.ZERO));
        couponInfo.setPlatformFlag(request.getPlatformFlag());
        couponInfo.setStoreId(request.getStoreId());
        couponInfo.setScopeType(request.getScopeType());
        couponInfo.setParticipateType(request.getParticipateType());
        couponInfo.setCouponDesc(request.getCouponDesc());
        couponInfo.setCouponType(request.getCouponType());
        couponInfo.setCouponMarketingType(request.getCouponMarketingType());
        couponInfo.setCouponDiscountMode(Optional.ofNullable(request.getCouponDiscountMode()).orElse(CouponDiscountMode.REDUCTION));
        couponInfo.setMaxDiscountLimit(request.getMaxDiscountLimit());
        couponInfo.setCreatePerson(request.getCreatePerson());
        couponInfo.setCreateTime(LocalDateTime.now());
        couponInfo.setDelFlag(DeleteFlag.NO);
        return couponInfo;
    }

    /**
     * 优惠券对象
     *
     * @return
     */
    public CouponInfo generateCouponByModify(CouponInfoModifyRequest request, CouponInfo couponInfo) {
        couponInfo.setCouponName(request.getCouponName());
        couponInfo.setRangeDayType(request.getRangeDayType());
        if (request.getRangeDayType() == RangeDayType.RANGE_DAY) {
            couponInfo.setStartTime(request.getStartTime());
            couponInfo.setEndTime(request.getEndTime());
            couponInfo.setEffectiveDays(null);
        } else {
            couponInfo.setEffectiveDays(request.getEffectiveDays());
            couponInfo.setStartTime(null);
            couponInfo.setEndTime(null);
        }
        couponInfo.setFullBuyType(request.getFullBuyType());
        couponInfo.setFullBuyPrice(request.getFullBuyPrice());
        couponInfo.setDenomination(Optional.ofNullable(request.getDenomination()).orElse(BigDecimal.ZERO));
        couponInfo.setScopeType(request.getScopeType());
        couponInfo.setCouponDesc(request.getCouponDesc());
        couponInfo.setCouponMarketingType(request.getCouponMarketingType());
        couponInfo.setCouponDiscountMode(Optional.ofNullable(request.getCouponDiscountMode()).orElse(CouponDiscountMode.REDUCTION));
        couponInfo.setMaxDiscountLimit(request.getMaxDiscountLimit());
        couponInfo.setUpdatePerson(request.getUpdatePerson());
        couponInfo.setUpdateTime(LocalDateTime.now());
        return couponInfo;
    }

    public List<CouponInfo> queryByIds(List<String> couponIds) {
        return couponInfoRepository.queryByIds(couponIds);
    }

    /**
     * CouponInfo  包装成vo
     *
     * @param couponInfoList
     * @param couponCateRelaList
     * @param cateList
     * @param marketingScopeList
     * @return
     */
    private List<CouponInfoVO> copyToCouponInfoVoNew(List<CouponInfo> couponInfoList, List<CouponCateRela> couponCateRelaList, List<CouponCate> cateList, List<CouponMarketingScope> marketingScopeList) {
        Map<String, List<String>> scopeNamesMap = this.couponDetail(couponInfoList, marketingScopeList);
        List<CouponInfoVO> couponInfoVoList = couponInfoList.stream().map(
                couponInfo -> {
                    CouponInfoResponse couponInfoResponse = new CouponInfoResponse();
                    CouponInfoVO couponInfoVo = new CouponInfoVO();
                    KsBeanUtil.copyPropertiesThird(couponInfo, couponInfoVo);
                    couponInfoResponse.setCouponInfo(couponInfoVo);
                    //设置优惠券分类id
                    List<String> cateIds = couponCateRelaList.stream().filter(item -> item.getCouponId().equals(couponInfo.getCouponId()))
                            .map(item -> item.getCateId()).collect(Collectors.toList());
                    couponInfoResponse.getCouponInfo().setCateIds(cateIds);
                    //设置优惠券分类名称
                    List<String> nameList = cateIds.stream().map(
                            id -> {
                                String name = "";
                                for (CouponCate cate : cateList) {
                                    if (cate.getCouponCateId().equals(id)) {
                                        name = cate.getCouponCateName();
                                        return name;
                                    }
                                }
                                return name;
                            }
                    ).collect(Collectors.toList());
                    couponInfoResponse.getCouponInfo().setCateNames(nameList);
                    if (ScopeType.SKU != couponInfo.getScopeType()) {
////                        this.couponDetail(couponInfoResponse, couponInfo,couponMarketingScopeMap.get(couponInfo.getCouponId()));
//                        List<CouponMarketingScopeDTO> scopeList = couponMarketingScopeMap.get(couponInfo.getCouponId());
////                        if (CouponType.FREIGHT_VOUCHER == couponInfo.getCouponType()) {
//                           // return;
////                        }
//                        //品牌分类
//                        if (ScopeType.BRAND.equals(couponInfo.getScopeType())) {
//                            //营销活动包含的所有品牌Id
//                            List<Long> brandsIds = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors.toList());
//                            couponBrandDetail(couponInfoResponse, couponInfo, brandsIds);
//                        }
//                        //店铺分类
//                        if (ScopeType.STORE_CATE.equals(couponInfo.getScopeType())) {
//                            //营销活动包含的所有商品Id
//                            List<Long> cateIdsFromStore = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors.toList());
//                            couponStoreCateDetail(couponInfoResponse, couponInfo, cateIdsFromStore);
//                        }
//                        //平台分类
//                        if (ScopeType.BOSS_CATE.equals(couponInfo.getScopeType())) {
//                            //营销活动包含的所有商品Id
//                            List<Long> cateIdsFromBoss = scopeList.stream().map(scope -> Long.valueOf(scope.getScopeId())).collect(Collectors.toList());
//                            couponBossCateDetail(couponInfoResponse, couponInfo, cateIdsFromBoss);
//                        }
//                        //店铺可用
//                        if (ScopeType.SKU.equals(couponInfo.getScopeType())) {
//                            //营销活动包含的所有商品Id
//                            List<String> goodsInfoIds = scopeList.stream().map(CouponMarketingScopeDTO::getScopeId).collect(Collectors.toList());
//                            couponGoodsDetail(couponInfoResponse, couponInfo, goodsInfoIds);
//                        }

                        couponInfoVo.setScopeNames(scopeNamesMap.get(couponInfo.getCouponId()));//关联商品
                    }
                    couponInfoVo.setCateNames(nameList);//关联分类
                    couponInfoVo.setCouponStatus(getCouponStatus(couponInfo));//获取优惠券状态
                    return couponInfoVo;
                }).collect(Collectors.toList());
        return couponInfoVoList;
    }

    public Map<String, List<String>> couponDetail(List<CouponInfo> couponInfoList, List<CouponMarketingScope> marketingScopeList) {
        Map<String, List<CouponMarketingScopeDTO>> couponMarketingScopeMap = marketingScopeList.stream().map(m -> {
            CouponMarketingScopeDTO couponMarketingScopeDTO = new CouponMarketingScopeDTO();
            couponMarketingScopeDTO.setCouponId(m.getCouponId());
            couponMarketingScopeDTO.setScopeId(m.getScopeId());
            return couponMarketingScopeDTO;
        }).collect(Collectors.groupingBy(CouponMarketingScopeDTO::getCouponId));
        List<CouponInfoForScopeNamesDTO> dtoList = couponInfoList.stream().map(couponInfo -> {
            CouponInfoForScopeNamesDTO couponInfoForScopeNamesDTO = new CouponInfoForScopeNamesDTO();
            couponInfoForScopeNamesDTO.setCouponId(couponInfo.getCouponId());
            couponInfoForScopeNamesDTO.setCouponType(com.wanmi.sbc.goods.bean.enums.CouponType.fromValue(couponInfo.getCouponType().toValue()));
            couponInfoForScopeNamesDTO.setScopeType(com.wanmi.sbc.goods.bean.enums.ScopeType.fromValue(couponInfo.getScopeType().toValue()));
            couponInfoForScopeNamesDTO.setPlatformFlag(couponInfo.getPlatformFlag());
            couponInfoForScopeNamesDTO.setStoreId(couponInfo.getStoreId());
            return couponInfoForScopeNamesDTO;
        }).collect(Collectors.toList());

        List<CouponInfoForScopeNamesVO> couponInfoForScopeNamesVOS = goodsCateQueryProvider.couponDetail(new GoodsCateListCouponDetailRequest(dtoList, couponMarketingScopeMap)).getContext().getVoList();
        return couponInfoForScopeNamesVOS.stream().collect(Collectors.toMap(CouponInfoForScopeNamesVO::getCouponId, CouponInfoForScopeNamesVO::getScopeNames));

    }

    /**
     * 分页查询优惠券列表
     *
     * @param request
     * @return
     */
    public List<CouponInfoVO> page(CouponInfoListByPageRequest request) {

        List<String> couponIds = CollectionUtils.isNotEmpty(request.getCouponIds()) ? request.getCouponIds() : couponInfoRepository.listByPage(request.getPageRequest());

        List<CouponInfo> couponInfoList = couponInfoRepository.findAllById(couponIds);

        if (CollectionUtils.isEmpty(couponInfoList)) {
            return Lists.newArrayList();
        }

        List<CouponCateRela> couponCateRelaList = couponCateRelaRepository.findByCouponIdIn(couponIds);
        Map<String, List<CouponCateRela>> cateIdsMap = couponCateRelaList.stream().collect(Collectors.groupingBy(CouponCateRela::getCouponId));

        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponIdIn(couponIds);
        Map<String, List<CouponMarketingScope>> scopeIdsMap = scopeList.stream().collect(Collectors.groupingBy(CouponMarketingScope::getCouponId));

        List<CouponInfoVO> couponInfoVOS = couponInfoMapper.couponInfoToCouponInfoVO(couponInfoList);
        couponInfoVOS.stream().forEach(c -> {
            List<CouponCateRela> cateRelaList = cateIdsMap.get(c.getCouponId());
            if (CollectionUtils.isNotEmpty(cateRelaList)) {
                c.setCateIds(cateRelaList.stream().map(CouponCateRela::getCateId).collect(Collectors.toList()));
            }
            List<CouponMarketingScope> scopes = scopeIdsMap.get(c.getCouponId());
            if (CollectionUtils.isNotEmpty(scopes)) {
                c.setScopeIds(scopes.stream().map(CouponMarketingScope::getScopeId).collect(Collectors.toList()));
            }
        });
        return couponInfoVOS;
    }

    @Autowired
    private EsActivityCouponQueryProvider esActivityCouponQueryProvider;

    /**
     * @param request
     * @param storeId
     * @return com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoPageResponse
     * @description 魔方优惠券分页列表
     * @author EDZ
     * @date 2021/6/11 11:23
     **/
    public MagicCouponInfoPageResponse magicCouponInfoPage(MagicCouponInfoPageRequest request, Long storeId) {
        EsActivityCouponPageRequest esActivityCouponPageRequest = new EsActivityCouponPageRequest();
        esActivityCouponPageRequest.setPageSize(request.getPageSize());
        esActivityCouponPageRequest.setPageNum(request.getPageNum());
        esActivityCouponPageRequest.setCouponActivityIds(Lists.newArrayList(request.getActivityId()));
        esActivityCouponPageRequest.setLikeCouponName(request.getLikeCouponName());
        esActivityCouponPageRequest.setCouponMarketingType(request.getCouponMarketingType());
        if (Objects.nonNull(request.getScopeType())){
            esActivityCouponPageRequest.setScopeType(request.getScopeType());
            esActivityCouponPageRequest.setStoreIds(Lists.newArrayList(storeId));
        }
        MicroServicePage<EsActivityCouponVO> activityCouponPage = esActivityCouponQueryProvider.page(esActivityCouponPageRequest).getContext().getActivityCouponPage();
        List<EsActivityCouponVO> couponInfos = activityCouponPage.getContent();
        if (CollectionUtils.isEmpty(couponInfos)){
            return MagicCouponInfoPageResponse.builder().couponInfos(new MicroServicePage<>(Collections.emptyList(),request.getPageable(),0)).build();
        }
        //        Optional<CouponActivity> optional = couponActivityRepository.findById(activityId);
//        boolean validation =
//                !optional.isPresent()
//                        || DeleteFlag.YES.equals(optional.get().getDelFlag())
//                        || DefaultFlag.YES.equals(optional.get().getPauseFlag())
//                        || optional.get().getEndTime().compareTo(LocalDateTime.now()) < 0
//                        || (storeId != null && !storeId.equals(optional.get().getStoreId()))
//                        || !CouponActivityType.ALL_COUPONS.equals(optional.get().getCouponActivityType());
//        if (validation) {
//            // 活动不存在/活动删除/活动暂停/活动过期/没有权限访问/不是全场赠券活动
//            return MagicCouponInfoPageResponse.builder()
//                    .couponInfos(new MicroServicePage<>(Collections.emptyList()))
//                    .build();
//        }
//        Integer scopeType =
//                request.getScopeType() == null ? -1 : request.getScopeType().toValue();
//        String likeCouponName =
//                StringUtils.isBlank(request.getLikeCouponName())
//
//        Page<CouponInfo> couponInfoPage = couponInfoRepository.findByActivityCouponPage(activityId, scopeType, likeCouponName, request.getPageRequest());
//        List<CouponInfo> couponInfos = couponInfoPage.getContent();
//        if (CollectionUtils.isEmpty(couponInfos)) {
//            return MagicCouponInfoPageResponse.builder().couponInfos(new MicroServicePage<>(Collections.emptyList())).build();
//        }


        // 查询优惠券使用范围
        List<String> couponIds = couponInfos.stream().map(EsActivityCouponVO::getCouponId).collect(Collectors.toList());
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponIdIn(couponIds);
        Map<String, List<String>> scopeNamesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(scopeList)) {
            Set<String> scopeSet = scopeList.stream().map(CouponMarketingScope::getCouponId).collect(Collectors.toSet());
            List<EsActivityCouponVO> filtedCouponInfo =
                    couponInfos.stream()
                            .filter(couponInfo -> scopeSet.contains(couponInfo.getCouponId()))
                            .collect(Collectors.toList());
            scopeNamesMap = this.couponDetail(KsBeanUtil.convert(filtedCouponInfo,CouponInfo.class), scopeList);
        }

//        List<CouponActivityConfig> couponActivityConfigs =
//                couponActivityConfigRepository.findByActivityId(activityId);
//        Map<String, Long> configMap = new HashMap<>();
//        for (CouponActivityConfig config : couponActivityConfigs) {
//            configMap.put(config.getCouponId(), config.getTotalCount());
//        }

        List<MagicCouponInfoVO> magicCouponInfoVOS = new ArrayList<>();
        for (EsActivityCouponVO couponInfo : couponInfos) {
            String couponId = couponInfo.getCouponId();
            MagicCouponInfoVO vo = new MagicCouponInfoVO();
            KsBeanUtil.copyProperties(couponInfo, vo);
//            vo.setTotalCount(configMap.get(couponId));
            if (scopeNamesMap.get(couponId) != null) {
                vo.setScopeNames(scopeNamesMap.get(couponId));
            }
            magicCouponInfoVOS.add(vo);
        }
        MicroServicePage<MagicCouponInfoVO> page = new MicroServicePage<>(magicCouponInfoVOS, request.getPageable(), activityCouponPage.getTotal());
        return MagicCouponInfoPageResponse.builder().couponInfos(page).build();
    }

    /**
     * @param requestList
     * @param customerId
     * @return java.util.List<com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoResponse>
     * @description h5/app优惠券魔方页面，优惠券数据状态查询
     * @author EDZ
     * @date 2021/6/11 11:22
     **/
    public List<MagicCouponInfoResponse> magicCouponInfoStatus(List<MagicCouponInfoRequest> requestList, String customerId) {
        Map<String, String> couponToActivity = new HashMap<>();
        for (MagicCouponInfoRequest request : requestList) {
            couponToActivity.put(request.getCouponId(), request.getActivityId());
        }
        List<String> activityIds = requestList.stream().map(MagicCouponInfoRequest::getActivityId).distinct().collect(Collectors.toList());
        List<String> couponIds = requestList.stream().map(MagicCouponInfoRequest::getCouponId).distinct().collect(Collectors.toList());
        List<Long> storeIds = requestList.stream().map(MagicCouponInfoRequest::getStoreId).distinct().collect(Collectors.toList());
        Map<String, Integer> sortMap = new HashMap<>();
        int sort = 0;
        for (String couponId : couponIds) {
            sortMap.put(couponId, sort);
            sort++;
        }
        List<CouponInfo> couponInfos = couponInfoRepository.findMagicCoupons(activityIds, couponIds);

        //筛选出门店优惠券id
        List<String> o2oCouponIds = couponInfos.stream().filter(couponInfo ->
                couponInfo.getCouponType() == CouponType.BOSS_STOREFRONT_VOUCHER ||
                        couponInfo.getCouponType() == CouponType.STOREFRONT_VOUCHER).map(CouponInfo::getCouponId).collect(Collectors.toList());

        //筛选出商家优惠券
        couponInfos = couponInfos.stream().filter(couponInfo ->
                couponInfo.getCouponType() == CouponType.GENERAL_VOUCHERS ||
                        couponInfo.getCouponType() == CouponType.STORE_VOUCHERS).collect(Collectors.toList());

        //只有o2o模式下才不为空
        if (CollectionUtils.isNotEmpty(storeIds)) {
            Long storeId = storeIds.get(0);
            if (storeId != null) {
                List<CouponInfo> magicO2oCoupons = couponInfoService.checkO2oCoupons(activityIds, o2oCouponIds, storeId);
                couponInfos.addAll(magicO2oCoupons);
            }
        }
        if (CollectionUtils.isEmpty(couponInfos)) {
            return new ArrayList<>();
        }
        couponIds = couponInfos.stream().map(CouponInfo::getCouponId).distinct().collect(Collectors.toList());
        // 查询查询优惠券使用范围
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponIdIn(couponIds);
        Map<String, List<String>> scopeNamesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(scopeList)) {
            Set<String> scopeSet = scopeList.stream().map(CouponMarketingScope::getCouponId).collect(Collectors.toSet());
            List<CouponInfo> filtedCouponInfo =
                    couponInfos.stream()
                            .filter(couponInfo -> scopeSet.contains(couponInfo.getCouponId()))
                            .collect(Collectors.toList());
            scopeNamesMap = this.couponDetail(filtedCouponInfo, scopeList);
        }
        // 查询券与活动的绑定关系
        List<CouponActivityConfig> couponActivityConfigs =
                couponActivityConfigRepository.findByActivityIdInAndCouponIdIn(
                        activityIds, couponIds);
        Map<String, CouponActivityConfig> configMap = new HashMap<>();
        for (CouponActivityConfig config : couponActivityConfigs) {
            if (config.getActivityId().equals(couponToActivity.get(config.getCouponId()))) {
                configMap.putIfAbsent(config.getActivityId() + "_" + config.getCouponId(), config);
            }
        }
        couponInfos = couponInfos.stream().filter(couponInfo -> {
            String couponId = couponInfo.getCouponId();
            String activityId = couponToActivity.get(couponId);
            return configMap.containsKey(activityId + "_" + couponId);
        }).collect(Collectors.toList());
        // 查询用户领取的券
        Map<String, List<CouponCode>> couponCodeMap = null;
        if (StringUtils.isNotBlank(customerId)) {
            CouponCodeQueryRequest couponCodeQueryRequest = new CouponCodeQueryRequest();
            couponCodeQueryRequest.setActivityIds(activityIds);
            couponCodeQueryRequest.setCouponIds(couponIds);
            couponCodeQueryRequest.setCustomerId(customerId);
            couponCodeQueryRequest.setDelFlag(DeleteFlag.NO);
            List<CouponCode> couponCodeList = couponCodeService.listCouponCodeByCondition(couponCodeQueryRequest);
            if (CollectionUtils.isNotEmpty(couponCodeList)) {
                couponCodeMap = couponCodeList.stream().collect(
                        Collectors.groupingBy(couponCode -> couponCode.getActivityId() + "_" + couponCode.getCouponId()));
            }
        }
        //活动信息
        List<CouponActivity> couponActivities = couponActivityRepository.findAllById(activityIds);
        Map<String, CouponActivity> activityMap = new HashMap<>();
        for (CouponActivity couponActivity : couponActivities) {
            activityMap.putIfAbsent(couponActivity.getActivityId(), couponActivity);
        }

        List<MagicCouponInfoResponse> responseList = new ArrayList<>();
        for (CouponInfo couponInfo : couponInfos) {
            String couponId = couponInfo.getCouponId();
            String activityId = couponToActivity.get(couponId);
            MagicCouponInfoResponse response = new MagicCouponInfoResponse();
            KsBeanUtil.copyProperties(couponInfo, response);
            response.setScopeNames(scopeNamesMap.get(couponId));
            CouponActivity couponActivity = activityMap.get(activityId);
            CouponActivityConfig config = configMap.get(activityId + "_" + couponId);
            DefaultFlag hasLeft = config.getHasLeft();
            // 统计活动优惠券总数
            long totalCount = config.getTotalCount();
            long leftCount = couponCodeService.getCouponLeftCount(activityId, couponId, totalCount, hasLeft);
            // 统计活动已领取优惠券
            long takeCount = totalCount - leftCount;
            response.setTotalCount(totalCount);
            response.setTakeCount(takeCount);
            if (StringUtils.isNotBlank(customerId)) {
                // 登录状态
                List<CouponCode> couponCodes = null;
                if (couponCodeMap != null) {
                    couponCodes = couponCodeMap.get(activityId + "_" + couponId);
                }
                // 用户是否有领取但未使用的优惠券（true：有；false：没有）
                boolean haveNoUse = false;
                // 用户是否有领取但未生效的优惠券（true：有；false：没有）
                boolean haveNoEffective = false;
                if (CollectionUtils.isNotEmpty(couponCodes)) {
                    haveNoUse = couponCodes.stream()
                            .anyMatch(couponCode -> couponCode.getUseStatus().equals(DefaultFlag.NO));
                    if (haveNoUse) {
                        haveNoEffective = couponCodes.stream()
                                .anyMatch(couponCode -> couponCode.getUseStatus().equals(DefaultFlag.NO)
                                        && couponCode.getStartTime().compareTo(LocalDateTime.now()) > 0);
                    }
                }
                if (couponActivity.getStartTime().isAfter(LocalDateTime.now())) {
                    response.setCouponStatus(MagicCouponStatus.NOT_START);
                } else if (couponActivity.getEndTime().isBefore(LocalDateTime.now())) {
                    response.setCouponStatus(MagicCouponStatus.ALREADY_END);
                } else if (leftCount == 0) {
                    // 3已抢光（券的上限已达到）
                    response.setCouponStatus(MagicCouponStatus.EMPTY);
                } else {
                    if (haveNoUse) {
                        response.setCouponStatus(
                                haveNoEffective
                                        // 1查看可用（已领取但券还未生效）
                                        ? MagicCouponStatus.NOT_YET_EFFECTIVE
                                        // 2立即使用（已领取且券已生效）
                                        : MagicCouponStatus.USE);
                    } else {
                        // 这里不用考虑receive_count每人领取上限
                        // 如果达到领取上限，展示为可领取，点击领取时提示：您已到达领取上限
                        // 0可领取（当前账号未领取）
                        response.setCouponStatus(MagicCouponStatus.AVAILABLE);
                    }
                }
            } else {
                //未登录状态
                if (couponActivity.getStartTime().isAfter(LocalDateTime.now())) {
                    response.setCouponStatus(MagicCouponStatus.NOT_START);
                } else if (couponActivity.getEndTime().isBefore(LocalDateTime.now())) {
                    response.setCouponStatus(MagicCouponStatus.ALREADY_END);
                } else {
                    response.setCouponStatus(
                            leftCount == 0
                                    // 3已抢光（券的上限已达到）
                                    ? MagicCouponStatus.EMPTY
                                    // 0可领取（访问用户未登录时，返回可领取，点击领取跳转登录页面）
                                    : MagicCouponStatus.AVAILABLE);
                }
            }
            //是否即将过期
            boolean couponWillEnd = false;
            if (couponInfo.getRangeDayType() == RangeDayType.RANGE_DAY) {
                LocalDateTime endTime = couponInfo.getEndTime();
                //如果结束时间加上5天，大于现在时间，即将过期
                if (LocalDateTime.now().plusDays(5).isAfter(endTime)) {
                    couponWillEnd = true;
                }
            }
            response.setCouponWillEnd(couponWillEnd);
            response.setActivityId(activityId);
            response.setSort(sortMap.get(couponId));
            responseList.add(response);
        }
        responseList.sort(Comparator.comparingInt(MagicCouponInfoResponse::getSort));
        return responseList;
    }

    public List<CouponInfo> checkO2oCoupons(List<String> activityIds, List<String> couponIds, Long storeId) {
        return null;
    }

    /**
     * 查询优惠券详情
     *
     * @param magicCouponInfoRequest
     * @param customerId
     * @return
     */
    public MagicCouponInfoResponse queryCouponInfo(MagicCouponInfoRequest magicCouponInfoRequest, String customerId) {
        String requestCouponId = magicCouponInfoRequest.getCouponId();
        //活动信息
        CouponActivity couponActivity = couponActivityRepository.findById(magicCouponInfoRequest.getActivityId()).orElse(null);
        if (Objects.isNull(couponActivity)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080190);
        }
        // 查询券与活动的绑定关系
        CouponActivityConfig couponActivityConfig =
                couponActivityConfigRepository.findByActivityIdAndCouponId(magicCouponInfoRequest.getActivityId(), requestCouponId);
        if (Objects.isNull(couponActivityConfig)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080190);
        }
        MagicCouponInfoResponse response = new MagicCouponInfoResponse();
        Map<String, String> couponToActivity = new HashMap<>();
        couponToActivity.put(requestCouponId, magicCouponInfoRequest.getActivityId());
        CouponCode myCouponCode = null;
        CouponInfo couponInfo = null;
        //false:为领券中心（未领券）入口， true:为个人优惠券列表 or 领券中心（已领券） 入口
        boolean sourceFlag = false;
        if(StringUtils.isNotBlank(customerId) && StringUtils.isNotBlank(magicCouponInfoRequest.getCouponCodeId())) {
            sourceFlag = true;
        }

        if(sourceFlag) {
            CouponCodeQueryRequest couponCodeQueryRequest = new CouponCodeQueryRequest();
            couponCodeQueryRequest.setActivityId(magicCouponInfoRequest.getActivityId());
            couponCodeQueryRequest.setCouponId(requestCouponId);
            couponCodeQueryRequest.setCouponCodeIds(Collections.singletonList(magicCouponInfoRequest.getCouponCodeId()));
            couponCodeQueryRequest.setCustomerId(customerId);
            couponCodeQueryRequest.setDelFlag(DeleteFlag.NO);
            List<CouponCode> couponCodes = couponCodeService.listCouponCodeByCondition(couponCodeQueryRequest);
            if(CollectionUtils.isEmpty(couponCodes)) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080190);
            }
            myCouponCode = couponCodes.get(0);
            response.setStartTime(myCouponCode.getStartTime());
            response.setEndTime(myCouponCode.getEndTime());
            couponInfo = couponInfoRepository.findById(requestCouponId).orElse(null);
        } else {
            couponInfo = couponInfoRepository.findCouponsByActivityId(magicCouponInfoRequest.getActivityId(), requestCouponId);
        }
        if (Objects.isNull(couponInfo)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080190);
        }
        // 查询查询优惠券使用范围
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponId(requestCouponId);
        Map<String, List<String>> scopeNamesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(scopeList)) {
            List<CouponInfo> filtedCouponInfo = Arrays.asList(couponInfo);
            scopeNamesMap = this.couponDetail(filtedCouponInfo, scopeList);
        }

        // 查询用户领取的券
        List<CouponCode> couponCodes = null;
        //领券中心过来的
        if (!sourceFlag) {
            CouponCodeQueryRequest couponCodeQueryRequest = new CouponCodeQueryRequest();
            couponCodeQueryRequest.setActivityId(magicCouponInfoRequest.getActivityId());
            couponCodeQueryRequest.setCouponId(requestCouponId);
            couponCodeQueryRequest.setCustomerId(customerId);
            couponCodeQueryRequest.setDelFlag(DeleteFlag.NO);
            //未领券过来的必须排除过期的
            couponCodeQueryRequest.setNotExpire(Boolean.TRUE);
            couponCodes = couponCodeService.listCouponCodeByCondition(couponCodeQueryRequest);
        }
        else {
            couponCodes = Collections.singletonList(myCouponCode);
        }

        String couponId = couponInfo.getCouponId();
        String activityId = couponToActivity.get(couponId);

        KsBeanUtil.copyProperties(couponInfo, response);
        response.setScopeNames(scopeNamesMap.get(couponId));
        DefaultFlag hasLeft = couponActivityConfig.getHasLeft();
        // 统计活动优惠券总数
        long totalCount = couponActivityConfig.getTotalCount();
        long leftCount = couponCodeService.getCouponLeftCount(activityId, couponId, totalCount, hasLeft);
        response.setLeftFlag(Boolean.FALSE);
        if (leftCount > 0) {
            response.setLeftFlag(Boolean.TRUE);
        }
        // 统计活动已领取优惠券
        long takeCount = totalCount - leftCount;
        response.setTotalCount(totalCount);
        response.setTakeCount(takeCount);
        response.setHasFetched(Boolean.FALSE);
        if (StringUtils.isNotBlank(customerId)) { // 登录状态
            // 用户是否有领取但未使用的优惠券（true：有；false：没有）
            boolean haveNoUse = false;
            // 用户是否有领取但未生效的优惠券（true：有；false：没有）
            boolean haveNoEffective = false;
            if (CollectionUtils.isNotEmpty(couponCodes)) {
                response.setHasFetched(Boolean.TRUE);
                haveNoUse = couponCodes.stream()
                        .anyMatch(couponCode -> couponCode.getUseStatus().equals(DefaultFlag.NO));
                if (haveNoUse) {
                    haveNoEffective = couponCodes.stream()
                            .anyMatch(couponCode -> couponCode.getUseStatus().equals(DefaultFlag.NO)
                                    && couponCode.getStartTime().compareTo(LocalDateTime.now()) > 0);
                }
            }
            if (!CouponActivityType.DISTRIBUTE_COUPON.equals(couponActivity.getCouponActivityType())
                    && !CouponActivityType.RIGHTS_COUPON.equals(couponActivity.getCouponActivityType())
                    && couponActivity.getStartTime().isAfter(LocalDateTime.now())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080050);
            } else if (couponActivity.getEndTime().isBefore(LocalDateTime.now())) {
                // 活动已结束
                response.setCouponStatus(MagicCouponStatus.ALREADY_END);
            } else { //验证用户是否可以领取 如果用户已领取但是未使用 优先展示使用状态
                if (haveNoUse) {
                    response.setCouponStatus(
                            haveNoEffective
                                    // 1查看可用（已领取但券还未生效）
                                    ? MagicCouponStatus.NOT_YET_EFFECTIVE
                                    // 2立即使用（已领取且券已生效）
                                    : MagicCouponStatus.USE);
                } else {
                    if (leftCount == 0) {
                        // 3已抢光（券的上限已达到）
                        response.setCouponStatus(MagicCouponStatus.EMPTY);
                    } else {
                        // 这里不用考虑receive_count每人领取上限
                        // 如果达到领取上限，展示为可领取，点击领取时提示：您已到达领取上限
                        // 0可领取（当前账号未领取）
                        response.setCouponStatus(MagicCouponStatus.AVAILABLE);
                    }
                }
            }
        } else {
            //未登录状态
            if (couponActivity.getStartTime().isAfter(LocalDateTime.now())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080050);
            } else if (couponActivity.getEndTime().isBefore(LocalDateTime.now())) {
                // 活动已结束
                response.setCouponStatus(MagicCouponStatus.ALREADY_END);
            } else {
                response.setCouponStatus(
                        leftCount == 0
                                // 3已抢光（券的上限已达到）
                                ? MagicCouponStatus.EMPTY
                                // 0可领取（访问用户未登录时，返回可领取，点击领取跳转登录页面）
                                : MagicCouponStatus.AVAILABLE);
            }
        }
        response.setActivityId(activityId);
        if (Objects.nonNull(couponInfo.getStoreId())) {
            StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(couponInfo.getStoreId()).build()).getContext().getStoreVO();
            if (Objects.nonNull(storeVO)) {
                response.setStoreName(storeVO.getStoreName());
            }
        }

        boolean activityWillEnd = false;
        Long activityCountDown = 0L;
        //优惠券活动是否显示倒计时
        if (!CouponActivityType.DISTRIBUTE_COUPON.equals(couponActivity.getCouponActivityType())
                && !CouponActivityType.RIGHTS_COUPON.equals(couponActivity.getCouponActivityType())
                && couponActivity.getEndTime().isAfter(LocalDateTime.now()) && couponActivity.getEndTime().isBefore(LocalDateTime.now().plusDays(1))) {
            activityWillEnd = true;
            activityCountDown = Duration.between(LocalDateTime.now(), couponActivity.getEndTime()).toMillis();
        }
        //是否即将过期
        boolean couponWillEnd = false;
        if (couponInfo.getRangeDayType() == RangeDayType.RANGE_DAY) {
            LocalDateTime endTime = couponInfo.getEndTime();
            //如果结束时间加上5天，大于现在时间，即将过期
            if (LocalDateTime.now().plusDays(Constants.FIVE).isAfter(endTime)) {
                couponWillEnd = true;
            }
        }
        response.setCouponWillEnd(couponWillEnd);

        //优惠券是否已经开始 - 建立在已经领券的基础上，并且券是未过期的
        Boolean couponStarted = null;
        if (response.isHasFetched()) {
            if (couponInfo.getRangeDayType() == RangeDayType.DAYS || LocalDateTime.now().isAfter(couponInfo.getStartTime())) {
                couponStarted = Boolean.TRUE;
            } else {
                couponStarted = Boolean.FALSE;
            }
        }

        BigDecimal fetchPercent = BigDecimal.ZERO;
        //计算已抢百分比
        if (takeCount > 0) {
            fetchPercent = new BigDecimal(takeCount).divide(new BigDecimal(totalCount), 2, RoundingMode.FLOOR);
        }
        //优惠券活动类型
        response.setActivityCouponType(couponActivity.getCouponActivityType());
        response.setFetchPercent(fetchPercent);

        response.setCouponStarted(couponStarted);
        response.setActivityWillEnd(activityWillEnd);
        response.setActivityCountDown(activityCountDown);
        return response;
    }

    /**
     * 查询优惠券详情
     *
     * @param magicCouponInfoRequest
     * @return
     */
    public MagicCouponInfoResponse queryCouponInfoById(CouponInfoByIdRequest magicCouponInfoRequest) {
        CouponInfo couponInfo = getCouponInfoById(magicCouponInfoRequest.getCouponId());
        if (Objects.isNull(couponInfo)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080190);
        }

        // 查询查询优惠券使用范围
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponId(magicCouponInfoRequest.getCouponId());
        Map<String, List<String>> scopeNamesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(scopeList)) {
            List<CouponInfo> filtedCouponInfo = Arrays.asList(couponInfo);
            scopeNamesMap = this.couponDetail(filtedCouponInfo, scopeList);
        }

        String couponId = couponInfo.getCouponId();
        MagicCouponInfoResponse response = new MagicCouponInfoResponse();
        KsBeanUtil.copyProperties(couponInfo, response);
        response.setScopeNames(scopeNamesMap.get(couponId));
        if (Objects.nonNull(couponInfo.getStoreId())) {
            StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(couponInfo.getStoreId()).build()).getContext().getStoreVO();
            if (Objects.nonNull(storeVO)) {
                response.setStoreName(storeVO.getStoreName());
            }
        }

        //优惠券是否已经开始 - 建立在已经领券的基础上，并且券是未过期的
        Boolean couponStarted = null;
        if (response.isHasFetched()) {
            if (couponInfo.getRangeDayType() == RangeDayType.DAYS || LocalDateTime.now().isAfter(couponInfo.getStartTime())) {
                couponStarted = Boolean.TRUE;
            } else {
                couponStarted = Boolean.FALSE;
            }
        }

        response.setCouponStarted(couponStarted);
        return response;
    }

    public List<MagicCouponInfoVO> getMagicCouponInfoVOS(List<CouponInfo> couponInfos) {
        // 获得所有的优惠券id
        List<String> couponIds = couponInfos.stream().map(CouponInfo::getCouponId).collect(Collectors.toList());
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponIdIn(couponIds);
        Map<String, List<String>> scopeNamesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(scopeList)) {
            Set<String> scopeSet = scopeList.stream().map(CouponMarketingScope::getCouponId).collect(Collectors.toSet());
            List<CouponInfo> filtedCouponInfo =
                    couponInfos.stream()
                            .filter(couponInfo -> scopeSet.contains(couponInfo.getCouponId()))
                            .collect(Collectors.toList());
            scopeNamesMap = this.couponDetail(filtedCouponInfo, scopeList);
        }

        List<MagicCouponInfoVO> magicCouponInfoVOS = new ArrayList<>();
        for (CouponInfo couponInfo : couponInfos) {
            String couponId = couponInfo.getCouponId();
            MagicCouponInfoVO vo = new MagicCouponInfoVO();
            KsBeanUtil.copyProperties(couponInfo, vo);
            if (scopeNamesMap.get(couponId) != null) {
                vo.setScopeNames(scopeNamesMap.get(couponId));
            }
            magicCouponInfoVOS.add(vo);
        }
        return magicCouponInfoVOS;
    }

    /**
     * 拼凑删除es-提供给findOne去调
     *
     * @param id 编号
     * @return "{index}:{id}"
     */
    private Object getDeleteIndex(String id) {
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.DOC_COUPON_INFO_TYPE, id);
    }

    /**
     * @param request
     * @return com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoPageResponse
     * @description 魔方新人优惠券分页列表
     * @author EDZ
     * @date 2021/6/11 11:23
     **/
    public MagicCouponInfoPageResponse magicNewcomerCouponInfoPage(MagicCouponInfoPageRequest request) {
        List<NewcomerPurchaseCoupon> newcomerPurchaseCoupons = newcomerPurchaseCouponRepository.getCoupons();
        if (CollectionUtils.isEmpty(newcomerPurchaseCoupons)) {
            return MagicCouponInfoPageResponse.builder().couponInfos(new MicroServicePage<>()).build();
        }
        Map<String, Long> couponTotalMap = newcomerPurchaseCoupons.stream().collect(Collectors.toMap(NewcomerPurchaseCoupon::getCouponId, NewcomerPurchaseCoupon::getActivityStock));
        List<String> couponsIds = Lists.newArrayList(couponTotalMap.keySet());

        CouponInfoQueryRequest couponInfoQueryRequest = new CouponInfoQueryRequest();
        couponInfoQueryRequest.setPageNum(request.getPageNum());
        couponInfoQueryRequest.setPageSize(request.getPageSize());
        couponInfoQueryRequest.setCouponIds(couponsIds);
        couponInfoQueryRequest.setLikeCouponName(request.getLikeCouponName());
        couponInfoQueryRequest.setScopeType(request.getScopeType());
        //分页查询SKU信息列表
        Page<CouponInfo> couponInfos = couponInfoRepository.findAll(this.getWhereCriteria(couponInfoQueryRequest), couponInfoQueryRequest.getPageRequest());

        // 查询优惠券使用范围
        List<String> couponIds = couponInfos.stream().map(CouponInfo::getCouponId).collect(Collectors.toList());
        List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponIdIn(couponIds);
        Map<String, List<String>> scopeNamesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(scopeList)) {
            Set<String> scopeSet = scopeList.stream().map(CouponMarketingScope::getCouponId).collect(Collectors.toSet());
            List<CouponInfo> filtedCouponInfo =
                    couponInfos.stream()
                            .filter(couponInfo -> scopeSet.contains(couponInfo.getCouponId()))
                            .collect(Collectors.toList());
            scopeNamesMap = this.couponDetail(KsBeanUtil.convert(filtedCouponInfo,CouponInfo.class), scopeList);
        }

        List<MagicCouponInfoVO> magicCouponInfoVOS = new ArrayList<>();
        for (CouponInfo couponInfo : couponInfos) {
            String couponId = couponInfo.getCouponId();
            MagicCouponInfoVO vo = new MagicCouponInfoVO();
            KsBeanUtil.copyProperties(couponInfo, vo);
            if (scopeNamesMap.get(couponId) != null) {
                vo.setScopeNames(scopeNamesMap.get(couponId));
            }

            Long total = couponTotalMap.get(couponId);
            if (total != null) {
                vo.setTotalCount(total);
            }
            magicCouponInfoVOS.add(vo);
        }
        MicroServicePage<MagicCouponInfoVO> page = new MicroServicePage<>(magicCouponInfoVOS, couponInfos.getPageable(), couponInfos.getTotalElements());
        return MagicCouponInfoPageResponse.builder().couponInfos(page).build();
    }
}
