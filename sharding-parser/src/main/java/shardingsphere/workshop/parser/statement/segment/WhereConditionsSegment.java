
package shardingsphere.workshop.parser.statement.segment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shardingsphere.workshop.parser.statement.ASTNode;

/**
 * WhereConditionsSegment
 * 条件类
 */
@RequiredArgsConstructor
@Getter
public final class WhereConditionsSegment implements ASTNode {
    
    private final ColumnNameSegment columnName;

    private final AssignmentValueSegment assignmentValue;
}
