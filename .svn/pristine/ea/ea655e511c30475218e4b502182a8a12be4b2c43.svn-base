package com.wanmi.sbc.marketing.bookingsalegoods.service;

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
import com.wanmi.sbc.goods.bean.vo.BookingSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleQueryRequest;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.BookingSaleGoodsQueryRequest;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.BookingSaleGoodsValidateRequest;
import com.wanmi.sbc.marketing.bean.dto.BookingGoodsInfoSimplePageDTO;
import com.wanmi.sbc.marketing.bean.dto.BookingSaleGoodsDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.BookingVO;
import com.wanmi.sbc.marketing.bookingsale.model.root.BookingSale;
import com.wanmi.sbc.marketing.bookingsale.service.BookingSaleService;
import com.wanmi.sbc.marketing.bookingsalegoods.model.root.BookingSaleGoods;
import com.wanmi.sbc.marketing.bookingsalegoods.repository.BookingSaleGoodsRepository;
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
 * <p>预售商品信息业务逻辑</p>
 *
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@Slf4j
@Service("BookingSaleGoodsService")
public class BookingSaleGoodsService {
    @Autowired
    private BookingSaleGoodsRepository bookingSaleGoodsRepository;

    @Autowired private BookingSaleService bookingSaleService;

    @Autowired private GoodsQueryProvider goodsQueryProvider;

    @Autowired private StoreCateGoodsRelaQueryProvider storeCateGoodsRelaQueryProvider;

    @Autowired private EntityManager entityManager;

    /**
     * 新增预售商品信息
     *
     * @author dany
     */
    @Transactional
    public BookingSaleGoods add(BookingSaleGoods entity) {
        bookingSaleGoodsRepository.save(entity);
        return entity;
    }

    /**
     * 修改预售商品信息
     *
     * @author dany
     */
    @Transactional
    public BookingSaleGoods modify(BookingSaleGoods entity) {
        bookingSaleGoodsRepository.save(entity);
        return entity;
    }

    /**
     * 单个删除预售商品信息
     *
     * @author dany
     */
    @Transactional
    public void deleteById(Long id) {
        bookingSaleGoodsRepository.deleteById(id);
    }

    /**
     * 批量删除预售商品信息
     *
     * @author dany
     */
    @Transactional
    public void deleteByIdList(List<Long> ids) {
        bookingSaleGoodsRepository.deleteByIdIn(ids);
    }

