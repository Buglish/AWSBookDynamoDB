package com.sjdp.awsbookdynamo.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.sjdp.awsbookdynamo.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class DynamoDBClientQueryBuilder {

    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    @Autowired
    DynamoDBMapper dynamoDBMapper;

    public List<Bookmark> getBookmarks(String book,Integer limit){
        List<Bookmark> response = new ArrayList<Bookmark>();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("Bookmark");

        Bookmark bookmark = new Bookmark();
        bookmark.setBook(book);

        DynamoDBQueryExpression<Bookmark> queryExpression = new DynamoDBQueryExpression<Bookmark>().withHashKeyValues(bookmark);
        queryExpression.setScanIndexForward(false);

        if(limit != null) {
            queryExpression.withLimit(limit);
            queryExpression.setLimit(limit);
        }

        response = dynamoDBMapper.queryPage(Bookmark.class, queryExpression).getResults();

        return response;
    }

    public List<Bookmark> getBookmarksBetween(String book, String start, String end){
        List<Bookmark> response = new ArrayList<Bookmark>();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("Bookmark");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("book = :v_book AND createdAt BETWEEN :v_start AND :v_end")
                .withValueMap(new ValueMap()
                        .withString(":v_book", book)
                        .withString(":v_start", start)
                        .withString(":v_end", end));

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            response.add(Utility.convertBookmarkObject(item));
        }

        return response;
    }


    public List<Bookmark> getSessionBookmarks(String book, Long session){
        List<Bookmark> response = new ArrayList<Bookmark>();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("Bookmark");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("book = :v_book")
                .withFilterExpression("readSession = :v_readSession")
                .withValueMap(new ValueMap()
                        .withString(":v_book", book)
                        .withNumber(":v_readSession", session)
                );

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            response.add(Utility.convertBookmarkObject(item));
        }
        return response;
    }

    public List<Bookmark> getSessionBookmark(String book, Long session,String createdAt){
        List<Bookmark> response = new ArrayList<Bookmark>();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("Bookmark");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("book = :v_book AND createdAt = :v_createdAt ")
                .withFilterExpression("readSession = :v_readSession")
                .withValueMap(new ValueMap()
                        .withString(":v_book", book)
                        .withNumber(":v_readSession", session)
                        .withString(":v_createdAt", createdAt)
                );

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            response.add(Utility.convertBookmarkObject(item));
        }
        return response;
    }

}
