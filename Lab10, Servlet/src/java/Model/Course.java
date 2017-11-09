package Model;

/**
 *
 * @author luisf
 */
public final class Course {

    public Course(int id, String name, String descr, int credits) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Course{" + "name=" + name + ", descr=" + descr + ", credits=" + credits + ", id=" + id + '}';
    }
    
     public org.json.JSONObject toJson(){
        return new org.json.JSONObject()
                .put("id", this.id)
                .put("name", this.name)
                .put("descr",this.descr)
                .put("credits", this.credits);
    } 

    public static Course fromJson(org.json.JSONObject jo)
            throws org.json.JSONException
    {
        return new Course(
                jo.getInt("id"),
                jo.getString("name"),
                jo.getString("descr"),
                jo.getInt("credits")               
            );
    }
    
    String name, descr;
    int credits, id;
}
