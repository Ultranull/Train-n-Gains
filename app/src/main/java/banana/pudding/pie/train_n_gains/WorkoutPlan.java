package banana.pudding.pie.train_n_gains;

import java.util.ArrayList;

public class WorkoutPlan {
    private String name;
    private String description;

    ArrayList<Integer> workouts;

    public WorkoutPlan(){
        this("None","None");
    }
    public WorkoutPlan(String name,String description){
        this.name=name;
        this.description=description;
        workouts=new ArrayList<>();
    }


    public void addWorkout(int workout){
        workouts.add(workout);
    }
    public void removeWorkout(int index){
        workouts.remove(index);
    }

    public void moveUp(int index){
        int val=workouts.remove(index);
        workouts.add(index-1,val);
    }

    public void moveDown(int index){
        int val=workouts.remove(index);
        workouts.add(index+1,val);
    }

    public ArrayList<Integer> getWorkouts() {
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
