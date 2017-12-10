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
	 * encrypt or decrypt
	 */
	static int q = 0;


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
				    	if(x.toUpperCase().charAt(i) == 'Q') {
							str1 += "U";
						} else {
						    str1 += x.toUpperCase().charAt(i) + "" ;
						}
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
	public static String[] setCode(String x, int a) {
		//System.out.println(x);
		q = a;
		String str2 = "";
		if(a == 1) {
		    str2 = makeCode(x);
		} else if(a == 2) {
			str2 = x.toUpperCase();
		}
		if(str2 != null) {
			encode = new String[str2.length() / 2];
			for(int i = 0, j = 0; i < encode.length; i++, j+=2 ) {
				encode[i] = "" + str2.substring(j, j + 2);
				//System.out.print(encode[i]+ " ");
			}
			//System.out.println();
			
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
			boolean square = true;
	  outer:for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < arr[0].length; j++) {
					if(arr[i][j] == encode[k].charAt(0)) {
						x = i; // the column
						y = j; // the row
						//System.out.println(x+""+y);
						break outer;
					}
					
				}
			}
			// for the case where the second pair is in the same row
			
			for(int i = 0; i < arr.length; i++) {
				if(encode[k].charAt(1) == arr[x][i]) {
					square = false;
					if(q == 2) {
						output[k] = arr[x][(y - 1 + 5) % arr.length] + "" + arr[x][(i - 1 + 5) % arr.length];
					}else {
					    output[k] = arr[x][(y + 1) % arr.length] + "" + arr[x][(i + 1) % arr.length]; // %5 wraps around, so 5 % 5 = 0, 6 % 5 = 1, 7 % 5 = 2, etc
				    }
				}	
			}
			// for the case where the second pair is in the same column
			for(int i = 0; i < arr.length; i++) {
				if(encode[k].charAt(1) == arr[i][y]) {
					square = false;
					if(q == 2) {
						output[k] = arr[(x - 1 + 5) % arr.length][y] + "" + arr[(i - 1 + 5) % arr.length][y];
					}else {
					    output[k] = arr[(x + 1) % arr.length][y] + "" + arr[(i + 1) % arr.length][y];
					}			
				}
			}
			
			// for the rectangular case
			//checks row
			//System.out.println("working");
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
							//System.out.println(a + " "+ b);
							output[k] = arr[x][b] + "" + arr[a][y];
						}
					}
				}
			}
		
	}
		/* for(int i = 0; i < output.length; i++) {
         	System.out.print(output[i] + " ");
         }*/
		 return output;
	}
	public String decode() {
		Playfair ob = new Playfair();
		String[] result = ob.encode();
		String output = "";
		for(int i = 0; i < result.length; i++) {
       	 output += result[i];
          }
		String str=output.substring(0,1);
		for(int i = 1; i < output.length() - 1; i++) {
	       	if(output.charAt(i) == 'X') {
	       		if(output.charAt(i-1) == output.charAt(i+1)) {
	       			continue;
	       		} else {
	       			str += output.charAt(i);
	       		}
	       	} else {
	       		str += output.charAt(i);
	       	}
	      }
		if(output.charAt(output.length()-1) != 'Z') {
			str += output.charAt(output.length() - 1);
		}
		return str;
	}
	@SuppressWarnings("resource")
	public static void main(final String[] unused) {

	        Scanner lineScanner = new Scanner(System.in);
	        //String linePrompt1 = String.format("Input 1 to encrypt or 2 to decrypt");
	        
	       // String linePrompt2 = String.format("Enter a line of text or code, with no space or punctuations, or a blank line to exit:");
	     

	        /*
	         * Two steps here: first get a line, then a shift integer.
	         */
	         repeat: while (true) {
	        	String line = null;
	        	int a = 0;
	        	System.out.println("Enter a line of text or code, with no space or punctuations, or a blank line to exit:");
	        	if(lineScanner.hasNextLine()) {
	        	line = lineScanner.nextLine();
	        	System.out.println("Input 1 to encrypt or 2 to decrypt");
	        	if(lineScanner.hasNextInt()) {
		           a = lineScanner.nextInt();
	        	}
	        	}
	            if (line.equals("") || setCode(line,a) == null) {
	                 System.out.println("Invalid input: no punctuations");
	                 break repeat;
	            }
	            
	            Playfair obj = new Playfair();
                if(a == 1) {
	               obj.setKey();
	               Playfair.setCode(line,a);
	               String[] result = obj.encode();
	               for(int i = 0; i < result.length; i++) {
	            	 System.out.print(result[i] + " ");
	               }
	               System.out.println("");
                } else if(a == 2) {
                   obj.setKey();
                   Playfair.setCode(line,a);
                   String result = obj.decode();
                   System.out.println(result);
                }
                code = "";
                encode = null;
                arr = null;
                q = 0;
	        }
	           lineScanner.close();
	         }
  }
	        
	 

