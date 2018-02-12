package com.epam.zhuckovich.entity;

/**
 * <p>Abstract class contains the id used in all subclasses.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

public abstract class Entity {
    protected int id;

    /**
     * <p>Returns the id parameter</p>
     * @return the id parameter
     */

    public int getId(){
        return id;
    }
}
