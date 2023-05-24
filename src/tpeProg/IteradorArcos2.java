package tpeProg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

public class IteradorArcos2<T> implements Iterator<Arco<T>> {
	
	private Iterator<Entry<Integer, T>> it;
	
	public IteradorArcos2(HashMap<Integer, T> arcos) {
		this.it = arcos.iterator();
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Arco<T> next() {
		// TODO Auto-generated method stub
		return null;
	}

}
