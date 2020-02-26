package store.server.gateway.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DefaultGraphQLError extends RuntimeException implements GraphQLError {

    private final HttpStatus statusCode;

    public DefaultGraphQLError(String message, HttpStatus statusCode) {
        super(message);

        this.statusCode = statusCode;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        switch (statusCode) {
            case BAD_REQUEST:
                return ErrorType.ValidationError;
            case UNAUTHORIZED: case FORBIDDEN:
                return ErrorType.OperationNotSupported;
            default:
                return ErrorType.DataFetchingException;
        }
    }

}
