package com.example.demo.result;
//返回结果数据格式封装
public class ResponseData extends Response {
    private Object data;

    public ResponseData(Object data) {
        this.data = data;
    }

    public ResponseData(ExceptionMsg msg) {
        super(msg);
    }

    public ResponseData(String status, String rspMsg) {
        super(status, rspMsg);
    }

    public ResponseData(String status, String rspMsg, Object data) {
        super(status, rspMsg);
        this.data = data;
    }

    public ResponseData(ExceptionMsg msg, Object data) {
        super(msg);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "data=" + data +
                "} " + super.toString();
    }
}

/*return new ResponseData(ExceptionMsg.SUCCESS,"你好");*/
