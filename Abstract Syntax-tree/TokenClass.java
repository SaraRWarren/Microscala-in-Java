// TokenClass enumeration definition
// TokenClass is an enumeration to represent lexical token classes in the 
// Clite programming language.

public enum TokenClass {
  EOF, 
  // keywords
  ELSE,IF, INT, MAIN, WHILE, ARGS, VAR, OBJ, LIST, HEAD, TAIL, 
  ISEMPTY, DEF, NIL, PRINTLN, ARRAY, STRING, RETURN,
  // punctuation
  COMMA, SEMICOLON, COLON, LBRACE, RBRACE, ACCESSOR, AMPERSAND, LBRACK, RBRACK, PERIOD,
  // operators
  LPAREN, RPAREN, ASSIGN, OR, AND, EQ, NE, LT, LE, GT, GE, 
  PLUS, MINUS, TIMES, SLASH, MOD, NOT, CONS,
  // ids and literals
  ID, INTEGER, BOOLEAN, FLOATLIT, CHARLIT, VARLIT
}
