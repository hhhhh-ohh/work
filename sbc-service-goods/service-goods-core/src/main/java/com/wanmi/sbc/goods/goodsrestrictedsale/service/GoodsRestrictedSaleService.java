package com.wanmi.sbc.goods.goodsrestrictedsale.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.api.constant.GoodsRestrictedErrorTips;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleAddRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleByInfoIdRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleQueryRequest;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.GoodsRestrictedSaleByIdResponse;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedCustomerRelaVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedPurchaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedSaleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedValidateVO;
import com.wanmi.sbc.goods.goodsrestrictedcustomerrela.model.root.GoodsRestrictedCustomerRela;
import com.wanmi.sbc.goods.goodsrestrictedcustomerrela.service.GoodsRestrictedCustomerRelaService;
import com.wanmi.sbc.goods.goodsrestrictedsale.model.root.GoodsRestrictedSale;
import com.wanmi.sbc.goods.goodsrestrictedsale.repository.GoodsRestrictedSaleRepository;
import com.wanmi.sbc.goods.restrictedrecord.model.root.RestrictedRecord;
import com.wanmi.sbc.goods.restrictedrecord.service.RestrictedRecordService;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressVO;
import io.seata.common.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>限售配置业务逻辑</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@Service("GoodsRestrictedSaleService")
public class GoodsRestrictedSaleService {
	@Autowired
	private GoodsRestrictedSaleRepository goodsRestrictedSaleRepository;

	@Autowired
	private GoodsRestrictedCustomerRelaService goodsRestrictedCustomerRelaService;

	@Autowired
	private RestrictedRecordService restrictedRecordService;

	@Autowired
	private ThirdAddressQueryProvider thirdAddressQueryProvider;

	/**
	 * 秒杀活动抢购商品订单类型："FLASH_SALE"
	 */
	public static final String FLASH_SALE_GOODS_ORDER_TYPE = "FLASH_SALE";


	/**
	 * 获取商品的起售量
	 * @author baijz
	 */
	@Transactional
	public GoodsRestrictedSaleByIdResponse findInGoodsInfoId(GoodsRestrictedSaleByInfoIdRequest request) {
		GoodsRestrictedSale goodsRestrictedSale = goodsRestrictedSaleRepository.findInGoodsInfoId(request.getGoodsInfoId());
		GoodsRestrictedSaleByIdResponse response = new GoodsRestrictedSaleByIdResponse();
		GoodsRestrictedSaleVO goodsRestrictedSaleVO = KsBeanUtil.copyPropertiesThird(goodsRestrictedSale, GoodsRestrictedSaleVO.class);
		response.setGoodsRestrictedSaleVO(goodsRestrictedSaleVO);
		return  response;
	}
	

	/**
	 * 批量新增限售配置
	 * @author baijz
	 */
	@Transactional
	public List<GoodsRestrictedSale> addBatch(GoodsRestrictedSaleAddRequest request) {
		List<GoodsRestrictedSale> goodsRestrictedSales = new ArrayList<>();
		GoodsRestrictedSale goodsRestrictedSale = KsBeanUtil.convert(request, GoodsRestrictedSale.class);
		//数据格式化
		GoodsRestrictedSale goodsRestrictedSaleM = this.setGoodsRestrictedFormate(goodsRestrictedSale);
		if(CollectionUtils.isNotEmpty(request.getGoodsInfoIds())){
			//清除已设置过的限售及会员关系
			List<GoodsRestrictedSale> goodsRestrictedSales1 = this.list(GoodsRestrictedSaleQueryRequest.builder().goodsInfoIds(request.getGoodsInfoIds()).build());
			if(CollectionUtils.isNotEmpty(goodsRestrictedSales1)){
				goodsRestrictedCustomerRelaService.deleteAllByRestrictedSaleIds(goodsRestrictedSales1.stream()
						.map(GoodsRestrictedSale::getRestrictedId).collect(Collectors.toList()));
			}
			goodsRestrictedSaleRepository.deleteAllByGoodsInfoIds(request.getGoodsInfoIds());
			//保存现有的限售方式
			request.getGoodsInfoIds().stream().forEach((i)->{
				GoodsRestrictedSale goodsRestrictedSaleT = KsBeanUtil.convert(goodsRestrictedSaleM, GoodsRestrictedSale.class);
				goodsRestrictedSaleT.setGoodsInfoId(i);
				goodsRestrictedSales.add(goodsRestrictedSaleT);
			});
		}else{
			goodsRestrictedSales.add(goodsRestrictedSale);
		}
		List<GoodsRestrictedSale> goodsRestrictedSaleList = goodsRestrictedSaleRepository.saveAll(goodsRestrictedSales);
		// 保存会员的限售关系
		goodsRestrictedSaleList.stream().forEach(item ->{
			this.deleteAndSaveRestrictedCustomer(
					item,request.getCustomerIds(),request.getCustomerLevelIds(),request.getAddressIds());
		});
		// 删除限售记录
		restrictedRecordService.deletBySkuIds(goodsRestrictedSaleList.stream().map(GoodsRestrictedSale::getGoodsInfoId).collect(Collectors.toList()));
		return goodsRestrictedSaleList;
	}


