package com.zyq.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class BeanCopyUtils {
    public static <T> T copyBean(Object source, Class<T> target) {
        T result = null;
        if (!Objects.isNull(source)) {
            try {
                result = target.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(source, result);
        }
        return result;
    }

    public static <S, T> List<T> copyBeanList(List<S> source, Class<T> target) {
        if (!Objects.isNull(source) && source.size() > 0) {
            List<T> resultList = source
                    .stream()
                    .map(i -> copyBean(i, target))
                    .collect(Collectors.toList());
            return resultList;
        }
        return null;
    }


}
