package com.sjdp.awsbookdynamo;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;

/**
 * This class is not part of the main application and is intended to only be ran once to import data for testing purposes.
 *
 * Uncomment the main method and run.
 **/

public class ImportData {

    static Logger logger = LoggerFactory.getLogger(ImportData.class);

    static BasicSessionCredentials awsCreds = new BasicSessionCredentials("FAKEACCESSKEY",
                                            "FAKESECRETKEYc066bfba78543113f2f2ef53df3e0ab1",
                                            "session_token");

    static AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000/", "ap-southeast-2"))
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();

    static DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);

    static String bookmarkTable = "Bookmark";
    static String bookTable = "Book";

    // ** Uncomment to import **
    //public static void main(String[] args) throws Exception {
    //    deleteTables();
    //    createTables();
    //    importData();
    //}

    static void deleteTables() {
        deleteTable(bookmarkTable);
        deleteTable(bookTable);
    }

    static void createTables() {
        createTable(bookmarkTable);
        createTable(bookTable);
    }

    static void deleteTable(String tableName) {
        System.out.println("Deleting table : "+tableName);
        Table table = dynamoDB.getTable(tableName);
        try {
            table.delete();
            table.waitForDelete();
        }
        catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        System.out.println("Finished Deleting table : "+tableName);
    }

    private static void createTable(String tableName){
        System.out.println("Creating table: " + tableName);
        try {
            ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
            keySchema.add(new KeySchemaElement().withAttributeName("book").withKeyType(KeyType.HASH));
            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
            attributeDefinitions.add(new AttributeDefinition().withAttributeName("book").withAttributeType("S"));

            if (tableName.equals(bookmarkTable)) {
                keySchema.add(new KeySchemaElement().withAttributeName("createdAt").withKeyType(KeyType.RANGE));
            }

            CreateTableRequest request = new CreateTableRequest()
                                                .withTableName(tableName)
                                                .withKeySchema(keySchema)
                                                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(10L)
                                                .withWriteCapacityUnits(5L));

            if (tableName.equals(bookmarkTable)) {
                attributeDefinitions.add(new AttributeDefinition().withAttributeName("createdAt").withAttributeType("S"));
                attributeDefinitions.add(new AttributeDefinition().withAttributeName("readSession").withAttributeType("N"));

                ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<LocalSecondaryIndex>();
                localSecondaryIndexes.add(new LocalSecondaryIndex().withIndexName("Session-Index")
                        .withKeySchema(new KeySchemaElement().withAttributeName("book").withKeyType(KeyType.HASH),
                                new KeySchemaElement().withAttributeName("readSession").withKeyType(KeyType.RANGE))
                        .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)));

                request.setLocalSecondaryIndexes(localSecondaryIndexes);
            }

            request.setAttributeDefinitions(attributeDefinitions);

            Table table = dynamoDB.createTable(request);
            table.waitForActive();

        } catch (Exception e) {
            logger.error("Failed to create table : " + tableName + " error : "+e.getMessage(),e);
        }
        System.out.println("Finished creating table : " + tableName);
    }

    static void importData(){
        System.out.println("Import Data to " + bookTable + " and " + bookmarkTable);

        Table assetTable = dynamoDB.getTable(bookTable);
        Table assetLocationTable = dynamoDB.getTable(bookmarkTable);

        List<String> books = new ArrayList<String>();

        try {
            Item item = new Item();

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("data.json");
            Scanner scanner = new Scanner(is);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                JSONObject jo = new JSONObject(line);
                System.out.println(jo.toString());

                String bookId = jo.getString("book");

                item = new Item().withPrimaryKey("book", bookId)
                        .withNumber("readSession", jo.getLong("readSession"))
                        .withNumber("page", jo.getDouble("page"))
                        .withNumber("word", jo.getDouble("word"))
                        .withString("createdAt", jo.getString("createdAt"));
                assetLocationTable.putItem(item);

                if(!books.contains(bookId)){
                    item = new Item().withPrimaryKey("book", jo.getString("book"));
                    assetTable.putItem(item);
                    books.add(bookId);
                }

            }
            scanner.close();

        }catch(ResourceNotFoundException nre){
            logger.error("[ResourceNotFoundException] "+ nre.getMessage(),nre);
        }catch(Exception e){
            logger.error("[Exception] "+ e.getMessage(),e);
        }
        System.out.println("Importing Done");
    }
}