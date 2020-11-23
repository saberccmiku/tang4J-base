package com.tianhengyun.common.tang4jbase.abstracts;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tianhengyun.common.tang4jbase.multiple.Multiple;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by saber on 2019/10/26.
 * 基础service类
 */
public interface AbstractService<T extends AbstractModel> extends IService<T> {


    /**
     * 分页的条件查询 key = val模式
     *
     * @param var1 分页器
     * @param t    可反射对象 其非final修饰的成员变量所对应的的条件数据作为串联条件
     * @return 分页数据
     */
    IPage<T> normalQuery(IPage<T> var1, T t);

    /**
     * 分页的条件查询 key = val模式
     *
     * @param t 可反射对象 其非final修饰的成员变量所对应的的条件数据作为串联条件
     * @return 分页数据
     */
    List<T> normalQuery(T t);

    /**
     * 分页的模糊查询
     *
     * @param var1 分页器
     * @param t    可反射对象 其非final修饰的成员变量所对应的的模糊数据作为串联条件
     * @return 分页数据
     */
    IPage<T> fuzzyQuery(IPage<T> var1, T t);

    /**
     * 模糊查询
     *
     * @param t 可反射对象 其非final修饰的成员变量所对应的的模糊数据作为串联条件
     * @return 分页数据
     */
    List<T> fuzzyQuery(T t);

    /**
     * 联表查询分页
     *
     * @param page      IPage分页器
     * @param multiples 联表器
     * @return 结果集
     */
    IPage<T> multipleSelectPage(IPage<T> page, @Param("multiples") Multiple... multiples);

    /**
     * 联表查询
     *
     * @param multiples 联表器
     * @return 结果集
     */
    List<T> multipleSelect(@Param("multiples") Multiple... multiples);
}
