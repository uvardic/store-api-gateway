package store.server.gateway.exception.handler;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.servlet.GraphQLErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        List<GraphQLError> errorOutput = new ArrayList<>();

        errorOutput.addAll(filterClientErrors(errors));
        errorOutput.addAll(filterServerErrors(errors));

        return errorOutput;
    }

    private List<GraphQLError> filterClientErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(error -> error instanceof Throwable)
                .collect(Collectors.toList());
    }

    private List<GraphQLError> filterServerErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(error -> !(error instanceof Throwable))
                .collect(Collectors.toList());
    }

    private static class GraphQLErrorAdapter implements GraphQLError {

        private final GraphQLError error;

        private GraphQLErrorAdapter(GraphQLError error) {
            this.error = error;
        }

        @Override
        public String getMessage() {
            return error.getMessage();
        }

        @Override
        public List<SourceLocation> getLocations() {
            return error.getLocations();
        }

        @Override
        public ErrorType getErrorType() {
            return error.getErrorType();
        }

        @Override
        public List<Object> getPath() {
            return error.getPath();
        }

        @Override
        public Map<String, Object> toSpecification() {
            return error.toSpecification();
        }

        @Override
        public Map<String, Object> getExtensions() {
            return error.getExtensions();
        }

    }

}
