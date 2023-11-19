# Java API with DynamoDB Local Project

Base structure created with spring initializr
https://start.spring.io/;

DynamoDB locally installed and deployed with command line.
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html

## Setup
Locally install DynamoDB with keys

aws kms create-key --endpoint-url https://ap-southeast-2.amazonaws.com

https://docs.aws.amazon.com/general/latest/gr/ddb.html

Asia Pacific (Sydney) 	ap-southeast-2

C:\> set AWS_ACCESS_KEY_ID=FAKEACCESSKEY

C:\> set AWS_SECRET_ACCESS_KEY=FAKESECRETKEYc066bfba78543113f2f2ef53df3e0ab1

C:\> set AWS_DEFAULT_REGION=ap-southeast-2

https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-sso.html


Deployed with

    java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb

## Populate Data

	Use seperate main class to upload/import entries e.g. CreateTablesLoadData

## Testing Endpoints

Get Book

    Get: http://localhost:8082/book/Book Title 1
    
Last Bookmark of All Books

    Get: http://localhost:8082/bookmark/last

Get Reading Session Bookmark Single Record

    Post: http://localhost:8082/bookmark/Book Title 1/session
    Payload:
    {
        "session":1,
        "createdAt":"2023-11-01T02:00:00Z"
    }

Get Reading Session Bookmarks

    Post: http://localhost:8082/bookmark/Book Title 1/session
    Payload:
    {
        "trip":1,
        "createdAt":""
    }

Get Bookmarks Between Dates

    Post: http://localhost:8082/bookmark/Book Title 1/between
    Payload:
    {
        "start":"2023-11-01T01:30:00Z",
        "end":"2023-11-01T03:30:00Z"
    }
    
