
grammar SQLStatement;

import Symbol, Keyword, Literals;

use
    : USE schemaName
    ;
    
schemaName
    : identifier
    ;
    
insert
    : INSERT INTO? tableName columnNames? VALUE assignmentValues
    ;
  
assignmentValues
    : LP_ assignmentValue (COMMA_ assignmentValue)* RP_
    ;

assignmentValue
    : identifier
    ;
    
columnNames
    : LP_ columnName (COMMA_ columnName)* RP_
    ;

columnName
    : identifier
    ;
   
tableName
    : identifier
    ;
    
identifier
    : IDENTIFIER_ | STRING_ | NUMBER_
    ;

selectColumnNames
    : (columnName (COMMA_ columnName)*) | ASTERISK_
    ;

whereConditions
    : columnName EQ_ assignmentValue
    ;

assignment
    : columnName EQ_ assignmentValue
    ;

setAssignmentsClause
    : SET assignment (COMMA_ assignment)*
    ;

select
    : SELECT selectColumnNames FROM tableName WHERE whereConditions
    ;

update
    : UPDATE tableName setAssignmentsClause WHERE whereConditions
    ;

delete
    : DELETE FROM tableName WHERE whereConditions
    ;