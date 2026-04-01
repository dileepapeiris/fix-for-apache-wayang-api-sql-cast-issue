/*
 * Dileepa Peiris - [https://github.com/dileepapeiris] 
 */


package org.apache.wayang.api.sql.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.wayang.api.sql.calcite.converter.functions.SqlRuntimeCast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Documents which {@link SqlTypeName} targets {@link SqlRuntimeCast} handles today. Every enum constant
 * must appear in either {@link #SUPPORTED_TARGETS} or {@link #UNSUPPORTED_TARGETS}; otherwise this
 * test fails after Calcite adds a new type name, prompting an update to the cast implementation and docs.
 */
class SqlRuntimeCastTypeCoverageTest {

    /** Targets implemented in {@link SqlRuntimeCast#castValue(Object, SqlTypeName)}. */
    static final EnumSet<SqlTypeName> SUPPORTED_TARGETS = EnumSet.of(
            SqlTypeName.BOOLEAN,
            SqlTypeName.TINYINT,
            SqlTypeName.SMALLINT,
            SqlTypeName.INTEGER,
            SqlTypeName.BIGINT,
            SqlTypeName.DECIMAL,
            SqlTypeName.FLOAT,
            SqlTypeName.REAL,
            SqlTypeName.DOUBLE,
            SqlTypeName.CHAR,
            SqlTypeName.VARCHAR);

}
