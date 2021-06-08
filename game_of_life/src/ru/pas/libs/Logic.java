package ru.pas.libs;

import java.util.ArrayList;
import java.util.Iterator;

public class Logic {
	private int mapSideLength = 6;
	ArrayList<boolean[][]> generationsHistory = new ArrayList<boolean[][]>();
	
	public Logic (int _mapSideLength){
		mapSideLength = _mapSideLength;
	}
	public boolean[][] createNewGeneration(boolean old[][]){
		boolean newGenerationCell[][] = new boolean[mapSideLength][mapSideLength];
		int n = mapSideLength - 1;
		int lifeAround = 0;
	    for(int i = 0; i < mapSideLength; i++)
			for(int j = 0; j < mapSideLength; j++){
				if (i == 0 && j == 0){
					lifeAround = bTI(old[n][n]) + bTI(old[n][0]) + bTI(old[n][1]) + bTI(old[0][n]) + bTI(old[0][1]) + bTI(old[1][n]) + bTI(old[1][0]) + bTI(old[1][1]);
				}else if (i == n && j == n){
					lifeAround = bTI(old[n - 1][n - 1]) + bTI(old[n - 1][n]) + bTI(old[n - 1][0]) + bTI(old[n][n -1]) + bTI(old[n][0]) + bTI(old[0][n - 1]) + bTI(old[0][n]) + bTI(old[0][0]);
				}else if (i == 0 && j == n){
					lifeAround = bTI(old[n][n - 1]) + bTI(old[n][n]) + bTI(old[n][0]) + bTI(old[0][n -1]) + bTI(old[0][0]) + bTI(old[1][n - 1]) + bTI(old[1][n]) + bTI(old[1][0]);
				}else if (i == n && j == 0){
					lifeAround = bTI(old[n - 1][n]) + bTI(old[n - 1][0]) + bTI(old[n - 1][1]) + bTI(old[n][n]) + bTI(old[n][1]) + bTI(old[0][n]) + bTI(old[0][0]) + bTI(old[0][1]);
				}else if (j == n){
					lifeAround = bTI(old[i - 1][n - 1]) + bTI(old[i - 1][n]) + bTI(old[i - 1][0]) + bTI(old[i][n - 1]) + bTI(old[i][0]) + bTI(old[i + 1][n - 1]) + bTI(old[i + 1][n]) + bTI(old[i + 1][0]);
				}else if (i == n){
					lifeAround = bTI(old[n - 1][j - 1]) + bTI(old[n - 1][j]) + bTI(old[n - 1][j + 1]) + bTI(old[n][j - 1]) + bTI(old[n][j + 1]) + bTI(old[0][j - 1]) + bTI(old[0][j]) + bTI(old[0][j + 1]);
				}else if (j == 0){
					lifeAround = bTI(old[i - 1][n]) + bTI(old[i - 1][0]) + bTI(old[i - 1][1]) + bTI(old[i][n]) + bTI(old[i][1]) + bTI(old[i + 1][n]) + bTI(old[i + 1][0]) + bTI(old[i + 1][1]);
				}else if (i == 0){
					lifeAround = bTI(old[n][j - 1]) + bTI(old[n][j]) + bTI(old[n][j + 1]) + bTI(old[0][j - 1]) + bTI(old[0][j + 1]) + bTI(old[1][j - 1]) + bTI(old[1][j]) + bTI(old[1][j + 1]);
				}else{
					lifeAround = bTI(old[i - 1][j - 1]) + bTI(old[i - 1][j]) + bTI(old[i - 1][j + 1]) + bTI(old[i][j - 1]) + bTI(old[i][j + 1]) + bTI(old[i + 1][j - 1]) + bTI(old[i + 1][j]) + bTI(old[i + 1][j + 1]);
				}
				newGenerationCell[i][j] = lifeCalc(old[i][j], lifeAround);
			}
		return newGenerationCell;
	}
	
	private int bTI(boolean bool){ //booleanToInt
		return bool ? 1 : 0;
	}
	
	private boolean lifeCalc (boolean old, int count){
		boolean life = false;
		if (old){
			if (count != 2 && count != 3)
				life = false;
			else
				life = old;
		}else{
			if (count == 3)
				life = true;
		}
		return life;
	}
	
	public void saveGeneration (boolean generation[][]){ //запись поколения в историю поколений
		generationsHistory.add(generation);
	}
	
	public boolean compareGenerations (boolean newGeneration[][]){ // сравнивает текущее поколение с предыдущими
		int count = 0;
		int generation = -1;
		boolean oldGeneration[][] = new boolean[newGeneration.length][newGeneration[0].length];
		Iterator <boolean[][]> iter = generationsHistory.iterator();
		while(iter.hasNext()){
			oldGeneration = iter.next();
			generation++;
			for(int i = 0; i < mapSideLength; i++)
				for (int j = 0; j < mapSideLength; j++){
					if (newGeneration[i][j] == oldGeneration[i][j])
						count++;
				}
			if (count == newGeneration.length*newGeneration[0].length){
				return true;
			}else
				count = 0;
		}
		return false;
	}
	
	public int cellsLiveCount(boolean newGeneration[][]){
		int count = 0;
		for(int i = 0; i < mapSideLength; i++)
			for (int j = 0; j < mapSideLength; j++){
				if (newGeneration[i][j])
					count++;
			}
		return count;
	}
}