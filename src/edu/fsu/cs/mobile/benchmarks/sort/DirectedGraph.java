// Created by Katrina Fishman
package edu.fsu.cs.mobile.benchmarks.sort;
import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class DirectedGraph {
	public int[][] graph; 
	public int arraySize;
	ArrayList<Integer> completed;
	Random ran;

	public DirectedGraph(int size, boolean isWeighted) {
		int next1, next2;
		ran = new Random(0);
		arraySize = size; 
		completed = new ArrayList<Integer>();
		graph = new int[arraySize][arraySize];

		// initialize everything to 0  
		for (int i = 0; i < arraySize; i++) {
			for (int j = 0; j < arraySize; j++)
				graph[i][j] = 0;
		}
		// generate first edge
		next1 = nextNum(); next2 = nextNum(); 
		// generate the rest of the edges 
		while (next1 == next2) {next1 = nextNum(); next2 = nextNum();}
		if (isWeighted == false) 
			graph[next1][next2] = 1;
		else
			graph[next1][next2] = ran.nextInt(arraySize)+1;
		addToCompleted(next1); addToCompleted(next2);
		// generate edges until you have edges between all of the vertices
		while (completed.size() < arraySize) {
			next1 = getCompleted(); next2 = nextNum(); 
			if (next1 != next2) {
				if (isWeighted == false)
					graph[next1][next2] = 1;
				else
					graph[next1][next2] = ran.nextInt(arraySize)+1;
				addToCompleted(next2); 
			}
		}

		for (int i = 0; i < arraySize; i++)
			showNeighbors(i, isWeighted);

		Log.i("Directed", ""+1);
	}

	public void showNeighbors(int vertex, boolean isWeighted) {
		for (int i = 0; i < graph.length; i++) {
			if (graph[vertex][i] != 0) {
				Log.i("Neighbor of", ""+vertex);
				Log.i("is", ""+i);
				if (isWeighted == true) 
					Log.i(" Edge is ", ""+graph[vertex][i]);
			}
		}
	}
	
	public int getWeight(int vertex1, int vertex2) {
		return graph[vertex1][vertex2];
	}
	
	public ArrayList<Integer> neighbors(int vertex) {
		ArrayList<Integer> n = new ArrayList<Integer>(); 
		for (int i = 0; i < graph.length; i++) {
			if (graph[vertex][i] != 0) 
				n.add(i);
		}
		return n;
	}

	public int nextNum() {
		int next = ran.nextInt(arraySize);
		for (int i = 0; i < completed.size(); i++) {
			if (completed.get(i) == next) {
				next = ran.nextInt(arraySize); 
				i = 0; 
			}
		}
		return next; 
	}
	public void addToCompleted(int addMe) {
		Boolean hasIt = false;
		for (int i = 0; i < completed.size(); i++) {
			if (completed.get(i) == addMe)
				hasIt = true; 
		}
		if (hasIt == false)
			completed.add(addMe);
	}

	public int getCompleted() {
		return completed.get(ran.nextInt(completed.size())); 
	}
	
	public int size() {
		return arraySize; 
	}
}
