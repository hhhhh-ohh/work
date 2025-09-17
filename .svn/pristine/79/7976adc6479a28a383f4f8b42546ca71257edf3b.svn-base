package com.wanmi.sbc.marketing.appointmentsale.service;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.AppointmentStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.JpaUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelByCustomerIdAndStoreIdRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelListByCustomerLevelNameRequest;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelByCustomerIdAndStoreIdResponse;
import com.wanmi.sbc.customer.bean.dto.MarketingCustomerLevelDTO;
import com.wanmi.sbc.customer.bean.vo.MarketingCustomerLevelVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleQueryRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsQueryRequest;
import com.wanmi.sbc.marketing.appointmentsale.model.root.AppointmentSale;
import com.wanmi.sbc.marketing.appointmentsale.model.root.AppointmentSaleDO;
import com.wanmi.sbc.marketing.appointmentsale.repository.AppointmentSaleRepository;
import com.wanmi.sbc.marketing.appointmentsalegoods.model.root.AppointmentSaleGoods;
import com.wanmi.sbc.marketing.appointmentsalegoods.model.root.AppointmentSaleGoodsDO;
import com.wanmi.sbc.marketing.appointmentsalegoods.repository.AppointmentSaleGoodsRepository;
import com.wanmi.sbc.marketing.appointmentsalegoods.service.AppointmentSaleGoodsService;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bookingsalegoods.model.root.BookingSaleGoods;
import com.wanmi.sbc.marketing.bookingsalegoods.repository.BookingSaleGoodsRepository;
import com.wanmi.sbc.marketing.newplugin.service.SkuCacheMarketingService;
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
 * 预约抢购业务逻辑
 *
 * @author zxd
 * @date 2020-05-21 10:32:23
 */
@Service("AppointmentSaleService")
public class AppointmentSaleService {
    @Autowired private AppointmentSaleRepository appointmentSaleRepository;

    @Autowired private AppointmentSaleGoodsRepository appointmentSaleGoodsRepository;

    @Autowired private AppointmentSaleGoodsService appointmentSaleGoodsService;

    @Autowired private BookingSaleGoodsRepository bookingSaleGoodsRepository;

    @Autowired private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired private SkuCacheMarketingService skuCacheMarketingService;

    @Autowired public SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired private EntityManager entityManager;

    /**
     * 新增预约抢购
     *
     * @author zxd
     */
    @Transactional
    public AppointmentSale add(AppointmentSale request) {
        List<AppointmentSaleGoods> appointmentSaleGoodsList = request.getAppointmentSaleGoods();
        //已迁移至bff下
        //validateAppointmentPermission(request);

        if (!mutexFlag()) {
            // 同一商品同一时间只能参加一个预约购买或者预售活动
            // 活动冲突的商品id
            List<String> clashGoodsInfoIdList = this.validateBookingActivityClash(request);
            List<AppointmentSaleGoods> haveAppointmentSaleGoods =
                    appointmentSaleGoodsService.list(
                            AppointmentSaleGoodsQueryRequest.builder()
                                    .goodsInfoIdList(
                                            request.getAppointmentSaleGoods().stream()
                                                    .map(AppointmentSaleGoods::getGoodsInfoId)
                                                    .collect(Collectors.toList()))
                                    .build());
            if (CollectionUtils.isNotEmpty(haveAppointmentSaleGoods)) {

                List<AppointmentSale> appointmentSales =
                        list(
                                AppointmentSaleQueryRequest.builder()
                                        .idList(
                                                haveAppointmentSaleGoods.stream()
                                                        .collect(
                                                                Collectors.groupingBy(
                                                                        AppointmentSaleGoods
                                                                                ::getAppointmentSaleId))
                                                        .keySet()
                                                        .stream()
                                                        .collect(Collectors.toList()))
                                        .delFlag(DeleteFlag.NO)
                                        .build())
                                .stream()
                                .filter(a -> !validateAppointmentSaleRepeat(a, request))
                                .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(appointmentSales)) {
                    // 活动重合,给前端返回导致活动冲突的商品id
                    List<AppointmentSaleGoods> saleGoodsList =
                            appointmentSaleGoodsService.list(
                                    AppointmentSaleGoodsQueryRequest.builder()
                                            .appointmentSaleIdList(
                                                    appointmentSales.stream()
                                                            .map(AppointmentSale::getId)
                                                            .collect(Collectors.toList()))
                                            .build());
                    List<String> goodsInfoIdList =
                            saleGoodsList.stream()
                                    .map(AppointmentSaleGoods::getGoodsInfoId)
                                    .collect(Collectors.toList());
                    List<String> clashGoodsInfoIds =
                            appointmentSaleGoodsList.stream()
                                    .filter(
                                            saleGoods ->
                                                    goodsInfoIdList.contains(
                                                            saleGoods.getGoodsInfoId()))
                                    .map(AppointmentSaleGoods::getGoodsInfoId)
                                    .collect(Collectors.toList());
                    clashGoodsInfoIdList.addAll(clashGoodsInfoIds);
                }
            }
            if (CollectionUtils.isNotEmpty(clashGoodsInfoIdList)) {
                throw new SbcRuntimeException(
                        MarketingErrorCodeEnum.K080140, new Object[]{clashGoodsInfoIdList.size()}, clashGoodsInfoIdList);
            }
        }
        AppointmentSale appointmentSale = appointmentSaleRepository.save(request);

