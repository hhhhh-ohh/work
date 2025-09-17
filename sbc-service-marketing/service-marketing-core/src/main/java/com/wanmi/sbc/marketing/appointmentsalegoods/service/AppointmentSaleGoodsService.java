package com.wanmi.sbc.marketing.appointmentsalegoods.service;

import com.wanmi.sbc.common.enums.AppointmentStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.JpaUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateGoodsRelaQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsCountByConditionRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateGoodsRelaCountRequest;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleQueryRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsQueryRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsValidateRequest;
import com.wanmi.sbc.marketing.appointmentsale.model.root.AppointmentSale;
import com.wanmi.sbc.marketing.appointmentsale.service.AppointmentSaleService;
import com.wanmi.sbc.marketing.appointmentsalegoods.model.root.AppointmentSaleGoods;
import com.wanmi.sbc.marketing.appointmentsalegoods.repository.AppointmentSaleGoodsRepository;
import com.wanmi.sbc.marketing.bean.dto.AppointmentGoodsInfoSimplePageDTO;
import com.wanmi.sbc.marketing.bean.dto.AppointmentSaleGoodsDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.AppointmentVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>预约抢购业务逻辑</p>
 *
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Slf4j
@Service("AppointmentSaleGoodsService")
public class AppointmentSaleGoodsService {
    @Autowired
    private AppointmentSaleGoodsRepository appointmentSaleGoodsRepository;

    @Autowired private AppointmentSaleService appointmentSaleService;

    @Autowired
    private EntityManager entityManager;

    @Autowired private GoodsQueryProvider goodsQueryProvider;

    @Autowired private StoreCateGoodsRelaQueryProvider storeCateGoodsRelaQueryProvider;

    /**
     * 新增预约抢购
     *
     * @author zxd
     */
    @Transactional
    public AppointmentSaleGoods add(AppointmentSaleGoods entity) {
        appointmentSaleGoodsRepository.save(entity);
        return entity;
    }

    /**
     * 修改预约抢购
     *
     * @author zxd
     */
    @Transactional
    public AppointmentSaleGoods modify(AppointmentSaleGoods entity) {
        appointmentSaleGoodsRepository.save(entity);
        return entity;
    }

    /**
     * 单个删除预约抢购
     *
     * @author zxd
     */
    @Transactional
    public void deleteById(Long id) {
        appointmentSaleGoodsRepository.deleteById(id);
    }

    /**
     * 批量删除预约抢购
     *
     * @author zxd
     */
    @Transactional
    public void deleteByIdList(List<Long> ids) {
        appointmentSaleGoodsRepository.deleteByIdIn(ids);
    }

