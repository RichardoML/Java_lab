package lab1;

import java.util.Stack;
import java.util.NoSuchElementException;


public class queue<E> extends Stack<E>{

	public final int dump =10;
	private Stack<E> stk;
	/*
	 * stk作为第一个用来存储数据的tasck
	 * super中的stack作为一个备用的stack
	 * */
	
	public queue()
	{
		super();
		stk = new Stack<E>();
	}
	
	/*
	 * 函数功能：当stk满了时，转移数据到super的stack中
	 * 注意：原来的顺序在倒入之后反过来，
	 * stk中的栈顶元素会放到super的栈底
	 * 所以queue中元素的顺序应该是 super[栈顶] -> super[栈底] -> stk[栈底] ->stk[栈顶]
	 */
	private void transdata()
	{
		while(!stk.empty())
		{
			super.push(stk.pop());
		}
	}
	
	
	public boolean add(E e) throws IllegalStateException,ClassCastException,NullPointerException,IllegalArgumentException
	{
		if (stk.size() >= dump)
		{
			if(!super.empty())//队列满
			{
				throw new IllegalStateException();
			}
			else //stk满，备用stack空，则倒数据
			{
				transdata();	
			}
		}
		stk.push(e);
		return true;
	}
	
	
	public boolean offer(E e) throws ClassCastException,NullPointerException,IllegalArgumentException
	{
		if(stk.size() == dump)
		{
			if(!super.empty()) return false;
			else
			{
				transdata();
			}
		}
		stk.push(e);
		return true;
	}
	
	
	
	public E remove() throws NoSuchElementException
	{
		if(super.empty())
		{
			if(stk.empty())
			{
				throw new NoSuchElementException();
			}
			transdata();//元素只存在于stk中
		}
		return super.pop();
	}
	
	public E poll()
	{
		if(super.empty())
		{
			if(stk.empty())
			{
				return null;
			}
			transdata();
		}
		return super.pop();
	}
	
	
	public E peek()
	{
		if(super.empty())
		{
			if(stk.empty())
			{
				return null;
			}
			transdata();
		}
		return super.peek();
	}
	
	
	
	public E element() throws NoSuchElementException
	{
		if(super.empty())
		{
			if(stk.empty())
			{
				throw new NoSuchElementException();
			}
			transdata();
		}
		return super.peek();
	}
	
	/*
	 * 函数作用：按照打印队列的内容,为了测试时方便查看队列内容
	 * */
	public void printContain()
	{
		int num;
		if(!super.empty())
		{
			//print super
			num = super.size();
			for(;num>0;num--)
			{
				System.out.print(super.elementAt(num-1)+" ");
			}
		}
		else
		{
			if(stk.empty())
			{
				System.out.print("empty");
			}
		}
		
		if(!stk.empty())
		{
			num = 0;
			for(;num<stk.size();num++)
			{
				System.out.print(stk.elementAt(num)+" ");
			}
		}
		
		System.out.print("\n");
	}
}
