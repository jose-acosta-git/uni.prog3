package tpeProg;

import java.util.Iterator;
import java.util.LinkedList;

public class IteradorArcos<T> implements Iterator<Arco<T>> {
	
	private Iterator<Arco<T>> it;
	
	public IteradorArcos(LinkedList<Arco<T>> arcos) {
		this.it = arcos.iterator();
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public Arco<T> next() {
		return it.next();
	}

}
