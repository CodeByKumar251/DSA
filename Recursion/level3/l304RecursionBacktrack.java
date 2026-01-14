import java.lang.Character.Subset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.plaf.synth.SynthStyle;

public class l304RecursionBacktrack {

    public static boolean isSafeToPlaceNumber(char[][] board, int r, int c, int num) {
        int n = board.length;
        int m = board[0].length;
        // row check
        for (int col = 0; col < m; col++) {
            if (board[r][col] - '0' == num)
                return false;
        }

        // col check
        for (int row = 0; row < n; row++) {
            if (board[row][c] - '0' == num)
                return false;
        }

        // matrix check
        int row = (r / 3) * 3;
        int col = (c / 3) * 3;

        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                if (board[i][j] - '0' == num)
                    return false;
            }
        }
        return true;
    }

    public static boolean solveSudoku(char[][] board, ArrayList<Integer> emptyIndex, int idx) {
        if (idx == emptyIndex.size()) {
            return true;
        }

        int cell = emptyIndex.get(idx);
        int r = cell / 9;
        int c = cell % 9;
        for (int num = 1; num <= 9; num++) {
            if (isSafeToPlaceNumber(board, r, c, num)) {
                board[r][c] = (char) (num + '0');
                if (solveSudoku(board, emptyIndex, idx + 1))
                    return true;
                board[r][c] = '.';
            }
        }
        return false;
    }

    public static void solveSudoku(char[][] board) {
        ArrayList<Integer> emptyIndex = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.')
                    emptyIndex.add((i * 9) + j);
            }
        }
        solveSudoku(board, emptyIndex, 0);

    }

    // ==================sudoku using bits========================================================
    int[] row = new int[9];
    int[] col = new int[9];
    int[][] mat = new int[3][3];

    public void solveSudoku_2(char[][] board) {
        ArrayList<Integer> emptyIndex = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.')
                    emptyIndex.add((i * 9) + j);
                else {
                    int mask = 1 << (board[i][j] - '0');
                    row[i] = (row[i] ^ mask); // row[i]^=mask, ans=ans+1, ans+=1
                    col[j] ^= mask;
                    mat[i / 3][j / 3] ^= mask;
                }
            }
        }
        solveSudoku_2(board, emptyIndex, 0);
    }

    boolean solveSudoku_2(char[][] board, ArrayList<Integer> emptyIndex, int idx) {
        if (idx == emptyIndex.size()) {
            return true;
        }

        int cell = emptyIndex.get(idx);
        int r = cell / 9;
        int c = cell % 9;
        for (int num = 1; num <= 9; num++) {
            int mask = 1 << num;
            if ((row[r] & mask) == 0 && (col[c] & mask) == 0 && (mat[r / 3][c / 3] & mask) == 0) {
                row[r] ^= mask;
                col[c] ^= mask;
                mat[r / 3][c / 3] ^= mask;
                board[r][c] = (char) (num + '0');
                if (solveSudoku(board, emptyIndex, idx + 1))
                    return true;
                board[r][c] = '.';
                row[r] ^= mask;
                col[c] ^= mask;
                mat[r / 3][c / 3] ^= mask;
            }
        }

        return false;
    }

    // ===============================word break(pep
    // coding)==========================
    public static int wordBreak(String str, String ans, HashSet<String> dict, int longestWordLen) {
        // base case
        if (str.length() == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            String nstr = str.substring(0, i + 1);
            if (nstr.length() > longestWordLen) {
                break;
            }
            if (dict.contains(nstr)) {
                count += wordBreak(str.substring(i + 1), ans + nstr + " ", dict, longestWordLen);
            }
        }
        return count;
    }

    // paring friends if given in string form
    public int friendsParing(String str, String ans, boolean[] used) {
        // first unused index
        int idx = 0;
        while (idx < str.length()) {
            if (!used[idx])
                break;
            idx++;
        }

        // base case
        if (idx == str.length()) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        used[idx] = true;
        count += friendsParing(str, ans + "(" + str.charAt(idx) + ")", used); // single friends

        for (int i = idx + 1; i < str.length(); i++) {
            if (!used[i]) {
                String friends = str.charAt(idx) + "" + str.charAt(i);
                used[i] = true;
                count += friendsParing(str, ans + friends, used); // pared friends
                used[i] = false;
            }
        }

        used[idx] = false;

        return count;
    }

    // =========================================crossword===============================
    public boolean isPossibleToPlace_H(char[][] board, String word, int r, int c) {
        int n=board.length; int m=board[0].length;
        int len=word.length();

        if(c+len>m) return false;
        if(c==0 && c+len<m && board[r][c+len]!='+' ) {
            return false;
        }else if(c>0 && board[r][c-1]!='+' && c+len==m ){
            return false;
        }else if(c>0 && c+len<m && board[r][c-1]!='+' && board[r][c+len]!='+' ) {
            return false;
        }

        for(int i=0;i<len;i++){
            if(board[r][i+c]!='-' && board[r][i+c]!=word.charAt(i)) return false;
        }
        
        return true;
    }

    public int place_H(char[][] board, String word, int r, int c) {
        int vis=0;
        for (int i = 0; i < word.length(); i++) {
            if(board[r][i+c]=='-'){
                int mask=(1<<i);
                vis^=mask;
                board[r][i+c] = word.charAt(i);
            }
            
        }
        return vis;

    }

    public void unPlace_H(char[][] board, String word, int r, int c, int vis) {
        for (int i = 0; i < word.length(); i++) {
            int mask=(1<<i);
            if((mask & vis)!=0){
                board[r][i+c] = '-';
            }   
        }
    }

    public boolean isPossibleToPlace_V(char[][] board, String word, int r, int c) {
        int n=board.length; int m=board[0].length; int len=word.length();

        if(r+len>n) return false;

        if(r==0 && r+len<n && board[r+len][c]!='+' ) {
            return false;
        }else if(r>0 && board[r-1][c]!='+' && r+len==n ){
            return false;
        }else if(r>0 && r+len<n && board[r-1][c]!='+' && board[r+len][c]!='+' ) {
            return false;
        }

        for(int i=0;i<word.length();i++){
            if(board[i+r][c]!='-' && board[i+r][c]!=word.charAt(i)) return false;
        }

        return true;
    }

    public int place_V(char[][] board, String word, int r, int c) {
        int vis=0;
        for (int i = 0; i < word.length(); i++) {
            if(board[r+i][c]=='-'){
                int mask=(1<<i);
                vis^=mask;
                board[r+i][c] = word.charAt(i);
            }
            
        }
        return vis;
    }

    public void unPlace_V(char[][] board, String word, int r, int c,int vis) {
        for (int i = 0; i < word.length(); i++) {
            int mask=(1<<i);
            if((mask & vis)!=0){
                board[i+r][c] = '-';
            }   
        }
    }

    public boolean crossword(char[][] board, String[] words, int idx) {
        if (idx == words.length) {
            return true ;
        }

        String word = words[idx];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if(board[i][j]=='-' || board[i][j]==word.charAt(0)){
                    if(isPossibleToPlace_H(board, word, i, j)){
                        int vis=place_H(board, word, i, j);
                        if(crossword(board,words,idx+1)) return true;
                        unPlace_H(board, word, i, j, vis);
                    }
                    if(isPossibleToPlace_V(board, word, i,j)){
                        int vis=place_V(board, word, i,j);
                        if(crossword(board,words,idx+1)) return true;
                        unPlace_V(board, word, i, j, vis);
                    }

                }
            }
        }

        return false;

    }

    public void crossword() {

        char[][] board = {
                { '+', '+', '+', '+', '+', '+', '+', '+', '+', '-' },
                { '-', '+', '+', '+', '+', '+', '+', '+', '+', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '+', '+', '-' },
                { '-', '+', '+', '+', '+', '+', '+', '+', '+', '-' },
                { '-', '+', '+', '+', '+', '+', '+', '+', '+', '-' },
                { '-', '+', '+', '+', '+', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '+', '+', '+', '-' },
                { '-', '+', '+', '+', '+', '+', '+', '+', '+', '-' },
                { '+', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '+', '+', '+', '+', '+', '+', '+', '+', '+', '+' }
        };

        String[] word = { "CHEMISTRY", "PHYSICS", "HISTORY", "MATHS", "CIVICS", "GEOGRAPHY" };

        char[][] output = {
                { '+', '+', '+', '+', '+', '+', '+', '+', '+', 'C' },
                { 'P', '+', '+', '+', '+', '+', '+', '+', '+', 'H' },
                { 'H', 'I', 'S', 'T', 'O', 'R', 'Y', '+', '+', 'E' },
                { 'Y', '+', '+', '+', '+', '+', '+', '+', '+', 'M' },
                { 'S', '+', '+', '+', '+', '+', '+', '+', '+', 'I' },
                { 'I', '+', '+', '+', '+', 'M', 'A', 'T', 'H', 'S' },
                { 'C', 'I', 'V', 'I', 'C', 'S', '+', '+', '+', 'T' },
                { 'S', '+', '+', '+', '+', '+', '+', '+', '+', 'R' },
                { '+', 'G', 'E', 'O', 'G', 'R', 'A', 'P', 'H', 'Y' },
                { '+', '+', '+', '+', '+', '+', '+', '+', '+', '+' }
        };

        crossword(board,word,0);
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                System.out.print(board[i][j]+ ",");
            }
            System.out.println();
        }

    }

    public static void sudoku() {
        char[][] board = { { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
                { '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
                { '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
                { '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
                { '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };
        solveSudoku(board);
        l304RecursionBacktrack obj = new l304RecursionBacktrack();
        obj.solveSudoku_2(board);
    }

    // Partition Equal Subset Sum (leetocde 416)
    public int PartitionEquiSets(int[] nums, int sum1, int sum2, ArrayList<ArrayList<Integer>> set, int ind){
        //base case
        if(ind==nums.length){
            if(sum1!=sum2) return 0;
            for(ArrayList<Integer> s:set){
                System.out.print(s+" ");
            }
            System.out.println();
            return 1;
        }


        int count=0;
        //part of subset 1
        set.get(0).add(nums[ind]);
        count+=PartitionEquiSets(nums,sum1+nums[ind],sum2,set,ind+1);
        set.get(0).remove(set.get(0).size()-1);

        //part of subset 2
        set.get(1).add(nums[ind]);
        count+=PartitionEquiSets(nums, sum1, sum2+nums[ind], set, ind+1);
        set.get(1).remove(set.get(1).size()-1);
        
        return count;
    }

    public void PartitionEquiSets(){
        int[] nums={1,5,11,5};
        ArrayList<ArrayList<Integer>> set=new ArrayList<>();
        set.add(new ArrayList<>());
        set.add(new ArrayList<>());
        //to stop arrangment of ans, fixed first number in one set
        set.get(0).add(nums[0]);
        System.out.println(PartitionEquiSets(nums, nums[0], 0, set, 1));
    }

    // Partition to K Equal Sum Subsets(leetocde 698)
    public int PartitionKSubsets(int[] nums, int[] sum, ArrayList<ArrayList<Integer>> sets, int ind ){
        int k=sum.length;
        //base case
        if(ind==nums.length){
            for(int i=1;i<k;i++){
                if(sum[i-1]!=sum[i]) return 0;
            }
            for(ArrayList<Integer> s:sets){
                System.out.print(s+" ");
            }
            System.out.println();
            return 1;
        }


        int count=0;
        for(int i=0;i<k;i++){
            sum[i]+=nums[ind];
            sets.get(i).add(nums[ind]);
            count+=PartitionKSubsets(nums,sum,sets,ind+1);
            sets.get(i).remove(sets.get(i).size()-1);
            sum[i]-=nums[ind];
            if(sets.get(i).size()==0){
                break;
            }
        }

        return count;
    }

    public void PartitionKSubsets(){
        int[] nums={1,2,3,4,5,6};
        int k=3;
        ArrayList<ArrayList<Integer>> sets=new ArrayList<>();
        for(int i=0;i<k;i++){
            sets.add(new ArrayList<>());
        }
        int[] sum=new int[k];
        PartitionKSubsets(nums,sum,sets,0);
    }

    // 9)k-partition(GFG, count number of ways to partition a set into k subsets)

    public int kPartition(int n, int k, int num, ArrayList<ArrayList<Integer>> set){
        //base case
        if(num>n){
            for(ArrayList<Integer> s:set){
                if(s.isEmpty()) return 0;
            }

            for(ArrayList<Integer> s:set){
                System.out.print(s+" ");
            }
            System.out.println();
            return 1;
        }


        int count=0;
        for(int i=0;i<k;i++){
            set.get(i).add(num);
            count+=kPartition(n,k,num+1,set);
            set.get(i).remove(set.get(i).size()-1);

            if(set.get(i).size()==0) {
                break;
            }
        }

        return count;
    }

    //ALT thinking mentioned in notes-> (top to buttom approach)- try to code that
    public int kPartitionAlt(int totalPlayer, int totalTeam,ArrayList<ArrayList<Integer>> set){
        //base case

        
        int count=0;

        ArrayList<Integer> myTeam=new ArrayList<>();
        myTeam.add(totalPlayer);
        count+=kPartitionAlt(totalPlayer-1,totalTeam-1,set);



        return count;


    }

    public void kPartition(){
        int n=3;
        int k=2;
        ArrayList<ArrayList<Integer>> set=new ArrayList<>();
        for(int i=0;i<k;i++) set.add(new ArrayList<>());
        // System.out.println(kPartition(n,k,1,set));
       
    }


    // 10)CryptArithmetic (leetcode 1307) ->(send+more=money -> assign unique digit for each chracter , find all possible solution)

    int[] map=new int[26];
    boolean[] isUsed=new boolean[10];
    public int strToInteger(String str){
        int num=0;
        for(int i=0;i<str.length();i++){
            num=(num*10)+map[str.charAt(i)-'a'];
        }
        return num;
    }

    public int CryptArithmetic(String uniqueStr,int idx,String str1,String str2,String str3){
        //base case
        if(idx==uniqueStr.length()){
            int num1=strToInteger(str1);
            int num2=strToInteger(str2);
            int num3=strToInteger(str3);

            if(num1+num2==num3){
                System.out.println(num1 + "\n"+ "+"+num2+"\n"+".............\n"+num3);
                System.out.println();
                return 1;
            }
            return 0;
        }


        int count=0;
        char ch=uniqueStr.charAt(idx);
        for(int num=0;num<=9;num++){
            if((str1.charAt(0)==ch || str2.charAt(0)==ch || str3.charAt(0)==ch) && num==0) continue;
            if(!isUsed[num]){
                isUsed[num]=true;
                map[ch-'a']=num;

                count+=CryptArithmetic(uniqueStr,idx+1,str1,str2,str3);

                map[ch-'a']=-1;
                isUsed[num]=false;
            }
        }


        return count;
    }

    public void CryptArithmetic(String str1, String str2, String str3){
        int[] uniqueChar=new int[26];
        String str=str1+str2+str3;
        for(int i=0;i<str.length();i++){
            uniqueChar[str.charAt(i)-'a']=1;
        }
        
        str="";
        for(int i=0;i<26;i++){
            if(uniqueChar[i]==1){
                str+=(char)(i+'a');
            }
        }
        Arrays.fill(map, -1);
        System.out.println(CryptArithmetic(str,0,str1,str2,str3));

    }

    public void CryptArithmetic(){
        String str1="send", str2="more", str3="money";
        CryptArithmetic(str1,str2,str3);
    }




    public static void main(String args[]) {
        // sudoku();
        l304RecursionBacktrack obj = new l304RecursionBacktrack();
        // obj.crossword();
        // obj.PartitionKSubsets();
        // obj.PartitionEquiSets();
        // obj.kPartition();

        obj.CryptArithmetic();

    }
}
