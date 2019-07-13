package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author MR.Yin
 * @desc  考试人
 * 这个类不需要，直接在微信账号字段加载oauthuserdetaile里面
 * 所有账号统一管理
 */

@Getter
@Setter
@Entity
@IsCreate
@Table(name="t_answer")
public class Answer extends BaseEntity {
    private static final long serialVersionUID = -4392335728780383857L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    @Description(label = "id")
    private Long id;

    @Column(name = "weChat_id_")
    @Description(label = "微信号")
    private String weChatId;



}
