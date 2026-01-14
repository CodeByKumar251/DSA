#include <iostream>
#include <vector>
#include <string>
#include<set>

using namespace std;

// 2.1.1 -> top to buttom(return type)
vector<string> getSubSequance(string str)
{
    // base
    if (str.size() == 0)
    {
        vector<string> base;
        base.push_back("");
        return base;
    }

    // faith
    vector<string> subs = getSubSequance(str.substr(1));

    // expectation from faith
    vector<string> subsSequance;
    for (string sub : subs)
    {
        // without append chracter
        subsSequance.push_back(sub);
        // with append first chracter before
        subsSequance.push_back(str[0] + sub);
    }

    return subsSequance;
}

// 2.1.2-> buttom to top approach
int getSubSequance2(string str, int ind, string subseq, vector<string> &ans)
{
    // base
    if (ind == str.size())
    {
        ans.push_back(subseq);
        return 1;
    }

    int count = 0;
    // pick chracter
    count += getSubSequance2(str, ind + 1, subseq + str[ind], ans);
    // not pick chracter
    count += getSubSequance2(str, ind + 1, subseq, ans);

    return count;
}

// 2.2.1-> top to buttom
vector<string> mazePath_HVD(int sr, int sc, int er, int ec)
{
    // base
    if (sr == er && sc == ec)
    {
        vector<string> base;
        base.push_back("");
        return base;
    }

    // expectation
    vector<string> paths;

    // faith
    if (sr + 1 <= er)
    {
        vector<string> pathsV = mazePath_HVD(sr + 1, sc, er, ec);
        for (string path : pathsV)
        {
            paths.push_back("v" + path);
        }
    }

    if (sc + 1 <= ec)
    {
        vector<string> pathsH = mazePath_HVD(sr, sc + 1, er, ec);
        for (string path : pathsH)
        {
            paths.push_back("h" + path);
        }
    }

    if (sr + 1 <= er && sc + 1 <= ec)
    {
        vector<string> pathsD = mazePath_HVD(sr + 1, sc + 1, er, ec);
        for (string path : pathsD)
        {
            paths.push_back("d" + path);
        }
    }

    return paths;
}

// 2.2.2 -> buttom to top
int mazePath_HVD(int sr, int sc, int er, int ec, vector<string> &paths, string path)
{
    // base
    if (sr == er && sc == ec)
    {
        paths.push_back(path);
        return 1;
    }

    int count = 0;
    if (sc + 1 <= ec)
    {
        count += mazePath_HVD(sr, sc + 1, er, ec, paths, path + "h");
    }
    if (sr + 1 <= er)
    {
        count += mazePath_HVD(sr + 1, sc, er, ec, paths, path + "v");
    }

    if (sc + 1 <= ec && sr + 1 <= er)
    {
        count += mazePath_HVD(sr + 1, sc + 1, er, ec, paths, path + "d");
    }

    return count;
}

// 2.2.3-> top  to buttom(return type)
vector<string> mazePaths_HVD_multi(int sr, int sc, int er, int ec)
{
    // base
    if (sr == er && sc == ec)
    {
        vector<string> base;
        base.push_back("");
        return base;
    }

    vector<string> paths;
    // faith:
    // horizontal move
    for (int jump = 1; sc + jump <= ec; jump++)
    {
        vector<string> pathH = mazePaths_HVD_multi(sr, sc + jump, er, ec);
        for (string path : pathH)
        {
            paths.push_back("h" + to_string(jump) + path);
        }
    }
    // vertical move
    for (int jump = 1; sr + jump <= er; jump++)
    {
        vector<string> pathV = mazePaths_HVD_multi(sr + jump, sc, er, ec);
        for (string path : pathV)
        {
            paths.push_back("v" + to_string(jump) + path);
        }
    }
    // digonal move
    for (int jump = 1; sc + jump <= ec && sr + jump <= er; jump++)
    {
        vector<string> pathD = mazePaths_HVD_multi(sr + jump, sc + jump, er, ec);
        for (string path : pathD)
        {
            paths.push_back("d" + to_string(jump) + path);
        }
    }

    return paths;
}

