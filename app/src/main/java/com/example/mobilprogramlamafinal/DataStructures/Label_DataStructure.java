package com.example.mobilprogramlamafinal.DataStructures;

public class Label_DataStructure {
    public Label_DataStructure(String tag, String description) {
        Tag = tag;
        Description = description;
    }

    String Tag;
    String Description;

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
