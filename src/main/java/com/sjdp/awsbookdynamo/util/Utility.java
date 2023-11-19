package com.sjdp.awsbookdynamo.util;

import com.amazonaws.services.dynamodbv2.document.Item;

import com.sjdp.awsbookdynamo.repository.Bookmark;

public class Utility {

    public static Bookmark convertBookmarkObject(Item item){
        Bookmark lr = new Bookmark();
        lr.setBook(item.getString("book"));
        lr.setReadSession(item.getLong("readSession"));
        lr.setPage(item.getLong("page"));
        lr.setWord(item.getLong("word"));
        lr.setCreatedAt(item.getString("createdAt"));
        return lr;
    }

}

