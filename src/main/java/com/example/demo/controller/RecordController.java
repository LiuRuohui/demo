package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Goods;
import com.example.demo.entity.Record;
import com.example.demo.entity.Storage;
import com.example.demo.service.GoodsService;
import com.example.demo.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuruohui
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private GoodsService goodsService;
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String goodsType = (String)param.get("goodsType");
        String storage = (String)param.get("storage");
        String roleId = (String)param.get("roleId");
        String userId = (String)param.get("userId");

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.apply(" a.goods=b.id and b.storage=c.id and b.goodsType=d.id ");

        if("2".equals(roleId)){
            // queryWrapper.eq(Record::getUserid,userId);
            queryWrapper.apply(" a.userId= "+userId);
        }

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like("b.name",name);
        }
        if(StringUtils.isNotBlank(goodsType) && !"null".equals(goodsType)){
            queryWrapper.eq("d.id",goodsType);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.eq("c.id",storage);
        }

        IPage result = recordService.pageCC(page,queryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        Goods goods = goodsService.getById(record.getGoods());
        int n = record.getCount();
        //出库
        if("2".equals(record.getAction())){
            n = -n;
            record.setCount(n);
        }

        int num = goods.getCount()+n;
        goods.setCount(num);
        goodsService.updateById(goods);

        return recordService.save(record)?Result.suc():Result.fail();
    }

}
