package tpeProg;

import java.util.Iterator;
import java.util.Set;

public class IteradorVertices implements Iterator<Integer>{
	
	private Iterator<Integer> it;
	
	public IteradorVertices(Set<Integer> vertices) {
		this.it = vertices.iterator();
	}

	@Override
	public boolean hasNext() {
		return this.it.hasNext();
	}

	@Override
	public Integer next() {
		return this.it.next();
	}

}
