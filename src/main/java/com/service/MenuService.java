package com.service;

import com.dao.MenuDao;
import com.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    public List<Menu> getMenus(String pid) {
        return menuDao.findMenusByPid(pid);
    }
}
