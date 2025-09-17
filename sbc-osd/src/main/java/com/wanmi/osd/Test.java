package com.wanmi.osd;

import com.wanmi.osd.bean.OsdClientParam;
import com.wanmi.osd.bean.OsdResource;
import com.wanmi.osd.bean.OsdType;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private static final String ali = "{\"accessKeyId\":\"LTAI5tFLmLreNm6BprKZVfWL\"," +
            "\"accessKeySecret\":\"lROd6MmNKWIi9Svz97kM33NCiQ8GEx\",\"bucketName\":\"wanmi-b2b\"," +
            "\"endPoint\":\"oss-cn-shanghai.aliyuncs.com\"}";
    private static final String huawei = "{\"accessKeyId\":\"SGPKCMX0ZTGWPQHFE3IR\"," +
            "\"accessKeySecret\":\"MpqzyMpLyeJLT4iuWy6lu6kc5kiVGSYeDMbrjVpm\",\"bucketName\":\"sbc\"," +
            "\"endPoint\":\"obs.cn-north-4.myhuaweicloud.com\"}";
    private static final String tx = "{\"accessKeyId\":\"AKID2CBuSmShT3VklZ4TbeQwEH4e2DLDnuQj\"," +
            "\"accessKeySecret\":\"DF7Tr5bDerrc8OyLcvylUxBBxZw3whV3\",\"bucketName\":\"kyw-1257829190\"," +
            "\"endPoint\":\"cos.ap-beijing.myqcloud.com\",\"region\": \"ap-beijing\"}";

    private static final String resourceKey = "Test.txt";
    private static final String path = "/Users/edz/workspace/sbc-osd/src/main/resources/Test.txt";

    private static final String minio = "{\"accessKeyId\":\"fm3mpccKxSU1kAEF\"," +
            "\"accessKeySecret\":\"HUhUrAVEk5jH0YwHQlyzIRCtgfMOZNyc\",\"bucketName\":\"images\"," +
            "\"endPoint\":\"https://minio.kstore.shop\",\"region\": \"us-east-1\"}";

    private static final String jd = "{\"accessKeyId\":\"6660F4471C608AAB0029B4FE95C59E63\"," +
            "\"accessKeySecret\":\"D51420C7E3E46710B254D40AA4059DA5\",\"bucketName\":\"xian-b2b-buk-x1\"," +
            "\"endPoint\":\"s3-ipv6.cn-north-1.jdcloud-oss.com\",\"region\": \"us-east-1\"}";


    public static void main(String[] args) throws Exception {
        testAli();
        testMinIO();
        testTx();
        testHWei();
        testJd();
    }

    @SneakyThrows
    public static void testAli() {
        // 测试阿里上传
        aliUpload();
        // 测试阿里是否存在资源
        aliExists();
        // 测试阿里资源内容
        aliObject();
        // 测试阿里删除
        aliDelete();
        // 测试阿里是否存在资源
        aliExists();
    }

    @SneakyThrows
    public static void testHWei() {
        // 测试华为上传
        huaweiUpload();
        // 测试华为是否存在资源
        huaweiExist();
        // 测试华为资源内容
        huaweiObject();
        // 测试华为删除
        huaweiDelete();
        // 测试华为是否存在资源
        huaweiExist();
    }

    @SneakyThrows
    public static void testTx() {
        // 测试腾讯上传
        txUpload();
        // 测试腾讯是否存在资源
        txExist();
        // 测试腾讯资源内容
        txObject();
        // 测试腾讯删除
        txDelete();
        // 测试腾讯是否存在资源
        txExist();
    }

    @SneakyThrows
    public static void testMinIO() {
        // 测试MinIO上传
        minioUpload();
        // 测试MinIO是否存在资源
        minioExist();
        // 测试MinIO资源内容
        minioObject();
        // 测试MinIO删除
        minioDelete();
        // 测试MinIO是否存在资源
        minioExist();
    }

    @SneakyThrows
    public static void testJd() {
        // 测试京东上传
        jdUpload();
        // 测试京东是否存在资源
        jdExist();
        // 测试京东资源内容
        jdObject();
        // 测试京东删除
        jdDelete();
        // 测试京东是否存在资源
        jdExist();
    }

    public static void aliUpload() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.ALIYUN.getName())
                .context(ali)
                .build();
        OsdResource osdResource = OsdResource.builder()
                .osdResourceKey(resourceKey)
                .osdInputStream(new FileInputStream(path))
                .build();
        OsdClient.instance().putObject(osdClientParam, osdResource);
    }


    public static void aliDelete() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(resourceKey);
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.ALIYUN.getName())
                .context(ali)
                .build();
        OsdClient.instance().deleteObject(osdClientParam, list);
    }

    public static void aliExists() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.ALIYUN.getName())
                .context(ali)
                .build();
        Boolean isExist = OsdClient.instance().doesObjectExist(osdClientParam, resourceKey);
        System.out.println("阿里云是否存在资源：" + isExist);
    }

    public static void aliObject() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.ALIYUN.getName())
                .context(ali)
                .build();
        byte[] bytes = OsdClient.instance().getObject(osdClientParam, resourceKey);
        System.out.println("阿里云资源内容：" + new String(bytes));
    }


    public static void huaweiUpload() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.HUAWEIYUN.getName())
                .context(huawei)
                .build();
        OsdResource osdResource = OsdResource.builder()
                .osdResourceKey(resourceKey)
                .osdInputStream(new FileInputStream(path))
                .build();
        OsdClient.instance().putObject(osdClientParam, osdResource);
    }


    public static void huaweiDelete() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(resourceKey);
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.HUAWEIYUN.getName())
                .context(huawei)
                .build();
        OsdClient.instance().deleteObject(osdClientParam, list);
    }

    public static void huaweiExist() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.HUAWEIYUN.getName())
                .context(huawei)
                .build();
        Boolean isExist = OsdClient.instance().doesObjectExist(osdClientParam, resourceKey);
        System.out.println("华为云是否存在资源：" + isExist);
    }

    public static void huaweiObject() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.HUAWEIYUN.getName())
                .context(huawei)
                .build();
        byte[] bytes = OsdClient.instance().getObject(osdClientParam, resourceKey);
        System.out.println("华为云资源内容：" + new String(bytes));
    }

    public static void txUpload() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.TXYUN.getName())
                .context(tx)
                .build();
        OsdResource osdResource = OsdResource.builder()
                .osdResourceKey(resourceKey)
                .osdInputStream(new FileInputStream(path))
                .build();
        OsdClient.instance().putObject(osdClientParam, osdResource);
    }


    public static void txDelete() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(resourceKey);
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.TXYUN.getName())
                .context(tx)
                .build();
        OsdClient.instance().deleteObject(osdClientParam, list);
    }

    public static void txExist() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.TXYUN.getName())
                .context(tx)
                .build();
        Boolean isExist = OsdClient.instance().doesObjectExist(osdClientParam, resourceKey);
        System.out.println("腾讯云是否存在资源：" + isExist);
    }


    public static void txObject() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.TXYUN.getName())
                .context(tx)
                .build();
        byte[] bytes = OsdClient.instance().getObject(osdClientParam, resourceKey);
        System.out.println("腾讯云资源内容：" + new String(bytes));
    }


    public static void minioUpload() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.MINIO.getName())
                .context(minio)
                .build();
        OsdResource osdResource = OsdResource.builder()
                .osdResourceKey(resourceKey)
                .osdInputStream(new FileInputStream(path))
                .build();
        OsdClient.instance().putObject(osdClientParam, osdResource);
    }


    public static void minioDelete() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(resourceKey);
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.MINIO.getName())
                .context(minio)
                .build();
        OsdClient.instance().deleteObject(osdClientParam, list);
    }

    public static void minioExist() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.MINIO.getName())
                .context(minio)
                .build();
        Boolean isExist = OsdClient.instance().doesObjectExist(osdClientParam, resourceKey);
        System.out.println("minio是否存在资源：" + isExist);
    }


    public static void minioObject() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.MINIO.getName())
                .context(minio)
                .build();
        byte[] bytes = OsdClient.instance().getObject(osdClientParam, resourceKey);
        System.out.println("minio资源内容：" + new String(bytes));
    }


    public static void jdUpload() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.JDYUN.getName())
                .context(jd)
                .build();
        OsdResource osdResource = OsdResource.builder()
                .osdResourceKey(resourceKey)
                .osdInputStream(new FileInputStream(path))
                .build();
        OsdClient.instance().putObject(osdClientParam, osdResource);
    }


    public static void jdDelete() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(resourceKey);
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.JDYUN.getName())
                .context(jd)
                .build();
        OsdClient.instance().deleteObject(osdClientParam, list);
    }

    public static void jdExist() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.JDYUN.getName())
                .context(jd)
                .build();
        Boolean isExist = OsdClient.instance().doesObjectExist(osdClientParam, resourceKey);
        System.out.println("京东云是否存在资源：" + isExist);
    }


    public static void jdObject() throws Exception {
        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(OsdType.JDYUN.getName())
                .context(jd)
                .build();
        byte[] bytes = OsdClient.instance().getObject(osdClientParam, resourceKey);
        System.out.println("京东云资源内容：" + new String(bytes));
    }
}