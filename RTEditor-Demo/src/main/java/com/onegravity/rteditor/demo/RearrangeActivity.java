package com.onegravity.rteditor.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar on 19/Apr/2017.
 */
public class RearrangeActivity extends AppCompatActivity {
    String[] items;
    private ItemAdapter listAdapter;

    public static void show(Activity activity, String[] items) {
        Intent intent = new Intent(activity, RearrangeActivity.class);
        items[items.length - 1] += "<br/>";
        intent.putExtra("items", items);
        activity.startActivityForResult(intent, 1000);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rearrange);

        items = getIntent().getExtras().getStringArray("items");
        DragListView mDragListView = (DragListView) findViewById(R.id.list);
        mDragListView.setDragEnabled(true);

        mDragListView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new ItemAdapter(makeList(items), R.layout.list_item, R.id.grabView, false);
        mDragListView.setAdapter(listAdapter, false);
        mDragListView.setCanDragHorizontally(false);
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("items", getArray(listAdapter.getItemList())));
                finish();
            }
        });
    }

    private String[] getArray(List<Pair<Long, String>> itemList) {
        String[] list = new String[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            list[i] = itemList.get(i).second;
        }
        return list;
    }

    private ArrayList<Pair<Long, String>> makeList(String[] items) {
        ArrayList<Pair<Long, String>> list = new ArrayList<Pair<Long, String>>();
        long i = 0;
        for (String item : items) {
            list.add(new Pair<Long, String>(i++, item));
        }
        return list;
    }
}
