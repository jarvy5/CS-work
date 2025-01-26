/**
 * Class represents solar panels, street map, and
 * an array of parking lot projects.
 * 
 * @author Jessica De Brito
 * @author Kal Pandit
 */
public class SolarPanels {
    
    private Panel[][] panels;
    private String[][] streetMap;
    private ParkingLot[] lots;

    /**
     * Default constructor: initializes empty panels and objects.
     */
    public SolarPanels() {
        panels = null;
        streetMap = null;
        lots = null;
        StdRandom.setSeed(2023);
    }

    /**
     * Updates the instance variable streetMap to be an l x w
     * array of Strings. Reads each label from input file in parameters.
     * 
     * @param streetMapFile the input file to read from
     */
    public void setupStreetMap(String streetMapFile) {
        StdIn.setFile(streetMapFile);
        int l = StdIn.readInt();
        int w = StdIn.readInt();


        streetMap = new String [l][w];

        for(int i = 0; i< streetMap.length; i++){
            for (int j = 0; j<streetMap[0].length;j++){
                streetMap[i][j] = StdIn.readString();
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Adds parking lot information to an array of parking lots.
     * Updates the instance variable lots to store these parking lots.
     * 
     * @param parkingLotFile the lot input file to read
     */
    public void setupParkingLots(String parkingLotFile) {
        StdIn.setFile(parkingLotFile);
        int n = StdIn.readInt();

        lots = new ParkingLot[n];

        for (int i = 0; i<n; i++){
                
                String a = StdIn.readString(); 
                int b = StdIn.readInt();
                double c =StdIn.readDouble();
                int d = StdIn.readInt();
                double e =StdIn.readDouble();

                lots[i] = new ParkingLot (a,b,c,d,e);
        
        }


        // WRITE YOUR CODE HERE
    }

    /**
     * Insert panels on each lot as much as space and budget allows.
     * Updates the instance variable panels to be a 2D array parallel to
     * streetMap, storing panels placed.
     * 
     * Panels have a 95% chance of working. Use StdRandom.uniform(); if
     * the resulting value is < 0.95 the panel works.
     * 
     * @param costPerPanel the fixed cost per panel, as a double
     */
    public void insertPanels(double costPerPanel) {
        panels = new Panel[streetMap.length][streetMap[0].length];
        double[][] panelBudgets = new double[streetMap.length][streetMap[0].length];
        
       
        for(int a= 0; a<lots.length;a++){
            int remainingPanels = lots[a].getMaxPanels();
            double remainingBudget = lots[a].getBudget();
    
            for (int i = 0; i < streetMap.length; i++) {
                for (int j = 0; j < streetMap[0].length; j++) {
                    if (streetMap[i][j].equals(lots[a].getLotName()) && remainingBudget >= costPerPanel && remainingPanels > 0) {
                        double randomValue = StdRandom.uniform();
    
                       
                        int estimatedMaxOutput1 = lots[a].getEnergyCapacity();
                        panels[i][j] = new Panel(lots[a].getPanelEfficiency(), estimatedMaxOutput1, randomValue < 0.95);
                        panelBudgets[i][j] = costPerPanel;
                        remainingBudget -= costPerPanel;
                        remainingPanels--;
                    }
                }
            }
        }
        

        // WRITE YOUR CODE HERE
    }

    /**
     * Given a temperature and coefficient, update panels' actual efficiency
     * values. Panels are most optimal at 77 degrees F.
     * 
     * Panels perform worse in hotter environments and better in colder ones.
     * worse = efficiency loss, better = efficiency gain.
     * 
     * Coefficients are usually negative to represent energy loss.
     * 
     * @param temperature the current temperature, in degrees F
     * @param coefficient the coefficient to use
     */
    public void updateActualEfficiency(int temperature, double coefficient) {
        double adj = coefficient * (temperature - 77);

        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[0].length; j++) {
                if (panels[i][j] != null) {
                    // Calculate efficiency adjustment based on temperature
                    panels[i][j].setActualEfficiency(panels[i][j].getActualEfficiency() - adj);
                }
            }
        }

        


        // WRITE YOUR CODE HERE
    }

    /**
     * For each WORKING panel, update the electricity generated for 4 hours 
     * of sunlight as follows:
     * 
     * (actual efficiency / 100) * 1500 * 4
     * 
     * RUN updateActualEfficiency BEFORE running this method.
     */
    public void updateElectricityGenerated() {
        double powerOutput;

        for(int i = 0; i < panels.length; i++){
            for(int j = 0; j < panels[0].length; j++){
                if(panels[i][j] != null && panels[i][j].isWorking()){
                powerOutput = (panels[i][j].getActualEfficiency() / 100) * 1500 *4;

                panels[i][j].setElectricityGenerated((int)powerOutput);
            
                }
            }
        }


        // WRITE YOUR CODE HERE
    }

    /**
     * Count the number of working panels in a parking lot.
     * 
     * @param parkingLot the parking lot name
     * @return the number of working panels
     */
    public int countWorkingPanels(String parkingLot) {
        
        int working = 0;

        for (int i = 0; i< panels.length; i++){
            for (int j = 0; j<panels[0].length; j++){
                if (panels[i][j] != null && panels [i][j].isWorking() && streetMap[i][j].equals(parkingLot)){
                    working++;
                }
            }
        }

        return working;
        
        // WRITE YOUR CODE HERE
        // return -1; // PLACEHOLDER TO AVOID COMPILATION ERROR - REPLACE WITH YOUR CODE
    }

    /**
     * Find the broken panels in the map and repair them.
     * @return the count of working panels in total, after repair
     */
    public int updateWorkingPanels() {

        int work = 0;
        for (int i =0; i<panels.length;i++){
            for (int j = 0; j< panels[0].length; j++){
                if(panels[i][j] != null){

                    if (!panels[i][j].isWorking()) {
                       panels[i][j].setIsWorking(true);
                        
                    } 
                    work++;
                }
            }
        }
       

        return work;
    
        // WRITE YOUR CODE HERE
        // return -1; // PLACEHOLDER TO AVOID COMPILATION ERROR - REPLACE WITH YOUR CODE
    }

    /**
     * Calculate Rutgers' savings on energy by using
     * these solar panels.
     * 
     * ASSUME:
     * - Multiply total electricity generated by 0.001 to convert to KwH.
     * - There are 365 days in a year.
     * 
     * RUN electricityGenerated before running this method.
     */
    public double calculateSavings() {
        int ak = 0;
        for(int i = 0; i<panels.length; i++){
            for (int j=0; j<panels[0].length;j++){
                if (panels[i][j] != null){
                    ak += panels[i][j].getElectricityGenerated();


                }
            }
        }
        double sav = (365*(ak*0.001)/4270000) *60000000;
        // WRITE YOUR CODE HERE
        return sav; // PLACEHOLDER TO AVOID COMPILATION ERROR - REPLACE WITH YOUR CODE
    }

    /*
     * Getter and Setter methods
     */
    public Panel[][] getPanels() {
        // DO NOT TOUCH THIS METHOD
        return this.panels;
    }

    public void setPanels(Panel[][] panels) {
        // DO NOT TOUCH THIS METHOD
        this.panels = panels;
    }

    public String[][] getStreetMap() {
        // DO NOT TOUCH THIS METHOD
        return this.streetMap;
    }

    public void setStreetMap(String[][] streetMap) {
        // DO NOT TOUCH THIS METHOD
        this.streetMap = streetMap;
    }

    public ParkingLot[] getLots() {
        // DO NOT TOUCH THIS METHOD
        return this.lots;
    }

    public void setLots(ParkingLot[] lots) {
        // DO NOT TOUCH THIS METHOD
        this.lots = lots;
    }
}
