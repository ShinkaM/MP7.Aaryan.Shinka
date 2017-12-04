
public class Playfair {
	private char[][] arr = new char[5][5];
	private String code = "";
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
								&& x.toUpperCase().charAt(k) <= 'Z') { //makes sure that the key is between A-Z								
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
	 * Replaces duplicated characters with an X
	 * @param x
	 * @return
	 */
	public String makeCode(String x) {
		x = "Helloworld";
		if(x.length() != 0) {
			for(int i = 0; i < x.length() -1; i++) {
				if(x.charAt(i) != x.charAt(i + 1)) { // checks for duplicated letters
					this.code += x.toUpperCase().charAt(i);
				} else {
					this.code += "X"; // adds filler character if there is a duplicste
				}
			}
			if(this.code.length() %2 != 0) {
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
			this.encode = new String[y.length()/2];
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
	
	
}

	
	


