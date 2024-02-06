package replacement_algorithms;

import java.util.ArrayList;
import java.util.List;

public class LruBuffer<E> implements BufferManager<E> {
	private final List<E> elements;
	private final int frames;

	public LruBuffer(int frames) {
		this.frames = frames;
		this.elements = new ArrayList<E>();
	}

	@Override
	public int processQuery(List<E> sequence) {
		int misses = 0;	
		for (E seqElement: sequence) {
			misses += this.processQuery(seqElement);
//			System.out.println("\nBuffer: ");
//			System.out.println(elements);
		}
		return misses;
	}

	@Override
	public int processQuery(E element) {
		if (!elements.contains(element)) {
			this.insert(element);
			return 1;
		}
		else {
			this.insert(element);
			return 0;
		}
	}

	@Override
	public void insert(E element) {
		elements.remove(element);
		elements.add(0, element);
		if (elements.size() > frames)
			elements.remove(elements.size() - 1);
	}
}
