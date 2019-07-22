package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@IsCreate
@Table(name="result_")
public class Result extends BaseEntity {
    private static final long serialVersionUID = 6946854695207855386L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    @Description(label = "id")
    private Long id;

    // 答案描述，答案的文字
    @Column(name = "description_")
    @Description(label = "答案")
    private String description;

    // 答案的分值 错的对的 正分负分交给管理管去设置
    @Column(name = "score_")
    @Description(label = "分值")
    private Integer score;

    // 答案是否正确
    @Column(name = "right_")
    @Description(label = "是否正确")
    private boolean right;

}
