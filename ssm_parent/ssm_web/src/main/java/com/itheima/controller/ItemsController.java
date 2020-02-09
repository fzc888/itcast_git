package com.itheima.controller;

import com.itheima.domain.Items;
import com.itheima.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/items")
public class ItemsController {

    @Autowired
    ItemsService itemsService;

    //查询所有
    @RequestMapping(value = "/list")
    public String findAll(Model model){
        List<Items> list = itemsService.findAll();
        model.addAttribute("items",list);
        return "items";//会走视图解析器
    }
    //保存
    @RequestMapping(value = "/save")
    public String save(Items items){
        int rows=itemsService.save(items);
        //保存成功
        if (rows>0){
            System.out.println("保存成功");
        }
        //重定向
        return "redirect:/items/list";
    }
}
