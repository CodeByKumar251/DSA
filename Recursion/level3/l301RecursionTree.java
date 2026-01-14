
import java.util.ArrayList;

public class l301RecursionTree {

    public static int permutationInfiCoins(int[] arr, int tar, String ans) {
        // base case
        if (tar == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for (int ele : arr) {
            if (tar - ele >= 0) {
                count += permutationInfiCoins(arr, tar - ele, ans + ele);
            }
        }

        return count;
    }

    public static int combinationInfiCoins(int[] arr, int tar, int idx, String ans) {
        // base
        if (tar == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for (int i = idx; i < arr.length; i++) {
            if (tar - arr[i] >= 0) {
                count += combinationInfiCoins(arr, tar - arr[i], i, ans + arr[i]);
            }
        }

        return count;
    }

    public static int combinationSingleCoins(int[] arr, int tar, int idx, String ans) {
        // base
        if (tar == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for (int i = idx; i < arr.length; i++) {
            if (tar - arr[i] >= 0) {
                count += combinationSingleCoins(arr, tar - arr[i], i + 1, ans + arr[i]);
            }
        }

        return count;
    }

    public static int permutationSingleCoins(int[] arr, int tar, int[] vis, String ans){

        if(tar==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        for(int i=0;i<arr.length;i++){
            if(vis[i]==0 && tar-arr[i]>=0){
                vis[i]=1;
                count+=permutationSingleCoins(arr,tar-arr[i],vis,ans+arr[i]);
                vis[i]=0;
            }
        }
        return count;
    }

    //removed space of vis arr
    public static int  permutationSingleCoinsBest(int arr[],int tar,String ans){
        if(tar==0){
            System.out.println(ans);
            return 1;
        }
 
        int count=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>0 && tar-arr[i]>=0){
                int val=arr[i];
                arr[i]=-val;
                count+=permutationSingleCoinsBest(arr,tar-val,ans+val);
                arr[i]=val;

            }
        }
        return count;
    }

    

    // =================================Do above example with subsequnce approach=======================================

    public static int  combinationSingleCoins_sub(int arr[],int tar,int idx, String ans){
        if(tar==0 || idx==arr.length){
            if(tar==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count=0;
        if(tar-arr[idx]>=0)
            count+=combinationSingleCoins_sub(arr,tar-arr[idx],idx+1,ans+arr[idx]);

        count+=combinationSingleCoins_sub(arr,tar,idx+1,ans);

        
        return count;
    }

    public static int  combinationInfiCoins_sub(int arr[],int tar,int idx, String ans){
        //base case
        if(tar==0 || idx==arr.length){
            if(tar==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        //pick
        int count=0;
        if(tar-arr[idx]>=0){
            count+=combinationInfiCoins_sub(arr,tar-arr[idx],idx,ans+arr[idx]);
        }
        //not pick
        count+=combinationInfiCoins_sub(arr, tar, idx+1, ans);

        return count;
    }


    public static int  permutationInfiCoins_sub(int arr[],int tar,int idx, String ans){
        //base case
        if(tar==0 || idx==arr.length){
            if(tar==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        //pick
        int count=0;
        if(tar-arr[idx]>=0){
            count+=permutationInfiCoins_sub(arr,tar-arr[idx],0,ans+arr[idx]);
        }
        //not pick
        count+=permutationInfiCoins_sub(arr, tar, idx+1, ans);

        return count;
        
    }

    public static int  permutationSingleCoins_sub(int arr[],int tar,int idx, String ans){
        //base case
        if(tar==0 || idx==arr.length){
            if(tar==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        //pick
        int count=0;
        if(arr[idx]>0 && tar-arr[idx]>=0){
            int val=arr[idx];
            arr[idx]=-val;
            count+=permutationSingleCoins_sub(arr,tar-val,0,ans+val);
            arr[idx]=val;
        }
        //not pick
        count+=permutationSingleCoins_sub(arr, tar, idx+1, ans);

        return count;

    }

    public static void subseq_usingComb(String str, int idx, ArrayList<String> ans, String psf){

        //base case
        if(idx==str.length()){
            ans.add(psf);
            return;
        }

        // subseq_usingComb(str, idx+1, ans, psf);
        for(int i=idx;i<str.length();i++){
            subseq_usingComb(str,i+1,ans,psf+str.charAt(i));
        }

        return;

    }

    // ===================================================================================================================

    public static void combinationPermutation() {
        int arr[] = { 2, 3, 5, 7 };
        int tar = 10;
        String ans = "";

        // System.out.println("total permutation infi= "+permutationInfiCoins(arr, tar,
        // ans));
        // System.out.println(combinationInfiCoins(arr, tar, 0, ans));
        //  System.out.println(combinationSingleCoins(arr, tar, 0, ans));

        int[] vis={0,0,0,0};
        // System.out.println(permutationSingleCoins(arr,tar,vis,""));
        System.out.println(permutationSingleCoinsBest(arr,tar,""));
        
    }

    public static void combinationPermutation_sub() {
        int arr[] = { 2, 3, 5, 7 };
        int tar = 10;
        String ans = "";

        //System.out.println(combinationSingleCoins_sub(arr,tar,0,""));
        //System.out.println(combinationInfiCoins_sub(arr,tar,0,""));
        // System.out.println(permutationInfiCoins_sub(arr,tar,0,""));
        //System.out.println(permutationSingleCoins_sub(arr,tar,0,""));
        
        ArrayList<String> subseq=new ArrayList<>();
        subseq_usingComb("abc",0,subseq,"");
        System.out.println(subseq);


    }




    public static void main(String args[]) {
        // combinationPermutation();
        combinationPermutation_sub();
    }

}