package edu.iastate.cs228.hw2;

import java.io.BufferedWriter;
import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array by polar angle with respect to a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class RotationalPointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	protected String outputFileName;   // "select.txt", "insert.txt", "merge.txt", or "quick.txt"
	
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[]. Set outputFileName. 
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public RotationalPointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null)
			throw new IllegalArgumentException("Array is empty in RotationalPointScanner");
		this.sortingAlgorithm = algo;
		this.points = new Point[pts.length];
		for (int j = 0; j < pts.length; j++)
			this.points[j] = new Point(pts[j]);
	}

	
	/**
	 * This constructor reads points from a file. Set outputFileName. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected RotationalPointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		Scanner in = new Scanner(new File(inputFileName));
		
		ArrayList<Integer> ary = new ArrayList<Integer>();
		
		while (in.hasNext())
		{
			ary.add(in.nextInt());
		}
		
		in.close();
		
		if (ary.size() % 2 != 2)
			throw new InputMismatchException("File has odd number of elements in RotationalPointScanner");
		
		this.points = new Point[ary.size()];
		
		for (int j = 0; j < ary.size(); j += 2)
		{
			this.points[j/2] = new Point(ary.get(j), ary.get(j + 1));
		}
	}

	
	/**
	 * Carry out three rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates. 
	 *     d) Sort points[] again by the polar angle with respect to medianCoordinatePoint.
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting. Copy the sorting result back onto the array points[] by calling 
	 * the method getPoints() in AbstractSorter. 
	 *      
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter; 
		
		switch (this.sortingAlgorithm)
		{
			case InsertionSort :
				aSorter = new InsertionSorter(points);
				this.outputFileName = "insert.txt";
				break;
			case MergeSort :
				aSorter = new MergeSorter(points);
				this.outputFileName = "merge.txt";
				break;
			case QuickSort :
				aSorter = new QuickSorter(points);
				this.outputFileName = "quick.txt";
				break;
			case SelectionSort :
				aSorter = new SelectionSorter(points);
				this.outputFileName = "select.txt";
				break;
			default :
				aSorter = null;
				break;
		}
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the three 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0, 1, or 2. in case it is 2, must have made 
		//        the call setReferencePoint(medianCoordinatePoint) already. 
		//
		//     b) call sort(). 		
		// 
		// sum up the times spent on the three sorting rounds and set the instance variable scanTime. 
		
		Point medianX, medianY;
		long startTime, endTime, totalTime;
		
		aSorter.setComparator(0);
		startTime = System.nanoTime();
		aSorter.sort();
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		medianX = aSorter.getMedian();
		
		aSorter.setComparator(1);
		startTime = System.nanoTime();
		aSorter.sort();
		endTime = System.nanoTime();
		totalTime += endTime - startTime;
		medianY = aSorter.getMedian();
		
		this.medianCoordinatePoint = new Point(medianX.getX(), medianY.getY());
		aSorter.setReferencePoint(this.medianCoordinatePoint);
		aSorter.setComparator(2);
		startTime = System.nanoTime();
		aSorter.sort();
		endTime = System.nanoTime();
		totalTime += endTime - startTime;
		aSorter.getPoints(points);
		
		this.scanTime = totalTime;
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		String out;
		switch (this.sortingAlgorithm)
		{
			case InsertionSort :
				out = "Insert\t\t" + points.length + "\t" + this.scanTime;
				break;
			case MergeSort :
				out = "Merge\t\t" + points.length + "\t" + this.scanTime;
				break;
			case QuickSort :
				out = "Quick\t\t" + points.length + "\t" + this.scanTime;
				break;
			case SelectionSort :
				out = "Select\t\t" + points.length + "\t" + this.scanTime;
				break;
			default :
				out = null;
				break;
		}
		return out; 
	}
	
	
	/**
	 * Write points[] after a call to scan().  When printed, the points will appear 
	 * in order of polar angle with respect to medianCoordinatePoint with every point occupying a separate 
	 * line.  The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		String out = "";
		
		for (Point x : this.points)
		{
			out += x.toString();
			out += "\n";
		}
		
		return out; 
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException
	{
		if (!(new File(this.outputFileName)).exists())
			throw new FileNotFoundException("Error in RotationalPointScanner");
			
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFileName));
			writer.append(this.toString());
			writer.close();
		} 
		catch (IOException e) 
		{
		}
	}	

	
	/**
	 * This method is called after each scan for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{		
		int numSegs = 0;  // number of segments to draw
		
		for (int j = 0; j < points.length - 1; j++)
			if (!points[j].equals(points[j+1]))
				numSegs += 1;
		
		if (!points[0].equals(points[points.length - 1]))
			numSegs += 1;

		// Based on Section 4.1, generate the line segments to draw for display of the sorting result.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		Segment[] segments = new Segment[numSegs]; 
		
		int segCnt = 0;
		
		for (int j = 0; j < points.length - 1; j++)
		{
			if (!points[j].equals(points[j+1]))
			{
				segments[segCnt] = new Segment(points[j], points[j+1]);
				segCnt++;
			}
		}
		
		if (!points[0].equals(points[points.length - 1]))
		{
			segments[segCnt] = new Segment(points[0], points[points.length - 1]);
			segCnt++;
		}

		String sort = null; 
		
		switch(sortingAlgorithm)
		{
			case SelectionSort: 
				sort = "Selection Sort"; 
				break; 
			case InsertionSort: 
				sort = "Insertion Sort"; 
				break; 
			case MergeSort: 
				sort = "Mergesort"; 
				break; 
			case QuickSort: 
				sort = "Quicksort"; 
				break; 
			default: 
				break; 		
		}
		
		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, sort);
		
	}
		
}
