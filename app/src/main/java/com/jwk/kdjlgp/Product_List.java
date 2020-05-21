package com.jwk.kdjlgp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.ujhgl.lohsy.ljsomsh.HYCenter;
import com.ujhgl.lohsy.ljsomsh.HYConstants;
import com.ujhgl.lohsy.ljsomsh.HYError;
import com.ujhgl.lohsy.ljsomsh.HYLog;
import com.ujhgl.lohsy.ljsomsh.HYProduct;
import com.ujhgl.lohsy.ljsomsh.HYTradeDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;




public class Product_List extends Activity implements HYTradeDelegate {

	private List<HYProduct> mProducts;
	private ListView listView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product__list);

		mProducts = new ArrayList<HYProduct>();
		HYCenter platform = HYCenter.shared();
		platform.setTradeDelegate(this);
		platform.getProductsFromStore(this);

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

		final HYCenter aPlatform = HYCenter.shared();
		if (aPlatform.onActivityResult(this, aRequestCode, aResultCode, aData))
			return;

		super.onActivityResult(aRequestCode, aResultCode, aData);
	}



	public void requestProductsSuccess(HYProduct[] aProducts)
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

		HYLog.info("Demo requestProductsSuccess: %s" + Arrays.toString(aProducts));



	}

	public void requestProductsFailure(HYError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ������Ʒ��Ϣʧ�ܣ����������ԭ��
	     */

		HYLog.info("Demo requestProductsFailure: %s", aError);
	}

	public void buyProductSuccess(Map<String, Object> aArgs)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ����ɹ�����ȡ��Ӧ����Ʒ����
	     */
		HYProduct aProduct = null;

		Object aObj = aArgs.get(HYConstants.ARG_PRODUCT);
		if (null != aObj && aObj instanceof HYProduct)
		{
			aProduct = (HYProduct)aObj;
		}

		HYLog.info("Demo buyProductSuccess: %s", aProduct);
	}

	public void buyProductFailure(HYError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ����ʧ�ܣ����������ԭ��
	     */

		HYLog.info("Demo buyProductFailure: %s", aError);
	}

	public void consumeSuccess(Map<String, Object> aArgs)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ��δʹ��
	     */
	}

	public void consumeFailure(HYError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ��δʹ��
	     */
	}

}
