package lab1;
import java.util.Scanner;
import java.util.NoSuchElementException;
public class test
{
	public static void main(String[] argv) throws IllegalStateException,ClassCastException,NullPointerException,IllegalArgumentException,NoSuchElementException
	{
		queue<Integer> que = new queue<Integer>();
		Scanner input = new Scanner(System.in);
		boolean exit = false;
		int choose;
		int innum;
		final int RANGE = 100;//压入队列的整数的范围
		int para;
		boolean re;
		int reint;
		while(!exit)
		{
			System.out.print(	
								"Test Class Queue:\n"+
								"-----------------------\n"+
								"0:exit\t\t1:add\n"+
								"2:offer\t\t3:remove\n"+
								"4:poll\t\t5:peek\n"+
								"6:element\n"+
								"------------------------\n"+
								"input a int:"
							);
			choose = input.nextInt();
			switch(choose)
			{
				case 0:	{	//exit
							exit = true;
							break;
						}
				case 1: {	//add
							System.out.print("input the number of int to add in queue:");
							para = input.nextInt();
							System.out.print("before add:\n");
							que.printContain();
							System.out.println("added num:");
							for(int i=0;i<para;i++)
							{
								innum = (int)(Math.random()*RANGE);
								try
								{
									que.add(innum);
								}
								catch(IllegalStateException e)
								{
									System.out.print("\nqueue is full\n");
									System.out.print(e);
									break;
								}
								System.out.print(innum+" ");
							}
							System.out.print("\nafter add:\n");
							que.printContain();	
							break;
						}
				case 2: {	//offer
							System.out.print("input the number of int to add in queue:");
							para = input.nextInt();
							System.out.print("before add:\n");
							que.printContain();
							System.out.println("added num:");
							for(int i=0;i<para;i++)
							{
								innum = (int)(Math.random()*RANGE);
									re = que.offer(innum);
								//System.out.println(re);
								if(!re)
								{
									System.out.print("\nqueue is full");
									break;
								}
								System.out.print(innum+" ");
							}
							System.out.print("\nafter add:\n");
							que.printContain();	
							break;
						}
				case 3: {	//remove
							System.out.println("before remove the head:");
							que.printContain();
							try
							{
								que.remove();
							}
							catch(NoSuchElementException e)
							{
								System.out.print("NO element in queue\n");
								System.out.println(e);
								break;
							}
							System.out.println("after remove the head:");
							que.printContain();
							break;
						}
				case 4:	{	//poll
							System.out.println("before poll the head:");
							que.printContain();
							try 
							{
								reint = que.poll();
							}
							catch(NullPointerException e)
							{
								System.out.println("NO element in queue");
								System.out.println(e);
								break;
							}
							/*
							 * 这里异常情况会返回null 空指针
							 * null空指针不能被当作一个int对待
							 * 会抛出一个NullPointerException的异常
							 * 所以用try catch来捕获这个异常并处理
							 * 下面peek也是一样
							 * */
							System.out.println("after poll the head:");
							que.printContain();
							break;
						}
				case 5:	{	//peek
							System.out.print("queue Contain:\n");
							que.printContain();
							try
							{
								reint = que.peek();
							}
							catch(NullPointerException e)
							{
								System.out.println("No element in queue");
								System.out.println(e);
								break;
							}
							System.out.println("peek:"+reint);
							System.out.println("after peek:");
							que.printContain();
							break;
						}
				case 6:	{	//element
							System.out.print("queue Contain:\n");
							que.printContain();
							try
							{
								reint = que.element();
							}
							catch(NoSuchElementException e)
							{
								System.out.println("No element in queue");
								System.out.println(e);
								break;
							}
							System.out.println("element:"+reint);
							System.out.println("after element:");
							que.printContain();
							break;
						}
				default:break;
			}
		}
		System.out.println("exit process");
		return;
	}
	
	
}
