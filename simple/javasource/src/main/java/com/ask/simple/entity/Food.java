package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author MR.Yin
 * @desc 菜品
 */
@Getter
@Setter
@Entity
@IsCreate
@Table(name="t_food")
public class Food extends BaseEntity {

    private static final long serialVersionUID = 4612026055437681513L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    @Description(label = "id")
    private Long id;

    @Column(name = "food_name_")
    @Description(label = "菜品名")
    private String foodName;

    @Column(name = "description_")
    @Description(label = "描述")
    private String description;

    @Column(name = "img_url_")
    @Description(label = "图片")
    private String imgUrl;

    @Column(name = "process_url_")
    @Description(label = "烹饪过程")
    private String processUrls;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exams_")
    private List<Exam> exams;

}
