package com.wanmi.sbc.elastic.provider.impl.employee;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeQueryProvider;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeePageRequest;
import com.wanmi.sbc.elastic.api.response.employee.EsEmployeePageResponse;
import com.wanmi.sbc.elastic.bean.vo.customer.EsEmployeePageVO;
import com.wanmi.sbc.elastic.employee.mapper.EsEmployeeMapper;
import com.wanmi.sbc.elastic.employee.model.root.EsEmployee;
import com.wanmi.sbc.elastic.employee.service.EsEmployeeService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

@RestController
public class EsEmployeeQueryProviderImpl implements EsEmployeeQueryProvider {
    @Autowired
    EsEmployeeService esEmployeeService;

    @Autowired
    EsEmployeeMapper esEmployeeMapper;


    @Override
    public BaseResponse<EsEmployeePageResponse> page(@RequestBody @Valid EsEmployeePageRequest employeePageRequest) {
        NativeQueryBuilder nativeSearchQueryBuilder = this.esCriteria(employeePageRequest);
        nativeSearchQueryBuilder.withPageable(employeePageRequest.getPageable());
        esEmployeeService.getSorts(employeePageRequest).forEach(nativeSearchQueryBuilder::withSort);
        Page<EsEmployee> page = esEmployeeService.page(nativeSearchQueryBuilder.build());
        Page<EsEmployeePageVO> pageVo = page.map(esEmployee -> {
            EsEmployeePageVO esEmployeePageVO = esEmployeeMapper.esEmployeeToEsEmployeePageVO(esEmployee);
            esEmployeePageVO.setIsLeader(CollectionUtils.isNotEmpty(esEmployeePageVO.getManageDepartmentIds()) ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO);
            return esEmployeePageVO;
        });
        MicroServicePage<EsEmployeePageVO> employeePageVOPage = new MicroServicePage(pageVo,pageVo.getPageable());
        return BaseResponse.success(EsEmployeePageResponse.builder().employeePageVOPage(employeePageVOPage).build());
    }


