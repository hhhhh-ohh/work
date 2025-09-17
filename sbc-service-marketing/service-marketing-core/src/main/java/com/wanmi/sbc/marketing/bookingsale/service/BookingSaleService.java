package com.wanmi.sbc.marketing.bookingsale.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PresellSaleStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.JpaUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelByCustomerIdAndStoreIdRequest;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelByCustomerIdAndStoreIdResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleQueryRequest;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.BookingSaleGoodsQueryRequest;
import com.wanmi.sbc.marketing.appointmentsalegoods.model.root.AppointmentSaleGoods;
import com.wanmi.sbc.marketing.appointmentsalegoods.repository.AppointmentSaleGoodsRepository;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bookingsale.model.root.BookingSale;
import com.wanmi.sbc.marketing.bookingsale.repository.BookingSaleRepository;
import com.wanmi.sbc.marketing.bookingsalegoods.model.root.BookingSaleGoods;
import com.wanmi.sbc.marketing.bookingsalegoods.repository.BookingSaleGoodsRepository;
import com.wanmi.sbc.marketing.bookingsalegoods.service.BookingSaleGoodsService;
import com.wanmi.sbc.marketing.newplugin.service.SkuCacheMarketingService;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import io.seata.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 预售信息业务逻辑
 *
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@Service("BookingSaleService")
public class BookingSaleService {
    @Autowired private BookingSaleRepository bookingSaleRepository;

    @Autowired private BookingSaleGoodsRepository bookingSaleGoodsRepository;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private BookingSaleGoodsService bookingSaleGoodsService;

    @Autowired private AppointmentSaleGoodsRepository appointmentSaleGoodsRepository;

    @Autowired private SkuCacheMarketingService skuCacheMarketingService;

    @Autowired private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired public SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired private EntityManager entityManager;

    /**
     * 新增预售信息
     *
     * @author dany
     */
    @Transactional
    public BookingSale add(BookingSale sale) {
        // 同一商品同一时间只能参加一个预约购买或者预售活动
        // 此处已迁移至bff
        //validateBookingPermission(sale);
        List<BookingSaleGoods> bookingSaleGoodsList = sale.getBookingSaleGoodsList();
        // 全局互斥时跳过原重突校验
        if (!mutexFlag()) {
            // 活动冲突的商品id
            List<String> clashGoodsInfoIdList = this.validateAppointmentActivity(sale);
            List<BookingSaleGoods> existsBookingSaleGoods =
                    bookingSaleGoodsService.list(
                            BookingSaleGoodsQueryRequest.builder()
                                    .goodsInfoIdList(
                                            sale.getBookingSaleGoodsList().stream()
                                                    .map(BookingSaleGoods::getGoodsInfoId)
                                                    .collect(Collectors.toList()))
                                    .build());
            if (CollectionUtils.isNotEmpty(existsBookingSaleGoods)) {
                // 查询冲突的活动
                List<BookingSale> bookingSales =
                        list(
                                        BookingSaleQueryRequest.builder()
                                                .idList(
                                                        existsBookingSaleGoods.stream()
                                                                .collect(
                                                                        Collectors.groupingBy(
                                                                                BookingSaleGoods
                                                                                        ::getBookingSaleId))
                                                                .keySet()
                                                                .stream()
                                                                .collect(Collectors.toList()))
                                                .delFlag(DeleteFlag.NO)
                                                .build())
                                .stream()
                                .filter(a -> !validateBookingSaleRepeat(a, sale))
                                .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(bookingSales)) {
                    // 活动重合,给前端返回导致活动冲突的商品id
                    List<BookingSaleGoods> saleGoodsList =
                            bookingSaleGoodsService.list(
                                    BookingSaleGoodsQueryRequest.builder()
                                            .bookingSaleIdList(
                                                    bookingSales.stream()
                                                            .map(BookingSale::getId)
                                                            .collect(Collectors.toList()))
                                            .build());
                    List<String> goodsInfoIdList =
                            saleGoodsList.stream()
                                    .map(BookingSaleGoods::getGoodsInfoId)
                                    .collect(Collectors.toList());
                    List<String> clashGoodsInfoIds =
                            bookingSaleGoodsList.stream()
                                    .filter(
                                            saleGoods ->
                                                    goodsInfoIdList.contains(
                                                            saleGoods.getGoodsInfoId()))
                                    .map(BookingSaleGoods::getGoodsInfoId)
                                    .collect(Collectors.toList());
                    clashGoodsInfoIdList.addAll(clashGoodsInfoIds);
                }
            }
            if (CollectionUtils.isNotEmpty(clashGoodsInfoIdList)) {
                throw new SbcRuntimeException(
                        MarketingErrorCodeEnum.K080140, new Object[] {clashGoodsInfoIdList.size()}, clashGoodsInfoIdList);
            }
        }
        sale.setCreateTime(LocalDateTime.now());
        BookingSale bookingSale = bookingSaleRepository.save(sale);

