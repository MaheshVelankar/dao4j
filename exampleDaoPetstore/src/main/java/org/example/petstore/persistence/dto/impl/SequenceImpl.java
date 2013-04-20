package org.example.petstore.persistence.dto.impl;

import it.mengoni.persistence.dto.AbstractPersistentObject;

import org.example.petstore.persistence.dto.Sequence;

import org.javatuples.Tuple;
import org.javatuples.Unit;


public class SequenceImpl extends AbstractPersistentObject implements Sequence {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer nextid;

    public SequenceImpl() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getNextid() {
        return nextid;
    }

    @Override
    public void setNextid(Integer nextid) {
        this.nextid = nextid;
    }

    @Override
    public String getDisplayLabel() {
        return name;
    }

    @Override
    protected Tuple newKey() {
        return new Unit<String>(name);
    }
}
