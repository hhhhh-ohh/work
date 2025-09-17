package com.wanmi.sbc.message.storemessagenode;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.storemessagenode.StoreMessageNodeQueryProvider;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeByIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeListRequest;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeByIdResponse;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeListResponse;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeSelectListResponse;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSelectVO;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Tag(name =  "商家消息节点管理API", description =  "StoreMessageNodeController")
@RestController
@Validated
@RequestMapping(value = "/storeMessageNode")
public class StoreMessageNodeController {

    @Autowired
    private StoreMessageNodeQueryProvider storeMessageNodeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "商家消息节点下拉列表")
    @GetMapping("/select-list")
    public BaseResponse<StoreMessageNodeSelectListResponse> getSelectList() {
        StoreMessageNodeListRequest selectListReq = new StoreMessageNodeListRequest();
        selectListReq.setPlatformType(StoreMessagePlatform.fromValue(commonUtil.getOperator().getPlatform()));
        selectListReq.setDelFlag(DeleteFlag.NO);
        List<StoreMessageNodeVO> nodeVOList = storeMessageNodeQueryProvider.list(selectListReq).getContext().getStoreMessageNodeVOList();
        return BaseResponse.success(new StoreMessageNodeSelectListResponse(KsBeanUtil.convert(nodeVOList, StoreMessageNodeSelectVO.class)));
    }

    @Operation(summary = "列表查询商家消息节点")
    @GetMapping("/list")
    public BaseResponse<StoreMessageNodeListResponse> list() {
        StoreMessageNodeListRequest listRequest = new StoreMessageNodeListRequest();
        listRequest.setPlatformType(StoreMessagePlatform.fromValue(commonUtil.getOperator().getPlatform()));
        listRequest.setDelFlag(DeleteFlag.NO);
        BaseResponse<StoreMessageNodeListResponse> response = storeMessageNodeQueryProvider.list(listRequest);
        List<StoreMessageNodeVO> storeMessageNodeVOList = response.getContext().getStoreMessageNodeVOList();
        // 处理跨行数，按菜单名称分组，第一个元素的跨行数为分组列表的数量
        Map<String, List<StoreMessageNodeVO>> menuNameMap =
                storeMessageNodeVOList.stream().collect(Collectors.groupingBy(StoreMessageNodeVO::getMenuName));
        for (Map.Entry<String, List<StoreMessageNodeVO>> entry : menuNameMap.entrySet()) {
            String menuName = entry.getKey();
            if (Objects.isNull(menuName)) {
                continue;
            }
            StoreMessageNodeVO firstNode = storeMessageNodeVOList.stream().filter(item -> menuName.equals(item.getMenuName())).findFirst().orElse(null);
            if (Objects.nonNull(firstNode)) {
                firstNode.setRowSpan(entry.getValue().size());
            }
        }
        return response;
    }

    @Operation(summary = "根据id查询商家消息节点")
    @GetMapping("/{id}")
    public BaseResponse<StoreMessageNodeByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreMessageNodeByIdRequest idReq = new StoreMessageNodeByIdRequest();
        idReq.setId(id);
        return storeMessageNodeQueryProvider.getById(idReq);
    }

}
