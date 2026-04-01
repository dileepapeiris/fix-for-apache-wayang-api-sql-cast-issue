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
        final JavaTypeFactoryImpl typeFactory = new JavaTypeFactoryImpl();
        final RexBuilder rexBuilder = new RexBuilder(typeFactory);
        final RelDataType intType = typeFactory.createSqlType(SqlTypeName.INTEGER);
        final RelDataType varcharType = typeFactory.createSqlType(SqlTypeName.VARCHAR, 255);

        final RexNode column = rexBuilder.makeInputRef(intType, 0);
        final RexNode castToVarchar = rexBuilder.makeCast(varcharType, column);
        final RexNode literal = rexBuilder.makeLiteral("1", varcharType, false);
        final RexNode condition = rexBuilder.makeCall(SqlStdOperatorTable.EQUALS, castToVarchar, literal);

        final FilterPredicateImpl predicate = new FilterPredicateImpl(condition);

        System.out.println("CAST(int_col AS VARCHAR) = '1'");
        System.out.println("  row (1,) -> " + predicate.test(new Record(1)));
        System.out.println("  row (2,) -> " + predicate.test(new Record(2)));
    }
}
