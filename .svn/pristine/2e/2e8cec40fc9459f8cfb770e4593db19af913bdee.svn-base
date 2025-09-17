package com.wanmi.sbc.goods.goodspropertydetailrel.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelQueryRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailRelVO;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import com.wanmi.sbc.goods.standard.model.root.StandardGoodsRel;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>商品与属性值关联业务逻辑</p>
 *
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Service("GoodsPropertyDetailRelService")
public class GoodsPropertyDetailRelService {
    @Autowired
    private GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;

    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    /**
     * 新增商品与属性值关联
     *
     * @author chenli
     */
    @Transactional
    public GoodsPropertyDetailRel add(GoodsPropertyDetailRel entity) {
        goodsPropertyDetailRelRepository.save(entity);
        return entity;
    }

    /**
     * 修改商品与属性值关联
     *
     * @author chenli
     */
    @Transactional
    public GoodsPropertyDetailRel modify(GoodsPropertyDetailRel entity) {
        goodsPropertyDetailRelRepository.save(entity);
        return entity;
    }

    /**
     * 根据商品id查询
     *
     * @param goodId
     * @return
     */
    public List<GoodsPropertyDetailRel> findByGoodsId(String goodId, Long goodsType, Long storeId) {
        GoodsPropertyType propertyType = Objects.equals(goodsType, 1L)
                ? GoodsPropertyType.STANDARD_GOODS : GoodsPropertyType.GOODS;
        List<GoodsPropertyDetailRel> goodsPropertyDetailRelList =
                goodsPropertyDetailRelRepository.findByGoodsIdAndDelFlagAndGoodsType(goodId, DeleteFlag.NO, propertyType);
        if (CollectionUtils.isEmpty(goodsPropertyDetailRelList)) {
            StandardGoodsRel standardGoodsRel =
                    standardGoodsRelRepository.findByStandardIdAndStoreId(goodId, storeId);
            if(Objects.isNull(standardGoodsRel)){
                return Collections.emptyList();
            }
            String goodsId = standardGoodsRel.getGoodsId();
            List<GoodsPropertyDetailRel> propertyDetailRelList =
                    goodsPropertyDetailRelRepository.findByGoodsIdAndDelFlagAndGoodsType(goodsId, DeleteFlag.NO, propertyType);
            if (CollectionUtils.isEmpty(propertyDetailRelList)) {
                return Collections.emptyList();
            }
            return propertyDetailRelList;
        }
        return goodsPropertyDetailRelList;
    }


    /**
     * 根据商品id查询
     *
     * @param propIdList
     * @return
     */
    public List<GoodsPropertyDetailRel> findByPropIdList(List<Long> propIdList) {
        List<GoodsPropertyDetailRel> goodsPropertyDetailRelList =
                goodsPropertyDetailRelRepository.findByPropIdInAndDelFlag(propIdList, DeleteFlag.NO);
        if (CollectionUtils.isEmpty(goodsPropertyDetailRelList)) {
            return Collections.emptyList();
        }
        return goodsPropertyDetailRelList;
    }
    public String findPriceByIdAndType(String goodsId,String goodsType){
        return goodsPropertyDetailRelRepository.findPriceByIdAndGoodsType(goodsId,goodsType);
    }
    /**
     * 根据三级类目id查询
     *
     * @param cateId
     * @return
     */
    public List<GoodsPropertyDetailRel> findByCateId(Long cateId) {
        List<GoodsPropertyDetailRel> goodsPropertyDetailRelList =
                goodsPropertyDetailRelRepository.findByCateIdAndDelFlag(cateId, DeleteFlag.NO);
        if (CollectionUtils.isEmpty(goodsPropertyDetailRelList)) {
            return Collections.emptyList();
        }
        return goodsPropertyDetailRelList;
    }

    /**
     * 单个删除商品与属性值关联
     *
     * @author chenli
     */
    @Transactional
    public void deleteById(GoodsPropertyDetailRel entity) {
        goodsPropertyDetailRelRepository.save(entity);
    }

    /**
     * 批量删除商品与属性值关联
     *
     * @author chenli
     */
    @Transactional
    public void deleteByIdList(List<GoodsPropertyDetailRel> infos) {
        goodsPropertyDetailRelRepository.saveAll(infos);
    }

    /**
     * 单个查询商品与属性值关联
     *
     * @author chenli
     */
    public GoodsPropertyDetailRel getOne(Long id) {
        return goodsPropertyDetailRelRepository.findByDetailRelIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品与属性值关联不存在"));
    }

    /**
     * 分页查询商品与属性值关联
     *
     * @author chenli
     */
    public Page<GoodsPropertyDetailRel> page(GoodsPropertyDetailRelQueryRequest queryReq) {
        return goodsPropertyDetailRelRepository.findAll(
                GoodsPropertyDetailRelWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询商品与属性值关联
     *
     * @author chenli
     */
    public List<GoodsPropertyDetailRel> list(GoodsPropertyDetailRelQueryRequest queryReq) {
        return goodsPropertyDetailRelRepository.findAll(GoodsPropertyDetailRelWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author chenli
     */
    public GoodsPropertyDetailRelVO wrapperVo(GoodsPropertyDetailRel goodsPropertyDetailRel) {
        if (goodsPropertyDetailRel != null) {
            GoodsPropertyDetailRelVO goodsPropertyDetailRelVO = KsBeanUtil.convert(goodsPropertyDetailRel, GoodsPropertyDetailRelVO.class);
            return goodsPropertyDetailRelVO;
        }
        return null;
    }

}

