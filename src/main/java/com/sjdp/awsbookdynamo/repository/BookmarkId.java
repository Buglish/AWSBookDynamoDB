package com.sjdp.awsbookdynamo.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class BookmarkId implements Serializable {

    @Serial
    private static final long serialVersionUID = -1623690946898638162L;
    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "book")
    private String book;

    @DynamoDBRangeKey
    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

}
