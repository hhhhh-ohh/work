package com.wanmi.sbc.mongo.oplog.data;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.bson.BsonTimestamp;

import java.time.LocalDateTime;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: zhanggaolei
 * \* Date: 2019-12-13
 * \* Time: 17:45
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
public class Meta {

    private Position position;
    private String model;
    private String clusterName;
    private LocalDateTime dateTime;

    public Meta(){

    }
    public Meta(String model, String clusterName){
        this.model = model;
        this.clusterName = clusterName;
    }


    /**
     * 为了解决BSONTimestamp空构造函数默认赋值了造成json反序列化的时候position字段无法解析过来
     * 的问题，新增这个方法
     * @param json
     * @return
     */
    public static Meta jsonToBean(String json){
        Meta meta = JSONObject.parseObject(json, Meta.class);
        Position position = JSONObject.parseObject(json).getObject("position",Position.class);
        if(position!=null) {
            meta.setPosition(position);
        }
        return meta;
    }

    /**
     * 为了解决BSONTimestamp空构造函数默认赋值了造成json反序列化的时候position字段无法解析过来
     * 的问题，新增这个方法
     * @param jsonBytes
     * @return
     */
    public static Meta jsonToBean(byte[] jsonBytes){
        String jsonString = new String(jsonBytes);
        Meta meta = JSONObject.parseObject(jsonString, Meta.class);
        String json = new String(jsonBytes);
        Position position = JSONObject.parseObject(json).getObject("position",Position.class);
        if(position!=null) {
            meta.setPosition(position);
        }
        return meta;
    }

    public void setPositionByBson(BsonTimestamp bsonTimestamp){
        this.position = new Position();
        position.setInc(bsonTimestamp.getInc());
        position.setTime(bsonTimestamp.getTime());
    }

}
