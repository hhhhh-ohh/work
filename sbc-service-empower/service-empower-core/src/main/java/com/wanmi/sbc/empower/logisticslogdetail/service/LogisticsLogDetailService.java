package com.wanmi.sbc.empower.logisticslogdetail.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.empower.logisticslogdetail.repository.LogisticsLogDetailRepository;
import com.wanmi.sbc.empower.logisticslogdetail.model.root.LogisticsLogDetail;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailQueryRequest;
import com.wanmi.sbc.empower.bean.vo.LogisticsLogDetailVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>物流记录明细业务逻辑</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@Service("LogisticsLogDetailService")
public class LogisticsLogDetailService {
	@Autowired
	private LogisticsLogDetailRepository logisticsLogDetailRepository;

	/**
	 * 新增物流记录明细
	 * @author 宋汉林
	 */
	@Transactional
	public LogisticsLogDetail add(LogisticsLogDetail entity) {
		logisticsLogDetailRepository.save(entity);
		return entity;
	}

	/**
	 * 新增物流记录明细
	 * @author 宋汉林
	 */
	@Transactional
	public void addAll(List<LogisticsLogDetail> list) {
		logisticsLogDetailRepository.saveAll(list);
	}

	/**
	 * 修改物流记录明细
	 * @author 宋汉林
	 */
	@Transactional
	public LogisticsLogDetail modify(LogisticsLogDetail entity) {
		logisticsLogDetailRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除物流记录明细
	 * @author 宋汉林
	 */
	@Transactional
	public void deleteById(LogisticsLogDetail entity) {
		logisticsLogDetailRepository.save(entity);
	}

	/**
	 * 批量删除物流记录明细
	 * @author 宋汉林
	 */
	@Transactional
	public void deleteByIdList(List<LogisticsLogDetail> infos) {
		logisticsLogDetailRepository.saveAll(infos);
	}

	/**
	 * 单个查询物流记录明细
	 * @author 宋汉林
	 */
	public LogisticsLogDetail getOne(Long id){
		return logisticsLogDetailRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "物流记录明细不存在"));
	}

	/**
	 * 分页查询物流记录明细
	 * @author 宋汉林
	 */
	public Page<LogisticsLogDetail> page(LogisticsLogDetailQueryRequest queryReq){
		return logisticsLogDetailRepository.findAll(
				LogisticsLogDetailWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询物流记录明细
	 * @author 宋汉林
	 */
	public List<LogisticsLogDetail> list(LogisticsLogDetailQueryRequest queryReq){
		return logisticsLogDetailRepository.findAll(LogisticsLogDetailWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 宋汉林
	 */
	public LogisticsLogDetailVO wrapperVo(LogisticsLogDetail logisticsLogDetail) {
		if (logisticsLogDetail != null){
			LogisticsLogDetailVO logisticsLogDetailVO = KsBeanUtil.convert(logisticsLogDetail, LogisticsLogDetailVO.class);
			return logisticsLogDetailVO;
		}
		return null;
	}
}

