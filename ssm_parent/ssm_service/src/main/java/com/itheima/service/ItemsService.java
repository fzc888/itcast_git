package com.itheima.service;

import com.itheima.domain.Items;

import java.util.List;

public interface ItemsService {
    //查询所有
    List<Items> findAll();

    //保存操作
    int save(Items items);
}
