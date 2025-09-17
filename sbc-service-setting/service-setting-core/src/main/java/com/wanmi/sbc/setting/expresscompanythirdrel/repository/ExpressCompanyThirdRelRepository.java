package com.wanmi.sbc.setting.expresscompanythirdrel.repository;

import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDTO;
import com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDetailDTO;
import com.wanmi.sbc.setting.expresscompanythirdrel.root.ExpressCompanyThirdRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.JoinColumn;
import java.util.List;

/**
 * @description 平台和第三方代销平台物流公司映射持久层
 * @author malianfeng
 * @date 2022/4/26 17:10
 */
@Repository
public interface ExpressCompanyThirdRelRepository extends JpaRepository<ExpressCompanyThirdRel, Long>,
        JpaSpecificationExecutor<ExpressCompanyThirdRel> {

    @Modifying
    @Query("DELETE FROM ExpressCompanyThirdRel WHERE sellPlatformType = ?1")
    void deleteBySellPlatformType(SellPlatformType sellPlatformType);

    @Query(value = "SELECT new com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDetailDTO(" +
            "ec.expressCompanyId, ec.expressName, ec.expressCode, tec.id, tec.expressName, tec.expressCode, ectr.sellPlatformType)"
            + "FROM ExpressCompany ec\n"
            + "INNER JOIN ExpressCompanyThirdRel ectr ON ectr.expressCompanyId = ec.expressCompanyId\n"
            + "INNER JOIN ThirdExpressCompany tec ON ectr.thirdExpressCompanyId = tec.id\n"
            + "WHERE ec.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO\n"
            + "AND tec.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO\n"
            + "AND ectr.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO\n"
            + "AND (:expressCompanyId IS NULL OR ec.expressCompanyId = :expressCompanyId)\n"
            + "AND (:sellPlatformType IS NULL OR ectr.sellPlatformType = :sellPlatformType)\n"
    )
    List<ExpressCompanyThirdRelDetailDTO> listWithDetail(@Param("expressCompanyId") Long expressCompanyId,
                                                         @Param("sellPlatformType") SellPlatformType sellPlatformType);

    /**
     * @description  根据平台物流公司Id 查询代销渠道公司物流信息
     * @author  wur
     * @date: 2022/4/28 10:34
     * @param expressCompanyIds
     * @param sellPlatformType
     * @return
     **/
    @Query(value = "SELECT new com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDetailDTO(" +
            "ectr.expressCompanyId, tec.id, tec.expressName, tec.expressCode, ectr.sellPlatformType) "
            + "FROM ThirdExpressCompany tec "
            + "INNER JOIN ExpressCompanyThirdRel ectr ON ectr.expressCompanyId in :expressCompanyIds and tec.id = ectr.thirdExpressCompanyId\n"
            + "WHERE tec.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO "
            + "AND ectr.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO "
            + "AND ectr.sellPlatformType = :sellPlatformType"
    )
    List<ExpressCompanyThirdRelDetailDTO> queryThirdExpress(@Param("expressCompanyIds") List<Long> expressCompanyIds,
                                                         @Param("sellPlatformType") SellPlatformType sellPlatformType);


    /**
     * @description   根据平台物流公司Code 查询代销渠道公司物流信息
     * @author  wur
     * @date: 2022/4/28 10:35
     * @param expressCode
     * @param sellPlatformType
     * @return
     **/
    @Query(value = "SELECT new com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDetailDTO(" +
            "ec.expressCompanyId, ec.expressName, ec.expressCode, tec.id, tec.expressName, tec.expressCode, ectr.sellPlatformType) "
            + "FROM ExpressCompany ec "
            + "INNER JOIN ExpressCompanyThirdRel ectr ON ectr.expressCompanyId = ec.expressCompanyId "
            + "INNER JOIN ThirdExpressCompany tec ON ectr.thirdExpressCompanyId = tec.id "
            + "WHERE ec.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO "
            + "AND tec.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO "
            + "AND ectr.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO "
            + "AND ec.expressCode = :expressCode "
            + "AND ectr.sellPlatformType = :sellPlatformType "
    )
    List<ExpressCompanyThirdRelDetailDTO> listWithDetailByExpressCode(@Param("expressCode") String expressCode,
                                                                      @Param("sellPlatformType") SellPlatformType sellPlatformType);

    /**

     * @description    根据第三方平台类型查询 查询已经绑定的平台物流公司
     * @author  wur
     * @date: 2022/4/28 10:37
     * @param sellPlatformType
     * @return
     **/
    @Query(value = "SELECT new com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDetailDTO(ec.expressCompanyId, ec.expressName, ec.expressCode) "
            + "FROM ExpressCompany ec "
            + "INNER JOIN ExpressCompanyThirdRel ectr ON "
                    +"ectr.sellPlatformType = :sellPlatformType "
                    +"and  ec.expressCompanyId = ectr.expressCompanyId "
                    +"and (COALESCE(:expressCompanyIdList, null) is null or ectr.expressCompanyId in :expressCompanyIdList) "
            + "WHERE ec.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO "
            + "AND ectr.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO "
    )
    List<ExpressCompanyThirdRelDetailDTO> queryExpress(@Param("expressCompanyIdList") List<Long> expressCompanyIdList, @Param("sellPlatformType") SellPlatformType sellPlatformType);

}
