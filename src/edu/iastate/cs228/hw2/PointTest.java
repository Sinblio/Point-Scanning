package edu.iastate.cs228.hw2;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PointTest 
{
	public static void main(String args[])
	{
		//Defines 2 coordinates for testing
		int x = 1;
		int y = 2;
		
		//Defines 2 test points 
		Point point1 = new Point();
		Point point2 = new Point(x,y);
		
		//Creates a duplicate point from point2
		Point point3 = new Point(point2);
		
		//Tests point2 and point3's constructor
		assertTrue(point2.getX() == point3.getX());
		assertTrue(point2.getY() == point3.getY());
		assertTrue(point2.getX() == x);
		assertTrue(point2.getY() == y);
		
		//Tests .equals method
		assertTrue(point2.equals(point3));
		assertFalse(point2.equals(point1));
		
		//Defines additional variable for testing the compareTo method
		Point point4 = new Point(-1, -2);
		
		//Tests compareTo with xORy as true
		Point.setXorY(true);
		assertTrue(point2.compareTo(point3) == 0);
		assertTrue(point1.compareTo(point2) == -1);
		assertTrue(point1.compareTo(point4) == 1);
		
		//Tests compareTo with xORy as false
		Point.setXorY(false);
		assertTrue(point2.compareTo(point3) == 0);
		assertTrue(point1.compareTo(point2) == -1);
		assertTrue(point1.compareTo(point4) == 1);
		
		//Tests toString method
		String s = "(" + x + ", " + y + ")";
		assertTrue(point2.toString().equals(s));
		
		//Prints passing message
		System.out.println("Everythings Good!!!");
	}
}
