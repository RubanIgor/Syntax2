package Syntax;

import java.util.List;

public final class Main {
    public static void main(String[] args) {
        final String inputString = "4+5*7";
        final List<Lexem> lexems = new MakerLexemList(inputString).makeList();
        for (Lexem lexem : lexems) System.out.println(lexem);
        System.out.println("-------------------------------------");
        final TreeNode tree = new Parser(lexems).makeTree();
        TreeNode.printTreeNode(tree);
        System.out.println(" = " + TreeNode.calcTreeNode(tree));
    }    
}