package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurentMap {

    public static File file = new File("C:\\Users\\john1\\Downloads\\SUNYOswegoCourses.txt");
    public static final ConcurrentHashMap<String, List<Course>>  CHM = new ConcurrentHashMap<>(65);
    public static String st;
    public static BufferedReader br;
    public static List<String> Majors = new ArrayList<>();

    public static void MakeNPopulate_hashmap() throws IOException {
        br = new BufferedReader(new FileReader(file));

        while ((st = br.readLine()) != null) {

            if (!CHM.containsKey(st.substring(0, st.indexOf(" ")))) {
                List<Course> SLM = new ArrayList<>();
                Course tempCourse = new Course();
                tempCourse.name = st.substring(0,st.indexOf("-")-1);
                SLM.add(tempCourse);
                CHM.put(st.substring(0,st.indexOf(" ")),SLM);
                Majors.add(st.substring(0,st.indexOf(" ")));

            } else if (CHM.containsKey(st.substring(0, st.indexOf(" ")))) {
                Course tempCourse = new Course();
                tempCourse.name = st.substring(0, st.indexOf("-") - 1);
                if (!CHM.get(st.substring(0, st.indexOf(" "))).contains(tempCourse)) {
                    CHM.get(st.substring(0, st.indexOf(" "))).add(tempCourse);
                }
            }
            // Print the string
            // System.out.println("Class: " + st.substring(0, 7));
        }
        //System.out.println("List of Majors :");
        //print(Majors);
        //System.out.println("number of majors: " + count_of_Majors);
    }

    public static void print(List<String> MajorList ){
        for (Object o : MajorList) {
            System.out.println(o);
        }
    }
    public static String pickMajor(){
        Random r = new Random();
        int rand1 = r.nextInt(Majors.size());
        String Major = "";
        Major = Majors.get(rand1);
        if(6 > CHM.get(Majors.get(rand1)).size()){
            int rand2 = r.nextInt(4);
            if(rand2 < 3){
                Major = Majors.get(r.nextInt(Majors.size()));
            }else{
                Major = Majors.get(rand1);
            }
        }
        return Major;
    }

    public static void printMap(){
        for(int i = 0; i < CHM.size(); i++) {
            System.out.println(Majors.get(i));

            if(!CHM.get(Majors.get(i)).contains(null)) {
                printRegisteredList(CHM.get(Majors.get(i)));
            }else{
                System.out.println("conatins null : " + CHM.get(Majors.get(i)).toString());
            }

        }
    }

    private static void printRegisteredList(List<Course> list){
        for (Course course : list) {
            System.out.println("Course name : " + course.name + " Students registered : " + course.studentsEnrolled);
        }
    }

/*    public static List<Course> registerforClass(String Major,List<Course> listofclasses) {

            for (int i = listofclasses.size(); i > 0; i--) {
                CHM.get(Major).indexOf(listofclasses.get(i - 1));
                CHM.get(Major).set(CHM.get(Major).indexOf(listofclasses.get(i - 1)),listofclasses.get(i - 1));
            }
            return listofclasses;
    }*/

    public static void registerforClass(String Major,List<Course> listofclasses) {

        for (int i = listofclasses.size(); i > 0; i--) {
            CHM.get(Major).indexOf(listofclasses.get(i - 1));
            CHM.get(Major).set(CHM.get(Major).indexOf(listofclasses.get(i - 1)),listofclasses.get(i - 1));
        }

    }

    public static void printRegList(List<Course> list, String name, String Major){
                System.out.println("Student " + name + " has a "+ Major + " Major ");
                System.out.println("They are Registered for : ");

                for (Course course : list) {
                    System.out.println("Course name : " + course.name);
                }
            }

    public static List<Course> LookforClass(String Major){
        Random rand = new Random();
        List<Course> classes = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                int r;
                r = rand.nextInt(CHM.get(Major).size());
                if (!intList.contains(r) && CHM.get(Major).get(r) != null) {
                    intList.add(r);
                    classes.add(CHM.get(Major).get(r));// in here make it check so that all the spots in classes are filled
                } else if (intList.contains(r)) {
                    i--;
                }
            }
        try {
            Thread.sleep(rand.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // make a loop that find the null and replases it with a class or go up and fix it up there

        return classes;
    }

    public static void run() throws InterruptedException, IOException {
        MakeNPopulate_hashmap();

        List<Thread> threads = new ArrayList<Thread>();

        for (int l = 0; l < 3000; l++) {

            Runnable task = new StudentsConcurentThread("student: "+l);
            Thread worker = new Thread(task);

            worker.setName(String.valueOf(l));

            threads.add(worker);
            worker.start();
        }
        printMap();
        System.out.println("Done");
    }
}

