package com.wanmi.sbc.setting.api.request.searchterms;


import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * <p>搜索词VO</p>
 * @author weiwenhao
 * @date 2020-04-16
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Data
public class SearchAssociationalWordRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 搜索词
     */
    @Schema(description = "搜索词")
    @Length(max = 10)
    private String searchTerms;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;


}
