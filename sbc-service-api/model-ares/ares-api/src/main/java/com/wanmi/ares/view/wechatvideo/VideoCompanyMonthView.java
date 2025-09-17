package com.wanmi.ares.view.wechatvideo;

import lombok.Data;

import java.io.Serializable;

/**
 * 视频号订单公司维度每月统计
 *
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Data
public class VideoCompanyMonthView extends VideoView implements Serializable {

	private static final long serialVersionUID = -9116060301642818663L;

	/**
	 * 店铺名称
	 */
	private String storeName;
}
