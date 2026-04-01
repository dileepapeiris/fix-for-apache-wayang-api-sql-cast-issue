/*
 * Dileepa Peiris - [https://github.com/dileepapeiris] 
 */

package org.apache.wayang.api.sql.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.wayang.api.sql.calcite.converter.functions.FilterPredicateImpl;
import org.apache.wayang.basic.data.Record;
import org.junit.jupiter.api.Test;

class SqlCastFilterDemoTest {

    @Test
    void filterPredicateMatchesCastIntToVarcharEqualsLiteral() {
        final JavaTypeFactoryImpl typeFactory = new JavaTypeFactoryImpl();
        final RexBuilder rexBuilder = new RexBuilder(typeFactory);
        final RelDataType intType = typeFactory.createSqlType(SqlTypeName.INTEGER);
        final RelDataType varcharType = typeFactory.createSqlType(SqlTypeName.VARCHAR, 255);

        final RexNode column = rexBuilder.makeInputRef(intType, 0);
        final RexNode castToVarchar = rexBuilder.makeCast(varcharType, column);
        final RexNode literal = rexBuilder.makeLiteral("1", varcharType, false);
        final RexNode condition = rexBuilder.makeCall(SqlStdOperatorTable.EQUALS, castToVarchar, literal);

        final FilterPredicateImpl predicate = new FilterPredicateImpl(condition);

        assertTrue(predicate.test(new Record(1)));
        assertFalse(predicate.test(new Record(2)));
    }
}
