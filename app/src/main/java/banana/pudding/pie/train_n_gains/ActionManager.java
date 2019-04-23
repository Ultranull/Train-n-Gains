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


    public LinkedHashMap<Integer,WorkoutAction> actions;

    public ActionManager(){
        actions=new LinkedHashMap<>();

        actions.put(1,new WorkoutAction(1,"Push-Ups","A compound exercise to build upper body and core strength. Uses chest," +
                " shoulders, triceps, back, abs, and leg muscles. This is an easy beginner exercise.",
                "Position yourself on the ground with your hands flat on the floor, slightly wider than your shoulders. " +
                        "Extend your legs, your toes on the ground (not your whole legs). Lift your stomach and back up while contracting " +
                        "your abdomen. Lift until your arms are straight. To begin, slowly bend your elbows until they are at a 90 degree angle. Lift yourself back up while exhaling at the same time.",
                WorkoutAction.TYPE.ARMS));

        actions.put(2,new WorkoutAction(2,"Sit-Ups","An abdominal exercise that helps build core strengh as well as endurance. " +
                "This exercise is similar to a 'crunch', but sit-ups have a fuller range of motion.",
                "Lye flat on your back. Keep your feet flat on the floor with you knees bent in the air. " +
                        "Keep your arms flat by your side or crossed on your chest. Lift your torso to a sitting position, " +
                        "and slowly lye flat again. While doing this, do not change the position of your legs, to ensure good positioning.", WorkoutAction.TYPE.CORE));

        actions.put(3,new WorkoutAction(3,"Arm-Curls","Typically a free weight exercise. Arm-curls work your biceps by using a curling motion.",
                "Stand straight with a dumbbell in each hand. Keep your elbows close to your torso and rotate the palms of your hands until they face forward. " +
                        "While in position, inhale slowly and life the weights up to your chest without moving your elbows forward or thrusting your back. " +
                        "Exhale while lifting the weights.", WorkoutAction.TYPE.ARMS));

        actions.put(4,new WorkoutAction(4,"Squats","Squats work all parts of the legs, including hips, glutes, quads, hamstrings, and even core. " +
                "They also help improve balance and bone density.","Stand with your feet slightly wider than shoulder-width apart. " +
                "Roll your shoulders back and down away from your ears. If you are doing body weight, extend your arms straight out. " +
                "If you are doing bar squats, position the bar on the back of your neck, resting at the base of the neck. Position your hands an even distance from your shoulder to hand grip. " +
                "Inhale, and lower yourself until your hips sink below your knees, then lift yourself back up slowly. Make sure you keep your chest/shoulders/glutes outwards.", WorkoutAction.TYPE.LEGS));

        actions.put(5,new WorkoutAction(5,"Pull-Ups","A great exercise to build upper body, including chest, biceps, and back. The only tool you need for this exercise is a bar that will support your weight.",
                "Stand below the bar. Grip the bar slightly wider than your shoulders. Hang from the with your palms facing away from you." +
                        " Pull yourself up until your chin reaches the same height as the bar. Avoid swinging or kicking your feet as you pull. Once in this position, " +
                        "lower yourself back to the starting hanging position, and repeat.", WorkoutAction.TYPE.BACK));


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
