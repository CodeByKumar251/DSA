import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class l001_findSet {
    
    //1
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

    //4
    public static int maximum(TreeNode root){
        return root==null? -(int) 1e9 : Math.max(root.val, Math.max(maximum(root.left),maximum(root.right)));
    }

    //5
    public static int minimum(TreeNode root){
        return root==null? (int) 1e9 : Math.max(root.val, Math.min(minimum(root.left),minimum(root.right)));
    }

    //6 find element in tree
    public static boolean find(TreeNode root, int data){
        if(root==null) return false;

        if(root.val==data) return true;

        return find(root.left, data) || find(root.right,data);
    }

    //7.1 find Node to root path
    public static  ArrayList<TreeNode> nodeToRoot(TreeNode root, int data){
        ArrayList<TreeNode> lst=new ArrayList<>();
        nodeToRoot(root,data,lst);
        return lst;
    }

    public static boolean nodeToRoot(TreeNode root,int data, ArrayList<TreeNode> lst){
        if(root==null) return false;

        if(root.val==data){
            lst.add(root);
            return true;
        } 
        boolean res=nodeToRoot(root.left, data, lst) || nodeToRoot(root.right, data, lst);
        if(res){
            lst.add(root);
        }

        return res;
    }
 
    //7.2 top to buttom find Node to root path
    public static  ArrayList<TreeNode> nodeToRoot2(TreeNode root, int data){
        if(root==null){
            return new ArrayList<>();
        }

        if(root.val==data){
            ArrayList<TreeNode> base=new ArrayList<>();
            base.add(root);
            return base;
        }

        ArrayList<TreeNode> leftLst=nodeToRoot2(root.left, data);
        if(leftLst.size()>0){
            leftLst.add(root);
            return leftLst;
        }

        ArrayList<TreeNode> rightLst=nodeToRoot2(root.right, data);
        if(rightLst.size()>0){
            rightLst.add(root);
            return rightLst;
        }

        return new ArrayList<>();
     
    }



    //8 root to leaf path
    public static ArrayList<ArrayList<Integer>> rootToAllLeafPaths(TreeNode root) {
        // code here
        ArrayList<ArrayList<Integer>> paths=new ArrayList<>();
        ArrayList<Integer> path=new ArrayList<>();
        rootToAllLeafPaths(root,paths,path);
        return paths;
    }
    
    public static void rootToAllLeafPaths(TreeNode root, ArrayList<ArrayList<Integer>> paths,ArrayList<Integer> path){
        //edge case
        if(root==null){
            return;
        }
        
        //base case
        if(root.left==null && root.right==null){
            path.add(root.val);
            paths.add(new ArrayList<>(path));
            path.remove(path.size()-1);
            return;
        }
        
        path.add(root.val);
        rootToAllLeafPaths(root.left,paths,path);
        rootToAllLeafPaths(root.right,paths,path);
        path.remove(path.size()-1);
        return;
    }

    // 9 singleChildParent
    public static ArrayList<Integer> singleChildParent(TreeNode root){
        ArrayList<Integer> ans=new ArrayList<>();
        singleChildParent(root,ans);
        return ans;
    }

    public static void  singleChildParent(TreeNode root, ArrayList<Integer> ans){
        //base
        if(root==null || (root.left==null && root.right==null)){
            return;
        }
        if(root.left==null || root.right==null){
            ans.add(root.val);
        }
        singleChildParent(root.left,ans);
        singleChildParent(root.right,ans);
        return;
    }

    //countAllSingleChildParent
    public static int countAllSingleChildParent(TreeNode root){
        //base case
        if(root==null || (root.left==null && root.right==null)){
            return 0;
        }

        int leftSingleChild=countAllSingleChildParent(root.left);
        int rightSIngleChild=countAllSingleChildParent(root.right);

        int singleChild=leftSingleChild+rightSIngleChild;
        if(root.left==null || root.right==null){
            singleChild+=1;
        }
       
        return singleChild;
    
    }

    //11)All Nodes Distance K in Binary Tree(leetcode 863)
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        ArrayList<TreeNode> paths=new ArrayList<>();
        nodeToRoot(root,target.val,paths);
        List<Integer> ans=new ArrayList<>();
        TreeNode block=null;
        for(int i=0;i<paths.size();i++){
            kDown(paths.get(i),k-i,block,ans);
            block=paths.get(i);
        }
        return ans;
    }

    public void kDown(TreeNode root, int k, TreeNode block,List<Integer> ans){
        if(root==null || root==block || k<0){
            return;
        }

        if(k==0){
            ans.add(root.val);
            return;
        }
        kDown(root.left,k-1,block,ans);
        kDown(root.right,k-1,block,ans);
        return;
    }

    //11.1 optimize remove extra space
     public List<Integer> distanceK2(TreeNode root, TreeNode target, int k){
        List<Integer> ans=new ArrayList<>();
        distanceK2(root,target,k,ans);
        return ans;
     }

     public int distanceK2(TreeNode root, TreeNode target, int k,List<Integer> ans){
        if(root==null)
            return -1;

        if(root==target){
            kDown(root, k, null, ans);
            return 1;
        }

        int ld=distanceK2(root.left,target,k,ans);
        if(ld!=-1){
            kDown(root, k-ld, root.left, ans);
            return ld+1;
        }

        int rd=distanceK2(root.right,target,k,ans);
        if(rd!=-1){
            kDown(root, k-rd, root.right, ans);
            return rd+1;
        }
        return -1;
     }

     //12)Burning Tree(Burn the binary tree starting from the target node - GFG)
     public void burningTree(TreeNode root, int burningNode){
        ArrayList<ArrayList<Integer>> ans=new ArrayList<>();
        burningTree(root,burningNode,ans);
     }

     public int  burningTree(TreeNode root, int burningNode, ArrayList<ArrayList<Integer>> ans){
        if(root==null){
            return -1;
        }

        if(root.val==burningNode){
            burningTreeNode(root,null,0,ans);
            return 1;
        }
        int lt=burningTree(root.left,burningNode,ans);
        if(lt!=-1){
           burningTreeNode(root,root.left,lt,ans);
           return lt+1; 
        }

        int rt=burningTree(root.right,burningNode,ans);
        if(rt!=-1){
           burningTreeNode(root,root.right,rt,ans);
           return rt+1; 
        }
        return -1;
     }

     public void burningTreeNode(TreeNode root, TreeNode block, int time,ArrayList<ArrayList<Integer>> ans){
        if(root==null) return;

        if(ans.size()==time) {
            ans.add(new ArrayList<>());
        }

        ans.get(time).add(root.val);
        burningTreeNode(root.left,block,time+1,ans);
        burningTreeNode(root.right,block,time+1,ans);
        return;
     }

     //13)Burning Tree with waters node (design by pepcoding)
    public void burningTreeWithWater(TreeNode root, int burningNode,HashSet<Integer> waterSet){
        ArrayList<ArrayList<Integer>> ans=new ArrayList<>();
        burningTreeWithWater(root,burningNode,waterSet,ans);
     }

    public int  burningTreeWithWater(TreeNode root, int burningNode,HashSet<Integer> waterSet,ArrayList<ArrayList<Integer>> ans){
        if(root==null) return -1;

        if(root.val==burningNode){
            if(!waterSet.contains(root.val)){
                burningTreeNodeWater(root,null,waterSet,ans,0);
                return 1;
            }
            return -2;  //fire node present but it have water
           
        }

        int lt=burningTreeWithWater(root.left,burningNode,waterSet,ans);
        if(lt>0){
            if(!waterSet.contains(root.val)){
                burningTreeNodeWater(root,root.left,waterSet,ans,lt);
                return lt+1;
            }
            return -2;  //root has part of node to root path but it have water
        }
        
        if(lt==-2) return -2;

        int rt=burningTreeWithWater(root.right,burningNode,waterSet,ans);
        if(rt>0){
            if(!waterSet.contains(root.val)){
                burningTreeNodeWater(root,root.right,waterSet,ans,rt);
                return rt+1;
            }
            return -2;  //root has part of node to root path but it have water
        }
        if(rt==-2) return -2;

        return -1;
     }

     public void burningTreeNodeWater(TreeNode root, TreeNode block,HashSet<Integer> waterSet,ArrayList<ArrayList<Integer>> ans, int time){
        if(root==null || root==block || waterSet.contains(root.val)){
            return;
        }

        if(ans.size()==time){
            ans.add(new ArrayList<>());
        }

        ans.get(time).add(root.val);
        burningTreeNodeWater(root.left,block,waterSet,ans,time+1);
        burningTreeNodeWater(root.right,block,waterSet,ans,time+1);
        return;
     }

     //14)Lowest Common Ancestor of a Binary Tree(leetcode 236, with little modification-> p or q may and may not be in tree )
     //broot force
     public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
        
        List<TreeNode> pToRoot=nodeToRoot(root, p.val);
        List<TreeNode> qToRoot=nodeToRoot(root,q.val);
        TreeNode lca=null;

        int i=pToRoot.size()-1;
        int j=qToRoot.size()-1;
        while(i>=0 && j>=0){
            if(!pToRoot.get(i).equals(qToRoot.get(j))) break;
            lca=pToRoot.get(i);
            i--;
            j--;
        }

        return lca;
     }

     //14 optimize-> remove space complexity
     TreeNode LCANode=null;
    public TreeNode lowestCommonAncestorOpt(TreeNode root, TreeNode p, TreeNode q){
        LCA(root,p,q);
        return LCANode;
    }

    public boolean LCA(TreeNode root, TreeNode p, TreeNode q){
        if(root==null) return false;

        boolean selfPresent=false;
        if(root.equals(p) || root.equals(q)) selfPresent =true;

        boolean leftPresent=LCA(root.left, p, q);
        if(LCANode!=null) return true;
        boolean rightPresent=LCA(root.right,p,q);
        if(LCANode!=null) return true;

        if( (leftPresent && rightPresent) || (leftPresent && selfPresent) || (rightPresent && selfPresent) ){
            LCANode=root;
            return true;
        }

        return selfPresent || leftPresent || rightPresent;

    }



    public static void main(String args[]){
        TreeNode node1=new TreeNode(1);
        TreeNode node2=new TreeNode(2);
        TreeNode node3=new TreeNode(3);
        TreeNode node4=new TreeNode(4);
        TreeNode node5=new TreeNode(5);
        TreeNode node6=new TreeNode(6);
        TreeNode node7=new TreeNode(7);

        node1.left=node2;
        node2.left=node4;
        node4.right=node5;
        node5.left=node6;
        node1.right=node3;
        node3.left=node7;
        
        // ArrayList<TreeNode> ans=nodeToRoot(node1,5);
        // for(TreeNode node:ans){
        //     System.out.println(node.val+" ");
        // }

        System.out.println(countAllSingleChildParent(node1));

    }

    

}
