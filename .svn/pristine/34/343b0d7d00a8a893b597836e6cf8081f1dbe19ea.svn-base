package com.wanmi.sbc.vas.commodityscoringalgorithm.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmQueryRequest;
import com.wanmi.sbc.vas.bean.vo.CommodityScoringAlgorithmVO;
import com.wanmi.sbc.vas.commodityscoringalgorithm.model.root.CommodityScoringAlgorithm;
import com.wanmi.sbc.vas.commodityscoringalgorithm.repository.CommodityScoringAlgorithmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>商品评分算法业务逻辑</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@Service("CommodityScoringAlgorithmService")
public class CommodityScoringAlgorithmService {
	@Autowired
	private CommodityScoringAlgorithmRepository commodityScoringAlgorithmRepository;

	/**
	 * 新增商品评分算法
	 * @author Bob
	 */
	@Transactional
	public CommodityScoringAlgorithm add(CommodityScoringAlgorithm entity) {
		commodityScoringAlgorithmRepository.save(entity);
		return entity;
	}

	/**
	 * 修改商品评分算法
	 * @author Bob
	 */
	@Transactional
	public void modify(List<CommodityScoringAlgorithm> entity) {
		List<Long> ids = entity.stream().map(CommodityScoringAlgorithm::getId).collect(Collectors.toList());
		Map<Long, CommodityScoringAlgorithm> commodityScoringAlgorithmMap =
				list(CommodityScoringAlgorithmQueryRequest.builder().idList(ids).build())
						.stream().collect(Collectors.toMap(CommodityScoringAlgorithm::getId, Function.identity()));
		entity.forEach(x -> {
			if (commodityScoringAlgorithmMap.containsKey(x.getId())){
				CommodityScoringAlgorithm item = commodityScoringAlgorithmMap.get(x.getId());
				item.setIsSelected(x.getIsSelected());
				item.setWeight(x.getWeight());
				commodityScoringAlgorithmRepository.save(item);
			}
		});
	}

	/**
	 * 单个删除商品评分算法
	 * @author Bob
	 */
	@Transactional
	public void deleteById(CommodityScoringAlgorithm entity) {
		commodityScoringAlgorithmRepository.save(entity);
	}

	/**
	 * 批量删除商品评分算法
	 * @author Bob
	 */
	@Transactional
	public void deleteByIdList(List<CommodityScoringAlgorithm> infos) {
		commodityScoringAlgorithmRepository.saveAll(infos);
	}

	/**
	 * 单个查询商品评分算法
	 * @author Bob
	 */
	public CommodityScoringAlgorithm getOne(Long id){
		return commodityScoringAlgorithmRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品评分算法不存在"));
	}

	/**
	 * 分页查询商品评分算法
	 * @author Bob
	 */
	public Page<CommodityScoringAlgorithm> page(CommodityScoringAlgorithmQueryRequest queryReq){
		return commodityScoringAlgorithmRepository.findAll(
				CommodityScoringAlgorithmWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询商品评分算法
	 * @author Bob
	 */
	public List<CommodityScoringAlgorithm> list(CommodityScoringAlgorithmQueryRequest queryReq){
		return commodityScoringAlgorithmRepository.findAll(CommodityScoringAlgorithmWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author Bob
	 */
	public CommodityScoringAlgorithmVO wrapperVo(CommodityScoringAlgorithm commodityScoringAlgorithm) {
		if (commodityScoringAlgorithm != null){
			CommodityScoringAlgorithmVO commodityScoringAlgorithmVO = KsBeanUtil.convert(commodityScoringAlgorithm, CommodityScoringAlgorithmVO.class);
			return commodityScoringAlgorithmVO;
		}
		return null;
	}
}

