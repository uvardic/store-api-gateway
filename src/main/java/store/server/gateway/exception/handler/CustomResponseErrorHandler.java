package store.server.gateway.exception.handler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import store.server.gateway.exception.DefaultGraphQLError;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

@Slf4j
@Component
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    public static final Set<HttpStatus> HANDLED_HTTP_ERROR_CODES = new HashSet<>();

    static {
        HANDLED_HTTP_ERROR_CODES.add(HttpStatus.BAD_REQUEST);
        HANDLED_HTTP_ERROR_CODES.add(HttpStatus.NOT_FOUND);
        HANDLED_HTTP_ERROR_CODES.add(HttpStatus.UNAUTHORIZED);
        HANDLED_HTTP_ERROR_CODES.add(HttpStatus.FORBIDDEN);
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());

        return (statusCode != null ? hasError(statusCode) : hasError(response.getRawStatusCode()));
    }

    private boolean hasError(HttpStatus statusCode) {
        return statusCode.isError() && !HANDLED_HTTP_ERROR_CODES.contains(statusCode);
    }

    private boolean hasError(int unknownStatusCode) {
        HttpStatus.Series series = HttpStatus.Series.resolve(unknownStatusCode);

        return (series == HttpStatus.Series.CLIENT_ERROR || series == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());

        if (statusCode == null) {
            throw new UnknownHttpStatusCodeException(
                    response.getRawStatusCode(), response.getStatusText(), response.getHeaders(),
                    getResponseBody(response), getCharset(response)
            );
        }

        handleError(response, statusCode);
    }

    private void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        String statusText = response.getStatusText();

        HttpHeaders headers = response.getHeaders();

        byte[] body = getResponseBody(response);

        Charset charset = getCharset(response);

        switch (statusCode.series()) {
            case CLIENT_ERROR:
                throw HttpClientErrorException.create(statusCode, statusText, headers, body, charset);
            case SERVER_ERROR:
                throw HttpServerErrorException.create(statusCode, statusText, headers, body, charset);
            default:
                throw new UnknownHttpStatusCodeException(statusCode.value(), statusText, headers, body, charset);
        }
    }

    private byte[] getResponseBody(ClientHttpResponse response) {
        try {
            return FileCopyUtils.copyToByteArray(response.getBody());
        } catch (IOException ignored) {}

        return new byte[0];
    }

    private Charset getCharset(ClientHttpResponse response) {
        MediaType contentType = response.getHeaders().getContentType();

        return (contentType != null ? contentType.getCharset() : null);
    }

    @SneakyThrows
    public void handleError(ResponseEntity<?> response, Class<? extends DefaultGraphQLError> errorClass) {
        if (isError(response))
            throw errorClass.getDeclaredConstructor(String.class).newInstance(getErrorMessage(response));
    }

    private boolean isError(ResponseEntity<?> response) {
        return HANDLED_HTTP_ERROR_CODES.contains(response.getStatusCode());
    }

    private String getErrorMessage(ResponseEntity<?> response) {
        LinkedHashMap<?, ?> responseBody = (LinkedHashMap<?, ?>) response.getBody();

        return (String) (responseBody != null ? responseBody.get("message") : "No message provided!");
    }

}
