package com.sjdp.awsbookdynamo.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "Bookmark")
public class Bookmark {

    @Id
    @DynamoDBIgnore
    @JsonIgnore
    private BookmarkId bookmarkId; //index 1 (book/createdAt)
    @DynamoDBIndexHashKey(attributeName = "readSession", globalSecondaryIndexNames ="Session-Index")
    private Long readSession; //index 2
    @DynamoDBAttribute(attributeName = "page")
    private Long page;
    @DynamoDBAttribute(attributeName = "word")
    private Long word;

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "book")
    public String getBook() {
        return this.bookmarkId != null ? this.bookmarkId.getBook() : null;
    }

    public void setBook(String book) {
        if(this.bookmarkId==null)
            this.bookmarkId = new BookmarkId();
        this.bookmarkId.setBook(book);
    }


    @DynamoDBRangeKey
    @DynamoDBAttribute(attributeName = "createdAt")
    public String getCreatedAt() {
        return this.bookmarkId != null ? this.bookmarkId.getCreatedAt() : null;
    }

    public void setCreatedAt(String createdAt) {
        if(this.bookmarkId == null)
            this.bookmarkId = new BookmarkId();
        this.bookmarkId.setCreatedAt(createdAt);
    }

}
