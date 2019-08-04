package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@IsCreate
@Table(name = "menu_function_",
              uniqueConstraints = {
        @UniqueConstraint(columnNames = {"form_route_", "table_route_"})})
public class MenuFunction extends BaseEntity {
    private static final long serialVersionUID = -7318256292767023491L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    private Long id;

    @Column(name = "cname_")
    private String cname;
    //属于那个组
    @Column(name = "ename_")
    private String ename;

    @Column(name = "form_route_")
    private String formRoute;

    @Column(name = "table_route_")
    private String tableRoute;

    @Column(name = "pid_")
    private Long pid;

    @Column(name = "is_menu_")
    private Integer isMenu;

    @Column(name = "is_show_")
    private Integer isShow;

    @Column(name = "type_")
    private String type;

    @Column(name = "sort_")
    private Integer sort;
}
