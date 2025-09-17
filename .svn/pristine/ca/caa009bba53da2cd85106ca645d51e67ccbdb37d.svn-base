package com.wanmi.sbc.customer.ledgerfile.service;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.LedgerFileVO;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import com.wanmi.sbc.customer.ledgerfile.repository.LedgerFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>分账文件业务逻辑</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Service("LedgerFileService")
public class LedgerFileService {
	@Autowired
	private LedgerFileRepository ledgerFileRepository;

	/**
	 * 新增分账文件
	 * @author 许云鹏
	 */
	@Transactional
	public String add(LedgerFile entity) {
		ledgerFileRepository.save(entity);
		return entity.getId();
	}

	/**
	 * 修改分账文件
	 * @author 许云鹏
	 */
	@Transactional
	public LedgerFile modify(LedgerFile entity) {
		LedgerFile old = ledgerFileRepository.getById(entity.getId());
		if(Objects.isNull(old)){
			entity.setFileExt(Constants.PDF);
			ledgerFileRepository.save(entity);
		} else {
			old.setContent(entity.getContent());
		}
		return entity;
	}

	/**
	 * 单个删除分账文件
	 * @author 许云鹏
	 */
	@Transactional
	public void deleteById(String id) {
		ledgerFileRepository.deleteById(id);
	}

	/**
	 * 批量删除分账文件
	 * @author 许云鹏
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		ledgerFileRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询分账文件
	 * @author 许云鹏
	 */
	public LedgerFile getOne(String id){
		return ledgerFileRepository.findById(id).orElse(null);
	}

	/**
	 * 将实体包装成VO
	 * @author 许云鹏
	 */
	public LedgerFileVO wrapperVo(LedgerFile ledgerFile) {
		if (ledgerFile != null){
			LedgerFileVO ledgerFileVO = KsBeanUtil.convert(ledgerFile, LedgerFileVO.class);
			return ledgerFileVO;
		}
		return null;
	}

	/**
	 * 根据ids查询
	 * @param ids
	 * @return
	 */
	public List<LedgerFile> findByIds(List<String> ids) {
		return ledgerFileRepository.findByIdIn(ids);
	}
}

