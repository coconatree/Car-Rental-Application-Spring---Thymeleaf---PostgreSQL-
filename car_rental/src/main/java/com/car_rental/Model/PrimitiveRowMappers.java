package com.car_rental.Model;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrimitiveRowMappers {

    public static RowMapper<String> GetStringRowMapper() {
        return new StringRowMapper();
    }

    public static RowMapper<Integer> GetIntegerRowMapper() {
        return new IntegerRowMapper();
    }

    public static RowMapper<Boolean> GetBooleanRowMapper() {
        return new BooleanRowMapper();
    }

    private static class StringRowMapper implements RowMapper<String> {

        @Nullable
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString(1);
        }
    }

    private static class IntegerRowMapper implements RowMapper<Integer> {
        @Nullable
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt(1);
        }
    }

    private static class BooleanRowMapper implements RowMapper<Boolean> {
        @Nullable
        @Override
        public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getBoolean(1);
        }
    }
}
