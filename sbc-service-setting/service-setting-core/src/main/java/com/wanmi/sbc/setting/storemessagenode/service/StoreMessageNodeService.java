package com.wanmi.sbc.setting.storemessagenode.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.storemessagenode.repository.StoreMessageNodeRepository;
import com.wanmi.sbc.setting.storemessagenode.model.root.StoreMessageNode;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeQueryRequest;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>商家消息节点业务逻辑</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@Service("StoreMessageNodeService")
public class StoreMessageNodeService {
	@Autowired
	private StoreMessageNodeRepository storeMessageNodeRepository;

	/**
	 * 新增商家消息节点
	 * @author 马连峰
	 */
	@Transactional
	public StoreMessageNode add(StoreMessageNode entity) {
		storeMessageNodeRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除商家消息节点
	 * @author 马连峰
	 */
	@Transactional
	public void deleteById(StoreMessageNode entity) {
		storeMessageNodeRepository.save(entity);
	}

	/**
	 * 批量删除商家消息节点
	 * @author 马连峰
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		storeMessageNodeRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询商家消息节点
	 * @author 马连峰
	 */
	public StoreMessageNode getOne(Long id){
		return storeMessageNodeRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商家消息节点不存在"));
	}

	/**
     * 列表查询商家消息节点
     *
     * @author 马连峰
     */
    public List<StoreMessageNode> list(StoreMessageNodeQueryRequest queryReq) {
        return storeMessageNodeRepository.findAll(
                StoreMessageNodeWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 马连峰
	 */
	public StoreMessageNodeVO wrapperVo(StoreMessageNode storeMessageNode) {
		if (storeMessageNode != null){
			StoreMessageNodeVO storeMessageNodeVO = KsBeanUtil.convert(storeMessageNode, StoreMessageNodeVO.class);
			return storeMessageNodeVO;
		}
		return null;
	}

}

