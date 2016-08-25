package com.yhy.contractlistview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yhy.contractlistview.R;
import com.yhy.contractlistview.adapter.ChargeAdapter;
import com.yhy.contractlistview.adapter.OperationAdapter;
import com.yhy.contractlistview.data.Charge;
import com.yhy.contractlistview.data.Operation;

import java.util.ArrayList;


/**
 * 作者 : YangHaoyi on 2016/8/25.
 * 邮箱 ：yanghaoyi@neusoft.com
 */
public class ListExpandActivity extends FragmentActivity {

    private ListView expandListview, normalListview;
    private OperationAdapter mAdapterNormal;
    private ChargeAdapter mAdapter;
    private ArrayList<Charge> items = new ArrayList<Charge>();
    private ArrayList<Operation> itemsNormal = new ArrayList<Operation>();
    private LinearLayout expandLinear,linearTitle,listTop,listBottom;
    private int expandViewHeight,norHeight,titleHeight,listTopHeight;
    private TextView showList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listexpand);
        init();
        System.out.println("ScrollViewIn");
    }

    private void init() {
        expandListview = (ListView) findViewById(R.id.expandListview);
        expandLinear = (LinearLayout) findViewById(R.id.expandLinear);
        linearTitle = (LinearLayout) findViewById(R.id.linearTitle);
        listTop = (LinearLayout) findViewById(R.id.listTop);
        listBottom = (LinearLayout) findViewById(R.id.listBottom);
        geneItems();
        mAdapter = new ChargeAdapter(items,this);
        expandListview.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(expandListview);



        normalListview = (ListView) findViewById(R.id.normalListview);
        geneItemsNormal();
        mAdapterNormal =new OperationAdapter(itemsNormal,this);
        normalListview.setAdapter(mAdapterNormal);
        setListViewHeightBasedOnChildren1(normalListview);
        ViewGroup.LayoutParams params = expandLinear.getLayoutParams();
        params.height = 0;
        expandLinear.setLayoutParams(params);



        showList = (TextView) findViewById(R.id.showList);
        showList.setOnClickListener(new View.OnClickListener() {

            boolean isExpand;
            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                expandLinear.clearAnimation();
                final int deltaValue1;
                final int startValue;
                int durationMillis = 350;
                int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                linearTitle.measure(w, titleHeight);
                titleHeight = linearTitle.getMeasuredHeight();
                int width = linearTitle.getMeasuredWidth();
                listTop.measure(0, 0);
                listTopHeight = listTop.getMeasuredHeight();
                /***  坑点，由于getMeasuredHeight无法计算自己的高度，必须用linear嵌套linear，子linear设置高度值，父linear warp ***/
                /***  坑点，由于gerMeasuredHeight无法计算margin的值，所以要用两个view设置高度代替即listTopHeight*2代替20*2，因为xml中的dp与view中是按比例系数换算的 ****/
                if (isExpand) {
                    System.out.println("startValue__is__true");
                    System.out.println("titleHeight_is:"+titleHeight);
                    deltaValue1 = (0+expandViewHeight+titleHeight+listTopHeight*2) - 0;
                    System.out.println("deltaValue1_is:"+deltaValue1);
                    System.out.println("expandViewHeight_is:"+expandViewHeight);
                    startValue = 0;
                } else {
                    System.out.println("startValue__is__false");
                    deltaValue1 = 0 - expandViewHeight-titleHeight-listTopHeight*2;
                    startValue = expandViewHeight+titleHeight+listTopHeight*2;
                }

                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        ViewGroup.LayoutParams params = expandLinear.getLayoutParams();
                        params.height = (int) (startValue + deltaValue1 * interpolatedTime);
                        System.out.println("interpolatedTime_is:"+interpolatedTime);
                        System.out.println("ExpandHeight_is:"+params.height);
                        expandLinear.setLayoutParams(params);
                    }

                };
                animation.setDuration(durationMillis);
                expandLinear.startAnimation(animation);
            }
        });
    }
    private void geneItems() {
        Charge charge = new Charge();
        charge.setTime("2016-08-25 07:01-13:00");
        charge.setDegree("3.12度");
        Charge charge1 = new Charge();
        items.add(charge);
        charge1.setTime("2016-08-25 13:01-23:00");
        charge1.setDegree("9.12度");
        items.add(charge1);
        Charge charge2 = new Charge();
        charge2.setTime("2016-08-25 13:01-00:00");
        charge2.setDegree("3.12度");
        items.add(charge2);
        Charge charge3 = new Charge();
        charge3.setTime("2016-08-25 00:01-01:10");
        charge3.setDegree("0.12度");
        items.add(charge3);
    }

    private void geneItemsNormal() {
        Operation operation = new Operation();
        operation.setTime("2016-06-07 17:23:30");
        operation.setOperation("订单开始");
        itemsNormal.add(operation);
        Operation operation1 = new Operation();
        operation1.setTime("2016-06-07 17:23:30");
        operation1.setOperation("开始充电");
        itemsNormal.add(operation1);
        Operation operation2 = new Operation();
        operation2.setTime("2016-06-07 17:26:15");
        operation2.setOperation("结束充电");
        itemsNormal.add(operation2);
    }
    public  void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            System.out.println("itemHeight_is"+totalHeight);
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        expandViewHeight = params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void setListViewHeightBasedOnChildren1(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if(null != listItem){
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
