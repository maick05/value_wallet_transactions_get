package br.speck.valuewallet.api.transactions.get.adapter.trigger;

import br.speck.valuewallet.api.transactions.get.SpringBootFunctionConfig;
import br.speck.valuewallet.api.transactions.get.adapter.exception.AbstractHttpException;
import br.speck.valuewallet.api.transactions.get.adapter.exception.InvalidPaginationHttpException;
import br.speck.valuewallet.api.transactions.get.application.constants.AppConstants;
import br.speck.valuewallet.api.transactions.get.application.constants.ErrorCodes;
import br.speck.valuewallet.api.transactions.get.application.dto.ErrorResponseDTO;
import br.speck.valuewallet.api.transactions.get.application.helper.DTOValidationHelper;
import br.speck.valuewallet.api.transactions.get.application.service.CommandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.function.ThrowingSupplier;

import java.net.URI;
import java.util.*;

/**
 * Abstract Azure Functions with HTTP Trigger.
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractHttpTriggerJava {
    protected final DTOValidationHelper dtoValidator = new DTOValidationHelper();

    protected void logHttpRequest(HttpRequestMessage<?> request, ExecutionContext context, String routeTemplate) {
        StringBuilder logBuilder = new StringBuilder();

        String method = request.getHttpMethod().name();
        logBuilder.append("HTTP Method: ").append(method).append("\n");

        URI requestUri = request.getUri();
        logBuilder.append("Request URL: ").append(requestUri.toString()).append("\n");

        var pathParams = this.extractPathParameters(routeTemplate, requestUri);
        if (!pathParams.isEmpty()) {
            logBuilder.append("Path Parameters: ").append(pathParams).append("\n");
        }

        Map<String, String> queryParams = request.getQueryParameters();
        if (queryParams != null && !queryParams.isEmpty()) {
            logBuilder.append("Query Parameters: ").append(queryParams).append("\n");
        }

        Optional<?> bodyOptional = (Optional<?>) request.getBody();
        if (bodyOptional.isPresent()) {
            Object body = bodyOptional.get();
            logBuilder.append("Body: ").append(body).append("\n");
        }

        Map<String, String> headers = request.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            logBuilder.append("Headers: ").append(headers).append("\n");
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

    protected Pageable getPageableParams(HttpRequestMessage<?> req) {
        try{
            Map<String, String> queryParams = req.getQueryParameters();
            String pageNumber = Objects.requireNonNullElse(queryParams.get("pageNumber"), AppConstants.DEFAULT_PAGE_NUMBER);
            String pageSize = Objects.requireNonNullElse(queryParams.get("pageSize"), AppConstants.DEFAULT_PAGE_SIZE);
            return PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        }catch (RuntimeException e){
            throw new InvalidPaginationHttpException(e.getMessage());
        }
    }

    protected <T> HttpResponseMessage executeSafely(
            HttpRequestMessage<?> request,
            String route, ExecutionContext context,
            Class serviceClass,
            ThrowingSupplier paramsDTO
    ) {
        try {
            this.logHttpRequest(request, context, route);
            CommandService service = (CommandService) SpringBootFunctionConfig.getBean(serviceClass);
            return this.printSuccess(request, HttpStatus.OK, service.execute(paramsDTO.get()));
        } catch (Exception error) {
            System.out.println("Error: " + error);
            return this.printError(request, error);
        }
    }

    protected HttpResponseMessage printError(HttpRequestMessage<?> request, Exception error){
        HttpResponseMessage.Builder respBuilder;
        List<ErrorResponseDTO> errors;

        Map<String, Object> data = new HashMap<>();
        if (error instanceof NumberFormatException ){
            respBuilder = request.createResponseBuilder(HttpStatus.BAD_REQUEST);
            errors = List.of(new ErrorResponseDTO(ErrorCodes.NUMBER_FORMAT, "Invalid Input Data: " + error.getMessage()));
        }
        else if (error instanceof AbstractHttpException){
            respBuilder = request.createResponseBuilder(((AbstractHttpException) error).status);
            errors = ((AbstractHttpException) error).getErrors();
        }else {
            respBuilder = request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR);
            errors = List.of(new ErrorResponseDTO(ErrorCodes.INTERNAL_SERVER, "Internal Error: " + error.getMessage()));
        }
        data.put("errors", errors);
        return respBuilder.body(this.stringify(data)).build();
    }

    protected HttpResponseMessage printSuccess(HttpRequestMessage<?> req, HttpStatus status, Object responseContent){
        return req.createResponseBuilder(status)
                .header("Content-Type", "application/json")
                .body(this.stringify(responseContent))
                .build();
    }
}
