class Node<T> {
	Node(T d, Node<T> n) { data = d; next = n; }
	T data;
	Node<T> next;
}

public class LinkedList<T> implements Sequence<T> {

	private Node<T> front;
	
	// Create a linked list with no elements.
	public LinkedList() { front = null; }
	
	public Iterator<T> begin() { return new ListIterator(front); }
	
	public Iterator<T> end() { return new ListIterator(null); }
	
	// Add element x to the front of the list.
	public void insert_front(T x) {
		Node<T> n = new Node<T>(x, front);
		front = n;
	}
	
	// Insert element x so that it appears one position after the position pos.
	// precondition: pos is the position of an element in the sequence.
	public void insert_after(ListIterator pos, T x) { // student implement
		Node<T> n = new Node<T>(x, pos.node.next);
		pos.node.next = n;
	}

	public class ListIterator implements Iterator<T> {

		Node<T> node;

		ListIterator(Node<T> p) { node = p; }

		public T get() { return node.data; }

		public void set(T x) { node.data = x; }

		public void advance() { node = node.next; }

		public boolean equals(Iterator<T> other) {
			return node == ((ListIterator)other).node;
		}

		public ListIterator clone() {
			return new ListIterator(node);
		}

	}

	// For purposes of comparison with SeqAlgo.equals
	public boolean equals(T other[]) {
		Node<T> i = this.front; int j = 0;
		while (i != null & j != other.length) {
			if (i.data != other[j])
				return false;
			i = i.next; ++j;
		}
		return i == null && j == other.length;
	}
}
