package com.wanmi.sbc.message.provider.impl.minimsgsetting;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.message.api.provider.minimsgsetting.MiniMsgSettingProvider;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingModifyRequest;
import com.wanmi.sbc.message.bean.dto.MiniMsgSettingDTO;
import com.wanmi.sbc.message.minimsgsetting.model.root.MiniMsgSetting;
import com.wanmi.sbc.message.minimsgsetting.service.MiniMsgSettingService;
import java.util.List;
import java.util.Objects;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>小程序订阅消息配置表保存服务接口实现</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@RestController
@Validated
public class MiniMsgSettingController implements MiniMsgSettingProvider {
	@Autowired
	private MiniMsgSettingService miniMsgSettingService;

	@Override
	public BaseResponse modify(@RequestBody @Valid MiniMsgSettingModifyRequest miniMsgSettingModifyRequest) {
		List<MiniMsgSettingDTO> miniMsgSettingDTOList =
				miniMsgSettingModifyRequest.getMiniMsgSettingDTOList();
		for (MiniMsgSettingDTO miniMsgSettingDTO :
                miniMsgSettingDTOList){
			// 开启小程序消息，校验授权频次
			if (Objects.isNull(miniMsgSettingDTO.getId()) || Objects.isNull(miniMsgSettingDTO.getStatus())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			if (miniMsgSettingDTO.getStatus() == Constants.ONE){
				if (Objects.isNull(miniMsgSettingDTO.getNum())
						|| miniMsgSettingDTO.getNum()<Constants.ONE|| miniMsgSettingDTO.getNum()>Constants.NUM_30){
					throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
				}
				// 验证模板
				if (CollectionUtils.isEmpty(miniMsgSettingDTO.getData())){
					throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
				}
			}
			MiniMsgSetting miniMsgSetting =
					miniMsgSettingService.getOne(miniMsgSettingDTO.getId());
			miniMsgSetting.setNum(miniMsgSettingDTO.getNum());
			miniMsgSetting.setMessage(JSON.toJSONString(miniMsgSettingDTO.getData()));
			miniMsgSetting.setStatus(miniMsgSettingDTO.getStatus());
			miniMsgSetting.setUpdatePerson(miniMsgSettingModifyRequest.getUpdatePerson());
			miniMsgSetting.setUpdateTime(miniMsgSettingModifyRequest.getUpdateTime());
			miniMsgSettingService.modify(miniMsgSetting);
		}
		return BaseResponse.SUCCESSFUL();
	}

}

