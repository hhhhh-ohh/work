package com.wanmi.sbc.dbreplay.bean.canal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-8-7
 * \* Time: 11:12
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \ 用于执行sql的类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SqlData {
    private String sql;                         //执行的sql
    private List<Integer> sqlType;              //参数的数据类型int
    private List<List<Object>> values;          //通过PreparedStatement的参数

    /**
     * 转换成jdbcTemplate执行时需要的int[]类型
     * @return
     */
    public int[] getDaoArgsType(){
        return this.sqlType.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 转换成jdbcTemplate执行时需要的object[]类型
     * @return
     */
    public List<Object[]> getDaoValues() {
        return  this.values.stream().map(list -> list.toArray(new Object[list.size()])).collect(Collectors.toList());
    }
}
