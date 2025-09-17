package com.wanmi.sbc.goods.goodsproperty.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Pinyin4jUtil;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyAddRequest;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyIndexRequest;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyPageRequest;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyModifyResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import com.wanmi.sbc.goods.bean.vo.PropertyDetailVO;
import com.wanmi.sbc.goods.goodspropcaterel.model.root.GoodsPropCateRel;
import com.wanmi.sbc.goods.goodspropcaterel.repository.GoodsPropCateRelRepository;
import com.wanmi.sbc.goods.goodsproperty.model.root.GoodsProperty;
import com.wanmi.sbc.goods.goodsproperty.repository.GoodsPropertyRepository;
import com.wanmi.sbc.goods.goodspropertydetail.model.root.GoodsPropertyDetail;
import com.wanmi.sbc.goods.goodspropertydetail.repository.GoodsPropertyDetailRepository;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品属性业务逻辑
 *
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Service("GoodsPropertyService")
public class GoodsPropertyService {

    @Autowired
    private GoodsPropertyRepository goodsPropertyRepository;

    @Autowired
    private GoodsPropCateRelRepository goodsPropCateRelRepository;

    @Autowired
    private GoodsPropertyDetailRepository goodsPropertyDetailRepository;

    @Autowired
    private GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    /**
     * 新增商品属性
     *
     * @author chenli
     */
    @Transactional(rollbackFor = Exception.class)
    public Long saveGoodsProperty(GoodsPropertyAddRequest request) {
        List<PropertyDetailVO> detailList = request.getDetailList();
        this.checkDetailList(detailList, request.getPropType());
        //是否下方新增,下方新增则修改排序
        GoodsProperty goodsProperty = this.copyProperties(request);
        GoodsProperty property = goodsPropertyRepository.save(goodsProperty);
        Long propId = property.getPropId();

        this.saveGoodsPropertyDetail(propId, detailList);
        List<Long> cateIdList = request.getCateIdList();
        this.saveGoodsPropCateRel(propId, cateIdList);
        return propId;
    }


    public List<GoodsProperty> getPropSortList(Long propId) {
        return goodsPropertyRepository.findPropSort(propId);
    }

