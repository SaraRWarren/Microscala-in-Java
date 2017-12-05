%%
%{
  private void echo () { System . out . print (yytext ()); }

  public int position () { return yycolumn; }
%}

%class    CliteLexer
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
Boolean    = true | false
Float 	   = {Integer}"."{Integer}
Char	   = "'"."'"


%%
{Comment}	{ echo (); }
{WhiteSpace}	{ echo (); }
","		{ echo (); return new Token (TokenClass . COMMA); }
";"		{ echo (); return new Token (TokenClass . SEMICOLON); }
"{"		{ echo (); return new Token (TokenClass . LBRACE); }
"}"		{ echo (); return new Token (TokenClass . RBRACE); }
"("		{ echo (); return new Token (TokenClass . LPAREN); }
")"		{ echo (); return new Token (TokenClass . RPAREN); }
"["		{ echo (); return new Token (TokenClass . LBRACK); }
"]"		{ echo (); return new Token (TokenClass . RBRACK); }
"="		{ echo (); return new Token (TokenClass . ASSIGN); }
"||"		{ echo (); return new Token (TokenClass . OR); }
"&&"		{ echo (); return new Token (TokenClass . AND); }
"+"		{ echo (); return new Token (TokenClass . PLUS); }
"-"		{ echo (); return new Token (TokenClass . MINUS); }
"*"		{ echo (); return new Token (TokenClass . TIMES); }
"/"		{ echo (); return new Token (TokenClass . SLASH); }
"%"		{ echo (); return new Token (TokenClass . MOD); }
"=="		{ echo (); return new Token (TokenClass . EQ); }
"!="		{ echo (); return new Token (TokenClass . NE); }
"<"		{ echo (); return new Token (TokenClass . LT); }
"<="		{ echo (); return new Token (TokenClass . LE); }
">"		{ echo (); return new Token (TokenClass . GT); }
">="		{ echo (); return new Token (TokenClass . GE); }
"!"		{ echo (); return new Token (TokenClass . NOT); }
"."             { echo (); return new Token (TokenClass . ACCESSOR); }
":"             { echo (); return new Token (TokenClass . AMPERSAND); }

bool		{ echo (); return new Token (TokenClass . BOOL); }
char		{ echo (); return new Token (TokenClass . CHAR); }
else		{ echo (); return new Token (TokenClass . ELSE); }
float		{ echo (); return new Token (TokenClass . FLOAT); }
if		{ echo (); return new Token (TokenClass . IF); }
Int		{ echo (); return new Token (TokenClass . INT); }
main		{ echo (); return new Token (TokenClass . MAIN); }
while		{ echo (); return new Token (TokenClass . WHILE); }
object 		{ echo (); return new Token (TokenClass . OBJ); }
def 		{ echo (); return new Token (TokenClass . DEF); }
List            { echo (); return new Token (TokenClass . LIST); }
println         { echo (); return new Token (TokenClass . PRINTLN); }
head            { echo (); return new Token (TokenClass . HEAD); }
tail            { echo (); return new Token (TokenClass . TAIL); }
isEmpty         { echo (); return new Token (TokenClass . ISEMPTY); }
Nil             { echo (); return new Token (TokenClass . NIL); }
Array           { echo (); return new Token (TokenClass . ARRAY); }
String          { echo (); return new Token (TokenClass . STRING); }

{Integer}	{ echo (); return new Token (TokenClass . INTEGER, yytext ()); }
{Boolean}	{ echo (); return new Token (TokenClass . BOOLEAN, yytext ()); }
{Float}		{ echo (); return new Token (TokenClass . FLOATLIT, yytext ()); }
{Char}		{ echo (); 
		  return new Token (TokenClass . CHARLIT, yytext () . substring (1, 2)); }
{Identifier}	{ echo (); return new Token (TokenClass . ID, yytext ()); }
.		{ echo (); ErrorMessage . print (yychar, "Illegal character"); }
