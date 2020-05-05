
package shardingsphere.workshop.parser.engine.visitor;

import autogen.MySQLStatementBaseVisitor;
import autogen.MySQLStatementParser;
import autogen.MySQLStatementParser.IdentifierContext;
import autogen.MySQLStatementParser.SchemaNameContext;
import autogen.MySQLStatementParser.UseContext;
import shardingsphere.workshop.parser.statement.ASTNode;
import shardingsphere.workshop.parser.statement.segment.*;
import shardingsphere.workshop.parser.statement.statement.*;

import java.util.ArrayList;
import java.util.List;

/**
 * MySQL visitor.
 */
public final class SQLVisitor extends MySQLStatementBaseVisitor<ASTNode> {
    
    @Override
    public ASTNode visitUse(final UseContext ctx) {
        SchemeNameSegment schemeName = (SchemeNameSegment) visit(ctx.schemaName());
        return new UseStatement(schemeName);
    }
    
    @Override
    public ASTNode visitSchemaName(final SchemaNameContext ctx) {
        IdentifierSegment identifier = (IdentifierSegment) visit(ctx.identifier());
        return new SchemeNameSegment(identifier);
    }
    
    @Override
    public ASTNode visitIdentifier(final IdentifierContext ctx) {
        return new IdentifierSegment(ctx.getText());
    }

    @Override
    public ASTNode visitSelect(final MySQLStatementParser.SelectContext ctx) {
        SelectColumnsSegment selectColumnsSegment = (SelectColumnsSegment)visit(ctx.selectColumnNames());
        TableNameSegment tableName = (TableNameSegment)visit(ctx.tableName());
        WhereConditionsSegment whereConditionsSegment = (WhereConditionsSegment) visit(ctx.whereConditions());
        return new SelectStatement(selectColumnsSegment, tableName,whereConditionsSegment);
    }

    public ASTNode visitInsert(MySQLStatementParser.InsertContext ctx) {
        ColumnNamesSegment columnsSegment = null;
        if(null != ctx.columnNames()){
            columnsSegment = (ColumnNamesSegment) visit(ctx.columnNames());
        }
        TableNameSegment tableNameSegment = (TableNameSegment) visit(ctx.tableName());
        AssignmentValuesSegment assignmentValuesSegment = (AssignmentValuesSegment) visit(ctx.assignmentValues());
        return new InsertStatement(columnsSegment,tableNameSegment,assignmentValuesSegment);
    }


    @Override
    public ASTNode visitColumnNames(MySQLStatementParser.ColumnNamesContext ctx) {
        List<ColumnNameSegment> columnNames = new ArrayList();
        for (MySQLStatementParser.ColumnNameContext each : ctx.columnName()) {
            ColumnNameSegment columnNameSegment = (ColumnNameSegment) visit(each);
            if(null != columnNameSegment){
                columnNames.add(columnNameSegment);
            }
        }
        return new ColumnNamesSegment(columnNames);
    }

    @Override
    public ASTNode visitColumnName(MySQLStatementParser.ColumnNameContext ctx) {
        IdentifierSegment identifier = (IdentifierSegment) visit(ctx.identifier());
        return new ColumnNameSegment(identifier);
    }

    @Override
    public ASTNode visitTableName(MySQLStatementParser.TableNameContext ctx) {
        IdentifierSegment identifier = (IdentifierSegment) visit(ctx.identifier());
        return new TableNameSegment(identifier);
    }

    @Override
    public ASTNode visitAssignmentValues(MySQLStatementParser.AssignmentValuesContext ctx) {
        List<AssignmentValueSegment> assignmentValueSegments = new ArrayList<>();
        for (MySQLStatementParser.AssignmentValueContext each : ctx.assignmentValue()) {
            AssignmentValueSegment assignmentValueSegment = (AssignmentValueSegment) visit(each);
            if(null != assignmentValueSegment){
                assignmentValueSegments.add(assignmentValueSegment);
            }
        }
        return new AssignmentValuesSegment(assignmentValueSegments);
    }

    @Override
    public ASTNode visitAssignmentValue(MySQLStatementParser.AssignmentValueContext ctx) {
        IdentifierSegment identifier = (IdentifierSegment) visit(ctx.identifier());
        return new AssignmentValueSegment(identifier);
    }

    @Override
    public ASTNode visitSelectColumnNames(MySQLStatementParser.SelectColumnNamesContext ctx) {
        //单独处理*的逻辑
        IdentifierSegment identifierSegment = new IdentifierSegment(ctx.getText());
        if(null != ctx.ASTERISK_()){
            identifierSegment = new IdentifierSegment(ctx.getText());
        }
        //处理多列逻辑
        List<ColumnNameSegment> columnNames = new ArrayList();
        for (MySQLStatementParser.ColumnNameContext each : ctx.columnName()) {
            ColumnNameSegment columnNameSegment = (ColumnNameSegment) visit(each);
            if(null != columnNameSegment){
                columnNames.add(columnNameSegment);
            }
        }
        return new SelectColumnsSegment(columnNames,identifierSegment);
    }

    @Override
    public ASTNode visitWhereConditions(MySQLStatementParser.WhereConditionsContext ctx) {
        ColumnNameSegment columnNameSegment = (ColumnNameSegment)visit(ctx.columnName());
        AssignmentValueSegment assignmentValueSegment = (AssignmentValueSegment)visit(ctx.assignmentValue());
        return new WhereConditionsSegment(columnNameSegment,assignmentValueSegment);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitAssignment(MySQLStatementParser.AssignmentContext ctx) {
        ColumnNameSegment columnNameSegment = (ColumnNameSegment)visit(ctx.columnName());
        AssignmentValueSegment assignmentValueSegment = (AssignmentValueSegment)visit(ctx.assignmentValue());
        return new AssignmentSegment(columnNameSegment,assignmentValueSegment);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitSetAssignmentsClause(MySQLStatementParser.SetAssignmentsClauseContext ctx) {
        List<AssignmentSegment> setAssignments = new ArrayList<>();
        for(MySQLStatementParser.AssignmentContext assignmentContext : ctx.assignment()){
            AssignmentSegment assignmentSegment = (AssignmentSegment)visit(assignmentContext);
            setAssignments.add(assignmentSegment);
        }
        return new SetAssignmentsClauseSegment(setAssignments);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitUpdate(MySQLStatementParser.UpdateContext ctx) {
        SetAssignmentsClauseSegment setAssignmentsClauseSegment = (SetAssignmentsClauseSegment)visit(ctx.setAssignmentsClause());
        TableNameSegment tableNameSegment = (TableNameSegment)visit(ctx.tableName());
        WhereConditionsSegment whereConditionsSegment = (WhereConditionsSegment) visit(ctx.whereConditions());
        return new UpdateStatement(setAssignmentsClauseSegment,tableNameSegment,whereConditionsSegment);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitDelete(MySQLStatementParser.DeleteContext ctx) {
        TableNameSegment tableNameSegment = (TableNameSegment)visit(ctx.tableName());
        WhereConditionsSegment whereConditionsSegment = (WhereConditionsSegment) visit(ctx.whereConditions());
        return new DeleteStatement(tableNameSegment,whereConditionsSegment);
    }
}
