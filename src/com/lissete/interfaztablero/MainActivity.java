package com.lissete.interfaztablero;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private final int ids[][] = {{R.id.a1, R.id.a2,R.id.a3,R.id.a4,R.id.a5,R.id.a6,R.id.a7},
			{R.id.b1, R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7},
			{R.id.c1, R.id.c2,R.id.c3,R.id.c4,R.id.c5,R.id.c6,R.id.c7},
			{R.id.d1, R.id.d2,R.id.d3,R.id.d4,R.id.d5,R.id.d6,R.id.d7},
			{R.id.e1, R.id.e2,R.id.e3,R.id.e4,R.id.e5,R.id.e6,R.id.e7},
			{R.id.f1, R.id.f2,R.id.f3,R.id.f4,R.id.f5,R.id.f6,R.id.f7}};
	
	private Game game;
	private ImageButton bValidar;
	private TextView resultadoTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		game = new Game();
		
		String x [] = getResources().getStringArray(R.array.colores);
		
		for(int i = 0; i< Game.NFILAS;  i++){
			for (int j = 0; j < Game.NCOLUMNAS;  j++){
				bValidar  = (ImageButton) findViewById(ids[i][j]);
				bValidar.setOnClickListener(this);
			}
		}	
		resultadoTextView = (TextView) findViewById(R.id.resultadoTextView);
	}
	
	public void onSaveInstanceState(Bundle outState){
		outState.putString("TABLERO", game.gridToString());
		super.onSaveInstanceState(outState);
	}
	
	public void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString("TABLERO");
        game.stringToGrid(grid);
        dibujarTablero();
    }
	
	
	
	protected void onResume(){
		super.onResume();
		Boolean play;
	    String player;
	    
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		play = getPlayMusic(sharedPreferences);
		player = getPlayerName(sharedPreferences);		
		
	   	if(player.trim().length()>0)
	   		resultadoTextView.setText( getResources().getString(R.string.player_name) + " : " + player);
	   	else
	   		resultadoTextView.setText("");	   	

	   	if (play == true)
	   		Music.play(this, R.raw.funkandblues);
	   	
	   	dibujarTablero();
	   	
    }
	
	
    public String getColor (SharedPreferences sharedPreferences) {
		String color = PrefActivity.COLORES_DEFAULT;		
	   	if (sharedPreferences.contains(PrefActivity.COLORES_KEY)){
	   		color = sharedPreferences.getString(PrefActivity.COLORES_KEY, PrefActivity.COLORES_DEFAULT);
	   	}	   		
	   	return color;
    }
    
    public int getColorId(String c){
    	int color = Integer.parseInt(c);
    	int color_id = R.color.gray;
    	
    	switch(color){
    	    case 1:
    	    	color_id = R.color.blue;
    	    	break;
    	    case 2:
    	    	color_id = R.color.purple;
    	    	break;
    	    case 3:
    	    	color_id = R.color.orange;
    	    	break;
    	    case 4:
    	    	color_id = R.color.red;
    	    	break;
    	}
    	return color_id;
    }
    
    public String getPlayerName(SharedPreferences sharedPreferences){
    	String name = "";
    	
    	if(sharedPreferences.contains(PrefActivity.PLAYER_KEY)){
	   		name = sharedPreferences.getString(PrefActivity.PLAYER_KEY, PrefActivity.PLAYER_DEFAULT);
	   	}
    	return name;
    }
    
    public Boolean getPlayMusic(SharedPreferences sharedPreferences){
    	Boolean music = false;
    	if (sharedPreferences.contains(PrefActivity.PLAY_MUSIC_KEY))
    	    music = sharedPreferences.getBoolean(PrefActivity.PLAY_MUSIC_KEY, 
    	    		PrefActivity.PLAY_MUSIC_DEFAULT);
    	return music;
    }
    
    protected void onPause(){
		super.onPause();
		Music.stop(this);
    }
	
    /*************************************************************************

    Dependiendo del estado de cada casilla del tablero asignar al
    identificador id el dibujable adecuado:
    - R.drawable.c4_button
    - R.drawable.c4_human_pressed_button
    - R.drawable.c4_machine_pressed_button
    *************************************************************************/
	

	
   public void dibujarTablero() {
       int id = 0;
       int color;
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       color = getColorId(getColor(sharedPreferences));
       

       for (int i = 0; i < Game.NFILAS; i++)
           for (int j = 0; j < Game.NCOLUMNAS; j++) {
        	   if(game.devolverCasilla(i,j)==Game.MAQUINA){
        		   id = R.color.yellow;
        	   }
        	   else if(game.devolverCasilla(i,j) == Game.JUGADOR){
        		   id = color;
        	   }
        	   else{
        		   id = R.color.light_blue;
        	   }
               ImageButton imageButton = (ImageButton) findViewById(ids[i][j]);
               GradientDrawable drawable = (GradientDrawable) imageButton.getDrawable();
               drawable.setColor(getResources().getColor(id));
           }
   }
   
	public void onClick (View v){
		pulsado(v);
	}
   public void pulsado(View v) {
       int fila, columna, id = v.getId();
       
       fila = deIdentificadorAFila(id);
       columna = deIdentificadorAColumna(id);
       

       if (game.sePuedeColocarFicha(fila, columna) != true) {
           Toast.makeText(this, R.string.nosepuedecolocarficha,
                   Toast.LENGTH_SHORT).show();
           return;
       }

       game.ponerJugador(fila, columna);
       
       
       
       if(game.comprobarCuatro(Game.JUGADOR, fila, columna)){
           dibujarTablero();
		AlertDialogFragment.newInstance(R.string.winerplayer).show(getFragmentManager(), "ALERT DIALOG");
       }
       else{
	       int posicion [] = game.juegaMaquina();       
	       
	       if(game.comprobarCuatro(Game.MAQUINA, posicion[0], posicion[1])){
	    	   dibujarTablero();
	    	   AlertDialogFragment.newInstance(R.string.winermachine).show(getFragmentManager(), "ALERT DIALOG");
	       }
	       else{
	    	   dibujarTablero();
	    	   if (game.tableroLleno()) {
	   			AlertDialogFragment.newInstance(R.string.tableroLLeno).show(getFragmentManager(), "ALERT DIALOG");
	   			return;
	   		}
	       }
       }
   }

	
	public int deIdentificadorAFila(int id){
		for(int i = 0; i <Game.NFILAS;  i++)
			for (int j = 0 ; j < Game.NCOLUMNAS;  j++)
				if(ids[i][j] == id)
					return i;	
		return -1;
	}
	public int deIdentificadorAColumna(int id){
		for(int i = 0; i <Game.NFILAS;  i++)
			for (int j = 0 ; j < Game.NCOLUMNAS;  j++)
				if(ids[i][j] == id)
					return j;	
		return -1;
	}
	
	public void restart(){
		game = new Game();
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.acerca_de:
				startActivity(new Intent(this, About.class));
				return true;
			case R.id.preferences:
                startActivity(new Intent(this, PrefActivity.class));
        		return true;
			case R.id.mi_salir:
				if (PrefActivity.getShowCloseDialogPreference(this)) {
					String title = getResources().getString(R.string.app_name);
					String message = getResources().getString(R.string.salir_de_la_app);					
					WarnDialog wd = new WarnDialog(title, message);
					wd.show(getFragmentManager(), "DIALOG");
				}
				else {
					finish();
				}
				return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true; 
	}

}
