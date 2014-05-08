package com.duckspot.roadie.download;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Provides a way of getting status updates for a group of Download objects.
 * Simply add the objects to the DownloadGroup, and register a 
 * PropertyChangeListener on the group object.  It will get a status update to
 * COMPLETE only when all the items are complete.  If any FAIL then it will
 * never get a complete.
 */
public class DownloadGroup implements PropertyChangeListener {
            
    private int nDownloads;
    private int status = Download.NEW;
    private PropertyChangeSupport mPcs =
        new PropertyChangeSupport(this);        
    
    public void
    addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }
    
    public void
    removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }        
            
    public void add(Download download) {
        nDownloads++;
        download.addPropertyChangeListener(this);
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }
        
    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        int oldStatus = this.status;
        this.status = status;
        mPcs.firePropertyChange("status", oldStatus, status);
    }

    private void upStatus(int status) {        
        if (status > this.status) {
            setStatus(status);
        }
    }
    
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("status")) {
            int downloadStatus = (int)evt.getNewValue();
            switch (downloadStatus) {
                case Download.COMPLETE:
                    nDownloads--;
                    if (nDownloads == 0) {
                        upStatus(Download.COMPLETE);
                    }
                    break;                
            }
        }
    }
    
}
