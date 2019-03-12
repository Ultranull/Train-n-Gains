package banana.pudding.pie.train_n_gains;

import android.media.Image;

import org.json.JSONObject;

public class WorkoutAction {



    public enum TYPE{
        ARMS,
        LEGS,
        CORE,
        BACK
    }

    private int id;
    private String name;
    private String description;
    private String instructions;
    private TYPE type;

    private Image image;

    public WorkoutAction(){
        this(-1,"None","None","None",TYPE.CORE);
    }
    public WorkoutAction(JSONObject obj){
        try {
            this.id=          obj.getInt("id");
            this.name=        obj.getString("name");
            this.description= obj.getString("desc");
            this.instructions=obj.getString("inst");
            this.type=        TYPE.valueOf(obj.getString("type"));
        }catch (Exception e){
            e.printStackTrace();
            this.id=          -1;
            this.name=        "None";
            this.description= "None";
            this.instructions="None";
            this.type=        TYPE.CORE;

        }
    }
    public WorkoutAction(int id,String name,String description,String instructions,TYPE type){
        this.id=id;
        this.name=name;
        this.description=description;
        this.instructions=instructions;
        this.type=type;
    }

public JSONObject toJSON(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("id", id);
            obj.put("name", name);
            obj.put("desc", description);
            obj.put("inst", instructions);
            obj.put("type", type.name());
        }catch (Exception e){e.printStackTrace();}
        return obj;
}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
