package com.hai.yun.bean;

public enum InfolistNo {
    No {
        @Override
        public InfolistNo getInstance() {
            return this;
        }
    };

    public abstract InfolistNo getInstance();

    private static int listNo = 0;

    public synchronized static int get() {
        return listNo;
    }

    public synchronized static void increase() {
        listNo++;
        if (listNo > 65535) {
            listNo = 0;
        }

    }
}
