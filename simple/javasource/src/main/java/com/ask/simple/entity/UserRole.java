package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@IsCreate
@Table(name = "user_role_",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name_"})},
        indexes = {@Index(columnList = "user_name_")})
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = -526335157679328935L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_")
    private Long id;

    @Column(name = "user_name_")
    private String userName;

    @Column(name = "role_id_")
    private Long roleId;



    // 角色名称
    @Column(name = "role_name_")
    @Description(label = "角色名称")
    private String roleName;


}
