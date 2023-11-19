package com.sjdp.awsbookdynamo.repository;


import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface BookmarkRepository extends CrudRepository<Bookmark, BookmarkId> {

}
