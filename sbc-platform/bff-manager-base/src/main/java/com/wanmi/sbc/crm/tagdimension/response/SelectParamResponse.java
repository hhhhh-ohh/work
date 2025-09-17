package com.wanmi.sbc.crm.tagdimension.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SelectParamResponse
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/8/26 20:58
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectParamResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    private List<Map<String,String>> selectParamList;
}
