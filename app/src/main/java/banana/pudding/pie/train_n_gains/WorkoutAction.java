package banana.pudding.pie.train_n_gains;



public class WorkoutAction {



    public enum TYPE{
        ARMS,
        LEGS,
        CORE,
        BACK,
        OTHER
    }

    private int id;
    private String name;
    private final String description;
    private final String instructions;
    private final TYPE type;


     WorkoutAction(){
        this(-1,"None","None","None",TYPE.CORE);
    }

     WorkoutAction(int id,String name,String description,String instructions,TYPE type){
        this.id=id;
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

     String getDescription() {
        return description;
    }


     String getInstructions() {
        return instructions;
    }


     TYPE getType() {
        return type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
