package com.ggdev.shorturl.utils;

public class BaristaUtils {

    public int getNextKey(int pageNumber, int perPageNum, int totalCount) {

        int balance =  totalCount % perPageNum;
        int lastPage;
        if(balance == 0) {
            lastPage = (totalCount / perPageNum);
        }else {
            lastPage = (totalCount / perPageNum) + 1;
        }

        // total   nowpage  perpage
        //  20        1       10

        int nextKey = pageNumber + 1;
        if(nextKey > lastPage) {
            nextKey = 0;
        }

        return nextKey;
    }

}
