package com.example.deepakrattan.contentproviderdemo1;

import android.net.Uri;

/**
 * Created by Deepak Rattan on 04-Nov-17.
 */

public class Contract {
    private Contract() {
    }

    // Customarily, to make Authority unique, it's the package name extended with "provider".
    // Must match with the authority defined in Android Manifest.
    public static final String AUTHORITY = "com.example.deepakrattan.contentproviderdemo1.provider";

    // The content path is an abstract semantic identifier of the data you are interested in.
    // It does not predict or presume in what form the data is stored or organized in the
    // background. As such, "words" could resolve into the name of a table, the name of a file,
    // or in this example, the name of a list.
    public static final String CONTENT_PATH = "words";

    // A content:// style URI to the authority for this table */

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    //Constants

    //This is the dataset name you will use when retrieving all the words.
    //The value is -2 because that's the first lowest value
    // not returned by a method call.
    public static final int ALL_ITEMS = -2;

    //This is the id you will use when retrieving a single word.
    public static final String WORD_ID = "id";

    //MIME types for this content provider
    //MIME types are of the form type/subtype, such as text/html for HTML pages
    public static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd.com.example.provider.words";
    public static final String MULTIPLE_RECORD_MIME_TYPE = "vnd.android.cursor.dir/vnd.com.example.provider.words";


}
