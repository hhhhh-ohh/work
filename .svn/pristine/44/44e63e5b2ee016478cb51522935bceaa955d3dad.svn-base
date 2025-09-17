package com.wanmi.sbc.goods.standard.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.standard.model.root.StandardGoods;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.standard.request.StandardSkuQueryRequest;
import com.wanmi.sbc.goods.standard.request.StandardSkuSaveRequest;
import com.wanmi.sbc.goods.standard.response.StandardSkuEditResponse;
import com.wanmi.sbc.goods.standardspec.model.root.StandardSkuSpecDetailRel;
import com.wanmi.sbc.goods.standardspec.repository.StandardSkuSpecDetailRelRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSpecDetailRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSpecRepository;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 商品服务
 * Created by daiyitian on 2017/4/11.
 */
@Service
public class StandardSkuService {

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private StandardSkuRepository standardInfoRepository;

    @Autowired
    private StandardSpecRepository standardSpecRepository;

    @Autowired
    private StandardSpecDetailRepository standardSpecDetailRepository;

    @Autowired
    private StandardSkuSpecDetailRelRepository standardInfoSpecDetailRelRepository;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    private StandardGoodsService standardGoodsService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StandardSkuStockService standardSkuStockService;

    /**
     * 根据ID查询商品SKU
     *
     * @param standardInfoId 商品SKU编号
     * @return list
     */
    @Transactional(readOnly = true, timeout = 10, propagation = Propagation.REQUIRES_NEW)
    public StandardSkuEditResponse findById(String standardInfoId) throws SbcRuntimeException {
        StandardSkuEditResponse response = new StandardSkuEditResponse();
        StandardSku skuInfo = standardInfoRepository.findById(standardInfoId).orElse(null);
        if (skuInfo == null || DeleteFlag.YES.toValue() == skuInfo.getDelFlag().toValue()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030155);
        }

        StandardGoods spu = standardGoodsRepository.findById(skuInfo.getGoodsId()).orElse(null);
        if (spu == null) {
            throw new SbcRuntimeException(standardGoodsService.getDeleteIndex(skuInfo.getGoodsId()), GoodsErrorCodeEnum.K030155);
        }

        StandardSkuVO standardSku = KsBeanUtil.copyPropertiesThird(skuInfo, StandardSkuVO.class);
        StandardGoodsVO standard = KsBeanUtil.copyPropertiesThird(spu, StandardGoodsVO.class);

