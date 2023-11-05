package com.dan.esr.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class ResourceUtils {

    private ResourceUtils(){}

    public static String getContentFromResource(String resourcePathName) {
        InputStream stream = ResourceUtils.class.getResourceAsStream(resourcePathName);

        try {
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
