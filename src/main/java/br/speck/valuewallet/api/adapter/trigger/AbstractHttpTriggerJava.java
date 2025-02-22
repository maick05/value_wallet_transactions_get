package br.speck.valuewallet.api.adapter.trigger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract Azure Functions with HTTP Trigger.
 */
public abstract class AbstractHttpTriggerJava {
    protected void logHttpRequest(HttpRequestMessage<?> request, ExecutionContext context, String routeTemplate) {
        StringBuilder logBuilder = new StringBuilder();

        String method = request.getHttpMethod().name();
        logBuilder.append("HTTP Method: ").append(method).append("\n");

        URI requestUri = request.getUri();
        logBuilder.append("Request URL: ").append(requestUri.toString()).append("\n");

        var pathParams = this.extractPathParameters(routeTemplate, requestUri);
        if (!pathParams.isEmpty()) {
            logBuilder.append("Path Parameters: ").append(pathParams.toString()).append("\n");
        }

        Map<String, String> queryParams = request.getQueryParameters();
        if (queryParams != null && !queryParams.isEmpty()) {
            logBuilder.append("Query Parameters: ").append(queryParams.toString()).append("\n");
        }

        Optional<?> bodyOptional = (Optional<?>) request.getBody();
        if (bodyOptional.isPresent()) {
            Object body = bodyOptional.get();
            logBuilder.append("Body: ").append(body.toString()).append("\n");
        }

        Map<String, String> headers = request.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            logBuilder.append("Headers: ").append(headers.toString()).append("\n");
        }

        // Registra a mensagem de log
        context.getLogger().info(logBuilder.toString());
    }

    protected String stringify(Object obj) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            objMapper.registerModule(new JavaTimeModule());
            return objMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error trying to parse JSON", e);
        }
    }

    private Map<String, String> extractPathParameters(String routeTemplate, URI requestUri) {
        Map<String, String> pathParams = new HashMap<>();
        String normalizedTemplate = normalize(routeTemplate);
        String normalizedPath = normalize(requestUri.getPath());
        String[] templateParts = normalizedTemplate.split("/");
        String[] pathParts = normalizedPath.split("/");

        if (templateParts.length != pathParts.length) {
            return pathParams;
        }

        for (int i = 0; i < templateParts.length; i++) {
            String templatePart = templateParts[i];
            if (templatePart.startsWith("{") && templatePart.endsWith("}")) {
                String paramName = templatePart.substring(1, templatePart.length() - 1);
                pathParams.put(paramName, pathParts[i]);
            }
        }
        return pathParams;
    }

    private static String normalize(String str) {
        if (str.startsWith("/"))
            str = str.substring(1);
        if (str.endsWith("/"))
            str = str.substring(0, str.length() - 1);
        return str;
    }
}
