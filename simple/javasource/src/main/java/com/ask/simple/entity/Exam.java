package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "subject_name_",unique = true)
    @Description(label = "题目")
    private String subjectName;

    @Column(name = "choose_")
    @Description(label = "可选答案项")
    private String choose;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="food_id_")
    private Food food;

    @JoinColumn(name="score")
    @Description(label = "分值")
    private Integer score;

}