// 2.2.4-> buttom to top(void type)
int mazePaths_HVD_multi(int sr, int sc, int er, int ec, vector<string> &paths, string path)
{
    // base
    if (sr == er && sc == ec)
    {
        paths.push_back(path);
        return 1;
    }

    int count = 0;
    for (int jump = 1; sc + jump <= ec; jump++)
    {
        count += mazePaths_HVD_multi(sr, sc + jump, er, ec, paths, path + "h" + to_string(jump));
    }
    for (int jump = 1; sr + jump <= er; jump++)
    {
        count += mazePaths_HVD_multi(sr + jump, sc, er, ec, paths, path + "v" + to_string(jump));
    }

    for (int jump = 1; sr + jump <= er && sc + jump <= ec; jump++)
    {
        count += mazePaths_HVD_multi(sr + jump, sc + jump, er, ec, paths, path + "d" + to_string(jump));
    }

    return count;
}

// 2.2.5 -> buttom to top
// Vector theory
int mazePath_HVD(int sr, int sc, int er, int ec, vector<vector<int>> &dir, vector<string> &dirS, vector<string> &paths, string path)
{
    // base
    if (sr == er && sc == ec)
    {
        paths.push_back(path);
        return 1;
    }

    int count = 0;
    for (int d = 0; d < dir.size(); d++)
    {
        int x = sr + dir[d][0];
        int y = sc + dir[d][1];
        if (x >= 0 && y >= 0 && x <= er && y <= ec)
            count += mazePath_HVD(x, y, er, ec, dir, dirS, paths, path + dirS[d]);
    }

    return count;
}

// 2.2.6 flood fill algo
int floodFill(int sr, int sc, vector<vector<bool>> &vis, vector<vector<int>> &dir, vector<string> &dirS, vector<string> &paths, string path)
{

    int n = vis.size();
    int m = vis[0].size();

    // base
    if (sr == n - 1 && sc == m - 1)
    {
        paths.push_back(path);
        return 1;
    }

    vis[sr][sc] = true;
    int count = 0;
    for (int d = 0; d < dir.size(); d++)
    {
        int r = sr + dir[d][0];
        int c = sc + dir[d][1];
        if (r >= 0 && c >= 0 && r < n && c < m && !vis[r][c])
            count += floodFill(r, c, vis, dir, dirS, paths, path + dirS[d]);
    }
    vis[sr][sc] = false;

    return count;
}

// 2.2.7 flood fill algo with jump could be greater then or equal to 1
int floodFill_multi(int sr, int sc, vector<vector<bool>> &vis, vector<vector<int>> &dir, vector<string> &dirS, vector<string> &paths, string path)
{

    int n = vis.size();
    int m = vis[0].size();

    // base
    if (sr == n - 1 && sc == m - 1)
    {
        paths.push_back(path);
        return 1;
    }

    vis[sr][sc] = true;
    int count = 0;
    for (int rad = 1; rad <= max(n, m); rad++)
    {
        for (int d = 0; d < dir.size(); d++)
        {
            int r = sr + rad * dir[d][0];
            int c = sc + rad * dir[d][1];
            if (r >= 0 && c >= 0 && r < n && c < m && !vis[r][c])
                count += floodFill_multi(r, c, vis, dir, dirS, paths, path + dirS[d] + to_string(rad));
        }
    }
    vis[sr][sc] = false;

    return count;
}
// 2.2.8 flood fill algo with jump could be greater then or equal to 1, remove extra call
int floodFill_multi_best(int sr, int sc, vector<vector<bool>> &vis, vector<vector<int>> &dir, vector<string> &dirS,
                         vector<string> &paths, string path)
{
    int n = vis.size();
    int m = vis[0].size();

    // base
    if (sr == n - 1 && sc == m - 1)
    {
        paths.push_back(path);
        return 1;
    }

    vis[sr][sc] = true;
    int count = 0;

    for (int d = 0; d < dir.size(); d++)
    {
        for (int rad = 1; rad <= max(n, m); rad++)
        {
            int r = sr + rad * dir[d][0];
            int c = sc + rad * dir[d][1];

            if (r >= 0 && c >= 0 && r < n && c < m)
            {
                if (!vis[r][c])
                    count += floodFill_multi(r, c, vis, dir, dirS, paths, path + dirS[d] + to_string(rad));
            }
            else
            {
                break;
            }
        }
    }
    vis[sr][sc] = false;

    return count;
}

