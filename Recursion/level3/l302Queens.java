import java.util.ArrayList;

public class l302Queens {

    //tnb=total no of boxes, tnq=total no of queens
    public static int queenCombination(int tnb, int tnq, int bno, int qno, String ans){

        if(qno==tnq){
            System.out.println(ans);
            return 1;
        }


        int count=0;
        for(int i=bno;i<tnb;i++){
            count+=queenCombination(tnb,tnq,i+1,qno+1,ans+"b"+i+"q"+qno);
        }
        return count;
    }

    public static int queenPermutation(boolean[] boxes, int tnq, int bno, int qno, String ans){
        if(qno==tnq){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        for(int i=bno;i<boxes.length;i++){
            if(!boxes[i]){
                boxes[i]=true;
                count+=queenPermutation(boxes,tnq,0,qno+1,ans+"b"+i+"q"+qno);
                boxes[i]=false;
            }  
        }
        return count;
    }

    public static int queenCombination2D(boolean[][] board,int tnq, int bno,String ans ){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        int n=board.length; int m=board[0].length;

        for(int i=bno;i<n*m;i++){
            int r=i/m;
            int c=i%m;
            count+=queenCombination2D(board,tnq-1,i+1,ans+"("+r+","+c+")");
        }
        return count;
        
    }

    public static int queenPermutation2D(boolean[][] board,int tnq, int bno,String ans ){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        int n=board.length; int m=board[0].length;

        for(int i=bno;i<n*m;i++){
            int r=i/m;
            int c=i%m;
            if(!board[r][c]){
                board[r][c]=true;
                count+=queenPermutation2D(board,tnq-1,0,ans+"("+r+","+c+")");
                board[r][c]=false;
            }
        }
        return count;
    }
    
// ====================================all above by subSequnce method========================================

public static int queenCombination_sub(int tnb, int tnq, int bno, int qno, String ans){
    //base case
    if(bno==tnb || qno==tnq){
        if(qno==tnq){
            System.out.println(ans);
            return 1;
        }
        return 0;
    }

    int count=0;
    //pick
    count+=queenCombination_sub(tnb,tnq,bno+1,qno+1,ans+"b"+bno+"q"+qno);
    //not pick
    count+=queenCombination_sub(tnb, tnq, bno+1, qno, ans);

    return count;
}

public static int queenPermutation_sub(boolean[] boxes, int tnq, int bno, int qno, String ans){
    //base case
    if(bno==boxes.length || qno==tnq){
        if(qno==tnq){
            System.out.println(ans);
            return 1;
        }
        return 0;
    }

    int count=0;
    //pick
    if(!boxes[bno]){
        boxes[bno]=true;
        count+=queenPermutation_sub(boxes,tnq,0,qno+1,ans+"b"+bno+"q"+qno);
        boxes[bno]=false;
    }
    //not pick
    count+=queenPermutation_sub(boxes, tnq, bno+1, qno, ans);

    return count;
}


public static int queenCombination_sub2D(boolean[][] board, int tnq, int bno, String ans){
    //base case
    if(bno==board.length*board[0].length || tnq==0){
        if(tnq==00){
            System.out.println(ans);
            return 1;
        }
        return 0;
    }

    int n=board.length; int m=board[0].length;
    int count=0;
    
    int r=bno/m;
    int c=bno%m;
    //pick
    count+=queenCombination_sub2D(board,tnq-1,bno+1,ans+"("+r+","+c+")");
    //not pick
    count+=queenCombination_sub2D(board, tnq, bno+1, ans);
    
    return count;
}

public static int queenPermutation_sub2D(boolean[][] board, int tnq, int bno, String ans){
    //base case
    if(bno==board.length*board[0].length || tnq==0){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }
        return 0;
    }

    int count=0;
    int n=board.length; int m=board[0].length;
    int r=bno/m;
    int c=bno%m;
    //pick
    if(!board[r][c]){
        board[r][c]=true;
        count+=queenPermutation_sub2D(board,tnq-1,0,ans+"("+r+","+c+")");
        board[r][c]=false;
    }
    //not pick
    count+=queenPermutation_sub2D(board, tnq, bno+1, ans);

    return count;
}
//============================================================================================================


//=============================nQueens==============================================

    public static boolean isSafeToPlaceQueen(boolean[][] board, int row, int col){

        int[][] dir={{0,-1},{-1,-1},{-1,0},{-1,1}};
        int n=board.length; int m=board[0].length;
        for(int d=0;d<dir.length;d++){
            for(int rad=1;rad<Math.max(m,n);rad++){
                int r=row+rad*dir[d][0];
                int c=col+rad*dir[d][1];
                if(r>=0 && c>=0 && r<n && c<m){
                    if(board[r][c]) return false;
                }else{
                    break;
                }
            }
        }
        return true;
    }

