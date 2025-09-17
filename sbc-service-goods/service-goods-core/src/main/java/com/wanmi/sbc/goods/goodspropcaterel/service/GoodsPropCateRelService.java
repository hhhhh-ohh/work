package com.wanmi.sbc.goods.goodspropcaterel.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelQueryRequest;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.goodspropcaterel.model.root.GoodsPropCateRel;
import com.wanmi.sbc.goods.goodspropcaterel.repository.GoodsPropCateRelRepository;
import com.wanmi.sbc.goods.goodspropertydetail.model.root.GoodsPropertyDetail;
import com.wanmi.sbc.goods.goodspropertydetail.repository.GoodsPropertyDetailRepository;
import com.wanmi.sbc.goods.info.repository.GoodsPropDetailRelRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>商品类目与属性关联业务逻辑</p>
 *
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Service("GoodsPropCateRelService")
public class GoodsPropCateRelService {

    @Autowired
    private GoodsPropCateRelRepository goodsPropCateRelRepository;

    @Autowired
    private GoodsCateRepository goodsCateRepository;

    @Autowired
    private GoodsPropertyDetailRepository goodsPropertyDetailRepository;

    @Autowired
    private GoodsPropDetailRelRepository goodsPropDetailRelRepository;

    private static final Integer GRADE_THREE = 3;
    private static final Integer GRADE_TWO = 2;
    private static final Integer GRADE_ONE = 1;

    /**
     * 新增商品类目与属性关联
     *
     * @author chenli
     */
    @Transactional
    public GoodsPropCateRel add(GoodsPropCateRel entity) {
        goodsPropCateRelRepository.save(entity);
        return entity;
    }

    /**
     * 修改商品类目与属性关联
     *
     * @author chenli
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifySort(List<GoodsPropertyVO> goodsPropCateVOList) {
        if (CollectionUtils.isEmpty(goodsPropCateVOList)) {
            return;
        }
        //三级类目属id相同
        Long cateId = goodsPropCateVOList.get(0).getCateId();
        GoodsCate goodsCate = goodsCateRepository.queryCatesShence(cateId);
        List<Long> resultCateIdList = Lists.newArrayList(cateId);
        this.findByCateId(goodsCate.getCateParentId(),resultCateIdList);
        goodsPropCateVOList.forEach(entity ->
            goodsPropCateRelRepository.updatePropCateSort(entity,resultCateIdList));
    }

    /**
     * 单个删除商品类目与属性关联
     *
     * @author chenli
     */
    @Transactional
    public void deleteById(GoodsPropCateRel entity) {
        goodsPropCateRelRepository.save(entity);
    }

    /**
     * 批量删除商品类目与属性关联
     *
     * @author chenli
     */
    @Transactional
    public void deleteByIdList(List<GoodsPropCateRel> infos) {
        goodsPropCateRelRepository.saveAll(infos);
    }

    /**
     * 单个查询商品类目与属性关联
     *
     * @author chenli
     */
    public GoodsPropCateRel getOne(Long id) {
        return goodsPropCateRelRepository.findByRelIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品类目与属性关联不存在"));
    }