// 2.3.4 longestPath return type(top to buttom) using flood fill algo
pair<string, int> longestPathFloodFill(int sr, int sc, vector<vector<int>> &vis,
                                       vector<vector<int>> dir, vector<string> dirS)
{
    int n = vis.size();
    int m = vis[0].size();
    // base
    if (sr == n - 1 && sc == m - 1)
    {
        pair<string, int> base;
        base.first = "";
        base.second = 0;
        return base;
    }

    vis[sr][sc] = true;
    pair<string, int> myAns;
    myAns.first = "";
    myAns.second = -1;
    for (int d = 0; d < dir.size(); d++)
    {
        int r = sr + dir[d][0];
        int c = sc + dir[d][1];
        if (r >= 0 && c >= 0 && r < n && c < m)
        {
            if (!vis[r][c])
            {
                pair<string, int> recAns = longestPathFloodFill(r, c, vis, dir, dirS);
                if (recAns.second != -1 && recAns.second + 1 > myAns.second)
                {
                    recAns.first = dirS[d] + recAns.first;
                    recAns.second = 1 + recAns.second;
                    myAns = recAns;
                }
            }
        }
    }
    vis[sr][sc] = false;
    return myAns;
}

// 2.3.4 longestPath
void longestPath()
{
    vector<vector<int>> dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    vector<string> dirS = {"D", "U", "R", "L"};
    vector<vector<int>> vis(3, vector<int>(3, 0));
    // vis[1][1] = vis[1][2] = vis[2][0] = 1;
    vis[1][1] = vis[1][2] = 1;
    pair<string,int> ans=longestPathFloodFill(0,0,vis,dir,dirS);
    cout<<"path is : "<<ans.first<<" ,@ length: "<<ans.second;
}



// 2.3.5 shotestPath return type(top to buttom) using flood fill algo
pair<string, int> shortestPathFloodFill(int sr, int sc, vector<vector<int>> &vis,
                                       vector<vector<int>> dir, vector<string> dirS)
{
    int n = vis.size();
    int m = vis[0].size();
    // base
    if (sr == n - 1 && sc == m - 1)
    {
        pair<string, int> base;
        base.first = "";
        base.second = 0;
        return base;
    }

    vis[sr][sc] = true;
    pair<string, int> myAns;
    myAns.first = "";
    myAns.second = 1e9;
    for (int d = 0; d < dir.size(); d++)
    {
        int r = sr + dir[d][0];
        int c = sc + dir[d][1];
        if (r >= 0 && c >= 0 && r < n && c < m)
        {
            if (!vis[r][c])
            {
                pair<string, int> recAns = shortestPathFloodFill(r, c, vis, dir, dirS);
                if (recAns.second != 1e9 && recAns.second + 1 < myAns.second)
                {
                    recAns.first = dirS[d] + recAns.first;
                    recAns.second = 1 + recAns.second;
                    myAns = recAns;
                }
            }
        }
    }
    vis[sr][sc] = false;
    return myAns;
}

// 2.3.5 shortestPath
void shortestPath()
{
    vector<vector<int>> dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    vector<string> dirS = {"D", "U", "R", "L"};
    vector<vector<int>> vis(3, vector<int>(3, 0));
    // vis[1][1] = vis[1][2] = vis[2][0] = 1;
    vis[1][1] = vis[1][2] = 1;
    pair<string,int> ans=shortestPathFloodFill(0,0,vis,dir,dirS);
    cout<<"path is : "<<ans.first<<" ,@ length: "<<ans.second;
}



// 2.3.6 longestPathMulti return type(top to buttom) using flood fill algo
pair<string, int> longestPathMultiFloodFill(int sr, int sc, vector<vector<int>> &vis,
                                       vector<vector<int>> dir, vector<string> dirS)
{
    int n = vis.size();
    int m = vis[0].size();
    // base
    if (sr == n - 1 && sc == m - 1)
    {
        pair<string, int> base;
        base.first = "";
        base.second = 0;
        return base;
    }

    vis[sr][sc] = true;
    pair<string, int> myAns;
    myAns.first = "";
    myAns.second = -1;
    for (int d = 0; d < dir.size(); d++)
    {
        for(int rad=1;rad<max(n,m);rad++){

            int r = sr + rad*dir[d][0];
            int c = sc + rad*dir[d][1];
            if (r >= 0 && c >= 0 && r < n && c < m)
            {
                if (!vis[r][c])
                {
                    pair<string, int> recAns = longestPathMultiFloodFill(r, c, vis, dir, dirS);
                    if (recAns.second != -1 && recAns.second + 1 > myAns.second)
                    {
                        recAns.first = dirS[d]+to_string(rad) + recAns.first;
                        recAns.second = 1 + recAns.second;
                        myAns = recAns;
                    }
                }
            }else break;
        }
    }
    vis[sr][sc] = false;
    return myAns;
}

