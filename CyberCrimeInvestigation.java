package investigation;

import java.util.ArrayList; 

/*  
 * This class represents a cyber crime investigation.  It contains a directory of hackers, which is a resizing
 * hash table. The hash table is an array of HNode objects, which are linked lists of Hacker objects.  
 * 
 * The class contains methods to add a hacker to the directory, remove a hacker from the directory.
 * You will implement these methods, to create and use the HashTable, as well as analyze the data in the directory.
 * 
 * @author Colin Sullivan
 */
public class CyberCrimeInvestigation {
       
    private HNode[] hackerDirectory;
    private int numHackers = 0; 

    public CyberCrimeInvestigation() {
        hackerDirectory = new HNode[10];
    }

    /**
     * Initializes the hacker directory from a file input.
     * @param inputFile
     */
    public void initializeTable(String inputFile) { 
        // DO NOT EDIT
        StdIn.setFile(inputFile);  
        while(!StdIn.isEmpty()){
            addHacker(readSingleHacker());
        }
    }

    /**
     * Reads a single hackers data from the already set file,
     * Then returns a Hacker object with the data, including 
     * the incident data.
     * 
     * StdIn.setFile() has already been called for you.
     * 
     * @param inputFile The name of the file to read hacker data from.
     */
     public Hacker readSingleHacker(){ 

        String name = StdIn.readLine();
        String IP = StdIn.readLine();
        String location = StdIn.readLine();
        String OS = StdIn.readLine();
        String Web_Server = StdIn.readLine();
        String date = StdIn.readLine();
        String URL = StdIn.readLine();

        Incident object = new Incident(OS, Web_Server, date, location, IP, URL);

        Hacker hack = new Hacker(name);
        hack.addIncident(object);

        return hack;
        // WRITE YOUR CODE HERE
        
        // Replace this line
    }

    /**
     * Adds a hacker to the directory.  If the hacker already exists in the directory,
     * instead adds the given Hacker's incidents to the existing Hacker's incidents.
     * 
     * After a new insertion (NOT if  hacker already exists), checks if the number of 
     * hackers in the table is >= table length divided by 2. If so, calls resize()
     * 
     * @param toAdd
     */
    public void addHacker(Hacker toAdd) {
        int index = toAdd.hashCode() % hackerDirectory.length;

        HNode see = hackerDirectory[index];
        HNode adding = new HNode(toAdd);

        if(see == null){
            
            hackerDirectory[index] = adding;
            numHackers++;
            
            if(numHackers >= hackerDirectory.length /2){
                resize();
            }
        } else {

            HNode prev = see;
            
            boolean check = false;
            while(see != null){
                if (see.getHacker().getName().equals(toAdd.getName())){
                    see.getHacker().getIncidents().addAll( toAdd.getIncidents());
                    check = true;
                }
                prev = see;
                see = see.getNext();
                
            }


            if(!check){
                
                prev.setNext(adding);
                numHackers++;

                if(numHackers >= hackerDirectory.length /2){
                    resize();
                }
            }

        }

        


        // WRITE YOUR CODE HERE

    }

    /**
     * Resizes the hacker directory to double its current size.  Rehashes all hackers
     * into the new doubled directory.
     */
    private void resize() {
        HNode[] temp = hackerDirectory; 
        numHackers = 0;
        HNode[] n = new HNode[this.hackerDirectory.length *2];
        this.hackerDirectory = n;

        for (int i= 0; i<temp.length; i++){
            HNode curr = temp[i];
            while(curr != null){
                addHacker(curr.getHacker());
                curr = curr.getNext();
            }
        }
        // WRITE YOUR CODE HERE 
        
    }

    /**
     * Searches the hacker directory for a hacker with the given name.
     * Returns null if the Hacker is not found
     * 
     * @param toSearch
     * @return The hacker object if found, null otherwise.
     */
    public Hacker search(String toSearch) {
        int actualIndex = Math.abs(toSearch.hashCode()) % hackerDirectory.length;

        HNode hacker = hackerDirectory[actualIndex];

        if(hacker == null){
            return null;
        } else{

            while(hacker != null){
                if(hacker.getHacker().getName().equals(toSearch)){
                    return hacker.getHacker();
                }
                hacker = hacker.getNext();
            }
            
            
        }
        return null;
    }

    /**
     * Removes a hacker from the directory.  Returns the removed hacker object.
     * If the hacker is not found, returns null.
     * 
     * @param toRemove
     * @return The removed hacker object, or null if not found.
     */
    public Hacker remove(String toRemove) {
        // WRITE YOUR CODE HERE
        Hacker remo = search(toRemove);
        
        if(remo == null){
            return null;
        } else {
            int Index = Math.abs(toRemove.hashCode()) % hackerDirectory.length;
            HNode ptr = hackerDirectory[Index];
            

            if(Index == 0 || Index == hackerDirectory.length-1){
                hackerDirectory[Index] = null;
                Hacker temp = ptr.getHacker();
                ptr = null;
                numHackers--;
                return temp;
                
            }

            HNode prev = null;

            while (ptr != null){
                
                
                    if(ptr.getHacker().getName().equals(toRemove)){
                       
                        Hacker temp = ptr.getHacker();
                        if(prev == null){
                            hackerDirectory[Index] = ptr.getNext();
                        } else {
                            prev.setNext(ptr.getNext());
                        }
                        
        
        
                        numHackers--;
                        return temp;
                        
                        
                    }
                
                prev = ptr;
                ptr = ptr.getNext();
            }










            // while(ptr != null){

            //     if(ptr.getHacker().getName().equals(toRemove)){

            //         hackerDirectory[Index] = null;
            //         Hacker temp = ptr.getHacker();
            //         if(prev == null){
                        
            //             ptr = null;
            //             numHackers--;
            //             return temp;
                        
            //         } else {
            //             prev.setNext(ptr.getNext());
            //             numHackers--;
            //             return temp;
            //         }
                    
                    
            //     }
            //     prev = ptr;
            //     ptr = ptr.getNext();
            // }


        }
        return null;
    } 

