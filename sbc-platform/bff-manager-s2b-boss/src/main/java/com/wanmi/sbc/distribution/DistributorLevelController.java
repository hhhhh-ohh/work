package com.wanmi.sbc.distribution;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelDeleteRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelBaseResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * 分销员等级
 * @author: Geek Wang
 * @createDate: 2019/6/13 17:16
 * @version: 1.0
 */
@Tag(name =  "分销员等级服务" ,description = "DistributorLevelController")
@RestController
@Validated
@RequestMapping("/distributor-level")
public class DistributorLevelController {

	@Autowired
	private DistributorLevelQueryProvider distributorLevelQueryProvider;

	@Autowired
	private DistributorLevelProvider distributorLevelProvider;

	/**
	 * 查询分销员等级基础信息列表（仅包含字段：分销员等级ID、分销员等级名称）
	 * @return
	 */
	@Operation(summary = "查询分销员等级基础信息列表（仅包含字段：分销员等级ID、分销员等级名称）")
	@RequestMapping(value = "/list-base-info",method = RequestMethod.POST)
	public BaseResponse<DistributorLevelBaseResponse> listBaseInfo(){
		return distributorLevelQueryProvider.listBaseInfo();
	}

	/**
	 * 删除分销员等级
	 */
	@Operation(summary = "删除分销员等级")
	@RequestMapping(method = RequestMethod.DELETE)
	public BaseResponse delete(@RequestBody @Valid DistributorLevelDeleteRequest request) {
		return distributorLevelProvider.delete(request);
	}
}
