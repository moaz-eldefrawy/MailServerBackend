package Services;

import java.io.Serializable;

class PriorityQueue implements  Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = -5562556808576696772L;
	int size;

	public class Node {
		Object element;
		Node next;
		int key;
	}

	Node head = new Node();
	Node tail = new Node();

	public void insert(Object item, int key) {

		if (key <= 0) {
			throw new RuntimeException("Invalid key");
		}

		Node x = new Node();
		x.element = item;
		x.key = key;

		if (size() == 0) {

			head = tail = x;

		} else {

			if (x.key < head.key) {
				x.next = head;
				head = x;

			}

			else {

				Node temp = head;

				while (temp.next != null) {
					if (x.key < temp.next.key) {
						break;
					}
					temp = temp.next;
				}
				x.next = temp.next;
				temp.next = x;
			}

		}

		size++;

	}

	public Object removeMin() {

		if (isEmpty())
			throw new RuntimeException("Empty queue");
		Object min = new Object();
		min = head.element;
		head = head.next;
		size--;
		return min;
	}

	public Object min() {

		if (isEmpty())
			throw new RuntimeException("Empty queue");
		return head.element;
	}

	public boolean isEmpty() {

		return size() == 0;
	}

	public int size() {
		return size;
	}

}
