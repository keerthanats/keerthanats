package com.example.shubhambakshi.parseapp3;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import android.content.Intent;
import java.util.Iterator;
import java.util.List;
import com.parse.ParseQuery;
import java.util.ArrayList;

/**
 * Created by User on 07-08-2015.
 */
public class Searchoperate extends Activity {
    List<ParseObject> validList = new ArrayList<ParseObject>();
    ListView contactListView1;
    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        final ParseObject testObject = new ParseObject("PARSE");
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("PARSE");


        query.whereMatches(MainActivity.searchParam,"[A-Za-z]");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> testObjectlist, ParseException e) {
                if (e == null) {

                    validList = checkAndCreateList(testObjectlist, MainActivity.searchString);
                    if (validList.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "No Match found", Toast.LENGTH_LONG).show();
                        return;
                    }
                    populateList();


                }
            }
        });
    }

    private List<ParseObject> checkAndCreateList(List<ParseObject> list, String pattern){
        List<ParseObject> validList =  new ArrayList<>();
        Iterator<ParseObject> itr = list.iterator();
        while(itr.hasNext()){
            ParseObject testObject = itr.next();
            if(isSubstring(testObject.get(MainActivity.searchParam.toString()).toString(),pattern)){
                validList.add(testObject);
            }
        }
        return validList;
    }

    private boolean isSubstring(String text, String pattern){
        if(text.toLowerCase().contains(pattern.toLowerCase())){
            return true;
        }
        return false;
    }


        private void populateList(){
            //  ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,validList);
         //   Toast.makeText(getApplicationContext(),"populate",Toast.LENGTH_LONG).show();
            try {
                ArrayAdapter adapter = new contactListAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, validList);
                setContentView(R.layout.contactlistview);
                contactListView1 = (ListView) findViewById(R.id.listView);
                contactListView1.setAdapter(adapter);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }



        }
        public class contactListAdapter extends ArrayAdapter {
            public contactListAdapter(Context context,int id,List validlist){
                //   public contactListAdapter(){
                //  super(MainActivity.this,R.layout.listview_items,validList);
                super(context,id,validlist);
            }


            @Override
            public View getView(int position, View view, ViewGroup parent) {
                //  view = super.getView(position, view, parent);

                if(view == null)
                    view = getLayoutInflater().inflate(R.layout.listview_items,parent,false);
                Iterator<ParseObject> itr = validList.iterator();
                ParseObject currentContact = validList.get(position);

                //   setContentView(R.layout.listview_items);

                TextView name = (TextView) view.findViewById(R.id.tv_name);
                TextView email = (TextView) view.findViewById(R.id.tv_email);
                TextView phone = (TextView) view.findViewById(R.id.tv_phone);
                TextView id = (TextView) view.findViewById(R.id.tv_id);

                //  setContentView(R.layout.listview_items);
                name.setText("FirstName: "+currentContact.get("FirstName").toString());
                email.setText("Email: "+currentContact.get("Email").toString());
                phone.setText("PhoneNumber: "+currentContact.get("PhoneNumber").toString());
                id.setText("ID: "+currentContact.get("ID").toString());


                return view;

            }
            @Override
            public ParseObject getItem(int position){
                return validList.get(position);
            }
            @Override
            public boolean hasStableIds() {
                return true;
            }


        }

    }




