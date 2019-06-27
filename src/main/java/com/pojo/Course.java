package com.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
@DynamicUpdate
public class Course implements Serializable {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column
    private String teacher;

    @Column
    private int number;

    @Column
    private int selected;

    @Column
    private String grade;

    /*
    orphanRemoval=true,在删除多方时，删除无关联的数据
     */
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval=true)
    private List<Al> als;

    /**
     * 选课多对多关系，课程为主表，课程下有多个学生
     *
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sc",joinColumns = @JoinColumn(name = "courseid"),
            inverseJoinColumns = @JoinColumn(name = "studentid"))
    @JsonIgnoreProperties("courses")
    private Set<Student> students;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public List<Al> getAls() {
        return als;
    }

    public void setAls(List<Al> als) {
        this.als = als;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", number=" + number +
                ", selected=" + selected +
                '}';
    }
}