// 2.3.6 longestPathMulti
void longestPathMulti()
{
    vector<vector<int>> dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    vector<string> dirS = {"D", "U", "R", "L"};
    vector<vector<int>> vis(3, vector<int>(3, 0));
    // vis[1][1] = vis[1][2] = vis[2][0] = 1;
    vis[1][1] = vis[1][2] = 1;
    pair<string,int> ans=longestPathMultiFloodFill(0,0,vis,dir,dirS);
    cout<<"path is : "<<ans.first<<" ,@ length: "<<ans.second;
}

// 2.3.7 shortestPathMulti return type(top to buttom) using flood fill algo
pair<string, int> shortestPathMultiFloodFill(int sr, int sc, vector<vector<int>> &vis,
                                       vector<vector<int>> dir, vector<string> dirS)
{
    int n = vis.size();
    int m = vis[0].size();
    // base
    if (sr == n - 1 && sc == m - 1)
    {
        pair<string, int> base;
        base.first = "";
        base.second = 0;
        return base;
    }

    vis[sr][sc] = true;
    pair<string, int> myAns;
    myAns.first = "";
    myAns.second = 1e9;
    for (int d = 0; d < dir.size(); d++)
    {
        for(int rad=1;rad<max(n,m);rad++){

            int r = sr + rad*dir[d][0];
            int c = sc + rad*dir[d][1];
            if (r >= 0 && c >= 0 && r < n && c < m)
            {
                if (!vis[r][c])
                {
                    pair<string, int> recAns = shortestPathMultiFloodFill(r, c, vis, dir, dirS);
                    if (recAns.second != 1e9 && recAns.second + 1 < myAns.second)
                    {
                        recAns.first = dirS[d]+to_string(rad) + recAns.first;
                        recAns.second = 1 + recAns.second;
                        myAns = recAns;
                    }
                }
            }else break;
        }
    }
    vis[sr][sc] = false;
    return myAns;
}

// 2.3.7 shortestPathMulti
void shortestPathMulti()
{
    vector<vector<int>> dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    vector<string> dirS = {"D", "U", "R", "L"};
    vector<vector<int>> vis(3, vector<int>(3, 0));
    // vis[1][1] = vis[1][2] = vis[2][0] = 1;
    vis[1][1] = vis[1][2] = 1;
    pair<string,int> ans=shortestPathMultiFloodFill(0,0,vis,dir,dirS);
    cout<<"path is : "<<ans.first<<" ,@ length: "<<ans.second;
}


//2.3.8 equiset
int equalSetSol(vector<int> & arr, int sum1, int sum2, string set1, string set2, int ind){
    //base case
    if(ind==arr.size()){
        if(sum1==sum2){
            cout<<set1<<"= "<<set2<<endl;
            return 1;
        }
        return 0;
    }

    int count=0;
    count+=equalSetSol(arr,sum1+arr[ind], sum2, set1 + to_string(arr[ind])+ " " , set2,ind+1);
    count+=equalSetSol(arr,sum1, sum2+arr[ind], set1, set2+ to_string(arr[ind])+ " " ,ind+1);

    return count;
}

void equalSet(){
    vector<int> arr={10,20,30,40,50,60,70,80};
    set<int> s1;
    set<int> s2;
    int count=equalSetSol(arr,arr[0],0,to_string(arr[0])+"","",1);
    cout<<count<<endl;
}

//2.3.9 all permuation of string
int permutationOfString(string str, string perm){
    //base case
    if(str.size()==0){
        cout<<perm<<endl;
        return 1;
    }

    int count=0;
    for(int i=0;i<str.size();i++){
        char ch=str[i];
        string ros=str.substr(0,i)+str.substr(i+1);
        count+=permutationOfString(ros,perm+ch);
    }
    return count;
}

//2.3.10 all unique permutation of string
int permutationStringUnique(string str, string ans){
    //base case
    if(str.size()==0){
        cout<<ans<<endl;
        return 1;
    }

    vector<bool> vis(26,false);
    int count=0;
    for(int i=0;i<str.size();i++){
        char ch=str[i];
        if(!vis[ch-'a']){
            vis[ch-'a']=true;
            string ros=str.substr(0,i)+str.substr(i+1);
            count+=permutationStringUnique(ros,ans+ch);
        }

    }
    return count;
}

