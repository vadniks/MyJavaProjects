grammar Lang;

exp
    : item* EOF
    ;

item
    : atom
    | list
    ;

list
    : LP item* RP
    ;

atom
    : BLN
    | NUM
    | STR
    | CMT
    | VAR
    | VDC
    | FUN
    ;

SPC
    : (' ' | '\n' | '\t' | '\r') + -> skip
    ;

NUM
    : DIG+
    ;

STR
    : '"' SYM+ NUM? '"'
    ;

CMT
    : '#' (SYM | NUM | ' ')* '#'
    ;

BLN
    : (TRU | FLS)
    ;

VAR
    : '$' SYM+ NUM?
    ;

VDC
    : VAR '=' (STR | NUM | BLN)
    ;

FUN
    : '@' (FNP | FNM) '[' ((SYM | NUM)+ | VAR) ',' ((SYM | NUM)+ | VAR) ']'
    ;

fragment FNP
    : 'pls'
    ;

fragment FNM
    : 'mns'
    ;

fragment TRU
    : 'true'
    ;

fragment FLS
    : 'false'
    ;

LP
    : '('
    ;

RP
    : ')'
    ;

fragment SYM
    : ('a'..'z') | ('A'..'Z') | '_'
    ;

fragment DIG
   : ('0'..'9')
   ;

