package org.example;

import java.util.List;
import java.util.Random;

class StudentsSyncThread implements Runnable{
    private final Random random = new Random();
    String Major = " ";
    String name = " ";

    public StudentsSyncThread(String name) {
        this.name = name;
    }

    /*public static List<Course> updateClassStCount(List<Course> list){
        int i = 0;
        while(i < list.size()){
            list.get(i).studentsEnrolled++;
            i++;
        }
        return list;
    }

    private String printpickedlist(List<Course> pickedlist){
        StringBuilder st = new StringBuilder();
        for(int i = 0; i < pickedlist.size(); i++){
            st.append(",").append(pickedlist.get(i).name);
        }
        return st.toString();
    }*/

    public void run(){

        Major = SyncMap.pickMajor();
        List<Course> picked_classes = SyncMap.LookforClass(Major);

        if(random.nextBoolean()){
            if(random.nextBoolean()){
            int i = 0;
            try {
                while (i < picked_classes.size()) {
                    //if(picked_classes.get(i)!=null)
                    picked_classes.get(i).studentsEnrolled++;

                    i++;
                }
            } catch (Exception e) {
                System.out.println("thread name : " + this.name + " picked_classes : " + picked_classes + " i : " + i);
                e.printStackTrace();
            }


        //System.out.println("thread :"+this.name+" looked for classes");
        List<Course> registered_classes = SyncMap.registerforClass(Major, picked_classes);
        //System.out.println("thread :"+this.name+" is registered");
        //SyncMap.printRegList(registered_classes,this.name,this.Major);
    }
}
    }

}
