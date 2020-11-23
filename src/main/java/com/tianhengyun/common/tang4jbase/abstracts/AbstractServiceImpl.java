package com.tianhengyun.common.tang4jbase.abstracts;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianhengyun.common.tang4jbase.multiple.Multiple;
import com.tianhengyun.common.tang4jbase.utils.QueryWrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by saber on 2019/10/26.
 * 基础service实现类
 */
public class AbstractServiceImpl<M extends AbstractMapper<T>, T extends AbstractModel> extends ServiceImpl<M, T> implements AbstractService<T> {

    @Autowired
    private M mapper;


    @Override
    public IPage<T> normalQuery(IPage<T> var1, T t) {
        return mapper.selectPage(var1, QueryWrapperUtil.initNormalQuerySql(t));
    }

    @Override
    public List<T> normalQuery(T t) {
        return mapper.selectList(QueryWrapperUtil.initNormalQuerySql(t));
    }

    @Override
    public IPage<T> fuzzyQuery(IPage<T> var1, T t) {
        return mapper.selectPage(var1, QueryWrapperUtil.initFuzzyQuerySql(t));
    }

    @Override
    public List<T> fuzzyQuery(T t) {
        return mapper.selectList(QueryWrapperUtil.initFuzzyQuerySql(t));
    }

    @Override
    public IPage<T> multipleSelectPage(IPage<T> page, Multiple... multiples) {
        return mapper.multipleSelectPage(page, multiples);
    }

    @Override
    public List<T> multipleSelect(Multiple... multiples) {
        return mapper.multipleSelect(multiples);
    }


}
