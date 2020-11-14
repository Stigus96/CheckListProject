package com.example.checklistapp;

public class Item {

    Long itemid;
    String title;
    Checklist checklist;
    boolean checked;

    public Long getItemid(){return itemid;}

    public void setItemid(Long itemid){
        this.itemid = itemid;
    }

    public String getTitle(){return title;}

    public void setTitle(String title){
        this.title = title;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
