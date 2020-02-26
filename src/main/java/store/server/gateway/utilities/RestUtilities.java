package store.server.gateway.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import store.server.gateway.exception.DefaultGraphQLError;
import store.server.gateway.exception.handler.CustomResponseErrorHandler;

import java.util.LinkedHashMap;

public class RestUtilities {

    private RestUtilities() {}

    public static void handleErrors(ResponseEntity<?> responseEntity, Class<? extends DefaultGraphQLError> errorClass) {
        if (unexpectedStatusCode(responseEntity.getStatusCode()))
            return;

        LinkedHashMap<?, ?> responseBody = (LinkedHashMap<?, ?>) responseEntity.getBody();

        try {
            throw errorClass
                    .getDeclaredConstructor(String.class, HttpStatus.class)
                    .newInstance(getMessage(responseBody), responseEntity.getStatusCode());
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean unexpectedStatusCode(HttpStatus statusCode) {
        return !CustomResponseErrorHandler.HANDLED_HTTP_ERROR_CODES.contains(statusCode);
    }

    private static String getMessage(LinkedHashMap<?, ?> responseBody) {
        return (String) (responseBody != null ? responseBody.get("message") : "No message provided!");
    }

}
