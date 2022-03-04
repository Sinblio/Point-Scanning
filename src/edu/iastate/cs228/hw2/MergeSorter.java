package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "MergeSorter";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int len = pts.length;
		if (len <= 1)
			return;
		int half = len / 2;
		
		Point arry1[] = new Point[half];
		Point arry2[] = new Point[len - half];
		
		for (int j = 0; j < arry1.length; j++)
			arry1[j] = pts[j];
		for (int j = half; j < len; j++)
			arry2[j - half] = pts[j];
		
		mergeSortRec(arry1);
		mergeSortRec(arry2);
		
		int j = 0;
		int k = 0;
		
		while (j < arry1.length && k < arry2.length)
		{
			if (this.pointComparator.compare(this.points[j], this.points[k]) == 1)
			{
				swap(j, k);
				k++;
			} else {
				j++;
			}
		}
	}

	
	// Other private methods in case you need ...

}
