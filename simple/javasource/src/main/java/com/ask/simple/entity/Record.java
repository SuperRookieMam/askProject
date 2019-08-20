package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "answer_")
    @Description(label = "答题人")
    private String answer;

    @Column(name = "answer_id_")
    @Description(label = "答题人Id")
    private Long answerId;

    @Column(name = "food_name_")
    @Description(label = "菜品名")
    private String foodName;
    @Column(name = "food_id_")
    @Description(label = "菜品id")
    private Long foodId;

    @Column(name = "score_")
    @Description(label = "实际得分")
    private Integer score;








}
