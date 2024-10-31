package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuruohui
 * @since 2023-06-02
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage pageCC(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
