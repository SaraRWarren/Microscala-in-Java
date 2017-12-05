
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
  public Program program () throws java.io.IOException {
    Program function, program = null;
    String functionID = null;
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
                 function = functionDef(functionID);
             }
             else if (token . symbol () == TokenClass . MAIN)
             {
                 getToken();
                 program = mainDef();
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
    return program;
  }
public Program mainDef () throws java.io.IOException 
{
    Program program;
    Statement state1, state2;
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
    state1 = statement();
    while (token . symbol () != TokenClass . RBRACE)
    {
       state2 = statement();
       state1 = new Statement (state1, state2);
    }
     getToken();
     program = new Program ("main", state1);
     return program;
}
public Program functionDef (String functionID) throws java.io.IOException 
{
    Expression expression;
    Program program;
    Statement returnTree, state1, state2;
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
    state1 = null;
    while (token.symbol() != TokenClass.RETURN)
    {
        state2 = statement();
        if (state1 == null)
            state1 = state2;
        else 
            state1 = new Statement (state1, state2);
    }
    getToken();
    expression = parseCons(); 
    returnTree = new Return(expression);
    if (state1 == null)
        state1 = returnTree;
    else 
        state1 = new Statement (state1, returnTree);

    if (token . symbol () != TokenClass . SEMICOLON) 
        ErrorMessage . print (lexer . position (), "; EXPECTED");
    getToken ();
    if (token . symbol () != TokenClass . RBRACE) 
        ErrorMessage . print (lexer . position (), "} EXPECTED");
    program = new Program(functionID, state1);
    System.out.println(program);
    getToken ();
    return program;
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
    Value value;
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
    value = literal();
    if (token . symbol () != TokenClass . SEMICOLON)
        ErrorMessage . print (lexer . position (), "; EXPECTED");
    getToken ();
  }

 /*() public Statement statements () throws java.io.IOException {
    while ( token . symbol () == TokenClass . LBRACE
        || token . symbol () == TokenClass . PRINTLN
        || token . symbol () == TokenClass . ID
        || token . symbol () == TokenClass . IF
        || token . symbol () == TokenClass . WHILE)
      statement ();
  }
*/
  public Statement statement () throws java.io.IOException 
  {
    Expression EXP;
    Statement statement = null, state1, state2;
    Variable ID;
    
    switch (token . symbol ()) {
        
        case IF :
        getToken ();
        if (token . symbol () != TokenClass . LPAREN) 
            ErrorMessage . print (lexer . position (), "( EXPECTED");
        getToken ();
        EXP = expression ();
        if (token . symbol () != TokenClass . RPAREN) 
             ErrorMessage . print (lexer . position (), ") EXPECTED");
        getToken ();
        state1 = statement ();
        if (token . symbol () == TokenClass . ELSE) 
        {
            getToken ();
            state2 = statement (); 
        }
        else 
            state2 = null;
        statement = new Conditional (EXP, state1, state2);
        break;

      case WHILE :
        getToken ();
        if (token . symbol () != TokenClass . LPAREN) 
            ErrorMessage . print (lexer . position (), "( EXPECTED");
        getToken ();
        EXP = expression ();
        if (token . symbol () != TokenClass . RPAREN) 
            ErrorMessage . print (lexer . position (), ") EXPECTED while");
        getToken ();
        state1 = statement ();
        statement = new Loop (EXP, state1);
        break;
        
      case ID :
        ID = new Variable (token.lexeme());
        getToken ();
        if (token . symbol () != TokenClass . ASSIGN) 
            ErrorMessage . print (lexer . position (), "= EXPECTED");
        getToken ();
        EXP = parseCons ();
        statement = new Assignment (ID, EXP);
        if (token . symbol () != TokenClass . SEMICOLON) 
            ErrorMessage . print (lexer . position (), "; EXPECTED");
        getToken ();
        break;
        
      case PRINTLN:
        getToken ();
        if (token . symbol () != TokenClass . LPAREN)  
            ErrorMessage . print (lexer . position (), "( EXPECTED");
        getToken ();					
        EXP = parseCons (); 			  		  
        if (token . symbol () != TokenClass . RPAREN) 
            ErrorMessage . print (lexer . position (), ") EXPECTED");
        getToken ();
        statement = new Print(EXP);
        if (token . symbol () != TokenClass . SEMICOLON)  
            ErrorMessage . print (lexer . position (), "; EXPECTED");
        getToken ();					
        break;

      case LBRACE :
        getToken ();
        state1 = statement ();
        while (token . symbol () != TokenClass . RBRACE)
        {
            state2 = statement();
            state1 = new Statement (state1, state2);
        }
        statement = state1;
        getToken ();
        break;

      default :
        ErrorMessage . print (lexer . position (), "UNRECOGNIZABLE SYMBOL");

    }
    return statement;
}
public Expression expression () throws java.io.IOException 
{
    Expression ANDEXP, EXP;
    EXP = conjunction (); 
    while (token . symbol () == TokenClass . OR) {
      getToken ();
      ANDEXP = conjunction ();
      EXP = new Binary ("||", EXP, ANDEXP);
    }
    return EXP;
  }

  public Expression conjunction () throws java.io.IOException 
  {
    Expression EXP, RELEXP;
    EXP = relExpr (); 
    while (token . symbol () == TokenClass . AND) {
      getToken ();
      RELEXP = relExpr ();
      EXP = new Binary ("&&", EXP, RELEXP);
    }
    return EXP;
  }  
  
