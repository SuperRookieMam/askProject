package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author MR.Yin
 * @desc:正确答案 绑定题 exam
 * 用一对多，jpa 自动维护，不需要手动维护，不需要这个类
 */
@Getter
@Setter
@Entity
@IsCreate
@Table(name="t_right_key")
public class RightKey extends BaseEntity {
    private static final long serialVersionUID = 7563309859862341200L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    @Description(label = "id")
    private Long id;

    @Column(name = "right_choose_")
    private String rightChoose;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="exam_id_")
    private Exam exam;
}