    /**
     * 修改商品属性
     *
     * @author chenli
     */
    @Transactional(rollbackFor = Exception.class)
    public GoodsPropertyModifyResponse modifyGoodsProperty(GoodsPropertyModifyRequest request) {
        List<PropertyDetailVO> detailList = request.getDetailList();
        Optional<GoodsProperty> goodsPropertyOptional =
                goodsPropertyRepository.findByPropIdAndDelFlag(request.getPropId(), DeleteFlag.NO);
        if(!goodsPropertyOptional.isPresent()){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030134);
        }
        GoodsProperty goodsProperty = goodsPropertyOptional.get();
        GoodsPropertyEnterType propType = goodsProperty.getPropType();
        if (!Objects.equals(propType, request.getPropType())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030133);
        }
        this.checkDetailList(detailList, request.getPropType());
        request.setPropPinYin(Pinyin4jUtil.converterToSpell(request.getPropName(), ","));
        goodsPropertyRepository.updateGoodsProperty(request);
        Long propId = request.getPropId();
        //修改数据,返回数据需同步es
        Map<Boolean, List<Long>> detailIdMap = this.updateGoodsPropertyDetail(propId, detailList);
        Map<Boolean, List<Long>> cateIdMap = this.updateGoodsPropCateRel(propId, request.getCateIdList());

        return new GoodsPropertyModifyResponse(detailIdMap, cateIdMap);
    }

    /**
     * 校验属性值
     *
     * @param detailList
     * @param propType
     */
    private void checkDetailList(List<PropertyDetailVO> detailList, GoodsPropertyEnterType propType) {
        //判断属性值是否存在
        if (Objects.equals(propType, GoodsPropertyEnterType.CHOOSE)) {
            if (CollectionUtils.isEmpty(detailList)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030132);
            }
            long count = detailList.stream().map(PropertyDetailVO::getDetailName).distinct().count();
            if (CollectionUtils.size(detailList) > count) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030131);
            }
            if (CollectionUtils.size(detailList) > Constants.NUM_100) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030130);
            }
        }
    }

    /**
     * 是否索引修改
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateIndexFlag(GoodsPropertyIndexRequest request) {
        Optional<GoodsProperty> goodsPropertyOptional =
                goodsPropertyRepository.findByPropIdAndDelFlag(request.getPropId(), DeleteFlag.NO);
        if(!goodsPropertyOptional.isPresent()){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030134);
        }
        goodsPropertyRepository.updateIndexFlag(request.getPropId(), request.getIndexFlag());
    }

    /**
     * 属性设值
     *
     * @param request
     * @return
     */
    private GoodsProperty copyProperties(GoodsPropertyAddRequest request) {
        //是否下方新增,下方新增则修改排序
        Long propSort = this.getPropSort(request);
        GoodsProperty goodsProperty = KsBeanUtil.convert(request, GoodsProperty.class);
        //文本类型默认关闭索引
        DefaultFlag indexFlag = Objects.equals(request.getPropType(), GoodsPropertyEnterType.TEXT) ?
                DefaultFlag.NO : DefaultFlag.YES;
        String pinyin = Pinyin4jUtil.converterToSpell(goodsProperty.getPropName(), ",");
        goodsProperty.setPropSort(propSort);
        goodsProperty.setDelFlag(DeleteFlag.NO);
        goodsProperty.setIndexFlag(indexFlag);
        goodsProperty.setPropPinYin(pinyin);
        return goodsProperty;
    }

    /**
     * 判断是否为下方新增，下方新增则修改排序，否则给当前时间
     *
     * @param request
     * @return
     */
    private Long getPropSort(GoodsPropertyAddRequest request) {
        if (Objects.isNull(request.getRowPropId())) {
            Long maxSort = goodsPropertyRepository.getMaxSort();
            return Objects.isNull(maxSort) ?
                    NumberUtils.LONG_ONE : maxSort + NumberUtils.LONG_ONE;
        }
        Optional<GoodsProperty> goodsPropOptional =
                goodsPropertyRepository.findByPropIdAndDelFlag(request.getRowPropId(), DeleteFlag.NO);
        if (!goodsPropOptional.isPresent()) {
            throw new SbcRuntimeException();
        }
        GoodsProperty goodsProperty = goodsPropOptional.get();
        Long propSort = goodsProperty.getPropSort();
        goodsPropertyRepository.updateGoodsPropSort(propSort);
        return propSort;
    }

    /**
     * 修改属性值
     *
     * @param propId
     * @param detailList
     * @return
     */
    private Map<Boolean, List<Long>> updateGoodsPropertyDetail(Long propId, List<PropertyDetailVO> detailList) {
        if (CollectionUtils.isEmpty(detailList)) {
            return Collections.emptyMap();
        }
        List<Long> propIdList = Collections.singletonList(propId);
        List<GoodsPropertyDetail> oldDetailList =
                goodsPropertyDetailRepository.findByPropIdInAndDelFlag(propIdList, DeleteFlag.NO);
        List<String> newDetailIdList = detailList.stream()
                .map(PropertyDetailVO::getDetailId)
                .collect(Collectors.toList());
        //分区出是否修改或者删除
        Map<Boolean, List<Long>> detailIdMap = oldDetailList.stream()
                .map(GoodsPropertyDetail::getDetailId)
                .collect(Collectors.partitioningBy(detailId -> !newDetailIdList.contains(detailId.toString())));
        //删除属性值
        List<Long> longList = detailIdMap.get(Boolean.TRUE);
        if (CollectionUtils.isNotEmpty(longList)) {
            goodsPropertyDetailRepository.deleteByIdList(detailIdMap.get(Boolean.TRUE));
        }
        this.updateDetail(propId, detailList);
        return detailIdMap;
    }

    /**
     * 先手动进行排序,然后保存
     *
     * @param detailList
     * @return
     */
    private void updateDetail(Long propId, List<PropertyDetailVO> detailList) {
        List<GoodsPropertyDetail> sortDetailIdList = Lists.newArrayList();
        for (int i = 0; i < detailList.size(); i++) {
            PropertyDetailVO propertyDetailVO = detailList.get(i);
            String detailName = propertyDetailVO.getDetailName();
            String detailId = propertyDetailVO.getDetailId();
            //判断传入id是否是数字（前端在有且只有在新增属性值时传入的uuid），来进行修改和新增属性值操作
            Long id = NumberUtils.isCreatable(detailId) ? Long.valueOf(detailId) : null;
            String pinyin = Pinyin4jUtil.converterToSpell(detailName, ",");
            GoodsPropertyDetail goodsPropertyDetail = new GoodsPropertyDetail();
            goodsPropertyDetail.setDetailId(id);
            goodsPropertyDetail.setDelFlag(DeleteFlag.NO);
            goodsPropertyDetail.setPropId(propId);
            goodsPropertyDetail.setDetailName(detailName);
            goodsPropertyDetail.setDetailPinYin(pinyin);
            //排序
            goodsPropertyDetail.setDetailSort(detailList.size() - i);
            goodsPropertyDetail.setCreateTime(LocalDateTime.now());
            sortDetailIdList.add(goodsPropertyDetail);
        }
        goodsPropertyDetailRepository.saveAll(sortDetailIdList);
    }

    /**
     * 修改属性类目关系表
     *
     * @param propId
     * @param cateIdList
     */
    private Map<Boolean, List<Long>> updateGoodsPropCateRel(Long propId, List<Long> cateIdList) {
        if (CollectionUtils.isEmpty(cateIdList)) {
            return Collections.emptyMap();
        }
        List<GoodsPropCateRel> goodsPropCateRelList =
                goodsPropCateRelRepository.findByPropIdAndDelFlag(propId, DeleteFlag.NO);
        List<Long> oldCateIdList = goodsPropCateRelList.stream()
                .map(GoodsPropCateRel::getCateId)
                .collect(Collectors.toList());
        //对库里已有数据进行分区，区分出数据是否需要删除还是保持在库中不变
        Map<Boolean, List<Long>> cateIdMap = oldCateIdList.stream()
                .collect(Collectors.partitioningBy(oldCateId -> !cateIdList.contains(oldCateId)));
        //取被删除数据
        List<Long> longList = cateIdMap.get(Boolean.TRUE);
        if (CollectionUtils.isNotEmpty(longList)) {
            goodsPropCateRelRepository.deleteByCateId(propId, longList);
            //删除商品关系表中数据
            goodsPropertyDetailRelRepository.deleteByCateId(propId, longList);
        }
        //过滤库中已有数据，新增未有数据
        List<Long> insetCateIdList = cateIdList.stream()
                .filter(cateId -> !cateIdMap.get(Boolean.FALSE).contains(cateId))
                .collect(Collectors.toList());
        this.saveGoodsPropCateRel(propId, insetCateIdList);
        return cateIdMap;
    }

    /**
     * 新增属性值
     *
     * @param propId
     * @param detailList
     */
    private void saveGoodsPropertyDetail(Long propId, List<PropertyDetailVO> detailList) {
        if (CollectionUtils.isEmpty(detailList)) {
            return;
        }
        List<String> detailNameList = detailList.stream().map(PropertyDetailVO::getDetailName).collect(Collectors.toList());
        List<GoodsPropertyDetail> goodsPropertyDetailList = Lists.newArrayList();
        for (int i = 0; i < detailNameList.size(); i++) {
            String detailName = detailNameList.get(i);
            String pinyin = Pinyin4jUtil.converterToSpell(detailName, ",");
            GoodsPropertyDetail goodsPropertyDetail = new GoodsPropertyDetail();
            goodsPropertyDetail.setDelFlag(DeleteFlag.NO);
            goodsPropertyDetail.setPropId(propId);
            goodsPropertyDetail.setDetailName(detailName);
            goodsPropertyDetail.setDetailPinYin(pinyin);
            goodsPropertyDetail.setDetailSort(detailNameList.size() - i);
            goodsPropertyDetail.setCreateTime(LocalDateTime.now());
            goodsPropertyDetailList.add(goodsPropertyDetail);
        }
        goodsPropertyDetailRepository.saveAll(goodsPropertyDetailList);

    }

    /**
     * 新增属性类目关系表
     *
     * @param propId
     * @param cateIdList
     */
    private void saveGoodsPropCateRel(Long propId, List<Long> cateIdList) {
        if (CollectionUtils.isEmpty(cateIdList)) {
            return;
        }

        GoodsCateByIdsRequest goodsCateByIdsRequest = new GoodsCateByIdsRequest();
        goodsCateByIdsRequest.setCateIds(cateIdList);

        //查询所有类目信息
        List<GoodsCateVO> goodsCateVOList =
                goodsCateQueryProvider.getByIds(goodsCateByIdsRequest).getContext().getGoodsCateVOList();

        List<GoodsPropCateRel> newList = goodsCateVOList.stream().map(cate -> {
            GoodsPropCateRel goodsPropCateRel = new GoodsPropCateRel();
            goodsPropCateRel.setDelFlag(DeleteFlag.NO);
            goodsPropCateRel.setPropId(propId);
            goodsPropCateRel.setCateId(cate.getCateId());
            goodsPropCateRel.setParentId(cate.getCateParentId());
            goodsPropCateRel.setCateGrade(cate.getCateGrade());
            goodsPropCateRel.setCatePath(cate.getCatePath());
            goodsPropCateRel.setRelSort(NumberUtils.INTEGER_ZERO);
            goodsPropCateRel.setCreateTime(LocalDateTime.now());
            return goodsPropCateRel;
        }).collect(Collectors.toList());
        goodsPropCateRelRepository.saveAll(newList);
    }

    /**
     * 单个删除商品属性
     *
     * @author chenli
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long propId) {
        int updateNum = goodsPropertyRepository.deleteGoodsProperty(propId);
        if (updateNum > NumberUtils.INTEGER_ZERO) {
            // 同时删除类目属性关系
            goodsPropCateRelRepository.deleteGoodsPropCateRel(propId);
            // 删除属性值和属性关系
            goodsPropertyDetailRepository.deleteGoodsPropertyDetail(propId);
            // 删除商品属性值关系
            goodsPropertyDetailRelRepository.deleteGoodsPropertyDetailRel(propId);
        }
    }

    /**
     * 单个查询商品属性
     *
     * @author chenli
     */
    public GoodsProperty getOne(Long id) {
        return goodsPropertyRepository
                .findByPropIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品属性不存在"));
    }

    /**
     * 类目属性列表
     *
     * @param propList
     * @return
     */
    public List<GoodsProperty> findCatePropertyList(Collection<Long> propList) {
        if (CollectionUtils.isEmpty(propList)) {
            return Collections.emptyList();
        }
        List<GoodsProperty> goodsPropertyList =
                goodsPropertyRepository.findByPropIdInAndDelFlag(propList, DeleteFlag.NO);
        return CollectionUtils.isEmpty(goodsPropertyList)
                ? Collections.emptyList() : goodsPropertyList;
    }

    /**
     * 类目属性列表
     *
     * @param propList
     * @return
     */
    public Map<Long, GoodsProperty> findGoodsPropertyMap(Collection<Long> propList) {
        if (CollectionUtils.isEmpty(propList)) {
            return Collections.emptyMap();
        }
        List<GoodsProperty> goodsPropertyList =
                goodsPropertyRepository.findByPropIdInAndDelFlag(propList, DeleteFlag.NO);
        if (CollectionUtils.isEmpty(goodsPropertyList)) {
            return Collections.emptyMap();
        }
        return goodsPropertyList.stream()
                .collect(Collectors.toMap(GoodsProperty::getPropId, Function.identity()));
    }

    /**
     * 分页查询商品属性
     *
     * @author chenli
     */
    public Page<GoodsProperty> page(GoodsPropertyPageRequest request) {
        Specification<GoodsProperty> condition = GoodsPropertyWhereCriteriaBuilder.build(request);
        PageRequest pageRequest = request.getPageRequest();
        return goodsPropertyRepository.findAll(condition, pageRequest);
    }

    /**
     * 列表查询商品属性
     *
     * @author chenli
     */
    public List<GoodsProperty> list(GoodsPropertyPageRequest queryReq) {
        return goodsPropertyRepository.findAll(GoodsPropertyWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author chenli
     */
    public GoodsPropertyVO wrapperVo(GoodsProperty goodsProperty) {
        if (goodsProperty != null) {
            GoodsPropertyVO goodsPropertyVO =
                    KsBeanUtil.convert(goodsProperty, GoodsPropertyVO.class);
            return goodsPropertyVO;
        }
        return null;
    }

    /**
     * 批量删除商品属性
     *
     * @author chenli
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIdList(List<GoodsProperty> infos) {
        goodsPropertyRepository.saveAll(infos);
    }

}
