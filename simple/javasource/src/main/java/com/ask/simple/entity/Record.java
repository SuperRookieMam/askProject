package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author MR.Yin
 * @desc 答题记录
 */
@Getter
@Setter
@Entity
@IsCreate
@Table(name="t_record")
public class Record extends BaseEntity {
    private static final long serialVersionUID = -5115878708968627393L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    @Description(label = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id_")
    @Description(label = "答题人")
    private Answer answer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="food_id_", insertable = false,updatable = false)
    @Description(label = "菜品")
    private Food food;


    @Column(name = "answer_question_")
    @Description(label = "回答")
    private String answerQuestion;
    @Column(name = "score_")
    @Description(label = "实际得分")
    private Integer score;








}
