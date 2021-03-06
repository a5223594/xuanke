package com.controller;

import com.dao.AlDao;
import com.pojo.Al;
import com.pojo.Course;
import com.pojo.Student;
import com.service.AlService;
import com.service.CourseService;
import com.service.StudentService;
import com.status.PageResult;
import com.status.Result;
import com.status.StatusCode;
import com.util.UpdateTool;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AlService alService;

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
     * @param id 课程id
     * @return
     */
    @RequestMapping(value = "/selectCourse/{id}",method = RequestMethod.GET)
    public Result selectCourse(@PathVariable String id){

        Claims claims = (Claims) request.getAttribute("student_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        Course course = courseService.getCourseById(id).get();
        //人数限制
        if(course.getSelected()+1>course.getNumber()){
            return new Result(false, StatusCode.ACCESSERROR, "选课人数已满");
        }
        String studentid = claims.getId();
        Student student = studentService.getStudentById(studentid).get();
        for (Student student1 : course.getStudents()) {
            if(student1.getId().equals(studentid)){
                return new Result(false, StatusCode.ERROR, "不能重复选课");
            }
        }
        //年级限制
        if(student.getGrade().equals(course.getGrade())){
            List<Al> als = course.getAls();//学院限制
            boolean flag = false;
            for (Al al : als) {
                if(al.getAcademy().equals(student.getAcademy())){
                    flag = true;
                }
            }
            if (flag) {
                course.getStudents().add(student);
                course.setSelected(course.getSelected()+1);
                courseService.updateCourse(course);
                return new Result(true, StatusCode.OK, "选课成功");
            }
        }
        return new Result(false, StatusCode.ERROR, "选课失败");
    }

    /**
     * 退课
     * @param id 课程id
     * @return
     */
    @RequestMapping(value = "/dropCourse/{id}",method = RequestMethod.GET)
    public Result dropCourse(@PathVariable String id){
        Claims claims = (Claims) request.getAttribute("student_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        Course course = courseService.getCourseById(id).get();
        String studentid = claims.getId();
        for (Student student : course.getStudents()) {
            if(student.getId().equals(studentid)){
                course.getStudents().remove(student);
                course.setSelected(course.getSelected()-1);
                courseService.updateCourse(course);
                return new Result(true, StatusCode.OK, "退课成功");
            }
        }
        return new Result(false, StatusCode.ERROR, "退课失败");
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
     * 获取所有老师
     */
    @RequestMapping(value= "/teachers",method = RequestMethod.GET)
    public Result getTeachers(){
        List<Course> allCourse = courseService.getAllCourse();
        Set<String> ts = new HashSet<>();
        for (Course course : allCourse) {
            ts.add(course.getTeacher());
        }
        return new Result(true, StatusCode.OK, "所有老师", ts);
    }
    @RequestMapping(value= "/academy",method = RequestMethod.GET)
    public Result getAcademy(){

        Set<String> as = new HashSet<>();
        List<Al> als = alService.findAll();
        for (Al al : als) {
            as.add(al.getAcademy());
        }

        return new Result(true, StatusCode.OK, "所有学院", as);
    }




    /**
     * 查询选课情况
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result getAllCourse() {
//        Claims claims = (Claims) request.getAttribute("admin_claims");
//        if (claims == null) {
//            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
//        }

        List<Course> courses = courseService.getAllCourse();
        return new Result(true,StatusCode.OK,"查询成功", courses);
    }

    /**
     * 分页查询选课情况
     */
    @RequestMapping(value = "/page/{currentPage}",method = RequestMethod.GET)
    public Result getPageCourse(@PathVariable int currentPage) {
        Pageable pageable = PageRequest.of(currentPage-1,5);
        Page<Course> courses = courseService.getAllCourse(pageable);
        return new Result(true,StatusCode.OK,"查询成功", new PageResult<Course>(courses.getTotalElements(),courses.getContent()));
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result addCourse(@RequestBody Course course) {
        Claims claims = (Claims) request.getAttribute("admin_claims");

        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        Optional<Course> optional = courseService.getCourseById(course.getId());
        if(optional.isPresent()){
            return new Result(false,StatusCode.ERROR,"课程编号重复");
        }else{
            //级联保存，al里应该设置course,不然courseid为null
            List<Al> als = course.getAls();
            for (Al al : als) {
                al.setCourse(course);
            }
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
            Course course1 = optional.get(); //原始数据
            UpdateTool.copyNullProperties(course1,course);//course是更改后的数据
            List<Al> als = course.getAls();
            for (Al al : als) {
                al.setCourse(course);
            }
            course.setAls(als);
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
