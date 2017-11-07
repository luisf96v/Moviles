package Model;

/**
 *
 * @author luisf
 */
public final class Course {

    public Course(String name, String descr, int credits) {
        this.name = name;
        this.descr = descr;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    String name, descr;
    int credits;
}
