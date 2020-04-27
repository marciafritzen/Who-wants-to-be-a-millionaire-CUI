package MillionaireGame;

import java.util.ArrayList;
/**
 *
 * @author Paul
 */
public class QuestionsArray {
//this class creates and holds an array of the question class
    private final ArrayList<Question> questions;

    
    //contructors
    public QuestionsArray(){
        this.questions = new ArrayList();
    }
    
    //get and set
    public void addToQuestionArray(Question q){
        this.questions.add(q);
    }
    
    public ArrayList<Question> getQuestionsArray() {
        return this.questions;
    }
}
