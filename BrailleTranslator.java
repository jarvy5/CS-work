package braille;

import java.util.ArrayList;

/**
 * Contains methods to translate Braille to English and English to Braille using
 * a BST.
 * Reads encodings, adds characters, and traverses tree to find encodings.
 * 
 * @author Seth Kelley
 * @author Kal Pandit
 */
public class BrailleTranslator {

    private TreeNode treeRoot;

    /**
     * Default constructor, sets symbols to an empty ArrayList
     */
    public BrailleTranslator() {
        treeRoot = null;
    }

    /**
     * Reads encodings from an input file as follows:
     * - One line has the number of characters
     * - n lines with character (as char) and encoding (as string) space-separated
     * USE StdIn.readChar() to read character and StdIn.readLine() after reading
     * encoding
     * 
     * @param inputFile the input file name
     */
    public void createSymbolTree(String inputFile) {

        /* PROVIDED, DO NOT EDIT */

        StdIn.setFile(inputFile);
        int numberOfChars = Integer.parseInt(StdIn.readLine());
        for (int i = 0; i < numberOfChars; i++) {
            Symbol s = readSingleEncoding();
            addCharacter(s);
        }
    }

    /**
     * Reads one line from an input file and returns its corresponding
     * Symbol object
     * 
     * ONE line has a character and its encoding (space separated)
     * 
     * @return the symbol object
     */
    public Symbol readSingleEncoding() {

        char c = StdIn.readChar();

        String s = StdIn.readString();
        StdIn.readLine();

        Symbol sy = new Symbol(c, s);

        // WRITE YOUR CODE HERE

        return sy; // Replace this line, it is provided so your code compiles
    }

    /**
     * Adds a character into the BST rooted at treeRoot.
     * Traces encoding path (0 = left, 1 = right), starting with an empty root.
     * Last digit of encoding indicates position (left or right) of character within
     * parent.
     * 
     * @param newSymbol the new symbol object to add
     */
    public void addCharacter(Symbol newSymbol) {

        String s = newSymbol.getEncoding();

        if (treeRoot == null) {
            Symbol add = new Symbol("");
            treeRoot = new TreeNode(add, null, null);

        }

        TreeNode ptr = treeRoot;
        String par = "";

        for (int i = 0; i < s.length(); i++) {

            par += s.charAt(i);

            if (s.charAt(i) == 'L') {

                if (ptr.getLeft() == null) {

                    if (par.equals(newSymbol.getEncoding())) {
                        TreeNode r = new TreeNode(new Symbol(newSymbol.getCharacter(), newSymbol.getEncoding()), null,
                                null);
                        ptr.setLeft(r);
                    } else {
                        TreeNode r = new TreeNode(new Symbol(par), null, null);
                        ptr.setLeft(r);
                    }
                }
                ptr = ptr.getLeft();
            }

            else if (s.charAt(i) == 'R') {

                if (ptr.getRight() == null) {

                    if (par.equals(newSymbol.getEncoding())) {
                        TreeNode r = new TreeNode(new Symbol(newSymbol.getCharacter(), newSymbol.getEncoding()), null,
                                null);
                        ptr.setRight(r);
                    } else {
                        TreeNode r = new TreeNode(new Symbol(par), null, null);
                        ptr.setRight(r);
                    }
                }
                ptr = ptr.getRight();

            }

        }

        // System.out.println(newSymbol.getCharacter());

        // if(ptr.getLeft() == null && ptr.getRight() == null){

        // TreeNode r = new TreeNode(newSymbol, null, null);
        // char f = newSymbol.getCharacter();

        // }

        // WRITE YOUR CODE HERE

    }

    /**
     * Given a sequence of characters, traverse the tree based on the characters
     * to find the TreeNode it leads to
     * 
     * @param encoding Sequence of braille (Ls and Rs)
     * @return Returns the TreeNode of where the characters lead to, or null if
     *         there is no path
     */
    public TreeNode getSymbolNode(String encoding) {
        TreeNode ptr = treeRoot;

        String hold = "";
        for (int i = 0; i < encoding.length(); i++) {
            hold += encoding.charAt(i);

            if (encoding.charAt(i) == 'L') {
                ptr = ptr.getLeft();
                if (hold.equals(encoding)) {
                    return ptr;
                }
            } else if (encoding.charAt(i) == 'R') {
                ptr = ptr.getRight();
                if (hold.equals(encoding)) {
                    return ptr;
                }
            }

        }

        // WRITE YOUR CODE HERE

        return null; // Replace this line, it is provided so your code compiles
    }

    /**
     * Given a character to look for in the tree will return the encoding of the
     * character
     * 
     * @param character The character that is to be looked for in the tree
     * @return Returns the String encoding of the character
     */
    public String findBrailleEncoding(char character) {

        TreeNode ptr = treeRoot;

        TreeNode c = findBrailleEncodingHelper(ptr, character);

        if (c.getSymbol().getCharacter() == character) {
            return c.getSymbol().getEncoding();
        } else {
            return null;
        }

        // WRITE YOUR CODE HERE

        // Replace this line, it is provided so your code compiles
    }

