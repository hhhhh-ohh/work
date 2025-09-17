package com.wanmi.sbc.empower.bean.vo.channel.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xufan
 * @Date: 2020/3/7 15:23
 * @Description: 配送信息
 *
 */
@Data
public class VopOrderTrack implements Serializable {

    private static final long serialVersionUID = -5764517353371355052L;
    /**
     * 操作内容明细
     */
    private String content;

    /**
     * 操作时间。日期格式为“yyyy-MM-dd hh:mm:ss”
     */
    private String msgTime;

    /**
     * 操作员名称
     */
    private String operator;
}
