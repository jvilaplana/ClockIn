package com.jordiv.clockin;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TicketListActivity extends ListActivity {
	static final int DIALOG_DELETE_ID = 0;
	static final String TAG = "JORDIV";

	private TicketDataSource datasource;
	private ArrayAdapter<Ticket> adapter;
	private List<Ticket> values;
	private ListView listView;
	private int ticketId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.ticket);

		datasource = new TicketDataSource(this);
		datasource.open();

		listView = getListView();

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos,
					long id) {
				onLongListItemClick(v, pos, id);
				return false;
			}
		});

		values = datasource.getAllTickets();

		// Use the SimpleCursorAdapter to show the elements in a ListView
		adapter = new ArrayAdapter<Ticket>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

	}

	// You then create your handler method:
	protected void onLongListItemClick(View v, int pos, long id) {
		Log.i(TAG, "onLongListItemClick id=" + pos);
		Bundle args = new Bundle();
		args.putInt("ticketId", pos);
		ticketId = args.getInt("ticketId");
		showDialog(DIALOG_DELETE_ID);
	}
	
	
	
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		Log.i(TAG, "FUU = " + ticketId);
		switch (id) {
		case DIALOG_DELETE_ID:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Delete ticket?")
					.setCancelable(false)
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// DELETE TICKET
							Log.i(TAG, "DELETE ITEM id = " + ticketId);
							Ticket ticket = datasource.getAllTickets().get(ticketId);
							Log.i(TAG, "ticket.id = " + ticket.getId());
							Log.i(TAG, "ticket.date = " + ticket.getDate());
							Log.i(TAG, "ticket.type = " + ticket.getType());
							
							datasource.deleteTicket(ticket);
							values = datasource.getAllTickets();
							adapter = new ArrayAdapter<Ticket>(TicketListActivity.this,
									android.R.layout.simple_list_item_1, values);
							setListAdapter(adapter);
							//values = datasource.getAllTickets();
							//adapter.remove(ticket);
							adapter.notifyDataSetChanged();
							//listView.invalidateViews();
							//listView.setAdapter(adapter);
							//values.remove(ticket);
							//adapter.remove(ticket);
							//dialog.dismiss();
						}})
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}});
			dialog = builder.create();
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Ticket> adapter = (ArrayAdapter<Ticket>) getListAdapter();
		// Ticket ticket = null;

		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}
