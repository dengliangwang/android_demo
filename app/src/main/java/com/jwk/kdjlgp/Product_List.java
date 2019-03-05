package com.jwk.kdjlgp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ujhgl.lohsy.ljsomsh.PTConstants;
import com.ujhgl.lohsy.ljsomsh.PTController;
import com.ujhgl.lohsy.ljsomsh.PTError;
import com.ujhgl.lohsy.ljsomsh.PTGoods;
import com.ujhgl.lohsy.ljsomsh.PTLog;
import com.ujhgl.lohsy.ljsomsh.PTTradeCallBack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;




public class Product_List extends Activity implements PTTradeCallBack {

	private List<PTGoods> mProducts;
	private ListView listView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product__list);

		mProducts = new ArrayList<PTGoods>();
		PTController platform = PTController.instance();
		platform.setTradeListener(this);
		platform.requestProducts(this);

		listView = (ListView) findViewById(R.id.product_list_view);

		ProductListAdapter productListAdapter = new ProductListAdapter(mProducts,this);
		listView.setAdapter(productListAdapter);

		Button button = (Button)findViewById(R.id.backtomainactivity);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Product_List.this, MainActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public void onActivityResult(int aRequestCode, int aResultCode, Intent aData)
	{

		final PTController aPlatform = PTController.instance();
		if (aPlatform.onActivityResult(this, aRequestCode, aResultCode, aData))
			return;

		super.onActivityResult(aRequestCode, aResultCode, aData);
	}



	public void requestProductsSuccess(PTGoods[] aProducts)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ������Ʒ��Ϣ�ɹ�����ȡ���Թ������Ʒ��Ϣ�б�
	     * δȡ����Ʒ��Ϣ�ģ���Ӧ��Ʒ����ֹ�������ʾ����ǰ�޷����򡱡�
	     */

			if (mProducts.size() > 0) {
				mProducts.clear();
			}



		for (int i = 0; i < aProducts.length; i++) {
			mProducts.add(aProducts[i]);
		}
		ProductListAdapter productListAdapter = (ProductListAdapter)listView.getAdapter();
		productListAdapter.setDta(mProducts);
		productListAdapter.notifyDataSetChanged();

		PTLog.info("Demo requestProductsSuccess: %s" + Arrays.toString(aProducts));



	}

	public void requestProductsFailure(PTError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ������Ʒ��Ϣʧ�ܣ����������ԭ��
	     */

		PTLog.info("Demo requestProductsFailure: %s", aError);
	}

	public void buyProductSuccess(Map<String, Object> aArgs)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ����ɹ�����ȡ��Ӧ����Ʒ����
	     */
		PTGoods aProduct = null;

		Object aObj = aArgs.get(PTConstants.ARG_PRODUCT);
		if (null != aObj && aObj instanceof PTGoods)
		{
			aProduct = (PTGoods)aObj;
		}

		PTLog.info("Demo buyProductSuccess: %s", aProduct);
	}

	public void buyProductFailure(PTError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ����ʧ�ܣ����������ԭ��
	     */

		PTLog.info("Demo buyProductFailure: %s", aError);
	}

	public void consumeSuccess(Map<String, Object> aArgs)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ��δʹ��
	     */
	}

	public void consumeFailure(PTError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ��δʹ��
	     */
	}

}
