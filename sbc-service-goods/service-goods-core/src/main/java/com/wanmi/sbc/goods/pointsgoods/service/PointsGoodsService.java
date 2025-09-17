package com.wanmi.sbc.goods.pointsgoods.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsSalesModifyRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsSimplePageRequest;
import com.wanmi.sbc.goods.api.request.pointsgoodscate.PointsGoodsCateQueryRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.PointsGoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsInfoStockService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.mq.ProducerService;
import com.wanmi.sbc.goods.pointsgoods.model.root.PointsGoods;
import com.wanmi.sbc.goods.pointsgoods.model.root.SimplePointsGoods;
import com.wanmi.sbc.goods.pointsgoods.repository.PointsGoodsRepository;
import com.wanmi.sbc.goods.pointsgoods.repository.SimplePointsGoodsRepository;
import com.wanmi.sbc.goods.pointsgoodscate.model.root.PointsGoodsCate;
import com.wanmi.sbc.goods.pointsgoodscate.service.PointsGoodsCateService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.util.mapper.PointsGoodsMapper;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;

import io.seata.spring.annotation.GlobalTransactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>积分商品表业务逻辑</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Slf4j
@Service("PointsGoodsService")
public class PointsGoodsService {
    @Autowired
    private PointsGoodsRepository pointsGoodsRepository;

    @Autowired
    private SimplePointsGoodsRepository simplePointsGoodsRepository;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private PointsGoodsCateService pointsGoodsCateService;

    @Autowired
    private GoodsInfoStockService goodsInfoStockService;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    private PointsGoodsMapper pointsGoodsMapper;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private EntityManager entityManager;


    /**
     * 新增积分商品表
     *
     * @author yang
     */
    @Transactional
    public PointsGoods add(PointsGoods entity) {
        // 验证参数
        checkParam(Lists.newArrayList(entity));
        pointsGoodsRepository.save(entity);
        return entity;
    }

    @Transactional
    public void batchAdd(List<PointsGoods> pointsGoodsList) {
        pointsGoodsList.forEach(pointsGoods -> {
            if (Objects.isNull(pointsGoods.getRecommendFlag())) {
                pointsGoods.setRecommendFlag(BoolFlag.NO);
            }
        });
        checkParam(pointsGoodsList);
        pointsGoodsRepository.saveAll(pointsGoodsList);
    }

