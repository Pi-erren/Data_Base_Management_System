package replacement_algorithms;

import java.util.ArrayList;
import java.util.List;

public class ClockBuffer<E> implements BufferManager<E>{
	private final List<E> elements;
	private final List<Integer> indicators;
	private int pointer;
	private final int frames;
	
	public ClockBuffer(int frames) throws IllegalArgumentException{
		if (frames < 1)
			throw new IllegalArgumentException("Number of frame of the buffer muste be >= 1");
		this.frames = frames;
		this.elements = new ArrayList<E>();
		this.indicators = new ArrayList<Integer>();
		for (int i = 0; i < frames; i++) {
			elements.add(null);
			indicators.add(0);
		}
		this.pointer = 0;
	}
	
	@Override
	public int processQuery(List<E> sequence) {
		int misses = 0;	
		for (E seqElement: sequence) {
			misses += this.processQuery(seqElement);
//			System.out.println("\nBuffer: ");
//			for (int i = 0; i < elements.size(); i++) {
//				System.out.println(elements.get(i) + ": " + indicators.get(i));
//			}
//			System.out.println("Pointer is pointing to: " + elements.get(pointer));
		}
		return misses;
	}
	
	@Override
	public int processQuery(E element) {
		switch (this.check(element)) {
			case 1:
				return 0;
			case 0:
				this.setIndicator(element, 1);
				return 0;
			case -1:
				// If there is a free frame in the buffer
				if (!this.add(element))
					this.insert(element);
				return 1;
		}	
		return 0;
	}
	
	@Override
	public void insert(E element) {
		// Move the pointer until it points to an element with an indicator at 0
		while (indicators.get(elements.indexOf(elements.get(pointer))) != 0) {
			indicators.set(pointer, 0);
			this.movePointer();
		}
		this.replace(element, pointer);
	}
	
	/**
	 * Add the given element in the first free frame
	 * 
	 * @param element the element to add
	 * @return true / false if we succeeded to add the element or not
	 */
	public boolean add(E element) {
		int freeIndex = this.checkForPlace();
		if (freeIndex != -1) {
			this.elements.set(freeIndex, element);
			this.indicators.set(freeIndex, 1);
			this.movePointer();
			return true;
		}
		return false;
	}
	
	/**
	 * Move the pointer of the clock algorithm
	 */
	public void movePointer() {
		if (pointer < frames - 1)
			pointer ++;
		else
			pointer = 0;
	}
		
	/**
	 * Replace an element
	 * 
	 * @param element, the element to put in the buffer
	 * @param index, the index of the element getting replaced
	 */
	public void replace(E element, int index){
		elements.set(index, element);
		indicators.set(index, 1);
	}
	
	/**
	 * Set the "second chance" indicator for a given element
	 * 
	 * @param element the element on which to set the "second chance" indicator
	 */
	public void setIndicator(E element, int indicator) {
		indicators.set(elements.indexOf(element), indicator);
	}
	
	/**
	 * Check if the element is in the buffer and return its indicator (0 or 1 wether it has a second chance or not)
	 * 
	 * @param element the element to check
	 * @return the indicator of the element if it was found, -1 if not
	 */
	public int check(E element) {
		for (int i = 0; i < frames; i++)
			if (elements.get(i) == element) return indicators.get(i);
		return -1;
	}
	
	
	/**
	 * Look if there is a free frame in the buffer
	 * 
	 * @return the index of the first free frame found
	 */
	public int checkForPlace() {
		for (E element : elements) {
			if (element == null)
				return elements.indexOf(element);
		}
		return -1;
	}
}