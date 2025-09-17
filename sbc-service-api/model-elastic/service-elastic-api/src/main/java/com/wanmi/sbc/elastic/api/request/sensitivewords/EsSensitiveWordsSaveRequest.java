package com.wanmi.sbc.elastic.api.request.sensitivewords;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.elastic.bean.vo.sensitivewords.EsSensitiveWordsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/11 18:03
 * @description <p> 敏感词查询 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema
public class EsSensitiveWordsSaveRequest extends BaseRequest {


    /**
     * 敏感词信息
     */
    @Schema(description = "敏感词信息")
    private List<EsSensitiveWordsVO> sensitiveWordsList;

}