        //如果是多规格
        if (Constants.yes.equals(standard.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(standardSpecRepository.findByGoodsId(standard.getGoodsId()), StandardSpecVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(standardSpecDetailRepository.findByGoodsId(standard.getGoodsId()), StandardSpecDetailVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().stream().forEach(standardSpec -> {
                standardSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(standardSpec.getSpecId())).map(StandardSpecDetailVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            List<StandardSkuSpecDetailRel> standardInfoSpecDetailRels = standardInfoSpecDetailRelRepository.findByGoodsId(standard.getGoodsId());
            standardSku.setMockSpecIds(standardInfoSpecDetailRels.stream().filter(detailRel -> detailRel.getGoodsInfoId().equals(standardSku.getGoodsInfoId())).map(StandardSkuSpecDetailRel::getSpecId).collect(Collectors.toList()));
            standardSku.setMockSpecDetailIds(standardInfoSpecDetailRels.stream().filter(detailRel -> detailRel.getGoodsInfoId().equals(standardSku.getGoodsInfoId())).map(StandardSkuSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            standardSku.setSpecText(StringUtils.join(standardInfoSpecDetailRels.stream().filter(specDetailRel -> standardSku.getGoodsInfoId().equals(specDetailRel.getGoodsInfoId())).map(StandardSkuSpecDetailRel::getDetailName).collect(Collectors.toList()), " "));
        }
        //如果是linkedmall商品，实时查库存
        if (Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standard.getGoodsSource())) {
            List<LinkedMallStockVO> stocks = null;
            if (standard.getThirdPlatformSpuId() != null) {
                stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(standard.getThirdPlatformSpuId())), "0", null)).getContext();
            }
            if (stocks != null) {
                Optional<LinkedMallStockVO> optional = stocks.stream()
                        .filter(v -> v.getItemId().equals(Long.valueOf(standard.getThirdPlatformSpuId())))
                        .findFirst();
                if (optional.isPresent()) {
                    Long totalStock = optional.get().getSkuList().stream()
                            .map(v -> v.getStock())
                            .reduce(0L, ((aLong, aLong2) -> aLong + aLong2));
                    standard.setStock(totalStock);
                }
                for (LinkedMallStockVO spuStock : stocks) {
                    for (LinkedMallStockVO.SkuStock sku : spuStock.getSkuList()) {
                        if (String.valueOf(spuStock.getItemId()).equals(standardSku.getThirdPlatformSpuId()) && String.valueOf(sku.getSkuId()).equals(standardSku.getThirdPlatformSkuId())) {
                            standardSku.setStock(sku.getStock());
                        }
                    }
                }
            }
        }
        response.setGoodsInfo(standardSku);
        response.setGoods(standard);
        return response;
    }

    /**
     * 商品SKU更新
     *
     * @param saveRequest 参数
     * @throws SbcRuntimeException 业务异常
     */
    @Transactional
    public StandardSku edit(StandardSkuSaveRequest saveRequest) throws SbcRuntimeException {
        StandardSku newStandardSku = KsBeanUtil.convert(saveRequest.getGoodsInfo(), StandardSku.class);
        StandardSku oldStandardSku = standardInfoRepository.findById(newStandardSku.getGoodsInfoId()).orElse(null);
        if (oldStandardSku == null || oldStandardSku.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030155);
        }
        newStandardSku.setUpdateTime(LocalDateTime.now());
        KsBeanUtil.copyProperties(newStandardSku, oldStandardSku);
        standardInfoRepository.save(oldStandardSku);
        return oldStandardSku;
    }


    /**
     * 批量查询
     *
     * @param request 参数
     * @return 商品库列表
     */
    public List<StandardSku> findAll(StandardSkuQueryRequest request) {
        return this.standardInfoRepository.findAll(request.getWhereCriteria());
    }

    /**
     * @description 根据关联skuId更新上下架
     * @author  daiyitian
     * @date 2021/4/13 15:39
     * @param addedFlag 上下架
     * @param reGoodsIds 关联spuId
     * @param reSkuId 关联skuId
     * @return void
     **/
    public void modifyAddedFlag(
            AddedFlag addedFlag, List<String> reGoodsIds, List<String> reSkuId) {
        List<String> standardIds = standardGoodsService.getUsedStandardByGoodsIds(reGoodsIds);
        if (CollectionUtils.isNotEmpty(standardIds)) {
            for (String standardId : standardIds) {
                List<StandardSku> standardSkuList =
                        standardInfoRepository.findByGoodsId(standardId);
                standardSkuList.stream()
                        .filter(sku -> reSkuId.contains(sku.getProviderGoodsInfoId()))
                        .forEach(sku -> sku.setAddedFlag(Integer.valueOf(addedFlag.toValue())));

                long addedOnLen =
                        standardSkuList.stream()
                                .filter(
                                        sku ->
                                                Integer.valueOf(AddedFlag.NO.toValue())
                                                        .equals(sku.getAddedFlag()))
                                .count();
                if (addedOnLen == standardSkuList.size()) {
                    standardGoodsRepository.updateAddedFlag(standardId, AddedFlag.NO.toValue());
                } else if (addedOnLen == 0) {
                    standardGoodsRepository.updateAddedFlag(standardId, AddedFlag.YES.toValue());
                } else {
                    standardGoodsRepository.updateAddedFlag(standardId, AddedFlag.PART.toValue());
                }
            }
        }
    }

    /**
     * 自定义字段的列表查询
     * @param request 参数
     * @param cols 列名
     * @return 列表
     */
    public List<StandardSku> listCols(StandardSkuQueryRequest request, List<String> cols) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<StandardSku> rt = cq.from(StandardSku.class);
        cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
        Specification<StandardSku> spec = request.getWhereCriteria();
        Predicate predicate = spec.toPredicate(rt, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }
        Sort sort = request.getSort();
        if (sort.isSorted()) {
            cq.orderBy(QueryUtils.toOrders(sort, rt, cb));
        }
        cq.orderBy(QueryUtils.toOrders(request.getSort(), rt, cb));
        return this.converter(entityManager.createQuery(cq).getResultList(), cols);
    }


