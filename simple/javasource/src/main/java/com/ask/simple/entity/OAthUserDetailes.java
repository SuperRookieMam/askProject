package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.codecreate.feature.annotation.Description;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@IsCreate
@Table(name = "oath_user_detailes_",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name_"})},
        indexes = {@Index(columnList = "user_name_")})
public class OAthUserDetailes extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 9056596580975978130L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_")
    private Long id;
    // 我们这边的账号，只能用英文，人员管理为唯一凭证
    @Column(name = "user_name_")
    private String username;

    @Column(name = "nickname_")
    @Description(label = "昵称")
    private String nickname;

    @Column(name = "weChat_id_")
    @Description(label = "微信号")
    private String weChatId;


    @Column(name = "pass_word_")
    private String password;

    @Column(name = "head_image_")
    private String headImage;

    // 用户的权限
    @Transient// 不建表，动态获取
    private List<? extends GrantedAuthority> authorities;

    @Column(name = "expired_")
    private boolean expired;

    @Column(name = "lock_")
    private boolean lock;

    @Column(name = "credentials_")
    private String credentials;

    @Column(name = "enabled_")
    private boolean enabled;

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !lock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
