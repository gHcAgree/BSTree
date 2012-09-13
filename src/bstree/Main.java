/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bstree;

import java.util.Stack;

/**
 *
 * @author RJXY
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /**
         *          5
         *     2         7
         *  1     4   6     9
         * 0     3
         * pre :5 2 1 0 4 3 7 6 9
         * in  :0 1 2 3 4 5 6 7 9
         * post:0 1 3 4 2 6 9 7 5
         */
        /*
        BSTree tree = new BSTree();
        tree.insert(5);
        tree.insert(2);
        tree.insert(1);
        tree.insert(7);
        tree.insert(6);
        tree.insert(4);
        tree.insert(3);
        tree.insert(9);
        tree.insert(0);

        tree.calcHeight();
        tree.printTree();

        int numberOfNodes = tree.countNodes();
        int numberOfLeaves = tree.countLeaves();
        int height = tree.calcHeight();
        boolean isBalanced = tree.isBalanced();
        boolean isPerfectlyBalanced = tree.isPerfectlyBalanced();

        System.out.println("Number of nodes:"+numberOfNodes);
        System.out.println("Number of nodes:"+numberOfLeaves);
        System.out.println("Height of the tree:"+height);
        System.out.println("Balanced:"+isBalanced);
        System.out.println("Perfectly Balanced:"+isPerfectlyBalanced);
        System.out.println("preOrder:");
        tree.preOrder();
        System.out.println();
        tree.iterativePreOrder();
        System.out.println();
        tree.MorrisPreOrder();
        System.out.println("\nInOrder:");
        tree.inOrder();
        System.out.println();
        tree.iterativeInOrder();
        System.out.println();
        tree.MorrisInOrder();
        System.out.println("\nPostOrder:");
        tree.postOrder();
        System.out.println();
        tree.iterativePostOrder();
        System.out.println();
        tree.MorrisPostOrder();
        System.out.println("\nAfter delete 5");
        tree.deleteByMerge(5);
        //tree.deleteByCopying(5);
        tree.breadthFirst();

        System.out.println("\nBalanced Tree");
        BSTree tree2 = new BSTree();
        Integer[] data = { 5,2,1,0,4,3,7,6,9 };
        Arrays.sort(data);
        tree2.balance(data, 0, data.length-1);
        System.out.println("preOrder:");
        tree2.preOrder();
        System.out.println();
        tree2.iterativePreOrder();
        System.out.println();
        tree2.MorrisPreOrder();
        System.out.println("\nInOrder:");
        tree2.inOrder();
        System.out.println();
        tree2.iterativeInOrder();
        System.out.println();
        tree2.MorrisInOrder();
        System.out.println("\nPostOrder:");
        tree2.postOrder();
        System.out.println();
        tree2.iterativePostOrder();
        System.out.println();
        tree2.MorrisPostOrder();
        ////////////////////////////////////
        ThreadedTree ttree = new ThreadedTree();
        ttree.threadedInsert(5);
        ttree.threadedInsert(2);
        ttree.threadedInsert(1);
        ttree.threadedInsert(7);
        ttree.threadedInsert(6);
        ttree.threadedInsert(4);
        ttree.threadedInsert(3);
        ttree.threadedInsert(9);
        ttree.threadedInsert(0);
        System.out.println("\nThreaded Tree");
        System.out.println("ThreadedInOrder:");
        ttree.threadedInOrder();
        

        Main main = new Main();
        String expr = "5(2(1(0,),4(3,)),7(6,9))";
        BSTree tree = main.createTree(expr);
        tree.calcHeight();
        tree.printTree();
         */
    }

    BSTree createTree(String expr) {
        BSTree tree = new BSTree();
        if(expr.equals(""))
            return tree;
        else
            tree.root = create(expr);
        return tree;
    }

    BSTreeNode create(String expr) {
        if(expr.length()==0)
            return null;
        else if(expr.length()==1){
            BSTreeNode root = new BSTreeNode(expr);
            return root;
        }
        else {
            BSTreeNode root = new BSTreeNode(expr.substring(0,1));
            String leftch = getLeftch(expr);
            String rightch = getRightch(expr,leftch);
            root.left = create(leftch);
            root.right = create(rightch);
            return root;
        }
    }

    String getLeftch(String expr) {
        if(expr.length()==1)
            return expr;
        else if(expr.length()<=6) {
            int i;
            for(i=0;i<expr.length();i++)
                if(expr.substring(i,i+1).equals(","))
                    break;
            if(expr.substring(i-1,i).equals("("))
                return "";
            else
                return expr.substring(i-1, i);
        }
        else {
            Stack stk = new Stack();
            int i;
            for(i=2;i<expr.length();i++) {
                String t = expr.substring(i,i+1);
                if(t.equals("("))
                    stk.push(t);
                else if(t.equals(")")) {
                    stk.pop();
                }
                else
                    continue;
                if(stk.isEmpty())
                    break;
            }
            return expr.substring(2,i+1);
        }
    }

    String getRightch(String expr,String leftch) {
        if(expr.length()>3) {
            String t  = expr.substring(2,expr.length()-1).replace(leftch,"");
            return t.substring(1,t.length());
        }
        else {
            return expr;
        }
    }
}
