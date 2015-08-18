package com.example.shubhambakshi.parseapp3;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseObject;
import android.content.Intent;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by User on 07-08-2015.
 */
public class MainActivity extends Activity {
    Button button;
    static TextView editText;
    static String searchString;
    static String searchParam;
    ListView contactListView1;
    List<ParseObject> validList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "UScatQxieE3MNKjYZlfH5WvkuRQxz1rmcheGHtNL", "tOQY1uuBcfXbKbbDGZgnxAPg8YQMsrDfBEGcatsQ");
        }catch(Exception e){
            Toast.makeText(getApplication(),"Problem connecting to the database",Toast.LENGTH_LONG).show();
            return;
        }
        editText = (TextView)findViewById(R.id.editText2);
        if(editText.isEnabled()){
            editText.setEnabled(false);

        }
        addListenerOnButton();
        addListenerOnButton1();
    }

    private void addListenerOnButton() {
        final RadioButton radio_name = (RadioButton) findViewById(R.id.fname);
        final RadioButton radio_phone = (RadioButton) findViewById(R.id.contact);
        final RadioButton radio_email = (RadioButton) findViewById(R.id.email);
        radio_name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radio_phone.isChecked()) radio_phone.setChecked(false);
                if(radio_email.isChecked()) radio_email.setChecked(false);
                workWithRadio(radio_name, "FirstName");

            }
        });

        radio_email.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_phone.isChecked()) radio_phone.setChecked(false);
                if (radio_name.isChecked()) radio_name.setChecked(false);
                workWithRadio(radio_email, "Email");

            }
        });

        radio_phone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_email.isChecked()) radio_email.setChecked(false);
                if (radio_name.isChecked()) radio_name.setChecked(false);
                workWithRadio(radio_phone, "PhoneNumber");

            }
        });

    }

    private void workWithRadio(RadioButton radio,final String param1) {
        if (!editText.isEnabled()) {
            enableEditText();
        }
        searchParam = param1.toString();
        //validateSearchParam(searchParam);
    }

    private void enableEditText(){
        editText.setEnabled(true);
        editText.setFocusable(true);

    }

   /* private void validateSearchParam(String param){
            Pattern pattern = Pattern.compile("[A-Za-z]]");
        
            if(searchParam.equals("FirstName") && !searchParam.contains(pattern));

    }*/

    public void populateList(List<ParseObject> list){

        //     ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,MainActivity.validList);
        ArrayAdapter adapter = new contactListAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,validList);
        // ArrayAdapter adapter = new contactListAdapter();
        setContentView(R.layout.contactlistview);
        contactListView1 = (ListView)findViewById(R.id.listView);
        try {
            contactListView1.setAdapter(adapter);
        }catch(Exception e1){
            Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
        }
    }




    public class contactListAdapter extends ArrayAdapter {
        public contactListAdapter(Context context,int id,List validlist){
            //     public contactListAdapter(){
            //   super(getApplicationContext(),R.layout.listview_items,MainActivity.validList);
            super(context,id,validlist);
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            //  view = super.getView(position, view, parent);

            if(view == null)
                view = getLayoutInflater().inflate(R.layout.listview_items,parent,false);
            Iterator<ParseObject> itr = validList.iterator();
            ParseObject currentContact = validList.get(position);



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

    private void addListenerOnButton1(){
        Button button = (Button)findViewById(R.id.findbutton);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(),Searchoperate.class);
                startActivity(intent);
            }
        });
    }


}
