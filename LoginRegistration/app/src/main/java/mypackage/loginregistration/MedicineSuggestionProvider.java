
package mypackage.loginregistration;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicineSuggestionProvider extends ContentProvider {
    SqliteHandler myDB;
    ArrayList<String> medicines;

    @Override
    public boolean onCreate() {
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        myDB = new SqliteHandler(getContext());

        String name = null;
        medicines = myDB.getAllName();

        MatrixCursor matrixCursor = new MatrixCursor(
                new String[]{
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                }
        );
        if (medicines != null) {
            String query = uri.getLastPathSegment().toString();
            int limit = Integer.parseInt(uri.getQueryParameter(
                    SearchManager.SUGGEST_PARAMETER_LIMIT));

            int length = medicines.size();

            for (int i = 0; i < length && matrixCursor.getCount() < limit; i++) {
                String recipe = medicines.get(i);
                if (recipe.contains(query)) {
                    matrixCursor.addRow(new Object[]{i, recipe, i});
                }
            }
        }
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
