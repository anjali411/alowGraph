import java.util.*;
import java.lang.*;
import java.io.*;

class alow
{
	public HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
	public HashMap<Integer, Node> reverseGraph = new HashMap<Integer, Node>();
	public HashMap<Integer, Node> gSCC = new HashMap<Integer, Node>();
	List<List<Integer>> strongComp = new ArrayList<List<Integer>>();
	
	public void updatedeg()
	{
		Set set = gSCC.keySet();
		Iterator entries = set.iterator();
		while(entries.hasNext())
		{
			int x = (int)entries.next();
			List<Integer> list = gSCC.get(x).getList();
			if(list.size()!=0)
			{
				for(int i=0; i<list.size(); i++)
					gSCC.get(list.get(i)).incrementdeg();
			}
		}

	}
	public void storegraph(String filename)
	{
		int i;
		Fileread obj = new Fileread();
		List<String> records = obj.readFile(filename);
		int size = records.size();
		for(i=1; i<size; i++)
		{
			String s = records.get(i);
			List<Integer> edge = new ArrayList<Integer>();
			if(s.equals("-"));
			else
			{
				String[] arr = s.split(",");
				for(int c=0;c<arr.length;c++)
					edge.add(Integer.parseInt(arr[c]));

			}
			Node node = new Node(edge);
			graph.put(i,node);	
		}
	}

	/*prints original graph*/
	public void printgraph()
	{
		Set set = graph.keySet();
		Iterator entries = set.iterator();
		while(entries.hasNext())
		{
			int x = (int)entries.next();
			List<Integer> list = graph.get(x).getList();
			if(list.size() != 0)
				{
				for(int i=0; i<list.size(); i++)
					System.out.print(list.get(i) + " ");
				}
			else
				System.out.println("-");
			
			System.out.println();
		}
	}
	/*prints reversed graph*/
	public void printReverseGraph()
	{
		Set set = reverseGraph.keySet();
		Iterator entries = set.iterator();
		while(entries.hasNext())
		{
			int x = (int)entries.next();
			System.out.print(x + " Neighbors --> ");
			List<Integer> list = reverseGraph.get(x).getList();
			if(list.size()!=0)
				{
					for(int i=0; i<list.size(); i++)
						System.out.print(list.get(i) + " ");
				}
			else
				System.out.print("-");	
			
			System.out.println();
		}
	}

	/*reverses original graph*/
	public void reversegraph()
	{
		for(int i=0; i<graph.size(); i++)
			reverseGraph.put(i+1,new Node());
		Set set = graph.keySet();
		Iterator entries = set.iterator();
		while(entries.hasNext())
		{
			int x = (int) entries.next();
			List<Integer> list = graph.get(x).getList();
			if(list.size()!=0)
			{	
				for(int i=0; i<list.size(); i++)
					reverseGraph.get(list.get(i)).incrementlist(x);
			}
		}
		//printReverseGraph();
	}		

	/*dfs over the entire graph*/
	public int[] dfs()
	{
		Stack<Integer> stack = new Stack<>();
		Stack<Integer> dummy = new Stack<>();
		Set set = graph.keySet();
		int size = set.size();
		int c = 0;
		int pathAhead = 0;
		int[] track = new int[size];

		Iterator itr = set.iterator();
		stack.push((int)itr.next());
		int current = -1;
		List<Integer> list;
		while(! stack.isEmpty())
		{
			current = stack.peek();
			if(graph.get(current).isVisited() == 1)
			{
				if(!dummy.contains(current))
					{
						dummy.push(current);
					}
				stack.pop();
			}
			else
			{
			graph.get(current).setVisited(1);
			list = graph.get(current).getList();
			if(list.size() != 0)
			{
				for(int z = 0; z<list.size(); z++)
					{
						int y = list.get(z);
						if(graph.get(y).isVisited() == 0)
						{
							stack.push(y);
							pathAhead = 1;
						}
							
					}
				if(pathAhead == 0)
					{
						stack.pop();
						dummy.push(current);
					}
				pathAhead = 0;
			}
			else
			{
				stack.pop();
				dummy.push(current);
			}

			}
			if(stack.isEmpty())
			{	
				while(itr.hasNext())
				{	
					int y = (int) itr.next();
					if(graph.get(y).isVisited() == 0)
					{
						stack.push(y);
						break;
					}
				}
			}

		}
		while(!dummy.isEmpty())
		{
			track[c++]=dummy.pop();
		}
		return track;
	}

