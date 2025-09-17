package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 会员查询参数
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerListByConditionRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 账户
     */
    @Schema(description = "账户")
    private String customerAccount;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String customerName;

    /**
     * 客户IDs
     */
    @Schema(description = "客户IDs")
    private List<String> customerIds;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态", contentSchema = com.wanmi.sbc.customer.bean.enums.CheckState.class)
    private Integer checkState;

    /**
     * 审核驳回原因
     */
    @Schema(description = "审核驳回原因")
    private String rejectReason;

    @Schema(description = "客户等级ID集合")
    private List<Long> customerLevelIds;

//    /**
//     * 封装公共条件
//     *
//     * @return
//     */
//    public Specification<Customer> getWhereCriteria() {
//        return (root, cquery, cbuild) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (CollectionUtils.isNotEmpty(customerIds)) {
//                predicates.add(root.get("customerId").in(customerIds));
//            }
//            if (Objects.nonNull(customerAccount) && StringUtils.isNotEmpty(customerAccount.trim())) {
//                predicates.add(cbuild.like(root.get("customerAccount"), buildLike(customerAccount)));
//            }
//            if (Objects.nonNull(customerId) && StringUtils.isNotEmpty(customerId.trim())) {
//                predicates.add(cbuild.equal(root.get("customerId"), customerId));
//            }
//            if (customerLevelId != null) {
//                predicates.add(cbuild.equal(root.get("customerLevelId"), customerLevelId));
//            }
//            if (checkState != null) {
//                predicates.add(cbuild.equal(root.get("checkState"), checkState));
//            }
//            //删除标记
//            if (delFlag != null) {
//                predicates.add(cbuild.equal(root.get("delFlag"), delFlag));
//            }
//            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
//            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
//        };
//    }
//
//    private static String buildLike(String field) {
//        StringBuilder stringBuilder = new StringBuilder();
//        return stringBuilder.append("%").append(XssUtils.replaceLikeWildcard(field)).append("%").toString();
//    }
}
