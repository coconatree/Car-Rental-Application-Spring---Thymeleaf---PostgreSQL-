package com.car_rental.Configuration;

import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Configuration
public class Utility {

    public static Optional<List<String>> extractParameterAsStringArray(HttpServletRequest request, String key) {
        try {
            return Optional.of(List.of(request.getParameterMap().get(key)));
        }
        catch (Exception err) {
            return Optional.empty();
        }
    }

    public static Optional<String> extractParameterAsString(HttpServletRequest request, String key) {
        try {
            return Optional.of(Arrays.stream(request.getParameterMap().get(key))
                    .reduce((string1, string2) -> string1 + string2).orElseThrow());
        }
        catch (Exception err) {
            return Optional.empty();
        }
    }

    public static Optional<Integer> extractParameterAsInteger(HttpServletRequest request, String key) {
        try {
            return Optional.of(Integer.valueOf(Arrays.stream(request.getParameterMap().get(key))
                    .reduce((string1, string2) -> string1 + string2).orElseThrow()));
        }
        catch (Exception err) {
            return Optional.empty();
        }
    }

    public static Optional<Date> extractParameterAsDate(HttpServletRequest request, String key) {
        try {
            DateConverter dateConverter = new DateConverter();
            Date convertedDate = dateConverter.convert(request.getParameterMap().get(key)[0]);

            if (convertedDate == null) {
                return Optional.empty();
            }

            return Optional.of(convertedDate);
        }
        catch (Exception err) {
            return Optional.empty();
        }
    }
}
