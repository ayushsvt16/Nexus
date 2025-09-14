package com.nexus.nexusproject.Repository;

import org.springframework.stereotype.Service;
import com.nexus.nexusproject.model.ExamResource;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class PapersResourceRepository {

    private final DynamoDbTable<ExamResource> table;

    public PapersResourceRepository(DynamoDbTable<ExamResource> table) {
        this.table = table;
    }
  
}
