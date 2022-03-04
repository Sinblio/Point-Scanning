package edu.iastate.cs228.hw2;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		// TODO 
		// 
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       RotationalPointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		// 	
		RotationalPointScanner[] scanners = new RotationalPointScanner[4];
		Scanner in = new Scanner(System.in);
		int trialNumber = 1;
		int command;
		Random RNGesus = new Random();
		
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println();
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
		System.out.print("Trial " + trialNumber + " : ");
		command = in.nextInt();
		
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() and draw() 
		//        methods in the RotationalPointScanner class.  You can visualize the result of each scan.  
		//        (Windows have to be closed manually before rerun.)  
		// 
		//     c) After all four scans are done for the input, print out the statistics table (cf. Section 2). 
		//
		// A sample scenario is given in Section 2 of the project description. 
		
		while (command == 1 || command == 2)
		{
			if (command == 1)
			{
				int numberOfPoints;
				System.out.print("Enter number of random points : ");
				numberOfPoints = in.nextInt();
				Point points[] = generateRandomPoints(numberOfPoints, RNGesus); 
				
				scanners[0] = new RotationalPointScanner(points, Algorithm.SelectionSort);
				scanners[1] = new RotationalPointScanner(points, Algorithm.InsertionSort);
				scanners[2] = new RotationalPointScanner(points, Algorithm.MergeSort);
				scanners[3] = new RotationalPointScanner(points, Algorithm.QuickSort);
			}
			if (command == 2)
			{
				String fileName;
				System.out.print("Enter number of random points : ");
				fileName = in.next();
				
				scanners[0] = new RotationalPointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new RotationalPointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new RotationalPointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new RotationalPointScanner(fileName, Algorithm.QuickSort);
			}
			
			for (RotationalPointScanner x : scanners)
			{
				x.scan();
				x.draw();
			}
			
			System.out.println();
			System.out.println("algorithm size time (ns)");
			System.out.println("----------------------------------");
			
			for (RotationalPointScanner x : scanners)
			{
				System.out.println(x.stats());
			}
			
			System.out.println("----------------------------------");		
			System.out.println();
			System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
			System.out.print("Trial " + trialNumber + " : ");
			command = in.nextInt();
		}
				
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts == 0)
			throw new IllegalArgumentException("Array will be empty in Comparesorters");
		Point points[] = new Point[numPts];
		
		for (int j = 0; j < numPts; j++)
			points[j] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		
		return points; 
	}
	
}
