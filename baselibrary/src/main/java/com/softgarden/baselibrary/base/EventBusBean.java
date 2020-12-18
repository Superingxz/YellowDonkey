package com.softgarden.baselibrary.base;

public class EventBusBean {

    private int code;
    private Object data;

    private EventBusBean(Builder builder) {
        this.code = builder.code;
        this.data = builder.data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public static class Builder {
        private int code;
        private Object data;

        public Builder() {
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setData(Object data) {
            this.data = data;
            return this;
        }

        public EventBusBean build() {
            return new EventBusBean(this);
        }

    }


}
