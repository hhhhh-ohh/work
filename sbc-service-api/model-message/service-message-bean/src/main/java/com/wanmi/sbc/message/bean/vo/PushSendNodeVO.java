package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>会员推送通知节点VO</p>
 * @author Bob
 * @date 2020-01-13 10:47:41
 */
@Schema
@Data
public class PushSendNodeVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 节点名称
	 */
	@Schema(description = "节点名称")
	private String nodeName;

	/**
	 * 节点类型
	 */
	@Schema(description = "节点类型")
	private Integer nodeType;

	/**
	 * 节点code
	 */
	@Schema(description = "节点code")
	private String nodeCode;

	/**
	 * 节点标题
	 */
	@Schema(description = "节点标题")
	private String nodeTitle;

	/**
	 * 通知内容
	 */
	@Schema(description = "通知内容")
	private String nodeContext;

	@Schema(description = "预计发送总数")
	private Long expectedSendCount;

	/**
	 * 实际发送总数
	 */
	@Schema(description = "实际发送总数")
	private Long actuallySendCount;

	/**
	 * 打开总数
	 */
	@Schema(description = "打开总数")
	private Long openCount;

	/**
	 * 状态 0:未启用 1:启用
	 */
	@Schema(description = "状态 0:未启用 1:启用")
	private Integer status;

	@Schema(description = "发送详情")
	private PushDetailVO pushDetailVO;

}