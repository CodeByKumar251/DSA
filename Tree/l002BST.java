  import java.util.ArrayList;
  import java.util.List;
public class l002BST {

    public static class TreeNode {
        int val=0;
        TreeNode left=null;
        TreeNode right=null;

        TreeNode(int val){
            this.val=val;
        }
    } 

    //2
    public static int size(TreeNode root){
        return root==null?0:size(root.left)+size(root.right)+1;
    }

    //3
    public static int height(TreeNode root){
        return root==null?-1:Math.max(height(root.left),height(root.right))+1;
    }

    //4 find maximum
    public static int maximum(TreeNode root){
        TreeNode curr=root;
        while(curr!=null){
            curr=curr.right;
        }
        return curr.val;
    }

    //5 find minimum
    public static int minimum(TreeNode root){
        TreeNode curr=root;
        while(curr!=null){
            curr=curr.left;
        }
        return curr.val;
    }

    //6 find element in tree(find an element in tree)
    public static boolean find(TreeNode root, int data){
        TreeNode curr=root;
        while (curr!=null) {
            if(curr.val==data) return true;
            else if(curr.val<data) curr=curr.right;
            else curr=curr.left;
        }
        return false;
    }

    //7 node to root path
    public List<TreeNode> nodeToRootPath(TreeNode root, int data){
        List<TreeNode> ans=new ArrayList<>();
        TreeNode curr=root;
        while(curr!=null){
            ans.add(curr);
            if(curr.val==data) return ans;
            else if(curr.val<data) curr=curr.right;
            else curr=curr.left;
        }
        return ans;
    }


    // 8)Lowest Common Ancestor of a Binary Search Tree(Leetcode 235) -> brut force
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> rootToP=nodeToRootPath(root,p.val);
        List<TreeNode> rootToQ=nodeToRootPath(root,q.val);
        int i=0; int j=0;
        TreeNode LCA=null;
        while(i<rootToP.size() && j<rootToQ.size()){
            if(rootToP.get(i)!=rootToQ.get(j)) break;
            LCA=rootToP.get(i);
            i++;
            j++;
        }
        return LCA;
    }

    //optimize space
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode LCA=null;
        TreeNode curr=root;
        while(curr!=null){
            if(p.val>curr.val && q.val>curr.val){
                curr=curr.right;
            }
            else if(p.val<curr.val && q.val<curr.val){
                curr=curr.left;
            }else{
                LCA=curr;
                break;
            }
        }
        return (LCA!=null && find(LCA,p.val) && find(LCA,q.val))?LCA:null;
    }
    

}
