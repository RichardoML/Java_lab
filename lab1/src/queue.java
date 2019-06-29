package lab1;

import java.util.Stack;
import java.util.NoSuchElementException;


public class queue<E> extends Stack<E>{

	public final int dump =10;
	private Stack<E> stk;
	/*
	 * stk��Ϊ��һ�������洢���ݵ�tasck
	 * super�е�stack��Ϊһ�����õ�stack
	 * */
	
	public queue()
	{
		super();
		stk = new Stack<E>();
	}
	
	/*
	 * �������ܣ���stk����ʱ��ת�����ݵ�super��stack��
	 * ע�⣺ԭ����˳���ڵ���֮�󷴹�����
	 * stk�е�ջ��Ԫ�ػ�ŵ�super��ջ��
	 * ����queue��Ԫ�ص�˳��Ӧ���� super[ջ��] -> super[ջ��] -> stk[ջ��] ->stk[ջ��]
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
			if(!super.empty())//������
			{
				throw new IllegalStateException();
			}
			else //stk��������stack�գ�������
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
			transdata();//Ԫ��ֻ������stk��
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
	 * �������ã����մ�ӡ���е�����,Ϊ�˲���ʱ����鿴��������
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
