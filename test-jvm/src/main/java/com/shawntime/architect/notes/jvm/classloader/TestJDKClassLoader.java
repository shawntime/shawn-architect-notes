package com.shawntime.architect.notes.jvm.classloader;

public class TestJDKClassLoader {

    /**
     * null
     * sun.misc.Launcher$ExtClassLoader
     * sun.misc.Launcher$AppClassLoader@18b4aac2
     * sun.misc.Launcher$AppClassLoader
     */
    public static void main(String[] args) {
        // 启动类加载器：由C++实现，classLoader为null
        System.out.println(String.class.getClassLoader());
        // 扩展类加载器：sun.misc.Launcher$ExtClassLoader
        System.out.println(com.sun.crypto.provider.DESKeyFactory.class.getClassLoader().getClass().getName());
        // 应用类加载器：sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(TestJDKClassLoader.class.getClassLoader());
        // 应用类加载器
        System.out.println(ClassLoader.getSystemClassLoader().getClass().getName());

        ClassLoader classLoader = TestJDKClassLoader.class.getClassLoader();
        printClassLoader(classLoader);

    }

    /**
     * sun.misc.Launcher$AppClassLoader@18b4aac2
     * sun.misc.Launcher$ExtClassLoader@7ea987ac
     * null
     */
    private static void printClassLoader(ClassLoader classLoader) {
        System.out.println("打印类的classLoader...");
        System.out.println(classLoader);
        do {
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        } while (classLoader != null);
    }

    public void print(Class<?> clazz) {
        printClassLoader(clazz.getClassLoader());
    }
}
