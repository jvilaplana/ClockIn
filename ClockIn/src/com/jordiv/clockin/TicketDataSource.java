package com.jordiv.clockin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TicketDataSource {
		// Database fields
		private SQLiteDatabase database;
		private ClockInOpenHelper dbHelper;
		private String[] allColumns = { ClockInOpenHelper.COLUMN_ID,
				ClockInOpenHelper.COLUMN_DATE, ClockInOpenHelper.COLUMN_TYPE };

		public TicketDataSource(Context context) {
			dbHelper = new ClockInOpenHelper(context);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public Ticket createTicket(long date, Boolean type) {
			ContentValues values = new ContentValues();
			values.put(ClockInOpenHelper.COLUMN_DATE, date);
			values.put(ClockInOpenHelper.COLUMN_TYPE, type);
			long insertId = database.insert(ClockInOpenHelper.TABLE_TICKETS, null, values);
			// To show how to query
			Cursor cursor = database.query(ClockInOpenHelper.TABLE_TICKETS,
					allColumns, ClockInOpenHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			return cursorToTicket(cursor);
		}

		public void deleteTicket(Ticket ticket) {
			long id = ticket.getId();
			System.out.println("Ticket deleted with id: " + id);
			database.delete(ClockInOpenHelper.TABLE_TICKETS, ClockInOpenHelper.COLUMN_ID + " = " + id, null);
		}

		public List<Ticket> getAllTickets() {
			List<Ticket> tickets = new ArrayList<Ticket>();
			Cursor cursor = database.query(ClockInOpenHelper.TABLE_TICKETS, allColumns, null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Ticket ticket = cursorToTicket(cursor);
				tickets.add(ticket);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return tickets;
		}

		private Ticket cursorToTicket(Cursor cursor) {
			Ticket ticket = new Ticket();
			ticket.setId(cursor.getLong(0));
			long date = cursor.getLong(1);
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.setTimeInMillis(date);
			ticket.setDate(calendar.getTime());
			ticket.setType(cursor.getInt(2) > 0);
			return ticket;
		}
}
