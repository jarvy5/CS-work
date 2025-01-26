

/*************************************************************************
 * Compilation: javac QuadraticKoch.java
 * Execution: java QuadraticKoch n
 *
 * @author Jeremy Hui
 *
 *************************************************************************/
public class QuadraticKoch {

    /**
     * Gets the set of coordinates to draw one segment of the Quadratic Koch Curve.
     * Returns the coordinates in a 2D array of doubles in the following format:
     * {array of x-coordinates,
     * array of y-coordinates}
     * 
     * @param x0 the x-coordinate of one endpoint
     * @param y0 the y-coordinate of one endpoint
     * @param x5 the x-coordinate of the other endpoint
     * @param y5 the y-coordinate of the other endpoint
     * @return the set of coordinates to draw one segment of the Quadratic Koch
     *         Curve
     */
    public static double[][] getCoords(double x0, double y0, double x5, double y5) {
        
        //if (y0 == y5){
            double [][] key = new double [2][6];
            double x = (x5-x0)/3.0;
            double y = (y5-y0)/3.0;
            

            key[0][0]= x0;
            key [1][0] = y0;

            key[0][1] = x0 + x;
            key [1][1] = y0+y;

            key [0][2] = key[0][1] - y;
            key[1][2] = key[1][1] +x;

            key [0][3] = key[0][2] +x;
            key [1][3] = key [1][2] +y ;

            key [0][4] = x0 + 2 *x;
            key[1][4] = y0 + 2 *y;

            key[0][5] = x5;
            key[1][5] = y5;

            return key;
            
            
            // {{x0,x0+d,x0+d-y,d+(x0+d-y)+d,x0+2*d,x5},
            //                 {y0,y0+y,y0+y+d,y0+y+d+y,y0+2*y,y5}};

                            
    }

                            // for (int i = 1; i<key.length; i++){
                            //     for(int j = 1; j<key[i].length;j++){
                            //         // StdDraw.line(key[i-1][j-1],key[i][j-1], key[i-1][j], key[i][j]);
                                    

                            //     }
                            // }
                            
                       // } // if ends here 
            //             else { 
            // double d = (y5-y0)/3.0;
            // double [][] key = {{x0,x0,x0-d,x0-d,x5,x5},{y0,y0+d,y0+d,y0+(2.0*d),y5-d,y5}};
                    
            //     for (int i = 1; i<key.length; i++){
            //     for(int j = 1; j<key[i].length;j++){
            //     //StdDraw.line(key[i-1][j-1],key[i][j-1], key[i-1][j], key[i][j]);
                
            // }
            
         
                   


        // WRITE YOUR CODE HERE
    

    /**
     * Gets the set of coordinates from getCoords() to draw the snowflake,
     * and calls Koch on two adjacent array indices with n being one less.
     * The method draws a line between the two endpoints if n == 0.
     * 
     * @param x0 the x-coordinate of one endpoint
     * @param y0 the y-coordinate of one endpoint
     * @param x5 the x-coordinate of the other endpoint
     * @param y5 the y-coordinate of the other endpoint
     * @param n  The current order
     */

     
     public static void koch(double x0, double y0, double x5, double y5, int n) {
 
        if (n==0){
            
            StdDraw.line(x0,y0,x5,y5);
            return;
            
        } 
        n--;
        // if (n - 1 == 0){
        //     double [] [] key = getCoords(x0,y0,x5,y5);
        //     for (int i = 1; i<key.length; i++){
        //         for(int j = 1; j<key[i].length;j++){
        //         StdDraw.line(key[i-1][j-1],key[i][j-1], key[i-1][j], key[i][j]);
                
        //     }}
        //    return;
        // }
        double key [] [] =getCoords(x0,y0,x5,y5);
        //koch(key [0][0], key [1][0], key [0][5], key [1][5],n-1);
        koch(key [0][0], key [1][0], key [0][1], key [1][1],n);
        koch(key [0][1], key [1][1], key [0][2], key [1][2],n);
        koch(key [0][2], key [1][2], key [0][3], key [1][3],n);
        koch(key [0][3], key [1][3], key [0][4], key [1][4],n);
        koch(key [0][4], key [1][4], key [0][5], key [1][5],n);
        //koch(key [0][5], key [1][5], key [0][5], key [1][5],n-1);
        // WRITE YOUR CODE HERE
    }

    /**
     * Takes an integer command-line argument n,
     * and draws a Quadratic Koch Curve of order n in a 1 x 1 canvas
     * with an initial square side length of 0.5.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        koch (0.25,0.25, 0.25,0.75, x);
        koch (0.25,0.75, 0.75,0.75, x);
        koch (0.75,0.75, 0.75,0.25, x);
        koch (0.75,0.25, 0.25,0.25, x);
        
        
        // WRITE YOUR CODE HERE

        // double [][] key = getCoords(0.25, 0.75, .75, .75);
        // for (int i = 0; i<key.length; i ++){
        //     for (int j = 0; j<key[i].length; j++){
        //         System.out.print(key[i][j] + " ");
        //     }
        //     System.out.println();
        // }

    }
}