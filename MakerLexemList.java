package Syntax;

import java.util.ArrayList;
import java.util.List;

public final class MakerLexemList {

    private static final String OPERATOR_CHARS = "+-*/%()";
    private static final LexemType[] OPERATOR_LEXEMS = {
        LexemType.Plus, 
        LexemType.Minus, 
        LexemType.Mult, 
        LexemType.Div,
        LexemType.Mod,
        LexemType.LeftParenthesis,
        LexemType.RightParenthesis
    };
    
    private final String inputString;
    private final int lengthString;
    private final List<Lexem> lexems;
    private int pos;
    private char currentChar;

    public MakerLexemList(String inputString) {
        this.inputString = inputString.toLowerCase();
        this.lengthString = inputString.length();
        this.lexems = new ArrayList<>();
    }
    
    public List<Lexem> makeList() {
        pos = -1;
        getNextChar();
        while (pos < lengthString) {
            if      (currentChar == ' ') getNextChar();
            else if (Character.isDigit(currentChar)) getNumber();
            else if (currentChar == '#') getHexNumber();
            else if (OPERATOR_CHARS.indexOf(currentChar) != -1) getOperator();
            
            else throw new RuntimeException("Неивестный символ: <" + currentChar + ">");
        }
        return lexems;
    }
    
     private void getNumber() {
        final StringBuilder buffer = new StringBuilder();
        
        if(currentChar != '0') {
            while (Character.isDigit(currentChar)) {            
                buffer.append(currentChar);
                getNextChar();
            }   
        } else {
            buffer.append(currentChar);
            getNextChar();
        }
            
        
        if(currentChar == '.') {
            buffer.append(currentChar);
            
            getNextChar();
            if(!Character.isDigit(currentChar))
                throw new RuntimeException("Not digit after point");
            
            while (Character.isDigit(currentChar)) {            
                buffer.append(currentChar);
                getNextChar();
            }  
        }
        
        if(currentChar == 'e') {
            buffer.append(currentChar);
            getNextChar();
            if(currentChar == '+' || currentChar == '-' ) {
                buffer.append(currentChar);
                getNextChar();
            }
            
            if(!Character.isDigit(currentChar))
                throw new RuntimeException("Not digit after exponent symbol");
               
            while (Character.isDigit(currentChar)) {            
                buffer.append(currentChar);
                getNextChar();
            }  
        }
        
        
        
        addLexem(LexemType.Number, buffer.toString());
    }    
    
     private void getHexNumber() {
        final StringBuilder buffer = new StringBuilder();
        getNextChar();
        if ("0123456789abcdef".indexOf(currentChar) == -1) throw new RuntimeException("Bad char after #");
        while ("0123456789abcdef".indexOf(currentChar) > -1) {            
            buffer.append(currentChar);
            getNextChar();
        }
        addLexem(LexemType.HexNumber, buffer.toString());
    }    
      
    private void getOperator() {
        addLexem(OPERATOR_LEXEMS[OPERATOR_CHARS.indexOf(currentChar)]);
        getNextChar();
    }
    
    private void getNextChar() {
        pos++;
        currentChar = pos < lengthString ? inputString.charAt(pos) : '\0';
    }
    
    private void addLexem(LexemType lexemType, String lexemValue) {
        lexems.add(new Lexem(lexemType, lexemValue));
    }
    
    private void addLexem(LexemType lexemType) {
        addLexem(lexemType, "");
    }
    
}
