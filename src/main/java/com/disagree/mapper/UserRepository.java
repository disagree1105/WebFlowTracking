package com.disagree.mapper;

import com.disagree.bean.cassandraBean.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by disagree on 2017/5/10.
 */
public interface UserRepository extends CrudRepository<User,String> {
}
