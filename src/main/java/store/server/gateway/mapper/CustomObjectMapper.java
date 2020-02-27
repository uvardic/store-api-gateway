package store.server.gateway.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomObjectMapper {

    private final ObjectMapper objectMapper;

    public <T> T mapResponse(ResponseEntity<?> response, Class<T> toValueClass) {
        return objectMapper.convertValue(response.getBody(), toValueClass);
    }

    public <T> List<T> mapResponseToList(ResponseEntity<?> response, Class<T> toValueClass) {
        return ((List<?>) Objects.requireNonNull(response.getBody()))
                .stream()
                .map(value -> objectMapper.convertValue(value, toValueClass))
                .collect(Collectors.toList());
    }

}
