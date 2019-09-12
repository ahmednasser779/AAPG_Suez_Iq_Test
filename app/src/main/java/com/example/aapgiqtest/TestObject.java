package com.example.aapgiqtest;

public class TestObject {

    private String mImageQuestion;
    private String mQuestion;
    private String mAnswer1;
    private String mAnswer2;
    private String mAnswer3;
    private String mAnswer4;

    public TestObject(String imageQuestion ,String Question, String Answer1, String Answer2, String Answer3, String Answer4) {
        this.mImageQuestion = imageQuestion;
        this.mQuestion = Question;
        this.mAnswer1 = Answer1;
        this.mAnswer2 = Answer2;
        this.mAnswer3 = Answer3;
        this.mAnswer4 = Answer4;
    }

    public String getmImageQuestion() {
        return mImageQuestion;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public String getmAnswer1() {
        return mAnswer1;
    }

    public String getmAnswer2() {
        return mAnswer2;
    }

    public String getmAnswer3() {
        return mAnswer3;
    }

    public String getmAnswer4() {
        return mAnswer4;
    }
}
