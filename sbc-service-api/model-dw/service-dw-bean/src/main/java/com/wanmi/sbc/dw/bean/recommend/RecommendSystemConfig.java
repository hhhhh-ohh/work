package com.wanmi.sbc.dw.bean.recommend;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName: com.wanmi.sbc.dw.bean.recommend.aa
 * @Description:
 * @Author: 何军红
 * @Time: 2020/12/3 18:32
 * @Version: 1.0
 */
@Data
public class RecommendSystemConfig {

    private String config_key;


    private String config_type;

    private String config_name;


    private String remark;


    private Integer status;


    private String context;

    private Timestamp create_time;

}