    public static boolean isSafeToPlaceQueen2(boolean[][] board, int row, int col){

        int[][] dir={{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
        int n=board.length; int m=board[0].length;
        for(int d=0;d<dir.length;d++){
            for(int rad=1;rad<Math.max(m,n);rad++){
                int r=row+rad*dir[d][0];
                int c=col+rad*dir[d][1];
                if(r>=0 && c>=0 && r<n && c<m){
                    if(board[r][c]) return false;
                }else{
                    break;
                }
            }
        }
        return true;
    }

    public static int nqueen_01_combi(boolean[][] board,int tnq, int idx,String ans ){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        int n=board.length; int m=board[0].length;

        for(int i=idx;i<n*m;i++){
            int r=i/m;
            int c=i%m;
            if(isSafeToPlaceQueen(board, r, c)){
                board[r][c]=true;
                count+=nqueen_01_combi(board,tnq-1,i+1,ans+"("+r+","+c+")");
                board[r][c]=false;
            }
        }
        return count;
        
    }

    public static int nqueen_01_permu(boolean[][] board,int tnq, int idx,String ans ){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        int n=board.length; int m=board[0].length;

        for(int i=idx;i<n*m;i++){
            int r=i/m;
            int c=i%m;
            if(isSafeToPlaceQueen2(board, r, c) && !board[r][c]){
                board[r][c]=true;
                count+=nqueen_01_permu(board,tnq-1,0,ans+"("+r+","+c+")");
                board[r][c]=false;
            }
        }
        return count;
        
    }

    
    public static int nqueen_01_combi_sub(boolean[][] board,int tnq, int idx,String ans ){
         int n=board.length; int m=board[0].length;
        if(tnq==0 || idx==n*m){
            if(tnq==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count=0;

        int r=idx/m;
        int c=idx%m;
        if(isSafeToPlaceQueen(board, r, c)){
            board[r][c]=true;
            count+=nqueen_01_combi_sub(board,tnq-1,idx+1,ans+"("+r+","+c+")");
            board[r][c]=false;
        }

        count+=nqueen_01_combi_sub(board,tnq,idx+1,ans);

        return count;
        
    }


    public static int nqueen_01_permu_sub(boolean[][] board,int tnq, int idx,String ans ){
         int n=board.length; int m=board[0].length;
        if(tnq==0 || idx==n*m){
            if(tnq==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count=0;

        int r=idx/m;
        int c=idx%m;
        if(isSafeToPlaceQueen2(board, r, c) && !board[r][c]){
            board[r][c]=true;
            count+=nqueen_01_permu_sub(board,tnq-1,0,ans+"("+r+","+c+")");
            board[r][c]=false;
        }

        count+=nqueen_01_permu_sub(board,tnq,idx+1,ans);
        return count;

    }


//=========================optimize isSafeToPlaceQueen ===========================================
    static boolean row[];
    static boolean col[];
    static boolean diag[];
    static boolean aDiag[];

    public static int nqueen_02_combi(int n, int m, int tnq, int idx,String ans ){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        calls++;
        for(int i=idx;i<n*m;i++){
            int r=i/m;
            int c=i%m;
            if(!row[r] && !col[c] && !diag[r+c] && !aDiag[r-c+m-1]){
                row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=true;
                count+=nqueen_02_combi(n,m,tnq-1,i+1,ans+"("+r+","+c+")");
                row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=false;
            }
        }
        return count;
        
    }

    public static int nqueen_02_permu(int n, int m, int tnq, int idx,String ans ){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        calls++;
        for(int i=idx;i<n*m;i++){
            int r=i/m;
            int c=i%m;
            if(!row[r] && !col[c] && !diag[r+c] && !aDiag[r-c+m-1]){
                row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=true;
                count+=nqueen_02_permu(n,m,tnq-1,0,ans+"("+r+","+c+")");
                row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=false;
            }
        }
        return count;
        
    }

    public static int nqueen_02_combi_sub(int n, int m,int tnq, int idx,String ans ){
        if(tnq==0 || idx==n*m){
            if(tnq==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count=0;

        int r=idx/m;
        int c=idx%m;
        if(!row[r] && !col[c] && !diag[r+c] && !aDiag[r-c+m-1]){
            row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=true;
            count+=nqueen_02_combi_sub(n,m,tnq-1,idx+1,ans+"("+r+","+c+")");
            row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=false;
        }

        count+=nqueen_02_combi_sub(n,m,tnq,idx+1,ans);

        return count;
        
    }

    public static int nqueen_02_permu_sub(int n, int m,int tnq, int idx,String ans ){
        if(tnq==0 || idx==n*m){
            if(tnq==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count=0;

        int r=idx/m;
        int c=idx%m;
        if(!row[r] && !col[c] && !diag[r+c] && !aDiag[r-c+m-1]){
            row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=true;
            count+=nqueen_02_permu_sub(n,m,tnq-1,0,ans+"("+r+","+c+")");
            row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=false;
        }

        count+=nqueen_02_permu_sub(n,m,tnq,idx+1,ans);

        return count;
        
    }

//=====================optimize recursion call=============================
    static int calls=0;
    public static int nqueen_03_combi(int n, int m, int tnq, int floor,String ans ){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        calls++;
        for(int room=0;room<m;room++){
            int r=floor;
            int c=room;
            if( !col[c] && !diag[r+c] && !aDiag[r-c+m-1]){
                col[c]=diag[r+c]=aDiag[r-c+m-1]=true;
                count+=nqueen_03_combi(n,m,tnq-1,floor+1,ans+"("+r+","+c+")");
                col[c]=diag[r+c]=aDiag[r-c+m-1]=false;
            }
        }
        return count;
        
    }

    public static int nqueen_03_perm(int n, int m, int tnq, int r,String ans ){
        if(tnq==0 || r==n){
            if(tnq==0)
                System.out.println(ans);
            
            return tnq==0? 1:0;
        }

        int count=0;
        calls++;
        for(int c=0;c<m;c++){
            if(!row[r] && !col[c] && !diag[r+c] && !aDiag[r-c+m-1]){
                row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=true;
                count+=nqueen_03_perm(n,m,tnq-1,0,ans+"("+r+","+c+")");
                row[r]=col[c]=diag[r+c]=aDiag[r-c+m-1]=false;
            }
        }
        count+=nqueen_03_perm(n,m,tnq,r+1,ans);
        return count;
        
    }

//=================optimize time complexity to O(1) using bits================
    static int cols=0;
    static int diags=0;
    static int aDiags=0;
    public static int nqueen_04_combi(int n, int m, int tnq, int floor,String ans ){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }

        int count=0;
        for(int room=0;room<m;room++){
            int r=floor;
            int c=room;
            if( (cols & (1<<c))==0 &&  (diags & (1<<(r+c)))==0 &&  (aDiags & (1<<(r-c+m-1)))==0){
                cols^=(1<<c);
                diags^=(1<<(r+c));
                aDiags^=(1<<(r-c+m-1));
                count+=nqueen_04_combi(n,m,tnq-1,floor+1,ans+"("+r+","+c+")");
                cols^=(1<<c);
                diags^=(1<<(r+c));
                aDiags^=(1<<(r-c+m-1));
            }
        }
        return count;
        
    }


    public static void nQueen(){
        int n=4; int m =4;
        int q=4;
        boolean[][] board=new boolean[n][m];
        
        // System.out.println(nqueen_01_combi(board,q,0,""));
        // System.out.println(nqueen_01_permu(board,q,0,""));
        // System.out.println(nqueen_01_combi_sub(board,q,0,""));
        // System.out.println(nqueen_01_permu_sub(board,q,0,""));

        row=new boolean[n];
        col=new boolean[m];
        diag=new boolean[m+n-1];
        aDiag=new boolean[m+n-1];
        // System.out.println(nqueen_02_combi(n,m,q,0,""));
        // System.out.println(nqueen_02_permu(n,m,q,0,""));
        // System.out.println(nqueen_02_combi_sub(4,4,4,0,""));
        // System.out.println(nqueen_02_permu_sub(4,4,4,0,""));

        // System.out.println(nqueen_03_combi(n,m,q,0,""));
        // System.out.println(nqueen_03_perm(n,m,q,0,""));
        // System.out.println(calls);
        System.out.println(nqueen_04_combi(n,m,q,0,""));

    }

    public static void queenProblems(){
        int tnb=5;
        int tnq=3;
        String ans="";
        boolean[] boxes=new boolean[tnb];

        boolean[][] board=new boolean[4][4];
        
        // System.out.println(queenCombination(tnb,tnq,0,0,""));
        // System.out.println(queenPermutation(boxes,tnq,0,0,""));
        // System.out.println(queenCombination2D(board,4,0,""));
        // System.out.println(queenPermutation2D(board,4,0,""));
    }

    public static void queenProblems_sub(){
        int tnb=5;
        int tnq=3;
        String ans="";
        boolean[] boxes=new boolean[tnb];
        boolean[][] board=new boolean[4][4];

        //  System.out.println(queenCombination_sub(tnb,tnq,0,0,""));
        //  System.out.println(queenPermutation_sub(boxes,tnq,0,0,""));
        // System.out.println(queenCombination_sub2D(board,4,0,""));
        // System.out.println(queenPermutation_sub2D(board,4,0,""));

    }

 

    public static void main(String args[]){
        // queenProblems();
        // queenProblems_sub();
        nQueen();
    }
    
}
