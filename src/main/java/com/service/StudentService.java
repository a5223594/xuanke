package com.service;

import com.dao.StudentDao;
import com.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    BCryptPasswordEncoder encoder;


    public List<Student> getAllStudent() {
        return studentDao.findAll();
    }

    public Optional<Student> getStudentById(String id){
        return studentDao.findById(id);
    }

    public String encodePassword(String password){
        return encoder.encode(password);
    }

    public Student findByIdAndPassword(String id, String password) {
        Optional<Student> optional = studentDao.findById(id);
        if(optional.isPresent()&& encoder.matches(password,optional.get().getPassword())){
            return optional.get();
        }else{
            return null;
        }
    }

    public Student changePassword(String id,String oldPassword, String newPassword) {
        Optional<Student> optional = studentDao.findById(id);
        if(optional.isPresent()&& encoder.matches(oldPassword,optional.get().getPassword())){
            Student student = optional.get();
            student.setPassword(encoder.encode(newPassword));
            studentDao.save(student);
            return student;
        }else{
            return null;
        }
    }
}
