package com.wanmi.sbc.setting.provider.impl.storemessagenodesetting;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeQueryRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.*;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.stockWarning.service.StockWarningService;
import com.wanmi.sbc.setting.storemessagenode.model.root.StoreMessageNode;
import com.wanmi.sbc.setting.storemessagenode.service.StoreMessageNodeService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingProvider;
import com.wanmi.sbc.setting.api.response.storemessagenodesetting.StoreMessageNodeSettingAddResponse;
import com.wanmi.sbc.setting.storemessagenodesetting.service.StoreMessageNodeSettingService;
import com.wanmi.sbc.setting.storemessagenodesetting.model.root.StoreMessageNodeSetting;

import jakarta.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>商家消息节点设置保存服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@RestController
@Validated
public class StoreMessageNodeSettingController implements StoreMessageNodeSettingProvider {
	@Autowired
	private StoreMessageNodeSettingService storeMessageNodeSettingService;

	@Autowired
	private StoreMessageNodeService storeMessageNodeService;

	@Autowired
	private StockWarningService stockWarningService;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public BaseResponse<StoreMessageNodeSettingAddResponse> add(@RequestBody @Valid StoreMessageNodeSettingAddRequest storeMessageNodeSettingAddRequest) {
		StoreMessageNodeSetting storeMessageNodeSetting = KsBeanUtil.convert(storeMessageNodeSettingAddRequest, StoreMessageNodeSetting.class);
		return BaseResponse.success(new StoreMessageNodeSettingAddResponse(
				storeMessageNodeSettingService.wrapperVo(storeMessageNodeSettingService.add(storeMessageNodeSetting))));
	}

	@Override
	public BaseResponse modifyStatus(@RequestBody @Valid StoreMessageNodeSettingModifyStatusRequest modifyStatusRequest) {
		Long storeId = modifyStatusRequest.getStoreId();
//		String nodeCode = modifyStatusRequest.getNodeCode();
//		BoolFlag status = modifyStatusRequest.getStatus();
//		String updatePerson = modifyStatusRequest.getUpdatePerson();
//		Long warningStock = modifyStatusRequest.getWarningStock();
		//如果不相等
		//查询原来的配置开关
		StoreMessageNodeSetting storeMessageNodeSetting = storeMessageNodeSettingService.getStatus(storeId);
		if (Objects.nonNull(storeMessageNodeSetting) && Objects.nonNull(modifyStatusRequest.getStatus()) && !modifyStatusRequest.getStatus().equals(storeMessageNodeSetting.getStatus())
				&& "GOODS_SKU_WARN_STOCK".equals(modifyStatusRequest.getNodeCode())) {
			if (Objects.nonNull(modifyStatusRequest.getIsSwitchChange()) && modifyStatusRequest.getIsSwitchChange() && redisUtil.hasKey(RedisKeyConstant.STOCK_WARNING_ONCE+storeId)) {
				throw new SbcRuntimeException(SettingErrorCodeEnum.K070115);
			}
		}
		int count = storeMessageNodeSettingService.modifyStatus(modifyStatusRequest);
		//获取当前时间
		LocalDateTime now = LocalDateTime.now();
		// 获取明天凌晨的时间
		LocalDateTime tom = LocalDateTime.of(now.plusDays(Constants.ONE).toLocalDate(), LocalTime.MIN);
		//设置过去时间。。明天凌晨重新可以操作
		if (Objects.nonNull(modifyStatusRequest.getIsSwitchChange()) && modifyStatusRequest.getIsSwitchChange() && "GOODS_SKU_WARN_STOCK".equals(modifyStatusRequest.getNodeCode())) {
			redisUtil.setString(RedisKeyConstant.STOCK_WARNING_ONCE+storeId, NumberUtils.INTEGER_ZERO.toString(), Duration.between(now,tom).getSeconds());
		}

		//如果商品库存预警开关关闭，且当前节点标识为商家库存预警时，则将数据库表stock_warning中isWarning字段刷新未预警状态
		if (BoolFlag.NO == modifyStatusRequest.getStatus() && "GOODS_SKU_WARN_STOCK".equals(modifyStatusRequest.getNodeCode())) {
			stockWarningService.modifyIsWarning(storeId);
		}
		// 影响行数为0，说明此配置不存在，需要初始化
		if (count == 0) {
			// 查询当前平台下是否存在该开关设置
			List<StoreMessageNode> nodeList = storeMessageNodeService.list(StoreMessageNodeQueryRequest.builder()
					.nodeCode(modifyStatusRequest.getNodeCode())
					.platformType(modifyStatusRequest.getPlatformType())
					.delFlag(DeleteFlag.NO).build());
			StoreMessageNode node = nodeList.stream().findFirst().orElse(null);
			if (Objects.isNull(node)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			// 构造实体
			StoreMessageNodeSetting entity = new StoreMessageNodeSetting();
			entity.setStoreId(modifyStatusRequest.getStoreId());
			entity.setNodeId(node.getId());
			entity.setNodeCode(node.getNodeCode());
			entity.setStatus(modifyStatusRequest.getStatus());
			entity.setDelFlag(DeleteFlag.NO);
			entity.setCreatePerson(modifyStatusRequest.getUpdatePerson());
			entity.setCreateTime(LocalDateTime.now());
			if (Objects.isNull(modifyStatusRequest.getWarningStock())) {
				entity.setWarningStock(1L);
			} else {
				entity.setWarningStock(modifyStatusRequest.getWarningStock());
			}
			// 保存开关设置
			storeMessageNodeSettingService.add(entity);
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid StoreMessageNodeSettingDelByIdRequest storeMessageNodeSettingDelByIdRequest) {
		StoreMessageNodeSetting storeMessageNodeSetting = KsBeanUtil.convert(storeMessageNodeSettingDelByIdRequest, StoreMessageNodeSetting.class);
		storeMessageNodeSetting.setDelFlag(DeleteFlag.YES);
		storeMessageNodeSettingService.deleteById(storeMessageNodeSetting);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid StoreMessageNodeSettingDelByIdListRequest storeMessageNodeSettingDelByIdListRequest) {
		storeMessageNodeSettingService.deleteByIdList(storeMessageNodeSettingDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