    /**
     * 单个查询预售商品信息
     *
     * @author dany
     */
    public BookingSaleGoods getOne(Long id, Long storeId) {
        return bookingSaleGoodsRepository.findByIdAndStoreId(id, storeId)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "预售商品信息不存在"));
    }

    /**
     * 分页查询预售商品信息
     *
     * @author dany
     */
    public Page<BookingSaleGoods> page(BookingSaleGoodsQueryRequest queryReq) {
        return bookingSaleGoodsRepository.findAll(
                BookingSaleGoodsWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询预售商品信息
     *
     * @author dany
     */
    public List<BookingSaleGoods> list(BookingSaleGoodsQueryRequest queryReq) {
        return bookingSaleGoodsRepository.findAll(BookingSaleGoodsWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author dany
     */
    public BookingSaleGoodsVO wrapperVo(BookingSaleGoods bookingSaleGoods) {
        if (bookingSaleGoods != null) {
            BookingSaleGoodsVO bookingSaleGoodsVO = KsBeanUtil.convert(bookingSaleGoods, BookingSaleGoodsVO.class);
            return bookingSaleGoodsVO;
        }
        return null;
    }

    public Page<BookingSaleGoods> build(BookingGoodsInfoSimplePageDTO queryRequest) {
        return bookingSaleGoodsRepository.findAll((root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<BookingSaleGoods, BookingSale> bookingSaleJoin = root.join(root.getModel().getSingularAttribute("bookingSale", BookingSale.class), JoinType.INNER);
            // 批量查询-goodsInfoIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsInfoIds())) {
                predicates.add(root.get("goodsInfoId").in(queryRequest.getGoodsInfoIds()));
            }

            if (Objects.nonNull(queryRequest.getStoreId())) {
                predicates.add(cbuild.equal(bookingSaleJoin.get("storeId"), queryRequest.getStoreId()));
            }

            // 批量查询-storeIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIds())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIds()));
            }

            if (Objects.nonNull(queryRequest.getBookingType())) {
                predicates.add(cbuild.equal(bookingSaleJoin.get("bookingType"), queryRequest.getBookingType()));
            }

            if (Objects.nonNull(queryRequest.getQueryTab())) {
                if (queryRequest.getQueryTab().equals(AppointmentStatus.NO_START)) {
                    predicates.add(cbuild.greaterThan(bookingSaleJoin.get("startTime"), LocalDateTime.now()));
                    predicates.add(cbuild.equal(bookingSaleJoin.get("pauseFlag"), 0));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.RUNNING)) {
                    Predicate earnestPredicate = cbuild.and(cbuild.equal(bookingSaleJoin.get("bookingType"), BookingType.EARNEST_MONEY.toValue()),
                            cbuild.lessThan(bookingSaleJoin.get("handSelStartTime"), LocalDateTime.now()),
                            cbuild.greaterThan(bookingSaleJoin.get("handSelEndTime"), LocalDateTime.now()),
                            cbuild.equal(bookingSaleJoin.get("pauseFlag"), 0));
                    Predicate fullPredicate = cbuild.and(cbuild.equal(bookingSaleJoin.get("bookingType"), BookingType.FULL_MONEY.toValue()),
                            cbuild.lessThan(bookingSaleJoin.get("startTime"), LocalDateTime.now()),
                            cbuild.greaterThan(bookingSaleJoin.get("endTime"), LocalDateTime.now()),
                            cbuild.equal(bookingSaleJoin.get("pauseFlag"), 0));
                    predicates.add(cbuild.or(earnestPredicate, fullPredicate));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.END)) {
                    Predicate earnestPredicate = cbuild.and(cbuild.equal(bookingSaleJoin.get("bookingType"), BookingType.EARNEST_MONEY.toValue()),
                            cbuild.lessThan(bookingSaleJoin.get("handSelEndTime"), LocalDateTime.now()));
                    Predicate fullPredicate = cbuild.and(cbuild.equal(bookingSaleJoin.get("bookingType"), BookingType.FULL_MONEY.toValue()),
                            cbuild.lessThan(bookingSaleJoin.get("endTime"), LocalDateTime.now()));
                    predicates.add(cbuild.or(earnestPredicate, fullPredicate));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.SUSPENDED)) {
                    predicates.add(cbuild.equal(bookingSaleJoin.get("pauseFlag"), 1));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.NO_START_AND_RUNNING)) {
                    Predicate earnestPredicate = cbuild.and(cbuild.equal(bookingSaleJoin.get("bookingType"), BookingType.EARNEST_MONEY.toValue()),
                            cbuild.greaterThan(bookingSaleJoin.get("handSelEndTime"), LocalDateTime.now()),
                            cbuild.equal(bookingSaleJoin.get("pauseFlag"), 0));
                    Predicate fullPredicate = cbuild.and(cbuild.equal(bookingSaleJoin.get("bookingType"), BookingType.FULL_MONEY.toValue()),
                            cbuild.greaterThan(bookingSaleJoin.get("endTime"), LocalDateTime.now()),
                            cbuild.equal(bookingSaleJoin.get("pauseFlag"), 0));
                    predicates.add(cbuild.or(earnestPredicate, fullPredicate));
                } else if (queryRequest.getQueryTab().equals(AppointmentStatus.RUNNING_SUSPENDED)) {
                    Predicate earnestPredicate = cbuild.and(cbuild.equal(bookingSaleJoin.get("bookingType"), BookingType.EARNEST_MONEY.toValue()),
                            cbuild.lessThan(bookingSaleJoin.get("handSelStartTime"), LocalDateTime.now()),
                            cbuild.greaterThan(bookingSaleJoin.get("handSelEndTime"), LocalDateTime.now()));
                    Predicate fullPredicate = cbuild.and(cbuild.equal(bookingSaleJoin.get("bookingType"), BookingType.FULL_MONEY.toValue()),
                            cbuild.lessThan(bookingSaleJoin.get("startTime"), LocalDateTime.now()),
                            cbuild.greaterThan(bookingSaleJoin.get("endTime"), LocalDateTime.now()));
                    predicates.add(cbuild.or(earnestPredicate, fullPredicate));
                }
            }

            // 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
            if (Objects.nonNull(queryRequest.getGoodsType())) {
                predicates.add(cbuild.equal(root.get("goodsType"), queryRequest.getGoodsType()));
            }

            Predicate[] pre = predicates.toArray(new Predicate[predicates.size()]);
            return cquery.where(cbuild.and(predicates.toArray(pre))).getRestriction();
        }, queryRequest.getPageable());
    }

    public BookingVO wrapperBookingVO(BookingSaleGoods entity, Map<Long, BookingSale> bookingSaleMap, Map<String,
            GoodsInfoVO> goodsInfoMap) {
        if (entity != null) {
            BookingSaleGoodsVO bookingSaleGoodsVO = KsBeanUtil.convert(entity, BookingSaleGoodsVO.class);
            bookingSaleGoodsVO.setGoodsInfoVO(goodsInfoMap.get(bookingSaleGoodsVO.getGoodsInfoId()));
            BookingSaleVO bookingSaleVO = KsBeanUtil.convert(bookingSaleMap.get(entity.getBookingSaleId()), BookingSaleVO.class);
            bookingSaleVO.buildStatus();
            return BookingVO.builder().
                    bookingSale(bookingSaleVO)
                    .bookingSaleGoods(bookingSaleGoodsVO).build();
        }
        return null;
    }

    public Page<BookingSaleGoodsVO> pageBookingGoodsInfo(BookingGoodsInfoSimpleCriterIaBuilder request) {
        Query query = entityManager.createNativeQuery(request.getQuerySql().concat(request.getQueryConditionSql()).concat(request.getQuerySort()));
        //组装查询参数
        this.wrapperQueryParam(query, request);
        query.setFirstResult(request.getPageNum() * request.getPageSize());
        query.setMaxResults(request.getPageSize());
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        // 查询预售活动商品spu列表
        List<BookingSaleGoodsVO> saleGoodsVOS = BookingGoodsInfoSimpleCriterIaBuilder.converter(query.getResultList());

        //查询预售活动spu列表总数
        Query totalCountRes =
                entityManager.createNativeQuery(request.getQueryTotalCountSql().concat(request.getQueryConditionSql()).concat(request.getQueryTotalTemp()));
        //组装查询参数
        this.wrapperQueryParam(totalCountRes, request);
        long totalCount = Long.parseLong(totalCountRes.getSingleResult().toString());

        return new PageImpl<>(saleGoodsVOS, request.getPageable(), totalCount);
    }

    @Transactional(rollbackFor = Exception.class)
    public int subCanBookingCount(BookingSaleGoodsDTO bookingSaleGoodsDTO) {
        return bookingSaleGoodsRepository.subCanBookingCount(bookingSaleGoodsDTO.getBookingSaleId(), bookingSaleGoodsDTO.getGoodsInfoId(), bookingSaleGoodsDTO.getStock());
    }

    @Transactional(rollbackFor = Exception.class)
    public int addCanBookingCount(BookingSaleGoodsDTO bookingSaleGoodsDTO) {
        return bookingSaleGoodsRepository.addCanBookingCount(bookingSaleGoodsDTO.getBookingSaleId(), bookingSaleGoodsDTO.getGoodsInfoId(), bookingSaleGoodsDTO.getStock());
    }

    @Transactional(rollbackFor = Exception.class)
    public int addBookingPayCount(BookingSaleGoodsDTO bookingSaleGoodsDTO) {
        return bookingSaleGoodsRepository.addBookingPayCount(bookingSaleGoodsDTO.getBookingSaleId(), bookingSaleGoodsDTO.getGoodsInfoId(), bookingSaleGoodsDTO.getStock());
    }

    @Transactional(rollbackFor = Exception.class)
    public int addBookinghandSelCount(BookingSaleGoodsDTO bookingSaleGoodsDTO) {
        return bookingSaleGoodsRepository.addBookinghandSelCount(bookingSaleGoodsDTO.getBookingSaleId(), bookingSaleGoodsDTO.getGoodsInfoId(), bookingSaleGoodsDTO.getStock());
    }

    @Transactional(rollbackFor = Exception.class)
    public int addBookingTailCount(BookingSaleGoodsDTO bookingSaleGoodsDTO) {
        return bookingSaleGoodsRepository.addBookingTailCount(bookingSaleGoodsDTO.getBookingSaleId(), bookingSaleGoodsDTO.getGoodsInfoId(), bookingSaleGoodsDTO.getStock());
    }

    /**
     * 预售互斥验证
     * @param request 入参
     * @return 验证结果 true:存在互斥
     */
    public void validate(BookingSaleGoodsValidateRequest request) {
        BookingSaleQueryRequest queryRequest = new BookingSaleQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setStoreId(request.getStoreId());
        queryRequest.setNotId(request.getNotId());
        //活动结束时间 >= 交叉开始时间
        queryRequest.setEndTimeBegin(request.getCrossBeginTime());
        //活动开始时间 <= 交叉结束时间
        queryRequest.setStartTimeEnd(request.getCrossEndTime());
        queryRequest.setPageSize(2);
        queryRequest.putSort("id", SortType.ASC.toValue());
        Boolean res = Boolean.FALSE;
        for (int pageNo = 0; ; pageNo++) {
            queryRequest.setPageNum(pageNo);
            Page<BookingSale> sales = this.bookingSaleService.pageCols(queryRequest, Collections.singletonList("id"));
            if (sales.getTotalElements() == 0) {
                break;
            }
            if (CollectionUtils.isNotEmpty(sales.getContent())) {
                //所有商品
                if(Boolean.TRUE.equals(request.getAllFlag())){
                    res = Boolean.TRUE;
                    break;
                }

                BookingSaleGoodsQueryRequest goodsRequest = new BookingSaleGoodsQueryRequest();
                goodsRequest.setBookingSaleIdList(sales.getContent().stream().map(BookingSale::getId).collect(Collectors.toList()));
                List<BookingSaleGoods> saleGoodsList = this.listCols(goodsRequest, Arrays.asList("goodsId", "goodsInfoId"));
                //数据不存在 => 跳过
                if (CollectionUtils.isEmpty(saleGoodsList)) {
                    continue;
                }
                List<String> spuIds = saleGoodsList.stream().map(BookingSaleGoods::getGoodsId).distinct().collect(Collectors.toList());
                List<String> skuIds = saleGoodsList.stream().map(BookingSaleGoods::getGoodsInfoId).collect(Collectors.toList());

                //验证商品相关品牌是否存在
                if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
                    if (this.checkGoodsAndBrand(spuIds, request.getBrandIds())) {
                        res = Boolean.TRUE;
                        break;
                    }
                } else if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
                    //验证商品相关店铺分类是否存在
                    if (this.checkGoodsAndStoreCate(spuIds, request.getStoreCateIds())) {
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
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{"预售"});
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
    public List<BookingSaleGoods> listCols(BookingSaleGoodsQueryRequest queryRequest, List<String> cols) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<BookingSaleGoods> rt = cq.from(BookingSaleGoods.class);
        cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
        Specification<BookingSaleGoods> spec = BookingSaleGoodsWhereCriteriaBuilder.build(queryRequest);
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
    private List<BookingSaleGoods> converter(List<Tuple> result, List<String> cols) {
        return result.stream().map(item -> {
            BookingSaleGoods sale = new BookingSaleGoods();
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
    private void wrapperQueryParam(Query query, BookingGoodsInfoSimpleCriterIaBuilder request) {
        if (StringUtils.isNoneBlank(request.getGoodsName())) {
            query.setParameter("goodsName", request.getGoodsName());
        }
        if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
            query.setParameter("goodsInfoIds", request.getGoodsInfoIds());
        }
    }
}

