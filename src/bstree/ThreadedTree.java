/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bstree;

/**
 *
 * @author RJXY
 */
public class ThreadedTree {
    ThreadedTreeNode root;

    public ThreadedTree() {
        root = null;
    }

    protected void visit(ThreadedTreeNode p) {
        System.out.print(p.el+" ");
    }

    protected void threadedInOrder() {
        ThreadedTreeNode p = root,prev = null;
        if(p!=null) {
            while(p.left!=null)
                p = p.left;
            while(p!=null) {
                visit(p);
                prev = p;
                p = p.right;
                //if the right child is not a successor
                if(p!=null&&!prev.successor) {
                    while(p.left!=null)
                        p = p.left;
                }
                //else visit the successor
            }
        }
    }

    //preorder && postorder is not so useful, because the successor is defined according to the inorder sequence
    protected void threadedPreOrder() {

    }

    protected void threadedPostOrder() {
        
    }

    public void threadedInsert(Comparable el) {
        ThreadedTreeNode newNode = new ThreadedTreeNode(el);
        if(root==null) {
            root = newNode;
            return;
        }

        ThreadedTreeNode p = root,prev = null;
        while(p!=null) {
            prev = p;
            if(el.compareTo(p.el)<0)
                p = p.left;
            else if(!p.successor)
                p = p.right;
            else
                break;
        }
        if(el.compareTo(prev.el)<0) {
            prev.left = newNode;
            newNode.successor = true;
            newNode.right = prev;
        }
        else if(prev.successor) {
            newNode.successor = true;
            prev.successor = false;
            newNode.right = prev.right;
            prev.right = newNode;
        }
        else
            prev.right = newNode;
    }
}

class ThreadedTreeNode {
    protected Comparable el;
    protected boolean successor;   //according to inorder traversal
    protected ThreadedTreeNode left,right;

    public ThreadedTreeNode() {
        left = right = null;
        successor = false;
    }

    public ThreadedTreeNode(Comparable el) {
        this(el,null,null);
    }

    public ThreadedTreeNode(Comparable el,ThreadedTreeNode l,ThreadedTreeNode r) {
        this.el = el;
        this.left = l;
        this.right = r;
        successor = false;
    }
}