        bookingSaleGoodsList.stream()
                .forEach(
                        k -> {
                            k.setBookingSaleId(bookingSale.getId());
                            k.setStoreId(bookingSale.getStoreId());
                            k.setCreatePerson(bookingSale.getCreatePerson());
                            k.setCreateTime(LocalDateTime.now());
                            k.setCanBookingCount(k.getBookingCount());
                        });

        bookingSale.setBookingSaleGoodsList(
                bookingSaleGoodsRepository.saveAll(bookingSaleGoodsList));

        this.flushCache(bookingSaleGoodsList,bookingSale);
        return bookingSale;
    }

    /**
     * 校验是否与预约活动冲突
     *
     * @param sale
     * @return
     */
    private List<String> validateAppointmentActivity(BookingSale sale) {
        List<String> goodsInfoIds =
                sale.getBookingSaleGoodsList().stream()
                        .map(BookingSaleGoods::getGoodsInfoId)
                        .collect(Collectors.toList());
        LocalDateTime startTime = sale.getBookingStartTime();
        LocalDateTime endTime = sale.getBookingEndTime();
        if (sale.getBookingType() == 1) {
            startTime = sale.getHandSelStartTime();
            endTime = sale.getTailEndTime();
        }
        List list =
                appointmentSaleGoodsRepository.existAppointmentActivity(
                        goodsInfoIds, startTime, endTime);
        List<String> goodsInfoIdList = new ArrayList<>();
        list.forEach(
                o -> {
                    Object[] objects = (Object[]) o;
                    AppointmentSaleGoods appointmentSaleGoods =
                            (KsBeanUtil.convert(objects[1], AppointmentSaleGoods.class));
                    goodsInfoIdList.add(appointmentSaleGoods.getGoodsInfoId());
                });
        return goodsInfoIdList;
    }

    /**
     * 校验同一商品同一时间只能参加一个预约购买或者预售活动
     *
     * @param oldSale 持有
     * @param newSale 新增
     * @return
     */
    private boolean validateBookingSaleRepeat(BookingSale oldSale, BookingSale newSale) {
        LocalDateTime oldStartTime;
        LocalDateTime oldEndTime;
        LocalDateTime newStartTime;
        LocalDateTime newEndTime;
        if (oldSale.getBookingType().equals(NumberUtils.INTEGER_ZERO)) {
            oldStartTime = oldSale.getBookingStartTime();
            oldEndTime = oldSale.getBookingEndTime();
        } else if (oldSale.getBookingType().equals(NumberUtils.INTEGER_ONE)) {
            oldStartTime = oldSale.getHandSelStartTime();
            oldEndTime = oldSale.getTailEndTime();
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        if (newSale.getBookingType().equals(NumberUtils.INTEGER_ZERO)) {
            newStartTime = newSale.getBookingStartTime();
            newEndTime = newSale.getBookingEndTime();
        } else if (newSale.getBookingType().equals(NumberUtils.INTEGER_ONE)) {
            newStartTime = newSale.getHandSelStartTime();
            newEndTime = newSale.getTailEndTime();
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        if (newStartTime.compareTo(oldStartTime) >= 0 && newStartTime.compareTo(oldEndTime) <= 0) {
            return false;
        }
        if (newEndTime.compareTo(oldStartTime) >= 0 && newEndTime.compareTo(oldEndTime) <= 0) {
            return false;
        }
        if (newStartTime.compareTo(oldStartTime) <= 0 && newEndTime.compareTo(oldStartTime) >= 0) {
            return false;
        }
        return true;
    }

    /**
     * 修改预售信息
     *
     * @author dany
     */
    @Transactional
    public BookingSale modify(BookingSale sale) {
        BookingSale bookingSale = getOne(sale.getId(), sale.getStoreId());
        // 同一商品同一时间只能参加一个预约购买或者预售活动
        // 此处已迁移至bff
        //validateBookingPermission(sale);
        // 全局互斥时跳过原重突校验
        if (!mutexFlag()) {
            // 活动冲突的商品id
            List<String> clashGoodsInfoIdList = this.validateAppointmentActivity(sale);
            List<BookingSaleGoods> haveBookingSaleGoods =
                    bookingSaleGoodsService
                            .list(
                                    BookingSaleGoodsQueryRequest.builder()
                                            .goodsInfoIdList(
                                                    sale.getBookingSaleGoodsList().stream()
                                                            .map(BookingSaleGoods::getGoodsInfoId)
                                                            .collect(Collectors.toList()))
                                            .build())
                            .stream()
                            .filter(g -> !g.getBookingSaleId().equals(sale.getId()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(haveBookingSaleGoods)) {
                List<BookingSale> bookingSales =
                        list(
                                BookingSaleQueryRequest.builder()
                                        .idList(
                                                haveBookingSaleGoods.stream()
                                                        .collect(
                                                                Collectors.groupingBy(
                                                                        BookingSaleGoods
                                                                                ::getBookingSaleId))
                                                        .keySet()
                                                        .stream()
                                                        .collect(Collectors.toList()))
                                        .delFlag(DeleteFlag.NO)
                                        .build())
                                .stream()
                                .filter(a -> !validateBookingSaleRepeat(a, sale))
                                .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(bookingSales)) {
                    // 活动重合,给前端返回导致活动冲突的商品id
                    List<BookingSaleGoods> saleGoodsList =
                            bookingSaleGoodsService.list(
                                    BookingSaleGoodsQueryRequest.builder()
                                            .bookingSaleIdList(
                                                    bookingSales.stream()
                                                            .map(BookingSale::getId)
                                                            .collect(Collectors.toList()))
                                            .build());
                    List<String> goodsInfoIdList =
                            saleGoodsList.stream()
                                    .map(BookingSaleGoods::getGoodsInfoId)
                                    .collect(Collectors.toList());
                    List<String> clashGoodsInfoIds =
                            sale.getBookingSaleGoodsList().stream()
                                    .filter(
                                            saleGoods ->
                                                    goodsInfoIdList.contains(
                                                            saleGoods.getGoodsInfoId()))
                                    .map(BookingSaleGoods::getGoodsInfoId)
                                    .collect(Collectors.toList());
                    clashGoodsInfoIdList.addAll(clashGoodsInfoIds);
                }
            }
            if (CollectionUtils.isNotEmpty(clashGoodsInfoIdList)) {
                throw new SbcRuntimeException(
                        MarketingErrorCodeEnum.K080140, new Object[]{clashGoodsInfoIdList.size()}, clashGoodsInfoIdList);
            }
        }
        sale.setId(bookingSale.getId());
        sale.setDelFlag(bookingSale.getDelFlag());
        sale.setUpdateTime(LocalDateTime.now());
        sale.setCreatePerson(bookingSale.getCreatePerson());
        sale.setCreateTime(bookingSale.getCreateTime());
        bookingSaleRepository.save(sale);

        List<BookingSaleGoods> oldBookingSaleGoods =
                bookingSaleGoodsRepository.findByBookingSaleIdAndStoreId(
                        sale.getId(), sale.getStoreId());

        List<BookingSaleGoods> newSaleGoodsList = sale.getBookingSaleGoodsList();

        Map<String, BookingSaleGoods> oldBookingSaleGoodsMap =
                oldBookingSaleGoods.stream()
                        .collect(
                                Collectors.toMap(
                                        BookingSaleGoods::getGoodsInfoId, Function.identity()));

        newSaleGoodsList.forEach(
                n -> {
                    if (oldBookingSaleGoodsMap.containsKey(n.getGoodsInfoId())) {
                        BookingSaleGoods bookingSaleGoods =
                                oldBookingSaleGoodsMap.get(n.getGoodsInfoId());
                        n.setCreatePerson(bookingSaleGoods.getCreatePerson());
                        n.setCreateTime(bookingSaleGoods.getCreateTime());
                        n.setId(bookingSaleGoods.getId());
                        oldBookingSaleGoodsMap.remove(n.getGoodsInfoId());
                    }
                    n.setUpdateTime(LocalDateTime.now());
                    n.setUpdatePerson(sale.getUpdatePerson());
                    n.setBookingSaleId(bookingSale.getId());
                    n.setStoreId(bookingSale.getStoreId());
                    n.setCanBookingCount(n.getBookingCount());
                });

        if (!oldBookingSaleGoods.isEmpty()) {
            List<BookingSaleGoods> bookingSaleGoodsArrayList = new ArrayList<>();
            oldBookingSaleGoodsMap.forEach(
                    (k, v) -> {
                        bookingSaleGoodsArrayList.add(v);
                    });
            bookingSaleGoodsRepository.deleteAll(bookingSaleGoodsArrayList);
        }

        bookingSaleGoodsRepository.saveAll(newSaleGoodsList);
        bookingSale.setBookingSaleGoodsList(newSaleGoodsList);

        //刷新缓存
        flushCache(newSaleGoodsList,bookingSale);
        return bookingSale;
    }

    /**
     * 单个删除预售信息
     *
     * @author dany
     */
    @Transactional
    public void deleteById(BookingSale entity) {
        bookingSaleRepository.save(entity);
        flushCache(Collections.singletonList(entity),true);
    }

    /**
     * 批量删除预售信息
     *
     * @author dany
     */
    @Transactional
    public void deleteByIdList(List<BookingSale> infos) {
        bookingSaleRepository.saveAll(infos);
        flushCache(infos,true);
    }

    /**
     * 单个查询预售信息
     *
     * @author dany
     */
    public BookingSale getOne(Long id, Long storeId) {
        if (Objects.isNull(storeId)) {
            return getById(id);
        }
        return bookingSaleRepository
                .findByIdAndStoreIdAndDelFlag(id, storeId, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "预售信息不存在"));
    }

    public BookingSale getById(Long id) {
        return bookingSaleRepository
                .findByIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "预售信息不存在"));
    }

    /**
     * 分页查询预售信息
     *
     * @author dany
     */
    public Page<BookingSale> page(BookingSaleQueryRequest queryReq) {
        return bookingSaleRepository.findAll(
                BookingSaleWhereCriteriaBuilder.build(queryReq), queryReq.getPageRequest());
    }

    /**
     * 列表查询预售信息
     *
     * @author dany
     */
    public List<BookingSale> list(BookingSaleQueryRequest queryReq) {
        return bookingSaleRepository.findAll(BookingSaleWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author dany
     */
    public BookingSaleVO wrapperVo(BookingSale bookingSale) {
        if (bookingSale != null) {
            BookingSaleVO bookingSaleVO = KsBeanUtil.convert(bookingSale, BookingSaleVO.class);
            bookingSaleVO.setCreateTime(bookingSale.getCreateTime());
            if(Objects.equals(bookingSale.getPauseFlag(),NumberUtils.INTEGER_ONE)){
                bookingSaleVO.setStatus(PresellSaleStatus.PAUSED);
            }
            return bookingSaleVO;
        }
        return null;
    }

    @Transactional
    public void modifyStatus(BookingSale sale) {
        bookingSaleRepository.save(sale);
        flushCache(Collections.singletonList(sale),false);
    }

    public List<BookingSale> inProgressBookingSaleInfoByGoodsInfoIdList(
            List<String> goodsInfoIdList) {
        List list = bookingSaleRepository.findByGoodsInfoIdInProcess(goodsInfoIdList);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<BookingSale> bookingSales = new ArrayList<>();
        list.forEach(
                o -> {
                    Object[] objects = (Object[]) o;
                    BookingSale bookingSale = KsBeanUtil.convert(objects[0], BookingSale.class);
                    bookingSale.setBookingSaleGoods(
                            KsBeanUtil.convert(objects[1], BookingSaleGoods.class));
                    bookingSales.add(bookingSale);
                });
        return bookingSales;
    }

    /**
     * 判断此商品此否正在进行预售活动
     *
     * @param goodsInfoId
     * @return
     */
    public BookingSale isInProcess(String goodsInfoId, String customerId) {
        Object object = bookingSaleRepository.findByGoodsInfoIdInProcess(goodsInfoId);
        if (Objects.isNull(object)) {
            return null;
        }
//        Object[] objects = (Object[]) object;
        BookingSale bookingSale = KsBeanUtil.convert(object, BookingSale.class);
        //如果会员Id不为空则验证用户等级是否满足活动要求
        if (StringUtils.isNotBlank(customerId)) {
            String joinLevel = bookingSale.getJoinLevel();
            if (joinLevel.equals(Constants.STR_0)) {
                CustomerLevelByCustomerIdAndStoreIdResponse response = customerLevelQueryProvider.getCustomerLevelByCustomerIdAndStoreId(CustomerLevelByCustomerIdAndStoreIdRequest.builder().storeId(bookingSale.getStoreId()).customerId(customerId).build()).getContext();
                if(Objects.isNull(response) || Objects.isNull(response.getLevelType())) {
                    return null;
                }
            } else if (!joinLevel.equals(Constants.STR_MINUS_1)) {
                List<Long> joinLevelList = Arrays.stream(bookingSale.getJoinLevel().split(",")) .map(x -> Long.valueOf(x)) .collect(Collectors.toList());
                CustomerLevelByCustomerIdAndStoreIdResponse response = customerLevelQueryProvider.getCustomerLevelByCustomerIdAndStoreId(CustomerLevelByCustomerIdAndStoreIdRequest.builder().storeId(bookingSale.getStoreId()).customerId(customerId).build()).getContext();
                if(Objects.isNull(response)) {
                    return null;
                }
                if (CollectionUtils.isNotEmpty(joinLevelList) && !joinLevelList.contains(response.getLevelId())) {
                    return null;
                }
            }
        }
        bookingSale.setBookingSaleGoods(bookingSaleGoodsRepository.findAll((rt, qr, cb) ->
                        cb.and(cb.equal(rt.get("bookingSaleId"), bookingSale.getId()), cb.equal(rt.get("goodsInfoId"), goodsInfoId)))
                .stream().findFirst().orElse(null));
        return bookingSale;
    }

    /**
     * 根据spuid列表获取未结束的预售活动
     *
     * @param goodsId
     * @return
     */
    public List<BookingSale> getNotEndActivity(String goodsId) {
        List list = bookingSaleRepository.getNotEndActivity(goodsId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<BookingSale> bookingSales = new ArrayList<>();
        list.forEach(
                o -> {
                    Object[] objects = (Object[]) o;
                    BookingSale bookingSale = KsBeanUtil.convert(objects[0], BookingSale.class);
                    bookingSale.setBookingSaleGoods(
                            KsBeanUtil.convert(objects[1], BookingSaleGoods.class));
                    bookingSales.add(bookingSale);
                });
        return bookingSales;
    }

    /** 下单时根据goodsInfoIds校验是否参加预售活动 */
    public void validParticipateInBookingSale(List<String> goodsInfoIdList) {
        if (CollectionUtils.isEmpty(goodsInfoIdList)) {
            return;
        }
        List list = bookingSaleRepository.findByGoodsInfoIdInBooking(goodsInfoIdList);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080171);
        }
    }

    /** 增加预售活动过期校验 */
    public void validateBookingQualification(
            List<String> bookingSaleGoodsInfoIds, Map<String, Long> skuIdAndBookSaleIdMap) {
        bookingSaleGoodsInfoIds.stream()
                .forEach(
                        skuId -> {
                            BookingSale bookingSaleVO = this.isInProcess(skuId, null);
                            if (Objects.isNull(bookingSaleVO)) {
                                throw new SbcRuntimeException(OrderErrorCodeEnum.K050151);
                            }
                            if (!bookingSaleVO.getId().equals(skuIdAndBookSaleIdMap.get(skuId))) {
                                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                            }
                            if (bookingSaleVO.getBookingType().equals(NumberUtils.INTEGER_ONE)
                                    && (bookingSaleVO
                                                    .getHandSelEndTime()
                                                    .isBefore(LocalDateTime.now())
                                            || bookingSaleVO
                                                    .getHandSelStartTime()
                                                    .isAfter(LocalDateTime.now()))) {
                                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
                            }
                            if (bookingSaleVO.getBookingType().equals(NumberUtils.INTEGER_ZERO)
                                    && (bookingSaleVO
                                                    .getBookingEndTime()
                                                    .isBefore(LocalDateTime.now())
                                            || bookingSaleVO
                                                    .getBookingStartTime()
                                                    .isAfter(LocalDateTime.now()))) {
                                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
                            }
                        });
    }

    /**
     * @description
     * @author zhanggaolei
     * @date 2021/7/22 2:46 下午
     * @param saleGoodsList
     * @param bookingSale
     * @return void
     */
    private void flushCache(
            List<BookingSaleGoods> saleGoodsList, BookingSale bookingSale) {
        // 刷新缓存
        List<GoodsInfoMarketingCacheDTO> cacheList = new ArrayList<>();
        for (BookingSaleGoods saleGoods : saleGoodsList) {
            GoodsInfoMarketingCacheDTO cacheDTO = new GoodsInfoMarketingCacheDTO();
            cacheDTO.setId(bookingSale.getId());
            cacheDTO.setSkuId(saleGoods.getGoodsInfoId());
            cacheDTO.setMarketingPluginType(MarketingPluginType.BOOKING_SALE);
            cacheDTO.setJoinLevel(
                    Arrays.stream(bookingSale.getJoinLevel().split(","))
                            .map(x -> Long.valueOf(x))
                            .collect(Collectors.toList()));
            cacheDTO.setJoinLevelType(bookingSale.getJoinLevelType());

            if (bookingSale.getBookingType() == BookingType.FULL_MONEY.toValue()) {
                cacheDTO.setPrice(saleGoods.getBookingPrice());
                cacheDTO.setBeginTime(bookingSale.getStartTime());
                cacheDTO.setEndTime(bookingSale.getEndTime());
            }else{
                cacheDTO.setBeginTime(bookingSale.getHandSelStartTime());
                cacheDTO.setEndTime(bookingSale.getHandSelEndTime());
            }
            cacheList.add(cacheDTO);
        }
        this.skuCacheMarketingService.setSkuCacheMarketing(cacheList);
    }

    /**
     * @description
     * @author zhanggaolei
     * @date 2021/7/22 2:48 下午
     * @param saleList
     * @return void
     */
    private void flushCache(List<BookingSale> saleList,Boolean delFlag) {
        List<Long> idList =
                saleList.stream().map(BookingSale::getId).collect(Collectors.toList());
        List<GoodsInfoMarketingCacheDTO> saveList = new ArrayList<>();
        List<BookingSaleGoods> list =
                bookingSaleGoodsService.list(
                        BookingSaleGoodsQueryRequest.builder().bookingSaleIdList(idList).build());
        Map<Long, List<BookingSaleGoods>> map =
                list.stream()
                        .collect(
                                Collectors.groupingBy(
                                        BookingSaleGoods::getBookingSaleId));
        List<GoodsInfoMarketingCacheDTO> delList = new ArrayList<>();
        for (BookingSale bookingSale : saleList) {
            for (BookingSaleGoods bookingSaleGoods : map.get(bookingSale.getId())) {
                GoodsInfoMarketingCacheDTO goodsInfoMarketingCacheDTO =
                        new GoodsInfoMarketingCacheDTO();
                goodsInfoMarketingCacheDTO.setId(bookingSale.getId());

                goodsInfoMarketingCacheDTO.setMarketingPluginType(
                        MarketingPluginType.BOOKING_SALE);
                goodsInfoMarketingCacheDTO.setJoinLevel(
                        Arrays.stream(bookingSale.getJoinLevel().split(","))
                                .map(x -> Long.valueOf(x))
                                .collect(Collectors.toList()));
                goodsInfoMarketingCacheDTO.setJoinLevelType(bookingSale.getJoinLevelType());
                goodsInfoMarketingCacheDTO.setSkuId(bookingSaleGoods.getGoodsInfoId());
                if (bookingSale.getBookingType() == BookingType.FULL_MONEY.toValue()) {
                    goodsInfoMarketingCacheDTO.setPrice(bookingSaleGoods.getBookingPrice());
                    goodsInfoMarketingCacheDTO.setBeginTime(bookingSale.getStartTime());
                    goodsInfoMarketingCacheDTO.setEndTime(bookingSale.getEndTime());
                }else{
                    goodsInfoMarketingCacheDTO.setBeginTime(bookingSale.getHandSelStartTime());
                    goodsInfoMarketingCacheDTO.setEndTime(bookingSale.getHandSelEndTime());
                }
                // 删除或者暂停
                if (bookingSale.getDelFlag().equals(DeleteFlag.YES)
                        || bookingSale.getPauseFlag() == 1 || true == delFlag) {
                    delList.add(goodsInfoMarketingCacheDTO);
                } else {
                    saveList.add(goodsInfoMarketingCacheDTO);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(saveList)) {
            this.skuCacheMarketingService.setSkuCacheMarketing(saveList);
        }
        if (CollectionUtils.isNotEmpty(delList)) {
            this.skuCacheMarketingService.delSkuCacheMarketing(delList);
        }
    }

    /**
     * @description 关闭活动
     * @author  xuyuneng
     * @date 2021/6/24 3:08 下午
     * @param id 活动id
     * @param storeId 店铺id
     * @return
     */
    @Transactional
    public void close(Long id, Long storeId) {
        BookingSale bookingSale = getOne(id, storeId);
        LocalDateTime now = LocalDateTime.now();

        if(bookingSale.getBookingType() == BookingType.EARNEST_MONEY.toValue()) {
            //定金预售
            if(NumberUtils.INTEGER_ONE.equals(bookingSale.getPauseFlag())
                    || bookingSale.getHandSelStartTime().isAfter(LocalDateTime.now())
                    || bookingSale.getHandSelEndTime().isBefore(LocalDateTime.now())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030142);
            }
            bookingSale.setHandSelEndTime(now);
            bookingSale.setTailEndTime(now);
            bookingSale.setEndTime(now);
        } else {
            //全款预售
            if(NumberUtils.INTEGER_ONE.equals(bookingSale.getPauseFlag())
                    || bookingSale.getBookingStartTime().isAfter(LocalDateTime.now())
                    || bookingSale.getBookingEndTime().isBefore(LocalDateTime.now())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030142);
            }
            bookingSale.setBookingEndTime(now);
            bookingSale.setEndTime(now);
        }

        bookingSaleRepository.save(bookingSale);
        flushCache(Collections.singletonList(bookingSale),true);
    }

    /**
     * 同步到缓存
     */
    public void sycCache(){
        List<BookingSale> bookingSales = this.bookingSaleRepository.findByInProcess();
        if(CollectionUtils.isNotEmpty(bookingSales)){
            flushCache(bookingSales,false);
        }
    }

    /**
     * 自定义字段的列表查询
     * @param cols 列名
     * @return 列表
     */
    public Page<BookingSale> pageCols(BookingSaleQueryRequest request, List<String> cols) {
        CriteriaBuilder countCb = entityManager.getCriteriaBuilder();
        Specification<BookingSale> spec = BookingSaleWhereCriteriaBuilder.build(request);
        CriteriaQuery<Long> countCq = countCb.createQuery(Long.class);
        Root<BookingSale> countRt = countCq.from(BookingSale.class);
        countCq.select(countCb.count(countRt));
        Predicate countPredicate = spec.toPredicate(countRt, countCq, countCb);
        if (countPredicate != null) {
            countCq.where(countPredicate);
        }
        long sum = entityManager.createQuery(countCq).getResultList().stream().filter(Objects::nonNull)
                .mapToLong(s -> s).sum();
        if (sum == 0) {
            return PageableExecutionUtils.getPage(Collections.emptyList(), request.getPageable(), () -> sum);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<BookingSale> rt = cq.from(BookingSale.class);
        cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
        Predicate predicate = spec.toPredicate(rt, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }
        cq.orderBy(QueryUtils.toOrders(request.getSort(), rt, cb));
        TypedQuery<Tuple> query = entityManager.createQuery(cq);
        query.setFirstResult((int) request.getPageRequest().getOffset());
        query.setMaxResults(request.getPageRequest().getPageSize());
        return PageableExecutionUtils.getPage(this.converter(query.getResultList(), cols), request.getPageable(), () -> sum);
    }

    /**
     * 查询对象转换
     * @param result
     * @return
     */
    private List<BookingSale> converter(List<Tuple> result, List<String> cols) {
        return result.stream().map(item -> {
            BookingSale sale = new BookingSale();
            sale.setId(JpaUtil.toLong(item,"id", cols));
            return sale;
        }).collect(Collectors.toList());
    }

    /**
     * 是否全局互斥
     * @return true:是 false:否
     */
    private Boolean mutexFlag() {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.MARKETING_MUTEX.toValue());
        ConfigVO configVO = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext().getConfig();
        //营销互斥不验证标识
        return Objects.nonNull(configVO) && NumberUtils.INTEGER_ONE.equals(configVO.getStatus());
    }
}
