package com.demo.example.dailyincomeexpensemanager;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.demo.example.dailyincomeexpensemanager.bean.DataBean;
import com.demo.example.widget.CircleImageView;
import com.demo.example.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class IncomeExpenceListAdapter extends BaseAdapter {
    private TextView amount;
    private TextView categoryName;
    private Context context;
    private List<DataBean> data;
    private TextView date;
    private String[] day2String = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private ImageView image;
    private CircleImageView imgCategory;
    private LayoutInflater influter;
    private TextView note;
    private TextView tvmethod;

    public IncomeExpenceListAdapter(Context context, List<DataBean> list) {
        this.context = context;
        this.data = list;
        this.influter = (LayoutInflater) this.context.getSystemService("layout_inflater");
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public DataBean getItem(int i) {
        return this.data.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (TextUtils.isEmpty(getItem(i).getId())) {
            return 0;
        }
        return Long.parseLong(getItem(i).getId());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Date date;
        ParseException e;
        if (view == null) {
            view = this.influter.inflate(R.layout.list_item_manager, (ViewGroup) null);
        }
        String date2 = this.data.get(i).getDate();
        Log.e("date", "date " + date2);
        this.amount = (TextView) view.findViewById(R.id.item_amount);
        this.categoryName = (TextView) view.findViewById(R.id.item_cat_name);
        this.note = (TextView) view.findViewById(R.id.item_note);
        this.date = (TextView) view.findViewById(R.id.item_date);
        this.imgCategory = (CircleImageView) view.findViewById(R.id.item_category);
        this.image = (ImageView) view.findViewById(R.id.item_pending);
        this.tvmethod = (TextView) view.findViewById(R.id.tv_method);
        int i2 = -16777216;
        try {
            i2 = Color.parseColor(this.data.get(i).getCategoryModel().getColor());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.imgCategory.setColorFilter(i2);
        this.imgCategory.setCircleBackgroundColor(i2);
        this.imgCategory.setBorderColor(i2);
        String replaceAll = this.data.get(i).getAmount().replaceAll(",", "");
        if (!TextUtils.isEmpty(replaceAll)) {
            String currencySymbol = ((BaseActivity) this.context).getCurrencySymbol();
            if (currencySymbol.equals("¢") || currencySymbol.equals("₣") || currencySymbol.equals("₧") || currencySymbol.equals("﷼") || currencySymbol.equals("₨")) {
                try {
                    this.amount.setText("" + ((int) MyUtils.RoundOff(Double.parseDouble(replaceAll))) + currencySymbol);
                } catch (NumberFormatException unused) {
                    this.amount.setText("0" + currencySymbol);
                }
            } else {
                try {
                    this.amount.setText("" + currencySymbol + ((int) MyUtils.RoundOff(Double.parseDouble(replaceAll))));
                } catch (NumberFormatException unused2) {
                    this.amount.setText(currencySymbol + "0");
                }
            }
        }
        this.categoryName.setTextColor(i2);
        this.categoryName.setText(this.data.get(i).getCategoryModel().getName());
        if (!TextUtils.isEmpty(getItem(i).getRefRecurring())) {
            view.findViewById(R.id.item_pending).setVisibility(View.VISIBLE);
            this.image.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        } else {
            this.image.setImageURI(Uri.parse(this.data.get(i).getImage()));
        }
        String note = this.data.get(i).getNote();
        if (note.equalsIgnoreCase("")) {
            this.note.setVisibility(View.GONE);
        } else {
            this.note.setText(note);
        }
        String method = this.data.get(i).getMethod();
        if (method.equalsIgnoreCase("")) {
            this.tvmethod.setVisibility(View.GONE);
        } else {
            this.tvmethod.setText(method);
        }
        Date date3 = new Date();
        try {
            date = MyUtils.sdfUser.parse(date2);
            Log.e("date " + date.getDate(), "day " + this.day2String[date.getDay()]);
        } catch (ParseException e4) {
            e = e4;
            date = date3;
        }
        this.date.setText("" + date.getDate());
        this.date.setText(new SimpleDateFormat("MMM d").format(date));
        return view;
    }
}
