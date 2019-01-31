package com.pdwy.dus.collection.model.bean;

/**
 * Author： by MR on 2018/9/14.
 */

public class MessageEvent {
    private String message;
    private String p;
    public  MessageEvent(String message,String p){
        this.message=message;
        this.p=p;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
//    作者：梨子哥
//    链接：https://www.jianshu.com/p/f9ae5691e1bb
//    來源：简书
//    简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。

}
