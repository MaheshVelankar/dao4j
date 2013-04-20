package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Sequence;


public interface SequenceDao extends Dao<Sequence> {
    public Sequence getByPrimaryKey(String name);
}
