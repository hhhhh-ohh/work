package com.wanmi.sbc.message.api.request.messagesendnode;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>站内信通知节点表修改参数</p>
 * @author xuyunpeng
 * @date 2020-01-09 11:45:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendNodeModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 节点名称
	 */
	@Schema(description = "节点名称")
	private String nodeName;

	/**
	 * 节点标题
	 */
	@Schema(description = "节点标题")
	private String nodeTitle;

	/**
	 * 内容
	 */
	@Schema(description = "内容")
	private String nodeContent;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;


}