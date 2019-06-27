package com.service;

import com.dao.AlDao;
import com.pojo.Al;
import com.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlService {

    @Autowired
    private AlDao alDao;

    public Al getAl(String academy, Course course) {
        return alDao.findAlByAcademyAndCourse(academy, course);
    }

    public List<Al> findAll(){
        return alDao.findAll();
    }

}
