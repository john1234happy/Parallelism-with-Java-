package org.example;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class SyncMap {

    public static File file = new File("C:\\Users\\john1\\Downloads\\SUNYOswegoCourses.txt");
    public static final  HashMap<String, List<Course>> HM = new HashMap<>(65);
    public static String st;
    public static BufferedReader br;
    public static List<String> Majors = new ArrayList<>();
    private static final ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
    private static final Lock r = lock1.readLock();
    private static final Lock w = lock1.writeLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void MakeNPopulate_hashmap() throws IOException {
        br = new BufferedReader(new FileReader(file));
        int list_count = 0;
        int count_of_Majors = 0;

        while ((st = br.readLine()) != null) {

            if (!HM.containsKey(st.substring(0, st.indexOf(" ")))) {
                List<Course> SLM = new ArrayList<>();
                Course tempCourse = new Course();
                tempCourse.name = st.substring(0,st.indexOf("-")-1);
                SLM.add(tempCourse);
                HM.put(st.substring(0,st.indexOf(" ")),SLM);
                Majors.add(st.substring(0,st.indexOf(" ")));

            } else if (HM.containsKey(st.substring(0, st.indexOf(" ")))) {
                Course tempCourse = new Course();
                tempCourse.name = st.substring(0, st.indexOf("-") - 1);
                if (!HM.get(st.substring(0, st.indexOf(" "))).contains(tempCourse)) {
                    HM.get(st.substring(0, st.indexOf(" "))).add(tempCourse);
                }
            }
            // Print the string
           // System.out.println("Class: " + st.substring(0, 7));
        }
        //ystem.out.println("List of Majors :");
        //print(Majors);
        //System.out.println("number of majors: " + count_of_Majors);
    }

    public static void print(List<String> MajorList ){
        for (Object o : MajorList) {
            System.out.println(o);
        }
    }
     public static String pickMajor(){ //make it a 3/4 chance that if the major has less than 6 classes the major get repicked.
        Random r = new Random();
        int rand1 = r.nextInt(Majors.size());
        String Major = "";
        Major = Majors.get(rand1);
        if(6 > HM.get(Majors.get(rand1)).size()){
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
        if(lock2.tryLock()){
            for(int i = 0; i < HM.size(); i++) {
                System.out.println(Majors.get(i));
                printRegisteredList(HM.get(Majors.get(i)));
            }
            lock2.unlock();
        }
     }

    private static void printRegisteredList(List<Course> list){
        for (Course course : list) {
            System.out.println("Course name : " + course.name + " Students registered : " + course.studentsEnrolled);
        }
    }

    public static List<Course> registerforClass(String Major,List<Course> listofclasses) {
        try {
            w.lock();
                for (int i = listofclasses.size(); i > 0; i--) {
                    HM.get(Major).remove(listofclasses.get(i - 1));
                    HM.get(Major).add(listofclasses.get(i - 1));
                }

            return listofclasses;
        }   finally{
            w.unlock();
        }
    }

    public static void printRegList(List<Course> list, String name, String Major){
        try {
            if (lock2.tryLock(60L, TimeUnit.MILLISECONDS)) {
                System.out.println("Student " + name + " has a "+ Major + " Major ");
                System.out.println("They are Registered for : ");

                for (Course course : list) {
                    System.out.println("Course name : " + course.name);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            lock2.unlock();
        }
    }

    public static List<Course> LookforClass(String Major){
        Random rand = new Random();
        List<Course> classes = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        try {
            r.lock();
                for (int i = 0; i < 4; i++) {
                    int r;
                    r = rand.nextInt(HM.get(Major).size());
                    if (!intList.contains(r)) {
                        intList.add(r);
                        classes.add(HM.get(Major).get(r));
                    } else if (intList.contains(r)) {
                        i--;
                    }
                }

        } finally { r.unlock(); }
        try {
            Thread.sleep(rand.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return classes;

    }

        public static void run() throws InterruptedException, IOException {
            MakeNPopulate_hashmap();

            List<Thread> threads = new ArrayList<Thread>();

            for (int l = 0; l < 3000; l++) {

                Runnable task = new StudentsSyncThread("student: " + l);
                Thread worker = new Thread(task);

                worker.setName(String.valueOf(l));

                threads.add(worker);
                worker.start();
            }
            printMap();
            System.out.println("done");
        }
}
