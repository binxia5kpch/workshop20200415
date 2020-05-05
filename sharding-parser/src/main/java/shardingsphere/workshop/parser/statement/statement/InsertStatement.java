
package shardingsphere.workshop.parser.statement.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shardingsphere.workshop.parser.statement.ASTNode;
import shardingsphere.workshop.parser.statement.segment.AssignmentValuesSegment;
import shardingsphere.workshop.parser.statement.segment.ColumnNamesSegment;
import shardingsphere.workshop.parser.statement.segment.IdentifierSegment;
import shardingsphere.workshop.parser.statement.segment.TableNameSegment;

/**
 * Use statement.
 *
 * @author panjuan
 */
@RequiredArgsConstructor
@Getter
public final class InsertStatement implements ASTNode {
    
    private final ColumnNamesSegment columnNames;

    private final TableNameSegment tableName;

    private final AssignmentValuesSegment assignmentValues;
}
