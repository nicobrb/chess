package it.uniba.chess.utils;

public enum ParserColumns {
	h,g,f,e,d,c,b,a;
	
	//we will check that this method only receives in input chars defining a chess column;
	public static int getColumnIntegerFromChar(char row) {
		switch(row) {
		case 'h':
			return 0;
		case 'g':
			return 1;
		case 'f':
			return 2;
		case 'e':
			return 3;
		case 'd':
			return 4;
		case 'c':
			return 5;
		case 'b':
			return 6;
		case 'a':
			return 7;
		default:
		}
		
		//necessary to make the code compile
		return -1;
	}
}