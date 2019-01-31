package com.pdwy.dus.collection.http.bean;

/**
 * 返回的图片地址
 * Author： by MR on 2019/1/10.
 */

public class PhotoHttp2Bean {

    /**
     * data : {"name":"1547797162313x6izgi.jpg","path":"/fileServerFile/file/2019/01/18/1547797162313x6izgi.jpg"}
     * code : 0
     * msg : 成功
     */

    private DataBean data;
    private String code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * name : 1547797162313x6izgi.jpg
         * path : /fileServerFile/file/2019/01/18/1547797162313x6izgi.jpg
         */

        private String name;
        private String path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
