package com.vin.app2.app;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by orphira on 14-4-25.
 */
public class DiffListViewAdapter extends BaseAdapter {
    private List<? extends Map<String, ?>> mArrayList;
    private int resource;
    private LayoutInflater mLayoutInflater;
    private final int TYPE_COUNT = 2;
    private final int FIRST_VIEW = 0;
    private final int SECOND_VIEW = 1;
    private int currentType;
    public DiffListViewAdapter(Context context,List<? extends Map<String, ?>> data, int res, String[] from,int[] to) {
        this.mArrayList=data;
        this.resource=res;
        mLayoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if (mArrayList==null) {
            return 0;
        } else {
            return (mArrayList.size()+1);
        }
    }
    public void setViewImage(ImageView v, String value) {
        v.setImageResource(R.drawable.no_image);
        ImageLoader.getInstance().displayImage(value, v);
    }

    public void setViewText(TextView v, String text) {
        v.setText(Html.fromHtml(text));
    }

    @Override
    public Object getItem(int i) {
        if (mArrayList==null) {
            return null;
        } else {
            if (i>0) {
                return mArrayList.get(i-1);
            } else {
                return mArrayList.get(i+1);
            }
        }
    }
    public int getViewTypeCount(){
        return TYPE_COUNT;
    }
    public int getItemViewType(int pos){
        if(pos == 0){
            return FIRST_VIEW;
        }else{
            return SECOND_VIEW;
        }
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View firstItemView = null;
        View othersItemView = null;
        currentType =getItemViewType(position);
        if (currentType== FIRST_VIEW) {
            firstItemView = view;
            FirstItemViewHolder firstItemViewHolder=null;
            if (firstItemView==null) {
                firstItemView = mLayoutInflater.inflate(R.layout.bbs_firstitem,null);
                firstItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("=====click first item=======");
                    }
                });
                firstItemViewHolder=new FirstItemViewHolder();
                firstItemViewHolder.imageView=(ImageView) firstItemView.findViewById(R.id.imageView);
                firstItemView.setTag(firstItemViewHolder);

            } else {
                firstItemViewHolder=(FirstItemViewHolder) firstItemView.getTag();
            }

            if (firstItemViewHolder.imageView!=null) {
                firstItemViewHolder.imageView.setImageResource(R.drawable.no_image);
            }

            view=firstItemView;

        } else {
            othersItemView = view;
            OthersViewHolder othersViewHolder=null;
            if (othersItemView==null) {
                othersItemView = mLayoutInflater.inflate(R.layout.bbs_othersitem,null);
                othersViewHolder=new OthersViewHolder();
                othersViewHolder.textView=(TextView) othersItemView.findViewById(R.id.textView);
                othersItemView.setTag(othersViewHolder);
            } else {
                othersViewHolder=(OthersViewHolder) othersItemView.getTag();
            }

            if (mArrayList!=null) {
                if (othersViewHolder.textView!=null) {
                    othersViewHolder.textView.setText((String)(mArrayList.get(position-1).get("content")));
                }

            }

            view=othersItemView;

        }

        return view;
    }
    //第一个Item的ViewHolder
    private class FirstItemViewHolder{
        ImageView imageView;
    }

    //除第一个Item以外其余Item的ViewHolder
    private class OthersViewHolder{
        TextView textView;
    }
}
