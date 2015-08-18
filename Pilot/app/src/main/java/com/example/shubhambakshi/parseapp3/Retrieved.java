package com.example.shubhambakshi.parseapp3;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.*;
import com.parse.ParseException;
import com.parse.ParseObject;
import android.content.Context;
import java.text.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.util.*;
import android.content.Intent;

import org.w3c.dom.Text;
public class Retrieved extends MainActivity {

    public List<ParseObject> retrieveData(final String searchString , String searchParam) {



        String names, ids, emails, phones;
        String Name, Email, Phone, Id;


        final MainActivity main = new MainActivity();
        final List<ParseObject> newObjectList = new ArrayList<ParseObject>();

        final ParseObject testObject = new ParseObject("PARSE");
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("PARSE");
        query.whereMatches(searchParam.toString(), "[A-Za-z]");


        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(final List<ParseObject> testObjectList, ParseException e) {
                if (e == null) {

                    if (testObjectList.isEmpty()) {

                        Toast.makeText(getApplicationContext(), "NO MATCH FOUND", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Iterator<ParseObject> itr = main.validList.iterator();
                    String str = itr.next().getString("FirstName").toString();
                    main.validList = checkAndCreateNewList(testObjectList, searchString);

                    //populateList();

                    Log.d("test", "Retrieved " + testObjectList.size() + "PARSE");

                } else {
                    Toast bread;
                    bread = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);
                    bread.show();
                    Log.d("test", "Error: " + e.getMessage());
                }


            }

        });
        return main.validList;

    }




    public  List<ParseObject> checkAndCreateNewList(List<ParseObject> testObjectList, String pattern){
        Iterator<ParseObject> itr= testObjectList.iterator();
        ArrayList<ParseObject> validList=new ArrayList<>();
        while(itr.hasNext()){
            ParseObject newObject = itr.next();
            if((isSubstring(newObject.get("FirstName").toString(),pattern))){
                validList.add(newObject);
              //  Toast.makeText(getApplicationContext(),validList.get(0).get("FirstName").toString(),Toast.LENGTH_LONG).show();
            }
        }
        return validList;

    }

    public boolean isSubstring(String text,String pattern) {
        if(text.toLowerCase().contains(pattern.toLowerCase())){
            return true;
        }
        return false;
    }

}