    /**
     * 单个查询预约抢购
     *
     * @author zxd
     */
    public AppointmentSaleGoods getOne(Long id, Long storeId) {
        return appointmentSaleGoodsRepository.findByIdAndStoreId(id, storeId)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "预约抢购不存在"));
    }

    /**
     * 分页查询预约抢购
     *
     * @author zxd
     */
    public Page<AppointmentSaleGoods> page(AppointmentSaleGoodsQueryRequest queryReq) {
        return appointmentSaleGoodsRepository.findAll(
                AppointmentSaleGoodsWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询预约抢购
     *
     * @author zxd
     */
    public List<AppointmentSaleGoods> list(AppointmentSaleGoodsQueryRequest queryReq) {
        return appointmentSaleGoodsRepository.findAll(AppointmentSaleGoodsWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author zxd
     */
    public AppointmentSaleGoodsVO wrapperVo(AppointmentSaleGoods appointmentSaleGoods) {
        if (appointmentSaleGoods != null) {
            AppointmentSaleGoodsVO appointmentSaleGoodsVO = KsBeanUtil.convert(appointmentSaleGoods, AppointmentSaleGoodsVO.class);
            return appointmentSaleGoodsVO;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateAppointmentCount(AppointmentSaleGoodsDTO appointmentSaleGoodsDTO) {
        return appointmentSaleGoodsRepository.updateAppointmentCount(appointmentSaleGoodsDTO.getAppointmentSaleId(), appointmentSaleGoodsDTO.getGoodsInfoId());
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateBuyCount(AppointmentSaleGoodsDTO appointmentSaleGoodsDTO) {
        return appointmentSaleGoodsRepository.updateBuyCount(appointmentSaleGoodsDTO.getAppointmentSaleId(), appointmentSaleGoodsDTO.getGoodsInfoId(), appointmentSaleGoodsDTO.getStock());
    }

    public Page<AppointmentSaleGoods> build(AppointmentGoodsInfoSimplePageDTO queryRequest) {
        return appointmentSaleGoodsRepository.findAll((root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<AppointmentSaleGoods, AppointmentSale> appointmentSaleJoin = root.join(root.getModel().getSingularAttribute("appointmentSale", AppointmentSale.class), JoinType.INNER);
            // 批量查询-goodsInfoIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsInfoIds())) {
                predicates.add(root.get("goodsInfoId").in(queryRequest.getGoodsInfoIds()));
            }

            // 查询-storeId
            if (Objects.nonNull(queryRequest.getStoreId())) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }
            // 批量查询-storeIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIds())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIds()));
            }

            if (Objects.nonNull(queryRequest.getAppointmentType())) {
                predicates.add(cbuild.equal(appointmentSaleJoin.get("appointmentType"), queryRequest.getAppointmentType()));
            }

            if (Objects.nonNull(queryRequest.getQueryTab())) {
                if (queryRequest.getQueryTab().equals(AppointmentStatus.NO_START)) {
                    predicates.add(cbuild.greaterThan(appointmentSaleJoin.get("appointmentStartTime"), LocalDateTime.now()));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.RUNNING)) {
                    predicates.add(cbuild.lessThan(appointmentSaleJoin.get("appointmentStartTime"), LocalDateTime.now()));
                    predicates.add(cbuild.greaterThan(appointmentSaleJoin.get("appointmentEndTime"), LocalDateTime.now()));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.END)) {
                    predicates.add(cbuild.lessThan(appointmentSaleJoin.get("snapUpEndTime"), LocalDateTime.now()));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.SUSPENDED)) {
                    predicates.add(cbuild.equal(appointmentSaleJoin.get("pauseFlag"), 1));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.NO_START_AND_RUNNING)) {
                    predicates.add(cbuild.greaterThan(appointmentSaleJoin.get("snapUpEndTime"), LocalDateTime.now()));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.RUNNING_SUSPENDED)) {
                    predicates.add(cbuild.lessThan(appointmentSaleJoin.get("appointmentStartTime"), LocalDateTime.now()));
                    predicates.add(cbuild.greaterThan(appointmentSaleJoin.get("snapUpEndTime"), LocalDateTime.now()));
                }
            }

            // 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
            if (Objects.nonNull(queryRequest.getGoodsType())) {
                predicates.add(cbuild.equal(root.get("goodsType"), queryRequest.getGoodsType()));
            }

            //是否删除
            if (Objects.nonNull(queryRequest.getDelFlag())) {
                predicates.add(cbuild.equal(appointmentSaleJoin.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] pre = predicates.toArray(new Predicate[predicates.size()]);
            return cquery.where(cbuild.and(predicates.toArray(pre))).getRestriction();
        }, queryRequest.getPageable());
    }

    public AppointmentVO wrapperAppointmentVO(AppointmentSaleGoods appointmentSaleGoods,
                                              Map<Long, AppointmentSale> appointmentSaleMap,
                                              Map<String, GoodsInfoVO> goodsInfoMap) {
        if (appointmentSaleGoods != null) {
            AppointmentSaleGoodsVO appointmentSaleGoodsVO = KsBeanUtil.convert(appointmentSaleGoods, AppointmentSaleGoodsVO.class);
            appointmentSaleGoodsVO.setGoodsInfoVO(goodsInfoMap.get(appointmentSaleGoodsVO.getGoodsInfoId()));
            AppointmentSaleVO appointmentSaleVO = KsBeanUtil.convert(appointmentSaleMap.get(appointmentSaleGoods.getAppointmentSaleId()), AppointmentSaleVO.class);
            appointmentSaleVO.buildStatus();
            return AppointmentVO.builder().
                    appointmentSale(appointmentSaleVO)
                    .appointmentSaleGoods(appointmentSaleGoodsVO).build();
        }
        return null;
    }


    public Page<AppointmentSaleGoodsVO> pageAppointmentGoodsInfo(AppointmentGoodsInfoSimpleCriterIaBuilder request) {
        Query query = entityManager.createNativeQuery(request.getQuerySql().concat(request.getQueryConditionSql()).concat(request.getQuerySort()));
        //组装查询参数
        wrapperQueryParam(query, request);
        query.setFirstResult(request.getPageNum() * request.getPageSize());
        query.setMaxResults(request.getPageSize());
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        // 查询预约活动商品spu列表
        List<AppointmentSaleGoodsVO> saleGoodsVOS = AppointmentGoodsInfoSimpleCriterIaBuilder.converter(query.getResultList());

        //查询预约活动spu列表总数
        Query totalCountRes =
                entityManager.createNativeQuery(request.getQueryTotalCountSql().concat(request.getQueryConditionSql()).concat(request.getQueryTotalTemp()));
        //组装查询参数
        wrapperQueryParam(totalCountRes, request);
        long totalCount = Long.parseLong(totalCountRes.getSingleResult().toString());

        return new PageImpl<>(saleGoodsVOS, request.getPageable(), totalCount);
    }

    /**
     * 预约互斥验证
     * @param request 入参
     * @return 验证结果
     */
    public void validate(AppointmentSaleGoodsValidateRequest request) {
        AppointmentSaleQueryRequest queryRequest = new AppointmentSaleQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setStoreId(request.getStoreId());
        queryRequest.setNotId(request.getNotId());
        //活动结束时间 >= 交叉开始时间
        queryRequest.setSnapUpEndTimeBegin(request.getCrossBeginTime());
        //活动开始时间 <= 交叉结束时间
        queryRequest.setAppointmentStartTimeEnd(request.getCrossEndTime());
        queryRequest.setPageSize(2);
        queryRequest.putSort("id", SortType.ASC.toValue());
        Boolean res = Boolean.FALSE;
        for (int pageNo = 0; ; pageNo++) {
            queryRequest.setPageNum(pageNo);
            Page<AppointmentSale> sales = appointmentSaleService.pageCols(queryRequest, Collections.singletonList("id"));
            if (sales.getTotalElements() == 0) {
                break;
            }
            if(CollectionUtils.isNotEmpty(sales.getContent())){
                if(Boolean.TRUE.equals(request.getAllFlag())){
                    res = Boolean.TRUE;
                    break;
                }

                AppointmentSaleGoodsQueryRequest goodsRequest = new AppointmentSaleGoodsQueryRequest();
                goodsRequest.setAppointmentSaleIdList(sales.stream().map(AppointmentSale::getId).collect(Collectors.toList()));
                List<AppointmentSaleGoods> saleGoodsList = this.listCols(goodsRequest, Arrays.asList("goodsId", "goodsInfoId"));
                //数据不存在
                if (CollectionUtils.isEmpty(saleGoodsList)) {
                    continue;
                }
                List<String> skuIds = saleGoodsList.stream().map(AppointmentSaleGoods::getGoodsInfoId).collect(Collectors.toList());
                List<String> spuIds = saleGoodsList.stream().map(AppointmentSaleGoods::getGoodsId).collect(Collectors.toList());

                //验证商品相关品牌是否存在
                if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
                    if(this.checkGoodsAndBrand(spuIds, request.getBrandIds())){
                        res = Boolean.TRUE;
                        break;
                    }
                } else if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
                    //验证商品相关店铺分类是否存在
                    if(this.checkGoodsAndStoreCate(spuIds, request.getStoreCateIds())){
                        res = Boolean.TRUE;
                        break;
                    }
                } else if (CollectionUtils.isNotEmpty(request.getSkuIds())
                        && request.getSkuIds().stream().anyMatch(skuIds::contains)) {
                    //验证自定义货品范围
                    res = Boolean.TRUE;
                    break;
                }
            }
            // 最后一页，退出循环
            if (pageNo >= sales.getTotalPages() - 1) {
                break;
            }
        }
        if(res){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{"预约"});
        }
    }

    /**
     * 验证商品、品牌的重合
     * @param spuIds 商品skuId
     * @param brandIds 品牌Id
     * @return 重合结果
     */
    public Boolean checkGoodsAndBrand(List<String> spuIds, List<Long> brandIds) {
        GoodsCountByConditionRequest count = new GoodsCountByConditionRequest();
        count.setGoodsIds(spuIds);
        count.setBrandIds(brandIds);
        count.setDelFlag(DeleteFlag.NO.toValue());
        return goodsQueryProvider.countByCondition(count).getContext().getCount() > 0;
    }

    /**
     * 验证商品、店铺分类的重合
     * @param spuIds 商品skuId
     * @param cateIds 店铺分类Id
     * @return 重合结果
     */
    public Boolean checkGoodsAndStoreCate(List<String> spuIds, List<Long> cateIds) {
        StoreCateGoodsRelaCountRequest count = new StoreCateGoodsRelaCountRequest();
        count.setGoodsIds(spuIds);
        count.setStoreCateIds(cateIds);
        return storeCateGoodsRelaQueryProvider.countByParams(count).getContext().getCount() > 0;
    }

    /**
     * 自定义字段的列表查询
     * @param queryRequest 参数
     * @param cols 列名
     * @return 列表
     */
    public List<AppointmentSaleGoods> listCols(AppointmentSaleGoodsQueryRequest queryRequest, List<String> cols) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<AppointmentSaleGoods> rt = cq.from(AppointmentSaleGoods.class);
        cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
        Specification<AppointmentSaleGoods> spec = AppointmentSaleGoodsWhereCriteriaBuilder.build(queryRequest);
        Predicate predicate = spec.toPredicate(rt, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }
        Sort sort = queryRequest.getSort();
        if (sort.isSorted()) {
            cq.orderBy(QueryUtils.toOrders(sort, rt, cb));
        }
        return this.converter(entityManager.createQuery(cq).getResultList(), cols);
    }

    /**
     * 查询对象转换
     * @param result
     * @return
     */
    private List<AppointmentSaleGoods> converter(List<Tuple> result, List<String> cols) {
        return result.stream().map(item -> {
            AppointmentSaleGoods sale = new AppointmentSaleGoods();
            sale.setGoodsId(JpaUtil.toString(item,"goodsId", cols));
            sale.setGoodsInfoId(JpaUtil.toString(item,"goodsInfoId", cols));
            return sale;
        }).collect(Collectors.toList());
    }

    /**
     * 组装查询参数
     *
     * @param query
     * @param request
     */
    private void wrapperQueryParam(Query query, AppointmentGoodsInfoSimpleCriterIaBuilder request) {
        if (StringUtils.isNoneBlank(request.getGoodsName())) {
            query.setParameter("goodsName", request.getGoodsName());
        }
        if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
            query.setParameter("goodsInfoIds", request.getGoodsInfoIds());
        }
    }
}

