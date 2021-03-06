package banana.pudding.pie.train_n_gains;


import java.util.ArrayList;

class WorkoutPlan {
    private String name;

    private final ArrayList<WorkoutAction> workouts;

     WorkoutPlan(){
        this("None");
    }
    private WorkoutPlan(String name){
        this.name=name;
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

}
