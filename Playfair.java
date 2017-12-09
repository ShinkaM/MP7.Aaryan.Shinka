import java.util.Scanner;
public class Playfair {
	/**
	 * Used to contain the key.
	 */
	static char[][] arr = new char[5][5];
	/**
	 * contains the message to be encoded, later made into an array in encode.
	 */
	static String code = "";
	
	/**
	 * contains the message to be encoded in AB CD EF . . .  format
	 */
	static String[] encode = null;
	
	/**
	 * this is the key used, has all letters of alphabet except Q and G
	 */
	static String key = "THEUICKBROWNFXJMPSVLAZYDG";


	/**
	 * This makes the key for the cipher.  
	 * Assumes that the key is 25 letters long, with no punctuations.
	 * @return
	 */
	public char[][] setKey(){
	    String x = key;
		int k = 0;
		while(k < 25) {
			for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < arr[0].length; j++) {
						if(x.toUpperCase().charAt(k) >= 'A' 
								&& x.toUpperCase().charAt(k) <= 'Z') { // makes sure that the key is between A-Z								
							arr[i][j] = x.toUpperCase().charAt(k);
							//System.out.print(arr[i][j] + " ");
							k++;
						} else {
							return null; // reject any character not in the alphabet
						}
				}
				//System.out.println();
			}
		}
		return arr;
	}
	
	/**
	 * A helper function
	 * Takes the string to be encoded and formats it so that its length is even and in all caps.
	 * Replaces duplicated characters with an X, so TREE = TR EX ES
	 * @param x
	 * @return
	 */
	public static String makeCode(String x) {
		String str1 = "";
		if (x.length() != 0) { // string is not null
			for (int i = 0; i < x.length() - 1; i += 1) {
				if(x.toUpperCase().charAt(i) >= 'A' && x.toUpperCase().charAt(i) <= 'Z'
						&& x.toUpperCase().charAt(i + 1) >= 'A' && x.toUpperCase().charAt(i + 1) <= 'Z') {
					if (x.charAt(i) != x.charAt(i + 1)) { // checks for duplicated letter pairs, like EE
						str1 += x.toUpperCase().charAt(i) + "" ;
					} else {
						str1 += x.toUpperCase().charAt(i) + "X"; // adds filler character if there is a duplicate
					}
					
					//System.out.println(code + "CC");
				} else {
					return null;
				}
			}
			str1 += x.toUpperCase().charAt(x.length()-1);
			if (str1.length() % 2 != 0) {
				str1 += "Z"; // adds a filler character if length is odd
			} 
			//System.out.println(str1 + "&&");
			return str1;
		} else {
			return null;
		}
		
	}
	/**
	 * Formats the code so that it is in the AB CD EF ... form
	 * @param x
	 * @return
	 */
	public String[] setCode(String x) {
		String str2 = makeCode(x);
		if(str2 != null) {
			encode = new String[str2.length() / 2];
			for(int i = 0, j = 0; i < encode.length; i++, j+=2 ) {
				encode[i] = "" + str2.substring(j, j + 2);
				System.out.print(encode[i]+ " ");
			}
			System.out.println();
			
			return encode;
		} else {
			return null;
		}
	}

	public String[] encode() { // the input is AB CD EF . . . made from setCode
		int x = 0;
		int y = 0;
		boolean square = true;
		String[] output = new String[encode.length];
		//find the i and j for the first letter of each pair (so H if HI is the pair)
		for(int k = 0; k < encode.length; k++) {
	  outer:for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < arr[0].length; j++) {
					if(arr[i][j] == encode[k].charAt(0)) {
						x = i; // the column
						y = j; // the row
						break outer;
					}
					
				}
			}
			// for the case where the second pair is in the same row
			
			for(int i = y + 1; i < arr.length; i++) {
				if(encode[k].charAt(1) == arr[x][i]) {
					square = false;
					output[k] = arr[x][(y + 1) % arr.length] + "" + arr[x][(i + 1) % arr.length]; // %5 wraps around, so 5 % 5 = 0, 6 % 5 = 1, 7 % 5 = 2, etc
				}
			}
			// for the case where the second pair is in the same column
			for(int i = x + 1; i < arr.length; i++) {
				if(encode[k].charAt(1) == arr[i][y]) {
					square = false;
					output[k] = arr[(x + 1) % arr.length][y] + "" + arr[(i + 1) % arr.length][y];
				}
			}
			
			// for the rectangular case
			//checks row
			if(square) {
					for(int a = 0; a < arr.length; a++) {
						for(int b = 0; b < arr[0].length; b++) {
						if(encode[k].charAt(1) == arr[a][b]) {
							// adds the letters in the opposite corners of the key
							//
							// A B C D
							// E F G H
							// I J K L
							// L M N O
							//
							//if input is EL, output is HI
							//
							output[k] = arr[x][b] + "" + arr[a][y];
						}
					}
				}
			}
		
	}
		 /*for(int i = 0; i < output.length; i++) {
         	System.out.print(output[i] + " " + "@@");
         }*/
		return output;
	}
	   @SuppressWarnings("resource")
	    public static void main(final String[] unused) {


	        String linePrompt = String.format("Enter a line of text, with no space or punctuations, or a blank line to exit:");
	     

	        /*
	         * Two steps here: first get a line, then a shift integer.
	         */
	        Scanner lineScanner = new Scanner(System.in);
	         repeat: while (true) {
	            String line = null;
	            System.out.println(linePrompt);
	            while (lineScanner.hasNextLine()) {
	                line = lineScanner.nextLine();
	                    if (lineScanner.equals("") || makeCode(line) == null) {
	                        System.out.println("Invalid input: no punctuations");
	                        break repeat;
	                        
	                    } else {
	                    	break;
	                    }
	            }
	         

	            Playfair obj = new Playfair();
	            obj.setKey();
	            obj.setCode(line);
	            String[] result = obj.encode();
	            for(int i = 0; i < result.length; i++) {
	            	System.out.print(result[i] + " ");
	            }
	            System.out.println("");
	        }
	           lineScanner.close();

  }
	 
}
