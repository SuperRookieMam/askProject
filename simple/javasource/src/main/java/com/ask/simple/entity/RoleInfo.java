package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@IsCreate
@Table(name = "role_info_",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"role_name_","resource_id_"})})
public class RoleInfo extends BaseEntity {
    private static final long serialVersionUID = -6133439565976081362L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    private Long id;

    // 角色名称
    @Column(name = "role_name_")
    private String roleName;

    @Column(name = "pid_")
    private Long pid;

    @Column(name = "resource_id_")
    private String resourceId ;

}
