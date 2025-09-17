package com.wanmi.sbc.convert;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.KsBeanUtil;
import lombok.Data;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanggaolei
 * @className Fastjson2BenchMark
 * @description
 * @date 2022/8/5 14:47
 **/
public class Fastjson2Benchmark {

    private static final List<Student> objList;
    private static final List<String> strList;
    private static final String source;

    static {
        objList = new ArrayList<>(100000);
        strList = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            Student student = new Student("学生姓名" + i, i % 10, "黑龙江省哈尔滨市南方区哈尔滨大街267号" + i);
            objList.add(student);
            strList.add(JSON.toJSONString(student));
        }
        source = JSON.toJSONString(objList);
    }

    public static void main(String[] args) throws RunnerException {
        //        Options opt = new OptionsBuilder()
        //                .include(Fastjson2Benchmark.class.getSimpleName())
        //                .warmupIterations(5)
        //                .measurementIterations(5)
        //                .forks(1)
        //                .build();
        //
        //        new Runner(opt).run();
//        Student student = objList.get(0);
//        student.setStudents(objList.subList(2,5));
//        System.out.println(com.alibaba.fastjson2.JSON.toJSONString(student));
//        System.out.println(com.alibaba.fastjson2.JSON.toJSONString(student,JSONWriter.Feature.ReferenceDetection));
//
//        System.out.println(JSON.toJSONString(student));
//        System.out.println(JSON.toJSONString(student, SerializerFeature.DisableCircularReferenceDetect));
//
//        StudentVO studentVO = KsBeanUtil.convert(student,StudentVO.class);
//        System.out.println(JSON.toJSONString(studentVO));
//        System.out.println(JSON.toJSONString(studentVO,SerializerFeature.DisableCircularReferenceDetect));


        Map<String, Object> dataMap = new HashMap<>();
        List<StudentVO> userList1 = new ArrayList<>();
        List<StudentVO> userList2 = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            StudentVO user = new StudentVO();
            user.setName(""+i);
            user.setDateTime(LocalDateTime.parse("2022-08-08T16:30:35.073"));
            user.setNoTime(LocalDateTime.now());
            userList1.add(user);
            userList2.add(user);
        }
        dataMap.put("userList1", userList1);
        dataMap.put("userList2", userList2);

        System.out.println(JSON.toJSONString(dataMap));
        System.out.println(com.alibaba.fastjson2.JSON.toJSONString(dataMap));
        System.out.println(JSON.toJSONString(dataMap));
        System.out.println(com.alibaba.fastjson2.JSON.toJSONString(dataMap,JSONWriter.Feature.ReferenceDetection));

        Student student = KsBeanUtil.convert(userList1.get(0),Student.class);

        System.out.println(JSON.toJSONString(student));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void fastJSON1ObjSeThroughput() {
        for (Student student : objList) {
            JSON.toJSONString(student);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void fastJSON1ObjDeThroughput() {
        for (String student : strList) {
            JSON.parseObject(student, Student.class);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void fastJSON2ObjSeThroughput() {
        for (Student student : objList) {
            com.alibaba.fastjson2.JSON.toJSONString(student);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void fastJSON2ObjDeThroughput() {
        for (String student : strList) {
            com.alibaba.fastjson2.JSON.parseObject(student, Student.class);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void fastJSON1ArraySeThroughput() {
        JSON.toJSONString(objList);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void fastJSON1ArrayDeThroughput() {
        JSON.parseArray(source, Student.class);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void fastJSON2ArraySeThroughput() {
        com.alibaba.fastjson2.JSON.toJSONString(objList, JSONWriter.Feature.ReferenceDetection);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void fastJSON2ArrayDeThroughput() {
        com.alibaba.fastjson2.JSON.parseArray(source, Student.class);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fastJSON1ObjSeTime() {
        for (Student student : objList) {
            JSON.toJSONString(student);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fastJSON1ObjDeTime() {
        for (String student : strList) {
            JSON.parseObject(student, Student.class);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fastJSON2ObjSeTime() {
        for (Student student : objList) {
            com.alibaba.fastjson2.JSON.toJSONString(student);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fastJSON2ObjDeTime() {
        for (String student : strList) {
            com.alibaba.fastjson2.JSON.parseObject(student, Student.class);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fastJSON1ArraySeTime() {
        JSON.toJSONString(objList);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fastJSON1ArrayDeTime() {
        JSON.parseArray(source, Student.class);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fastJSON2ArraySeTime() {
        com.alibaba.fastjson2.JSON.toJSONString(objList, JSONWriter.Feature.ReferenceDetection);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void fastJSON2ArrayDeTime() {
        com.alibaba.fastjson2.JSON.parseArray(source, Student.class);
    }

    @Data
    private static class Student {
        private String name;
        private int age;
        private String address;
        private List<Student> students;
        private LocalDateTime dateTime;
        public Student() {
        }

        public Student(String name, int age, String address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    @Data
    private static class StudentVO {
        private String name;
        private String address;

        @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        private LocalDateTime dateTime;

        private LocalDateTime noTime;

        private String six;

        private List<Student> students;
    }
}
