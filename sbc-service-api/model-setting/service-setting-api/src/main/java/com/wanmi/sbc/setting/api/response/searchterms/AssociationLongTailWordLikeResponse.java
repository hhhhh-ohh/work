package com.wanmi.sbc.setting.api.response.searchterms;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.AssociationLongTailLikeWordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
public class AssociationLongTailWordLikeResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 搜索词&联想词信息
     */
    List<AssociationLongTailLikeWordVO>  AssociationLongTailLikeWordList;

}
