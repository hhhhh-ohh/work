package com.wanmi.sbc.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

/***
 * 反射工具类
 * 主要用于获得一个接口下所有的实现类（同包查找）
 * @className ReflectionsUtils
 * @author zhengyang
 * @date 2021/11/1 20:00
 **/
@Slf4j
public class ReflectionsUtils {

    /***
     * 获取所有接口的实现类
     * @param clazz 需要扫描的接口
     * @return      当前包下，接口所有的实现类
     */
    public static List<Class> getAllInterfaceAchieveClass(Class clazz){
        // 断言参数不为空
        Assert.notNull(clazz, ()-> "getAllInterfaceAchieveClass params can't be null!");
        // 断言参数必须为一个接口
        Assert.isTrue(clazz.isInterface(), ()-> "getAllInterfaceAchieveClass params must a interface !");

        List<Class> allClsList = Lists.newArrayList();
        try {
            List<Class> allClass = getAllClassByPath(clazz.getPackage().getName());
            // 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
            if(!allClass.isEmpty()) {
                for (Class cls : allClass) {
                    // 排除抽象类
                    if(!Modifier.isAbstract(cls.getModifiers())
                            && clazz.isAssignableFrom(cls)
                            && !clazz.equals(cls)) {
                            allClsList.add(cls);
                    }
                }
            }
        } catch (Exception e) {
            log.error("ReflectionsUtils => getAllInterfaceAchieveClass is error! ", e);
        }
        return allClsList;
    }

    /**
     * 从指定路径下获取所有类
     * @param packageName   指定包名路径
     * @return              所有类对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Class> getAllClassByPath(String packageName) throws IOException {
        List<Class> list = Lists.newArrayList();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        List<File> fileList = Lists.newArrayList();
        Enumeration<URL> enumeration = classLoader.getResources(path);
        while (enumeration.hasMoreElements()) {
            fileList.add(new File(enumeration.nextElement().getFile()));
        }
        if (!fileList.isEmpty()) {
            for (File file : fileList) {
                list.addAll(findClass(file, packageName));
            }
        }
        return list;
    }

    /***
     * 查找文件夹下所有类文件
     * 递归查找文件夹
     * @param file          单个文件对象
     * @param packageName   包名
     * @return              找到的类集合
     * @throws ClassNotFoundException
     */
    private static List<Class> findClass(File file, String packageName)  {
        List<Class> list = Lists.newArrayList();
        if (!file.exists()) {
            return list;
        }
        File[] files = file.listFiles();
        for (File childFile : files) {
            if (childFile.isDirectory()) {
                // 添加断言用于判断
                Assert.isTrue(!childFile.getName().contains("."), ()-> "directory can't support . char !");
                // 如果是文件夹，递归查找所有类放入集合
                list.addAll(findClass(childFile, packageName+"."+childFile.getName()));
            }else if(childFile.getName().endsWith(".class")){
                String className = packageName + '.' + childFile.getName().substring(0,
                        childFile.getName().length()-6);
                // 保存的类文件不需要后缀.class
                try {
                    list.add(Class.forName(className));
                } catch (Exception e) {
                    log.warn("ReflectionsUtils => findClass load class error, className is{} , error=> ", className, e);
                }
            }
        }
        return list;
    }
}
