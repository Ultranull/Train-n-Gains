package banana.pudding.pie.train_n_gains;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ActionManager {

    /*  database scheme
        id | name     | descr | ...
        01 | push ups | ..... | ...
     */

    private LinkedHashMap<Integer,WorkoutAction> actions;

    public ActionManager(){
        actions=new LinkedHashMap<>();
        actions.put(1,new WorkoutAction(1,"push-ups","sample","dance", WorkoutAction.TYPE.ARMS));
        actions.put(2,new WorkoutAction(2,"sit-ups","foobar","dance", WorkoutAction.TYPE.CORE));
        actions.put(3,new WorkoutAction(3,"arm-curls","fizzbuzz","dance", WorkoutAction.TYPE.ARMS));
    }
    public ArrayList<WorkoutAction> getActions(){
        return new ArrayList<>(actions.values());
    }

    public WorkoutAction getAction(int id){
        return actions.get(id);
    }

    public void load(String file){}
    public void save(String file){}


}
