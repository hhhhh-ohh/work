package com.wanmi.sbc.vas.recommend.filterrulessetting.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.vas.bean.enums.recommen.FilterRulesType;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * <p>实体类</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Data
@Entity
@Table(name = "filter_rules_setting")
public class FilterRulesSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 多少天内不重复
	 */
	@Column(name = "day_num")
	private Integer dayNum;

	/**
	 * 多少条内不重复
	 */
	@Column(name = "num")
	private Integer num;

	/**
	 *  过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重
	 */
	@Column(name = "type")
	private FilterRulesType type;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	public FilterRulesSetting(Integer dayNum, Integer num, FilterRulesType type, DeleteFlag delFlag, LocalDateTime createTime){
		super.setCreateTime(createTime);
		this.dayNum = dayNum;
		this.num = num;
		this.type = type;
		this.delFlag = delFlag;
	}

	public FilterRulesSetting(){}

}