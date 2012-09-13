/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bstree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


/**
 *
 * @author RJXY
 */
public class BSTree {
    protected BSTreeNode root;
    protected int nodeCount;
    protected int leavesCount;
    protected int height;
    protected boolean isBalanced;
    protected boolean isPerfectlyBalanced;
    ArrayList<Integer> heightOfLeaves;

    public BSTree() {
        root = null;
        nodeCount = 0;
        leavesCount = 0;
        height = 0;
        isBalanced = true;
        isPerfectlyBalanced = true;
        heightOfLeaves = new ArrayList<Integer>();
    }

    public BSTree(BSTreeNode r) {
        root = r;
        nodeCount = 0;
        leavesCount = 0;
        height = 0;
        isBalanced = true;
        isPerfectlyBalanced = true;
        heightOfLeaves = new ArrayList<Integer>();
    }

    protected void visit(BSTreeNode p) {
        System.out.print(p.el+" ");
    }

    protected Comparable search(Comparable el) {
        return search(root,el);
    }

    protected Comparable search(BSTreeNode p,Comparable el) {
        while(p!=null) {
            if(p.el==el)
                return p.el;
            else if(el.compareTo(p.el)<0)
                p = p.left;
            else
                p = p.right;
        }
        return null;
    }

    protected void preOrder() {
        preOrder(root);
    }

    protected void inOrder() {
        inOrder(root);
    }

    protected void postOrder() {
        postOrder(root);
    }

    protected void preOrder(BSTreeNode p) {
        if(p!=null) {
            visit(p);
            preOrder(p.left);
            preOrder(p.right);
        }
    }

    protected void inOrder(BSTreeNode p) {
        if(p!=null) {
            inOrder(p.left);
            visit(p);
            inOrder(p.right);
        }
    }

    protected void postOrder(BSTreeNode p) {
        if(p!=null) {
            postOrder(p.left);
            postOrder(p.right);
            visit(p);
        }
    }

    protected void breadthFirst() {
        BSTreeNode p = root;
        ArrayList queue = new ArrayList();
        if(p!=null) {
            queue.add(p);
            while(!queue.isEmpty()) {
                 p = (BSTreeNode)queue.remove(0);
                 visit(p);
                 if(p.right!=null)
                     queue.add(p.right);
                 if(p.left!=null)
                     queue.add(p.left);
            }
        }
    }

    protected void iterativePreOrder() {
        BSTreeNode p = root;
        Stack stack = new Stack();
        stack.push(p);
        while(!stack.isEmpty()) {
            p = (BSTreeNode)stack.pop();
            visit(p);
            if(p.right!=null)
                stack.push(p.right);
            if(p.left!=null)
                stack.push(p.left);
        }
    }

    protected void iterativeInOrder() {
        BSTreeNode p = root;
        Stack stack = new Stack();
        while(p!=null) {
            while(p!=null) {
                if(p.right!=null)
                    stack.push(p.right);
                stack.push(p);
                p = p.left;
            }
            p = (BSTreeNode)stack.pop();
            while(!stack.isEmpty()&&p.right==null) {
                visit(p);
                p = (BSTreeNode)stack.pop();
            }
            visit(p);
            if(!stack.isEmpty())
                p = (BSTreeNode)stack.pop();
            else
                p = null;
        }
    }

    protected void iterativePostOrder() {
        BSTreeNode p = root,q=root;
        Stack stack  = new Stack();
        while(p!=null) {
            while(p.left!=null) {
                stack.push(p);
                p = p.left;
            }
            while(p!=null&&((p.right==null)||p.right==q)) {  //if the node has no right child or its right child has already been visited
                visit(p);
                q = p;        //q is the last node visited
                if(stack.isEmpty())
                    return;
                p = (BSTreeNode)stack.pop();
            }
            stack.push(p);
            p = p.right;
        }
    }

    public void MorrisInOrder() {
        BSTreeNode p = root;
        while(p!=null) {
            if(p.left==null) {
                visit(p);
                p = p.right;
            }
            else {
                BSTreeNode rightMost = p.left;
                while(rightMost.right!=null && rightMost.right!=p) //rightMost.right==p for reconstruction
                    rightMost = rightMost.right;
                if(rightMost.right==null) {   //transform the tree
                    rightMost.right = p;
                    p = p.left;
                }
                else {  //rightMost.right == p, then reconstruct the tree
                    visit(p);
                    rightMost.right = null;
                    p = p.right;
                }
            }
        }
    }

