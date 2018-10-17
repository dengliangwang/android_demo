package com.jwk.kdjlgp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.morlia.mosdk.MOConstants;
import com.morlia.mosdk.MOError;
import com.morlia.mosdk.MOLog;
import com.morlia.mosdk.MOPlatform;
import com.morlia.mosdk.MOPlugin;
import com.morlia.mosdk.MOProduct;
import com.morlia.mosdk.MOTradeListener;


public class Product_List extends Activity implements MOTradeListener {

	private List<MOProduct> mProducts;
	private ListView listView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product__list);

		mProducts = new ArrayList<MOProduct>();
		MOPlatform platform = MOPlatform.instance();
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

		final MOPlatform aPlatform = MOPlatform.instance();
		if (aPlatform.onActivityResult(this, aRequestCode, aResultCode, aData))
			return;

		super.onActivityResult(aRequestCode, aResultCode, aData);
	}



	public void requestProductsSuccess(MOProduct[] aProducts)
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

		MOLog.info("Demo requestProductsSuccess: %s" + Arrays.toString(aProducts));



	}

	public void requestProductsFailure(MOError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ������Ʒ��Ϣʧ�ܣ����������ԭ��
	     */

		MOLog.info("Demo requestProductsFailure: %s", aError);
	}

	public void buyProductSuccess(Map<String, Object> aArgs)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ����ɹ�����ȡ��Ӧ����Ʒ����
	     */
		MOProduct aProduct = null;

		Object aObj = aArgs.get(MOConstants.ARG_PRODUCT);
		if (null != aObj && aObj instanceof MOProduct)
		{
			aProduct = (MOProduct)aObj;
		}

		MOLog.info("Demo buyProductSuccess: %s", aProduct);
	}

	public void buyProductFailure(MOError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ����ʧ�ܣ����������ԭ��
	     */

		MOLog.info("Demo buyProductFailure: %s", aError);
	}

	public void consumeSuccess(Map<String, Object> aArgs)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ��δʹ��
	     */
	}

	public void consumeFailure(MOError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ��δʹ��
	     */
	}

}
