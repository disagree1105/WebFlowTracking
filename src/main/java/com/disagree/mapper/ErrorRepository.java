package com.disagree.mapper;

import com.disagree.bean.cassandraBean.Error;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by disagree on 2017/5/11.
 */
public interface ErrorRepository extends CrudRepository<Error,String> {
}
