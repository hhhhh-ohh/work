package com.wanmi.sbc.setting.api.response.searchterms;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.SearchAssociationalWordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>搜索词结果</p>
 * @author weiwenhao
 * @date 2020-04-16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchAssociationalWordPageResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 设置搜索词信息
     */
    @Schema(description = "已保存搜索词信息")
    private MicroServicePage<SearchAssociationalWordVO> searchAssociationalWordPage;


}
