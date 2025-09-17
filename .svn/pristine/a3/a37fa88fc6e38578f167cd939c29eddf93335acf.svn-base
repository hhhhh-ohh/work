package com.wanmi.sbc.goods.goodspropertydetail.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailQueryRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailVO;
import com.wanmi.sbc.goods.bean.vo.PropertyDetailVO;
import com.wanmi.sbc.goods.goodspropertydetail.model.root.GoodsPropertyDetail;
import com.wanmi.sbc.goods.goodspropertydetail.repository.GoodsPropertyDetailRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>商品属性值业务逻辑</p>
 *
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Service("GoodsPropertyDetailService")
public class GoodsPropertyDetailService {

    @Autowired
    private GoodsPropertyDetailRepository goodsPropertyDetailRepository;

    /**
     * 新增商品属性值
     *
     * @author chenli
     */
    @Transactional
    public GoodsPropertyDetail add(GoodsPropertyDetail entity) {
        goodsPropertyDetailRepository.save(entity);
        return entity;
    }

    /**
     * 根据属性id获取属性
     *
     * @param idList
     * @return
     */
    public Map<Long, GoodsPropertyDetail> getGoodsPropertyDetailMap(List<Long> idList) {
        List<GoodsPropertyDetail> propDetailList = goodsPropertyDetailRepository.findByPropIdInAndDelFlag(idList, DeleteFlag.NO);
        if (CollectionUtils.isEmpty(propDetailList)) {
            return Collections.emptyMap();
        }
        return propDetailList.stream().collect(Collectors.toMap(GoodsPropertyDetail::getDetailId, Function.identity()));
    }

    /**
     * 根据属性id获取属性值
     *
     * @param propId
     * @return
     */
    public List<PropertyDetailVO> getPropDetailList(Long propId) {
        List<GoodsPropertyDetail> goodsPropertyDetailList =
                goodsPropertyDetailRepository.findByPropIdAndDelFlagOrderByDetailSortDesc(propId, DeleteFlag.NO);
        if (CollectionUtils.isEmpty(goodsPropertyDetailList)) {
            return Collections.emptyList();
        }
        return goodsPropertyDetailList.stream()
                .map(target -> {
                    PropertyDetailVO detailVO = new PropertyDetailVO();
                    detailVO.setDetailId(target.getDetailId().toString());
                    detailVO.setDetailName(target.getDetailName());
                    return detailVO;
                }).collect(Collectors.toList());
    }

    /**
     * 修改商品属性值
     *
     * @author chenli
     */
    @Transactional
    public GoodsPropertyDetail modify(GoodsPropertyDetail entity) {
        goodsPropertyDetailRepository.save(entity);
        return entity;
    }

    /**
     * 单个删除商品属性值
     *
     * @author chenli
     */
    @Transactional
    public void deleteById(GoodsPropertyDetail entity) {
        goodsPropertyDetailRepository.save(entity);
    }

    /**
     * 批量删除商品属性值
     *
     * @author chenli
     */
    @Transactional
    public void deleteByIdList(List<GoodsPropertyDetail> infos) {
        goodsPropertyDetailRepository.saveAll(infos);
    }

    /**
     * 单个查询商品属性值
     *
     * @author chenli
     */
    public GoodsPropertyDetail getOne(Long id) {
        return goodsPropertyDetailRepository.findByDetailIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品属性值不存在"));
    }

    /**
     * 分页查询商品属性值
     *
     * @author chenli
     */
    public Page<GoodsPropertyDetail> page(GoodsPropertyDetailQueryRequest queryReq) {
        return goodsPropertyDetailRepository.findAll(
                GoodsPropertyDetailWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询商品属性值
     *
     * @author chenli
     */
    public List<GoodsPropertyDetail> list(GoodsPropertyDetailQueryRequest queryReq) {
        return goodsPropertyDetailRepository.findAll(GoodsPropertyDetailWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author chenli
     */
    public GoodsPropertyDetailVO wrapperVo(GoodsPropertyDetail goodsPropertyDetail) {
        if (goodsPropertyDetail != null) {
            GoodsPropertyDetailVO goodsPropertyDetailVO = KsBeanUtil.convert(goodsPropertyDetail, GoodsPropertyDetailVO.class);
            return goodsPropertyDetailVO;
        }
        return null;
    }
}

