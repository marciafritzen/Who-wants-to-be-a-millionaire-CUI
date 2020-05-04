
package MillionaireGame;

/**
 *
 * @author Paul
 */
public enum Lifeline {
    
    FiftyFity("50/50") ,CallAFriend("Call A Friend"), PassQuestion("Pass Question");
   
    private final String lifeLineName;
    
    private Lifeline(String lifeLineName){
        this.lifeLineName = lifeLineName;       
    }
    public String getlifeLineName() {
        return lifeLineName;
    }    

}
