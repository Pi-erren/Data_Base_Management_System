package replacement_algorithms;

import java.util.List;

public interface BufferManager<E> {
	
	/**
	 * Process the query of a list of pages
	 * 
	 * @param sequence
	 * @return the number of missing pages
	 */
	int processQuery(List<E> sequence);

	/**
	 * Process the query for one element
	 * 
	 * @param element
	 * @return 1 if the page was missing, else 0
	 */
	int processQuery(E element);
	
	/**
	 * Insert the element in the next place where element has 0 as an indicator
	 * 
	 * @param element
	 */
	void insert(E element);
}