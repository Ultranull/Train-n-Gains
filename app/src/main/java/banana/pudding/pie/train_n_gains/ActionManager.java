package banana.pudding.pie.train_n_gains;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class ActionManager {

    /*  database scheme
        id | name     | descr | ...
        01 | push ups | ..... | ...
     */

    private LinkedHashMap<Integer,WorkoutAction> actions;

    public ActionManager(){
        actions=new LinkedHashMap<>();
        actions.put(1,new WorkoutAction(1,"Push-Ups","sample","do push-ups", WorkoutAction.TYPE.ARMS));
        actions.put(2,new WorkoutAction(2,"Sit-Ups","foobar","do sit-ups", WorkoutAction.TYPE.CORE));
        actions.put(3,new WorkoutAction(3,"Arm-Curls","fizzbuzz","do arm-curls", WorkoutAction.TYPE.ARMS));
        actions.put(4,new WorkoutAction(4,"Squats","fizzbuzz","do squats", WorkoutAction.TYPE.LEGS));
        actions.put(5,new WorkoutAction(5,"Pull-Ups","fizzbuzz","do pull-ups", WorkoutAction.TYPE.BACK));


    }
    public ArrayList<WorkoutAction> getActions(){
        return new ArrayList<>(actions.values());
    }

    public WorkoutAction getAction(int id){
        return actions.get(id);
    }

    public void load(String contents){
        try {
            JSONArray jsonArray = new JSONArray(contents);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject obj=jsonArray.getJSONObject(i);
                int id=obj.getInt("id");
                actions.put(id,new WorkoutAction(obj));
            }
        }catch (Exception e){e.printStackTrace();}
    }
    public String save(){
        JSONArray jsonArray = new JSONArray();
        Iterator<WorkoutAction> iter=actions.values().iterator();
        try {
            while (iter.hasNext()){
                JSONObject obj=iter.next().toJSON();
                jsonArray.put(obj);
            }
        }catch (Exception e){e.printStackTrace();}
        return jsonArray.toString();
    }


}
