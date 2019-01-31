package com.pdwy.dus.collection;

/**
 *  前后台切换事件通知
 * Created by marui on 16/9/24.
 */

public class ToForegroundEvent {
    private boolean isForeground;   //软件是否在前台

    public ToForegroundEvent(boolean isForeground) {
        this.isForeground = isForeground;
    }

    public boolean isForeground() {
        return isForeground;
    }

    /**
     *
     * @param foreground the foreground
     */
    public void setForeground(boolean foreground) {
        isForeground = foreground;
    }
}
