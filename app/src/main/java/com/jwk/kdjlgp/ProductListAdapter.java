package com.jwk.kdjlgp;

import java.util.HashMap;
import java.util.List;




import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ujhgl.lohsy.ljsomsh.HYCenter;
import com.ujhgl.lohsy.ljsomsh.HYProduct;
import com.ujhgl.lohsy.ljsomsh.HYLog;

public class ProductListAdapter extends BaseAdapter implements View.OnClickListener {
	
	private Context context;
	private List<HYProduct> data;
	private Activity activity;

	public ProductListAdapter(List<HYProduct> aData,Activity aActivity) {
		// TODO Auto-generated constructor stub
		this.data = aData;
		this.activity = aActivity;
	}
	
	public void setDta(List<HYProduct> data) {
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
		
		HYProduct product = data.get(arg0);
		
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
			aParams.put("server",	"10");
			aParams.put("role",		"Vayne");
			//商品编号(如果同一个商品id，对应了不同的商品，可使用该字段作区分,如果一个商品id对应一个商品，此字段可忽略)
			aParams.put("number","0");
			//可选自定义参数
			aParams.put("extra1",	"1LZuZ3uVFQR7EAlUZouDZ1Z1Zto6ouvz");
			aParams.put("extra2",	"0.99");
			HYCenter platform = HYCenter.shared();
			platform.purchaseProduct(activity, data.get(tag), aParams);
			
			break;
			
		case R.id.productid:
			
			break;

		default:
			break;
		}
		
	}

}