        List<AppointmentSaleGoods> saleGoods = request.getAppointmentSaleGoods();

        saleGoods.stream()
                .forEach(
                        k -> {
                            k.setAppointmentSaleId(appointmentSale.getId());
                            k.setStoreId(appointmentSale.getStoreId());
                            k.setCreatePerson(appointmentSale.getCreatePerson());
                        });
        appointmentSaleGoodsRepository.saveAll(saleGoods);
        appointmentSale.setAppointmentSaleGoods(saleGoods);

        //刷新缓存
        flushCache(saleGoods,appointmentSale);
        return appointmentSale;
    }

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    /**
     * 校验是否存在预售活动
     *
     * @param appointmentSale
     * @return
     */
    private List<String> validateBookingActivityClash(AppointmentSale appointmentSale) {
        List<String> goodsInfoIds =
                appointmentSale.getAppointmentSaleGoods().stream()
                        .map(AppointmentSaleGoods::getGoodsInfoId)
                        .collect(Collectors.toList());
        LocalDateTime startTime = appointmentSale.getAppointmentStartTime();
        LocalDateTime endTime = appointmentSale.getSnapUpEndTime();
        List list =
                bookingSaleGoodsRepository.existBookingActivity(goodsInfoIds, startTime, endTime);
        List<String> goodsInfoIdList = new ArrayList<>();
        list.forEach(
                o -> {
                    Object[] objects = (Object[]) o;
                    BookingSaleGoods bookingSaleGoods =
                            (KsBeanUtil.convert(objects[1], BookingSaleGoods.class));
                    goodsInfoIdList.add(bookingSaleGoods.getGoodsInfoId());
                });
        return goodsInfoIdList;
    }

    /**
     * 校验同一商品同一时间只能参加一个预约购买或者预售活动
     *
     * @param oldAppointmentSale 持有
     * @param newAppointmentSale 新增
     * @return
     */
    private boolean validateAppointmentSaleRepeat(
            AppointmentSale oldAppointmentSale, AppointmentSale newAppointmentSale) {

        if (newAppointmentSale
                                .getAppointmentStartTime()
                                .compareTo(oldAppointmentSale.getAppointmentStartTime())
                        >= 0
                && newAppointmentSale
                                .getAppointmentStartTime()
                                .compareTo(oldAppointmentSale.getSnapUpEndTime())
                        <= 0) {
            return false;
        }

        if (newAppointmentSale
                                .getSnapUpEndTime()
                                .compareTo(oldAppointmentSale.getAppointmentStartTime())
                        >= 0
                && newAppointmentSale
                                .getSnapUpEndTime()
                                .compareTo(oldAppointmentSale.getSnapUpEndTime())
                        <= 0) {
            return false;
        }

        if (newAppointmentSale
                                .getAppointmentStartTime()
                                .compareTo(oldAppointmentSale.getAppointmentStartTime())
                        <= 0
                && newAppointmentSale
                                .getSnapUpEndTime()
                                .compareTo(oldAppointmentSale.getSnapUpEndTime())
                        >= 0) {
            return false;
        }
        return true;
    }

    /**
     * 修改预约抢购
     *
     * @author zxd
     */
    @Transactional
    public AppointmentSale modify(AppointmentSale sale) {
        AppointmentSale appointmentSale = getOne(sale.getId(), sale.getStoreId());

        //已迁移至bff下
        //validateAppointmentPermission(sale);

        if (!mutexFlag()) {
            // 同一商品同一时间只能参加一个预约购买或者预售活动
            // 活动冲突的商品id
            List<String> clashGoodsInfoIdList = this.validateBookingActivityClash(sale);
            List<AppointmentSaleGoods> haveAppointmentSaleGoods =
                    appointmentSaleGoodsService
                            .list(
                                    AppointmentSaleGoodsQueryRequest.builder()
                                            .goodsInfoIdList(
                                                    sale.getAppointmentSaleGoods().stream()
                                                            .map(AppointmentSaleGoods::getGoodsInfoId)
                                                            .collect(Collectors.toList()))
                                            .build())
                            .stream()
                            .filter(g -> !g.getAppointmentSaleId().equals(sale.getId()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(haveAppointmentSaleGoods)) {
                List<AppointmentSale> appointmentSales =
                        list(
                                AppointmentSaleQueryRequest.builder()
                                        .idList(
                                                haveAppointmentSaleGoods.stream()
                                                        .collect(
                                                                Collectors.groupingBy(
                                                                        AppointmentSaleGoods
                                                                                ::getAppointmentSaleId))
                                                        .keySet()
                                                        .stream()
                                                        .collect(Collectors.toList()))
                                        .delFlag(DeleteFlag.NO)
                                        .build())
                                .stream()
                                .filter(a -> !validateAppointmentSaleRepeat(a, sale))
                                .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(appointmentSales)) {
                    // 活动重合,给前端返回导致活动冲突的商品id
                    List<AppointmentSaleGoods> saleGoodsList =
                            appointmentSaleGoodsService.list(
                                    AppointmentSaleGoodsQueryRequest.builder()
                                            .appointmentSaleIdList(
                                                    appointmentSales.stream()
                                                            .map(AppointmentSale::getId)
                                                            .collect(Collectors.toList()))
                                            .build());
                    List<String> goodsInfoIdList =
                            saleGoodsList.stream()
                                    .map(AppointmentSaleGoods::getGoodsInfoId)
                                    .collect(Collectors.toList());
                    List<String> clashGoodsInfoIds =
                            sale.getAppointmentSaleGoods().stream()
                                    .filter(
                                            saleGoods ->
                                                    goodsInfoIdList.contains(
                                                            saleGoods.getGoodsInfoId()))
                                    .map(AppointmentSaleGoods::getGoodsInfoId)
                                    .collect(Collectors.toList());
                    clashGoodsInfoIdList.addAll(clashGoodsInfoIds);
                }
            }
            if (CollectionUtils.isNotEmpty(clashGoodsInfoIdList)) {
                throw new SbcRuntimeException(
                        MarketingErrorCodeEnum.K080140, new Object[]{clashGoodsInfoIdList.size()}, clashGoodsInfoIdList);
            }
        }
        sale.setId(appointmentSale.getId());
        sale.setDelFlag(appointmentSale.getDelFlag());
        sale.setUpdateTime(LocalDateTime.now());
        sale.setCreatePerson(appointmentSale.getCreatePerson());
        sale.setCreateTime(appointmentSale.getCreateTime());
        appointmentSaleRepository.save(sale);

        List<AppointmentSaleGoods> oldAppointmentSaleGoods =
                appointmentSaleGoodsRepository.findByAppointmentSaleIdAndStoreId(
                        sale.getId(), sale.getStoreId());

        List<AppointmentSaleGoods> newSaleGoods = sale.getAppointmentSaleGoods();

        Map<String, AppointmentSaleGoods> oldAppointmentSaleGoodsMap =
                oldAppointmentSaleGoods.stream()
                        .collect(
                                Collectors.toMap(
                                        AppointmentSaleGoods::getGoodsInfoId, Function.identity()));

        newSaleGoods.forEach(
                n -> {
                    if (oldAppointmentSaleGoodsMap.containsKey(n.getGoodsInfoId())) {
                        AppointmentSaleGoods appointmentSaleGoods =
                                oldAppointmentSaleGoodsMap.get(n.getGoodsInfoId());
                        n.setCreatePerson(appointmentSaleGoods.getCreatePerson());
                        n.setCreateTime(appointmentSaleGoods.getCreateTime());
                        n.setId(appointmentSaleGoods.getId());
                        oldAppointmentSaleGoodsMap.remove(n.getGoodsInfoId());
                    }
                    n.setUpdateTime(LocalDateTime.now());
                    n.setUpdatePerson(sale.getUpdatePerson());
                    n.setAppointmentSaleId(appointmentSale.getId());
                    n.setStoreId(appointmentSale.getStoreId());
                });

        if (!oldAppointmentSaleGoodsMap.isEmpty()) {
            List<AppointmentSaleGoods> appointmentSaleGoodsList = new ArrayList<>();
            oldAppointmentSaleGoodsMap.forEach(
                    (k, v) -> {
                        appointmentSaleGoodsList.add(v);
                    });
            appointmentSaleGoodsRepository.deleteAll(appointmentSaleGoodsList);
            // 修改预约购买活动，被删除的商品需要同步删除缓存
            List<GoodsInfoMarketingCacheDTO> delList = new ArrayList<>();
            for (AppointmentSaleGoods saleGoods : appointmentSaleGoodsList) {
                GoodsInfoMarketingCacheDTO goodsInfoMarketingCacheDTO =
                        new GoodsInfoMarketingCacheDTO();
                goodsInfoMarketingCacheDTO.setId(appointmentSale.getId());
                goodsInfoMarketingCacheDTO.setBeginTime(appointmentSale.getAppointmentStartTime());
                goodsInfoMarketingCacheDTO.setEndTime(appointmentSale.getSnapUpEndTime());
                goodsInfoMarketingCacheDTO.setMarketingPluginType(
                        MarketingPluginType.APPOINTMENT_SALE);
                goodsInfoMarketingCacheDTO.setSkuId(saleGoods.getGoodsInfoId());
                goodsInfoMarketingCacheDTO.setPrice(saleGoods.getPrice());
                goodsInfoMarketingCacheDTO.setJoinLevel(
                        Arrays.stream(appointmentSale.getJoinLevel().split(","))
                                .map(x -> Long.valueOf(x))
                                .collect(Collectors.toList()));
                goodsInfoMarketingCacheDTO.setJoinLevelType(appointmentSale.getJoinLevelType());
                delList.add(goodsInfoMarketingCacheDTO);
            }
            this.skuCacheMarketingService.delSkuCacheMarketing(delList);
        }

        appointmentSaleGoodsRepository.saveAll(newSaleGoods);
        appointmentSale.setAppointmentSaleGoods(newSaleGoods);

        flushCache(newSaleGoods,appointmentSale);
        return appointmentSale;
    }

    /**
     * 单个删除预约抢购
     *
     * @author zxd
     */
    @Transactional
    public void deleteById(AppointmentSale sale) {
        appointmentSaleRepository.save(sale);
        flushCache(Collections.singletonList(sale),true);
    }

    /**
     * 批量删除预约抢购
     *
     * @author zxd
     */
    @Transactional
    public void deleteByIdList(List<AppointmentSale> infos) {
        appointmentSaleRepository.saveAll(infos);
        flushCache(infos,true);
    }

    /**
     * 单个查询预约抢购
     *
     * @author zxd
     */
    public AppointmentSale getOne(Long id, Long storeId) {
        if (Objects.isNull(storeId)) {
            return appointmentSaleRepository
                    .findByIdAndDelFlag(id, DeleteFlag.NO)
                    .orElseThrow(
                            () -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "预约抢购不存在"));
        }
        return appointmentSaleRepository
                .findByIdAndStoreIdAndDelFlag(id, storeId, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "预约抢购不存在"));
    }

    /**
     * 分页查询预约抢购
     *
     * @author zxd
     */
    public Page<AppointmentSale> page(AppointmentSaleQueryRequest queryReq) {

        Page<AppointmentSale> appointmentSalesPage =
                appointmentSaleRepository.findAll(
                        AppointmentSaleWhereCriteriaBuilder.build(queryReq),
                        queryReq.getPageRequest());
        return appointmentSalesPage;
    }

    /**
     * 列表查询预约抢购
     *
     * @author zxd
     */
    public List<AppointmentSale> list(AppointmentSaleQueryRequest request) {
        return appointmentSaleRepository.findAll(
                AppointmentSaleWhereCriteriaBuilder.build(request));
    }

    /**
     * 将实体包装成VO
     *
     * @author zxd
     */
    public AppointmentSaleVO wrapperVo(AppointmentSale appointmentSale) {
        if (appointmentSale != null) {
            AppointmentSaleVO appointmentSaleVO =
                    KsBeanUtil.convert(appointmentSale, AppointmentSaleVO.class);
            if(Objects.equals(appointmentSale.getPauseFlag(),NumberUtils.INTEGER_ONE)){
                appointmentSaleVO.setStatus(AppointmentStatus.SUSPENDED);
            }
            appointmentSaleVO.buildAppointmentFlag();
            return appointmentSaleVO;
        }
        return null;
    }

    @Transactional
    public void modifyStatus(AppointmentSale sale) {
        appointmentSaleRepository.save(sale);
        flushCache(Collections.singletonList(sale),false);
    }

    /**
     * 获取正在进行中预约活动
     *
     * @param goodsInfoId
     * @return
     */
    public AppointmentSale isInProcess(String goodsInfoId, String customerId) {
        Object object = appointmentSaleRepository.findByGoodsInfoIdInProcess(goodsInfoId);
        if (Objects.isNull(object)) {
            return null;
        }
        Object[] objects = (Object[]) object;
        AppointmentSale appointmentSale = KsBeanUtil.convert(objects[0], AppointmentSale.class);
        //如果会员Id不为空则验证用户等级是否满足活动要求
        if (StringUtils.isNotBlank(customerId)) {
            String joinLevel = appointmentSale.getJoinLevel();
            if (joinLevel.equals(Constants.STR_0)) {
                CustomerLevelByCustomerIdAndStoreIdResponse response = customerLevelQueryProvider.getCustomerLevelByCustomerIdAndStoreId(CustomerLevelByCustomerIdAndStoreIdRequest.builder().storeId(appointmentSale.getStoreId()).customerId(customerId).build()).getContext();
                if(Objects.isNull(response) || Objects.isNull(response.getLevelType())) {
                    return null;
                }

            } else if (!joinLevel.equals(Constants.STR_MINUS_1)) {
                List<Long> joinLevelList = Arrays.stream(appointmentSale.getJoinLevel().split(",")) .map(x -> Long.valueOf(x)) .collect(Collectors.toList());
                CustomerLevelByCustomerIdAndStoreIdResponse response = customerLevelQueryProvider.getCustomerLevelByCustomerIdAndStoreId(CustomerLevelByCustomerIdAndStoreIdRequest.builder().storeId(appointmentSale.getStoreId()).customerId(customerId).build()).getContext();
                if(Objects.isNull(response)) {
                    return null;
                }
                if (CollectionUtils.isNotEmpty(joinLevelList) && !joinLevelList.contains(response.getLevelId())) {
                    return null;
                }
            }
        }
        appointmentSale.setAppointmentSaleGood(
                KsBeanUtil.convert(objects[1], AppointmentSaleGoods.class));
        return appointmentSale;
    }

    /**
     * @param goodsInfoIdList
     * @return
     */
    public List<AppointmentSaleDO> inProgressAppointmentSaleInfoByGoodsInfoIdList(
            List<String> goodsInfoIdList) {
        if (CollectionUtils.isEmpty(goodsInfoIdList)) {
            return Collections.emptyList();
        }
        List list = appointmentSaleRepository.findByGoodsInfoIdInProcess(goodsInfoIdList);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<AppointmentSaleDO> appointmentSaleDOS = new ArrayList<>();
        list.forEach(
                o -> {
                    Object[] objects = (Object[]) o;
                    AppointmentSaleDO appointmentSaleDO =
                            KsBeanUtil.convert(objects[0], AppointmentSaleDO.class);
                    appointmentSaleDO.setAppointmentSaleGood(
                            KsBeanUtil.convert(objects[1], AppointmentSaleGoodsDO.class));
                    appointmentSaleDOS.add(appointmentSaleDO);
                });
        return appointmentSaleDOS;
    }

    /**
     * 根据spuid列表获取未结束的预约活动
     *
     * @param goodsId
     * @return
     */
    public List<AppointmentSaleDO> getNotEndActivity(String goodsId) {
        List list = appointmentSaleRepository.getNotEndActivity(goodsId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<AppointmentSaleDO> appointmentSaleDOS = new ArrayList<>();
        list.forEach(
                o -> {
                    Object[] objects = (Object[]) o;
                    AppointmentSaleDO appointmentSaleDO =
                            KsBeanUtil.convert(objects[0], AppointmentSaleDO.class);
                    appointmentSaleDO.setAppointmentSaleGood(
                            KsBeanUtil.convert(objects[1], AppointmentSaleGoodsDO.class));
                    appointmentSaleDOS.add(appointmentSaleDO);
                });
        return appointmentSaleDOS;
    }

    /** 下单时根据goodsInfoIds校验是否参加预约活动 */
    public void validParticipateInAppointmentSale(List<String> goodsInfoIdList) {
        if (CollectionUtils.isEmpty(goodsInfoIdList)) {
            return;
        }
        List appointmentList =
                appointmentSaleRepository.findByGoodsInfoIdInAppointment(goodsInfoIdList);
        if (CollectionUtils.isNotEmpty(appointmentList)) {
            throw new SbcRuntimeException(
                    MarketingErrorCodeEnum.K080170);
        }
    }

    /**
     * 分页查询预约抢购
     *
     * @author zxd
     */
    public MicroServicePage<AppointmentSaleVO> pageNew(AppointmentSaleQueryRequest queryReq) {
        Page<AppointmentSale> appointmentSalePage = this.page(queryReq);
        MicroServicePage<AppointmentSaleVO> newPage =
                KsBeanUtil.convertPage(appointmentSalePage, AppointmentSaleVO.class);
        if (CollectionUtils.isEmpty(newPage.getContent())) {
            return newPage;
        }

        List<AppointmentSaleVO> appointmentSaleVOS = newPage.getContent();

        List<Long> ids =
                appointmentSaleVOS.stream()
                        .map(AppointmentSaleVO::getId)
                        .collect(Collectors.toList());
        Map<Long, List<AppointmentSaleGoods>> saleGoodsMap =
                appointmentSaleGoodsService
                        .list(
                                AppointmentSaleGoodsQueryRequest.builder()
                                        .appointmentSaleIdList(ids)
                                        .build())
                        .stream()
                        .collect(Collectors.groupingBy(AppointmentSaleGoods::getAppointmentSaleId));

        List<MarketingCustomerLevelDTO> customerLevelDTOList =
                appointmentSaleVOS.stream()
                        .map(
                                m -> {
                                    MarketingCustomerLevelDTO dto = new MarketingCustomerLevelDTO();
                                    dto.setId(m.getId());
                                    dto.setStoreId(m.getStoreId());
                                    dto.setJoinLevel(m.getJoinLevel());
                                    return dto;
                                })
                        .collect(Collectors.toList());
        List<MarketingCustomerLevelVO> marketingCustomerLevelVOList =
                customerLevelQueryProvider
                        .listByCustomerLevelName(
                                new CustomerLevelListByCustomerLevelNameRequest(
                                        customerLevelDTOList))
                        .getContext()
                        .getCustomerLevelVOList();
        Map<Long, MarketingCustomerLevelVO> levelVOMap =
                marketingCustomerLevelVOList.stream()
                        .collect(
                                Collectors.toMap(
                                        MarketingCustomerLevelVO::getId, Function.identity()));
        appointmentSaleVOS.stream()
                .forEach(
                        s -> {
                            MarketingCustomerLevelVO levelVO = levelVOMap.get(s.getId());
                            s.setLevelName(levelVO.getLevelName());
                            s.setStoreName(levelVO.getStoreName());
                            if (saleGoodsMap.containsKey(s.getId())) {
                                s.setAppointmentCount(
                                        saleGoodsMap.get(s.getId()).stream()
                                                .mapToInt(AppointmentSaleGoods::getAppointmentCount)
                                                .sum());
                                s.setBuyerCount(
                                        saleGoodsMap.get(s.getId()).stream()
                                                .mapToInt(AppointmentSaleGoods::getBuyerCount)
                                                .sum());
                            }
                            s.buildStatus();
                        });
        return newPage;
    }

    /**
     * @description
     * @author zhanggaolei
     * @date 2021/7/22 2:46 下午
     * @param saleGoodsList
     * @param appointmentSale
     * @return void
     */
    private void flushCache(
            List<AppointmentSaleGoods> saleGoodsList, AppointmentSale appointmentSale) {
        // 刷新缓存
        List<GoodsInfoMarketingCacheDTO> cacheList = new ArrayList<>();
        for (AppointmentSaleGoods saleGoods : saleGoodsList) {
            GoodsInfoMarketingCacheDTO cacheDTO = new GoodsInfoMarketingCacheDTO();
            cacheDTO.setId(appointmentSale.getId());
            cacheDTO.setSkuId(saleGoods.getGoodsInfoId());
            cacheDTO.setMarketingPluginType(MarketingPluginType.APPOINTMENT_SALE);
            cacheDTO.setJoinLevel(
                    Arrays.stream(appointmentSale.getJoinLevel().split(","))
                            .map(x -> Long.valueOf(x))
                            .collect(Collectors.toList()));
            cacheDTO.setJoinLevelType(appointmentSale.getJoinLevelType());
            cacheDTO.setBeginTime(appointmentSale.getAppointmentStartTime());
            cacheDTO.setEndTime(appointmentSale.getSnapUpEndTime());
            cacheDTO.setPrice(saleGoods.getPrice());
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
    private void flushCache(List<AppointmentSale> saleList,Boolean delFlag) {
        List<Long> idList =
                saleList.stream().map(AppointmentSale::getId).collect(Collectors.toList());
        List<GoodsInfoMarketingCacheDTO> saveList = new ArrayList<>();
        List<AppointmentSaleGoods> list =
                appointmentSaleGoodsService.list(
                        AppointmentSaleGoodsQueryRequest.builder().appointmentSaleIdList(idList).build());
        Map<Long, List<AppointmentSaleGoods>> map =
                list.stream()
                        .collect(
                                Collectors.groupingBy(
                                        AppointmentSaleGoods::getAppointmentSaleId));
        List<GoodsInfoMarketingCacheDTO> delList = new ArrayList<>();
        for (AppointmentSale appointmentSale : saleList) {
            for (AppointmentSaleGoods saleGoods : map.get(appointmentSale.getId())) {
                GoodsInfoMarketingCacheDTO goodsInfoMarketingCacheDTO =
                        new GoodsInfoMarketingCacheDTO();
                goodsInfoMarketingCacheDTO.setId(appointmentSale.getId());
                goodsInfoMarketingCacheDTO.setBeginTime(appointmentSale.getAppointmentStartTime());
                goodsInfoMarketingCacheDTO.setEndTime(appointmentSale.getSnapUpEndTime());
                goodsInfoMarketingCacheDTO.setMarketingPluginType(
                        MarketingPluginType.APPOINTMENT_SALE);
                goodsInfoMarketingCacheDTO.setSkuId(saleGoods.getGoodsInfoId());
                goodsInfoMarketingCacheDTO.setPrice(saleGoods.getPrice());
                goodsInfoMarketingCacheDTO.setJoinLevel(
                        Arrays.stream(appointmentSale.getJoinLevel().split(","))
                                .map(x -> Long.valueOf(x))
                                .collect(Collectors.toList()));
                goodsInfoMarketingCacheDTO.setJoinLevelType(appointmentSale.getJoinLevelType());
                // 删除或者暂停
                if (appointmentSale.getDelFlag().equals(DeleteFlag.YES)
                        || appointmentSale.getPauseFlag() == 1 || true == delFlag) {
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
     * @author  xuyunpeng
     * @date 2021/6/24 1:57 下午
     * @param id 活动id
     * @param storeId 店铺id
     * @return
     */
    @Transactional
    public void closeActivity(Long id, Long storeId) {
        AppointmentSale appointmentSale = getOne(id, storeId);

        if (NumberUtils.INTEGER_ONE.equals(appointmentSale.getPauseFlag())
                || appointmentSale.getAppointmentStartTime().isAfter(LocalDateTime.now())
                || appointmentSale.getSnapUpEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080150);
        }

        if(LocalDateTime.now().isBefore(appointmentSale.getAppointmentEndTime())) {
            appointmentSale.setAppointmentEndTime(LocalDateTime.now());
            appointmentSale.setSnapUpStartTime(LocalDateTime.now());
            appointmentSale.setSnapUpEndTime(LocalDateTime.now());
        } else if (LocalDateTime.now().isBefore(appointmentSale.getSnapUpStartTime())) {
            appointmentSale.setSnapUpStartTime(LocalDateTime.now());
            appointmentSale.setSnapUpEndTime(LocalDateTime.now());
        } else {
            appointmentSale.setSnapUpEndTime(LocalDateTime.now());
        }
        appointmentSaleRepository.save(appointmentSale);
        flushCache(Collections.singletonList(appointmentSale),true);
    }


    /**
     * 同步到缓存
     */
    public void sycCache(){
        List<AppointmentSale> appointmentSaleList = this.appointmentSaleRepository.findByInProcess();
        if(CollectionUtils.isNotEmpty(appointmentSaleList)){
            flushCache(appointmentSaleList,false);
        }
    }

    /**
     * 自定义字段的列表查询
     * @param cols 列名
     * @return 列表
     */
    public Page<AppointmentSale> pageCols(AppointmentSaleQueryRequest request, List<String> cols) {
        CriteriaBuilder countCb = entityManager.getCriteriaBuilder();
        Specification<AppointmentSale> spec = AppointmentSaleWhereCriteriaBuilder.build(request);
        CriteriaQuery<Long> countCq = countCb.createQuery(Long.class);
        Root<AppointmentSale> countRt = countCq.from(AppointmentSale.class);
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
        Root<AppointmentSale> rt = cq.from(AppointmentSale.class);
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
    private List<AppointmentSale> converter(List<Tuple> result, List<String> cols) {
        return result.stream().map(item -> {
            AppointmentSale sale = new AppointmentSale();
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
