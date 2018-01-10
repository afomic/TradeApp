package com.afomic.tradeapp.model;

import com.orm.SugarRecord;

/**
 * Created by afomic on 1/7/18.
 *
 */

public class Currency extends SugarRecord {
    private String name;
    private boolean selected;

    public Currency(){

    }

    public Currency(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
