public class l303Bits{

    public static void leftShift(){
        int n=5;
        for(int i=1;i<=4;i++){
            System.out.println((n<<i));
        }
    }

    public static void rightShift(){
        int n=80;
        for(int i=1;i<=4;i++){
            System.out.println((n>>i));
        }

    }

    //find total number of bits in -1
    public static int totalBits(){
        int n=-1;
        int noOfBits=0;
        while(n!=0){
            n>>>=1;
            noOfBits++;
        }
        return noOfBits;
    }

    public static void offOn(int n, int k){
        int bitmask=(1<<k);
        n=(n | bitmask);
    }

     public static void onOff(int n, int k){
        int mask=(~(n<<k));
        n &=mask;
    }

    public static void andOper(){
        int n=11;
        while(n!=0){
            int m=(1 & n);
            n >>>=1;
            System.out.println(m +"..." + n);
        }
    }

    public static void andOper2(){
        int n=11;  //1011
        int m=1;   //0001
        
        n=(n & m);
        System.out.println(n);
    }

    public static void modulus(){
        int n=-8;
        while(n!=0){
            System.out.println(n%2);
            n=n/2;
        }
    }




    public static void main(String args[]){
        // leftShift();
        // rightShift();
        // System.out.println(totalBits());
        // andOper();
        // andOper2();
        modulus();
    }


}