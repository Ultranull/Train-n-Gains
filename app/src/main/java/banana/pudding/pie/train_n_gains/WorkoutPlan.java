package banana.pudding.pie.train_n_gains;

import java.util.ArrayList;

public class WorkoutPlan {
    private String name;
    private String description;

    ArrayList<WorkoutAction> workouts;

    public WorkoutPlan(){
        this("None","None");
    }
    public WorkoutPlan(String name,String description){
        this.name=name;
        this.description=description;
        workouts=new ArrayList<>();
    }


    public void addWorkout(WorkoutAction workout){
        workouts.add(workout);
    }
    public void removeWorkout(int index){
        workouts.remove(index);
    }

    public void moveUp(int index){
        WorkoutAction val=workouts.remove(index);
        workouts.add(index-1,val);
    }

    public void moveDown(int index){
        WorkoutAction val=workouts.remove(index);
        workouts.add(index+1,val);
    }

    public ArrayList<WorkoutAction> getWorkouts() {
        return workouts;
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
}
