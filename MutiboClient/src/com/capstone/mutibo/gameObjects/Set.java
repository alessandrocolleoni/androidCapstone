package com.capstone.mutibo.gameObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class Set implements Parcelable {
	
	private long id;
	
	private String question;
	
	private String explanation;
	
	private String answer1;
	
	private String answer2;
	
	private String answer3;
	
	private String correctAnswer;
	
	public static final Creator<Set> CREATOR = new Parcelable.Creator<Set>() {

		@Override
		public Set createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Set(source);
		}

		@Override
		public Set[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Set[size];
		}
	};
	
	public Set() {}
	
	public Set(Parcel source){
        id = source.readLong();
        question = source.readString();
        explanation = source.readString();
        answer1 = source.readString();
        answer2 = source.readString();
        answer3 = source.readString();
        correctAnswer = source.readString();
    }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
        dest.writeString(question);
        dest.writeString(explanation);
        dest.writeString(answer1);
        dest.writeString(answer2);
        dest.writeString(answer3);
        dest.writeString(correctAnswer);
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
}
