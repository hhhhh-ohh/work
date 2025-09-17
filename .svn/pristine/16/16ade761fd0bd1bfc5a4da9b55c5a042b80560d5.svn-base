package com.wanmi.sbc.goods.thirdgoodscate.service;

import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.ThirdGoodsCateQueryRequest;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.CheckCateIdResponse;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateDTO;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateRelDTO;
import com.wanmi.sbc.goods.bean.vo.ThirdGoodsCateRelVO;
import com.wanmi.sbc.goods.bean.vo.ThirdGoodsCateVO;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.goodscatethirdcaterel.model.root.GoodsCateThirdCateRel;
import com.wanmi.sbc.goods.goodscatethirdcaterel.repository.GoodsCateThirdCateRelRepository;
import com.wanmi.sbc.goods.thirdgoodscate.model.root.ThirdGoodsCate;
import com.wanmi.sbc.goods.thirdgoodscate.repository.ThirdGoodsCateRepository;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>业务逻辑</p>
 *
 * @author
 * @date 2020-08-17 14:46:43
 */
@Service("ThirdGoodsCateService")
public class ThirdGoodsCateService {

    @Autowired
    private ThirdGoodsCateRepository thirdGoodsCateRepository;

    @Autowired
    private GoodsCateThirdCateRelRepository goodsCateThirdCateRelRepository;

    @Autowired
    private GoodsCateService goodsCateService;


    /**
     * 新增
     *
     * @author
     */
    @Transactional
    public ThirdGoodsCate add(ThirdGoodsCate entity) {
        thirdGoodsCateRepository.save(entity);
        return entity;
    }

    /**
     * 修改
     *
     * @author
     */
    @Transactional
    public ThirdGoodsCate modify(ThirdGoodsCate entity) {
        thirdGoodsCateRepository.save(entity);
        return entity;
    }

    /**
     * 单个删除
     *
     * @author
     */
    @Transactional
    public void deleteById(ThirdGoodsCate entity) {
        thirdGoodsCateRepository.save(entity);
    }

    /**
     * 批量删除
     *
     * @author
     */
    @Transactional
    public void deleteByIdList(List<ThirdGoodsCate> infos) {
        thirdGoodsCateRepository.saveAll(infos);
    }

