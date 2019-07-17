package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.base.entity.FileInfoDetails;
import com.ask.codecreate.feature.annotation.IsCreate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@IsCreate
@Table(name = "shopping_file_info_")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FileInfo extends BaseEntity implements FileInfoDetails {
    private static final long serialVersionUID = -125483214414128953L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id_")
    private String id;

    @Column(name = "filename_")
    private String filename;

    @Column(name = "path_")
    private String path;

    @Column(name = "size_")
    private Long size;
}