public Expression relExpr () throws java.io.IOException {
    boolean notExpr;
    String relop;
    Expression exp, listExp;
    notExpr = false;
    if (token . symbol () == TokenClass . NOT) { 	// [!]
      getToken ();
      notExpr = true;
    }
    exp = parseCons (); 					// ListExpr
    if (token . symbol () == TokenClass . EQ
            || token . symbol () == TokenClass . NE
            || token . symbol () == TokenClass . LT
            || token . symbol () == TokenClass . LE
            || token . symbol () == TokenClass . GT
            || token . symbol () == TokenClass . GE) 
    {	
      relop = token . lexeme ();
      getToken ();
      listExp = parseCons (); 				// ListExpr
      exp = new Binary (relop, exp, listExp);
    } 							// ]
    if (notExpr)
      exp = new Unary ("!", exp);
    return exp;
  }  
  
  
public Expression parseCons () throws java.io.IOException {
    Expression EXP, CONSEXP;
    EXP = addition ();                                         
    if (token . symbol () == TokenClass . CONS) 
    { 	
        getToken ();
        CONSEXP = parseCons ();
        EXP = new Binary("::", EXP, CONSEXP);
    }
    return EXP;
  }

 public Expression addition () throws java.io.IOException {
    Expression EXP, TermEXP;
    String ADDop;
    EXP = term (); 
    while (token . symbol () == TokenClass . PLUS || 
	token . symbol () == TokenClass . MINUS) {
      ADDop = token . lexeme (); 
      getToken ();
      TermEXP = term ();
      EXP = new Binary (ADDop, EXP, TermEXP);
    }
    return EXP;
  }

  public Expression term () throws java.io.IOException {
    Expression EXP, FactorEXP;
    String TERMop;
    EXP = factor ();
    while (token . symbol () == TokenClass . TIMES 
        || token . symbol () == TokenClass . SLASH
        || token . symbol () == TokenClass . MOD) { 
      TERMop = token . lexeme ();
      getToken ();
      FactorEXP = factor (); 
      EXP = new Binary (TERMop, EXP, FactorEXP);
    }
    return EXP;
  }

  public Expression factor () throws java.io.IOException {
    /*Expression PrimaryEXP, EXP = null;
    //token . symbol () == TokenClass . MINUS ||
    PrimaryEXP = primary (); 
    String relop;
    if (token . symbol () == TokenClass . MINUS || token . symbol () == TokenClass . NOT)
    {
        relop = token . lexeme ();
        getToken (); 
        EXP = new Unary (relop, PrimaryEXP);
    }
    return EXP;
    */
    Expression exp = null;
    String addop, listop;
    addop = null;
    if (token . symbol () == TokenClass . PLUS || 
	token . symbol () == TokenClass . MINUS) { 	// [AddOper]
      addop = token . lexeme ();
      getToken ();
    }
    exp = primary (); 			// SimpleExpr
    while (token . symbol () == TokenClass . PERIOD) 
    {  // { .
      getToken ();
      if (token . symbol () != TokenClass . ISEMPTY 
            && token . symbol () != TokenClass . HEAD
            && token . symbol () != TokenClass . TAIL)	// head | tail | isEmpty
         ErrorMessage . print (lexer . position (), "LIST METHOD EXPECTED");
      listop = token . lexeme ();
      getToken ();
      exp = new Unary (listop, exp);
    } 							// }
    if (addop != null)
      exp = new Unary (addop, exp);
    return exp;
  }

  public Expression primary  () throws java.io.IOException {
    ArrayList<Expression> EXPList;
    Expression EXP = null, primaryEXP;
    String ID, LISTop;
    if (token . symbol () == TokenClass.LPAREN)
    {
        getToken ();
        EXP = expression ();
        if (token . symbol () != TokenClass . RPAREN) 
            ErrorMessage . print (lexer . position (), ") EXPECTED");
        getToken ();
    }
    else if(token . symbol () == TokenClass.ID)
    {
        ID = token . lexeme ();
        getToken ();			
        if (token . symbol () != TokenClass . LPAREN) 
            EXP = new Variable (ID);
        else
        {
            getToken ();
            EXPList = new ArrayList<Expression> ();
            if (token . symbol () != TokenClass . RPAREN)  
            { 
                primaryEXP = parseCons();
                EXPList . add (primaryEXP);
                while (token . symbol () == TokenClass . COMMA) 
                {
                    getToken ();
                    primaryEXP = parseCons();
                    EXPList . add (primaryEXP);
                }						
                if (token . symbol () != TokenClass . RPAREN) 
                    ErrorMessage . print (lexer . position (), ") EXPECTED");
            } 						
            getToken ();
            EXP = new FunctionCall (ID, EXPList);
        }
    }
    else
        EXP = literal();
    return EXP;
}
  
