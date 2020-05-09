package MillionaireGame;

import java.util.ArrayList;
/**
 * @author Paul Vu 17981406, Peter Ho 17978553
 */
public class QuestionsArray {
//this class creates and holds an array of the question class, FileReadWriter will fill up this class array with questions
    private final ArrayList<Question> questions;
    
    //contructor
    public QuestionsArray(){
        this.questions = new ArrayList();
    }
    

    public void addToQuestionArray(Question q){
        this.questions.add(q);
    }
    
    //get
    public ArrayList<Question> getQuestionsArray() {
        return this.questions;
    }
}
