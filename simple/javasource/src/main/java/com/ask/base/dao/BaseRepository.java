package com.ask.base.dao;

import com.ask.orm.dao.MyJpaBaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends MyJpaBaseRepository<T,ID> {
}
