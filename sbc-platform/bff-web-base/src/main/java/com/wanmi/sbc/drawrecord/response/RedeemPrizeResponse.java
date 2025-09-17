package com.wanmi.sbc.drawrecord.response;

import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 类描述：
 *
 * @ClassName RedeemPrizeResponse
 * @Description 领取奖品Response
 * @Author ghj
 * @Date 4/16/21 10:46 AM
 * @Version 1.0
 */
@Schema
@Data
@Builder
public class RedeemPrizeResponse  {

    private DrawRecordVO drawRecordVO;
}
