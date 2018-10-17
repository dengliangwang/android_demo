package com.jwk.kdjlgp;

import java.util.HashMap;
import java.util.List;

import com.morlia.mosdk.MOLog;
import com.morlia.mosdk.MOPlatform;
import com.morlia.mosdk.MOProduct;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ProductListAdapter extends BaseAdapter implements View.OnClickListener {
	
	private Context context;
	private List<MOProduct> data;
	private Activity activity;

	public ProductListAdapter(List<MOProduct> aData,Activity aActivity) {
		// TODO Auto-generated constructor stub
		this.data = aData;
		this.activity = aActivity;
	}
	
	public void setDta(List<MOProduct> data) {
		this.data = data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (context == null) {
			context = arg2.getContext();
		}
		
		if (arg1 == null) {
			
			arg1 = LayoutInflater.from(arg2.getContext()).inflate(R.layout.activity_product__list_cell, null);
		}
		
		MOProduct product = data.get(arg0);
		
		TextView textView = (TextView)arg1.findViewById(R.id.productid);
		textView.setText(product.getName());

		Button buy = (Button)arg1.findViewById(R.id.buytag);
		buy.setTag(R.id.product_buy_btn_tag, arg0);
		buy.setOnClickListener(this);
		
		
		return arg1;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		int id = arg0.getId();
		
		switch (id) {
		case R.id.buytag:
			
			int tag = Integer.parseInt(arg0.getTag(R.id.product_buy_btn_tag).toString());
			HashMap<String, String> aParams = new HashMap<String, String>();
			aParams.put("server",	"server");
			aParams.put("role",		"role");
			aParams.put("extra1",	"1LZuZ3uVFQR7EAlUZouDZ1Z1Zto6ouvz");
			aParams.put("extra2",	"0.99");
			MOPlatform platform = MOPlatform.instance();
			platform.buyProduct(activity, data.get(tag), aParams);
			MOLog.info("点击了 " + tag + " 列");
			
			break;
			
		case R.id.productid:
			
			break;

		default:
			break;
		}
		
	}

}
