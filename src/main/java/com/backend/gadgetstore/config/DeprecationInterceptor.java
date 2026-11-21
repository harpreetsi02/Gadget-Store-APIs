package com.backend.gadgetstore.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DeprecationInterceptor implements HandlerInterceptor {

    private final String deprecationValue;
    private final String sunsetValue;

    public DeprecationInterceptor(
            @Value("${app.api.v1.deprecated-on}") String deprecatedOn,
            @Value("${app.api.v1.sunset-date}") String sunsetDate
    ) {
        long deprecatedEpoch = LocalDate.parse(deprecatedOn)
                .atStartOfDay(ZoneOffset.UTC).toEpochSecond();
        this.deprecationValue = "@" + deprecatedEpoch;

        ZonedDateTime sunset = LocalDate.parse(sunsetDate)
                .atStartOfDay(ZoneOffset.UTC).plusDays(1).minusSeconds(1);
        this.sunsetValue = DateTimeFormatter.RFC_1123_DATE_TIME.format(sunset);
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        response.setHeader("Deprecation", deprecationValue);
        response.setHeader("Sunset", sunsetValue);

        String successor = request.getRequestURI().replace("/api/v1", "/api/v2");
        response.setHeader("Link", "<" + successor + ">; rel=\"successor-version\"");

        return true;
    }
}
