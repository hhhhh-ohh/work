package com.wanmi.sbc.elastic.sensitivewords.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author houshuai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = EsConstants.SENSITIVE_WORDS)
public class EsSensitiveWords implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 敏感词id 主键
	 */
	@Id
	private Long sensitiveId;

	/**
	 * 敏感词内容
	 */
	private String sensitiveWords;

	/**
	 * 是否删除
	 */
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 修改人
	 */
	private String updateUser;

	/**
	 * 修改时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除人
	 */
	private String deleteUser;

	/**
	 * 删除时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

}
