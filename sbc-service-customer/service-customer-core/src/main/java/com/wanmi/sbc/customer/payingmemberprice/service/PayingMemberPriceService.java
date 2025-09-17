package com.wanmi.sbc.customer.payingmemberprice.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelQueryRequest;
import com.wanmi.sbc.customer.bean.vo.PayingMemberRightsRelVO;
import com.wanmi.sbc.customer.payingmemberrightsrel.model.root.PayingMemberRightsRel;
import com.wanmi.sbc.customer.payingmemberrightsrel.repository.PayingMemberRightsRelRepository;
import com.wanmi.sbc.customer.payingmemberrightsrel.service.PayingMemberRightsRelService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.customer.payingmemberprice.repository.PayingMemberPriceRepository;
import com.wanmi.sbc.customer.payingmemberprice.model.root.PayingMemberPrice;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceQueryRequest;
import com.wanmi.sbc.customer.bean.vo.PayingMemberPriceVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>付费设置表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Service("PayingMemberPriceService")
public class PayingMemberPriceService {

	@Autowired
	private PayingMemberPriceRepository payingMemberPriceRepository;

	@Autowired
	private PayingMemberRightsRelRepository payingMemberRightsRelRepository;

	@Autowired
	private PayingMemberRightsRelService payingMemberRightsRelService;


	/**
	 * 新增付费设置表
	 * @author zhanghao
	 */
	@Transactional
	public void add(PayingMemberPriceAddRequest payingMemberPriceAddRequest) {
		PayingMemberPrice payingMemberPrice = KsBeanUtil.convert(payingMemberPriceAddRequest, PayingMemberPrice.class);
		payingMemberPrice.setDelFlag(DeleteFlag.NO);
		payingMemberPrice.setCreateTime(LocalDateTime.now());
		List<PayingMemberRightsRelAddRequest> payingMemberRightsRelAddRequests = payingMemberPriceAddRequest.getPayingMemberRightsRelAddRequests();
		//保存付费设置，获取付费设置id
		payingMemberPriceRepository.save(payingMemberPrice);
		payingMemberRightsRelRepository.saveAll(payingMemberRightsRelAddRequests.parallelStream().map(payingMemberRightsRelAddRequest -> {
			PayingMemberRightsRel payingMemberRightsRel = KsBeanUtil.convert(payingMemberRightsRelAddRequest, PayingMemberRightsRel.class);
			payingMemberRightsRel.setPriceId(payingMemberPrice.getPriceId());
			payingMemberRightsRel.setDelFlag(DeleteFlag.NO);
			payingMemberRightsRel.setLevelId(payingMemberPrice.getLevelId());
			payingMemberRightsRel.setCreateTime(LocalDateTime.now());
			payingMemberRightsRel.setCreatePerson(payingMemberPrice.getCreatePerson());
			return payingMemberRightsRel;
		}).collect(Collectors.toList()));
	}

	/**
	 * 修改付费设置表
	 * @author zhanghao
	 */
	@Transactional
	public PayingMemberPrice modify(PayingMemberPrice entity) {
		payingMemberPriceRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除付费设置表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberPrice entity) {
		payingMemberPriceRepository.save(entity);
	}

	/**
	 * 批量删除付费设置表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Integer> ids) {
		payingMemberPriceRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询付费设置表
	 * @author zhanghao
	 */
	public PayingMemberPrice getOne(Integer id){
		return payingMemberPriceRepository.findByPriceIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "付费设置表不存在"));
	}

	/**
	 * 分页查询付费设置表
	 * @author zhanghao
	 */
	public Page<PayingMemberPrice> page(PayingMemberPriceQueryRequest queryReq){
		return payingMemberPriceRepository.findAll(
				PayingMemberPriceWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询付费设置表
	 * @author zhanghao
	 */
	public List<PayingMemberPriceVO> list(PayingMemberPriceQueryRequest queryReq){
		List<PayingMemberPrice> payingMemberPriceList = payingMemberPriceRepository.findAll(PayingMemberPriceWhereCriteriaBuilder.build(queryReq));
		List<PayingMemberPriceVO> newList = payingMemberPriceList.stream().map(entity -> {
			PayingMemberPriceVO payingMemberPriceVO = wrapperVo(entity);
			List<PayingMemberRightsRel> payingMemberRightsRelList = payingMemberRightsRelService.list(PayingMemberRightsRelQueryRequest.builder()
					.priceId(entity.getPriceId())
					.delFlag(DeleteFlag.NO)
					.build());
			payingMemberPriceVO.setPayingMemberRightsRelVOS(KsBeanUtil.convert(payingMemberRightsRelList, PayingMemberRightsRelVO.class));
			return payingMemberPriceVO;
		}).collect(Collectors.toList());
		return newList;
	}


	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberPriceVO wrapperVo(PayingMemberPrice payingMemberPrice) {
		if (payingMemberPrice != null){
			PayingMemberPriceVO payingMemberPriceVO = KsBeanUtil.convert(payingMemberPrice, PayingMemberPriceVO.class);
			return payingMemberPriceVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberPriceQueryRequest queryReq) {
		return payingMemberPriceRepository.count(PayingMemberPriceWhereCriteriaBuilder.build(queryReq));
	}
}

