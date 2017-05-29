package com.day0404;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {
	private TextView textView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_content, container, false);
		textView = (TextView) view.findViewById(R.id.textView);
		//因为上面给每个framgent绑定了一个参数，下面就去我们创建的ContentFragment接收传递进来的参数
		String text = getArguments().getString("text");
		textView.setText(text);
		return view;
		
	}

}
