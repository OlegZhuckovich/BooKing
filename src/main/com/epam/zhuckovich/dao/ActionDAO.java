package com.epam.zhuckovich.dao;

import java.sql.PreparedStatement;
import java.util.List;

@FunctionalInterface
public interface ActionDAO<T> {
    List<T> executeQuery(PreparedStatement statement);
}
