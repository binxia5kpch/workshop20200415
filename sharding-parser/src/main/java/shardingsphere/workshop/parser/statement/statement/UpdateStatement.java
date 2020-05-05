
package shardingsphere.workshop.parser.statement.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shardingsphere.workshop.parser.statement.ASTNode;
import shardingsphere.workshop.parser.statement.segment.*;

/**
 * Use statement.
 *
 * @author panjuan
 */
@RequiredArgsConstructor
@Getter
public final class UpdateStatement implements ASTNode {

    private final SetAssignmentsClauseSegment setAssignmentsClauseSegment;

    private final TableNameSegment tableName;

    private final WhereConditionsSegment whereConditions;
}
