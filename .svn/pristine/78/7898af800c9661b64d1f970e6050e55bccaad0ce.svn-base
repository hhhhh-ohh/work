package com.wanmi.sbc.goods.api.provider.wechatvideo.wechatcateaudit;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.GradeRequest;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.WechatAuditRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcateaudit.WechatCateAuditQueryRequest;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.GradeResponse;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatCateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>微信类目审核状态查询服务Provider</p>
 * @author 
 * @date 2022-04-09 17:02:02
 */
@FeignClient(value = "${application.goods.name}", contextId = "WechatCateAuditQueryProvider")
public interface WechatCateAuditQueryProvider {



	/**
	 * 根据审核状态，查询微信类目审核记录
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatcateaudit/tree")
	BaseResponse<List<WechatCateDTO>> tree(@RequestBody WechatCateAuditQueryRequest request);

	/**
	 * 查询可供审核的微信类目并关联平台类目
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/thirdgoodscate/list/wechat/forAudit")
	BaseResponse<List<WechatCateDTO>> listWechatForAudit();


	/**
	 * 根据第三级类目查询一二级类目
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatcateaudit/gradeBycateIds")
	BaseResponse<List<GradeResponse>> gradeBycateIds(@RequestBody GradeRequest request);
	/**
	 * 查询审核失败的微信类目
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatcateaudit/getUnchekedWechatCate")
	BaseResponse<WechatCateDTO> getUnchekedWechatCate(@RequestBody Long cateId);

	/**
	 * 微信类目提审
	 * @param request
	 * @return
	 */
	@PostMapping("/goods/${application.goods.version}/wechatcateaudit/wechatAudit")
	BaseResponse wechatAudit(@RequestBody @Valid WechatAuditRequest request);

}

