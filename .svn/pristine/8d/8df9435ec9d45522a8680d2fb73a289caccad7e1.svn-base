package com.wanmi.sbc.setting.storemessagenodesetting.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.storemessage.ProviderMessageNode;
import com.wanmi.sbc.common.enums.storemessage.SupplierMessageNode;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingModifyStatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.storemessagenodesetting.repository.StoreMessageNodeSettingRepository;
import com.wanmi.sbc.setting.storemessagenodesetting.model.root.StoreMessageNodeSetting;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingQueryRequest;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>商家消息节点设置业务逻辑</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Service("StoreMessageNodeSettingService")
public class StoreMessageNodeSettingService {
	@Autowired
	private StoreMessageNodeSettingRepository storeMessageNodeSettingRepository;

	/**
	 * 新增商家消息节点设置
	 * @author 马连峰
	 */
	@Transactional
	public StoreMessageNodeSetting add(StoreMessageNodeSetting entity) {
		storeMessageNodeSettingRepository.save(entity);
		return entity;
	}

	/**
	 * 修改商家消息节点开关
	 * @author 马连峰
	 */
	@Transactional(rollbackFor = Exception.class)
	public int modifyStatus(StoreMessageNodeSettingModifyStatusRequest modifyStatusRequest) {
		return storeMessageNodeSettingRepository.modifyStatus(modifyStatusRequest);
	}

	/**
	 * 单个删除商家消息节点设置
	 * @author 马连峰
	 */
	@Transactional
	public void deleteById(StoreMessageNodeSetting entity) {
		storeMessageNodeSettingRepository.save(entity);
	}

	/**
	 * 批量删除商家消息节点设置
	 * @author 马连峰
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		storeMessageNodeSettingRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询商家消息节点设置
	 * @author 马连峰
	 */
	public StoreMessageNodeSetting getOne(Long id, Long storeId){
		return storeMessageNodeSettingRepository.findByIdAndStoreIdAndDelFlag(id, storeId, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商家消息节点设置不存在"));
	}

	/**
	 * 列表查询商家消息节点设置
	 * @author 马连峰
	 */
	public List<StoreMessageNodeSetting> list(StoreMessageNodeSettingQueryRequest queryReq){
		return storeMessageNodeSettingRepository.findAll(StoreMessageNodeSettingWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 单个查询商家sku库存预警值
	 */
	public StoreMessageNodeSetting getWarningStock ( Long storeId) {
		String nodeCode = SupplierMessageNode.GOODS_SKU_WARN_STOCK.getCode();
		return storeMessageNodeSettingRepository.getWarningStock(storeId,nodeCode);
	}

	/**
	 * 单个查询当前商家的开关状态
	 */
	public StoreMessageNodeSetting getStatus (Long storeId) {
		String nodeCode = ProviderMessageNode.GOODS_SKU_WARN_STOCK.getCode();
		return storeMessageNodeSettingRepository.getStatus(storeId,nodeCode);
	}

	/**
	 * 将实体包装成VO
	 * @author 马连峰
	 */
	public StoreMessageNodeSettingVO wrapperVo(StoreMessageNodeSetting storeMessageNodeSetting) {
		if (storeMessageNodeSetting != null){
			StoreMessageNodeSettingVO storeMessageNodeSettingVO = KsBeanUtil.convert(storeMessageNodeSetting, StoreMessageNodeSettingVO.class);
			return storeMessageNodeSettingVO;
		}
		return null;
	}

}

