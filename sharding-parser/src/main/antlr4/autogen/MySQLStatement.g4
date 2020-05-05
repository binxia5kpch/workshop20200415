
grammar MySQLStatement;

import Symbol, SQLStatement;

execute
    : (use
    | insert
    | select
    | update
    | delete
    ) SEMI_?
    ;
