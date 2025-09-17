package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.enums.NoticeType;
import com.wanmi.sbc.message.bean.enums.SwitchFlag;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>站内信通知节点表VO</p>
 * @author xuyunpeng
 * @date 2020-01-09 11:45:53
 */
@Schema
@Data
public class MessageSendNodeVO extends BasicResponse {
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
	 * 启用状态：0未启用 1启用
	 */
	@Schema(description = "启用状态：0未启用 1启用")
	private SwitchFlag status;

	/**
	 * 发送数
	 */
	@Schema(description = "发送数")
	private Integer sendSum;

	/**
	 * 打开数
	 */
	@Schema(description = "打开数")
	private Integer openSum;

	/**
	 * 跳转路由
	 */
	@Schema(description = "跳转路由")
	private String routeName;

	/**
	 * 节点类型
	 */
	@Schema(description = "节点类型")
	private NoticeType nodeType;

	/**
	 * 节点code
	 */
	@Schema(description = "节点code")
	private String nodeCode;

}