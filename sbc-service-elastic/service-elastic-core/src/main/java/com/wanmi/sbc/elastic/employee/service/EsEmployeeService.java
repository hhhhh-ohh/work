package com.wanmi.sbc.elastic.employee.service;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.ids;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.department.DepartmentProvider;
import com.wanmi.sbc.customer.api.provider.department.DepartmentQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.department.DepartmentListRequest;
import com.wanmi.sbc.customer.api.request.department.ModifyEmployeeNumRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeePageRequest;
import com.wanmi.sbc.customer.api.response.department.DepartmentListResponse;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.vo.DepartmentVO;
import com.wanmi.sbc.customer.bean.vo.EmployeeDepartmentVO;
import com.wanmi.sbc.customer.bean.vo.EmployeePageVO;
import com.wanmi.sbc.elastic.api.request.employee.*;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.employee.model.root.EsEmployee;
import com.wanmi.sbc.elastic.employee.repository.EsEmployeeRepository;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class EsEmployeeService {

    @Autowired
    private EsEmployeeRepository esEmployeeRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private DepartmentProvider departmentProvider;

    @Autowired
    private DepartmentQueryProvider departmentQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esEmployee.json")
    private Resource mapping;

    public void save(EsEmployee esEmployee) {
        this.createIndexAddMapping();
        esEmployeeRepository.save(esEmployee);
    }

    public Page<EsEmployee> page(Query searchQuery) {
        return esBaseService.commonPage(searchQuery, EsEmployee.class, EsConstants.DOC_EMPLOYEE_TYPE);
    }

    public void batchEnableByIds(List<String> employeeIds) {
        List<EsEmployee> EsEmployeeList = findByIds(employeeIds)
                .peek(esEmployee -> esEmployee.setAccountState(AccountState.ENABLE)).collect(Collectors.toList());
        saveAll(EsEmployeeList);
    }

    public void disableById(EsEmployeeDisableByIdRequest esEmployeeDisableByIdRequest) {
        Optional<EsEmployee> esEmployeeOptional = esEmployeeRepository.findById(esEmployeeDisableByIdRequest.getEmployeeId()).map(esEmployee -> {
            esEmployee.setAccountState(AccountState.DISABLE);
            esEmployee.setAccountDisableReason(esEmployeeDisableByIdRequest.getAccountDisableReason());
            return esEmployee;
        });
        esEmployeeOptional.ifPresent(this::save);
    }

    public void modifyNameById(EsEmployeeModifyNameByIdRequest esEmployeeModifyNameByIdRequest) {
        Optional<EsEmployee> esEmployeeOptional = esEmployeeRepository.findById(esEmployeeModifyNameByIdRequest.getEmployeeId()).map(esEmployee -> {
            esEmployee.setEmployeeName(esEmployeeModifyNameByIdRequest.getEmployeeName());
            return esEmployee;
        });
        esEmployeeOptional.ifPresent(this::save);
    }

    /**
     * @Author yangzhen
     * @Description 变更为主管
     * @Date 15:08 2021/3/26
     **/
    public void modifyLeaderById(EsEmployeeModifyLeaderByIdRequest esEmployeeModifyLeaderByIdRequest) {
        Optional<EsEmployee> esEmployeeOptional = esEmployeeRepository.findById(esEmployeeModifyLeaderByIdRequest.getNewEmployeeId()).map(esEmployee -> {
            List<String> manageDepartmentIds = esEmployee.getManageDepartmentIds();
            if (CollectionUtils.isNotEmpty(manageDepartmentIds)) {
                List<String> manageIds = new ArrayList<>(Arrays.asList(manageDepartmentIds.get(0).split("\\|")));
                if (CollectionUtils.isNotEmpty(manageIds)) {
                    if (!manageIds.contains(esEmployeeModifyLeaderByIdRequest.getDepartmentId())) {
                        manageIds.add(esEmployeeModifyLeaderByIdRequest.getDepartmentId());
                    }
                } else {
                    manageIds.add(esEmployeeModifyLeaderByIdRequest.getDepartmentId());
                }
                esEmployee.setManageDepartmentIds(Collections.singletonList(String.join("|", manageIds)));
            } else {
                esEmployee.setManageDepartmentIds(Collections.singletonList(esEmployeeModifyLeaderByIdRequest.getDepartmentId()));
            }
            esEmployee.setIsLeader(esEmployeeModifyLeaderByIdRequest.getIsLeader());
            return esEmployee;
        });
        esEmployeeOptional.ifPresent(this::save);
        //旧的主管部门清除
        if (StringUtils.isNotBlank(esEmployeeModifyLeaderByIdRequest.getOldEmployeeId())) {
            Optional<EsEmployee> esEmployeeOptional1 = esEmployeeRepository.findById(esEmployeeModifyLeaderByIdRequest.getOldEmployeeId()).map(esEmployee -> {
                List<String> manageDepartmentIds = esEmployee.getManageDepartmentIds();
                //如果旧的主管下还有管理部门集合 清除掉当前更换主管的部门
                if (CollectionUtils.isNotEmpty(manageDepartmentIds)) {
                    List<String> oldManageIds = new ArrayList<>(Arrays.asList(manageDepartmentIds.get(0).split("\\|")));
                    if (CollectionUtils.isNotEmpty(oldManageIds)) {
                        oldManageIds.remove(esEmployeeModifyLeaderByIdRequest.getDepartmentId());
                    }
                    if (oldManageIds.size() == 0) {
                        esEmployee.setManageDepartmentIds(null);
                    } else {
                        esEmployee.setManageDepartmentIds(Collections.singletonList(String.join("|", oldManageIds)));
                    }
                    //如果没有管理的部门了，将是否为主管置为否
                    if (CollectionUtils.isEmpty(oldManageIds)) {
                        esEmployee.setIsLeader(NumberUtils.INTEGER_ZERO);
                    }
                }
                return esEmployee;
            });
            esEmployeeOptional1.ifPresent(this::save);
        }
    }

    public void batchDisableByIds(EsEmployeeBatchDisableByIdsRequest esEmployeeBatchDisableByIdsRequest) {
        List<String> employeeIds = esEmployeeBatchDisableByIdsRequest.getEmployeeIds();
        List<EsEmployee> EsEmployeeList = findByIds(employeeIds).map(esEmployee -> {
            esEmployee.setAccountState(AccountState.DISABLE);
            esEmployee.setAccountDisableReason(esEmployeeBatchDisableByIdsRequest.getAccountDisableReason());
            return esEmployee;
        }).collect(Collectors.toList());
        saveAll(EsEmployeeList);
    }


    public Stream<EsEmployee> findByIds(List<String> employeeIds) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        if (CollectionUtils.isNotEmpty(employeeIds)) {
//            boolQueryBuilder.must(QueryBuilders.idsQuery().addIds(employeeIds.toArray(new String[0])));
//        }
//        builder.withQuery(boolQueryBuilder);
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        if (CollectionUtils.isNotEmpty(employeeIds)) {
            boolQueryBuilder.must(ids(a -> a.values(employeeIds)));
        }
        NativeQuery builder = NativeQuery.builder()
                .withQuery(a -> a.bool(boolQueryBuilder.build()))
                .build();
        return esBaseService.commonPage(builder, EsEmployee.class, EsConstants.DOC_EMPLOYEE_TYPE).stream();
    }

    public void batchDeleteByIds(List<String> employeeIds) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.idsQuery().addIds(employeeIds.toArray(new String[0])));
