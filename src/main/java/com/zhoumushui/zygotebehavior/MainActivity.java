package com.zhoumushui.zygotebehavior;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhoumushui.zygotebehavior.util.HintUtil;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Context context;
    private ListView listBehavior;

    private ArrayList<String> arrayListBehavior;
    private LayoutInflater layoutInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        initialLayout();
    }

    private void initialLayout() {
        listBehavior = (ListView) findViewById(R.id.listBehavior);
        arrayListBehavior = new ArrayList<>();
        arrayListBehavior.add("AvatarImageActivity");
        arrayListBehavior.add("NotExistActivity");
        MyListAdapter myListAdapter = new MyListAdapter(context, arrayListBehavior);
        listBehavior.setAdapter(myListAdapter);

        listBehavior.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                try {
                    String classPath = "com.zhoumushui.zygotebehavior."
                            + arrayListBehavior.get(position);
                    intent.setClass(MainActivity.this,
                            Class.forName(classPath).newInstance().getClass());
                    startActivity(intent);
                    HintUtil.showToast(context, "Start " + arrayListBehavior.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                    HintUtil.showToast(context, "Not found " + arrayListBehavior.get(position));
                }
            }
        });
    }

    class MyListAdapter extends BaseAdapter {
        ArrayList<String> arrayListBehavior;

        public MyListAdapter(Context context, ArrayList<String> arrayListBehavior) {
            layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.arrayListBehavior = arrayListBehavior;
        }

        @Override
        public int getCount() {
            return arrayListBehavior.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayListBehavior.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_list_behavior, null);

                myViewHolder = new MyViewHolder();
                myViewHolder.textBehavior = (TextView) convertView.findViewById(R.id.textBehavior);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }

            myViewHolder.textBehavior.setText(arrayListBehavior.get(position));
            return convertView;
        }

        class MyViewHolder {
            TextView textBehavior;
        }
    }

}
