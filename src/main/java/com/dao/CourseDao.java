package com.dao;

import com.pojo.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao extends JpaRepository<Course,String> {


    @Query(value = "select course.* from sc join course on sc.courseid = course.id where sc.studentid = ?1",nativeQuery = true)
    List<Course> getMyCourseByStudentId(String id);

    @Query(value = "select * from course join al on course.id = al.courseid where course.teacher = ?1 and al.academy = ?2",nativeQuery = true)
    List<Course> getCoursesByTeacherAndAcademy(String teacher,String academy);

    List<Course> getCoursesByTeacherContaining(String teacher);

    @Query(value = "select * from course join al on course.id = al.courseid where al.academy = ?1",nativeQuery = true)
    List<Course> getCoursesByAcademy(String academy);

}
