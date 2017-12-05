%%
%{
  private void echo () { System . out . print (yytext ()); }

  public int position () { return yycolumn; }
%}

%class    MicroScalaLexer
%function nextToken
%type	  Token
%unicode
%line
%column
%eofval{
  { return new Token (TokenClass . EOF); }
%eofval}

Comment	   = "//".*
WhiteSpace = [ \t\n]
Identifier = [:letter:]("_"?([:letter:] | [:digit:]))*
Integer    = [:digit:] [:digit:]*

%%
{Comment}	{ echo (); }
{WhiteSpace}	{ echo (); }
";"		{ echo (); return new Token (TokenClass . SEMICOLON); }
":"		{ echo (); return new Token (TokenClass . COLON); }
"."		{ echo (); return new Token (TokenClass . PERIOD); }
","		{ echo (); return new Token (TokenClass . COMMA); }
"{"		{ echo (); return new Token (TokenClass . LBRACE); }
"}"		{ echo (); return new Token (TokenClass . RBRACE); }
"["		{ echo (); return new Token (TokenClass . LBRACK); }
"]"		{ echo (); return new Token (TokenClass . RBRACK); }
"||"		{ echo (); return new Token (TokenClass . OR); }
"&&"		{ echo (); return new Token (TokenClass . AND); }
"!"		{ echo (); return new Token (TokenClass . NOT); }
"<"		{ echo (); return new Token (TokenClass . LT, "<"); }
"<="		{ echo (); return new Token (TokenClass . LE, "<="); }
">"		{ echo (); return new Token (TokenClass . GT, ">"); }
">="		{ echo (); return new Token (TokenClass . GE, ">="); }
"=="		{ echo (); return new Token (TokenClass . EQ, "=="); }
"!="		{ echo (); return new Token (TokenClass . NE, "!="); }
"::"		{ echo (); return new Token (TokenClass . CONS); }
"("		{ echo (); return new Token (TokenClass . LPAREN); }
")"		{ echo (); return new Token (TokenClass . RPAREN); }
"+"		{ echo (); return new Token (TokenClass . PLUS, "+"); }
"-"		{ echo (); return new Token (TokenClass . MINUS, "-"); }
"*"		{ echo (); return new Token (TokenClass . TIMES, "*"); }
"/"		{ echo (); return new Token (TokenClass . SLASH, "/"); }
"%"		{ echo (); return new Token (TokenClass . MOD, "%"); }
"="		{ echo (); return new Token (TokenClass . ASSIGN); }
args		{ echo (); return new Token (TokenClass . ARGS); }
Array		{ echo (); return new Token (TokenClass . ARRAY); }
def		{ echo (); return new Token (TokenClass . DEF); }
else		{ echo (); return new Token (TokenClass . ELSE); }
head		{ echo (); return new Token (TokenClass . HEAD); }
if		{ echo (); return new Token (TokenClass . IF); }
Int		{ echo (); return new Token (TokenClass . INT); }
isEmpty		{ echo (); return new Token (TokenClass . HEAD); }
List		{ echo (); return new Token (TokenClass . LIST); }
main		{ echo (); return new Token (TokenClass . MAIN); }
Nil		{ echo (); return new Token (TokenClass . NIL); }
object		{ echo (); return new Token (TokenClass . OBJ); }
println		{ echo (); return new Token (TokenClass . PRINTLN); }
return		{ echo (); return new Token (TokenClass . RETURN); }
String		{ echo (); return new Token (TokenClass . STRING); }
tail		{ echo (); return new Token (TokenClass . TAIL); }
var		{ echo (); return new Token (TokenClass . VAR); }
while		{ echo (); return new Token (TokenClass . WHILE); }
{Integer}	{ echo (); return new Token (TokenClass . INTEGER, yytext ()); }
{Identifier}	{ echo (); return new Token (TokenClass . ID, yytext ()); }
.		{ echo (); ErrorMessage . print (yychar, "Illegal character"); }
