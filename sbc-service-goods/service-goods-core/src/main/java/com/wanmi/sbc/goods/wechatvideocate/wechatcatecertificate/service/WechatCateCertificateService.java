package com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.service;

import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcatecertificate.WechatCateCertificateQueryRequest;
import com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.model.root.WechatCateCertificate;
import com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.repository.WechatCateCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>微信类目资质业务逻辑</p>
 * @author 
 * @date 2022-04-14 10:13:05
 */
@Service("WechatCateCertificateService")
public class WechatCateCertificateService {
	@Autowired
	private WechatCateCertificateRepository wechatCateCertificateRepository;


    /**
	 * 列表查询微信类目资质
	 * @author 
	 */
	public List<WechatCateCertificate> list(WechatCateCertificateQueryRequest queryReq){
		return wechatCateCertificateRepository.findAll(
				WechatCateCertificateWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

}
