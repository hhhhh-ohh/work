package com.wanmi.sbc.marketing.api.request.drawrecord;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类描述：
 *
 * @ClassName DrawRecordModifyListRequest
 * @Description TODO
 * @Author ghj
 * @Date 4/19/21 7:21 PM
 * @Version 1.0
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordModifyListRequest {

    private List<DrawRecordModifyRequest> modifyRequests;
}
