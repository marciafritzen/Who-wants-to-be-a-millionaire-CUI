/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MillionaireGame;

/**
 * @author Paul Vu 17981406, Peter Ho 17978553
 */
public class Question {
//the question class serves to hold a single question with multiple answers but only 1 correct answer
    private String question;
    private String[] answers;
    private int correctAnswer;
    
    
    public Question(String question, String[] answers, int correctAnswer) {
        this.setQuestion(question);
        this.setAnswers(answers);
        this.setCorrectAnswer(correctAnswer);
    }
    
//get and set methods
    public String getQuestion() {
        return this.question;
    }

    private void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return this.answers;
    }

    private void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return this.correctAnswer;
    }

    private void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    /**
     * toString method prints out question and the four possible answers[]
     * @return output
     */
    @Override
    public String toString() {
       String question_string = this.getQuestion() + "\n";
        for (String ans : this.getAnswers()) {
            question_string += ans + "\n";
        }
        question_string += "Answer: ";
        return question_string;
    }
}
