package banana.pudding.pie.train_n_gains;


import java.util.ArrayList;

public class WorkoutPlan {
    private String name;
    private String description;

    private ArrayList<WorkoutAction> workouts;

     WorkoutPlan(){
        this("None","None");
    }
    private WorkoutPlan(String name,String description){
        this.name=name;
        this.description=description;
        workouts=new ArrayList<>();
    }


     void addWorkout(WorkoutAction workout){
        workouts.add(workout);
    }
     void removeWorkout(int index){
        workouts.remove(index);
    }

     void moveUp(int index){
        if(workouts.size()>1 && index-1>=0) {
            WorkoutAction val = workouts.remove(index);
            workouts.add(index - 1, val);
        }
    }

     void moveDown(int index){
        if(workouts.size()>1 && index+1<workouts.size()) {
            WorkoutAction val=workouts.remove(index);
            workouts.add(index+1,val);
        }
    }

     ArrayList<WorkoutAction> getWorkouts() {
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
