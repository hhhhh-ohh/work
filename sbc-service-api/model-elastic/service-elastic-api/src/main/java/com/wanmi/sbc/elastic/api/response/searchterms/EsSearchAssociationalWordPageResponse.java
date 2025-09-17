package com.wanmi.sbc.elastic.api.response.searchterms;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.searchterms.EsSearchAssociationalWordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsSearchAssociationalWordPageResponse extends BasicResponse {

    /**
     * 设置搜索词信息
     */
    @Schema(description = "已保存搜索词信息")
    private MicroServicePage<EsSearchAssociationalWordVO> searchAssociationalWordPage;


}