package com.example.mindsparktreasurehunt;

import java.util.ArrayList;

public class Hunt extends BaseModel {
	private String title;
	private ArrayList<Clue> clues;
	private String description;
	
	public Hunt() {
		clues = new ArrayList<Clue>();
	}
	
	public String toString() {
		return getTitle();
	}

	public ArrayList<Clue> getClues() {
		return clues;
	}

	public void addClue(Clue clue) {
		clues.add(clue);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
