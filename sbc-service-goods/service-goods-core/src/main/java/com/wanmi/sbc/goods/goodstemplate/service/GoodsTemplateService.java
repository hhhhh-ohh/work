package com.wanmi.sbc.goods.goodstemplate.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.goods.api.request.goodstemplate.*;
import com.wanmi.sbc.goods.goodstemplate.model.root.GoodsTemplateRel;
import com.wanmi.sbc.goods.goodstemplate.repository.GoodsTemplateRelRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.goods.goodstemplate.repository.GoodsTemplateRepository;
import com.wanmi.sbc.goods.goodstemplate.model.root.GoodsTemplate;
import com.wanmi.sbc.goods.bean.vo.GoodsTemplateVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>GoodsTemplate业务逻辑</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Service("GoodsTemplateService")
public class GoodsTemplateService {
	@Autowired
	private GoodsTemplateRepository goodsTemplateRepository;

	@Autowired
	private GoodsTemplateRelRepository goodsTemplateRelRepository;

	/**
	 * 新增GoodsTemplate
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public GoodsTemplate add(@Valid GoodsTemplateAddRequest request) {
		GoodsTemplate goodsTemplate = KsBeanUtil.convert(request, GoodsTemplate.class);
		goodsTemplate.setCreateTime(LocalDateTime.now());
		goodsTemplate.setUpdatePerson(request.getCreatePerson());
		goodsTemplate.setUpdateTime(LocalDateTime.now());
		goodsTemplateRepository.save(goodsTemplate);
		return goodsTemplate;
	}

	/**
	 * 修改GoodsTemplate
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public GoodsTemplate modify(@Valid GoodsTemplateModifyRequest request) {
		GoodsTemplate goodsTemplate = goodsTemplateRepository
				.findByIdAndDelFlag(request.getId(), DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
		if (!Objects.equals(goodsTemplate.getStoreId(),request.getStoreId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}
		goodsTemplate.setName(request.getName());
		goodsTemplate.setPosition(request.getPosition());
		goodsTemplate.setTopContent(request.getTopContent());
		goodsTemplate.setDownContent(request.getDownContent());
		goodsTemplate.setUpdatePerson(request.getUpdatePerson());
		goodsTemplate.setUpdateTime(LocalDateTime.now());
		goodsTemplateRepository.save(goodsTemplate);
		return goodsTemplate;
	}

	/**
	 * 单个删除GoodsTemplate
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void deleteById(@Valid GoodsTemplateDelByIdRequest request) {
		GoodsTemplate goodsTemplate = goodsTemplateRepository
				.findByIdAndDelFlag(request.getId(), DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
		if (!Objects.equals(goodsTemplate.getStoreId(),request.getStoreId())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
		}
		goodsTemplate.setDelFlag(DeleteFlag.YES);
		goodsTemplate.setUpdatePerson(request.getUpdatePerson());
		goodsTemplate.setUpdateTime(LocalDateTime.now());
		goodsTemplateRepository.save(goodsTemplate);
		goodsTemplateRelRepository.deleteByTemplateId(request.getId());
	}

	/**
	 * 批量删除GoodsTemplate
	 * @author 黄昭
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		goodsTemplateRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询GoodsTemplate
	 * @author 黄昭
	 */
	public GoodsTemplate getOne(Long id){
		return goodsTemplateRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品模版不存在"));
	}

	/**
	 * 分页查询GoodsTemplate
	 * @author 黄昭
	 */
	public Page<GoodsTemplate> page(GoodsTemplateQueryRequest queryReq){
		return goodsTemplateRepository.findAll(
				GoodsTemplateWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询GoodsTemplate
	 * @author 黄昭
	 */
	public List<GoodsTemplate> list(GoodsTemplateQueryRequest queryReq){
		return goodsTemplateRepository.findAll(GoodsTemplateWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 黄昭
	 */
	public GoodsTemplateVO wrapperVo(GoodsTemplate goodsTemplate) {
		if (goodsTemplate != null){
			GoodsTemplateVO goodsTemplateVO = KsBeanUtil.convert(goodsTemplate, GoodsTemplateVO.class);
			return goodsTemplateVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 黄昭
	 */
	public Long count(GoodsTemplateQueryRequest queryReq) {
		return goodsTemplateRepository.count(GoodsTemplateWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 根据商品Id获取商品模版信息
	 * @param request
	 * @return
	 */
	public GoodsTemplateVO getByGoodsId(GoodsTemplateByGoodsIdRequest request) {
		GoodsTemplateRel goodsTemplateRel = goodsTemplateRelRepository.getByGoodsId(request.getGoodsId());
		if (Objects.nonNull(goodsTemplateRel)){
			GoodsTemplate goodsTemplate = goodsTemplateRepository.findByIdAndDelFlag(goodsTemplateRel.getTemplateId(), DeleteFlag.NO)
					.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品模版不存在"));
			return wrapperVo(goodsTemplate);
		}
		return null;
	}
}

