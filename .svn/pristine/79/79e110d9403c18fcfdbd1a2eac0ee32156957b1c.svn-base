package com.wanmi.sbc.goods.brand.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.brand.model.root.ContractBrand;
import com.wanmi.sbc.goods.brand.model.root.ContractBrandAudit;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * 二次签约品牌查询请求结构
 * @author wangchao
 */
@Data
public class ContractBrandAuditQueryRequest extends BaseRequest {

    private static final long serialVersionUID = 8027423166340436946L;

    /**
     * 签约品牌分类
     */
    private Long contractBrandId;


    /**
     * 店铺主键
     */
    private Long storeId;

    /**
     * 平台品牌id
     */
    private List<Long> goodsBrandIds;

    /**
     * 自定义品牌名称
     */
    private String checkBrandName;

    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<ContractBrandAudit> getWhereCriteria() {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (storeId != null) {
                predicates.add(cbuild.equal(root.get("storeId"), storeId));
            }
            if (goodsBrandIds != null) {
                Join<ContractBrand, GoodsBrand> contractBrandJoin = root.join("goodsBrand");
                CriteriaBuilder.In in = cbuild.in(contractBrandJoin.get("brandId"));
                for (Long id : goodsBrandIds) {
                    in.value(id);
                }
                predicates.add(in);
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        }

                ;
    }
}
