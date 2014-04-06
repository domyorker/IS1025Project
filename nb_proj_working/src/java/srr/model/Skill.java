package srr.model;

import java.math.BigInteger;

/**
 * A class to represent a skill
 * @author Jose Marte
 */
public class Skill {
    private BigInteger skillID;
    private String name;

    /**
     * @return the skillID
     */
    public BigInteger getSkillID() {
        return skillID;
    }

    /**
     * @param skillID the skillID to set
     */
    public void setSkillID(BigInteger skillID) {
        this.skillID = skillID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
