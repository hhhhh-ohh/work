package com.wanmi.sbc.setting.api.request.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.enums.SettingType;
import lombok.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频直播带货应用设置列表查询请求参数</p>
 * @author zhaiqiankun
 * @date 2022-04-11 20:00:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Integer> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Integer id;

	/**
	 * 设置名称
	 */
	@Schema(description = "设置名称")
	private String name;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer settingSort;

	/**
	 * 设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景,9:营业执照
	 */
	@Schema(description = "设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景,9:营业执照")
	private SettingType settingType;

	/**
	 * 排除的设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景,9:营业执照
	 */
	@Schema(description = "排除的设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景,9:营业执照")
	private SettingType notSettingType;

	/**
	 * 状态,0:未启用1:已启用2:禁用
	 */
	@Schema(description = "状态,0:未启用1:已启用2:禁用")
	private Integer status;

	/**
	 * 失败原因
	 */
	@Schema(description = "失败原因")
	private String failReason;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

	/**
	 * 查询指定字段
	 */
	@Schema(description = "查询指定字段")
	private List<String> findFields;

	/**
	 * 多重排序
	 * 内容：key:字段,value:desc或asc
	 */
	@Schema(description = "多重排序，内容：key:字段,value:desc或asc")
	private Map<String, String> sortMap = new LinkedHashMap<>();

	/**
	 * 填序排序
	 *
	 * @param column
	 * @param sort
	 */
	public void putSort(String column, String sort) {
		sortMap.put(column, sort);
	}

}