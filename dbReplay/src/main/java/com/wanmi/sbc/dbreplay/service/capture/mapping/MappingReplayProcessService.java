package com.wanmi.sbc.dbreplay.service.capture.mapping;

import com.wanmi.sbc.dbreplay.bean.canal.SqlData;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingBean;
import com.wanmi.sbc.dbreplay.bean.capture.OplogData;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingDispatch;
import com.wanmi.sbc.dbreplay.config.capture.dispatch.DispatchInterface;
import com.wanmi.sbc.dbreplay.dao.canal.CanalBaseDao;
import com.wanmi.sbc.dbreplay.utils.SpringContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wanmi.sbc.dbreplay.common.MappingCache.BEAN;
import static com.wanmi.sbc.dbreplay.common.MappingCache.DATA;

/**
 * \* Author: zgl
 * \* Date: 2020-2-19
 * \* Time: 14:37
 * \* Description:
 * \
 */
@Service
@Slf4j
public class MappingReplayProcessService {

    @Autowired
    private CanalBaseDao baseDao;

    public void process(OplogData oplogData){
        log.info("mappingReplayProcessService==oplogData=={}",oplogData);
        List<String> list = DATA.get(oplogData.getCollection());
        if(CollectionUtils.isEmpty(list)){
            log.error("{}===no search this collection mapping config ",oplogData.getCollection());
            return;
        }
        for(String beanName : list) {
            MappingBean mappingBean = BEAN.get(beanName);
            if (mappingBean == null) {
                log.error("{}===no search this config", beanName);
                continue;
            }
            log.info("mapping process beanName={},type={},dispatch={}", beanName, oplogData.getType().name(), mappingBean.getDispatch());

            String jsonData = oplogData.getData();
            MappingDispatch dispatch =  mappingBean.getDispatch();
            if (dispatch != null){
                if (CollectionUtils.isNotEmpty((dispatch.getTypeFilter()))) {
                    if (!mappingBean.getDispatch().getTypeFilter().contains(oplogData.getType().name())) {
                        log.info("this type({}) no config mapping!", oplogData.getType().name());
                        return;
                    }
                }
                if (MapUtils.isNotEmpty(dispatch.getTypeDispatch())
                        && StringUtils.isNotBlank(dispatch.getTypeDispatch().get(oplogData.getType().name())))
                {
                    DispatchInterface dispatchInterface = SpringContextHelper.getBean(dispatch.getTypeDispatch().get(oplogData.getType().name()));
                    jsonData = dispatchInterface.beforeProcess(oplogData);
                }else if(StringUtils.isNotBlank(dispatch.getDispatch())){
                    DispatchInterface dispatchInterface = SpringContextHelper.getBean(dispatch.getDispatch());
                    jsonData = dispatchInterface.beforeProcess(oplogData);
                }
            }
            DataProcess dataProcess = oplogData.getType().process();
            List<SqlData> sqlDatas = dataProcess.getSqlData(jsonData, mappingBean, oplogData.getCondition());
            if (CollectionUtils.isNotEmpty(sqlDatas)) {
               /* this.baseDao.updateValues(sqlData);*/
                this.baseDao.update(sqlDatas);
            }
        }
    }


}
