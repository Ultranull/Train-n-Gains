package banana.pudding.pie.train_n_gains;

import android.media.Image;

public class WorkoutAction {


    public static WorkoutAction PUSHUPS=new WorkoutAction("Push-ups","do push-ups","plank then lift yourself",TYPE.ARMS);


    public enum TYPE{
        ARMS,
        LEGS,
        CORE,
        BACK
    }

    private String name;
    private String description;
    private String instructions;
    private TYPE type;

    private Image image;

    public WorkoutAction(){
        this("None","None","None",TYPE.CORE);
    }

    public WorkoutAction(String name,String description,String instructions,TYPE type){
        this.name=name;
        this.description=description;
        this.instructions=instructions;
        this.type=type;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
