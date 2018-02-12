package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.entity.Entity;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * <p>The functional interface that used for various actions
 * to obtain information from the database</p>
 * @param <T> generic type that should be a subclass of Entity class
 */

@FunctionalInterface
public interface ActionDAO<T extends Entity> {
    List<T> executeQuery(PreparedStatement statement);
}
