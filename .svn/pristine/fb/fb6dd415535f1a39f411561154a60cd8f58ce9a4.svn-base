package com.wanmi.sbc.goods.grouponsharerecord.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.goods.grouponsharerecord.repository.GrouponShareRecordRepository;
import com.wanmi.sbc.goods.grouponsharerecord.model.root.GrouponShareRecord;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordQueryRequest;
import com.wanmi.sbc.goods.bean.vo.GrouponShareRecordVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import java.util.List;

/**
 * <p>拼团分享访问记录业务逻辑</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@Service("GrouponShareRecordService")
public class GrouponShareRecordService {
	@Autowired
	private GrouponShareRecordRepository grouponShareRecordRepository;

	/**
	 * 新增拼团分享访问记录
	 * @author zhangwenchang
	 */
	@Transactional
	public GrouponShareRecord add(GrouponShareRecord entity) {
		grouponShareRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 修改拼团分享访问记录
	 * @author zhangwenchang
	 */
	@Transactional
	public GrouponShareRecord modify(GrouponShareRecord entity) {
		grouponShareRecordRepository.save(entity);
		return entity;
	}


	/**
	 * 单个查询拼团分享访问记录
	 * @author zhangwenchang
	 */
	public GrouponShareRecord getOne(Long id, Long storeId){
		return grouponShareRecordRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "拼团分享访问记录不存在"));
	}

	/**
	 * 分页查询拼团分享访问记录
	 * @author zhangwenchang
	 */
	public Page<GrouponShareRecord> page(GrouponShareRecordQueryRequest queryReq){
		return grouponShareRecordRepository.findAll(
				GrouponShareRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询拼团分享访问记录
	 * @author zhangwenchang
	 */
	public List<GrouponShareRecord> list(GrouponShareRecordQueryRequest queryReq){
		return grouponShareRecordRepository.findAll(GrouponShareRecordWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author zhangwenchang
	 */
	public GrouponShareRecordVO wrapperVo(GrouponShareRecord grouponShareRecord) {
		if (grouponShareRecord != null){
			GrouponShareRecordVO grouponShareRecordVO = KsBeanUtil.convert(grouponShareRecord, GrouponShareRecordVO.class);
			return grouponShareRecordVO;
		}
		return null;
	}
}

