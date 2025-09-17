package com.wanmi.sbc.goods.api.request.restrictedrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.vo.RestrictedRecordSimpVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>限售新增参数</p>
 * @author 限售记录
 * @date 2020-04-11 15:59:01
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestrictedRecordBatchAddRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 限售信息
	 */
	@NotNull
	private List<RestrictedRecordSimpVO> restrictedRecordSimpVOS;

	/**
	 * 会员Id
	 */
	@NotNull
	private String customerId;

	/***
	 * 门店ID
	 */
	private Long storeId;

	/**
	 * 下单时间
	 */
	@NotNull
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime orderTime;

}