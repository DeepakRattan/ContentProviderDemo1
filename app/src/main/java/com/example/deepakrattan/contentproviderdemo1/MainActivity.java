package com.example.deepakrattan.contentproviderdemo1;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAllWords, btnFirstWord;
    private TextView txtWords;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewByID
        txtWords = (TextView) findViewById(R.id.txtWords);
        btnAllWords = (Button) findViewById(R.id.btnAllWords);
        btnFirstWord = (Button) findViewById(R.id.btnFirstWord);

        btnAllWords.setOnClickListener(this);
        btnFirstWord.setOnClickListener(this);
        txtWords.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        // URI That identifies the content provider and the table.
        String queryUri = Contract.CONTENT_URI.toString();

        // The columns to return for each row. Setting this to null returns all of them.
        // When there is only one column, as in the case of this example, setting this
        // explicitly is optional, but can be helpful for documentation purposes.
        String[] projection = new String[]{Contract.CONTENT_PATH}; // Only get words.

        // Argument clause for the selection criteria for which rows to return.
        // Formatted as an SQL WHERE clause (excluding the WHERE itself).
        // Passing null returns all rows for the given URI.
        String selectionClause;

        // Argument values for the selection criteria.
        // If you include ?s in selection, they are replaced by values from selectionArgs,
        // in the order that they appear.
        // IMPORTANT: It is a best security practice to always separate selection and selectionArgs.
        String selectionArgs[];

        // The order in which to sort the results.
        // Formatted as an SQL ORDER BY clause (excluding the ORDER BY keyword).
        // Usually ASC or DESC; null requests the default sort order, which could be unordered.
        String sortOrder = null; // For this example, we accept the order returned by the response.

        // Set selection criteria depending on which button was pressed.
        switch (v.getId()) {
            case R.id.btnAllWords:
                txtWords.setText(" ");
                selectionClause = null;
                selectionArgs = null;
                break;
            case R.id.btnFirstWord:
                txtWords.setText(" ");
                selectionClause = Contract.WORD_ID + " = ?";
                selectionArgs = new String[]{"0"};
                break;
            default:
                selectionClause = null;
                selectionArgs = null;
        }

        // Let the content resolver parse the query and do the right things with it.
        // If you provide a well-formed query, the results should always be civilized.
        // This is magic that is explained in the next practical.
        // We don't need to create a custom content resolver,
        // ...we just use the one already there for our app context.

        Cursor cursor = getContentResolver().query(Uri.parse(queryUri), projection, selectionClause, selectionArgs, sortOrder);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(projection[0]);
                String word = cursor.getString(index);
                txtWords.append(word + "\n");
            }
        } else {
            Log.d(TAG, "onClickDisplayEntries " + "No data returned.");
            txtWords.append("No data returned." + "\n");

        }
    }
}
