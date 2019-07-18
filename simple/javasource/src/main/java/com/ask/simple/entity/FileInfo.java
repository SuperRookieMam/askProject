package com.ask.simple.entity;

import com.ask.base.entity.BaseEntity;
import com.ask.base.entity.FileInfoDetails;
import com.ask.codecreate.feature.annotation.Description;
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
    @Description(label = "编号")
    private String id;

    @Column(name = "filename_")
    @Description(label = "文件名")
    private String filename;

    @Column(name = "path_")
    @Description(label = "文件路径")
    private String path;

    @Column(name = "thumbnail_path_")
    @Description(label = "缩略图路径")
    private String thumbnailPath;

    @Column(name = "thumbnail_path2_")
    @Description(label = "缩略图路径2")
    private String thumbnailPath2;

    @Column(name = "preview_path_")
    @Description(label = "预览路径")
    private String previewPath;

    @Column(name = "size_")
    @Description(label = "文件大小")
    private Long size;
}
