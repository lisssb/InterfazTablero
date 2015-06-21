package com.lissete.interfaztablero;

import java.util.Random;

public class Game {
	static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    static final int NTABLE = NFILAS * NCOLUMNAS;
    static final int VACIO = 0;
    static final int MAQUINA = 1;
    static final int JUGADOR = 2;
    private int tablero[][];
    
    
	public Game(){
		tablero = new int [NFILAS][NCOLUMNAS];
		for(int i=0; i < NFILAS ;  i++){
			for(int j= 0; j < NCOLUMNAS ;  j++){
				tablero[i][j] = VACIO;
			}
		}
	}

	public String gridToString (){
	    String str = "";
	    for (int i=0; i<NFILAS; i++)
		for (int j=0; j<NCOLUMNAS; j++)
		    str += tablero[i][j];
	    return str;
	}

	public void stringToGrid (String str){
	    for (int i=0, cont=0; i<NFILAS; i++)
	        for (int j=0; j<NCOLUMNAS; j++)
		     tablero[i][j] = str.charAt(cont++)-'0';		
	}
	
	public int devolverCasilla(int i, int j){
		return tablero[i][j];
	}
	
    /*************************************************************************
   
    Parametros: indices i y j del tablero.
    Retorno: cierto si la posicion tablero[i][j] esta vacia (su valor
    es VACIO) y falso en caso contrario
    *************************************************************************/
   public boolean estaVacio(int i, int j) {
      if (tablero[i][j]==VACIO){
    	  return true;
      }
   	  return false;
   }
   
   /*************************************************************************
   
   Parametros: indices i y j del tablero.
   Retorno: cierto si la posicion tablero[i][j] esta ocupada por el
   jugador (su valor es JUGADOR) y falso en caso contrario
   *************************************************************************/
  public boolean estaJugador(int i, int j) {
  	if (tablero[i][j]==JUGADOR){
  		return true;
  	}
  	return false;
  }

  public void ponerJugador(int i, int j) {
      tablero[i][j] = JUGADOR;
  }

  public boolean tableroLleno() {
      for (int i=0; i<NFILAS;  i++){
          for (int j=0; j<NCOLUMNAS;  j++){
              if (tablero[i][j] == VACIO){
                  return false;
              }
          }
      }
      return true;
  }
	
   /*************************************************************************
  
   Parametros: indices i y j del tablero.
   Retorno: cierto si se puede colocar ficha en la posicion (i,j) del
   tablero. se debe comprobar que esa posicion del tablero esta vacia
   (su valor es VACIO) y que es la posicion vacia mas baja del tablero.
   En caso contrario, la funcion debe devolver false.
   *************************************************************************/
  public boolean sePuedeColocarFicha(int i, int j) {
      boolean isLast = true;
      for (int pos = i+1 ; pos <NFILAS; pos++){
    	  if(estaVacio(pos, j)){
    		  isLast = false;
    	  }
      }
      return isLast && estaVacio(i, j);
  }


 public int[] juegaMaquina() {
     int i;
     int fila = -1, columna;
     Random r = new Random();
     int array [] = new int[2];

     do {
         columna = r.nextInt(NCOLUMNAS);
         for (i = 0; i < NFILAS;  i ++)
             if (tablero[i][columna] == VACIO && (i == NFILAS-1 || tablero[i+1][columna] != VACIO)) {
                 fila = i;
                 break;
             }
     } while (fila < 0);

     tablero[fila][columna] = MAQUINA;
     array[0]= fila;
     array[1] = columna;
     return array;
 }
 public boolean comprobarCuatro(int turno, int i, int j ){
	 return checkRow(turno, i, j) || checkColumn(turno, i, j) || checkDiagonal(turno, i, j);
 }
 
 
 public boolean checkDiagonal(int turno, int i, int j){
	 int count = 0;
	 
	 //arriba izquierda
	 for (int current_i = i-1, current_j = j-1; current_i >= 0 && current_j >=0 && tablero[current_i][current_j] == turno; current_i--, current_j--, count++);
	 if(count >= 3){
		 return true;
	 }
	 //abajo derecha
	 for (int current_i = i+1, current_j = j+1; current_i < NFILAS && current_j <NCOLUMNAS && tablero[current_i][current_j] == turno; current_i++, current_j++, count++);

	 if(count >= 3){
		 return true;
	 }
	 count = 0;
	 
	 //arriba derecha
	 
	 for (int current_i = i-1, current_j = j+1; current_i >=0 && current_j <NCOLUMNAS && tablero[current_i][current_j] == turno; current_i--, current_j++, count++);

	 if(count >= 3){
		 return true;
	 }
	 
	//abajo izquierda
	 
	 for (int current_i = i+1, current_j = j-1; current_i < NFILAS && current_j >= 0 && tablero[current_i][current_j] == turno; current_i++, current_j--, count++);

	 if(count >= 3){
		 return true;
	 }
	 return false;
 }
 
 public boolean checkColumn (int turno, int i, int j){
	 int count = 0;
	 for (int fila = 0; fila < NFILAS; fila++){
		 if(tablero[fila][j] == turno){
			 count = count + 1;
			 if(count == 4){return true;}
			 
		 }
		 else{
			 count = 0;
		 }
	 }
	 return false;
 }
 
 public boolean checkRow (int turno, int i, int j){
	 int count = 0;
	 for (int columna = 0; columna < NFILAS; columna++){
		 if(tablero[i][columna] == turno){
			 count = count + 1;
			 if(count == 4){return true;}			 
		 }
		 else{
			 count = 0;
		 }
	 }
	 return false;
 }
 
 
 
}
