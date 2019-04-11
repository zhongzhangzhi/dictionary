package com.example.dictionary.filter;

import com.alibaba.fastjson.serializer.AfterFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import com.example.dictionary.common.ClassKey;
import com.example.dictionary.pojo.POJO;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

/**
 *
 * POJO序列化过滤处理基类
 *
 * SerializeFilter是通过编程扩展的方式定制序列化。ali.fastjson支持6种SerializeFilter，用于不同场景的定制序列化。
 *      AfterFilter 序列化时在最后添加内容
 * 参考博客：https://www.cnblogs.com/dirgo/p/5178629.html
 *
 * @author <a href="mailto:zhongzhangzhi@xiniaoyun.com">zhongzhangzhi(zhongzhangzhi@xiniaoyun.com)</a>
 */
public abstract class BaseExtendedAttributeFilter<T extends Annotation> extends AfterFilter {

    public BaseExtendedAttributeFilter(Class<T> filterClass) {
        this.filterClass = filterClass;
    }

    /**
     * Annotation Class
     */
    private final Class<T> filterClass;

    /**
     * 不合规的Class
     */
    private final Set<ClassKey> ignoreClassCache = new HashSet<>(256);

    /**
     * key:     new ClassKey(POJO)
     * value:   POJO中存在T注解的字段集合
     */
    private final Map<ClassKey, Field[]> fieldsCache = new ConcurrentReferenceHashMap<>(256);

    /**
     * 入口
     * @param object  序列化对象
     */
    @Override
    public void writeAfter(Object object) {
        if(object != null) {
            ClassKey classKey = new ClassKey(object.getClass());
            if(canFilter(classKey)) {
                Field[] fields = this.fieldsCache.get(classKey);
                if(fields == null) {
                    System.err.println(String.format("class '%s' has no fields to filter", classKey));
                } else {
                    doWrite(object, fields);
                }
            }
        }
    }

    /**
     * 校验合法性，并补录fieldsCache
     * @param classKey
     * @return
     */
    private Boolean canFilter(ClassKey classKey) {
        if(this.ignoreClassCache.contains(classKey)) {
            return false;
        }
        if(this.fieldsCache.containsKey(classKey)) {
            return true;
        }
        Class<?> cls = classKey.get_class();
        if(!this.support(cls)) {
            System.err.println(String.format("not support class '%s'", classKey));
            this.ignoreClassCache.add(classKey);
            return false;
        } else {
            final List<Field> fields = new ArrayList(6);
            ReflectionUtils.doWithFields(cls, field -> {
                T t = AnnotationUtils.getAnnotation(field, this.filterClass);
                if(t !=  null) {
                    ReflectionUtils.makeAccessible(field);
                    fields.add(field);
                }
            });
            if(fields.isEmpty()) {
                System.err.println(String.format("not support class '%s'", classKey));
                this.ignoreClassCache.add(classKey);
                return false;
            } else {
                this.fieldsCache.put(classKey, fields.toArray(new Field[fields.size()]));
                return true;
            }
        }
    }

    /**
     * 向Object写入数据字典字段信息
     * @param object    序列化POJO对象
     * @param fields    POJO的字段集合
     */
    private void doWrite(Object object, Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            T t = AnnotationUtils.getAnnotation(field, this.filterClass);
            Object value = ReflectionUtils.getField(field, object);
            if(value != null) {
                this.doWrite(field, value, t);
            }
        }
    }

    protected abstract void doWrite(Field field, Object value, T t);

    private Boolean support(Class<?> c) {
        return ClassUtils.isAssignable(POJO.class, c);
    }

}
