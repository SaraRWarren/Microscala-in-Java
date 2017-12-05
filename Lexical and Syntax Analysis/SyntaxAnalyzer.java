
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

// SyntaxAnalyzer class
// SyntaxAnalyzer is a class to represent a recursive descent parser for the 
// Clite programming language, described in Programming Languages: Principles 
// and Paradigms, 2nd ed., by Allen B. Tucker and Robert E. Noonan, 
// McGraw Hill, 2007.

public class SyntaxAnalyzer {

  protected MicroScalaLexer lexer;	// lexical analyzer
  protected Token token;	// current token

  public SyntaxAnalyzer () throws java.io.IOException {
    lexer = new MicroScalaLexer (new BufferedReader(new InputStreamReader(System.in)));
    getToken ();
  }

    SyntaxAnalyzer(InputStream in) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  private void getToken () throws java.io.IOException { 
    token = lexer . nextToken (); 
  }

  public void program () throws java.io.IOException {
    String functionID;
    boolean isMain;
    if (token . symbol () != TokenClass . OBJ) 
      ErrorMessage . print (lexer . position (), "object EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . ID) 
      ErrorMessage . print (lexer . position (), "id EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . LBRACE)
      ErrorMessage . print (lexer . position (), "{ EXPECTED");
    getToken();
    
    isMain = false;
    while ((token . symbol () == TokenClass . DEF || token . symbol () == TokenClass . VAR) && !isMain)
    {
         if (token . symbol () == TokenClass . DEF) 
         {
             getToken();
             if (token . symbol () == TokenClass . ID)
             {
                 functionID = token.lexeme();
                 getToken();
                 functionDef(functionID);
             }
             else if (token . symbol () == TokenClass . MAIN)
             {
                 getToken();
                 mainDef();
                 isMain  = true;
             }
             else
                 ErrorMessage . print (lexer . position (), "id EXPECTED");
         }
         else 
             declarations();
    }
    if (isMain == false)
        ErrorMessage . print (lexer . position (), "main EXPECTED");
    if (token . symbol () != TokenClass . RBRACE) 
      ErrorMessage . print (lexer . position (), "} EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . EOF) 
      ErrorMessage . print (lexer . position (), "END OF PROGRAM EXPECTED");
    
  }
public void mainDef () throws java.io.IOException 
{
    if (token . symbol () != TokenClass . LPAREN) 
        ErrorMessage . print (lexer . position (), "( EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . ARGS) 
        ErrorMessage . print (lexer . position (), "args EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . COLON) 
        ErrorMessage . print (lexer . position (), ": EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . ARRAY) 
        ErrorMessage . print (lexer . position (), "Array EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . LBRACK) 
        ErrorMessage . print (lexer . position (), "[ EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . STRING) 
        ErrorMessage . print (lexer . position (), "String EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . RBRACK) 
        ErrorMessage . print (lexer . position (), "] EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . RPAREN) 
        ErrorMessage . print (lexer . position (), ") EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . LBRACE) 
        ErrorMessage . print (lexer . position (), "{ EXPECTED");
    getToken ();
    while ( token.symbol() == TokenClass.VAR)
    {
        declarations();  
    }
    statements();
    while (token . symbol () != TokenClass . RBRACE)
    {
        statements();
    }
     getToken();
}
public void functionDef (String functionID) throws java.io.IOException 
{
    if (token . symbol () != TokenClass . LPAREN) 
        ErrorMessage . print (lexer . position (), "( EXPECTED");
    getToken ();
    if (token . symbol () == TokenClass . ID) 
    {
        getToken ();
        if (token . symbol () != TokenClass . COLON) 
            ErrorMessage . print (lexer . position (), ": EXPECTED");
        getToken ();
        type();
        while (token . symbol () == TokenClass . COMMA) 
        {
            getToken ();
            if (token . symbol () != TokenClass . ID) 
                ErrorMessage . print (lexer . position (), "id EXPECTED");
            getToken ();
            if (token . symbol () != TokenClass . COLON) 
                ErrorMessage . print (lexer . position (), ": EXPECTED");
            getToken ();
            type();
        }
    }
    if (token . symbol () != TokenClass . RPAREN) 
        ErrorMessage . print (lexer . position (), ") EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . COLON) 
        ErrorMessage . print (lexer . position (), ": EXPECTED");
    getToken ();
    type();
    if (token . symbol () != TokenClass . ASSIGN) 
        ErrorMessage . print (lexer . position (), "= EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . LBRACE) 
        ErrorMessage . print (lexer . position (), "{ EXPECTED");
    getToken ();
    while (token.symbol() == TokenClass.VAR)
    {
        declarations();
    }
    while (token.symbol() != TokenClass.RETURN)
    {
        statements();
    }
    getToken();
    parseCons (); 
    
    if (token . symbol () != TokenClass . SEMICOLON) 
        ErrorMessage . print (lexer . position (), "; EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . RBRACE) 
        ErrorMessage . print (lexer . position (), "} EXPECTED");
    getToken ();
}

public void type () throws java.io.IOException 
{
    if (token . symbol () == TokenClass . INT)            
        getToken ();
    else if (token . symbol () == TokenClass . LIST) 
    {    
        getToken ();
        if (token . symbol () != TokenClass . LBRACK)  
            ErrorMessage . print (lexer . position (), "[ EXPECTED");
        getToken ();
        if (token . symbol () != TokenClass . INT)          
            ErrorMessage . print (lexer . position (), "Int EXPECTED");
        getToken ();
        if (token . symbol () != TokenClass . RBRACK) 
            ErrorMessage . print (lexer . position (), "] EXPECTED");
        getToken ();
    }
    else
        ErrorMessage . print (lexer . position (), "type EXPECTED");
}

  public void declarations () throws java.io.IOException {
    if (token . symbol () != TokenClass . VAR)
        ErrorMessage . print (lexer . position (), "var EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . ID)
        ErrorMessage . print (lexer . position (), "ID EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . COLON)
        ErrorMessage . print (lexer . position (), ": EXPECTED");
    getToken ();
    type();
    if (token . symbol () != TokenClass . ASSIGN)
        ErrorMessage . print (lexer . position (), "= EXPECTED");
    getToken ();
    literal();
    if (token . symbol () != TokenClass . SEMICOLON)
        ErrorMessage . print (lexer . position (), "; EXPECTED");
    getToken ();
  }

  public void statements () throws java.io.IOException {
    while ( token . symbol () == TokenClass . LBRACE
        || token . symbol () == TokenClass . PRINTLN
        || token . symbol () == TokenClass . ID
        || token . symbol () == TokenClass . IF
        || token . symbol () == TokenClass . WHILE)
      statement ();
  }

  public void statement () throws java.io.IOException {

    switch (token . symbol ()) {

      case PRINTLN:
        getToken ();
        if (token . symbol () != TokenClass . LPAREN)  
            ErrorMessage . print (lexer . position (), "( EXPECTED");
        getToken ();					
        parseCons (); 			  		  
        if (token . symbol () != TokenClass . RPAREN) 
            ErrorMessage . print (lexer . position (), ") EXPECTED");
        getToken ();					
        if (token . symbol () != TokenClass . SEMICOLON)  
            ErrorMessage . print (lexer . position (), "; EXPECTED");
        getToken ();					
        break;

      case LBRACE :
        getToken ();
        statement ();
        while (token . symbol () != TokenClass . RBRACE)
            statement();
        getToken ();
        break;

      case ID :
        getToken ();
        if (token . symbol () != TokenClass . ASSIGN) 
            ErrorMessage . print (lexer . position (), "= EXPECTED");
        getToken ();
        parseCons (); 	
        if (token . symbol () != TokenClass . SEMICOLON) 
            ErrorMessage . print (lexer . position (), "; EXPECTED");
        getToken ();
        break;

      case IF :
        getToken ();
        if (token . symbol () != TokenClass . LPAREN) 
          ErrorMessage . print (lexer . position (), "( EXPECTED");
        getToken ();
        expression ();
        if (token . symbol () != TokenClass . RPAREN) 
          ErrorMessage . print (lexer . position (), ") EXPECTED");
        getToken ();
        statement ();
        if (token . symbol () == TokenClass . ELSE) 
        {
            getToken ();
            statement (); 
        }
        break;

      case WHILE :
        getToken ();
        if (token . symbol () != TokenClass . LPAREN) 
          ErrorMessage . print (lexer . position (), "( EXPECTED");
        getToken ();
        expression ();
        if (token . symbol () != TokenClass . RPAREN) 
          ErrorMessage . print (lexer . position (), ") EXPECTED while");
        getToken ();
        statement ();
        break;

      default :
        ErrorMessage . print (lexer . position (), "UNRECOGNIZABLE SYMBOL");

    }
  }
public void parseCons () throws java.io.IOException {
    expression ();                                         
    if (token . symbol () == TokenClass . CONS) 
    { 	
        getToken ();
        parseCons ();					
    }
  }

public void literal () throws java.io.IOException 
{
    if (token . symbol () == TokenClass . INTEGER)  	
        getToken ();
    else if (token . symbol () == TokenClass . NIL)  	
        getToken ();
    else
        ErrorMessage . print (lexer . position (), "literal EXPECTED");
}

  public void expression () throws java.io.IOException {
    conjunction (); 
    while (token . symbol () == TokenClass . OR) {
      getToken ();
      conjunction (); 
    }
  }

  public void conjunction () throws java.io.IOException {
    equality (); 
    while (token . symbol () == TokenClass . AND) {
      getToken ();
      equality (); 
    }
  }

  public void equality () throws java.io.IOException {
    relation ();
    if (token . symbol () == TokenClass . EQ
        || token . symbol () == TokenClass . NE) {
      getToken ();
      relation (); 
    }
  }

  public void relation () throws java.io.IOException {
    addition ();
    switch (token . symbol ()) {
      case LT : 
      case GT : 
      case LE : 
      case GE :
        getToken ();
        addition (); 
	break;
      default : 
        break;
    }
  }

  public void addition () throws java.io.IOException {
    term (); 
    while (token . symbol () == TokenClass . PLUS || 
	token . symbol () == TokenClass . MINUS) {
      getToken ();
      term (); 
    }
  }

  public void term () throws java.io.IOException {
    factor ();
    while (token . symbol () == TokenClass . TIMES 
        || token . symbol () == TokenClass . SLASH
        || token . symbol () == TokenClass . MOD) { 
      getToken ();
      factor (); 
    }
  }

  public void factor () throws java.io.IOException {
    if (token . symbol () == TokenClass . MINUS
        || token . symbol () == TokenClass . NOT)
      getToken ();
    primary (); 
  }

  public void primary  () throws java.io.IOException {
    switch (token . symbol ()) 
    {
        case ID : 
            getToken ();			
            if (token . symbol () == TokenClass . LPAREN) 
            {
                getToken ();
                if (token . symbol () != TokenClass . RPAREN)  
                { 
                    parseCons();					
                    while (token . symbol () == TokenClass . COMMA) 
                    {
                        getToken ();
                        parseCons();					
                    }						
                    if (token . symbol () != TokenClass . RPAREN) 
                     ErrorMessage . print (lexer . position (), ") EXPECTED");
                } 						
            getToken ();
            
            }
            if (token . symbol () == TokenClass . PERIOD) 
            {
                while (token . symbol () == TokenClass . PERIOD)
                {
                    getToken ();
                    if (token . symbol () != TokenClass . ISEMPTY 
                    && token . symbol () != TokenClass . HEAD
                    && token . symbol () != TokenClass . TAIL)
                    {
                        ErrorMessage . print (lexer . position (), "List operator EXPECTED");
                    }
                    getToken ();
                }
            }
        break;

      case INTEGER : 
      case BOOLEAN :
      case FLOATLIT :
      case CHARLIT :
        getToken ();
        break;
      case NIL:
          literal();
          break;
      case LPAREN :
        getToken ();
        expression ();
        if (token . symbol () != TokenClass . RPAREN) 
          ErrorMessage . print (lexer . position (), ") EXPECTED");
        getToken ();
        break;

      default :
        ErrorMessage . print (lexer . position (), "UNRECOGNIZABLE SYMBOL");
    }
  }

}
