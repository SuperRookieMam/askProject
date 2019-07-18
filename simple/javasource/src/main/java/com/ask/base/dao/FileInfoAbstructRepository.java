package com.ask.base.dao;

import com.ask.base.entity.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface FileInfoAbstructRepository<T extends BaseEntity,ID extends Serializable> extends BaseRepository<T,ID>{

}
