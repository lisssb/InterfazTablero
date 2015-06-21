package com.lissete.interfaztablero;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlertDialogFragment extends DialogFragment {
	
	public static AlertDialogFragment newInstance(int title) {
		AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final MainActivity main = (MainActivity) getActivity();
		int title = getArguments().getInt("title");

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(R.string.gameOverMessage);
		alertDialogBuilder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						main.restart();
						main.dibujarTablero();
						dialog.dismiss();
					}
				});
		alertDialogBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						main.finish();
						dialog.dismiss();
					}
				});
		return alertDialogBuilder.create();
	}
}