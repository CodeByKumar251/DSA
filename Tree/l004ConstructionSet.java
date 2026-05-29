
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.plugins.bmp.BMPImageWriteParam;
public class l004ConstructionSet{
        
    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val){
            this.val=val;
        }
    }


    //1 construct any valid  BST from inorder traversal(sorted array)
    //T.C=O(n), S.C=0(h)=stack 
    public TreeNode bstFromInorder(int[] inorder){
        int startIndex=0;
        int endIndex=inorder.length-1;

        return bstFromInorder(inorder,startIndex,endIndex);
    }

    public TreeNode bstFromInorder(int[] inorder,int startIndex,int endIndex){
        if(startIndex>endIndex){
            return null;
        }

        int mid=startIndex+(endIndex-startIndex)/2;

        TreeNode root=new TreeNode(inorder[mid]);
        root.left=bstFromInorder(inorder,startIndex,mid-1);
        root.right=bstFromInorder(inorder,mid+1,endIndex);

        return root;   
    }

    //2 convert sorted doubly linked list to BST
    public TreeNode sdllToBST(TreeNode head){

        if(head==null || head.right==null){
            return head;
        }

        TreeNode mid=getMidNode(head);
        TreeNode prev=mid.left;
        TreeNode aft=mid.right;

        mid.left=mid.right=null;
        aft.left=null;
        if(prev!=null){
            prev.right=null;
        }
        TreeNode root=mid;
        TreeNode leftHead=prev!=null?head:null;
        TreeNode rightHead=aft;

        root.left=sdllToBST(leftHead);
        root.right=sdllToBST(rightHead);

        return root;
    }

    public TreeNode getMidNode(TreeNode root){
        TreeNode fast=root;
        TreeNode slow=root;
        while(fast!=null && fast.right!=null && fast.right.right!=null){
            fast=fast.right.right;
            slow=slow.right;
        }

        return slow;

    }

    //3 convert a binary tree(BT) into BST
    //A!=BT->DLL(doubly linked list) -> SDLL(sorted DLL)->BST
    //BT->DLL => moriss traversal (can also do from stack)

    public TreeNode btToBST(TreeNode root){
        if(root==null) return root;

        TreeNode head=btToDLL(root);  //binary tree to doubly linked list
        head=mergeSort(head);    //doubly linked list to sorted doubly linked list
        root=sdllToBST(head);     //sorted doubly linked list to BST

        return root;
    }

    public TreeNode btToDLL(TreeNode root){
        TreeNode curr=root;
        TreeNode dummy=new TreeNode(-1);
        TreeNode prev=dummy;

        while (curr!=null) {
            TreeNode left=curr.left;
            if(left==null){
                curr.left=prev;
                prev.right=curr;
                
                curr=curr.right;
                prev=prev.right;
            }else{
                TreeNode rightMostNode=findRightMostNode(left,curr);
                if(rightMostNode.right==null){       //thread creation
                    rightMostNode.right=curr;
                    curr=curr.left;
                }else{    //thread break
                    rightMostNode.right=null;
                    curr.left=prev;
                    prev.right=curr;

                    curr=curr.right;
                    prev=prev.right;
                }
            }
        }

        TreeNode head=dummy.right;
        dummy.right=head.left=null;

        return head;
    }

    public TreeNode findRightMostNode(TreeNode left,TreeNode curr){
        while (left.right!=null && left.right!=curr) {
            left=left.right;
        }
        return left;
    }

    TreeNode mergeTwoSortedDLL(TreeNode l1, TreeNode l2){
        if(l1==null || l2==null){
            return (l1!=null)?l1:l2;
        }

        TreeNode dummy=new TreeNode(-1);
        TreeNode prev=dummy;

        while(l1!=null && l2!=null){
            if(l1.val<=l2.val){
                prev.right=l1;
                l1.left=prev;

                l1=l1.right;
            }else{
                prev.right=l2;
                l2.left=prev;

                l2=l2.right;
            }

            prev=prev.right;
        }

        if(l1!=null){
            prev.right=l1;
            l1.left=prev;
        }else{
            prev.right=l2;
            l2.left=prev;
        }

        TreeNode head=dummy.right;
        head.left=dummy.right=null;

        return head;
    }

    public TreeNode mergeSort(TreeNode head){
        if(head==null || head.right==null){
            return head;
        }

        TreeNode midNode=getMidNode(head);
        TreeNode forwHead=midNode.right;
        forwHead.left=midNode.right=null;

        return mergeTwoSortedDLL(mergeSort(head), mergeSort(forwHead));

    }

    // 4 display to visualize tree
    public void display(TreeNode root){
        if(root==null){
            return;
        }

        String str="";
        str+=(root.left!=null)?root.left.val:".";
        str+="--->"+root.val+"<---";
        str+=(root.right!=null)?root.right.val:".";

        System.out.println(str);
        display(root.left);
        display(root.right);
    }

    //5 preorder to BST(unique BST will created)
    public TreeNode preOrderToBST(ArrayList<Integer> preOrder){
        int[] idx=new int[1];
        idx[0]=0;
        TreeNode root=preOrderToBST(preOrder,-1e9,1e9,idx);
        return root;
    }

    public TreeNode preOrderToBST(ArrayList<Integer> preOrder, double lr,double rr,int[] idx){
        int i=idx[0];
        if(i>=preOrder.size() || preOrder.get(i)<lr || preOrder.get(i)>rr){
            return null;
        }

        TreeNode root=new TreeNode(preOrder.get(i));
        idx[0]++;

        root.left=preOrderToBST(preOrder,lr,root.val,idx);
        root.right=preOrderToBST(preOrder,root.val,rr,idx);

        return root;

    }

    //6 postOrder to BST(unique)
    public TreeNode postOrderToBST(ArrayList<Integer> postOrder){
        int[] idx=new int[1];
        idx[0]=postOrder.size()-1;
        TreeNode root=postOrderToBST(postOrder,-1e9,1e9,idx);
        return root;
    }

    public TreeNode postOrderToBST(ArrayList<Integer> postOrder, double lr,double rr,int[] idx){
        int i=idx[0];
        if(i<=-1 || postOrder.get(i)<lr || postOrder.get(i)>rr){
            return null;
        }

        TreeNode root=new TreeNode(postOrder.get(i));
        idx[0]--;

        root.right=postOrderToBST(postOrder,root.val,rr,idx);
        root.left=postOrderToBST(postOrder,lr,root.val,idx);
        
        return root;

    }


    //7 Serialize and Deserialize BST (leetcode 449  https://leetcode.com/problems/serialize-and-deserialize-bst/description/)

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder stb=new StringBuilder();
        serialize(root,stb);
        return stb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.equals("")) return null;
        String arr[]=data.split(" ");
        ArrayList<Integer> preOrder=new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            preOrder.add(Integer.parseInt(arr[i]));
        }
        int[] idx=new int[1];
        idx[0]=0;
        TreeNode root=preOrderToBST(preOrder,-1e13,1e13,idx);
        return root;

    }

    private void serialize(TreeNode root,StringBuilder stb){
        if(root==null) return;

        stb.append(root.val+" ");
        serialize(root.left,stb);
        serialize(root.right,stb);
        return;
    };

    
    //9 construct binary tree from preOrder and inorder(leetcode 105 https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/)
   // T.C= nlog(n), n for searching log(n) for creation, S.C=o(1), wrost T.c=O(n^2)
   //A1
    public TreeNode buildTree(int[] preOrder,int[] inOrder){
        return buildTree(preOrder,0,preOrder.length-1,inOrder,0,inOrder.length-1);
    }

    private TreeNode buildTree(int[] preOrder , int psi, int pei, int[] inOrder, int isi, int iei){
        //base case
        if(psi>pei) return null;

        TreeNode root=new TreeNode(preOrder[psi]);
        int idx=isi;
        while(inOrder[idx]!=preOrder[psi]){
            idx++;
        }

        int tnel=idx-isi;

        root.left=buildTree(preOrder,psi+1,psi+tnel,inOrder,isi,idx-1);
        root.right=buildTree(preOrder,psi+tnel+1,pei,inOrder,idx+1,iei);

        return root;
    }

     //A2 -> best T.C=log(n), wrost T.C=o(n), S.C=o(n)
    public TreeNode buildTreeA2(int[] preOrder,int[] inOrder){
        HashMap<Integer,Integer> map=new HashMap();
        for(int i=0;i<inOrder.length;i++){
            map.put(inOrder[i], i);
        }
        return buildTreeA2(preOrder,0,preOrder.length-1,inOrder,0,inOrder.length-1,map);
    }

    private TreeNode buildTreeA2(int[] preOrder , int psi, int pei, int[] inOrder, int isi, int iei,HashMap<Integer,Integer> map){
        //base case
        if(psi>pei) return null;

        TreeNode root=new TreeNode(preOrder[psi]);
        int idx=map.get(preOrder[psi]);

        int tnel=idx-isi;

        root.left=buildTreeA2(preOrder,psi+1,psi+tnel,inOrder,isi,idx-1,map);
        root.right=buildTreeA2(preOrder,psi+tnel+1,pei,inOrder,idx+1,iei,map);

        return root;
    }

    //A3 :- in A2 after using map there are no need of inOrder, we will try to reduce parameter
    public TreeNode buildTreeA3(int[] preOrder,int[] inOrder){
        HashMap<Integer,Integer> map=new HashMap();
        for(int i=0;i<inOrder.length;i++){
            map.put(inOrder[i], i);
        }
        return buildTreeA3(preOrder,0,preOrder.length-1,map);
    }

    int index=0;
    private TreeNode buildTreeA3(int[] preOrder , int start, int end,HashMap<Integer,Integer> map){
        //base case
        if(start>end) return null;

        int rootVal=preOrder[index++];
        TreeNode root=new TreeNode(rootVal);
        int idx=map.get(rootVal);

        root.left=buildTreeA3(preOrder,start,idx-1,map);
        root.right=buildTreeA3(preOrder,idx+1,end,map);

        return root;
    }

   


    //10 Construct binary tree from postOrder and inOrder (leetcode 106 https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/)
    // T.C= nlog(n), n for searching log(n) for creation, S.C=o(1), wrost T.c=O(n^2)
    public TreeNode buildTree2(int[] postOrder,int[] inOrder){
        return buildTree2(postOrder,0,postOrder.length-1,inOrder,0,inOrder.length-1);
    }

    private TreeNode buildTree2(int[] postOrder , int psi, int pei, int[] inOrder, int isi, int iei){
        //base case
        if(psi>pei) return null;

        TreeNode root=new TreeNode(postOrder[pei]);

        int idx=isi;
        while(inOrder[idx]!=postOrder[pei]){
            idx++;
        }

        int tnel=idx-isi;

        root.left=buildTree2(postOrder,psi,psi+tnel-1,inOrder,isi,idx-1);
        root.right=buildTree2(postOrder,psi+tnel,pei-1,inOrder,idx+1,iei);

        return root;
    }

   // 11 construct binary tree from preOrder and postOrder(to make exactly same binary tree need full binary tree)(leetcode 889 https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/)
   // T.C= nlog(n), n for searching log(n) for creation, S.C=o(1), wrost T.c=O(n^2)  
   public TreeNode buildTree3(int[] preOrder,int[] postOrder){
        return buildTree3(preOrder,0,preOrder.length-1,postOrder,0,postOrder.length-1);
    }

    private TreeNode buildTree3(int[] preOrder, int prsi, int prei, int[] postOrder , int posi, int poei){
        //base case
        if(prsi>prei) return null;

        TreeNode root=new TreeNode(preOrder[prsi]);

        if(prsi==prei) return root;

        int idx=posi;
        while(postOrder[idx]!=preOrder[prsi+1]){
            idx++;
        }
        int tnel=idx-posi+1;
        
        
        root.left=buildTree3(preOrder,prsi+1,prsi+tnel, postOrder,posi,posi+tnel-1);
        root.right=buildTree3(preOrder,prsi+tnel+1,prei,postOrder,idx+1,poei-1);

        return root;
    }

    //12)Serialize and Deserialize Binary Tree(leetocode 297, https://leetcode.com/problems/serialize-and-deserialize-binary-tree/description/)
    //A1 , this approach will not work , if value  in tree will be duplicate becuase in build tree search of root will give wrong index
    // Encodes a tree to a single string.
    public String serializeBT(TreeNode root) {
        if(root==null) return "";
        String data=preOrderTrav(root);

        data=data+" "+inOrderTrav(root);
        return data;
        
    }

    // Decodes your encoded data to tree.
    public TreeNode deserializeBT(String data) {
        if(data.length()==0) return null;
        String[] preOrderInOrder=data.split(" ");

        String[] preOrderStr=preOrderInOrder[0].split(".");

        int[] preOrder=new int[preOrderStr.length];
        for(int i=0;i<preOrderStr.length;i++){
            preOrder[i]=Integer.parseInt(preOrderStr[i]);
        }

        String[] inOrderStr=preOrderInOrder[1].split(".");
        int[] inOrder=new int[inOrderStr.length];

        for(int i=0;i<inOrderStr.length;i++){
            inOrder[i]=Integer.parseInt(inOrderStr[i]);
        }

         return buildTree(preOrder,0,preOrder.length-1,inOrder,0,inOrder.length-1);
         
    }

    private String preOrderTrav(TreeNode root){
        if(root==null) return "";

        String data=root.val+".";
        data+=preOrderTrav(root.left);
        data+=preOrderTrav(root.right);
        return data;
    }

    private String inOrderTrav(TreeNode root){
        if(root==null) return "";

        String data="";
        data+=inOrderTrav(root.left);
        data+=root.val+".";
        data+=inOrderTrav(root.right);
        return data;
    }

    //A2

    int idx=0;
    // Encodes a tree to a single string.
    public String serializeA2(TreeNode root) {
        if(root==null) return "";
        StringBuilder stb=new StringBuilder();
        serializeA2(root,stb);
        return stb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserializeA2(String data) {
        if(data.length()==0) return null;
        String[] preOrder=data.split(" ");
        return deserializeA2(data,preOrder);
    }
    
    private void  serializeA2(TreeNode root, StringBuilder stb) {
        if(root==null){
            stb.append("#"+" ");
            return;
        }
        stb.append(root.val+" ");
        serializeA2(root.left,stb);
        serializeA2(root.right,stb);
    }

    public TreeNode deserializeA2(String data, String[] preOrder){
        //base case
        if(idx>=preOrder.length || preOrder[idx].equals("#")){
            idx++;
            return null;
        }

        TreeNode root=new TreeNode(Integer.parseInt(preOrder[idx++]));
        root.left=deserializeA2(data,preOrder);
        root.right=deserializeA2(data,preOrder);

        return root;
    }

    //A3

    // Encodes a tree to a single string.
    public String serializeA3(TreeNode root) {
        if(root==null) return "";

        //level order traversal
        StringBuilder stb=new StringBuilder();
        //linked list as queue
        LinkedList<TreeNode> lst=new LinkedList<>();
        lst.addFirst(root);

        while(!lst.isEmpty()){
            TreeNode rooNode=lst.removeFirst();
            stb.append((rooNode!=null?rooNode.val:"#") +" ");

            if(rooNode==null){
                continue;
            }
            lst.addLast(rooNode.left);
            lst.addLast(rooNode.right);
        }
        System.out.println(stb.toString());
        return stb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserializeA3(String data) {
        if(data.length()==0) return null;

        String[] levelOrder=data.split(" ");
        for(String ele:levelOrder){
            System.out.println(ele);
        }

        LinkedList<TreeNode> lst=new LinkedList<>();
        TreeNode root= new TreeNode(Integer.parseInt(levelOrder[0]));
        lst.addFirst(root);
        
        int idx=1;
        while(!lst.isEmpty()){
            TreeNode rootNode=lst.removeFirst();

            if(!levelOrder[idx].equals("#")){
                TreeNode leftChild=new TreeNode(Integer.parseInt(levelOrder[idx]));
                rootNode.left=leftChild;
                lst.addLast(leftChild);
            }
            idx++;
            if(!levelOrder[idx].equals("#")){
                TreeNode rightChild=new TreeNode(Integer.parseInt(levelOrder[idx]));
                rootNode.right=rightChild;
                lst.addLast(rightChild);
            }
            idx++;
        }

        return root;
    }

    //13)check balance binary tree (leetcode 110 https://leetcode.com/problems/balanced-binary-tree/description/)

    /*
    A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one */
    public boolean isBalanced(TreeNode root) {

        return isBalancedHelp(root).isBalanced;
    }

    private bstPair isBalancedHelp(TreeNode root){
        //base case
        if(root==null){
            return new bstPair();
        }

        bstPair bstL=isBalancedHelp(root.left);
        bstPair bstR=isBalancedHelp(root.right);

        bstPair bstF=new bstPair();
        bstF.isBalanced=(bstL.isBalanced && bstR.isBalanced && Math.abs(bstL.height-bstR.height)<=1 );
        if(!bstF.isBalanced){
            return bstF;
        }
        bstF.height=Math.max(bstL.height,bstR.height)+1;
        return bstF;
    }

    class bstPair{
        int height=0;
        boolean isBalanced=true;
    }

    //14)largest BST in tree (GFG https://www.geeksforgeeks.org/problems/largest-bst/1)
    // little modification from gfg return largest bst Node with size well as
    public int largestBst(TreeNode root){

        return largestBst_(root).size;
    }

    public TreeNode largestBstNode(TreeNode root){

        return largestBst_(root).largestNode;
    }

    private bst largestBst_(TreeNode root){
        //base case
        if(root==null){
            return new bst();
        }

        bst bstL= largestBst_(root.left);
        bst bstR= largestBst_(root.right);

        bst bstF=new bst();

        bstF.isBST=(bstL.isBST && bstR.isBST && root.val>bstL.max && root.val<bstR.min);
    
        if(bstF.isBST){
            bstF.min=Math.min(root.val, bstL.min);
            bstF.max=Math.max(root.val, bstR.max);
            bstF.size=bstL.size+bstR.size+1;
            bstF.largestNode=root;
        }else{
            if(bstL.size>bstR.size){
                bstF.size=bstL.size;
                bstF.largestNode=bstL.largestNode;
            }else{
                 bstF.size=bstR.size;
                bstF.largestNode=bstR.largestNode;
            }
        }

        return bstF;
    }

    class bst{
        boolean isBST=true;
        int min=Integer.MAX_VALUE;
        int max=Integer.MIN_VALUE;
        int size=0;
        TreeNode largestNode=null;
    }


}