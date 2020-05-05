
package shardingsphere.workshop.parser.statement.segment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shardingsphere.workshop.parser.statement.ASTNode;

import java.util.List;

/**
 * SetAssignmentsClauseSegment setAssignmentsClauseSegment
 */
@RequiredArgsConstructor
@Getter
public final class SetAssignmentsClauseSegment implements ASTNode {

    private final List<AssignmentSegment> setAssignments;
}
