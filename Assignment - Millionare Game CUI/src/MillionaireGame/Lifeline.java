
package MillionaireGame;

/**
 * @author Paul Vu 17981406, Peter Ho 17978553
 */
public enum Lifeline {
    //lifeline class holds ID of different life lines, functionailty of life lines are in the game class
    FiftyFity("50/50") ,CallAFriend("Call A Friend"), PassQuestion("Pass Question");
   
    private final String lifeLineName;
    
    private Lifeline(String lifeLineName){
        this.lifeLineName = lifeLineName;       
    }
    public String getlifeLineName() {
        return lifeLineName;
    }    

}
