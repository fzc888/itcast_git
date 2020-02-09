package com.itheima.dao;



import com.itheima.domain.Items;

import java.util.List;

public interface ItemsDao {
    //查询所有
    List<Items> findAll();

    //保存操作
    int save(Items items);
}