    /**
     * 修改积分商品表
     *
     * @author yang
     */
    @Transactional
    public PointsGoods modify(PointsGoods entity) {
        PointsGoods oldGoods = this.getById(entity.getPointsGoodsId());
        PointsGoodsStatus pointsGoodsStatus = getPointsGoodsStatus(oldGoods);
        // 活动已开始无法编辑
        if (!(PointsGoodsStatus.NOT_START == pointsGoodsStatus)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030096);
        }
        // 开启的活动才验证
        checkParam(Lists.newArrayList(entity));
        pointsGoodsRepository.save(entity);
        return entity;
    }

    @Transactional
    @GlobalTransactional
    public void modifyStatus(PointsGoods pointsGoods) {
        // 开启时才验证
        if (EnableStatus.ENABLE.equals(pointsGoods.getStatus())) {
            // 验证库存
            if (pointsGoods.getStock() == 0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030094);
            }
            // 验证店铺状态
            checkStoreStatus(Lists.newArrayList(pointsGoods.getGoodsInfo()));
            // 验证是否活动已结束
            PointsGoodsStatus pointsGoodsStatus = getPointsGoodsStatus(pointsGoods);
            if ((PointsGoodsStatus.ENDED == pointsGoodsStatus)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030091);
            }
        }
        pointsGoodsRepository.save(pointsGoods);
    }

    // 验证添加参数
    private void checkParam(List<PointsGoods> entitys) {
        // 验证分类是否已删除
        List<Integer> cateIds = entitys.stream().map(PointsGoods::getCateId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<String> goodsInfoIds = entitys.stream().map(PointsGoods::getGoodsInfoId).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(cateIds)) {
            List<PointsGoodsCate> pointsGoodsCates = pointsGoodsCateService.list(PointsGoodsCateQueryRequest.builder().cateIdList(cateIds).build());
            List<String> errorNames = new ArrayList<>();
            pointsGoodsCates.forEach(cate -> {
                if (cate.getDelFlag().equals(DeleteFlag.YES)) {
                    errorNames.add(cate.getCateName());
                }
            });

            if (CollectionUtils.isNotEmpty(errorNames)) {
                String message = errorNames.stream().collect(Collectors.joining(","));
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030098,
                        new Object[]{message});
            }
        }

        // 验证sku状态
        Map<String, GoodsInfo> goodsInfoMap = goodsInfoService.findByParams(GoodsInfoQueryRequest.builder()
                .goodsInfoIds(goodsInfoIds)
                .build()).stream().collect(Collectors.toMap(GoodsInfo::getGoodsInfoId, Function.identity()));
        List<String> ids = goodsInfoMap.values().stream()
                .filter(goodsInfo -> DeleteFlag.YES.equals(goodsInfo.getDelFlag()))
                .map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            String message = ids.stream().collect(Collectors.joining(","));
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030087,
                    new Object[]{message.substring(0, message.length() - 1 )});
        }
        // 验证店铺状态
        checkStoreStatus(Lists.newArrayList(goodsInfoMap.values()));
        // 验证兑换时间
        checkTime(entitys, goodsInfoMap);
    }

    // 验证店铺状态
    private void checkStoreStatus(List<GoodsInfo> goodsInfo) {
        // 判断店铺是否关店
        List<String> goodsIds = goodsInfo.stream().map(GoodsInfo::getGoodsId).distinct().collect(Collectors.toList());
        List<Goods> goods = goodsService.listByGoodsIds(goodsIds);
        List<Long> storeIds = goods.stream().map(Goods::getStoreId).distinct().collect(Collectors.toList());
        List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder()
                .storeIds(storeIds)
                .build()).getContext().getStoreVOList();
        List<Long> companyInfoIds = storeVOList.stream().map(storeVO -> storeVO.getCompanyInfo().getCompanyInfoId()).distinct().collect(Collectors.toList());

        EmployeeListRequest employeeListRequest = new EmployeeListRequest();
        employeeListRequest.setCompanyInfoIds(companyInfoIds);
        Map<Long, List<EmployeeListVO>> employeeMap = employeeQueryProvider.list(employeeListRequest)
                .getContext().getEmployeeList().stream().collect(Collectors.groupingBy(EmployeeListVO::getCompanyInfoId));

        storeVOList.stream().forEach(storeVO -> {
            if (storeVO.getStoreState().equals(StoreState.CLOSED)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030088,
                        new Object[]{storeVO.getStoreName()});
            }
            if (storeVO.getDelFlag().equals(DeleteFlag.YES)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030095,
                        new Object[]{storeVO.getStoreName()});
            }
            // 判断店铺是否已禁用
            List<EmployeeListVO> employeeList = employeeMap.get(storeVO.getCompanyInfo().getCompanyInfoId());
            if (Objects.isNull(employeeList)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030089,
                        new Object[]{storeVO.getStoreName()});
            } else {
                List<EmployeeListVO> listVOS = employeeList.stream()
                        .filter(employeeListVO -> employeeListVO.getDelFlag().equals(DeleteFlag.NO))
                        .filter(employeeListVO -> employeeListVO.getAccountState().equals(AccountState.ENABLE))
                        .collect(Collectors.toList());
                if (listVOS.size() == 0) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030089,
                            new Object[]{storeVO.getStoreName()});
                }
            }
        });
    }

    // 验证兑换时间
    private void checkTime(List<PointsGoods> entitys, Map<String, GoodsInfo> goodsInfoMap) {
        List<String> goodsInfoIds = entitys.stream().map(PointsGoods::getGoodsInfoId).collect(Collectors.toList());
        List<SimplePointsGoods> pointsGoodsList = simplelist(PointsGoodsQueryRequest.builder()
                .goodsInfoIds(goodsInfoIds)
                .delFlag(DeleteFlag.NO)
                .build());
        for (PointsGoods entity: entitys) {
            // 修改时排除自己在比较
            //修改sonar扫描出来的问题
            if (Objects.nonNull(entity.getPointsGoodsId()) && CollectionUtils.isNotEmpty(pointsGoodsList)) {
                pointsGoodsList = pointsGoodsList.stream()
                        .filter(pointsGoods -> !pointsGoods.getPointsGoodsId().equals(entity.getPointsGoodsId()))
                        .collect(Collectors.toList());
            }
            // 验证结束时间是否在开始时间之前
            if (!entity.getBeginTime().isBefore(entity.getEndTime())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030093);
            }
            if (Objects.nonNull(pointsGoodsList) && pointsGoodsList.size() > 0) {
                for (SimplePointsGoods pointsGoods : pointsGoodsList) {
                    // 开始时间等于已绑定积分商品的开始时间
                    if (pointsGoods.getBeginTime().isEqual(entity.getBeginTime()) ||
                            // 开始时间等于已绑定积分商品的结束时间
                            pointsGoods.getEndTime().isEqual(entity.getBeginTime()) ||
                            // 开始时间在已绑定积分商品的时间段内
                            (pointsGoods.getBeginTime().isBefore(entity.getBeginTime())
                                    && pointsGoods.getEndTime().isAfter(entity.getBeginTime())) ||
                            // 结束时间等于已绑定积分商品的开始时间
                            pointsGoods.getBeginTime().isEqual(entity.getEndTime()) ||
                            // 结束时间等于已绑定积分商品的结束时间
                            pointsGoods.getEndTime().isEqual(entity.getEndTime()) ||
                            // 结束时间在已绑定积分商品的时间段内
                            (pointsGoods.getBeginTime().isBefore(entity.getEndTime())
                                    && pointsGoods.getEndTime().isAfter(entity.getEndTime())) ||
                            // 该商品绑定积分商品的时间段在该商品时间段内
                            (pointsGoods.getBeginTime().isAfter(entity.getBeginTime())
                                    && pointsGoods.getEndTime().isBefore(entity.getEndTime()))
                    ) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030090,
                                new Object[]{goodsInfoMap.get(pointsGoods.getGoodsInfoId()).getGoodsInfoName(),
                                        pointsGoods.getBeginTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                                        pointsGoods.getEndTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))});
                    }
                    // 兑换开始时间应大于当前时间
                    if (entity.getBeginTime().isBefore(LocalDateTime.now())) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030092);
                    }
                }
            }
        }
    }

    /**
     * 单个删除积分商品表
     *
     * @author yang
     */
    @Transactional
    public void deleteById(String id) {
        PointsGoods pointsGoods = getById(id);
        PointsGoodsStatus pointsGoodsStatus = getPointsGoodsStatus(pointsGoods);
        // 活动开始无法删除
        if (!(PointsGoodsStatus.NOT_START == pointsGoodsStatus)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030097);
        }
        // 加库存
        // goodsInfoRepository.addStockById(pointsGoods.getStock(), pointsGoods.getGoodsInfoId());
        goodsInfoStockService.addStockById(pointsGoods.getStock(), pointsGoods.getGoodsInfoId(),pointsGoods.getGoodsId());
        pointsGoodsRepository.modifyDelFlagById(id);
    }


    /**
     * 批量删除积分商品表
     *
     * @author yang
     */
    @Transactional
    public void deleteByIdList(List<String> ids) {
        pointsGoodsRepository.deleteByIdList(ids);
    }

    /**
     * 单个查询积分商品表
     *
     * @author yang
     */

    public PointsGoods getById(String id) {
        PointsGoods pointsGoods = pointsGoodsRepository.findById(id)
                .orElseThrow(() -> new SbcRuntimeException( CommonErrorCodeEnum.K999999, new Object[]{"积分商品信息不存在"},this.getDeleteIndex(id)));
        if (DeleteFlag.YES.equals(pointsGoods.getDelFlag())) {
            throw new SbcRuntimeException( CommonErrorCodeEnum.K999999,new Object[]{"积分商品信息不存在"} ,this.getDeleteIndex(id));
        }
        return pointsGoods;
    }

    /**
     * 根据积分商品Id减库存
     *
     * @param stock         库存数
     * @param pointsGoodsId 积分商品Id
     */
    @Transactional
    @GlobalTransactional
    public void subStockById(Long stock, String pointsGoodsId) {
        int updateCount = pointsGoodsRepository.subStockById(stock, pointsGoodsId);
        PointsGoods pointsGoods = this.getById(pointsGoodsId);
        if (pointsGoods.getStock() == 0) {
            pointsGoods.setStatus(EnableStatus.DISABLE);
            modifyStatus(pointsGoods);
        }
        if (updateCount <= 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030061);
        }

        if (!ThirdPlatformType.LINKED_MALL.equals(pointsGoods.getGoodsInfo().getThirdPlatformType())) {
            GoodsInfoMinusStockDTO dto = new GoodsInfoMinusStockDTO();
            dto.setGoodsInfoId(pointsGoods.getGoodsInfoId());
            dto.setStock(stock);
            goodsInfoService.batchSubStock(Collections.singletonList(dto));
        }
    }

    /**
     * 根据积分商品Id库存清零并停用
     *
     * @param pointsGoodsId
     */
    @Transactional
    public void resetStockById(String pointsGoodsId) {
        pointsGoodsRepository.resetStockById(pointsGoodsId);
    }

    /**
     * 分页查询积分商品表
     *
     * @author yang
     */
    public Page<PointsGoods> page(PointsGoodsQueryRequest queryReq) {
        Page<PointsGoods> pointsGoodsPage = pointsGoodsRepository.findAll(
                PointsGoodsWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = pointsGoodsPage.getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getGoods().getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getGoods().getThirdPlatformSpuId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(itemIds)) {
            List<LinkedMallStockVO> stocks =
                    linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
            if (stocks != null) {
                for (PointsGoods pointsGoods : pointsGoodsPage.getContent()) {
                    Optional<LinkedMallStockVO> stock = stocks.stream()
                            .filter(v -> String.valueOf(v.getItemId()).equals(pointsGoods.getGoods().getThirdPlatformSpuId())).findFirst();
                    if (stock.isPresent()) {
                        Long spuStock = stock.get().getSkuList().stream().map(v -> v.getStock()).reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                        pointsGoods.getGoods().setStock(spuStock);
                        Optional<LinkedMallStockVO.SkuStock> skuStock = stock.get().getSkuList().stream()
                                .filter(v -> String.valueOf(v.getSkuId()).equals(pointsGoods.getGoodsInfo().getThirdPlatformSkuId())).findFirst();
                        if (skuStock.isPresent()) {
                            GoodsInfo goodsInfo = pointsGoods.getGoodsInfo();
                            Long quantity = skuStock.get().getStock();
                            goodsInfo.setStock(quantity);
                        }
                    }
                }
            }
        }
        List<GoodsInfo> goodsInfos = pointsGoodsPage.getContent().stream().map(PointsGoods::getGoodsInfo).collect(Collectors.toList());
        goodsInfoService.updateGoodsInfoSupplyPriceAndStock(goodsInfos);
        return pointsGoodsPage;
    }

    /**
     * 列表查询积分商品表
     *
     * @author yang
     */
    @Transactional(rollbackFor = {Exception.class})
    public List<PointsGoods> list(PointsGoodsQueryRequest queryReq) {
        Sort sort = queryReq.getSort();
        if (Objects.nonNull(sort)) {
            return pointsGoodsRepository.findAll(PointsGoodsWhereCriteriaBuilder.build(queryReq), sort);
        } else {
            return pointsGoodsRepository.findAll(PointsGoodsWhereCriteriaBuilder.build(queryReq));
        }
    }

    /**
     * 列表查询积分商品表
     *
     * @author yang
     */
    public List<SimplePointsGoods> simplelist(PointsGoodsQueryRequest queryReq) {
        Sort sort = queryReq.getSort();
        if (Objects.nonNull(sort)) {
            return simplePointsGoodsRepository.findAll(SimplePointsGoodsWhereCriteriaBuilder.build(queryReq), sort);
        } else {
            return simplePointsGoodsRepository.findAll(SimplePointsGoodsWhereCriteriaBuilder.build(queryReq));
        }
    }

    /**
     * 查询过期的积分商品
     *
     * @return
     */
    public List<PointsGoods> queryOverdueList() {
        return pointsGoodsRepository.queryOverdueList();
    }

    /**
     * 根据店铺id查询
     *
     * @param storeId
     * @return
     */
    public List<PointsGoods> getByStoreId(Long storeId) {
        return pointsGoodsRepository.getByStoreId(storeId);
    }

    /**
     * 根据店铺id更新状态
     *
     * @param storeId
     * @return
     */
    @Transactional
    public void modifyStatusByStoreId(Long storeId, EnableStatus status) {
        List<Goods> goods =
                goodsService.findAll(GoodsQueryRequest.builder().storeId(storeId).delFlag(DeleteFlag.NO.toValue()).build());
        List<String> goodsIds = goods.stream().map(Goods::getGoodsId).toList();
        if (CollectionUtils.isEmpty(goodsIds)) {
            return;
        }
        pointsGoodsRepository.modifyStatusByStoreId(goodsIds, status.toValue());
    }

    /**
     * 将实体包装成VO
     *
     * @author yang
     */
    public PointsGoodsVO wrapperVo(PointsGoods pointsGoods) {
        if (pointsGoods != null) {
            PointsGoodsVO pointsGoodsVO = new PointsGoodsVO();
            KsBeanUtil.copyPropertiesThird(pointsGoods, pointsGoodsVO);
            PointsGoodsCate pointsGoodsCate = pointsGoods.getPointsGoodsCate();
            if (Objects.nonNull(pointsGoodsCate)) {
                PointsGoodsCateVO pointsGoodsCateVO = new PointsGoodsCateVO();
                KsBeanUtil.copyPropertiesThird(pointsGoodsCate, pointsGoodsCateVO);
                pointsGoodsVO.setPointsGoodsCate(pointsGoodsCateVO);
            }
            Goods goods = pointsGoods.getGoods();
            if (Objects.nonNull(goods)) {
                GoodsVO goodsVO = new GoodsVO();
                KsBeanUtil.copyPropertiesThird(goods, goodsVO);
                pointsGoodsVO.setGoods(goodsVO);
            }
            GoodsInfo goodsInfo = pointsGoods.getGoodsInfo();
            if (Objects.nonNull(goodsInfo)) {
                GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                KsBeanUtil.copyPropertiesThird(goodsInfo, goodsInfoVO);
                StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder()
                        .storeId(goodsInfo.getStoreId())
                        .build()).getContext().getStoreVO();
                // 店铺名称
                goodsInfoVO.setStoreName(storeVO.getStoreName());
                // 最大可兑换库存
                pointsGoodsVO.setMaxStock(goodsInfo.getStock() + pointsGoods.getStock());
                pointsGoodsVO.setGoodsInfo(goodsInfoVO);
            }
            PointsGoodsStatus pointsGoodsStatus = getPointsGoodsStatus(pointsGoods);
            pointsGoodsVO.setPointsGoodsStatus(pointsGoodsStatus);
            String goodsInfoId = pointsGoods.getGoodsInfoId();
            List<GoodsInfoSpecDetailRel> GoodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsInfoId(goodsInfoId);
            pointsGoodsVO.setSpecText(StringUtils.join(GoodsInfoSpecDetailRels.stream()
                    .map(GoodsInfoSpecDetailRel::getDetailName)
                    .collect(Collectors.toList()), " "));
            return pointsGoodsVO;
        }
        return null;
    }

    /**
     * 获取积分商品活动状态
     *
     * @param pointsGoods
     * @return
     */
    public PointsGoodsStatus getPointsGoodsStatus(PointsGoods pointsGoods) {
        if (LocalDateTime.now().isBefore(pointsGoods.getBeginTime())) {
            return PointsGoodsStatus.NOT_START;
        } else if (LocalDateTime.now().isAfter(pointsGoods.getEndTime())) {
            return PointsGoodsStatus.ENDED;
        } else {
            if (pointsGoods.getStatus().equals(EnableStatus.DISABLE)) {
                return PointsGoodsStatus.PAUSED;
            } else {
                return PointsGoodsStatus.STARTED;
            }
        }
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 更新积分商品销量数据
     * @Date 10:38 2019/5/29
     * @Param [pointsGoodsSalesModifyRequest]
     **/
    @Transactional
    public void updatePointsGoodsSalesNum(PointsGoodsSalesModifyRequest pointsGoodsSalesModifyRequest) {
        pointsGoodsRepository.updatePointsGoodsSalesNum(pointsGoodsSalesModifyRequest.getSalesNum(), pointsGoodsSalesModifyRequest.getPointsGoodsId());
        //更新ES增加销量
        producerService.addSales(pointsGoodsSalesModifyRequest.getPointsGoodsId(), pointsGoodsSalesModifyRequest.getSalesNum());
    }

    /**
     * 将实体包装成VO
     *
     * @author yang
     */
    public PointsGoodsVO wrapperBaseVo(PointsGoods pointsGoods) {
        if (pointsGoods != null) {
            PointsGoodsVO pointsGoodsVO = new PointsGoodsVO();
            KsBeanUtil.copyPropertiesThird(pointsGoods, pointsGoodsVO);
            PointsGoodsCate pointsGoodsCate = pointsGoods.getPointsGoodsCate();
            if (Objects.nonNull(pointsGoodsCate)) {
                PointsGoodsCateVO pointsGoodsCateVO = new PointsGoodsCateVO();
                KsBeanUtil.copyPropertiesThird(pointsGoodsCate, pointsGoodsCateVO);
                pointsGoodsVO.setPointsGoodsCate(pointsGoodsCateVO);
            }
            Goods goods = pointsGoods.getGoods();
            if (Objects.nonNull(goods)) {
                GoodsVO goodsVO = new GoodsVO();
                KsBeanUtil.copyPropertiesThird(goods, goodsVO);
                pointsGoodsVO.setGoods(goodsVO);
            }
            GoodsInfo goodsInfo = pointsGoods.getGoodsInfo();
            if (Objects.nonNull(goodsInfo)) {
                GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                KsBeanUtil.copyPropertiesThird(goodsInfo, goodsInfoVO);
                // 最大可兑换库存
                pointsGoodsVO.setMaxStock(goodsInfo.getStock() + pointsGoods.getStock());
                pointsGoodsVO.setGoodsInfo(goodsInfoVO);
            }
            PointsGoodsStatus pointsGoodsStatus = getPointsGoodsStatus(pointsGoods);
            pointsGoodsVO.setPointsGoodsStatus(pointsGoodsStatus);
            String goodsInfoId = pointsGoods.getGoodsInfoId();
            List<GoodsInfoSpecDetailRel> GoodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsInfoId(goodsInfoId);
            pointsGoodsVO.setSpecText(StringUtils.join(GoodsInfoSpecDetailRels.stream()
                    .map(GoodsInfoSpecDetailRel::getDetailName)
                    .collect(Collectors.toList()), " "));
            return pointsGoodsVO;
        }
        return null;
    }


    /**
     * 简易分页查询
     *
     * @param request
     * @return 积分商品分页信息
     */
    public MicroServicePage<PointsGoodsVO> simplePage(PointsGoodsSimplePageRequest request) {
        PointsGoodsQueryRequest queryRequest = new PointsGoodsQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        queryRequest.setDelFlag(DeleteFlag.NO);
        //分页优化，当百万数据时，先分页提取pointsGoodsIds
        if (CollectionUtils.isEmpty(queryRequest.getPointsGoodsIds())) {
            Page<String> ids = this.findIdsByCondition(queryRequest);
            if (CollectionUtils.isEmpty(ids.getContent())) {
                return new MicroServicePage<>(Collections.emptyList(), request.getPageable(), ids.getTotalElements());
            }
            queryRequest.setPointsGoodsIds(ids.getContent());
            List<SimplePointsGoods> pointsGoods = simplePointsGoodsRepository.findAll(
                    SimplePointsGoodsWhereCriteriaBuilder.build(queryRequest), request.getSort());
            List<PointsGoodsVO> pointsGoodsVOS = pointsGoods.stream()
                    .map(s -> pointsGoodsMapper.simpleToVO(s)).collect(Collectors.toList());
            return new MicroServicePage<>(pointsGoodsVOS, ids.getPageable(), ids.getTotalElements());
        }
        Page<SimplePointsGoods> simpleVOS = simplePointsGoodsRepository.findAll(
                SimplePointsGoodsWhereCriteriaBuilder.build(queryRequest), request.getPageRequest());
        if (CollectionUtils.isEmpty(simpleVOS.getContent())) {
            return new MicroServicePage<>(Collections.emptyList(), simpleVOS.getPageable(), simpleVOS.getTotalElements());
        }
        List<PointsGoodsVO> pointsGoodsVOS = simpleVOS.getContent().stream()
                .map(s -> pointsGoodsMapper.simpleToVO(s)).collect(Collectors.toList());
        return new MicroServicePage<>(pointsGoodsVOS, simpleVOS.getPageable(), simpleVOS.getTotalElements());
    }

    /**
     * 分页提取goodsId
     *
     * @param request 条件请求
     * @return goodsId的分页列表
     */
    private Page<String> findIdsByCondition(PointsGoodsQueryRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<SimplePointsGoods> rt = cq.from(SimplePointsGoods.class);
        cq.select(rt.get("pointsGoodsId"));
        Specification<SimplePointsGoods> spec = SimplePointsGoodsWhereCriteriaBuilder.build(request);
        Predicate predicate = spec.toPredicate(rt, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }
        Sort sort = request.getSort();
        if (sort.isSorted()) {
            cq.orderBy(QueryUtils.toOrders(sort, rt, cb));
        }
        cq.orderBy(QueryUtils.toOrders(request.getSort(), rt, cb));
        TypedQuery<String> query = entityManager.createQuery(cq);
        query.setFirstResult((int) request.getPageRequest().getOffset());
        query.setMaxResults(request.getPageRequest().getPageSize());

        return PageableExecutionUtils.getPage(query.getResultList(), request.getPageable(), () -> {
            CriteriaBuilder countCb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> countCq = countCb.createQuery(Long.class);
            Root<SimplePointsGoods> crt = countCq.from(SimplePointsGoods.class);
            countCq.select(countCb.count(crt));
            if (spec.toPredicate(crt, countCq, countCb) != null) {
                countCq.where(spec.toPredicate(crt, countCq, countCb));
            }
            return entityManager.createQuery(countCq).getResultList().stream().filter(Objects::nonNull).mapToLong(s -> s).sum();
        });
    }

    /**
     * 拼凑删除es-提供给findOne去调
     *
     * @param id 编号
     * @return "es_points_goods_info:{id}"
     */
    private Object getDeleteIndex(String id) {
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.DOC_POINTS_GOODS_INFO_TYPE, id);
    }

    @Transactional
    public void modifyEndTime(String pointsGoodsId) {
        PointsGoods pointsGoods = getById(pointsGoodsId);
        //进行中才能关闭
        if (EnableStatus.DISABLE.equals(pointsGoods.getStatus())
                || pointsGoods.getBeginTime().isAfter(LocalDateTime.now())
                || pointsGoods.getEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030099);
        }
        pointsGoodsRepository.modifyEndTime(pointsGoodsId);
    }

    /**
     * 根据货品Id查询是否是积分商品
     * @author  wur
     * @date: 2021/6/8 20:38
     * @param goodsInfoId 商品ID
     * @return  积分商品信息
     **/
    public List<PointsGoods> getByGoodsInfoId(String goodsInfoId) {
        return pointsGoodsRepository.getByGoodsInfoId(goodsInfoId);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateCateId(Integer cateId) {
        pointsGoodsRepository.updateCateId(cateId,DeleteFlag.NO);
    }
}
