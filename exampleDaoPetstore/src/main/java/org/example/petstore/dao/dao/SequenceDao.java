package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Sequence;
import it.mengoni.persistence.dao.Dao;
public interface SequenceDao extends Dao<Sequence> {
public Sequence getByPrimaryKey(String name);
}