	/*topo sort on the condensed graph of strongly connected components;
	returning 1 if no two nodes have in degree 0 simutaneously; else returning 0*/
	public int topological_sort()
	{
		Queue queue = new LinkedList<>();
		Set set = gSCC.keySet();
		Iterator entries = set.iterator();

		while(entries.hasNext())
		{
			int x =(int) entries.next();
			if(gSCC.get(x).getDeg() == 0)
				{
					queue.add(x);
				}
		}
		if(queue.size()>1)
			return 0;
		while(!queue.isEmpty())
		{
			int current = (int)queue.remove();
			gSCC.get(current).setTopoVisited(1);
			List<Integer> list = gSCC.get(current).getList();
			if(list.size()!=0)
			{
				for(int h = 0; h<list.size(); h++)
				{
					int g = list.get(h);
					gSCC.get(g).decrementdeg();
					if(gSCC.get(g).getDeg() == 0)
						queue.add(g);
				}
			}
			else
			{
				while(entries.hasNext())
				{
					int y = (int) entries.next();		
					if(graph.get(y).isTopoVisited() == 0)
						return 0;
				}
			}

			if(queue.size()>1)
				return 0;

		}
		return 1;
	}

	/*prints strongly connected components of the graph*/
	public void printstrcomp(List<List<Integer>> list) 
	{																			
		for(int i=0; i<list.size(); i++)
		{
			for(int j=0; j<list.get(i).size(); j++)
			{
				System.out.print(list.get(i).get(j) + ", ");
			}
			System.out.println();
		}
	}
	
	/*Finding strongly connected components*/
	public void strComp(int x) 
	{
		Stack<Integer> stack = new Stack<>();
		stack.push(x);
		int current = -1;
		List<Integer> l1 = new ArrayList<>();
		List<Integer> list;
		while(! stack.isEmpty())
		{
			current = stack.pop();
			l1.add(current);
			reverseGraph.get(current).setVisited(1);
		    
			list = reverseGraph.get(current).getList();
			if(list.size() != 0)
			{
				for(int z = 0; z<list.size(); z++)
					{
						int y = list.get(z);
						if(reverseGraph.get(y).isVisited() == 0 && !stack.contains(y))
							stack.push(y);							
					}
			}
		}
		strongComp.add(l1);
	}

	/*Forms a condensed graph of strongly connected components*/
	public void gscc()
	{
		HashMap<Integer,Integer> med = new HashMap<Integer, Integer>();
		for(int i=0; i<strongComp.size(); i++)
		{
			for(int j=0; j<strongComp.get(i).size(); j++)
				med.put(strongComp.get(i).get(j), i+1);
		}

		for(int i=0; i<strongComp.size(); i++)
		{
			List<Integer> list = new ArrayList<>();
			List<Integer> dummy = strongComp.get(i);
			for(int j=0; j<dummy.size(); j++)
			{	
				List<Integer> dummy1 = graph.get(dummy.get(j)).getList();
				if(dummy1.size()!=0)
				{
					for(int k=0; k<dummy1.size(); k++)
					{
						if(!dummy.contains(dummy1.get(k)) && !list.contains(med.get(dummy1.get(k))))
							list.add( med.get(dummy1.get(k)) );
					}
				}
			}
			gSCC.put(i+1,new Node(list));
		}
		updatedeg();
	}
	public void printgSCC()
	{
		Set set = gSCC.keySet();
		Iterator entries = set.iterator();
		while(entries.hasNext())
		{
			int x = (int)entries.next();
			System.out.print(x+ " Neighbors --> ");
			List<Integer> list = gSCC.get(x).getList();
			if(list.size()!=0)
				{
					for(int i=0; i<list.size(); i++)
						System.out.print(list.get(i) + " ");
				}
			else
				System.out.println("-");

			System.out.println();
		}
	}
	public void oneWayStrConn()
	{
		if(graph.size() == 0)
		{
			System.out.println("Empty graph");
			return;
		}

		int[] track = dfs();
		reversegraph();
		
		for(int i=0 ; i<track.length; i++)
			{
				if(reverseGraph.get(track[i]).isVisited() == 0)
					strComp(track[i]);
			}
		gscc();
		
		System.out.println(topological_sort());
	}

	public static void main(String[] args) 
	{
		alow obj = new alow();	
		obj.storegraph("input.txt");
		obj.oneWayStrConn();
	}
}

