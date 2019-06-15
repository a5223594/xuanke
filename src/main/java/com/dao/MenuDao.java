package com.dao;

import com.pojo.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends JpaRepository<Menu,String> {

    List<Menu> findMenusByPid(String pid);
}
