package com.controller;

import com.pojo.Al;
import com.pojo.Course;
import com.pojo.Student;
import com.service.CourseService;
import com.service.StudentService;
import com.status.Result;
import com.status.StatusCode;
import com.util.UpdateTool;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findCourseById(@PathVariable String id){
        Optional<Course> optional = courseService.getCourseById(id);
        return optional.map(course -> new Result(true, StatusCode.OK, "查询成功", course))
                .orElse(new Result(false, StatusCode.ERROR, "查询失败"));
    }

    /**
     * 选课
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectCourse/{id}",method = RequestMethod.GET)
    public Result selectCourse(@PathVariable String id){

        Claims claims = (Claims) request.getAttribute("student_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        String studentid = claims.getId();
        Student student = studentService.getStudentById(studentid).get();
        //todo 选课，没判断学院限制，年级限制，人数限制，添加人数
        Course course = courseService.getCourseById(id).get();
        course.getStudents().add(student);
        courseService.updateCourse(course);
        return new Result(true, StatusCode.OK, "选课成功");
    }


    /**
     * 获取学号为id的选课情况
     * @return
     */
    @RequestMapping(value = "/mycourse")
    public Result getMyCourse(){
        Claims claims = (Claims) request.getAttribute("student_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        String id = claims.getId();
        List<Course> courses = courseService.getMyCourseById(id);
        if(courses.size()>0)
            return new Result(true, StatusCode.OK,"我的选课情况",courses);
        else{
            return new Result(false,StatusCode.ERROR,"选课为空");
        }
    }

    /**
     * 根据学院和老师查询
     * @param map
     * @return
     */
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public Result getCoursesLike(@RequestBody Map<String,String> map) {
        String teacher = map.get("teacher");
        String academy = map.get("academy");
        List<Course> courses;
        if(StringUtils.isNotBlank(teacher)&&StringUtils.isNotBlank(academy)){
            courses = courseService.getCoursesByTeacherAndAcademy(teacher,academy);
        }else if(StringUtils.isNotBlank(teacher)){
            courses = courseService.getCoursesByTeacher(teacher);
        }else if(StringUtils.isNotBlank(academy)){
            courses = courseService.getCoursesByAcademy(academy);
        }else{
            courses = courseService.getAllCourse();
        }
        return new Result(true,StatusCode.OK,"查询成功",courses);
    }

    /**
     * 查询选课情况
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result getAllCourse() {
        Claims claims = (Claims) request.getAttribute("admin_claims");

        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        List<Course> courses = courseService.getAllCourse();
        return new Result(true,StatusCode.OK,"查询成功", courses);
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result addCourse(@RequestBody Course course) {
        Claims claims = (Claims) request.getAttribute("admin_claims");

        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        Optional<Course> optional = courseService.getCourseById(course.getId());
        if(optional.isPresent()){
            return new Result(false,StatusCode.ERROR,"添加失败");
        }else{
            //级联保存，al里应该设置course,不然courseid为null
            List<Al> als = course.getAls();
            als.forEach(i->{
                i.setCourse(course);
            });
            courseService.addCourse(course);
            return new Result(true, StatusCode.OK, "添加成功");
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result updateCourse(@RequestBody Course course) {
        Claims claims = (Claims) request.getAttribute("admin_claims");

        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        //更新数据，注意没传入的字段不能保存为空
        String id =course.getId();
        Optional<Course> optional =courseService.getCourseById(id);
        if(id==null){
            return new Result(false,StatusCode.ERROR,"课程编号不能为空");
        }else if(!optional.isPresent()){
            return new Result(false,StatusCode.ERROR,"课程不存在");
        }else{
            UpdateTool.copyNullProperties(optional.get(),course);
            //更新al时，应传入id
            List<Al> als = course.getAls();
            als.forEach(i->{
                i.setCourse(course);
            });
            courseService.updateCourse(course);
            return new Result(true, StatusCode.OK, "更新成功");
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteCourse(@PathVariable String id){
        Claims claims = (Claims) request.getAttribute("admin_claims");

        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        courseService.deleteCourse(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


}
