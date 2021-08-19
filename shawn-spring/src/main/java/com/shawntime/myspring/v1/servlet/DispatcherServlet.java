package com.shawntime.myspring.v1.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Splitter;
import com.shawntime.myspring.v1.annotation.Autowired;
import com.shawntime.myspring.v1.annotation.Component;
import com.shawntime.myspring.v1.annotation.Controller;
import com.shawntime.myspring.v1.annotation.Repository;
import com.shawntime.myspring.v1.annotation.RequestMapping;
import com.shawntime.myspring.v1.annotation.RequestParam;
import com.shawntime.myspring.v1.annotation.Service;
import com.shawntime.myspring.v1.exception.MySpringException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class DispatcherServlet extends HttpServlet {

    private static final String PACKAGE_SCANNER_PATH = "scanPackages";

    private static final String CONTEXT_CONFIG_LOCATION_KEY = "contextConfigLocation";

    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> iocBeanMap = new ConcurrentHashMap<>();

    private Map<String, HandlerMapping> handlerMappingMap = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 6、处理请求
        HandlerMapping handlerMapping = getHandlerMapping(req);
        if (handlerMapping == null) {
            return;
        }
        Method method = handlerMapping.getMethod();
        Object object = handlerMapping.getObject();
        Object[] argValues = getArgs(handlerMapping, req, resp);
        try {
            Object returnValue = method.invoke(object, argValues);
            if (returnValue == null || returnValue instanceof Void) {
                return;
            }
            resp.getWriter().write(returnValue.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Object[] getArgs(HandlerMapping handlerMapping,
                             HttpServletRequest req,
                             HttpServletResponse resp) {
        Class<?>[] parameterTypes = handlerMapping.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        Map<String, String[]> parameterMap = req.getParameterMap();
        Map<String, Integer> paramIndexMap = handlerMapping.getParamIndexMap();
        parameterMap.forEach((paramKey, paramValues) -> {
            if (parameterMap.containsKey(paramKey)) {
                String value = Arrays.toString(paramValues)
                        .replaceAll("\\[|\\]", "")
                        .replaceAll("\\s", "");
                int index = paramIndexMap.get(paramKey);
                Class<?> parameterType = parameterTypes[index];
                args[index] = convert(parameterType, value);
            }
            String requestName = HttpServletRequest.class.getName();
            if (paramIndexMap.containsKey(requestName)) {
                int index = paramIndexMap.get(requestName);
                args[index] = req;
            }
            String responseName = HttpServletResponse.class.getName();
            if (paramIndexMap.containsKey(responseName)) {
                int index = paramIndexMap.get(responseName);
                args[index] = resp;
            }
        });
        return args;
    }

    private Object convert(Class<?> parameterType, String value) {
        if (parameterType == String.class) {
            return value;
        }
        if (parameterType == Integer.class) {
            return Integer.parseInt(value);
        }
        if (parameterType == Long.class) {
            return Long.parseLong(value);
        }
        if (parameterType == Double.class) {
            return Double.parseDouble(value);
        }
        if (parameterType == Byte.class) {
            return Byte.parseByte(value);
        }
        if (parameterType == Short.class) {
            return Short.parseShort(value);
        }
        if (parameterType == Float.class) {
            return Float.parseFloat(value);
        }
        return value;
    }

    private HandlerMapping getHandlerMapping(HttpServletRequest req) {
        String requestUri = req.getRequestURI();
        String jumpUrl = requestUri.replace(req.getContextPath(), "").replaceAll("/+", "/");
        return handlerMappingMap.get(jumpUrl);
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 1、读取配置文件
        doLoadConfig(servletConfig);
        // 2、扫描包路径
        doScanner();
        // 3、扫描的类注册到IOC容器中
        doRegisterIoc();
        // 4、依赖注入
        doAutowired();
        // 5、初始化HandlerMapping
        doHandlerMapping();
    }

    private void doHandlerMapping() {
        iocBeanMap.forEach((beanName, bean) -> {
            Class<?> aClass = bean.getClass();
            if (aClass.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = aClass.getAnnotation(RequestMapping.class);
                String url = requestMapping.name();
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                        String jumpUrl = (url + methodRequestMapping.name()).replaceAll("/+", "/");
                        HandlerMapping handlerMapping = new HandlerMapping(bean, method, jumpUrl);
                        handlerMappingMap.put(jumpUrl, handlerMapping);
                    }
                }
            }
        });
    }

    private void doAutowired() {
        iocBeanMap.forEach((beanName, bean) -> {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    String methodName = getMethodName(field);
                    Object object = iocBeanMap.get(methodName);
                    if (object != null) {
                        field.setAccessible(true);
                        try {
                            field.set(bean, object);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private String getMethodName(Field field) {
        Autowired autowired = field.getAnnotation(Autowired.class);
        String methodName = autowired.value();
        if (StringUtils.isNotEmpty(methodName)) {
            return methodName;
        }
        return toLowerFirstCase(field.getName());
    }

    private void doRegisterIoc() {
        if (CollectionUtils.isEmpty(classNames)) {
            return;
        }
        classNames.forEach(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                registerIoc(clazz);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerIoc(Class<?> clazz) throws IllegalAccessException, InstantiationException {

        if (clazz.isAnnotationPresent(Controller.class)) {
            Controller controller = clazz.getAnnotation(Controller.class);
            String beanName = controller.value();
            diBean(clazz, beanName);
        }
        if (clazz.isAnnotationPresent(Service.class)) {
            Service service = clazz.getAnnotation(Service.class);
            String beanName = service.value();
            diBean(clazz, beanName);
        }
        if (clazz.isAnnotationPresent(Repository.class)) {
            Repository repository = clazz.getAnnotation(Repository.class);
            String beanName = repository.value();
            diBean(clazz, beanName);
        }
        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = clazz.getAnnotation(Component.class);
            String beanName = component.value();
            diBean(clazz, beanName);
        }
    }

    private void diBean(Class<?> clazz, String beanName) throws InstantiationException, IllegalAccessException {
        if (StringUtils.isEmpty(beanName)) {
            beanName = toLowerFirstCase(clazz.getSimpleName());
        }
        Object object = clazz.newInstance();
        iocBeanMap.put(beanName, object);
    }

    private String toLowerFirstCase(String simpleName) {
        char chat = simpleName.charAt(0);
        if (Character.isLowerCase(chat)) {
            return simpleName;
        } else {
            return (new StringBuilder())
                    .append(Character.toLowerCase(chat))
                    .append(simpleName.substring(1)).toString();
        }
    }

    private void doScanner() {
        String packagePaths = properties.getProperty(PACKAGE_SCANNER_PATH);
        if (StringUtils.isEmpty(packagePaths)) {
            return;
        }
        Splitter.on(",")
                .trimResults().omitEmptyStrings().split(packagePaths)
                .forEach(packagePath -> packageScanner(packagePath));
    }

    private void packageScanner(String packageName) {
        // packageName = com.shawntime.spring
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                packageScanner(packageName + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = packageName + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    private void doLoadConfig(ServletConfig servletConfig) {
        String configName = servletConfig.getInitParameter(CONTEXT_CONFIG_LOCATION_KEY);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new MySpringException("读取配置信息异常");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new MySpringException("读取配置信息异常");
                }
            }
        }
    }

    private static class HandlerMapping {

        private Object object;

        private Method method;

        private String url;

        private Map<String, Integer> paramIndexMap;

        private Class<?>[] parameterTypes;


        public HandlerMapping(Object object, Method method, String url) {
            this.object = object;
            this.method = method;
            this.url = url;
            parameterTypes = method.getParameterTypes();
            paramIndexMap = initParamIndexMap();
        }

        public Map<String, Integer> initParamIndexMap() {
            Map<String, Integer> paramIndexMap = new HashMap<>();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; ++i) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation instanceof RequestParam) {
                        RequestParam requestParam = (RequestParam) annotation;
                        String paramName = requestParam.value();
                        if (StringUtils.isNotEmpty(paramName)) {
                            paramIndexMap.put(paramName, i);
                        }

                    }
                }
            }
            for (int i = 0; i < parameterTypes.length; ++i) {
                Class<?> clazz = parameterTypes[i];
                if (clazz == HttpServletRequest.class || clazz == HttpServletResponse.class) {
                    paramIndexMap.put(clazz.getName(), i);
                }
            }
            return paramIndexMap;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setParamIndexMap(Map<String, Integer> paramIndexMap) {
            this.paramIndexMap = paramIndexMap;
        }

        public void setParameterTypes(Class<?>[] parameterTypes) {
            this.parameterTypes = parameterTypes;
        }

        public Object getObject() {
            return object;
        }

        public Method getMethod() {
            return method;
        }

        public String getUrl() {
            return url;
        }

        public Map<String, Integer> getParamIndexMap() {
            return paramIndexMap;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }
    }
}
