package com.wanmi.sbc.customer.bean.vo;

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账文件VO</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Schema
@Data
public class LedgerFileVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 文件内容
	 */
	@Schema(description = "文件内容")
	private byte[] content;

	/**
	 * 文件扩展名
	 */
	@Schema(description = "文件扩展名")
	private String fileExt;

}