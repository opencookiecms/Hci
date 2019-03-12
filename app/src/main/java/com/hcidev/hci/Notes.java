package com.hcidev.hci;

import com.google.gson.annotations.SerializedName;

public class Notes {

    @SerializedName("note_id")
    private int note_id;
    @SerializedName("note_title")
    private String note_title;
    @SerializedName("note_content")
    private String note_content;
    @SerializedName("note_link")
    private String note_link;
    @SerializedName("note_type")
    private int note_type;
    @SerializedName("note_userId")
    private int note_userId;
    @SerializedName("note_reg")
    private String note_reg;


    public Notes(int note_id, String note_title, String note_content, String note_link, int note_type, int note_userId, String note_reg) {
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_content = note_content;
        this.note_link = note_link;
        this.note_type = note_type;
        this.note_userId = note_userId;
        this.note_reg = note_reg;
    }

    public int getNote_id() {
        return note_id;
    }

    public String getNote_title() {
        return note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public String getNote_link() {
        return note_link;
    }

    public int getNote_type() {
        return note_type;
    }

    public int getNote_userId() {
        return note_userId;
    }

    public String getNote_reg() {
        return note_reg;
    }
}