public Value literal () throws java.io.IOException 
{   
    Value VAL = null;
    if (token . symbol () == TokenClass . INTEGER)
    {
        VAL = new IntValue (token . lexeme ()); 
        getToken ();
    }          
    else if (token . symbol () == TokenClass . NIL) 
    {
        VAL = new NilValue();
        getToken ();
    }        
    else
        ErrorMessage . print (lexer . position (), "literal EXPECTED");
    return VAL;
}
/* 

  public Expression equality () throws java.io.IOException {
    Expression EXP, RELEXP;
    String relop;
    EXP = relation ();
    if (token . symbol () == TokenClass . EQ
        || token . symbol () == TokenClass . NE) {
        relop = token.lexeme();
        getToken ();
        RELEXP = relation (); 
        EXP = new Binary (relop, EXP, RELEXP);
    }
    return EXP;
  }

  public Expression relation () throws java.io.IOException {
    Expression EXP, PLUSMINEXP;
    String relop;
    EXP = addition ();
    switch (token . symbol ()) {
      case LT :
      case GT : 
      case LE : 
      case GE :
        relop = token . lexeme (); 
        getToken ();
        PLUSMINEXP = addition (); 
        EXP = new Binary (relop, EXP, PLUSMINEXP);
	break;
      default : 
        break;
    }
    return EXP;
  }
*/
}
