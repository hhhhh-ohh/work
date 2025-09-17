package com.wanmi.sbc.customer.department.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.api.request.department.DepartmentQueryRequest;
import com.wanmi.sbc.customer.api.request.department.ModifyEmployeeNumRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.DepartmentTreeVO;
import com.wanmi.sbc.customer.bean.vo.DepartmentVO;
import com.wanmi.sbc.customer.department.model.root.Department;
import com.wanmi.sbc.customer.department.repository.DepartmentRepository;
import com.wanmi.sbc.customer.employee.model.root.Employee;
import com.wanmi.sbc.customer.employee.repository.EmployeeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>部门管理业务逻辑</p>
 *
 * @author wanggang
 * @date 2020-02-26 19:02:40
 */
@Service("DepartmentService")
public class DepartmentService {

    private static final String SPLIT_CHAR = "|";

    @Value("classpath:/download/department_import_template.xls")
    private Resource templateFile;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 新增部门管理
     *
     * @author wanggang
     */
    @Transactional
    public Department add(Department entity) {
        if (StringUtils.isBlank(entity.getParentDepartmentId())) {
            entity.setParentDepartmentId("0");
        }

        //验证重复名称
        findByParentDepartmentIdAndDepartmentNameAndDelFlag(entity.getParentDepartmentId(),
                entity.getDepartmentName(), entity.getCompanyInfoId());


        DepartmentQueryRequest queryRequest = new DepartmentQueryRequest();
        queryRequest.setParentDepartmentId(entity.getParentDepartmentId());
        Long count = departmentRepository.count(DepartmentWhereCriteriaBuilder.build(queryRequest));
        entity.setDepartmentSort(count.intValue() + 1);

        entity.setDepartmentId(UUIDUtil.getUUID());
        entity.setDelFlag(DeleteFlag.NO);
        entity.setDepartmentGrade(1);
        entity.setEmployeeNum(0);
        //填充部门路径，获取父类的部门路径进行拼凑,例01|001|0001
        String catePath = String.valueOf(NumberUtils.LONG_ZERO);
        if (!Objects.equals(entity.getParentDepartmentId(), Constants.STR_0)) {
            Department parentDepartment = departmentRepository.findById(entity.getParentDepartmentId()).orElse(null);
            if (Objects.isNull(parentDepartment) || Objects.equals(parentDepartment.getDelFlag(), DeleteFlag.YES)) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010120);
            }
            catePath =
                    parentDepartment.getParentDepartmentIds().concat(String.valueOf(parentDepartment.getDepartmentId()));
            entity.setDepartmentGrade(parentDepartment.getDepartmentGrade() + 1);
        }
        entity.setParentDepartmentIds(catePath.concat(SPLIT_CHAR));
        try {
            entity = departmentRepository.saveAndFlush(entity);
        } catch (Exception e) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010126);
        }

        return entity;
    }

    /**
     * 新增部门管理(导入)
     *
     * @author wanggang
     */
    @Transactional
    public List<Department> add(List<Department> list) {
        return departmentRepository.saveAll(list);
    }

    /**
     * 修改部门管理
     *
     * @author wanggang
     */
    @Transactional
    public Department modifyDepartmentName(Department entity) {
        Department oldDepartment =
                departmentRepository.findById(entity.getDepartmentId()).orElseThrow(() -> new SbcRuntimeException(CustomerErrorCodeEnum.K010122));
        if (oldDepartment.getDepartmentName().equals(entity.getDepartmentName())) {
            return oldDepartment;
        }
        findByParentDepartmentIdAndDepartmentNameAndDelFlag(oldDepartment.getParentDepartmentId(),
                entity.getDepartmentName(), oldDepartment.getCompanyInfoId());
        oldDepartment.setDepartmentName(entity.getDepartmentName());
        oldDepartment.setUpdatePerson(entity.getUpdatePerson());
        return oldDepartment;
    }


    /**
     * 单个删除部门管理以及子部门
     *
     * @author wanggang
     */
    @Transactional
    public Department deleteById(String id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null || department.getDelFlag() == DeleteFlag.YES) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (department.getEmployeeNum() > 0) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010125);
        }
        String oldCatePath =
                department.getParentDepartmentIds().concat(String.valueOf(department.getDepartmentId()).concat(SPLIT_CHAR));
        departmentRepository.modifyByParentDepartmentIdIsStartingWith(oldCatePath);
        departmentRepository.modifyByDepartmentId(id);
        return department;
    }

    /**
     * 根据管理部门集合查询所有子级部门ID集合
     *
     * @param manageDepartmentIds
     * @return
     */
    public Set<String> findByManageDepartmentIds(String manageDepartmentIds) {
        String[] departmentIds = manageDepartmentIds.split("\\|");
        Set<String> departmentIdList = new HashSet<>();
        for (String departmentId : departmentIds) {
            Department department = departmentRepository.findById(departmentId).orElse(null);
            if (Objects.isNull(department)) {
                continue;
            }
            String parentDepartmentPath =
                    department.getParentDepartmentIds().concat(String.valueOf(department.getDepartmentId()).concat(SPLIT_CHAR));
            List<Department> departmentList =
                    departmentRepository.findByParentDepartmentIdsStartingWith(parentDepartmentPath);
            departmentIdList.add(departmentId);
            departmentIdList.addAll(departmentList.stream().map(Department::getDepartmentId).collect(Collectors.toList()));
        }
        return departmentIdList;
    }

    /**
     * 根据管理部门集合查询当前部门及其所有子级部门ID集合以及上级部门
     *
     * @param manageDepartmentIds
     * @return
     */
    public List<String> findAllByManageDepartmentIds(String manageDepartmentIds) {
        String[] departmentIds = manageDepartmentIds.split("\\|");
        Set<String> departmentIdList = new HashSet<>();
        for (String departmentId : departmentIds) {
            Department department = departmentRepository.findById(departmentId).orElse(null);
            if (Objects.isNull(department)) {
                continue;
            }
            String parentDepartmentPath =
                    department.getParentDepartmentIds().concat(String.valueOf(department.getDepartmentId()).concat(SPLIT_CHAR));
            List<Department> departmentList =
                    departmentRepository.findByParentDepartmentIdsStartingWith(parentDepartmentPath);
            departmentIdList.add(departmentId);
            String parentDepartmentIds = department.getParentDepartmentIds().substring(1).replace("\\|", "");
            ;
            departmentIdList.addAll(Arrays.asList(parentDepartmentIds.split("\\|")));
            departmentIdList.addAll(departmentList.stream().map(Department::getDepartmentId).collect(Collectors.toList()));
        }
        return departmentIdList.stream().filter(s -> StringUtils.isNotBlank(s)).collect(Collectors.toList());
    }

    /**
     * 根据归属部门查询部门树（只到当前当前归属部门层级）
     *
     * @param belongToDepartmentIds
     * @return
     */
    public List<String> findByBelongToDepartmentIds(String belongToDepartmentIds) {
        String[] departmentIds = belongToDepartmentIds.split(",");
        Set<String> departmentIdList = new HashSet<>();
        for (String departmentId : departmentIds) {
            Department department = departmentRepository.findById(departmentId).orElse(null);
            if (Objects.isNull(department)) {
                continue;
            }
            String parentDepartmentIds = department.getParentDepartmentIds().substring(1).replace("\\|", "");
            ;
            departmentIdList.addAll(Arrays.asList(parentDepartmentIds.split("\\|")));
            departmentIdList.add(departmentId);
        }
        return departmentIdList.stream().filter(s -> StringUtils.isNotBlank(s)).collect(Collectors.toList());
    }


    /**
     * 导出部门 模板
     *
     * @return base64位文件字符串
     */
    public String exportTemplate() {
        if (templateFile == null || !templateFile.exists()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010124);
        }
        try (InputStream is = templateFile.getInputStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Workbook wk = WorkbookFactory.create(is)) {
            wk.write(baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 分页查询部门管理
     *
     * @author wanggang
     */
    public Page<Department> page(DepartmentQueryRequest queryReq) {
        return departmentRepository.findAll(
                DepartmentWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询部门管理
     *
     * @author wanggang
     */
    public List<Department> list(DepartmentQueryRequest queryReq) {
        return departmentRepository.findAll(DepartmentWhereCriteriaBuilder.build(queryReq), queryReq.getSort());
    }

    /**
     * 修改主管
     *
     * @param oldEmployeeId
     * @param newEmployeeId
     * @param departmentId
     * @return
     */
    @Transactional
    public int modifyLeader(String oldEmployeeId, String newEmployeeId, String departmentId, Long companyInfoId) {
        Optional<Department> departmentOp = departmentRepository.findById(departmentId);
        Department department =
                departmentOp.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
        if (Objects.nonNull(companyInfoId) && !Objects.equals(department.getCompanyInfoId(), companyInfoId)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        List<String> employeeIds = StringUtils.isNotBlank(oldEmployeeId) ?
                Arrays.asList(oldEmployeeId, newEmployeeId) :
                Collections.singletonList(newEmployeeId);
        List<Employee> employeeList = employeeRepository.findByEmployeeIdIn(employeeIds);
        Map<String, Employee> map = employeeList.stream().collect(Collectors.toMap(Employee::getEmployeeId,
                Function.identity()));
        List<Employee> result = new ArrayList<>();
        if (StringUtils.isNotBlank(oldEmployeeId) && employeeIds.size() > 1) {
            Employee oldEmployee = map.get(oldEmployeeId);
            if (Objects.nonNull(oldEmployee)) {
                String oldEmployeeManageDepartmentIds = oldEmployee.getManageDepartmentIds();
                oldEmployeeManageDepartmentIds =
                        oldEmployeeManageDepartmentIds.replace(departmentId.concat(SPLIT_CHAR), "");
                oldEmployee.setManageDepartmentIds(oldEmployeeManageDepartmentIds);
                result.add(oldEmployee);
            }
        }
        Employee newEmployee = map.get(newEmployeeId);
        String newEmployeeManageDepartmentIds = newEmployee.getManageDepartmentIds();
        newEmployeeManageDepartmentIds = StringUtils.isNotBlank(newEmployeeManageDepartmentIds) ?
                newEmployeeManageDepartmentIds.concat(departmentId).concat(SPLIT_CHAR) :
                departmentId.concat(SPLIT_CHAR);
        newEmployee.setManageDepartmentIds(newEmployeeManageDepartmentIds);
        result.add(newEmployee);
        employeeRepository.saveAll(result);
        return departmentRepository.modifyLeaderByDepartmentId(departmentId, newEmployee.getEmployeeId(),
                newEmployee.getEmployeeName());
    }

    /**
     * 根据主管ID修改主管名称
     *
     * @param employeeId
     * @param employeeName
     * @return
     */
    @Transactional
    public int modifyEmployeeNameByEmployeeId(String employeeId, String employeeName) {
        return departmentRepository.modifyEmployeeNameByEmployeeId(employeeId, employeeName);
    }

    /**
     * 数据总数
     *
     * @return
     */
    public Long countByCompanyInfoId(Long companyInfoId) {
        return departmentRepository.countByCompanyInfoIdAndDelFlag(companyInfoId, DeleteFlag.NO);
    }

    /**
     * 初始化部门主管(置空主管ID、主管名称)
     *
     * @return
     */
    @Transactional
    public int initDepartmentLeader(List<String> departmentIds) {
        return departmentRepository.initDepartmentLeader(departmentIds);
    }

    /**
     * 部门管理列表结构转化为树形结构
     *
     * @param list
     * @return
     */
    public List<DepartmentTreeVO> listToTree(List<DepartmentTreeVO> list) {
        List<DepartmentTreeVO> treeList = new LinkedList<>();
        if (CollectionUtils.isEmpty(list)) {
            return treeList;
        }
        for (DepartmentTreeVO tree : list) {
            if ("0".equals(tree.getParentDepartmentId())) {
                treeList.add(findChildren(tree, list));
            }
        }
        return treeList;
    }

    /**
     * 遍历根节点，生成子节点
     *
     * @param tree
     * @param list
     * @return
     */
    private DepartmentTreeVO findChildren(DepartmentTreeVO tree, List<DepartmentTreeVO> list) {
        for (DepartmentTreeVO node : list) {
            if (node.getParentDepartmentId().equals(tree.getDepartmentId())) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new LinkedList<>());
                }
                tree.getChildren().add(findChildren(node, list));
            }
        }
        return tree;
    }


    /**
     * 将实体包装成VO
     *
     * @author wanggang
     */
    public DepartmentVO wrapperVo(Department department) {
        if (department != null) {
            DepartmentVO departmentVO = KsBeanUtil.convert(department, DepartmentVO.class);
            return departmentVO;
        }
        return null;
    }

    /**
     * 验证部门名称是否重复
     *
     * @param parentDepartmentId
     * @param departmentName
     */
    private void findByParentDepartmentIdAndDepartmentNameAndDelFlag(String parentDepartmentId, String departmentName
            , Long companyInfoId) {
        //验证重复名称
        Department checkDepartment =
                departmentRepository.findByParentDepartmentIdAndDepartmentNameAndDelFlagAndCompanyInfoId(parentDepartmentId, departmentName, DeleteFlag.NO, companyInfoId);
        if (Objects.nonNull(checkDepartment)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010121);
        }
    }

    /**
     * 批量更新各部门人员数量
     *
     * @param request
     */
    @Transactional(rollbackFor = {Exception.class})
    public void modifyDepartmentEmployeeNum(ModifyEmployeeNumRequest request) {
        if (MapUtils.isNotEmpty(request.getModifyEmployeeNumMap())) {
            Set<Map.Entry<String, Integer>> entries = request.getModifyEmployeeNumMap().entrySet();
            entries.forEach(v -> departmentRepository.modifyDepartmentEmployeeNum(v.getKey(), v.getValue() == null ? 0 : v.getValue()));
        }

    }
}

