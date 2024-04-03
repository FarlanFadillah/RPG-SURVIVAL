package com.ui;

public class Message{
    public int delay;
    String text;
    public int count, start;
    public Message(String text, int start, int delay){
        this.text = text;
        this.start = start;
        this.delay = delay;
    }
    
}
