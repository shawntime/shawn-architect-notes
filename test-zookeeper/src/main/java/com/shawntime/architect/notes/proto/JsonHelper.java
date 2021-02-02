package com.shawntime.architect.notes.proto;

import java.lang.reflect.Type;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by zhouxiaoming on 2015/9/4.
 * 利用fastjon序列化和反序列化
 */
public final class JsonHelper {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private JsonHelper() {
        // Utility classes should always be final and have a private constructor
    }

    public static <T> String serialize(T object) {
        JSON.DEFFAULT_DATE_FORMAT = DEFAULT_DATE_FORMAT;
        return serialize(object, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
    }

    public static <T> String serialize(T object, SerializerFeature... features) {
        return JSON.toJSONString(object, features);
    }

    public static <T> T deSerialize(String string, Class<T> clazz) {
        return JSON.parseObject(string, clazz);
    }

    public static <T> T deSerialize(String string, Type type) {
        return JSON.parseObject(string, type);
    }

    public static <T> List<T> deSerializeList(String string, Class<T> clazz) {
        return JSON.parseArray(string, clazz);
    }

}
