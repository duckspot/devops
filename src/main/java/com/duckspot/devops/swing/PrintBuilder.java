package com.duckspot.devops.swing;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class PrintBuilder implements Appendable {

    private boolean complete = false;
    private StringBuilder builder = new StringBuilder();    
    private String text = "";
    private int lastLineStartsAt = 0;
    private int nextCharAt = -1;      // if -1 means at the end
    
    private PropertyChangeSupport mPcs =
        new PropertyChangeSupport(this);
 
    public void setComplete(boolean complete) {
        boolean oldValue = this.complete;
        this.complete = complete;
        mPcs.firePropertyChange("complete", oldValue, complete);
    }
    
    private void setText(String text) {
        String oldValue = this.text;
        this.text = text;
        mPcs.firePropertyChange("text", oldValue, text);
    }
    
    public String getText() {
        return text;
    }
    
    public void
    addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }
    
    public void
    removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }
    
    private void appendChar(char c) {
        switch (c) {
            case '\r':
                nextCharAt = lastLineStartsAt;
                break;
            case '\n':
                builder.append(c);
                lastLineStartsAt = builder.length();
                nextCharAt = -1;
                break;
            default:
                if (nextCharAt >= 0) {                    
                    builder.setLength(nextCharAt);
                    nextCharAt = -1;
                }
                builder.append(c);
        }
    }

    @Override
    public Appendable append(char c) throws IOException {
        appendChar(c);
        setText(builder.toString());
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        for (int i = start; i < end; i++) {
            appendChar(csq.charAt(i));
        }
        setText(builder.toString());
        return this;
    }

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        append(csq, 0, csq.length());        
        return this;
    }

    @Override
    public String toString() {
        return text;
    }        
}