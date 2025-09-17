package com.wanmi.sbc.setting.api.response.cancellationreason;

import com.wanmi.sbc.setting.bean.vo.CancellationReasonVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author houshuai
 * @date 2022/3/29 15:30
 * @description <p> 注销原因返回体 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class CancellationReasonDetailResponse extends CancellationReasonVO{

}
