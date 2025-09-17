package com.wanmi.sbc.goods.flashsalecate.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.flashsalecate.FlashSaleCateQueryRequest;
import com.wanmi.sbc.goods.api.request.flashsalecate.FlashSaleCateSortRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.FlashSaleCateVO;
import com.wanmi.sbc.goods.flashsalecate.model.root.FlashSaleCate;
import com.wanmi.sbc.goods.flashsalecate.repository.FlashSaleCateRepository;
import com.wanmi.sbc.goods.flashsalegoods.repository.FlashSaleGoodsRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>秒杀分类业务逻辑</p>
 *
 * @author yxz
 * @date 2019-06-11 10:11:15
 */
@Service("FlashSaleCateService")
public class FlashSaleCateService {
    @Autowired
    private FlashSaleCateRepository flashSaleCateRepository;

    @Autowired
    private FlashSaleGoodsRepository flashSaleGoodsRepository;

    /**
     * 新增秒杀分类
     *
     * @author yxz
     */
    @Transactional
    public FlashSaleCate add(FlashSaleCate entity) {
        FlashSaleCateQueryRequest cateQueryRequest = FlashSaleCateQueryRequest.builder().delFlag(DeleteFlag.NO).build();
        List<FlashSaleCate> flashSaleCates = list(cateQueryRequest);
        if (flashSaleCates.size() >= Constants.NUM_16) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030102);
        }

        if (flashSaleCates.stream()
                .anyMatch(cate ->
                        StringUtils.equals(entity.getCateName(), cate.getCateName()))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030084);
        }

        flashSaleCateRepository.save(entity);
        return entity;
    }

    /**
     * 修改秒杀分类
     *
     * @author yxz
     */
    @Transactional
    public FlashSaleCate modify(FlashSaleCate entity) {
        FlashSaleCateQueryRequest cateQueryRequest = FlashSaleCateQueryRequest.builder().delFlag(DeleteFlag.NO)
                .cateName(entity.getCateName()).build();
        List<FlashSaleCate> flashSaleCates = list(cateQueryRequest);
        //除了自已的分类以外
        if (CollectionUtils.isNotEmpty(flashSaleCates) && !flashSaleCates.get(0).getCateId().equals(entity.getCateId())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030101);
        }

        flashSaleCateRepository.save(entity);
        return entity;
    }

    /**
     * 单个删除秒杀分类
     *
     * @author yxz
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        flashSaleGoodsRepository.updateCateId(id,DeleteFlag.NO);
        flashSaleCateRepository.modifyDelFlagById(id);
    }

    /**
     * 列表查询秒杀分类
     *
     * @author yxz
     */
    public List<FlashSaleCate> list(FlashSaleCateQueryRequest queryReq) {
        Sort sort = queryReq.getSort();
        if (Objects.nonNull(sort)) {
            return flashSaleCateRepository.findAll(FlashSaleCateWhereCriteriaBuilder.build(queryReq), sort);
        } else {
            return flashSaleCateRepository.findAll(FlashSaleCateWhereCriteriaBuilder.build(queryReq));
        }
    }

    /**
     * 拖拽排序
     *
     * @param request
     */
    @Transactional
    public void editSort(FlashSaleCateSortRequest request) {
        List<Long> cateIdList = request.getCateIdList();
        for (int i = 0; i < cateIdList.size(); i++) {
            Optional<FlashSaleCate> flashSaleCateOptional = flashSaleCateRepository.findById(cateIdList.get(i));
            if (flashSaleCateOptional.isPresent()) {
                FlashSaleCate flashSaleCate = flashSaleCateOptional.get();
                flashSaleCate.setUpdatePerson(request.getUpdatePerson());
                flashSaleCate.setUpdateTime(request.getUpdateTime());
                flashSaleCate.setSort(i  + 1);
                flashSaleCateRepository.save(flashSaleCate);
            }
        }
    }

    /**
     * 将实体包装成VO
     *
     * @author yxz
     */
    public FlashSaleCateVO wrapperVo(FlashSaleCate flashSaleCate) {
        if (flashSaleCate != null) {
            FlashSaleCateVO flashSaleCateVO = new FlashSaleCateVO();
            KsBeanUtil.copyPropertiesThird(flashSaleCate, flashSaleCateVO);
            return flashSaleCateVO;
        }
        return null;
    }
}
