package Model;

/**
 *
 * @author luisf
 */
public final class Student {

    public Student(String id, String name, String lastN1, String lastN2, int age) {
        this.id = id;
        this.name = name;
        this.lastN1 = lastN1;
        this.lastN2 = lastN2;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastN1() {
        return lastN1;
    }

    public void setLastN1(String lastN1) {
        this.lastN1 = lastN1;
    }

    public String getLastN2() {
        return lastN2;
    }

    public void setLastN2(String lastN2) {
        this.lastN2 = lastN2;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", lastN1=" + lastN1 + ", lastN2=" + lastN2 + ", age=" + age + '}';
    }
    
    public org.json.JSONObject toJson(){
        return new org.json.JSONObject()
                .put("id", id)
                .put("name", name)
                .put("lastN1",lastN1)
                .put("lastN2", lastN2)
                .put("age", age);
    } 

    public static Student fromJson(org.json.JSONObject jo)
            throws org.json.JSONException
    {
        return new Student(
                jo.getString("id"),
                jo.getString("name"),
                jo.getString("lastN1"),
                jo.getString("lastN2"),
                jo.getInt("age")               
            );
    }
    
    
    private String id, name, lastN1, lastN2;
    private int age;
}
