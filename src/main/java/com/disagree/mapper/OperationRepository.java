package com.disagree.mapper;

import com.disagree.bean.cassandraBean.Error;
import com.disagree.bean.cassandraBean.Operation;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by disagree on 2017/5/11.
 */
public interface OperationRepository extends CrudRepository<Operation, String> {
}
