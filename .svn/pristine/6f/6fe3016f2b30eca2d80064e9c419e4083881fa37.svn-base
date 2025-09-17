package com.wanmi.sbc.setting.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>帮助中心文章信息VO</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Schema
@Data
public class HelpCenterArticleVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	private Long id;

	/**
	 * 文章标题
	 */
	@Schema(description = "文章标题")
	private String articleTitle;

	/**
	 * 文章分类id
	 */
	@Schema(description = "文章分类id")
	private Long articleCateId;

	/**
	 * 文章分类名称
	 */
	@Schema(description = "文章分类名称")
	private String articleCateName;

	/**
	 * 文章内容
	 */
	@Schema(description = "文章内容")
	private String articleContent;

	/**
	 * 文章状态，0:展示，1:隐藏
	 */
	@Schema(description = "文章状态，0:展示，1:隐藏")
	private DefaultFlag articleType;

	/**
	 * 查看次数
	 */
	@Schema(description = "查看次数")
	private Long viewNum;

	/**
	 * 解决次数
	 */
	@Schema(description = "解决次数")
	private Long solveNum;

	/**
	 * 未解决次数
	 */
	@Schema(description = "未解决次数")
	private Long unresolvedNum;

	/**
	 * 文章可点击状态
	 */
	@Schema(description = "文章可点击状态，0:不可点击，1:可点击")
	private DefaultFlag clickSolveType;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPersonName;

	/**
	 * 创建人账号
	 */
	@Schema(description = "创建人账号")
	@SensitiveWordsField(signType = SignWordType.NAME)
	private String createPersonAccount;

	/**
	 * 更新时间时间
	 */
	@Schema(description = "更细时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

}