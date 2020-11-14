package com.example.checklistapp;

import java.util.ArrayList;
import java.util.List;

public class Checklist {
    Long checklistid;
    String title;
    List<Item> items;
    User owner;

    public long getChecklistid() {return checklistid;}

    public void setChecklistid(long checklistid) {this.checklistid = checklistid;}

    public String getTitle() { return title;}

    public void setTitle(String title) {this.title = title;}

    public List<Item> getItems(){
        if(items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<Item> items){this.items = items;}

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


}