//        builder.withQuery(boolQueryBuilder);
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        boolQueryBuilder.must(ids(a -> a.values(employeeIds)));
        NativeQuery builder = NativeQuery.builder()
                .withQuery(a -> a.bool(boolQueryBuilder.build()))
                .build();
        elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
        elasticsearchTemplate.delete(builder, EsEmployee.class,
                IndexCoordinates.of(EsConstants.DOC_EMPLOYEE_TYPE));
    }

    public void changeDepartment(EsEmployeeChangeDepartmentRequest eSEmployeeChangeDepartmentRequest) {
        List<String> employeeIds = eSEmployeeChangeDepartmentRequest.getEmployeeIds();
        List<String> departmentIds = eSEmployeeChangeDepartmentRequest.getDepartmentIds();
        List<EsEmployee> EsEmployeeList = findByIds(employeeIds).map(esEmployee -> {
            esEmployee.setDepartmentIds(departmentIds);
            return esEmployee;
        }).collect(Collectors.toList());
        saveAll(EsEmployeeList);
    }

    public void batchSetEmployeeByIds(List<String> employeeIds) {
        List<EsEmployee> EsEmployeeList = findByIds(employeeIds).map(esEmployee -> {
            esEmployee.setIsEmployee(Constants.ZERO);
            return esEmployee;
        }).collect(Collectors.toList());
        saveAll(EsEmployeeList);
    }

    public void batchDimissionByIds(EsEmployeeBatchDimissionByIdsRequest esEmployeeBatchDimissionByIdsRequest) {
        List<String> employeeIds = esEmployeeBatchDimissionByIdsRequest.getEmployeeIds();
        List<EsEmployee> EsEmployeeList = findByIds(employeeIds).map(esEmployee -> {
            esEmployee.setAccountState(AccountState.DIMISSION);
            esEmployee.setAccountDisableReason(esEmployeeBatchDimissionByIdsRequest.getAccountDimissionReason());
            esEmployee.setManageDepartmentIds(null);
            return esEmployee;
        }).collect(Collectors.toList());
        saveAll(EsEmployeeList);
    }

    public void activateAccount(EsEmployeeActivateAccountRequest esEsEmployeeActivateAccountRequest) {
        List<String> employeeIds = esEsEmployeeActivateAccountRequest.getEmployeeIds();
        List<EsEmployee> EsEmployeeList = findByIds(employeeIds).filter(esEmployee ->
                !AccountState.DIMISSION.equals(esEmployee.getAccountState()))
                .peek(esEmployee -> esEmployee.setBecomeMember(Constants.ONE)).collect(Collectors.toList());
        saveAll(EsEmployeeList);
    }

    public void handoverEmployee(EsEmployeeHandoverRequest esEmployeeHandoverRequest) {
        List<String> employeeIds = esEmployeeHandoverRequest.getEmployeeIds();
        List<EsEmployee> EsEmployeeList = findByIds(employeeIds).filter(esEmployee ->
                !AccountState.DIMISSION.equals(esEmployee.getAccountState()))
                .filter(esEmployee ->
                        esEmployee.getIsEmployee() == Constants.ZERO)
                .peek(esEmployee -> esEmployee.setHeirEmployeeId(esEmployeeHandoverRequest.getNewEmployeeId())).collect(Collectors.toList());
        saveAll(EsEmployeeList);
    }

    public void saveAll(List<EsEmployee> employeeList) {
        this.createIndexAddMapping();
        esEmployeeRepository.saveAll(employeeList);
    }

    public void init(EsEmployeeInitRequest pageRequest) {
        List<EsEmployee> allEsEmployee = new ArrayList<>();
        initEsEmployee(pageRequest, allEsEmployee);
        //更新各部门人员数量
        initDepartmentEmployeeNum(allEsEmployee);

    }

    public void initDepartmentEmployeeNum(List<EsEmployee> allEsEmployee) {
        List<EmployeeDepartmentVO> list = new ArrayList<>();

        allEsEmployee.stream().distinct().collect(Collectors.toList()).forEach(esEmployee -> {
            for (String departmentId : esEmployee.getDepartmentIds()) {
                EmployeeDepartmentVO vo = new EmployeeDepartmentVO();
                vo.setDepartmentId(departmentId);
                vo.setEmployeeId(esEmployee.getEmployeeId());
                list.add(vo);
            }
        });

        Map<String, List<EmployeeDepartmentVO>> map = list.stream().collect(Collectors.groupingBy(EmployeeDepartmentVO::getDepartmentId));

        //查询所有部门
        BaseResponse<DepartmentListResponse> response =
                departmentQueryProvider.listDepartmentTree(DepartmentListRequest.builder().delFlag(DeleteFlag.NO.toValue()).build());

        if (CollectionUtils.isNotEmpty(response.getContext().getDepartmentVOS())) {
            List<DepartmentVO> departmentVos = response.getContext().getDepartmentVOS();

            for (DepartmentVO departmentVO : departmentVos) {
                departmentVO.setAllEmployeeIds(CollectionUtils.isNotEmpty(map.get(departmentVO.getDepartmentId()))
                        ? map.get(departmentVO.getDepartmentId()).stream().map(EmployeeDepartmentVO::getEmployeeId).collect(Collectors.toList()) : new ArrayList<>());
            }

            Map<String, Integer> modifyEmployeeNumMap = new HashMap<>();
            for (DepartmentVO departmentVO : departmentVos) {
                List<DepartmentVO> children = new ArrayList<>();
                getChildren(departmentVO, departmentVos, children);
                if (CollectionUtils.isNotEmpty(children)) {
                    for (DepartmentVO child : children) {
                        departmentVO.getAllEmployeeIds().addAll(child.getAllEmployeeIds());
                    }
                    departmentVO.setEmployeeNum((int) departmentVO.getAllEmployeeIds().stream().distinct().count());
                } else {
                    departmentVO.setEmployeeNum(departmentVO.getAllEmployeeIds().size());
                }

                modifyEmployeeNumMap.put(departmentVO.getDepartmentId(), departmentVO.getEmployeeNum());
            }

            ModifyEmployeeNumRequest build = ModifyEmployeeNumRequest.builder()
                    .modifyEmployeeNumMap(modifyEmployeeNumMap)
                    .build();
            departmentProvider.modifyDepartmentEmployeeNum(build);

        }
    }


    private void getChildren(DepartmentVO departmentVO, List<DepartmentVO> departmentVos, List<DepartmentVO> children) {

        List<DepartmentVO> collect = departmentVos.stream().filter(v -> v.getParentDepartmentId().equals(departmentVO.getDepartmentId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(collect)) {
            children.addAll(collect);
            for (DepartmentVO vo : collect) {
                getChildren(vo, departmentVos, children);
            }
        }
    }


    private void initEsEmployee(EsEmployeeInitRequest pageRequest, List<EsEmployee> allEmployee) {
        this.createIndexAddMapping();
        List<EmployeePageVO> employeePageVOS;
        try {
            EmployeePageRequest request = new EmployeePageRequest();
            if (CollectionUtils.isNotEmpty(pageRequest.getIdList())) {
                if (pageRequest.getPageNum()>0){
                    return;
                }
                request.setEmployeeIds(pageRequest.getIdList());
                request.setPageNum(0);
                request.setPageSize(pageRequest.getIdList().size());
                employeePageVOS = employeeQueryProvider.page(request).getContext().getEmployeePageVOPage().getContent();
            } else {
                if (pageRequest.getPageSize() == null) {
                    pageRequest.setPageSize(2000);
                }
                request.setPageSize(pageRequest.getPageSize());
                request.setPageNum(pageRequest.getPageNum());
                employeePageVOS = employeeQueryProvider.pageList(request).getContext().getEmployeeList();
            }
            List<EsEmployee> esEmployeeList = employeePageVOS.parallelStream().filter(employeePageVO -> StringUtils.isNotEmpty(employeePageVO.getEmployeeId())).map(employeePageVO -> {
                EsEmployee esEmployee = KsBeanUtil.convert(employeePageVO, EsEmployee.class);
                if (StringUtils.isNotEmpty(employeePageVO.getRoleIds())) {
                    esEmployee.setRoleIds(Arrays.asList(employeePageVO.getRoleIds().split(",")));
                }
                if (StringUtils.isNotEmpty(employeePageVO.getManageDepartmentIds())) {
                    esEmployee.setManageDepartmentIds(Arrays.asList(employeePageVO.getManageDepartmentIds().split(",")));
                }
                if (StringUtils.isNotEmpty(employeePageVO.getDepartmentIds())) {
                    esEmployee.setDepartmentIds(Arrays.asList(employeePageVO.getDepartmentIds().split(",")));
                }
                if (employeePageVO.getIsLeader() == null) {
                    esEmployee.setIsLeader(Constants.ZERO);
                }
                if (employeePageVO.getBecomeMember() == null) {
                    esEmployee.setBecomeMember(Constants.ZERO);
                }
                return esEmployee;
            }).collect(Collectors.toList());

            //如果不是最后一页，继续执行
            if (CollectionUtils.isNotEmpty(employeePageVOS)) {
                saveAll(esEmployeeList);
                Integer pageNum = pageRequest.getPageNum() + 1;
                pageRequest.setPageNum(pageNum);
                esEmployeeList.forEach(esEmployee -> {
                    if (CollectionUtils.isNotEmpty(esEmployee.getDepartmentIds()) && Objects.equals(DeleteFlag.NO, esEmployee.getDelFlag())) {
                        allEmployee.add(esEmployee);
                    }
                });
                initEsEmployee(pageRequest, allEmployee);
            } else {
                log.info("==========ES初始化员工结束，结束pageNum:{}==============", pageRequest.getPageNum());
            }

        } catch (Exception e) {
            log.error("==========ES初始化员工异常，异常pageNum:{}==============", pageRequest.getPageNum());
            log.error(e.getMessage());
        }

    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        esBaseService.existsOrCreate(EsConstants.DOC_EMPLOYEE_TYPE, mapping);
    }

    /**
     * 排序
     *
     * @return
     */
    public List<SortOptions> getSorts(EsEmployeePageRequest request) {
        List<SortOptions> sortBuilders = new ArrayList<>();
        if (MapUtils.isNotEmpty(request.getSortMap())) {
            request.getSortMap()
                    .forEach((k, v) ->
                            sortBuilders.add(SortOptions.of(g -> g.field(a -> a.field(k)
                                    .order(SortOrder.Desc.name().equalsIgnoreCase(v) ? SortOrder.Desc : SortOrder.Asc)))));
        }
        return sortBuilders;
    }

}
