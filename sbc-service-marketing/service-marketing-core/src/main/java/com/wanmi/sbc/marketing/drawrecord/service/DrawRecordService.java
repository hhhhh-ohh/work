package com.wanmi.sbc.marketing.drawrecord.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordModifyListRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;
import com.wanmi.sbc.marketing.drawrecord.model.root.DrawRecord;
import com.wanmi.sbc.marketing.drawrecord.repository.DrawRecordRepository;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>抽奖记录表业务逻辑</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Slf4j
@Service("DrawRecordService")
public class DrawRecordService {
	@Autowired
	private DrawRecordRepository drawRecordRepository;

	/**
	 * 分页查询抽奖记录表
	 * @author wwc
	 */
	public Page<DrawRecord> page(DrawRecordQueryRequest queryReq){
		return drawRecordRepository.findAll(
				DrawRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/** 
	 * 新增抽奖记录表
	 * @author wwc
	 */
	@Transactional
	public DrawRecord add(DrawRecord entity) {
		drawRecordRepository.save(entity);
		return entity;
	}
	
	/** 
	 * 修改抽奖记录表
	 * @author wwc
	 */
	@Transactional
	public DrawRecord modify(DrawRecord entity) {
		drawRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除抽奖记录表
	 * @author wwc
	 */
	@Transactional
	public void deleteById(Long id) {
//		drawRecordRepository.delete(id);
	}
	
	/** 
	 * 批量删除抽奖记录表
	 * @author wwc
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
//		ids.forEach(id -> drawRecordRepository.delete(id));
	}
	
	/** 
	 * 单个查询抽奖记录表
	 * @author wwc
	 */
	public DrawRecord getById(Long id){
		return drawRecordRepository.findById(id).orElse(null);
	}
	
	/** 
	 * 列表查询抽奖记录表
	 * @author wwc
	 */
	public List<DrawRecord> list(DrawRecordQueryRequest queryReq){
		return drawRecordRepository.findAll(
				DrawRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 * @author wwc
	 */
	public DrawRecordVO wrapperVo(DrawRecord drawRecord) {
		if (drawRecord != null){
			DrawRecordVO drawRecordVO=new DrawRecordVO();
			KsBeanUtil.copyPropertiesThird(drawRecord,drawRecordVO);
			if (Objects.nonNull(drawRecord.getDeliveryTime())) {
				String deliveryTime = drawRecord.getDeliveryTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				drawRecordVO.setDeliveryTimeOfString(deliveryTime);
			}
			return drawRecordVO;
		}
		return null;
	}

	/**
	 * 导入中奖发货信息
	 * @param modifyRequests
	 */
	@Transactional
	public void modifyImportPeizeDelivery(DrawRecordModifyListRequest modifyRequests) {
		// 发送到mq异步修改 防止数据量大 超时回滚 保存失败
		//drawRecordSink.subOutput().send(new GenericMessage<>(JSONObject.toJSONString(modifyRequests)));
	}

	public Long total(DrawRecordQueryRequest queryReq) {
		return drawRecordRepository.count(DrawRecordWhereCriteriaBuilder.build(queryReq));
	}
}