    public void MorrisPreOrder() {
        BSTreeNode p = root;
        while(p!=null) {
            if(p.left==null) {
                visit(p);
                p = p.right;
            }
            else {
                BSTreeNode rightMost = p.left;
                while(rightMost.right!=null && rightMost.right!=p) //rightMost.right==p for reconstruction
                    rightMost = rightMost.right;
                if(rightMost.right==null) {   //transform the tree
                    visit(p);      //difference from inorder
                    rightMost.right = p;
                    p = p.left;
                }
                else {  //rightMost.right == p, then reconstruct the tree
                    //visit(p);
                    rightMost.right = null;
                    p = p.right;
                }
            }
        }
    }

    public void MorrisPostOrder() {
        BSTreeNode dummy = new BSTreeNode();
        BSTreeNode p = dummy;
        dummy.left = root;
        while(p!=null) {
            if(p.left==null) {
                p = p.right;
            }
            else {
                BSTreeNode rightMost = p.left;
                while(rightMost.right!=null && rightMost.right!=p) //rightMost.right==p for reconstruction
                    rightMost = rightMost.right;
                if(rightMost.right==null) {   //transform the tree
                    rightMost.right = p;
                    p = p.left;
                }
                else {  //rightMost.right == p, then reconstruct the tree
                    rightMost.right = null;
                    rightMost = p.left;
                    BSTreeNode p1 = rightMost.right;
                    BSTreeNode p2 = null;
                    while(p1!=null) {   //reverse the right reference before the p(excluded)
                        p2 = p1.right;
                        p1.right = rightMost;
                        rightMost = p1;
                        p1 = p2;
                    }
                    while(rightMost!=p.left) { //visit upward and restore the right reference
                        visit(rightMost);
                        p1 = rightMost.right;
                        rightMost.right = p2;
                        p2 = rightMost;
                        rightMost = p1;
                    }
                    visit(rightMost);          //visit the temporary parent
                    p = p.right;               //go to next chain
                }
            }
        }
    }

    public void insert(Comparable el) {
        BSTreeNode p = root,prev = null;
        while(p!=null) {
            prev = p;
            if(el.compareTo(p.el)<0)
                p = p.left;
            else
                p = p.right;
        }
        if(root==null)
            root = new BSTreeNode(el);
        else if(el.compareTo(prev.el)<0)
            prev.left = new BSTreeNode(el);
        else
            prev.right = new BSTreeNode(el);
    }

    public void deleteByMerge(Comparable el) {
        BSTreeNode p = root,prev = null;
        while(p!=null&&!p.el.equals(el)) {
            prev = p;
            if(p.el.compareTo(el)<0)
                p = p.right;
            else
                p = p.left;
        }

        BSTreeNode target = p;

        if(p!=null&&p.el.equals(el)) {
            if(target.left==null)
                target = target.right;
            else if(target.right==null)
                target = target.left;
            else {
                //critical algorithm
                BSTreeNode rightMost = target.left;
                while(rightMost.right!=null)
                    rightMost = rightMost.right;
                rightMost.right = target.right;

                target = target.left;
            }
            if(p==root)          //delete the root
                root = target;
            else if(prev.left==p)
                prev.left = target;
            else
                prev.right = target;
        }
        else if(root!=null)
            System.out.println(el+" is not in the tree");
        else
            System.out.println("Empty tree");
    }

    public void deleteByCopying(Comparable el) {
        BSTreeNode p = root,prev = null;
        while(p!=null&&!p.el.equals(el)) {
            prev = p;
            if(p.el.compareTo(el)<0)
                p = p.right;
            else
                p = p.left;
        }

        BSTreeNode target = p;

        if(p!=null&&p.el.equals(el)) {
            if(target.left==null)
                target = target.right;
            else if(target.right==null)
                target = target.left;
            else {
                //critical algorithm
                BSTreeNode rightMost = target.left;
                BSTreeNode previous  = target;
                while(rightMost.right!=null) {
                    previous = rightMost;
                    rightMost = rightMost.right;
                }
                target.el = rightMost.el;

                if(previous==target)
                    previous.left = rightMost.left;   //save the left child of the right most node
                else
                    previous.right = rightMost.left;  //save the left child of the right most node
            }
            /*necessary???
            if(p==root)          //delete the root
                root = target;
            else if(prev.left==p)
                prev.left = target;
            else
                prev.right = target; 
             */
        }
        else if(root!=null)
            System.out.println(el+" is not in the tree");
        else
            System.out.println("Empty tree");
    }

