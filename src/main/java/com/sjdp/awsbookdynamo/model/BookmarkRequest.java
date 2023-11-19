package com.sjdp.awsbookdynamo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookmarkRequest {
    Long session;
    String createdAt; //optional

}
