package com.wanmi.sbc.goods.restrictedrecord.service;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.restrictedrecord.RestrictedRecordQueryRequest;
import com.wanmi.sbc.goods.bean.enums.AssignPersonRestrictedType;
import com.wanmi.sbc.goods.bean.enums.RestrictedCycleType;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedVO;
import com.wanmi.sbc.goods.bean.vo.RestrictedRecordSimpVO;
import com.wanmi.sbc.goods.bean.vo.RestrictedRecordVO;
import com.wanmi.sbc.goods.goodsrestrictedsale.service.GoodsRestrictedSaleService;
import com.wanmi.sbc.goods.restrictedrecord.model.root.RestrictedRecord;
import com.wanmi.sbc.goods.restrictedrecord.repository.RestrictedRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>限售业务逻辑</p>
 * @author 限售记录
 * @date 2020-04-11 15:59:01
 */
@Service("RestrictedRecordService")
public class RestrictedRecordService {

	@Autowired
	private RestrictedRecordRepository restrictedRecordRepository;

	@Autowired
	private GoodsRestrictedSaleService goodsRestrictedSaleService;

	/**
	 * 新增限售
	 * @author 限售记录
	 */
	@Transactional
	public RestrictedRecord add(RestrictedRecord entity) {
		restrictedRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 修改限售
	 * @author 限售记录
	 */
	@Transactional
	public RestrictedRecord modify(RestrictedRecord entity) {
		restrictedRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除限售
	 * @author 限售记录
	 */
	@Transactional
	public void deleteById(Long id) {
		restrictedRecordRepository.deleteById(id);
	}

	/**
	 * 批量删除限售
	 * @author 限售记录
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(id -> restrictedRecordRepository.deleteById(id));
	}

	/**
	 * 单个查询限售
	 * @author 限售记录
	 */
	public RestrictedRecord getById(Long id){
		return restrictedRecordRepository.findById(id).orElse(null);
	}

	/**
	 * 单个查询限售记录
	 * @param goodsInfoId
	 * @param customerId
	 * @return
	 */
	public RestrictedRecord findByCustomerIdAndGoodsInfoId(String goodsInfoId, String customerId, Long storeId){
		RestrictedRecord restrictedRecord = restrictedRecordRepository.findByCustomerIdAndGoodsInfoId(customerId,goodsInfoId);
		//修改sonar扫描出来的问题
		if (Objects.isNull(restrictedRecord)){
			return null;
		}
		if(LocalDate.now().isBefore(restrictedRecord.getEndDate())){
			return null;
		}
		if(Objects.nonNull(storeId) && !storeId.equals(restrictedRecord.getStoreId())){
			return null;
		}
		return restrictedRecord;
	}

	/**
	 * 批量查询限售记录
	 * @param goodsInfoIds
	 * @param customerId
	 * @return
	 */
	public List<RestrictedRecord> findByCustomerIdAndGoodsInfoIds(List<String> goodsInfoIds, String customerId, Long storeId){
		return findByCustomerIdAndGoodsInfoIdsAndStoreId(customerId,goodsInfoIds,storeId);
	}

	/**
	 * 批量查询有效的限售记录
	 * @param goodsInfoIds
	 * @param customerId
	 * @return
	 */
	public List<RestrictedRecord> findEffectiveByCustomerIdAndGoodsInfoIds(List<String> goodsInfoIds, String customerId, Long storeId){
		if(StringUtils.isEmpty(customerId)){
			return new ArrayList<>();
		}
		List<RestrictedRecord> restrictedRecords = findByCustomerIdAndGoodsInfoIdsAndStoreId(customerId,goodsInfoIds,storeId);
		return restrictedRecords.stream().filter(r -> (Objects.nonNull(r.getEndDate()) && LocalDate.now().isBefore(r.getEndDate())
				|| (RestrictedCycleType.RESTRICTED_BY_ALL_LIFE == r.getRestrictedCycleType()))).collect(Collectors.toList());
	}


	/**
	 * 批量查询有效的限售记录，删除失效记录
	 * @param goodsInfoIds
	 * @param customerId
	 * @return
	 */
	@Transactional
	public List<RestrictedRecord> findEffectiveRecordAndDelete(List<String> goodsInfoIds, String customerId, Long storeId){
		List<RestrictedRecord> restrictedRecords = findByCustomerIdAndGoodsInfoIdsAndStoreId(customerId,goodsInfoIds,storeId);
		List<RestrictedRecord> deleteRestrictedRecords = restrictedRecords.stream().filter(r -> (Objects.nonNull(r.getEndDate()) && LocalDate.now().isAfter(r.getEndDate()))).collect(Collectors.toList());
		// 删除无效记录
		if(!CollectionUtils.isEmpty(deleteRestrictedRecords)) {
			this.deleteByIdList(deleteRestrictedRecords.stream().map(RestrictedRecord::getRecordId).collect(Collectors.toList()));
		}
		return restrictedRecords.stream().filter(r -> (Objects.nonNull(r.getEndDate()) && LocalDate.now().isBefore(r.getEndDate())
				|| (RestrictedCycleType.RESTRICTED_BY_ALL_LIFE == r.getRestrictedCycleType()) || Objects.isNull(r.getRestrictedCycleType())))
				.collect(Collectors.toList());
	}

	/**
	 * 分页查询限售
	 * @author 限售记录
	 */
	public Page<RestrictedRecord> page(RestrictedRecordQueryRequest queryReq){
		return restrictedRecordRepository.findAll(
				RestrictedRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询限售
	 * @author 限售记录
	 */
	public List<RestrictedRecord> list(RestrictedRecordQueryRequest queryReq){
		return restrictedRecordRepository.findAll(
				RestrictedRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 * @author 限售记录
	 */
	public RestrictedRecordVO wrapperVo(RestrictedRecord restrictedRecord) {
		if (restrictedRecord != null){
			RestrictedRecordVO restrictedRecordVO = new RestrictedRecordVO();
			KsBeanUtil.copyPropertiesThird(restrictedRecord,restrictedRecordVO);
			return restrictedRecordVO;
		}
		return null;
	}

	/**
	 * 批量保存限售记录——新增记录和增加限售数量接口
	 * @param recordSimpVOS
	 * @param customerId
	 * @param customerId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void batchSaveAndPlus(List<RestrictedRecordSimpVO> recordSimpVOS, String customerId, Long storeId){
		Map<String,Long> skuNumMap = recordSimpVOS.stream().collect(Collectors.toMap(RestrictedRecordSimpVO::getSkuId,RestrictedRecordSimpVO::getNum));
		if(!CollectionUtils.isEmpty(recordSimpVOS)){
			List<String> goodInfoIds = recordSimpVOS.stream().map(RestrictedRecordSimpVO::getSkuId).collect(Collectors.toList());
			// 查询所有的限售的信息
 			List<GoodsRestrictedVO> goodsRestrictedVOS = goodsRestrictedSaleService.findByGoodsInfoIds(goodInfoIds, storeId);
			// 查询所有的限售记录
			List<RestrictedRecord> restrictedRecords = this.findEffectiveRecordAndDelete(goodInfoIds,customerId, storeId);
			Map<String,RestrictedRecord> restrictedRecordMap = restrictedRecords.stream().collect(Collectors.toMap(RestrictedRecord::getGoodsInfoId,Function.identity()));
			if(!CollectionUtils.isEmpty(goodsRestrictedVOS)){
				goodsRestrictedVOS.stream().forEach(goodsRestrictedVO -> {
					RestrictedRecord restrictedRecord = restrictedRecordMap.get(goodsRestrictedVO.getGoodsInfoId());
					// 1. 判断是否有该限售记录
					if(Objects.isNull(restrictedRecord)){
						//没有该记录直接保存
						RestrictedRecord rr = new RestrictedRecord();
						rr.setStoreId(storeId);
						rr.setCustomerId(customerId);
						rr.setPurchaseNum(skuNumMap.get(goodsRestrictedVO.getGoodsInfoId()));
						rr.setGoodsInfoId(goodsRestrictedVO.getGoodsInfoId());
						if(Objects.nonNull(goodsRestrictedVO.getRestrictedCycleType())){
							restrictedRecordRepository.save(this.setRestrictTypeAndCycle(rr,goodsRestrictedVO.getRestrictedCycleType()));
						}
						return;
					}
					// 2.只有起购数量限制
					if(Objects.isNull(goodsRestrictedVO.getAssignPersonRestrictedType()) && Objects.isNull(goodsRestrictedVO.getRestrictedNum())
							&& Objects.isNull(goodsRestrictedVO.getRestrictedCycleType())){
						restrictedRecordRepository.plusPurchaseNum(customerId,goodsRestrictedVO.getGoodsInfoId(),skuNumMap.get(goodsRestrictedVO.getGoodsInfoId()));
						return;
					}
					// 2. 指定会员限购且无限售数量与个人限售方式
					if (AssignPersonRestrictedType.RESTRICTED_ASSIGN_CUSTOMER.equals(goodsRestrictedVO.getAssignPersonRestrictedType())
							&& Objects.isNull(goodsRestrictedVO.getRestrictedNum()) && Objects.isNull(goodsRestrictedVO.getRestrictedCycleType())){
						restrictedRecordRepository.plusPurchaseNum(customerId,goodsRestrictedVO.getGoodsInfoId(),skuNumMap.get(goodsRestrictedVO.getGoodsInfoId()));
						return;
					}
					// 3. 非终生限售，如果超期了则删除原有的记录，重新入一条记录 （直接改数量和限售的时间）
					if(!(RestrictedCycleType.RESTRICTED_BY_ALL_LIFE == goodsRestrictedVO.getRestrictedCycleType())
							&& LocalDate.now().isAfter(restrictedRecord.getEndDate())){
						restrictedRecord.setPurchaseNum(skuNumMap.get(goodsRestrictedVO.getGoodsInfoId()));
						if(Objects.nonNull(restrictedRecord.getRestrictedCycleType())){
							restrictedRecordRepository.save(this.setRestrictTypeAndCycle(restrictedRecord,restrictedRecord.getRestrictedCycleType()));
						}
						return;
					}
					// 4. 在周期内 || 终生限售，增加限售记录
					if( (RestrictedCycleType.RESTRICTED_BY_ALL_LIFE == goodsRestrictedVO.getRestrictedCycleType())
							|| ( (LocalDate.now().equals(restrictedRecord.getStartDate()) || LocalDate.now().isAfter(restrictedRecord.getStartDate()))  && LocalDate.now().isBefore(restrictedRecord.getEndDate()))){
						restrictedRecordRepository.plusPurchaseNum(customerId,goodsRestrictedVO.getGoodsInfoId(),skuNumMap.get(goodsRestrictedVO.getGoodsInfoId()));
						return;
					}

				});
			}
		}
	}

	/**
	 * 减少购买记录 —— 订单的逆向流程考虑
	 * @param customerId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int batchReducePurseNum(List<RestrictedRecordSimpVO> recordSimpVOS, String customerId, LocalDateTime orderTime, Long storeId){
		Map<String,Long> skuNumMap = recordSimpVOS.stream().collect(Collectors.toMap(RestrictedRecordSimpVO::getSkuId,RestrictedRecordSimpVO::getNum));
		if(!CollectionUtils.isEmpty(recordSimpVOS)){
			List<String> goodInfoIds = recordSimpVOS.stream().map(RestrictedRecordSimpVO::getSkuId).collect(Collectors.toList());
			// 查询所有的限售记录
			List<RestrictedRecord> restrictedRecords = this.findByCustomerIdAndGoodsInfoIds(goodInfoIds,customerId, storeId);
			// 查询所有的限售的信息
			List<GoodsRestrictedVO> goodsRestrictedVOS = goodsRestrictedSaleService.findByGoodsInfoIds(goodInfoIds, storeId);
			Map<String,RestrictedRecord> restrictedRecordMap = restrictedRecords.stream().collect(Collectors.toMap(RestrictedRecord::getGoodsInfoId, Function.identity(),(key1, key2) -> key2));
			goodsRestrictedVOS.stream().forEach(goodsRestrictedVO -> {
				RestrictedRecord restrictedRecord = restrictedRecordMap.get(goodsRestrictedVO.getGoodsInfoId());
				// 1. 查询是否有该记录
				if(Objects.isNull(restrictedRecord)){
					return ;
				}

				//设置的是订单限售不做处理
				if((RestrictedCycleType.RESTRICTED_BY_ORDER == goodsRestrictedVO.getRestrictedCycleType())){
					return ;
				}

				// 2. 非终生限售，不在周期内，则删除原有的限售记录
				if( !(RestrictedCycleType.RESTRICTED_BY_ALL_LIFE == goodsRestrictedVO.getRestrictedCycleType())
						&& (Objects.isNull(restrictedRecord.getEndDate()) || LocalDate.now().equals(restrictedRecord.getEndDate()) || LocalDate.now().isAfter(restrictedRecord.getEndDate()) )){
					restrictedRecordRepository.deleteById(restrictedRecord.getRecordId());
					return ;
				}

				// 3. 终身限售 || 周期内限售，则扣除购买的记录

				if(this.ifReduceFlag(goodsRestrictedVO,restrictedRecord, orderTime)){
					Long reduceNum;

					if(restrictedRecord.getPurchaseNum().compareTo(skuNumMap.get(goodsRestrictedVO.getGoodsInfoId())) < 0){
						reduceNum = restrictedRecord.getPurchaseNum();
					}else{
						reduceNum = skuNumMap.get(goodsRestrictedVO.getGoodsInfoId());
					}
					restrictedRecordRepository.reducePurchaseNum(customerId,goodsRestrictedVO.getGoodsInfoId(),reduceNum);
				}
			});
		}
		return Constants.yes;
	}

	/**
	 * 判断需要返还的flag
	 * @param goodsRestrictedVO
	 * @return
	 */
	private Boolean ifReduceFlag(GoodsRestrictedVO goodsRestrictedVO ,RestrictedRecord restrictedRecord, LocalDateTime orderTime){
		//终身限售
		if(RestrictedCycleType.RESTRICTED_BY_ALL_LIFE == goodsRestrictedVO.getRestrictedCycleType()){
			return Boolean.TRUE;
		}
		//在周期内
		if((LocalDate.now().isAfter(restrictedRecord.getStartDate()) || LocalDate.now().equals(restrictedRecord.getStartDate()))
				&& LocalDate.now().isBefore(restrictedRecord.getEndDate())){
			// 下单时间处于限售配置生效时间之内
			if (Objects.nonNull(goodsRestrictedVO.getUpdateTime())) {
                if (orderTime.isAfter(goodsRestrictedVO.getUpdateTime())
                        || orderTime.isEqual(goodsRestrictedVO.getUpdateTime())) {
                    return Boolean.TRUE;
                }
			} else if(orderTime.isAfter(goodsRestrictedVO.getCreateTime()) || orderTime.isEqual(goodsRestrictedVO.getCreateTime())){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 批量删除限售记录
	 * @param goodsInfoIds
	 */
	public void deletBySkuIds(List<String> goodsInfoIds){
		restrictedRecordRepository.deleteAllByGoodsInfoIds(goodsInfoIds);
	}

	/**
	 * 根据限售周期类型设置开始时间和结束时间
	 * @return
	 */
	public RestrictedRecord setRestrictTypeAndCycle(RestrictedRecord restrictedRecord, RestrictedCycleType restrictedCycleType){
		if (Objects.nonNull(restrictedCycleType)) {
			switch (restrictedCycleType) {
				case RESTRICTED_BY_DAY:
					restrictedRecord.setStartDate(LocalDate.now());
					restrictedRecord.setEndDate(DateUtil.tomorrowDay());
					break;
				case RESTRICTED_BY_WEEK:
					restrictedRecord.setStartDate(DateUtil.firstDayOfWeek());
					restrictedRecord.setEndDate(DateUtil.firstDayOfNextWeek());
					break;
				case RESTRICTED_BY_MONTH:
					restrictedRecord.setStartDate(DateUtil.firstDayOfMonth());
					restrictedRecord.setEndDate(DateUtil.firstDayOfNextMonth());
					break;
				case RESTRICTED_BY_YEAR:
					restrictedRecord.setStartDate(DateUtil.firstDayOfYear());
					restrictedRecord.setEndDate(DateUtil.firstDayOfNextYear());
					break;
				default:
					break;
			}
			restrictedRecord.setRestrictedCycleType(restrictedCycleType);
		}
		return restrictedRecord;
	}

	/**
	 * 根据用户ID，SKUID集合，门店ID查询限购记录
	 * @param customerId    用户ID
	 * @param goodsInfoIds  SKUID集合
	 * @param storeId		门店ID
	 * @return				限购记录
	 */
	private List<RestrictedRecord> findByCustomerIdAndGoodsInfoIdsAndStoreId(String customerId, List<String> goodsInfoIds,
														   Long storeId){
        if (Objects.nonNull(storeId)) {
			return restrictedRecordRepository.findByCustomerIdAndGoodsInfoIdsAndStoreId(
					customerId, goodsInfoIds,storeId);
		}
		return restrictedRecordRepository.findByCustomerIdAndGoodsInfoIds(customerId, goodsInfoIds);
	}
}
