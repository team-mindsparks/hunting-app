package com.example.mindsparktreasurehunt;

public class Persistence {

	public static Persistence sharedInstance = new Persistence();
	
	private Hunt selectedHunt;

	public Hunt getSelectedHunt() {
		return selectedHunt;
	}

	public void setSelectedHunt(Hunt selectedHunt) {
		this.selectedHunt = selectedHunt;
	}
	
	private Clue selectedClue;

	public Clue getSelectedClue() {
		return selectedClue;
	}

	public void setSelectedClue(Clue item) {
		this.selectedClue = item;
	}
}