    /**
     * 分页查询商品类目与属性关联
     *
     * @author chenli
     */
    public Page<GoodsPropCateRel> page(GoodsPropCateRelQueryRequest queryReq) {
        return goodsPropCateRelRepository.findAll(
                GoodsPropCateRelWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 将实体包装成VO
     *
     * @author chenli
     */
    public GoodsPropCateRelVO wrapperVo(GoodsPropCateRel goodsPropCateRel) {
        if (goodsPropCateRel != null) {
            GoodsPropCateRelVO goodsPropCateRelVO = KsBeanUtil.convert(goodsPropCateRel, GoodsPropCateRelVO.class);
            return goodsPropCateRelVO;
        }
        return null;
    }

    /**
     * 根据属性id查询类目名称<k,v><PropId,CateName> CateName:多个以‘;’隔开
     *
     * @param propIdList
     * @return
     */
    public Map<Long, String> cateNameMap(List<Long> propIdList) {
        if (CollectionUtils.isEmpty(propIdList)) {
            return Collections.emptyMap();
        }

        Map<Long, String> cateNameMap = new HashMap<>();

        propIdList.forEach(
                propId -> {
                    List<GoodsPropCateRel> propCateRelList =
                            goodsPropCateRelRepository.findByPropIdInAndDelFlag(
                                    Arrays.asList(propId), DeleteFlag.NO);

                    if (CollectionUtils.isNotEmpty(propCateRelList)) {
                        List<GoodsPropCateRelVO> propCateRelVOList =
                                KsBeanUtil.convertList(propCateRelList, GoodsPropCateRelVO.class);

                        // 取出最高分类id返回前端
                        List<Long> cateIdLists = setCateIds(propCateRelVOList);

                        List<GoodsCate> goodsCateList =
                                goodsCateRepository.queryCates(cateIdLists, DeleteFlag.NO);

                        Map<Long, String> goodCateMap =
                                goodsCateList.stream()
                                        .collect(
                                                Collectors.toMap(
                                                        GoodsCate::getCateId,
                                                        GoodsCate::getCateName));
                        cateNameMap.putAll(
                                propCateRelVOList.stream()
                                        .filter(rel -> cateIdLists.contains(rel.getCateId()))
                                        .peek(
                                                rel -> {
                                                    String cateName =
                                                            goodCateMap.get(rel.getCateId());
                                                    rel.setCateName(cateName);
                                                })
                                        .collect(
                                                Collectors.groupingBy(
                                                        GoodsPropCateRelVO::getPropId,
                                                        Collectors.mapping(
                                                                GoodsPropCateRelVO::getCateName,
                                                                Collectors.joining(";")))));
                    }
                });

        return cateNameMap;
    }

    /**
     * 根据所有分类id 取出最高分类id
     * 例：如果有一级类目了 则一级下面的二级和三级都无需返回
     * @param propCateRelVOList
     * @return
     */
    private List<Long> setCateIds(List<GoodsPropCateRelVO> propCateRelVOList){
        List<Long> cateIdLists = new ArrayList<>();
        // 根据分类层次进行分组
        if (CollectionUtils.isNotEmpty(propCateRelVOList)) {
            Map<Integer, List<GoodsPropCateRelVO>> cateRelVOMap =
                    propCateRelVOList.stream()
                            .collect(Collectors.groupingBy(GoodsPropCateRelVO::getCateGrade));

            List<GoodsPropCateRelVO> firstCate = cateRelVOMap.get(GRADE_ONE);
            List<GoodsPropCateRelVO> secondeCate = cateRelVOMap.get(GRADE_TWO);
            List<GoodsPropCateRelVO> thirdCate = cateRelVOMap.get(GRADE_THREE);

            // 过滤已经有二级类目的 无需向前端展示三级类目
            if (CollectionUtils.isNotEmpty(thirdCate)) {

                if(CollectionUtils.isNotEmpty(secondeCate)){
                    List<Long> secondCateIds =
                            secondeCate.stream()
                                    .map(GoodsPropCateRelVO::getCateId)
                                    .collect(Collectors.toList());

                    cateIdLists.addAll(
                            thirdCate.stream()
                                    .filter(cate -> !secondCateIds.contains(cate.getParentId()))
                                    .map(GoodsPropCateRelVO::getCateId)
                                    .collect(Collectors.toList()));
                }else{
                    cateIdLists.addAll(
                            thirdCate.stream()
                                    .map(GoodsPropCateRelVO::getCateId)
                                    .collect(Collectors.toList()));
                }

            }

            // 过滤已经有一级类目的 无需向前端展示二级类目
            if (CollectionUtils.isNotEmpty(secondeCate)) {

                if(CollectionUtils.isNotEmpty(firstCate)){
                    List<Long> firstCateIds =
                            firstCate.stream()
                                    .map(GoodsPropCateRelVO::getCateId)
                                    .collect(Collectors.toList());

                    cateIdLists.addAll(
                            secondeCate.stream()
                                    .filter(cate -> !firstCateIds.contains(cate.getParentId()))
                                    .map(GoodsPropCateRelVO::getCateId)
                                    .collect(Collectors.toList()));
                }else{
                    cateIdLists.addAll(
                            secondeCate.stream()
                                    .map(GoodsPropCateRelVO::getCateId)
                                    .collect(Collectors.toList()));
                }

            }

            // 一级都需要展示
            if (CollectionUtils.isNotEmpty(firstCate)) {
                cateIdLists.addAll(
                        firstCate.stream()
                                .map(GoodsPropCateRelVO::getCateId)
                                .collect(Collectors.toList()));
            }
        }

        return cateIdLists;
    }

    /**
     * 获取属性值并拼接，两属性值之间以‘，’隔开
     * <k,v><PropId,DetailName>
     *
     * @param propIdList
     * @return
     */
    public Map<Long, String> detailNameMap(Collection<Long> propIdList) {
        if (CollectionUtils.isEmpty(propIdList)) {
            return Collections.emptyMap();
        }
        List<GoodsPropertyDetail> goodsPropertyDetail =
                goodsPropertyDetailRepository.findByPropIdInAndDelFlag(propIdList, DeleteFlag.NO);
        return Optional.ofNullable(goodsPropertyDetail)
                .orElseGet(Collections::emptyList).stream()
                .sorted(Comparator.comparing(GoodsPropertyDetail::getDetailSort).reversed())
                .collect(Collectors.groupingBy(GoodsPropertyDetail::getPropId, Maps::newLinkedHashMap,
                        Collectors.mapping(GoodsPropertyDetail::getDetailName, Collectors.joining(";"))));
    }

    /**
     * 获取属性值id和name
     * <k,v><PropId,<K,V>>
     *
     * @param propIdList
     * @return
     */
    public Map<Long, List<GoodsPropDetailVO>> detailMap(Collection<Long> propIdList) {
        if (CollectionUtils.isEmpty(propIdList)) {
            return Collections.emptyMap();
        }

        List<GoodsPropertyDetail> goodsPropertyDetail =
                goodsPropertyDetailRepository.findByPropIdInAndDelFlag(propIdList, DeleteFlag.NO);

        return Optional.ofNullable(goodsPropertyDetail)
                .orElseGet(Collections::emptyList).stream()
                .map(target -> {
                    GoodsPropDetailVO goodsPropVO = new GoodsPropDetailVO();
                    goodsPropVO.setPropId(target.getPropId());
                    goodsPropVO.setDetailName(target.getDetailName());
                    goodsPropVO.setDetailId(target.getDetailId());
                    goodsPropVO.setSort(target.getDetailSort());
                    return goodsPropVO;
                }).collect(Collectors.groupingBy(GoodsPropDetailVO::getPropId,
                        Collectors.collectingAndThen(Collectors.toCollection(() ->
                                Sets.newTreeSet(Comparator.comparing(GoodsPropDetailVO::getSort).reversed())), Lists::newArrayList)));
    }

    /**
     * 获取属性值id和name
     * <k,v><PropId,<K,V>>
     *
     * @param detailIdList
     * @return 查看详情选中属性值
     */
    public Map<Long, List<GoodsPropDetailVO>> detailFilterMap(Collection<Long> detailIdList) {
        if (CollectionUtils.isEmpty(detailIdList)) {
            return Collections.emptyMap();
        }

        List<GoodsPropertyDetail> goodsPropertyDetail =
                goodsPropertyDetailRepository.findByDetailIdInAndDelFlag(detailIdList, DeleteFlag.NO);
        return Optional.ofNullable(goodsPropertyDetail)
                .orElseGet(Collections::emptyList).stream()
                .map(target -> {
                    GoodsPropDetailVO goodsPropVO = new GoodsPropDetailVO();
                    goodsPropVO.setPropId(target.getPropId());
                    goodsPropVO.setDetailName(target.getDetailName());
                    goodsPropVO.setDetailId(target.getDetailId());
                    return goodsPropVO;
                }).collect(Collectors.groupingBy(GoodsPropDetailVO::getPropId));
    }

    /**
     * 根据属性id获取类目id
     *
     * @param propId
     * @return
     */
    public List<String> getCateIdList(Long propId) {
        List<GoodsPropCateRel> goodsPropCateRelList =
                goodsPropCateRelRepository.findByPropIdAndDelFlag(propId, DeleteFlag.NO);
        if (CollectionUtils.isEmpty(goodsPropCateRelList)) {
            return Collections.emptyList();
        }

        //取出最高分类id返回前端
        List<GoodsPropCateRelVO> propCateRelVOList =
                KsBeanUtil.copyListProperties(goodsPropCateRelList,GoodsPropCateRelVO.class);

        List<Long> cateIdLists = setCateIds(propCateRelVOList);

        return cateIdLists.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    /**
     * 根据cateId查询
     *
     * @param cateId
     * @return
     */
    public List<Long> getPropIdList(Long cateId) {
        List<Long> resultCateIdList = Lists.newArrayList(cateId);
        List<GoodsPropCateRel> goodsPropCateRelList =
                goodsPropCateRelRepository.findByCateIdInAndDelFlag(resultCateIdList, DeleteFlag.NO);
        if (CollectionUtils.isEmpty(goodsPropCateRelList)) {
            //不可为空集合
            return Collections.singletonList(NumberUtils.LONG_MINUS_ONE);
        }
        return goodsPropCateRelList.stream()
                .map(GoodsPropCateRel::getPropId)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 根据cateId查询
     *
     * @param cateId
     * @return
     */
    public Map<Long, Integer> catePropSortMap(Long cateId) {
        List<GoodsPropCateRel> goodsPropCateList = Lists.newArrayList();
        this.findCateByCateId(cateId, goodsPropCateList);
        if (CollectionUtils.isEmpty(goodsPropCateList)) {
            //不可为空集合
            return Collections.emptyMap();
        }
        return goodsPropCateList.stream()
                .collect(Collectors.toMap(GoodsPropCateRel::getPropId, GoodsPropCateRel::getRelSort,
                        (oldKey, newKey) -> oldKey));
    }


    /**
     * 根据id查询父id
     */
    private void findByCateId(Long cateId, List<Long> cateIdList) {
        GoodsCate goodsCate = goodsCateRepository.queryCatesShence(cateId);
        if (Objects.isNull(goodsCate)) {
            return;
        }
        Long cateParentId = goodsCate.getCateParentId();
        Long cateCateId = goodsCate.getCateId();
        cateIdList.add(cateParentId);
        cateIdList.add(cateCateId);
    }


    /**
     * 根据三级分类，查询类目属性关系
     *
     * @param cateId
     * @return
     */
    private void findCateByCateId(Long cateId, List<GoodsPropCateRel> goodsPropCateList) {
        List<GoodsPropCateRel> goodsPropCateRelList =
                goodsPropCateRelRepository.findByCateIdAndDelFlag(cateId, DeleteFlag.NO);
        goodsPropCateList.addAll(goodsPropCateRelList);
    }

    /**
     * 根据分类id和属性id查询未删除的关联关系
     *
     * @param cateId
     * @param propId
     * @return
     */
    public GoodsPropCateRel getByCateIdAndPropId(Long cateId, Long propId) {
        return goodsPropCateRelRepository
                        .findByCateIdAndPropIdAndDelFlag(cateId, propId, DeleteFlag.NO)
                        .orElse(null);
    }

    /**
     * 新增商品类目与属性关联
     *
     * @author chenli
     */
    @Transactional
    public List<GoodsPropCateRel> batchAdd(List<GoodsPropCateRel> entity) {
        goodsPropCateRelRepository.saveAll(entity);
        return entity;
    }
}

