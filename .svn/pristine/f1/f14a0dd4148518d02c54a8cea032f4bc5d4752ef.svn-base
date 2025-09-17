package com.wanmi.sbc.message.provider.impl.vopmessage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.vopmessage.VopLogProvider;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import com.wanmi.sbc.message.vopmessage.model.root.VopLog;
import com.wanmi.sbc.message.vopmessage.service.VopLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDate;

/**
 * <p>Vop消息发送表保存服务接口实现</p>
 * @author xufeng
 * @date 2022-05-20 15:53:00
 */
@RestController
@Validated
public class VopLogController implements VopLogProvider {
	@Autowired
	private VopLogService vopLogService;

	@Override
	public BaseResponse add(@RequestBody @Valid VopLogAddRequest vopLogAddRequest) {
		VopLog vopLog=new VopLog();
		KsBeanUtil.copyPropertiesThird(vopLogAddRequest,vopLog);
		vopLog.setDate(LocalDate.now());
		vopLog.setDelFlag(DeleteFlag.NO);
		vopLogService.add(vopLog);
		return BaseResponse.SUCCESSFUL();
	}

}

