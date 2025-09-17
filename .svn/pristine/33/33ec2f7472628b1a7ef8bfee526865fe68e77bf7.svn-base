package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;


@Schema
@Data
public class SentinelConfigVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 0：直接拒绝（默认）2：匀速通过
	 */
	@Schema(description = "0：直接拒绝（默认）2：匀速通过")
	private Long controlBehavior = 0L;

	/**
	 * 限流阈值
	 */
	@Schema(description = "限流阈值")
	private Long count;

	/**
	 * 授权使用限制来源方，默认default
	 */
	@Schema(description = "授权使用限制来源方，默认default")
	private String limitApp = "default";

	/**
	 * 0：线程数（客户端并发控制）1：QPS（默认）
	 */
	@Schema(description = "0：线程数（客户端并发控制）1：QPS（默认）")
	private Long grade = 1L;

	/**
	 * 当controlBehavior=2时，排队等待时间
	 */
	@Schema(description = "当controlBehavior=2时，排队等待时间")
	private Long maxQueueingTimeMs = 500L;

	/**
	 * 资源名称
	 */
	@Schema(description = "资源名称")
	private String resource;

	private Long strategy = 0L;

	private Long warmUpPeriodSec = 10L;

	/**
	 * 是否为集群模式，默认false
	 */
	@Schema(description = "是否为集群模式，默认false")
	private Boolean clusterMode = Boolean.TRUE;

	/**
	 * 集群限流配置
	 */
	@Schema(description = "集群限流配置")
	private ClusterConfigVO clusterConfig;

}