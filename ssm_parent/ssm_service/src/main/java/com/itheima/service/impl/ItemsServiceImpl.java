package com.itheima.service.impl;

import com.itheima.dao.ItemsDao;
import com.itheima.domain.Items;
import com.itheima.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemsServiceImpl implements ItemsService {
    @Autowired
    ItemsDao itemsDao;

    public List<Items> findAll() {
        List<Items> list = itemsDao.findAll();
        return list;
    }

    public int save(Items items) {
        int rows = itemsDao.save(items);
        return rows;
    }
}
