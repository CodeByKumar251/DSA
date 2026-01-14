#include<iostream>
#include<vector>
#include<string>
using namespace std;


int findLastIndex(vector<int>& arr, int ind,int ele){
    //base case
    if(ind==arr.size()) return -1;

    //faith will return index 
    int lastIndex=findLastIndex(arr,ind+1,ele);

    //if return index then not compare then
    if(lastIndex!=-1) return lastIndex;

    if(arr[ind]==ele) return ind;
    return -1;

}

vector<string> subSequance(string str){
    //base
    if(str.size()==0){
        vector<string> ans;
        ans.push_back("");
        return ans;
    }

    //faith
    vector<string> SmallAns=subSequance(str.substr(1));

    //relation between expectation and faith
    vector<string> ans=SmallAns;
    for(auto subs:SmallAns){
        ans.push_back(str[0]+subs);
    }
    return ans;

}

vector<string> gkc(vector<string>& keys, string num){
    //base case
    if(num.size()==0){
        vector<string> base;
        base.push_back("");
        return base;
    }

    //faith
    vector<string> smallAns=gkc(keys,num.substr(1));

    //get expectation from faith
    vector<string> ans;
    string keyString=keys[num[0]-'0'];
    for(auto word:smallAns){ 
        for(auto ch:keyString){
            ans.push_back(ch+word);
        }
    }

    return ans;

}

vector<string> getStairPaths(int n){
    //base
    if(n==0){
        vector<string> base;
        base.push_back("");
        return base;
    }else if(n<0){
        vector<string> base;
        return base;
    }

    //faith
    vector<string> paths1=getStairPaths(n-1);
    vector<string> paths2=getStairPaths(n-2);
    vector<string> paths3=getStairPaths(n-3);

    vector<string> paths;

    //expectation from faith
    for(auto path:paths1){
        paths.push_back(to_string(1)+path);
    }
    for(auto path:paths2){
        paths.push_back(to_string(2)+path);
    }
    for(auto path:paths3){
        paths.push_back(to_string(3)+path);
    }

    return paths;

}

void getStairPaths2(int n, string path, vector<string> & paths){

    //base
    cout<<path<<endl;
    if(n==0){
        paths.push_back(path);
        return;
    }else if(n<0){
        return;
    }

    //faith
    getStairPaths2(n-1, to_string(1)+path, paths);
    getStairPaths2(n-2, to_string(2)+path, paths);
    getStairPaths2(n-3, to_string(3)+path, paths);

    return ;

}

void getStairPaths3(int n, string path, vector<string> & paths){

    //base
    if(n==0){
        paths.push_back(path);
        return;
    }else if(n<0){
        return;
    }

    //faith
    string path1=to_string(1)+path;
    getStairPaths3(n-1, path1, paths);

    string path2=to_string(2)+path;
    getStairPaths3(n-2, path2, paths);

    string path3=to_string(3)+path;
    getStairPaths3(n-3, path3, paths);

    return ;

}

vector<string> getMazePaths(int sr,int sc, int dr, int dc){
    //base
    if(sr==dr && sc==dc){
        vector<string> base;
        base.push_back("");
        return base;
    }else if(sr>dr || sc>dc){
        vector<string> base;
        return base;
    }


    //faith
    vector<string> paths1=getMazePaths(sr+1,sc,dr,dc);
    vector<string> paths2=getMazePaths(sr,sc+1,dr,dc);

    //expecation from faith
    vector<string> paths;
    for(auto path:paths1){
        paths.push_back("h"+path);
    }
    for(auto path:paths2){
        paths.push_back("v"+path);
    }

    return paths;
}

void getMazePaths2(int sr,int sc, int dr, int dc, string path, vector<string> & paths){
    //base
    if(sr==dr && sc==dc){
        paths.push_back(path);
        return ;
    }else if(sr>dr || sc>dc){
        return ;
    }


     
    string path1="h"+path; //expectaion 
    getMazePaths2(sr+1,sc,dr,dc,path1, paths);   //faith

    string path2="v"+path;  //expectation
    getMazePaths2(sr,sc+1,dr,dc,path2,paths);

    return ;
}

