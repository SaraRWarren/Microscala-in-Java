// CliteSyn.java

// This program is a recursive descent parser for Clite.

public class MicroScalaSyn {

  public static void main (String args []) throws java.io.IOException {

    System . out . println ("Source Program");
    System . out . println ("--------------");
    System . out . println ();

    SyntaxAnalyzer synanalyzer = new SyntaxAnalyzer ();
    synanalyzer . program ();

    System . out . println ();
    System . out . println ("PARSE SUCCESSFUL");
  }

}
