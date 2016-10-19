package com.sogeti.smartshelf.model;

/**
 *
 * @author fabdin
 */
public class Message {
    String text;

    public Message(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
