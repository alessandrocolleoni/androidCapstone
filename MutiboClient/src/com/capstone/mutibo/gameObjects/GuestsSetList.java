package com.capstone.mutibo.gameObjects;

import java.util.ArrayList;

public class GuestsSetList {
	
	public static ArrayList<Set> guestSets() {
		
		ArrayList<Set> guestSets = new ArrayList<Set>();
		
		Set set1 = new Set();
		set1.setQuestion("In one of these movie an actor not appears. Which one?");
		set1.setAnswer1("Taxi Driver");
		set1.setAnswer2("Casino");
		set1.setAnswer3("Goodfellas");
		set1.setCorrectAnswer("Scarface");
		set1.setExplanation("Robert De Niro does not apppear on Scarface");
		
		guestSets.add(set1);
		
		Set set2 = new Set();
		set2.setQuestion("All based on a writer''s book?");
		set2.setAnswer1("The Shining");
		set2.setAnswer2("Pet Sematary");
		set2.setAnswer3("It");
		set2.setCorrectAnswer("Poltergeist");
		set2.setExplanation("Poltergeist is not based on a Stephen King book");
		
		guestSets.add(set2);
		
		Set set3 = new Set();
		set3.setQuestion("Which movie differs from others?");
		set3.setAnswer1("'X-Men: Days of Future Past");
		set3.setAnswer2("The Matrix");
		set3.setAnswer3("Lucy");
		set3.setCorrectAnswer("Forrest Gump");
		set3.setExplanation("Forrest Gump is not a science Fiction movie");
		
		guestSets.add(set3);
		
		Set set4 = new Set();
		set4.setQuestion("A matter of awards...");
		set4.setAnswer1("Tom Jones");
		set4.setAnswer2("Rocky");
		set4.setAnswer3("The Greatest Show on Earth");
		set4.setCorrectAnswer("Citizen Kane");
		set4.setExplanation("Citizen Kane did not win Best Picture");
		
		guestSets.add(set4);
		
		Set set5 = new Set();
		set5.setQuestion("Old versus New...");
		set5.setAnswer1("The Empire Strikes Back");
		set5.setAnswer2("Return of the Jedi");
		set5.setAnswer3("A New Hope");
		set5.setCorrectAnswer("The Phantom Menace");
		set5.setExplanation("The Phantom Menace is part of the new Star Wars trilogy");
		
		guestSets.add(set5);
		
		Set set6 = new Set();
		set6.setQuestion("Which cartoon differs from others?");
		set6.setAnswer1("Monsters Inc.");
		set6.setAnswer2("Toy Story");
		set6.setAnswer3("Finding Nemo");
		set6.setCorrectAnswer("Shrek");
		set6.setExplanation("Shrek is not a Pixar movie");
		
		guestSets.add(set6);
		
		Set set7 = new Set();
		set7.setQuestion("Death and blood... But one is different. Which one?");
		set7.setAnswer1("Zombieland");
		set7.setAnswer2("Dawn of the Dead");
		set7.setAnswer3("28 Days Later");
		set7.setCorrectAnswer("Saw");
		set7.setExplanation("Saw is not a zombie movie");
		
		guestSets.add(set7);
		
		Set set8 = new Set();
		set8.setQuestion("Which is the one not about Vietnam?");
		set8.setAnswer1("Apocalypse Now");
		set8.setAnswer2("Full Metal Jacket");
		set8.setAnswer3("Platoon");
		set8.setCorrectAnswer("Salvate il soldato ryan");
		set8.setExplanation("Save ryan is about WWII, not Vietnam War");
		
		guestSets.add(set8);
		
		return guestSets;
	}
	
}
