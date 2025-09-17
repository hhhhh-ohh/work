package com.wanmi.sbc.marketing.electroniccoupon.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicImportRecordQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.ElectronicImportRecordVO;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicImportRecord;
import com.wanmi.sbc.marketing.electroniccoupon.repository.ElectronicImportRecordRepository;
import com.wanmi.sbc.marketing.electroniccoupon.service.criteria.ElectronicImportRecordWhereCriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * <p>卡密导入记录表业务逻辑</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
@Slf4j
@Service("ElectronicImportRecordService")
public class ElectronicImportRecordService {
	@Autowired
	private ElectronicImportRecordRepository electronicImportRecordRepository;

	@Value("classpath:/download/electronic_cards_template.xlsx")
	private Resource templateFile;

	/**
	 * 新增卡密导入记录表
	 * @author 许云鹏
	 */
	@Transactional
	public ElectronicImportRecord add(ElectronicImportRecord entity) {
		electronicImportRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 分页查询卡密导入记录表
	 * @author 许云鹏
	 */
	public Page<ElectronicImportRecord> page(ElectronicImportRecordQueryRequest queryReq){
		return electronicImportRecordRepository.findAll(
				ElectronicImportRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public ElectronicImportRecordVO wrapperVo(ElectronicImportRecord electronicImportRecord) {
		if (electronicImportRecord != null){
			ElectronicImportRecordVO electronicImportRecordVO = KsBeanUtil.convert(electronicImportRecord, ElectronicImportRecordVO.class);
			return electronicImportRecordVO;
		}
		return null;
	}

	/**
	 * 下载卡券模版
	 * @return
	 */
	public String exportTemplate() {
		if (templateFile == null || !templateFile.exists()) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010097);
		}

		try (ByteArrayOutputStream bas = new ByteArrayOutputStream();
			 InputStream is = templateFile.getInputStream();
			 Workbook wk = WorkbookFactory.create(is)) {
				wk.write(bas);
				return Base64.getEncoder().encodeToString(bas.toByteArray());
		} catch (Exception e) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
		}
	}


}

