package com.shawntime.architect.notes.jvm.classloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 */
public class MyClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String classPath = "D://data/";
        MyClassLoader myClassLoader = new MyClassLoader(classPath);
        Class<?> clazz = myClassLoader.loadClass("com.shawntime.architect.notes.jvm.classloader.TestJDKClassLoader");
        if (clazz != null) {
            // com.shawntime.architect.notes.jvm.classloader.MyClassLoaderTest$MyClassLoader@74a14482
            System.out.println(clazz.getClassLoader());

            Object object = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("print", Class.class);
            /**
             * com.shawntime.architect.notes.jvm.classloader.MyClassLoaderTest$MyClassLoader@74a14482
             * sun.misc.Launcher$AppClassLoader@18b4aac2
             * sun.misc.Launcher$ExtClassLoader@677327b6
             * null
             */
            method.invoke(object, clazz);
        }

    }

    static class MyClassLoader extends ClassLoader {

        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            String fileName = name.replaceAll("\\.", "/");
            String path = classPath + fileName + ".class";
            byte[] classBytes = getClassBytes(path);
            return defineClass(name, classBytes, 0, classBytes.length);
        }

        private byte[] getClassBytes(String path) {
            try (FileInputStream fileInputStream = new FileInputStream(path)) {
                int len = fileInputStream.available();
                byte[] data = new byte[len];
                fileInputStream.read(data);
                fileInputStream.close();
                return data;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getClassPath() {
            return classPath;
        }

        public void setClassPath(String classPath) {
            this.classPath = classPath;
        }
    }
}
