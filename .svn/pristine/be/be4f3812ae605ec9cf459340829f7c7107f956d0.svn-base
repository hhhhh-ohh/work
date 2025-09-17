package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.api.request.wechatvideo.VideoUserAddRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>视频号新增参数</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserBatchSaveRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	@Schema(description = "批量新增")
	private List<VideoUserAddRequest> addRequestList;
}