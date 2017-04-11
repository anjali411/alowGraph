import java.util.*;
import java.lang.*;
import java.io.*;

public class Node
{
	int visited = 0;
	List<Integer> edges;
	int deg;
	int topovisited = 0;
	
	public Node()
	{
		this.edges = new ArrayList<>();
		this.deg = 0;	
	}

    public Node( List<Integer> list)
	{
		this.edges = list;
		this.deg = 0;
		// /this.c = 0;
	}
	public int isVisited()
	{
		return visited;
	}
	public int isTopoVisited()
	{
		return topovisited;
	}
	public List<Integer> getList()
	{
		return edges;
	}
	public void setVisited(int a)
	{
		visited = a;
	}
	public void setTopoVisited(int a)
	{
		topovisited = a;
	}
	public void incrementdeg()
	{
		deg++;
	}
	public void incrementlist(int x)
	{
		edges.add(x);
	}
	public void decrementdeg()
	{
		deg--;
	}
	public int getDeg()
	{
		return deg;
	}
	public int containsElement(int x)
	{
		if(edges.size()!=0)
		{
			for(int i=0; i<edges.size(); i++)
			{
				if(edges.get(i) == x)
					return 1;
			}
		}
		return 0;
	}
}