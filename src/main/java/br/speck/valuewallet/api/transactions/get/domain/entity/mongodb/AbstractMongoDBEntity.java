package br.speck.valuewallet.api.transactions.get.domain.entity.mongodb;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public abstract class AbstractMongoDBEntity {

    @Id
    @JsonProperty("id")
    protected String id;

    public String getId() {
        return id;
    }
}
