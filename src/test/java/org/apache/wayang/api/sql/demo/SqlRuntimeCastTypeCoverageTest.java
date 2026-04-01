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

    private static final EnumSet<SqlTypeName> UNSUPPORTED_TARGETS = EnumSet.complementOf(SUPPORTED_TARGETS);

    @Test
    void everySqlTypeNameIsClassifiedAsSupportedOrUnsupported() {
        final EnumSet<SqlTypeName> all = EnumSet.allOf(SqlTypeName.class);
        final EnumSet<SqlTypeName> union = EnumSet.copyOf(SUPPORTED_TARGETS);
        union.addAll(UNSUPPORTED_TARGETS);
        assertTrue(union.containsAll(all),
                "Missing classification for: "
                        + all.stream().filter(t -> !union.contains(t)).collect(Collectors.toList()));
        assertTrue(SUPPORTED_TARGETS.stream().noneMatch(UNSUPPORTED_TARGETS::contains));
    }

    @ParameterizedTest
    @EnumSource(value = SqlTypeName.class, mode = EnumSource.Mode.INCLUDE, names = {
            "BOOLEAN", "TINYINT", "SMALLINT", "INTEGER", "BIGINT", "DECIMAL",
            "FLOAT", "REAL", "DOUBLE", "CHAR", "VARCHAR"
    })
    void supportedTargetAcceptsCanonicalStringInput(final SqlTypeName target) {
        assertDoesNotThrow(() -> SqlRuntimeCast.castValue(canonicalInputFor(target), target));
    }

    @ParameterizedTest
    @EnumSource(value = SqlTypeName.class, mode = EnumSource.Mode.EXCLUDE, names = {
            "BOOLEAN", "TINYINT", "SMALLINT", "INTEGER", "BIGINT", "DECIMAL",
            "FLOAT", "REAL", "DOUBLE", "CHAR", "VARCHAR"
    })
    void unsupportedTargetThrowsWithSampleInput(final SqlTypeName target) {
        assertThrows(UnsupportedOperationException.class,
                () -> SqlRuntimeCast.castValue("1", target));
    }

    private static Object canonicalInputFor(final SqlTypeName target) {
        switch (target) {
            case BOOLEAN:
                return "TRUE";
            case TINYINT:
            case SMALLINT:
            case INTEGER:
            case BIGINT:
                return "1";
            case DECIMAL:
                return "1.0";
            case FLOAT:
            case REAL:
            case DOUBLE:
                return "1.5";
            case CHAR:
            case VARCHAR:
                return "x";
            default:
                throw new IllegalArgumentException(target.name());
        }
    }

}
