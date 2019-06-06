package com.service;

import com.dao.AdminDao;
import com.pojo.Admin;
import com.util.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    public Optional<Admin> getAdminById(String id) {
        return adminDao.findById(id);
    }

    public Admin findByIdAndPassword(String id, String password) {
        Optional<Admin> optional = adminDao.findById(id);
        if(optional.isPresent()&& encoder.matches(password,optional.get().getPassword())){
            return optional.get();
        }else{
            return null;
        }
    }

    public Admin changePassword(String id,String oldPassword, String newPassword) {
        Optional<Admin> optional = adminDao.findById(id);
        if(optional.isPresent()&& encoder.matches(oldPassword,optional.get().getPassword())){
            Admin admin = optional.get();
            admin.setPassword(encoder.encode(newPassword));
            adminDao.save(admin);
            return admin;
        }else{
            return null;
        }
    }
}
