package br.speck.valuewallet.api.transactions.get.adapter.trigger;

import br.speck.valuewallet.api.transactions.get.SpringBootFunctionConfig;
import br.speck.valuewallet.api.transactions.get.application.constants.RouteTemplates;
import br.speck.valuewallet.api.transactions.get.application.dto.GetTransactionDetailsDTO;
import br.speck.valuewallet.api.transactions.get.application.dto.ListTransactionDTO;
import br.speck.valuewallet.api.transactions.get.application.dto.TransactionDetailsResponseDTO;
import br.speck.valuewallet.api.transactions.get.application.service.GetDetailsTransactionService;
import br.speck.valuewallet.api.transactions.get.application.service.ListTransactionService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Azure Functions with HTTP Trigger.
 */
@Component
public class HttpTriggerJava extends AbstractHttpTriggerJava{
    /**
     * This function listens at endpoint "/api/HttpTriggerJava". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTriggerJava
     * 2. curl {your host}/api/HttpTriggerJava?name=HTTP%20Query
     */
    @FunctionName("list")
    public HttpResponseMessage list(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION, route = RouteTemplates.LIST_TRANSACTIONS_ROUTE)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context
    ) {
        return executeSafely(
                request,
                RouteTemplates.LIST_TRANSACTIONS_ROUTE,
                context,
                ListTransactionService.class,
                () -> new ListTransactionDTO(this.getPageableParams(request))
        );
    }

    @FunctionName("getDetails")
    public HttpResponseMessage getDetails(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.FUNCTION,
                    route = RouteTemplates.GET_DETAILS_TRANSACTIONS_ROUTE
            )
            HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) throws ClassNotFoundException {

        this.logHttpRequest(request, context, RouteTemplates.GET_DETAILS_TRANSACTIONS_ROUTE);

        GetDetailsTransactionService service = SpringBootFunctionConfig.getBean(GetDetailsTransactionService.class);
        TransactionDetailsResponseDTO transactionDetails = service.execute(new GetTransactionDetailsDTO(id));

        return this.printSuccess(request, HttpStatus.OK, transactionDetails);
    }

}
