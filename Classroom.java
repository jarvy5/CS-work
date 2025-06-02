package kindergarten;

/**
 * You're a kindergarten teacher and you have many students to teach.
 * You'll supervise students when they line up, when they're in class,
 * and when they're playing musical chairs!
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine; // when students are in line - refers to the front of a singly linked list
    private SNode musicalChairs; // when students are in musical chairs: holds a reference to the LAST student in
                                 // the CLL
    private boolean[][] openSeats; // represents seat positions in the classroom
    private Student[][] studentsInSeats; // represents students in their corresponding seats

    /**
     * Constructor for classrooms. Do not edit.
     * 
     * @param inLine          passes in students in line
     * @param mChairs         passes in musical chairs
     * @param seats           passes in availability
     * @param studentsSitting passes in students sitting
     */
    public Classroom(SNode inLine, SNode mChairs, boolean[][] seats, Student[][] studentsSitting) {
        studentsInLine = inLine;
        musicalChairs = mChairs;
        openSeats = seats;
        studentsInSeats = studentsSitting;
    }

    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students filing in line, before they enter the
     * classroom.
     * 
     * It does this by reading students from an input file and adding each to the
     * front
     * of the studentsInLine linked list.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. For each line of the input file:
     * 1. read a student from the file
     * 2. create a Student object with the student's info
     * 3. insert the Student to the FRONT of studentsInLine
     * 
     * Students are stored in reverse alphabetical order in the input file.
     * This method will put students in A-Z order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to front.
     * 
     * @param filename the student input file
     */
    public void enterClassroom(String filename) {
        StdIn.setFile(filename);
        int students = StdIn.readInt();

        for(int i = 0; i<students; i++){
           String first = StdIn.readString();
           String last = StdIn.readString();
           int height = StdIn.readInt();

           Student s = new Student(first, last, height);

           SNode hold = new SNode(s, null);
           
           //hold.setNext(studentsInLine);

           if(studentsInLine==null){
            studentsInLine = hold;
           } else{
            hold.setNext(studentsInLine);
            studentsInLine = hold;
           }

        }

        // WRITE YOUR CODE HERE
    }

    /**
     * 
     * This method creates the open seats in the classroom.
     * 
     * 1. Open the file using StdIn.setFile(seatingChart);
     * 
     * 2. You will read the seating input file with the format:
     * An integer representing the number of rows in the classroom
     * An integer representing the number of columns in the classroom
     * Number of r lines, each containing c true or false values (true represents
     * that a
     * seat is present in that column)
     * 
     * 3. Initialize openSeats and studentsInSeats arrays with r rows and c columns
     * 
     * 4. Update studentsInSeats with the booleans read from the input file
     * 
     * This method does not seat students in the seats.
     * 
     * @param openSeatsFile the seating chart input file
     */
    public void createSeats(String openSeatsFile) {
        StdIn.setFile(openSeatsFile);
        int row = StdIn.readInt();
        int col = StdIn.readInt();

        
        this.studentsInSeats = new Student[row][col];
        this.openSeats = new boolean[row][col];
        boolean seat;

        for (int i = 0; i<row;i++){
            for (int j = 0; j<col; j++){
                seat = StdIn.readBoolean();
                openSeats[i][j] = seat;
                studentsInSeats[i][j] = null;
            }
        }

        // WRITE YOUR CODE HERE

    }

    /**
     * 
     * This method simulates students moving from the line to their respective
     * seats.
     * 
     * Students are removed one by one from the line and inserted into
     * studentsInSeats
     * according to the seating positions in openSeats.
     * 
     * studentsInLine will then be empty at the end of this method.
     * 
     * NOTE: If the students just played musical chairs, the winner of musical
     * chairs is seated separately
     * by seatMusicalChairsWinner().
     */
    public void seatStudents() {


        while(studentsInLine!= null){

        for (int i =0; i< studentsInSeats.length; i++){
            for (int j=0; j<studentsInSeats[i].length; j++){
                if (openSeats[i][j] == true){
                    studentsInSeats[i][j] = studentsInLine.getStudent();
                    studentsInLine = studentsInLine.getNext();
                }

                if (studentsInLine == null){
                    return;
                }
            }
        }
        
    }

        // WRITE YOUR CODE HERE

    }

    /**
     * Traverses studentsInSeats row by row, column by column removing a student
     * from their seat and adding them to the END of the musical chairs CLL.
     * 
     * NOTE: musicalChairs refers to the LAST student in the CLL.
     */
    public void insertMusicalChairs() {

        //SNode ptr = new SNode();
        Student s = new Student();
        

        for(int i= 0; i<studentsInSeats.length; i++){
            for(int j=0; j<studentsInSeats[i].length;j++){

                s = studentsInSeats[i][j];
                SNode hold_student = new SNode(s, null);
                
                if( studentsInSeats[i][j] !=null){
                    if(musicalChairs == null){
                        hold_student.setNext(hold_student);
                        musicalChairs = hold_student;
                       
                    }
                    else{
                        SNode temp = musicalChairs.getNext();
                        musicalChairs.setNext(hold_student);
                        hold_student.setNext(temp);
                        musicalChairs = hold_student;
                    }

                    studentsInSeats[i][j] = null;
                
                }
            }
        }
        // WRITE YOUR CODE HERE

    }

    /**
     * 
     * Removes a random student from the musicalChairs, if they can't find a seat.
     * Once eliminated students go back to the line.
     * 
     * @param size represents the number of students currently sitting in
     *             musicalChairs
     * 
     *             1. Use StdRandom.uniform(size) to pick the nth student (n=0 =
     *             student at front).
     *             2. Search for the selected student, and delete them from
     *             musicalChairs
     *             3. Call insertByName to insert the deleted student back to the
     *             line.
     * 
     *             The random value denotes the refers to the position of the
     *             student to be removed
     *             in the musicalChairs. 0 is the first student
     */
    public void moveStudentFromChairsToLine(int size) {

        if(musicalChairs == null){
            return;
        }

        int x = StdRandom.uniform(size);
        SNode ptr = new SNode();
        Student stu = new Student();

        if(x ==0){
           stu = musicalChairs.getNext().getStudent();

           if(musicalChairs.getNext() == musicalChairs){
            musicalChairs = null;
           } 
           else{
            SNode next = musicalChairs.getNext().getNext();
           musicalChairs.setNext(next);
            }

        } else {

            ptr = musicalChairs.getNext();

            for(int i=0; i<x-1; i++){
            ptr = ptr.getNext();
            }

            stu = ptr.getNext().getStudent();

            if(ptr.getNext() == musicalChairs.getNext()){
               SNode front = musicalChairs.getNext().getNext();
               musicalChairs.setNext(front);
            } 
            else if(ptr.getNext() == musicalChairs){
                SNode next = ptr.getNext().getNext();
                ptr.setNext(next);
                musicalChairs = ptr;
            } else {
                SNode next = ptr.getNext().getNext();
                ptr.setNext(next);
            }
            
        }

        insertByName(stu);
        
        
        // WRITE YOUR CODE HERE

    }

    /**
     * Inserts a single student, eliminated from musical chairs, to the line
     * inserted
     * in ascending order by last name and then first name if students have the same
     * last name
     * 
     * USE compareNameTo on a student: < 0 = less than, > 0 greater than, = 0 equal
     * 
     * @param eliminatedStudent the student eliminated from chairs to insert
     */
    public void insertByName(Student eliminatedStudent) {

        SNode ptr = new SNode();
        SNode prev = new SNode();
        SNode student = new SNode(eliminatedStudent, null);
        ptr = studentsInLine;

        if(studentsInLine== null){
            studentsInLine = student;
            return;
        }

        int num = eliminatedStudent.compareNameTo(ptr.getStudent());
        if (num < 0){
            student.setNext(studentsInLine);
            studentsInLine = student;
            return;
        }

        while (ptr != null && num > 0){
            prev = ptr;
            ptr = ptr.getNext();

            if(ptr != null){
                num = eliminatedStudent.compareNameTo(ptr.getStudent());
            }
            
            
        }

        prev.setNext(student);
        student.setNext(ptr);
        


        // WRITE YOUR CODE HERE
    }

    /**
     * 
     * Removes ALL eliminated students from musical chairs and inserts those
     * students
     * back in studentsInLine in ascending name order (earliest to latest).
     * 
     * At the end of this method, all students will be in studentsInLine besides
     * the winner, who will only be in musicalChairs.
     * 
     * 1. Find the number of students currently in musicalChairs
     * 2. While there's more than 1 student in musicalChairs, call
     * moveRandomStudentFromChairsToLine()
     * --> pass the size from step (1) into the method call.
     */
    public void eliminateLosingStudents() {
        SNode ptr = new SNode();
        int size = 1;
        ptr = musicalChairs;
        
        while (ptr.getNext()!= musicalChairs){
            ptr = ptr.getNext();
            size++;
        }

        while (musicalChairs.getNext() != musicalChairs){
            moveStudentFromChairsToLine(size);
            size--;
        }

        // WRITE YOUR CODE HERE

    }

    /*
     * If musicalChairs (CLL) contains ONLY ONE student (the winner),
     * this method removes the winner from musicalChairs and inserts that student
     * into the first available seat in studentsInSeats. musicalChairs will then be
     * empty.
     * 
     * ASSUME eliminateLosingStudents is called before this method is.
     * 
     * NOTE: This method does nothing if there is more than one student in
     * musicalChairs
     * OR if musicalChairs is empty.
     */
    public void seatMusicalChairsWinner() {
       
        Student su = new Student();

        if(musicalChairs == null){
            return;
        }

        if(musicalChairs.getNext() == musicalChairs){
            su = musicalChairs.getStudent();

            musicalChairs.setNext(null);
            musicalChairs = null;

            for(int i=0; i< openSeats.length; i++){
                for(int j=0; j<openSeats[i].length;j++){
                    if (openSeats[i][j] == true){
                        openSeats[i][j] = false;
                        studentsInSeats[i][j] = su;
                        return;
                    }
                }
            }

        } else {
            return;
        }

        // WRITE YOUR CODE HERE

    }

    /**
     * 
     * This method simulates the game of Msical Chairs!
     * 
     * This method calls previously-written methods to remove students from
     * musicalChairs until there is only one student (the winner), then seats the
     * winner
     * and then seat the students from the studentsInline.
     * 
     * DO NOT UPDATE THIS METHOD
     */
    public void playMusicalChairs() {
        eliminateLosingStudents();
        seatMusicalChairsWinner();
        seatStudents();
    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine() {

        // Print studentsInLine
        StdOut.println("Students in Line:");
        if (studentsInLine == null) {
            StdOut.println("EMPTY");
        }

        for (SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print());
            if (ptr.getNext() != null) {
                StdOut.print(" -> ");
            }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents() {

        StdOut.println("Sitting Students:");

        if (studentsInSeats != null) {

            for (int i = 0; i < studentsInSeats.length; i++) {
                for (int j = 0; j < studentsInSeats[i].length; j++) {

                    String stringToPrint = "";
                    if (studentsInSeats[i][j] == null) {

                        if (openSeats[i][j] == false) {
                            stringToPrint = "X";
                        } else {
                            stringToPrint = "EMPTY";
                        }

                    } else {
                        stringToPrint = studentsInSeats[i][j].print();
                    }

                    StdOut.print(stringToPrint);

                    for (int o = 0; o < (10 - stringToPrint.length()); o++) {
                        StdOut.print(" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs() {
        StdOut.println("Students in Musical Chairs:");

        if (musicalChairs == null) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for (ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if (ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() {
        return studentsInLine;
    }

    public void setStudentsInLine(SNode l) {
        studentsInLine = l;
    }

    public SNode getMusicalChairs() {
        return musicalChairs;
    }

    public void setMusicalChairs(SNode m) {
        musicalChairs = m;
    }

    public boolean[][] getOpenSeats() {
        return openSeats;
    }

    public void setOpenSeats(boolean[][] a) {
        openSeats = a;
    }

    public Student[][] getStudentsInSeats() {
        return studentsInSeats;
    }

    public void setStudentsInSeats(Student[][] s) {
        studentsInSeats = s;
    }

}
