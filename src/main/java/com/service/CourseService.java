package com.service;

import com.dao.CourseDao;
import com.pojo.Course;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseDao courseDao;


    public List<Course> getMyCourseById(String id) {
        return courseDao.getMyCourseByStudentId(id);
    }

    public List<Course> getAllCourse() {
        return courseDao.findAll();
    }

    public List<Course> getCoursesByTeacherAndAcademy(String teacher,String academy){
        return courseDao.getCoursesByTeacherAndAcademy(teacher,academy);
    }

    public List<Course> getCoursesByTeacher(String teacher){
        return courseDao.getCoursesByTeacherContaining(teacher);
    }

    public List<Course> getCoursesByAcademy(String academy){
        return courseDao.getCoursesByAcademy(academy);
    }

    public Optional<Course> getCourseById(String id) {
        return courseDao.findById(id);
    }
    @Transactional
    public void addCourse(Course course) {
        courseDao.save(course);
    }

    @Transactional
    public void deleteCourse(String id){
        courseDao.deleteById(id);
    }

    @Transactional
    public void updateCourse(Course course) {
        courseDao.save(course);
    }
}
