package projec2test2;


import java.util.EmptyStackException;

public class StackLinkedList<T> {

	   
    // Constructor 
	StackLinkedList() 
    { 
        this.top = null; 
    } 
	 Node<T> top; 
	 
	 
	 public boolean isEmpty() {
		 return (top==null);
		 
	 }
	 public void push(T x) {
    	if(x==null)
    	{
    		throw new NullPointerException();
    	}
    	top=new Node<T>(x,top);
    }
    
    public T pop() {
    	if(isEmpty()) {
    		throw new EmptyStackException();
    	}
    	T element = top.element;
    	top = top.next;
    	return element;
    	
    }
    
    public T peek() {
    	if(isEmpty()) {
    		throw new EmptyStackException();
    	}
    	T element = top.element;
    	return element;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
