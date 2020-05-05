
package shardingsphere.workshop.parser.statement.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shardingsphere.workshop.parser.statement.ASTNode;
import shardingsphere.workshop.parser.statement.segment.AssignmentValuesSegment;
import shardingsphere.workshop.parser.statement.segment.ColumnNamesSegment;
import shardingsphere.workshop.parser.statement.segment.TableNameSegment;
import shardingsphere.workshop.parser.statement.segment.WhereConditionsSegment;

/**
 * Use statement.
 *
 * @author panjuan
 */
@RequiredArgsConstructor
@Getter
public final class DeleteStatement implements ASTNode {

    private final TableNameSegment tableName;

    private final WhereConditionsSegment whereConditions;
}
