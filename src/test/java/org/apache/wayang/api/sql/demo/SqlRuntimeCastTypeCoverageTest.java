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
