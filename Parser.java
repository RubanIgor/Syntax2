package Syntax;

import java.util.List;

public final class Parser {
    
    private static final Lexem FINISH = new Lexem(LexemType.Finish);
    
    private final List<Lexem> lexems;
    private final int size;
    private int pos;
    private Lexem currentLexem;
    private LexemType currentLexemType;

    public Parser(List<Lexem> lexems) {
        this.lexems = lexems;
        this.size = lexems.size();
    }
    
    public TreeNode makeTree() {
        pos = -1;
        getNextLexem();
        TreeNode res = getExpression();
        if (currentLexemType != LexemType.Finish) throw new RuntimeException("Ожидается окончание выражения");
        return res;
    }        
    
    private void getNextLexem() {
        pos++;
        currentLexem = get(0);
        currentLexemType = currentLexem.getLexemType();
    }
    
    private boolean signForExpr() {
        return (currentLexemType == LexemType.Plus) || (currentLexemType == LexemType.Minus);
    }
    
    private TreeNode getExpression() {
        LexemType unarySign = LexemType.Plus;
        if (signForExpr()) {
            unarySign = currentLexemType;
            getNextLexem();
        }
        TreeNode expr = getTerm();
        if (unarySign == LexemType.Minus) expr = new TreeNode(new Lexem(unarySign), new TreeNode(new Lexem(LexemType.Number, "0")), expr);
        while (signForExpr()) {
            LexemType sign = currentLexemType;
            getNextLexem();
            TreeNode add = getTerm();
            expr = new TreeNode(new Lexem(sign), expr, add);
	}        
        return expr;
    }

    private TreeNode getTerm() {
        TreeNode mult = getFactor();
        while ((currentLexemType == LexemType.Mult) || (currentLexemType == LexemType.Div) || (currentLexemType == LexemType.Mod)) {
            LexemType sign = currentLexemType;
            getNextLexem();
            TreeNode fact = getFactor();
            mult = new TreeNode(new Lexem(sign), mult, fact);
	}        
	return mult;
    }
    
    private TreeNode getFactor() {
        if (currentLexemType == LexemType.Number) {
            TreeNode rez = new TreeNode(currentLexem);
            getNextLexem();
            return rez;    
        }
        if (currentLexemType == LexemType.HexNumber) {
            TreeNode rez = new TreeNode(currentLexem);
            getNextLexem();
            return rez;    
        }       
        if (currentLexemType == LexemType.LeftParenthesis) {
            getNextLexem();
            TreeNode rez = getExpression();
            if (currentLexemType != LexemType.RightParenthesis) {
                getNextLexem();
                throw new RuntimeException("Ожидается: <)>");
            }
            getNextLexem();
            return rez;    
        }
        throw new RuntimeException("Неожиданная лексема: <" + currentLexemType + ">");
    }
    
    private Lexem get(int relativePosition) {
        final int position = pos + relativePosition;
        return position < size ? lexems.get(position) : FINISH;
    }
    
}
