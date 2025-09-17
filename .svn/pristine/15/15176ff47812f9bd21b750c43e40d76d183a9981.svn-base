package com.wanmi.sbc.empower.api.request.channel.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengyang
 * @className ChannelGoodsSyncQueryRequest
 * @description
 *  <p>商品同步请求对象</p>
 *  用于发起一个同步通知-开始同步
 * @date 2021/5/23 11:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelGoodsSyncRequest extends BaseRequest {

    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;

    @Schema(description = "店铺主键")
    private Long storeId;

    @Schema(description = "公司编号")
    private Long companyInfoId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "指定商品池编号")
    private String poolNum;

    @Schema(description = "批次号- 用来实现并发处理，因为同步时会使用分布式锁，所以通过批次号来区分锁的Key")
    private String batchNum;
}
