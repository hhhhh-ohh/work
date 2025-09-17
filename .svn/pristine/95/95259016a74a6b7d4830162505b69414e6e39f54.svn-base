package com.wanmi.sbc.login;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.RoleInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.*;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByCompanyInfoIdRequest;
import com.wanmi.sbc.customer.api.response.employee.*;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.customer.bean.vo.EmployeeVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.sso.SSOConstant;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.CookieUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 登录
 * Created by CHENLI on 2017/11/3.
 */
@Tag(name = "LoginController", description = "登录服务API")
@RestController("supplierLoginController")
@Validated
@RequestMapping("/employee")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private RoleInfoQueryProvider roleInfoQueryProvider;

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${cookie.secure}")
    private boolean secure;

    @Value("${cookie.maxAge}")
    private Integer maxAge;

    @Value("${cookie.path}")
    private String path;

    @Value("${cookie.domain}")
    private String domain;

    // 密码错误超过5次后锁定的时间，单位：分钟
    private static final int LOCK_MINUTES = 30;

    // 允许密码错误最大次数
    private static final int PASS_WRONG_MAX_COUNTS = 5;


    /**
     * 会员登录
     *
     * @param loginRequest
     * @return ResponseEntity<LoginResponse>
     */
    @Operation(summary = "会员登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse<LoginResponse> login(@Valid @RequestBody EmployeeLoginRequest loginRequest,
                                             HttpServletResponse httpServletResponse) {
        String account = new String(Base64.getUrlDecoder().decode(loginRequest.getAccount().getBytes()));
        // 前端传参为base64编码后的password字符数组，要转为passwordChars字符数组处理
        char[] passwordChars = SecurityUtil.decodeAndWipeBase64Chars(loginRequest.getPassword());
        //商家登录
        return loginByAccount(account, passwordChars, httpServletResponse);

    }

    /**
     * 按账号查找登录
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    private BaseResponse<LoginResponse> loginByAccount(String account,
                                                              char[] password,
                                                              HttpServletResponse httpServletResponse) {
        EmployeeListRequest request = new EmployeeListRequest();
        request.setAccountName(account);
        request.setAccountTypeList(Arrays.asList(AccountType.s2bSupplier,
                AccountType.O2O));
        List<EmployeeListVO> employeeListVOS =
                employeeQueryProvider.list(request).getContext().getEmployeeList();
        if(CollectionUtils.isNotEmpty(employeeListVOS)){
            //门店账号、商家账号，一个账号类型只可能存在一种
            EmployeeVO employee = new EmployeeVO();
            KsBeanUtil.copyPropertiesThird(employeeListVOS.get(0), employee);
            return customerValidate(employee, password,httpServletResponse);
        }else{
            throw validateNull(account);
        }
    }


    /**
     * 当账号不存在时
     *
     * @param account
     * @return
     */
    public SbcRuntimeException validateNull(String account) {
        //登录错误次数
        String errKey = CacheKeyConstant.S2B_SUPPLIER_LOGIN_ERR.concat(account);
        //锁定时间
        String lockTimeKey = CacheKeyConstant.S2B_SUPPLIER_LOCK_TIME.concat(account);

        String errCountStr = redisService.getString(errKey);
        String lockTimeStr = redisService.getString(lockTimeKey);
        int error = NumberUtils.toInt(errCountStr);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime lockTime = LocalDateTime.now();
        if (StringUtils.isNotBlank(lockTimeStr)) {
            lockTime = LocalDateTime.parse(lockTimeStr, df);
            if (LocalDateTime.now().isAfter(lockTime.plus(LOCK_MINUTES, ChronoUnit.MINUTES))) {
                error = 0;
                redisService.delete(errKey);
                redisService.delete(lockTimeKey);
            }
        }

        boolean isRedisErr = false;
        if (Constants.ERROR.equals(errCountStr)) {
            isRedisErr = true;
        }

        if (!isRedisErr) {
            error = error + 1;
            redisService.setString(errKey, String.valueOf(error));
            //错误次数小于5次
            if (error < PASS_WRONG_MAX_COUNTS) {
                //用户名或密码错误，还有几次机会
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010078,
                        new Object[]{PASS_WRONG_MAX_COUNTS - error});
            } else if (error == PASS_WRONG_MAX_COUNTS) {
                redisService.setString(lockTimeKey, df.format(LocalDateTime.now()));
                //连续输错密码5次，请30分钟后重试
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010079, new Object[]{LOCK_MINUTES});
            } else {
                //连续输错密码5次，请{0}分钟后重试
                long retryMinutes = LocalDateTime.now().until
                        (lockTime.plus(LOCK_MINUTES, ChronoUnit.MINUTES), ChronoUnit.MINUTES);
                if ( retryMinutes < NumberUtils.INTEGER_ONE) {
                    //小于1分钟，提示1分钟后再试
                    retryMinutes = NumberUtils.INTEGER_ONE;
                }else {
                    retryMinutes += Constants.ONE;
                }
                //连续输错密码5次，请{0}分钟后重试
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010079, new Object[]{retryMinutes});
            }
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 验证登录账户
     *
     * @param employee
     * @param password
     * @return
     */
    private BaseResponse<LoginResponse> customerValidate(EmployeeVO employee, char[] password, HttpServletResponse
            httpServletResponse) {
        Integer currentErrCount = employee.getLoginErrorTime();

        //账号禁用
        if (AccountState.DISABLE.equals(employee.getAccountState())) {
            logger.info("员工[{}]已被锁定或禁用", employee.getAccountName());
            // 账户被锁定或禁用
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010087,
                    new Object[]{"，原因为：" + employee.getAccountDisableReason() + "，如有疑问请联系平台"});
        }
        //输错密码，账号被锁定
        if (Objects.nonNull(employee.getLoginLockTime())) {
            //如果已经过了30分钟
            if (LocalDateTime.now().isAfter(employee.getLoginLockTime().plus(LOCK_MINUTES, ChronoUnit.MINUTES))) {
                // 30分钟后解锁用户
                BaseResponse<EmployeeUnlockResponse> response = employeeProvider.unlockById(
                        EmployeeUnlockByIdRequest.builder().employeeId(employee.getEmployeeId()).build()
                );
                if (!response.getCode().equals(CommonErrorCodeEnum.K000000.getCode())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, response.getMessage());
                }
                currentErrCount = 0;
            }
        }

        //根据规则加密密码
        String encodePwd = SecurityUtil.getStoreLogpwd(String.valueOf(employee.getEmployeeId()), password,
                employee.getEmployeeSaltVal());
        // 擦除passwordChars字符数组，避免敏感数据泄漏
        CharArrayUtils.wipe(password);
        if (employee.getAccountPassword().equals(encodePwd)) {
            //在锁定账号的30分钟内，就算密码正确，也提示用户30分钟后再试
            if (Objects.nonNull(employee.getLoginLockTime()) && LocalDateTime.now().isBefore(employee.getLoginLockTime().plus(LOCK_MINUTES, ChronoUnit.MINUTES))) {
                //请{0}分钟后重试
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010092,
                        new Object[]{LocalDateTime.now().until(employee.getLoginLockTime().plus(LOCK_MINUTES,
                                ChronoUnit.MINUTES), ChronoUnit.MINUTES)});
            }

            Long storeId = null;
            Long companyInfoId = employee.getCompanyInfoId();
