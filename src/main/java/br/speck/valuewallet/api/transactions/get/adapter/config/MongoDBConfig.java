package br.speck.valuewallet.api.transactions.get.adapter.config;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@DependsOn("secretClient")
public class MongoDBConfig extends AbstractKeyVaultConfig {

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        String mongoUri = getSecretValue("APPS-DATABASE-MONGO-URI");
        String databaseName = getSecretValue("VALUEWALLET-DATABASE-MONGO-DATABASE");
        return new SimpleMongoClientDatabaseFactory(mongoUri + "/" + databaseName);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        String mongoUri = getSecretValue("APPS-DATABASE-MONGO-URI");
        String databaseName = getSecretValue("VALUEWALLET-DATABASE-MONGO-DATABASE");
        return MongoClients.create(mongoUri).getDatabase(databaseName);
    }
}
