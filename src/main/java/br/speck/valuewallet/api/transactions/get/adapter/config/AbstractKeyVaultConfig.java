package br.speck.valuewallet.api.transactions.get.adapter.config;

import com.azure.security.keyvault.secrets.SecretClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class AbstractKeyVaultConfig {
    @Autowired
    protected SecretClient secretClient;

    @Autowired
    protected Environment env;


    protected String getSecretValue(String secretName) {
        try {
            return secretClient.getSecret(secretName).getValue();
        } catch (Exception e) {
            System.err.println("❌ Error whiling retrieving Key Vault secret '" + secretName + "': " + e.getMessage());
            return env.getProperty(secretName, "");
        }
    }

    @PostConstruct
    public void init() {
        System.out.println(this.getClass().getSimpleName() + " config values loaded.");
    }
}
