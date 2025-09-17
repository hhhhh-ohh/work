package com.wanmi.sbc.elastic.api.request.searchterms;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.elastic.bean.vo.searchterms.EsSearchAssociationalWordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsSearchAssociationalWordRequest extends BaseRequest {


    /**
     * 设置搜索词信息
     */
    @Schema(description = "已保存搜索词信息")
    private List<EsSearchAssociationalWordVO> searchAssociationalWordVOList;


}