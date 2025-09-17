package com.wanmi.sbc.mongo.oplog.context.handler;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.wanmi.sbc.mongo.oplog.data.Position;
import com.wanmi.sbc.mongo.oplog.utils.TimestampUtil;
import org.bson.BsonTimestamp;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.BSONTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

 /**
   * @Description:   拉取OpLog(从指定时间戳处开始)
   * @Author: ZhangLingKe
   * @CreateDate: 2019/8/14 17:30
   */
@Component
public class OplogPullHandler {

    private static final int PAGE_SIZE=100;
    private static final String OPLOG="oplog.rs";

//    @Autowired
//    private MongoFactory factory;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 以TAILABLE方式获取的DBCursor在mongodb driver
     *      内部是以一个while循环不停发送getmore命令到mongodb实现的
     *
     * @param timestamp oplog 起始时间
     * @return          一个持续等待数据、不超时的DBCursor
     */
//    public MongoCursor oplogCursor(BSONTimestamp timestamp) {
//        DBObject query = new BasicDBObject();
//        List<String> opList = new ArrayList<String>(){{
//            add("i");add("u");add("d");add("c");
//        }};
//
//        query.put("ts", new BasicDBObject(QueryOperators.GT, timestamp));
//        query.put("op", new BasicDBObject(QueryOperators.IN, opList));
//        int options = Bytes.QUERYOPTION_TAILABLE |
//                Bytes.QUERYOPTION_AWAITDATA |
//                Bytes.QUERYOPTION_NOTIMEOUT |
//                Bytes.QUERYOPTION_OPLOGREPLAY;
//        return factory.getDBCollection().find(query).setOptions(options);
//    }

    public MongoCursor<Document>  oplogCursor(BsonTimestamp timestamp) {
        List<Bson> filters = new ArrayList<>();
        filters.add(Filters.gt("ts",timestamp));
        filters.add(Filters.in("op",getOpList()));

        return mongoTemplate.getCollection(OPLOG).find(Filters.and(filters)).limit(100).oplogReplay(true).cursor();
    }

    public MongoCursor<Document> oplogCursorByPosition(Position position){
        BsonTimestamp bsonTimestamp = new BsonTimestamp(position.getTime(),position.getInc());
        return oplogCursor(bsonTimestamp);
    }

     /**
      * 获取时间戳
      */
     public BsonTimestamp getTimestamp() {

         //1.获取最近一次保存的值
         BsonTimestamp timestamp = TimestampUtil.get();

         //2.从mongodb中获取最新的值
         if (timestamp == null) {
             Bson sort = Sorts.descending("$natural");
             try (MongoCursor<Document> cursor = mongoTemplate.getCollection(OPLOG).find().sort(sort).limit(1).cursor()) {
                 while (cursor.hasNext()) {
                     Document dbObject = cursor.next();
                     timestamp = (BsonTimestamp) dbObject.get("ts");
                     break;
                 }
             }
         }

         //3.仍获取不到则以当前时刻作为起始值
         if (timestamp == null) {
             timestamp = TimestampUtil.getNow();
         }
         return timestamp;
     }

    private List<String> getOpList(){
        return new ArrayList<String>(){{
            add("i");add("u");add("d");add("c");
        }};

    }

}