	/**
	 * 修改限售配置
	 * @author baijz
	 */
	@Transactional
	public GoodsRestrictedSale modifyNew(GoodsRestrictedSaleModifyRequest request) {
		//校验地址
		this.checkAddress(request.getAddressIds());

		GoodsRestrictedSale goodsRestrictedSale = KsBeanUtil.convert(request, GoodsRestrictedSale.class);
		//数据格式化
		goodsRestrictedSale = this.setGoodsRestrictedFormate(goodsRestrictedSale);
		//保存成功后，更新修改会员关系表
		GoodsRestrictedSale goodsRestrictedSale1 = goodsRestrictedSaleRepository.save(goodsRestrictedSale);
		goodsRestrictedSale1.setGoodsRestrictedCustomerRelas(this.deleteAndSaveRestrictedCustomer(
				goodsRestrictedSale1,request.getCustomerIds(),request.getCustomerLevelIds(),request.getAddressIds()));
		//删除限售记录
		restrictedRecordService.deletBySkuIds(Collections.singletonList(goodsRestrictedSale.getGoodsInfoId()));
		return goodsRestrictedSale1;
	}

	/**
	 * 根据地址ids，校验地址的正确性
	 **/
	private void checkAddress(List<String> addressIds) {
		if (CollectionUtils.isEmpty(addressIds)){
			return;
		}
		String address = org.apache.commons.lang3.StringUtils.join(addressIds, Constants.CATE_PATH_SPLITTER);
		String[] split = org.apache.commons.lang3.StringUtils.split(address, Constants.CATE_PATH_SPLITTER);
		List<String> addressIdList = Arrays.stream(split).distinct().collect(Collectors.toList());

		ThirdAddressListRequest request = ThirdAddressListRequest.builder()
				.delFlag(DeleteFlag.NO)
				.idList(addressIdList)
				.build();

		List<ThirdAddressVO> addressList = thirdAddressQueryProvider.list(request).getContext().getThirdAddressList();
		if (addressList.size() != addressIdList.size()){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}

	/**
	 * 保存限售的会员配置
	 * @param goodsRestrictedSale
	 * @param customerIds
	 * @param customerLevelIds
	 * @return
	 */
	private List<GoodsRestrictedCustomerRela> deleteAndSaveRestrictedCustomer(GoodsRestrictedSale goodsRestrictedSale
			, List<String> customerIds, List<Long> customerLevelIds, List<String> addressIds){

		if(CollectionUtils.isNotEmpty(goodsRestrictedSale.getGoodsRestrictedCustomerRelas())) {
			// 删除已关联的数据
			goodsRestrictedCustomerRelaService.deleteByIdList(
					goodsRestrictedSale.getGoodsRestrictedCustomerRelas().stream().map(GoodsRestrictedCustomerRela::getRelaId).collect(Collectors.toList()));
		}

		// 保存修改后的数据
		List<GoodsRestrictedCustomerRela> relaList = new ArrayList<>();
		if(AssignPersonRestrictedType.RESTRICTED_CUSTOMER_LEVEL == goodsRestrictedSale.getAssignPersonRestrictedType()){
			if(CollectionUtils.isNotEmpty(customerLevelIds)){
				customerLevelIds.stream().forEach(levelId -> {
					GoodsRestrictedCustomerRela grcr = new GoodsRestrictedCustomerRela();
					grcr.setCustomerLevelId(levelId);
					grcr.setRestrictedSaleId(goodsRestrictedSale.getRestrictedId());
					grcr.setAssignPersonRestrictedType(goodsRestrictedSale.getAssignPersonRestrictedType());
					relaList.add(grcr);
				});
			}
		}
		if(AssignPersonRestrictedType.RESTRICTED_ASSIGN_CUSTOMER == goodsRestrictedSale.getAssignPersonRestrictedType()){
			if(CollectionUtils.isNotEmpty(customerIds)){
				customerIds.stream().forEach(customerId -> {
					GoodsRestrictedCustomerRela grcr = new GoodsRestrictedCustomerRela();
					grcr.setCustomerId(customerId);
					grcr.setRestrictedSaleId(goodsRestrictedSale.getRestrictedId());
					grcr.setAssignPersonRestrictedType(goodsRestrictedSale.getAssignPersonRestrictedType());
					relaList.add(grcr);
				});
			}
		}
		if(AssignPersonRestrictedType.RESTRICTED_ADDRESS == goodsRestrictedSale.getAssignPersonRestrictedType()){
			if(CollectionUtils.isNotEmpty(addressIds)){
				addressIds.stream().forEach(addressId -> {
					GoodsRestrictedCustomerRela grcr = new GoodsRestrictedCustomerRela();
					grcr.setAddressId(addressId);
					grcr.setRestrictedSaleId(goodsRestrictedSale.getRestrictedId());
					grcr.setAssignPersonRestrictedType(goodsRestrictedSale.getAssignPersonRestrictedType());
					relaList.add(grcr);
				});
			}
		}
		return goodsRestrictedCustomerRelaService.addBatch(relaList);
	}


	/**
	 * 单个删除限售配置
	 * @author baijz
	 */
	@Transactional
	public void deleteById(Long id) {
		goodsRestrictedSaleRepository.deleteById(id);
	}

	/**
	 * 批量删除限售配置
	 * @author baijz
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		goodsRestrictedSaleRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询限售配置
	 * @author baijz
	 */
	public GoodsRestrictedSale getById(Long id){
		return goodsRestrictedSaleRepository.findById(id).orElse(null);
	}

	/**
	 * 分页查询限售配置
	 * @author baijz
	 */
	public Page<GoodsRestrictedSale> page(GoodsRestrictedSaleQueryRequest queryReq){
		return goodsRestrictedSaleRepository.findAll(
				GoodsRestrictedSaleWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询限售配置
	 * @author baijz
	 */
	public List<GoodsRestrictedSale> list(GoodsRestrictedSaleQueryRequest queryReq){
		return goodsRestrictedSaleRepository.findAll(
				GoodsRestrictedSaleWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 * @author baijz
	 */
	public GoodsRestrictedSaleVO wrapperVo(GoodsRestrictedSale goodsRestrictedSale) {
		if (goodsRestrictedSale != null){
			GoodsRestrictedSaleVO goodsRestrictedSaleVO=KsBeanUtil.convert(goodsRestrictedSale,GoodsRestrictedSaleVO.class);
			return goodsRestrictedSaleVO;
		}
		return null;
	}

	/**
	 * 将实体包装成VO
	 * @author baijz
	 */
	public List<GoodsRestrictedSaleVO> wrapperVoList(List<GoodsRestrictedSale> goodsRestrictedSale) {
		if (goodsRestrictedSale != null){
			return  KsBeanUtil.convertList(goodsRestrictedSale,GoodsRestrictedSaleVO.class);
		}
		return null;
	}

	/**
	 * 格式化商品限售数据
	 */
	public GoodsRestrictedSale setGoodsRestrictedFormate(GoodsRestrictedSale goodsRestrictedSale){
		// 没设置限售方式，清空限售方式里面的设置
		if(DefaultFlag.NO.equals(goodsRestrictedSale.getRestrictedWay())){
			//个人限售的方式(  0:终生限售  1:周期限售)
			goodsRestrictedSale.setPersonRestrictedType(null);
			//个人限售的周期 (0:天  1:周  2:月  3:年)
			goodsRestrictedSale.setPersonRestrictedCycle(null);
			//特定会员的限售类型 0: 会员等级  1：指定会员
			goodsRestrictedSale.setAssignPersonRestrictedType(null);
			//是否指定会员限售的标识
			goodsRestrictedSale.setRestrictedAssignFlag(DefaultFlag.NO);
			//是否每人限售标识
			goodsRestrictedSale.setRestrictedPrePersonFlag(DefaultFlag.NO);
			//是否每单限售的标识
			goodsRestrictedSale.setRestrictedPreOrderFlag(DefaultFlag.NO);
			// 限售方式 0: 按会员 1：按订单
			goodsRestrictedSale.setRestrictedType(null);
			//清空限售数量
			goodsRestrictedSale.setRestrictedNum(null);

		}else{
			//设置了按人，并且没有设置限制每人购买数量
			if(RestrictedType.RESTRICTED_HUMAN == goodsRestrictedSale.getRestrictedType()
					&& DefaultFlag.NO.equals(goodsRestrictedSale.getRestrictedPrePersonFlag())){
				//清空限售数量
				goodsRestrictedSale.setRestrictedNum(null);
				//个人限售的方式(  0:终生限售  1:周期限售)
				goodsRestrictedSale.setPersonRestrictedType(null);
				//个人限售的周期 (0:天  1:周  2:月  3:年)
				goodsRestrictedSale.setPersonRestrictedCycle(null);


			}
		}
		//没设置起售数量
		if(DefaultFlag.NO.equals(goodsRestrictedSale.getRestrictedStartNum())){
			//清空起售数量
			goodsRestrictedSale.setStartSaleNum(NumberUtils.LONG_ONE);
		}


		/**
		 * 按人设置限售数据
		 */
		if(RestrictedType.RESTRICTED_HUMAN == goodsRestrictedSale.getRestrictedType()){
			goodsRestrictedSale.setRestrictedPreOrderFlag(DefaultFlag.NO);
			if(DefaultFlag.NO.equals(goodsRestrictedSale.getPersonRestrictedType())){
				goodsRestrictedSale.setPersonRestrictedType(null);
				goodsRestrictedSale.setPersonRestrictedCycle(null);
			}
			if(DefaultFlag.NO.equals(goodsRestrictedSale.getRestrictedAssignFlag())){
				goodsRestrictedSale.setAssignPersonRestrictedType(null);
			}
		}

		/**
		 * 按订单，清除按人设置的数据
		 */
		if(RestrictedType.RESTRICTED_ORDER == goodsRestrictedSale.getRestrictedType()){
			goodsRestrictedSale.setRestrictedAssignFlag(DefaultFlag.NO);
			goodsRestrictedSale.setRestrictedPrePersonFlag(DefaultFlag.NO);
			goodsRestrictedSale.setPersonRestrictedType(null);
			goodsRestrictedSale.setPersonRestrictedCycle(null);
			goodsRestrictedSale.setAssignPersonRestrictedType(null);
		}
		return goodsRestrictedSale;
	}


	/**
	 * 根据goodsInfoIds查询限售信息
	 * @param goodsInfoIds
	 * @return
	 */
	public List<GoodsRestrictedVO> findByGoodsInfoIds(List<String> goodsInfoIds, Long storeId){
		List<GoodsRestrictedSale> goodsRestrictedSales = this.list(GoodsRestrictedSaleQueryRequest.builder()
				.delFlag(DeleteFlag.NO)
				.goodsInfoIds(goodsInfoIds)
				.storeId(storeId)
				.build());
		List<GoodsRestrictedVO> goodsRestrictedVOS = new ArrayList<>();
		goodsRestrictedSales.stream().forEach((i)->{
			GoodsRestrictedVO goodsRestrictedVO = KsBeanUtil.convert(i,GoodsRestrictedVO.class);
			if( RestrictedType.RESTRICTED_ORDER == i.getRestrictedType()){
				goodsRestrictedVO.setRestrictedCycleType(RestrictedCycleType.RESTRICTED_BY_ORDER);
			}

			if(PersonRestrictedType.RESTRICTED_ALL_LIFE == i.getPersonRestrictedType()){
				goodsRestrictedVO.setRestrictedCycleType(RestrictedCycleType.RESTRICTED_BY_ALL_LIFE);
			}

            if (PersonRestrictedType.RESTRICTED_BY_CYCLE == i.getPersonRestrictedType()) {
                switch (i.getPersonRestrictedCycle()) {
                    case RESTRICTED_EVERY_DAY:
                        goodsRestrictedVO.setRestrictedCycleType(RestrictedCycleType.RESTRICTED_BY_DAY);
                        break;
                    case RESTRICTED_EVERY_WEEK:
                        goodsRestrictedVO.setRestrictedCycleType(RestrictedCycleType.RESTRICTED_BY_WEEK);
                        break;
                    case RESTRICTED_EVERY_MONTH:
                        goodsRestrictedVO.setRestrictedCycleType(RestrictedCycleType.RESTRICTED_BY_MONTH);
                        break;
                    case RESTRICTED_EVERY_YEAR:
                        goodsRestrictedVO.setRestrictedCycleType(RestrictedCycleType.RESTRICTED_BY_YEAR);
                        break;
                    default:
                        break;
                }
            }
            goodsRestrictedVO.setRestrictedWay(i.getRestrictedWay());
            goodsRestrictedVO.setRestrictedStartNum(i.getRestrictedStartNum());
            goodsRestrictedVOS.add(goodsRestrictedVO);
        });
        return goodsRestrictedVOS;
    }

	/**
	 * 立即购买校验会员的限售
	 * @param goodsInfoId
	 * @param customerVO
	 * @param buyNum
	 *
	 * @Result <goodsInfoId, errorMessage>
	 */
	public void validateGoodsRestricted(String goodsInfoId, CustomerVO customerVO, Long buyNum, Long storeId){
		List<GoodsRestrictedVO> goodsRestrictedVOS = this.findByGoodsInfoIds(Collections.singletonList(goodsInfoId), storeId);
		RestrictedRecord restrictedRecord = restrictedRecordService
				.findByCustomerIdAndGoodsInfoId(goodsInfoId,customerVO.getCustomerId(), storeId);
		if(CollectionUtils.isNotEmpty(goodsRestrictedVOS)){
			GoodsRestrictedVO goodsRestrictedVO = goodsRestrictedVOS.get(0);
			List<GoodsRestrictedCustomerRelaVO> relas = goodsRestrictedVO.getGoodsRestrictedCustomerRelas();
			//1. 判断是否有权限购买
			if(goodsRestrictedVO.getAssignPersonRestrictedType() != null ){
				if(AssignPersonRestrictedType.RESTRICTED_CUSTOMER_LEVEL == goodsRestrictedVO.getAssignPersonRestrictedType()){
					if(relas.stream().noneMatch(r->r.getCustomerLevelId().equals(customerVO.getCustomerLevelId()))){
						//没有商品的购买权限
						throw new SbcRuntimeException(GoodsErrorCodeEnum.K030170, new Object[] {GoodsRestrictedErrorTips.WITHOUT_GOODS_AUTHORITY});
					}
				}

				if(AssignPersonRestrictedType.RESTRICTED_ASSIGN_CUSTOMER == goodsRestrictedVO.getAssignPersonRestrictedType()){
					if(relas.stream().noneMatch(r->r.getCustomerId().equals(customerVO.getCustomerId()))){
						//没有商品的购买权限
						throw new SbcRuntimeException(GoodsErrorCodeEnum.K030170, new Object[] {GoodsRestrictedErrorTips.WITHOUT_GOODS_AUTHORITY} );
					}
				}
			}
			Long goodsRelaseNum;
			//2. 判断是否符合限售数量
			if(this.validateRestrictRecord(restrictedRecord)){
				goodsRelaseNum = goodsRestrictedVO.getRestrictedNum() - restrictedRecord.getPurchaseNum();
			}else{
				goodsRelaseNum = goodsRestrictedVO.getRestrictedNum();
			}
			if(buyNum.compareTo(goodsRestrictedVO.getStartSaleNum()) < 0 ) {
				//最少起订量为x件
				throw new SbcRuntimeException(GoodsErrorCodeEnum.K030171 ,new Object[] {String.format(GoodsRestrictedErrorTips.GOODS_PURCHASE_MIN_NUMBER,goodsRestrictedVO.getStartSaleNum())});
			}
			if(buyNum.compareTo(goodsRelaseNum) > 0){
				//最多可购买x件
				throw new SbcRuntimeException(GoodsErrorCodeEnum.K030169 ,new Object[] {String.format(GoodsRestrictedErrorTips.GOODS_PURCHASE_MOST_NUMBER,goodsRelaseNum)});
			}
		}
	}

	/**
	 * 验证限售记录的有效性
	 * @param restrictedRecord
	 * @return
	 */
	private boolean validateRestrictRecord(RestrictedRecord restrictedRecord) {
		//没有记录，没有购买数量
		if(ObjectUtils.isEmpty(restrictedRecord)
				|| ObjectUtils.isEmpty(restrictedRecord.getPurchaseNum())){
			return false;
		}

		if(RestrictedCycleType.RESTRICTED_BY_ALL_LIFE == restrictedRecord.getRestrictedCycleType()
				|| RestrictedCycleType.RESTRICTED_BY_ORDER == restrictedRecord.getRestrictedCycleType()){
			return true;
		}

		if( LocalDate.now().isAfter(restrictedRecord.getEndDate())){
			return false;
		}
		return true;
	}

    /***
     * 提交订单检验商品限售 （ 抢购和拼团不校验 ）
	 *
     * @param goodsRestrictedValidateVOS	商品校验VO
     * @param customerVO					用户VO
     * @param storeId						门店ID
     */
    public void validateBatchGoodsRestricted(List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS,
											 CustomerSimplifyVO customerVO, Long storeId) {
		if(CollectionUtils.isNotEmpty(goodsRestrictedValidateVOS)){
			List<String> goodsInfoIds = goodsRestrictedValidateVOS.stream().map(GoodsRestrictedValidateVO::getSkuId).collect(Collectors.toList());
			List<GoodsRestrictedVO> goodsRestrictedVOS = this.findByGoodsInfoIds(goodsInfoIds, storeId);
			List<RestrictedRecord> restrictedRecords = restrictedRecordService
					.findEffectiveByCustomerIdAndGoodsInfoIds(goodsInfoIds,customerVO.getCustomerId(), storeId);
			if(CollectionUtils.isNotEmpty(goodsRestrictedVOS)) {
				goodsRestrictedVOS.stream().forEach(g -> {
					List<GoodsRestrictedCustomerRelaVO> relas = g.getGoodsRestrictedCustomerRelas();
					//1. 判断是否有权限购买
					if (g.getAssignPersonRestrictedType() != null) {
						if (AssignPersonRestrictedType.RESTRICTED_CUSTOMER_LEVEL == g.getAssignPersonRestrictedType()) {
							if (relas.stream().noneMatch(r -> r.getCustomerLevelId().equals(customerVO.getCustomerLevelId())
									|| customerVO.getStoreCustomerRelaListByAll().stream()
									.anyMatch(scr -> scr.getStoreLevelId().equals(r.getCustomerLevelId())
											&& scr.getStoreId().equals(g.getGoodsInfo().getStoreId()))

							)) {
								//没有商品的购买权限
								throw new SbcRuntimeException(GoodsErrorCodeEnum.K030170,
									new Object[] {String.format(GoodsRestrictedErrorTips.WITHOUT_GOODS_INFO_AUTHORITY,
											g.getGoodsInfo().getGoodsInfoName())});
							}
						}

                        if (AssignPersonRestrictedType.RESTRICTED_ASSIGN_CUSTOMER == g.getAssignPersonRestrictedType()) {
                            if (relas.stream().noneMatch(r -> r.getCustomerId().equals(customerVO.getCustomerId()))) {
                                //没有商品的购买权限
                                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030170,
                                       new Object[] {String.format(GoodsRestrictedErrorTips.WITHOUT_GOODS_INFO_AUTHORITY,
											   g.getGoodsInfo().getGoodsInfoName())} );
                            }
                        }
                    }
                    Long goodsRelaseNum;
                    //2. 判断是否符合限售数量
                    GoodsRestrictedValidateVO validateVO =
                            goodsRestrictedValidateVOS.stream().filter(gr -> gr.getSkuId().equals(g.getGoodsInfoId()))
                            .findFirst().orElse(null);

                    if (DefaultFlag.YES.equals(g.getRestrictedStartNum()) && validateVO.getNum().compareTo(g.getStartSaleNum()) < 0) {
                        //最少起订量为x件
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030171,
                              new Object[] {String.format(GoodsRestrictedErrorTips.GOODS_PURCHASE_MIN_NUMBER_PURCHASE,
									  g.getGoodsInfo().getGoodsInfoName(), g.getStartSaleNum())});
                    }

                    if(DefaultFlag.YES.equals(g.getRestrictedWay())){
                        RestrictedRecord restrictedRecord =
                                restrictedRecords.stream().filter(r -> r.getCustomerId().equals(customerVO.getCustomerId())
                                        && r.getGoodsInfoId().equals(g.getGoodsInfoId())).findFirst().orElse(null);
                        if (this.validateRestrictRecord(restrictedRecord)) {
                            goodsRelaseNum = g.getRestrictedNum() - restrictedRecord.getPurchaseNum();
                        } else {
                            goodsRelaseNum = g.getRestrictedNum();
                        }
                        if (Objects.nonNull(g.getRestrictedCycleType()) && validateVO.getNum().compareTo(goodsRelaseNum) > 0) {
                            //最多可购买x件
                            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030169,
                                  new Object[] {String.format(GoodsRestrictedErrorTips.GOODS_PURCHASE_MOST_NUMBER_PURCHASE, g.getGoodsInfo().getGoodsInfoName(), goodsRelaseNum)}  );
                        }
                    }
                });
            }
        }
    }


	/**
	 * 购物车限售数据的获取
	 * @param validateRequest
	 *
	 * @Result <goodsInfoId, errorMessage>
	 */
	public List<GoodsRestrictedPurchaseVO> getGoodsRestrictedInfo(GoodsRestrictedBatchValidateRequest validateRequest){
		List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseVOS = new ArrayList<>();
		List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS = validateRequest.getGoodsRestrictedValidateVOS();
		CustomerVO customerVO = validateRequest.getCustomerVO();
		if(CollectionUtils.isNotEmpty(goodsRestrictedValidateVOS)){
			List<String> goodsInfoIds = goodsRestrictedValidateVOS.stream().map(GoodsRestrictedValidateVO::getSkuId).collect(Collectors.toList());
			List<RestrictedRecord> restrictedRecords = restrictedRecordService.findEffectiveByCustomerIdAndGoodsInfoIds(goodsInfoIds,customerVO.getCustomerId(),
					validateRequest.getStoreId());
			//过滤周期的记录
			List<GoodsRestrictedVO> goodsRestrictedVOS = this.findByGoodsInfoIds(goodsInfoIds, validateRequest.getStoreId());
			if(CollectionUtils.isNotEmpty(goodsRestrictedVOS)) {
				goodsRestrictedVOS.stream().forEach(g -> {
					GoodsRestrictedPurchaseVO goodsRestrictedPurchaseVO = KsBeanUtil.convert(g,GoodsRestrictedPurchaseVO.class);
					List<GoodsRestrictedCustomerRelaVO> relas = g.getGoodsRestrictedCustomerRelas();
					//1. 判断是否有权限购买
					if(g.getAssignPersonRestrictedType() != null ){
						if(AssignPersonRestrictedType.RESTRICTED_CUSTOMER_LEVEL == g.getAssignPersonRestrictedType()){
							if (relas.stream().noneMatch(r -> r.getCustomerLevelId().equals(customerVO.getCustomerLevelId())
									|| (CollectionUtils.isNotEmpty(customerVO.getStoreCustomerRelaListByAll()) && customerVO.getStoreCustomerRelaListByAll().stream()
									.anyMatch(scr -> r.getCustomerLevelId().equals(scr.getStoreLevelId())
											&& scr.getStoreId().equals(g.getGoodsInfo().getStoreId())))

							)){
								//没有商品的购买权限
								goodsRestrictedPurchaseVO.setDefaultFlag(DefaultFlag.NO);
								goodsRestrictedPurchaseVOS.add(goodsRestrictedPurchaseVO);
								return;
							}
						}

						if(AssignPersonRestrictedType.RESTRICTED_ASSIGN_CUSTOMER == g.getAssignPersonRestrictedType()){
							if(relas.stream().noneMatch(r->r.getCustomerId().equals(customerVO.getCustomerId()))){
								//没有商品的购买权限
								goodsRestrictedPurchaseVO.setDefaultFlag(DefaultFlag.NO);
								goodsRestrictedPurchaseVOS.add(goodsRestrictedPurchaseVO);
								return;
							}
						}
					}
					Long goodsRelaseNum;
					//2. 判断是否符合限售数量
					RestrictedRecord restrictedRecord = restrictedRecords.stream().filter(r->r.getCustomerId().equals(customerVO.getCustomerId())
							&& r.getGoodsInfoId().equals(g.getGoodsInfoId())).findFirst().orElse(null);
					if(this.validateRestrictRecord(restrictedRecord)){
						goodsRelaseNum = g.getRestrictedNum() - restrictedRecord.getPurchaseNum();
					}else{
						goodsRelaseNum = g.getRestrictedNum();
					}
					GoodsRestrictedValidateVO validateVO = goodsRestrictedValidateVOS.stream().filter(gr->gr.getSkuId().equals(g.getGoodsInfoId()))
							.findFirst().orElse(null);
					if(Objects.nonNull(validateVO) ) {
						//最少起订量为x件
						goodsRestrictedPurchaseVO.setDefaultFlag(DefaultFlag.YES);
						//最多可购买x件
						goodsRestrictedPurchaseVO.setRestrictedNum(goodsRelaseNum);
						goodsRestrictedPurchaseVOS.add(goodsRestrictedPurchaseVO);
					}
				});
			}
		}
		return goodsRestrictedPurchaseVOS;
	}


	/**
	 * 提交订单检验商品限售 （ 抢购和拼团不校验 ）
	 * @param validateRequest
	 *
	 * @Result <goodsInfoId, errorMessage>
	 */
	public void validateBatchGoodsRestricted(GoodsRestrictedBatchValidateRequest validateRequest){
		// 拼团或抢购不校验、开店礼包不校验
		if((validateRequest.getSnapshotType() != null && validateRequest.getSnapshotType().equals(FLASH_SALE_GOODS_ORDER_TYPE))
				|| (validateRequest.getOpenGroupon() != null && validateRequest.getOpenGroupon())
				|| (validateRequest.getStoreBagsFlag() != null && validateRequest.getStoreBagsFlag())
				|| (validateRequest.getBuyCycleFlag() != null && validateRequest.getBuyCycleFlag())){
			return;
		}
		// 商品类型
		Integer goodsType = validateRequest.getGoodsType();
		// 是否是虚拟商品或电子卡券，这2种商品立即购买时，可以不需要收货地址
		boolean virtualFlag = Objects.equals(goodsType, GoodsType.VIRTUAL_GOODS.toValue()) || Objects.equals(goodsType, GoodsType.ELECTRONIC_COUPON_GOODS.toValue());
		List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS = validateRequest.getGoodsRestrictedValidateVOS();
		CustomerVO customerVO = validateRequest.getCustomerVO();
		if(CollectionUtils.isNotEmpty(goodsRestrictedValidateVOS)){
			List<String> goodsInfoIds = goodsRestrictedValidateVOS.stream().map(GoodsRestrictedValidateVO::getSkuId).collect(Collectors.toList());
			List<GoodsRestrictedVO> goodsRestrictedVOS = this.findByGoodsInfoIds(goodsInfoIds, validateRequest.getStoreId());
			List<RestrictedRecord> restrictedRecords = restrictedRecordService
					.findEffectiveByCustomerIdAndGoodsInfoIds(goodsInfoIds,customerVO.getCustomerId(), validateRequest.getStoreId());
			if(CollectionUtils.isNotEmpty(goodsRestrictedVOS)) {
				goodsRestrictedVOS.stream().forEach(g -> {
					List<GoodsRestrictedCustomerRelaVO> relas = g.getGoodsRestrictedCustomerRelas();
					//1. 判断是否有权限购买
					if (g.getAssignPersonRestrictedType() != null) {
						if (AssignPersonRestrictedType.RESTRICTED_CUSTOMER_LEVEL == g.getAssignPersonRestrictedType()) {
							if (relas.stream().noneMatch(r -> r.getCustomerLevelId().equals(customerVO.getCustomerLevelId())
									|| (CollectionUtils.isNotEmpty(customerVO.getStoreCustomerRelaListByAll()) && customerVO.getStoreCustomerRelaListByAll().stream()
									.anyMatch(scr -> scr.getStoreLevelId().equals(r.getCustomerLevelId())
											&& scr.getStoreId().equals(g.getGoodsInfo().getStoreId())))

							)) {
								//没有商品的购买权限
								throw new SbcRuntimeException(GoodsErrorCodeEnum.K030170,
									new Object[] {String.format(GoodsRestrictedErrorTips.WITHOUT_GOODS_INFO_AUTHORITY,
											g.getGoodsInfo().getGoodsInfoName())});
							}
						}

						if (AssignPersonRestrictedType.RESTRICTED_ASSIGN_CUSTOMER == g.getAssignPersonRestrictedType()) {
							if (relas.stream().noneMatch(r -> r.getCustomerId().equals(customerVO.getCustomerId()))) {
								//没有商品的购买权限
								throw new SbcRuntimeException(GoodsErrorCodeEnum.K030170,
									new Object[] {String.format(GoodsRestrictedErrorTips.WITHOUT_GOODS_INFO_AUTHORITY,
											g.getGoodsInfo().getGoodsInfoName())});
							}
						}
						// 虚拟商品、自提订单不校验限售地区
						if (!virtualFlag && (CollectionUtils.isEmpty(validateRequest.getPickUpSkuIds())
								|| !validateRequest.getPickUpSkuIds().contains(g.getGoodsInfoId()))) {
							if (AssignPersonRestrictedType.RESTRICTED_ADDRESS == g.getAssignPersonRestrictedType()) {
								if (StringUtils.isBlank(validateRequest.getAddressId())){
									//没有商品的购买权限
									throw new SbcRuntimeException(GoodsErrorCodeEnum.K030170,
										new Object[] {GoodsRestrictedErrorTips.GOODS_RESTRICTED_ADDRESS_NULL}	);
								}
								if (relas.stream().noneMatch(r -> validateRequest.getAddressId().startsWith(r.getAddressId()))) {
									//没有商品的购买权限
									throw new SbcRuntimeException(GoodsErrorCodeEnum.K030170,
										new Object[] {GoodsRestrictedErrorTips.GOODS_RESTRICTED_ADDRESS});
								}
							}
						}
					}
					Long goodsRelaseNum;
					//2. 判断是否符合限售数量
					GoodsRestrictedValidateVO validateVO =
							goodsRestrictedValidateVOS.stream().filter(gr -> gr.getSkuId().equals(g.getGoodsInfoId()))
									.findFirst().orElse(null);

					if (DefaultFlag.YES.equals(g.getRestrictedStartNum()) && validateVO.getNum().compareTo(g.getStartSaleNum()) < 0) {
						//最少起订量为x件
						throw new SbcRuntimeException(GoodsErrorCodeEnum.K030171,
							new Object[] {String.format(GoodsRestrictedErrorTips.GOODS_PURCHASE_MIN_NUMBER_PURCHASE,
									g.getGoodsInfo().getGoodsInfoName(), g.getStartSaleNum())}	);
					}

					if(DefaultFlag.YES.equals(g.getRestrictedWay())){
						RestrictedRecord restrictedRecord =
								restrictedRecords.stream().filter(r -> r.getCustomerId().equals(customerVO.getCustomerId())
										&& r.getGoodsInfoId().equals(g.getGoodsInfoId())).findFirst().orElse(null);
						if (this.validateRestrictRecord(restrictedRecord)) {
							goodsRelaseNum = g.getRestrictedNum() - restrictedRecord.getPurchaseNum();
							//如果是商家端或买家修改订单操作需要将订单中的数据补回来
							if(Objects.equals(Boolean.TRUE , validateRequest.getSupplierUpdate())
									|| BooleanUtils.isTrue(validateRequest.getBuyerModifyConsignee())) {
								goodsRelaseNum = goodsRelaseNum + validateVO.getNum();
							}
						} else {
							goodsRelaseNum = g.getRestrictedNum();
						}
						if (Objects.nonNull(g.getRestrictedCycleType()) && validateVO.getNum().compareTo(goodsRelaseNum) > 0) {
							//最多可购买x件
							throw new SbcRuntimeException(GoodsErrorCodeEnum.K030169,
								new Object[] {String.format(GoodsRestrictedErrorTips.GOODS_PURCHASE_MOST_NUMBER_PURCHASE, g.getGoodsInfo().getGoodsInfoName(), goodsRelaseNum)}	);
						}
					}
				});
			}
		}
	}

}
