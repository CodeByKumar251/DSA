import java.util.ArrayList;
import java.util.LinkedList;

public class l003TraverslSet {

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

    // 3 preOrder traversal
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
    // using recursion
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

    // 6 used Morris inOrder taversal
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

    // 6-> solved using stack
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
    // using stack
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

    // 7 using inOrder morris traversal
    class BSTIterator2 {
        private TreeNode curr=null;
        public BSTIterator2(TreeNode root) {
            curr=root;
        }

        private TreeNode getRightMostNode(TreeNode left, TreeNode curr) {
            while (left.right != null && left.right != curr) {
                left = left.right;
            }
            return left;
        }

        public int next() {
            int ans=-1;
            while (curr != null) {
                TreeNode left = curr.left;
                if (left == null) {
                    ans=curr.val;
                    curr = curr.right;
                    break;
                } else {
                    TreeNode rightMost = getRightMostNode(left, curr);
                    if (rightMost.right == null) { // thread creation block
                        rightMost.right = curr; // thread is created
                        curr = curr.left;
                    } else { // thread destroy block
                        rightMost.right = null; // thread is cut
                        ans=curr.val;
                        curr = curr.right;
                        break;
                    }
                }
            }
            return ans;
        }

        public boolean hasNext() {
            return curr!=null;
        }
    }

    //8 kth smallest element in BST(leetcode 230)
    //A1 using morris inOrder traversal
    public int kthSmallest(TreeNode root, int k) {
        TreeNode curr=root;
        int ans=-1;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                if(--k==0) ans= curr.val;
                curr = curr.right;
                
            } else {
                TreeNode rightMost = getRightMostNode(left, curr);
                if (rightMost.right == null) { // thread creation block
                    rightMost.right = curr; // thread is created
                    curr = curr.left;
                } else { // thread destroy block
                    rightMost.right = null; // thread is cut
                    if(--k==0) ans= curr.val;
                    curr = curr.right;
                }
            }
        }
        return ans;
    }



}




