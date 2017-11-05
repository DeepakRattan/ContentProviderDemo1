package com.example.deepakrattan.contentproviderdemo1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static java.lang.Integer.parseInt;

/**
 * Created by Deepak Rattan on 04-Nov-17.
 */

public class MyContentProvider extends ContentProvider {
    private static final String TAG = MyContentProvider.class.getSimpleName();
    public String[] data;
    //UriMatcher is a helper class for processing the accepted Uri schemes
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    @Override
    public boolean onCreate() {
        initializeUriMatching();
        Context context = getContext();
        //Getting data from string-array
        data = context.getResources().getStringArray(R.array.words);
        return true;
    }

    private void initializeUriMatching() {
        // Matches a URI that references one word in the list by its index.
        // The # symbol matches a string of numeric characters of any length.
        // For this sample, this references the first, second, etc. words in the list.
        // For a database, this could be an ID.
        // Note that addURI expects separate authority and path arguments.
        // The last argument is the integer code to assign to this URI pattern.
        uriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH + "/#", 1);
        // Matches a URI that is just the authority + the path,
        // triggering the return of all words.
        uriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH, 0);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int id = -1;
        // Analyze the URI and build a query from the arguments.
        // This code changes depending on your backend.
        // This example uses the matcher to get the integer code for this URI pattern.

        // Determine which integer code corresponds to the URI, then switch on it.
        switch (uriMatcher.match(uri)) {
            case 0:
                // Matches URI to get all of the entries.
                id = Contract.ALL_ITEMS;
                // Look at the remaining arguments to see whether there are constraints.
                // In this example, we only support getting a specific entry by id. Not full search.
                // For a real-life app, you need error-catching code; here we assume that the
                // value we need is actually in selectionArgs and valid.
                if (selection != null) {
                    id = Integer.parseInt(selectionArgs[0]);
                }
                break;
            case 1:
                // The URI ends in a numeric value, which represents an id.
                // Parse the URI to extract the value of the last, numeric part of the path,
                // and set the id to that value.
                id = parseInt(uri.getLastPathSegment());
                // With a database, you would then use this value and the path to build a query.
                break;
            case UriMatcher.NO_MATCH:
                // You should do some error handling here.
                Log.d(TAG, "NO MATCH FOR THIS URI IN SCHEME.");
                id = -1;
                break;
            default:
                // You should do some error handling here.
                Log.d(TAG, "INVALID URI - URI NOT RECOGNZED.");
                id = -1;
        }
        return populateCursor(id);
    }

    private Cursor populateCursor(int id) {
        // The query() method must return a cursor.
        // If you are not using data storage that returns a cursor,
        // you can use a simple MatrixCursor to hold the data to return.
        MatrixCursor cursor = new MatrixCursor(new String[]{Contract.CONTENT_PATH});
        // If there is a valid query, execute it and add the result to the cursor.
        if (id == Contract.ALL_ITEMS) {
            for (int i = 0; i < data.length; i++) {
                String word = data[i];
                cursor.addRow(new Object[]{word});
            }
        } else if (id >= 0) {
            // Execute the query to get the requested word.
            String word = data[id];
            // Add the result to the cursor.
            cursor.addRow(new Object[]{word});
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 0:
                return Contract.MULTIPLE_RECORD_MIME_TYPE;
            case 1:
                return Contract.SINGLE_RECORD_MIME_TYPE;
            default:
                // Alternatively, throw an exception.
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: ");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete: ");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: ");
        return 0;
    }
}
