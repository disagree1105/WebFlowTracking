package com.disagree.bean.cassandraBean;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by disagree on 2017/5/11.
 */
@Table
public class User {
    @PrimaryKey
    int uid;
    String username;

    public int getId() {
        return uid;
    }

    public User(int uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public void setId(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
