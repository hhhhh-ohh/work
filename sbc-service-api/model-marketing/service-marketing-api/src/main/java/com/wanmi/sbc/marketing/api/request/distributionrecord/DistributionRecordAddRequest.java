package com.wanmi.sbc.marketing.api.request.distributionrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>DistributionRecord新增参数</p>
 * @author baijz
 * @date 2019-02-27 18:56:40
 */
@Data
@Schema
public class DistributionRecordAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 分销记录保存列表
	 */
	List<DistributionRecordAddInfo> distributionRecordAddInfos;

}