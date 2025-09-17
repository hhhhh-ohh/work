package com.wanmi.sbc.goods.standard.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.dto.StandardSkuDTO;
import com.wanmi.sbc.goods.bean.dto.StandardSpecDTO;
import com.wanmi.sbc.goods.bean.dto.StandardSpecDetailDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.StandardGoodsVO;
import com.wanmi.sbc.goods.bean.vo.StandardImageVO;
import com.wanmi.sbc.goods.bean.vo.StandardPropDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.StandardSkuSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.StandardSkuVO;
import com.wanmi.sbc.goods.bean.vo.StandardSpecDetailVO;
import com.wanmi.sbc.goods.bean.vo.StandardSpecVO;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.repository.GoodsBrandRepository;
import com.wanmi.sbc.goods.brand.request.GoodsBrandQueryRequest;
import com.wanmi.sbc.goods.brand.service.GoodsBrandService;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.request.GoodsCateQueryRequest;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoods;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateGoodsRepository;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.standard.model.root.StandardGoods;
import com.wanmi.sbc.goods.standard.model.root.StandardGoodsRel;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardPropDetailRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.standard.request.StandardQueryRequest;
import com.wanmi.sbc.goods.standard.request.StandardSaveRequest;
import com.wanmi.sbc.goods.standard.request.StandardSkuQueryRequest;
import com.wanmi.sbc.goods.standard.response.StandardEditResponse;
import com.wanmi.sbc.goods.standard.response.StandardQueryResponse;
import com.wanmi.sbc.goods.standardimages.model.root.StandardImage;
import com.wanmi.sbc.goods.standardimages.repository.StandardImageRepository;
import com.wanmi.sbc.goods.standardspec.model.root.StandardSkuSpecDetailRel;
import com.wanmi.sbc.goods.standardspec.model.root.StandardSpec;
import com.wanmi.sbc.goods.standardspec.model.root.StandardSpecDetail;
import com.wanmi.sbc.goods.standardspec.repository.StandardSkuSpecDetailRelRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSpecDetailRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSpecRepository;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 商品库服务
 * Created by daiyitian on 2017/4/11.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class StandardGoodsService {

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private StandardSkuRepository standardSkuRepository;

    @Autowired
    private StandardSpecRepository standardSpecRepository;

    @Autowired
    private StandardSpecDetailRepository standardSpecDetailRepository;

    @Autowired
    private StandardSkuSpecDetailRelRepository standardSkuSpecDetailRelRepository;

    @Autowired
    private StandardImageRepository standardImageRepository;

    @Autowired
    private GoodsBrandRepository goodsBrandRepository;

    @Autowired
    private GoodsCateRepository goodsCateRepository;

    @Autowired
    private StandardPropDetailRelRepository standardPropDetailRelRepository;

    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    @Autowired
    private GoodsCateService goodsCateService;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GoodsBrandService goodsBrandService;

    @Autowired
    private GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;

    @Autowired private FreightTemplateGoodsRepository freightTemplateGoodsRepository;

    public static final Integer BOSS_CODE = 3;


    /**
     * 分页查询商品库
     *
     * @param request 参数
     * @return list
     */
    public StandardQueryResponse page(StandardQueryRequest request) {
        StandardQueryResponse response = new StandardQueryResponse();

        List<StandardSkuVO> standardSkuVoList = new ArrayList<>();
        List<StandardSkuSpecDetailRel> standardSkuSpecDetails = new ArrayList<>();
        List<GoodsBrand> goodsBrandList = new ArrayList<>();
        List<GoodsCate> goodsCateList = new ArrayList<>();

        //获取该分类的所有子分类
        if (request.getCateId() != null) {
            request.setCateIds(goodsCateService.getChlidCateId(request.getCateId()));
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.getCateIds().add(request.getCateId());
                request.setCateId(null);
            }
        }
        if (request.getToLeadType() != null && request.getToLeadType() != -1) {
            List<String> standardIds = standardGoodsRelRepository.
                    findByStoreIds(Collections.singletonList(
                            request.getStoreId())).stream().map(StandardGoodsRel::getStandardId).collect(Collectors.toList());
            request.setGoodsIds(standardIds);
        }

        //根据SKU模糊查询SKU，获取SKU编号
        StandardSkuQueryRequest standardSkuQueryRequest = new StandardSkuQueryRequest();

        if (StringUtils.isNotBlank(request.getLikeGoodsInfoNo())) {
            standardSkuQueryRequest.setLikeGoodsInfoNo(request.getLikeGoodsInfoNo());
            standardSkuQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<StandardSku> infos = standardSkuRepository.findAll(standardSkuQueryRequest.getWhereCriteria());
            //不知道这是要干嘛，全表查一遍又不用。。先优化掉
//            standardSkuRepository.findAll();
            if (CollectionUtils.isNotEmpty(infos)) {
                request.setGoodsIds(infos.stream().map(StandardSku::getGoodsId).collect(Collectors.toList()));
            } else {
                return response;
            }
        }

        Page<StandardGoods> goodsPage = standardGoodsRepository.findAll(request.getWhereCriteria(), request.getPageRequest());
        if (CollectionUtils.isNotEmpty(goodsPage.getContent())) {
            List<String> goodsIds = goodsPage.getContent().stream().map(StandardGoods::getGoodsId).collect(Collectors.toList());
            //查询所有SKU
            StandardSkuQueryRequest skuQueryRequest = new StandardSkuQueryRequest();
            skuQueryRequest.setGoodsIds(goodsIds);
            skuQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<StandardSku> standardSkus = standardSkuRepository.findAll(skuQueryRequest.getWhereCriteria());
            //查询所有SKU规格值关联
            standardSkuSpecDetails.addAll(standardSkuSpecDetailRelRepository.findByGoodsIds(goodsIds));

            //填充每个SKU的规格关系
            standardSkus.forEach(standardSku -> {
                //为空，则以商品库主图
                if (StringUtils.isBlank(standardSku.getGoodsInfoImg())) {
                    standardSku.setGoodsInfoImg(goodsPage.getContent().stream().filter(goods -> goods.getGoodsId().equals(standardSku.getGoodsId())).findFirst().orElseGet(StandardGoods::new).getGoodsImg());
                }
                updateStandardSkuStockAndSupplyPrice(standardSku);
            });

            //填充每个SKU的SKU关系
            goodsPage.getContent().forEach(goods -> {
                //取SKU最小市场价
                goods.setMarketPrice(standardSkus.stream().filter(goodsInfo -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).filter(goodsInfo -> Objects.nonNull(goodsInfo.getMarketPrice())).map(StandardSku::getMarketPrice).min(BigDecimal::compareTo).orElseGet(goods::getMarketPrice));
                goods.setSupplyPrice(standardSkus.stream().filter(goodsInfo -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).filter(goodsInfo -> Objects.nonNull(goodsInfo.getSupplyPrice())).map(StandardSku::getSupplyPrice).min(BigDecimal::compareTo).orElseGet(goods::getSupplyPrice));
            });

            standardSkuVoList.addAll(KsBeanUtil.convertList(standardSkus, StandardSkuVO.class));
            standardSkuVoList.forEach(sku -> sku.setSpecDetailRelIds(standardSkuSpecDetails.stream().filter(specDetailRel -> specDetailRel.getGoodsInfoId().equals(sku.getGoodsInfoId())).map(StandardSkuSpecDetailRel::getSpecDetailRelId).collect(Collectors.toList())));

            //获取所有品牌
            GoodsBrandQueryRequest brandRequest = new GoodsBrandQueryRequest();
            brandRequest.setDelFlag(DeleteFlag.NO.toValue());
            brandRequest.setBrandIds(goodsPage.getContent().stream().filter(goods -> goods.getBrandId() != null).map(StandardGoods::getBrandId).distinct().collect(Collectors.toList()));
            goodsBrandList.addAll(goodsBrandRepository.findAll(brandRequest.getWhereCriteria()));

            //获取所有分类
            GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
            cateRequest.setCateIds(goodsPage.getContent().stream().filter(goods -> goods.getCateId() != null).map(StandardGoods::getCateId).distinct().collect(Collectors.toList()));
            goodsCateList.addAll(goodsCateRepository.findAll(cateRequest.getWhereCriteria()));
        }

        response.setStandardGoodsPage(KsBeanUtil.convertPage(goodsPage, StandardGoodsVO.class));
        if(CollectionUtils.isNotEmpty(response.getStandardGoodsPage().getContent())){
            response.getStandardGoodsPage().getContent().forEach(goods -> {
                goods.setGoodsInfoIds(standardSkuVoList.stream()
                        .filter(standardSku -> standardSku.getGoodsId().equals(goods.getGoodsId()))
                        .map(StandardSkuVO::getGoodsInfoId).collect(Collectors.toList()));
                //合计库存
                goods.setStock(standardSkuVoList.stream()
                        .filter(goodsInfo -> goodsInfo.getGoodsId().equals(goods.getGoodsId())
                                && Objects.nonNull(goodsInfo.getStock())).mapToLong(StandardSkuVO::getStock).sum());
            });
        }

        response.setStandardSkuList(standardSkuVoList);
        response.setStandardSkuSpecDetails(KsBeanUtil.convertList(standardSkuSpecDetails, StandardSkuSpecDetailRelVO.class));
        response.setGoodsBrandList(KsBeanUtil.convertList(goodsBrandList, GoodsBrandVO.class));
        response.setGoodsCateList(KsBeanUtil.convertList(goodsCateList, GoodsCateVO.class));
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = response.getStandardGoodsPage().stream()
                .filter(v -> Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(v.getGoodsSource()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (StandardSkuVO standardSku : response.getStandardSkuList()) {
                for (LinkedMallStockVO spuStock : stocks) {
                    Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                            .filter(v -> String.valueOf(spuStock.getItemId()).equals(standardSku.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(standardSku.getThirdPlatformSkuId()))
                            .findFirst();
                    if (stock.isPresent()) {
                        standardSku.setStock(stock.get().getStock());
                    }
                }
            }
            for (StandardGoodsVO standardGoods : response.getStandardGoodsPage()) {
                Long spuStock = response.getStandardSkuList().stream()
                        .filter(v -> v.getGoodsId().equals(standardGoods.getGoodsId()) && v.getStock() != null)
                        .map(StandardSkuVO::getStock).reduce(0L, Long::sum);
                standardGoods.setStock(spuStock);
            }
        }

        return response;
    }

    /**
     * 分页查询商品库
     *
     * @param request 参数
     * @return list
     */
    public Page<StandardGoods> simplePage(StandardQueryRequest request) {
        //分页优化，当百万数据时，先分页提取goodsId
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            Page<String> ids = this.pageCols(request);
            if (CollectionUtils.isEmpty(ids.getContent())) {
                return new PageImpl<>(Collections.emptyList(), request.getPageable(), ids.getTotalElements());
            }
            request.setGoodsIds(ids.getContent());
            List<StandardGoods> goods = standardGoodsRepository.findAll(request.getWhereCriteria(), request.getSort());
            return new PageImpl<>(goods, request.getPageable(), ids.getTotalElements());
        }
        return standardGoodsRepository.findAll(request.getWhereCriteria(), request.getPageRequest());
    }

    /***
     * 按条件查询商品库列表
     * @param request
     * @return
     */
    public List<StandardGoods> listStandardGoods(StandardQueryRequest request) {
        return standardGoodsRepository.findAll(request.getWhereCriteria());
    }

    /**
     * @description
     * @author  wur
     * @date: 2021/9/15 11:05
     * @param providerGoodsIdList
     * @return
     **/
    public List<StandardGoodsVO> queryByProvideGoodsIdList(List<String> providerGoodsIdList) {
        List<StandardGoodsRel>  standardGoodsRelList = standardGoodsRelRepository.findAllByGoodsIds(providerGoodsIdList);
        if (CollectionUtils.isEmpty(standardGoodsRelList)) {
            return null;
        }
        List<String> standardIdList = standardGoodsRelList.stream().map(StandardGoodsRel :: getStandardId).collect(Collectors.toList());

        List<StandardGoodsVO> standardGoodsList = KsBeanUtil.convertList(standardGoodsRepository.findByGoodsIdIn(standardIdList), StandardGoodsVO.class);
        standardGoodsList.forEach(standardGoods -> {
            for (StandardGoodsRel standardGoodsRel : standardGoodsRelList) {
                if (standardGoodsRel.getStandardId().equals(standardGoods.getGoodsId())) {
                    standardGoods.setProviderGoodsId(standardGoodsRel.getGoodsId());
                }
            }
        });
        return standardGoodsList;
    }

    /***
     * 按条件查询商品库SKU列表
     * @param request
     * @return
     */
    public List<StandardSku> listStandardSku(StandardSkuQueryRequest request) {
        return standardSkuRepository.findAll(request.getWhereCriteria());
    }

    private void updateStandardSkuStockAndSupplyPrice(StandardSku standardSku) {
        if (StringUtils.isNotBlank(standardSku.getProviderGoodsInfoId()) && !Integer.valueOf(GoodsSource.LINKED_MALL.ordinal()).equals(standardSku.getGoodsSource())) {
            GoodsInfo providerGoodsInfo = goodsInfoRepository.findById(standardSku.getProviderGoodsInfoId()).orElse(null);
            if (providerGoodsInfo != null) {
                standardSku.setStock(providerGoodsInfo.getStock());
                standardSku.setSupplyPrice(providerGoodsInfo.getSupplyPrice());
            }
        }
    }

    /**
     *
     * @param providerGoodsId
     * @return
     * @throws SbcRuntimeException
     */
    public StandardEditResponse findInfoByProviderGoodsId(String providerGoodsId) {
        StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findByGoodsId(providerGoodsId);
        if (Objects.isNull(standardGoodsRel)) {
            //修改sonar扫描出来的问题
            throw new SbcRuntimeException(this.getDeleteIndex(providerGoodsId), GoodsErrorCodeEnum.K030155);
        }
        return this.findInfoById(standardGoodsRel.getStandardId());
    }

    /**
     * 根据ID查询商品库
     *
     * @param goodsId 商品库ID
     * @return list
     */
    public StandardEditResponse findInfoById(String goodsId) throws SbcRuntimeException {
        StandardEditResponse response = new StandardEditResponse();
        StandardGoods spu = standardGoodsRepository.findById(goodsId)
                .orElseThrow(() -> new SbcRuntimeException(this.getDeleteIndex(goodsId), GoodsErrorCodeEnum.K030155));
        if (DeleteFlag.YES.equals(spu.getDelFlag())) {
            throw new SbcRuntimeException(this.getDeleteIndex(goodsId), GoodsErrorCodeEnum.K030155);
        }

        StandardGoodsVO goods = KsBeanUtil.copyPropertiesThird(spu, StandardGoodsVO.class);

        if (goods.getGoodsSource() == GoodsSource.PROVIDER.toValue() && goods.getStoreId() == null) {
            List<StandardGoodsRel> standardGoodsRelList = standardGoodsRelRepository.findByStandardId(goods.getGoodsId());
            if (CollectionUtils.isNotEmpty(standardGoodsRelList)) {
                standardGoodsRelList = standardGoodsRelList.stream().filter(standardGoodsRel -> standardGoodsRel.getDelFlag() == DeleteFlag.NO).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(standardGoodsRelList)) {
                    goods.setStoreId(standardGoodsRelList.get(0).getStoreId());
                }
            }
        }

        if ((goods.getGoodsSource() != GoodsSource.SELLER.toValue() && goods.getGoodsSource() != GoodsSource.PLATFORM.toValue())
                && goods.getStoreId() != null) {
            StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findByStandardIdAndStoreId(goodsId, goods.getStoreId());
            if (standardGoodsRel != null) {
                goods.setProviderGoodsId(standardGoodsRel.getGoodsId());
                goods.setStoreId(standardGoodsRel.getStoreId());
                //查询运费模板
                Goods providerGoods = goodsRepository.findById(standardGoodsRel.getGoodsId()).orElse(null);
                if (Objects.nonNull(providerGoods) && Objects.nonNull(providerGoods.getFreightTempId())) {
                    FreightTemplateGoods goodsTemplate = freightTemplateGoodsRepository.queryById(providerGoods.getFreightTempId());
                    if (Objects.nonNull(goodsTemplate)) {
                        goods.setFreightTempName(goodsTemplate.getFreightTempName());
                    }
                }
            }
        }

        response.setGoods(goods);

        //查询商品库图片
        response.setImages(KsBeanUtil.convertList(standardImageRepository.findByGoodsId(goods.getGoodsId()), StandardImageVO.class));

        //查询SKU列表
        StandardSkuQueryRequest infoQueryRequest = new StandardSkuQueryRequest();
        infoQueryRequest.setGoodsId(goods.getGoodsId());
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<StandardSkuVO> standardSkus = KsBeanUtil.convertList(standardSkuRepository.findAll(infoQueryRequest.getWhereCriteria()), StandardSkuVO.class);

        //商品库属性
        response.setGoodsPropDetailRels(KsBeanUtil.convertList(standardPropDetailRelRepository.queryByGoodsId(goods.getGoodsId()), StandardPropDetailRelVO.class));

        //如果是多规格
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(standardSpecRepository.findByGoodsId(goods.getGoodsId()), StandardSpecVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(standardSpecDetailRepository.findByGoodsId(goods.getGoodsId()), StandardSpecDetailVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(standardSpec -> {
                standardSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(standardSpec.getSpecId())).map(StandardSpecDetailVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            Map<String, List<StandardSkuSpecDetailRel>> standardSkuSpecDetailRels = standardSkuSpecDetailRelRepository.findByGoodsId(goods.getGoodsId()).stream().collect(Collectors.groupingBy(StandardSkuSpecDetailRel::getGoodsInfoId));
            standardSkus.forEach(standardSku -> {
                standardSku.setMockSpecIds(standardSkuSpecDetailRels.getOrDefault(standardSku.getGoodsInfoId(), new ArrayList<>()).stream().map(StandardSkuSpecDetailRel::getSpecId).collect(Collectors.toList()));
                standardSku.setMockSpecDetailIds(standardSkuSpecDetailRels.getOrDefault(standardSku.getGoodsInfoId(), new ArrayList<>()).stream().map(StandardSkuSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            });
        }
        response.setGoodsInfos(standardSkus);
        //如果是linkedmall商品，实时查库存
        if (Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(goods.getGoodsSource())) {
            List<LinkedMallStockVO> sotckList = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(goods.getThirdPlatformSpuId())), "0", null))
                    .getContext();
            if (sotckList != null && sotckList.size() > 0) {
                LinkedMallStockVO stock = sotckList.get(0);
                for (StandardSkuVO goodsInfo : response.getGoodsInfos()) {
                    for (LinkedMallStockVO.SkuStock sku : stock.getSkuList()) {
                        if (stock.getItemId().equals(Long.valueOf(goodsInfo.getThirdPlatformSpuId())) && sku.getSkuId().equals(Long.valueOf(goodsInfo.getThirdPlatformSkuId()))) {
                            goodsInfo.setStock(sku.getStock());
                        }
                    }
                }
            }
        }
        return response;
    }


    /**
     * 商品库新增
     *
     * @param saveRequest
     * @return SPU编号
     * @throws SbcRuntimeException
     */
    @Transactional
    public String add(StandardSaveRequest saveRequest) throws SbcRuntimeException {
        log.info("商品库新增开始");
        long startTime = System.currentTimeMillis();
        List<StandardImage> standardImages = KsBeanUtil.convert(saveRequest.getImages(), StandardImage.class);
        List<StandardSkuDTO> standardSkus = saveRequest.getGoodsInfos();
        StandardGoods goods = KsBeanUtil.convert(saveRequest.getGoods(), StandardGoods.class);
        Integer goodsType = goods.getGoodsType();
        //验证商品库相关基础数据
        this.checkBasic(goods);
        if (Objects.isNull(goods.getGoodsSource())) {
            goods.setGoodsSource(BOSS_CODE);
        }
        goods.setDelFlag(DeleteFlag.NO);
        goods.setAddedFlag(AddedFlag.NO.toValue());
        goods.setCreateTime(LocalDateTime.now());
        goods.setUpdateTime(goods.getCreateTime());
        if (CollectionUtils.isNotEmpty(standardImages)) {
            goods.setGoodsImg(standardImages.get(0).getArtworkUrl());
        }
        if (goods.getMoreSpecFlag() == null) {
            goods.setMoreSpecFlag(Constants.no);
        }

        final String goodsId = standardGoodsRepository.save(goods).getGoodsId();

        //新增图片
        if (CollectionUtils.isNotEmpty(standardImages)) {
            standardImages.forEach(standardImage -> {
                standardImage.setCreateTime(goods.getCreateTime());
                standardImage.setUpdateTime(goods.getUpdateTime());
                standardImage.setGoodsId(goodsId);
                standardImage.setDelFlag(DeleteFlag.NO);
                standardImage.setImageId(standardImageRepository.save(standardImage).getImageId());
            });
        }

        //保存商品库属性
        List<GoodsPropertyDetailRel> standardPropDetailRels = KsBeanUtil.convert(saveRequest.getGoodsPropertyDetailRel(), GoodsPropertyDetailRel.class);
        //如果是修改则设置修改时间，如果是新增则设置创建时间，
        if (CollectionUtils.isNotEmpty(standardPropDetailRels)) {
            standardPropDetailRels.forEach(standardPropDetailRel -> {
                standardPropDetailRel.setGoodsId(goodsId);
            });
            // 保存该商品的属性
            goodsPropertyDetailRelRepository.saveAll(standardPropDetailRels);
        }

        List<StandardSpecDTO> specs = saveRequest.getGoodsSpecs();
        List<StandardSpecDetailDTO> specDetails = saveRequest.getGoodsSpecDetails();

//        List<StandardSkuSpecDetailRel> specDetailRels = new ArrayList<>(10);
        log.info("商品库新增多规格开始->花费{}毫秒", (System.currentTimeMillis() - startTime));
        //如果是多规格
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            /**
             * 填放可用SKU相应的规格\规格值
             * （主要解决SKU可以前端删除，但相应规格值或规格仍然出现的情况）
             */
            Map<Long, Integer> isSpecEnable = new HashMap<>();
            Map<Long, Integer> isSpecDetailEnable = new HashMap<>();
            standardSkus.forEach(standardSku -> {
                standardSku.getMockSpecIds().forEach(specId -> {
                    isSpecEnable.put(specId, Constants.yes);
                });
                standardSku.getMockSpecDetailIds().forEach(detailId -> {
                    isSpecDetailEnable.put(detailId, Constants.yes);
                });
            });

            //新增规格
            specs.stream()
                    .filter(standardSpec -> Constants.yes.equals(isSpecEnable.get(standardSpec.getMockSpecId()))) //如果SKU有这个规格
                    .forEach(standardSpec -> {
                        standardSpec.setCreateTime(goods.getCreateTime());
                        standardSpec.setUpdateTime(goods.getUpdateTime());
                        standardSpec.setGoodsId(goodsId);
                        standardSpec.setDelFlag(DeleteFlag.NO);
                        standardSpec.setSpecId(standardSpecRepository.save(KsBeanUtil.copyPropertiesThird(standardSpec, StandardSpec.class)).getSpecId());
                    });
            //新增规格值
            specDetails.stream()
                    .filter(standardSpecDetail -> Constants.yes.equals(isSpecDetailEnable.get(standardSpecDetail.getMockSpecDetailId()))) //如果SKU有这个规格值
                    .forEach(standardSpecDetail -> {
                        Optional<StandardSpecDTO> specOpt = specs.stream().filter(standardSpec -> standardSpec.getMockSpecId().equals(standardSpecDetail.getMockSpecId())).findFirst();
                        if (specOpt.isPresent()) {
                            standardSpecDetail.setCreateTime(goods.getCreateTime());
                            standardSpecDetail.setUpdateTime(goods.getUpdateTime());
                            standardSpecDetail.setGoodsId(goodsId);
                            standardSpecDetail.setDelFlag(DeleteFlag.NO);
                            standardSpecDetail.setSpecId(specOpt.get().getSpecId());
                            standardSpecDetail.setSpecDetailId(standardSpecDetailRepository.save(KsBeanUtil.copyPropertiesThird(standardSpecDetail, StandardSpecDetail.class)).getSpecDetailId());
                        }
                    });
        }
        log.info("商品库新增多规格结束->花费{}毫秒", (System.currentTimeMillis() - startTime));
        for (StandardSkuDTO sku : standardSkus) {
            sku.setGoodsType(goodsType);
            //如果是虚拟商品，设置重量体积为0
            if (NumberUtils.INTEGER_ONE.equals(goodsType)) {
                sku.setGoodsCubage(BigDecimal.ZERO);
                sku.setGoodsWeight(BigDecimal.ZERO);
            }
            sku.setGoodsId(goodsId);
            sku.setGoodsInfoName(goods.getGoodsName());
            sku.setCostPrice(goods.getCostPrice());
            sku.setCreateTime(goods.getCreateTime());
            sku.setUpdateTime(goods.getUpdateTime());
            sku.setDelFlag(goods.getDelFlag());
            sku.setGoodsSource(goods.getGoodsSource());
            sku.setAddedFlag(goods.getAddedFlag());
            String goodsInfoId = standardSkuRepository.save(KsBeanUtil.copyPropertiesThird(sku, StandardSku.class)).getGoodsInfoId();
            sku.setGoodsInfoId(goodsInfoId);
            //如果是多规格,新增SKU与规格明细值的关联表
            if (Constants.yes.equals(goods.getMoreSpecFlag())) {
                if (CollectionUtils.isNotEmpty(specs)) {
                    for (StandardSpecDTO spec : specs) {
                        if (sku.getMockSpecIds().contains(spec.getMockSpecId())) {
                            for (StandardSpecDetailDTO detail : specDetails) {
                                if (spec.getMockSpecId().equals(detail.getMockSpecId()) && sku.getMockSpecDetailIds().contains(detail.getMockSpecDetailId())) {
                                    StandardSkuSpecDetailRel detailRel = new StandardSkuSpecDetailRel();
                                    detailRel.setGoodsId(goodsId);
                                    detailRel.setGoodsInfoId(goodsInfoId);
                                    detailRel.setSpecId(spec.getSpecId());
                                    detailRel.setSpecDetailId(detail.getSpecDetailId());
                                    detailRel.setDetailName(detail.getDetailName());
                                    detailRel.setCreateTime(detail.getCreateTime());
                                    detailRel.setUpdateTime(detail.getUpdateTime());
                                    detailRel.setDelFlag(detail.getDelFlag());
//                                    specDetailRels.add(standardSkuSpecDetailRelRepository.save(detailRel));
                                    standardSkuSpecDetailRelRepository.save(detailRel);
                                }
                            }
                        }
                    }
                }
            }
        }
        log.info("商品库新增结束->花费{}毫秒", (System.currentTimeMillis() - startTime));
        return goodsId;
    }

    /**
     * 商品库更新
     *
     * @param saveRequest
     * @throws SbcRuntimeException
     */
    @Transactional
    public Map<String, Object> edit(StandardSaveRequest saveRequest) throws SbcRuntimeException {
        log.info("商品库更新开始");
        long startTime = System.currentTimeMillis();
        StandardGoods newGoods = KsBeanUtil.convert(saveRequest.getGoods(), StandardGoods.class);
        StandardGoods oldGoods = standardGoodsRepository.findById(newGoods.getGoodsId()).orElse(null);
        if (oldGoods == null || oldGoods.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(this.getDeleteIndex(newGoods.getGoodsId()), GoodsErrorCodeEnum.K030155);
        }
        //平台类目不允许修改
        if (Objects.nonNull(newGoods.getCateId()) && !oldGoods.getCateId().equals(newGoods.getCateId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //验证商品库相关基础数据
        this.checkBasic(newGoods);
        Integer goodsType = newGoods.getGoodsType();
        List<StandardImage> standardImages = KsBeanUtil.convert(saveRequest.getImages(), StandardImage.class);
        if (CollectionUtils.isNotEmpty(standardImages)) {
            newGoods.setGoodsImg(standardImages.get(0).getArtworkUrl());
        } else {
            newGoods.setGoodsImg(null);
        }
        if (newGoods.getMoreSpecFlag() == null) {
            newGoods.setMoreSpecFlag(Constants.no);
        }

        LocalDateTime currDate = LocalDateTime.now();
        //更新商品库
        newGoods.setUpdateTime(currDate);
        KsBeanUtil.copyProperties(newGoods, oldGoods);
        standardGoodsRepository.save(oldGoods);

        //更新图片
        List<StandardImage> oldImages = standardImageRepository.findByGoodsId(newGoods.getGoodsId());
        if (CollectionUtils.isNotEmpty(oldImages)) {
            for (StandardImage oldImage : oldImages) {
                if (CollectionUtils.isNotEmpty(standardImages)) {
                    Optional<StandardImage> imageOpt = standardImages.stream().filter(standardImage -> oldImage.getImageId().equals(standardImage.getImageId())).findFirst();
                    //如果图片存在，更新
                    if (imageOpt.isPresent()) {
                        KsBeanUtil.copyProperties(imageOpt.get(), oldImage);
                    } else {
                        oldImage.setDelFlag(DeleteFlag.YES);
                    }
                } else {
                    oldImage.setDelFlag(DeleteFlag.YES);
                }
                oldImage.setUpdateTime(currDate);
                standardImageRepository.saveAll(oldImages);
            }
        }

        //新增图片
        if (CollectionUtils.isNotEmpty(standardImages)) {
            standardImages.stream().filter(standardImage -> standardImage.getImageId() == null).forEach(standardImage -> {
                standardImage.setCreateTime(currDate);
                standardImage.setUpdateTime(currDate);
                standardImage.setGoodsId(newGoods.getGoodsId());
                standardImage.setDelFlag(DeleteFlag.NO);
                standardImageRepository.save(standardImage);
            });
        }

        //保存商品库属性
        List<GoodsPropertyDetailRel> goodsPropDetailRels = KsBeanUtil.convert(saveRequest.getGoodsPropertyDetailRel(), GoodsPropertyDetailRel.class);
        // 先删除该商品下的属性
        goodsPropertyDetailRelRepository.deleteByGoodsIdAndGoodsType(newGoods.getGoodsId(), GoodsPropertyType.STANDARD_GOODS);
        // 再保存该商品的属性
        if (CollectionUtils.isNotEmpty(goodsPropDetailRels)) {
            goodsPropertyDetailRelRepository.saveAll(goodsPropDetailRels);
        }
        List<StandardSpecDTO> specs = saveRequest.getGoodsSpecs();
        List<StandardSpecDetailDTO> specDetails = saveRequest.getGoodsSpecDetails();
        log.info("商品库更新多规格开始->花费{}毫秒", (System.currentTimeMillis() - startTime));
        //如果是多规格
        if (Constants.yes.equals(newGoods.getMoreSpecFlag())) {

            /**
             * 填放可用SKU相应的规格\规格值
             * （主要解决SKU可以前端删除，但相应规格值或规格仍然出现的情况）
             */
            Map<Long, Integer> isSpecEnable = new HashMap<>();
            Map<Long, Integer> isSpecDetailEnable = new HashMap<>();
            saveRequest.getGoodsInfos().forEach(standardSku -> {
                standardSku.getMockSpecIds().forEach(specId -> {
                    isSpecEnable.put(specId, Constants.yes);
                });
                standardSku.getMockSpecDetailIds().forEach(detailId -> {
                    isSpecDetailEnable.put(detailId, Constants.yes);
                });
            });

            if (Constants.yes.equals(oldGoods.getMoreSpecFlag())) {
                //更新规格
                List<StandardSpec> standardSpecs = standardSpecRepository.findByGoodsId(oldGoods.getGoodsId());
                if (CollectionUtils.isNotEmpty(standardSpecs)) {
                    for (StandardSpec oldSpec : standardSpecs) {
                        if (CollectionUtils.isNotEmpty(specs)) {
                            Optional<StandardSpecDTO> specOpt = specs.stream().filter(spec -> oldSpec.getSpecId().equals(spec.getSpecId())).findFirst();
                            //如果规格存在且SKU有这个规格，更新
                            if (specOpt.isPresent() && Constants.yes.equals(isSpecEnable.get(specOpt.get().getMockSpecId()))) {
                                KsBeanUtil.copyProperties(specOpt.get(), oldSpec);
                            } else {
                                oldSpec.setDelFlag(DeleteFlag.YES);
                            }
                        } else {
                            oldSpec.setDelFlag(DeleteFlag.YES);
                        }
                        oldSpec.setUpdateTime(currDate);
                        standardSpecRepository.save(oldSpec);
                    }
                }

                //更新规格值
                List<StandardSpecDetail> standardSpecDetails = standardSpecDetailRepository.findByGoodsId(oldGoods.getGoodsId());
                if (CollectionUtils.isNotEmpty(standardSpecDetails)) {

                    for (StandardSpecDetail oldSpecDetail : standardSpecDetails) {
                        if (CollectionUtils.isNotEmpty(specDetails)) {
                            Optional<StandardSpecDetailDTO> specDetailOpt = specDetails.stream().filter(specDetail -> oldSpecDetail.getSpecDetailId().equals(specDetail.getSpecDetailId())).findFirst();
                            //如果规格值存在且SKU有这个规格值，更新
                            if (specDetailOpt.isPresent() && Constants.yes.equals(isSpecDetailEnable.get(specDetailOpt.get().getMockSpecDetailId()))) {
                                KsBeanUtil.copyProperties(specDetailOpt.get(), oldSpecDetail);

                                //更新SKU规格值表的名称备注
                                standardSkuSpecDetailRelRepository.updateNameBySpecDetail(specDetailOpt.get().getDetailName(), oldSpecDetail.getSpecDetailId(), oldGoods.getGoodsId());
                            } else {
                                oldSpecDetail.setDelFlag(DeleteFlag.YES);
                            }
                        } else {
                            oldSpecDetail.setDelFlag(DeleteFlag.YES);
                        }
                        oldSpecDetail.setUpdateTime(currDate);
                        standardSpecDetailRepository.save(oldSpecDetail);
                    }
                }
            }

            //新增规格
            if (CollectionUtils.isNotEmpty(specs)) {
                specs.stream().filter(standardSpec -> standardSpec.getSpecId() == null && Constants.yes.equals(isSpecEnable.get(standardSpec.getMockSpecId()))).forEach(standardSpec -> {
                    standardSpec.setCreateTime(currDate);
                    standardSpec.setUpdateTime(currDate);
                    standardSpec.setGoodsId(newGoods.getGoodsId());
                    standardSpec.setDelFlag(DeleteFlag.NO);
                    standardSpec.setSpecId(standardSpecRepository.save(KsBeanUtil.copyPropertiesThird(standardSpec, StandardSpec.class)).getSpecId());
                });
            }
            //新增规格值
            if (CollectionUtils.isNotEmpty(specDetails)) {
                specDetails.stream().filter(standardSpecDetail -> standardSpecDetail.getSpecDetailId() == null && Constants.yes.equals(isSpecDetailEnable.get(standardSpecDetail.getMockSpecDetailId()))).forEach(standardSpecDetail -> {
                    Optional<StandardSpecDTO> specOpt = specs.stream().filter(standardSpec -> standardSpec.getMockSpecId().equals(standardSpecDetail.getMockSpecId())).findFirst();
                    if (specOpt.isPresent()) {
                        standardSpecDetail.setCreateTime(currDate);
                        standardSpecDetail.setUpdateTime(currDate);
                        standardSpecDetail.setGoodsId(newGoods.getGoodsId());
                        standardSpecDetail.setDelFlag(DeleteFlag.NO);
                        standardSpecDetail.setSpecId(specOpt.get().getSpecId());
                        standardSpecDetail.setSpecDetailId(
                                standardSpecDetailRepository.save(KsBeanUtil.copyPropertiesThird(standardSpecDetail, StandardSpecDetail.class))
                                        .getSpecDetailId());
                    }
                });
            }
        } else {//修改为单规格
            //如果老数据为多规格
            if (Constants.yes.equals(oldGoods.getMoreSpecFlag())) {
                //删除规格
                standardSpecRepository.deleteByGoodsId(newGoods.getGoodsId());

                //删除规格值
                standardSpecDetailRepository.deleteByGoodsId(newGoods.getGoodsId());

                //删除商品库规格值
                standardSkuSpecDetailRelRepository.deleteByGoodsId(newGoods.getGoodsId());
            }
        }

        //只存储新增的SKU数据，用于当修改价格及订货量设置为否时，只为新SKU增加相关的价格数据
        List<StandardSkuDTO> newStandardSku = new ArrayList<>();//需要被添加的sku信息

        //更新原有的SKU列表
        List<StandardSkuDTO> standardSkus = saveRequest.getGoodsInfos();
        List<StandardSku> oldStandardSkus = new ArrayList<>();//需要被更新的sku信息
        List<String> delInfoIds = new ArrayList<>();//需要被删除的sku信息
        if (CollectionUtils.isNotEmpty(standardSkus)) {
            StandardSkuQueryRequest infoQueryRequest = new StandardSkuQueryRequest();
            infoQueryRequest.setGoodsId(newGoods.getGoodsId());
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<StandardSku> oldInfos = standardSkuRepository.findAll(infoQueryRequest.getWhereCriteria());

            if (CollectionUtils.isNotEmpty(oldInfos)) {
                for (StandardSku oldInfo : oldInfos) {
                    Optional<StandardSkuDTO> infoOpt = standardSkus.stream().filter(standardSku -> oldInfo.getGoodsInfoId().equals(standardSku.getGoodsInfoId())).findFirst();
                    //如果SKU存在，更新
                    if (infoOpt.isPresent()) {
                        infoOpt.get().setCostPrice(newGoods.getCostPrice());
                        KsBeanUtil.copyProperties(infoOpt.get(), oldInfo);
                        oldStandardSkus.add(oldInfo);//修改前后都存在的数据--加入需要被更新的sku中
                    } else {
                        oldInfo.setDelFlag(DeleteFlag.YES);
                        delInfoIds.add(oldInfo.getGoodsInfoId());//修改后不存在的数据--加入需要被删除的sku中
                    }
                    oldInfo.setGoodsInfoName(newGoods.getGoodsName());
                    oldInfo.setUpdateTime(currDate);
                    standardSkuRepository.save(oldInfo);
                }

                //删除SKU相关的规格关联表
                if (!delInfoIds.isEmpty()) {
                    standardSkuSpecDetailRelRepository.deleteByGoodsInfoIds(delInfoIds, newGoods.getGoodsId());
                }
            }
            log.info("商品库更新多规格结束->花费{}毫秒", (System.currentTimeMillis() - startTime));
            //只保存新SKU
            for (StandardSkuDTO sku : standardSkus) {
                sku.setGoodsId(newGoods.getGoodsId());
                sku.setGoodsInfoName(newGoods.getGoodsName());
                sku.setCreateTime(currDate);
                sku.setUpdateTime(currDate);
                sku.setGoodsType(goodsType);
                //如果是虚拟商品，设置重量体积为0
                if (NumberUtils.INTEGER_ONE.equals(goodsType)) {
                    sku.setGoodsCubage(BigDecimal.ZERO);
                    sku.setGoodsWeight(BigDecimal.ZERO);
                }
                sku.setDelFlag(DeleteFlag.NO);
                //只处理新增的SKU
                if (sku.getGoodsInfoId() != null) {
                    continue;
                }
                sku.setCostPrice(oldGoods.getCostPrice());
                String standardSkuId = standardSkuRepository.save(KsBeanUtil.copyPropertiesThird(sku, StandardSku.class)).getGoodsInfoId();
                sku.setGoodsInfoId(standardSkuId);

                //如果是多规格,新增SKU与规格明细值的关联表
                if (Constants.yes.equals(newGoods.getMoreSpecFlag())) {
                    if (CollectionUtils.isNotEmpty(specs)) {
                        for (StandardSpecDTO spec : specs) {
                            if (sku.getMockSpecIds().contains(spec.getMockSpecId())) {
                                for (StandardSpecDetailDTO detail : specDetails) {
                                    if (spec.getMockSpecId().equals(detail.getMockSpecId()) && sku.getMockSpecDetailIds().contains(detail.getMockSpecDetailId())) {
                                        StandardSkuSpecDetailRel detailRel = new StandardSkuSpecDetailRel();
                                        detailRel.setGoodsId(newGoods.getGoodsId());
                                        detailRel.setGoodsInfoId(standardSkuId);
                                        detailRel.setSpecId(spec.getSpecId());
                                        detailRel.setSpecDetailId(detail.getSpecDetailId());
                                        detailRel.setDetailName(detail.getDetailName());
                                        detailRel.setCreateTime(currDate);
                                        detailRel.setUpdateTime(currDate);
                                        detailRel.setDelFlag(DeleteFlag.NO);
                                        standardSkuSpecDetailRelRepository.save(detailRel);
                                    }
                                }
                            }
                        }
                    }
                }
                newStandardSku.add(sku);//修改后才存在(新出现)的数据--加入需要被添加的sku中
            }
        }
        log.info("商品库更新结束->花费{}毫秒", (System.currentTimeMillis() - startTime));
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("newStandardSku", newStandardSku);
        returnMap.put("delInfoIds", delInfoIds);
        returnMap.put("oldStandardSkus", oldStandardSkus);
        return returnMap;
    }

    /**
     * 商品库删除
     *
     * @param goodsIds 多个商品库
     * @throws SbcRuntimeException
     */
    @Transactional
    public void delete(List<String> goodsIds) throws SbcRuntimeException {

        if (standardGoodsRelRepository.countByStandardIds(goodsIds) > 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030158);
        }
        standardGoodsRelRepository.deleteByStandardIds(goodsIds);
        standardGoodsRepository.deleteByGoodsIds(goodsIds);
        standardSkuRepository.deleteByGoodsIds(goodsIds);
        standardPropDetailRelRepository.deleteByGoodsIds(goodsIds);
        standardSpecRepository.deleteByGoodsIds(goodsIds);
        standardSpecDetailRepository.deleteByGoodsIds(goodsIds);
        standardSkuSpecDetailRelRepository.deleteByGoodsIds(goodsIds);
    }


    /**
     * 供货商品库删除
     *
     * @param goodsIds 多个商品库
     * @throws SbcRuntimeException
     */
    @Transactional
    public void deleteProvider(List<String> goodsIds) throws SbcRuntimeException {

        // 相关商品下架
        List<StandardGoodsRel> standardGoodsRels = standardGoodsRelRepository.findByStandardIds(goodsIds);
        List<String> standardGoodsIds = standardGoodsRels.stream().map(StandardGoodsRel::getGoodsId).collect(Collectors.toList());

        //删除供应商商品库商品

        goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), standardGoodsIds, UnAddedFlagReason.BOSSDELETE.toString(), Boolean.FALSE, Boolean.FALSE);
        goodsInfoRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), standardGoodsIds, Boolean.FALSE, Boolean.FALSE);

        standardGoodsRelRepository.deleteByStandardIds(goodsIds);
        standardGoodsRepository.deleteByGoodsIds(goodsIds);
        standardSkuRepository.deleteByGoodsIds(goodsIds);
        standardPropDetailRelRepository.deleteByGoodsIds(goodsIds);
        standardSpecRepository.deleteByGoodsIds(goodsIds);
        standardSpecDetailRepository.deleteByGoodsIds(goodsIds);
        standardSkuSpecDetailRelRepository.deleteByGoodsIds(goodsIds);


    }


    /**
     * 供货商品库删除
     *
     * @param standardGoodsIds 多个商品库
     * @param deleteReason
     * @throws SbcRuntimeException
     */
    @Transactional
    public List<String> deleteProviderAddReason(List<String> standardGoodsIds, String deleteReason) throws SbcRuntimeException {

        // 相关商品下架
        List<StandardGoodsRel> standardGoodsRels = standardGoodsRelRepository.findByStandardIds(standardGoodsIds);
        List<String> goodsIds = standardGoodsRels.stream().map(StandardGoodsRel::getGoodsId).collect(Collectors.toList());

        //删除供应商商品库商品
        standardGoodsRepository.deleteByGoodsIds(standardGoodsIds);
        standardSkuRepository.deleteByGoodsIds(standardGoodsIds);
        standardPropDetailRelRepository.deleteByGoodsIds(standardGoodsIds);
        standardSpecRepository.deleteByGoodsIds(standardGoodsIds);
        standardSpecDetailRepository.deleteByGoodsIds(standardGoodsIds);
        standardSkuSpecDetailRelRepository.deleteByGoodsIds(standardGoodsIds);

        //供货商商品和商家商品 都下架
        goodsRepository.updateAddedFlagByGoodsIdsAddDeleteReason(AddedFlag.NO.toValue(), goodsIds, UnAddedFlagReason.BOSSDELETE.toString(), deleteReason);
        goodsInfoRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), goodsIds, Boolean.FALSE, Boolean.FALSE);


        List<Goods> allGoods = goodsRepository.findAllByGoodsIdIn(goodsIds);
        List<String> providerGoodsIds = allGoods.stream().filter(goods -> 0 == goods.getGoodsSource()).map(Goods::getGoodsId).collect(Collectors.toList());
        //删除商品库与供应商商品的关联
        if (CollectionUtils.isNotEmpty(providerGoodsIds)) {
            standardGoodsRelRepository.deleteByGoodsIds(providerGoodsIds);
        }

        return allGoods.stream().filter(goods -> goods.getGoodsSource() == 1).map(Goods::getGoodsId).collect(Collectors.toList());

    }

    /**
     * 列出已被导入的商品库ID
     *
     * @param standardIds 商品库Id
     */
    public List<String> getUsedStandard(List<String> standardIds, List<Long> storeIds) {
        return standardGoodsRelRepository.findByStandardIds(standardIds, storeIds).stream().map(StandardGoodsRel::getStandardId).distinct().collect(Collectors.toList());
    }

    /**
     * 列出已被导入的商品ID
     *
     * @param goodsIds 商品Id（非商品库）
     */
    public List<String> getUsedGoods(List<String> goodsIds) {
//        return standardGoodsRepository.findByGoodsIds(goodsIds).stream().map(StandardGoods::getGoodsId).distinct().collect(Collectors.toList());
        return standardGoodsRelRepository.findByGoodsIds(goodsIds).stream().map(StandardGoodsRel::getGoodsId).distinct().collect(Collectors.toList());
    }

    /***
     * 根据商品ID集合返回关联的商品库商品ID
     * @param goodsIds  goodsIds 商品Id（非商品库）
     * @return          standardGoodsId  商品库商品ID列表
     */
    public List<String> getStandardGoodsIdByGoodsId(List<String> goodsIds) {
        return standardGoodsRelRepository.findByGoodsIds(goodsIds).stream()
                .map(StandardGoodsRel::getStandardId).distinct().collect(Collectors.toList());
    }

    /**
     * 检测商品库公共基础类
     * 如分类、品牌、店铺分类
     *
     * @param goods 商品库信息
     */
    private void checkBasic(StandardGoods goods) {
        GoodsCate cate = this.goodsCateRepository.findById(goods.getCateId()).orElse(null);
        if (Objects.isNull(cate) || Objects.equals(DeleteFlag.YES, cate.getDelFlag())) {
            // VOP商品-1代表没有映射
            if (GoodsSource.VOP.ordinal() != goods.getGoodsSource() && goods.getCateId() != -1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030057);
            }

        }

        if (goods.getBrandId() != null) {
            GoodsBrand brand = this.goodsBrandService.findById(goods.getBrandId());
            if (Objects.isNull(brand) || Objects.equals(DeleteFlag.YES, brand.getDelFlag())) {
                throw new SbcRuntimeException(this.goodsBrandService.getDeleteIndex(goods.getBrandId()), GoodsErrorCodeEnum.K030058);
            }
        }
    }


    /**
     * 列出已被导入的SKUID
     *
     * @param standardIds 商品库Id
     */
    public List<String> getUsedGoodsId(List<String> standardIds, List<Long> storeIds) {
        return standardGoodsRelRepository.findByStandardIds(
                standardIds, storeIds).stream().map(StandardGoodsRel::getGoodsId).distinct().collect(Collectors.toList());
    }

    /**
     * 列出已被导入的商品库ID
     *
     * @param goodsIds 商品Id（非商品库）
     */
    public List<String> getUsedStandardByGoodsIds(List<String> goodsIds) {
        return standardGoodsRelRepository.findByGoodsIds(goodsIds).stream().map(StandardGoodsRel::getStandardId).distinct().collect(Collectors.toList());
    }

    /**
     * 列出已被导入的商品库ID
     *
     * @param standardIds 商品库Id
     */
    public List<String> getNeedSynStandard(List<String> standardIds, List<Long> storeIds, BoolFlag flag) {
        return standardGoodsRelRepository.findByNeedSynStandardIds(standardIds, storeIds, flag).stream().map(StandardGoodsRel::getStandardId).distinct().collect(Collectors.toList());
    }


    public List<StandardGoodsRel> getGoodsIdByStandardId(String goodsId) {
        return standardGoodsRelRepository.findByStandardId(goodsId);
    }

    public List<StandardGoodsRel> listRelByStandardIds(List<String> standardIds) {
        return standardGoodsRelRepository.findByStandardIds(standardIds);
    }

    /**
     * 自定义字段的列表查询
     *
     * @param request 参数
     * @return 列表
     */
    public Page<String> pageCols(StandardQueryRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<StandardGoods> rt = cq.from(StandardGoods.class);
        cq.select(rt.get("goodsId"));
        Specification<StandardGoods> spec = request.getWhereCriteria();
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
            Root<StandardGoods> crt = countCq.from(StandardGoods.class);
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
     * @param id 商品库编号
     * @return "es_standard_goods:{id}"
     */
    public Object getDeleteIndex(String id) {
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.DOC_STANDARD_GOODS, id);
    }

    /**
     * 根据货品ID集合批量查询
     *
     * @param goodsIds 货品ID
     * @return 货品信息
     * @author wur
     * @date: 2021/6/24 9:02
     **/
    public List<StandardGoods> findAllByGoodsId(List<String> goodsIds) {
        return standardGoodsRepository.findAllByGoodsIdIn(goodsIds);
    }
}
