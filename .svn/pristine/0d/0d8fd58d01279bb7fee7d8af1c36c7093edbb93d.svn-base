package com.wanmi.sbc.setting.api.response.operatedatalog;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.OperateDataLogVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>系统日志新增结果</p>
 * @author guanfl
 * @date 2020-04-21 14:57:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperateDataLogAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的系统日志信息
     */
    @Schema(description = "已新增的系统日志信息")
    private OperateDataLogVO operateDataLogVO;
}
