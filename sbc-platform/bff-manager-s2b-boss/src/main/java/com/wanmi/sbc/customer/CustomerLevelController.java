package com.wanmi.sbc.customer;

import static java.util.Objects.nonNull;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailPageRequest;
import com.wanmi.sbc.customer.api.request.level.*;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.validator.CustomerLevelValidator;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * 会员等级
 * Created by CHENLI on 2017/4/17.
 */
@Tag(name =  "会员等级API", description =  "CustomerLevelController")
@RestController
@Validated
@RequestMapping("/customer")
public class CustomerLevelController {

    @Autowired
    private CustomerLevelProvider customerLevelProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private CustomerLevelValidator customerLevelValidator;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @InitBinder
    public void initBinder(DataBinder binder) {
        if (binder.getTarget() instanceof CustomerLevelEditRequest) {
            binder.setValidator(customerLevelValidator);
        }
    }

    /**
     * 分页查询会员等级
     *
     * @param customerLevelQueryRequest
     * @return
     */
    @Operation(summary = "分页查询会员等级")
    @RequestMapping(value = "/customerLevels", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> list(@RequestBody CustomerLevelQueryRequest customerLevelQueryRequest) {
        customerLevelQueryRequest.putSort("createTime", SortType.ASC.toValue());
        customerLevelQueryRequest.putSort("customerLevelId", SortType.ASC.toValue());
        CustomerLevelPageRequest request = new CustomerLevelPageRequest();
        KsBeanUtil.copyPropertiesThird(customerLevelQueryRequest, request);
        return ResponseEntity.ok(BaseResponse.success(customerLevelQueryProvider.pageCustomerLevel(request).getContext().getCustomerLevelVOPage()));
    }

    /**
     * 保存会员等级
     *
     * @param customerLevelEditRequest
     * @return
     */
    @Operation(summary = "保存会员等级")
    @RequestMapping(value = "/customerLevel", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> add(@Valid @RequestBody CustomerLevelEditRequest customerLevelEditRequest) {
        // 验证参数
        String result = checkCustomerLevelParameter(customerLevelEditRequest);
        if (result != null) {
            return ResponseEntity.ok(BaseResponse.error(result));
        }
        CustomerLevelVO vo = customerLevelQueryProvider.getCustomerLevel(
                CustomerLevelGetRequest.builder().customerLevelName(customerLevelEditRequest.getCustomerLevelName()).build()
        ).getContext();
        if(Objects.nonNull(vo) && Objects.nonNull(vo.getCustomerLevelId())){
            return ResponseEntity.ok(BaseResponse.error("等级名称不可重复"));
        }
        CustomerLevelAddRequest addRequest = new CustomerLevelAddRequest();
        KsBeanUtil.copyPropertiesThird(customerLevelEditRequest, addRequest);
        addRequest.setEmployeeId(commonUtil.getOperatorId());

        operateLogMQUtil.convertAndSend("客户", "新增客户等级",
                "新增客户等级：" + customerLevelEditRequest.getCustomerLevelName());

        return ResponseEntity.ok(customerLevelProvider.addCustomerLevel(addRequest));
    }

    /**
     * 通过ID查询会员等级详情
     *
     * @param customerLevelId
     * @return
     */
    @Operation(summary = "通过ID查询会员等级详情")
    @Parameter(name = "customerLevelId", description = "会员等级id", required = true)
    @RequestMapping(value = "/customerLevel/{customerLevelId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerLevelVO> findById(@PathVariable("customerLevelId") Long customerLevelId) {
        CustomerLevelVO vo = customerLevelQueryProvider.getCustomerLevelById(
                CustomerLevelByIdRequest.builder().customerLevelId(customerLevelId).build()
        ).getContext();
        if (Objects.isNull(vo) || Objects.isNull(vo.getCustomerLevelId())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010011);
        }
        return ResponseEntity.ok(vo);
    }

    /**
     * 条件查询单条信息
     *
     * @param customerLevelQueryRequest
     * @return
     */
    @Operation(summary = "条件查询单条信息")
    @RequestMapping(value = "/Level", method = RequestMethod.POST)
    public ResponseEntity<CustomerLevelVO> info(@RequestBody CustomerLevelQueryRequest customerLevelQueryRequest) {
        CustomerLevelGetRequest request = new CustomerLevelGetRequest();
        KsBeanUtil.copyPropertiesThird(customerLevelQueryRequest, request);
        return ResponseEntity.ok(customerLevelQueryProvider.getCustomerLevel(request).getContext());
    }

    /**
     * 编辑会员等级
     *
     * @param customerLevelEditRequest
     * @return
     */
    @Operation(summary = "编辑会员等级")
    @RequestMapping(value = "/customerLevel", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> edit(@RequestBody CustomerLevelEditRequest customerLevelEditRequest) {
        // 验证参数
        String result = checkCustomerLevelParameter(customerLevelEditRequest);
        if (result != null) {
            return ResponseEntity.ok(BaseResponse.error(result));
        }
        CustomerLevelModifyRequest modifyRequest = new CustomerLevelModifyRequest();
        KsBeanUtil.copyPropertiesThird(customerLevelEditRequest, modifyRequest);
        modifyRequest.setEmployeeId(commonUtil.getOperatorId());

        //操作日志记录
        operateLogMQUtil.convertAndSend("客户", "编辑客户等级",
                "编辑客户等级：" + customerLevelEditRequest.getCustomerLevelName());

        return ResponseEntity.ok(customerLevelProvider.modifyCustomerLevel(modifyRequest));
    }

    /**
     * 删除会员等级
     *
     * @param customerLevelId
     * @return
     */
    @Operation(summary = "删除会员等级")
    @Parameter(name = "customerLevelId", description = "会员等级id", required = true)
    @RequestMapping(value = "/customerLevel/{customerLevelId}", method = RequestMethod.DELETE)
    public ResponseEntity<BaseResponse> delete(@PathVariable("customerLevelId") Long customerLevelId) {
        if (customerLevelId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 判断是否有会员是该等级
        Long count = customerQueryProvider.count(CustomerDetailPageRequest.builder()
                .defaultFlag(DefaultFlag.YES)
                .delFlag(DefaultFlag.NO.toValue())
                .customerLevelId(customerLevelId).build()).getContext();
        if (count > 0) {
            return ResponseEntity.ok(BaseResponse.error("已有会员达到该等级，不可删除"));
        }
        // 判断是否删除的最高等级
        List<CustomerLevelVO> customerLevelVOList = customerLevelQueryProvider.listAllCustomerLevel().getContext().getCustomerLevelVOList();
        if (customerLevelVOList != null && customerLevelVOList.size() > 0) {
            if (customerLevelVOList.size() == 1) {
                return ResponseEntity.ok(BaseResponse.error("默认等级无法删除"));
            }
            CustomerLevelVO customerLevelVO = customerLevelVOList.get(customerLevelVOList.size() - 1);
            if (!customerLevelVO.getCustomerLevelId().equals(customerLevelId)) {
                return ResponseEntity.ok(BaseResponse.error("只能删除最高等级"));
            }
        }

        //操作日志记录
        CustomerLevelVO customerLevel = customerLevelQueryProvider.getCustomerLevelById(
                CustomerLevelByIdRequest.builder().customerLevelId(customerLevelId).build()
        ).getContext();

        if (nonNull(customerLevel)) {
            operateLogMQUtil.convertAndSend("客户", "删除客户等级",
                    "删除客户等级：" + customerLevel.getCustomerLevelName());
        }

        return ResponseEntity.ok(customerLevelProvider.deleteCustomerLevel(
                CustomerLevelDeleteByIdRequest.builder().customerLevelId(customerLevelId).build()));
    }

    /**
     * 验证参数
     *
     * @param customerLevelEditRequest
     * @return
     */
    private String checkCustomerLevelParameter(CustomerLevelEditRequest customerLevelEditRequest) {
        // 平台等级名称验证
        int length = customerLevelEditRequest.getCustomerLevelName().length();
        if (!(length >= Constants.ONE && length <= Constants.FIVE)) {
            return "等级名称仅限1-5位字符";
        }
        CustomerLevelVO vo = customerLevelQueryProvider.getCustomerLevel(
                CustomerLevelGetRequest.builder().customerLevelName(customerLevelEditRequest.getCustomerLevelName()).build()
        ).getContext();
        // 新建有相同名称或者修改除该等级外有相同名称
        if (Objects.nonNull(vo.getCustomerLevelId())
                && (Objects.isNull(customerLevelEditRequest.getCustomerLevelId())
                || !(vo.getCustomerLevelId().equals(customerLevelEditRequest.getCustomerLevelId())))) {
            return "等级名称不可重复";
        }

        // 平台等级条数验证
        List<CustomerLevelVO> customerLevelVOList = customerLevelQueryProvider.listAllCustomerLevel().getContext().getCustomerLevelVOList();
        if (customerLevelEditRequest.getCustomerLevelId() == null && customerLevelVOList != null && customerLevelVOList.size() >= Constants.TEN) {
            return "最多支持10个平台客户等级";
        }

        // 编辑默认等级验证
        if (Objects.nonNull(customerLevelEditRequest.getCustomerLevelId())
                && customerLevelEditRequest.getCustomerLevelId().equals(customerLevelVOList.get(0).getCustomerLevelId())
                && !customerLevelEditRequest.getGrowthValue().equals(customerLevelVOList.get(0).getGrowthValue())) {
            return "默认等级成长值不可编辑";
        }

        // 成长值验证
        if (!(Objects.nonNull(customerLevelEditRequest.getCustomerLevelId())
                && (customerLevelEditRequest.getCustomerLevelId().equals(customerLevelVOList.get(0).getCustomerLevelId())))
                && customerLevelEditRequest.getGrowthValue().compareTo((long) 1) < 0) {
            return "成长值最少为1";
        }
        if (customerLevelEditRequest.getGrowthValue().compareTo((long) 999999999) > 0) {
            return "成长值最大为999999999";
        }
        if (customerLevelEditRequest.getCustomerLevelId() != null) {
            // 修改成长值不能小于上一等级，大于下一等级
            for (int i = 0; i < customerLevelVOList.size(); i++) {
                if (customerLevelVOList.get(i).getCustomerLevelId().equals(customerLevelEditRequest.getCustomerLevelId())) {
                    if (i != 0 &&
                            !(customerLevelEditRequest.getGrowthValue().compareTo(customerLevelVOList.get(i - 1).getGrowthValue()) > 0)) {
                        return "当前成长值应大于低级别成长值";
                    }
                    if (i != customerLevelVOList.size() - 1 &&
                            !(customerLevelEditRequest.getGrowthValue().compareTo(customerLevelVOList.get(i + 1).getGrowthValue()) < 0)) {
                        return "当前成长值应小于高级别成长值";
                    }
                }
            }
        } else {
            if (customerLevelVOList != null && customerLevelVOList.size() > 0) {
                Long growthValue = customerLevelVOList.get(customerLevelVOList.size() - 1).getGrowthValue();
                if (!(growthValue.compareTo(customerLevelEditRequest.getGrowthValue()) < 0)) {
                    return "当前成长值应大于低级别成长值";
                }
            }
        }

        // 验证折扣率
        BigDecimal discount = customerLevelEditRequest.getCustomerLevelDiscount();
        if (!(discount.compareTo(BigDecimal.valueOf(0)) > 0)
                || discount.compareTo(BigDecimal.valueOf(1)) > 0) {
            return "请输入0-1（不包含0）之间的数字，精确到小数点后两位";
        }
        BigDecimal result = discount.setScale(2, RoundingMode.HALF_UP);
        if (!(result.compareTo(discount) == 0)) {
            return "请输入0-1（不包含0）之间的数字，精确到小数点后两位";
        }
        return null;
    }
}
