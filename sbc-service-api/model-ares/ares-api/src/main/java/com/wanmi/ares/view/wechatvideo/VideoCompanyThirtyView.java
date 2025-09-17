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
public class VideoCompanyThirtyView extends VideoView implements Serializable {

	private static final long serialVersionUID = -912332342765786563L;

	/**
	 * 店铺名称
	 */
	private String storeName;
}