    /**
     * 查询对象转换
     * @param result
     * @return
     */
    private List<StandardSku> converter(List<Tuple> result, List<String> cols) {
        return result.stream().map(item -> {
            StandardSku sku = new StandardSku();
            sku.setGoodsId(toString(item, "goodsId", cols));
            sku.setGoodsInfoId(toString(item,"goodsInfoId", cols));
            sku.setGoodsInfoNo(toString(item,"goodsInfoNo", cols));
            sku.setMarketPrice(toBigDecimal(item,"marketPrice", cols));
            sku.setCostPrice(toBigDecimal(item,"costPrice", cols));
            sku.setStock(toLong(item,"stock", cols));
            sku.setThirdPlatformSpuId(toString(item,"thirdPlatformSpuId", cols));
            sku.setThirdPlatformSkuId(toString(item,"thirdPlatformSkuId", cols));
            sku.setSupplyPrice(toBigDecimal(item, "supplyPrice", cols));
            return sku;
        }).collect(Collectors.toList());
    }


    private String toString(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        Object value = tuple.get(name);
        return value != null ? value.toString() : null;
    }

    private BigDecimal toBigDecimal(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        Object value = tuple.get(name);
        return value != null ? new BigDecimal(value.toString()) : null;
    }

    private Long toLong(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        Object value = tuple.get(name);
        return value != null ? NumberUtils.toLong(value.toString()) : null;
    }

    /**
     * 条件查询SKU数据
     *
     * @param request 查询条件
     * @return 商品sku列表
     */
    public List<StandardSku> findByParams(StandardSkuQueryRequest request) {
        return this.standardInfoRepository.findAll(request.getWhereCriteria());
    }

    /**
     * 填充供应商库存、价格、有效性
     * @param goodsInfoList
     */
    public void fillSupplyPriceAndStock(List<StandardSkuVO> goodsInfoList){
        List<String> providerGoodsInfoIds = goodsInfoList.stream()
                .filter(standardSku -> nonNull(standardSku) && StringUtils.isNotBlank(standardSku.getProviderGoodsInfoId()))
                .map(StandardSkuVO::getProviderGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)) {
            List<StandardSku> providerGoodsInfoList = standardInfoRepository.findByGoodsInfoIds(providerGoodsInfoIds);
            Map<String, Long> stockMap = standardSkuStockService.getRedisStockByGoodsInfoIds(providerGoodsInfoIds);

            Map<String, StandardSku> skuMap = providerGoodsInfoList.stream().collect(Collectors.toMap(StandardSku::getGoodsInfoId, Function.identity()));

            goodsInfoList.forEach(
                    goodsInfo -> {
                        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
                            StandardSku providerGoodsInfo =
                                    skuMap.get(goodsInfo.getProviderGoodsInfoId());
                            if (nonNull(providerGoodsInfo)) {
                                if (MapUtils.isNotEmpty(stockMap)
                                        && skuMap.get(providerGoodsInfo.getGoodsInfoId()) != null) {
                                    Long stock = stockMap.get(providerGoodsInfo.getGoodsInfoId());
                                    if (Objects.isNull(stock)) {
                                        goodsInfo.setStock(providerGoodsInfo.getStock());
                                    } else {
                                        goodsInfo.setStock(stock);
                                    }
                                } else {
                                    goodsInfo.setStock(providerGoodsInfo.getStock());
                                }
                                goodsInfo.setSupplyPrice(providerGoodsInfo.getSupplyPrice());
                            }
                        }
                    });
        }
    }
}
