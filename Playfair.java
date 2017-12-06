
public class Playfair {
	private char[][] arr = new char[5][5];
	/**
	 * contains the message to be encoded, later made into an array in encode.
	 */
	private String code = "";
	
	/**
	 * contains the message to be encoded in AB CD EF . . .  format
	 */
	private String[] encode = null;
	private String key = "THEUICKBROWNFXJMPSVRLAZYD";// missing Q
	public char[] cAlphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	public String k1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * This makes the key for the cipher.  
	 * Assumes that the key is 25 letters long, with no punctuations.
	 * @return
	 */
	public boolean setKey(String x){
		x = key;
		int k = 0;
		while(k < 25) {
			for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < i; j++) {
						if(x.toUpperCase().charAt(k) >= 'A' 
								&& x.toUpperCase().charAt(k) <= 'Z') { // makes sure that the key is between A-Z								
							this.arr[i][j] = x.toUpperCase().charAt(k);
							k++;
						} else {
							return false; // reject any character not in the alphabet
						}
				}
			}
		}
		return true;
	}
	
	public char[][] getKey(){
		return this.arr;
	}
	
	/**
	 * A helper function
	 * Takes the string to be encoded and formats it so that its length is even.
	 * Replaces duplicated characters with an X, so TREE = TR EX ES
	 * @param x
	 * @return
	 */
	public String makeCode(String x) {
		x = "Helloworld";
		if (x.length() != 0) {
			for (int i = 0; i < x.length() -1; i++) {
				if (x.charAt(i) != x.charAt(i + 1)) { // checks for duplicated letters
					this.code += x.toUpperCase().charAt(i);
				} else {
					this.code += "X"; // adds filler character if there is a duplicste
				}
			}
			if (this.code.length() %2 != 0) {
				this.code += "Z"; // adds a filler character if length is odd
			} 
			return this.code;
		} else {
			return null;
		}
	}
	/**
	 * Formats the code so that it is in the AB CD EF ... form
	 * @param x
	 * @return
	 */
	public boolean setCode(String x) {
		if(x != null) {
			String y = makeCode(x);
			this.encode = new String[y.length() / 2];
			for(int i = 0; i < encode.length -1; i++) {
				this.encode[i] = x.substring(i, i+1);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public String[] getCode() {
		return this.encode;
	}

	/***
	 * A constructor
	 * a = key.
	 * b = code.
	 * @param x
	 * @param y
	 */
	public Playfair(String a, String b) {
		if(a.length() > 25) {
			a = null;
		} else {
			this.setKey(a);
		}
		if(b.length() == 0) {
			b = null;
		} else {
			this.setCode(b);
		}
	}

	public String[] encode(String[] text) { // the input is AB CD EF . . . 
		int x = 0;
		int y = 0;
		String[] code = new String[this.encode.length];
		
		//find the i and j for the first letter of each pair
		for(int k = 0; k < text.length; k++) {
			for(int i = 0; i < this.arr.length; i++) {
				for(int j = 0; j < i; j++) {
					if(this.arr[i][j] == text[k].charAt(0)) {
						x = i; // the row
						y = j; // the column
					}
					
				}
			}
			// for the case where the second pair is in the same row
			
			if(text[k].charAt(1) == this.arr[x][y + 1]) {
				code[k] += this.arr[x][(y + 1) % 5] + this.arr[x][(y + 2) % 5]; // %5 wraps around, so 5 % 5 = 0, 6 % 5 = 1, 7 % 5 = 2, etc
			}
			
			// for the case where the second pair is in the same column
			if(text[k].charAt(1) == this.arr[x + 1][y]) {
				code[k] += this.arr[(x + 1) % 5][y] + this.arr[(x + 2) % 5][y];
			}
			
			// for the rectangular case
			//checks row
			for(int a = 0; a < arr.length; a++) {
				if(text[k].charAt(1) == this.arr[x][a]) {
					// adds the letters in the opposite corners of the key
					//
					// A B C D
					// E F G H
					// I J K L
					// L M N O
					//
					//if input is EL, output is HI
					//
					code[k] += this.arr[a][y] + this.arr[x][a];
				}
			}
		}
		return code;
	}
}
	
	
	


