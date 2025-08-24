package ru.leppla.RequestAnalytics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.leppla.RequestAnalytics.dto.JsonRequestDTO;
import ru.leppla.RequestAnalytics.dto.TextRequestDTO;
import ru.leppla.RequestAnalytics.entity.*;
import ru.leppla.RequestAnalytics.repository.RequestRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void handleTextRequest(TextRequestDTO dto) throws URISyntaxException {
        URI uri = new URI(dto.getUrl());

        Request request = new Request();
        request.setUrl(uri.getHost());
        request.setPath(uri.getPath());
        request.setBody(null);
        request.setRequestTime(LocalDateTime.now());

        requestRepository.save(request);
    }

    public void handleJsonRequest(JsonRequestDTO dto) {
        try {
            URI uri = new URI(dto.getUrl());

            Request request = new Request();
            request.setUrl(uri.getHost());
            request.setPath(dto.getPath());
            request.setBody(dto.getBody());
            request.setRequestTime(LocalDateTime.now());

            if (dto.getHeaders() != null) {
                for (Map.Entry<String, String> entry : dto.getHeaders().entrySet()) {
                    Header header = new Header();
                    header.setHeaderName(entry.getKey());
                    header.setHeaderValue(entry.getValue());
                    header.setRequest(request);
                    request.getHeaders().add(header);
                }
            }

            if (dto.getVariable_params() != null) {
                for (Map.Entry<String, String> entry : dto.getVariable_params().entrySet()) {
                    Param param = new Param();
                    param.setParamName(entry.getKey());
                    param.setParamValue(entry.getValue());
                    param.setRequest(request);
                    request.getParams().add(param);
                }
            }

            requestRepository.save(request);
        } catch (URISyntaxException e) {
            logger.warn("Некорректный URL в JSON-запросе: {}", dto.getUrl(), e);
            throw new RuntimeException("Недопустимый URL-адрес в запросе JSON: " + dto.getUrl(), e);
        }
    }
}