package com.wanmi.sbc.order.api.response.payingmemberrecordtemp;

import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费记录临时表修改结果</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecordTempModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的付费记录临时表信息
     */
    @Schema(description = "已修改的付费记录临时表信息")
    private PayingMemberRecordTempVO payingMemberRecordTempVO;
}