    private TreeNode findBrailleEncodingHelper(TreeNode n, char ans) {
        if (n == null) {
            return null;
        }

        if (n.getSymbol().getCharacter() == ans) {
            return n;
        }

        if (findBrailleEncodingHelper(n.getLeft(), ans) != null) {
            return findBrailleEncodingHelper(n.getLeft(), ans);
        }

        return findBrailleEncodingHelper(n.getRight(), ans);
    }

    /**
     * Given a prefix to a Braille encoding, return an ArrayList of all encodings
     * that start with
     * that prefix
     * 
     * @param start the prefix to search for
     * @return all Symbol nodes which have encodings starting with the given prefix
     */
    public ArrayList<Symbol> encodingsStartWith(String start) {

        TreeNode startNode = getSymbolNode(start);
        ArrayList<Symbol> list = new ArrayList<>();

        preOrderHelp(startNode, list);

        return list;
        // WRITE YOUR CODE HERE

        // Replace this line, it is provided so your code compiles
    }

    private void preOrderHelp(TreeNode t, ArrayList<Symbol> n) {
        if (t == null) {
            return;
        }
        if (t.getSymbol().getCharacter() != Character.MIN_VALUE)
            n.add(t.getSymbol());

        preOrderHelp(t.getLeft(), n);
        preOrderHelp(t.getRight(), n);
    }

    /**
     * Reads an input file and processes encodings six chars at a time.
     * Then, calls getSymbolNode on each six char chunk to get the
     * character.
     * 
     * Return the result of all translations, as a String.
     * 
     * @param input the input file
     * @return the translated output of the Braille input
     */
    public String translateBraille(String input) {
        StdIn.setFile(input);
        String code = StdIn.readLine();
        String sym = "";
        String answer = "";
        int count =0;
        for (int i = 0; i < code.length(); i++) {
            sym += code.charAt(i);
            count ++;
            if(count% 6 ==0){
                TreeNode letter = getSymbolNode(sym);
                answer += letter.getSymbol().getCharacter();
                sym = "";
            }
            
        }
        // WRITE YOUR CODE HERE

        return answer; // Replace this line, it is provided so your code compiles
    }

    /**
     * Given a character, delete it from the tree and delete any encodings not
     * attached to a character (ie. no children).
     * 
     * @param symbol the symbol to delete
     */
public void deleteSymbol(char symbol) {

    String code = findBrailleEncoding(symbol);


    for(int i =0; i<6; i++){

            TreeNode Tnode = getSymbolNode(code);
            TreeNode Pnode = getSymbolNode(code.substring(0, 5-i));


            if(Tnode.getLeft() != null && Tnode.getRight() != null){
                break;
            }


        // String Pstring = Pnode.getSymbol().getEncoding();
        String Tstring = Tnode.getSymbol().getEncoding();


      if(Tnode.getLeft() == null && Tnode.getRight() == null){
            if(Pnode != null){

                if(Tstring.charAt(Tstring.length()-1) == 'L'){
                Pnode.setLeft(null);
                } else if(Tstring.charAt(Tstring.length()-1) == 'R'){
                    Pnode.setRight(null);
                } 
                } else if(Pnode == null){

                if(Tstring.equals("L")){
                    treeRoot.setLeft(null);
                } else if(Tstring.equals("R")){
                    treeRoot.setRight(null);
                }
                }
    
    
          

 
        }
        code = code.substring(0, 5-i );

    }


   // WRITE YOUR CODE HERE
}

   

    public TreeNode getTreeRoot() {
        return this.treeRoot;
    }

    public void setTreeRoot(TreeNode treeRoot) {
        this.treeRoot = treeRoot;
    }

    public void printTree() {
        printTree(treeRoot, "", false, true);
    }

    private void printTree(TreeNode n, String indent, boolean isRight, boolean isRoot) {
        StdOut.print(indent);

        // Print out either a right connection or a left connection
        if (!isRoot)
            StdOut.print(isRight ? "|+R- " : "--L- ");

        // If we're at the root, we don't want a 1 or 0
        else
            StdOut.print("+--- ");

        if (n == null) {
            StdOut.println("null");
            return;
        }
        // If we have an associated character print it too
        if (n.getSymbol() != null && n.getSymbol().hasCharacter()) {
            StdOut.print(n.getSymbol().getCharacter() + " -> ");
            StdOut.print(n.getSymbol().getEncoding());
        } else if (n.getSymbol() != null) {
            StdOut.print(n.getSymbol().getEncoding() + " ");
            if (n.getSymbol().getEncoding().equals("")) {
                StdOut.print("\"\" ");
            }
        }
        StdOut.println();

        // If no more children we're done
        if (n.getSymbol() != null && n.getLeft() == null && n.getRight() == null)
            return;

        // Add to the indent based on whether we're branching left or right
        indent += isRight ? "|    " : "     ";

        printTree(n.getRight(), indent, true, false);
        printTree(n.getLeft(), indent, false, false);
    }

}
