package Syntax;
public final class Lexem {
    private final LexemType lexemType;
    private final String lexemValue;

    public Lexem(LexemType lexemType, String lexemValue) {
        this.lexemType = lexemType;
        this.lexemValue = lexemValue;
    }
    public Lexem(LexemType lexemType) {
        this(lexemType, "");
    }

    public LexemType getLexemType() {
        return lexemType;
    }

    public String getLexemValue() {
        return lexemValue;
    }

    @Override
    public String toString() {
        switch (getLexemType()) {
            case Plus               : return "+";
            case Minus              : return "-";
            case Mult               : return "*";
            case Div                : return "/";
            case Mod                : return "%";
            case LeftParenthesis    : return "(";
            case RightParenthesis   : return ")";
            case Number             : return getLexemValue();
            case HexNumber          : return "#" + getLexemValue();
            default                 : return "" + getLexemType() + " (" + getLexemValue() + ")";
        }
    }
    
}