//            Store store = storeService.queryStoreByCompanyInfoId(companyInfoId);
            StoreVO store = storeQueryProvider.getStoreByCompanyInfoId(new StoreByCompanyInfoIdRequest(companyInfoId))
                    .getContext().getStoreVO();
            if (Objects.nonNull(store)) {
                storeId = store.getStoreId();
            }

            Map<String, Object> claims = Maps.newHashMap();
            claims.put("employeeId", employee.getEmployeeId());
            claims.put("EmployeeName", employee.getAccountName());
            claims.put("adminId", companyInfoId);
            claims.put("companyInfoId", companyInfoId);
            claims.put("storeId", storeId);
            //商家
            claims.put("platform",
                    StoreType.O2O.equals(store.getStoreType()) ?
                            Platform.STOREFRONT.toValue() :
                            Platform.SUPPLIER.toValue());
            claims.put("ip", HttpUtil.getIpAddr());
            //以下为魔方建站需要的固定参数(目前userId写死)
            claims.put("envCode", SSOConstant.ENV_CODE);
            claims.put("systemCode", SSOConstant.SYSTEM_CODE);
            claims.put("userId", SSOConstant.PLAT_SHOP_ID);
            if (Objects.nonNull(store.getCompanyType())){
                claims.put("companyType",store.getCompanyType().toValue());
            }
            claims.put("storeType", store.getStoreType().toValue());
            claims.put("realEmployeeName", StringUtils.isNotBlank(employee.getEmployeeName()) ? employee.getEmployeeName() : employee.getAccountName());
            //员工版本，不一致表示失效
            claims.put("versionNo", employee.getVersionNo());

            Map<String, String> vasList = redisService.hgetall(ConfigKey.VALUE_ADDED_SERVICES.toString());
            claims.put(ConfigKey.VALUE_ADDED_SERVICES.toString(), JSONObject.toJSONString(vasList));
            if (Objects.isNull(employee.getRoleIds())) {
                claims.put("roleName", org.apache.commons.lang3.StringUtils.EMPTY);
            } else {
                if (!StringUtils.isNotBlank(employee.getRoleIds())){
                    throw new SbcRuntimeException(CustomerErrorCodeEnum.K010099, new Object[]{"，请联系您的管理员"});
                }
                String roleName = org.apache.commons.lang3.StringUtils.EMPTY;
                String[] roleIds = employee.getRoleIds().split(",");
                for (String roleId: roleIds) {
                    RoleInfoQueryRequest roleInfoQueryRequest = new RoleInfoQueryRequest();
                    roleInfoQueryRequest.setRoleInfoId(Long.valueOf(roleId));
                    RoleInfoQueryResponse roleInfoQueryResponse =
                            roleInfoQueryProvider.getRoleInfoById(roleInfoQueryRequest).getContext();
                    if (Objects.nonNull(roleInfoQueryResponse) && Objects.nonNull(roleInfoQueryResponse.getRoleInfoVO())
                            && StringUtils.isNotBlank(roleInfoQueryResponse.getRoleInfoVO().getRoleName())) {
                        roleName += roleInfoQueryResponse.getRoleInfoVO().getRoleName() + ";";
                    }
                    claims.put("roleName", roleName);
                }

            }

            Date now = new Date();
            String token = Jwts.builder().setSubject(employee.getAccountName())
                    .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                    .setIssuedAt(now)
                    .setClaims(claims)
                    .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS))) // 有效期一个星期
                    .compact();
            logger.info("supplier-jwtSecretKey", jwtSecretKey);
            //redis保存token,并且7天后过期
            commonUtil.putLoginRedis(token);
            // 更新登录时间
            BaseResponse<EmployeeLoginTimeModifyResponse> response = employeeProvider.modifyLoginTimeById(
                    EmployeeLoginTimeModifyByIdRequest.builder().employeeId(employee.getEmployeeId()).build()
            );
            if (!response.getCode().equals(CommonErrorCodeEnum.K000000.getCode())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, response.getMessage());
            }
            Cookie cookie = new Cookie(storeId.toString(), token);
            cookie.setSecure(secure);
            cookie.setMaxAge(maxAge);
            cookie.setPath(path);
            if(StringUtils.isNotEmpty(domain)){
                cookie.setDomain(domain);
            }
            httpServletResponse.addCookie(cookie);
            logger.info("魔方domian配置为:[{}]", cookie.getDomain());
            //操作日志记录
            operateLogMQUtil.convertAndSend("登录", "登录",
                    StoreType.O2O.equals(store.getStoreType()) ? "登录O2O 门店端" :
                            "登录S2B 商家端",
                    new DefaultClaims(claims));

            boolean isEnd = Boolean.FALSE;
            if (Objects.nonNull(store.getContractEndDate())) {
                isEnd = store.getContractEndDate().isBefore(LocalDateTime.now());
            }

            return BaseResponse.success(LoginResponse.builder()
                    .accountName(employee.getAccountName())
                    .companyInfoId(companyInfoId)
                    .storeId(storeId)
                    .mobile(employee.getEmployeeMobile())
                    .auditState(Objects.isNull(store.getAuditState()) ? -1 : store.getAuditState().toValue())
                    .isMasterAccount(employee.getIsMasterAccount())
                    .isEmployee(employee.getIsEmployee())
                    .employeeId(employee.getEmployeeId())
                    .employeeName(StringUtils.isNotBlank(employee.getEmployeeName()) ? employee.getEmployeeName() : employee.getAccountName())
                    .companyType(store.getCompanyType())
                    .supplierName(Objects.nonNull(store) ? store.getSupplierName() : "")
                    .storeType(Objects.nonNull(store) ? store.getStoreType() : null)
                    .storeName(Objects.nonNull(store) ? store.getStoreName() : "")
                    .storeLogo(Objects.nonNull(store) ? store.getStoreLogo() : "")
                    .token(token)
                    .storeType(store.getStoreType())
                    .storeState(store.getStoreState())
                    .endFlag(isEnd)
                    .build());
        } else {
            logger.info("员工[{}]密码校验失败", employee.getEmployeeName());

            // 记录失败次数
            BaseResponse<EmployeeLoginErrorCountModifyByIdResponse> response =
                    employeeProvider.modifyLoginErrorCountById(
                            EmployeeLoginErrorCountModifyByIdRequest.builder().employeeId(employee.getEmployeeId())
                                    .build()
                    );
            if (!response.getCode().equals(CommonErrorCodeEnum.K000000.getCode())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, response.getMessage());
            }

            // 超过最大错误次数，锁定用户; 否则错误次数+1
            currentErrCount = currentErrCount + 1;
            //错误次数小于5次
            if (currentErrCount < PASS_WRONG_MAX_COUNTS) {
                //用户名或密码错误，还有几次机会
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010078,
                        new Object[]{PASS_WRONG_MAX_COUNTS - currentErrCount});
            } else if (currentErrCount == PASS_WRONG_MAX_COUNTS) {
                BaseResponse<EmployeeLoginLockTimeModifyByIdResponse> modifyResponse =
                        employeeProvider.modifyLoginLockTimeById(
                        EmployeeLoginLockTimeModifyByIdRequest.builder().employeeId(employee.getEmployeeId()).build()
                );
                if (!modifyResponse.getCode().equals(CommonErrorCodeEnum.K000000.getCode())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, modifyResponse.getMessage());
                }

                //连续输错密码5次，请30分钟后重试
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010079, new Object[]{LOCK_MINUTES});
            } else {
                //连续输错密码5次，请{0}分钟后重试
                long retryMinutes = LocalDateTime.now().until
                        (LocalDateTime.now().plus(LOCK_MINUTES, ChronoUnit.MINUTES), ChronoUnit.MINUTES);
                if ( retryMinutes < NumberUtils.INTEGER_ONE) {
                    //小于1分钟，提示1分钟后再试
                    retryMinutes = NumberUtils.INTEGER_ONE;
                }else {
                    retryMinutes += Constants.ONE;
                }
                //连续输错密码5次，请{0}分钟后重试
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010079, new Object[]{retryMinutes});
            }
        }
    }

    /**
     * 登录后验证商家店铺的各种状态
     *
     * @return
     */
    @Operation(summary = "登录后验证商家店铺的各种状态")
    @RequestMapping(value = "/store/state", method = RequestMethod.GET)
    public BaseResponse<LoginStoreResponse> queryStoreState() {
        LoginStoreResponse storeResponse = new LoginStoreResponse();
        StoreVO store = storeQueryProvider.getNoDeleteStoreById(new NoDeleteStoreByIdRequest(commonUtil.getStoreId()))
                .getContext().getStoreVO();
        //店铺不存在
        if (Objects.isNull(store)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010105);
        }
        storeResponse.setStoreState(store.getStoreState());
        storeResponse.setStoreClosedReason(store.getStoreClosedReason());
        storeResponse.setContractEndDate(store.getContractEndDate());
        storeResponse.setStoreType((store.getStoreType()));
        storeResponse.setCompanyCode(store.getCompanyInfo().getCompanyCode());
        storeResponse.setStoreName(store.getStoreName());
        //店铺已过期(当前时间超过了截止日期)
        int overDueDay = -1;
        if (Objects.nonNull(store.getContractEndDate())) {
            Period period = Period.between(LocalDate.from(LocalDateTime.now()),
                    LocalDate.from(store.getContractEndDate()));
            overDueDay = period.getYears() * 365 + period.getMonths() * 30 + period.getDays();
        }
        storeResponse.setOverDueDay(overDueDay);
        storeResponse.setStoreLogo(store.getStoreLogo());

        return BaseResponse.success(storeResponse);
    }

    /**
     * 登出
     *
     * @param response
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @Operation(summary = "管理员登出接口，会员登出")
    public BaseResponse logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.clear(response, String.valueOf(commonUtil.getStoreId()));
        String token = commonUtil.getToken(request);
        commonUtil.deleteLoginRedis(token);
        return BaseResponse.SUCCESSFUL();
    }
}
