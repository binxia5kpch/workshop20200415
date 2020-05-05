
package shardingsphere.workshop.parser.statement.segment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shardingsphere.workshop.parser.statement.ASTNode;

import java.util.List;

/**
 * Identifier segment.
 */
@RequiredArgsConstructor
@Getter
public final class AssignmentValuesSegment implements ASTNode {
    
    private final List<AssignmentValueSegment> assignmentValues;
}
