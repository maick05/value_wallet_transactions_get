package br.speck.valuewallet.api.transactions.get.adapter.config;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureCredentialConfig {

    @Value("${spring.cloud.azure.keyvault.secret.endpoint}")
    private String keyVaultUrl;

    @Value("${spring.cloud.azure.credential.client-id}")
    private String clientId;

    @Value("${spring.cloud.azure.credential.client-secret}")
    private String clientSecret;

    @Value("${spring.cloud.azure.credential.tenant-id}")
    private String tenantId;

    @Bean
    public ClientSecretCredential clientSecretCredential() {
        return new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
    }

    @Bean
    public SecretClient secretClient(ClientSecretCredential credential) {
        return new SecretClientBuilder()
                .vaultUrl(keyVaultUrl)
                .credential(credential)
                .buildClient();
    }
}
