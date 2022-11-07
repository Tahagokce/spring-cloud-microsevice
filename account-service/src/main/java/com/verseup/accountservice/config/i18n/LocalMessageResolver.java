package com.verseup.accountservice.config.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LocalMessageResolver {

    private static final String I18N_LOCATION_PATTERN = "classpath*:/i18n/messages/message_*.properties";
    private static final String DELIMITER_SCORE = "_";
    private static final String DELIMITER_POINT = ".";
    private static final Map<String, Properties> messageProperties = new HashMap<>();

    protected LocalMessageResolver() {
        loadMessages();
    }

    private void loadMessages() {
        ResourcePatternResolver pathMatchingResourcePatternResolver
                = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = pathMatchingResourcePatternResolver.getResources(I18N_LOCATION_PATTERN);
            if (resources.length > 0) {
                Arrays.stream(resources).forEach(r -> {
                    String[] localeSplit = StringUtils.split(r.getFilename(), DELIMITER_SCORE);
                    if (localeSplit != null && localeSplit.length > 1) {
                        String[] locale = StringUtils.split(localeSplit[1], DELIMITER_POINT);
                        if (locale != null && locale.length > 1) {
                            addToMessageProperties(locale[0], r);
                        }
                    }
                });
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    private void addToMessageProperties(String lang, Resource resource) {
        Properties property;
        try {
            property = PropertiesLoaderUtils.loadProperties(new EncodedResource(resource, Charset.defaultCharset()));
            if (!property.isEmpty()) {
                messageProperties.putIfAbsent(lang, property);
            }
        } catch (IOException e) {
            log.error("", e);
        }

    }

    public String getMessage(String locale, String key, Object... params) {
        String message = "";
        if (!messageProperties.isEmpty() && !StringUtils.isEmpty(key)) {
            Properties property = messageProperties.get(locale);
            if (property != null) {
                message = property.getProperty(key);
                if (!StringUtils.isEmpty(message) && message.contains(":param:")) {
                    message = bindParameters(message, params);
                }
            }
        }
        return message;
    }

    private String bindParameters(String message, Object... params) {
        Matcher matcher = Pattern
                .compile(":param:")
                .matcher(message);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (matcher.find()) {
            matcher.appendReplacement(sb, String.valueOf(params[i++]));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}