    /**
     * 单个查询
     *
     * @author
     */
    public ThirdGoodsCate getOne(Long id) {
        return thirdGoodsCateRepository.findByCateIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "不存在"));
    }

    /**
     * 分页查询
     *
     * @author
     */
    public Page<ThirdGoodsCate> page(ThirdGoodsCateQueryRequest queryReq) {
        return thirdGoodsCateRepository.findAll(
                ThirdGoodsCateWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询
     *
     * @author
     */
    public List<ThirdGoodsCate> list(ThirdGoodsCateQueryRequest queryReq) {
        return thirdGoodsCateRepository.findAll(ThirdGoodsCateWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 根据三方类目父id关联查询平台类目
     *
     * @author
     */
    public List<ThirdGoodsCateRelDTO> getAllRel(ThirdPlatformType source, Long cateParentId) {
        return thirdGoodsCateRepository.getRelByParentId(source, cateParentId);
    }

    /**
     * 将实体包装成VO
     *
     * @author
     */
    public ThirdGoodsCateVO wrapperVo(ThirdGoodsCate thirdGoodsCate) {
        if (thirdGoodsCate != null) {
            ThirdGoodsCateVO thirdGoodsCateVO = KsBeanUtil.convert(thirdGoodsCate, ThirdGoodsCateVO.class);
            return thirdGoodsCateVO;
        }
        return null;
    }

    /**
     * 全量更新所有类目
     */
    @Transactional
    public void updateAll(List<ThirdGoodsCateDTO> thirdGoodsCateDTOS) {
        List<ThirdGoodsCate> thirdGoodsCates = KsBeanUtil.convert(thirdGoodsCateDTOS, ThirdGoodsCate.class);
        for (ThirdGoodsCate thirdGoodsCate : thirdGoodsCates) {
            thirdGoodsCate.setCreateTime(LocalDateTime.now());
            thirdGoodsCate.setUpdateTime(LocalDateTime.now());
            thirdGoodsCate.setDelFlag(DeleteFlag.NO);
        }
        ThirdPlatformType thirdPlatformType = thirdGoodsCateDTOS.get(0).getThirdPlatformType();
        if (thirdPlatformType == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080135);
        }
        thirdGoodsCateRepository.delAllByThirdPlatformType(thirdPlatformType);
        thirdGoodsCateRepository.saveAll(thirdGoodsCates);
    }

    /**
     * 全量更新所有类目（每次同步仅针对未同步的分类做新增，已同步的分类不做删除）
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAllNoDel(List<ThirdGoodsCateDTO> thirdGoodsCateDTOS) {
        ThirdPlatformType thirdPlatformType = thirdGoodsCateDTOS.get(0).getThirdPlatformType();
        if (thirdPlatformType == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080135);
        }
        List<ThirdGoodsCate> originThirdGoodsCateList = thirdGoodsCateRepository.findByThirdPlatformTypeAndDelFlag(
                thirdPlatformType, DeleteFlag.NO);
        Map<Long, Long> cateIdToIdMap = new HashMap<>();
        for (ThirdGoodsCate thirdGoodsCate : originThirdGoodsCateList) {
            cateIdToIdMap.put(thirdGoodsCate.getCateId(), thirdGoodsCate.getId());
        }
        List<ThirdGoodsCate> thirdGoodsCates = KsBeanUtil.convert(thirdGoodsCateDTOS, ThirdGoodsCate.class);
        for (ThirdGoodsCate thirdGoodsCate : thirdGoodsCates) {
            thirdGoodsCate.setId(cateIdToIdMap.get(thirdGoodsCate.getCateId()));
            thirdGoodsCate.setCreateTime(LocalDateTime.now());
            thirdGoodsCate.setUpdateTime(LocalDateTime.now());
            thirdGoodsCate.setDelFlag(DeleteFlag.NO);
        }
        thirdGoodsCateRepository.saveAll(thirdGoodsCates);
    }

    /**
     * 检查类目是否存在，返回查询出的类目
     * @param thirdGoodsCateDTOS
     * @return
     */
    public BaseResponse<CheckCateIdResponse> checkCateIdExist(
            List<ThirdGoodsCateDTO> thirdGoodsCateDTOS) {
        CheckCateIdResponse response = new CheckCateIdResponse();
        Set<Long> catIdSet = new HashSet<>();
        for (ThirdGoodsCateDTO dto : thirdGoodsCateDTOS){
            ThirdGoodsCate thirdGoodsCate = thirdGoodsCateRepository.findByCateIdAndThirdPlatformTypeAndDelFlag(
                    dto.getCateId(), dto.getThirdPlatformType(), DeleteFlag.NO);
            if (thirdGoodsCate != null) {
                catIdSet.add(thirdGoodsCate.getCateId());
                if (thirdGoodsCate.getCateGrade() == 3) {
                    Long cateId = thirdGoodsCate.getCateId();
                    GoodsCateThirdCateRel goodsCateThirdCateRel = goodsCateThirdCateRelRepository.findByThirdCateIdAndThirdPlatformTypeAndDelFlag(
                            cateId, thirdGoodsCate.getThirdPlatformType(), DeleteFlag.NO);
                    if (goodsCateThirdCateRel != null) {
                        response.setCateId(goodsCateThirdCateRel.getCateId());
                    }
                }
            }
        }
        response.setThirdCateIdSet(catIdSet);
        return BaseResponse.success(response);
    }

    /**
     * 直接批量添加类目
     */
    @Transactional(rollbackFor = Exception.class)
    public void addAll(List<ThirdGoodsCateDTO> thirdGoodsCateDTOS) {
        ThirdPlatformType thirdPlatformType = thirdGoodsCateDTOS.get(0).getThirdPlatformType();
        if (thirdPlatformType == null) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080135);
        }
        List<ThirdGoodsCate> thirdGoodsCates = KsBeanUtil.convert(thirdGoodsCateDTOS, ThirdGoodsCate.class);
        for (ThirdGoodsCate thirdGoodsCate : thirdGoodsCates) {
            thirdGoodsCate.setCreateTime(LocalDateTime.now());
            thirdGoodsCate.setUpdateTime(LocalDateTime.now());
            thirdGoodsCate.setDelFlag(DeleteFlag.NO);
        }
        thirdGoodsCateRepository.saveAll(thirdGoodsCates);
    }


    public List<ThirdGoodsCate> getByCateIds(ThirdPlatformType source, List<Long> cateIds) {
        return thirdGoodsCateRepository.getByCateIds(source, cateIds);
    }

    /**
     * 查询所有三方类目并关联平台类目
     *
     * @return
     */
    public List<ThirdGoodsCateRelVO> listRel(ThirdPlatformType thirdPlatformType) {
        List<ThirdGoodsCateRelDTO> allCate = thirdGoodsCateRepository.getRel(thirdPlatformType);
        List<ThirdGoodsCateRelVO> thirdGoodsCateRelVOS = KsBeanUtil.convertList(allCate, ThirdGoodsCateRelVO.class);

        Map<Long, List<ThirdGoodsCateRelVO>> thirdGoodsCateRelMap = thirdGoodsCateRelVOS.stream().filter(c -> Objects.nonNull(c.getCateId()))
                .collect(Collectors.groupingBy(ThirdGoodsCateRelVO::getCateId));

        Map<Long, GoodsCate> goodsCateMap = Maps.newHashMap();
        Map<Long, GoodsCate> parentGoodsCateMap = Maps.newHashMap();
        if (!org.springframework.util.CollectionUtils.isEmpty(thirdGoodsCateRelMap)) {
            List<GoodsCate> goodsCateList = goodsCateService.findByIds(new ArrayList<>(thirdGoodsCateRelMap.keySet()));
            if (!CollectionUtils.isEmpty(goodsCateList)) {
                goodsCateMap = goodsCateList.stream().collect(Collectors.toMap(GoodsCate::getCateId, Function.identity()));
                List<Long> parentCateIdList = goodsCateList.stream().map(c -> Arrays.asList(c.getCatePath().split("\\|"))).flatMap(Collection::stream).distinct()
                        .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                parentGoodsCateMap = goodsCateService.findByIds(parentCateIdList).stream().collect(Collectors.toMap(GoodsCate::getCateId, Function.identity()));
            }
        }

        for (ThirdGoodsCateRelVO thirdGoodsCateRelVO : thirdGoodsCateRelVOS) {
            thirdGoodsCateRelVO.setPath(catePathNew(thirdGoodsCateRelVO.getCateId(), goodsCateMap, parentGoodsCateMap));
        }
		//一级类目
        List<ThirdGoodsCateRelVO> oneGrade = thirdGoodsCateRelVOS.stream().filter(v -> v.getThirdCateParentId() == 0).collect(Collectors.toList());
        Map<Long, List<ThirdGoodsCateRelVO>> thirdGoodsCateRelChildMap = thirdGoodsCateRelVOS.stream().filter(v -> v.getThirdCateParentId() != 0).collect(Collectors.groupingBy(ThirdGoodsCateRelVO::getThirdCateParentId));
        for (ThirdGoodsCateRelVO thirdGoodsCateRelVO : oneGrade) {
            getChildrenCateNew(thirdGoodsCateRelVO, thirdGoodsCateRelChildMap);
        }
        return oneGrade;
    }

    /**
     * 根据类目id查询名称路径
     *
     * @param cateId
     * @return
     */
    public String catePath(Long cateId) {
        if (cateId == null) {
            return null;
        } else {
            StringBuilder path = new StringBuilder();
            GoodsCate goodsCate = goodsCateService.findById(cateId);
            String[] parentCateIds = goodsCate.getCatePath().split("\\|");
            for (int i = 1; i < parentCateIds.length; i++) {
                path.append(goodsCateService.findById(Long.valueOf(parentCateIds[i])).getCateName()).append(" - ");
            }
            return path.append(goodsCate.getCateName()).toString();
        }
    }


    //递归子类目
    public void getChildrenCateNew(ThirdGoodsCateRelVO cateRelVO, Map<Long, List<ThirdGoodsCateRelVO>> thirdGoodsCateRelMap) {
        List<ThirdGoodsCateRelVO> children = thirdGoodsCateRelMap.get(cateRelVO.getThirdCateId());
        if (!CollectionUtils.isEmpty(children) && children.size() > 0) {
            cateRelVO.setChildren(children);
            for (ThirdGoodsCateRelVO child : children) {
                getChildrenCateNew(child, thirdGoodsCateRelMap);
            }
        }
    }

    /**
     * 根据类目id查询名称路径
     *
     * @param cateId
     * @param goodsCateMap
     * @param parentGoodsCateMap
     * @return
     */
    public String catePathNew(Long cateId, Map<Long, GoodsCate> goodsCateMap, Map<Long, GoodsCate> parentGoodsCateMap) {
        if (Objects.isNull(cateId)) {
            return null;
        }
        StringBuilder path = new StringBuilder();
        GoodsCate goodsCate = goodsCateMap.get(cateId);
        String[] parentCateIds = goodsCate.getCatePath().split("\\|");
        for (int i = 1; i < parentCateIds.length; i++) {
            path.append(parentGoodsCateMap.get(Long.valueOf(parentCateIds[i])).getCateName()).append(" - ");
        }
        return path.append(goodsCate.getCateName()).toString();
    }


}


