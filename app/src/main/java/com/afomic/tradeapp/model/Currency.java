package com.afomic.tradeapp.model;

/**
 * Created by afomic on 1/7/18.
 *
 */

public class Currency {
    private String name;
    private boolean selected;

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
