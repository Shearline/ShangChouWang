package com.jimmy.crowd.service.api;

import com.jimmy.crowd.entity.Menu;

import java.util.List;

public interface MenuService {

    public List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
