package cn.mccreefei.zhihu.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2018-01-22 9:56
 */
@Slf4j
public class BeanUtils {
    public static <T> T propertiesCopy(Object source, Class<T> target){
        if (source == null){
            return null;
        }
        try {
            T instance = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("复制对象属性失败！", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> collectionCopy(Collection source, Class<T> target){
        if (source == null){
            return new ArrayList<T>();
        }
        List<T> instance = new ArrayList<T>();
        for (Object o : source){
            instance.add(propertiesCopy(o, target));
        }
        return instance;
    }
}
