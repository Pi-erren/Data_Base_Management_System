package replacement_algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FifoBuffer<E> implements BufferManager<E>{
	
	private Queue<E> elements;
	
	private int frames;
	
	public FifoBuffer(int frames) {
		if (frames < 1) throw new IllegalArgumentException("Number of frame of the buffer muste be >= 1");
		this.frames = frames;
		this.elements = new LinkedList<E>();
		for (int i = 0; i < frames; i++) {
			elements.add(null);
		}
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
		return 0;
	}

	@Override
	public void insert(E element) {
		elements.offer(element);
		if (elements.size() >= frames) elements.poll();
	}
}
