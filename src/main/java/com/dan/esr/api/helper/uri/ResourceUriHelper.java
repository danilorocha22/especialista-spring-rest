package com.dan.esr.api.helper.uri;

import javax.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpHeaders.LOCATION;

@UtilityClass
public class ResourceUriHelper {

    public static void addUriInResponseHeader(Object resourceId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId).toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();

        requireNonNull(response).setHeader(LOCATION, uri.toString());
    }
}