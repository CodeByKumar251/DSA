import java.util.ArrayList;
import java.util.LinkedList;

import org.w3c.dom.Node;

public class l003TraversalSet {

    public class TreeNode {
        int val = 0;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    // 1 inOrder traversal
    public void inOrder(TreeNode root, ArrayList<Integer> ans) {
        if (root == null) {
            return;
        }

        inOrder(root.left, ans);
        ans.add(root.val);
        inOrder(root.right, ans);

        return;
    }

    // 2 preOrder traversal
    public void preOrder(TreeNode root, ArrayList<Integer> ans) {
        if (root == null) {
            return;
        }

        ans.add(root.val);
        preOrder(root.left, ans);
        preOrder(root.right, ans);

        return;

    }

    // 3 postOrder traversal
    public void postOrder(TreeNode root, ArrayList<Integer> ans) {
        if (root == null) {
            return;
        }

        preOrder(root.left, ans);
        preOrder(root.right, ans);
        ans.add(root.val);

        return;

    }

    // 4 Morris Inorder traversal
    public ArrayList<Integer> morrisInOrderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                ans.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMostNode(left, curr);
                if (rightMost.right == null) { // thread creation block
                    rightMost.right = curr; // thread is created
                    curr = curr.left;
                } else { // thread destroy block
                    rightMost.right = null; // thread is cut
                    ans.add(curr.val);
                    curr = curr.right;
                }
            }
        }
        return ans;
    }

    public TreeNode getRightMostNode(TreeNode left, TreeNode curr) {
        while (left.right != null && left.right != curr) {
            left = left.right;
        }
        return left;
    }

    // 5 Morris preorder traveral
    public ArrayList<Integer> morrisPreOrderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                ans.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMostNode(left, curr);
                if (rightMost.right == null) { // thread creation block
                    rightMost.right = curr; // thread is created
                    ans.add(curr.val);
                    curr = curr.left;
                } else { // thread destroy block
                    rightMost.right = null; // thread is cut
                    curr = curr.right;
                }
            }
        }
        return ans;

    }

    // 6 validate BST(leetcode 98. Validate Binary Search Tree)
    // A1:using recursion
    public boolean isValidBST(TreeNode root) {
        long min = -(long) 1e13;
        long max = (long) 1e13;
        return isValidBST(root, min, max);
    }

    boolean isValidBST(TreeNode root, long min, long max) {
        if (root == null)
            return true;

        if (root.val <= min || root.val >= max)
            return false;

        return (isValidBST(root.left, min, root.val) && isValidBST(root.right, root.val, max));
    }

    // 6 A2: using Morris inOrder taversal
    public boolean isValidBST2(TreeNode root) {
        TreeNode curr = root;
        long prev = -(long) 1e13;
        boolean flag = true;
        while (curr != null) {
            TreeNode leftNode = curr.left;
            if (leftNode == null) {
                if (prev >= curr.val)
                    return false;
                prev = curr.val;
                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMostNode(leftNode, curr);
                if (rightMost.right == null) { // thread creation
                    rightMost.right = curr; // thread created
                    curr = curr.left;
                } else { // thread break
                    rightMost.right = null; // thred break
                    if (prev >= curr.val)
                        return false;
                    prev = curr.val;
                    curr = curr.right;
                }
            }
        }
        return true;
    }

    // 6 A3: solved using stack
    public boolean isValidBST3(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        insertAllLeft(root, stack);
        long prev = -(long) 1e13;
        while (stack.size() != 0) {
            TreeNode curr = stack.removeFirst();
            // inOrder
            if (prev >= curr.val) {
                return false;
            }
            prev = curr.val;
            insertAllLeft(curr.right, stack);
        }

        return true;
    }

    public void insertAllLeft(TreeNode root, LinkedList<TreeNode> stack) {
        while (root != null) {
            stack.addFirst(root);
            root = root.left;
        }
    }

    // 7)leetcode 173. Binary Search Tree Iterator
    // A1: using stack
    class BSTIterator {
        private LinkedList<TreeNode> stack = new LinkedList<>();

        public BSTIterator(TreeNode root) {
            insertAllLeft(root, stack);
        }

        public int next() {
            TreeNode curr = stack.removeFirst();
            insertAllLeft(curr.right, stack);
            return curr.val;
        }

        public boolean hasNext() {
            return stack.size() != 0;
        }

        private void insertAllLeft(TreeNode root, LinkedList<TreeNode> stack) {
            while (root != null) {
                stack.addFirst(root);
                root = root.left;
            }
        }
    }

    // 7 A2: using inOrder morris traversal
    class BSTIterator2 {
        private TreeNode curr = null;

        public BSTIterator2(TreeNode root) {
            curr = root;
        }

        private TreeNode getRightMostNode(TreeNode left, TreeNode curr) {
            while (left.right != null && left.right != curr) {
                left = left.right;
            }
            return left;
        }

        public int next() {
            int ans = -1;
            while (curr != null) {
                TreeNode left = curr.left;
                if (left == null) {
                    ans = curr.val;
                    curr = curr.right;
                    break;
                } else {
                    TreeNode rightMost = getRightMostNode(left, curr);
                    if (rightMost.right == null) { // thread creation block
                        rightMost.right = curr; // thread is created
                        curr = curr.left;
                    } else { // thread destroy block
                        rightMost.right = null; // thread is cut
                        ans = curr.val;
                        curr = curr.right;
                        break;
                    }
                }
            }
            return ans;
        }

        public boolean hasNext() {
            return curr != null;
        }
    }

    // 8 kth smallest element in BST(leetcode 230)
    // 8 A1: using morris inOrder traversal T.C=o(n),S.C=0(1)
    public int kthSmallest(TreeNode root, int k) {
        TreeNode curr = root;
        int ans = -1;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                if (--k == 0)
                    ans = curr.val;
                curr = curr.right;

            } else {
                TreeNode rightMost = getRightMostNode(left, curr);
                if (rightMost.right == null) { // thread creation block
                    rightMost.right = curr; // thread is created
                    curr = curr.left;
                } else { // thread destroy block
                    rightMost.right = null; // thread is cut
                    if (--k == 0)
                        ans = curr.val;
                    curr = curr.right;
                }
            }
        }
        return ans;
    }

    // 8 A2: using stack , T.C=0(n), S.C=0(k)
    public int kthSmallest2(TreeNode root, int k) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        insertAllLeft(root, stack);
        while (stack.size() != 0) {
            TreeNode curr = stack.removeFirst();
            if (--k == 0)
                return curr.val;
            insertAllLeft(root.right, stack);
        }
        return -1;
    }

    // 8 A3: using recursion inOrder, T.C=0(n), S.C=0(n)
    public int kthSmallest3(TreeNode root, int k) {
        ArrayList<Integer> inorder = new ArrayList<>();
        inOrder(root, inorder);
        return inorder.get(k - 1);
    }

    // 8 A4: using recursion inOrder, T.C=0(n), S.C=0(1)
    public int kthSmallest4(TreeNode root, int k) {
        int[] ans = new int[2]; // ans[0]=maintain count, ans[1]=kthSmallest, we can aslo use wrapper class
                                // instead of arr
        inOrderKthSmallest(root, k, ans);
        return ans[1];
    }

    private void inOrderKthSmallest(TreeNode root, int k, int[] ans) {
        if (root == null)
            return;

        inOrderKthSmallest(root.left, k, ans);
        ans[0] = ans[0] + 1;
        if (ans[0] == k) {
            ans[1] = root.val;
            return;
        }
        inOrderKthSmallest(root.right, k, ans);
        return;

    }

    // 9 kth largest element in BST(GFG Kth largest element in BST)
    // 9 A1: using recursion inOrder, T.C=O(n), S.C=O(n)
    public int kthLargest(TreeNode root, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        inOrder(root, ans);
        return ans.get(ans.size() - k);
    }

    // 9 A2: using recursion inOrder without space
    // count total no of nodes, and then find len-k+1 smallest nodes
    // T.C=0(n)+0(n), S.C=0(1), S.C of stack =0(h), h=height of tree
    public int kthLargest2(TreeNode root, int k) {
        int len = totalNodes(root);
        int m = len - k + 1;
        int[] ans = new int[2];
        inOrderKthSmallest(root, m, ans);
        return ans[1];

    }

    private int totalNodes(TreeNode root) {
        if (root == null)
            return 0;
        return 1 + totalNodes(root.left) + totalNodes(root.right);
    }

    // 9 A3: reversal inOrder: (inOrder:-left,root,right reversal inOrder:-
    // right,root,left)
    // T.C=O(n), S.C=0(h), h=height of tree
    public int kthLargest3(TreeNode root, int k) {
        int[] ans = new int[2]; // ans[0]=count, ans[1]=kth largest
        reversalInOrder(root, k, ans);
        return ans[1];
    }

    private void reversalInOrder(TreeNode root, int k, int[] ans) {
        if (root == null) {
            return;
        }
        reversalInOrder(root.right, k, ans);
        ans[0] += 1;
        if (ans[0] == k) {
            ans[1] = root.val;
            return;
        }
        reversalInOrder(root.left, k, ans);

        return;
    }

    // 9 A4: Morris reversal inOrder
    // T.C=O(n), S.C=O(n)
    public int kthLargest4(TreeNode root, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            TreeNode rightNode = curr.right;
            if (rightNode == null) {
                ans.add(curr.val);
                curr = curr.left;
            } else {
                TreeNode leftMost = getLeftMostNode(rightNode, curr);
                if (leftMost.left == null) { // thread creation block
                    leftMost.left = curr; // thread created
                    curr = curr.right;
                } else { // thred break block
                    leftMost.left = null; // thread break
                    ans.add(curr.val);
                    curr = curr.left;

                }
            }
        }

        return ans.get(k - 1);
    }

    private TreeNode getLeftMostNode(TreeNode rightNode, TreeNode curr) {
        while (rightNode.left != null && rightNode.left != curr) {
            rightNode = rightNode.left;
        }
        return rightNode;
    }

    // 9 A5: Morris reversal inOrder without storing ans
    // T.C=O(n), S.C=O(1)
    public int kthLargest5(TreeNode root, int k) {
        TreeNode curr = root;
        int ans = -1;
        while (curr != null) {
            TreeNode rightNode = curr.right;
            if (rightNode == null) {
                if (--k == 0)
                    ans = curr.val;
                curr = curr.left;
            } else {
                TreeNode leftMost = getLeftMostNode(rightNode, curr);
                if (leftMost.left == null) { // thread creation block
                    leftMost.left = curr; // thread created
                    curr = curr.right;
                } else { // thred break block
                    leftMost.left = null; // thread break
                    if (--k == 0)
                        ans = curr.val;
                    curr = curr.left;

                }
            }
        }

        return ans;
    }

    // 10 A1: using Morris inorder traveral
    // T.C=0(n), S.C=0(1)
    public TreeNode bstToCircularDoublyLinkedList(TreeNode root) {
        TreeNode curr = root;
        TreeNode dummy = new TreeNode(-1);
        TreeNode prev = dummy;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                prev.right = curr;
                curr.left = prev;
                prev = prev.right;

                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMostNode(left, curr);
                if (rightMost.right == null) { // thread creation block
                    rightMost.right = curr; // thread is created
                    curr = curr.left;
                } else { // thread destroy block
                    rightMost.right = null; // thread is cut

                    prev.right = curr;
                    curr.left = prev;
                    prev = prev.right;

                    curr = curr.right;
                }
            }
        }
    
        TreeNode head=dummy.right;
        dummy.right=head.left=null;

        //circular
        prev.right=head;
        head.left=prev;

        return prev;
    }

    //**11)Predecessor and successor of binary tree
    /*
     In an inorder traversal the number just before of the target is the predecessor and the number just after of  the target is the successor.  */
    // do not take extra space, only recursion space are allowed
     public ArrayList<Node> findPreSuc(Node root, int key) {
        // code here
        


        return null;
        
    }

}
