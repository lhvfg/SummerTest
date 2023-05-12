#include<iostream>
#include<cstring>
#include <algorithm>
#include<climits>
#include<set>
#include<cmath>
using namespace std;

int x[10],xb;
char str[30];
const int N = 6,n=10;
int dist[n];//dist[i]��ʾ���i�����ľ���
int g[n][n];//g[i][j]��ʾ���i�����j�ıߵĳ���
bool st[n];//st[i]��ʾ�ý���Ƿ�ȷ������С���룬1��ȷ����0��δȷ��
int way[n];
set<int> S,ans;
set<int>::iterator it;

int fy(int i,int j)
{
	int sum=0;
	for(int t=1;t<=j-i;t++)
	{
		sum+=x[5+t];
	}
	return sum+x[i];
}

void dijkstra()
{
    for(int i=0;i<n;i++)
    {
    	dist[i]=INT_MAX; 
	}
    dist[1] = 0;
    st[1]=true;
    S.insert(1);
    while(!S.count(6))//n���㣬ѭ��n��
    {
    	int Min=INT_MAX;
    	int t=-1,p;
        for(int i=2;i<=N;i++)
        {
        	if(st[i])
        	continue;
        //	cout<<"̽��"<<i<<endl;
        	for(it=S.begin();it!=S.end();it++)
        	{
             	if(dist[i]>g[*it][i]+dist[*it])
             	{
             		dist[i]=g[*it][i]+dist[*it];
			       way[i]=*it;
				 }
        //     	cout<<*it<<" ->"<<i<<":"<<dist[i]<<endl;
             	if(dist[i]<Min)
             	{
             		Min=dist[i];
             		t=i;
             		p=*it;
        //     		cout<<"Min�ı�,Min="<<Min<<",S�������" <<p<<endl;
				 }
			}
		}
			st[t]=true;
			S.insert(t);
		//	cout<<p<<"->"<<t<<"��t���ٵķ�����"<<Min<<endl;
     //   cout<<endl;
	}
}

int main()
{
	for(int i=1;i<=10;i++)
	{
		cin>>str;
		if(i==1)
		{
			int pos=1;
		while(str[pos]>='0'&&str[pos]<='9')
		{
		x[i]=x[i]*10+str[pos]-'0';
		pos++;		
		}
		}
		else 
		{
			int pos=0;
		while(str[pos]>='0'&&str[pos]<='9')
		{
		x[i]=x[i]*10+str[pos]-'0';
		pos++;		
		}
		}
//		cout<<x[i]<<endl;
	}
	for(int i=1;i<=N;i++)
	{
		for(int j=i+1;j<=N;j++)
		{
			g[i][j]=fy(i,j);
//			cout<<i<<"->"<<j<<"����:"<<g[i][j]<<endl;
		}
	}
	dijkstra();
	int now=6,past;
	xb=0;
	while(1)
	{
		past=way[now];
		if(past==0)
		break;
		ans.insert(past);
		now=past;
	}
//	for(int i=xb-1;i>-1;i--)
//	cout<<ans[i]<<" ";
    it = ans.begin();
//    cout<<ans.size()<<endl;
	if(ans.size()>=2)
	{
	cout<<"("<<*it<<",";
	for(int i=0;i<ans.size()-2;i++)
	{
		it++;
		cout<<" "<<*it<<",";
	}
	it++;
	cout<<" "<<*it<<"),";	
	}
	else
	cout<<"("<<*it<<"),";
	cout<<dist[6];
	return 0; 
 } 
