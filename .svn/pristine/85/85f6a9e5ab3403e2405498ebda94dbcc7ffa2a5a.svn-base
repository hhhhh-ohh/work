package com.wanmi.sbc.setting.recommend.model.root;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanmi.sbc.setting.recommendcate.model.root.RecommendCate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import java.util.List;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;

import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>种草信息表实体类</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Data
@Entity
@Table(name = "recommend")
public class Recommend extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * page_code
	 */
	@Column(name = "page_code")
	private String pageCode;

	/**
	 * 标题
	 */
	@Column(name = "title")
	private String title;

	/**
	 * 新标题
	 */
	@Column(name = "new_title")
	private String newTitle;

	/**
	 * 分类id
	 */
	@Column(name = "cate_id")
	private Long cateId;

	/**
	 * 新分类id
	 */
	@Column(name = "new_cate_id")
	private Long newCateId;

	/**
	 * 保存状态 1:草稿 2:已发布 3:修改已发布
	 */
	@Column(name = "save_status	")
	private Integer saveStatus;

	/**
	 * 封面
	 */
	@Column(name = "cover_img")
	private String coverImg;

	/**
	 * 视频
	 */
	@Column(name = "video")
	private String video;

	/**
	 * 阅读数
	 */
	@Column(name = "read_num")
	private Long readNum;

	/**
	 * 点赞数
	 */
	@Column(name = "fabulous_num")
	private Long fabulousNum;

	/**
	 * 转发数
	 */
	@Column(name = "forward_num")
	private Long forwardNum;

	/**
	 * 转发数是否展示 1:是 0:否
	 */
	@Column(name = "forward_type")
	private Integer forwardType;

	/**
	 * 访客数
	 */
	@Column(name = "visitor_num")
	private Long visitorNum;

	/**
	 * 是否置顶 0:否 1:是
	 */
	@Column(name = "is_top")
	private Integer isTop;

	/**
	 * 置顶时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "top_time")
	private LocalDateTime topTime;

	/**
	 * 内容状态 0:隐藏 1:公开
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 删除时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "delete_time")
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Column(name = "delete_person")
	private String deletePerson;
}
