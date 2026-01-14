#include <iostream>
#include <vector>
#include <string>
#include<set>

using namespace std;


void printIncreasing(int a, int b){    //a, a+1,a+2 ......b

    //base case
    if(a>b) return;

    //faith a, rest will print recursion
    cout<<a<<" ";
    printIncreasing(a+1,b);

}

// printIncreasing work same as below
void pppppp(int a, int b){
    if(a>b) return;
}

void ppppp(int a, int b){
    cout<<a<<" ";
    pppppp(a+1,b);
}

void pppp(int a, int b){
    cout<<a<<" ";
    ppppp(a+1,b);
}

void ppp(int a, int b){
    cout<<a<<" ";
    pppp(a+1,b);
}

void pp(int a, int b){
    cout<<a<<" ";
    ppp(a+1,b);
}

void p(int a, int b){
    cout<<a<<" ";
    pp(a+1,b);
}




void printDecreasing(int a, int b){   //b, b-1.b-2 ......a
    //base
    if(a>b) return;

    //faith
    printDecreasing(a+1,b);
    cout<<a<<" ";


}

void printIncreasingDecreasing(int a, int b){   //a, a+1,.....b, b, b-1, b-2, ...... a

    //base
    if(a>b) return;

    cout<<a<<" ";
    printIncreasingDecreasing(a+1,b); //faith
    cout<<a<<" ";

}

void printOddEven(int a, int b){    //print odd from a to b, print even from b to a 

    //base
    if(a>b) return;

    if(a%2!=0) cout<<a<<" ";
    printOddEven(a+1,b);  //faith
    if(a%2==0) cout<<a<<" ";

}

//factorial

int fact(int n){

    //faith   fact(n-1)

    return n==0? 1:fact(n-1)*n;
}

//power function a^b

int power(int a, int b){

    //faith power(a, b-1)

    return b==0? 1:power(a,b-1)*a;

}

//better logic for power

int power2(int a, int b){

    //base
    if(b==0) return 1;

    //faith
    int smallAns=power2(a,b/2);

    smallAns*=smallAns;

    return b%2==0? smallAns:smallAns*a;
    
}

int printTreePath(int n){
    if(n==1 || n==0){
        cout<<"base: "<<n<<endl;
        return n;
    }

    int ans=0;
    cout<<"pre: "<<n<<endl;
    ans+=printTreePath(n-1);

    cout<<"In: "<<n<<endl;
    
    ans+=printTreePath(n-2);
    cout<<"post: "<<n<<endl;

    return ans+3;
}

int findMaxElement(vector<int> & arr, int ind){

    //base case
    if(ind==arr.size()) INT_MIN;

    //faith findMaximumElement(arr,ind+1) will give maxium element from ind+1 to whole arr
    return max(arr[ind],findMaxElement(arr,ind+1));

}

int findMinElement(vector<int> & arr, int ind){
    
    if(ind==arr.size()) INT_MAX;

    return min(arr[ind],findMinElement(arr,ind+1));
}

bool findElement(vector<int> & arr, int ele, int ind){
    //base case
    if(ind==arr.size()) return false;

    //faith if arr[ind]!=ele then return by recursion call
    return arr[ind]==ele || findElement(arr,ele,ind+1);
}

int firstIndex(vector<int> & arr, int ele, int ind){
    //base case
    if(ind==arr.size()) return -1;

    //faith
    // if(arr[ind]==ele) return ind;

    // return firstIndex(arr,ele,ind+1);

    return arr[ind]==ele? ind:firstIndex(arr,ele,ind+1);
}

int lastIndex(vector<int> &arr, int ele, int ind){
    //base
    if(ind==arr.size()) return -1; 

    //faith
    int lastIdx=lastIndex(arr,ele,ind+1);

    if(lastIdx!=-1) return lastIdx;

    if(arr[ind]==ele) return ind;
    return -1;

}

vector<int> findAllIndex(vector<int> arr, int indx, int data, int count){
    //base
    if(indx==arr.size()){
        vector<int> base(count,0);
        return base;
    }

    if(arr[indx]==data) count++;

    vector<int> ans=findAllIndex(arr,indx+1,data,count);

    if(arr[indx]==data)  ans[count-1]=indx;

    return ans;
}


int main(){
    p(5,9);

    // printIncreasing(5,9);
    // cout<<endl;
    // printDecreasing(5,9);
    // cout<<endl;
    // printIncreasingDecreasing(5,9);
    // cout<<endl;
    // printOddEven(5,9);
    // cout<<endl;
    // cout<<fact(5);
    // cout<<endl;
    // cout<<power(2,5);
    // cout<<endl;
    // cout<<power2(2,5);

    vector<int> arr={0,8,2,3,8,9,8,8,6};
    vector<int> result=findAllIndex(arr,0,8,0);
    for(int indx:result){
        cout<<indx<<" ";
    }

    return 0;
}   