    public NativeQueryBuilder esCriteria(EsEmployeePageRequest employeePageRequest) {

        NativeQueryBuilder queryBuilder = new NativeQueryBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        //员工账户
        if (StringUtils.isNotEmpty(employeePageRequest.getAccountName())) {
//            boolQueryBuilder.must(QueryBuilders.wildcardQuery("accountName", "*" + employeePageRequest.getAccountName() + "*"));
            boolQueryBuilder.must(wildcard(g -> g.field("accountName").value(ElasticCommonUtil.replaceEsLikeWildcard(employeePageRequest.getAccountName().trim()))));
        }

        //是否离职
        if (Objects.equals(NumberUtils.INTEGER_ONE, employeePageRequest.getIsHiddenDimission())) {
//            boolQueryBuilder.mustNot(QueryBuilders.termQuery("accountState", 2));
            boolQueryBuilder.mustNot(term(g -> g.field("accountState").value(2)));
        }

        //账号类型
        if (employeePageRequest.getAccountType() != null) {
//            boolQueryBuilder.must(QueryBuilders.termQuery("accountType", employeePageRequest.getAccountType().toValue()));
            boolQueryBuilder.must(term(g -> g.field("accountType").value(employeePageRequest.getAccountType().toValue())));
        }

        //商家id
        if (employeePageRequest.getCompanyInfoId() != null) {
//            boolQueryBuilder.must(QueryBuilders.termQuery("companyInfoId", employeePageRequest.getCompanyInfoId()));
            boolQueryBuilder.must(term(g -> g.field("companyInfoId").value(employeePageRequest.getCompanyInfoId())));
        }

        //员工姓名
        if (StringUtils.isNotEmpty(employeePageRequest.getUserName())) {
//            boolQueryBuilder.must(QueryBuilders.wildcardQuery("employeeName", "*" + employeePageRequest.getUserName() + "*"));
            boolQueryBuilder.must(wildcard(g -> g.field("employeeName").value(ElasticCommonUtil.replaceEsLikeWildcard(employeePageRequest.getUserName().trim()))));
        }

        //员工手机
        if (StringUtils.isNotEmpty(employeePageRequest.getUserPhone())) {
            String phone = employeePageRequest.getUserPhone().replaceAll("\\?", "-").replaceAll("\\*","-");
//            boolQueryBuilder.must(QueryBuilders.wildcardQuery("employeeMobile", "*" + phone + "*"));
            boolQueryBuilder.must(wildcard(g -> g.field("employeeMobile").value("*" + phone + "*")));
        }

        //员工工号
        if (StringUtils.isNotEmpty(employeePageRequest.getJobNo())) {
//            boolQueryBuilder.must(QueryBuilders.wildcardQuery("jobNo", "*" + employeePageRequest.getJobNo() + "*"));
            boolQueryBuilder.must(wildcard(g -> g.field("jobNo").value("*" + employeePageRequest.getJobNo() + "*")));
        }

        // 员工状态
        if (employeePageRequest.getAccountState() != null) {
//            boolQueryBuilder.must(QueryBuilders.termQuery("accountState", employeePageRequest.getAccountState().toValue()));
            boolQueryBuilder.must(term(g -> g.field("accountState").value(employeePageRequest.getAccountState().toValue())));
        }

        // 是否主管
        Optional<Integer> isLeader = Optional.ofNullable(employeePageRequest.getIsLeader());
        if (isLeader.isPresent()) {
            if(isLeader.get().intValue() == 1){
//                boolQueryBuilder.must(QueryBuilders.existsQuery("manageDepartmentIds"));
                boolQueryBuilder.must(exists(a -> a.field("manageDepartmentIds")));
            }else {
//                boolQueryBuilder.mustNot(QueryBuilders.existsQuery("manageDepartmentIds"));
                boolQueryBuilder.mustNot(exists(a -> a.field("manageDepartmentIds")));
            }
        }

        // 是否业务员
        if (employeePageRequest.getIsEmployee() != null) {
//            boolQueryBuilder.must(QueryBuilders.termQuery("isEmployee", employeePageRequest.getIsEmployee()));
            boolQueryBuilder.must(term(g -> g.field("isEmployee").value(employeePageRequest.getIsEmployee())));
        }

        // 是否激活会员账户
        if (employeePageRequest.getBecomeMember() != null) {
//            boolQueryBuilder.must(QueryBuilders.termQuery("becomeMember", employeePageRequest.getBecomeMember()));
            boolQueryBuilder.must(term(g -> g.field("becomeMember").value(employeePageRequest.getBecomeMember())));
        }
        if(Objects.isNull(employeePageRequest.getAllDepartmentClick()) || !employeePageRequest.getAllDepartmentClick()){
            List<String> departmentIds = employeePageRequest.getDepartmentIds();
            // 批量查询-部门id
            if (CollectionUtils.isNotEmpty(departmentIds)) {
//                boolQueryBuilder.must(QueryBuilders.termsQuery("departmentIds",departmentIds));
                boolQueryBuilder.must(terms(g -> g.field("departmentIds").terms(t -> t.value(departmentIds.stream().map(FieldValue::of).collect(Collectors.toList())))));
            }
        }
        // 批量查询-角色id
        if (CollectionUtils.isNotEmpty(employeePageRequest.getRoleIds())) {
//            boolQueryBuilder.must(QueryBuilders.termsQuery("roleIds", employeePageRequest.getRoleIds()));
            boolQueryBuilder.must(terms(g -> g.field("roleIds").terms(t -> t.value(employeePageRequest.getRoleIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
//        boolQueryBuilder.must(QueryBuilders.termQuery("delFlag", DeleteFlag.NO.toValue()));
        boolQueryBuilder.must(term(g -> g.field("delFlag").value(DeleteFlag.NO.toValue())));
        queryBuilder.withQuery(a -> a.bool(boolQueryBuilder.build()));
        return queryBuilder;
    }
}
