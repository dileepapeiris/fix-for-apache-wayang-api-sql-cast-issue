/*
 * Dileepa Peiris - [https://github.com/dileepapeiris] 
 */

package org.apache.wayang.api.sql.demo;

import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.wayang.api.sql.calcite.converter.functions.FilterPredicateImpl;
import org.apache.wayang.basic.data.Record;

/**
 * Minimal end-to-end use of {@link FilterPredicateImpl} with a {@code CAST} in the predicate
 * (integer column to {@code VARCHAR}, compared to a literal). Run with:
 * {@code mvn -pl wayang-api/wayang-api-sql-cast-demo exec:java}
 */
public final class SqlCastFilterDemo {

    private SqlCastFilterDemo() {}

    /**
     * @param args unused
     */
    public static void main(final String[] args) {
    }
}
