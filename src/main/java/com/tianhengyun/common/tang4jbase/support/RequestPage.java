package com.tianhengyun.common.tang4jbase.support;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.List;
import java.util.Objects;

/**
 * 入参分页
 *
 * @param <T> 对应数据库的实体类
 */
public class RequestPage<T> {

    private Integer size = 10;
    private Integer current = 1;
    private List<OrderItem> orderItems;
    private T data;

    public T getData() {
        if (Objects.isNull(data))
            throw new RuntimeException("Incorrect format,it must use {\"data\":\"\"}");
        return data;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setData(T data) {
        this.data = data;
    }
}
