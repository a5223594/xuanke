package com.dao;

import com.pojo.Al;
import com.pojo.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlDao extends JpaRepository<Al,String> {

    Al findAlByAcademyAndCourse(String academy, Course course);
}
