package com.spr.dao;

import com.spr.model.Tea;

import java.util.List;
import java.util.UUID;

public interface TeaDao {

    List<Tea> selectAllTea();

    Tea selectTeaById(UUID id);

    int updateTea(Tea tea);
}
