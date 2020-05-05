
package shardingsphere.workshop.parser.statement.segment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shardingsphere.workshop.parser.statement.ASTNode;

/**
 * ColumnNameSegment columnName
 */
@RequiredArgsConstructor
@Getter
public final class ColumnNameSegment implements ASTNode {
    
    private final IdentifierSegment identifier;
}