    public void balance(Comparable[] data,int first,int last) {
        if(first<=last) {
            int middle = Math.round((first+last)/2);
            insert(data[middle]);
            balance(data,first,middle-1);
            balance(data,middle+1,last);
        }
    }

    protected int countNodes() {
        return countNodes(root);
    }

    protected int countNodes(BSTreeNode p) {
        if(p!=null) {
            this.nodeCount++;
            countNodes(p.left);
            countNodes(p.right);
        }

        return this.nodeCount;
    }

    protected int countLeaves() {
        return countLeaves(root);
    }

    protected int countLeaves(BSTreeNode p) {
        if(p!=null) {
            if(p.left==null&&p.right==null)
                leavesCount++;
            countLeaves(p.left);
            countLeaves(p.right);
        }

        return this.leavesCount;
    }

    protected int calcHeight() {
        return calcHeight(root,0);
    }

    protected int calcHeight(BSTreeNode p,int h) {     
        if(p!=null) {
            if(p.left==null&&p.right==null) {
                this.height=Math.max(height,h+1);
                heightOfLeaves.add(h+1);
            }
            h++;
            calcHeight(p.left,h);
            calcHeight(p.right,h);
        }

        return this.height;
    }

    protected boolean isBalanced() {
        return isBalanced(root);
    }

    protected boolean isBalanced(BSTreeNode p) {
        if(p!=null) {
            BSTree leftch = new BSTree(p.left);
            BSTree rightch = new BSTree(p.right);
            if(Math.abs(leftch.calcHeight()-rightch.calcHeight())>1)
                this.isBalanced = false;
            if(this.isBalanced) {
                isBalanced(p.left);
            }
            if(this.isBalanced) {
                isBalanced(p.right);
            }
        }

        return this.isBalanced;
    }

    protected boolean isPerfectlyBalanced() {
        if(!this.isBalanced())
            this.isPerfectlyBalanced = false;
        else {
            this.calcHeight();
            if(!heightOfLeaves.isEmpty()) {
                Collections.sort(heightOfLeaves);
                int last = heightOfLeaves.size()-1;
                if(heightOfLeaves.get(last)-heightOfLeaves.get(0)>1)
                    this.isPerfectlyBalanced = false;
                else
                    this.isPerfectlyBalanced = true;
            }
            else
                this.isPerfectlyBalanced = true;
        }

        return this.isPerfectlyBalanced;
    }

    protected void printTree() {
        if(this.root==null)
            return;
        else {
            ArrayList<BSTreeNode> queue = new ArrayList<BSTreeNode>();
            ArrayList<BSTreeNode> queue2 = new ArrayList<BSTreeNode>();
            queue.add(this.root);

            while(!queue.isEmpty()) {
                BSTreeNode p = queue.remove(0);
                queue2.add(p);
                if(p.left!=null)
                    queue.add(p.left);
                if(p.right!=null)
                    queue.add(p.right);
            }

            ArrayList<BSTreeNode> prev = new ArrayList<BSTreeNode>();
            ArrayList<BSTreeNode> curr = new ArrayList<BSTreeNode>();
            int h=1;
            prev.add(queue2.get(0));

            do {
                curr.clear();
                indent(h);
                while(!prev.isEmpty()) {
                    BSTreeNode p = prev.remove(0);
                    System.out.print(p.el);
                    indent(h-1);
                    if(p.left==null)
                        curr.add(new BSTreeNode(" "));
                    else
                        curr.add(p.left);
                    
                    if(p.right==null)
                        curr.add(new BSTreeNode(" "));
                    else
                        curr.add(p.right);
                }
                copyElements(curr,prev);
                System.out.println();
                h++;
            }while(h<=this.height);
        }
    }

    void copyElements(ArrayList<BSTreeNode> qa,ArrayList<BSTreeNode> qb) {
        for(int i=0;i<qa.size();i++)
            qb.add(qa.get(i));
    }

    private void indent(int h) {
        int indent = 1;
        for(int i=0;i<this.height-h;i++)
            indent*=2;
        indent-=1;

        String blank = "";
        for(int j=0;j<indent;j++)
            blank+=" ";
        System.out.print(blank);
    }
}

class BSTreeNode {
    protected Comparable el;
    protected BSTreeNode left,right;
    public BSTreeNode() {
        left = right = null;
    }

    public BSTreeNode(Comparable el) {
        this(el,null,null);
    }

    public BSTreeNode(Comparable el,BSTreeNode l,BSTreeNode r) {
        this.el = el;
        this.left = l;
        this.right = r;
    }
}