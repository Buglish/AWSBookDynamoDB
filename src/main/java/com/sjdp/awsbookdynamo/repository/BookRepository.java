package com.sjdp.awsbookdynamo.repository;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface BookRepository extends DynamoDBPagingAndSortingRepository<Book, String> {

}
