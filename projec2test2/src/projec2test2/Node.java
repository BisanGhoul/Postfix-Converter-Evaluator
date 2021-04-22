package projec2test2;


public class Node<T> {
	 T element;
 Node<T> next;
public Node(T element, Node<T> next) {
this.element = element;
this.next = next;
}
public T getData() {
	return element;
}
public void setData(T element) {
	this.element = element;
}
public Node<T> getNext() {
	return next;
}
public void setNext(Node<T> next) {
	this.next = next;
}
public String toString(){
	return element+"";
	}

}