package perform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import replacement_algorithms.ClockBuffer;
import replacement_algorithms.FifoBuffer;
import replacement_algorithms.LruBuffer;

public class Main {
	
	public static void main(String[] args) {
		int frames = 4;
		
		List<String> seq1=List.of("A","B","C","D","E","A","B", "C", "D", "E");
		List<String> seq2=List.of("A","B","C","D","B","A","E","F","B","A","B","C","G","F","C","B","A","B","C","F");
		List<String> seq3=List.of("A","B","C","D","E","A", "B", "F", "G", "H", "C", "D", "I", "J");
		List<Integer> seq4=List.of(1, 2, 3, 4, 5, 2, 3, 1, 4, 5, 1, 2, 3, 4, 5, 2, 3, 1, 4);
		
		
		ClockBuffer<Integer> clockBuffer = new ClockBuffer<Integer>(frames);
		FifoBuffer<Integer>  fifoBuffer = new FifoBuffer<Integer>(frames);
		LruBuffer<Integer> lruBuffer = new LruBuffer<Integer>(frames);
//		System.out.println("\n\n----------- Sequence 1 -----------");
//		System.out.println("Processing sequence with clock algorithm...");
//		System.out.println("Page misses: "+clockBuffer.processQuery(seq1));
//		System.out.println("Processing sequence with FiFo algorithm...");
//		System.out.println("Page misses: "+fifoBuffer.processQuery(seq1));
//		System.out.println("Processing sequence with LRU algorithm...");
//		System.out.println("Page misses: "+lruBuffer.processQuery(seq1));
//		
//		clockBuffer = new ClockBuffer<String>(frames);
//		fifoBuffer = new FifoBuffer<String>(frames);
//		lruBuffer = new LruBuffer<String>(frames);
//		System.out.println("\n\n----------- Sequence 2 -----------");
//		System.out.println("Processing sequence with clock algorithm...");
//		System.out.println("Page misses: "+clockBuffer.processQuery(seq2));
//		System.out.println("Processing sequence with FiFo algorithm...");
//		System.out.println("Page misses: "+fifoBuffer.processQuery(seq2));
//		System.out.println("Processing sequence with LRU algorithm...");
//		System.out.println("Page misses: "+lruBuffer.processQuery(seq2));
//		
//		clockBuffer = new ClockBuffer<String>(frames);
//		fifoBuffer = new FifoBuffer<String>(frames);
//		lruBuffer = new LruBuffer<String>(frames);
//		System.out.println("\n\n----------- Sequence 3 -----------");
//		System.out.println("Processing sequence with clock algorithm...");
//		System.out.println("Page misses: "+clockBuffer.processQuery(seq3));
//		System.out.println("Processing sequence with FiFo algorithm...");
//		System.out.println("Page misses: "+fifoBuffer.processQuery(seq3));
//		System.out.println("Processing sequence with LRU algorithm...");
//		System.out.println("Page misses: "+lruBuffer.processQuery(seq3));
		
		clockBuffer = new ClockBuffer<Integer>(frames);
		fifoBuffer = new FifoBuffer<Integer>(frames);
		lruBuffer = new LruBuffer<Integer>(frames);
		System.out.println("\n\n----------- Sequence 4 -----------");
		System.out.println("Processing sequence with clock algorithm...");
		System.out.println("Page misses: "+clockBuffer.processQuery(seq4));
		System.out.println("Processing sequence with FiFo algorithm...");
		System.out.println("Page misses: "+fifoBuffer.processQuery(seq4));
		System.out.println("Processing sequence with LRU algorithm...");
		System.out.println("Page misses: "+lruBuffer.processQuery(seq4));
	}

}
