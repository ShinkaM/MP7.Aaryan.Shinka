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
	 * this is the key used, has all letters of alphabet except Q
	 */
	static String key = "THEUICKBROWNFXJMPSVRLAZYD";


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
							k++;
						} else {
							return null; // reject any character not in the alphabet
						}
				}
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
	
		if (x.length() != 0) { // string is not null
			for (int i = 0; i < x.length() - 2; i += 2) {
				if(x.toUpperCase().charAt(i) >= 'A' && x.toUpperCase().charAt(i +1) <= 'Z') {
					if (x.charAt(i) != x.charAt(i + 1)) { // checks for duplicated letter pairs, like EE
						code += x.charAt(i) + x.charAt(i + 1);
					} else {
						code += x.charAt(i) + "X" + x.charAt(i + 1); // adds filler character if there is a duplicate
					}
					System.out.println(code + "CC");
				} else {
					return null;
				}
			}
			if (code.length() %2 != 0) {
				code += "Z"; // adds a filler character if length is odd
			} 
			
			return code;
		} else {
			return null;
		}
	}
	/**
	 * Formats the code so that it is in the AB CD EF ... form
	 * @param x
	 * @return
	 */
	public String[] setCode() {
		if(code != null) {
			encode = new String[code.length() / 2];
			for(int i = 0, j = 0; i < encode.length; i++, j+=2 ) {
				encode[i] = code.substring(j, j + 2);
			}
	//		System.out.println("*" + String.valueOf(encode[0]));
			return encode;
		} else {
			return null;
		}
	}

	public String[] encode() { // the input is AB CD EF . . . made from setCode
		int x = 0;
		int y = 0;
		String[] output = new String[encode.length];
		//find the i and j for the first letter of each pair (so H if HI is the pair)
		for(int k = 0; k < encode.length; k++) {
			for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < i; j++) {
					if(arr[i][j] == encode[k].charAt(0)) {
						x = i; // the row
						y = j; // the column
					}
					
				}
			}
			// for the case where the second pair is in the same row
			for(int i = y + 1; i < arr.length; i++) {
				if(encode[k].charAt(1) == arr[x][i]) {
					output[k] += arr[x][(y + 1) % arr.length] + arr[x][(i + 1) % arr.length]; // %5 wraps around, so 5 % 5 = 0, 6 % 5 = 1, 7 % 5 = 2, etc
				}
			}
			// for the case where the second pair is in the same column
			for(int i = x + 1; i < arr.length; i++) {
				if(encode[k].charAt(1) == arr[i][y]) {
					output[k] += arr[(x + 1) % arr.length][y] + arr[(i + 1) % arr.length][y];
				}
			}
			
			// for the rectangular case
			//checks row
			for(int a = 0; a < arr.length; a++) {
				for(int b = 0; b < arr.length; b++) {
				if(encode[k].charAt(1) == arr[b][a]) {
					// adds the letters in the opposite corners of the key
					//
					// A B C D
					// E F G H
					// I J K L
					// L M N O
					//
					//if input is EL, output is HI
					//
					output[k] += arr[x][b] + arr[a][y];
				}
			}
		}
		
	}
		return output;
}
	   @SuppressWarnings("resource")
	    public static void main(final String[] unused) {


	        String linePrompt = String.format("Enter a line of text, or a blank line to exit:");
	     

	        /*
	         * Two steps here: first get a line, then a shift integer.
	         */
	        Scanner lineScanner = new Scanner(System.in);
	         while (true) {
	            String line = null;
	            

	            System.out.println(linePrompt);
	            while (lineScanner.hasNextLine()) {
	                Scanner textScanner = new Scanner(lineScanner.nextLine());
	                if (textScanner.hasNextLine()) {
	                    line = textScanner.nextLine();
	                    if (textScanner.hasNext() || makeCode(line) == null) {
	                        line = null;
	                        System.out.println("Invalid input: no punctuations");
	                    }
	                } else {
	                    System.out.println("Invalid input: please enter a text.");
	                }
	                textScanner.close();
	                if (line != null) {
	                    break;
	                }
	            }

	            if (line == null || line.equals("")) {
	                throw new RuntimeException("Should have a line at this point");
	            }
	            Playfair obj = new Playfair();
	            obj.setKey();
	            Playfair.makeCode(line);
	            obj.setCode();
	            String[] result = obj.encode();
	            for(int i = 0; i < result.length; i++) {
	            	System.out.print(result[i] + " ");
	            }
	        }
	         

   }
}

	
	
	


