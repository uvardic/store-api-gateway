package store.server.gateway.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultGraphQLError extends RuntimeException implements GraphQLError {

    private final Map<String, Object> extensions = new HashMap<>();

    protected DefaultGraphQLError(String message) {
        super(message, null, false, false);
    }

    protected void addExtension(String name, Object value) {
        extensions.put(name, value);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

}
