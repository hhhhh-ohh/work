package com.wanmi.sbc.order.api.response.returnorder;

import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 根据userId获取退单快照响应结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class ReturnOrderTransferByUserIdResponse extends ReturnOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;
}