//2.3.11  all unique permutation of string
int permutationStringUnique2(string str, string ans){
    //base case
    if(str.size()==0){
        cout<<ans<<endl;
        return 1;
    }

    char prev='$';
    int count=0;
    for(int i=0;i<str.size();i++){
        char ch=str[i]; 
        if(ch!=prev){
            string ros=str.substr(0,i)+str.substr(i+1);
            count+=permutationStringUnique(ros,ans+ch);
        }
        prev=ch;

    }
    return count;
}

int main()
{

    int count=permutationStringUnique2("aabc","");
    cout<<count<<endl;

    // int count=permutationOfString("abc","");
    // cout<<count<<endl;
    // equalSet();

    // longestPath();
    // shortestPath();
    // longestPathMulti();
    // shortestPathMulti();


    // 2.1.1-> top to buttom(return type)
    // string str="abc";
    // vector<string> ans=getSubSequance(str);
    // for(string sub:ans){
    //     cout<<sub<<" ";
    // }

    // 2.1.2-> buttom to top approach
    //  string str2="abc";
    //  vector<string> ans2;
    //  int count=getSubSequance2(str2,0,"",ans2);
    //  cout<<"count="<<count<<endl;
    //  for(string sub:ans2){
    //      cout<<sub<<",";
    //  }

    // 2.2.1-> top to buttom
    //  vector<string> paths=mazePath_HVD(0,0,2,2);
    //  for(string path:paths){
    //      cout<<path<<" ";
    //  }

    // 2.2.2 -> buttom to top
    //  vector<string> paths;
    //  int count=mazePath_HVD(0,0,2,2,paths,"");
    //  cout<<"total possible path ="<<count<<endl;
    //  for(string path:paths){
    //      cout<<path<<" ";
    //  }

    // 2.2.3-> top  to buttom(return type)
    //  vector<string> paths=mazePaths_HVD_multi(0,0,2,2);
    //  for(string path:paths){
    //      cout<<path<<" ";
    //  }

    // 2.2.4-> buttom to top(void type)
    //  vector<string> paths;
    //  int count=mazePaths_HVD_multi(0,0,2,2,paths,"");
    //  cout<<"total possible path: "<<count<<endl;
    //  for(string path:paths){
    //      cout<<path<<" ";
    //  }

    // 2.2.5 -> buttom to top
    // Vector theory
    //  vector<vector<int>> dir={{1,0},{0,1},{1,1}};
    //  vector<string> dirS={"V","H","D"};
    //  vector<string> paths;
    //  int count=mazePath_HVD(0,0,2,2,dir,dirS,paths,"");
    //  cout<<"count: "<<count<<endl;
    //  for(string path:paths){
    //      cout<<path<<" ";
    //  }

    // will make loop, go to flood fil algo
    //  vector<vector<int>> dir={{0,1},{1,1},{1,0},{1,-1}, {0,-1},{-1,-1},{-1,0},{-1,1}};
    //  vector<string> dirS={"R","S","D","W","L","N","U","E"};
    //  vector<string> paths;
    //  int count=mazePath_HVD(0,0,2,2,dir,dirS,paths,"");
    //  cout<<"count: "<<count<<endl;
    //  for(string path:paths){
    //      cout<<path<<" ";
    //  }

    // 2.2.6 flood fill algo
    //  vector<vector<int>> dir={{1,0},{-1,0},{0,1},{0,-1}};
    //  vector<string> dirS={"D","U","R","L"};
    //  vector<string> paths;
    //  vector<vector<bool>> vis(3, vector<bool>(3,false));
    //  int count=floodFill(0,0,vis,dir,dirS,paths,"");
    //  cout<<"count: "<<count<<endl;
    //  for(string path:paths){
    //      cout<<path<<" ";
    //  }

    // 2.2.7 flood fill algo with jump could be greater then or equal to 1
    //  vector<vector<int>> dir={{1,0},{0,1},{1,1}};
    //  vector<string> dirS={"V","H","D"};
    //  vector<string> paths;
    //  vector<vector<bool>> vis(3, vector<bool>(3,false));
    //  int count=floodFill_multi_best(0,0,vis,dir,dirS,paths,"");
    //  cout<<"count: "<<count<<endl;
    //  for(string path:paths){
    //      cout<<path<<" ";
    //  }

    // 2.3.4 longestPath return type(top to buttom) using flood fill algo

    return 0;
}