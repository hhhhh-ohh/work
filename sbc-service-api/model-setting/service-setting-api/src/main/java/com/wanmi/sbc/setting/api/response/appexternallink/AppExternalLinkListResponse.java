package com.wanmi.sbc.setting.api.response.appexternallink;

import com.wanmi.sbc.setting.bean.vo.AppExternalLinkVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>AppExternalLink列表结果</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalLinkListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * AppExternalLink列表结果
     */
    @Schema(description = "AppExternalLink列表结果")
    private List<AppExternalLinkVO> appExternalLinkVOList;
}
