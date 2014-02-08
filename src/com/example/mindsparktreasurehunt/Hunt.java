package com.example.mindsparktreasurehunt;

import java.util.ArrayList;

public class Hunt extends BaseModel {
	private String name;
	private ArrayList<Clue> clues;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}

	public ArrayList<Clue> getClues() {
		return clues;
	}

	public void addClue(Clue clue) {
		clues.add(clue);
	}

}
