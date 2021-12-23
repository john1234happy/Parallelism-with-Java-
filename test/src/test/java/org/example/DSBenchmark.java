package org.example;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class DSBenchmark {

    @State(Scope.Benchmark)
    public static class MyState {

        public static File file = new File("C:\\Users\\john1\\Downloads\\SUNYOswegoCourses.txt");
        public static HashMap<String, List<Course>> HM = null;
        public static String st;
        public static BufferedReader sbr;
        public static BufferedReader cbr;
        public static List<String> Majors = new ArrayList<>();
        private static final ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
        private static final ReentrantLock lock = new ReentrantLock();
        public static ConcurrentHashMap<String, List<Course>> CHM = null;

        @Setup(Level.Trial)
        public void doSetup() throws IOException {
            HM = new HashMap<>(65);
                sbr = new BufferedReader(new FileReader(file));
                int list_count = 0;
                int count_of_Majors = 0;

                while ((st = sbr.readLine()) != null) {

                    if (!HM.containsKey(st.substring(0, st.indexOf(" ")))) {
                        List<Course> SLM = new ArrayList<>();
                        Course tempCourse = new Course();
                        tempCourse.name = st.substring(0,st.indexOf("-")-1);
                        SLM.add(tempCourse);
                        HM.put(st.substring(0,st.indexOf(" ")),SLM);
                        Majors.add(st.substring(0,st.indexOf(" ")));
                        count_of_Majors++;

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
                //System.out.println("List of Majors :");
                //print(Majors);
                //System.out.println("number of majors: " + count_of_Majors);
            CHM = new ConcurrentHashMap<>(65);
            cbr = new BufferedReader(new FileReader(file));

            while ((st = cbr.readLine()) != null) {

                if (!CHM.containsKey(st.substring(0, st.indexOf(" ")))) {
                    List<Course> SLM = new ArrayList<>();
                    Course tempCourse = new Course();
                    tempCourse.name = st.substring(0,st.indexOf("-")-1);
                    SLM.add(tempCourse);
                    CHM.put(st.substring(0,st.indexOf(" ")),SLM);

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


        @TearDown(Level.Trial)
        public void doTearDown() {
            HM = null;
            CHM = null;

            System.out.println("Do TearDown");
        }

        public static void printMap(){
            for(int i = 0; i < HM.size(); i++) {
                if(!HM.get(Majors.get(i)).contains(null)) {
                    printRegisteredList(HM.get(Majors.get(i)));
                }else{
                    System.out.println("conatins null : " + HM.get(Majors.get(i)).toString());
                }

            }
        }

        private static void printRegisteredList(List<Course> list){
            for (Course course : list) {
                System.out.println("Course name : " + course.name + " Students registered : " + course.studentsEnrolled);
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
        public static List<Course> SyncRegisterforClass(String Major,List<Course> listofclasses) {
            try {
                lock.lock();
                for (int i = listofclasses.size(); i > 0; i--) {
                    HM.get(Major).remove(listofclasses.get(i - 1));
                    HM.get(Major).add(listofclasses.get(i - 1));
                }

                return listofclasses;
            }   finally{
                lock.unlock();
            }
        }
        public static List<Course> ConnRegisterforClass(String Major,List<Course> listofclasses) {

            for (int i = listofclasses.size(); i > 0; i--) {
                CHM.get(Major).indexOf(listofclasses.get(i - 1));
                CHM.get(Major).set(CHM.get(Major).indexOf(listofclasses.get(i - 1)),listofclasses.get(i - 1));
            }
            return listofclasses;
        }

        public static List<Course> SyncLookforClass(String Major){
            Random rand = new Random();
            List<Course> classes = new ArrayList<>();
            List<Integer> intList = new ArrayList<>();
            try {
                lock.lock();
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

            } finally { lock.unlock(); }
            return classes;

        }

        public static List<Course> ConnLookforClass(String Major){
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
            // make a loop that find the null and replases it with a class or go up and fix it up there

            return classes;
        }
        private final Random random = new Random();
    }


    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 40)
    @Measurement(iterations = 5, time = 40)
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)

    public int SyncBenchmark1(MyState state, Blackhole blackhole) throws IOException {

       String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.SyncLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                    while (i < picked_classes.size()) {
                        picked_classes.get(i).studentsEnrolled++;
                        i++;
                    }

                List<Course> registered_classes = MyState.SyncRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 40)
    @Measurement(iterations = 5, time = 40)
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public int ConnBenchmark1(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.ConnLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }
                List<Course> registered_classes = MyState.ConnRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }

    /*@Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 20)
    @Measurement(iterations = 5, time = 20)
    @Threads(8)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)

    public int SyncBenchmark2(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.SyncLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }

                List<Course> registered_classes = MyState.SyncRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }
    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 20)
    @Measurement(iterations = 5, time = 20)
    @Threads(8)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public int ConnBenchmark2(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.ConnLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }
                List<Course> registered_classes = MyState.ConnRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 20)
    @Measurement(iterations = 5, time = 20)
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)

    public int SyncBenchmark3(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.SyncLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }

                List<Course> registered_classes = MyState.SyncRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }
    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 20)
    @Measurement(iterations = 5, time = 20)
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public int ConnBenchmark3(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.ConnLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }
                List<Course> registered_classes = MyState.ConnRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 20)
    @Measurement(iterations = 5, time = 20)
    @Threads(32)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)

    public int SyncBenchmark4(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.SyncLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }

                List<Course> registered_classes = MyState.SyncRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 20)
    @Measurement(iterations = 5, time = 20)
    @Threads(32)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public int ConnBenchmark4(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.ConnLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }
                List<Course> registered_classes = MyState.ConnRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 20)
    @Measurement(iterations = 5, time = 20)
    @Threads(64)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)

    public int SyncBenchmark5(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.SyncLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }

                List<Course> registered_classes = MyState.SyncRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5, time = 20)
    @Measurement(iterations = 5, time = 20)
    @Threads(64)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public int ConnBenchmark5(MyState state, Blackhole blackhole) throws IOException {

        String Major = MyState.pickMajor();
        List<Course> picked_classes = MyState.ConnLookforClass(Major);

        if(state.random.nextBoolean()){
            if(state.random.nextBoolean()){
                int i = 0;

                while (i < picked_classes.size()) {
                    picked_classes.get(i).studentsEnrolled++;
                    i++;
                }
                List<Course> registered_classes = MyState.ConnRegisterforClass(Major, picked_classes);
                blackhole.consume(registered_classes);
            }
        }

        return 1;
    }*/

}
