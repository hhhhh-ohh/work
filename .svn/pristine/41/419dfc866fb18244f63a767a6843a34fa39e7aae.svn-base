package com.wanmi.sbc.marketing.common.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 营销通用查询入参
 *
 * @Author dyt
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingQueryRequest extends BaseQueryRequest {


	/**
	 * 搜索条件:开始时间开始
	 */
	@Schema(description = "搜索条件:开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeBegin;

	/**
	 * 搜索条件:开始时间截止
	 */
	@Schema(description = "搜索条件:开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeEnd;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeBegin;

	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeEnd;

	/**
	 * 删除状态
	 */
	private DeleteFlag deleteFlag;

	/**
	 * 批量查询-店铺
	 */
	private List<Long> storeIds;

	/**
	 * 批量查询-营销类型
	 */
	private List<MarketingType> marketingTypes;

	/**
	 * 批量查询-范围类型
	 */
	private List<MarketingScopeType> marketingScopeTypes;

	/**
	 * 批量查询-插件类型
	 */
	private List<PluginType> pluginTypes;

	/**
	 * 批量查询-审核状态
	 */
	private List<AuditStatus> auditStatusList;

	/**
	 * 非当前id
	 */
	private Long notId;

	/**
	 * 封装公共条件
	 *
	 * @return 营销数据
	 */
	public Specification<Marketing> getWhereCriteria() {
		return (root, cq, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			// 大于或等于 搜索条件:开始时间开始
			if (this.getStartTimeBegin() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("beginTime"),
								this.getStartTimeBegin()));
			}
			// 小于或等于 搜索条件:开始时间截止
			if (this.getStartTimeEnd() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("beginTime"),
								this.getStartTimeEnd()));
			}

			// 大于或等于 搜索条件:结束时间开始
			if (this.getEndTimeBegin() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("endTime"),
								this.getEndTimeBegin()));
			}
			// 小于或等于 搜索条件:结束时间截止
			if (this.getEndTimeEnd() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("endTime"),
								this.getEndTimeEnd()));
			}
			//批量查询-营销类型
			if (CollectionUtils.isNotEmpty(this.getMarketingTypes())) {
				predicates.add(root.get("marketingType").in(this.getMarketingTypes()));
			}
			//批量查询-范围类型
			if (CollectionUtils.isNotEmpty(this.getMarketingScopeTypes())) {
				predicates.add(root.get("scopeType").in(this.getMarketingScopeTypes()));
			}
			//批量查询-审核状态
			if (CollectionUtils.isNotEmpty(this.getAuditStatusList())) {
				predicates.add(root.get("auditStatus").in(this.getAuditStatusList()));
			}
			//批量查询-店铺id
			if (CollectionUtils.isNotEmpty(this.getStoreIds())) {
				predicates.add(root.get("storeId").in(this.getStoreIds()));
			}
			//批量查询-删除状态
			if (Objects.nonNull(this.getDeleteFlag())) {
				predicates.add(cb.equal(root.get("delFlag"), this.getDeleteFlag()));
			}
			//非当前id
			if (Objects.nonNull(this.getNotId())) {
				predicates.add(cb.notEqual(root.get("marketingId"), this.getNotId()));
			}
			Predicate[] p = predicates.toArray(new Predicate[0]);
			return p.length == 0 ? null : p.length == 1 ? p[0] : cb.and(p);
		};
	}

}
