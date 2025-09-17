package com.wanmi.sbc.empower.api.request.customerservice;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingItemVO;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 在线客服配置修改参数
 *
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServiceSettingModifyRequest extends EmpowerBaseRequest {
  private static final long serialVersionUID = 1L;

  /** onlineService客服信息 */
  @Schema(description = "onlineService客服信息")
  private CustomerServiceSettingVO qqOnlineServerRop;

  /** onlineerviceItem座席列表 */
  @Schema(description = "onlineerviceItem座席列表")
  private List<CustomerServiceSettingItemVO> qqOnlineServerItemRopList;

  /** onlineService客服信息 */
  @Schema(description = "onlineService客服信息")
  private CustomerServiceSettingVO weChatOnlineServerRop;

  @Schema(description = "店铺id", hidden = true)
  private Long storeId;

  /** onlineService网易七鱼客服信息 */
  @Schema(description = "onlineService网易七鱼客服信息")
  private CustomerServiceSettingVO qiYuOnlineServerRop;
}