void getMazePaths3(int sr,int sc, int dr, int dc, string path, vector<string> & paths){
    //base
    if(sr==dr && sc==dc){
        paths.push_back(path);
        return ;
    }else if(sr>dr || sc>dc){
        return ;
    }


    if(sr<dr){
        string path1="h"+path; //expectaion 
        getMazePaths3(sr+1,sc,dr,dc,path1, paths);   //faith
    }

    if(sc<dc){
        string path2="v"+path;  //expectation
        getMazePaths3(sr,sc+1,dr,dc,path2,paths);
    }

    return ;
}

    vector<string> getMazePathsWithJump(int sr, int sc, int dr, int dc){
        //base
        if(sr==dr && sc==dc){
            vector<string> base;
            base.push_back("");
            return base;
        }

        
        vector<string> paths;

        //horizontal move
        for(int ms=1;ms<=dc-sc;ms++){
            //faith
            vector<string> pathsH=getMazePathsWithJump(sr,sc+ms,dr,dc);
            for(auto path:pathsH){
                paths.push_back("h"+to_string(ms)+path);
            }
        }

        //vertical move
        for(int ms=1;ms<=dr-sr;ms++){
            //faith
            vector<string> pathsV=getMazePathsWithJump(sr+ms,sc,dr,dc);
            for(auto path:pathsV){
                paths.push_back("v"+to_string(ms)+path);
            }
        }

        //digonal move
        for(int ms=1; ms<=dc-sc && ms<=dr-sr;ms++){
            //faith
            vector<string> pathsD=getMazePathsWithJump(sr+ms,sc+ms,dr,dc);
            for(string path:pathsD){
                paths.push_back("d"+to_string(ms)+path);
            }
        }

        return paths;
    }




int main(){ 

    //findLastIndex
    vector<int> arr={1,3,8,4,6,8,7,9};
    // int ans=findLastIndex(arr,0,8);
    // cout<<"last index: "<<ans;

    //subSequance
    string str="abc";
    vector<string> ans=subSequance(str);
    for(auto sub:ans){
        cout<<sub<<" ";
    }

    //gkc
    // vector<string> keys={"?!", "abc","def","ghi","jkl","mnop","qrst","uv","wxyz",".?"};
    // vector<string> ans= gkc(keys,"573");

    // for(auto word:ans){
    //     cout<<word<<" ";
    // }
    // cout<<"size of ans: "<<ans.size();

    //getStairPaths
    // vector<string> ans=getStairPaths(4);
    // for(auto path:ans){
    //     cout<<path<<" ";
    // }

    //getStairPaths2 
    // vector<string> ans2;
    // getStairPaths2(3,"",ans2);
    //  for(auto path:ans2){
    //     cout<<path<<" ";
    // }

    // vector<string> ans3;
    // getStairPaths3(5,"",ans3);
    //  for(auto path:ans3){
    //     cout<<path<<" ";
    // }

    //getMazePaths
    // vector<string> paths=getMazePaths(0,0,2,2);
    // for(auto path:paths){
    //     cout<<path<<" ";
    // }

    // cout<<endl;
    // vector<string> paths2;
    // getMazePaths2(0,0,2,2,"",paths2);
    //   for(auto path:paths2){
    //     cout<<path<<" ";
    // }

    // cout<<endl;
    // vector<string> paths3;
    // getMazePaths3(0,0,2,2,"",paths3);
    // for(auto path:paths3){
    //     cout<<path<<" ";
    // }

    //getMazePathsWithJump
    // vector<string> paths=getMazePathsWithJump(0,0,2,2);
    //  for(auto path:paths){
    //     cout<<path<<" ";
    // }


    return 0;
}