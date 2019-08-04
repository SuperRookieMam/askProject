package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author MR.Yin
 * @desc 考题
 */

@Getter
@Setter
@Entity
@IsCreate
@Table(name="t_exam")
public class Exam extends BaseEntity {
    private static final long serialVersionUID = 3501462140056691581L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    @Description(label = "id")
    private Long id;

    //题目
    @Column(name = "subject_name_")
    @Description(label = "题目类型")
    private String subjectName;

    @Column(name = "description_")
    @Description(label = "题目描述")
    private String description;

    // 单选,多选,
    @Column(name = "choose_")
    @Description(label = "答案类型")
    private String choose;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id_")
    private List<Result> results;

    @Column(name = "exams_")
    @Description(label = "答案类型")
    private Long exams;

}
