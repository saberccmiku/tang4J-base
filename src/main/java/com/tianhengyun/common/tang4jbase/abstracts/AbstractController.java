package com.tianhengyun.common.tang4jbase.abstracts;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianhengyun.common.tang4jbase.support.RequestPage;
import com.tianhengyun.common.tang4jbase.support.ResponseModel;
import com.tianhengyun.common.tang4jbase.support.ResponseModelFactory;
import com.tianhengyun.common.tang4jbase.utils.QueryWrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by saber on 2019/10/26.
 * 基础controller
 */
public class AbstractController<M extends AbstractService<T>, T extends AbstractModel> implements InitializingBean {

    public M service;

    public AbstractController(M m) {
        this.service = m;
    }

    private final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public void afterPropertiesSet() throws Exception {

        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object object = field.get(this);
                Class<?> cls = field.getType();
                if (object == null && cls.getSimpleName().toLowerCase().contains("service" )) {
                    //v = ApplicationContextHolder.getService(cls);
                    field.set(this, object);
                }
                field.setAccessible(false);
            }

        } catch (Exception ex) {
            logger.error("", ex);
            this.afterPropertiesSet();
        }


    }

    @PostMapping("/insert")
    public ResponseModel iInsert(@RequestBody T t) {
        try {
            return ResponseModelFactory.OKWithData(this.service.save(t));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @DeleteMapping("/{id}" )
    public ResponseModel iDelete(@PathVariable("id" ) @RequestBody Serializable id) {
        try {
            return ResponseModelFactory.OKWithData(this.service.removeById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @GetMapping("/{id}" )
    public ResponseModel iDetail(@PathVariable("id" ) @RequestBody Serializable id) {
        try {
            return ResponseModelFactory.OKWithData(this.service.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @PutMapping("/update")
    public ResponseModel iUpdate(@RequestBody T t) {
        try {
            return ResponseModelFactory.OKWithData(this.service.updateById(t));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @PostMapping("/insertBatch" )
    public ResponseModel iInsertBatch(@RequestBody List<T> ts) {
        try {
            return ResponseModelFactory.OKWithData(this.service.saveBatch(ts));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @DeleteMapping("/deleteBatch" )
    public ResponseModel iDeleteBatch(@RequestBody List<Serializable> ids) {
        try {
            return ResponseModelFactory.OKWithData(this.service.removeByIds(ids));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @GetMapping("/selectBatch" )
    public ResponseModel iSelectBatch(@RequestBody List<Serializable> ids) {
        try {
            return ResponseModelFactory.OKWithData(this.service.listByIds(ids));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @PutMapping("/updateBatch" )
    public ResponseModel iUpdateBatch(@RequestBody List<T> ts) {
        try {
            return ResponseModelFactory.OKWithData(this.service.updateBatchById(ts));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @GetMapping("/list" )
    public ResponseModel iList(@RequestBody T t) {
        try {
            return ResponseModelFactory.OKWithData(this.service.list(QueryWrapperUtil.initNormalQuerySql(t)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModelFactory.error(e.getMessage());
        }
    }


    @GetMapping("/pageList")
    public ResponseModel iPageList(@RequestBody RequestPage<T> request) {
        try {
            IPage<T> page = new Page<>(request.getCurrent(), request.getSize());
            if (!CollectionUtils.isEmpty(request.getOrderItems())) {
                page.orders().addAll(request.getOrderItems());
            }
            return ResponseModelFactory.OKWithData(this.service.page(page, QueryWrapperUtil.initFuzzyQuerySql(request.getData())));
        } catch (Exception e) {
            logger.error(this.getClass().getName(), e);
            return ResponseModelFactory.error(e.getMessage());
        }
    }


}
