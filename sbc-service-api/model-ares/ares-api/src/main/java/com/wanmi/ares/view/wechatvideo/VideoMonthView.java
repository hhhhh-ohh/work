package com.wanmi.ares.view.wechatvideo;

import lombok.Data;

import java.io.Serializable;

/**
 * 视频号维度自然月统计
 *
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Data
public class VideoMonthView extends VideoView implements Serializable {

	private static final long serialVersionUID = -911234325456448663L;

	/**
	 * 视频号名称
	 */
	private String videoName;

}