    /**
     * Merges two hackers into one based on number of incidents.
     * 
     * @param hacker1 One hacker
     * @param hacker2 Another hacker to attempt merging with
     * @return True if the merge was successful, false otherwise.
     */
    public boolean mergeHackers(String hacker1, String hacker2) {  

        Hacker one = search(hacker1);
        Hacker two = search(hacker2);

        if(one == null || two == null){
            return false;
        } else {
            if(one.numIncidents()<two.numIncidents()){
                //Hacker one merge with hacker two
                ArrayList <Incident> inc = one.getIncidents();
                for(int i =0; i<one.numIncidents(); i++){
                    two.addIncident(inc.get(i));
                }

                two.addAlias(hacker1);

                // ArrayList <String> list = two.getAliases();
                // for(int i = 0; i<list.size(); i++){
                //     two.addAlias(list.get(i));
                // }

                
                remove(hacker1);
                return true;
            } else if(two.numIncidents()<one.numIncidents() || one.numIncidents() == two.numIncidents()){
                //Hacker two merge with hacker one
                ArrayList <Incident> inc = two.getIncidents();
                for(int i =0; i<two.numIncidents(); i++){
                    one.addIncident(inc.get(i));
                }
                
                // ArrayList <String> list = two.getAliases();
                // for(int i = 0; i<list.size(); i++){
                //     one.addAlias(list.get(i));
                // }

                one.addAlias(hacker2);
                remove(hacker2);
                return true;
            } 
        }

        // WRITE YOUR CODE HERE 
        // Replace this line

        return false;

    }

    /**
     * Gets the top n most wanted Hackers from the directory, and
     * returns them in an arraylist. 
     * 
     * You should use the provided MaxPQ class to do this. You can
     * add all hackers, then delMax() n times, to get the top n hackers.
     * 
     * @param n
     * @return Arraylist containing top n hackers
     */
    public ArrayList<Hacker> getNMostWanted(int n) {

        MaxPQ<Hacker> queue = new MaxPQ<>();

        for(int i=0; i<hackerDirectory.length; i++ ){
            
            if(hackerDirectory[i] != null){
                
                HNode ptr  = hackerDirectory[i];
                while(ptr != null){
                    queue.insert(ptr.getHacker());
                    ptr = ptr.getNext();
                }
                
            }

        }

        ArrayList<Hacker> answer = new ArrayList<>(n);

        for(int i= 0; i<n; i++){
            answer.add(queue.delMax());
        }
        
        // WRITE YOUR CODE HERE 

        return answer; // Replace this line
    }

    /**
     * Gets all hackers that have been involved in incidents at the given location.
     * 
     * You should check all hackers, and ALL of each hackers incidents.
     * You should not add a single hacker more than once.
     * 
     * @param location
     * @return Arraylist containing all hackers who have been involved in incidents at the given location.
     */
    public ArrayList<Hacker> getHackersByLocation(String location) {

        ArrayList<Hacker> answer = new ArrayList<>();

        for(int i=0; i<this.hackerDirectory.length; i++){
            HNode hack = hackerDirectory[i];

            while (hack != null){
                ArrayList<Incident> inc= hack.getHacker().getIncidents();
                
                for(int j=0; j<inc.size(); j++){
                    if(inc.get(j).getLocation().equals(location)){
                        if(!answer.contains(hack.getHacker())){
                            answer.add(hack.getHacker());
                        }
                        
                    }
                }

                hack = hack.getNext();
            }
        }
        // WRITE YOUR CODE HERE 

        return answer; // Replace this line
    }
  

    /**
     * PROVIDED--DO NOT MODIFY!
     * Outputs the entire hacker directory to the terminal. 
     */
     public void printHackerDirectory() { 
        System.out.println(toString());
    } 

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.hackerDirectory.length; i++) {
            HNode headHackerNode = hackerDirectory[i];
            while (headHackerNode != null) {
                if (headHackerNode.getHacker() != null) {
                    sb.append(headHackerNode.getHacker().toString()).append("\n");
                    ArrayList<Incident> incidents = headHackerNode.getHacker().getIncidents();
                    for (Incident incident : incidents) {
                        sb.append("\t" +incident.toString()).append("\n");
                    }
                }
                headHackerNode = headHackerNode.getNext();
            } 
        }
        return sb.toString();
    }

    public HNode[] getHackerDirectory() {
        return hackerDirectory;
    }
}
