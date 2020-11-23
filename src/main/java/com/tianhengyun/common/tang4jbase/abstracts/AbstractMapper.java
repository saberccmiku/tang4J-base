package com.tianhengyun.common.tang4jbase.abstracts;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tianhengyun.common.tang4jbase.multiple.Multiple;
import com.tianhengyun.common.tang4jbase.multiple.MultipleSelect;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by saber on 2019/10/26
 * 基础Mapper类
 */
public interface AbstractMapper<T extends AbstractModel> extends BaseMapper<T> {

    /**
     * 联表查询分页
     * @param page IPage分页器
     * @param multiples 联表器
     * @return 结果集
     */
    @SelectProvider(type = MultipleSelect.class, method = "createMultipleSql")
    IPage<T> multipleSelectPage(IPage<T> page, @Param("multiples") Multiple... multiples);

    /**
     * 联表查询
     * @param multiples 联表器
     * @return 结果集
     */
    @SelectProvider(type = MultipleSelect.class, method = "createMultipleSql")
    List<T> multipleSelect(@Param("multiples") Multiple... multiples);

}
